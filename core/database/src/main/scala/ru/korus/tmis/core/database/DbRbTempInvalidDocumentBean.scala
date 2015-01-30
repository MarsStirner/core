package ru.korus.tmis.core.database

import ru.korus.tmis.core.entity.model.RbTempInvalidDocument
import javax.interceptor.Interceptors
import javax.ejb.Stateless
import grizzled.slf4j.Logging
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.persistence.PersistenceContext
import javax.persistence.EntityManager
import java.lang.Iterable
import ru.korus.tmis.core.exception.NoSuchRbTempInvalidDocumentException
import ru.korus.tmis.scala.util.{I18nable, ConfigManager}
import scala.language.reflectiveCalls

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbRbTempInvalidDocumentBean
  extends DbRbTempInvalidDocumentBeanLocal
  with Logging
  with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def getAllRbTempInvalidDocument(): Iterable[RbTempInvalidDocument] = {
    em.createNamedQuery("RbTempInvalidDocument.findAll",
      classOf[RbTempInvalidDocument]).getResultList
  }

  val FindByIdQuery = """
    SELECT t
    FROM
      RbTempInvalidDocument t
    WHERE
      t.id = :id
                      """

  def getRbTempInvalidDocumentById(id: Int): RbTempInvalidDocument = {
    val result = em.createQuery(FindByIdQuery,
      classOf[RbTempInvalidDocument])
      .setParameter("id", id)
      .getResultList

    result.size match {
      case 0 => {
        throw new NoSuchRbTempInvalidDocumentException(
          ConfigManager.ErrorCodes.RbTempInvalidDocumentNotFound,
          id,
          i18n("error.rbTempInvalidDocumentNotFound"))
      }
      case size => {
        val rbTempInvalidReason = result.iterator.next()
        em.detach(rbTempInvalidReason)
        rbTempInvalidReason
      }
    }
  }
}