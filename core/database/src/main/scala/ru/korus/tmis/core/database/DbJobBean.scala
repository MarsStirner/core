package ru.korus.tmis.core.database

import scala.collection.JavaConversions._
import javax.interceptor.Interceptors
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.ejb.{EJB, Stateless}
import grizzled.slf4j.Logging
import ru.korus.tmis.util.{ConfigManager, CAPids, I18nable}
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.core.entity.model.{Action, Job}
import java.util.Date

/**
 * Методы для работы с Job
 * Author: mmakankov Systema-Soft
 * Date: 2/13/13 2:30 PM
 * Since: 1.0.0.69
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

  @EJB
  private var dbManager: DbManagerBeanLocal = _

  def insertOrUpdateJob(id: Int,
                        action: Action): Job = {

    var job: Job = null
    val now = new Date
    if (id > 0) {
      job = getJobById(id)
    }
    else {
      job = new Job
      job.setCreatePerson(action.getAssigner)
      job.setCreateDatetime(now)
    }
    job.setDate(action.getPlannedEndDate)
    job.setBegTime(action.getPlannedEndDate)
    job.setJobType(action.getActionType.getJobType)
    job.setQuantity(1) /////
    job.setDeleted(false)
    job.setModifyPerson(action.getAssigner)
    job.setModifyDatetime(now)
    job
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
}
