package ru.korus.tmis.core.database

import scala.collection.JavaConversions._
import javax.interceptor.Interceptors
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.ejb.{EJB, Stateless}
import grizzled.slf4j.Logging
import ru.korus.tmis.util.{ConfigManager, CAPids, I18nable}
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.core.entity.model.{OrgStructure, Action, Job}
import java.util.Date

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
    job.setEndTime(action.getPlannedEndDate) ///неправильно! нотнулл нужно убрать!
    job.setJobType(action.getActionType.getJobType)
    job.setOrgStructure(department)

    job.setDeleted(false)
    job.setModifyPerson(action.getAssigner)
    job.setModifyDatetime(now)
    job
  }

  def getJobForAction(action: Action): Job = {
    val result = em.createQuery(JobForActionQuery, classOf[Job])
      .setParameter("plannedEndDate", action.getPlannedEndDate)
      .setParameter("eventId", action.getEvent.getId.intValue())
      .setParameter("actionTypeId", action.getActionType.getId.intValue())
      .getResultList

    result.size match {
      case 0 => {
        null /*
        throw new CoreException(
          ConfigManager.ErrorCodes.JobNotFound,
          i18n("error.jobNotFound").format(id))          */
      }
      case size => {
        result.foreach(em.detach(_))
        result(0)
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
      SELECT j
      FROM
        Job j,
        JobTicket jt,
        APValueJobTicket apval,
        ActionProperty ap,
        ActionTypeTissueType attt
        JOIN ap.action a
      WHERE
        j.begTime = :plannedEndDate
      AND
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
        a.actionType.mnemonic = 'LAB'
      AND
        ap.deleted = 0
      AND
        a.deleted = 0
      AND
        j.deleted = 0
    """
}
