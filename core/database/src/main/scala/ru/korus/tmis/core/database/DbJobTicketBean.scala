package ru.korus.tmis.core.database

import java.sql.Time

import common.DbManagerBeanLocal
import javax.interceptor.Interceptors
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.ejb.{EJB, Stateless}
import grizzled.slf4j.Logging
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.entity.model._
import scala.collection.JavaConversions._
import ru.korus.tmis.core.data.{TakingOfBiomaterialRequesData, TakingOfBiomaterialRequesDataFilter}
import ru.korus.tmis.core.auth.{AuthStorageBeanLocal, AuthData}
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.util.reflect.LoggingManager
import java.util.Date
import java.text.SimpleDateFormat
import java.util
import ru.korus.tmis.scala.util.{CAPids, I18nable, ConfigManager}
import scala.language.reflectiveCalls

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
  var appLock: AuthStorageBeanLocal = _

  @EJB
  private var dbManager: DbManagerBeanLocal = _

  @EJB
  var dbRbLaboratory: DbRbLaboratory = _

  def getJobTicketById(id: Int): JobTicket = {
    val result = em.createQuery(JobTicketByIdQuery, classOf[JobTicket])
                   .setParameter("id", id)
                   .getResultList

    result.size match {
      case 0 =>
        throw new CoreException( ConfigManager.ErrorCodes.JobTicketNotFound, i18n("error.jobTicketNotFound").format(id))
      case size =>
        result.foreach(em.detach(_))
        result(0)
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
      //jt.setStatus(2)
      jt.setJob(job)
    }
    jt.setDatetime(action.getPlannedEndDate)
    jt.setLabel("")
    jt.setNote("")
    jt.setIdx(0)
    jt
  }

  def getDirectionsWithJobTicketsBetweenDate (request: TakingOfBiomaterialRequesData,
                                              filter: TakingOfBiomaterialRequesDataFilter): util.List[(Action, ActionTypeTissueType, JobTicket)] = {

    val dayMoreThan = filter.getBeginDate
    val dayLessThan = filter.getEndDate
    val timeLessThan = new Time(filter.getEndDate.getTime)
    val timeMoreThan = new Time(filter.getBeginDate.getTime)
    val department = filter.getDepartmentId
    val labs: util.List[String] = filter.getLabs.toList match {
      case Nil | null => dbRbLaboratory.getAllLabs.map(_.getName)
      case _ => filter.getLabs
    }

    val order = request.getSortingMethod.toLowerCase match {
      case "desc" => "desc"
      case _ => "asc"
    }

    val orderField = request.getSortingField.toLowerCase match {
      case "sex"                              => "research.event.patient.sex"
      case "actiontype"                       => "research.actionType.name"
      case "urgent"                           => "research.isUrgent"
      case "tube"                             => "research.actionType.testTubeType.name"
      case "status"                           => "jt.status"
      case "fullname" | "fio" | "patient"     => "research.event.patient.lastName %s, research.event.patient.firstName %s, research.event.patient.patrName".format(order, order)
      case "birthdate"                        => "research.event.patient.birthDate"
      case "tissuetype"                       => "attt.tissueType.name"
      case "date"                             => "jt.datetime"
      case _                                  => "research.event.patient.lastName %s, research.event.patient.firstName %s, research.event.patient.patrName".format(order, order)
    }

    val queryString = DirectionsWithJobTicketsBetweenDateQuery.format(orderField, order)

    val query = em.createQuery(queryString,
      classOf[Array[AnyRef]])
    val queryResult = query
      .setParameter("department", department)
      .setParameter("day_more_than", dayMoreThan)
      .setParameter("day_less_than", dayLessThan)
      .setParameter("begTime_less_than", timeLessThan)
      .setParameter("endTime_more_than", timeMoreThan)
      .setParameter("labs", labs)
      .getResultList

    val outList = new util.ArrayList[(Action, ActionTypeTissueType, JobTicket)]

    queryResult.size() match {
      case 0 => outList
      case size => queryResult.foldLeft(outList)(
          (list, aj) => {
            if(
            (filter.getStatus < 0 || aj(1).asInstanceOf[JobTicket].getStatus == filter.getStatus)
              &&
            (filter.getBiomaterial < 1 || aj(2).asInstanceOf[ActionTypeTissueType].getTissueType.getId == filter.getBiomaterial)
              &&
            (filter.getJobTicketId < 1 || aj(1).asInstanceOf[JobTicket].getId == filter.getJobTicketId)
            ) {
              em.detach(aj(0))
              em.detach(aj(1))
              em.detach(aj(2))
              list.add((aj(0).asInstanceOf[Action], aj(2).asInstanceOf[ActionTypeTissueType], aj(1).asInstanceOf[JobTicket]))
            }
            list
          }
        )
    }
  }

  def modifyJobTicketStatus(id: Int, status: Int, auth: AuthData) = {

    var isComplete: Boolean = false

    if (id > 0) {
      val jobTicket = this.getJobTicketById(id)
      //val oldJobTicket = JobTicket.clone(jobTicket)
      //val lockId = appLock.acquireLock("Job_Ticket", id, oldJobTicket.getId.intValue(), auth)

      try {
        jobTicket.setStatus(status)
        dbManager.merge(jobTicket)
        isComplete = true
      } finally {
        //appLock.releaseLock(lockId)
      }
    } else {
      LoggingManager.setLoggerType(LoggingManager.LoggingTypes.Debug)
      LoggingManager.warning("code " + ConfigManager.ErrorCodes.JobTicketNotFound +
        "Невозможно отредактировать" + ConfigManager.Messages("error.jobTicketNotFound").format(id))
    }
    isComplete
  }

  def getJobTicketAndTakenTissueForAction(eventId: Int, atId: Int, datef: Date, departmentId: Int, urgent: Boolean) = {
    val formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm")
    val strDate = formatter.format(datef)
    val date = formatter.parse(strDate)

    val query = em.createQuery(JobTicketAndTakenTissueForActionQuery, classOf[Array[AnyRef]])
                  .setParameter("plannedEndDate", strDate)
                  .setParameter("eventId", eventId)
                  .setParameter("actionTypeId", atId)
                  .setParameter("departmentId", departmentId)
                  .setParameter("urgent", urgent)

    val result = query.getResultList

    result.size match {
      case 0 =>
        null /*
        throw new CoreException(
          ConfigManager.ErrorCodes.JobNotFound,
          i18n("error.jobNotFound").format(id))          */
      case size =>
        /*val jobAndJobTicket = result.foldLeft(new java.util.LinkedList[(JobTicket, TakenTissue)])(
          (list, aj) => {
            em.detach(aj(0))
            em.detach(aj(1))
            list.add((aj(0).asInstanceOf[JobTicket], aj(1).asInstanceOf[TakenTissue]))
            list
          }
        )
        jobAndJobTicket.get(0)*/
        val jt = result.get(0)(0).asInstanceOf[JobTicket]
        val tt = result.get(0)(1).asInstanceOf[TakenTissue]
        em.detach(jt)
        em.detach(tt)
        (jt, tt)
    }
  }

  def getActionTypeTissueTypeForActionType(actionTypeId: Int) = {
    val query = em.createQuery(ActionTypeTissueTypeByActionTypeIdQuery, classOf[ActionTypeTissueType])
      .setParameter(":actionTypeId", actionTypeId)

    val result = query.getResultList

    result.size match {
      case 0 =>
        null /*
        throw new CoreException(
          ConfigManager.ErrorCodes.JobNotFound,
          i18n("error.jobNotFound").format(id))          */
      case size =>
        result.foreach(em.detach(_))
        result(0)
    }
  }

  val ActionTypeTissueTypeByActionTypeIdQuery =
    """
      SELECT attp
      FROM
        ActionTypeTissueType attp
      WHERE
        attp.actionType.id = :actionTypeId
    """

  def getJobTicketForAction(actionId: Int) = {
    val query = em.createQuery(JobTicketForActionQuery, classOf[JobTicket])
      .setParameter("actionId", actionId)

    val result = query.getResultList

    result.size match {
      case 0 =>
        null /*
        throw new CoreException(
          ConfigManager.ErrorCodes.JobNotFound,
          i18n("error.jobNotFound").format(id))          */
      case size =>
        result.foreach(jt => em.detach(jt))
        result.get(0)
    }
  }

  def getActionsForJobTicket(jobTicketId: Int) = {
    val query = em.createQuery(ActionsForJobTicketQuery, classOf[Action])
      .setParameter("jobTicketId", jobTicketId)

    val result = query.getResultList

    result.size match {
      case 0 =>
        new util.LinkedList[Action]
        /*
        throw new CoreException(
          ConfigManager.ErrorCodes.JobNotFound,
          i18n("error.ActionsForJobTicketNotFound").format(jobTicketId))
          */
      case size => {
        result.foreach(a => {em.detach(a)})
      }
      result
    }
  }

  def getLaboratoryCodeForActionId(actionId: Int) = {
    val query = em.createQuery(LaboratoryCodeByActionIdQuery, classOf[String])
      .setParameter("actionId", actionId)

    val result = query.getResultList
    if (result.size() > 0) {
      result.get(0)
    } else {
      null
    }

  }

  val LaboratoryCodeByActionIdQuery =
    """
    SELECT
      lab.code
    FROM
      Action a,
      ActionProperty ap,
      ActionPropertyType apt,
      RbTest rbTest,
      RbLaboratoryTest labTest,
      RbLaboratory lab,
      ActionType at
    WHERE
      ap.action.id = a.id
    AND
      ap.actionPropertyType.id = apt.id
    AND
      apt.test.id = rbTest.id
    AND
      labTest.testId = rbTest.id
    AND
      labTest.rbLaboratory.id = lab.id
    AND
      at.id = a.actionType.id
    AND
      a.id = :actionId
    AND
      ap.isAssigned = 1
    """

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
      SELECT research, jt, attt FROM JobTicket jt
      LEFT JOIN jt.propertiesValues jtValue
      LEFT JOIN jtValue.actionProperty jtProperty
      LEFT JOIN jtProperty.action research
      LEFT JOIN research.actionType.actionTypeTissueType attt
      LEFT JOIN research.actionType.actionPropertyTypes apt
      LEFT JOIN apt.test.rbLaboratoryTest.rbLaboratory lab

      WHERE
        jt.job.orgStructure.id = :department           AND
        jt.job.date >= :day_more_than                  AND
        jt.job.date <= :day_less_than                  AND
        jt.job.begTime <= :begTime_less_than           AND
        jt.job.endTime >= :endTime_more_than           AND
        lab.name IN :labs                              AND

        research.deleted = false                       AND
        research.event.deleted = false                 AND
        research.event.patient.deleted = false
      ORDER BY %s %s
    """


  val JobTicketAndTakenTissueForActionQuery =
    """
      SELECT DISTINCT jt, tt
      FROM
        JobTicket jt
          JOIN jt.job j,
        APValueJobTicket apval,
        ActionProperty ap
          JOIN ap.action a
          JOIN a.takenTissue tt,
        ActionTypeTissueType attt
      WHERE
        a.event.id = :eventId
      AND
        (a.actionType.mnemonic = 'LAB' OR a.actionType.mnemonic = 'BAK_LAB')
      AND
        a.isUrgent = :urgent
      AND
        apval.id.id = ap.id
      AND
        apval.value = jt.id
      AND
        attt.actionType.id = :actionTypeId
      AND
        tt.type.id = attt.tissueType.id
      AND
        substring(jt.datetime, 1, 16) = :plannedEndDate
      AND
        j.orgStructure.id = :departmentId
      AND
        ap.deleted = 0
      AND
        a.deleted = 0
      AND
        j.deleted = 0
    """

  val JobTicketForActionQuery =
    """
      SELECT DISTINCT jt
      FROM
        JobTicket jt
          JOIN jt.job j,
        APValueJobTicket apval,
        ActionProperty ap
          JOIN ap.action a
      WHERE
        a.id = :actionId
      AND
        apval.id.id = ap.id
      AND
        apval.value = jt.id
      AND
        ap.deleted = 0
      AND
        a.deleted = 0
      AND
        j.deleted = 0
    """

  val ActionsForJobTicketQuery =
    """
      SELECT a
      FROM
        JobTicket jt
          JOIN jt.job j,
        APValueJobTicket apval,
        ActionProperty ap
          JOIN ap.action a
      WHERE
        jt.id = :jobTicketId
      AND
        (a.actionType.mnemonic = 'LAB' OR a.actionType.mnemonic = 'BAK_LAB')
      AND
        apval.id.id = ap.id
      AND
        apval.value = jt.id
      AND
        ap.deleted = 0
      AND
        a.deleted = 0
      AND
        j.deleted = 0
    """/*      AND
        a.isUrgent = 0*/
}