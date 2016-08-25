package ru.korus.tmis.core.database


import javax.interceptor.Interceptors
import javax.ejb.Stateless
import java.util

import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.entity.model.RbPolicyType
import ru.korus.tmis.core.exception.NoSuchRbPolicyTypeException
import ru.korus.tmis.core.data.{QueryDataStructure, DictionaryListRequestDataFilter}
import scala.collection.JavaConversions._
import ru.korus.tmis.core.filter.ListDataFilter
import ru.korus.tmis.scala.util.{I18nable, ConfigManager}
import scala.language.reflectiveCalls


@Stateless
class DbRbPolicyTypeBean
  extends DbRbPolicyTypeBeanLocal

  with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  val RbPolicyTypeFindQuery = """
    SELECT t
    FROM
      RbPolicyType t
    WHERE
      t.id = :id
                              """


  def getAllRbPolicyTypes(): util.List[RbPolicyType] = {
    em.createNamedQuery("RbPolicyType.findAll", classOf[RbPolicyType]).getResultList
  }

  def getRbPolicyTypeById(id: Int): RbPolicyType = {
    val result = em.createQuery(RbPolicyTypeFindQuery,
      classOf[RbPolicyType])
      .setParameter("id", id)
      .getResultList

    result.size match {
      case 0 => {
        throw new NoSuchRbPolicyTypeException(
          ConfigManager.ErrorCodes.RbPolicyTypeNotFound,
          id,
          i18n("error.rbDocumentTypeNotFound"))
      }
      case size => {

        result(0)
      }
    }
  }

  def getCountOfRbPolicyTypeWithFilter(filter: Object) = {
    var queryStr: QueryDataStructure = if (filter.isInstanceOf[DictionaryListRequestDataFilter]) {
      filter.asInstanceOf[DictionaryListRequestDataFilter].toQueryStructure()
    }
    else {
      new QueryDataStructure()
    }

    if (queryStr.data.size() > 0 || queryStr.query.size > 0) {
      if (queryStr.query.indexOf("AND ") == 0) {
        queryStr.query = "WHERE " + queryStr.query.substring("AND ".length())
      }
    }
    var typed = em.createQuery(AllRbPolicyTypeWithFilterQuery.format(
      "count(r)",
      queryStr.query,
      ""),
      classOf[Long])
    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }
    typed.getSingleResult
  }

  def getAllRbPolicyTypeWithFilter(page: Int, limit: Int, sorting: String, filter: ListDataFilter): java.util.LinkedList[Object] = {

    val queryStr = filter.toQueryStructure()
    if (queryStr.data.size() > 0 || queryStr.query.size > 0) {
      if (queryStr.query.indexOf("AND ") == 0) {
        queryStr.query = "WHERE " + queryStr.query.substring("AND ".length())
      }
    }

    val typed = em.createQuery(AllRbPolicyTypeWithFilterQuery.format("r.id, r.name", queryStr.query, sorting), classOf[Array[AnyRef]])
      .setMaxResults(limit)
      .setFirstResult(limit * page)
    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }
    val result = typed.getResultList

    val list = new java.util.LinkedList[Object]
    result.foreach(f => {
      list.add((f(0).asInstanceOf[java.lang.Integer],
        f(1).asInstanceOf[java.lang.String]))
    })
    list
  }

  val AllRbPolicyTypeWithFilterQuery = """
  SELECT %s
  FROM RbPolicyType r
  %s
  %s
                                       """

  def findByCode(policyTypeCode: String): RbPolicyType = {
    val result = em.createNamedQuery("RbPolicyType.findByCode", classOf[RbPolicyType])
      .setParameter("code", policyTypeCode).setMaxResults(1).getResultList
    result.size match {
      case 0 => {
        null
      }
      case size => {
        result(0)
      }
    }
  }
}