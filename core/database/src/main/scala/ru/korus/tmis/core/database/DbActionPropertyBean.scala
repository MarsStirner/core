package ru.korus.tmis.core.database

import ru.korus.tmis.core.auth.AuthData
import ru.korus.tmis.core.entity.model._
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.core.logging.LoggingInterceptor
import ru.korus.tmis.util.{ConfigManager, I18nable}

import java.util.{List, Date}
import javax.ejb.{TransactionAttributeType, TransactionAttribute, EJB, Stateless}
import javax.interceptor.Interceptors
import javax.persistence.{EntityManager, PersistenceContext}

import grizzled.slf4j.Logging
import scala.collection.JavaConversions._
import scala.collection.mutable.LinkedHashMap

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbActionPropertyBean
  extends DbActionPropertyBeanLocal
  with Logging
  with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  @PersistenceContext(unitName = "rls")
  var emRls: EntityManager = _

  @EJB
  var dbAction: DbActionBeanLocal = _

  @EJB
  var dbActionPropertyType: DbActionPropertyTypeBeanLocal = _

  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  def getActionPropertyById(id: Int) = {
    val result = em.createQuery(ActionPropertyFindQuery,
      classOf[ActionProperty])
      .setParameter("id", id)
      .getResultList

    result.size match {
      case 0 => {
        throw new CoreException(
          ConfigManager.ErrorCodes.ActionPropertyNotFound,
          i18n("error.actionPropertyNotFound").format(id))
      }
      case size => {
        val actionProperty = result.iterator.next()
        em.detach(actionProperty) //временно закрыл
        actionProperty
      }
    }
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  def getActionPropertiesByActionId(actionId: Int) = {
    val result = em.createQuery(ActionPropertiesByActionIdQuery,
      classOf[ActionProperty])
      .setParameter("actionId", actionId)
      .getResultList

    result.foldLeft(LinkedHashMap.empty[ActionProperty, java.util.List[APValue]])(
      (map, ap) => {
        em.detach(ap)
        map.put(ap, getActionPropertyValue(ap))
        map
      }
    )
  }

  def getActionPropertyValue(actionProperty: ActionProperty) = {
    val APValueRLSType = classOf[APValueRLS]
    actionProperty.getValueClass match {
      case null => {
        error("ActionProperty with NULL valueClass: " + actionProperty)
        scala.List.empty[APValue]
      }
      case APValueRLSType => {
        // Получаем целочисленные значения РЛС, которые соответствуют значениям ПК из rls.vNomen
        val apvs = getPropertyValues(actionProperty.getId.intValue, APValueRLSType.getSimpleName)
        // Достаем для каждого значения строку из РЛС и передаем в обертку
        val result = apvs.foldLeft(scala.List.empty[APValue])((l, v) => {
          val apvRls = v.asInstanceOf[APValueRLS]
          val ns = getRLSNomenclature(apvRls.getKey)
          val vals: scala.List[APValue] = ns.map((n) => {
            val w = new APValueRLSWrapper(apvRls, n)
            w.asInstanceOf[APValue]
          }).toList
          vals ::: l
        })
        result
      }
      case apValueClass => {
        val apvs = getPropertyValues(actionProperty.getId.intValue, apValueClass.getSimpleName)
        val result = apvs.map((v) => v.asInstanceOf[APValue])
        result
      }
    }
  }

  def createActionProperty(a: Action, aptId: Int, userData: AuthData) = {
    val apt = dbActionPropertyType.getActionPropertyTypeById(aptId)

    val now = new Date()

    val ap = new ActionProperty

    ap.setCreateDatetime(now)
    ap.setModifyDatetime(now)
    //TODO: временно подсовываю пустой Staff когда нет AuthData
    if (userData != null) {
      ap.setCreatePerson(userData.user)
      ap.setCreateDatetime(now)
      ap.setModifyPerson(userData.user)
      ap.setModifyDatetime(now)
    }

    ap.setAction(a)
    ap.setType(apt)

    ap
  }

  def updateActionProperty(id: Int, version: Int, userData: AuthData) = {
    val ap = getActionPropertyById(id)

    ap.setModifyPerson(userData.user)
    ap.setModifyDatetime(new Date)
    ap.setVersion(version)

    ap
  }

  def setActionPropertyValue(ap: ActionProperty, value: String, index: Int = 0) = {
    val apvs = getActionPropertyValue(ap)

    apvs.size match {
      case 0 => {
        // Если не нашли значение, то создаем новое, если не флатДиректори
        if (ap.getType.getTypeName.compareTo("FlatDirectory") != 0 && ap.getType.getTypeName.compareTo("FlatDictionary") != 0) {
          createActionPropertyValue(ap, value, index)
        } else if (value != null && value.compareTo("") != 0) {
          createActionPropertyValue(ap, value, index)
        }
        else null
      }
      case size => {
        if (ap.getType.getIsVector) {
          val apv = createActionPropertyValue(ap, value, index)
          apv
        }
        else {
          val apv = apvs.get(0)
          if (apv.unwrap.setValueFromString(value)) apv else null
        }
      }
    }
  }

  def createActionPropertyValue(ap: ActionProperty, value: String, index: Int = 0) = {
    val cls = ap.getValueClass
    cls match {
      case null => {
        throw new CoreException(i18n("error.actionPropertyTypeClassNotFound").format(ap.getId))
      }
      case _ => {}
    }
    val apv = cls.newInstance.asInstanceOf[APValue]
    // Устанваливаем id, index
    apv.linkToActionProperty(ap, index)
    // Записываем значение
    if (apv.setValueFromString(value)) apv else null
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * Получить APValue в простом случае по id и имени entity-класса
   */
  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  def getPropertyValues(id: Int, entityName: String) = {
    val query = APValueQuery.format(entityName)
    val apvs = em.createQuery(query)
      .setParameter("id", id)
      .getResultList
    apvs.foreach(v => em.detach(v))
    apvs
  }

  /**
   * @param code поле code в таблице rls.vNomen
   */
  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  def getRLSNomenclature(code: Int): List[Nomenclature] = {
    emRls.createQuery("SELECT n FROM Nomenclature n WHERE n.code = :code",
      classOf[Nomenclature])
      .setParameter("code", code)
      .getResultList
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  def getActionPropertiesByActionIdAndTypeNames(actionId: Int, names: java.util.List[String]) = {
    val result = em.createQuery(ActionPropertiesByActionIdAndNamesQuery,
      classOf[ActionProperty])
      .setParameter("actionId", actionId)
      .setParameter("names", asJavaCollection(names))
      .getResultList

    result.foldLeft(LinkedHashMap.empty[ActionProperty, java.util.List[APValue]])(
      (map, ap) => {
        em.detach(ap)
        map.put(ap, getActionPropertyValue(ap))
        map
      }
    )
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  def getActionPropertiesByActionIdAndTypeId(actionId: Int, typeId: Int) = {
    val result = em.createQuery(ActionPropertiesByActionIdAndTypeIdQuery,
      classOf[ActionProperty])
      .setParameter("actionId", actionId)
      .setParameter("typeId", typeId)
      .getResultList

    result.foreach(v => em.detach(v))
    result
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  def getActionPropertiesByActionIdAndRbCoreActionPropertyIds(actionId: Int, ids: java.util.List[java.lang.Integer]) = {
    val result = em.createQuery(ActionPropertiesByActionIdAndRbCoreActionPropertyIds, classOf[ActionProperty])
      .setParameter("actionId", actionId)
      .setParameter("ids", asJavaCollection(ids))
      .getResultList

    result.foldLeft(LinkedHashMap.empty[ActionProperty, java.util.List[APValue]])(
      (map, ap) => {
        em.detach(ap)
        map.put(ap, getActionPropertyValue(ap))
        map
      }
    )
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  def getActionPropertiesForEventByActionTypes(eventId: Int, atIds: java.util.Set[java.lang.Integer], coreIds: java.util.Set[java.lang.Integer]) = {
    val result = em.createQuery(ActionPropertiesForEventByActionTypesQuery, classOf[Array[AnyRef]])
      .setParameter("eventId", eventId)
      .setParameter("atIds", asJavaCollection(atIds))
      .setParameter("coreIds", asJavaCollection(coreIds))
      .getResultList

    result.foldLeft(LinkedHashMap.empty[ActionProperty, java.util.List[APValue]])(
      (map, ap) => {
        em.detach(ap(0).asInstanceOf[ActionProperty])
        map.put(ap(0).asInstanceOf[ActionProperty], getActionPropertyValue(ap(0).asInstanceOf[ActionProperty]))
        map
      }
    )
  }

  val ActionPropertyFindQuery = """
    SELECT ap
    FROM
      ActionProperty ap
    WHERE
      ap.id = :id
    AND
      ap.deleted = 0
                                """

  val APValueQuery = """
    SELECT v
    FROM
      %s v
    WHERE
      v.id.id = :id
    ORDER BY
      v.id.index ASC
                     """

  val ActionPropertiesByActionIdQuery = """
    SELECT ap
    FROM
      ActionProperty ap
    WHERE
      ap.action.id = :actionId
    AND
      ap.deleted = 0
    ORDER BY
      ap.actionPropertyType.idx ASC
                                        """

  val ActionPropertiesByActionIdAndNamesQuery = """
    SELECT ap
    FROM
      ActionProperty ap
    WHERE
      ap.action.id = :actionId
    AND
      ap.actionPropertyType.name IN :names
    AND
      ap.deleted = 0
    ORDER BY
      ap.actionPropertyType.idx ASC
                                                """

  val ActionPropertiesByActionIdAndTypeIdQuery = """
    SELECT ap
    FROM
      ActionProperty ap
    WHERE
      ap.action.id = :actionId
    AND
      ap.actionPropertyType.id = :typeId
    AND
      ap.deleted = 0
                                                 """

  val ActionPropertiesByActionIdAndRbCoreActionPropertyIds =
    """
      SELECT ap
      FROM
        ActionProperty ap
      WHERE
        ap.action.id = :actionId
      AND
        ap.actionPropertyType.id IN (
          SELECT cap.actionPropertyType.id
          FROM RbCoreActionProperty cap
          WHERE cap.actionType.id = ap.action.actionType.id
          AND cap.id IN :ids
        )
      AND
        ap.deleted = 0
    """
  val ActionPropertiesForEventByActionTypesQuery =
    """
      SELECT ap, MAX(a.createDatetime)
      FROM
        ActionProperty ap
          JOIN ap.action a
          JOIN a.actionType at
          JOIN a.event e
      WHERE
        e.id = :eventId
      AND at.id IN :atIds
      AND
        ap.actionPropertyType.id IN (
          SELECT cap.actionPropertyType.id
          FROM RbCoreActionProperty cap
          WHERE cap.actionType.id = at.id
          AND cap.id IN :coreIds
        )
      AND
        ap.deleted = 0
      GROUP BY ap
    """

  def createActionProperty(action: Action, actionPropertyType: ActionPropertyType): ActionProperty = {
    val now = new Date
    val newActionProperty = new ActionProperty()
    //Инициализируем структуру Event
    try {
      newActionProperty.setCreateDatetime(now);
      newActionProperty.setCreatePerson(null);
      newActionProperty.setModifyPerson(null);
      newActionProperty.setModifyDatetime(now);
      newActionProperty.setDeleted(false);
      newActionProperty.setAction(action);
      newActionProperty.setType(actionPropertyType)
      newActionProperty.setNorm("");
      //1. Инсертим
      em.persist(newActionProperty);
    }
    catch {
      case ex: Exception => throw new CoreException("error while creating action ");
    }
    newActionProperty
  }

  val ActionProperty_ActionByValue = """
    SELECT ap_act
    FROM APValueAction ap_act
    WHERE ap_act.value = :VALUE
                                     """

  def getActionProperty_ActionByValue(action: Action): APValueAction = {
    em.createQuery(ActionProperty_ActionByValue, classOf[APValueAction]).setParameter("VALUE", action).getSingleResult
  }
}
