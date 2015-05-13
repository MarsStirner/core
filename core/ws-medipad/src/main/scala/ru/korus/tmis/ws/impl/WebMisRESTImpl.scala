package ru.korus.tmis.ws.impl

import java.net.URI
import java.util.Date
import java.{util => ju, lang}
import javax.ejb.{EJB, Stateless}
import javax.servlet.http.Cookie

import com.google.common.collect.Lists
import grizzled.slf4j.Logging
import org.codehaus.jackson.map.ObjectMapper
import org.joda.time.DateTime
import ru.korus.tmis.core.auth.{AuthData, AuthStorageBeanLocal, AuthToken}
import ru.korus.tmis.core.data._
import ru.korus.tmis.core.data.adapters.ISODate
import ru.korus.tmis.core.database._
import ru.korus.tmis.core.database.bak.{DbBbtResponseBeanLocal, DbBbtResultOrganismBeanLocal, DbBbtResultTextBeanLocal}
import ru.korus.tmis.core.database.common._
import ru.korus.tmis.core.database.finance.DbEventLocalContractLocal
import ru.korus.tmis.core.entity.model._
import ru.korus.tmis.core.entity.model.fd.APValueFlatDirectory
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.core.lock.ActionWithLockInfo
import ru.korus.tmis.core.notification.{DbNotificationActionBeanLocal, NotificationBeanLocal}
import ru.korus.tmis.core.patient._
import ru.korus.tmis.core.values.InfectionControl
import ru.korus.tmis.scala.util._
import ru.korus.tmis.ws.webmis.rest.{LockData, WebMisREST}

import scala.Predef._
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import scala.collection.mutable
import scala.language.reflectiveCalls

/**
 * User: idmitriev
 * Date: 3/19/13
 * Time: 11:53 AM
 */

@Stateless
class WebMisRESTImpl extends WebMisREST
with Logging
with I18nable
with CAPids {
  @EJB
  private var authStorage: AuthStorageBeanLocal = _

  @EJB
  private var patientBean: PatientBeanLocal = _

  @EJB
  private var actionBean: DbActionBeanLocal = _

  @EJB
  private var actionTypeBean: DbActionTypeBeanLocal = _

  @EJB
  private var actionPropertyBean: DbActionPropertyBeanLocal = _

  @EJB
  private var actionPropertyTypeBean: DbActionPropertyTypeBeanLocal = _

  @EJB
  private var appealBean: AppealBeanLocal = _

  @EJB
  private var dbStaff: DbStaffBeanLocal = _

  @EJB
  private var primaryAssessmentBean: PrimaryAssessmentBeanLocal = _

  @EJB
  private var dbOrgStructureBean: DbOrgStructureBeanLocal = _

  @EJB
  private var dbCustomQueryBean: DbCustomQueryLocal = _

  @EJB
  private var flatDirectoryBean: DbFlatDirectoryBeanLocal = _

  @EJB
  private var seventhFormBean: SeventhFormBeanLocal = _

  @EJB
  private var dbBloodTypeBean: DbRbBloodTypeBeanLocal = _

  @EJB
  private var dbRelationTypeBean: DbRbRelationTypeBeanLocal = _

  @EJB
  private var dbDocumentTypeBean: DbRbDocumentTypeBeanLocal = _

  @EJB
  private var dbOrganizationBean: DbOrganizationBeanLocal = _

  @EJB
  private var dbRbPolicyTypeBean: DbRbPolicyTypeBeanLocal = _

  @EJB
  private var dbRbSocTypeBean: DbRbSocTypeBeanLocal = _

  @EJB
  private var dbSchemeKladrBean: DbSchemeKladrBeanLocal = _

  @EJB
  private var hospitalBedBean: HospitalBedBeanLocal = _

  @EJB
  private var hospitalBedProfileBean: DbRbHospitalBedProfileBeanLocal = _

  @EJB
  private var assignmentBean: AssignmentBeanLocal = _

  @EJB
  private var dbRlsBean: DbRlsBeanLocal = _

  @EJB
  private var dbSpeciality: DbRbSpecialityBeanLocal = _

  @EJB
  private var dbRbContactType: DbRbContactTypeBeanLocal = _

  @EJB
  private var dbRbFinance: DbRbFinanceBeanLocal = _

  @EJB
  private var dbRbRequestTypes: DbRbRequestTypeBeanLocal = _

  @EJB
  private var dbEventBean: DbEventBeanLocal = _

  @EJB
  private var dbClientRelation: DbClientRelationBeanLocal = _

  @EJB
  private var dbRbQuotaStatus: DbRbQuotaStatusBeanLocal = _

  @EJB
  private var dbQuotaTypeBean: DbQuotaTypeBeanLocal = _

  @EJB
  private var dbClientQuoting: DbClientQuotingBeanLocal = _

  @EJB
  var dbEventPerson: DbEventPersonBeanLocal = _

  @EJB
  var dbEventTypeBean: DbEventTypeBeanLocal = _

  @EJB
  var dbContractBean: DbContractBeanLocal = _

  @EJB
  var dbJobTicketBean: DbJobTicketBeanLocal = _

  @EJB
  var dbRbTissueType: DbRbTissueTypeBeanLocal = _

  @EJB
  var dbRbOperationType: DbRbOperationTypeBeanLocal = _

  @EJB
  var directionBean: DirectionBeanLocal = _

  @EJB
  var dbDiagnosticBean: DbDiagnosticBeanLocal = _

  @EJB
  var diagnosisBean: DiagnosisBeanLocal = _

  @EJB
  var dbLayoutAttributeBean: DbLayoutAttributeBeanLocal = _

  @EJB
  var dbTempInvalidBean: DbTempInvalidBeanLocal = _

  @EJB
  var dbBbtResultTextBean: DbBbtResultTextBeanLocal = _

  @EJB
  var dbBbtResponseBean: DbBbtResponseBeanLocal = _

  @EJB
  var dbBbtResultOrganismBean: DbBbtResultOrganismBeanLocal = _

  @EJB
  var dbRBPrintTemplateBan: DbRbPrintTemplateBeanLocal = _

  @EJB
  var dbSettingsBean: DbSettingsBeanLocal = _

  @EJB
  var dbAutoSaveStorageLocal: DbAutoSaveStorageLocal = _

  @EJB
  var appLockBeanLocal: AppLockBeanLocal = _

  @EJB
  var notificationBeanLocal: NotificationBeanLocal = _

  @EJB
  var dbRbLaboratory: DbRbLaboratory = _

  @EJB
  var dbFdRecordBean: DbFDFieldBeanLocal = _

  @EJB
  var monitoringBeanLocal: MonitoringBeanLocal = _

  @EJB
  var dbNotificationActionBeanLocal: DbNotificationActionBeanLocal = _

  @EJB
  var dbEventClientRelation: DbEventClientRelationBeanLocal = _

  @EJB
  var dbEventLocalContractLocal: DbEventLocalContractLocal = _


  def getAllPatients(requestData: PatientRequestData, auth: AuthData): PatientData = {
    if (auth != null) {
      val patients = patientBean.getAllPatients(requestData)
      return new PatientData(Lists.newArrayList(patients), requestData)
    }
    throw new CoreException(
      i18n("error.cantGetPatients").format()
    )
  }

  def getPatientById(id: Int, auth: AuthData) = {
    if (auth != null) {
      val patient = patientBean.getPatientById(id)
      val map = patientBean.getKLADRAddressMapForPatient(patient)
      val street = patientBean.getKLADRStreetForPatient(patient)

      new PatientCardData(patient, map, street)
    }
    else
      throw new CoreException(
        i18n("error.cantGetPatient").format()
      )
  }

  def insertPatient(patientData: PatientCardData, auth: AuthData): PatientCardData = {
    //requiresPermissions(Array("clientAssessmentCreate"))
    val inPatientEntry = patientData.getData
    if (inPatientEntry != null) {
      if (auth != null) {
        val outPatientEntry: PatientEntry = patientBean.savePatient(-1, inPatientEntry, auth) //currentAuthData

        patientData.setData(outPatientEntry)
        return patientData
      }
    }

    throw new CoreException(
      i18n("error.cantSavePatient").format()
    )
  }

  def updatePatient(id: Int, patientData: PatientCardData, auth: AuthData): PatientCardData = {
    //requiresPermissions(Array("clientAssessmentCreate"))
    val inPatientEntry = patientData.getData
    if (inPatientEntry != null) {
      if (auth != null) {
        val outPatientEntry: PatientEntry = patientBean.savePatient(id, inPatientEntry, auth)
        patientData.setData(outPatientEntry)
        return patientData
      }
    }

    throw new CoreException(
      i18n("error.cantSavePatient").format()
    )
  }

  def checkExistanceNumber(name: String, typeId: Int, number: String, serial: String) = {
    if (name.compareTo("appealNumber") == 0) {
      new TrueFalseContainer(appealBean.checkAppealNumber(number))
    } else if (name.compareTo("SNILS") == 0) {
      new TrueFalseContainer(patientBean.checkSNILSNumber(number))
    } else if (name.compareTo("policy") == 0) {
      new TrueFalseContainer(patientBean.checkPolicyNumber(number, serial, typeId))
    } else {
      null: TrueFalseContainer
    }
  }

  //Insert or modify appeal
  def insertAppealForPatient(appealData: AppealData, patientId: Int, auth: AuthData) = {
    val ide = appealBean.insertAppealForPatient(appealData, patientId, auth)
    constructAppealData(ide)
  }

  def updateAppeal(appealData: AppealData, eventId: Int, auth: AuthData) = {
    val ide = appealBean.updateAppeal(appealData, eventId, auth)
    constructAppealData(ide)
  }

  private def constructAppealData(ide: Int): AppealData = {
    if (ide > 0) {
      val result = appealBean.getAppealById(ide)

      val positionE = result.iterator.next()
      val positionA = positionE._2.iterator.next()
      val values = positionA._2.asInstanceOf[java.util.Map[(java.lang.Integer, ActionProperty), java.util.List[Object]]]

      val mapper: ObjectMapper = new ObjectMapper()
      val patient = positionE._1.getPatient
      val map = patientBean.getKLADRAddressMapForPatient(patient)
      val street = patientBean.getKLADRStreetForPatient(patient)
      val currentDepartment = hospitalBedBean.getCurrentDepartmentForAppeal(ide)

      val event: Event = positionE._1

      new AppealData(event,
        positionA._1,
        values,
        null,
        "standart",
        map,
        street,
        null,
        actionBean.getLastActionByActionTypeIdAndEventId, //havePrimary
        dbEventClientRelation.getByEvent(event),
        null,
        if (positionA._1.getContractId != null) {
          dbContractBean.getContractById(positionA._1.getContractId.intValue())
        } else {
          null
        },
        currentDepartment,
        dbDiagnosticBean.getDiagnosticsByEventIdAndTypes,
        dbTempInvalidBean.getTempInvalidByEventId(event.getId.intValue()),
        event.getEventLocalContract)
    } else {
      throw new CoreException("Неудачная попытка сохранения(изменения) обращения")
    }
  }

  def getAppealById(id: Int, auth: AuthData) = {

    val result = appealBean.getAppealById(id)

    val positionE = result.iterator.next()
    val positionA = positionE._2.iterator.next()
    val values = positionA._2.asInstanceOf[java.util.Map[(java.lang.Integer, ActionProperty), java.util.List[Object]]]

    val mapper: ObjectMapper = new ObjectMapper()
    mapper.setSerializationConfig(mapper.getSerializationConfig.withView(classOf[Views.DynamicFieldsStandartForm]))
    //val map = patientBean.getKLADRAddressMapForPatient(result)
    //val appType = dbFDRecordBean.getIdValueFDRecordByEventTypeId(25, positionE._1.getEventType.getId.intValue())
    val currentDepartment = hospitalBedBean.getCurrentDepartmentForAppeal(id)

    val event: Event = positionE._1
    new AppealData(event,
      positionA._1,
      values,
      null,
      "standart",
      null,
      null,
      null,
      actionBean.getLastActionByActionTypeIdAndEventId, //havePrimary
      dbEventClientRelation.getByEvent(event),
      actionPropertyBean.getActionPropertiesByActionIdAndActionPropertyTypeCodes,
      if (event.getContract != null) {
        dbContractBean.getContractById(event.getContract.getId.intValue())
      } else {
        null
      },
      currentDepartment,
      dbDiagnosticBean.getDiagnosticsByEventIdAndTypes,
      dbTempInvalidBean.getTempInvalidByEventId(event.getId.intValue()),
      event.getEventLocalContract
    )
  }

  def getAppealPrintFormById(id: Int, auth: AuthData) = {
    val result = appealBean.getAppealById(id)
    val positionE = result.iterator.next()
    val positionA = positionE._2.iterator.next()
    val values = positionA._2.asInstanceOf[java.util.Map[(java.lang.Integer, ActionProperty), java.util.List[Object]]]
    val mapper: ObjectMapper = new ObjectMapper()

    mapper.setSerializationConfig(mapper.getSerializationConfig.withView(classOf[Views.DynamicFieldsPrintForm]))

    val map = patientBean.getKLADRAddressMapForPatient(positionE._1.getPatient)
    val street = patientBean.getKLADRStreetForPatient(positionE._1.getPatient)
    val currentDepartment = hospitalBedBean.getCurrentDepartmentForAppeal(id)

    val event: Event = positionE._1
    val eventLocalContract: EventLocalContract = event.getEventLocalContract
    val template: TempInvalid = dbTempInvalidBean.getTempInvalidByEventId(event.getId.intValue())
    val contract: Contract = if (positionA._1.getContractId != null) {
      dbContractBean.getContractById(positionA._1.getContractId.intValue())
    } else {
      null
    }

    val appealData: AppealData = new AppealData(event,
      positionA._1,
      values,
      actionPropertyBean.getActionPropertiesByEventIdsAndActionPropertyTypeCodes,
      "print_form",
      map,
      street,
      null,
      actionBean.getLastActionByActionTypeIdAndEventId, //havePrimary
      dbEventClientRelation.getByEvent(event),
      actionPropertyBean.getActionPropertiesByActionIdAndActionPropertyTypeCodes, //в тч Admission Diagnosis
      contract,
      currentDepartment,
      dbDiagnosticBean.getDiagnosticsByEventIdAndTypes,
      template,
      eventLocalContract)

    mapper.writeValueAsString(appealData)

  }

  def getAllAppealsByPatient(requestData: AppealSimplifiedRequestData, auth: AuthData): AppealSimplifiedDataList = {
    val set = appealBean.getSupportedAppealTypeCodes //справочник госпитализаций
    requestData.filter.code = set.asInstanceOf[ju.Collection[String]]
    appealBean.getAllAppealsByPatient(requestData, auth)
  }

  def getAllAppealsForReceivedPatientByPeriod(requestData: ReceivedRequestData, authData: AuthData) = {

    /*var authData = auth;
    if (auth == null) {
      //достаем authData из TreadLocal  --- оставил пока для примера...
      var curAuthContext = ThreadLocalByRequest.get()
      authData = curAuthContext.getCurrentUserAuthData
      if(curAuthContext.getHResult!=CurrentAuthContext.HResult.S_OK)
        authData = null;
      ThreadLocalByRequest.unset()
      curAuthContext.clean()
    }*/
    if (requestData.filter.asInstanceOf[ReceivedRequestDataFilter].role <= 0)
      requestData.filter.asInstanceOf[ReceivedRequestDataFilter].role = authData.getUserRole.getId.intValue() //Роль из данных авторизации (клиент пока не использует)

    val mapper: ObjectMapper = new ObjectMapper()
    requestData.filter.asInstanceOf[ReceivedRequestDataFilter].role match {
      case 29 => mapper.setSerializationConfig(mapper.getSerializationConfig.withView(classOf[ReceivedPatientsDataViews.AdmissionDepartmentsNurseView])) //Сестра приемного отделения
      case _ => mapper.setSerializationConfig(mapper.getSerializationConfig.withView(classOf[ReceivedPatientsDataViews.AdmissionDepartmentsDoctorView])) //Доктор
    }

    requestData.setRecordsCount(dbEventBean.getCountOfAppealsForReceivedPatientByPeriod(requestData.filter))
    val data = if (requestData.recordsCount != 0) {
      val received = dbEventBean.getAllAppealsForReceivedPatientByPeriod(requestData.page - 1,
        requestData.limit,
        requestData.sortingFieldInternal,
        requestData.sortingMethod,
        requestData.filter)
      new ReceivedPatientsData(received,
        requestData,
        appealBean.getDiagnosisListByAppealId,
        actionPropertyBean.getActionPropertiesByEventIdsAndActionPropertyTypeCodes,
        actionBean.getLastActionByActionTypeIdAndEventId,
        appealBean.getPatientsHospitalizedStatus,
        actionBean.getAppealActionByEventId)
    } else new ReceivedPatientsData()

    mapper.writeValueAsString(data)
  }

  //запрос на структуру первичного мед. осмотра
  def getStructOfPrimaryMedExam(actionTypeId: Int, eventId: Int, authData: AuthData) = {
    //TODO: подключить анализ авторизационных данных и доступных ролей
    //primaryAssessmentBean.getPrimaryAssessmentEmptyStruct("1_1_01", "PrimaryAssesment", null)
    val listForConverter = new java.util.ArrayList[String]
    listForConverter.add(ActionPropertyWrapperInfo.Value.toString)
    listForConverter.add(ActionPropertyWrapperInfo.ValueId.toString)
    listForConverter.add(ActionPropertyWrapperInfo.Norm.toString)
    listForConverter.add(ActionPropertyWrapperInfo.Unit.toString)

    val listForSummary = new java.util.ArrayList[StringId]
    listForSummary.add(ActionWrapperInfo.assessmentId)
    listForSummary.add(ActionWrapperInfo.assessmentName)
    listForSummary.add(ActionWrapperInfo.assessmentBeginDate)
    listForSummary.add(ActionWrapperInfo.assessmentEndDate)
    listForSummary.add(ActionWrapperInfo.doctorLastName)
    listForSummary.add(ActionWrapperInfo.doctorFirstName)
    listForSummary.add(ActionWrapperInfo.doctorMiddleName)
    listForSummary.add(ActionWrapperInfo.doctorSpecs)
    listForSummary.add(ActionWrapperInfo.assignerLastName)
    listForSummary.add(ActionWrapperInfo.assignerFirstName)
    listForSummary.add(ActionWrapperInfo.assignerMiddleName)
    listForSummary.add(ActionWrapperInfo.assignerSpecs)
    listForSummary.add(ActionWrapperInfo.assignerPost)
    listForSummary.add(ActionWrapperInfo.urgent)
    listForSummary.add(ActionWrapperInfo.multiplicity)
    listForSummary.add(ActionWrapperInfo.finance)
    listForSummary.add(ActionWrapperInfo.plannedEndDate)

    //Для направлений на лабисследования, консультации и инструментальные иследования выводить поле "Направивший врач"
    val at = actionTypeBean.getActionTypeById(actionTypeId)
    val flgDiagnostics = at != null &&
      (at.getMnemonic.toUpperCase.compareTo("LAB") == 0 ||
        at.getMnemonic.toUpperCase.compareTo("BAK_LAB") == 0 ||
        at.getMnemonic.toUpperCase.compareTo("DIAG") == 0 ||
        at.getMnemonic.toUpperCase.compareTo("CONS") == 0)
    if (flgDiagnostics) {
      listForSummary.add(ActionWrapperInfo.executorId)
      listForSummary.add(ActionWrapperInfo.assignerId)
    }
    //listForSummary.add(ActionWrapperInfo.toOrder)

    val event = if (eventId > 0) dbEventBean.getEventById(eventId) else null

    primaryAssessmentBean.getEmptyStructure(actionTypeId,
      "Document",
      listForConverter,
      listForSummary,
      authData,
      postProcessing(event),
      null)
  }

  //запрос на структуру первичного мед. осмотра с копированием данных из предыдущего осмотра
  def getStructOfPrimaryMedExamWithCopy(actionTypeId: Int, authData: AuthData, eventId: Int) = {
    val lastActionId = actionBean.getActionIdWithCopyByEventId(eventId, actionTypeId)
    try {
      primaryAssessmentBean.getPrimaryAssessmentById(lastActionId, "Document", authData, postProcessing(), false) //postProcessingWithCopy _, true)
    }
    catch {
      case e: Exception =>
        getStructOfPrimaryMedExam(actionTypeId, -1, authData)
    }

  }

  private def postProcessing(event: Event = null)(jData: JSONCommonData, reWriteId: java.lang.Boolean) = {

    val cache = mutable.HashMap[Int, java.util.List[Action]]()
    val APValueCache = mutable.HashMap[ActionProperty, java.util.List[APValue]]()
    val pastActionsCache = mutable.HashMap[(Set[String], Int, String), java.util.List[Action]]()

    val at = try {
      actionTypeBean.getActionTypeById(jData.getData.get(0).getId) //TODO: jData.getData.get(0).getId это Action.id, но не ActionType.id
    } catch {
      case e: Throwable => null
    }
    if (at != null) {
      val aptList = actionPropertyTypeBean.getActionPropertyTypesByActionTypeId(at.getId);
      // Последний дневниковый осмотр из всех историй болезни
      val IC = InfectionControl
      val lastAction = if (event == null) null else actionBean.getLastActionByActionTypesAndClientId(IC.documents.toList, event.getPatient.getId)
      jData.data.get(0).group.get(1).attribute.foreach(ap => {
        if (ap.typeId == null || ap.typeId.intValue() <= 0) {
          if (reWriteId.booleanValue) //в id -> apt.id
            ap.typeId = ap.id
          else
            ap.typeId = actionPropertyBean.getActionPropertyById(ap.id.intValue()).getType.getId.intValue()
        }

        // Вычисляем "подтягивающиеся" из прошлых документов значение
        if (event != null) {
          val aProp = try {
            aptList.find(p => p.getId == ap.getId).get
          } catch {
            case e: Throwable => null
          }

          if (aProp != null) {
            ap setCalculatedValue new APValueContainer(calculateActionPropertyValue(event, at, aProp, cache, APValueCache, pastActionsCache, lastAction))
          }
        }
      }
      )
    }
    jData
  }

  private def notifyAction(actions: java.util.List[Action], baseUri: URI): java.lang.Boolean = {
    actions.toList.foreach(action => {
      dbNotificationActionBeanLocal.pullDb()
    })
    true
  }

  private def postProcessingForDiagnosis(jData: JSONCommonData, reWriteId: java.lang.Boolean) = {
    jData.data.get(0).group.get(1).attribute.foreach(ap => {
      val value = if (ap.typeId != null && ap.typeId.intValue() > 0)
        ap.typeId.intValue()
      else
        actionPropertyBean.getActionPropertyById(ap.id.intValue()).getType.getId.intValue()
      ap.typeId = value //Integer.valueOf(value).intValue()
    })
    jData
  }

  /**
   * Метод для вставки аттрибутов, которые должны подтягиваться при создании новых документов
   * (например диагноз из предыдущего документа)
   * Так как данный метод "сам в себе" то и константы-идентификаторы я буду хранить рядом для лучшей ясности.
   * Метод костылен по своей природе, т.к. костыльна сама поставленная задача.
   * @param event Экземпляр события, для которого создается документ
   * @param at Тип создаваемого документа
   * @param apt Свойство создаваемого документа
   */
  private def calculateActionPropertyValue(event: Event, at: ActionType, apt: ActionPropertyType,
                                           cache: mutable.HashMap[Int, java.util.List[Action]],
                                           apValueCache: mutable.HashMap[ActionProperty, java.util.List[APValue]],
                                           pastActionsCache: mutable.HashMap[(Set[String], Int, String),
                                             java.util.List[Action]],
                                           lastAction: Action): APValue = {

    val IC = InfectionControl

    val therapySet = Set("therapyTitle", "therapyBegDate", "therapyPhaseTitle", "therapyPhaseBegDate")


    // Получение значения свойства у предыдущих действий
    def getProperty(oldDocumentCodes: Set[String], actionTypeCodes: Set[String], event: Event): APValue = {
      if (oldDocumentCodes.contains(at.getCode)) {
        val pastActions = pastActionsCache.getOrElseUpdate((oldDocumentCodes, event.getId, "a.begDate DESC"), actionBean.getActionsByTypeCodeAndEventId(oldDocumentCodes, event.getId, "a.begDate DESC", null))
        if (pastActions != null && !pastActions.isEmpty)
          pastActions.head.getActionProperties.foreach(e => {
            if (e.getType.getCode != null && e.getType.getCode.equals(apt.getCode)) {
              val values = apValueCache.getOrElseUpdate(e, actionPropertyBean.getActionPropertyValue(e))
              if (actionTypeCodes.contains(e.getType.getCode)) {
                if (values.size() > 0)
                  return values.head
              }
            }
          })
        null
      }
      null
    }

    def getLastActionByTypeCodes(typeCode: List[String], event: Event): Action = {
      actionBean.getLastActionByEventAndActionTypes(event.getId, typeCode)
    }

    // Получение значений свойства у предыдущих дневниковых осмотров для нового дневникового осмотра
    def getPropertyCustom1(oldDocumentCodes: Set[String], actionTypeCodes: Set[String], event: Event): APValue = {

      def getTherapyPhaseEndDate(action: Action): Date = {
        action.getActionProperties.foreach(p =>
          if (p.getType.getCode != null && p.getType.getCode.equals("therapyPhaseEndDate"))
            apValueCache.getOrElseUpdate(p, actionPropertyBean.getActionPropertyValue(p)).foreach {
              case d: Date => return d
              case _ =>
            }
        )
        null
      }

      if (oldDocumentCodes.contains(at.getCode)) {
        val pastActions = pastActionsCache.getOrElseUpdate((oldDocumentCodes, event.getId, "a.begDate DESC"), actionBean.getActionsByTypeCodeAndEventId(oldDocumentCodes, event.getId, "a.begDate DESC", null))
        if (pastActions != null && !pastActions.isEmpty && getTherapyPhaseEndDate(pastActions.head) != null)
          pastActions.head.getActionProperties.foreach(e => {
            if (e.getType.getCode != null && e.getType.getCode.equals(apt.getCode)) {
              val values = apValueCache.getOrElseUpdate(e, actionPropertyBean.getActionPropertyValue(e))
              if (actionTypeCodes.contains(e.getType.getCode)) {
                if (values.size() > 0)
                  return values.head
              }
            }
          })
        null
      }
      null
    }

    // Получение значений свойств по свойствам инфекционного контроля
    def getPropertyCustom2(actionTypeCodesPrefix: String, lastAction: Action): APValue = {

      if (!apt.getCode.startsWith(actionTypeCodesPrefix))
        return null

      // Ничего не возвращем, если в прошлом не было дневникового осмотра
      if (lastAction == null)
        return null

      val endDateProperty = lastAction.getActionProperties.find(ap =>
        ap.getType.getCode != null && ap.getType.getCode.equals(actionTypeCodesPrefix + IC.separator + IC.endDatePostfix))

      val endDateValue: Date = {
        if (endDateProperty.isDefined) {
          val ap = endDateProperty.get
          val value = apValueCache.getOrElseUpdate(ap, actionPropertyBean.getActionPropertyValue(ap))
          if (!value.isEmpty)
            value.head match {
              case p: APValueDate => p.getValue // в БД время сохраняется как 00.00.00
              case _ => null
            } else
            null
        } else
          null
      }

      // Даты конца нет или она в будущем,  нужно подтянуть значения
      if (endDateValue == null || endDateValue.after(new Date())) {
        lastAction.getActionProperties.foreach(p => {
          if (p.getType.getCode != null && p.getType.getCode.equals(apt.getCode)) {
            val values = apValueCache.getOrElseUpdate(p, actionPropertyBean.getActionPropertyValue(p))
            if (values.size() > 0)
              return values.head
          }
        })
        null
      } else
        null
    }

    // Получения значений для лекарственных назначений
    def getPropertyCustom3(drugType: String, lastAction: Action): APValue = {

      // Ничего не возвращем, если в прошлом не было дневникового осмотра
      if (lastAction == null)
        return null

      val endDateProperty = lastAction.getActionProperties
        .find(ap => ap.getType.getCode != null
        && ap.getType.getCode.startsWith("infect" + drugType)
        && ap.getType.getCode.endsWith("EndDate_" + apt.getCode.last))

      val endDateValue: Date = {
        if (endDateProperty.isDefined) {
          val ap = endDateProperty.get
          val value = apValueCache.getOrElseUpdate(ap, actionPropertyBean.getActionPropertyValue(ap))
          if (!value.isEmpty)
            value.head match {
              case p: APValueDate => p.getValue // в БД время сохраняется как 00.00.00
              case _ => null
            } else
            null
        } else
          null
      }

      // Даты конца нет или она в будущем,  нужно подтянуть значения
      if (endDateValue == null || endDateValue.after(new Date())) {
        lastAction.getActionProperties.foreach(p => {
          if (p.getType.getCode != null && p.getType.getCode.equals(apt.getCode)) {
            val values = apValueCache.getOrElseUpdate(p, actionPropertyBean.getActionPropertyValue(p))
            if (values.size() > 0)
              return values.head
          }
        })
        null
      } else
        null
    }

    // Получение значения поля "Локальная инфекция"
    def getPropertyCustom4(lastAction: Action): APValue = {

      // Ничего не возвращем, если в прошлом не было дневникового осмотра
      if (lastAction == null)
        return null

      def getPropertyValue(prop: Option[ActionProperty]) = {
        if (prop.isDefined) {
          val ap = prop.get
          val value = apValueCache.getOrElseUpdate(ap, actionPropertyBean.getActionPropertyValue(ap))
          if (!value.isEmpty)
            value.head match {
              case p: APValueString => p.getValue
              case _ => null
            } else
            null
        } else
          null
      }

      val localInfectProperty = lastAction.getActionProperties.find(ap => ap.getType.getCode != null && ap.getType.getCode.equals(IC.localInfectionChecker)).orNull

      for (prefix <- IC.localInfectPrefixes) {

        val values =
          Set(
            lastAction.getActionProperties.find(ap => ap.getType.getCode != null && ap.getType.getCode.equals(prefix + IC.separator + IC.endDatePostfix)),
            lastAction.getActionProperties.find(ap => ap.getType.getCode != null && ap.getType.getCode.equals(prefix + IC.separator + IC.beginDatePostfix)),
            lastAction.getActionProperties.find(ap => ap.getType.getCode != null && ap.getType.getCode.equals(prefix + IC.separator + IC.etiologyPostfix)))
            .collect({
            case x: Some[ActionProperty] => getPropertyValue(x)
          })

        if (values.exists(_ != null))
          return {
            val v = apValueCache.getOrElseUpdate(localInfectProperty, actionPropertyBean.getActionPropertyValue(localInfectProperty))
            if (v.size() > 0)
              v.head
            else
              null
          }
      }
      null
    }

    at.getCode match {

      // Заключительный эпикриз
      case "4504" => if (event == null) null else getProperty(Set("4501", "4502", "4503", "4504", "4505", "4506", "4507", "4508", "4509", "4510", "4511"), Set("mainDiag", "mainDiagMkb"), event)

      // Дневниковый осмотр
      case x if IC.documents.contains(x) =>
        if (therapySet.contains(x)) // Подтягивания значений для полей терапии
          if (event == null) null else getPropertyCustom1(IC.documents, therapySet, event)
        else if (IC.allInfectPrefixes.exists(p => apt.getCode != null && (apt.getCode.startsWith(p + IC.separator) || apt.getCode.equals(p)))) {
          // или для полей инфекционного контроля
          return getPropertyCustom2(IC.allInfectPrefixes.find(p => apt.getCode.startsWith(p + IC.separator) || apt.getCode.equals(p)).get, lastAction)
        }
        else if (apt.getCode != null && apt.getCode.equals(IC.localInfectionChecker)) {
          return getPropertyCustom4(lastAction)
        }
        else if (IC.EmpiricTherapyProperties.contains(apt.getCode)) {
          return getPropertyCustom3("Empiric", lastAction)
        }
        else if (IC.TelicTherapyProperties.contains(apt.getCode)) {
          return getPropertyCustom3("Telic", lastAction)
        }
        else if (IC.ProphylaxisTherapyProperties.contains(apt.getCode)) {
          return getPropertyCustom3("Prophylaxis", lastAction)
        }
        else
          null
      case _ => null
    }

  }

  def calculateActionPropertyValue(eventId: Int, actionTypeId: Int, actionPropertyId: Int) = {
    val IC = InfectionControl
    val event: Event = dbEventBean.getEventById(eventId)
    calculateActionPropertyValue(
      event,
      actionTypeBean.getActionTypeById(actionTypeId),
      actionPropertyTypeBean.getActionPropertyTypeById(actionPropertyId),
      mutable.HashMap[Int, java.util.List[Action]](),
      mutable.HashMap[ActionProperty, java.util.List[APValue]](),
      mutable.HashMap[(Set[String], Int, String), java.util.List[Action]](),
      actionBean.getLastActionByActionTypesAndClientId(IC.documents.toList, event.getPatient.getId)
    )

  }

  def initActionTypeIdByFlatCode(data: JSONCommonData) {
    data.getData.foreach(d => {
      if (d.getFlatCode != null && !d.getFlatCode.isEmpty) {
        val at = actionTypeBean.getActionTypeByFlatCode(d.getFlatCode())
        if (d.getTypeId == null && at != null)
          d.setTypeId(at.getId)
      }
    })

  }

  //создание первичного мед. осмотра
  def insertPrimaryMedExamForPatient(eventId: Int, data: JSONCommonData, authData: AuthData, baseUri: URI) = {

    validateDocumentsAvailability(eventId)

    initActionTypeIdByFlatCode(data);

    val isPrimary = data.getData.find(ce => ce.getTypeId.compareTo(i18n("db.actionType.primary").toInt) == 0).orNull != null //Врач прописывается только для первичного осмотра  (ид=139)
    if (isPrimary)
      appealBean.setExecPersonForAppeal(eventId, 0, authData, ExecPersonSetType.EP_CREATE_PRIMARY)

    //создаем осмотр. ЕвентПерсон не флашится!!!
    val returnValue = primaryAssessmentBean createOrUpdatePrimaryAssessmentForEventId(eventId,
      data,
      null,
      authData,
      baseUri,
      notifyAction,
      postProcessing())

    returnValue
  }

  //редактирование первичного мед. осмотра
  def modifyPrimaryMedExamForPatient(actionId: Int, data: JSONCommonData, authData: AuthData, baseUri: URI) = {


    validateDocumentsAvailability(actionBean.getActionById(actionId).getEvent.getId)

    //создаем ответственного, если до этого был другой
    if (data.getData.find(ce => ce.getTypeId.compareTo(i18n("db.actionType.primary").toInt) == 0).orNull != null) //Врач прописывается только для первичного осмотра  (ид=139)
      appealBean.setExecPersonForAppeal(actionId, 0, authData, ExecPersonSetType.EP_MODIFY_PRIMARY)

    //создаем осмотр. ЕвентПерсон не флашится!!!
    val returnValue = primaryAssessmentBean createOrUpdatePrimaryAssessmentForEventId(null,
      data,
      actionId,
      authData,
      baseUri,
      notifyAction,
      postProcessing())

    returnValue
  }

  private def validateDocumentsAvailability(eventId: Int) = {
    val event = dbEventBean.getEventById(eventId)
    if (event == null)
      throw new CoreException(i18n("error.event.NotFound").format(eventId))

    //, Нельзя создавать документы для закрытой госпитализации,
    // если прошло больше дней, чем указанно в конфигурации
    val closeDate = event.getExecDate
    if (closeDate != null) {
      val availableDays = ConfigManager.Common.eventEditableDays
      try {
        if (new DateTime(closeDate).plusDays(availableDays).getMillis < new DateTime().getMillis) {
          throw new CoreException("Редактирование документов разрешено только в течении " + availableDays + " после закрытия истории болезни")
        }
      } catch {
        case e: NumberFormatException =>
          throw new CoreException("Невозможно обработать значение свойства 'Common.eventEditableDays': " + availableDays)
        case e: Throwable =>
          throw new CoreException(i18n("error.unknownError"))
      }
    }
  }


  def getPrimaryAssessmentById(assessmentId: Int, authData: AuthData) = {

    //TODO: подключить анализ авторизационных данных и доступных ролей
    //val authData:AuthData = null

    val json_data = primaryAssessmentBean.getPrimaryAssessmentById(assessmentId,
      "Assessment",
      authData,
      postProcessing(), false)

    val actionWithLockInfo = authStorage.getLockInfo(actionBean.getActionById(assessmentId))
    if (actionWithLockInfo != null &&
      actionWithLockInfo.lockInfo != null &&
      actionWithLockInfo.lockInfo.person != null
    ) {
      val lockInfo = new LockInfoContainer(actionWithLockInfo.lockInfo.person.getId, actionWithLockInfo.lockInfo.person.getFullName)
      json_data.data.foreach(d => d.setLockInfo(lockInfo))
    }
    json_data
  }

  def getAllPatientsForDepartmentIdAndDoctorIdByPeriod(requestData: PatientsListRequestData, auth: AuthData) = {
    patientBean.getAllPatientsForDepartmentIdAndDoctorIdByPeriod(requestData, auth)
  }

  def getActionWithLockInfoList(list: java.util.List[Action]): java.util.List[ActionWithLockInfo] = {
    val res: java.util.List[ActionWithLockInfo] = new java.util.LinkedList[ActionWithLockInfo]
    list.foreach(action => res.add(authStorage.getLockInfo(action)))
    res
  }

  //Возвращает список осмотров по пациенту и обращению с фильтрацией по типу действия
  def getListOfAssessmentsForPatientByEvent(requestData: AssessmentsListRequestData, auth: AuthData) = {
    val action_list = actionBean.getActionsWithFilter(requestData.limit,
      requestData.page - 1,
      requestData.sortingFieldInternal,
      requestData.filter.unwrap(),
      requestData.rewriteRecordsCount)
    val actionWithLockInfoList = getActionWithLockInfoList(action_list)
    //actionBean.getActionsByEventIdWithFilter(requestData.eventId, auth, requestData)
    val assessments: AssessmentsListData = new AssessmentsListData(actionWithLockInfoList, requestData)
    assessments
  }

  //<= Hospital Bed =>
  //Данные об регистрации на койке
  def getPatientToHospitalBedById(actionId: Int, authData: AuthData) = {

    val action = actionBean.getActionById(actionId)

    val mapper: ObjectMapper = new ObjectMapper()
    if (action.getEndDate == null) //Если действие закрыто (перевод)
      mapper.setSerializationConfig(mapper.getSerializationConfig.withView(classOf[HospitalBedViews.RegistrationFormView]))
    else
      mapper.setSerializationConfig(mapper.getSerializationConfig.withView(classOf[HospitalBedViews.MoveView]))
    mapper.writeValueAsString(hospitalBedBean.getRegistryFormWithChamberList(action, authData))
  }

  def getMovingListForEvent(filter: HospitalBedDataListFilter, authData: AuthData) = {

    val mapper: ObjectMapper = new ObjectMapper()
    mapper.setSerializationConfig(mapper.getSerializationConfig.withView(classOf[HospitalBedViews.MovesListView]))
    mapper.writeValueAsString(hospitalBedBean.getMovingListByEventIdAndFilter(filter, authData))
  }

  //Регистрирует пациента на койке
  def registryPatientToHospitalBed(eventId: Int, data: HospitalBedData, authData: AuthData) = {
    authData.setUser(dbStaff.getStaffById(authData.getUser.getId));
    val action = hospitalBedBean.registryPatientToHospitalBed(eventId, data, authData)
    val mapper: ObjectMapper = new ObjectMapper()
    mapper.setSerializationConfig(mapper.getSerializationConfig.withView(classOf[HospitalBedViews.RegistrationView]))
    mapper.writeValueAsString(hospitalBedBean.getRegistryOriginalForm(action, authData))
  }

  //Редактирует регистрацию пациента на койке
  def modifyPatientToHospitalBed(actionId: Int, data: HospitalBedData, authData: AuthData) = {
    authData.setUser(dbStaff.getStaffById(authData.getUser.getId));
    val action = hospitalBedBean.modifyPatientToHospitalBed(actionId, data, authData)
    val mapper: ObjectMapper = new ObjectMapper()
    mapper.setSerializationConfig(mapper.getSerializationConfig.withView(classOf[HospitalBedViews.RegistrationView]))
    mapper.writeValueAsString(hospitalBedBean.getRegistryOriginalForm(action, authData))
  }

  //Снятие пациента с койки
  def callOffHospitalBedForPatient(actionId: Int, authData: AuthData) = {
    hospitalBedBean.callOffHospitalBedForPatient(actionId, authData)
  }

  //Список коек
  def getVacantHospitalBeds(departmentId: Int, authData: AuthData) = {
    new BedDataListContainer(hospitalBedBean.getCaseHospitalBedsByDepartmentId(departmentId), departmentId)
  }

  //Профиль койки по идентификатору
  def getBedProfileById(profileId: Int, authData: AuthData) = {
    hospitalBedProfileBean.getRbHospitalBedProfileById(profileId) match {
      case null =>
        throw new CoreException("В базе данных не найден профиль койки с идентификатором " + profileId);
      case (p: RbHospitalBedProfile) => new HospitalBedProfileContainer(p)
    }
  }

  //Полный список профилей коек в системе
  def getAllAvailableBedProfiles(authData: AuthData) = {
    new HospitalBedProfilesListContainer(hospitalBedProfileBean.getAllRbHospitalBedProfiles)
  }

  def getAllAvailableBedProfiles = hospitalBedProfileBean.getAllRbHospitalBedProfiles

  //форма 007
  def getForm007(departmentId: Int,
                 beginDate: Long,
                 endDate: Long,
                 profileBeds: java.util.List[Integer],
                 authData: AuthData) = seventhFormBean.getForm007LinearView(departmentId, beginDate, endDate, profileBeds)


  def movingPatientToDepartment(eventId: Int, data: HospitalBedData, authData: AuthData) = {

    val action = hospitalBedBean.movingPatientToDepartment(eventId, data, authData)

    val mapper: ObjectMapper = new ObjectMapper()
    mapper.setSerializationConfig(mapper.getSerializationConfig.withView(classOf[HospitalBedViews.MoveView]))
    mapper.writeValueAsString(hospitalBedBean.getRegistryOriginalForm(action, authData))
  }

  def closeLastMovingOfAppeal(eventId: Int, authData: AuthData, date: Date) = {
    val actionTypes = new java.util.HashSet[Integer]
    actionTypes.add(i18n("db.actionType.moving").toInt)
    val lastAction = actionBean.getLastActionByActionTypeIdAndEventId(eventId, actionTypes)
    val action = actionBean.getActionById(lastAction)

    if (date.before(action.getBegDate))
      throw new CoreException("Дата закрытия движения не может быть раньше даты начала движения")

    action.setEndDate(date)
    action.setStatus(2) //A little piece of magic - 2 is a status of CLOSED action
    actionBean.updateAction(action)
    val mapper: ObjectMapper = new ObjectMapper()
    mapper.setSerializationConfig(mapper.getSerializationConfig.withView(classOf[ActionDataContainer]))
    mapper.writeValueAsString(new ActionDataContainer(action))
  }

  def insertOrUpdateQuota(quotaData: QuotaData, eventId: Int, auth: AuthData) = {
    var quota = appealBean.insertOrUpdateClientQuoting(quotaData.getData, eventId, auth)
    val mapper: ObjectMapper = new ObjectMapper()
    mapper.setSerializationConfig(mapper.getSerializationConfig.withView(classOf[QuotaViews.DynamicFieldsQuotaCreate]))
    if (quotaData.getData.getId > 0) {
      quota = dbClientQuoting.getClientQuotingById(quotaData.getData.getId)
    }
    mapper.writeValueAsString(new QuotaData(new QuotaEntry(quota, classOf[QuotaViews.DynamicFieldsQuotaCreate]), quotaData.getRequestData))
  }

  def getQuotaHistory(appealId: Int, request: QuotaRequestData) = {
    val appeal = dbEventBean.getEventById(appealId)
    //val result = appealBean.getAppealById(appealId)
    //val appeal = result.iterator.next()._1
    val quotaList = dbClientQuoting.getAllClientQuotingForPatient(appeal.getPatient.getId.intValue(),
      request.page - 1,
      request.limit,
      request.sortingFieldInternal,
      request.sortingMethod,
      request.filter,
      request.rewriteRecordsCount)

    val mapper: ObjectMapper = new ObjectMapper()
    mapper.setSerializationConfig(mapper.getSerializationConfig.withView(classOf[QuotaViews.DynamicFieldsQuotaHistory]))
    mapper.writeValueAsString(new QuotaListData(quotaList, request))
  }

  /*
  def insertTalonSPOForPatient(data: Object) = {

    /*newEvent = eventBean.createEvent(/*appealData.data.patient.id.toInt*/patientId,
      /*appealData.data.appealType.id.toInt*/53, //дневной стационар
      authData) */

    null
  }*/

  def getAllPersons(requestData: ListDataRequest) = {

    //TODO: подключить анализ авторизационных данных и доступных ролей
    new AllPersonsListData(dbStaff.getAllPersonsByRequest(requestData.limit,
      requestData.page - 1,
      requestData.sortingFieldInternal,
      requestData.filter.unwrap(),
      requestData.rewriteRecordsCount),
      requestData)


    /*requestData.setRecordsCount(dbStaff.getCountAllPersonsWithFilter(requestData.filter))
    val list = new AllPersonsListData(dbStaff.getAllPersonsByRequest(requestData.limit,
      requestData.page-1,
      requestData.sortingField,
      requestData.sortingMethod,
      requestData.filter
    ),
      requestData)
    list */
  }

  def getAllDepartments(requestData: ListDataRequest) = {
    //TODO: подключить анализ авторизационных данных и доступных ролей
    requestData.setRecordsCount(dbOrgStructureBean.getCountAllOrgStructuresWithFilter(requestData.filter))
    val list = new AllDepartmentsListData(dbOrgStructureBean.getAllOrgStructuresByRequest(requestData.limit,
      requestData.page - 1,
      requestData.sortingFieldInternal,
      requestData.filter.unwrap()
    ),
      requestData)
    list
  }

  def getAllDepartmentsByHasBeds(hasBeds: String, hasPatients: String) = {
    //Для медипада
    val flgBeds = hasBeds.toLowerCase.compareTo("true") == 0 || hasBeds.toLowerCase.compareTo("yes") == 0
    val flgPatients = hasPatients.toLowerCase.compareTo("true") == 0 || hasPatients.toLowerCase.compareTo("yes") == 0
    val filter = new DepartmentsDataFilter(flgBeds, flgPatients)
    new AllDepartmentsListDataMP(dbOrgStructureBean.getAllOrgStructuresByRequest(0, 0, "", filter.unwrap()), null)
  }


  def getListOfDiagnosticsForPatientByEvent(requestData: DiagnosticsListRequestData, authData: AuthData) = {

    //TODO: подключить анализ авторизационных данных и доступных ролей
    requestData.setRecordsCount(dbCustomQueryBean.getCountDiagnosticsWithFilter(requestData.filter))
    var actions: java.util.List[Action] = null
    if (requestData.getRecordsCount > 0) {
      actions = dbCustomQueryBean.getAllDiagnosticsWithFilter(requestData.page - 1,
        requestData.limit,
        requestData.sortingFieldInternal,
        requestData.filter.unwrap())
    }
    val ajtList = new ju.LinkedList[(Action, JobTicket)]()
    if (actions != null && actions.size() > 0) {
      actions.foreach(a => {
        ajtList.add((a, dbJobTicketBean.getJobTicketForAction(a.getId.intValue())))
      })
    }
    val list = new DiagnosticsListData(ajtList, requestData, authData)
    list
  }

  def getInfoAboutDiagnosticsForPatientByEvent(actionId: Int, authData: AuthData) = {
    val json_data = directionBean.getDirectionById(actionId, "Diagnostic", null, authData)
    json_data
  }

  def getFreePersons(requestData: ListDataRequest, beginDate: Long) = {

    //<= Изменить запрос (ждем отклик)
    //requestData.setRecordsCount(dbStaff.getCountAllPersonsWithFilter(requestData.filter))
    var citoActionsCount = 0
    if (beginDate > 0) {
      citoActionsCount = actionBean.getActionForDateAndPacientInQueueType(beginDate, 1).toInt
    }

    new FreePersonsListDataFilter()
    val list = new FreePersonsListData(dbStaff.getEmptyPersonsByRequest(requestData.limit,
      requestData.page - 1,
      requestData.sortingFieldInternal,
      requestData.filter.unwrap(),
      citoActionsCount),
      requestData)
    list
  }

  def getListOfActionTypeIdNames(request: ListDataRequest, patientId: Int) = {

    //TODO: подключить анализ авторизационных данных и доступных ролей
    val count = actionTypeBean.getCountAllActionTypeWithFilter(request.filter)

    val result = count match {
      case 0 =>
        val actionType = if (request.filter.asInstanceOf[ActionTypesListRequestDataFilter].getGroupId > 0) {
          actionTypeBean.getActionTypeById(request.filter.asInstanceOf[ActionTypesListRequestDataFilter].getGroupId)
        } else if (request.filter.asInstanceOf[ActionTypesListRequestDataFilter].getCode != null &&
          request.filter.asInstanceOf[ActionTypesListRequestDataFilter].getCode.compareTo("") != 0) {
          val types = actionTypeBean.getActionTypesByCode(request.filter.asInstanceOf[ActionTypesListRequestDataFilter].getCode)
          if (types != null && types.size() > 0) {
            val mnems = request.filter.asInstanceOf[ActionTypesListRequestDataFilter].getMnemonics
            types.find(p => {
              if (mnems != null && mnems.size() > 0) {
                val ress = mnems.find(mnem => mnem.compareTo(p.getMnemonic) == 0).orNull
                if (ress != null) true else false
              }
              else {
                p.getMnemonic.compareTo("") == 0
              }
            }).orNull
          } else null
        } else null

        //empty action property
        val listForConverter = new java.util.ArrayList[String]
        listForConverter.add(ActionPropertyWrapperInfo.IsAssignable.toString)
        listForConverter.add(ActionPropertyWrapperInfo.IsAssigned.toString)
        listForConverter.add(ActionPropertyWrapperInfo.Value.toString)
        listForConverter.add(ActionPropertyWrapperInfo.Norm.toString)
        listForConverter.add(ActionPropertyWrapperInfo.Unit.toString)

        val listForSummary = new java.util.ArrayList[StringId]
        listForSummary.add(ActionWrapperInfo.assessmentId)
        listForSummary.add(ActionWrapperInfo.assessmentName)
        listForSummary.add(ActionWrapperInfo.assessmentDate)
        listForSummary.add(ActionWrapperInfo.doctorLastName)
        listForSummary.add(ActionWrapperInfo.doctorFirstName)
        listForSummary.add(ActionWrapperInfo.doctorMiddleName)
        listForSummary.add(ActionWrapperInfo.doctorSpecs)
        listForSummary.add(ActionWrapperInfo.assignerLastName)
        listForSummary.add(ActionWrapperInfo.assignerFirstName)
        listForSummary.add(ActionWrapperInfo.assignerMiddleName)
        listForSummary.add(ActionWrapperInfo.assignerSpecs)
        listForSummary.add(ActionWrapperInfo.assignerPost)
        listForSummary.add(ActionWrapperInfo.urgent)
        listForSummary.add(ActionWrapperInfo.multiplicity)
        listForSummary.add(ActionWrapperInfo.finance)
        listForSummary.add(ActionWrapperInfo.plannedEndDate)

        //Для направлений на лабисследования, консультации и инструментальные иследования выводить поле "Направивший врач"
        val mnemonics = request.filter.asInstanceOf[ActionTypesListRequestDataFilter].getMnemonics
        val flgDiagnostics = mnemonics != null &&
          mnemonics.size() > 0 &&
          mnemonics.exists(p => p.toUpperCase.compareTo("LAB") == 0 ||
            p.toUpperCase.compareTo("BAK_LAB") == 0 ||
            p.toUpperCase.compareTo("DIAG") == 0 ||
            p.toUpperCase.compareTo("CONS") == 0)

        if (flgDiagnostics) {
          listForSummary.add(ActionWrapperInfo.executorId)
          listForSummary.add(ActionWrapperInfo.assignerId)
        }
        var json = new JSONCommonData()
        if (actionType != null) {
          json = primaryAssessmentBean.getEmptyStructure(actionType.getId.intValue(), "Action", listForConverter, listForSummary, null, postProcessing(null), patientBean.getPatientById(patientId))
        }
        json.setRequestData(request)
        json
      case _ =>
        val mapper: ObjectMapper = new ObjectMapper()
        mapper.setSerializationConfig(mapper.getSerializationConfig.withView(classOf[ActionTypesListDataViews.OneLevelView])) //плоская структурв
        mapper.writeValueAsString(new ActionTypesListData(request, actionTypeBean.getAllActionTypeWithFilter))
    }
    result
  }

  def getListOfActionTypes(request: ListDataRequest) = {
    val mapper: ObjectMapper = new ObjectMapper()
    mapper.setSerializationConfig(mapper.getSerializationConfig.withView(classOf[ActionTypesListDataViews.DefaultView])) //дерево
    // Данный параметр использовался в прошлой реализации, в текущей он не требуется
    request.filter.asInstanceOf[ActionTypesListRequestDataFilter].setCode(null)
    mapper.writeValueAsString(new ActionTypesListData(request, actionTypeBean.getAllActionTypeWithFilter))
  }

  //********* Диагнозтические исследования **********
  def insertLaboratoryStudies(eventId: Int, data: CommonData, auth: AuthData) = {
    val json = directionBean.createDirectionsForEventIdFromCommonData(eventId, data, "Diagnostic", null, "LAB", auth, postProcessingForDiagnosis)
    json.getData.map(entity => entity.getId.intValue()).foreach(a_id => {
      val action = actionBean.getActionById(a_id)
      if (action.getStatus == 2 && !action.getIsUrgent) {
        directionBean.sendActionToLis(a_id)
      }
    })
    json
    //primaryAssessmentBean.createAssessmentsForEventIdFromCommonData(eventId, data, "Diagnostic", null, auth,  postProcessingForDiagnosis _)// postProcessingForDiagnosis
  }

  def modifyLaboratoryStudies(eventId: Int, data: CommonData, auth: AuthData) = {
    directionBean.modifyDirectionsForEventIdFromCommonData(eventId, data, "Diagnostic", null, "LAB", auth, postProcessingForDiagnosis) // postProcessingForDiagnosis
  }

  def insertInstrumentalStudies(eventId: Int, data: CommonData, auth: AuthData) = {
    directionBean.createDirectionsForEventIdFromCommonData(eventId, data, "Diagnostic", null, "DIAG", auth, postProcessingForDiagnosis) // postProcessingForDiagnosis
  }

  def modifyInstrumentalStudies(eventId: Int, data: CommonData, auth: AuthData) = {
    directionBean.modifyDirectionsForEventIdFromCommonData(eventId, data, "Diagnostic", null, "DIAG", auth, postProcessingForDiagnosis) // postProcessingForDiagnosis
  }

  def insertConsultation(request: ConsultationRequestData, authData: AuthData) = {
    val actionId = directionBean.createConsultation(request, authData)
    val json = directionBean.getDirectionById(actionId, "Consultation", null, authData)
    json.setRequestData(request) //по идее эта штука должна быть в конструкторе вызываемая в методе гет
    json
  }

  def modifyConsultation(request: ConsultationRequestData, authData: AuthData) = {
    val actionId = directionBean.createConsultation(request, authData)
    val json = directionBean.getDirectionById(actionId, "Consultation", null, authData)
    json.setRequestData(request) //по идее эта штука должна быть в конструкторе вызываемая в методе гет
    json
  }

  def removeDirection(data: AssignmentsToRemoveDataList, directionType: String, auth: AuthData) = {
    directionBean.removeDirections(data, directionType, auth)
  }

  def checkCountOfConsultations(eventId: Int, pqt: Int, executorId: Int, data: Long) {
    val executor = dbStaff.getStaffById(executorId)
    val actionsCount = actionBean.getActionForEventAndPacientInQueueType(eventId, data, pqt)
    if (pqt == 1) {
      if (executor.getMaxCito <= 0 || executor.getMaxCito <= actionsCount) {
        throw new CoreException(ConfigManager.Messages("error.citoLimit"))
      }
    } else if (pqt == 2) {
      if (executor.getMaxOverQueue <= 0 || executor.getMaxOverQueue <= actionsCount) {
        throw new CoreException(ConfigManager.Messages("error.overQueueLimit"))
      }
    }
  }

  def getPlannedTime(actionId: Int) = {
    val a = actionBean.getActionById(actionId)
    val action19 = actionBean.getEvent29AndAction19ForAction(a)
    val apva = actionPropertyBean.getActionPropertyValue_ActionByValue(action19)
    val filter = new FreePersonsListDataFilter(0,
      a.getExecutor.getId.intValue(),
      a.getActionType.getId.intValue(),
      a.getPlannedEndDate.getTime,
      0)
    val timesAP = dbStaff.getActionPropertyForPersonByRequest(filter)
    if (timesAP != null) {
      val timesAPV = actionPropertyBean.getActionPropertyValue(timesAP)
      val data = new ScheduleContainer(timesAPV.get(apva.getId.getIndex).asInstanceOf[APValueTime])
      data
    } else {
      null
    }
  }

  //*********  **********

  def getFlatDirectories(request: FlatDirectoryRequestData) = {
    val sorting = request.sortingFields.foldLeft(new java.util.LinkedHashMap[java.lang.Integer, java.lang.Integer])(
      (a, b) => {
        a.put(Integer.valueOf(b._1), Integer.valueOf(b._2))
        a
      })
    val flatRecords = flatDirectoryBean.getFlatDirectoriesWithFilterRecords(request.page,
      request.limit,
      sorting,
      request.filter,
      request,
      null)

    new FlatDirectoryData(flatRecords, request)
  }

  def getAllMkbs(request: ListDataRequest, auth: AuthData) = {
    request.setRecordsCount(dbCustomQueryBean.getCountOfMkbsWithFilter(request.filter))
    val mkbs = dbCustomQueryBean.getAllMkbsWithFilter(request.page,
      request.limit,
      request.sortingFieldInternal,
      request.filter.unwrap())

    val mkbs_display = dbCustomQueryBean.getDistinctMkbsWithFilter(request.sortingFieldInternal,
      request.filter.unwrap())

    val mapper: ObjectMapper = new ObjectMapper()
    val set = new java.util.HashSet[String]
    set.add("class")
    set.add("group")
    set.add("subgroup")
    set.add("mkb")

    if (set.contains(request.filter.asInstanceOf[MKBListRequestDataFilter].view)) {
      mapper.setSerializationConfig(mapper.getSerializationConfig.withView(classOf[AllMKBListDataViews.OneLevelView])) //плоская структурв
    } else {
      mapper.setSerializationConfig(mapper.getSerializationConfig.withView(classOf[AllMKBListDataViews.DefaultView])) //дерево
    }
    mapper.writeValueAsString(new AllMKBListData(mkbs, mkbs_display, request))
  }

  def getThesaurusList(request: ListDataRequest, auth: AuthData) = {
    request.setRecordsCount(dbCustomQueryBean.getCountOfThesaurusWithFilter(request.filter))
    val thesaurus = dbCustomQueryBean.getAllThesaurusWithFilter(request.page,
      request.limit,
      request.sortingFieldInternal,
      request.filter.unwrap())
    new ThesaurusListData(thesaurus, request)
  }

  def getDictionary(request: ListDataRequest, dictName: String /*, auth: AuthData*/) = {

    val mapper: ObjectMapper = new ObjectMapper()

    val list: java.util.List[Object] = dictName match {
      case null => null
      case "bloodTypes" | "bloodPhenotype" =>
        //Группы крови
        val tableName: String = if (dictName.equals("bloodTypes")) "RbBloodType" else "RbBloodPhenotype"
        mapper.setSerializationConfig(mapper.getSerializationConfig.withView(classOf[DictionaryDataViews.DefaultView]))
        request.setRecordsCount(dbBloodTypeBean.getCountOfBloodTypesWithFilter(request.filter, tableName))
        dbBloodTypeBean.getAllBloodTypesWithFilter(request.page - 1,
          request.limit,
          request.sortingFieldInternal,
          request.filter.unwrap(), tableName)
      case "relationships" =>
        //Типы родственных связей
        mapper.setSerializationConfig(mapper.getSerializationConfig.withView(classOf[DictionaryDataViews.DefaultView]))
        request.setRecordsCount(dbRelationTypeBean.getCountOfRelationsWithFilter(request.filter))
        dbRelationTypeBean.getAllRelationsWithFilter(request.page - 1,
          request.limit,
          request.sortingFieldInternal,
          request.filter.unwrap())
      case "citizenships" =>
        //Гражданство
        mapper.setSerializationConfig(mapper.getSerializationConfig.withView(classOf[DictionaryDataViews.DefaultView]))
        request.setRecordsCount(dbRbSocTypeBean.getCountOfSocStatusTypesWithFilter(request.filter))
        dbRbSocTypeBean.getAllSocStatusTypesWithFilter(request.page - 1,
          request.limit,
          request.sortingFieldInternal,
          request.filter.unwrap())
      case "citizenships2" =>
        //Второе гражданство
        mapper.setSerializationConfig(mapper.getSerializationConfig.withView(classOf[DictionaryDataViews.DefaultView]))
        request.setRecordsCount(dbRbSocTypeBean.getCountOfSocStatusTypesWithFilter(request.filter))
        dbRbSocTypeBean.getAllSocStatusTypesWithFilter(request.page - 1,
          request.limit,
          request.sortingFieldInternal,
          request.filter.unwrap())
      case "socStatus" =>
        //Соц статусы
        mapper.setSerializationConfig(mapper.getSerializationConfig.withView(classOf[DictionaryDataViews.DefaultView]))
        request.setRecordsCount(dbRbSocTypeBean.getCountOfSocStatusTypesWithFilter(request.filter))
        dbRbSocTypeBean.getAllSocStatusTypesWithFilter(request.page - 1,
          request.limit,
          request.sortingFieldInternal,
          request.filter.unwrap())
      case "TFOMS" =>
        //ТФОМС
        mapper.setSerializationConfig(mapper.getSerializationConfig.withView(classOf[DictionaryDataViews.TFOMSView]))
        request.setRecordsCount(dbOrganizationBean.getCountOfOrganizationWithFilter(request.filter))
        dbOrganizationBean.getAllOrganizationWithFilter(request.page - 1,
          request.limit,
          request.sortingFieldInternal,
          request.filter.unwrap())
      case "clientDocument" =>
        //Типы документов, удостоверяющих личность
        mapper.setSerializationConfig(mapper.getSerializationConfig.withView(classOf[DictionaryDataViews.ClientDocumentView]))
        request.setRecordsCount(dbDocumentTypeBean.getCountOfDocumentTypesWithFilter(request.filter))
        dbDocumentTypeBean.getAllDocumentTypesWithFilter(request.page - 1,
          request.limit,
          request.sortingFieldInternal,
          request.filter.unwrap())
      case "insurance" =>
        //Страховые компании
        mapper.setSerializationConfig(mapper.getSerializationConfig.withView(classOf[DictionaryDataViews.InsuranceView]))
        request.setRecordsCount(dbOrganizationBean.getCountOfOrganizationWithFilter(request.filter))
        dbOrganizationBean.getAllOrganizationWithFilter(request.page - 1,
          request.limit,
          request.sortingFieldInternal,
          request.filter.unwrap())
      case "policyTypes" =>
        //Тип полиса
        mapper.setSerializationConfig(mapper.getSerializationConfig.withView(classOf[DictionaryDataViews.PolicyTypeView]))
        request.setRecordsCount(dbRbPolicyTypeBean.getCountOfRbPolicyTypeWithFilter(request.filter))
        dbRbPolicyTypeBean.getAllRbPolicyTypeWithFilter(request.page - 1,
          request.limit,
          request.sortingFieldInternal,
          request.filter.unwrap())
      case "disabilityTypes" =>
        //Тип инвалидности
        mapper.setSerializationConfig(mapper.getSerializationConfig.withView(classOf[DictionaryDataViews.DefaultView]))
        request.setRecordsCount(dbRbSocTypeBean.getCountOfSocStatusTypesWithFilter(request.filter))
        dbRbSocTypeBean.getAllSocStatusTypesWithFilter(request.page - 1,
          request.limit,
          request.sortingFieldInternal,
          request.filter.unwrap())
      case "KLADR" =>
        //адреса по кладру
        mapper.setSerializationConfig(mapper.getSerializationConfig.withView(classOf[DictionaryDataViews.KLADRView]))
        request.setRecordsCount(dbSchemeKladrBean.getCountOfKladrRecordsWithFilter(request.filter))
        dbSchemeKladrBean.getAllKladrRecordsWithFilter(request.page - 1,
          request.limit,
          request.sortingFieldInternal,
          request.filter.unwrap())
      case "valueDomain" =>
        mapper.setSerializationConfig(mapper.getSerializationConfig.withView(classOf[DictionaryDataViews.ValueDomainView]))
        //request.setRecordsCount(dbSchemeKladrBean.getCountOfKladrRecordsWithFilter(request.filter))
        actionPropertyTypeBean.getActionPropertyTypeValueDomainsWithFilter(request.page - 1,
          request.limit,
          request.sortingFieldInternal,
          request.filter.unwrap())
      case "specialities" =>
        //  Специальности
        mapper.setSerializationConfig(mapper.getSerializationConfig.withView(classOf[DictionaryDataViews.DefaultView]))
        request.setRecordsCount(dbSpeciality.getCountOfBloodTypesWithFilter(request.filter))
        dbSpeciality.getAllSpecialitiesWithFilter(request.page - 1,
          request.limit,
          request.sortingFieldInternal,
          request.filter.unwrap())
      case "contactTypes" =>
        //  Типы контактов
        mapper.setSerializationConfig(mapper.getSerializationConfig.withView(classOf[DictionaryDataViews.DefaultView]))
        request.setRecordsCount(dbRbContactType.getCountOfAllRbContactTypesWithFilter(request.filter))
        dbRbContactType.getAllRbContactTypesWithFilter(request.page - 1,
          request.limit,
          request.sortingFieldInternal,
          request.filter.unwrap())
      case "requestTypes" =>
        //  Типы обращений
        mapper.setSerializationConfig(mapper.getSerializationConfig.withView(classOf[DictionaryDataViews.RequestTypesView]))
        dbRbRequestTypes.getAllRbRequestTypesWithFilter(request.page - 1,
          request.limit,
          request.sortingFieldInternal,
          request.filter.unwrap(),
          request.rewriteRecordsCount)
      case "finance" =>
        //  Типы оплаты
        mapper.setSerializationConfig(mapper.getSerializationConfig.withView(classOf[DictionaryDataViews.DefaultView]))
        dbRbFinance.getAllRbFinanceWithFilter(request.page - 1,
          request.limit,
          request.sortingFieldInternal,
          request.filter.unwrap(),
          request.rewriteRecordsCount)
      case "quotaStatus" =>
        //   Статусы квот
        mapper.setSerializationConfig(mapper.getSerializationConfig.withView(classOf[DictionaryDataViews.DefaultView]))
        dbRbQuotaStatus.getAllRbQuotaStatusWithFilter(request.page - 1,
          request.limit,
          request.sortingFieldInternal,
          request.filter.unwrap(),
          request.rewriteRecordsCount)
      case "tissueTypes" =>
        //Типы исследования
        mapper.setSerializationConfig(mapper.getSerializationConfig.withView(classOf[DictionaryDataViews.DefaultView]))
        dbRbTissueType.getAllRbTissueTypeWithFilter(request.page - 1,
          request.limit,
          request.sortingFieldInternal,
          request.filter.unwrap(),
          request.rewriteRecordsCount)
      case "operationTypes" =>
        //Типы операций
        mapper.setSerializationConfig(mapper.getSerializationConfig.withView(classOf[DictionaryDataViews.DefaultView]))
        dbRbOperationType.getAllRbOperationTypeWithFilter(request.page - 1,
          request.limit,
          request.sortingFieldInternal,
          request.filter.unwrap(),
          request.rewriteRecordsCount)
    }
    mapper.writeValueAsString(new DictionaryListData(list, request))
  }

  def getQuotaTypes(request: ListDataRequest) = {
    val quotaTypes = dbQuotaTypeBean.getAllQuotaTypesWithFilter(request.page - 1,
      request.limit,
      request.sortingFieldInternal,
      request.filter.unwrap(),
      request.rewriteRecordsCount)
    new GroupTypesListData(quotaTypes, request)
  }

  //Сервисы по назначениям
  def insertAssignment(assignmentData: AssignmentData, eventId: Int, auth: AuthData) = {

    val action = assignmentBean.insertAssignmentForPatient(assignmentData, eventId, auth)
    assignmentBean.getAssignmentById(action.getId.intValue())
  }

  def getAssignmentById(actionId: Int, auth: AuthData) = assignmentBean.getAssignmentById(actionId)


  def getEventTypes(request: ListDataRequest, authData: AuthData) = {
    val mapper: ObjectMapper = new ObjectMapper()
    mapper.setSerializationConfig(mapper.getSerializationConfig.withView(classOf[DictionaryDataViews.DefaultView]))

    val list = dbEventTypeBean.getEventTypesByRequestTypeIdAndFinanceId(request.page - 1,
      request.limit,
      request.sortingFieldInternal,
      request.filter.unwrap(),
      request.rewriteRecordsCount)

    mapper.writeValueAsString(new EventTypesListData(list, request))
  }

  def getContracts(eventTypeId: Int, eventTypeCode: String, showDeleted: Boolean, showExpired: Boolean) = {
    val e = if (eventTypeCode != null && !eventTypeCode.isEmpty()) dbEventTypeBean.getEventTypeByCode(eventTypeCode) else dbEventTypeBean.getEventTypeById(eventTypeId)
    val result = dbContractBean.getContractsByEventTypeId(eventTypeId, e.getFinance.getId, showDeleted, showExpired)
    if (result == null)
      new ju.ArrayList[ContractContainer]()
    else
      result.map(x => new ContractContainer(x)).asJava
  }

  def getPatientsFromOpenAppealsWhatHasBedsByDepartmentId(departmentId: Int) = {
    patientBean.getCurrentPatientsByDepartmentId(departmentId)
  }

  def getTakingOfBiomaterial(request: TakingOfBiomaterialRequesData, authData: AuthData) = {

    val res = dbJobTicketBean.getDirectionsWithJobTicketsBetweenDate(request, request.filter)
    //пересоберем мапу и сгруппируем по жобТикету
    var actions = new java.util.LinkedList[(Action, ActionTypeTissueType)]()
    var map = new mutable.LinkedHashMap[JobTicket, ju.LinkedList[(Action, ActionTypeTissueType)]]
    if (!res.isEmpty) {
      request.rewriteRecordsCount(res.size().toLong)
      var firstJobTicket = res.iterator.next()._3
      res.foreach(f => {
        if (firstJobTicket.getId.intValue() == f._3.getId.intValue()) {
          actions.add((f._1, f._2))
        } else {
          map += (firstJobTicket -> actions)
          actions = new java.util.LinkedList[(Action, ActionTypeTissueType)]()
          actions.add((f._1, f._2))
          firstJobTicket = f._3
        }
      })
      //добавляем последний жобТикет, если
      // actions.size() > 0 --- остались акшен не добавленные в мапу.
      // map.size == 0 --- если нашли только один жоб тикет, так как мапа пустая и все акшены остались в actions
      if (actions.size() > 0 || map.size == 0) map += (firstJobTicket -> actions)
    }
    new TakingOfBiomaterialData(map,
      hospitalBedBean.getLastMovingActionForEventId,
      actionPropertyBean.getActionPropertiesByActionIdAndRbCoreActionPropertyIds,
      request)
  }


  override def getJobTicketById(id: Int, authData: AuthData): JobTicket = {
    dbJobTicketBean getJobTicketById id
  }

  def updateJobTicketsStatuses(data: JobTicketStatusDataList, authData: AuthData) = {
    directionBean.updateJobTicketsStatuses(data, authData)
  }

  def deletePatientInfo(id: Int) = patientBean.deletePatientInfo(id)

  def getDiagnosesByAppeal(appealId: Int, authData: AuthData) = diagnosisBean.getDiagnosesByAppeal(appealId)

  def getBloodTypesHistory(patientId: Int, authData: AuthData) = patientBean.getBloodHistory(patientId)

  def insertBloodTypeForPatient(patientId: Int, data: BloodHistoryData, authData: AuthData) = {
    patientBean.insertBloodTypeForPatient(patientId,
      data,
      authData)
  }

  def getMonitoringInfoByAppeal(eventId: Int, condition: Int, authData: AuthData) = {
    appealBean.getMonitoringInfo(eventId, condition, authData)
  }


  def getInfectionMonitoring(eventId: Int, authData: AuthData) = {
    val outList = new java.util.ArrayList[java.util.List[AnyRef]]()
    appealBean.getInfectionMonitoring(dbEventBean.getEventById(eventId).getPatient)
      .foreach(p => outList.add(List[AnyRef](p._1, ISODate(p._2), ISODate(p._3), p._4)))
    val resList = new java.util.ArrayList[java.util.List[AnyRef]]()
    //TODO убрать этот костыль, outList не должен содержать дублей
    outList.foreach(i => {
      if (i.size() > 2 && (resList.isEmpty ||
        resList.last.get(0) != i.get(0) ||
        resList.last.get(1) != i.get(1) )) {
        resList.add(i)
      }
    })
    resList
  }

  def getInfectionDrugMonitoring(eventId: Int, authData: AuthData) = {
    val outList = new java.util.ArrayList[java.util.List[AnyRef]]()
    appealBean.getInfectionDrugMonitoring(dbEventBean.getEventById(eventId).getPatient)
      .foreach(p => outList.add(List[AnyRef](p._1, ISODate(p._2), ISODate(p._3), p._4, p._5).asJava))
    outList
  }

  def getInfectionDrugMonitoringList(eventId: Int) = {
    monitoringBeanLocal.getInfectionDrugMonitoring(dbEventBean.getEventById(eventId))
  }

  def getSurgicalOperationsByAppeal(eventId: Int, authData: AuthData) = {
    appealBean.getSurgicalOperations(eventId, authData)
  }

  def setExecPersonForAppeal(eventId: Int, personId: Int, authData: AuthData) = {
    appealBean.setExecPersonForAppeal(eventId, personId, authData, ExecPersonSetType.EP_SET_IN_LPU)
  }

  def getLayoutAttributes = new LayoutAttributeListData(dbLayoutAttributeBean.getAllLayoutAttributes)

  def getBakResult(actionId: Int, authData: AuthData): BakLabResultDataContainer = {
    val response = dbBbtResponseBean.get(actionId)
    if (response == null)
      throw new CoreException("В базе данных отсутствуют результаты исследований БАК лаборатории для исследования с id=" + actionId)

    val r = new BakLabResultDataContainer(
      response,
      dbBbtResultTextBean.getByActionId(actionId),
      dbBbtResultOrganismBean.getByActionId(actionId)
    )
    r
  }

  def getBuildVersion: String = {
    i18n("build.jenkins.number").format()
  }

  def getRbPrintTemplatesByIds(ids: ju.List[Integer], authData: AuthData): ju.List[RbPrintTemplate] = {
    dbRBPrintTemplateBan getRbPrintTemplateByIds ids.asScala.toArray.map(_.toInt)
  }

  def getRbPrintTemplatesByContexts(contexts: ju.List[String], authData: AuthData, fields: Array[String], fRender: Integer): ju.List[RbPrintTemplate] = {
    var templates = dbRBPrintTemplateBan getRbPrintTemplatesByContexts contexts.asScala.toArray

    // Проводим фильтрацию по заданным значениям
    if (fRender != null)
      templates = templates.filter(_.getRender == fRender)


    // Убираем значения не заданных полей, если задан параметр fields
    if (fields != null && fields.nonEmpty) {
      templates.foreach(template => {
        classOf[RbPrintTemplate].getDeclaredFields.foreach(f => {
          if (!fields.contains(f.getName)) {
            f.getType match {
              case t if t.equals(classOf[String]) =>
                f.setAccessible(true)
                f.set(template, null)
              case t if t.equals(classOf[Integer]) =>
                f.setAccessible(true)
                f.set(template, null)
              case _ =>
            }
          }
        })

      })
    }
    seqAsJavaList(templates)
  }

  def getOrganizationById(id: Int, authData: AuthData): OrganizationContainer = {
    val org = dbOrganizationBean.getOrganizationById(id)
    if (org != null)
      new OrganizationContainer(org)
    else
      throw new CoreException("Cannot find Organization with id=" + id)
  }

  //__________________________________________________________________________________________________
  //***************  AUTHDATA  *******************
  //__________________________________________________________________________________________________

  def checkTokenCookies(cookies: lang.Iterable[Cookie]) = {
    authStorage.checkTokenCookies(cookies)
  }

  def getStorageAuthData(token: AuthToken) = {
    authStorage.getAuthData(token)
  }

  def removeAction(actionId: Int) {
    actionBean.removeAction(actionId)
    dbDiagnosticBean.deleteDiagnostic(actionId)
  }

  def saveAutoSaveField(id: String, text: String, auth: AuthData) = {
    dbAutoSaveStorageLocal.save(id, auth.getUserId, text)
    ""
  }

  def loadAutoSaveField(id: String, auth: AuthData) = {
    dbAutoSaveStorageLocal.load(id, auth.getUserId)
  }

  def deleteAutoSaveField(id: String, auth: AuthData) {
    dbAutoSaveStorageLocal.delete(id, auth.getUserId)
  }

  override def getRlsById(id: Int): DrugData = {
    val n: Nomenclature = dbRlsBean.getRlsById(id)
    new DrugData(n, dbRlsBean.getRlsBalanceOfGood(n))
  }

  override def getRlsByText(text: String): ju.List[DrugData] = {
    val res = new ju.LinkedList[DrugData]();
    dbRlsBean.getRlsByText(text).foreach(n => {
      res.add(new DrugData(n, dbRlsBean.getRlsBalanceOfGood(n)))
    })
    return res
  }

  def getLabs: java.util.List[RbLaboratory] = dbRbLaboratory.getAllLabs

  /**
   * @param patientId Идентификатор пациента
   * @return Данные о терапии в пределах всех историй болезни
   */
  def getTherapiesInfo(patientId: Int): ju.List[TherapyContainer] = {

    val actions = actionBean.getAllActionsOfPatientThatHasActionProperty(patientId, "therapyTitle")

    val l = actions.flatMap(a => {

      val properties = a.getActionProperties

      val therapy = {
        val props = properties.filter(p => p.getType.getCode != null && p.getType.getCode.equals("therapyTitle"))
        props.size match {
          case 1 =>
            val values = actionPropertyBean.getActionPropertyValue(props.head)
            values.size match {
              case 1 => values.head match {
                case h: APValueFlatDirectory => Option(h);
                case _ => None
              }
              case 0 => None
              case _ => throw new CoreException("Invalid action property values " + props.head)
            }
          case _ => throw new CoreException("Invalid action type configuration " + a.getActionType)
        }
      }

      val begDate = {
        val props = properties.filter(p => p.getType.getCode != null && p.getType.getCode.equals("therapyBegDate"))
        props.size match {
          case 1 =>
            val values = actionPropertyBean.getActionPropertyValue(props.head)
            values.size match {
              case 1 => values.head match {
                case h: APValueDate => Option(h.getValue);
                case _ => None
              }
              case 0 => None
              case _ => throw new CoreException("Invalid action property values " + props.head)
            }
          case _ => throw new CoreException("Invalid action type configuration " + a.getActionType)
        }
      }

      val endDate = {
        val props = properties.filter(p => p.getType.getCode != null && p.getType.getCode.equals("therapyEndDate"))
        props.size match {
          case 1 =>
            val values = actionPropertyBean.getActionPropertyValue(props.head)
            values.size match {
              case 1 => values.head match {
                case h: APValueDate => Option(h.getValue);
                case _ => None
              }
              case 0 => None
              case _ => throw new CoreException("Invalid action property values " + props.head)
            }
          case _ => throw new CoreException("Invalid action type configuration " + a.getActionType)
        }
      }

      val therapyPhase = {
        val props = properties.filter(p => p.getType.getCode != null && p.getType.getCode.equals("therapyPhaseTitle"))
        props.size match {
          case 1 =>
            val values = actionPropertyBean.getActionPropertyValue(props.head)
            values.size match {
              case 1 => values.head match {
                case h: APValueFlatDirectory => Option(h.getValue);
                case _ => None
              }
              case 0 => None
              case _ => throw new CoreException("Invalid action property values " + props.head)
            }
          case _ => throw new CoreException("Invalid action type configuration " + a.getActionType)
        }
      }

      val phaseBegDate = {
        val props = properties.filter(p => p.getType.getCode != null && p.getType.getCode.equals("therapyPhaseBegDate"))
        props.size match {
          case 1 =>
            val values = actionPropertyBean.getActionPropertyValue(props.head)
            values.size match {
              case 1 => values.head match {
                case h: APValueDate => Option(h.getValue);
                case _ => None
              }
              case 0 => None
              case _ => throw new CoreException("Invalid action property values " + props.head)
            }
          case _ => throw new CoreException("Invalid action type configuration " + a.getActionType)
        }
      }

      val phaseEndDate = {
        val props = properties.filter(p => p.getType.getCode != null && p.getType.getCode.equals("therapyPhaseEndDate"))
        props.size match {
          case 1 =>
            val values = actionPropertyBean.getActionPropertyValue(props.head)
            values.size match {
              case 1 => values.head match {
                case h: APValueDate => Option(h.getValue);
                case _ => None
              }
              case 0 => None
              case _ => throw new CoreException("Invalid action property values " + props.head)
            }
          case _ => throw new CoreException("Invalid action type configuration " + a.getActionType)
        }
      }

      val phaseDay = {
        val props = properties.filter(p => p.getType.getCode != null && p.getType.getCode.equals("therapyPhaseDay"))
        props.size match {
          case 1 =>
            val values = actionPropertyBean.getActionPropertyValue(props.head)
            values.size match {
              case 1 => values.head match {
                case h: APValueString => Option(h.getValue);
                case _ => None
              }
              case 0 => None
              case _ => throw new CoreException("Invalid action property values " + props.head)
            }
          case _ => throw new CoreException("Invalid action type configuration " + a.getActionType)
        }
      }

      (therapy, therapyPhase) match {
        case (Some(x), Some(y)) =>
          val valMap = x.getValue.getFieldValues.map(v => v.getFDField.getName -> v.getValue).toMap
          val phaseValMap = y.getFieldValues.map(v => v.getFDField.getName -> v.getValue).toMap
          Option((
            x.getValue.getId,
            valMap("Наименование"),
            valMap("Ссылка"),
            begDate,
            endDate,
            "unknownEventId",
            phaseValMap("Наименование"),
            phaseValMap("Ссылка"),
            y.getId,
            phaseBegDate,
            phaseEndDate,
            phaseDay,
            a.getCreateDatetime,
            a.getEvent.getId,
            a.getId
            ))
        case _ => None
      }

    }).toSet

    val g = l.groupBy(p => (p._1, p._2, p._3, p._4)).map(p => {
      val therapyEndDateInGroup = p._2.map(_._5).toList.sorted.last.orNull
      val e = p._2.groupBy(s => (s._6, s._7, s._8, s._9, s._10)).map(t => {
        val therapyPhaseEndDateInGroup = t._2.map(_._11).toList.sorted.last.orNull
        val d = t._2.map(y => new TherapyDay(y._12.orNull, y._13, y._14, y._15)).toList.sortBy(_.createDate).asJava
        val event = if (!d.isEmpty) d.head.getEventId else 0
        new TherapyPhase(event, t._1._2, t._1._3, t._1._4, t._1._5.orNull, therapyPhaseEndDateInGroup, d)
      }).toList.asJava
      new TherapyContainer(p._1._1, p._1._2, p._1._3, p._1._4.orNull, therapyEndDateInGroup, e)
    })

    g.toList.asJava
  }

  def lock(actionId: Int, auth: AuthData): LockData = {
    val appLockDetail: AppLockDetail = authStorage.getAppLock(auth.getAuthToken, "Action", actionId)
    new LockData(appLockDetail.getId.getMasterId)
  }

  def prolongLock(actionId: Int, auth: AuthData): LockData = {
    val appLockDetail: AppLockDetail = authStorage.prolongAppLock(auth.getAuthToken, "Action", actionId)
    new LockData(appLockDetail.getId.getMasterId)
  }

  def releaseLock(actionId: Int, auth: AuthData) {
    authStorage.releaseAppLock(auth.getAuthToken, "Action", actionId)
  }

}
