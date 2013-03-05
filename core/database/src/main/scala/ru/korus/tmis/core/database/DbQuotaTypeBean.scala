package ru.korus.tmis.core.database

import grizzled.slf4j.Logging
import ru.korus.tmis.util.{ConfigManager, I18nable}
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.entity.model.QuotaType
import ru.korus.tmis.core.exception.CoreException
import scala.collection.JavaConversions._
import ru.korus.tmis.core.data.{QuotaTypesListRequestDataFilter, DictionaryListRequestDataFilter, QueryDataStructure}
import javax.interceptor.Interceptors
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.ejb.Stateless
import ru.korus.tmis.core.filter.ListDataFilter

/**
 * Класс с методами для работы с таблицей s11r64.QuotaType
 * @author mmakankov
 * @since 1.0.0.48
 * @see DbQuotaTypeBeanLocal
 */
@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbQuotaTypeBean
  extends DbQuotaTypeBeanLocal
  with Logging
  with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def getQuotaTypeById(id: Int) = {
    val query = em.createQuery(QuotaTypeFindQuery,
      classOf[QuotaType])
      .setParameter("id", id)

    val result = query.getResultList

    result.size match {
      case 0 => {
        throw new CoreException(
          ConfigManager.ErrorCodes.QuotaTypeNotFound,
          i18n("error.quotaTypeNotFound").format(id))
      }
      case size => {
        result.foreach(rbType => {
          em.detach(rbType)
        })
        result(0)
      }
    }
  }

  def getQuotaTypeByCode(code: String) = {
    val result = em.createQuery(QuotaTypeFindByCodeQuery,
      classOf[QuotaType])
      .setParameter("code", code)
      .getResultList

    result.size match {
      case 0 => {
        null
        //throw new CoreException(
        //  ConfigManager.ErrorCodes.QuotaTypeNotFound,
        //  i18n("error.quotaTypeNotFound").format(id))
      }
      case size => {
        result.foreach(rbType => {
          em.detach(rbType)
        })
        result(0)
      }
    }
  }

  def getAllQuotaTypesWithFilter(page: Int, limit: Int, sorting: String, filter: ListDataFilter, records: (java.lang.Long) => java.lang.Boolean) = {

    val queryStr = filter.toQueryStructure()
    if (queryStr.data.size() > 0) {
      if (queryStr.query.indexOf("AND ") == 0) {
        queryStr.query = "WHERE " + queryStr.query.substring("AND ".length())
      }
    }

    //Перепишем количество записей для структуры
    val countTyped = em.createQuery(AllQuotaTypesWithFilterQuery.format("count(r)", queryStr.query, ""), classOf[Long])
    if (queryStr.data.size() > 0) queryStr.data.foreach(qdp => countTyped.setParameter(qdp.name, qdp.value))
    if (records!=null) records(countTyped.getSingleResult)

    val typed = em.createQuery(AllQuotaTypesWithFilterQuery.format("r", queryStr.query, sorting), classOf[QuotaType])
      .setMaxResults(limit)
      .setFirstResult(limit * page)
    if (queryStr.data.size() > 0) queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))

    val result = typed.getResultList
    result.foreach(f => {
      em.detach(f)
    })
    result
  }

  val QuotaTypeFindByCodeQuery = """
  SELECT r
  FROM
    QuotaType r
  WHERE
    r.code = :code
                                 """

  val QuotaTypeFindQuery = """
    SELECT r
    FROM
      QuotaType r
    WHERE
      r.id = :id
                               """

  val AllQuotaTypesWithFilterQuery = """
  SELECT %s
  FROM QuotaType r
  %s
  %s
                                     """
}
