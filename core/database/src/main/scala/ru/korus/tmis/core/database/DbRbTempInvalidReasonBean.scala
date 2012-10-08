package ru.korus.tmis.core.database

import ru.korus.tmis.core.entity.model.RbTempInvalidReason
import javax.interceptor.Interceptors
import javax.ejb.Stateless
import grizzled.slf4j.Logging
import ru.korus.tmis.util.{ConfigManager, I18nable}
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.persistence.PersistenceContext
import javax.persistence.EntityManager
import java.lang.Iterable
import ru.korus.tmis.core.exception.NoSuchRbTempInvalidReasonException

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbRbTempInvalidReasonBean
  extends DbRbTempInvalidReasonBeanLocal
  with Logging
  with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def getAllRbTempInvalidReason(): Iterable[RbTempInvalidReason] = {
    em.createNamedQuery("RbTempInvalidReason.findAll",
      classOf[RbTempInvalidReason]).getResultList
  }

  val FindByIdQuery = """
    SELECT t
    FROM
      RbTempInvalidReason t
    WHERE
      t.id = :id
                      """

  def getRbTempInvalidReasonById(id: Int): RbTempInvalidReason = {
    val result = em.createQuery(FindByIdQuery,
      classOf[RbTempInvalidReason])
      .setParameter("id", id)
      .getResultList

    result.size match {
      case 0 => {
        throw new NoSuchRbTempInvalidReasonException(
          ConfigManager.ErrorCodes.RbTempInvalidReasonNotFound,
          id,
          i18n("error.rbTempInvalidReasonNotFound"))
      }
      case size => {
        val rbTempInvalidReason = result.iterator.next()
        em.detach(rbTempInvalidReason)
        rbTempInvalidReason
      }
    }
  }

}