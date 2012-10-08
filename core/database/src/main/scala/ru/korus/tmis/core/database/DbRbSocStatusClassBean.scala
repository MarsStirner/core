package ru.korus.tmis.core.database

import ru.korus.tmis.core.entity.model.RbSocStatusClass
import ru.korus.tmis.core.exception.NoSuchEntityException
import javax.interceptor.Interceptors
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.ejb.Stateless
import grizzled.slf4j.Logging
import ru.korus.tmis.util.{I18nable, ConfigManager}
import scala.collection.JavaConversions._
import javax.persistence.{EntityManager, PersistenceContext}

/**
 * Created with IntelliJ IDEA.
 * User: mmakankov
 * Date: 11.07.12
 * Time: 17:43
 * To change this template use File | Settings | File Templates.
 */

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbRbSocStatusClassBean extends DbRbSocStatusClassBeanLocal
with Logging
with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  val FindByIdQuery = """
    SELECT r
    FROM
      RbSocStatusClass r
    WHERE
      r.id = :id
                      """

  def getSocStatusClassById(id: Int): RbSocStatusClass = {
    val result = em.createQuery(FindByIdQuery,
      classOf[RbSocStatusClass])
      .setParameter("id", id)
      .getResultList

    result.size match {
      case 0 => {
        throw new NoSuchEntityException(
          ConfigManager.ErrorCodes.ClientSocStatusTypeNotFound,
          id,
          i18n("error.ClientSocStatusClassNotFound"))
      }
      case size => {
        result.foreach(rbType => {
          em.detach(rbType)
        })
        result(0)
      }
    }
  }

}
