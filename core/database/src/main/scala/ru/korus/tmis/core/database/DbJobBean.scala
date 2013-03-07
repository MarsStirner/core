package ru.korus.tmis.core.database

import scala.collection.JavaConversions._
import javax.interceptor.Interceptors
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.ejb.{EJB, Stateless}
import grizzled.slf4j.Logging
import ru.korus.tmis.util.{ConfigManager, CAPids, I18nable}
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.core.entity.model.{JobTicket, OrgStructure, Action, Job}
import java.util.{Calendar, Date}
import java.text.SimpleDateFormat

/**
 * Методы для работы с Job
 * @author mmakankov Systema-Soft
 * @since 1.0.0.70
 */
@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbJobBean extends DbJobBeanLocal
                with Logging
                with I18nable
                with CAPids {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  @EJB
  var appLock: AppLockBeanLocal = _

  def insertOrUpdateJob(id: Int, action: Action, department: OrgStructure): Job = {

    var job: Job = null
    val now = new Date
    if (id > 0) {
      job = getJobById(id)
      job.setQuantity(job.getQuantity + 1)
    }
    else {
      job = new Job
      job.setCreatePerson(action.getAssigner)
      job.setCreateDatetime(now)
      job.setQuantity(1)
    }
    job.setDate(action.getPlannedEndDate)
    job.setBegTime(action.getPlannedEndDate)
    job.setEndTime(action.getPlannedEndDate)
    job.setJobType(action.getActionType.getJobType)
    job.setOrgStructure(department)

    job.setDeleted(false)
    job.setModifyPerson(action.getAssigner)
    job.setModifyDatetime(now)
    job
  }

  def getJobAndJobTicketForAction(action: Action) = {
    var intDate = action.getPlannedEndDate.getTime/1000/60/60/24
    var date = new Date(intDate*1000*60*60*24)

    val formatter = new SimpleDateFormat("yyyy-MM-dd")

    var strDate = formatter.format(action.getPlannedEndDate)
    date = formatter.parse(strDate)
    /*
      val today = Calendar.getInstance()
      today.setTime(formatter.parse(formatter.format(new Date())))
      today.set(Calendar.HOUR_OF_DAY, 8); //смещение между астрономическими и медицинскими сутками

      today.getTime

    if(fDate != null){
      Calendar c = Calendar.getInstance();
      c.setTime(fDate);
      c.add(Calendar.DAY_OF_YEAR, 1);
      Date fDate2 = c.getTime();
      q.setDate("fdate", fDate2);
    }
    var sqlDate = new java.sql.Date(intDate)
    sqlDate.g
                            */
    val query = em.createQuery(JobForActionQuery, classOf[Array[AnyRef]])
      .setParameter("plannedEndDate", date)
      .setParameter("eventId", action.getEvent.getId.intValue())
      .setParameter("actionTypeId", action.getActionType.getId.intValue())

    val result = query.getResultList

    result.size match {
      case 0 => {
        null /*
        throw new CoreException(
          ConfigManager.ErrorCodes.JobNotFound,
          i18n("error.jobNotFound").format(id))          */
      }
      case size => {
        val jobAndJobTicket = result.foldLeft(new java.util.LinkedList[(Job, JobTicket)])(
          (list, aj) => {
            em.detach(aj(0))
            em.detach(aj(1))
            list.add((aj(0).asInstanceOf[Job], aj(1).asInstanceOf[JobTicket]))
            list
          }
        )
        jobAndJobTicket.get(0).asInstanceOf[(Job, JobTicket)]
      }
    }
  }

  def getJobById(id: Int): Job = {
    val result = em.createQuery(JobByIdQuery, classOf[Job])
      .setParameter("id", id)
      .getResultList

    result.size match {
      case 0 => {
        throw new CoreException(
          ConfigManager.ErrorCodes.JobNotFound,
          i18n("error.jobNotFound").format(id))
      }
      case size => {
        result.foreach(em.detach(_))
        result(0)
      }
    }
  }

  val JobByIdQuery =
    """
      SELECT j
      FROM
        Job j
      WHERE
        j.id = :id
    """

  val JobForActionQuery =
    """
      SELECT DISTINCT j, jt
      FROM
        Job j,
        JobTicket jt,
        APValueJobTicket apval,
        ActionProperty ap,
        ActionTypeTissueType attt
        JOIN ap.action a
      WHERE
        jt.job.id = j.id
      AND
        apval.value = jt.id
      AND
        apval.id.id = ap.id
      AND
        a.event.id = :eventId
      AND
        a.actionType.id = :actionTypeId
      AND
        attt.actionType.id = a.actionType.id
      AND
        j.date = :plannedEndDate
      AND
        a.actionType.mnemonic = 'LAB'
      AND
        ap.deleted = 0
      AND
        a.deleted = 0
      AND
        j.deleted = 0
    """
}
