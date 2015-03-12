package ru.korus.tmis.core.database

import javax.interceptor.Interceptors

import javax.ejb.Stateless
import javax.persistence.{EntityManager, PersistenceContext}
import grizzled.slf4j.Logging
import java.lang.Iterable
import ru.korus.tmis.core.exception.NoSuchRbDocumentTypeException
import ru.korus.tmis.core.entity.model.{RbDocumentType}
import scala.collection.JavaConversions._
import ru.korus.tmis.core.data.{DictionaryListRequestDataFilter, QueryDataStructure}
import ru.korus.tmis.core.filter.ListDataFilter
import ru.korus.tmis.scala.util.{I18nable, ConfigManager}
import scala.language.reflectiveCalls


@Stateless
class DbRbDocumentTypeBean
  extends DbRbDocumentTypeBeanLocal
  with Logging
  with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  val RbDocumentTypeFindQuery = """
    SELECT t
    FROM
      RbDocumentType t
    WHERE
      t.id = :id
                                """
  val RbDocumentTypeFindByNameQuery = """
    SELECT t
    FROM
      RbDocumentType t
    WHERE
      t.name = :name
    AND
      t.documentTypeGroup.id = 1
                                      """

  val RbDocumentTypeDictionaryFindQuery = """
    SELECT t.id, t.name
    FROM
      RbDocumentType t
    WHERE
      t.documentTypeGroup.id = 1
                                          """

  val FindAllRbDocumentTypeDictionaryFindQuery = """
    SELECT t
    FROM
      RbDocumentType t
    WHERE
      t.documentTypeGroup.id = 1
                                                 """

  val AllDocumentTypesWithFilterQuery = """
  SELECT DISTINCT %s
  FROM
    RbDocumentType r
  %s
  %s
                                        """


  def getCountOfDocumentTypesWithFilter(filter: Object) = {
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
    var typed = em.createQuery(AllDocumentTypesWithFilterQuery.format(
      "count(r)",
      queryStr.query,
      ""),
      classOf[Long])
    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }
    typed.getSingleResult
  }

  def getAllDocumentTypesWithFilter(page: Int, limit: Int, sorting: String, filter: ListDataFilter): java.util.LinkedList[Object] = {

    val queryStr = filter.toQueryStructure()
    if (queryStr.data.size() > 0 || queryStr.query.size > 0) {
      if (queryStr.query.indexOf("AND ") == 0) {
        queryStr.query = "WHERE " + queryStr.query.substring("AND ".length())
      }
    }
    val typed = em.createQuery(AllDocumentTypesWithFilterQuery.format("r.id, r.name", queryStr.query, sorting), classOf[Array[AnyRef]])
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

  def getAllRbDocumentTypesData(): java.util.LinkedList[Object] = {
    val types = em.createQuery(RbDocumentTypeDictionaryFindQuery, classOf[Array[AnyRef]]).getResultList
    val list = new java.util.LinkedList[Object]
    types.foreach(t => {
      list.add((t(0).asInstanceOf[java.lang.Integer], t(1).asInstanceOf[java.lang.String]))
    })
    list
  }


  def getAllRbDocumentTypes(): Iterable[RbDocumentType] = {
    em.createNamedQuery("RbDocumentType.findAll", classOf[RbDocumentType]).getResultList
  }

  def findAllRbDocumentTypes(): Iterable[RbDocumentType] = {
    em.createQuery(FindAllRbDocumentTypeDictionaryFindQuery, classOf[RbDocumentType]).getResultList
  }

  def getRbDocumentTypeById(id: Int): RbDocumentType = {
    val result = em.createQuery(RbDocumentTypeFindQuery,
      classOf[RbDocumentType])
      .setParameter("id", id)
      .getResultList

    result.size match {
      case 0 => {
        throw new NoSuchRbDocumentTypeException(
          ConfigManager.ErrorCodes.RbDocumentTypeNotFound,
          id,
          i18n("error.rbDocumentTypeNotFound"))
      }
      case size => {
        result(0)
      }
    }
  }

  def findByName(name: String): RbDocumentType = {
    val result = em.createQuery(RbDocumentTypeFindByNameQuery,
      classOf[RbDocumentType])
      .setParameter("name", name)
      .getResultList

    result.size match {
          case 0 => {
            throw new NoSuchRbDocumentTypeException(
              ConfigManager.ErrorCodes.RbDocumentTypeNotFound,
              1,
              i18n("error.rbDocumentTypeNotFound"))
          }
          case size => {
            result.foreach(rbType => {
              em.detach(rbType)
            })
            result(0)
          }
        }
  }

  @Override
  def findByCode(code: String): RbDocumentType = {
    val result = em.createNamedQuery("RbDocumentType.findByCode", classOf[RbDocumentType])
      .setParameter("code", code).setMaxResults(1).getResultList
    result.size match {
      case 0 => {
        null
      }
      case size => {
        em.detach(result(0))
        result(0)
      }
    }
  }
}
