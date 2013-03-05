package ru.korus.tmis.core.database

import javax.interceptor.Interceptors
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.ejb.{EJB, Stateless}
import grizzled.slf4j.Logging
import ru.korus.tmis.util.{ConfigManager, CAPids, I18nable}
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.entity.model.{Job, ActionTypeTissueType, Action, JobTicket}
import scala.collection.JavaConversions._
import collection.mutable
import ru.korus.tmis.core.data.TakingOfBiomaterialRequesDataFilter
import ru.korus.tmis.core.auth.AuthData
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.util.reflect.LoggingManager
import java.util.Date

/**
 * Методы для работы с JobTicket
 * @author idmitriev Systema-Soft
 * @since 1.0.0.64
 */
@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbJobTicketBean extends DbJobTicketBeanLocal
                         with Logging
                         with I18nable
                         with CAPids {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  @EJB
  var appLock: AppLockBeanLocal = _

  @EJB
  private var dbManager: DbManagerBeanLocal = _

  def getJobTicketById(id: Int): JobTicket = {
    val result = em.createQuery(JobTicketByIdQuery, classOf[JobTicket])
                   .setParameter("id", id)
                   .getResultList

    result.size match {
      case 0 => {
        throw new CoreException(
          ConfigManager.ErrorCodes.JobTicketNotFound,
          i18n("error.jobTicketNotFound").format(id))
      }
      case size => {
        result.foreach(em.detach(_))
        result(0)
      }
    }
  }

  def insertOrUpdateJobTicket(id: Int, action: Action, job: Job): JobTicket = {
    //4) при создании нового Action надо проверять на возможное существование записи в Job и Job_Ticket для данного пациента,
    // event, биоматериала, планируемой даты выполнения
    //Получение нового JobTicket для действия с забором БМ
    //Job_Ticket.master_id = Job.id
    //Job_Ticket.datetime = action.plannedEndDate
    //JT.status = 0

    var jt: JobTicket = null
    if (id > 0) {
      jt = getJobTicketById(id)
    }
    else {
      jt = new JobTicket
      jt.setStatus(0)
      jt.setJob(job)
    }
    jt.setDatetime(action.getPlannedEndDate)
    jt.setLabel("")
    jt.setNote("")
    jt.setIdx(0)
    jt
  }

  def getDirectionsWithJobTicketsBetweenDate (sortQuery: String,
                                              filter: TakingOfBiomaterialRequesDataFilter) = {

    val queryStr  = filter.toQueryStructure()
    val typed = em.createQuery(DirectionsWithJobTicketsBetweenDateQuery.format(i18n("db.action.movingFlatCode"),
                                                                               iCapIds("db.rbCAP.moving.id.bed"),
                                                                               queryStr.query,
                                                                               sortQuery),
                                classOf[Array[AnyRef]])

    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }
    val result = typed.setParameter("beginDate", filter.beginDate)
                      .setParameter("endDate", filter.endDate)
                      .setParameter("departmentId", filter.departmentId)
                      .getResultList

    result.size() match {
      case 0 => null
      case size => {
        val directions = result.foldLeft(new java.util.LinkedList[(Action, ActionTypeTissueType, JobTicket)])(
          (list, aj) => {
            em.detach(aj(0))
            em.detach(aj(1))
            em.detach(aj(2))
            list.add((aj(0).asInstanceOf[Action], aj(2).asInstanceOf[ActionTypeTissueType], aj(1).asInstanceOf[JobTicket]))
            list
          }
        )
        directions
      }
    }
  }

  def modifyJobTicketStatus(id: Int, status: Int, auth: AuthData) = {

    var lockId: Int = -1
    var oldJobTicket : JobTicket = null
    var isComplete: Boolean = false

    if (id > 0) {
      try {
        val jobTicket = this.getJobTicketById(id)
        oldJobTicket = JobTicket.clone(jobTicket)
        lockId = appLock.acquireLock("Job_Ticket", oldJobTicket.getId.intValue(), oldJobTicket.getId.intValue(), auth)

        jobTicket.setStatus(status)
        dbManager.merge(jobTicket)
        isComplete = true
      } finally {
        if (lockId > 0) appLock.releaseLock(lockId)
      }
    } else {
      LoggingManager.setLoggerType(LoggingManager.LoggingTypes.Debug)
      LoggingManager.warning("code " + ConfigManager.ErrorCodes.JobTicketNotFound +
                             "Невозможно отредактировать: " + ConfigManager.Messages("error.jobTicketNotFound".format(id)))
    }
    isComplete
  }
  val JobTicketByIdQuery =
    """
      SELECT jt
      FROM
        JobTicket jt
      WHERE
        jt.id = :id
    """

  val DirectionsWithJobTicketsBetweenDateQuery =
    """
    SELECT a, jt, attp
    FROM
      JobTicket jt,
      APValueJobTicket apval,
      ActionProperty ap
        JOIN ap.action a
        JOIN a.event e
        JOIN e.patient pat,
      ActionTypeTissueType attp
    WHERE
      jt.datetime BETWEEN :beginDate AND :endDate
    AND
      apval.value = jt.id
    AND
      apval.id.id = ap.id
    AND
      pat.id IN (
          SELECT pat2.id
          FROM
            APValueHospitalBed apbed
              JOIN apbed.value bed,
            ActionProperty ap2
              JOIN ap2.actionPropertyType apt2
              JOIN ap2.action a2
              JOIN a2.actionType at2
              JOIN a2.event e2
              JOIN e2.patient pat2
          WHERE
            apbed.id.id = ap2.id
          AND
            bed.masterDepartment.id = :departmentId
          AND
            at2.flatCode = '%s'
          AND
            apt2.id IN (
              SELECT cap.actionPropertyType.id
              FROM
                RbCoreActionProperty cap
              WHERE
                cap.id = '%s'
            )
          AND (
            a2.endDate IS NULL
            OR
            jt.datetime BETWEEN a2.begDate AND a2.endDate
          )
          AND
            a2.deleted = 0
          AND
            ap2.deleted = 0
          AND
            at2.deleted = 0
          AND
            apt2.deleted = 0
          AND
            e2.deleted = 0
      )
    AND
      ap.deleted = 0
    AND
      a.deleted = 0
    AND
      a.actionType.mnemonic = 'LAB'
    AND
      e.deleted = 0
    AND
      attp.actionType.id = a.actionType.id
    %s
    %s
    """
}