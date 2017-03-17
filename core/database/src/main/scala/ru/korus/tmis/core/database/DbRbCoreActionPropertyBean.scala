package ru.korus.tmis.core.database

import java.util
import javax.interceptor.Interceptors
import javax.ejb.Stateless
import javax.persistence.{EntityManager, PersistenceContext}

import ru.korus.tmis.core.entity.model.RbCoreActionProperty

import scala.collection.JavaConversions._


@Stateless
class DbRbCoreActionPropertyBean
  extends DbRbCoreActionPropertyBeanLocal
 {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def getRbCoreActionPropertyByActionTypeIdAndCorePropertyName(actionTypeId: Int, cpName: String): RbCoreActionProperty = {

    val result = em.createQuery(RbCoreActionPropertiesByActionTypeIdQuery.format(AndActionTypeId + AndCorePropertyName),
      classOf[RbCoreActionProperty])
      .setParameter("actionTypeId", actionTypeId)
      .setParameter("cpName", cpName)
      .getResultList

    result.size() match {
      case 0 => null
      case _ => {

        result.iterator().next()
      }
    }
  }

  def getRbCoreActionPropertyByActionTypeIdAndActionPropertyTypeId(actionTypeId: Int, actionPropertyTypeId: Int): RbCoreActionProperty = {

    val result = em.createQuery(RbCoreActionPropertiesByActionTypeIdQuery.format(AndActionTypeId + AndActionPropertyTypeId),
      classOf[RbCoreActionProperty])
      .setParameter("actionTypeId", actionTypeId)
      .setParameter("actionPropertyTypeId", actionPropertyTypeId)
      .getResultList

    result.size() match {
      case 0 => null
      case _ => {

        result.iterator().next()
      }
    }
  }

  def getRbCoreActionPropertiesByActionTypeId(actionTypeId: Int): util.List[RbCoreActionProperty] = {
    val result = em.createQuery(RbCoreActionPropertiesByActionTypeIdQuery.format(AndActionTypeId),
      classOf[RbCoreActionProperty])
      .setParameter("actionTypeId", actionTypeId)
      .getResultList

    result.size() match {
      case 0 => null
      case _ => {

        result
      }
    }
  }

  def getRbCoreActionPropertiesByIds(capIds: java.util.List[java.lang.Integer]): util.List[RbCoreActionProperty] = {
    val result = em.createQuery(RbCoreActionPropertiesByActionTypeIdQuery.format(AndCapIds),
      classOf[RbCoreActionProperty])
      .setParameter("ids", asJavaCollection(capIds))
      .getResultList

    result.size() match {
      case 0 => null
      case _ => {

        result
      }
    }
  }

  def getRbCoreActionPropertiesByActionPropertyTypeId(actionPropertyTypeId: Int): RbCoreActionProperty = {
    val result = em.createQuery(RbCoreActionPropertiesByActionTypeIdQuery.format(ActionPropertyTypeId),
      classOf[RbCoreActionProperty])
      .setParameter("actionPropertyTypeId", actionPropertyTypeId)
      .getResultList

    result.size() match {
      case 0 => null
      case _ => {
        result.iterator().next()
      }
    }
  }

  def getRbCoreActionPropertiesById(id: Int): RbCoreActionProperty = {
    val result = em.createQuery(RbCoreActionPropertiesByActionTypeIdQuery.format(CoreId),
      classOf[RbCoreActionProperty])
      .setParameter("id", id)
      .getResultList

    result.size() match {
      case 0 => null
      case _ => {

        result.iterator().next()
      }
    }
  }

  val RbCoreActionPropertiesByActionTypeIdQuery =
    """
       SELECT cap
       FROM RbCoreActionProperty cap
       WHERE
       %s
    """
  val AndCorePropertyName =
    """
      AND
          cap.name = :cpName
    """
  val AndActionTypeId =
    """
      cap.actionType.id = :actionTypeId
    """
  val AndCapIds =
    """
      cap.id IN :ids
    """
  val AndActionPropertyTypeId =
    """
      AND
        cap.actionPropertyType.id = :actionPropertyTypeId
    """
  val ActionPropertyTypeId =
    """
      cap.actionPropertyType.id = :actionPropertyTypeId
    """
  val CoreId =
    """
      cap.id = :id
    """
}
