package ru.korus.tmis.core.database

import ru.korus.tmis.core.auth.AuthData
import ru.korus.tmis.core.entity.model._
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.core.logging.db.LoggingInterceptor
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
        em.detach(actionProperty)
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

    ap.setCreatePerson(userData.user)
    ap.setCreateDatetime(now)
    ap.setModifyPerson(userData.user)
    ap.setModifyDatetime(now)

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

  def setActionPropertyValue(ap: ActionProperty, value: String) = {
    val apvs = getActionPropertyValue(ap)

    apvs.size match {
      case 0 => {
        // Если не нашли значение, то создаем новое
        val apv = createActionPropertyValue(ap, value)
        apv
      }
      case size => {
        val apv = apvs.get(0)
        apv.unwrap.setValueFromString(value)
        apv
      }
    }
  }

  def createActionPropertyValue(ap: ActionProperty, value: String) = {
    val cls = ap.getValueClass
    cls match {
      case null => {
        throw new CoreException(i18n("error.actionPropertyTypeClassNotFound").format(ap.getId))
      }
      case _ => {}
    }
    val apv = cls.newInstance.asInstanceOf[APValue]
    // Устанваливаем id, index
    apv.linkToActionProperty(ap)
    // Записываем значение
    apv.setValueFromString(value)
    apv
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
}
