package ru.korus.tmis.core.database

import java.util.Date
import javax.ejb.{EJB, Stateless}
import javax.persistence.{EntityManager, PersistenceContext}

import ru.korus.tmis.core.auth.AuthStorageBeanLocal
import ru.korus.tmis.core.entity.model.{Action, Job, OrgStructure}
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.scala.util.{CAPids, ConfigManager, I18nable}

import scala.collection.JavaConversions._
import scala.language.reflectiveCalls

/**
 * Методы для работы с Job
 * @author mmakankov Systema-Soft
 * @since 1.0.0.70
 */
@Stateless
class DbJobBean extends DbJobBeanLocal
                with I18nable
                with CAPids {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  @EJB
  var appLock: AuthStorageBeanLocal = _

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

  def getJobById(id: Int): Job = {
    val result = em.createQuery(JobByIdQuery, classOf[Job])
      .setParameter("id", id)
      .getResultList
    result.size match {
      case 0 => throw new CoreException(
        ConfigManager.ErrorCodes.JobNotFound,
        i18n("error.jobNotFound").format(id))
      case size => result.iterator.next
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
