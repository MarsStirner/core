package ru.korus.tmis.core.database

import javax.interceptor.Interceptors

import javax.ejb.Stateless
import grizzled.slf4j.Logging
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.entity.model.Speciality
import scala.collection.JavaConversions._
import ru.korus.tmis.core.data.{DictionaryListRequestDataFilter, QueryDataStructure}
import ru.korus.tmis.core.filter.ListDataFilter
import ru.korus.tmis.scala.util.I18nable

/**
 * Created with IntelliJ IDEA.
 * User: mmakankov
 * Date: 28.09.12
 * Time: 11:35
 * To change this template use File | Settings | File Templates.
 */

@Stateless
class DbRbSpecialityBean extends DbRbSpecialityBeanLocal
with Logging
with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  val RbSpecialityFindQuery = """
    SELECT r
    FROM
      Speciality r
    WHERE
      r.id = :id
                              """

  val AllSpecialitiesWithFilterQuery = """
  SELECT DISTINCT %s
  FROM
    Speciality r
  %s
  %s
                                       """

  def getCountOfBloodTypesWithFilter(filter: Object) = {
    var queryStr: QueryDataStructure = if (filter.isInstanceOf[DictionaryListRequestDataFilter]) {
      filter.asInstanceOf[DictionaryListRequestDataFilter].toQueryStructure()
    }
    else {
      new QueryDataStructure()
    }
    if (queryStr.data.size() > 0) {
      if (queryStr.query.indexOf("AND ") == 0) {
        queryStr.query = "WHERE " + queryStr.query.substring("AND ".length())
      }
    }
    var typed = em.createQuery(AllSpecialitiesWithFilterQuery.format(
      "count(r)",
      queryStr.query,
      ""),
      classOf[Long])
    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }
    typed.getSingleResult
  }

  def getAllSpecialitiesWithFilter(page: Int, limit: Int, sorting: String, filter: ListDataFilter): java.util.LinkedList[Object] = {

    val queryStr = filter.toQueryStructure()
    if (queryStr.data.size() > 0) {
      if (queryStr.query.indexOf("AND ") == 0) {
        queryStr.query = "WHERE " + queryStr.query.substring("AND ".length())
      }
    }
    val typed = em.createQuery(AllSpecialitiesWithFilterQuery.format("r.id, r.name", queryStr.query, sorting), classOf[Array[AnyRef]])
                  .setMaxResults(limit)
                  .setFirstResult(limit * page)
    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }
    val result = typed.getResultList
    val list = new java.util.LinkedList[Object]
    result.foreach(f => {

      list.add((f(0).asInstanceOf[java.lang.Integer], f(1).asInstanceOf[java.lang.String]))
    })
    list
  }


}
