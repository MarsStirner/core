package ru.korus.tmis.core.database

import java.util
import javax.interceptor.Interceptors

import javax.ejb.{EJB, Stateless}
import grizzled.slf4j.Logging
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.data.{DictionaryListRequestDataFilter, QueryDataStructure}
import ru.korus.tmis.core.entity.model.OrgStructure
import scala.collection.JavaConversions._
import ru.korus.tmis.core.filter.ListDataFilter
import ru.korus.tmis.scala.util.I18nable

/**
 * Методы для работы с таблицей s11r64.rbFinance.
 * @author Ivan Dmitriev
 * @since 1.0.0.45
 */

@Stateless
class DbRbRequestTypeBean extends DbRbRequestTypeBeanLocal
                          with Logging
                          with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def filterByOrgStruct(arrays: util.List[Array[AnyRef]], orgStructure: OrgStructure) : util.List[Array[AnyRef]] = {
    val requestTypeIdByOrgList = em.createNamedQuery("OrgStructureEventType.findRequestTypeIdByOrgId", classOf[Integer])
      .setParameter("orgId", orgStructure.getId)
      .getResultList
    return arrays.filter(a => { a.length > 0 && requestTypeIdByOrgList.contains(a(0))})
  }

  def getAllRbRequestTypesWithFilter(page: Int, limit: Int, sorting: String, filter: ListDataFilter, records: (java.lang.Long) => java.lang.Boolean,  orgStructure: OrgStructure) = {

    val queryStr = filter.toQueryStructure()
    if (queryStr.data.size() > 0) {
      if (queryStr.query.indexOf("AND ") == 0) {
        queryStr.query = "WHERE " + queryStr.query.substring("AND ".length())
      }
    }

    if (records!=null) records(em.createQuery(AllRbRequestTypesWithFilterQuery.format("count(r)", queryStr.query, ""), classOf[Long]).getSingleResult)//Перепишем количество записей для структуры

    val typed = em.createQuery(AllRbRequestTypesWithFilterQuery.format("r.id, r.name, r.code", queryStr.query, sorting), classOf[Array[AnyRef]])
                  .setMaxResults(limit)
                  .setFirstResult(limit * page)
    if (queryStr.data.size() > 0) queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))

    val result = filterByOrgStruct(typed.getResultList, orgStructure)
    val list = new java.util.LinkedList[Object]
    result.foreach(f => {
      list.add((f(0).asInstanceOf[java.lang.Integer], f(1).asInstanceOf[java.lang.String], f(2).asInstanceOf[java.lang.String]))
    })
    list
  }

  val AllRbRequestTypesWithFilterQuery = """
  SELECT DISTINCT %s
  FROM
    RbRequestType r
  %s
  %s
  """
}
