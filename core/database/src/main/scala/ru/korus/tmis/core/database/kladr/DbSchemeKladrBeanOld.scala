package ru.korus.tmis.core.database.kladr


import javax.ejb.Stateless

import javax.interceptor.Interceptors
import ru.korus.tmis.core.data.{QueryDataStructure, DictionaryListRequestDataFilter}
import javax.persistence.{TypedQuery, PersistenceContext, EntityManager}
import scala.collection.JavaConversions._
import ru.korus.tmis.core.entity.model.kladr.{Street, Kladr}
import ru.korus.tmis.core.filter.ListDataFilter
import ru.korus.tmis.scala.util.I18nable
import ru.korus.tmis.core.database.DbSchemeKladrBeanLocal


class DbSchemeKladrBeanOld
  extends DbSchemeKladrBeanLocal

  with I18nable {

 // @PersistenceContext(unitName = "kladr")
  //var em: EntityManager = _

  val KladrByFilterQuery = """
     SELECT %s
     FROM
      Kladr kl,
      SocrBase socr
     WHERE
      kl.socr = socr.scName
     AND
      kl.code LIKE '%%00'
     %s
     %s
                           """
  val StreetByFilterQuery = """
     SELECT %s
     FROM
      Street str
     %s
     %s
                            """
  val kladrByCodeQuery = """
     SELECT kl
     FROM
      Kladr kl
     WHERE kl.code = :code
                         """

  def getKladrByCode(code: String) = {
//    val result = em.createQuery(kladrByCodeQuery, classOf[Kladr])
//      .setParameter("code", code)
//      .getResultList
//
//
//    result.size() match {
//      case 0 => {
//        null
//      }
//      case size => {
//        result.get(0)
//      }
//    }
    throw new UnsupportedOperationException()
  }

  def getStreetByCode(code: String) = {
    throw new UnsupportedOperationException()
//    val result = em.createQuery(StreetByFilterQuery.format("str", "WHERE str.code = :code", ""), classOf[Street])
//      .setParameter("code", code)
//      .getResultList
//
//
//    result.size() match {
//      case 0 => {
//        null
//      }
//      case size => {
//        result.get(0)
//      }
//    }
  }

  def getCountOfKladrRecordsWithFilter(filter: Object) = {
    throw new UnsupportedOperationException()
//    var optional = false
//    var queryStr: QueryDataStructure = if (filter.isInstanceOf[DictionaryListRequestDataFilter]) {
//      if (filter.asInstanceOf[DictionaryListRequestDataFilter].level.compare("street") == 0) {
//        optional = true
//      }
//      filter.asInstanceOf[DictionaryListRequestDataFilter].toQueryStructure()
//    }
//    else {
//      new QueryDataStructure()
//    }
//
//    var query = ""
//    if (optional) {
//      query = StreetByFilterQuery.format("count(str.code)", "WHERE str.code LIKE '%%00'\n" + queryStr.query, "")
//    } else {
//      query = KladrByFilterQuery.format("count(kl.code)", queryStr.query, "")
//    }
//    var typed = em.createQuery(query, classOf[Long])
//    if (queryStr.data.size() > 0) {
//      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
//    }
//    typed.getSingleResult
  }

  def getAllKladrRecordsWithFilter(page: Int, limit: Int, sorting: String, filter: ListDataFilter): java.util.LinkedList[Object] = {
    throw new UnsupportedOperationException()
//    val optional = if (filter.asInstanceOf[DictionaryListRequestDataFilter].level.compare("street") == 0) true else false
//    val queryStr = filter.toQueryStructure()
//
//    val query =
//      if (optional) {
//        StreetByFilterQuery.format("str.code, str.name, str.socr, str.index", "WHERE str.code LIKE '%%00'\n"+ queryStr.query, "ORDER BY str.name asc, str.socr asc, str.code asc")
//      } else {
//        KladrByFilterQuery.format("kl.code, kl.name, kl.socr, kl.index", queryStr.query, "ORDER BY kl.name asc, kl.socr asc, kl.code asc")
//      }
//
//    val typed = em.createQuery(query, classOf[Array[AnyRef]])
//      .setMaxResults(limit)
//      .setFirstResult(limit * page)
//
//    if (queryStr.data.size() > 0) {
//      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
//    }
//
//    val result = typed.getResultList
//    val list = new java.util.LinkedList[Object]
//    result.foreach(f => {
//      list.add((f(0).asInstanceOf[java.lang.String], f(1).asInstanceOf[java.lang.String], f(2).asInstanceOf[java.lang.String], f(3).asInstanceOf[java.lang.String]))
//    })
//    list
  }
}
