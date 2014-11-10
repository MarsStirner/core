package ru.korus.tmis.core.patient

import java.util

import grizzled.slf4j.Logging
import ru.korus.tmis.core.entity.model._
import ru.korus.tmis.core.data._
import javax.ejb.{EJB, Stateless}
import ru.korus.tmis.core.database._
import common._
import ru.korus.tmis.core.values.InfectionControl
import scala.collection.JavaConversions._
import ru.korus.tmis.core.auth.{AuthStorageBeanLocal, AuthData}
import javax.persistence.{PersistenceContext, EntityManager}
import java.util.{ArrayList, Date, LinkedList}
import ru.korus.tmis.core.exception.CoreException
import collection.JavaConversions

import ru.korus.tmis.scala.util.{CAPids, I18nable, ConfigManager}
import org.joda.time.{DateTime, Years}
import scala.language.reflectiveCalls
import scala.collection.JavaConverters._
import scala.util.Try

@Stateless
class AppealBean extends AppealBeanLocal
with Logging
with I18nable
with CAPids {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  @EJB
  var organizationBeanLocal: DbOrganizationBeanLocal = _

  @EJB
  var appLock: AuthStorageBeanLocal = _

  @EJB
  private var dbEventBean: DbEventBeanLocal = _

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

  @EJB
  private var dbStaff: DbStaffBeanLocal = _

  @EJB
  private var dbRbResultBean: DbRbResultBeanLocal = _

  @EJB
  private var dbContractBean: DbContractBeanLocal = _

  @EJB
  var dbTempInvalidBean: DbTempInvalidBeanLocal = _

/*
  @Inject
  @Any
  var actionEvent: javax.enterprise.event.Event[Notification] = _
*/

  private class IndexOf[T](seq: Seq[T]) {
    def unapply(pos: T) = seq find (pos == _) map (seq indexOf _)
  }

  val list = List(iCapIds("db.rbCAP.hosp.primary.id.directed").toInt, //Кем направлен
    iCapIds("db.rbCAP.hosp.primary.id.number").toInt, //№ направления
    iCapIds("db.rbCAP.hosp.primary.id.deliveredType").toInt, //Кем доставлен
    iCapIds("db.rbCAP.hosp.primary.id.diagnosis.assigment.code").toInt, //Диагноз направившего учреждения
    iCapIds("db.rbCAP.hosp.primary.id.deliveredAfterType").toInt, //Доставлен в стационар от начала заболевания
    iCapIds("db.rbCAP.hosp.primary.id.sentTo").toInt, //Направлен в отделение
    iCapIds("db.rbCAP.hosp.primary.id.cancel").toInt, //Причина отказа в госпитализации
    iCapIds("db.rbCAP.hosp.primary.id.appealWithDeseaseThisYear").toInt, //Госпитализирован по поводу данного заболевания в текущем году
    iCapIds("db.rbCAP.hosp.primary.id.transportationType").toInt, //Вид транспортировки
    iCapIds("db.rbCAP.hosp.primary.id.placeType").toInt, //Профиль койки
    iCapIds("db.rbCAP.hosp.primary.id.drugsType").toInt, //Доставлен в состоянии опьянения
    iCapIds("db.rbCAP.hosp.primary.id.injury").toInt, //Травма
    iCapIds("db.rbCAP.hosp.primary.id.assignmentDate").toInt, //Дата направления
    iCapIds("db.rbCAP.hosp.primary.id.hospitalizationChannelType").toInt, //Канал госпитализации
    iCapIds("db.rbCAP.hosp.primary.id.doctor").toInt, //Направивший врач
    iCapIds("db.rbCAP.hosp.primary.id.diagnosis.assignment.description").toInt, //Клиническое описание
    iCapIds("db.rbCAP.hosp.primary.id.diagnosis.aftereffect.code").toInt, //Диагноз направившего учреждения (осложнения)
    iCapIds("db.rbCAP.hosp.primary.id.diagnosis.aftereffect.description").toInt, //Клиническое описание (осложнения)
    iCapIds("db.rbCAP.hosp.primary.id.ambulanceNumber").toInt, //№ наряда СП
    iCapIds("db.rbCAP.hosp.primary.id.bloodPressure.left.ADdiast").toInt, //Левая рука: АД диаст.
    iCapIds("db.rbCAP.hosp.primary.id.bloodPressure.left.ADsyst").toInt, //Левая рука: АД сист.
    iCapIds("db.rbCAP.hosp.primary.id.temperature").toInt, //t
    iCapIds("db.rbCAP.hosp.primary.id.weight").toInt, //Вес при поступлении
    iCapIds("db.rbCAP.hosp.primary.id.height").toInt, //Рост
    iCapIds("db.rbCAP.hosp.primary.id.agreedType").toInt, //Тип согласования
    iCapIds("db.rbCAP.hosp.primary.id.agreedDoctor").toInt, //Комментарий к согласованию
    iCapIds("db.rbCAP.hosp.primary.id.hospitalizationWith").toInt, //Законный представитель
    iCapIds("db.rbCAP.hosp.primary.id.hospitalizationType").toInt, //Тип госпитализации
    iCapIds("db.rbCAP.hosp.primary.id.hospitalizationPointType").toInt, //Цель госпитализации
    iCapIds("db.rbCAP.hosp.primary.id.diagnosis.attendant.code").toInt, //Диагноз направившего учреждения (сопутствующий)
    iCapIds("db.rbCAP.hosp.primary.id.diagnosis.attendant.description").toInt, //Клиническое описание (сопутствующий)
    iCapIds("db.rbCAP.hosp.primary.id.bloodPressure.right.ADdiast").toInt, //Правая рука: АД диаст.
    iCapIds("db.rbCAP.hosp.primary.id.bloodPressure.right.ADsyst").toInt, //Правая рука: АД сист.
    iCapIds("db.rbCAP.hosp.primary.id.note").toInt, //Примечание
    iCapIds("db.rbCap.host.primary.id.orgStructStay").toInt, //Отделение поступления
    iCapIds("db.rbCap.host.primary.id.orgStructDirectedFrom").toInt, //Направлен из
    iCapIds("db.rbCap.host.primary.id.reopening").toInt) //Переоткрытие ИБ

  //Insert or modify appeal
  def insertAppealForPatient(appealData: AppealData, patientId: Int, authData: AuthData) = {

    //1. Event и проверка данных на валидность
    val newEvent = this.verificationData(patientId, authData, appealData, true)
    dbManager.persist(newEvent)
    dbManager.detach(newEvent)
    val res = insertOrModifyAppeal(appealData, newEvent, true, authData)
    updateTempInvalid(newEvent, appealData.data.tempInvalid, authData)
    res
  }

  def updateAppeal(appealData: AppealData, eventId: Int, authData: AuthData) = {
    val newEvent = this.verificationData(eventId, authData, appealData, false)
    val res = insertOrModifyAppeal(appealData, newEvent, false, authData)
    updateTempInvalid(newEvent, appealData.data.tempInvalid, authData)
    res
  }

  def insertOrModifyAppeal(appealData: AppealData, event: Event, flgCreate: Boolean, authData: AuthData) = {

    var entities = Set.empty[AnyRef]
    val now = new Date()
    var newEvent = event
    //2. Action

    var oldAction: Action = null // Action.clone(temp)
    var oldValues = Map.empty[ActionProperty, java.util.List[APValue]] //actionPropertyBean.getActionPropertiesByActionId(oldAction.getId.intValue)
    var lockId: Int = -1

    val temp = actionBean.getAppealActionByEventId(newEvent.getId.intValue(), i18n("db.actionType.hospitalization.primary").toInt)
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
          actionTypeBean.getActionTypeById(i18n("db.actionType.hospitalization.primary").toInt).getId.intValue(),
          authData)
        action.setStatus(ActionStatus.FINISHED.getCode) //TODO: Материть Александра!
        dbManager.persist(action)
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
        list2.foreach(ff => {
          //создание недостающих акшен пропертей
          val res = actionPropertyBean.createActionProperty(action, ff.getId.intValue(), authData)
          em.persist(res)
        })
        //пересобираем лист акшенПропертей
        list = actionPropertyBean.getActionPropertiesByActionId(temp.getId.intValue).keySet.toList
      }

      action.setIsUrgent(appealData.data.getUrgent)
      if (appealData.data.rangeAppealDateTime != null) {
        if (appealData.data.rangeAppealDateTime.getStart != null)
          action.setBegDate(appealData.data.rangeAppealDateTime.getStart)
        action.setEndDate(appealData.data.rangeAppealDateTime.getEnd)
      }

      if (!flgCreate)
        entities = entities + action

      //3. Action Property

      list.foreach(f => {
        val ap: ActionProperty =
          if (flgCreate && f.isInstanceOf[ActionPropertyType]) {
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

        var values = this.getValueByCase(ap.getType.getId.intValue(), appealData, authData)
        if (values != null) {
          values.size match {
            case 0 => {
              if (flgCreate) {
                //В случае, если не приходит значение для ActionProperty, то записываем значение по умолчанию.
                val defValue = ap.getType.getDefaultValue
                if (defValue != null && !defValue.trim.isEmpty) {
                  val apv = actionPropertyBean.setActionPropertyValue(ap, defValue, 0)
                  if (apv != null)
                    entities = entities + apv.unwrap
                }
              } else {
                //Если пришел пустой список, а старые значения есть, то зачистим их
                val apvs = actionPropertyBean.getActionPropertyValue(ap)
                if (apvs != null && apvs.size() > 0) {
                  for (i <- 0 until apvs.size) {
                    var apv = apvs(i).unwrap()
                    apv = em.merge(apv)
                    em.remove(apv)
                  }
                }
              }
            }
            case _ => {
              if (ap.getType.getIsVector) {
                //Если вектор, то сперва зачищаем старый список
                val apvs = actionPropertyBean.getActionPropertyValue(ap)
                if (apvs != null && apvs.size() > values.size) {
                  for (i <- values.size to apvs.size - 1) {
                    //если новых значений меньше тем старых, то хвост зачистим
                    var apv = apvs(i).unwrap()
                    apv = em.merge(apv)
                    em.remove(apv)
                  }
                }
              }
              var it = 0
              // Если не пришло отделение поступления - проставляем приемное отделение
              val pTypeId = dbRbCoreActionPropertyBean.getRbCoreActionPropertiesByActionPropertyTypeId(ap.getType.getId.intValue()).getId.toInt
              val storedpTypeId = iCapIds("db.rbCap.host.primary.id.orgStructStay").toInt
              if (pTypeId.equals(storedpTypeId) && values.head.equals("0"))
                values = Set(i18n("db.dayHospital.id"))

              values.foreach(value => {
                val apv = actionPropertyBean.setActionPropertyValue(ap, value, it)
                if (apv != null)
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
      if (lockId > 0) appLock.releaseLock(lockId)
    }

    if (!flgCreate) {
      //Редактирование обращения (В случае если изменен НИБ)
      var flgEventRewrite = true
      if (appealData.data.number != null &&
        !appealData.data.number.isEmpty &&
        newEvent.getExternalId.compareTo(appealData.data.number) != 0) {

        //проверка НИБ на уникальность
        if (this.checkAppealNumber(appealData.data.number) == false)
          throw new CoreException("Номер истории болезни в запросе (НИБ = %s) отличается от текущего (НИБ = %s) и не является уникальным".format(appealData.data.number, newEvent.getExternalId))

        newEvent.setExternalId(appealData.data.number)
        newEvent.setModifyDatetime(now)
        newEvent.setModifyPerson(authData.user)
        //newEvent.setExecDate(now)

        flgEventRewrite = true
      }
      if (appealData.data.refuseAppealReason != null && !appealData.data.refuseAppealReason.isEmpty) {
        newEvent = this.revokeAppealById(newEvent, 15, authData)
        //this.insertCompleteDiagnoses(appealData.data.id, authData)   //Старый вариант (заменено кодом ниже)
        //final диагноз
        val admissionMkb = dbCustomQueryBean.getDiagnosisForMainDiagInAppeal(appealData.data.id)
        if (admissionMkb != null) {
          var map = Map.empty[String, java.util.Set[AnyRef]]
          map += ("finalMkb" -> Set[AnyRef]((-1, "", Integer.valueOf(admissionMkb.getId.intValue), 0, 0)))
          val diag = diagnosisBean.insertDiagnoses(appealData.data.id, mapAsJavaMap(map), authData)
          diag.filter(p => p.isInstanceOf[Diagnostic]).toList.foreach(f => f.asInstanceOf[Diagnostic].setResult(this.getRbResultById(15)))
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
    if (clientRelations != null && clientRelations.size() > 0) {
      //Если законные представители заполнены
      val patient = newEvent.getPatient
      val serverRelations = patient.getActiveClientRelatives()

      clientRelations.foreach(f => {
        val serverRelation = serverRelations.find(element => ((element.getRelative.getId.intValue() == f.getRelative.getId) &&
          (element.getRelativeType.getId.intValue() == f.getRelativeType.getId)))
          .getOrElse(null)
        if (serverRelation == null) {
          val updateRelation = serverRelations.find(element => element.getRelative.getId.intValue() == f.getRelative.getId).getOrElse(null)
          val relationId = if (updateRelation == null) -1 else updateRelation.getId.intValue()
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
    if (setRel != null && setRel.size > 0) dbManager.mergeAll(setRel)
    //*****
    //Создание/редактирование записи для Event_Persons
    if (flgCreate)
      setExecPersonForAppeal(newEvent.getId.intValue(), 0, authData, ExecPersonSetType.EP_CREATE_APPEAL)
    //dbEventPerson.insertOrUpdateEventPerson(0, newEvent, authData.getUser, true) //в ивенте только создание

    //Создание/редактирование диагнозов (отд. записи)
    var map = Map.empty[String, java.util.Set[AnyRef]]
    Set(i18n("appeal.diagnosis.diagnosisKind.diagReceivedMkb"),
      i18n("appeal.diagnosis.diagnosisKind.aftereffectMkb"),
      i18n("appeal.diagnosis.diagnosisKind.attendantMkb")).foreach(flatCode => {
      val values = appealData.data.diagnoses.filter(p => p.getDiagnosisKind.compareTo(flatCode) == 0)
        .map(f => {
        var mkb: Mkb = null
        try {
          mkb = dbMkbBean.getMkbByCode(f.getMkb.getCode)
        } catch {
          case e: Exception => mkb = null
        }
        (Integer.valueOf(f.getDiagnosticId),
          f.getDescription,
          if (mkb != null) Integer.valueOf(mkb.getId.intValue) else -1,
          0, // characterId
          0) // stageId
      })
        .toSet[AnyRef]
      map += (flatCode -> values)
    })
    val diagnoses = diagnosisBean.insertDiagnoses(newEvent.getId.intValue(), mapAsJavaMap(map), authData)
    val mergedItems = diagnoses.filter(p => (p.isInstanceOf[Diagnosis] &&
      p.asInstanceOf[Diagnosis].getId != null &&
      p.asInstanceOf[Diagnosis].getId.intValue() > 0) ||
      (p.isInstanceOf[Diagnostic] &&
        p.asInstanceOf[Diagnostic].getId != null &&
        p.asInstanceOf[Diagnostic].getId.intValue() > 0)
    ).toList
    val persistedItems = diagnoses.filter(p => (p.isInstanceOf[Diagnosis] &&
      (p.asInstanceOf[Diagnosis].getId == null ||
        p.asInstanceOf[Diagnosis].getId.intValue() <= 0)) ||
      (p.isInstanceOf[Diagnostic] &&
        (p.asInstanceOf[Diagnostic].getId == null ||
          p.asInstanceOf[Diagnostic].getId.intValue() <= 0))
    ).toList
    dbManager.mergeAll(mergedItems)
    dbManager.persistAll(persistedItems)

    newEvent.getId.intValue()
  }

  def getAppealById(id: Int) = {
    //запрос  данных из Эвента
    val event = dbEventBean.getEventById(id)
    if (event == null) {
      throw new CoreException("Обращение с id=%d не найдено в БД".format(id))
    }
    //запрос данных из Action
    val action = actionBean.getAppealActionByEventId(event.getId.intValue(), i18n("db.actionType.hospitalization.primary").toInt)
    if (action == null) {
      throw new CoreException(ConfigManager.ErrorCodes.ActionNotFound, "Невозможно открыть обращение[id=%d], т.к. в нем отсутствует действие Поступление".format(id))
    }
    //Запрос данных из ActionProperty
    val findMapActionProperty = actionPropertyBean.getActionPropertiesByActionId(action.getId.intValue())

    val values: java.util.Map[(java.lang.Integer, ActionProperty), java.util.List[Object]] = findMapActionProperty.foldLeft(new java.util.HashMap[(java.lang.Integer, ActionProperty), java.util.List[Object]])(
      (str_key, el) => {
        val (ap, apvs) = el
        val aptId = ap.getType.getId.intValue()
        val rbCap = dbRbCoreActionPropertyBean.getRbCoreActionPropertiesByActionPropertyTypeId(aptId)
        if (rbCap != null) {
          val key = (rbCap.getId, ap)
          val list: java.util.List[Object] = new ArrayList[Object]
          if (apvs != null && apvs.size > 0) {
            apvs.foreach(apv => list += (apv.getValue))
          }
          else
            list += null

          if (str_key.containsKey(key)) {
            list.addAll(str_key.get(key))
            str_key.remove(key)
          }
          str_key.put(key, list)
        } else {
          //Ворнинг! Нету такого типа экшн проперти в кореэкшнпропертитайп
        }
        str_key
      })

    val eventsMap = new java.util.HashMap[Event, java.util.Map[Action, java.util.Map[Object, java.util.List[Object]]]]
    val actionsMap = new java.util.HashMap[Action, java.util.Map[Object, java.util.List[Object]]]

    actionsMap.put(action, values.asInstanceOf[java.util.Map[Object, java.util.List[Object]]])
    eventsMap.put(event, actionsMap)

    eventsMap
  }

  def getAllAppealsByPatient(requestData: AppealSimplifiedRequestData,
                             authData: AuthData) = {

    //TODO: подключить анализ авторизационных данных и доступных ролей
    //requestData.setRecordsCount(dbCustomQueryBean.getCountOfAppealsWithFilter(requestData.filter))
    val map = dbCustomQueryBean.getAllAppealsWithFilter(requestData.page - 1, requestData.limit, requestData.sortingFieldInternal, requestData.sortingMethod, requestData.filter, requestData.rewriteRecordsCount _)
    new AppealSimplifiedDataList(map, requestData)
  }

  def checkAppealNumber(number: String) = {
    val result = dbEventBean.getAllAppealsForReceivedPatientByPeriod(0, 1, "id", "desc", new ReceivedRequestDataFilter(number))
    val isNumberFree = (result == null || result.length < 1)
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
      event.setResult(dbRbResultBean.getRbResultById(resultId)) //какой-то айдишник =)
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
    val event = dbEventBean.getEventById(eventId)
    val execDate = event.getExecDate

    var setATIds = JavaConversions.setAsJavaSet(Set(i18n("db.actionType.hospitalization.primary").toInt: java.lang.Integer))
    val hospId = actionBean.getLastActionByActionTypeIdAndEventId(eventId, setATIds)
    if (hospId > 0) {
      //Есть экшн - поступление
      val lstSentToIds = JavaConversions.seqAsJavaList(scala.List(i18n("db.rbCAP.hosp.primary.id.sentTo").toInt: java.lang.Integer))
      val lstCancelIds = JavaConversions.seqAsJavaList(scala.List(i18n("db.rbCAP.hosp.primary.id.cancel").toInt: java.lang.Integer))
      val apSentToWithValues = actionPropertyBean.getActionPropertiesByActionIdAndRbCoreActionPropertyIds(hospId, lstSentToIds)
      val apCancelWithValues = actionPropertyBean.getActionPropertiesByActionIdAndRbCoreActionPropertyIds(hospId, lstCancelIds)
      if (execDate != null) {
        if (apCancelWithValues != null &&
          apCancelWithValues.size() > 0 &&
          apCancelWithValues.filter(element => element._2.size() > 0).size > 0) {
          status = i18n("patient.status.canceled").toString + ": " + apCancelWithValues.iterator.next()._2.get(0).getValueAsString
        } else {
          status = i18n("patient.status.discharged").toString + ": " + ConfigManager.DateFormatter.format(execDate)
        }
      } else {
        if (apSentToWithValues != null &&
          apSentToWithValues.size() > 0 &&
          apSentToWithValues.filter(element => element._2.size() > 0).size > 0) {
          //Проверяем наличие экшна - Движение
          setATIds = JavaConversions.setAsJavaSet(Set(i18n("db.actionType.moving").toInt: java.lang.Integer))
          val movingId = actionBean.getLastActionByActionTypeIdAndEventId(eventId, setATIds)
          status = if (movingId > 0)
            if (actionBean.getActionById(movingId).getEndDate == null) i18n("patient.status.regToBed").toString else i18n("patient.status.sentTo").toString
          else i18n("patient.status.sentTo").toString
        } else {
          if (execDate != null)
            status = i18n("patient.status.discharged").toString
          else {
            setATIds = JavaConversions.setAsJavaSet(Set(i18n("db.actionType.primary").toInt: java.lang.Integer,
              i18n("db.actionType.secondary").toInt: java.lang.Integer))
            val primaryId = actionBean.getLastActionByActionTypeIdAndEventId(eventId, setATIds)
            status = if (primaryId > 0) i18n("patient.status.hospitalized").toString
            else i18n("patient.status.require").toString
          }
        }
      }
    } else {
      status = if (execDate != null) i18n("patient.status.discharged").toString
      else i18n("patient.status.require").toString
    }
    status
  }

  //Внутренние методы


  @throws(classOf[CoreException])
  def checkAppealBegEndDate(datePeriod: DatePeriodContainer) {
    if (datePeriod.start after datePeriod.end)
      throw new CoreException(i18n("error.appeal.create.InvalidPeriod"))

    val maxDiffYears: Int = 3
    val years: Int = Years.yearsBetween(new DateTime(datePeriod.getStart), new DateTime()).getYears
    if (Math.abs(years) > maxDiffYears - 1) {
      throw new CoreException(i18n("error.appeal.create.InvalidPeriod.StartDate", maxDiffYears))
    }
  }

  def updateTempInvalid(event: Event, tempInvalidCont: TempInvalidAppealContainer, authDate: AuthData) {
    if (tempInvalidCont != null &&
        tempInvalidCont.begDate != null &&
        tempInvalidCont.endDate != null &&
        tempInvalidCont.serial != null &&
        tempInvalidCont.number != null) {

      var tempInvalid = if(event.getId == null) new TempInvalid() else dbTempInvalidBean.getTempInvalidByEventId(event.getId)
      if (tempInvalid == null) {
        tempInvalid = new TempInvalid()
      }
      tempInvalid.setEvent(event)
      dbTempInvalidBean.insertOrUpdateTempInvalid(tempInvalid,
        tempInvalidCont.begDate,//Date beginDate,
        tempInvalidCont.endDate, //Date endDate,
        0,//short docType,
        if (tempInvalidCont.isByService) "09" else "01",//int reasonId,
        tempInvalidCont.begDate,//Date caseBegDate,
        tempInvalidCont.serial, //String serial,
        tempInvalidCont.number, //String number,
        0,//short sex,
        0,//byte age,
        0,//int duration,
        0,//short closed,
        event.getPatient,// Patient patient,
        authDate.getUser//Staff sessionUser)
      )
    }
  }

  @throws(classOf[CoreException])
  def verificationData(id: Int, authData: AuthData, appealData: AppealData, flgCreate: Boolean): Event = {
    //для создания ид пациента, для редактирование ид обращения

    if (authData == null)
      throw new CoreException(i18n("error.appeal.create.NoAuthData"))


    var event: Event = null
    val now: Date = new Date;
    if (flgCreate) {
      //Создаем новое
      if (id <= 0) {
        throw new CoreException(i18n("error.appeal.create.InvalidPatientData").format(id))
      }

      if (appealData.data.appealType == null ||
        appealData.data.appealType.eventType == null ||
        appealData.data.appealType.eventType.getId <= 0) {
        throw new CoreException(i18n("error.appeal.create.NoAppealType"))
      }

      if (appealData.data.contract == null || appealData.data.contract.getId < 1)
        throw new CoreException(i18n("error.appeal.create.InvalidContractData"))

      val contract = dbContractBean.getContractById(appealData.data.contract.getId)

      if (contract.getEndDate.getTime < now.getTime)
        throw new CoreException(i18n("error.appeal.create.ContractIsExpired"))

      checkAppealBegEndDate(appealData.getData.rangeAppealDateTime)

      event = dbEventBean.createEvent(id,
        //dbEventBean.getEventTypeIdByFDRecordId(appealData.data.appealType.getId()),
        appealData.data.appealType.eventType.getId,
        //dbEventBean.getEventTypeIdByRequestTypeIdAndFinanceId(appealData.data.appealType.requestType.getId(), appealData.data.appealType.finance.getId()),
        appealData.data.rangeAppealDateTime.getStart(),
        /*appealData.data.rangeAppealDateTime.getEnd()*/ null,
        appealData.getData.getContract.getId,
        authData)
    }
    else {
      //Редактирование
      event = dbEventBean.getEventById(id)
      if (event == null) {
        throw new CoreException("Обращение с id = %s не найдено в БД".format(appealData.data.id.toString))
        return null
      }

      event.setModifyDatetime(now)
      event.setModifyPerson(authData.user)
      event.setSetDate(appealData.data.rangeAppealDateTime.getStart())
      event.setEventType(dbEventTypeBean.getEventTypeById(appealData.data.appealType.eventType.getId))
      event.setVersion(appealData.getData().getVersion())
    }

    val hosptype = if (appealData.data.hospitalizationType != null) appealData.data.hospitalizationType.getId else 0
    if (hosptype > 0) {
      //val value = dbFDRecordBean.getFDRecordById(hosptype)
      //if (value!=null) {            //TODO: ! Материть Сашу
      val order = if ( /*value.getId.intValue()*/ hosptype == 220) 1
      else if ( /*value.getId.intValue()*/ hosptype == 221) 2
      else if ( /*value.getId.intValue()*/ hosptype == 222) 3
      else if ( /*value.getId.intValue()*/ hosptype == 223) 4
      else 0
      event.setOrder(order)
      //}
    } else event.setOrder(0)

    if (appealData.data.appealWithDeseaseThisYear != null && !appealData.data.appealWithDeseaseThisYear.isEmpty) {
      val value = appealData.data.appealWithDeseaseThisYear
      val isPrim = if (value.contains("первично")) 1 else if (value.contains("повторно")) 2 else 0 //TODO: ! Материть Сашу
      event.setIsPrimary(isPrim)
    } else event.setIsPrimary(0)

    event.setOrganisation(organizationBeanLocal.getOrganizationById(ConfigManager.Common.OrgId))

    return event
  }

  private def AnyToSetOfString(that: AnyRef, sec: String): Set[String] = {
    that match {
      case null => null //В случае если не обрабатываем проперти вернем нулл (чтобы не переписывать значения)
      case x: Date => Set(ConfigManager.DateFormatter.format(that))
      case x: IdNameContainer => if(x.getId > 0) Set(x.getId.toString) else Set.empty[String]
      case x: util.LinkedList[LegalRepresentativeContainer] =>
        var hospWith = Set.empty[String]
        x.foreach(e => {
          if (e.getRelative.getId > 0) {
            sec match {
              case "relative" => hospWith += e.getRelative.getId.toString
              case "note" => hospWith += e.getNote.toString
              case _ => hospWith += e.getRelative.getId.toString
            }
          }
        })
        hospWith
      case _ => try { Set(that.toString) } catch {
        case e: Exception => throw new CoreException("Не могу преобразовать данные типа: %s в строковый массив".format(that.getClass.getName))
      }
    }
  }

  private def getValueByCase(aptId: Int, appealData: AppealData, authData: AuthData) = {

    val listNdx = new IndexOf(list)
    val cap = dbRbCoreActionPropertyBean.getRbCoreActionPropertiesByActionPropertyTypeId(aptId)
    if (cap == null) {
      this.AnyToSetOfString(null, "")
    } else {
      cap.getId.intValue() match {
        case listNdx(0) => this.AnyToSetOfString(appealData.data.assignment.directed, "") //Кем направлен
        case listNdx(1) => this.AnyToSetOfString(appealData.data.assignment.number, "") //Номер направления
        case listNdx(2) => this.AnyToSetOfString(appealData.data.deliveredType, "") //Кем доставлен
        case listNdx(3) => this.writeMKBDiagnosesFromAppealData(appealData.data.diagnoses, "diagReceivedMkb", false) //Диагноз направившего учреждения
        case listNdx(4) => this.AnyToSetOfString(appealData.data.deliveredAfterType, "") //Доставлен в стационар от начала заболевания
        case listNdx(5) => this.AnyToSetOfString(null, "") //Направлен в отделение
        case listNdx(6) => this.AnyToSetOfString(appealData.data.refuseAppealReason, "") //Причина отказа в госпитализации
        case listNdx(7) => this.AnyToSetOfString(appealData.data.appealWithDeseaseThisYear, "") //Госпитализирован по поводу данного заболевания в текущем году
        case listNdx(8) => this.AnyToSetOfString(appealData.data.movingType, "") //Вид транспортировки
        case listNdx(9) => this.AnyToSetOfString(null, "") //Профиль койки
        case listNdx(10) => this.AnyToSetOfString(appealData.data.stateType, "") //Доставлен в состоянии опьянения
        case listNdx(11) => this.AnyToSetOfString(appealData.data.injury, "") //Травма
        case listNdx(12) => this.AnyToSetOfString(appealData.data.assignment.assignmentDate, "") //Дата направления
        case listNdx(13) => this.AnyToSetOfString(appealData.data.hospitalizationChannelType, "") //Канал госпитализации
        case listNdx(14) => this.AnyToSetOfString(appealData.data.assignment.doctor, "") //Направивший врач
        case listNdx(15) => this.writeMKBDiagnosesFromAppealData(appealData.data.diagnoses, "diagReceivedMkb", true) //Клиническое описание
        case listNdx(16) => this.writeMKBDiagnosesFromAppealData(appealData.data.diagnoses, "aftereffectMkb", false) //Диагноз направившего учреждения (осложнения)
        case listNdx(17) => this.writeMKBDiagnosesFromAppealData(appealData.data.diagnoses, "aftereffectMkb", true) //Клиническое описание (осложнения)
        case listNdx(18) => this.AnyToSetOfString(appealData.data.ambulanceNumber, "") //Номер наряда СП
        case listNdx(19) => this.AnyToSetOfString(java.lang.Double.valueOf(appealData.data.physicalParameters.bloodPressure.left.diast), "") //Артериальное давление (левая рука Диаст)
        case listNdx(20) => this.AnyToSetOfString(java.lang.Double.valueOf(appealData.data.physicalParameters.bloodPressure.left.syst), "") //Артериальное давление (левая рука Сист)
        case listNdx(21) => this.AnyToSetOfString(java.lang.Double.valueOf(appealData.data.physicalParameters.temperature), "") //t температура тела
        case listNdx(22) => this.AnyToSetOfString(java.lang.Double.valueOf(appealData.data.physicalParameters.weight), "") //Вес при поступлении
        case listNdx(23) => this.AnyToSetOfString(java.lang.Double.valueOf(appealData.data.physicalParameters.height), "") //Рост
        case listNdx(24) => this.AnyToSetOfString(appealData.data.agreedType, "") //Тип согласования
        case listNdx(25) => this.AnyToSetOfString(appealData.data.agreedDoctor, "") //Комментарий к согласованию
        case listNdx(26) => this.AnyToSetOfString(appealData.data.hospitalizationWith, "relative") //Законный представитель
        case listNdx(27) => this.AnyToSetOfString(appealData.data.hospitalizationType, "") //Тип госпитализации
        case listNdx(28) => this.AnyToSetOfString(appealData.data.hospitalizationPointType, "") //Цель госпитализации
        case listNdx(29) => this.writeMKBDiagnosesFromAppealData(appealData.data.diagnoses, "attendantMkb", false) //Диагноз направившего учреждения (сопутствующий)
        case listNdx(30) => this.writeMKBDiagnosesFromAppealData(appealData.data.diagnoses, "attendantMkb", true) //Клиническое описание (сопутствующий)
        case listNdx(31) => this.AnyToSetOfString(java.lang.Double.valueOf(appealData.data.physicalParameters.bloodPressure.right.diast), "") //Артериальное давление (правая рука)
        case listNdx(32) => this.AnyToSetOfString(java.lang.Double.valueOf(appealData.data.physicalParameters.bloodPressure.right.syst), "")
        case listNdx(33) => this.AnyToSetOfString(appealData.data.hospitalizationWith, "note") //Примечание
        case listNdx(34) => this.AnyToSetOfString(appealData.data.orgStructStay.toString, "orgStructStay") //Отделение поступления
        case listNdx(35) => this.AnyToSetOfString(appealData.data.orgStructDirectedFrom.toString, "orgStructDirectedFrom") //Напрвлен из
        case listNdx(36) => this.AnyToSetOfString(appealData.data.reopening, "reopening") //Переоткрытие ИБ
        case _ => this.AnyToSetOfString(null, "")
      }
    }
  }

  /**
   * Метод для получения списка идентификаторов диагнозов МКВ или клинических описаний по ключу.
   * @author Ivan Dmitriev
   * @param list_dia Сортированный лист структур с данными о диагнозах (DiagnosisContainer из структуры AppealData).
   * @param cell  Метка типа выводимого списка (фильтр данных из DiagnosisContainer по полю diagnosisKind).<pre>
   *              &#15; Возможные значения:
   *              &#15; "assignment" - Диагноз/Клиническое описание направившего учреждения
   *              &#15; "attendant" - Диагноз/Клиническое описание направившего учреждения (сопутствующий)
   *              &#15; "aftereffect" - Диагноз/Клиническое описание направившего учреждения (осложнения)</pre>
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
    if (list_dia == null)
      return valueSet

    list_dia.filter(element => element.diagnosisKind.compare(cell) == 0).toList.foreach(dc => {
      if (isDesc) {
        //Получаем набор описаний
        valueSet += dc.description.toString
      } else {
        try {
          if (dc.mkb.getCode() != null) {
            var mkb: Mkb = null
            try {
              mkb = dbMkbBean.getMkbByCode(dc.mkb.getCode().toString)
            } catch {
              case e: Exception => mkb = null
            }
            if (mkb != null && mkb.getId.intValue() > 0)
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
    val diagnosisPropertyList = Map("assignment" -> i18n("appeal.db.actionPropertyType.name.diagnosis.assigment.code").toString,
      "attendant" -> i18n("appeal.db.actionPropertyType.name.diagnosis.attendant.code").toString,
      "aftereffect" -> i18n("appeal.db.actionPropertyType.name.diagnosis.aftereffect.code").toString)

    val setATIds = JavaConversions.setAsJavaSet(Set(i18n("db.actionType.hospitalization.primary").toInt: java.lang.Integer))
    val actionId = actionBean.getLastActionByActionTypeIdAndEventId(eventId, setATIds)
    if (actionId > 0) {
      var actionProperties = actionPropertyBean.getActionPropertiesByActionId(actionId)
        .filter(element => element._1.getType.getName.contains(constPartToName))
      if (filter != null && !filter.isEmpty) {
        actionProperties = actionProperties.filter(element => element._2.filter(value => (value.getValue.asInstanceOf[Mkb].getDiagID.contains(filter) ||
          value.getValue.asInstanceOf[Mkb].getDiagName.contains(filter))).size > 0)
      }
      diagnosisPropertyList.foreach(name => {
        val values = actionProperties.find(element => element._1.getType.getName.compareTo(name._2) == 0).getOrElse(null)
        val mkbs = if (values != null) {
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
      val event = dbEventBean.getEventById(eventId)
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

  def getAppealTypeCodesWithFlatDirectoryId(id: Int) = {
    val rbResult = em.createQuery(eventTypeCodesByFlatDirectoryIdQuery, classOf[String])
      .setParameter("id", id)
      .getResultList
    rbResult
  }

  def getSupportedAppealTypeCodes = {
    val rbResult = em.createQuery(eventSupportedTypeCodesQuery, classOf[String])
      .setParameter("supportedRequestTypes", asJavaCollection(i18n("webmis.supportedEventTypes").split(",")))
      .getResultList
    rbResult
  }

  def insertOrUpdateClientQuoting(dataEntry: QuotaEntry, eventId: Int, auth: AuthData) = {
    var lockId: Int = -1
    var oldQuota: ClientQuoting = null
    var clientQuoting: ClientQuoting = null
    var quotaVersion: Int = 0
    if (dataEntry.getId() > 0) {
      quotaVersion = dataEntry.getVersion
      oldQuota = ClientQuoting.clone(dbClientQuoting.getClientQuotingById(dataEntry.getId))
      lockId = appLock.acquireLock("Client_Quoting", oldQuota.getId.intValue(), oldQuota.getId.intValue(), auth)
    }
    try {
      val event: Event = dbEventBean.getEventById(eventId)
      val patient = event.getPatient
      var mkb: Mkb = null
      try {
        mkb = dbMkbBean.getMkbByCode(dataEntry.getMkb.getCode)
      } catch {
        case e: Exception => mkb = null
      }
      var isPersist = true
      if (dataEntry.getId > 0) {
        isPersist = false
      }
      clientQuoting = dbClientQuoting.insertOrUpdateClientQuoting(dataEntry.getId,
        dataEntry.getVersion,
        dataEntry.getQuotaType.getId,
        dataEntry.getStatus.getId,
        dataEntry.getDepartment.getId,
        event.getExternalId,
        dataEntry.getTalonNumber,
        dataEntry.getStage.getId,
        dataEntry.getRequest.getId,
        mkb,
        patient,
        event,
        auth.getUser)
      if (isPersist) dbManager.persist(clientQuoting) else dbManager.merge(clientQuoting)
    } finally {
      if (lockId > 0) appLock.releaseLock(lockId)
    }
    clientQuoting
  }

  def getMonitoringInfo(eventId: Int, condition: Int, authData: AuthData) = {
    val codes = setAsJavaSet(condition match {
      case 0 => Set("TEMPERATURE", "BPRAS", "BPRAD", "PULS", "SPO2", "RR", "STATE", "WB", "GROWTH", "WEIGHT")
      case 1 => Set("K", "NA", "CA", "GLUCOSE", "TP", "UREA", "TB", "CB", "WBC", "GRAN", "NEUT", "HGB", "PLT")
      case _ => Set("TEMPERATURE", "BPRAS", "BPRAD", "PULS", "SPO2", "RR", "STATE", "WB", "GROWTH", "WEIGHT")
    })
    val map = actionPropertyBean.getActionPropertiesByEventIdsAndActionPropertyTypeCodes(List(Integer.valueOf(eventId)), codes, 5, false)
    if (map != null && map.contains(Integer.valueOf(eventId)))
      new MonitoringInfoListData(map.get(Integer.valueOf(eventId)))
    else
      new MonitoringInfoListData()
  }

  def getInfectionMonitoring(patient: Patient): java.util.Set[(String, Date, Date, java.util.List[Integer])] = {
    val IC = InfectionControl

    val q =
      """SELECT ap FROM Event e, Action a, ActionProperty ap WHERE
        |e.patient = :patient
        |AND a.event = e
        |AND ap.action = a
        |AND a.actionType.code IN :documents""".stripMargin
    em.createQuery(q, classOf[ActionProperty])
      .setParameter("patient", patient)
      .setParameter("documents", IC.documents.asJava)
      .getResultList
    .filter(e => e.getType.getCode != null && IC.allInfectPrefixes.exists(p => e.getType.getCode.startsWith(p)))
    .groupBy( e => (e.getAction.getId,  e.getType.getCode.split(IC.separator).head))
    .flatMap(e => {
      val actionId = e._1._1
      val list = e._2
      val name = {
        list.find( p => p.getType.getCode.equals(e._1._2)) match {
          case Some(x) => Some(x.getType.getName)
          case None => None
        }
      }
      val beginDate = list.find( p => p.getType.getCode.equals(e._1._2 + IC.separator + IC.beginDatePostfix))
      val endDate = list.find( p => p.getType.getCode.equals(e._1._2 + IC.separator + IC.endDatePostfix))

      (name, beginDate, endDate) match {
        case (Some(x), Some(y), Some(z)) =>
          val bd = actionPropertyBean.getActionPropertyValue(y)
          val ed = actionPropertyBean.getActionPropertyValue(z)
          (bd.size(), ed.size()) match {
            case (1, 1) => (bd.head, ed.head) match {
              case (begin: APValueDate, end: APValueDate) => Some((x, begin.getValue, end.getValue, actionId))
              case _ => None
            }
            case (1, 0) => bd.head match {
              case b: APValueDate => Some(x, b.getValue, null, actionId)
              case _ => None
            }
            case (0, 1) => ed.head match {
              case end: APValueDate => Some(x, end.getValue, null, actionId)
              case _ => None
            }
            case _ => None
          }
        case _ => None
      }
    })
    .groupBy(p => (p._1, p._2)).map(e => (e._1._1, e._1._2, Try(e._2.toList.filter(_._3 != null).sortBy(_._3).last._3).getOrElse(null), e._2.map(_._4).toList.asJava)).toSet.asJava
  }

  def getInfectionDrugMonitoring(patient: Patient): java.util.Set[(String, Date, Date, String, java.util.List[Integer])] = {
    val IC = InfectionControl

    val q =
      """SELECT ap FROM Event e, Action a, ActionProperty ap WHERE
        |e.patient = :patient
        |AND a.event = e
        |AND ap.action = a
        |AND ap.actionPropertyType.code IN :drugTherapyProperties
        |AND a.actionType.code IN :documents""".stripMargin
    em.createQuery(q, classOf[ActionProperty])
      .setParameter("patient", patient)
      .setParameter("documents", IC.documents.asJava)
      .setParameter("drugTherapyProperties", IC.drugTherapyProperties.asJava)
      .getResultList
      .groupBy(e => (e.getAction.getId,  e.getType.getCode.split('_').last))
      .flatMap(e => {
      val drugId = e._1._2
      val list = e._2
      val actionId = e._1._1

      val name = list.find( p => p.getType.getCode.equals(IC.infectDrugNamePrefix + '_' + drugId))
      val beginDate = list.find( p => p.getType.getCode.equals(IC.infectDrugBeginDatePrefix + '_' + drugId))
      val endDate = list.find( p => p.getType.getCode.equals(IC.infectDrugEndDatePrefix + '_' + drugId))
      val therapyType = list.find( p => p.getType.getCode.equals(IC.infectTherapyTypePrefix + '_' + drugId))

      (name, beginDate, endDate, therapyType) match {
        case (Some(a), Some(b), Some(c), Some(d)) =>
          val n = actionPropertyBean.getActionPropertyValue(a)
          val bd = actionPropertyBean.getActionPropertyValue(b)
          val ed = actionPropertyBean.getActionPropertyValue(c)
          val t = actionPropertyBean.getActionPropertyValue(d)

          (n.size(), bd.size()) match {
            case (1, 1) => (n.head, bd.head) match {
              case (x: APValueString, y: APValueDate) =>
                val e = ed.size() match {
                  case 1 => ed.head match {
                    case p: APValueDate => p.getValue
                    case _ => null
                  }
                  case _ => null
                }
                val ty = t.size() match {
                  case 1 => t.head match {
                    case p: APValueString => p.getValue
                    case _ => null
                  }
                  case _ => null
                }
                Some((x.getValue, y.getValue, e, ty, actionId))
              case _ => None
            }
            case _ => None
          }
        case _ => None
      }
    })
      .groupBy(e => (e._1, e._2, e._4))
      .map(e => (e._1._1, e._1._2, Try(e._2.filter(_._3 != null).toList.sortBy(_._3).last._3).getOrElse(null), e._1._3, e._2.map(_._5).toList.asJava)).toSet.asJava
  }

  def getSurgicalOperations(eventId: Int, authData: AuthData) = {
    val codes = setAsJavaSet(Set("operationName", "complicationName", "methodAnesthesia"))
    val map = actionPropertyBean.getActionPropertiesByEventIdsAndActionPropertyTypeCodes(List(Integer.valueOf(eventId)), codes, Int.MaxValue, true)
    if (map != null && map.contains(Integer.valueOf(eventId)))
      new SurgicalOperationsListData(map.get(Integer.valueOf(eventId)),
        actionPropertyBean.getActionPropertiesByActionIdAndTypeTypeNames _)
    else
      new SurgicalOperationsListData()
  }

  def setExecPersonForAppeal(id: Int, personId: Int, authData: AuthData, epst: ExecPersonSetType) = {

    var eventPerson: EventPerson = null
    var execPerson: Staff = authData.getUser
    var isCreate: Boolean = true

    val event = epst.getVarId match {
      case 1 => actionBean.getActionById(id).getEvent
      case _ => dbEventBean.getEventById(id)
    }

    if (epst.isFindLast) {
      eventPerson = dbEventPerson.getLastEventPersonForEventId(event.getId.intValue())
      if (personId > 0) execPerson = dbStaff.getStaffById(personId)
      isCreate = (eventPerson == null || eventPerson.getPerson != execPerson)
    }

    if (isCreate) {
      dbEventPerson.insertOrUpdateEventPerson(epst.getEventPersonId(eventPerson),
        event,
        execPerson,
        epst.getFlushFlag)

      //Изменим запись о назначевшем враче в ивенте
      event.setExecutor(execPerson)
      event.setModifyDatetime(new Date())
      event.setModifyPerson(authData.getUser)
      event.setVersion(event.getVersion)
      dbManager.merge(event)

      true
    }
    else
      false
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

  val eventSupportedTypeCodesQuery = """
     SELECT et.code
      FROM
        EventType et
      WHERE
        et.requestType.code IN :supportedRequestTypes
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
}