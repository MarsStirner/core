package ru.korus.tmis.core.database

import java.util
import javax.interceptor.Interceptors
import javax.ejb.Stateless
import javax.persistence.{EntityManager, PersistenceContext}

import ru.korus.tmis.core.filter.ListDataFilter

import scala.collection.JavaConversions._
import ru.korus.tmis.scala.util.I18nable

/**
 * Методы для работы с таблицей s11r64.rbOperationType.
 * @author Ivan Dmitriev
 * @since 1.0.1.19
 */

@Stateless
class DbRbOperationTypeBean extends DbRbOperationTypeBeanLocal

                            with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def getAllRbOperationTypeWithFilter(page: Int,
                                   limit: Int,
                                   sorting: String,
                                   filter: ListDataFilter,
                                   records: (java.lang.Long) => java.lang.Boolean): util.LinkedList[Object] = {

    val queryStr = filter.toQueryStructure()
    if (queryStr.data.size() > 0) {
      if (queryStr.query.indexOf("AND ") == 0) {
        queryStr.query = "WHERE " + queryStr.query.substring("AND ".length())
      }
    }

    if (records!=null) records(em.createQuery(AllRbOperationTypeWithFilterQuery.format("count(r)", queryStr.query, ""), classOf[Long]).getSingleResult)//Перепишем количество записей для структуры

    val typed = em.createQuery(AllRbOperationTypeWithFilterQuery.format("r.id, r.name, r.code", queryStr.query, sorting), classOf[Array[AnyRef]])
                  .setMaxResults(limit)
                  .setFirstResult(limit * page)
    if (queryStr.data.size() > 0) queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))

    val result = typed.getResultList
    val list = new java.util.LinkedList[Object]
    result.foreach(f => list.add((f(0).asInstanceOf[java.lang.Integer], f(1).asInstanceOf[java.lang.String], f(2).asInstanceOf[java.lang.String])))
    list
  }

  val AllRbOperationTypeWithFilterQuery = """
  SELECT DISTINCT %s
  FROM
    RbOperationType r
  %s
  %s
                                       """

}
