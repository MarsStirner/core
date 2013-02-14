package ru.korus.tmis.core.database

import javax.interceptor.Interceptors
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.ejb.Stateless
import grizzled.slf4j.Logging
import ru.korus.tmis.util.I18nable
import javax.persistence.{EntityManager, PersistenceContext}
import java.util.Date
import ru.korus.tmis.core.entity.model.{Action, JobTicket}
import scala.collection.JavaConversions._
import collection.mutable

/**
 * Методы для работы с JobTicket
 * Author: idmitriev Systema-Soft
 * Date: 2/13/13 2:30 PM
 * Since: 1.0.0.64
 */
@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbJobTicketBean extends DbJobTicketBeanLocal
                         with Logging
                         with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def getDirectionsWithJobTicketsBetweenDate (beginDate: Date, endDate: Date) = {

    val result = em.createQuery(DirectionsWithJobTicketsBetweenDateQuery, classOf[Array[AnyRef]])
                   .setParameter("beginDate", beginDate)
                   .setParameter("endDate", endDate)
                   .getResultList
    result.size() match {
      case 0 => asJavaMap(mutable.LinkedHashMap.empty[Action,JobTicket])
      case size => {
        //result.foreach(em.detach(_))
        //result
        //val directions = result.map((e) => e(0).asInstanceOf[Action])
        val directions = result.foldLeft(mutable.LinkedHashMap.empty[Action,JobTicket])(
          (map, aj) => map += (aj(0).asInstanceOf[Action] -> aj(1).asInstanceOf[JobTicket])
        )
        directions.foreach(em.detach(_))
        asJavaMap(directions)
      }
    }
  }

  val DirectionsWithJobTicketsBetweenDateQuery =
    """
    SELECT a, jt
    FROM
      JobTicket jt,
      APValueJobTicket apval,
      ActionProperty ap
        JOIN ap.action a
        JOIN a.event e
    WHERE
      jt.datetime BETWEEN :beginDate AND :endDate
    AND
      apval.val = jt.id
    AND
      apval.id = ap.id
    AND
      ap.deleted = 0
    AND
      a.deleted = 0
    AND
      e.deleted = 0
    GROUP BY a
    """
}
