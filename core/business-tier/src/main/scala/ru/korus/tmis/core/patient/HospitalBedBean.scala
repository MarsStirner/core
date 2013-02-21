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
  private var actionTypeBean: DbActionTypeBeanLocal = _

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

  private val list2 = List(iCapIds("db.rbCAP.hosp.primary.id.sentTo").toInt)

  private val unknownOperation = 0
  private val directionInDepartment = 1    //Направление в отделение
  private val movingInDepartment = 2       //Перевод в отделение


  def registryPatientToHospitalBed(eventId: Int, hbData: HospitalBedData, authData: AuthData): Action = {

    if(!this.verificationData(eventId, -1, hbData, 0)) return null

    //val actionType = actionTypeBean.getActionTypeByCode("4202") //Движение

    //Инициализируем новый action
    val action: Action = actionBean.createAction(eventId.intValue(),
                                                 i18n("db.actionType.moving").toInt,
                                                 //actionType.getId.intValue(),
                                                 authData)
    action.setBegDate(hbData.data.bedRegistration.moveDatetime)
    action.setEndDate(null:java.util.Date)
    dbManager.persist(action)

    if(hbData.data.bedRegistration!=null) {

      val propertyValueSet = new HashSet[APValue]
      val listNdx = new IndexOf(list)
      val cap = dbRbCoreActionPropertyBean.getRbCoreActionPropertiesByActionTypeId(i18n("db.actionType.moving").toInt)

      cap.foreach((coreAPT) => {
        coreAPT.getId.intValue() match {
            case listNdx(0) => {   //Переведен из отделения
              propertyValueSet += this.createActionPropertyWithValue(action, coreAPT.getActionPropertyType.getId.intValue(), Integer.valueOf(hbData.data.bedRegistration.movedFromUnitId), authData)
            }
            case listNdx(1) => {   //Время поступления
            val formatter: DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
              propertyValueSet += this.createActionPropertyWithValue(action, coreAPT.getActionPropertyType.getId.intValue(), formatter.format(hbData.data.bedRegistration.moveDatetime), authData)
            }
            case listNdx(2) => {   //Отделение пребывания
              if(hbData.data.bedRegistration.bedId>0){ // достанем из профиля койки
              val department = dbOrgStructureBean.getOrgStructureByHospitalBedId(hbData.data.bedRegistration.bedId.intValue())
                if(department!=null)
                  propertyValueSet += this.createActionPropertyWithValue(action, coreAPT.getActionPropertyType.getId.intValue(), department.getId, authData)
                else
                  propertyValueSet += this.createActionPropertyWithValue(action, coreAPT.getActionPropertyType.getId.intValue(), null, authData)
              }
            }
            case listNdx(3) => {   //койка
              propertyValueSet += this.createActionPropertyWithValue(action, coreAPT.getActionPropertyType.getId.intValue(), Integer.valueOf(hbData.data.bedRegistration.bedId), authData)
            }
            case listNdx(4) => {    //Патронаж
              propertyValueSet += this.createActionPropertyWithValue(action, coreAPT.getActionPropertyType.getId.intValue(), hbData.data.bedRegistration.patronage, authData)
            }
            case _ => {
              propertyValueSet += this.createActionPropertyWithValue(action, coreAPT.getActionPropertyType.getId.intValue(), null, authData)
            }

          }
      })

      dbManager.persistAll(propertyValueSet)
      propertyValueSet.clear()
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

    //Направление в отделение, если для данного эвента нету экшна 4202, в противном случае перевод в отделение
    var flgOption: Int  = this.unknownOperation
    var actionList = actionBean.getActionsByTypeCodeAndEventId(JavaConversions.asJavaSet(Set("4202")), eventId, "a.createDatetime desc", authData)
    if(actionList!=null) {      //Перевод в отделение
      flgOption = this.movingInDepartment
    }
    else {                      //Направление в отделение
      actionList = actionBean.getActionsByTypeCodeAndEventId(JavaConversions.asJavaSet(Set("4201")), eventId, "a.createDatetime desc", authData)
      if(actionList!=null) {
        flgOption = this.directionInDepartment
      }
      else {
        throw new CoreException("Невозможно направить пациента в отделение. \nДля госпитализации с id=%s не найден первичный осмотр".format(eventId.toString))
        return null
      }
    }

    val oldAction =  Action.clone(actionList.get(0))
    val from: java.lang.Integer =
      if(flgOption == this.movingInDepartment)
          this.getLastDepartmentLocationByAction(actionList.get(0).getId.intValue()/*hbData.data.move.clientId.intValue*/)
      else null
    val oldValues = actionPropertyBean.getActionPropertiesByActionId(oldAction.getId.intValue)
    val lockId = appLock.acquireLock("Action", actionList.get(0).getId.intValue(), oldAction.getIdx, authData)

    var result = List[Action]()
    var entities = Set.empty[AnyRef]

    try {
      val action = actionBean.updateAction (actionList.get(0).getId.intValue(),
                                            oldAction.getVersion.intValue,
                                            authData)
      action.setStatus(ActionStatus.FINISHED.getCode)  //Добавлено в спеку по переводам
      action.setBegDate(oldAction.getBegDate)
      if (flgOption == this.directionInDepartment)
        action.setEndDate(oldAction.getEndDate)
      else {
        if(hbData.data.move.moveDatetime!=null)
          action.setEndDate(hbData.data.move.moveDatetime)
        else
          action.setEndDate(new Date())
      }

      entities = entities + action
      result = action :: result

      if(hbData.data.move!=null) {
        oldValues.foreach(f => {

          val ap = actionPropertyBean.updateActionProperty( f._1.getId.intValue,
                                                            f._1.getVersion.intValue,
                                                            authData)
          entities = entities + ap
          val cap = dbRbCoreActionPropertyBean.getRbCoreActionPropertiesByActionPropertyTypeId(ap.getType.getId.intValue())
          flgOption match {
            case this.movingInDepartment => {
              val listNdx = new IndexOf(list)
              cap.getId.intValue() match {
                case listNdx(0) => {    //Переведен из отделения
                  val apv = actionPropertyBean.setActionPropertyValue(ap, from.toString, 0)
                  entities = entities + apv.unwrap
                }
                case listNdx(5) => {   //Время выбытия
                  val formatter: DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    val apv = actionPropertyBean.setActionPropertyValue(ap, formatter.format(hbData.data.move.moveDatetime), 0)
                    entities = entities + apv.unwrap
                }
                case listNdx(6) => {   //Переведен в отделение
                  val apv = actionPropertyBean.setActionPropertyValue(ap, hbData.data.move.unitId.toString, 0)
                    entities = entities + apv.unwrap
                }
                case _ =>  null
              }
            }
            case this.directionInDepartment => {
              val listNdx = new IndexOf(list2)
              cap.getId.intValue() match {
                case listNdx(0) => {    //Направлен в отделение
                  if(hbData.data.move.unitId>0){
                    val apv = actionPropertyBean.setActionPropertyValue(ap, hbData.data.move.unitId.toString, 0)
                    entities = entities + apv.unwrap
                  }
                }
                case _ => null
              }
            }
            case _ => {
              throw new CoreException("Невозможно направить пациента в отделение. \nДля госпитализации с id=%s не найден первичный осмотр".format(eventId.toString))
              return null
            }
          }
        })
      }

      result = dbManager.mergeAll(entities).filter(result.contains(_)).map(_.asInstanceOf[Action]).toList
      val r = dbManager.detachAll[Action](result).toList
      /*
      r.foreach(newAction => {
        val newValues = actionPropertyBean.getActionPropertiesByActionId(newAction.getId.intValue)
        val hren = new ModifyActionNotification(null,       //oldAction
          null, //oldValues
          null, //newAction
          null)//newValues
        actionEvent.fire(hren)
      })
      */
      return r.get(0)
    }
    finally {
      appLock.releaseLock(lockId)
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
                                                                           iCapIds("db.rbCAP.moving.id.bed")),
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

    val apv_map = actionPropertyBean.getActionPropertiesByActionId(action.getId.intValue)

    //Таблица соответствия id
    val corrMap = new java.util.HashMap[String, java.util.List[RbCoreActionProperty]]()
    corrMap.put(i18n("db.actionType.moving").toString, dbRbCoreActionPropertyBean.getRbCoreActionPropertiesByActionTypeId(i18n("db.actionType.moving").toInt))
    corrMap.put(i18n("db.actionType.hospitalization.primary").toString, dbRbCoreActionPropertyBean.getRbCoreActionPropertiesByActionTypeId(i18n("db.actionType.hospitalization.primary").toInt))

    new HospitalBedData(action, apv_map, null, corrMap, null)
  }

  def getRegistryFormWithChamberList(action: Action, authData: AuthData) = {

    if (action.getActionType.getCode.compareTo("4202")!=0){
      throw new CoreException("Action c id = %s не является действием 'Движение'".format(action.getId.toString))
      null
    }
    else {
      val apv_map = actionPropertyBean.getActionPropertiesByActionId(action.getId.intValue)

      val core = dbRbCoreActionPropertyBean.getRbCoreActionPropertiesByActionTypeId(i18n("db.actionType.moving").toInt)
      val listNdx = new IndexOf(list)
      var departmentId: Int = - 1
      val result =
        if (action.getEndDate==null){
          core.find(element => element.getName == i18n("db.actionPropertyType.moving.name.located").toString)
        }
        else {
          core.find(element => element.getName == i18n("db.actionPropertyType.moving.name.movedIn").toString)
        }
      val res = result.getOrElse(null)
      if(res!=null){
        val result2 = apv_map.find {element => element._1.getType.getId.intValue() == res.getActionPropertyType.getId}
        val res2 = result2.getOrElse(null)
        if(res2!=null){
            departmentId = res2._2.get(0).asInstanceOf[APValueOrgStructure].getValue.getId.intValue()
        }
      }

      if (departmentId<0){
        throw new CoreException("Для Action c id = %s не удалось найти отделение, где находится пациент".format(action.getId.toString))
        null
      }
      else {
        //Список коек отделения
        val beds = this.getCaseHospitalBedsByDepartmentId(departmentId)
        //Таблица соответствия id
        val corrMap = new java.util.HashMap[String, java.util.List[RbCoreActionProperty]]()
        corrMap.put(i18n("db.actionType.moving").toString, dbRbCoreActionPropertyBean.getRbCoreActionPropertiesByActionTypeId(i18n("db.actionType.moving").toInt))

        new HospitalBedData(action, apv_map, beds, corrMap, null)
      }
    }
  }

  def getMovingListByEventIdAndFilter(filter: AnyRef, authData: AuthData): HospitalBedData = {
    if(filter.isInstanceOf[HospitalBedDataListFilter]) {
      val map = new java.util.LinkedHashMap[Action, java.util.Map[ActionProperty, java.util.List[APValue]]]

      //Список всех экшенов
      val actionList = actionBean.getActionsByTypeCodeAndEventId(JavaConversions.asJavaSet(Set("4201","4202")),
                                                            filter.asInstanceOf[HospitalBedDataListFilter].eventId,
                                                            filter.asInstanceOf[HospitalBedDataListFilter].toSortingString("") + " asc",
                                                            authData)
      //Таблица соответствия id
      val corrMap = new java.util.HashMap[String, java.util.List[RbCoreActionProperty]]()
      corrMap.put(i18n("db.actionType.hospitalization.primary").toString, dbRbCoreActionPropertyBean.getRbCoreActionPropertiesByActionTypeId(i18n("db.actionType.hospitalization.primary").toInt))
      corrMap.put(i18n("db.actionType.moving").toString, dbRbCoreActionPropertyBean.getRbCoreActionPropertiesByActionTypeId(i18n("db.actionType.moving").toInt))

      if (actionList!=null) {
        actionList.foreach(action=>{
          val apv_map = actionPropertyBean.getActionPropertiesByActionId(action.getId.intValue)
          map.put(action, apv_map)
        })
      } else {
        logTmis.setLoggerType(logTmis.LoggingTypes.Debug)
        logTmis.warning("Не найдено ни одного действия для выбранного обращения. ")
        //TODO: Варнинг, что не найдена экшны для этого обращения    Добавить коды ошибок.
      }
      return new HospitalBedData(map, corrMap, null)
    }
    else {
      throw new CoreException("Ошибка при получении списка движения пациента. Некорректный фильтр")
      return null
    }
  }

  def callOffHospitalBedForPatient(actionId: Int, authData: AuthData): Boolean = {

    val oldAction =  Action.clone(actionBean.getActionById(actionId))
    //0. Проверяем тип действия
    if (oldAction.getActionType.getCode.compareTo("4202")!=0){
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

      val listMovAP = JavaConversions.asJavaList(List(
                                                    iCapIds("db.rbCAP.moving.id.bed").toInt: java.lang.Integer,
                                                    iCapIds("db.rbCAP.moving.id.movedIn").toInt: java.lang.Integer,
                                                    iCapIds("db.rbCAP.moving.id.endTime").toInt: java.lang.Integer
                                                 ))
      try {

        //Редактируем старое действие
        val action =  actionBean.getActionById(oldLastAction.getId.intValue())
        action.setModifyPerson(authData.user)
        action.setModifyDatetime(new Date())
        action.setVersion(oldLastAction.getVersion.intValue)

        //Редактируем значения свойств действия
        val oldLastValues = actionPropertyBean.getActionPropertiesByActionIdAndRbCoreActionPropertyIds(oldLastAction.getId.intValue,
                                                                                                       listMovAP)

        val coreAPs = dbRbCoreActionPropertyBean.getRbCoreActionPropertiesByIds(listMovAP)

        val lstCoreAP = coreAPs.filter(p=>p.getId.compareTo(iCapIds("db.rbCAP.moving.id.bed").toInt: java.lang.Integer)==0).toList
        val coreAP = if(lstCoreAP!=null&&lstCoreAP.size>0)lstCoreAP.get(0) else null

        if (oldLastValues!=null && coreAP!=null) {
          val result = oldLastValues.find(p=> p._1.getType.getId.intValue()==coreAP.getActionPropertyType.getId.intValue()).getOrElse(null)
          if (result!=null && result._2!=0 && result._2.size()>0) {
            bed = result._2.get(0).getValue.asInstanceOf[OrgStructureHospitalBed]
            val result2 = em.createQuery(BusyHospitalBedsByDepartmentIdQuery.format(i18n("db.action.movingFlatCode"),
                                                                                    iCapIds("db.rbCAP.moving.id.bed")),
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
            val lstCoreAPForEdit = coreAPs.filter(p=>p.getId.compareTo(iCapIds("db.rbCAP.moving.id.movedIn").toInt: java.lang.Integer)==0 ||
                                                     p.getId.compareTo(iCapIds("db.rbCAP.moving.id.endTime").toInt: java.lang.Integer)==0).toList

            lstCoreAPForEdit.foreach(cap => {
              val ap_values = oldLastValues.find(p=> p._1.getType.getId.intValue()==cap.getActionPropertyType.getId.intValue()).getOrElse(null)
              if(ap_values!=null){

                //Обновим АР
                val ap = actionPropertyBean.updateActionProperty( ap_values._1.getId.intValue,
                                                                  ap_values._1.getVersion.intValue,
                                                                  authData)
                entities = entities + ap

                //Обновим APVs
                if (ap_values._2!=0 && ap_values._2.size()>0) {
                  flgEdit match {
                    case 1 => { //Редактируем значения "Переведен в" и "Время выбытия"
                      if (cap.getId.compareTo(iCapIds("db.rbCAP.moving.id.movedIn").toInt: java.lang.Integer)==0) {
                        val apv = actionPropertyBean.setActionPropertyValue(ap_values._1, bed.getMasterDepartment.getId.toString, 0)
                        entities = entities + apv.unwrap
                      }
                      else if (cap.getId.compareTo(iCapIds("db.rbCAP.moving.id.endTime").toInt: java.lang.Integer)==0) {
                        val now = new Date()
                        val formatter: DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                        val apv = actionPropertyBean.setActionPropertyValue(ap_values._1, formatter.format(now), 0)
                        entities = entities + apv.unwrap
                        action.setEndDate(now)
                      }
                      //apvs_merged = ap_values._2.toList ::: apvs_merged
                    }
                    case 2 => { //Затираем значения "Переведен в" и "Время выбытия"
                      action.setEndDate(null)
                      apvs_removed = ap_values._2.toList ::: apvs_removed
                    }
                    case _ => {}
                  }
                }
              }
            })
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

  private def getLastMovingActionForEventId(eventId: Int) = {
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
        apt.id IN (
          SELECT cap.actionPropertyType.id
          FROM RbCoreActionProperty cap
          WHERE
            cap.id = '%s'
        )
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
