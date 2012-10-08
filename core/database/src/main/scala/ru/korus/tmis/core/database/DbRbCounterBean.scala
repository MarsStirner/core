package ru.korus.tmis.core.database

import javax.ejb.Stateless
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.interceptor.Interceptors
import grizzled.slf4j.Logging
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.entity.model.RbCounter
import ru.korus.tmis.util.{ConfigManager, I18nable}
import ru.korus.tmis.core.exception.NoSuchRbCounterIdException
import java.lang.Iterable

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbRbCounterBean
  extends DbRbCounterBeanLocal
  with Logging
  with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  val RbCounterFindQuery = """
    SELECT t
    FROM
      RbCounter t
    WHERE
      t.id = :id
                           """


  def getAllRbCounters(): Iterable[RbCounter] = {
    em.createNamedQuery("RbCounter.findAll", classOf[RbCounter]).getResultList
  }

  def getRbCounterById(id: Int): RbCounter = {
    val result = em.createQuery(RbCounterFindQuery,
      classOf[RbCounter])
      .setParameter("id", id)
      .getResultList

    result.size match {
      case 0 => {
        throw new NoSuchRbCounterIdException(
          ConfigManager.ErrorCodes.RbCounterNotFound,
          id,
          i18n("error.rbCounterNotFound"))
      }
      case size => {
        val rbCounter = result.iterator.next()
        em.detach(rbCounter)
        rbCounter
      }
    }
  }

  def setRbCounterValue(rbCounter: RbCounter, value: Int) = {
    rbCounter.setValue(value);
    rbCounter
  }
}
