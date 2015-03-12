package ru.korus.tmis.core.database

import grizzled.slf4j.Logging
import javax.ejb.Stateless

import javax.interceptor.Interceptors
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.entity.model.RbRelationType
import ru.korus.tmis.core.exception.NoSuchRbRelationTypeException

import java.lang.Iterable
import scala.collection.JavaConversions._
import ru.korus.tmis.core.data.{DictionaryListRequestDataFilter, QueryDataStructure, DictionaryListData, ListDataRequest}
import ru.korus.tmis.core.filter.ListDataFilter
import ru.korus.tmis.scala.util.{I18nable, ConfigManager}
import scala.language.reflectiveCalls


@Stateless
class DbRbRelationTypeBean
  extends DbRbRelationTypeBeanLocal
  with Logging
  with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  val RbRelationTypeFindQuery = """
    SELECT t
    FROM
      RbRelationType t
    WHERE
      t.id = :id
                                """

  val RbRelationTypeDictionaryFindQuery = """
    SELECT t.id, t.leftName, t.rightName
    FROM
      RbRelationType t
                                          """

  val AllRelationsWithFilterQuery = """
  SELECT DISTINCT %s
  FROM
    RbRelationType r
  %s
  %s
                                    """


  def getCountOfRelationsWithFilter(filter: Object) = {
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
    var typed = em.createQuery(AllRelationsWithFilterQuery.format(
      "count(r)",
      queryStr.query,
      ""),
      classOf[Long])
    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }
    typed.getSingleResult
  }

  def getAllRelationsWithFilter(page: Int, limit: Int, sorting: String, filter: ListDataFilter): java.util.LinkedList[Object] = {
    val queryStr = filter.toQueryStructure()
    if (queryStr.data.size() > 0 || queryStr.query.size > 0) {
      if (queryStr.query.indexOf("AND ") == 0) {
        queryStr.query = "WHERE " + queryStr.query.substring("AND ".length())
      }
    }
    val typed = em.createQuery(AllRelationsWithFilterQuery.format("r.id, r.leftName, r.rightName", queryStr.query, sorting), classOf[Array[AnyRef]])
                  .setMaxResults(limit)
                  .setFirstResult(limit * page)
    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }
    val result = typed.getResultList
    val list = new java.util.LinkedList[Object]
    result.foreach(f => {
      //em.detach(f)
      list.add((f(0).asInstanceOf[java.lang.Integer], f(1).asInstanceOf[java.lang.String] + " - " + f(2).asInstanceOf[java.lang.String]))
    })
    list //result
  }

  def getAllRbRelationTypesData(): java.util.LinkedList[Object] = {
    val types = em.createQuery(RbRelationTypeDictionaryFindQuery, classOf[Array[AnyRef]]).getResultList
    val list = new java.util.LinkedList[Object]
    types.foreach(t => {
      list.add((t(0).asInstanceOf[java.lang.Integer], t(1).asInstanceOf[java.lang.String] + " - " + t(2).asInstanceOf[java.lang.String]))
    })
    list
  }

  def getAllRbRelationTypes(): Iterable[RbRelationType] = {
    em.createNamedQuery("RbRelationType.findAll", classOf[RbRelationType]).getResultList
  }

  def getRbRelationTypeById(id: Int): RbRelationType = {
    val result = em.createQuery(RbRelationTypeFindQuery,
      classOf[RbRelationType])
      .setParameter("id", id)
      .getResultList

    result.size match {
      case 0 => {
        throw new NoSuchRbRelationTypeException(
          ConfigManager.ErrorCodes.RbRelationTypeNotFound,
          id,
          i18n("error.rbRelationTypeNotFound").format(id))
      }
      case size => {
        result.foreach(rbType => {
          em.detach(rbType)
        })
        result(0)
      }
    }
  }
}
