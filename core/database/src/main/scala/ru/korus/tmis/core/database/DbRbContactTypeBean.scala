package ru.korus.tmis.core.database

import javax.ejb.Stateless
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.interceptor.Interceptors
import grizzled.slf4j.Logging
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.util.{ConfigManager, I18nable}
import ru.korus.tmis.core.exception.NoSuchRbContactTypeException
import ru.korus.tmis.core.entity.model.RbContactType
import java.lang.Iterable

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbRbContactTypeBean
  extends DbRbContactTypeBeanLocal
  with Logging
  with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  val RbContactTypeFindQuery = """
    SELECT t
    FROM
      RbContactType t
    WHERE
      t.id = :id
                               """


  def getAllRbContactTypes(): Iterable[RbContactType] = {
    em.createNamedQuery("RbContactType.findAll", classOf[RbContactType]).getResultList
  }

  def getRbContactTypeById(id: Int): RbContactType = {
    val result = em.createQuery(RbContactTypeFindQuery,
      classOf[RbContactType])
      .setParameter("id", id)
      .getResultList

    result.size match {
      case 0 => {
        throw new NoSuchRbContactTypeException(
          ConfigManager.ErrorCodes.RbContactTypeNotFound,
          id,
          i18n("error.rbContactTypeNotFound").format(id))
      }
      case size => {
        val rbContactType = result.iterator.next()
        em.detach(rbContactType)
        rbContactType
      }
    }
  }
}
