package ru.korus.tmis.core.database

import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.core.logging.db.LoggingInterceptor
import ru.korus.tmis.util.{ConfigManager, I18nable}

import grizzled.slf4j.Logging
import java.util.Collection
import javax.ejb.{TransactionAttributeType, Stateless, TransactionAttribute}
import javax.interceptor.Interceptors
import javax.persistence._

import scala.collection.JavaConversions._

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
class DbManagerBean
  extends DbManagerBeanLocal
  with Logging
  with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def persistAll[T](entities: Collection[T]) = {
    try {
      entities.foreach(e => em.persist(e))
      em.flush()
      entities.foreach(e => trace("Persisted " + e))
    } catch {
      case e: OptimisticLockException => {
        throw new CoreException(
          ConfigManager.ErrorCodes.RecordChanged,
          i18n("error.entryIsChanged"))
      }
    }
  }

  def mergeAll[T](entities: Collection[T]) = {
    try {
      val result = entities.map(e => em.merge(e))
      em.flush()
      result.foreach(e => trace("Merged " + e))
      asJavaCollection(result)
    } catch {
      case e: OptimisticLockException => {
        throw new CoreException(
          ConfigManager.ErrorCodes.RecordChanged,
          i18n("error.entryIsChanged"))
      }
    }
  }

  def detachAll[T](entities: Collection[T]) = {
    val result = entities.map(e => {
      em.detach(e)
      e
    })
    result.foreach(e => trace("Detached " + e))
    asJavaCollection(result)
  }

  def removeAll[T](entities: Collection[T]) = {
    try {
      val merged = entities.map(e => em.merge(e))
      merged.foreach(e => em.remove(e))
      em.flush()
      merged.foreach(e => trace("Removed " + e))
    } catch {
      case e: OptimisticLockException => {
        throw new CoreException(
          ConfigManager.ErrorCodes.RecordChanged,
          i18n("error.entryIsChanged"))
      }
    }
  }
}
