package ru.korus.tmis.core.database

import javax.ejb.Stateless

import javax.interceptor.Interceptors
import grizzled.slf4j.Logging
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.exception.NoSuchRbContactTypeException
import ru.korus.tmis.core.entity.model.{ClientContact, RbContactType}
import java.lang.Iterable
import ru.korus.tmis.core.data.{DictionaryListRequestDataFilter, QueryDataStructure}
import scala.collection.JavaConversions._
import ru.korus.tmis.core.filter.ListDataFilter
import ru.korus.tmis.scala.util.{I18nable, ConfigManager}
import scala.language.reflectiveCalls


@Stateless
class DbRbContactTypeBean
  extends DbRbContactTypeBeanLocal
  with Logging
  with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  val RbContactTypeFindQuery = """
    SELECT t
    FROM
      RbContactType t
    WHERE
      t.id = :id
                               """

  val FindByNameQuery = """
    SELECT d
    FROM
      RbContactType d
    WHERE
      d.name = :name
                        """


  def findByName(name: String): RbContactType = {
    em.createQuery(FindByNameQuery,
      classOf[RbContactType])
      .setParameter("name", name)
      .getSingleResult
  }

  /*def getAllRbContactTypes(): Iterable[RbContactType] = {
    em.createNamedQuery("RbContactType.findAll", classOf[RbContactType]).getResultList
  }*/


  def getRbContactTypeById(id: Int): RbContactType = {
    val result = em.createQuery(RbContactTypeFindQuery,
      classOf[RbContactType])
      .setParameter("id", id)
      .getResultList

    result.size match {
      case 0 => {
        throw new NoSuchRbContactTypeException(
          ConfigManager.ErrorCodes.RbContactTypeNotFound,
          id,
          i18n("error.rbContactTypeNotFound").format(id))
      }
      case size => {
        val rbContactType = result.iterator.next()
        em.detach(rbContactType)
        rbContactType
      }
    }
  }

  def getCountOfAllRbContactTypesWithFilter(filter: Object) = {
    val queryStr: QueryDataStructure = if (filter.isInstanceOf[DictionaryListRequestDataFilter])
      filter.asInstanceOf[DictionaryListRequestDataFilter].toQueryStructure()
    else new QueryDataStructure()

    if (queryStr.data.size() > 0) {
      if (queryStr.query.indexOf("AND ") == 0) {
        queryStr.query = "WHERE " + queryStr.query.substring("AND ".length())
      }
    }
    var typed = em.createQuery(AllRbContactTypesWithFilterQuery.format("count(r)", queryStr.query, ""), classOf[Long])
    if (queryStr.data.size() > 0) queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    typed.getSingleResult
  }

  def getAllRbContactTypesWithFilter(page: Int, limit: Int, sorting: String, filter: ListDataFilter): java.util.LinkedList[Object] = {

    val queryStr = filter.toQueryStructure()
    if (queryStr.data.size() > 0) {
      if (queryStr.query.indexOf("AND ") == 0) {
        queryStr.query = "WHERE " + queryStr.query.substring("AND ".length())
      }
    }
    val typed = em.createQuery(AllRbContactTypesWithFilterQuery.format("r.id, r.name", queryStr.query, sorting), classOf[Array[AnyRef]])
      .setMaxResults(limit)
      .setFirstResult(limit * page)
    if (queryStr.data.size() > 0) queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))

    val result = typed.getResultList
    val list = new java.util.LinkedList[Object]
    result.foreach(f => {
      list.add((f(0).asInstanceOf[java.lang.Integer], f(1).asInstanceOf[java.lang.String]))
    })
    list
  }

  val AllRbContactTypesWithFilterQuery = """
  SELECT DISTINCT %s
  FROM
    RbContactType r
  %s
  %s
                                         """
}
