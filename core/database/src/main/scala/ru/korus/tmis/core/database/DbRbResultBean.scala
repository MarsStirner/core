package ru.korus.tmis.core.database

import javax.interceptor.Interceptors

import javax.ejb.Stateless
import grizzled.slf4j.Logging
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.entity.model.{RbAcheResult, EventType, RbResult}
import ru.korus.tmis.core.exception.CoreException
import scala.collection.JavaConversions._
import ru.korus.tmis.scala.util.{I18nable, ConfigManager}
import scala.language.reflectiveCalls

/**
 * Created with IntelliJ IDEA.
 * User: mmakankov
 * Date: 30.07.13
 * Time: 16:36
 * To change this template use File | Settings | File Templates.
 */
@Stateless
class DbRbResultBean
  extends DbRbResultBeanLocal
  with I18nable
  with Logging  {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def getRbResultById(id: Int): RbResult = {
    val result = em.createQuery(FindByIdQuery,
      classOf[RbResult])
      .setParameter("id", id)
      .getResultList

    result.size match {
      case 0 => {
        throw new CoreException(
          ConfigManager.ErrorCodes.RbResultNotFound,
          i18n("error.rbResultForEventNotFound").format(id))
      }
      case size => {
        result.foreach(rbStatus => {
          em.detach(rbStatus)
        })
        result(0)
      }
    }
  }

  def getRbResultByCodeAndEventType(et: EventType, code: String): RbResult = {
    val result = em.createQuery(FindByCodeAndEventTypeQuery,
      classOf[RbResult])
      .setParameter("eventPurpose", et.getPurposeId)
      .setParameter("code", code)
      .getResultList

    result.size match {
      case 0 => {
        throw new CoreException(
          ConfigManager.ErrorCodes.RbResultNotFound,
          i18n("error.rbResultForEventNotFound").format(et.getId))
      }
      case size => {
        result.foreach(rbStatus => {
          em.detach(rbStatus)
        })
        result(0)
      }
    }
  }

  def getRbAcheResultByCodeAndEventType(et: EventType, code: String): RbAcheResult = {
    val result = em.createQuery(FindByCodeAndEventTypeInAcheResultQuery,
      classOf[RbAcheResult])
      .setParameter("eventPurpose", et.getPurposeId)
      .setParameter("code", code)
      .getResultList

    result.size match {
      case 0 => {
        throw new CoreException(
          ConfigManager.ErrorCodes.RbResultNotFound,
          i18n("error.rbResultForEventNotFound").format(et.getId))
      }
      case size => {
        result.foreach(rbStatus => {
          em.detach(rbStatus)
        })
        result(0)
      }
    }
  }


  val FindByCodeAndEventTypeQuery = """
  SELECT r
    FROM
  RbResult r
    WHERE
    r.code = :code
    AND
    r.eventPurposeType.id = :eventPurpose
                                    """

  val FindByCodeAndEventTypeInAcheResultQuery = """
  SELECT r
    FROM
  RbAcheResult r
    WHERE
    r.code = :code
    AND
    r.eventPurpose_id = :eventPurpose
                                    """

  val FindByIdQuery = """
  SELECT r
    FROM
  RbResult r
    WHERE
    r.id = :id
                      """
}