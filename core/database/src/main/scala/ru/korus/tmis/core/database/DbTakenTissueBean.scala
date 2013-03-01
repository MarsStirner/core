package ru.korus.tmis.core.database

import scala.collection.JavaConversions._
import javax.interceptor.Interceptors
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.ejb.{EJB, Stateless}
import grizzled.slf4j.Logging
import ru.korus.tmis.util.{ConfigManager, CAPids, I18nable}
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.core.entity.model.TakenTissue

/**
 * Методы для работы с TakenTissueJournal
 * Author: mmakankov Systema-Soft
 * Date: 2/13/13 2:30 PM
 * Since: 1.0.0.69
 */

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbTakenTissueBean extends DbTakenTissueBeanLocal
                        with Logging
                        with I18nable
                        with CAPids {

    @PersistenceContext(unitName = "s11r64")
    var em: EntityManager = _

    @EJB
    var appLock: AppLockBeanLocal = _

    @EJB
    private var dbManager: DbManagerBeanLocal = _

  def getTakenTissueById(id: Int): TakenTissue = {
    val result = em.createQuery(TakenTissueByIdQuery, classOf[TakenTissue])
      .setParameter("id", id)
      .getResultList

    result.size match {
      case 0 => {
        throw new CoreException(
          ConfigManager.ErrorCodes.TakenTissueNotFound,
          i18n("error.TakenTissueNotFound").format(id))
      }
      case size => {
        result.foreach(em.detach(_))
        result(0)
      }
    }
  }

  val TakenTissueByIdQuery =
    """
      SELECT tt
      FROM
        TakenTissue tt
      WHERE
        tt.id = :id
    """
}
