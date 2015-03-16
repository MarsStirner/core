package ru.korus.tmis.core.database

import common.DbManagerBeanLocal
import javax.interceptor.Interceptors
import org.joda.time._

import javax.ejb.{EJB, Stateless}
import grizzled.slf4j.Logging
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.entity.model._
import scala.collection.JavaConversions._
import ru.korus.tmis.core.data.{TakingOfBiomaterialRequesData, TakingOfBiomaterialRequesDataFilter}
import ru.korus.tmis.core.auth.{AuthStorageBeanLocal, AuthData}
import ru.korus.tmis.core.exception.CoreException
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

    val dateMoreThan = filter.getBeginDate
    val dateLessThan = filter.getEndDate
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
      .setParameter("day_more_than", new LocalDate(dateMoreThan).minusDays(1).toDate) // Расширяем границы на один день
      .setParameter("day_less_than", new LocalDate(dateLessThan).plusDays(1).toDate)  // вперед и назад чтобы точно попасть
      .setParameter("labs", labs)                                                     // во временные промежутки, т.к. в БД
      .getResultList                                                                  // будет дата со временем 00:00

    val outList = new util.ArrayList[(Action, ActionTypeTissueType, JobTicket)]

    // Проводим уточняющую фильтрацию по времени
    val result = queryResult.map(e => (e.apply(0).asInstanceOf[Action], e.apply(1).asInstanceOf[JobTicket], e.apply(2).asInstanceOf[ActionTypeTissueType]))
    .filter(e => {
      if(e._2.getDatetime == null) { // Такое маловероятно, но в БД встречается, поэтому фильтруем по временному промежутку на сущности Job
        val day = new LocalDate(e._2.getJob.getDate)
        val begTime = new LocalTime(e._2.getJob.getBegTime)
        val endTime = new LocalTime(e._2.getJob.getEndTime)

        val startDateTime = day.toDateTime(begTime)
        val endDateTime = day.toDateTime(endTime)

        (dateMoreThan.before(endDateTime.toDate) || dateMoreThan.equals(endDateTime.toDate)) &&
          (dateLessThan.after(startDateTime.toDate) || dateLessThan.equals(startDateTime.toDate))
      } else {
        (e._2.getDatetime.after(dateMoreThan) || e._2.getDatetime.equals(dateMoreThan)) &&
          (e._2.getDatetime.before(dateLessThan) || e._2.getDatetime.equals(dateLessThan))
      }
    })

    result.size match {
      case 0 => outList
      case size => result.foldLeft(outList)(
          (list, aj) => {
            if(
            (filter.getStatus < 0 || aj._2.getStatus == filter.getStatus)
              &&
            (filter.getBiomaterial < 1 || aj._3.getTissueType.getId == filter.getBiomaterial)
              &&
            (filter.getJobTicketId < 1 || aj._2.getId == filter.getJobTicketId)
            ) {

              list.add((aj._1, aj._3, aj._2))
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

            list.add((aj(0).asInstanceOf[JobTicket], aj(1).asInstanceOf[TakenTissue]))
            list
          }
        )
        jobAndJobTicket.get(0)*/
        val jt = result.get(0)(0).asInstanceOf[JobTicket]
        val tt = result.get(0)(1).asInstanceOf[TakenTissue]

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
      SELECT DISTINCT research, jt, attt FROM JobTicket jt
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