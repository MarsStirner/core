package ru.korus.tmis.core.database.common


import javax.interceptor.Interceptors
import grizzled.slf4j.Logging
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.entity.model.RbBloodType
import ru.korus.tmis.core.exception.NoSuchRbBloodTypeException
import java.lang.Iterable
import javax.ejb.{Stateless}
import scala.collection.JavaConversions._
import ru.korus.tmis.core.data.{QueryDataStructure, DictionaryListRequestDataFilter, ListDataRequest, DictionaryListData}
import ru.korus.tmis.core.filter.ListDataFilter
import ru.korus.tmis.scala.util.{I18nable, ConfigManager}
import scala.language.reflectiveCalls


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
    %s r
  %s
  %s
                                     """


  def getCountOfBloodTypesWithFilter(filter: Object,
                                     tableName: String) = {
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
      tableName,
      queryStr.query,
      ""),
      classOf[Long])
    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }
    typed.getSingleResult
  }

  def getAllBloodTypesWithFilter(page: Int, limit: Int, sorting: String, filter: ListDataFilter,
                                 tableName: String): java.util.LinkedList[Object] = {

    val queryStr = filter.toQueryStructure()
    if (queryStr.data.size() > 0) {
      if (queryStr.query.indexOf("AND ") == 0) {
        queryStr.query = "WHERE " + queryStr.query.substring("AND ".length())
      }
    }
    val typed = em.createQuery(AllBloodTypesWithFilterQuery.format("r.id, r.name", tableName, queryStr.query, sorting), classOf[Array[AnyRef]])
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

        result(0)
      }
    }
  }
}