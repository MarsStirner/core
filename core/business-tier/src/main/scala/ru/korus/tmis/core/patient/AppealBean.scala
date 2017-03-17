package ru.korus.tmis.core.patient

import java.lang.Boolean
import java.util
import java.util.Date
import javax.ejb.{EJB, Stateless}
import javax.persistence.{EntityManager, PersistenceContext}

import grizzled.slf4j.Logging
import org.apache.commons.lang.{ObjectUtils, StringUtils}
import org.joda.time.{DateTime, Years}
import org.slf4j.{Logger, LoggerFactory}
import ru.korus.tmis.core.auth.{AuthData, AuthStorageBeanLocal}
import ru.korus.tmis.core.data._
import ru.korus.tmis.core.database._
import ru.korus.tmis.core.database.common._
import ru.korus.tmis.core.database.finance.DbEventLocalContractLocal
import ru.korus.tmis.core.entity.model._
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.core.values.InfectionControl
import ru.korus.tmis.scala.util.{CAPids, ConfigManager, I18nable}

import scala.collection.JavaConversions
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import scala.language.reflectiveCalls
import scala.util.Try

@Stateless
class AppealBean extends AppealBeanLocal
with I18nable
with Logging
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
  var dbRbDiagnosisTypeBean: DbRbDiagnosisTypeBeanLocal = _

  @EJB
  var dbRbDiseaseCharacterBean: DbRbDiseaseCharacterBeanLocal = _

  @EJB
  var dbPatientBean: DbPatientBeanLocal = _

  @EJB
  var dbClientRelation: DbClientRelationBeanLocal = _

  @EJB
  var dbEventClientRelation: DbEventClientRelationBeanLocal = _

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

  @EJB
  private var dbRbResultBeanLocal: DbRbResultBeanLocal = _

  @EJB
  private var dbEventLocalContract: DbEventLocalContractLocal = _

  private class IndexOf[T](seq: Seq[T]) {
    def unapply(pos: T): Option[Int] = seq find (pos == _) map (seq indexOf _)
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
  def insertAppealForPatient(appealData: AppealData, patientId: Int, userData: AuthData, staff: Staff): Int = {

    //1. Event и проверка данных на валидность
    val newEvent = this.verificationData(patientId, staff, appealData, flgCreate = true)
    dbManager.persist(newEvent)
    val res = insertOrModifyAppeal(appealData, newEvent, flgCreate = true, userData, staff)
    updateTempInvalid(newEvent, appealData.data.tempInvalid, staff)
    res
  }

  def updateAppeal(appealData: AppealData, eventId: Int, userData: AuthData, staff: Staff): Int = {
    val newEvent = this.verificationData(eventId, staff, appealData, flgCreate = false)
    val res = insertOrModifyAppeal(appealData, newEvent, flgCreate = false, userData, staff)
    updateTempInvalid(newEvent, appealData.data.tempInvalid, staff)
    res
  }

  def insertOrModifyAppeal(appealData: AppealData, event: Event, flgCreate: Boolean, userData: AuthData, staff: Staff): Int = {

    var entities = Set.empty[AnyRef]
    val now = new Date()
    //2. Action

    var oldAction: Action = null // Action.clone(temp)
    var oldValues = Map.empty[ActionProperty, util.List[APValue]] //actionPropertyBean.getActionPropertiesByActionId(oldAction.getId.intValue)

    val temp = actionBean.getAppealActionByEventId(event.getId.intValue(), i18n("db.actionType.hospitalization.primary").toInt)
    var action: Action = null
    var list = List.empty[AnyRef]

    val data: AppealEntry = appealData.data
    if (flgCreate) {
      //Обновим контейнер новыми данными
      data.setId(event.getId.intValue())
      data.setNumber(event.getExternalId)
    } else {
      oldAction = Action.clone(temp)
      oldValues = actionPropertyBean.getActionPropertiesByActionId(oldAction.getId.intValue).toMap
    }

    if (temp == null) {
      action = actionBean.createAction(event.getId.intValue(),
        actionTypeBean.getActionTypeById(i18n("db.actionType.hospitalization.primary").toInt).getId.intValue(), userData, staff)
      action.setStatus(ActionStatus.FINISHED.getCode) //TODO: Материть Александра!
      dbManager.persist(action)
      list = actionPropertyTypeBean.getActionPropertyTypesByActionTypeId(i18n("db.actionType.hospitalization.primary").toInt).toList
    } else {
      action = actionBean.updateAction(temp.getId.intValue(), temp.getVersion.intValue, userData, staff)
      list = actionPropertyBean.getActionPropertiesByActionId(temp.getId.intValue).keySet.toList

      val list2 = actionPropertyTypeBean.getActionPropertyTypesByActionTypeId(i18n("db.actionType.hospitalization.primary").toInt)
        .toList
        .filter(p => {
          val filtred = list.filter(pp => pp.asInstanceOf[ActionProperty].getType.getId == p.getId)
          filtred == null || filtred.isEmpty
        })
      list2.foreach(ff => {
        //создание недостающих акшен пропертей
        val res = actionPropertyBean.createActionProperty(action, ff.getId.intValue(), staff)
        em.persist(res)
      })
      //пересобираем лист акшенПропертей
      list = actionPropertyBean.getActionPropertiesByActionId(temp.getId.intValue).keySet.toList
    }

    action.setIsUrgent(data.getUrgent)
    if (data.rangeAppealDateTime != null) {
      if (data.rangeAppealDateTime.getStart != null)
        action.setBegDate(data.rangeAppealDateTime.getStart)
      action.setEndDate(data.rangeAppealDateTime.getEnd)
    }

    /* if (!flgCreate)
       entities = entities + action*/

    //3. Action Property
    list.foreach(f => {
      val ap: ActionProperty =
        if (flgCreate && f.isInstanceOf[ActionPropertyType]) {
          val res = actionPropertyBean.createActionProperty(action, f.asInstanceOf[ActionPropertyType].getId.intValue(), staff)
          em.persist(res)
          res
        }
        else {
          val apUpdate = actionPropertyBean.updateActionProperty(f.asInstanceOf[ActionProperty].getId.intValue,
            f.asInstanceOf[ActionProperty].getVersion.intValue,
            staff)
          em.merge(apUpdate)
          apUpdate
        }
      /* if (!flgCreate)
         entities = entities + ap*/

      var values = this.getValueByCase(ap.getType.getId.intValue(), data)
      if (values != null) {
        values.size match {
          case 0 =>
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
          case _ =>
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
              val apv = em.merge(actionPropertyBean.setActionPropertyValue(ap, value, it))
              /* if (apv != null)
                 entities = entities + apv.unwrap*/
              it = it + 1
            })
        }
      }
    })

    if (!flgCreate) dbManager.mergeAll(entities) else dbManager.persistAll(entities)

    if (!flgCreate) {
      //Редактирование обращения (В случае если изменен НИБ)
      var flgEventRewrite = true
      if (data.number != null &&
        !data.number.isEmpty &&
        event.getExternalId.compareTo(data.number) != 0) {

        //проверка НИБ на уникальность
        if (this.checkAppealNumber(data.number) == false)
          throw new CoreException("Номер истории болезни в запросе (НИБ = %s) отличается от текущего (НИБ = %s) и не является уникальным".format(data.number, event.getExternalId))

        event.setExternalId(data.number)
        event.setModifyDatetime(now)
        event.setModifyPerson(staff)
        //event.setExecDate(now)

        flgEventRewrite = true
      }
      if (data.refuseAppealReason != null && !data.refuseAppealReason.isEmpty) {
        revokeAppealById(event, 15, staff)
        //this.insertCompleteDiagnoses(appealData.data.id, authData)   //Старый вариант (заменено кодом ниже)
        //final диагноз
        val admissionMkb = dbCustomQueryBean.getDiagnosisForMainDiagInAppeal(data.id)
        if (admissionMkb != null) {
          var map = Map.empty[String, util.Set[AnyRef]]
          map += ("finalMkb" -> Set[AnyRef]((-1, "", Integer.valueOf(admissionMkb.getId.intValue), 0, 0)))
          val diag = diagnosisBean.insertDiagnoses(data.id, null, mapAsJavaMap(map), staff)
          diag.filter(p => p.isInstanceOf[Diagnostic]).toList.foreach(f => f.asInstanceOf[Diagnostic].setResult(this.getRbResultById(15)))
          dbManager.persistAll(diag)
        }
        flgEventRewrite = true
      }
      if (flgEventRewrite) {
        dbManager.merge(event)
      }
    }

    //******
    //Создание/Редактирование записей для законных представителей
    val clientRelations = if (data.getHospitalizationWith == null) {
      new util.LinkedList[LegalRepresentativeContainer]()
    } else {
      data.getHospitalizationWith
    }
    var setRel = Set.empty[ClientRelation]
    //Если законные представители заполнены
    val patient = event.getPatient
    val eventClientRelationList = dbEventClientRelation.getByEvent(event)

    clientRelations.foreach(f => {
      val curEventClientRel: EventClientRelation = eventClientRelationList.find(ecl => ecl.getClientRelation.getRelativeType.getId.intValue() == f.getRelativeType.getId)
        .getOrElse({
          val parent = dbPatientBean.getPatientById(f.getRelative.getId)
          val tempServerRelation = dbClientRelation.createClientRelationByRelativePerson(
            f.getRelativeType.getId,
            parent,
            patient,
            staff)

          val res = dbEventClientRelation.insertOrUpdate(event, tempServerRelation, f.getNote)
          em.flush()
          setRel += tempServerRelation
          res
        })
      if (!curEventClientRel.getClientRelation.getRelative.getId.equals(f.getRelative.getId) ||
        !curEventClientRel.getNote.equals(f.getNote)) {
        curEventClientRel.getClientRelation.setRelative(dbPatientBean.getPatientById(f.getRelative.getId))
        curEventClientRel.setNote(f.getNote)
        em.merge(curEventClientRel)
      }
    })
    eventClientRelationList.foreach(f => {
      val cr = clientRelations.find(cl => f.getClientRelation.getRelativeType.getId.intValue() == cl.getRelativeType.getId)
        .getOrElse({
          f.setDeleted(true)
          em.merge(f)
        })
    })
    if (setRel != null && setRel.nonEmpty) dbManager.mergeAll(setRel)
    //*****
    //Создание/редактирование записи для Event_Persons
    if (flgCreate)
      setExecPersonForAppeal(event.getId.intValue(), 0, staff, ExecPersonSetType.EP_CREATE_APPEAL)

    //Создание/редактирование диагнозов (отд. записи)
    //val diagnosisInfo = createDiagnosticAndDiagnosis(data, event, staff)
    //Создание/редактирование диагнозов (отд. записи)
    var map = Map.empty[String, java.util.Set[AnyRef]]
    var diagFlatCodes: Set[String] = Set()
    appealData.data.diagnoses.foreach(f => if (f.getDiagnosisKind != null && !f.getDiagnosisKind.isEmpty) diagFlatCodes += f.getDiagnosisKind)
    diagFlatCodes.foreach(flatCode => {
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
    val diagnoses = diagnosisBean.insertDiagnoses(event.getId.intValue(), null, mapAsJavaMap(map), staff)
    //Установка локальных контрактов из входных данных (если необходимо)
    val eventLocalContract = createEventLocalContract(data, event)
    event.getId.intValue()
  }

  /**
   * Если необходимо, то установить локальный контракт для обращения
   * @param data  данные с фронтенда (обращение)
   * @param event модифицируемое обращение
   * @return созданный локальный контракт для обращения \ null если контракт не был создан или в обращении не нужно его создавать
   */
  def createEventLocalContract(data: AppealEntry, event: Event): EventLocalContract = {
    if (data.getPayer != null && data.getPaymentContract != null) {
      val res: EventLocalContract = dbEventLocalContract.insertOrUpdate(event, data.getPayer, data.getPaymentContract)
      event.setEventLocalContract(res)
      res
    } else {
      null
    }
  }

  /*
  def createDiagnosticAndDiagnosis(data: AppealEntry, event: Event, staff: Staff): util.Map[Diagnosis, util.List[Diagnostic]] = {
    val result = new util.LinkedHashMap[Diagnosis, util.List[Diagnostic]](data.diagnoses.size())
    for (x <- data.diagnoses) {
      if (x.getDiagnosticId == 0) {
        //Означает что диагнозы еще не были созданы,
        // поэтому ищем тут нужные данные и создаем записи
        // если нужных данных не находим - пропускаем и не создаем этот диагноз
        if (StringUtils.isNotEmpty(x.getDiagnosisKind) && StringUtils.isNotEmpty(x.getMkb.getCode) && x.getMkb.getId > 0) {
          val mkb: Mkb = dbMkbBean.getByCode(x.getMkb.getCode)
          if (mkb == null) {
            logger.error("Diagnosis skipped[{}]: Mkb code=\'{}\' not founded", x, x.getMkb.getCode: Any)
          } //else if (!ObjectUtils.equals(x.getMkb.getId, mkb.getId)) {
           // logger.error("Diagnosis skipped[%s]: Mkb code=\'%s\' identifiers mismatch: requested [%d] actual [%d] ".format(x, x.getMkb.getCode, x.getMkb.getId, mkb.getId.toInt))
          //} TODO вернуть эту проверку после исправления выбора МКБ из диалогового окна
          else {
            val diagnosisType: RbDiagnosisType = dbRbDiagnosisTypeBean.getRbDiagnosisTypeByFlatCode(x.getDiagnosisKind)
            if (diagnosisType == null) {
              logger.error("rbDiagnosisType with flatCode=\'%s\' not found".format(x.getDiagnosisKind))
            } else {
              val character: RbDiseaseCharacter = dbRbDiseaseCharacterBean.getDiseaseCharacterById(3)

              val diagnosis = diagnosisBean.insertDiagnosis(staff, event.getPatient, diagnosisType, character, mkb)
              val diagnostic = diagnosisBean.insertDiagnostic(staff, event, null, diagnosis, diagnosisType, character, x.getDescription)
              val diagnosticList = new util.ArrayList[Diagnostic](1)
              diagnosticList.add(diagnostic)
              result.put(diagnosis, diagnosticList)
            }
          }
        }
      } else {
        //Идентификатор диагностики уже есть, значит либо меняем данные, либо удаляем их (если МКБ пустое)
        if (StringUtils.isEmpty(x.getMkb.getCode) || x.getMkb.getId <= 0) {
          //Код МКБ пуст либо идентифкатор МКБ менее или равен нулю
          //Значит нужно удалить диагностику и диагноз
          val deletedDiagnostic = diagnosisBean.deleteDiagnosis(event, x.getDiagnosticId)
          logger.info("After delete Diagnostic[{}] is {}", x.getDiagnosticId, deletedDiagnostic)
        } else {
          //Диагнозы не удаляются и существуют - будем их менять
          val mkb: Mkb = dbMkbBean.getByCode(x.getMkb.getCode)
          if (mkb == null) {
            logger.error("Diagnosis skipped[{}]: Mkb code=\'{}\' not founded", x, x.getMkb.getCode: Any)
          } // else if (!ObjectUtils.equals(x.getMkb.getId, mkb.getId)) {
          // logger.error("Diagnosis skipped[%s]: Mkb code=\'%s\' identifiers mismatch: requested [%d] actual [%d] ".format(x, x.getMkb.getCode, x.getMkb.getId, mkb.getId.toInt))
          //} TODO вернуть эту проверку после исправления выбора МКБ из диалогового окна
         else {
            val diagnosisType: RbDiagnosisType = dbRbDiagnosisTypeBean.getRbDiagnosisTypeByFlatCode(x.getDiagnosisKind)
            if (diagnosisType == null) {
              logger.error("rbDiagnosisType with flatCode=\'%s\' not found".format(x.getDiagnosisKind))
            } else {
              val character: RbDiseaseCharacter = dbRbDiseaseCharacterBean.getDiseaseCharacterById(3)
              val oldDiagnostic = diagnosisBean.getDiagnostic(x.getDiagnosticId)
              if (oldDiagnostic == null) {
                logger.warn("Diagnostic[{}] doesnt exists! Create new!", x.getDiagnosticId)
                //Означает что диагнозы еще не были созданы. т.к  не найдено диагностики по ее идшнику с фронтенда => создаем новые
                val diagnosis = diagnosisBean.insertDiagnosis(staff, event.getPatient, diagnosisType, character, mkb)
                val diagnostic = diagnosisBean.insertDiagnostic(staff, event, null, diagnosis, diagnosisType, character, x.getDescription)
                val diagnosticList = new util.ArrayList[Diagnostic](1)
                diagnosticList.add(diagnostic)
                result.put(diagnosis, diagnosticList)
              } else {
                //oldDoiagnostic найдена
                val diagnosis = diagnosisBean.modifyDiagnosis(staff, event.getPatient, diagnosisType, character, mkb, oldDiagnostic.getDiagnosis)
                val diagnostic = diagnosisBean.modifyDiagnostic(staff, event, null, diagnosis, diagnosisType, character, x.getDescription, oldDiagnostic)
                val diagnosticList = new util.ArrayList[Diagnostic](1)
                diagnosticList.add(diagnostic)
                result.put(diagnosis, diagnosticList)
              }
            }
          }
        }
      }
    }
    result
  }
  */

  def getAppealById(id: Int): util.HashMap[Event, util.Map[Action, util.Map[Object, util.List[Object]]]] = {
    //запрос  данных из Эвента
    val event = dbEventBean.getEventById(id)
    if (event == null) {
      throw new CoreException("Обращение с id=%d не найдено в БД".format(id))
    }
    //запрос данных из Action
    val action = actionBean.getAppealActionByEventId(event.getId.intValue(), i18n("db.actionType.hospitalization.primary").toInt)
    /*if (action == null) {
      throw new CoreException(ConfigManager.ErrorCodes.ActionNotFound, "Невозможно открыть обращение[id=%d], т.к. в нем отсутствует действие Поступление".format(id))
    }*/
    //Запрос данных из ActionProperty
    val findMapActionProperty = if (action == null) {
      new java.util.HashMap[ActionProperty, java.util.List[APValue]]
    } else {
      actionPropertyBean.getActionPropertiesByActionId(action.getId.intValue())
    }

    val values: java.util.Map[(java.lang.Integer, ActionProperty), java.util.List[Object]] = findMapActionProperty.foldLeft(new java.util.HashMap[(java.lang.Integer, ActionProperty), java.util.List[Object]])(
      (str_key, el) => {
        val (ap, apvs) = el
        val aptId = ap.getType.getId.intValue()
        val rbCap = dbRbCoreActionPropertyBean.getRbCoreActionPropertiesByActionPropertyTypeId(aptId)
        if (rbCap != null) {
          val key = (rbCap.getId, ap)
          val list: java.util.List[Object] = new util.ArrayList[Object]
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
                             authData: AuthData): AppealSimplifiedDataList = {

    //TODO: подключить анализ авторизационных данных и доступных ролей
    //requestData.setRecordsCount(dbCustomQueryBean.getCountOfAppealsWithFilter(requestData.filter))
    val map = dbCustomQueryBean.getAllAppealsWithFilter(requestData.page - 1, requestData.limit, requestData.sortingFieldInternal, requestData.sortingMethod, requestData.filter, requestData.rewriteRecordsCount _)
    new AppealSimplifiedDataList(map, requestData)
  }

  def checkAppealNumber(number: String): Boolean = {
    val result = dbEventBean.getAllAppealsForReceivedPatientByPeriod(0, 1, "id", "desc", new ReceivedRequestDataFilter(number))
    result == null || result.isEmpty
  }


  def revokeAppealById(event: Event, resultId: Int, staff: Staff): Event = {
    if (event == null) {
      throw new CoreException("Не указано редактируемое обращение")
    }
    //Закрываем госпитализацию с причиной отказа
    val now = new Date()
    event.setModifyDatetime(now)
    event.setModifyPerson(staff)
    event.setExecDate(now)
    try {
      event.setResult(dbRbResultBean.getRbResultById(resultId)) //какой-то айдишник =)
    }
    catch {
      case e: Exception =>
        error("revokeAppealById >> Ошибка при закрытии госпитализации: %s".format(e.getMessage))
        throw new CoreException("Ошибка при закрытии госпитализации (id = %s)".format(event.getId.toString))
    }
    event
  }

  def getPatientsHospitalizedStatus(eventId: Int): String = {
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
        if (apCancelWithValues.exists(element => element._2.size() > 0)) {
          status = i18n("patient.status.canceled").toString + ": " + apCancelWithValues.iterator.next()._2.get(0).getValueAsString
        } else {
          status = i18n("patient.status.discharged").toString + ": " + ConfigManager.DateFormatter.format(execDate)
        }
      } else {
        if (apSentToWithValues.exists(element => element._2.size() > 0)) {
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
    if (datePeriod.start != null && datePeriod.end != null && (datePeriod.start after datePeriod.end))
      throw new CoreException(i18n("error.appeal.create.InvalidPeriod"))

    val maxDiffYears: Int = 3
    val years: Int = Years.yearsBetween(new DateTime(datePeriod.getStart), new DateTime()).getYears
    if (Math.abs(years) > maxDiffYears - 1) {
      throw new CoreException(i18n("error.appeal.create.InvalidPeriod.StartDate", maxDiffYears))
    }
  }

  def updateTempInvalid(event: Event, tempInvalidCont: TempInvalidAppealContainer, staff: Staff) {
    if (tempInvalidCont != null &&
      tempInvalidCont.serial != null &&
      tempInvalidCont.number != null) {

      var tempInvalid = if (event.getId == null) new TempInvalid() else dbTempInvalidBean.getTempInvalidByEventId(event.getId)
      if (tempInvalid == null) {
        tempInvalid = new TempInvalid()
      }
      tempInvalid.setEvent(event)
      dbTempInvalidBean.insertOrUpdateTempInvalid(tempInvalid,
        tempInvalidCont.begDate, //Date beginDate,
        tempInvalidCont.endDate, //Date endDate,
        0, //short docType,
        if (tempInvalidCont.isByService) "09" else "01", //int reasonId,
        tempInvalidCont.begDate, //Date caseBegDate,
        tempInvalidCont.serial, //String serial,
        tempInvalidCont.number, //String number,
        0, //short sex,
        0, //byte age,
        0, //int duration,
        0, //short closed,
        event.getPatient, // Patient patient,
        staff //Staff sessionUser)
      )
    }
  }

  @throws(classOf[CoreException])
  def verificationData(id: Int, staff: Staff, appealData: AppealData, flgCreate: Boolean): Event = {
    //для создания ид пациента, для редактирование ид обращения

    if (staff == null)
      throw new CoreException(i18n("error.appeal.create.NoAuthData"))


    var event: Event = null
    val now: Date = new Date
    if (flgCreate) {
      //Создаем новое
      if (id <= 0) {
        throw new CoreException(i18n("error.appeal.create.InvalidPatientData").format(id))
      }

      if (appealData.data.appealType.eventType != null && appealData.data.appealType.eventType.id < 1) {
        val et = dbEventTypeBean.getEventTypeByCode(appealData.data.appealType.eventType.getCode)
        appealData.data.appealType.eventType.id = et.getId
      }

      if (appealData.data.appealType == null ||
        appealData.data.appealType.eventType == null ||
        appealData.data.appealType.eventType.getId <= 0) {
        throw new CoreException(i18n("error.appeal.create.NoAppealType"))
      }

      if (appealData.data.contract != null && appealData.data.contract.getId < 1) {
        val contract: Contract = dbContractBean.getContractByNumber(appealData.data.contract.getNumber)
        appealData.data.contract.id = contract.getId
      }

      if (appealData.data.contract == null || appealData.data.contract.getId < 1)
        throw new CoreException(i18n("error.appeal.create.InvalidContractData"))

      val contract = dbContractBean.getContractById(appealData.data.contract.getId)

      if (contract.getEndDate.getTime < now.getTime)
        throw new CoreException(i18n("error.appeal.create.ContractIsExpired"))

      checkAppealBegEndDate(appealData.getData.rangeAppealDateTime)

      val patient = dbPatientBean.getPatientById(id)
      val et = dbEventTypeBean.getEventTypeById(appealData.data.appealType.eventType.getId)
      if (!et.isAgeValid(patient.getBirthDate))
        throw new CoreException(i18n("error.appeal.create.InvalidPatientAge").format(et.getName, et.getAge))

      val patientSex = patient.getSex match {
        case 0 => Sex.UNDEFINED
        case 1 => Sex.MEN
        case 2 => Sex.WOMEN
      }

      if (!et.isSexValid(patientSex))
        throw new CoreException(i18n("error.appeal.create.InvalidPatientSex").format(et.getName, et.getSex))

      val result: RbResult = if (appealData.data.result == null) null
      else
        dbRbResultBeanLocal.getRbResultByCodeAndEventType(dbEventTypeBean.getEventTypeById(appealData.data.appealType.eventType.getId), appealData.data.result.code)

      val acheResult: RbAcheResult = if (appealData.data.result == null) null
      else
        dbRbResultBeanLocal.getRbAcheResultByCodeAndEventType(dbEventTypeBean.getEventTypeById(appealData.data.appealType.eventType.getId), appealData.data.acheResult.code)

      val execPerson = if (appealData.data.execPerson == null || appealData.data.execPerson.getId == 0) null
      else
        dbStaff.getStaffById(appealData.data.execPerson.getId)

      event = dbEventBean.createEvent(id,
        //dbEventBean.getEventTypeIdByFDRecordId(appealData.data.appealType.getId()),
        appealData.data.appealType.eventType.getId,
        //dbEventBean.getEventTypeIdByRequestTypeIdAndFinanceId(appealData.data.appealType.requestType.getId(), appealData.data.appealType.finance.getId()),
        appealData.data.rangeAppealDateTime.getStart,
        /*appealData.data.rangeAppealDateTime.getEnd()*/ null,
        appealData.getData.getContract.getId,
        result,
        acheResult,
        execPerson,
        staff)
    }
    else {
      //Редактирование
      event = dbEventBean.getEventById(id)
      if (event == null) {
        throw new CoreException("Обращение с id = %s не найдено в БД".format(appealData.data.id.toString))
      }

      event.setModifyDatetime(now)
      event.setModifyPerson(staff)
      dbManager.merge(event)
      event.setSetDate(appealData.data.rangeAppealDateTime.getStart)
      event.setEventType(dbEventTypeBean.getEventTypeById(appealData.data.appealType.eventType.getId))
    }

    val hosptype = if (appealData.data.hospitalizationType != null) appealData.data.hospitalizationType.getId else 0
    if (hosptype > 0) {
      //val value = dbFDRecordBean.getFDRecordById(hosptype)
      //if (value!=null) {            //TODO: ! Материть Сашу
      // 'Порядок наступления (1-плановый, 2-экстренный, 3-самотёком, 4-принудительный)',
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
      val isPrim = if (value.contains("первично")) 1
      else if (value.contains("повторно")) 2
      else if (value.contains("активное посещение")) 3
      else if (value.contains("перевозка")) 4
      else if (value.contains("амбулаторно")) 5
      else 0 //TODO: ! Материть Сашу
      event.setIsPrimary(isPrim)
    } else event.setIsPrimary(0)

    event.setOrganisation(organizationBeanLocal.getOrganizationById(ConfigManager.Common.OrgId))

    event
  }

  private def AnyToSetOfString(that: AnyRef, sec: String): Set[String] = {
    that match {
      case null => null //В случае если не обрабатываем проперти вернем нулл (чтобы не переписывать значения)
      case x: Date => Set(ConfigManager.DateFormatter.format(that))
      case x: IdNameContainer => if (x.getId > 0) Set(x.getId.toString) else Set.empty[String]
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
      case _ => try {
        Set(that.toString)
      } catch {
        case e: Exception => throw new CoreException("Не могу преобразовать данные типа: %s в строковый массив".format(that.getClass.getName))
      }
    }
  }

  private def getValueByCase(aptId: Int, data: AppealEntry) = {

    val listNdx = new IndexOf(list)
    val cap = dbRbCoreActionPropertyBean.getRbCoreActionPropertiesByActionPropertyTypeId(aptId)
    if (cap == null) {
      this.AnyToSetOfString(null, "")
    } else {
      try {
        cap.getId.intValue() match {
          case listNdx(0) => this.AnyToSetOfString(data.assignment.directed, "") //Кем направлен
          case listNdx(1) => this.AnyToSetOfString(data.assignment.number, "") //Номер направления
          case listNdx(2) => this.AnyToSetOfString(data.deliveredType, "") //Кем доставлен
          case listNdx(3) => this.writeMKBDiagnosesFromAppealData(data.diagnoses, "diagReceivedMkb", isDesc = false) //Диагноз направившего учреждения
          case listNdx(4) => this.AnyToSetOfString(data.deliveredAfterType, "") //Доставлен в стационар от начала заболевания
          case listNdx(5) => this.AnyToSetOfString(null, "") //Направлен в отделение
          case listNdx(6) => this.AnyToSetOfString(data.refuseAppealReason, "") //Причина отказа в госпитализации
          case listNdx(7) => this.AnyToSetOfString(data.appealWithDeseaseThisYear, "") //Госпитализирован по поводу данного заболевания в текущем году
          case listNdx(8) => this.AnyToSetOfString(data.movingType, "") //Вид транспортировки
          case listNdx(9) => this.AnyToSetOfString(null, "") //Профиль койки
          case listNdx(10) => this.AnyToSetOfString(data.stateType, "") //Доставлен в состоянии опьянения
          case listNdx(11) => this.AnyToSetOfString(data.injury, "") //Травма
          case listNdx(12) => this.AnyToSetOfString(data.assignment.assignmentDate, "") //Дата направления
          case listNdx(13) => this.AnyToSetOfString(data.hospitalizationChannelType, "") //Канал госпитализации
          case listNdx(14) => this.AnyToSetOfString(data.assignment.doctor, "") //Направивший врач
          case listNdx(15) => this.writeMKBDiagnosesFromAppealData(data.diagnoses, "diagReceivedMkb", isDesc = true) //Клиническое описание
          case listNdx(16) => this.writeMKBDiagnosesFromAppealData(data.diagnoses, "aftereffectMkb", isDesc = false) //Диагноз направившего учреждения (осложнения)
          case listNdx(17) => this.writeMKBDiagnosesFromAppealData(data.diagnoses, "aftereffectMkb", isDesc = true) //Клиническое описание (осложнения)
          case listNdx(18) => this.AnyToSetOfString(data.ambulanceNumber, "") //Номер наряда СП
          case listNdx(19) => this.AnyToSetOfString(java.lang.Double.valueOf(data.physicalParameters.bloodPressure.left.diast), "") //Артериальное давление (левая рука Диаст)
          case listNdx(20) => this.AnyToSetOfString(java.lang.Double.valueOf(data.physicalParameters.bloodPressure.left.syst), "") //Артериальное давление (левая рука Сист)
          case listNdx(21) => this.AnyToSetOfString(java.lang.Double.valueOf(data.physicalParameters.temperature), "") //t температура тела
          case listNdx(22) => this.AnyToSetOfString(java.lang.Double.valueOf(data.physicalParameters.weight), "") //Вес при поступлении
          case listNdx(23) => this.AnyToSetOfString(java.lang.Double.valueOf(data.physicalParameters.height), "") //Рост
          case listNdx(24) => this.AnyToSetOfString(data.agreedType, "") //Тип согласования
          case listNdx(25) => this.AnyToSetOfString(data.agreedDoctor, "") //Комментарий к согласованию
          case listNdx(26) => this.AnyToSetOfString(data.hospitalizationWith, "relative") //Законный представитель
          case listNdx(27) => this.AnyToSetOfString(data.hospitalizationType, "") //Тип госпитализации
          case listNdx(28) => this.AnyToSetOfString(data.hospitalizationPointType, "") //Цель госпитализации
          case listNdx(29) => this.writeMKBDiagnosesFromAppealData(data.diagnoses, "attendantMkb", isDesc = false) //Диагноз направившего учреждения (сопутствующий)
          case listNdx(30) => this.writeMKBDiagnosesFromAppealData(data.diagnoses, "attendantMkb", isDesc = true) //Клиническое описание (сопутствующий)
          case listNdx(31) => this.AnyToSetOfString(java.lang.Double.valueOf(data.physicalParameters.bloodPressure.right.diast), "") //Артериальное давление (правая рука)
          case listNdx(32) => this.AnyToSetOfString(java.lang.Double.valueOf(data.physicalParameters.bloodPressure.right.syst), "")
          case listNdx(33) => this.AnyToSetOfString(data.hospitalizationWith, "note") //Примечание
          case listNdx(34) => this.AnyToSetOfString(data.orgStructStay.toString, "orgStructStay") //Отделение поступления
          case listNdx(35) => this.AnyToSetOfString(data.orgStructDirectedFrom.toString, "orgStructDirectedFrom") //Напрвлен из
          case listNdx(36) => this.AnyToSetOfString(data.reopening, "reopening") //Переоткрытие ИБ
          case _ => this.AnyToSetOfString(null, "")
        }
      } catch {
        case e: NullPointerException => this.AnyToSetOfString(null, "")
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
  private def writeMKBDiagnosesFromAppealData(list_dia: util.LinkedList[DiagnosisContainer], cell: String, isDesc: Boolean): Set[String] = {

    var valueSet = Set.empty[String]
    if (list_dia == null)
      return valueSet

    list_dia.filter(element => element.diagnosisKind.compare(cell) == 0).toList.foreach(dc => {
      if (isDesc) {
        //Получаем набор описаний
        valueSet += dc.description.toString
      } else {
        try {
          if (dc.mkb.getCode != null) {
            var mkb: Mkb = null
            try {
              mkb = dbMkbBean.getMkbByCode(dc.mkb.getCode.toString)
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
  def getDiagnosisListByAppealId(eventId: Int, filter: String): util.HashMap[String, util.List[Mkb]] = {
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
        actionProperties = actionProperties.filter(element => element._2.exists(value => value.getValue.asInstanceOf[Mkb].getDiagID.contains(filter) ||
          value.getValue.asInstanceOf[Mkb].getDiagName.contains(filter)))
      }
      diagnosisPropertyList.foreach(name => {
        val values = actionProperties.find(element => element._1.getType.getName.compareTo(name._2) == 0).orNull
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

  private def getRbDiagnosisTypeById(id: Int): RbDiagnosisType = {
    val diagType = em.createQuery(DiagnosisTypeByIdQuery, classOf[RbDiagnosisType])
      .setParameter("id", id)
      .getResultList
    diagType.size match {
      case 0 =>
        null
      case size =>
        diagType(0)
    }
  }

  private def getRbResultById(id: Int): RbResult = {
    val rbResult = em.createQuery(RbResultByIdQuery, classOf[RbResult])
      .setParameter("id", id)
      .getResultList
    rbResult.size match {
      case 0 =>
        null
      case size =>
        rbResult(0)
    }
  }

  def getAppealTypeCodesWithFlatDirectoryId(id: Int): util.List[String] = {
    val rbResult = em.createQuery(eventTypeCodesByFlatDirectoryIdQuery, classOf[String])
      .setParameter("id", id)
      .getResultList
    rbResult
  }

  def getSupportedAppealTypeCodes: util.List[String] = {
    val rbResult = em.createQuery(eventSupportedTypeCodesQuery, classOf[String])
      .setParameter("supportedRequestTypes", asJavaCollection(i18n("webmis.supportedEventTypes").split(",")))
      .getResultList
    rbResult
  }

  def getMonitoringInfo(eventId: Int, condition: Int, authData: AuthData): MonitoringInfoListData = {
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

  def getInfectionMonitoring(patient: Patient): util.TreeSet[(String, Date, Date, util.List[Integer], Integer)] = {
    val IC = InfectionControl
    val infectIntervalCode = IC.allInfectPrefixes.foldLeft(new java.util.LinkedList[String]())((l, c) => {
      l add (c + IC.separator + IC.beginDatePostfix)
      l add (c + IC.separator + IC.endDatePostfix)
      l
    })
    val qInfectActionIds =
      """SELECT DISTINCT ap.action.id FROM ActionProperty ap WHERE ap.action.event.patient = :patient
        AND ap.action.event.deleted = false
        AND ap.action.deleted = false
        AND ap.deleted = false
        AND ap.actionPropertyType.code IN :infectIntervalCode
        AND ap.action.actionType.code IN :documents
        AND exists ( SELECT apvd.id FROM APValueDate apvd WHERE apvd.id.id = ap.id )
      """
    val actionIds = em.createQuery(qInfectActionIds, classOf[java.util.List[Integer]])
      .setParameter("patient", patient)
      .setParameter("infectIntervalCode", infectIntervalCode)
      .setParameter("documents", IC.documents.asJava)
      .getResultList
    val q =
      """SELECT ap FROM  ActionProperty ap WHERE ap.action.id IN :actionIds
        |AND ap.deleted = false""".stripMargin
    val properties: util.List[ActionProperty] = if (actionIds.isEmpty) new java.util.LinkedList[ActionProperty]
    else em.createQuery(q, classOf[ActionProperty])
      .setParameter("actionIds", actionIds)
      .getResultList
    val r = properties
      .filter(e => e.getType.getCode != null && IC.allInfectPrefixes.exists(p => e.getType.getCode.startsWith(p)))
      .groupBy(e => (e.getAction.getId, e.getType.getCode.split(IC.separator).head))
      .flatMap(e => {
        val actionId = e._1._1
        val list = e._2
        val name = {
          list.find(p => p.getType.getCode.equals(e._1._2)) match {
            case Some(x) =>
              if (x.getType.getCode.endsWith(IC.customInfectionPostfix)) // Поля "Другое", название инфекции получаем из значения свойства
                actionPropertyBean.getActionPropertyValue(x).headOption match {
                  case Some(y) => Some(y.getValue.toString)
                  case _ => None
                }
              else Some(x.getType.getName)
            case None => None
          }
        }
        val beginDate = list.find(p => p.getType.getCode.equals(e._1._2 + IC.separator + IC.beginDatePostfix))
        val endDate = list.find(p => p.getType.getCode.equals(e._1._2 + IC.separator + IC.endDatePostfix))

        (name, beginDate, endDate) match {
          case (Some(x), Some(y), Some(z)) =>
            val bd = actionPropertyBean.getActionPropertyValue(y)
            val ed = actionPropertyBean.getActionPropertyValue(z)
            (bd.size(), ed.size()) match {
              case (1, 1) => (bd.head, ed.head) match {
                case (begin: APValueDate, end: APValueDate) => Some((x, begin.getValue, end.getValue, actionId, new Integer(y.getIdx)))
                case _ => None
              }
              case (1, 0) => bd.head match {
                case b: APValueDate => Some(x, b.getValue, null, actionId, new Integer(y.getIdx))
                case _ => None
              }
              case (0, 1) => ed.head match {
                case end: APValueDate => Some(x, end.getValue, null, actionId, new Integer(y.getIdx))
                case _ => None
              }
              case _ => None
            }
          case _ => None
        }
      })
      .groupBy(p => (p._1, p._2, p._5)).map(e => (e._1._1, e._1._2, Try(e._2.toList.filter(_._3 != null).sortBy(_._3).last._3).getOrElse(null), e._2.map(_._4).toList.asJava, e._1._3))

    // Безумная сортировка - сначала по дате начала, потом по порядку расположения на форме редактирования (idx свойства "Дата начала")
    val result = new util.TreeSet[(String, Date, Date, util.List[Integer], Integer)](new util.Comparator[(String, Date, Date, util.List[Integer], Integer)] {
      override def compare(o1: (String, Date, Date, util.List[Integer], Integer), o2: (String, Date, Date, util.List[Integer], Integer)): Int = {
        val dateOrder = o2._2.compareTo(o1._2)
        val idxOrder = o2._5.compareTo(o1._5)
        if (dateOrder != 0)
          dateOrder
        else if (idxOrder != 0)
          idxOrder
        else if (o1.equals(o2))
          0
        else
          1
      }
    })

    r.foreach(result.add)
    result
  }


  def getInfectionDrugMonitoring(patient: Patient): util.TreeSet[(String, Date, Date, String, util.List[Integer], Integer)] = {
    val IC = InfectionControl

    val q =
      """SELECT ap FROM Event e, Action a, ActionProperty ap WHERE
        |e.patient = :patient
        |AND a.event = e AND a.deleted = false
        |AND ap.action = a AND ap.deleted = false
        |AND (ap.actionPropertyType.code LIKE "infectProphylaxisName%" OR ap.actionPropertyType.code LIKE "infectEmpiricName%" OR ap.actionPropertyType.code LIKE "infectTelicName%")
        |AND a.actionType.code IN :documents""".stripMargin
    val r = em.createQuery(q, classOf[ActionProperty])
      .setParameter("patient", patient)
      .setParameter("documents", IC.documents.asJava)
      .getResultList
      .groupBy(e => (e.getAction.getId, e.getType.getCode.split('_').last))
      .flatMap(e => {
        val drugId = e._1._2
        val list = e._2
        val actionId = e._1._1

        val (therapyType, prefix) = if (list.find(p => p.getType.getCode.startsWith("infectProphylaxisName")) != null) {
          ("Профилактика", "infectProphylaxisName")
        }
        else if (list.find(p => p.getType.getCode.startsWith("infectEmpiricName")) != null) {
          ("Эмпирическая", "infectEmpiricName")
        }
        else if (list.find(p => p.getType.getCode.startsWith("infectTelicName")) != null) {
          ("Целенаправленная", "infectTelicName")
        }
        else null

        val name = list.find(p => p.getType.getCode.equals(prefix + "DrugName_" + drugId))
        val beginDate = list.find(p => p.getType.getCode.equals(prefix + "BeginDate_" + drugId))
        val endDate = list.find(p => p.getType.getCode.equals(prefix + "EndDate_" + drugId))

        (name, beginDate) match {
          case (Some(a), Some(b)) =>
            val n = actionPropertyBean.getActionPropertyValue(a)
            val bd = actionPropertyBean.getActionPropertyValue(b)
            val ed = if (endDate.isEmpty) null else actionPropertyBean.getActionPropertyValue(endDate.get)


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

                  Some((x.getValue.trim, y.getValue, e, therapyType, actionId, new Integer(b.getIdx))) // Убираем пробелы - интерфейс иногда вставляет их вперед
                case _ => None
              }
              case _ => None
            }
          case _ => None
        }
      })
      .groupBy(e => (e._1, e._2, e._4, e._6))
      .map(e => (e._1._1, e._1._2, Try(e._2.filter(_._3 != null).toList.sortBy(_._3).last._3).getOrElse(null), e._1._3, e._2.map(_._5).toList.asJava, e._1._4))

    // Безумная сортировка - сначала по полю "Тип терапии", дате начала, потом по порядку расположения на форме редактирования (idx свойства "Дата начала")
    val result = new util.TreeSet[(String, Date, Date, String, util.List[Integer], Integer)](new util.Comparator[(String, Date, Date, String, util.List[Integer], Integer)] {
      override def compare(o1: (String, Date, Date, String, util.List[Integer], Integer), o2: (String, Date, Date, String, util.List[Integer], Integer)): Int = {

        val tt1 = if (o1._4 != null) o1._4.trim else " " // Убираем пробелы - интерфейс иногда вставляет их вперед
        val tt2 = if (o2._4 != null) o2._4.trim else " "

        // Тип терапии бывает Профилактика, Целенаправленная, Эмпирическая и может отсутствовать
        // выводитб требуется в данном порядке, на случай путацицы написания, сравниваю только первую букву в верхнем регистре
        if (!tt1.head.equals(tt2.head)) {
          if (tt1.toUpperCase.startsWith("П"))
            return -1
          else if (tt1.toUpperCase.startsWith("Э") && !tt2.toUpperCase.startsWith("П"))
            return 1
          else if (tt1.toUpperCase.startsWith("Ц") && !tt2.toUpperCase.startsWith("П") && !tt2.toUpperCase.startsWith("Э"))
            return 1
          else
            return -1
        }

        val dateOrder = o2._2.compareTo(o1._2)
        val idxOrder = o2._6.compareTo(o1._6)
        if (dateOrder != 0)
          dateOrder
        else if (idxOrder != 0)
          idxOrder
        else if (o1.equals(o2))
          0
        else
          1
      }
    })
    r.foreach(result.add)
    result
  }

  def getSurgicalOperations(eventId: Int, authData: AuthData): SurgicalOperationsListData = {
    val codes = setAsJavaSet(Set("operationName", "complicationName", "methodAnesthesia"))
    val map = actionPropertyBean.getActionPropertiesByEventIdsAndActionPropertyTypeCodes(List(Integer.valueOf(eventId)), codes, Int.MaxValue, true)
    if (map != null && map.contains(Integer.valueOf(eventId)))
      new SurgicalOperationsListData(map.get(Integer.valueOf(eventId)), actionPropertyBean.getActionPropertiesByActionIdAndTypeTypeNames)
    else
      new SurgicalOperationsListData()
  }

  def setExecPersonForAppeal(id: Int, personId: Int, staff: Staff, epst: ExecPersonSetType): Boolean = {

    var eventPerson: EventPerson = null
    var execPerson: Staff = staff
    var isCreate: Boolean = true

    val event = epst.getVarId match {
      case 1 => actionBean.getActionById(id).getEvent
      case _ => dbEventBean.getEventById(id)
    }

    if (epst.isFindLast) {
      eventPerson = dbEventPerson.getLastEventPersonForEventId(event.getId.intValue())
      if (personId > 0) execPerson = dbStaff.getStaffById(personId)
      isCreate = (eventPerson == null
        || eventPerson.getPerson != execPerson
        || event.getExecutor == null
        || event.getExecutor.getId != personId)
    }

    if (isCreate) {
      dbEventPerson.insertOrUpdateEventPerson(epst.getEventPersonId(eventPerson),
        event,
        execPerson,
        epst.getFlushFlag)

      //Изменим запись о назначевшем враче в ивенте
      event.setExecutor(execPerson)
      event.setModifyDatetime(new Date())
      event.setModifyPerson(staff)
      dbManager.merge(event)

      true
    }
    else
      false
  }

  val eventTypeCodesByFlatDirectoryIdQuery =
    """
    SELECT et.code
    FROM
      FDRecord fdr,
      FDFieldValue fdfv,
      EventType et
    WHERE
      fdr.flatDirectory.id = :id
    AND
      fdfv.record.id = fdr.id
    AND
      et.code = fdfv.value
    """

  val eventSupportedTypeCodesQuery =
    """
     SELECT et.code
      FROM
        EventType et
      WHERE
        et.requestType.code IN :supportedRequestTypes
    """

  val DiagnosisTypeByIdQuery =
    """
    SELECT dt
    FROM
      RbDiagnosisType dt
    WHERE
      dt.id = :id
    """

  val RbResultByIdQuery =
    """
    SELECT r
    FROM
      RbResult r
    WHERE
      r.id = :id
    """
}