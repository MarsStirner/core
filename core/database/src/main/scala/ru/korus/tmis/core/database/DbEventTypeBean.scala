package ru.korus.tmis.core.database

import javax.persistence.{PersistenceContext, EntityManager}
import javax.ejb.Stateless
import grizzled.slf4j.Logging
import ru.korus.tmis.util.{ConfigManager, I18nable}
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.interceptor.Interceptors
import ru.korus.tmis.core.data.{EventTypesListRequestDataFilter, QueryDataStructure}
import ru.korus.tmis.core.entity.model.EventType
import scala.collection.JavaConversions._
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.core.filter.ListDataFilter

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbEventTypeBean
  extends DbEventTypeBeanLocal
  with I18nable
  with Logging {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def getEventTypeById(id: Int) = {
    val result = em.createQuery(EventTypeByIdQuery, classOf[EventType])
                   .setParameter("id", id)
                   .getResultList

    if (result== null || result.size()<=0){
      throw new CoreException(
        ConfigManager.ErrorCodes.EventTypeNotFound,
        i18n("error.eventTypeNotFound").format(id))
    } else {
      result.foreach(em.detach(_))
      result.iterator().next()
    }
  }

  def getEventTypesByRequestTypeIdAndFinanceId(page: Int, limit: Int, sorting: String, filter: ListDataFilter, records: (java.lang.Long) => java.lang.Boolean) = {

    val queryStr = filter.toQueryStructure()

    val typed = em.createQuery(EventTypesByRequestTypeIdAndFinanceIdQuery.format("et", queryStr.query, sorting), classOf[EventType])
    if (queryStr.data.size() > 0) queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    val result = typed.getResultList
    result.foreach(em.detach(_))

    //Перепишем общее количество записей для запроса
    if (records!=null) records(result.size)

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
