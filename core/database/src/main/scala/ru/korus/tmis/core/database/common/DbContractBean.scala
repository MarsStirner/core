package ru.korus.tmis.core.database.common

import javax.interceptor.Interceptors
import javax.ejb.{TransactionAttributeType, TransactionAttribute, Stateless}
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.persistence.{EntityManager, PersistenceContext}
import grizzled.slf4j.Logging
import ru.korus.tmis.core.entity.model.{EventType, Contract}
import ru.korus.tmis.core.exception.NoSuchEntityException
import scala.collection.JavaConversions._
import java.util.Date
import ru.korus.tmis.util.reflect.TmisLogging
import ru.korus.tmis.scala.util.{I18nable, ConfigManager}


/**
 * Created with IntelliJ IDEA.
 * User: mmakankov
 * Date: 31.01.13
 * Time: 14:35
 * To change this template use File | Settings | File Templates.
 */

//@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbContractBean
  extends DbContractBeanLocal
  with Logging
  with I18nable
  with TmisLogging {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  val FindByIdQuery = """
    SELECT c
    FROM
      Contract c
    WHERE
      c.id = :id
                      """

  val FindContractForEventQuery = """
    SELECT c
    FROM
      Contract c
    WHERE
      c.deleted = 0
    AND
      c.endDate > :date
    AND
      c.finance.id = :financeId
    AND
      exists (
        SELECT cs
        FROM
          ContractSpecification cs
        WHERE
          cs.master.id = c.id
        AND
          cs.eventType.id = :eventTypeId
        AND
          cs.deleted = 0)
                                  """

  def getContractById(id: Int) = {
    val result = em.createQuery(FindByIdQuery,
      classOf[Contract])
      .setParameter("id", id)
      .getResultList

    result.size match {
      case 0 => {
        throw new NoSuchEntityException(
          ConfigManager.ErrorCodes.ContractNotFound,
          id,
          i18n("error.ContractNotFound"))
      }
      case size => {
        result.foreach((r) => {
          em.detach(r)
        })
        result(0)
      }
    }
  }

  //@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  def getContractForEventType(eventType: EventType) = {
    val result = em.createQuery(FindContractForEventQuery,
      classOf[Contract])
      .setParameter("date", new Date)
      .setParameter("financeId", eventType.getFinance.getId.intValue())
      .setParameter("eventTypeId", eventType.getId.intValue())
      .getResultList

    result.size match {
      case 0 => {
        logTmis.warning(i18n("error.ContractNotFound").format(eventType.getId.intValue()))
        null
        /*
        throw new NoSuchEntityException(
          ConfigManager.ErrorCodes.ContractNotFound,
          eventType.getId.intValue(),
          i18n("error.ContractNotFound"))
        */
      }
      case size => {
        result.foreach((r) => {
          em.detach(r)
        })
        result(0)
      }
    }
  }
}
