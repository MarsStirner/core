package ru.korus.tmis.core.database.common

import ru.korus.tmis.core.exception.CoreException


import grizzled.slf4j.Logging
import java.util.Collection
import javax.ejb.{TransactionAttributeType, Stateless, TransactionAttribute}
import javax.interceptor.Interceptors
import javax.persistence._

import scala.collection.JavaConversions._
import org.eclipse.persistence.jpa.JpaEntityManager
import javax.ejb.{SessionContext, TransactionAttributeType, Stateless, TransactionAttribute}
import javax.annotation.Resource
import ru.korus.tmis.scala.util.{I18nable, ConfigManager}
import scala.language.reflectiveCalls


@Stateless
class DbManagerBean
  extends DbManagerBeanLocal
  with Logging
  with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _
  var vendorJpaEm: JpaEntityManager = _

  def getEntityId[T](entity: T) = {
    //## TODO: работает строго через раз: каждый второй раз vendorJpaEm.getServerSession() == null
    //##    if (vendorJpaEm == null) {
    //##      vendorJpaEm = em.unwrap(classOf[JpaEntityManager])
    //##    }
    vendorJpaEm = em.unwrap(classOf[JpaEntityManager])
    vendorJpaEm.getServerSession().getId(entity)
  }

  def mergeOrPersisRoutine[T](e: T): T = {
    var id = getEntityId(e)
    var ci = e.asInstanceOf[AnyRef].getClass.asInstanceOf[Class[T]]
    var existingEntity = id match {
      case null => {
        null
      }
      case _ => {
        em.find(ci, id)
      }
    }
    if (existingEntity == null) {
      //persts only when entry not exists         	trace("!!!Persisted " + e)
      em.persist(e)
      e
    } else if (!em.contains(e)) {
      //merge entity e with managed entity _e
      //##TODO: ситуация исключительная. Если entity не найдена по ИД то и мерджить ее нельзя. Вешается с локом БД
      //      var _e = em.merge(e)
      //      _e
      e
    } else {
      //do nothing.. if e managed all changes
      //will persist to db on em.flush()
      e
    }
  }

  def persist[T](entity: T) = {
    try {
      em.persist(entity)
      em.flush()
      trace("Persisted " + entity)
    } catch {
      case entity: OptimisticLockException => {
        throw new CoreException(
          ConfigManager.ErrorCodes.RecordChanged,
          i18n("error.entryIsChanged"))
      }
    }
  }


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

  def persistOrMerge[T](entity: T) = {
    var result = mergeOrPersisRoutine(entity)
    em.flush()
    result
  }

  def persistOrMergeAll[T](entities: Collection[T]) = {
    try {
      val result =
        entities.map(e => {
          mergeOrPersisRoutine(e)
        })
      em.flush()
      entities.foreach(e => trace("Persisted " + e))
      asJavaCollection(result)
    } catch {
      case e: OptimisticLockException => {
        throw new CoreException(
          ConfigManager.ErrorCodes.RecordChanged,
          i18n("error.entryIsChanged"), e)
      }
    }
  }

  def merge[T](entity: T) = {
    try {
      val result = em.merge(entity)
      em.flush()
      trace("Merged " + entity)
      result
    } catch {
      case e: OptimisticLockException => {
        throw new CoreException(
          ConfigManager.ErrorCodes.RecordChanged,
          i18n("error.entryIsChanged"), e)
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
          i18n("error.entryIsChanged"), e)
      }
    }
  }

  def detach[T](entity: T) = {
    em.detach(entity)
    trace("Detached " + entity)
    entity
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

  def refresh[T](entity: T) {
    em.refresh(entity)
  }

}
