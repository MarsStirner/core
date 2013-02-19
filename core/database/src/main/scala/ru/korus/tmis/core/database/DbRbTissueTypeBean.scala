package ru.korus.tmis.core.database

import grizzled.slf4j.Logging
import ru.korus.tmis.util.I18nable
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.data.{DictionaryListRequestDataFilter, QueryDataStructure}
import javax.interceptor.Interceptors
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.ejb.Stateless
import scala.collection.JavaConversions._

/**
 * Методы для работы с таблицей s11r64.rbTissueType.
 * @author Ivan Dmitriev
 * @since 1.0.0.65
 */
@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbRbTissueTypeBean extends DbRbTissueTypeBeanLocal
with Logging
with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def getAllRbTissueTypeWithFilter(page: Int, limit: Int, sortingField: String, sortingMethod: String, filter: Object, records: (java.lang.Long) => java.lang.Boolean) = {
    val queryStr: QueryDataStructure = if (filter.isInstanceOf[DictionaryListRequestDataFilter])
      filter.asInstanceOf[DictionaryListRequestDataFilter].toQueryStructure()
    else new QueryDataStructure()

    val sorting = "ORDER BY %s %s".format(sortingField, sortingMethod)
    if (queryStr.data.size() > 0) {
      if (queryStr.query.indexOf("AND ") == 0) {
        queryStr.query = "WHERE " + queryStr.query.substring("AND ".length())
      }
    }

    if (records!=null) records(em.createQuery(AllRbTissueTypeWithFilterQuery.format("count(r)", queryStr.query, ""), classOf[Long]).getSingleResult)//Перепишем количество записей для структуры

    val typed = em.createQuery(AllRbTissueTypeWithFilterQuery.format("r.id, r.name", queryStr.query, sorting), classOf[Array[AnyRef]])
                  .setMaxResults(limit)
                  .setFirstResult(limit * page)
    if (queryStr.data.size() > 0) queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))

    val result = typed.getResultList
    val list = new java.util.LinkedList[Object]
    result.foreach(f => list.add((f(0).asInstanceOf[java.lang.Integer], f(1).asInstanceOf[java.lang.String])))
    list
  }

  val AllRbTissueTypeWithFilterQuery = """
  SELECT DISTINCT %s
  FROM
    RbTissueType r
  %s
  %s
                                    """
}
