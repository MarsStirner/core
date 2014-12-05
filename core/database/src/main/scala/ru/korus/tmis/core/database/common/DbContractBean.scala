package ru.korus.tmis.core.database.common

import javax.ejb.Stateless
import javax.persistence.{EntityManager, PersistenceContext}
import grizzled.slf4j.Logging
import ru.korus.tmis.core.entity.model.{ContractSpecification, EventType, Contract}
import ru.korus.tmis.core.exception.NoSuchEntityException
import scala.collection.JavaConversions._
import java.util.Date
import ru.korus.tmis.util.reflect.TmisLogging
import ru.korus.tmis.scala.util.{I18nable, ConfigManager}
import java.util


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

  val FindByNumberQuery = """
    SELECT c
    FROM
      Contract c
    WHERE
      c.number = :number
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

  def getContractByNumber(number: String) = {
    val result = em.createQuery(FindByNumberQuery,
      classOf[Contract])
      .setParameter("number", number)
      .getResultList

    result.size match {
      case 0 => {
        throw new NoSuchEntityException(
          ConfigManager.ErrorCodes.ContractNotFound,
          0,
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



  /**
   * Поведение данного метода неверно - контрактов для одного типа событий
   * может быть несколько. Используйте {@link #getContractsByEventTypeId(int, int, boolean, boolean) getContractsByEventTypeId}
   * @param eventType Тип события
   * @return Первый контракт из списка или null, если таковых не нашлось
   */
  @Deprecated
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
      }
      case size => {
        result.foreach((r) => {
          em.detach(r)
        })
        result(0)
      }
    }
  }

  /**
   * Реализация метода получения доступных контрактов
   * @param eventTypeId Идентификатор типа события, для которого ищются договора
   * @param financeId Идентификатор типа финансирования (в 99.999% соответствует EventType-у)
   * @param showDeleted Если задан как true - запрос отрабатывает не обращая внимание на поле deleted
   * @param showExpired Если задан как true - то не проверяется дата окончания договора относительно текущего момента
   * @return Список доступных договоров или null, если таких не имеется
   */
  def getContractsByEventTypeId(eventTypeId: Int, financeId: Int, showDeleted: Boolean, showExpired: Boolean) = {
    val cb = em.getCriteriaBuilder

    val q = cb.createQuery(classOf[Contract])
    val root = q.from(classOf[Contract])

    val sq = q.subquery(classOf[ContractSpecification])
    val subRoot = q.from(classOf[ContractSpecification])

    var subQueryConditions = List(
      cb.equal(subRoot.get("master"), root),
      cb.equal(subRoot.get("eventType").get("id"), eventTypeId)
    )

    if(!showDeleted) subQueryConditions :+= cb.equal(subRoot.get("deleted"), false)

    sq.where(List(cb.and(subQueryConditions:_*)):_*)

    var queryConditions = List(
      cb.equal(root.get("finance").get("id"), financeId),
      cb.exists(sq)
    )

    if(!showDeleted) queryConditions :+= cb.equal(root.get("deleted"), false)
    if(!showExpired) queryConditions :+= cb.greaterThan(root.get("endDate"), new Date())

    q.
      where(List(cb.and(queryConditions:_*)):_*)

    val query = em.createQuery(q)
    val result = query.getResultList()

    result.size match {
      case 0 => {
        logTmis.warning(i18n("error.ContractNotFound").format(eventTypeId.intValue()))
        null
      }
      case size => {
        result.foreach((r) => {
          em.detach(r)
        })
        result
      }
    }
  }


  def getContractByOrganisationId(organisationId: Int): util.List[Contract] = {
    em.createNamedQuery("Contract.findByOrganisationId", classOf[Contract])
      .setParameter("ORGANISATIONID", organisationId)
      .getResultList
  }

}
