package ru.korus.tmis.core.database

import java.{util, lang}
import javax.interceptor.Interceptors

import javax.ejb.Stateless

import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.data.{DictionaryListRequestDataFilter, QueryDataStructure}
import scala.collection.JavaConversions._
import ru.korus.tmis.core.filter.ListDataFilter
import ru.korus.tmis.scala.util.I18nable
import ru.korus.tmis.core.entity.model.{OrgStructure, Event}


/**
 * Методы для работы с таблицей s11r64.rbFinance.
 * @author Ivan Dmitriev
 * @since 1.0.0.45
 */

@Stateless
class DbRbFinanceBean   extends DbRbFinanceBeanLocal

                        with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def filterByOrgStructure(arrays: util.List[Array[AnyRef]], orgStructure: OrgStructure) : util.List[Array[AnyRef]] = {
    val requestTypeIdByOrgList = em.createNamedQuery("OrgStructureEventType.findFinanceTypeIdByOrgId", classOf[Integer])
      .setParameter("orgId", orgStructure.getId)
      .getResultList
    arrays.filter(a => { a.length > 0 && requestTypeIdByOrgList.contains(a(0))})
  }

  def getAllRbFinanceWithFilter(page: Int, limit: Int, sorting: String, filter: ListDataFilter, records: (java.lang.Long) => java.lang.Boolean, eventId: Integer, orgStructure: OrgStructure): util.LinkedList[Object] = {

    val queryStr = filter.toQueryStructure()
    if (queryStr.data.size() > 0) {
      if (queryStr.query.indexOf("AND ") == 0) {
        queryStr.query = "WHERE " + queryStr.query.substring("AND ".length())
      }
    }

    var args: String = "r.id, r.name"
    var argsCount: String = "count(r)"
    val query: String = if(eventId == null) {
       AllRbFinanceWithFilterQuery
      } else {
      val event: Event = em.find(classOf[Event], eventId);
      if( event == null || event.getEventType == null || event.getEventType.getRequestType == null) {
        AllRbFinanceWithFilterQuery
      } else {
        args = "et.finance.id, et.finance.name"
        argsCount = "count(et.finance)"
        RbFinanceWithFilterByEventTypeQuery.format("%s", String.valueOf(event.getEventType.getRequestType.getId) ) + " %s %s"
      }

    }
    if (records!=null) {
      records(em.createQuery(query.format(argsCount, queryStr.query, ""), classOf[Long]).getSingleResult)
    }//Перепишем количество записей для структуры

    var q: String = query.format(args, queryStr.query, sorting)
    if(eventId != null) {
      q = q.replace("r.id", "et.finance.id")
    }

    val typed = em.createQuery(q, classOf[Array[AnyRef]])
                  .setMaxResults(limit)
                  .setFirstResult(limit * page)
    if (queryStr.data.size() > 0) queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))

    val result = filterByOrgStructure(typed.getResultList, orgStructure)
    val list = new java.util.LinkedList[Object]
    result.foreach(f => {
      list.add((f(0).asInstanceOf[java.lang.Integer], f(1).asInstanceOf[java.lang.String]))
    })
    list
  }

  val AllRbFinanceWithFilterQuery = """
  SELECT DISTINCT %s
  FROM
    RbFinance r
  %s
  %s
  """

  val RbFinanceWithFilterByEventTypeQuery = """
  SELECT DISTINCT %s
  FROM
    EventType et
  WHERE et.requestType.id = %s"""

}
