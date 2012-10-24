package ru.korus.tmis.core.database

import ru.korus.tmis.core.entity.model.Nomenclature
import ru.korus.tmis.core.logging.LoggingInterceptor

import grizzled.slf4j.Logging
import javax.ejb.Stateless
import javax.interceptor.Interceptors
import javax.persistence.{EntityManager, PersistenceContext}

import scala.collection.JavaConversions._
import ru.korus.tmis.core.entity.model.Nomenclature
import ru.korus.tmis.core.data.{RlsDataListFilter, QueryDataStructure}

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbRlsBean
  extends DbRlsBeanLocal
  with Logging {

  @PersistenceContext(unitName = "rls")
  var em: EntityManager = _

  def getRlsDrugList = {
    val result = em.createQuery(RlsSortedNomenQuery,
      classOf[Nomenclature])
      .getResultList
    result.foreach(em.detach(_))
    result
  }

  def getCountOfRlsRecordsWithFilter(filter: RlsDataListFilter) = {

    val queryStr: QueryDataStructure = if (filter != null) filter.toQueryStructure()
    else new QueryDataStructure()

    if (queryStr.data.size() > 0 || queryStr.query.size > 0) {
      if (queryStr.query.indexOf("AND ") == 0)
        queryStr.query = "WHERE " + queryStr.query.substring("AND ".length())
    }

    var typed = em.createQuery(RlsListQuery.format("count(n)", queryStr.query), classOf[Long])
    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }
    typed.getSingleResult
  }

  def getRlsListWithFilter(page: Int, limit: Int, sortingField: String, sortingMethod: String, filter: RlsDataListFilter) = {

    val queryStr: QueryDataStructure = if (filter != null) filter.toQueryStructure()
    else new QueryDataStructure()

    if (queryStr.data.size() > 0 || queryStr.query.size > 0) {
      if (queryStr.query.indexOf("AND ") == 0)
        queryStr.query = "WHERE " + queryStr.query.substring("AND ".length())
    }

    queryStr.query = queryStr.query + " " + "ORDER BY %s %s".format(sortingField, sortingMethod)

    var typed = em.createQuery(RlsListQuery.format("n", queryStr.query), classOf[Nomenclature])
    if (limit > 0 && page > 0) {
      typed = typed.setMaxResults(limit)
        .setFirstResult(limit * (page - 1))
    }

    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }

    val result = typed.getResultList
    result.size() match {
      case 0 => null
      case _ => {
        result.foreach(em.detach(_))
        result
      }
    }
  }

  val RlsSortedNomenQuery = """
    SELECT n
    FROM Nomenclature n
    ORDER BY
      n.tradeName,
      n.iNPName,
      n.form,
      n.dosage,
      n.filling,
      n.packing
                            """

  val RlsListQuery = """
  SELECT %s
    FROM Nomenclature n
  %s
                     """
}
