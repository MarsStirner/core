package ru.korus.tmis.core.database

import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.interceptor.Interceptors
import grizzled.slf4j.Logging
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.util.{ConfigManager, I18nable}
import ru.korus.tmis.core.entity.model.RbBloodType
import ru.korus.tmis.core.exception.NoSuchRbBloodTypeException
import java.lang.Iterable
import javax.ejb.{Stateless}
import scala.collection.JavaConversions._
import ru.korus.tmis.core.data.{QueryDataStructure, DictionaryListRequestDataFilter, ListDataRequest, DictionaryListData}

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbRbBloodTypeBean
  extends DbRbBloodTypeBeanLocal
  with Logging
  with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  val RbBloodTypeFindQuery = """
    SELECT t
    FROM
      RbBloodType t
    WHERE
      t.id = :id
                             """

  val RbBloodTypeDictionaryFindQuery = """
    SELECT t.id, t.name
    FROM
      RbBloodType t
                                       """

  val AllBloodTypesWithFilterQuery = """
  SELECT DISTINCT %s
  FROM
    RbBloodType r
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
    var typed = em.createQuery(AllBloodTypesWithFilterQuery.format(
      "count(r)",
      queryStr.query,
      ""),
      classOf[Long])
    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }
    typed.getSingleResult
  }

  def getAllBloodTypesWithFilter(page: Int, limit: Int, sortingField: String, sortingMethod: String, filter: Object): java.util.LinkedList[Object] = {
    var queryStr: QueryDataStructure = if (filter.isInstanceOf[DictionaryListRequestDataFilter]) {
      filter.asInstanceOf[DictionaryListRequestDataFilter].toQueryStructure()
    }
    else {
      new QueryDataStructure()
    }
    val sorting = "ORDER BY %s %s".format(sortingField, sortingMethod)
    if (queryStr.data.size() > 0) {
      if (queryStr.query.indexOf("AND ") == 0) {
        queryStr.query = "WHERE " + queryStr.query.substring("AND ".length())
      }
    }
    var typed = em.createQuery(AllBloodTypesWithFilterQuery.format("r.id, r.name", queryStr.query, sorting), classOf[Array[AnyRef]])
      .setMaxResults(limit)
      .setFirstResult(limit * page)
    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }
    val result = typed.getResultList
    val list = new java.util.LinkedList[Object]
    result.foreach(f => {
      //em.detach(f)
      list.add((f(0).asInstanceOf[java.lang.Integer], f(1).asInstanceOf[java.lang.String]))
    })
    list
  }

  def getAllBloodTypesData(): java.util.LinkedList[Object] = {
    val types = em.createQuery(RbBloodTypeDictionaryFindQuery, classOf[Array[AnyRef]]).getResultList
    val list = new java.util.LinkedList[Object]
    types.foreach(t => {
      list.add((t(0).asInstanceOf[java.lang.Integer], t(1).asInstanceOf[java.lang.String]))
    })
    list
  }

  def getAllRbBloodTypes(): Iterable[RbBloodType] = {
    em.createNamedQuery("RbBloodType.findAll", classOf[RbBloodType]).getResultList
  }

  def getRbBloodTypeById(id: Int): RbBloodType = {
    val result = em.createQuery(RbBloodTypeFindQuery,
      classOf[RbBloodType])
      .setParameter("id", id)
      .getResultList

    result.size match {
      case 0 => {
        throw new NoSuchRbBloodTypeException(
          ConfigManager.ErrorCodes.RbBloodTypeNotFound,
          id,
          i18n("error.rbBloodTypeNotFound").format(id))
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