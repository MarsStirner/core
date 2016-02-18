package ru.korus.tmis.core.patient

import grizzled.slf4j.Logging
import javax.ejb.{EJB, Stateless}
import javax.interceptor.Interceptors
import javax.persistence.{PersistenceContext, EntityManager}
import ru.korus.tmis.core.data.{HospitalBedDataListFilter, HospitalBedData}
import ru.korus.tmis.core.auth.{AuthStorageBeanLocal, AuthData}
import ru.korus.tmis.core.entity.model._
import ru.korus.tmis.core.database._
import common._
import ru.korus.tmis.core.exception.CoreException
import scala.collection.JavaConversions._
import java.text.{SimpleDateFormat, DateFormat}
import javax.inject.Inject
import javax.enterprise.event.Event
import javax.enterprise.inject.Any
import collection.JavaConversions
import ru.korus.tmis.core.common.CommonDataProcessorBeanLocal
import java.util.Date
import ru.korus.tmis.scala.util.{CAPids, I18nable, ConfigManager}
import scala.language.reflectiveCalls

@Stateless
class HospitalBedBean extends HospitalBedBeanLocal
with Logging
with I18nable
with CAPids {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  @EJB
  var appLock: AuthStorageBeanLocal = _

  @EJB
  private var eventBean: DbEventBeanLocal = _

  @EJB
  private var actionBean: DbActionBeanLocal = _

  @EJB
  private var actionPropertyTypeBean: DbActionPropertyTypeBeanLocal = _

  @EJB
  private var actionPropertyBean: DbActionPropertyBeanLocal = _

  @EJB
  private var dbManager: DbManagerBeanLocal = _

  @EJB
  private var dbOrgStructureBean: DbOrgStructureBeanLocal = _

  @EJB
  private var dbRbCoreActionPropertyBean: DbRbCoreActionPropertyBeanLocal = _

  @EJB
  private var commonDataProcessor: CommonDataProcessorBeanLocal = _

  @EJB
  var dbEventPerson: DbEventPersonBeanLocal = _

  @EJB
  var dbOrgStructureHospitalBed: DbOrgStructureHospitalBedBeanLocal = _

  private class IndexOf[T](seq: Seq[T]) {
    def unapply(pos: T) = seq find (pos == _) map (seq indexOf _)
  }

  private val list = List(iCapIds("db.rbCAP.moving.id.movedFrom").toInt,
    iCapIds("db.rbCAP.moving.id.beginTime").toInt,
    iCapIds("db.rbCAP.moving.id.located").toInt,
    iCapIds("db.rbCAP.moving.id.bed").toInt,
    iCapIds("db.rbCAP.moving.id.patronage").toInt,
    iCapIds("db.rbCAP.moving.id.endTime").toInt,
    iCapIds("db.rbCAP.moving.id.movedIn").toInt)

  private val unknownOperation = 0
  private val directionInDepartment = 1
  //Направление в отделение
  private val movingInDepartment = 2 //Перевод в отделение


  def registryPatientToHospitalBed(eventId: Int, hbData: HospitalBedData, authData: AuthData, staff: Staff): Action = {

    if (!this.verificationData(eventId, -1, hbData, 0)) return null

    var date: Date = null
    //Последний action (moving or received)
    val filter = new HospitalBedDataListFilter(eventId)
    val lastActions = actionBean.getActionsWithFilter(0, 0, filter.toSortingString("createDatetime", "desc"), filter.unwrap, null)
    val lastAction: Action = if (lastActions != null && lastActions.size() > 0) lastActions.get(0) else null

    //if(hbData.data.bedRegistration!=null && hbData.data.bedRegistration.moveDatetime!=null)
    //  date = hbData.data.bedRegistration.moveDatetime
    //else
    if (lastAction != null) //ищем дату выписки в последнем Action
      date = new Date(lastAction.getEndDate.getTime + 1000)
    else date = new Date()

    if (date == null) {
      throw new CoreException("Регистрация пациента невозможна. \nНе заданы дата и время поступления")
    }

    //Инициализируем новый action
    val action: Action = actionBean.createAction(eventId.intValue(),
      i18n("db.actionType.moving").toInt, authData,
      staff)
    action.setBegDate(date)
    action.setEndDate(null: java.util.Date)
    dbManager.merge(action)

    if (hbData.data.bedRegistration != null) {

      var entities = Set.empty[AnyRef]
      val types = actionPropertyTypeBean.getActionPropertyTypesByActionTypeId(i18n("db.actionType.moving").toInt)
      types.foreach(apt => {
        val code = apt.getCode

        var value: AnyRef = null
        if (code.compareTo(ConfigManager.Messages("db.apt.moving.codes.hospOrgStruct")) == 0) {
          if (hbData.data.bedRegistration.bedId > 0) {
            // достанем из профиля койки
            val department = dbOrgStructureBean.getOrgStructureByHospitalBedId(hbData.data.bedRegistration.bedId.intValue())
            if (department != null) value = department.getId else null
          }
          if (value == null) {
            //берем значение по умолчанию - отделение, куда направили
            val codes = Set[String](ConfigManager.Messages("db.apt.moving.codes.orgStructTransfer"))
            val lastProperties = actionPropertyBean.getActionPropertiesByActionIdAndActionPropertyTypeCodes(lastAction.getId.intValue, codes)
            if (lastProperties != null && lastProperties.size() > 0) {
              val apv = lastProperties.iterator.next()._2
              if (apv != null && apv.size() > 0)
                value = apv.get(0).asInstanceOf[APValueOrgStructure].getValue.getId
            }
          }
        }
        else if (code.compareTo(ConfigManager.Messages("db.apt.moving.codes.timeArrival")) == 0) {
          val formatter: DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

          if (hbData.data.bedRegistration.moveDatetime != null) {
            value = formatter.format(hbData.data.bedRegistration.moveDatetime)
          }
          else {
            //берем значение по умолчанию из предыдущего действия
            if (lastAction.getActionType.getFlatCode.compareTo(ConfigManager.Messages("db.action.admissionFlatCode")) == 0) {
              value = formatter.format(lastAction.getEndDate) //TODO: getBegDate???
            }
            else if (lastAction.getActionType.getFlatCode.compareTo(ConfigManager.Messages("db.action.movingFlatCode")) == 0) {
              val codes = Set[String](ConfigManager.Messages("db.apt.moving.codes.timeLeaved"))
              val lastProperties = actionPropertyBean.getActionPropertiesByActionIdAndActionPropertyTypeCodes(lastAction.getId.intValue, codes)
              if (lastProperties != null && lastProperties.size() > 0) {
                val apv = lastProperties.iterator.next()._2
                if (apv != null && apv.size() > 0)
                  value = formatter.format(apv.get(0).asInstanceOf[APValueTime].getValue)
              }
            }
          }
        }
        else if (code.compareTo(ConfigManager.Messages("db.apt.moving.codes.patronage")) == 0) {
          value = hbData.data.bedRegistration.patronage
        }
        else if (code.compareTo(ConfigManager.Messages("db.apt.moving.codes.hospitalBed")) == 0) {
          value = if (hbData.data.bedRegistration.bedId > 0)
            Integer.valueOf(hbData.data.bedRegistration.bedId)
          else null
        }
        else if (code.compareTo(ConfigManager.Messages("db.apt.moving.codes.hospitalBedProfile")) == 0) {
          value = if (hbData.getData.getBedRegistration.getBedProfileId > 0)
            Integer.valueOf(hbData.getData.getBedRegistration.getBedProfileId)
          else null
          //TODO Implement writing hospital bed profile
        }

        else if (code.compareTo(ConfigManager.Messages("db.apt.moving.codes.orgStructReceived")) == 0) {
          if (hbData.data.bedRegistration.movedFromUnitId > 0)
            value = Integer.valueOf(hbData.data.bedRegistration.movedFromUnitId)
          else {
            //берем значение по умолчанию из предыдущего действия
            if (
              lastAction.getActionType.getFlatCode.compareTo(ConfigManager.Messages("db.action.movingFlatCode")) == 0 ||
                lastAction.getActionType.getFlatCode.compareTo(ConfigManager.Messages("db.action.admissionFlatCode")) == 0
            ) {
              val codes = Set[String](ConfigManager.Messages("db.apt.moving.codes.hospOrgStruct"))
              val lastProperties = actionPropertyBean.getActionPropertiesByActionIdAndActionPropertyTypeCodes(lastAction.getId.intValue, codes)
              if (lastProperties != null && lastProperties.size() > 0) {
                val apv = lastProperties.iterator.next()._2
                if (apv != null && apv.size > 0 && apv.get(0).asInstanceOf[APValueOrgStructure].getValue != null)
                  value = apv.get(0).asInstanceOf[APValueOrgStructure].getValue.getId
              }
            }
          }
        }

        val apv = this.createActionPropertyWithValue(action, apt.getId.intValue(), value, staff)
        if (apv != null) entities += apv
      })
      dbManager.persistAll(entities)
      entities.foreach(dbManager.refresh(_))
      /** ** По доработанной спеке https://docs.google.com/document/d/1wkIKuMt3UQ5PMHVlsE2NVweE-n1zkfKyBQbTrjYwuRo/edit#
        * Пункт 3.3
        */
      var currentEvent = eventBean.getEventById(eventId)
      currentEvent.setExecutor(null)
      var currentEventPerson = dbEventPerson.getLastEventPersonForEventId(eventId)
      if (currentEventPerson != null) {
        currentEventPerson.setEndDate(new Date)
        em.merge(currentEventPerson)
      }
      em.merge(currentEvent)
      em.flush()
    }
    action
  }

  def modifyPatientToHospitalBed(actionId: Int, hbData: HospitalBedData, authData: AuthData, staff: Staff): Action = {

    if (!this.verificationData(-1, actionId, hbData, 1)) return null

    val oldAction = Action.clone(actionBean.getActionById(actionId))
    val oldValues = actionPropertyBean.getActionPropertiesByActionId(oldAction.getId.intValue)
    val lockId = appLock.acquireLock("Action", actionId, oldAction.getIdx, authData)

    var result = List[Action]()
    var entities = Set.empty[AnyRef]

    try {
      val action = actionBean.updateAction(actionId,
        oldAction.getVersion.intValue,
        authData,
        staff)
      action.setBegDate(hbData.data.bedRegistration.moveDatetime)
      action.setEndDate(null: java.util.Date)

      entities = entities + action
      result = action :: result

      if (hbData.data.bedRegistration != null) {
        oldValues.foreach(f => {

          val ap = actionPropertyBean.updateActionProperty(f._1.getId.intValue,
            f._1.getVersion.intValue,
            staff)
          entities = entities + ap

          val listNdx = new IndexOf(list)
          val cap = dbRbCoreActionPropertyBean.getRbCoreActionPropertiesByActionPropertyTypeId(ap.getType.getId.intValue())

          cap.getId.intValue() match {
            case listNdx(0) => {
              //Переведен из отделения
              if (hbData.data.bedRegistration.movedFromUnitId > 0) {
                val apv = actionPropertyBean.setActionPropertyValue(ap, hbData.data.bedRegistration.movedFromUnitId.toString, 0)
                entities = entities + apv.unwrap
              }
            }
            case listNdx(1) => {
              //Время поступления
              val formatter: DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
              if (hbData.data.bedRegistration.moveDatetime != null) {
                val apv = actionPropertyBean.setActionPropertyValue(ap, formatter.format(hbData.data.bedRegistration.moveDatetime), 0)
                entities = entities + apv.unwrap
              }
            }
            case listNdx(2) => {
              //Отделение пребывания
              if (hbData.data.bedRegistration.bedId > 0) {
                // достанем из профиля койки
                val department = dbOrgStructureBean.getOrgStructureByHospitalBedId(hbData.data.bedRegistration.bedId.intValue())
                if (department != null) {
                  val apv = actionPropertyBean.setActionPropertyValue(ap, department.getId.toString, 0)
                  entities = entities + apv.unwrap
                }
              }
            }
            case listNdx(3) => {
              //койка
              if (hbData.data.bedRegistration.bedId > 0) {
                val apv = actionPropertyBean.setActionPropertyValue(ap, hbData.data.bedRegistration.bedId.toString, 0)
                entities = entities + apv.unwrap
              }
            }
            case listNdx(4) => {
              //Патронаж
              if (hbData.data.bedRegistration.patronage != null) {
                val apv = actionPropertyBean.setActionPropertyValue(ap, hbData.data.bedRegistration.patronage.toString, 0)
                entities = entities + apv.unwrap
              }
            }
            case _ => {
              null
            }
          }
        })
      }
      result = dbManager.mergeAll(entities).filter(result.contains(_)).map(_.asInstanceOf[Action]).toList


      return result.get(0)
    }
    finally {
      appLock.releaseLock(lockId)
    }
  }

  //Движение
  def movingPatientToDepartment(eventId: Int, hbData: HospitalBedData, authData: AuthData, staff: Staff): Action = {

    if (!this.verificationData(eventId, -1, hbData, 0)) return null

    val filter = new HospitalBedDataListFilter(eventId)
    val actions = actionBean.getActionsWithFilter(0, 0, filter.toSortingString("createDatetime", "desc"), filter.unwrap, null)
    if (actions != null && actions.size() > 0) {

      var action = actions.get(0)
      var result = List[Action]()
      var entities = Set.empty[AnyRef]
      val date = if (hbData.data.move != null && hbData.data.move.moveDatetime != null) hbData.data.move.moveDatetime
      else new Date

      val flgOption =
        if (action.getActionType.getFlatCode.compareTo(i18n("db.action.movingFlatCode")) == 0)
          this.movingInDepartment
        else if (action.getActionType.getFlatCode.compareTo(i18n("db.action.admissionFlatCode")) == 0)
          this.directionInDepartment
        else
          this.unknownOperation

      if (flgOption == this.unknownOperation) {
        throw new CoreException("Невозможно направить/перевести пациента в отделение. \nДля госпитализации с id=%s не найден осмотр при госпитализации".format(eventId.toString))
        return null
      }

      val codes = flgOption match {
        case this.directionInDepartment => Set[String](ConfigManager.Messages("db.apt.received.codes.orgStructDirection"))
        case this.movingInDepartment => Set[String](ConfigManager.Messages("db.apt.moving.codes.orgStructTransfer"),
          ConfigManager.Messages("db.apt.moving.codes.timeLeaved"))
        case _ => Set.empty[String]
      }

      val oldAction = Action.clone(action)
      val oldValues = actionPropertyBean.getActionPropertiesByActionIdAndActionPropertyTypeCodes(oldAction.getId.intValue, codes)
      val lockId = appLock.acquireLock("Action", action.getId.intValue(), oldAction.getIdx, authData)

      try {
        //Action
        action = actionBean.updateAction(action.getId.intValue(),
          oldAction.getVersion.intValue,
          authData,
          staff)

        action.setBegDate(oldAction.getBegDate)
        if (flgOption == movingInDepartment) {
          //Движение
          action.setStatus(ActionStatus.FINISHED.getCode)
          action.setEndDate(date)
        }

        entities = entities + action
        result = action :: result

        //ActionProperty
        if (hbData.data.move != null) {
          oldValues.foreach(f => {
            val ap = actionPropertyBean.updateActionProperty(f._1.getId.intValue,
              f._1.getVersion.intValue,
              staff)
            entities = entities + ap

            val code = ap.getType.getCode
            if (flgOption == movingInDepartment) {
              if (code.compareTo(ConfigManager.Messages("db.apt.moving.codes.orgStructTransfer")) == 0 && hbData.data.move.unitId > 0) {
                val apv = actionPropertyBean.setActionPropertyValue(ap, hbData.data.move.unitId.toString, 0)
                entities = entities + apv.unwrap
              }
              else if (code.compareTo(ConfigManager.Messages("db.apt.moving.codes.timeLeaved")) == 0) {
                val formatter: DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val apv = actionPropertyBean.setActionPropertyValue(ap, formatter.format(date), 0)
                entities = entities + apv.unwrap
              }
            }
            else if (flgOption == directionInDepartment) {
              if (code.compareTo(ConfigManager.Messages("db.apt.received.codes.orgStructDirection")) == 0 && hbData.data.move.unitId > 0) {
                val apv = actionPropertyBean.setActionPropertyValue(ap, hbData.data.move.unitId.toString, 0)
                entities = entities + apv.unwrap
              }
            }
          })
        }
        result = dbManager.mergeAll(entities).filter(result.contains(_)).map(_.asInstanceOf[Action]).toList

        return result.get(0)
      }
      finally {
        appLock.releaseLock(lockId)
      }
    }
    else {
      throw new CoreException("Невозможно направить/перевести пациента в отделение. \nДля госпитализации с id=%s не найден осмотр при госпитализации".format(eventId.toString))
    }
  }

  //Запрос на занятые койки в отделении
  def getCaseHospitalBedsByDepartmentId(departmentId: Int) = {

    val allBeds = dbOrgStructureHospitalBed.getHospitalBedByDepartmentId(departmentId)

    /*val ids = CollectionUtils.collect(allBeds, new Transformer() {
       def transform(orgBed: Object) = orgBed.asInstanceOf[OrgStructureHospitalBed].getId
    }) */
    val ids = allBeds.map(f => f.getId)
    val result = dbOrgStructureHospitalBed.getBusyHospitalBedByIds(asJavaCollection(ids))

    val map = new java.util.LinkedHashMap[OrgStructureHospitalBed, java.lang.Boolean]()
    allBeds.foreach(allBed => {
      val res = result.find(bed => allBed.getId.intValue() == bed.getId.intValue())
      if (res == None) map.put(allBed, false)
      else map.put(allBed, true)
    })
    map
  }

  def getRegistryOriginalForm(action: Action) = {
    new HospitalBedData(action,
      actionPropertyBean.getActionPropertiesByActionIdAndActionPropertyTypeCodes _,
      null,
      null)
  }

  def getRegistryFormWithChamberList(action: Action) = {
    new HospitalBedData(action,
      actionPropertyBean.getActionPropertiesByActionIdAndActionPropertyTypeCodes _,
      this.getCaseHospitalBedsByDepartmentId _,
      null)
  }

  def getMovingListByEventIdAndFilter(filter: HospitalBedDataListFilter): HospitalBedData = {
    val actions = actionBean.getActionsWithFilter(0, 0, filter.toSortingString("createDatetime", "asc"), filter.unwrap, null)
    return new HospitalBedData(actions,
      actionPropertyBean.getActionPropertiesByActionIdAndActionPropertyTypeCodes _,
      null)
  }

  def callOffHospitalBedForPatient(actionId: Int, authData: AuthData, staff: Staff): Boolean = {

    val oldAction = Action.clone(actionBean.getActionById(actionId))
    //0. Проверяем тип действия

    if (oldAction.getActionType.getFlatCode.compareTo(i18n("db.action.movingFlatCode")) != 0) {
      throw new CoreException("Action c id = %s не является действием с типом 'Движение'".format(actionId.toString))
    }

    //1. Отменяем текущее действие
    //val oldValues = actionPropertyBean.getActionPropertiesByActionId(oldAction.getId.intValue)
    val action = actionBean.getActionById(actionId)
    //Помечаем действие как "Удалено"
    action.setModifyPerson(staff)
    action.setModifyDatetime(new Date())

    action.setDeleted(true)
    dbManager.merge(action)


    //создаем акшен del_moving
    val del_movingAction = actionBean.createAction(action.getEvent.getId.intValue(), 3871, authData, staff)
    del_movingAction.setDeleted(true)
    del_movingAction.setParentActionId(action.getId.intValue())
    dbManager.persist(del_movingAction)

    /** ** По доработанной спеке https://docs.google.com/document/d/1wkIKuMt3UQ5PMHVlsE2NVweE-n1zkfKyBQbTrjYwuRo/edit#
      * Пункт 3.3
      */
    var currentEvent = eventBean.getEventById(action.getEvent.getId.intValue())
    dbEventPerson.insertOrUpdateEventPerson(0, //id предыдущего евентПерсона. Здесь 0, потому что предыдущий ивентаПеросн всегда закрыт
      currentEvent,
      staff,
      false)

    //Изменим запись о назначевшем враче в ивенте
    currentEvent.setExecutor(staff)
    currentEvent.setModifyDatetime(new Date())
    currentEvent.setModifyPerson(staff)
    currentEvent.setVersion(currentEvent.getVersion)
    dbManager.merge(currentEvent)



    //2. Ищем последнее движение, находим старую койку и регистрируем на нее
    val temp = this.getLastCloseMovingActionForEventId(oldAction.getEvent.getId.intValue())
    if (temp != null) {
      val oldLastAction = Action.clone(temp)

      //var flgBusyBed = false
      var resultA = List[Action]()
      var entities = Set.empty[AnyRef]
      var apvs_removed = List[APValue]()
      //var apvs_merged = List[APValue]()
      var bed: OrgStructureHospitalBed = null
      var flgEdit: Short = 0 //0 - оставляем как есть, 1 - редактируем значения, 2 - удаляем значения

      val listMovAP = JavaConversions.setAsJavaSet(Set(
        i18n("db.apt.moving.codes.hospitalBed"),
        i18n("db.apt.moving.codes.orgStructTransfer"),
        i18n("db.apt.moving.codes.timeLeaved")
      ))


      //Редактируем старое действие
      val action = actionBean.getActionById(oldLastAction.getId.intValue())
      action.setModifyPerson(staff)
      action.setModifyDatetime(new Date())


      //Редактируем значения свойств действия
      val oldLastValues = actionPropertyBean.getActionPropertiesByActionIdAndActionPropertyTypeCodes(oldLastAction.getId.intValue,
        listMovAP)
      if (oldLastValues != null) {
        val result = oldLastValues.find(p => p._1.getType.getCode.compareTo(i18n("db.apt.moving.codes.hospitalBed")) == 0).getOrElse(null)
        if (result != null && result._2 != null && result._2.size() > 0) {
          bed = result._2.get(0).getValue.asInstanceOf[OrgStructureHospitalBed]
          val result2 = dbOrgStructureHospitalBed.getBusyHospitalBedByIds(asJavaCollection(asJavaCollection(Set(bed.getId))))
          if (result2 != null && result2.size() > 0) {
            //Койка уже занята, инициируем перевод в это же отделение (Редактируем значения Переведен в и Время выбытия)
            flgEdit = 1
          }
          else {
            //Койка свободна - кладем на койку (Затираем значения Переведен в и время выбытия)
            flgEdit = 2
          }
        }

        if (flgEdit > 0) {
          val ap_values = oldLastValues.filter(p => p._1.getType.getCode.compareTo(i18n("db.apt.moving.codes.orgStructTransfer")) == 0 ||
            p._1.getType.getCode.compareTo(i18n("db.apt.moving.codes.timeLeaved")) == 0).toList

          if (ap_values != null) {
            ap_values.foreach(ap_val => {
              //Обновим АР
              val ap = actionPropertyBean.updateActionProperty(ap_val._1.getId.intValue,
                ap_val._1.getVersion.intValue,
                staff)
              entities = entities + ap

              //Обновим APVs
              if (ap_val._2 != null && ap_val._2.size() > 0) {
                flgEdit match {
                  case 1 => {
                    //Редактируем значения "Переведен в" и "Время выбытия"
                    if (ap_val._1.getType.getCode.compareTo(i18n("db.apt.moving.codes.orgStructTransfer")) == 0) {
                      val apv = actionPropertyBean.setActionPropertyValue(ap_val._1, bed.getMasterDepartment.getId.toString, 0)
                      entities = entities + apv.unwrap
                    }
                    else if (ap_val._1.getType.getCode.compareTo(i18n("db.apt.moving.codes.timeLeaved")) == 0) {
                      val now = new Date()
                      val formatter: DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                      val apv = actionPropertyBean.setActionPropertyValue(ap_val._1, formatter.format(now), 0)
                      entities = entities + apv.unwrap
                      action.setEndDate(now)
                    }
                    //apvs_merged = ap_values._2.toList ::: apvs_merged
                  }
                  case 2 => {
                    //Затираем значения "Переведен в" и "Время выбытия"
                    action.setEndDate(null)
                    apvs_removed = ap_val._2.toList ::: apvs_removed
                  }
                  case _ => {}
                }
              }
            })
          }
        }
      }

      entities = entities + action
      resultA = action :: resultA

      dbManager.removeAll(apvs_removed)
      resultA = dbManager.mergeAll(entities).filter(resultA.contains(_)).map(_.asInstanceOf[Action]).toList


    }
    return true
  }

  /////////Внутренние методы/////////

  @throws(classOf[CoreException])
  def verificationData(eventId: Int, actionId: Int, hbData: HospitalBedData, flgParent: Int): Boolean = {

    if (hbData == null) {
      throw new CoreException("Некорректные данные в HospitalBedData")
      return false
    }
    if (flgParent == 0) {
      if (eventBean.getEventById(eventId) == null) {
        throw new CoreException("В таблице Event БД нету записи с заданным в запросе id")
        return false
      }
    } else if (flgParent == 1) {
      val action = actionBean.getActionById(actionId)
      if (action.getActionType.getCode.compareTo("4202") != 0) {
        throw new CoreException("Некорректный Action id в запросе. Тип Action не 'Движение'")
      }
    }

    return true
  }

  private def createActionPropertyWithValue(action: Action, aptId: Int, value: AnyRef, staff: Staff): APValue = {

    var ap: ActionProperty = null
    try {
      ap = actionPropertyBean.createActionProperty(action, aptId, staff)
      dbManager.persist(ap)
    }
    catch {
      case e: Exception => {
        error("createActionPropertyWithValue >> Ошибка при создании новой записи в ActionProperty: %s".format(e.getMessage))
        throw new CoreException("Ошибка при создании записи в ActionProperty", e)
      }
        dbManager.removeAll(Set(ap))
    }

    if (value == null || (value.isInstanceOf[java.lang.Integer] && value.asInstanceOf[java.lang.Integer].intValue() <= 0)) {
      return null
    }

    var valueStr: String = if (value.isInstanceOf[String]) {
      value.asInstanceOf[String]
    } else {
      value.toString
    }
    try {
      if (ap != null && valueStr != null) {
        var n_ap = actionPropertyBean.setActionPropertyValue(ap, valueStr, 0) //тип таблицы анализируется внутри
        return n_ap.asInstanceOf[APValue]
      }
    }
    catch {
      case e: Exception => {
        error("createActionPropertyWithValue >> Ошибка при записи значения ActionPropertyValue: %s".format(e.getMessage))
        throw new CoreException("Ошибка при записи значения ActionPropertyValue", e)
      }
    }
    null
  }

  def getLastCloseMovingActionForEventId(eventId: Int) = {
    this.getLastMovingActionByCondition(eventId, "AND a.endDate IS NOT NULL ORDER BY a.endDate desc, a.id desc")
  }

  def getLastMovingActionForEventId(eventId: Int) = {
    this.getLastMovingActionByCondition(eventId, "ORDER BY a.createDatetime desc")
  }

  /**
   * Получение отделения в котором находится госпитализированый пациент
   * (Когда нибудь код здесь надо будет поправить, надо посмотреть, где еще используются
   * методы и аккуратно поправить поиск не по движениям а по движениям и поступлениям в 1 запрос).
   * Если ты пришел править этот метод - то задача по рефакторингу ложится на тебя.
   * @param eventId Идентификатор госпитализации
   * @return Экземпляр класса, описывающего организационную структуру в БД
   */
  def getCurrentDepartmentForAppeal(eventId: Int) = {
    var department: OrgStructure = getCurrentDepartment(eventId)
    //Добавлено для совместимости с предыдущим поведением, когда если не было движений - возвращалось приемное отделение
    //Рекомендую убрать, когда точно будет ясно, что
    if (department == null)
      dbOrgStructureBean.getOrgStructureById(i18n("db.dayHospital.id").toInt)
    else
      department
  }


  def getCurrentDepartment(eventId: Int): OrgStructure = {
    // Текущее отделение пребывания пациента
    val moving = this.getLastMovingActionForEventId(eventId)
    var department: OrgStructure = null
    if (moving != null) {
      val bedValues = actionPropertyBean.getActionPropertiesByActionIdAndTypeCodes(moving.getId.intValue,
        JavaConversions.seqAsJavaList(List(i18n("db.apt.moving.codes.hospOrgStruct"))))
      if (bedValues != null && bedValues.size() > 0) {
        val values = bedValues.iterator.next()._2
        if (values != null && values.size() > 0) {
          department = values.get(0).getValue.asInstanceOf[OrgStructure]
        }
      }
      // Отсутствуют движения, смотрим поступления
    } else {
      val receiving = getReceivingActionByCondition(eventId, "ORDER BY a.createDatetime desc")
      if (receiving != null) {
        val bedValues = actionPropertyBean.getActionPropertiesByActionIdAndTypeCodes(receiving.getId.intValue, JavaConversions.seqAsJavaList(List(i18n("db.apt.moving.codes.hospOrgStruct"))))
        if (bedValues != null && bedValues.size() > 0) {
          val values = bedValues.iterator.next()._2
          if (values != null && values.size() > 0) {
            department = values.get(0).getValue.asInstanceOf[OrgStructure]
          }
        }
      }
    }
    department
  }

  private def getLastMovingActionByCondition(eventId: Int, condition: String) = {
    getActionByConditionAndCode(eventId, condition, i18n("db.action.movingFlatCode"))
  }

  private def getReceivingActionByCondition(eventId: Int, condition: String) = {
    getActionByConditionAndCode(eventId, condition, i18n("db.action.admissionFlatCode"))
  }

  private def getActionByConditionAndCode(eventId: Int, condition: String, code: String) = {
    val result = em.createQuery(LastMovingActionByEventIdQuery.format(code, condition),
      classOf[Action])
      .setParameter("id", eventId)
      .getResultList

    result.size() match {
      case 0 => null
      case _ => {

        result.iterator().next
      }
    }
  }

  val LastMovingActionByEventIdQuery =
    """
    SELECT a
    FROM
      Action a
        JOIN a.actionType at
        JOIN a.event e
    WHERE
      e.id = :id
    AND
      at.flatCode = '%s'
    AND
      a.deleted = 0
    AND
      at.deleted = 0
    %s
    """
}
