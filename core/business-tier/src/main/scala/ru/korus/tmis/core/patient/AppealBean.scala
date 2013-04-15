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
import ru.korus.tmis.util.{CAPids, ConfigManager, I18nable}
import javax.persistence.{PersistenceContext, EntityManager}
import collection.mutable.{HashMap, HashSet}
import java.util.{ArrayList, Date, LinkedList}
import java.text.{DateFormat, SimpleDateFormat}
import ru.korus.tmis.core.exception.CoreException
import java.{util, sql}
import collection.{mutable, JavaConversions}
import java.sql.{Connection, DriverManager, ResultSet}
import ru.korus.tmis.core.event.{Notification, ModifyActionNotification}
import javax.inject.Inject
import javax.enterprise.inject.Any
import java.lang.Iterable
;

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class AppealBean extends AppealBeanLocal
                    with Logging
                    with I18nable
                    with CAPids{

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
  private var dbManager: DbManagerBeanLocal = _

  @EJB
  private var dbCustomQueryBean: DbCustomQueryLocal = _

  @EJB
  private var dbRbCoreActionPropertyBean: DbRbCoreActionPropertyBeanLocal = _

  @EJB
  private var dbMkbBean: DbMkbBeanLocal = _

  @EJB
  private var dbFDRecordBean: DbFDRecordBeanLocal = _

  @EJB
  var dbPatientBean: DbPatientBeanLocal = _

  @EJB
  var dbClientRelation: DbClientRelationBeanLocal = _

  @EJB
  var dbClientQuoting: DbClientQuotingBeanLocal = _

  @EJB
  var dbEventPerson: DbEventPersonBeanLocal = _

  @EJB
  var dbEventTypeBean: DbEventTypeBeanLocal = _

  @EJB
  var diagnosisBean: DiagnosisBeanLocal = _

  @Inject
  @Any
  var actionEvent: javax.enterprise.event.Event[Notification] = _

  private class IndexOf[T] (seq: Seq[T]) {
    def unapply(pos: T) = seq find (pos ==) map (seq indexOf _)
  }

  val list = List(  iCapIds("db.rbCAP.hosp.primary.id.directed").toInt,                          //Кем направлен
                    iCapIds("db.rbCAP.hosp.primary.id.number").toInt,                            //№ направления
                    iCapIds("db.rbCAP.hosp.primary.id.deliveredType").toInt,                     //Кем доставлен
                    iCapIds("db.rbCAP.hosp.primary.id.diagnosis.assigment.code").toInt,          //Диагноз направившего учреждения
                    iCapIds("db.rbCAP.hosp.primary.id.deliveredAfterType").toInt,                //Доставлен в стационар от начала заболевания
                    iCapIds("db.rbCAP.hosp.primary.id.sentTo").toInt,                            //Направлен в отделение
                    iCapIds("db.rbCAP.hosp.primary.id.cancel").toInt,                            //Причина отказа в госпитализации
                    iCapIds("db.rbCAP.hosp.primary.id.appealWithDeseaseThisYear").toInt,         //Госпитализирован по поводу данного заболевания в текущем году
                    iCapIds("db.rbCAP.hosp.primary.id.transportationType").toInt,                //Вид транспортировки
                    iCapIds("db.rbCAP.hosp.primary.id.placeType").toInt,                         //Профиль койки
                    iCapIds("db.rbCAP.hosp.primary.id.drugsType").toInt,                         //Доставлен в состоянии опьянения
                    iCapIds("db.rbCAP.hosp.primary.id.injury").toInt,                            //Травма
                    iCapIds("db.rbCAP.hosp.primary.id.assignmentDate").toInt,                    //Дата направления
                    iCapIds("db.rbCAP.hosp.primary.id.hospitalizationChannelType").toInt,        //Канал госпитализации
                    iCapIds("db.rbCAP.hosp.primary.id.doctor").toInt,                            //Направивший врач
                    iCapIds("db.rbCAP.hosp.primary.id.diagnosis.assignment.description").toInt,  //Клиническое описание
                    iCapIds("db.rbCAP.hosp.primary.id.diagnosis.aftereffect.code").toInt,        //Диагноз направившего учреждения (осложнения)
                    iCapIds("db.rbCAP.hosp.primary.id.diagnosis.aftereffect.description").toInt, //Клиническое описание (осложнения)
                    iCapIds("db.rbCAP.hosp.primary.id.ambulanceNumber").toInt,                   //№ наряда СП
                    iCapIds("db.rbCAP.hosp.primary.id.bloodPressure.left.ADdiast").toInt,        //Левая рука: АД диаст.
                    iCapIds("db.rbCAP.hosp.primary.id.bloodPressure.left.ADsyst").toInt,         //Левая рука: АД сист.
                    iCapIds("db.rbCAP.hosp.primary.id.temperature").toInt,                       //t
                    iCapIds("db.rbCAP.hosp.primary.id.weight").toInt,                            //Вес при поступлении
                    iCapIds("db.rbCAP.hosp.primary.id.height").toInt,                            //Рост
                    iCapIds("db.rbCAP.hosp.primary.id.agreedType").toInt,                        //Тип согласования
                    iCapIds("db.rbCAP.hosp.primary.id.agreedDoctor").toInt,                      //Комментарий к согласованию
                    iCapIds("db.rbCAP.hosp.primary.id.hospitalizationWith").toInt,               //Законный представитель
                    iCapIds("db.rbCAP.hosp.primary.id.hospitalizationType").toInt,               //Тип госпитализации
                    iCapIds("db.rbCAP.hosp.primary.id.hospitalizationPointType").toInt,          //Цель госпитализации
                    iCapIds("db.rbCAP.hosp.primary.id.diagnosis.attendant.code").toInt,          //Диагноз направившего учреждения (сопутствующий)
                    iCapIds("db.rbCAP.hosp.primary.id.diagnosis.attendant.description").toInt,   //Клиническое описание (сопутствующий)
                    iCapIds("db.rbCAP.hosp.primary.id.bloodPressure.right.ADdiast").toInt,       //Правая рука: АД диаст.
                    iCapIds("db.rbCAP.hosp.primary.id.bloodPressure.right.ADsyst").toInt,        //Правая рука: АД сист.
                    iCapIds("db.rbCAP.hosp.primary.id.note").toInt)                              //Примечание

  //Insert or modify appeal
  def insertAppealForPatient(appealData : AppealData, patientId: Int, authData: AuthData) = {

    //1. Event и проверка данных на валидность
    var newEvent = this.verificationData(patientId, authData, appealData, true)
    dbManager.persist(newEvent)
    dbManager.detach(newEvent)
    insertOrModifyAppeal(appealData, newEvent, true, authData)
  }

  def updateAppeal(appealData : AppealData, eventId: Int, authData: AuthData) = {

    var newEvent = this.verificationData(eventId, authData, appealData, false)
    insertOrModifyAppeal(appealData, newEvent, false, authData)
  }

  private def insertOrModifyAppeal(appealData : AppealData, event: Event, flgCreate: Boolean, authData: AuthData) = {

    var entities = Set.empty[AnyRef]
    val now = new Date()
    var newEvent = event
    //2. Action

    var oldAction: Action = null// Action.clone(temp)
    var oldValues = Map.empty[ActionProperty, java.util.List[APValue]] //actionPropertyBean.getActionPropertiesByActionId(oldAction.getId.intValue)
    var lockId: Int = -1//appLock.acquireLock("Action", temp.getId.intValue(), oldAction.getIdx, authData)

    val temp = actionBean.getAppealActionByEventId(newEvent.getId.intValue(), i18n("db.actionType.hospitalization.primary").toInt)
    var action: Action = null
    var list =  List.empty[AnyRef]

    if(flgCreate) {
      //Обновим контейнер новыми данными
      appealData.data.setId(newEvent.getId.intValue())
      appealData.data.setNumber(newEvent.getExternalId)
    } else {
      oldAction = Action.clone(temp)
      oldValues = actionPropertyBean.getActionPropertiesByActionId(oldAction.getId.intValue).toMap
      lockId = appLock.acquireLock("Action", temp.getId.intValue(), oldAction.getIdx, authData)
    }

    try {

      if (temp==null){
        action = actionBean.createAction(newEvent.getId.intValue(),
                                          actionTypeBean.getActionTypeById(i18n("db.actionType.hospitalization.primary").toInt).getId.intValue(),
                                          authData)
        action.setStatus(ActionStatus.FINISHED.getCode) //TODO: Материть Александра!
        em.persist(action)
        list = actionPropertyTypeBean.getActionPropertyTypesByActionTypeId(i18n("db.actionType.hospitalization.primary").toInt).toList
      }
      else {
        action = actionBean.updateAction(temp.getId.intValue(),
                                         temp.getVersion.intValue,
                                         authData)
        list = actionPropertyBean.getActionPropertiesByActionId(temp.getId.intValue).keySet.toList

        val list2 = actionPropertyTypeBean.getActionPropertyTypesByActionTypeId(i18n("db.actionType.hospitalization.primary").toInt)
                                          .toList
                                          .filter(p => {
          val filtred = list.filter(pp => pp.asInstanceOf[ActionProperty].getType.getId == p.getId)
          if (filtred == null || filtred.size == 0) true else false
        })
        list2.foreach(ff => { //создание недостающих акшен пропертей
          val res = actionPropertyBean.createActionProperty(action, ff.getId.intValue(), authData)
          em.persist(res)
        })
        //пересобираем лист акшенПропертей
        list = actionPropertyBean.getActionPropertiesByActionId(temp.getId.intValue).keySet.toList
      }

      action.setIsUrgent(appealData.data.getUrgent)
      if(appealData.data.rangeAppealDateTime.getStart!=null)
        action.setBegDate(appealData.data.rangeAppealDateTime.getStart)
      action.setEndDate(appealData.data.rangeAppealDateTime.getEnd)

      if (!flgCreate)
        entities = entities + action

      //3. Action Property

      list.foreach(f => {
        val ap: ActionProperty =
          if(flgCreate){
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
        if (values!=null){
          values.size match {
            case 0 => {
              if (flgCreate) {
                //В случае, если на приходит значение для ActionProperty, то записываем значение по умолчанию.
                val defValue = ap.getType.getDefaultValue
                if (defValue!=null && !defValue.trim.isEmpty) {
                  val apv = actionPropertyBean.setActionPropertyValue(ap, defValue, 0)
                  if (apv!=null)
                    entities = entities + apv.unwrap
                }
              } else { //Если пришел пустой список, а старые значения есть, то зачистим их
                val apvs = actionPropertyBean.getActionPropertyValue(ap)
                if (apvs!=null && apvs.size()>0) {
                  for(i <- 0 until apvs.size){
                    var apv = apvs(i).unwrap()
                    apv = em.merge(apv)
                    em.remove(apv)
                  }
                }
              }
            }
            case _ => {
              if(ap.getType.getIsVector) { //Если вектор, то сперва зачищаем старый список
              val apvs = actionPropertyBean.getActionPropertyValue(ap)
                if (apvs!=null && apvs.size()>values.size){
                  for(i <- values.size to apvs.size-1) { //если новых значений меньше тем старых, то хвост зачистим
                    var apv = apvs(i).unwrap()
                    apv = em.merge(apv)
                    em.remove(apv)
                  }
                }
              }
              var it = 0
              values.foreach(value => {
                val apv = actionPropertyBean.setActionPropertyValue(ap, value, it)
                if (apv!=null)
                  entities = entities + apv.unwrap
                it = it + 1
              })
            }
          }
        }
      })

      if (!flgCreate) dbManager.mergeAll(entities) else dbManager.persistAll(entities)
      dbManager.detach(action)
       /*
      if (!flgCreate) {
        val newValues = actionPropertyBean.getActionPropertiesByActionId(action.getId.intValue)
        actionEvent.fire(new ModifyActionNotification(oldAction,
          oldValues,
          action,
          newValues))
      }
      */
    }
    finally {
      if (lockId>0) appLock.releaseLock(lockId)
    }

    if (!flgCreate){
      //Редактирование обращения (В случае если изменен НИБ)
      var flgEventRewrite = true
      if(appealData.data.number!=null &&
        !appealData.data.number.isEmpty &&
        newEvent.getExternalId.compareTo(appealData.data.number)!=0) {

        //проверка НИБ на уникальность
        if (this.checkAppealNumber(appealData.data.number)==false)
          throw new CoreException("Номер истории болезни в запросе (НИБ = %s) отличается от текущего (НИБ = %s) и не является уникальным".format(appealData.data.number, newEvent.getExternalId))

        newEvent.setExternalId(appealData.data.number)
        newEvent.setModifyDatetime(now)
        newEvent.setModifyPerson(authData.user)
        //newEvent.setExecDate(now)

        flgEventRewrite = true
      }
      if(appealData.data.refuseAppealReason!=null && !appealData.data.refuseAppealReason.isEmpty) {
        newEvent = this.revokeAppealById(newEvent, 15, authData)
        //this.insertCompleteDiagnoses(appealData.data.id, authData)   //Старый вариант (заменено кодом ниже)
        //final диагноз
        val admissionMkb = dbCustomQueryBean.getDiagnosisForMainDiagInAppeal(appealData.data.id)
        if (admissionMkb != null) {
          var map = Map.empty[String, java.util.Set[AnyRef]]
          map += ("final" -> Set[AnyRef]((-1, "", Integer.valueOf(admissionMkb.getId.intValue))))
          val diag = diagnosisBean.insertDiagnoses(appealData.data.id, asJavaMap(map), authData)
          diag.filter(p=>p.isInstanceOf[Diagnostic]).toList.foreach(f=>f.asInstanceOf[Diagnostic].setResult(this.getRbResultById(15)))
          dbManager.persistAll(diag)
        }

        flgEventRewrite = true
      }
      if (flgEventRewrite == true) {
        newEvent.setVersion(newEvent.getVersion)
        dbManager.merge(newEvent)
      }
    }
    //******
    //Создание/Редактирование записей для законных представителей
    val clientRelations = appealData.data.getHospitalizationWith
    var setRel = Set.empty[ClientRelation]
    if (clientRelations!=null && clientRelations.size()>0) { //Если законные представители заполнены
      val patient = newEvent.getPatient
      val serverRelations = patient.getActiveClientRelatives()

      clientRelations.foreach(f => {
        val serverRelation = serverRelations.find(element => ((element.getRelative.getId.intValue() == f.getRelative.getId) &&
                                                              (element.getRelativeType.getId.intValue()==f.getRelativeType.getId)))
                                            .getOrElse(null)
        if (serverRelation==null){
          val updateRelation = serverRelations.find(element => element.getRelative.getId.intValue() == f.getRelative.getId).getOrElse(null)
          val relationId = if(updateRelation==null) -1 else updateRelation.getId.intValue()
          val parent = dbPatientBean.getPatientById(f.getRelative.getId)
          val tempServerRelation = dbClientRelation.insertOrUpdateClientRelationByRelativePerson(relationId,
                                                                                    f.getRelativeType.getId,
                                                                                    parent,
                                                                                    patient,
                                                                                    authData.user)
          setRel += tempServerRelation
        }
      })
    }
    if (setRel!=null && setRel.size>0) dbManager.mergeAll(setRel)
    //*****
    //Создание/редактирование записи для Event_Persons
    if (flgCreate)
      dbEventPerson.insertOrUpdateEventPerson(0, newEvent, authData.getUser, true) //в ивенте только создание

    //Создание/редактирование диагнозов (отд. записи)
    var map = Map.empty[String, java.util.Set[AnyRef]]
    Set("assignment", "aftereffect", "attendant").foreach(flatCode=>{
      val values = appealData.data.diagnoses.filter(p=>p.getDiagnosisKind.compareTo(flatCode)==0)
                                            .map(f=>{
                                                      val mkb = dbMkbBean.getMkbByCode(f.getMkb.getCode)
                                                      (Integer.valueOf(f.getDiagnosticId),
                                                      f.getDescription,
                                                      if(mkb!=null) Integer.valueOf(mkb.getId.intValue) else -1)
                                                    })
                                            .toSet[AnyRef]
      map += (flatCode -> values)
    })
    val diagnoses = diagnosisBean.insertDiagnoses(newEvent.getId.intValue(), asJavaMap(map), authData)
    val mergedItems = diagnoses.filter(p=> (p.isInstanceOf[Diagnosis] &&
                                              p.asInstanceOf[Diagnosis].getId!=null &&
                                              p.asInstanceOf[Diagnosis].getId.intValue()>0) ||
                                             (p.isInstanceOf[Diagnostic] &&
                                              p.asInstanceOf[Diagnostic].getId!=null &&
                                              p.asInstanceOf[Diagnostic].getId.intValue()>0)
                                        ).toList
    val persistedItems = diagnoses.filter(p=> (p.isInstanceOf[Diagnosis] &&
                                                (p.asInstanceOf[Diagnosis].getId==null ||
                                                 p.asInstanceOf[Diagnosis].getId.intValue()<=0))||
                                              (p.isInstanceOf[Diagnostic] &&
                                                (p.asInstanceOf[Diagnostic].getId==null ||
                                                 p.asInstanceOf[Diagnostic].getId.intValue()<=0))
                                          ).toList
    dbManager.mergeAll(mergedItems)
    dbManager.persistAll(persistedItems)
    //
    newEvent.getId.intValue()
  }

  def getAppealById(id: Int) = {
    //запрос  данных из Эвента
    val event = eventBean.getEventById(id)
    if(event==null){
      throw new CoreException("Обращение с id=%d не найдено в БД".format(id))
    }
    //запрос данных из Action
    val action = actionBean.getAppealActionByEventId(event.getId.intValue(),i18n("db.actionType.hospitalization.primary").toInt)
    if(action==null){
      throw new CoreException("Первичный осмотр для обращения с id=%d не найден в БД".format(id))
    }
    //Запрос данных из ActionProperty
    val findMapActionProperty = actionPropertyBean.getActionPropertiesByActionId(action.getId.intValue())

    val values: java.util.Map[java.lang.Integer, java.util.List[Object]] = findMapActionProperty.foldLeft(new java.util.HashMap[java.lang.Integer, java.util.List[Object]])(
      (str_key, el) => {
        val (ap,  apvs) = el
        val aptId = ap.getType.getId.intValue()
        val rbCap = dbRbCoreActionPropertyBean.getRbCoreActionPropertiesByActionPropertyTypeId(aptId)
        if (rbCap!=null) {
          val key = rbCap.getId
          val list: java.util.List[Object] = new ArrayList[Object]
          if(apvs!=null && apvs.size>0) {
            apvs.foreach(apv=>list += apv.getValue)
          }
          else
            list += null

          if(str_key.containsKey(key)){
            list.addAll(str_key.get(key))
            str_key.remove(key)
          }
          str_key.put(key,list)
        } else {
          //Ворнинг! Нету такого типа экшн проперти в кореэкшнпропертитайп
        }
        str_key
      })

    val eventsMap = new java.util.HashMap[Event, java.util.Map[Action, java.util.Map[java.lang.Integer,java.util.List[Object]]]]
    val actionsMap = new java.util.HashMap[Action, java.util.Map[java.lang.Integer,java.util.List[Object]]]

    actionsMap.put(action, values)
    eventsMap.put(event, actionsMap)

    eventsMap
  }

  def getAllAppealsByPatient( requestData: AppealSimplifiedRequestData,
                              authData: AuthData) = {

    //TODO: подключить анализ авторизационных данных и доступных ролей
    //requestData.setRecordsCount(dbCustomQueryBean.getCountOfAppealsWithFilter(requestData.filter))
    val map = dbCustomQueryBean.getAllAppealsWithFilter(requestData.page-1, requestData.limit, requestData.sortingFieldInternal, requestData.sortingMethod, requestData.filter, requestData.rewriteRecordsCount _)
    new AppealSimplifiedDataList(map, requestData)
  }

  def getCountOfAppealsForReceivedPatientByPeriod(filter: Object) = {

    var queryStr: QueryDataStructure = if(filter.isInstanceOf[ReceivedRequestDataFilter]) {
      filter.asInstanceOf[ReceivedRequestDataFilter].toQueryStructure()
    }
    else {new QueryDataStructure()}

    var typed= em.createQuery(AllAppealsWithFilterQuery.format("count(e)", i18n("db.flatDirectory.eventType.hospitalization"), queryStr.query, ""), classOf[Long])

    if(queryStr.data.size()>0) {
      queryStr.data.foreach(qdp=>typed.setParameter(qdp.name, qdp.value))
    }

    typed.getSingleResult
  }

  def getAllAppealsForReceivedPatientByPeriod (page: Int, limit: Int, sortingField: String, sortingMethod: String, filter: Object) = {

    var queryStr: QueryDataStructure = if(filter.isInstanceOf[ReceivedRequestDataFilter]) {
      filter.asInstanceOf[ReceivedRequestDataFilter].toQueryStructure()
    }
    else {new QueryDataStructure()}

    val sorting = "ORDER BY %s %s".format(sortingField, sortingMethod)

    val q = AllAppealsWithFilterQuery.format("e", i18n("db.flatDirectory.eventType.hospitalization") ,queryStr.query, sorting)
    var typed = em.createQuery(q, classOf[Event])
      .setMaxResults(limit)
      .setFirstResult(limit*page)

    if(queryStr.data.size()>0) {
      queryStr.data.foreach(qdp=>typed.setParameter(qdp.name, qdp.value))
    }

    val result = typed.getResultList
    result.foreach(e=>em.detach(e))
    result
  }

  def checkAppealNumber(number : String) = {
    var isNumberFree = false
    val result = em.createQuery(AppealByExternalIdQuery, classOf[Event])
      .setParameter("externalId", number)
      .getResultList

    if (result == null || result.length < 1) {
      isNumberFree = true
    }
    isNumberFree
  }


  def revokeAppealById(event : Event, resultId: Int, authData: AuthData) = {

    //Закрываем госпитализацию с причиной отказа
    val now = new Date()
    if(event == null){
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

  def getPatientsHospitalizedStatus(eventId: Int) = {
    var status: String = ""
    val event = eventBean.getEventById(eventId)
    val execDate = event.getExecDate

    var setATIds = JavaConversions.asJavaSet(Set(i18n("db.actionType.hospitalization.primary").toInt :java.lang.Integer))
    val hospId = actionBean.getLastActionByActionTypeIdAndEventId (eventId, setATIds)
    if(hospId>0) { //Есть экшн - поступление
      val lstSentToIds = JavaConversions.asJavaList(scala.List(i18n("db.rbCAP.hosp.primary.id.sentTo").toInt :java.lang.Integer))
      val lstCancelIds = JavaConversions.asJavaList(scala.List(i18n("db.rbCAP.hosp.primary.id.cancel").toInt :java.lang.Integer))
      val apSentToWithValues = actionPropertyBean.getActionPropertiesByActionIdAndRbCoreActionPropertyIds(hospId, lstSentToIds)
      val apCancelWithValues = actionPropertyBean.getActionPropertiesByActionIdAndRbCoreActionPropertyIds(hospId, lstCancelIds)
      if (execDate!= null){
        if (apCancelWithValues!=null &&
          apCancelWithValues.size()>0 &&
          apCancelWithValues.filter(element => element._2.size()>0).size>0){
          status = i18n("patient.status.canceled").toString + ": " + apCancelWithValues.iterator.next()._2.get(0).getValueAsString
        } else {
          status = i18n("patient.status.discharged").toString + ": " + ConfigManager.DateFormatter.format(execDate)
        }
      } else {
        if (apSentToWithValues!=null &&
          apSentToWithValues.size()>0 &&
          apSentToWithValues.filter(element => element._2.size()>0).size>0){
          //Проверяем наличие экшна - Движение
          setATIds = JavaConversions.asJavaSet(Set(i18n("db.actionType.moving").toInt :java.lang.Integer))
          val movingId = actionBean.getLastActionByActionTypeIdAndEventId(eventId, setATIds)
          status = if (movingId>0) i18n("patient.status.regToBed").toString
          else i18n("patient.status.sentTo").toString
        } else {
          if (execDate!= null)
            status = i18n("patient.status.discharged").toString
          else {
            setATIds = JavaConversions.asJavaSet(Set(i18n("db.actionType.primary").toInt :java.lang.Integer,
                                                     i18n("db.actionType.secondary").toInt :java.lang.Integer))
            val primaryId = actionBean.getLastActionByActionTypeIdAndEventId (eventId, setATIds)
            status = if(primaryId>0) i18n("patient.status.hospitalized").toString
                     else i18n("patient.status.require").toString
          }
        }
      }
    } else {
      status = if (execDate!= null) i18n("patient.status.discharged").toString
               else i18n("patient.status.require").toString
    }
    status
  }

  //Внутренние методы

  @throws(classOf[CoreException])
  private def verificationData(id: Int, authData: AuthData, appealData: AppealData, flgCreate: Boolean): Event = {   //для создания ид пациента, для редактирование ид обращения

    if (authData==null){
      throw new CoreException("Mетод для изменения обращения по госпитализации не доступен для неавторизованного пользователя.")
      return null
    }

    var event: Event = null
    if (flgCreate) {            //Создаем новое
      if (id <= 0) {
        throw new CoreException("Невозможно создать госпитализацию. Пациент не установлен.")
        return null
      }
      if (appealData.data.appealType==null ||
        appealData.data.appealType.eventType==null ||
        appealData.data.appealType.eventType.getId<=0){
        throw new CoreException("Невозможно создать госпитализацию. Не задан тип обращения.")
        return null
      }
      event = eventBean.createEvent(id,
                                    //eventBean.getEventTypeIdByFDRecordId(appealData.data.appealType.getId()),
                                    appealData.data.appealType.eventType.getId,
                                    //eventBean.getEventTypeIdByRequestTypeIdAndFinanceId(appealData.data.appealType.requestType.getId(), appealData.data.appealType.finance.getId()),
                                    appealData.data.rangeAppealDateTime.getStart(),
                                    /*appealData.data.rangeAppealDateTime.getEnd()*/null,
                                    authData)
    }
    else {                      //Редактирование
      event = eventBean.getEventById(id)
      if (event==null) {
        throw new CoreException("Обращение с id = %s не найдено в БД".format(appealData.data.id.toString))
        return null
      }

 //   Закомментировано согласно пожеланиям Александра
 //   Мотивация - хотят редактировать эвент тайп и финанс айди! (как бы потом не было бо-бо от этого)
 //     val eventTypeId = appealData.data.appealType.eventType.getId//eventBean.getEventTypeIdByRequestTypeIdAndFinanceId(appealData.data.appealType.requestType.getId(), appealData.data.appealType.finance.getId())
 //     if(event.getEventType.getId.intValue()!=eventTypeId) {
 //       throw new CoreException("Тип найденного обращения не соответствует типу в полученному в запросе (requestType = %s, finance = %s)".format(appealData.data.appealType.requestType.getId().toString, appealData.data.appealType.finance.getId().toString))
 //       return null
 //     }

      val now = new Date()
      event.setModifyDatetime(now)
      event.setModifyPerson(authData.user)
      event.setSetDate(appealData.data.rangeAppealDateTime.getStart())
      //event.setExecDate(appealData.data.rangeAppealDateTime.getEnd())
      event.setEventType(dbEventTypeBean.getEventTypeById(appealData.data.appealType.eventType.getId))
      event.setVersion(appealData.getData().getVersion())
    }

    val hosptype = if(appealData.data.hospitalizationType!=null) appealData.data.hospitalizationType.getId else 0
    if(hosptype>0) {
      //val value = dbFDRecordBean.getFDRecordById(hosptype)
      //if (value!=null) {            //TODO: ! Материть Сашу
        val order = if(/*value.getId.intValue()*/hosptype==220) 1
        else if (/*value.getId.intValue()*/hosptype==221) 2
        else if (/*value.getId.intValue()*/hosptype==222) 3
        else if (/*value.getId.intValue()*/hosptype==223) 4
        else 0
        event.setOrder(order)
      //}
    } else event.setOrder(0)

    if (appealData.data.appealWithDeseaseThisYear!=null && !appealData.data.appealWithDeseaseThisYear.isEmpty) {
      val value = appealData.data.appealWithDeseaseThisYear
      val isPrim = if(value.contains("первично")) 1 else if (value.contains("повторно")) 2 else 0   //TODO: ! Материть Сашу
      event.setIsPrimary(isPrim)
    } else event.setIsPrimary(0)

    event.setOrgId(3479)  //TODO: ! Материть Сашу

    return event
  }

  private def AnyToSetOfString(that: AnyRef, sec: String): Set[String] = {
    if(that==null)
      return null //Set.empty[String]     //В случае если не обрабатываем проперти вернем нулл (чтобы не переписывать значения)

    if (that.isInstanceOf[Date]) {
      return Set(ConfigManager.DateFormatter.format(that))
    }
    else if (that.isInstanceOf[LinkedList[/*IdValueContainer*/LegalRepresentativeContainer]]) {
      var hospWith = Set.empty[String]
      that.asInstanceOf[LinkedList[LegalRepresentativeContainer]].foreach(e => {
        if(e.getRelative.getId>0) {
          sec match {
            case "relative" => hospWith += e.getRelative.getId.toString
            case "note" => hospWith += e.getNote.toString
            case _ => hospWith += e.getRelative.getId.toString
          }
        }
      })
      return hospWith
    }
    else if (that.isInstanceOf[IdNameContainer]) {

      return if(that.asInstanceOf[IdNameContainer].getId>0)
                Set(that.asInstanceOf[IdNameContainer].getId.toString)
             else Set.empty[String]
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

  private def getValueByCase(aptId: Int, appealData : AppealData, authData: AuthData) = {

    val listNdx = new IndexOf(list)
    val cap = dbRbCoreActionPropertyBean.getRbCoreActionPropertiesByActionPropertyTypeId(aptId)

    cap.getId.intValue() match {
      case listNdx(0) => this.AnyToSetOfString(appealData.data.assignment.directed, "")                                                 //Кем направлен
      case listNdx(1) => this.AnyToSetOfString(appealData.data.assignment.number, "")                                                   //Номер направления
      case listNdx(2) => this.AnyToSetOfString(appealData.data.deliveredType, "")                                                       //Кем доставлен
      case listNdx(3) => this.writeMKBDiagnosesFromAppealData(appealData.data.diagnoses, "assignment", false)                       //Диагноз направившего учреждения
      case listNdx(4) => this.AnyToSetOfString(appealData.data.deliveredAfterType, "")                                                  //Доставлен в стационар от начала заболевания
      case listNdx(5) => this.AnyToSetOfString(null, "")                                                                                //Направлен в отделение
      case listNdx(6) => this.AnyToSetOfString(appealData.data.refuseAppealReason, "")                                                  //Причина отказа в госпитализации
      case listNdx(7) => this.AnyToSetOfString(appealData.data.appealWithDeseaseThisYear, "")                                           //Госпитализирован по поводу данного заболевания в текущем году
      case listNdx(8) => this.AnyToSetOfString(appealData.data.movingType, "")                                                          //Вид транспортировки
      case listNdx(9) => this.AnyToSetOfString(null, "")                                                                                //Профиль койки
      case listNdx(10) => this.AnyToSetOfString(appealData.data.stateType, "")                                                          //Доставлен в состоянии опьянения
      case listNdx(11) => this.AnyToSetOfString(appealData.data.injury, "")                                                             //Травма
      case listNdx(12) => this.AnyToSetOfString(appealData.data.assignment.assignmentDate, "")                                          //Дата направления
      case listNdx(13) => this.AnyToSetOfString(appealData.data.hospitalizationChannelType, "")      //Канал госпитализации
      case listNdx(14) => this.AnyToSetOfString(appealData.data.assignment.doctor, "")                                                  //Направивший врач
      case listNdx(15) => this.writeMKBDiagnosesFromAppealData(appealData.data.diagnoses, "assignment", true)                       //Клиническое описание
      case listNdx(16) => this.writeMKBDiagnosesFromAppealData(appealData.data.diagnoses, "aftereffect", false)                     //Диагноз направившего учреждения (осложнения)
      case listNdx(17) => this.writeMKBDiagnosesFromAppealData(appealData.data.diagnoses, "aftereffect", true)                      //Клиническое описание (осложнения)
      case listNdx(18) => this.AnyToSetOfString(appealData.data.ambulanceNumber, "")                                                    //Номер наряда СП
      case listNdx(19) => this.AnyToSetOfString(java.lang.Double.valueOf(appealData.data.physicalParameters.bloodPressure.left.diast), "")                              //Артериальное давление (левая рука Диаст)
      case listNdx(20) => this.AnyToSetOfString(java.lang.Double.valueOf(appealData.data.physicalParameters.bloodPressure.left.syst), "")                             //Артериальное давление (левая рука Сист)
      case listNdx(21) => this.AnyToSetOfString(java.lang.Double.valueOf(appealData.data.physicalParameters.temperature), "")           //t температура тела
      case listNdx(22) => this.AnyToSetOfString(java.lang.Double.valueOf(appealData.data.physicalParameters.weight), "")                //Вес при поступлении
      case listNdx(23) => this.AnyToSetOfString(java.lang.Double.valueOf(appealData.data.physicalParameters.height), "")                //Рост
      case listNdx(24) => this.AnyToSetOfString(appealData.data.agreedType, "")                      //Тип согласования
      case listNdx(25) => this.AnyToSetOfString(appealData.data.agreedDoctor, "")                                                       //Комментарий к согласованию
      case listNdx(26) => this.AnyToSetOfString(appealData.data.hospitalizationWith, "relative")                                                //Законный представитель
      case listNdx(27) => this.AnyToSetOfString(appealData.data.hospitalizationType, "")             //Тип госпитализации
      case listNdx(28) => this.AnyToSetOfString(appealData.data.hospitalizationPointType, "")        //Цель госпитализации
      case listNdx(29) => this.writeMKBDiagnosesFromAppealData(appealData.data.diagnoses, "attendant", false)                       //Диагноз направившего учреждения (сопутствующий)
      case listNdx(30) => this.writeMKBDiagnosesFromAppealData(appealData.data.diagnoses, "attendant", true)                        //Клиническое описание (сопутствующий)
      case listNdx(31) => this.AnyToSetOfString(java.lang.Double.valueOf(appealData.data.physicalParameters.bloodPressure.right.diast), "")                              //Артериальное давление (правая рука)
      case listNdx(32) => this.AnyToSetOfString(java.lang.Double.valueOf(appealData.data.physicalParameters.bloodPressure.right.syst), "")
      case listNdx(33) => this.AnyToSetOfString(appealData.data.hospitalizationWith, "note") //Примечание
      case _ => this.AnyToSetOfString(null, "")
    }
  }

  /**
   * Метод для получения списка идентификаторов диагнозов МКВ или клинических описаний по ключу.
   * @author Ivan Dmitriev
   * @param list_dia Сортированный лист структур с данными о диагнозах (DiagnosisContainer из структуры AppealData).
   * @param cell  Метка типа выводимого списка (фильтр данных из DiagnosisContainer по полю diagnosisKind).<pre>
   * &#15; Возможные значения:
   * &#15; "assignment" - Диагноз/Клиническое описание направившего учреждения
   * &#15; "attendant" - Диагноз/Клиническое описание направившего учреждения (сопутствующий)
   * &#15; "aftereffect" - Диагноз/Клиническое описание направившего учреждения (осложнения)</pre>
   * @param isDesc Флаг типа выводимого списка (true - список клинических описаний, false - список идентификаторов МКВ).
   * @return scala.collection.immutable.Set[String] Список значений для записи в ActionProperty.
   * @throws CoreException
   * @see CoreException
   * @see DiagnosisContainer
   * @see AppealData
   * @version 1.0.0.42 Последние изменения
   */
  private def writeMKBDiagnosesFromAppealData(list_dia: LinkedList[DiagnosisContainer], cell: String, isDesc: Boolean): Set[String] = {

    var valueSet = Set.empty[String]
    if(list_dia == null)
      return valueSet

    list_dia.filter(element => element.diagnosisKind.compare(cell)==0).toList.foreach(dc=>{
      if(isDesc) {  //Получаем набор описаний
        valueSet += dc.description.toString
      } else {
        try {
          if (dc.mkb.getCode() != null) {
            val mkb = dbMkbBean.getMkbByCode(dc.mkb.getCode().toString)
            if(mkb!=null && mkb.getId.intValue()>0)
              valueSet += mkb.getId.toString
          }
        } catch {
          case e: Exception => throw new CoreException("Ошибка при получении значения диагноза MKB")
        }
      }
    })
    valueSet
  }

  //Диагнозы из первичного осмотра(при госпитализации)
  def getDiagnosisListByAppealId(eventId: Int, filter: String) = {
    val map = new java.util.HashMap[String, java.util.List[Mkb]]
    val constPartToName = "Диагноз направившего учреждения"
    val diagnosisPropertyList = Map( "assignment" -> i18n("appeal.db.actionPropertyType.name.diagnosis.assigment.code").toString,
      "attendant" -> i18n("appeal.db.actionPropertyType.name.diagnosis.attendant.code").toString,
      "aftereffect"-> i18n("appeal.db.actionPropertyType.name.diagnosis.aftereffect.code").toString)

    val setATIds = JavaConversions.asJavaSet(Set(i18n("db.actionType.hospitalization.primary").toInt :java.lang.Integer))
    val actionId = actionBean.getLastActionByActionTypeIdAndEventId (eventId, setATIds)
    if(actionId>0) {
      var actionProperties = actionPropertyBean.getActionPropertiesByActionId(actionId)
                                               .filter(element => element._1.getType.getName.contains(constPartToName))
      if(filter!=null && !filter.isEmpty){
        actionProperties = actionProperties.filter(element => element._2.filter(value => (value.getValue.asInstanceOf[Mkb].getDiagID.contains(filter) ||
                                                                         value.getValue.asInstanceOf[Mkb].getDiagName.contains(filter))).size>0)
      }
      diagnosisPropertyList.foreach(name => {
        val values = actionProperties.find(element => element._1.getType.getName.compareTo(name._2)==0).getOrElse(null)
        val mkbs = if (values!=null){
            values._2.foldLeft(new java.util.LinkedList[Mkb])(
            (mkbList, value) => {
              mkbList.add(value.getValue.asInstanceOf[Mkb])
              mkbList
            })
        } else new java.util.LinkedList[Mkb]
        map.put(name._1, mkbs)
      })
    }
    map
  }

  /*private def insertCompleteDiagnoses (eventId: Int, authData: AuthData) = {

    val now = new Date()

    val diag = dbCustomQueryBean.getDiagnosisForMainDiagInAppeal(eventId)
    if (diag != null) {
      val event = eventBean.getEventById(eventId)
      val diagnosis = new Diagnosis()
      diagnosis.setCreateDatetime(now)
      diagnosis.setModifyDatetime(now)
      diagnosis.setSetDate(now)
      diagnosis.setEndDate(now)
      diagnosis.setMkb(diag)
      diagnosis.setMkbExCode("")  //TODO: Уточнить! diag.getDiagName
      diagnosis.setPatient(event.getPatient)

      val diagnostic = new Diagnostic()
      diagnostic.setCreateDatetime(now)
      diagnostic.setModifyDatetime(now)
      diagnostic.setNotes("")
      diagnostic.setSetDate(now)
      diagnostic.setHospital(0)

      if (authData!=null) {
        diagnosis.setCreatePerson(authData.getUser)
        diagnosis.setModifyPerson(authData.getUser)
        diagnosis.setPerson(authData.getUser)

        diagnostic.setCreatePerson(authData.getUser)
        diagnostic.setModifyPerson(authData.getUser)
        diagnostic.setSpeciality(authData.getUser.getSpeciality)
      }

      diagnostic.setEvent(event)

      val diagType = this.getRbDiagnosisTypeById(1)
      if (diagType!=null) {
        diagnosis.setDiagnosisType(diagType)
        diagnostic.setDiagnosisType(diagType)
      }

      val rbResult = this.getRbResultById(15)
      if(rbResult!=null)
          diagnostic.setResult(rbResult)


      dbManager.persist(diagnosis)
      diagnostic.setDiagnosis(diagnosis)
      dbManager.persist(diagnostic)
    }
    true
  }*/

  private def getRbDiagnosisTypeById(id: Int): RbDiagnosisType = {
    val diagType = em.createQuery(DiagnosisTypeByIdQuery,classOf[RbDiagnosisType])
                      .setParameter("id", id)
                      .getResultList
    diagType.size match {
      case 0 => {null}
      case size => {
        diagType.foreach(dt=>em.detach(dt))
        diagType(0)
      }
    }
  }

  private def getRbResultById(id: Int): RbResult = {
    val rbResult = em.createQuery(RbResultByIdQuery,classOf[RbResult])
                      .setParameter("id", id)
                      .getResultList
    rbResult.size match {
      case 0 => {null}
      case size => {
        rbResult.foreach(res=>em.detach(res))
        rbResult(0)
      }
    }
  }

  def getAppealTypeCodesWithFlatDirectoryId (id: Int) = {
    val rbResult = em.createQuery(eventTypeCodesByFlatDirectoryIdQuery,classOf[String])
      .setParameter("id", id)
      .getResultList
    rbResult
  }

  def insertOrUpdateClientQuoting(dataEntry: QuotaEntry, eventId: Int, auth: AuthData) = {
    var lockId: Int = -1
    var oldQuota : ClientQuoting = null
    var clientQuoting : ClientQuoting = null
    var quotaVersion : Int = 0
    if (dataEntry.getId() > 0) {
      quotaVersion = dataEntry.getVersion
      oldQuota = ClientQuoting.clone(dbClientQuoting.getClientQuotingById(dataEntry.getId))
      lockId = appLock.acquireLock("Client_Quoting", oldQuota.getId.intValue(), oldQuota.getId.intValue(), auth)
    }
    try {
      val patient = eventBean.getEventById(eventId).getPatient
      val mkb = dbMkbBean.getMkbByCode(dataEntry.getMkb.getCode)
      var isPersist = true
      if (dataEntry.getId > 0) {
        isPersist = false
      }
      clientQuoting = dbClientQuoting.insertOrUpdateClientQuoting(dataEntry.getId,
                                                                      dataEntry.getVersion,
                                                                      dataEntry.getQuotaType.getId,
                                                                      dataEntry.getStatus.getId,
                                                                      dataEntry.getDepartment.getId,
                                                                      dataEntry.getAppealNumber,
                                                                      dataEntry.getTalonNumber,
                                                                      dataEntry.getStage.getId,
                                                                      dataEntry.getRequest.getId,
                                                                      mkb,
                                                                      patient,
                                                                      auth.getUser)
      if (isPersist) dbManager.persist(clientQuoting) else dbManager.merge(clientQuoting)
    } finally {
      if (lockId > 0) appLock.releaseLock(lockId)
    }
    clientQuoting
  }

  def getMonitoringInfo(eventId: Int, condition: Int, authData: AuthData)  = {
    val codes = asJavaSet(condition match {
      case 0 => Set("TEMPERATURE", "BPRAS","BPRAD", "PULS", "SP02", "RR", "STATE", "WB")
      case 1 => Set("K", "NA","CA", "GLUCOSE", "TP", "UREA", "TB", "CB")
      case _ => Set("TEMPERATURE", "BPRAS","BPRAD", "PULS", "SP02", "RR", "STATE", "WB")
    })
    val map = actionPropertyBean.getActionPropertiesByEventIdAndActionPropertyTypeCodes(eventId, codes)
    new MonitoringInfoListData(map, codes)
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

  val eventTypeCodesByFlatDirectoryIdQuery = """
    SELECT et.code
    FROM
      FDRecord fdr,
      FDFieldValue fdfv,
      EventType et
    WHERE
      fdr.flatDirectory.id = :id
    AND
      fdfv.pk.fdRecord.id = fdr.id
    AND
      et.code = fdfv.value
                                             """

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
      Event e
    WHERE
      e.deleted = 0
    AND
      e.eventType.code IN (
        SELECT fdfv.value
        FROM
          FDFieldValue fdfv
        WHERE
          fdfv.pk.fdRecord.flatDirectory.id = '%s'
      )
    %s
    %s
                                  """
  /*
      SELECT Max(et.id)
  FROM
    EventType et,
    FDFieldValue fdfv
  WHERE
    fdfv.pk.fdRecord.id = '%s'
  AND
    et.code = fdfv.value
   */
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
/*
    AND
      exists(
        SELECT ap3
        FROM ActionProperty ap3
                JOIN ap3.actionPropertyType apt3,
             APValueString apstr
        WHERE
          ap3.action = a2
        AND
          apt3.name = 'Патронаж'
        AND
          ap3.id = apstr.id.id
        AND
          apstr.value = 'Да'
      )
*/