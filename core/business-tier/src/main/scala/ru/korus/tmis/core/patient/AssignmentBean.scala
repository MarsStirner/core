package ru.korus.tmis.core.patient

import grizzled.slf4j.Logging
import javax.ejb.{EJB, Stateless}
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.interceptor.Interceptors
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.auth.AuthData
import ru.korus.tmis.core.data._
import ru.korus.tmis.core.database._
import common.{DbActionPropertyTypeBeanLocal, DbActionPropertyBeanLocal, DbManagerBeanLocal, DbActionBeanLocal}
import javax.inject.Inject
import java.util.{LinkedList, Date}
import ru.korus.tmis.core.entity.model._
import ru.korus.tmis.core.exception.CoreException
import scala.collection.JavaConversions._
import collection.mutable
import javax.enterprise.inject.Any
import ru.korus.tmis.scala.util.{I18nable, ConfigManager}
import scala.language.reflectiveCalls

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class AssignmentBean extends AssignmentBeanLocal
with Logging
with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  @EJB
  var appLock: AppLockBeanLocal = _

  @EJB
  private var actionBean: DbActionBeanLocal = _

  @EJB
  private var actionPropertyBean: DbActionPropertyBeanLocal = _

  @EJB
  private var actionPropertyTypeBean: DbActionPropertyTypeBeanLocal = _

  @EJB
  private var dbRbCoreActionPropertyBean: DbRbCoreActionPropertyBeanLocal = _

  @EJB
  private var dbManager: DbManagerBeanLocal = _

  private class IndexOf[T](seq: Seq[T]) {
    def unapply(pos: T) = seq find (pos == _) map (seq indexOf _)
  }

  final val list = List(ConfigManager.Messages("db.actionPropertyType.assignment.name.injectionMethod").toInt,
    ConfigManager.Messages("db.actionPropertyType.assignment.name.source").toInt,
    ConfigManager.Messages("db.actionPropertyType.assignment.name.description").toInt,
    ConfigManager.Messages("db.actionPropertyType.assignment.name.injectionSpeed").toInt,
    ConfigManager.Messages("db.actionPropertyType.assignment.name.dropOilierNumber").toInt,
    ConfigManager.Messages("db.actionPropertyType.assignment.name.dosage").toInt,
    ConfigManager.Messages("db.actionPropertyType.assignment.name.quantity").toInt,
    ConfigManager.Messages("db.actionPropertyType.assignment.name.units").toInt,
    ConfigManager.Messages("db.actionPropertyType.assignment.name.assignmentScheme").toInt,
    ConfigManager.Messages("db.actionPropertyType.assignment.name.medicationBegin").toInt,
    ConfigManager.Messages("db.actionPropertyType.assignment.name.medicationEnd").toInt,
    ConfigManager.Messages("db.actionPropertyType.assignment.name.weekDays").toInt,
    ConfigManager.Messages("db.actionPropertyType.assignment.name.hospitalizationDays").toInt,
    ConfigManager.Messages("db.actionPropertyType.assignment.name.allDays").toInt,
    ConfigManager.Messages("db.actionPropertyType.assignment.name.afterDays").toInt,
    ConfigManager.Messages("db.actionPropertyType.assignment.name.onceDaily").toInt,
    ConfigManager.Messages("db.actionPropertyType.assignment.name.medicationTimes").toInt,
    ConfigManager.Messages("db.actionPropertyType.assignment.name.admissibleChange").toInt,
    ConfigManager.Messages("db.actionPropertyType.assignment.name.medicationMethod").toInt,
    ConfigManager.Messages("db.actionPropertyType.assignment.name.paymentType").toInt,
    ConfigManager.Messages("db.actionPropertyType.assignment.name.departmentHead").toInt,
    ConfigManager.Messages("db.actionPropertyType.assignment.name.note").toInt)

  def insertAssignmentForPatient(assignmentData: AssignmentData, eventId: Int, authData: AuthData) = {

    //var entities = Set.empty[AnyRef]
    //val now = new Date()
    val flgCreate = if (assignmentData.data.id > 0) false else true
    //1. Проверка данных на валидность

    //2. Action

    var oldAction: Action = null
    var oldValues = Map.empty[ActionProperty, java.util.List[APValue]]
    var lockId: Int = -1

    val temp: Action = if (!flgCreate) actionBean.getActionById(assignmentData.data.id) else null

    var action: Action = null
    var list = List.empty[AnyRef]

    if (!flgCreate) {
      oldAction = Action.clone(temp)
      oldValues = actionPropertyBean.getActionPropertiesByActionId(oldAction.getId.intValue).toMap
      lockId = appLock.acquireLock("Action", temp.getId.intValue(), oldAction.getIdx, authData)
    }

    try {

      if (temp == null) {
        action = actionBean.createAction(eventId,
          assignmentData.data.assignmentType.getId.intValue(),
          authData)
        em.persist(action)
        //dbManager.persist(action)
        list = actionPropertyTypeBean.getActionPropertyTypesByActionTypeId(assignmentData.data.assignmentType.getId.intValue()).toList
      }
      else {
        action = actionBean.updateAction(temp.getId.intValue(),
          temp.getVersion.intValue,
          authData)
        em.merge(action)
        list = actionPropertyBean.getActionPropertiesByActionId(temp.getId.intValue).keySet.toList
      }

      action.setIsUrgent(assignmentData.data.getUrgent)
      if (assignmentData.data.rangeDateTime.getStart != null)
        action.setBegDate(assignmentData.data.rangeDateTime.getStart)
      if (assignmentData.data.rangeDateTime.getEnd != null)
        action.setEndDate(assignmentData.data.rangeDateTime.getEnd)

      //3. Action Property

      list.foreach(f => {
        val ap: ActionProperty =
          if (flgCreate) {
            val res = actionPropertyBean.createActionProperty(action, f.asInstanceOf[ActionPropertyType].getId.intValue(), authData)
            em.persist(res)
            //dbManager.persist(res)
            res
          }
          else {
            val res = actionPropertyBean.updateActionProperty(f.asInstanceOf[ActionProperty].getId.intValue,
              f.asInstanceOf[ActionProperty].getVersion.intValue,
              authData)
            em.merge(res)
          }

        val values = this.getValueByCase(ap.getType.getId.intValue(), assignmentData, authData)
        values.size match {
          case 0 => {
            if (flgCreate) {
              //В случае, если на приходит значение для ActionProperty, то записываем значение по умолчанию.
              val defValue = ap.getType.getDefaultValue
              if (defValue != null && !defValue.trim.isEmpty) {
                val apv = actionPropertyBean.setActionPropertyValue(ap, defValue, 0)
                if (apv != null)
                  em.merge(apv.unwrap) //entities = entities + apv.unwrap
              }
            } else null //Если не пришло новое значее, старое не трогаем
          }
          case _ => {
            if (ap.getType.getIsVector) {
              //Если вектор, то сперва зачищаем старый список
              val apvs = actionPropertyBean.getActionPropertyValue(ap)
              if (apvs != null && apvs.size() > values.size) {
                //dbManager.removeAll(apvs)
                for (i <- values.size to apvs.size - 1) {
                  //если новых значений меньше тем старых, то хвост зачистим
                  var apv = apvs(i).unwrap()
                  apv = em.merge(apv)
                  em.remove(apv)
                }
              }
            }
            var it = 0
            values.foreach(value => {
              val apv = actionPropertyBean.setActionPropertyValue(ap, value, it)
              if (apv != null)
                em.merge(apv.unwrap) //entities = entities + apv.unwrap
              it = it + 1
            })
          }
        }
      })
      em.flush()
      em.detach(action)
    }
    finally {
      if (lockId > 0) appLock.releaseLock(lockId)
    }

    action
  }

  def getAssignmentById(actionId: Int) = {

    val action = actionBean.getActionById(actionId)
    var values = actionPropertyBean.getActionPropertiesByActionId(actionId)
    //Таблица соответствия id
    val corrMap = new java.util.HashMap[String, java.util.List[RbCoreActionProperty]]()
    corrMap.put(i18n("db.actionType.assignment").toString, dbRbCoreActionPropertyBean.getRbCoreActionPropertiesByActionTypeId(i18n("db.actionType.assignment").toInt))

    new AssignmentData(action, values, corrMap, null)
  }

  private def AnyToSetOfString(that: AnyRef): Set[String] = {
    if (that == null)
      return Set.empty[String]

    if (that.isInstanceOf[java.util.LinkedList[_]]) {
      var set = Set.empty[String]
      that.asInstanceOf[java.util.LinkedList[_]].foreach(e => {
        if (e.isInstanceOf[Date])
          set = set + ConfigManager.DateFormatter.format(e)
        else
          set = set + e.toString
      })
      return set
    }
    else if (that.isInstanceOf[FlatDirectoryContainer]) {
      return Set(that.asInstanceOf[FlatDirectoryContainer].getId().toString)
    }
    else if (that.isInstanceOf[RlsContainer]) {
      return Set(that.asInstanceOf[RlsContainer].getId().toString)
    }
    else if (that.isInstanceOf[PersonIdNameContainer]) {
      return Set(that.asInstanceOf[PersonIdNameContainer].getId().toString)
    }
    else if (that.isInstanceOf[Date]) {
      return Set(ConfigManager.DateFormatter.format(that))
    }
    else {
      try {
        return Set(that.toString)
      }
      catch {
        case e: Exception => {
          throw new CoreException("Не могу преобразовать данные типа: %s в строковый массив".format(that.getClass.getName))
        }
      }
    }
  }

  private def getValueByCase(aptId: Int, assignmentData: AssignmentData, authData: AuthData) = {

    val listNdx = new IndexOf(list)
    val cap = dbRbCoreActionPropertyBean.getRbCoreActionPropertiesByActionPropertyTypeId(aptId)

    if (cap != null) {
      cap.getId.intValue() match {
        case listNdx(0) => this.AnyToSetOfString(assignmentData.data.injectionMethod) //Метод введения
        case listNdx(1) => this.AnyToSetOfString(assignmentData.data.source) //Источник
        case listNdx(2) => this.AnyToSetOfString(assignmentData.data.description) //Наименование
        case listNdx(3) => this.AnyToSetOfString(assignmentData.data.injectionSpeed) //Скорость введения
        case listNdx(4) => this.AnyToSetOfString(Integer.valueOf(assignmentData.data.dropOilierNumber)) //Номер капельницы
        case listNdx(5) => this.AnyToSetOfString(assignmentData.data.dosage) //Доза
        case listNdx(6) => this.AnyToSetOfString(Integer.valueOf(assignmentData.data.quantity)) //Количество
        case listNdx(7) => this.AnyToSetOfString(assignmentData.data.units) //Единицы
        case listNdx(8) => this.AnyToSetOfString(assignmentData.data.assignmentScheme) //Схема назначения
        case listNdx(9) => this.AnyToSetOfString(assignmentData.data.medication.start) //Начало приема
        case listNdx(10) => this.AnyToSetOfString(assignmentData.data.medication.end) //Конец приема
        case listNdx(11) => this.AnyToSetOfString(assignmentData.data.weekDays) //Дни недели
        case listNdx(12) => this.AnyToSetOfString(assignmentData.data.hospitalizationDays) //Дни госпитализации
        case listNdx(13) => this.AnyToSetOfString(Integer.valueOf(assignmentData.data.allDays)) //Дней
        case listNdx(14) => this.AnyToSetOfString(Integer.valueOf(assignmentData.data.afterDays)) //Через дней
        case listNdx(15) => this.AnyToSetOfString(Integer.valueOf(assignmentData.data.onceDaily)) //Раз в день
        case listNdx(16) => this.AnyToSetOfString(assignmentData.data.medicationTimes) //Время приема
        case listNdx(17) => this.AnyToSetOfString(assignmentData.data.admissibleChange) //Допустимость замены
        case listNdx(18) => this.AnyToSetOfString(assignmentData.data.medicationMethod) //Способ приема
        case listNdx(19) => this.AnyToSetOfString(assignmentData.data.paymentType) //Вид оплаты
        case listNdx(20) => this.AnyToSetOfString(assignmentData.data.departmentHead) //Заведующий отделением
        case listNdx(21) => this.AnyToSetOfString(assignmentData.data.note) //Комментарий
        case _ => this.AnyToSetOfString(null)
      }
    } else this.AnyToSetOfString(null)
  }

}
