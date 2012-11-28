package ru.korus.tmis.core.database

import grizzled.slf4j.Logging
import ru.korus.tmis.util.{ConfigManager, I18nable}
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.entity.model.QuotaType
import ru.korus.tmis.core.exception.CoreException
import scala.collection.JavaConversions._
import ru.korus.tmis.core.data.{DictionaryListRequestDataFilter, QueryDataStructure}
import javax.interceptor.Interceptors
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.ejb.Stateless

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

  def getQuotaTypeById(id: Int): QuotaType = {
    val result = em.createQuery(QuotaTypeFindQuery,
      classOf[QuotaType])
      .setParameter("id", id)
      .getResultList

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

  def getAllQuotaTypesWithFilter(page: Int, limit: Int, sortingField: String, sortingMethod: String, filter: Object, records: (java.lang.Long) => java.lang.Boolean) = {
    val queryStr: QueryDataStructure = if (filter.isInstanceOf[DictionaryListRequestDataFilter])
      filter.asInstanceOf[DictionaryListRequestDataFilter].toQueryStructure()
    else new QueryDataStructure()

    val sorting = "ORDER BY %s %s".format(sortingField, sortingMethod)
    if (queryStr.data.size() > 0) {
      if (queryStr.query.indexOf("AND ") == 0) {
        queryStr.query = "WHERE " + queryStr.query.substring("AND ".length())
      }
    }

    if (records!=null) records(em.createQuery(AllQuotaTypesWithFilterQuery.format("count(r)", queryStr.query, ""), classOf[Long]).getSingleResult)//Перепишем количество записей для структуры

    var typed = em.createQuery(AllQuotaTypesWithFilterQuery.format("r.id, r.name", queryStr.query, sorting), classOf[Array[AnyRef]])
      .setMaxResults(limit)
      .setFirstResult(limit * page)
    if (queryStr.data.size() > 0) queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))

    val result = typed.getResultList
    val list = new java.util.LinkedList[Object]
    result.foreach(f => {
      list.add((f(0).asInstanceOf[java.lang.Integer], f(1).asInstanceOf[java.lang.String]))
    })
    list
  }

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
