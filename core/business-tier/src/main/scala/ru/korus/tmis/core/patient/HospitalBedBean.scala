package ru.korus.tmis.core.patient

import ru.korus.tmis.util.{CAPids, ConfigManager, I18nable}
import grizzled.slf4j.Logging
import javax.ejb.{TransactionAttributeType, TransactionAttribute, EJB, Stateless}
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.interceptor.Interceptors
import javax.persistence.{PersistenceContext, EntityManager}
import ru.korus.tmis.core.data.{HospitalBedDataListFilter, HospitalBedData}
import ru.korus.tmis.core.auth.AuthData
import ru.korus.tmis.core.entity.model._
import ru.korus.tmis.core.database._
import ru.korus.tmis.core.exception.CoreException
import collection.mutable.HashSet
import scala.collection.JavaConversions._
import java.text.{SimpleDateFormat, DateFormat}
import ru.korus.tmis.core.event.{Notification, ModifyActionNotification}
import javax.inject.Inject
import javax.enterprise.event.Event
import javax.enterprise.inject.Any
import collection.{mutable, JavaConversions}
import ru.korus.tmis.core.common.CommonDataProcessorBeanLocal
import java.util.Date
import org.slf4j.LoggerFactory
import ru.korus.tmis.util.reflect.TmisLogging
import org.apache.commons.collections.{CollectionUtils, Transformer}

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class HospitalBedBean extends HospitalBedBeanLocal
with Logging
with I18nable
with CAPids
with TmisLogging{

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  @EJB
  var appLock: AppLockBeanLocal = _

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

  @Inject
  @Any
  var actionEvent: Event[Notification] = _

  private class IndexOf[T] (seq: Seq[T]) {
    def unapply(pos: T) = seq find (pos ==) map (seq indexOf _)
  }

  private val list = List(iCapIds("db.rbCAP.moving.id.movedFrom").toInt,
                          iCapIds("db.rbCAP.moving.id.beginTime").toInt,
                          iCapIds("db.rbCAP.moving.id.located").toInt,
                          iCapIds("db.rbCAP.moving.id.bed").toInt,
                          iCapIds("db.rbCAP.moving.id.patronage").toInt,
                          iCapIds("db.rbCAP.moving.id.endTime").toInt,
                          iCapIds("db.rbCAP.moving.id.movedIn").toInt)

  private val unknownOperation = 0
  private val directionInDepartment = 1    //Направление в отделение
  private val movingInDepartment = 2       //Перевод в отделение


  def registryPatientToHospitalBed(eventId: Int, hbData: HospitalBedData, authData: AuthData): Action = {

    if(!this.verificationData(eventId, -1, hbData, 0)) return null

    var date: Date = null
    //Последний action (moving or received)
    val filter = new HospitalBedDataListFilter(eventId)
    val lastActions = actionBean.getActionsWithFilter(0, 0, filter.toSortingString("createDatetime", "desc"), filter.unwrap, null, authData)
    val lastAction: Action = if(lastActions!=null && lastActions.size()>0) lastActions.get(0) else null

    if(hbData.data.bedRegistration!=null && hbData.data.bedRegistration.moveDatetime!=null)
      date = hbData.data.bedRegistration.moveDatetime
    else if (lastAction!=null)//ищем дату выписки в последнем Action
      date = lastAction.getEndDate

    if(date==null) {
      throw new CoreException("Регистрация пациента невозможна. \nНе заданы дата и время поступления")
      return null
    }

    //Инициализируем новый action
    val action: Action = actionBean.createAction(eventId.intValue(),
                                                 i18n("db.actionType.moving").toInt,
                                                 authData)
    action.setBegDate(date)
    action.setEndDate(null:java.util.Date)
    dbManager.persist(action)

    if(hbData.data.bedRegistration!=null) {

      var entities = Set.empty[AnyRef]
      val types = actionPropertyTypeBean.getActionPropertyTypesByActionTypeId(i18n("db.actionType.moving").toInt)
      types.foreach(apt => {
        val code = apt.getCode

        var value: AnyRef = null
        if(code.compareTo(ConfigManager.Messages("db.apt.moving.codes.hospOrgStruct"))==0){
          if(hbData.data.bedRegistration.bedId>0){ // достанем из профиля койки
            val department = dbOrgStructureBean.getOrgStructureByHospitalBedId(hbData.data.bedRegistration.bedId.intValue())
            if(department!=null) value = department.getId else null
          }
          if(value==null) { //берем значение по умолчанию - отделение, куда направили
            val codes = Set[String](ConfigManager.Messages("db.apt.moving.codes.orgStructTransfer"))
            val lastProperties = actionPropertyBean.getActionPropertiesByActionIdAndActionPropertyTypeCodes(lastAction.getId.intValue, codes)
            if(lastProperties!=null && lastProperties.size()>0){
              val apv = lastProperties.iterator.next()._2
              if(apv!=null && apv.size()>0)
                value = apv.get(0).asInstanceOf[APValueOrgStructure].getValue.getId
            }
          }
        }
        else if(code.compareTo(ConfigManager.Messages("db.apt.moving.codes.timeArrival"))==0){
          val formatter: DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

          if(hbData.data.bedRegistration.moveDatetime!=null) {
            value = formatter.format(hbData.data.bedRegistration.moveDatetime)
          }
          else { //берем значение по умолчанию из предыдущего действия
            if (lastAction.getActionType.getFlatCode.compareTo(ConfigManager.Messages("db.action.admissionFlatCode"))==0) {
              value = formatter.format(lastAction.getBegDate)  //TODO: getBegDate???
            }
            else if(lastAction.getActionType.getFlatCode.compareTo(ConfigManager.Messages("db.action.movingFlatCode"))==0){
              val codes = Set[String](ConfigManager.Messages("db.apt.moving.codes.timeLeaved"))
              val lastProperties = actionPropertyBean.getActionPropertiesByActionIdAndActionPropertyTypeCodes(lastAction.getId.intValue, codes)
              if(lastProperties!=null && lastProperties.size()>0){
                val apv = lastProperties.iterator.next()._2
                if(apv!=null && apv.size()>0)
                  value = formatter.format(apv.get(0).asInstanceOf[APValueTime].getValue)
              }
            }
          }
        }
        else if(code.compareTo(ConfigManager.Messages("db.apt.moving.codes.patronage"))==0){
          value = hbData.data.bedRegistration.patronage
        }
        else if(code.compareTo(ConfigManager.Messages("db.apt.moving.codes.hospitalBed"))==0){
          value = if(hbData.data.bedRegistration.bedId>0)
            Integer.valueOf(hbData.data.bedRegistration.bedId)
          else null
        }

        val apv = this.createActionPropertyWithValue(action, apt.getId.intValue(), value, authData)
        if(apv!=null) entities += apv
      })

      dbManager.persistAll(entities)
    }
    action
  }

  def modifyPatientToHospitalBed(actionId: Int, hbData: HospitalBedData, authData: AuthData): Action = {

    if(!this.verificationData(-1, actionId, hbData, 1)) return null

    val oldAction = Action.clone(actionBean.getActionById(actionId))
    val oldValues = actionPropertyBean.getActionPropertiesByActionId(oldAction.getId.intValue)
    val lockId = appLock.acquireLock("Action", actionId, oldAction.getIdx, authData)

    var result = List[Action]()
    var entities = Set.empty[AnyRef]

    try {
      val action = actionBean.updateAction(actionId,
                                           oldAction.getVersion.intValue,
                                           authData)
      action.setBegDate(hbData.data.bedRegistration.moveDatetime)
      action.setEndDate(null:java.util.Date)

      entities = entities + action
      result = action :: result

      if(hbData.data.bedRegistration!=null) {
        oldValues.foreach(f => {

          val ap = actionPropertyBean.updateActionProperty( f._1.getId.intValue,
                                                            f._1.getVersion.intValue,
                                                            authData)
          entities = entities + ap

          val listNdx = new IndexOf(list)
          val cap = dbRbCoreActionPropertyBean.getRbCoreActionPropertiesByActionPropertyTypeId(ap.getType.getId.intValue())

          cap.getId.intValue() match {
            case listNdx(0) => {    //Переведен из отделения
              if(hbData.data.bedRegistration.movedFromUnitId>0){
                val apv = actionPropertyBean.setActionPropertyValue(ap, hbData.data.bedRegistration.movedFromUnitId.toString, 0)
                entities = entities + apv.unwrap
              }
            }
            case listNdx(1) => {   //Время поступления
            val formatter: DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
              if(hbData.data.bedRegistration.moveDatetime!=null){
                val apv = actionPropertyBean.setActionPropertyValue(ap, formatter.format(hbData.data.bedRegistration.moveDatetime), 0)
                entities = entities + apv.unwrap
              }
            }
            case listNdx(2) => {   //Отделение пребывания
              if(hbData.data.bedRegistration.bedId>0){ // достанем из профиля койки
              val department = dbOrgStructureBean.getOrgStructureByHospitalBedId(hbData.data.bedRegistration.bedId.intValue())
                if(department!=null){
                  val apv = actionPropertyBean.setActionPropertyValue(ap, department.getId.toString, 0)
                  entities = entities + apv.unwrap
                }
              }
            }
            case listNdx(3) => {   //койка
              if(hbData.data.bedRegistration.bedId>0){
                val apv = actionPropertyBean.setActionPropertyValue(ap, hbData.data.bedRegistration.bedId.toString, 0)
                entities = entities + apv.unwrap
              }
            }
            case listNdx(4) => {    //Патронаж
              if(hbData.data.bedRegistration.patronage!=null){
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
      val r = dbManager.detachAll[Action](result).toList
      /*
      r.foreach(newAction => {
      val newValues = actionPropertyBean.getActionPropertiesByActionId(newAction.getId.intValue)
      actionEvent.fire(new ModifyActionNotification(oldAction,
                                                    oldValues,
                                                    newAction,
                                                    newValues))
      })
      */
      return r.get(0)
    }
    finally {
      appLock.releaseLock(lockId)
    }
  }

  //Движение
  def movingPatientToDepartment(eventId: Int, hbData: HospitalBedData, authData: AuthData): Action = {

    if(!this.verificationData(eventId, -1, hbData, 0)) return null

    val filter = new HospitalBedDataListFilter(eventId)
    val actions = actionBean.getActionsWithFilter(0, 0, filter.toSortingString("createDatetime", "desc"), filter.unwrap, null, authData)
    if (actions!=null && actions.size()>0){

      var action = actions.get(0)
      var result = List[Action]()
      var entities = Set.empty[AnyRef]
      val date = if (hbData.data.move!=null && hbData.data.move.moveDatetime!=null) hbData.data.move.moveDatetime
                 else new Date

      val flgOption =
        if (action.getActionType.getFlatCode.compareTo(i18n("db.action.movingFlatCode"))==0)
          this.movingInDepartment
        else if (action.getActionType.getFlatCode.compareTo(i18n("db.action.admissionFlatCode"))==0)
          this.directionInDepartment
        else
          this.unknownOperation

      if (flgOption==this.unknownOperation) {
        throw new CoreException("Невозможно направить/перевести пациента в отделение. \nДля госпитализации с id=%s не найден осмотр при госпитализации".format(eventId.toString))
        return null
      }

      val codes = flgOption match {
        case this.directionInDepartment => Set[String](ConfigManager.Messages("db.apt.received.codes.orgStructDirection"))
        case this.movingInDepartment =>  Set[String](ConfigManager.Messages("db.apt.moving.codes.orgStructTransfer"),
                                                     ConfigManager.Messages("db.apt.moving.codes.timeLeaved"))
        case _ => Set.empty[String]
      }

      val oldAction =  Action.clone(action)
      val oldValues = actionPropertyBean.getActionPropertiesByActionIdAndActionPropertyTypeCodes(oldAction.getId.intValue, codes)
      val lockId = appLock.acquireLock("Action", action.getId.intValue(), oldAction.getIdx, authData)

      try{
          //Action
          action = actionBean.updateAction(action.getId.intValue(),
                                           oldAction.getVersion.intValue,
                                           authData)

          action.setBegDate(oldAction.getBegDate)
          if (flgOption==movingInDepartment) {     //Движение
            action.setStatus(ActionStatus.FINISHED.getCode)
            action.setEndDate(date)
          }

          entities = entities + action
          result = action :: result

          //ActionProperty
          if(hbData.data.move!=null) {
            oldValues.foreach(f => {
              val ap = actionPropertyBean.updateActionProperty( f._1.getId.intValue,
                                                                f._1.getVersion.intValue,
                                                                authData)
              entities = entities + ap

              val code = ap.getType.getCode
              if (flgOption==movingInDepartment) {
                if(code.compareTo(ConfigManager.Messages("db.apt.moving.codes.orgStructTransfer"))==0  && hbData.data.move.unitId>0){
                  val apv = actionPropertyBean.setActionPropertyValue(ap, hbData.data.move.unitId.toString, 0)
                  entities = entities + apv.unwrap
                }
                else if(code.compareTo(ConfigManager.Messages("db.apt.moving.codes.timeLeaved"))==0){
                  val formatter: DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                  val apv = actionPropertyBean.setActionPropertyValue(ap, formatter.format(date), 0)
                  entities = entities + apv.unwrap
                }
              }
              else if(flgOption==directionInDepartment) {
                if(code.compareTo(ConfigManager.Messages("db.apt.received.codes.orgStructDirection"))==0 && hbData.data.move.unitId>0) {
                  val apv = actionPropertyBean.setActionPropertyValue(ap, hbData.data.move.unitId.toString, 0)
                  entities = entities + apv.unwrap
                }
              }
            })
          }
          result = dbManager.mergeAll(entities).filter(result.contains(_)).map(_.asInstanceOf[Action]).toList
          val r = dbManager.detachAll[Action](result).toList
          return r.get(0)
      }
      finally {
        appLock.releaseLock(lockId)
      }
    }
    else {
      throw new CoreException("Невозможно направить/перевести пациента в отделение. \nДля госпитализации с id=%s не найден осмотр при госпитализации".format(eventId.toString))
      return null
    }
  }

  //Запрос на занятые койки в отделении
  def getCaseHospitalBedsByDepartmentId(departmentId: Int) = {

    val allBeds = em.createQuery(AllHospitalBedsByDepartmentIdQuery, classOf[OrgStructureHospitalBed])
                .setParameter("departmentId", departmentId)
                .getResultList

    val ids = CollectionUtils.collect(allBeds, new Transformer() {
       def transform(orgBed: Object) = orgBed.asInstanceOf[OrgStructureHospitalBed].getId
    })

    val result = em.createQuery(BusyHospitalBedsByDepartmentIdQuery.format(i18n("db.action.movingFlatCode"),
                                                                           i18n("db.apt.moving.codes.hospitalBed")),
                                classOf[OrgStructureHospitalBed])
      .setParameter("ids", asJavaCollection(ids))
      .getResultList

    val map = new java.util.LinkedHashMap[OrgStructureHospitalBed, java.lang.Boolean]()
    allBeds.foreach(allBed => {
      val res = result.find(bed => allBed.getId.intValue()==bed.getId.intValue())
      if(res==None) map.put(allBed, false)
      else map.put(allBed, true)
    })
    result.foreach(em.detach(_))
    map
  }

  def getRegistryOriginalForm(action: Action, authData: AuthData) = {
    new HospitalBedData(action,
                        actionPropertyBean.getActionPropertiesByActionIdAndActionPropertyTypeCodes _,
                        null,
                        null)
  }

  def getRegistryFormWithChamberList(action: Action, authData: AuthData) = {
    new HospitalBedData(action,
                        actionPropertyBean.getActionPropertiesByActionIdAndActionPropertyTypeCodes _,
                        this.getCaseHospitalBedsByDepartmentId _,
                        null)
  }

  def getMovingListByEventIdAndFilter(filter: HospitalBedDataListFilter, authData: AuthData): HospitalBedData = {
      val actions = actionBean.getActionsWithFilter(0, 0, filter.toSortingString("createDatetime", "asc"), filter.unwrap, null, authData)
      return new HospitalBedData(actions,
                                 actionPropertyBean.getActionPropertiesByActionIdAndActionPropertyTypeCodes _,
                                 null)
  }

  def callOffHospitalBedForPatient(actionId: Int, authData: AuthData): Boolean = {

    val oldAction =  Action.clone(actionBean.getActionById(actionId))
    //0. Проверяем тип действия

    if (oldAction.getActionType.getFlatCode.compareTo(i18n("db.action.movingFlatCode"))!=0) {
      throw new CoreException("Action c id = %s не является действием с типом 'Движение'".format(actionId.toString))
    }

    //1. Отменяем текущее действие
    //val oldValues = actionPropertyBean.getActionPropertiesByActionId(oldAction.getId.intValue)
    val lockId = appLock.acquireLock("Action", actionId, oldAction.getIdx, authData)

    try {
      val action =  actionBean.getActionById(actionId)
      //Помечаем действие как "Удалено"
      action.setModifyPerson(authData.user)
      action.setModifyDatetime(new Date())
      action.setVersion(oldAction.getVersion.intValue)
      action.setDeleted(true)
      dbManager.merge(action)
      dbManager.detach(action)

      //создаем акшен del_moving
      val del_movingAction = actionBean.createAction(action.getEvent.getId.intValue(), 3871, authData)
      del_movingAction.setDeleted(true)
      del_movingAction.setParentActionId(action.getId.intValue())
      dbManager.persist(del_movingAction)

      /*
      val newValues = actionPropertyBean.getActionPropertiesByActionId(action.getId.intValue)
      actionEvent.fire( new ModifyActionNotification(oldAction,
                        oldValues,
                        action,
                        newValues))
      */
    }
    finally {
      appLock.releaseLock(lockId)
    }

    //2. Ищем последнее движение, находим старую койку и регистрируем на нее
    val temp = this.getLastMovingActionForEventId(oldAction.getEvent.getId.intValue())
    if (temp!=null) {
      val oldLastAction =  Action.clone(temp)
      val lockLastId = appLock.acquireLock("Action", oldLastAction.getId.intValue(), oldLastAction.getIdx, authData)

      //var flgBusyBed = false
      var resultA = List[Action]()
      var entities = Set.empty[AnyRef]
      var apvs_removed = List[APValue]()
      //var apvs_merged = List[APValue]()
      var bed: OrgStructureHospitalBed = null
      var flgEdit: Short = 0 //0 - оставляем как есть, 1 - редактируем значения, 2 - удаляем значения

      val listMovAP = JavaConversions.asJavaSet(Set(
                                                    i18n("db.apt.moving.codes.hospitalBed"),
                                                    i18n("db.apt.moving.codes.orgStructTransfer"),
                                                    i18n("db.apt.moving.codes.timeLeaved")
                                                 ))
      try {

        //Редактируем старое действие
        val action =  actionBean.getActionById(oldLastAction.getId.intValue())
        action.setModifyPerson(authData.user)
        action.setModifyDatetime(new Date())
        action.setVersion(oldLastAction.getVersion.intValue)

        //Редактируем значения свойств действия
        val oldLastValues = actionPropertyBean.getActionPropertiesByActionIdAndActionPropertyTypeCodes(oldLastAction.getId.intValue,
                                                                                                       listMovAP)
        if (oldLastValues!=null) {
          val result = oldLastValues.find(p=> p._1.getType.getCode.compareTo(i18n("db.apt.moving.codes.hospitalBed"))==0).getOrElse(null)
          if (result!=null && result._2!=0 && result._2.size()>0) {
            bed = result._2.get(0).getValue.asInstanceOf[OrgStructureHospitalBed]
            val result2 = em.createQuery(BusyHospitalBedsByDepartmentIdQuery.format(i18n("db.action.movingFlatCode"),
                                                                                    i18n("db.apt.moving.codes.hospitalBed")),
                                                                                    classOf[OrgStructureHospitalBed])
                                                                                    .setParameter("ids", asJavaCollection(Set(bed.getId.intValue())))
                                                                                    .getResultList
            result2.foreach(em.detach(_))
            if(result2!=null && result2.size()>0) { //Койка уже занята, инициируем перевод в это же отделение (Редактируем значения Переведен в и Время выбытия)
              flgEdit = 1
            }
            else { //Койка свободна - кладем на койку (Затираем значения Переведен в и время выбытия)
              flgEdit = 2
            }
          }

          if (flgEdit>0){
            val ap_values = oldLastValues.filter(p=> p._1.getType.getCode.compareTo(i18n("db.apt.moving.codes.orgStructTransfer"))==0 ||
                                                     p._1.getType.getCode.compareTo(i18n("db.apt.moving.codes.timeLeaved"))==0).toList

            if(ap_values!=null){
              ap_values.foreach(ap_val => {
                //Обновим АР
                val ap = actionPropertyBean.updateActionProperty( ap_val._1.getId.intValue,
                                                                  ap_val._1.getVersion.intValue,
                                                                  authData)
                entities = entities + ap

                //Обновим APVs
                if (ap_val._2!=0 && ap_val._2.size()>0) {
                  flgEdit match {
                    case 1 => { //Редактируем значения "Переведен в" и "Время выбытия"
                      if (ap_val._1.getType.getCode.compareTo(i18n("db.apt.moving.codes.orgStructTransfer"))==0) {
                        val apv = actionPropertyBean.setActionPropertyValue(ap_val._1, bed.getMasterDepartment.getId.toString, 0)
                        entities = entities + apv.unwrap
                      }
                      else if (ap_val._1.getType.getCode.compareTo(i18n("db.apt.moving.codes.timeLeaved"))==0) {
                        val now = new Date()
                        val formatter: DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        val apv = actionPropertyBean.setActionPropertyValue(ap_val._1, formatter.format(now), 0)
                        entities = entities + apv.unwrap
                        action.setEndDate(now)
                      }
                      //apvs_merged = ap_values._2.toList ::: apvs_merged
                    }
                    case 2 => { //Затираем значения "Переведен в" и "Время выбытия"
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
        val r = dbManager.detachAll[Action](resultA).toList

        /*
        r.foreach(newAction => {
          val newValues = actionPropertyBean.getActionPropertiesByActionId(newAction.getId.intValue)
          actionEvent.fire(new ModifyActionNotification(oldLastAction,
            oldLastValues,
            newAction,
            newValues))
        })
        */
      }
      finally {
        appLock.releaseLock(lockLastId)
      }
    }
    return true
  }

  /////////Внутренние методы/////////

  @throws(classOf[CoreException])
  private def verificationData(eventId: Int, actionId: Int, hbData: HospitalBedData, flgParent:Int): Boolean = {

    if (hbData==null){
      throw new CoreException("Некорректные данные в HospitalBedData")
      return false
    }
    if(flgParent==0){
      if (eventBean.getEventById(eventId)==null){
        throw new CoreException("В таблице Event БД нету записи с заданным в запросе id")
        return false
      }
    } else if (flgParent==1){
      val action = actionBean.getActionById(actionId)
      if(action.getActionType.getCode.compareTo("4202")!=0){
        throw new CoreException("Некорректный Action id в запросе. Тип Action не 'Движение'")
        return false
      }
    }

    return true
  }

  private def createActionPropertyWithValue(action: Action, aptId: Int, value: AnyRef, authData: AuthData) : APValue = {

    var ap: ActionProperty = null
    try {
      ap = actionPropertyBean.createActionProperty(action, aptId, authData)
      dbManager.persist(ap)
    }
    catch {
      case e: Exception => {
        error("createActionPropertyWithValue >> Ошибка при создании новой записи в ActionProperty: %s".format(e.getMessage))
        throw new CoreException("Ошибка при создании записи в ActionProperty")
      }
      dbManager.removeAll(Set(ap))
    }

    if (value==null || (value.isInstanceOf[java.lang.Integer] && value.asInstanceOf[java.lang.Integer].intValue()<=0)) {
      return null
    }

    var valueStr:String = if(value.isInstanceOf[String]){value.asInstanceOf[String]} else {value.toString}
    try {
      if(ap!=null && valueStr!=null) {
        var n_ap = actionPropertyBean.setActionPropertyValue(ap, valueStr, 0)  //тип таблицы анализируется внутри
        return n_ap.asInstanceOf[APValue]
      }
    }
    catch {
      case e: Exception => {
        error("createActionPropertyWithValue >> Ошибка при записи значения ActionPropertyValue: %s".format(e.getMessage))
        throw new CoreException("Ошибка при записи значения ActionPropertyValue")
      }
    }
    null
  }

  private def getLastDepartmentLocationByAction(actionId: Int):java.lang.Integer = {
    var result = em.createQuery(LastDepartmentLocationByActionQuery, classOf[Int])
      .setParameter("id", actionId)
      .getResultList
    if(result!=null&&result.size()>0)
      Integer.valueOf(result.iterator().next.intValue())
    else
      null:java.lang.Integer
  }

  def getLastMovingActionForEventId(eventId: Int) = {
    val result = em.createQuery(LastMovingActionByEventIdQuery.format(i18n("db.action.movingFlatCode")),
                                classOf[Action])
      .setParameter("id", eventId)
      .getResultList

    result.size() match {
      case 0 => null
      case _ => {
        result.foreach(em.detach(_))
        result.iterator().next
      }
    }
  }

  val LastDepartmentLocationByActionQuery =
    """
    SELECT apv.value.id
    FROM
      ActionProperty ap
        JOIN ap.action a
        JOIN ap.actionPropertyType apt,
      APValueOrgStructure apv
    WHERE
      a.id = :id
    AND
      ap.id = apv.id.id
    AND
      apt.name = 'Отделение пребывания'
    AND
      a.deleted = 0
    AND
      apt.deleted = 0
    """

  val AllHospitalBedsByDepartmentIdQuery =
    """
    SELECT DISTINCT bed
    FROM
      OrgStructureHospitalBed bed
      WHERE
        bed.masterDepartment.id = :departmentId
    ORDER BY bed.id
    """


  val BusyHospitalBedsByDepartmentIdQuery =
    """
    SELECT DISTINCT bed
    FROM
      APValueHospitalBed apval
        JOIN apval.value bed,
      ActionProperty ap
        JOIN ap.actionPropertyType apt
        JOIN ap.action a
        JOIN a.actionType at
      WHERE
        apval.id.id = ap.id
      AND
        bed.id IN :ids
      AND
        at.flatCode = '%s'
      AND
        apt.code = '%s'
      AND
        a.endDate IS NULL
      AND
        a.deleted = 0
      AND
        ap.deleted = 0
      AND
        at.deleted = 0
      AND
        apt.deleted = 0
    """

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
    AND
      a.endDate IS NOT NULL
    ORDER BY a.endDate desc, a.id desc
    """
  /*val LastMovingActionByEventIdQuery =
    """
    SELECT a, COALESCE(max(a.endDate),0)
    FROM
      Action a
        JOIN a.actionType at
        JOIN a.event e
    WHERE
      e.id = :id
    AND
      at.flatCode = '%s'
    AND
      a.endDate IS NOT NULL
    AND
      a.deleted = 0
    AND
      at.deleted = 0
    """*/
}
