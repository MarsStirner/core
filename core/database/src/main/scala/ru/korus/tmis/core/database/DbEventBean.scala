package ru.korus.tmis.core.database

import ru.korus.tmis.core.logging.db.LoggingInterceptor
import ru.korus.tmis.core.entity.model.{OrgStructure, ActionType, Event}
import ru.korus.tmis.util.I18nable

import grizzled.slf4j.Logging
import java.util.Date
import javax.persistence.{EntityManager, PersistenceContext}
import javax.ejb.{TransactionAttributeType, TransactionAttribute, Stateless}
import javax.interceptor.Interceptors

import scala.collection.JavaConversions._
import ru.korus.tmis.core.exception.CoreException

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbEventBean
  extends DbEventBeanLocal
  with I18nable
  with Logging {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  def getEventById(id: Int) = {
    val result = em.createQuery(EventByIdQuery,
                                classOf[Event])
                 .setParameter("id", id)
                 .getResultList

    val e = result.get(0)
    em.detach(e)
    e
  }

  def getActionTypeFilter(eventId: Int) = {
    val result = em.createQuery(ActionTypeFilterByEventIdQuery,
                                classOf[ActionType])
                 .setParameter("id", eventId)
                 .getResultList

    result.foreach(em.detach(_))
    new java.util.HashSet(result)
  }

  def getOrgStructureForEvent(eventId: Int) = {
    val result = em.createQuery(AllOrgStructuresForEventQuery,
                                classOf[Array[AnyRef]])
                 .setParameter("eid", eventId)
                 .getResultList
                 .sortBy {
                    case (Array(org, date)) => date.asInstanceOf[Date].getTime
                  }
                 .lastOption.map{ _.head }
                 .asInstanceOf[Option[OrgStructure]]

    result match{
      case Some(org) => em detach org; org
      case None => throw new CoreException("OrgStructure for Event id = " + eventId + " not found")
    }
  }

  val AllOrgStructuresForEventQuery = """
    SELECT org, a.begDate
    FROM
      Action a,
      ActionProperty ap,
      APValueOrgStructure apvos,
      OrgStructure org
    WHERE
      a.event.id = :eid AND
      a.id = ap.action.id AND
      ap.id = apvos.id.id AND
      apvos.value.id = org.id
    AND
      a.actionType.flatCode = '%s'
    AND
      ap.actionPropertyType.name = '%s'
    AND
      ap.deleted = 0 AND
      a.deleted = 0
  """.format(i18n("db.action.movingFlatCode"), i18n("db.apt.departmentName"))


  val ActionTypeFilterByEventIdQuery = """
    SELECT at
    FROM
      EventTypeAction eta JOIN eta.actionType at,
      Event e
    WHERE
      e.id = :id
    AND
      eta.eventType = e.eventType
    AND
      at.deleted = 0
  """

  val EventByIdQuery = """
    SELECT e
    FROM
      Event e
    WHERE
      e.id = :id
    AND
      e.deleted = 0
  """
}
