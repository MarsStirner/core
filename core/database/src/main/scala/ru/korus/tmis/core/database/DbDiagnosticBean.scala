package ru.korus.tmis.core.database

import javax.interceptor.Interceptors
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.ejb.Stateless
import grizzled.slf4j.Logging
import ru.korus.tmis.util.I18nable
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.entity.model.Diagnostic
import scala.collection.JavaConversions._

/**
 * Методы для работы с таблицей Diagnostic
 * @author Ivan Dmitriev
 */
@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbDiagnosticBean  extends DbDiagnosticBeanLocal
                        with Logging
                        with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def getDiagnosticsByEventId (eventId: Int) = {
    val result =  em.createQuery(DiagnosticsByEventIdQuery, classOf[Diagnostic])
                    .setParameter("eventId", eventId)
                    .getResultList

    result.foreach(em.detach(_))
    result
  }

  val DiagnosticsByEventIdQuery =
    """
    SELECT diac
    FROM
      Diagnostic diac
        JOIN diac.event e
    WHERE
      e.id = :eventId
    AND
      diac.deleted = '0'
    """

}
