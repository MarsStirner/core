package ru.korus.tmis.core.database

import javax.interceptor.Interceptors

import javax.ejb.Stateless
import grizzled.slf4j.Logging
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.data.{DictionaryListRequestDataFilter, QueryDataStructure}
import scala.collection.JavaConversions._
import ru.korus.tmis.core.entity.model.RbQuotaStatus
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.core.filter.ListDataFilter
import ru.korus.tmis.scala.util.{I18nable, ConfigManager}
import scala.language.reflectiveCalls

/**
 * Класс с методами для работы с таблицей s11r64.RbQuotaStatus
 * @author mmakankov
 * @since 1.0.0.48
 * @see DbRbQuotaStatusBeanLocal
 */


@Stateless
class DbRbQuotaStatusBean
  extends DbRbQuotaStatusBeanLocal
  with Logging
  with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _


  def getRbQuotaStatusById(id: Int): RbQuotaStatus = {
    val result = em.createQuery(RbQuotaStatusFindQuery,
      classOf[RbQuotaStatus])
      .setParameter("id", id)
      .getResultList

    result.size match {
      case 0 => {
        throw new CoreException(
          ConfigManager.ErrorCodes.RbQuotaStatusNotFound,
          i18n("error.rbQuotaStatusNotFound").format(id))
      }
      case size => {

        result(0)
      }
    }
  }

  def getAllRbQuotaStatusWithFilter(page: Int, limit: Int, sorting: String, filter: ListDataFilter, records: (java.lang.Long) => java.lang.Boolean) = {

    val queryStr = filter.toQueryStructure()
    if (queryStr.data.size() > 0) {
      if (queryStr.query.indexOf("AND ") == 0) {
        queryStr.query = "WHERE " + queryStr.query.substring("AND ".length())
      }
    }

    if (records!=null) records(em.createQuery(AllRbQuotaStatusWithFilterQuery.format("count(r)", queryStr.query, ""), classOf[Long]).getSingleResult)//Перепишем количество записей для структуры

    val typed = em.createQuery(AllRbQuotaStatusWithFilterQuery.format("r.id, r.name", queryStr.query, sorting), classOf[Array[AnyRef]])
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

  val RbQuotaStatusFindQuery = """
    SELECT r
    FROM
      RbQuotaStatus r
    WHERE
      r.id = :id
                               """

  val AllRbQuotaStatusWithFilterQuery = """
  SELECT %s
  FROM RbQuotaStatus r
  %s
  %s
                                        """
}
