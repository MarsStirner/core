package ru.korus.tmis.core.patient

import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.interceptor.Interceptors
import grizzled.slf4j.Logging
import ru.korus.tmis.core.entity.model._
import ru.korus.tmis.core.data._
import javax.ejb.{EJB, Stateless}
import ru.korus.tmis.core.database._
import scala.collection.JavaConversions._
import ru.korus.tmis.core.auth.AuthData
import ru.korus.tmis.util.{ConfigManager, I18nable}
import javax.persistence.{PersistenceContext, EntityManager}
import java.util.{ArrayList, Date, LinkedList}
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.core.event.{Notification, ModifyActionNotification}
import javax.inject.Inject
import javax.enterprise.inject.Any
;

//import ru.korus.tmis.core.common.CommonDataProcessorBeanLocal
//import ru.korus.tmis.util.ConfigManager.APWI
//import org.json.{JSONArray, JSONObject}

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class AppealBean extends AppealBeanLocal
with Logging
with I18nable {

  //  val APPEALWI = ConfigManager.APPEALWI

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
  private var actionPropertyTypeBean: DbActionPropertyTypeBeanLocal = _

  @EJB
  private var rbCounterBean: DbRbCounterBeanLocal = _

  @EJB
  private var dbManager: DbManagerBeanLocal = _

  @EJB
  private var dbCustomQueryBean: DbCustomQueryLocal = _

  @EJB
  private var dbRbCoreActionPropertyBean: DbRbCoreActionPropertyBeanLocal = _

  @EJB
  private var dbMkbBean: DbMkbBeanLocal = _

  @Inject
  @Any
  var actionEvent: javax.enterprise.event.Event[Notification] = _

  private class IndexOf[T](seq: Seq[T]) {
    def unapply(pos: T) = seq find (pos ==) map (seq indexOf _)
  }

  val list = List(i18n("appeal.db.actionPropertyType.name.directed").toString, //Кем направлен
    i18n("appeal.db.actionPropertyType.name.number").toString, //№ направления
    i18n("appeal.db.actionPropertyType.name.deliveredType").toString, //Кем доставлен
    i18n("appeal.db.actionPropertyType.name.diagnosis.assigment.code").toString, //Диагноз направившего учреждения
    i18n("appeal.db.actionPropertyType.name.deliveredAfterType").toString, //Доставлен в стационар от начала заболевания
    i18n("appeal.db.actionPropertyType.name.brunchType").toString, //Направлен в отделение
    i18n("appeal.db.actionPropertyType.name.refuseAppealReason").toString, //Причина отказа в госпитализации
    i18n("appeal.db.actionPropertyType.name.appealWithDeseaseThisYear").toString, //Госпитализирован по поводу данного заболевания в текущем году
    i18n("appeal.db.actionPropertyType.name.transportationType").toString, //Вид транспортировки
    i18n("appeal.db.actionPropertyType.name.placeType").toString, //Профиль койки
    i18n("appeal.db.actionPropertyType.name.stateType").toString, //Доставлен в состоянии опьянения
    i18n("appeal.db.actionPropertyType.name.injury").toString, //Травма
    i18n("appeal.db.actionPropertyType.name.assignmentDate").toString, //Дата направления
    i18n("appeal.db.actionPropertyType.name.hospitalizationChannelType").toString, //Канал госпитализации
    i18n("appeal.db.actionPropertyType.name.doctor").toString, //Направивший врач
    i18n("appeal.db.actionPropertyType.name.diagnosis.assignment.description").toString, //Клиническое описание
    i18n("appeal.db.actionPropertyType.name.diagnosis.aftereffect.code").toString, //Диагноз направившего учреждения (осложнения)
    i18n("appeal.db.actionPropertyType.name.diagnosis.aftereffect.description").toString, //Клиническое описание (осложнения)
    i18n("appeal.db.actionPropertyType.name.ambulanceNumber").toString, //№ наряда СП
    i18n("appeal.db.actionPropertyType.name.bloodPressure.left").toString, //Артериальное давление. Левая рука
    i18n("appeal.db.actionPropertyType.name.bloodPressure.right").toString, //Артериальное давление. Правая рука
    i18n("appeal.db.actionPropertyType.name.temperature").toString, //t
    i18n("appeal.db.actionPropertyType.name.weight").toString, //Вес при поступлении
    i18n("appeal.db.actionPropertyType.name.height").toString, //Рост
    i18n("appeal.db.actionPropertyType.name.agreedType").toString, //Тип согласования
    i18n("appeal.db.actionPropertyType.name.agreedDoctor").toString, //Комментарий к согласованию
    i18n("appeal.db.actionPropertyType.name.hospitalizationWith").toString, //Законный представитель
    i18n("appeal.db.actionPropertyType.name.hospitalizationType").toString, //Тип госпитализации
    i18n("appeal.db.actionPropertyType.name.hospitalizationPointType").toString, //Цель госпитализации
    i18n("appeal.db.actionPropertyType.name.diagnosis.attendant.code").toString, //Диагноз направившего учреждения (сопутствующий)
    i18n("appeal.db.actionPropertyType.name.diagnosis.attendant.description").toString, //Клиническое описание (сопутствующий)
    i18n("db.actionPropertyType.moving.name.beginTime").toString, //Время поступления
    i18n("db.actionPropertyType.moving.name.bed").toString, //койка
    i18n("db.actionPropertyType.moving.name.endTime").toString, //Время выбытия
    i18n("db.actionPropertyType.moving.name.patronage").toString, //Патронаж
    i18n("db.actionPropertyType.moving.name.located").toString, //Отделение пребывания
    i18n("db.actionPropertyType.moving.name.movedIn").toString, //Переведен в отделение
    i18n("db.actionPropertyType.moving.name.movedFrom").toString) //Переведен из отделения

  //Insert or modify appeal
  def insertAppealForPatient(appealData: AppealData, patientId: Int, authData: AuthData) = {

    //1. Event и проверка данных на валидность

    var entities = Set.empty[AnyRef]
    val now = new Date()
    val flgCreate = if (appealData.data.id > 0) false else true

    var newEvent = this.verificationData(appealData.data.id, patientId, authData, appealData, flgCreate)
    if (flgCreate) {
      dbManager.persist(newEvent)
      dbManager.detach(newEvent)
    }

    //2. Action

    var oldAction: Action = null // Action.clone(temp)
    var oldValues = Map.empty[ActionProperty, java.util.List[APValue]] //actionPropertyBean.getActionPropertiesByActionId(oldAction.getId.intValue)
    var lockId: Int = -1 //appLock.acquireLock("Action", temp.getId.intValue(), oldAction.getIdx, authData)

    val temp = actionBean.getAppealActionByEventId(newEvent.getId.intValue(), "4201")
    var action: Action = null
    var list = List.empty[AnyRef]

    if (flgCreate) {
      //Обновим контейнер новыми данными
      appealData.data.setId(newEvent.getId.intValue())
      appealData.data.setNumber(newEvent.getExternalId)
    } else {
      oldAction = Action.clone(temp)
      oldValues = actionPropertyBean.getActionPropertiesByActionId(oldAction.getId.intValue).toMap
      lockId = appLock.acquireLock("Action", temp.getId.intValue(), oldAction.getIdx, authData)
    }

    try {

      if (temp == null) {
        action = actionBean.createAction(newEvent.getId.intValue(),
          actionTypeBean.getActionTypeByCode("4201").getId.intValue(),
          authData)
        em.persist(action)
        list = actionPropertyTypeBean.getActionPropertyTypeByActionTypeIdWithCode("4201").toList
      }
      else {
        action = actionBean.updateAction(temp.getId.intValue(),
          temp.getVersion.intValue,
          authData)
        list = actionPropertyBean.getActionPropertiesByActionId(temp.getId.intValue).keySet.toList
      }

      action.setIsUrgent(appealData.data.getUrgent)
      if (appealData.data.rangeAppealDateTime.getStart != null)
        action.setBegDate(appealData.data.rangeAppealDateTime.getStart)
      if (appealData.data.rangeAppealDateTime.getEnd != null)
        action.setEndDate(appealData.data.rangeAppealDateTime.getEnd)

      if (!flgCreate)
        entities = entities + action

      //3. Action Property

      list.foreach(f => {
        val ap: ActionProperty =
          if (flgCreate) {
            val res = actionPropertyBean.createActionProperty(action, f.asInstanceOf[ActionPropertyType].getId.intValue(), authData)
            em.persist(res)
            res
          }
          else {
            actionPropertyBean.updateActionProperty(f.asInstanceOf[ActionProperty].getId.intValue,
              f.asInstanceOf[ActionProperty].getVersion.intValue,
              authData)
          }
        if (!flgCreate)
          entities = entities + ap

        val values = this.getValueByCase(ap.getType.getId.intValue(), appealData, authData)
        values.size match {
          case 0 => {
            if (flgCreate) {
              //В случае, если на приходит значение для ActionProperty, то записываем значение по умолчанию.
              val defValue = ap.getType.getDefaultValue
              if (defValue != null && !defValue.trim.isEmpty) {
                val apv = actionPropertyBean.setActionPropertyValue(ap, defValue, 0)
                if (apv != null)
                  entities = entities + apv.unwrap
              }
            } else null
          }
          case _ => {
            var it = 0
            values.foreach(value => {
              val apv = actionPropertyBean.setActionPropertyValue(ap, value, it)
              if (apv != null)
                entities = entities + apv.unwrap
              it = it + 1
            })
          }
        }
      })

      if (!flgCreate) dbManager.mergeAll(entities) else dbManager.persistAll(entities)
      dbManager.detach(action)

      if (!flgCreate) {
        val newValues = actionPropertyBean.getActionPropertiesByActionId(action.getId.intValue)
        actionEvent.fire(new ModifyActionNotification(oldAction,
          oldValues,
          action,
          newValues))
      }
    }
    finally {
      if (lockId > 0) appLock.releaseLock(lockId)
    }

    if (!flgCreate) {
      //Редактирование обращения (В случае если изменен НИБ)
      var flgEventRewrite = false
      if (appealData.data.number != null &&
        !appealData.data.number.isEmpty &&
        newEvent.getExternalId.compareTo(appealData.data.number) != 0) {

        //проверка НИБ на уникальность
        if (this.checkAppealNumber(appealData.data.number) == false)
          throw new CoreException("Номер истории болезни в запросе (НИБ = %s) отличается от текущего (НИБ = %s) и не является уникальным".format(appealData.data.number, newEvent.getExternalId))

        newEvent.setExternalId(appealData.data.number)
        newEvent.setModifyDatetime(now)
        newEvent.setModifyPerson(authData.user)
        newEvent.setExecDate(now)

        flgEventRewrite = true
      }
      if (appealData.data.refuseAppealReason != null) {
        newEvent = this.revokeAppealById(newEvent, 15, authData)
        this.insertCompleteDiagnoses(appealData.data.id, authData)

        flgEventRewrite = true
      }
      if (flgEventRewrite == true) {
        newEvent.setVersion(newEvent.getVersion)
        dbManager.merge(newEvent)
      }
    }
    newEvent.getId.intValue()
  }

  def getAppealById(id: Int) = {
    //запрос  данных из Эвента
    var event = eventBean.getEventById(id)
    if (event == null) {
      throw new CoreException("Обращение с id=%d не найдено в БД".format(id))
    }
    //запрос данных из Action
    var action = actionBean.getAppealActionByEventId(event.getId.intValue(), "4201")
    if (action == null) {
      throw new CoreException("Первичный осмотр для обращения с id=%d не найден в БД".format(id))
    }
    //Запрос данных из ActionProperty
    var findMapActionProperty = actionPropertyBean.getActionPropertiesByActionId(action.getId.intValue())

    var values: java.util.Map[String, java.util.List[Object]] = findMapActionProperty.foldLeft(new java.util.HashMap[String, java.util.List[Object]])(
      (str_key, el) => {
        val (ap, apvs) = el
        val key = ap.getType.getName
        val list: java.util.List[Object] = new ArrayList[Object]
        if (apvs != null && apvs.size > 0) {
          apvs.foreach(apv => list += apv.getValue)
        }
        else
          list += null

        if (str_key.containsKey(key)) {
          list.addAll(str_key.get(key))
          str_key.remove(key)
        }
        str_key.put(key, list)
        str_key
      })

    val eventsMap = new java.util.HashMap[Event, java.util.Map[Action, java.util.Map[String, java.util.List[Object]]]]
    val actionsMap = new java.util.HashMap[Action, java.util.Map[String, java.util.List[Object]]]

    actionsMap.put(action, values)
    eventsMap.put(event, actionsMap)

    eventsMap
  }

  def getAllAppealsByPatient(requestData: AppealSimplifiedRequestData,
                             authData: AuthData) = {

    //TODO: подключить анализ авторизационных данных и доступных ролей
    requestData.setRecordsCount(dbCustomQueryBean.getCountOfAppealsWithFilter(requestData.filter))
    val map = dbCustomQueryBean.getAllAppealsWithFilter(requestData.page - 1, requestData.limit, requestData.sortingFieldInternal, requestData.sortingMethod, requestData.filter)
    new AppealSimplifiedDataList(map, requestData)
  }

  def getCountOfAppealsForReceivedPatientByPeriod(filter: Object) = {

    var queryStr: QueryDataStructure = if (filter.isInstanceOf[ReceivedRequestDataFilter]) {
      filter.asInstanceOf[ReceivedRequestDataFilter].toQueryStructure()
    }
    else {
      new QueryDataStructure()
    }

    var typed = em.createQuery(AllAppealsWithFilterQuery.format("count(e)", queryStr.query, ""), classOf[Long])

    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }

    typed.getSingleResult
  }

  def getAllAppealsForReceivedPatientByPeriod(page: Int, limit: Int, sortingField: String, sortingMethod: String, filter: Object) = {

    var queryStr: QueryDataStructure = if (filter.isInstanceOf[ReceivedRequestDataFilter]) {
      filter.asInstanceOf[ReceivedRequestDataFilter].toQueryStructure()
    }
    else {
      new QueryDataStructure()
    }

    val sorting = "ORDER BY %s %s".format(sortingField, sortingMethod)

    var typed = em.createQuery(AllAppealsWithFilterQuery.format("e, a", queryStr.query, sorting), classOf[Array[AnyRef]])
      .setMaxResults(limit)
      .setFirstResult(limit * page)

    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }

    val result = typed.getResultList

    val map = new java.util.LinkedHashMap[Event, Object]
    result.foreach(f => {
      map.put(f(0).asInstanceOf[Event],
        (
          f(1).asInstanceOf[Action],
          this.getDiagnosisListByActionId(f(1).asInstanceOf[Action].getId.intValue())
          )
      )
    })
    map
  }

  def checkAppealNumber(number: String) = {
    var isNumberFree = false
    val result = em.createQuery(AppealByExternalIdQuery, classOf[Event])
      .setParameter("externalId", number)
      .getResultList

    if (result == null || result.length < 1) {
      isNumberFree = true
    }
    isNumberFree
  }


  def revokeAppealById(event: Event, resultId: Int, authData: AuthData) = {

    //Закрываем госпитализацию с причиной отказа
    val now = new Date()
    if (event == null) {
      throw new CoreException("Не указано редактируемое обращение")
    }
    //TODO: Возможно надо прикрутить блокировку
    try {
      event.setModifyDatetime(now)
      event.setModifyPerson(authData.user)
      event.setExecDate(now)
      event.setResultId(resultId) //какой-то айдишник =)
    }
    catch {
      case e: Exception => {
        error("revokeAppealById >> Ошибка при закрытии госпитализации: %s".format(e.getMessage))
        throw new CoreException("Ошибка при закрытии госпитализации (id = %s)".format(event.getId.toString))
      }
    }
    finally {}
    event
  }

  //Внутренние методы

  @throws(classOf[CoreException])
  private def verificationData(eventId: Int, patientId: Int, authData: AuthData, appealData: AppealData, flgCreate: Boolean): Event = {

    if (authData == null) {
      throw new CoreException("Mетод для изменения обращения по госпитализации не доступен для неавторизованного пользователя.")
      return null
    }

    var event: Event = null
    if (flgCreate) {
      //Создаем новое
      if (patientId <= 0) {
        throw new CoreException("Невозможно открыть госпитализацию. Пациент не установлен.")
        return null
      }
      event = eventBean.createEvent(patientId,
        eventBean.getEventTypeIdByFDRecordId(appealData.data.appealType.getId()),
        appealData.data.rangeAppealDateTime.getStart(),
        appealData.data.rangeAppealDateTime.getEnd(),
        authData)
    }
    else {
      //Редактирование
      event = eventBean.getEventById(appealData.data.id)
      if (event == null) {
        throw new CoreException("Обращение с id = %s не найдено в БД".format(appealData.data.id.toString))
        return null
      }

      val eventTypeId = eventBean.getEventTypeIdByFDRecordId(appealData.data.appealType.getId())
      if (event.getEventType.getId.intValue() != eventTypeId) {
        throw new CoreException("Тип найденного обращения не соответствует типу в запросе (appealType = %s)".format(appealData.data.appealType.getId().toString))
        return null
      }
    }

    return event
  }

  private def AnyToSetOfString(that: AnyRef): Set[String] = {
    if (that == null)
      return Set.empty[String]

    if (that.isInstanceOf[Date]) {
      return Set(ConfigManager.DateFormatter.format(that))
    }
    else if (that.isInstanceOf[LinkedList[IdValueContainer]]) {
      val hospWith = Set.empty[String]
      that.asInstanceOf[LinkedList[IdValueContainer]].foreach(e => hospWith + e.getId().toString)
      return hospWith
    }
    else if (that.isInstanceOf[IdNameContainer]) {
      return Set(that.asInstanceOf[IdNameContainer].getId().toString)
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

  private def getValueByCase(aptId: Int, appealData: AppealData, authData: AuthData) = {

    val listNdx = new IndexOf(list)
    val cap = dbRbCoreActionPropertyBean.getRbCoreActionPropertiesByActionPropertyTypeId(aptId)

    cap.getName match {
      case listNdx(0) => this.AnyToSetOfString(appealData.data.assignment.directed) //Кем направлен
      case listNdx(1) => this.AnyToSetOfString(appealData.data.assignment.number) //Номер направления
      case listNdx(2) => this.AnyToSetOfString(appealData.data.deliveredType) //Кем доставлен
      case listNdx(3) => this.writeMKBDiagnosesFromAppealData(appealData.data.diagnoses, "assignment", false) //Диагноз направившего учреждения
      case listNdx(4) => this.AnyToSetOfString(appealData.data.deliveredAfterType) //Доставлен в стационар от начала заболевания
      case listNdx(5) => this.AnyToSetOfString(null) //Направлен в отделение
      case listNdx(6) => this.AnyToSetOfString(appealData.data.refuseAppealReason) //Причина отказа в госпитализации
      case listNdx(7) => this.AnyToSetOfString(appealData.data.appealWithDeseaseThisYear) //Госпитализирован по поводу данного заболевания в текущем году
      case listNdx(8) => this.AnyToSetOfString(appealData.data.movingType) //Вид транспортировки
      case listNdx(9) => this.AnyToSetOfString(null) //Профиль койки
      case listNdx(10) => this.AnyToSetOfString(appealData.data.stateType) //Доставлен в состоянии опьянения
      case listNdx(11) => this.AnyToSetOfString(appealData.data.injury) //Травма
      case listNdx(12) => this.AnyToSetOfString(appealData.data.assignment.assignmentDate) //Дата направления
      case listNdx(13) => this.AnyToSetOfString(appealData.data.hospitalizationChannelType) //Канал госпитализации
      case listNdx(14) => this.AnyToSetOfString(appealData.data.assignment.doctor) //Направивший врач
      case listNdx(15) => this.writeMKBDiagnosesFromAppealData(appealData.data.diagnoses, "assignment", true) //Клиническое описание
      case listNdx(16) => this.writeMKBDiagnosesFromAppealData(appealData.data.diagnoses, "aftereffect", false) //Диагноз направившего учреждения (осложнения)
      case listNdx(17) => this.writeMKBDiagnosesFromAppealData(appealData.data.diagnoses, "aftereffect", true) //Клиническое описание (осложнения)
      case listNdx(18) => this.AnyToSetOfString(appealData.data.ambulanceNumber) //Номер наряда СП
      case listNdx(19) => this.AnyToSetOfString(appealData.data.physicalParameters.bloodPressure.left) //Артериальное давление (левая рука)
      case listNdx(20) => this.AnyToSetOfString(appealData.data.physicalParameters.bloodPressure.right) //Артериальное давление (правая рука)
      case listNdx(21) => this.AnyToSetOfString(java.lang.Double.valueOf(appealData.data.physicalParameters.temperature)) //t температура тела
      case listNdx(22) => this.AnyToSetOfString(java.lang.Double.valueOf(appealData.data.physicalParameters.weight)) //Вес при поступлении
      case listNdx(23) => this.AnyToSetOfString(java.lang.Double.valueOf(appealData.data.physicalParameters.height)) //Рост
      case listNdx(24) => this.AnyToSetOfString(appealData.data.agreedType) //Тип согласования
      case listNdx(25) => this.AnyToSetOfString(appealData.data.agreedDoctor) //Комментарий к согласованию
      case listNdx(26) => this.AnyToSetOfString(appealData.data.hospitalizationWith) //Законный представитель
      case listNdx(27) => this.AnyToSetOfString(appealData.data.hospitalizationType) //Тип госпитализации
      case listNdx(28) => this.AnyToSetOfString(appealData.data.hospitalizationPointType) //Цель госпитализации
      case listNdx(29) => this.writeMKBDiagnosesFromAppealData(appealData.data.diagnoses, "attendant", false) //Диагноз направившего учреждения (сопутствующий)
      case listNdx(30) => this.writeMKBDiagnosesFromAppealData(appealData.data.diagnoses, "attendant", true) //Клиническое описание (сопутствующий)
      case _ => this.AnyToSetOfString(null)
    }
  }

  private def writeMKBDiagnosesFromAppealData(list_dia: LinkedList[DiagnosisContainer], cell: String, isDesc: Boolean): Set[String] = {

    var valueSet = Set.empty[String]
    if (list_dia == null)
      return valueSet

    try {
      list_dia.foreach((dc) => {
        val mkb = dbMkbBean.getMkbByCode(dc.mkb.getCode().toString)
        val mkbId = if (mkb != null) mkb.getId.intValue() else 0
        if (mkbId > 0) {
          if (dc.getDiagnosisKind().compare(cell) == 0) {
            if (isDesc) {
              valueSet += dc.description.toString
            } else {
              valueSet += mkbId.toString
            }
          }
        }
      })
    } catch {
      case e: Exception => {
        error("writeMKBDiagnosesFromAppealData >> Ошибка при получении значения диагноза MKB: %s".format(e.getMessage))
        throw new CoreException("Ошибка при получении значения диагноза MKB")
      }
    }
    valueSet
  }

  private def getDiagnosisListByActionId(actionId: Int) = {

    val map = new java.util.HashMap[String, java.util.List[Mkb]]
    val actionProperties = actionPropertyBean.getActionPropertiesByActionId(actionId)
    var diagnoses: LinkedList[DiagnosisSimplifyContainer] = new LinkedList[DiagnosisSimplifyContainer]

    actionProperties.foreach(f => {
      val ap_name = f._1.getType.getName
      if (ap_name.contains("Диагноз направившего учреждения")) {
        val dStr: String =
          if (ap_name.contains("(осложнения)")) {
            "aftereffect"
          }
          else if (ap_name.contains("(сопутствующий)")) {
            "attendant"
          }
          else "assignment"

        f._2.foreach(apv => {
          if (!map.containsKey(dStr))
            map.put(dStr, new java.util.LinkedList[Mkb])

          val list = map.get(dStr)
          list.add(apv.getValue.asInstanceOf[Mkb])
          map.remove(dStr)
          map.put(dStr, list)
        })
      }
    })
    map
  }

  private def insertCompleteDiagnoses(eventId: Int, authData: AuthData) = {

    val now = new Date()

    val event = eventBean.getEventById(eventId)
    val diag = dbCustomQueryBean.getDiagnosisForMainDiagInAppeal(eventId)

    val diagnosis = new Diagnosis()
    diagnosis.setCreateDatetime(now)
    diagnosis.setModifyDatetime(now)
    diagnosis.setSetDate(now)
    diagnosis.setEndDate(now)
    diagnosis.setMkb(diag)
    diagnosis.setMkbExCode("") //TODO: Уточнить! diag.getDiagName
    diagnosis.setPatient(event.getPatient)

    val diagnostic = new Diagnostic()
    diagnostic.setCreateDatetime(now)
    diagnostic.setModifyDatetime(now)
    diagnostic.setNotes("")
    diagnostic.setSetDate(now)
    diagnostic.setHospital(0)

    if (authData != null) {
      diagnosis.setCreatePerson(authData.getUser)
      diagnosis.setModifyPerson(authData.getUser)
      diagnosis.setPerson(authData.getUser)

      diagnostic.setCreatePerson(authData.getUser)
      diagnostic.setModifyPerson(authData.getUser)
      diagnostic.setSpeciality(authData.getUser.getSpeciality)
    }

    diagnostic.setEvent(event)

    val diagType = this.getRbDiagnosisTypeById(1)
    if (diagType != null) {
      diagnosis.setDiagnosisType(diagType)
      diagnostic.setDiagnosisType(diagType)
    }

    val rbResult = this.getRbResultById(15)
    if (rbResult != null)
      diagnostic.setResult(rbResult)


    dbManager.persist(diagnosis)
    diagnostic.setDiagnosis(diagnosis)
    dbManager.persist(diagnostic)
    true
  }

  private def getRbDiagnosisTypeById(id: Int): RbDiagnosisType = {
    val diagType = em.createQuery(DiagnosisTypeByIdQuery, classOf[RbDiagnosisType])
      .setParameter("id", id)
      .getResultList
    diagType.size match {
      case 0 => {
        null
      }
      case size => {
        diagType.foreach(dt => em.detach(dt))
        diagType(0)
      }
    }
  }

  private def getRbResultById(id: Int): RbResult = {
    val rbResult = em.createQuery(RbResultByIdQuery, classOf[RbResult])
      .setParameter("id", id)
      .getResultList
    rbResult.size match {
      case 0 => {
        null
      }
      case size => {
        rbResult.foreach(res => em.detach(res))
        rbResult(0)
      }
    }
  }

  /*
  def getBasicInfoOfDiseaseHistory(patientId: Int, externalId: String, authData: AuthData) = {

    var actions: java.util.List[Action] = new LinkedList[Action]
    val action = actionBean.getActionByEventExternalId(externalId)
    actions.add(action)

    val f = details(action)//summary(action)
    f
  }

  def summary(assessment: Action) = {
    val group = new CommonGroup(0, "Summary")

    val attributes = List(
      APPEALWI.Id,
      APPEALWI.Number,
      APPEALWI.AmbulanceNumber,
      APPEALWI.AppealTypeId,
      APPEALWI.Urgent,
      APPEALWI.AppealDateTime,
      APPEALWI.Patient,
      APPEALWI.ExecutorLastName,
      APPEALWI.ExecutorFirstName,
      APPEALWI.ExecutorMiddleName,
      APPEALWI.ExecutorSpecs
    )

    var wrapp = new AppealWrapperEx(assessment)
    var dMap  = new java.util.HashMap[String, Object]
    var list_att = new java.util.ArrayList[java.util.Map[String, Object]]
    attributes.foreach(attribute=>{
      dMap.putAll(wrapp.get(attribute))//.foreach(elem => dMap.putAll(elem))
    })
    list_att.add(dMap)

    val f = new JSONArray(list_att)
    f
  }

  def details(assessment: Action) = {
    var propertiesMap =
      actionPropertyBean.getActionPropertiesByActionId(assessment.getId.intValue)

    val group = new CommonGroup(1, "Details")

    val attributes = List(
      APPEALWI.Assignment//,
      //APPEALWI.PhysicalParameters,
      //APPEALWI.Diagnoses
    )

    var wrapp = new AppealWrapperEx(assessment)
    var dMap  = new java.util.HashMap[String, Object]

    var list_att = new java.util.ArrayList[java.util.Map[String, Object]]

    val maps = propertiesMap.foldLeft(Map.empty[String, (ActionProperty, java.util.List[APValue])])(
    (str_key, el) => {
      var (ap,  apv)  = el
      str_key + (ap.getType.getName -> el)
    })

    attributes.foreach(attribute=>{
      var mft = APPEALWI.apply_property(attribute)
      if(mft!=null) {
        dMap.putAll(wrapp.get_property(attribute, mft, maps))
      }
    })

    list_att.add(dMap)
    new JSONArray(list_att)
  }

*/

  val DiagnosisTypeByIdQuery = """
    SELECT dt
    FROM
      RbDiagnosisType dt
    WHERE
      dt.id = :id
                               """

  val RbResultByIdQuery = """
    SELECT r
    FROM
      RbResult r
    WHERE
      r.id = :id
                          """

  val AllAppealsWithFilterQuery = """
    SELECT %s
    FROM
      Action a
        JOIN a.event e
    WHERE
      e.deleted = 0
    AND
      a.deleted = 0
    %s
    %s
                                  """

  val AppealByExternalIdQuery = """
  SELECT e
  FROM
    Event e
  WHERE
    e.externalId = :externalId
  AND
    e.deleted = 0
   AND
    e.eventType.id = '53'

                                """
}