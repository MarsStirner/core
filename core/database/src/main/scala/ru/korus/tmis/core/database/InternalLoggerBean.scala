package ru.korus.tmis.core.database

import ru.korus.tmis.core.entity.model.{ProfileEvent, FaultEvent}
import ru.korus.tmis.util.I18nable

import java.lang.String
import java.util.UUID
import javax.ejb.{TransactionAttributeType, TransactionAttribute, Stateless}
import javax.persistence.{EntityManager, PersistenceContext}

import grizzled.slf4j.Logging

import scala.collection.JavaConversions._

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
class InternalLoggerBean
  extends InternalLoggerBeanLocal
  with Logging
  with I18nable {

  @PersistenceContext(unitName = "tmis_core")
  var em: EntityManager = _

  def logFault(in: String, out: String) = {
    try {
      em.persist(new FaultEvent(in, out))
    }
    catch {
      case ex: Exception => {
        error("Exception when logging fault: " + ex)
      }
    }
  }

  def logMethodCall(sessionId: UUID,
                    nestedLevel: Int,
                    number: Int,
                    time: Long,
                    className: String,
                    methodName: String) = {
    try {
      em.persist(new ProfileEvent(sessionId,
        nestedLevel,
        number,
        time,
        className,
        methodName))
    }
    catch {
      case ex: Exception => {
        error("Exception when logging method call: " + ex)
      }
    }
  }

  def getStatistics(limit: Int) = {
    val rootEvents = em
      .createQuery(SelectTopStatisticsQuery, classOf[ProfileEvent])
      .setMaxResults(limit)
      .getResultList

    val sessionIdsHigh = rootEvents.map(_.getSessionIdHigh)
    val sessionIdsLow = rootEvents.map(_.getSessionIdLow)

    val allEvents = em
      .createQuery(SelectDetailedTopStatisticsQuery, classOf[ProfileEvent])
      .setParameter("sessionIdsHigh",
      asJavaCollection(sessionIdsHigh))
      .setParameter("sessionIdsLow",
      asJavaCollection(sessionIdsLow))
      .getResultList

    val auxMap = rootEvents.foldLeft(
      Map.empty[(Long, Long), ProfileEvent]
    )((map, e) => {
      map + ((e.getSessionIdHigh, e.getSessionIdLow) -> e)
    })

    val resultMap = rootEvents.foldLeft(
      Map.empty[ProfileEvent, java.util.List[ProfileEvent]]
    )((map, e) => {
      map + (e -> new java.util.LinkedList[ProfileEvent]())
    })

    allEvents.foldLeft(resultMap)(
      (map, e) => {
        auxMap.get((e.getSessionIdHigh, e.getSessionIdLow)) match {
          case None => {}
          case Some(key) => {
            map.get(key) match {
              case None => {}
              case Some(list) => list.add(e)
            }
          }
        }
        map
      })
  }

  val SelectTopStatisticsQuery = """
    SELECT p
    FROM ProfileEvent p
    WHERE
      p.nestedLevel = 0
    AND
      p.number = 0
    ORDER BY p.time DESC
                                 """

  val SelectDetailedTopStatisticsQuery = """
    SELECT p
    FROM ProfileEvent p
    WHERE
      p.sessionIdHigh IN :sessionIdsHigh
    AND
      p.sessionIdLow IN :sessionIdsLow
    ORDER BY p.number ASC
                                         """
}
