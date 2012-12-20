package ru.korus.tmis.core.database

import javax.interceptor.Interceptors
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.ejb.Stateless
import ru.korus.tmis.util.I18nable
import grizzled.slf4j.Logging
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.data.{DictionaryListRequestDataFilter, QueryDataStructure}
import scala.collection.JavaConversions._

/**
 * Методы для работы с таблицей s11r64.rbFinance.
 * @author Ivan Dmitriev
 * @since 1.0.0.45
 */
@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbRbRequestTypeBean extends DbRbRequestTypeBeanLocal
                          with Logging
                          with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def getAllRbRequestTypesWithFilter(page: Int, limit: Int, sortingField: String, sortingMethod: String, filter: Object, records: (java.lang.Long) => java.lang.Boolean) = {
    val queryStr: QueryDataStructure = if (filter.isInstanceOf[DictionaryListRequestDataFilter])
      filter.asInstanceOf[DictionaryListRequestDataFilter].toQueryStructure()
    else new QueryDataStructure()

    val sorting = "ORDER BY %s %s".format(sortingField, sortingMethod)
    if (queryStr.data.size() > 0) {
      if (queryStr.query.indexOf("AND ") == 0) {
        queryStr.query = "WHERE " + queryStr.query.substring("AND ".length())
      }
    }

    if (records!=null) records(em.createQuery(AllRbRequestTypesWithFilterQuery.format("count(r)", queryStr.query, ""), classOf[Long]).getSingleResult)//Перепишем количество записей для структуры

    var typed = em.createQuery(AllRbRequestTypesWithFilterQuery.format("r.id, r.name, r.code", queryStr.query, sorting), classOf[Array[AnyRef]])
      .setMaxResults(limit)
      .setFirstResult(limit * page)
    if (queryStr.data.size() > 0) queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))

    val result = typed.getResultList
    val list = new java.util.LinkedList[Object]
    result.foreach(f => {
      list.add((f(0).asInstanceOf[java.lang.Integer], f(1).asInstanceOf[java.lang.String], f(2).asInstanceOf[java.lang.String]))
    })
    list
  }

  val AllRbRequestTypesWithFilterQuery = """
  SELECT DISTINCT %s
  FROM
    RbRequestType r
  %s
  %s
  """
}
