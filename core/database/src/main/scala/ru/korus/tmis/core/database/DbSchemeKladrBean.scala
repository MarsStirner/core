package ru.korus.tmis.core.database

import ru.korus.tmis.util.I18nable
import grizzled.slf4j.Logging
import javax.ejb.Stateless
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.interceptor.Interceptors
import ru.korus.tmis.core.data.{QueryDataStructure, DictionaryListRequestDataFilter}
import javax.persistence.{TypedQuery, PersistenceContext, EntityManager}
import scala.collection.JavaConversions._
import ru.korus.tmis.core.entity.model.kladr.{Street, Kladr}


@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbSchemeKladrBean
  extends DbSchemeKladrBeanLocal
  with Logging
  with I18nable {

  @PersistenceContext(unitName = "kladr")
  var em: EntityManager = _

  //kl.code, kl.name, kl.sock
  val KladrByFilterQuery = """
     SELECT %s
     FROM
      Kladr kl,
      SocrBase socr
     WHERE
      kl.socr = socr.scName
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
    val result = em.createQuery(kladrByCodeQuery, classOf[Kladr])
      .setParameter("code", code)
      .getResultList

    //result.foreach(fdr=>em.detach(fdr))
    result.size() match {
      case 0 => {
        null
      }
      case size => {
        result.get(0)
      }
    }
  }

  def getStreetByCode(code: String) = {
    val result = em.createQuery(StreetByFilterQuery.format("str", "WHERE str.code = :code", ""), classOf[Street])
      .setParameter("code", code)
      .getResultList

    //result.foreach(fdr=>em.detach(fdr))
    result.size() match {
      case 0 => {
        null
      }
      case size => {
        result.get(0)
      }
    }
  }

  def getCountOfKladrRecordsWithFilter(filter: Object) = {
    var optional = false
    var queryStr: QueryDataStructure = if (filter.isInstanceOf[DictionaryListRequestDataFilter]) {
      if (filter.asInstanceOf[DictionaryListRequestDataFilter].level.compare("street") == 0) {
        optional = true
      }
      filter.asInstanceOf[DictionaryListRequestDataFilter].toQueryStructure()
    }
    else {
      new QueryDataStructure()
    }

    var query = ""
    if (optional) {
      if (queryStr.data.size() > 0 || queryStr.query.size > 0) {
        if (queryStr.query.indexOf("AND ") == 0) {
          queryStr.query = "WHERE " + queryStr.query.substring("AND ".length())
        }
      }
      query = StreetByFilterQuery.format("count(str.code)", queryStr.query, "")
    } else {
      query = KladrByFilterQuery.format("count(kl.code)", queryStr.query, "")
    }
    var typed = em.createQuery(query, classOf[Long])
    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }
    typed.getSingleResult
  }

  def getAllKladrRecordsWithFilter(page: Int, limit: Int, sortingField: String, sortingMethod: String, filter: Object): java.util.LinkedList[Object] = {

    var optional = false
    var queryStr: QueryDataStructure = if (filter.isInstanceOf[DictionaryListRequestDataFilter]) {
      if (filter.asInstanceOf[DictionaryListRequestDataFilter].level.compare("street") == 0) {
        optional = true
      }
      filter.asInstanceOf[DictionaryListRequestDataFilter].toQueryStructure()
    }
    else {
      new QueryDataStructure()
    }

    val sorting = "ORDER BY %s %s".format(sortingField, sortingMethod)

    var query = ""
    if (optional) {
      if (queryStr.data.size() > 0 || queryStr.query.size > 0) {
        if (queryStr.query.indexOf("AND ") == 0) {
          queryStr.query = "WHERE " + queryStr.query.substring("AND ".length())
        }
      }
      query = StreetByFilterQuery.format("str.code, str.name, str.socr, str.index", queryStr.query, "" /*sorting*/)
    } else {
      query = KladrByFilterQuery.format("kl.code, kl.name, kl.socr, kl.index", queryStr.query, "" /*sorting*/)
    }

    var typed = em.createQuery(query, classOf[Array[AnyRef]])
      .setMaxResults(limit)
      .setFirstResult(limit * page)

    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }

    val result = typed.getResultList
    val list = new java.util.LinkedList[Object]
    result.foreach(f => {
      list.add((f(0).asInstanceOf[java.lang.String], f(1).asInstanceOf[java.lang.String], f(2).asInstanceOf[java.lang.String], f(3).asInstanceOf[java.lang.String]))
    })
    list
  }
}
