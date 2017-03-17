package ru.korus.tmis.core.database

import java.util
import javax.ejb.Stateless
import javax.persistence.{EntityManager, PersistenceContext}

import ru.korus.tmis.core.entity.model.{EventType, OrgStructure}
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.core.filter.ListDataFilter
import ru.korus.tmis.scala.util.{ConfigManager, I18nable}

import scala.collection.JavaConversions._
import scala.language.reflectiveCalls

@Stateless
class DbEventTypeBean
  extends DbEventTypeBeanLocal
  with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def getEventTypeById(id: Int): EventType = {
    val result = em.createQuery(EventTypeByIdQuery, classOf[EventType])
                   .setParameter("id", id)
                   .getResultList

    if (result== null || result.size()<=0){
      throw new CoreException(
        ConfigManager.ErrorCodes.EventTypeNotFound,
        i18n("error.eventTypeNotFound").format(id))
    } else {
      result.iterator().next()
    }
  }

  def getEventTypeByCode(code: String): EventType = {
    val result = em.createQuery(EventTypeByCodeQuery, classOf[EventType])
      .setParameter("code", code)
      .getResultList

    if (result== null || result.size()<=0){
      throw new CoreException(
        ConfigManager.ErrorCodes.EventTypeNotFound,
        i18n("error.eventTypeNotFound").format(code))
    } else {

      result.iterator().next()
    }
  }


  def getEventTypesByRequestTypeIdAndFinanceId(page: Int, limit: Int, sorting: String, filter: ListDataFilter, records: (java.lang.Long) => java.lang.Boolean): util.List[EventType] = {

    val queryStr = filter.toQueryStructure

    val typed = em.createQuery(EventTypesByRequestTypeIdAndFinanceIdQuery.format("et", queryStr.query, sorting), classOf[EventType])
    if (queryStr.data.size() > 0) queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    val result = typed.getResultList


    //Перепишем общее количество записей для запроса
    if (records!=null) records(result.size.toLong)

    //проведем  разбиение на страницы вручную (необходимо чтобы не использовать отдельный запрос на recordcounts)
    if (page>=0 && limit>0) {
      if((result.size - limit*(page+1))>0)
        result.dropRight(result.size - limit*(page+1)).drop(page*limit)
      else
        result.drop(page*limit)
    }
    else
      result
  }

  def filterByPersonDepartment(eventTypeList: java.util.List[EventType], orgStructure: OrgStructure): java.util.List[EventType] = {
    if(orgStructure == null) {
      return eventTypeList
    }
    val eventTypeIdByOrgList = em.createNamedQuery("OrgStructureEventType.findEventTypeIdByOrgId", classOf[Integer])
      .setParameter("orgId", orgStructure.getId)
      .getResultList
    eventTypeList.filter(et => eventTypeIdByOrgList.contains(et.getId))
  }

  val EventTypeByIdQuery =
    """
    SELECT et
    FROM
      EventType et
    WHERE
      et.id = :id
    AND
      et.deleted = '0'
    """

  val EventTypeByCodeQuery =
    """
    SELECT et
    FROM
      EventType et
    WHERE
      et.code = :code
    AND
      et.deleted = '0'
    """


  val EventTypesByRequestTypeIdAndFinanceIdQuery =
    """
    SELECT %s
    FROM
      EventType et
    WHERE
      et.deleted = '0'
      %s
      %s
    """

}
