package ru.korus.tmis.core.database

import ru.korus.tmis.core.entity.model.RbTempInvalidReason
import javax.interceptor.Interceptors
import javax.ejb.Stateless


import javax.persistence.PersistenceContext
import javax.persistence.EntityManager
import java.lang.Iterable
import ru.korus.tmis.core.exception.NoSuchRbTempInvalidReasonException
import ru.korus.tmis.scala.util.{I18nable, ConfigManager}
import scala.language.reflectiveCalls


@Stateless
class DbRbTempInvalidReasonBean
  extends DbRbTempInvalidReasonBeanLocal

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

        rbTempInvalidReason
      }
    }
  }

  def getRbTempInvalidReasonByCode(code: String): RbTempInvalidReason = {
    val result = em.createNamedQuery("RbTempInvalidReason.findByCode",
      classOf[RbTempInvalidReason]).setParameter("code", code).getResultList
    if (result.isEmpty) {
      return null
    } else {
      return result.iterator.next
    }
  }
}