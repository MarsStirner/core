package ru.korus.tmis.core.database

import javax.interceptor.Interceptors
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.ejb.Stateless
import javax.persistence.{EntityManager, PersistenceContext}
import grizzled.slf4j.Logging
import java.lang.Iterable
import ru.korus.tmis.util.{ConfigManager, I18nable}
import ru.korus.tmis.core.exception.NoSuchRbDocumentTypeException
import ru.korus.tmis.core.entity.model.{RbDocumentType}
import scala.collection.JavaConversions._
import ru.korus.tmis.core.data.{DictionaryListRequestDataFilter, QueryDataStructure}

@Interceptors(Array(classOf[LoggingInterceptor]))
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

  val RbDocumentTypeDictionaryFindQuery = """
    SELECT t.id, t.name
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

  def getAllDocumentTypesWithFilter(page: Int, limit: Int, sortingField: String, sortingMethod: String, filter: Object): java.util.LinkedList[Object] = {
    var queryStr: QueryDataStructure = if (filter.isInstanceOf[DictionaryListRequestDataFilter]) {
      filter.asInstanceOf[DictionaryListRequestDataFilter].toQueryStructure()
    }
    else {
      new QueryDataStructure()
    }
    val sorting = "ORDER BY %s %s".format(sortingField, sortingMethod)
    if (queryStr.data.size() > 0 || queryStr.query.size > 0) {
      if (queryStr.query.indexOf("AND ") == 0) {
        queryStr.query = "WHERE " + queryStr.query.substring("AND ".length())
      }
    }
    var typed = em.createQuery(AllDocumentTypesWithFilterQuery.format("r.id, r.name", queryStr.query, sorting), classOf[Array[AnyRef]])
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
        result.foreach(rbType => {
          em.detach(rbType)
        })
        result(0)
      }
    }
  }
}
