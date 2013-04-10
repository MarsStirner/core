package ru.korus.tmis.ws.impl

import javax.inject.Named
import javax.jws.{HandlerChain, WebService}
import ru.korus.tmis.core.data._
import ru.korus.tmis.core.auth.{AuthToken, AuthStorageBeanLocal, AuthData}
import org.codehaus.jackson.map.ObjectMapper
import ru.korus.tmis.core.exception.CoreException
import java.util
import ru.korus.tmis.util._
import ru.korus.tmis.core.entity.model.{JobTicket, ActionTypeTissueType, Action}
import collection.mutable
import util.LinkedList
import grizzled.slf4j.Logging
import ru.korus.tmis.ws.webmis.rest.WebMisREST
import javax.ejb.EJB
import ru.korus.tmis.core.database._
import ru.korus.tmis.core.patient._
import ru.korus.tmis.util.StringId
import scala.collection.JavaConversions._
import ru.korus.tmis.core.assessment.AssessmentBeanLocal
import ru.korus.tmis.core.diagnostic.DiagnosticBeanLocal
import ru.korus.tmis.core.thesaurus.ThesaurusBeanLocal
import ru.korus.tmis.core.treatment.TreatmentBeanLocal
import com.google.common.collect.Lists
import javax.servlet.http.HttpServletRequest

/**
 * Created with IntelliJ IDEA.
 * User: idmitriev
 * Date: 3/19/13
 * Time: 11:53 AM
 * To change this template use File | Settings | File Templates.
 */

/*@Named
@WebService(
  endpointInterface = "ru.korus.tmis.ws.medipad.WebMisService",
  targetNamespace = "http://korus.ru/tmis/webmis",
  serviceName = "tmis-webmis",
  portName = "webmis",
  name = "webmis"
)
@HandlerChain(file = "tmis-ws-logging-handlers.xml") */
class WebMisRESTImpl  extends WebMisREST
                      with Logging
                      with I18nable {
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
  private  var dbDocumentTypeBean: DbRbDocumentTypeBeanLocal = _

  @EJB
  private  var dbOrganizationBean: DbOrganizationBeanLocal = _

  @EJB
  private var dbRbPolicyTypeBean: DbRbPolicyTypeBeanLocal = _

  @EJB
  private var dbRbSocTypeBean: DbRbSocTypeBeanLocal = _

  @EJB
  private var dbSchemeKladrBean: DbSchemeKladrBeanLocal = _

  @EJB
  private var hospitalBedBean: HospitalBedBeanLocal = _

  @EJB
  private var assignmentBean: AssignmentBeanLocal = _

  @EJB
  private var dbRlsBean: DbRlsBeanLocal = _

  @EJB
  private var dbSpeciality: DbRbSpecialityBeanLocal = _

  @EJB
  private var dbRbCoreActionPropertyBean: DbRbCoreActionPropertyBeanLocal = _

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
  var directionBean: DirectionBeanLocal = _

  @EJB
  var dbDiagnosticBean: DbDiagnosticBeanLocal = _

  def getAllPatients(requestData: PatientRequestData, auth: AuthData): PatientData = {
    if (auth != null) {
      val patients = patientBean.getAllPatients(requestData)
      return new PatientData(Lists.newArrayList(patients),requestData)
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

  def insertPatient(patientData: PatientCardData, auth: AuthData) : PatientCardData = {
    //requiresPermissions(Array("clientAssessmentCreate"))
    val inPatientEntry = patientData.getData()
    if (inPatientEntry != null) {
      if (auth != null) {
        val outPatientEntry: PatientEntry = patientBean.savePatient(-1, inPatientEntry, auth)  //currentAuthData
        patientData.setData(outPatientEntry)
        return patientData
      }
    }

    throw new CoreException(
      i18n("error.cantSavePatient").format()
    )
  }

  def updatePatient(id: Int, patientData: PatientCardData, auth: AuthData) : PatientCardData = {
    //requiresPermissions(Array("clientAssessmentCreate"))
    val inPatientEntry = patientData.getData()
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
  def checkExistanceNumber(name : String, typeId: Int, number : String, serial: String) = {
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
  def insertAppealForPatient(appealData : AppealData, patientId: Int, auth: AuthData) = {

    val ide = appealBean.insertAppealForPatient(appealData, patientId, auth)
    constructAppealData(ide)
  }
  def updateAppeal(appealData : AppealData, eventId: Int, auth: AuthData) = {

    val ide = appealBean.updateAppeal(appealData, eventId, auth)
    constructAppealData(ide)
  }

  private def constructAppealData(ide: Int) = {
    if(ide>0) {
      val result = appealBean.getAppealById(ide)

      val positionE = result.iterator.next()
      val positionA = positionE._2.iterator.next()
      val values = positionA._2.asInstanceOf[java.util.Map[java.lang.Integer, java.util.List[Object]]]

      val mapper: ObjectMapper = new ObjectMapper()
      mapper.getSerializationConfig().setSerializationView(classOf[Views.DynamicFieldsStandartForm]);
      val patient = positionE._1.getPatient
      val map = patientBean.getKLADRAddressMapForPatient(patient)
      val street = patientBean.getKLADRStreetForPatient(patient)
      //val appType = dbFDRecordBean.getIdValueFDRecordByEventTypeId(25, positionE._1.getEventType.getId.intValue())
      mapper.writeValueAsString(new AppealData( positionE._1,
        positionA._1,
        values,
        null,
        "standart",
        map,
        street,
        null,
        actionBean.getLastActionByActionTypeIdAndEventId _,  //havePrimary
        dbClientRelation.getClientRelationByRelativeId _,
        null,
        null,
        if (positionA._1.getContractId != null) {
          dbContractBean.getContractById(positionA._1.getContractId.intValue())
        } else {null},
        dbDiagnosticBean.getDiagnosticsByEventIdAndTypes _
      ))
    } else {
      throw new CoreException("Неудачная попытка сохранения(изменения) обращения")
    }
  }

  def getAppealById(id: Int, auth: AuthData) = {

    val result = appealBean.getAppealById(id)

    val positionE = result.iterator.next()
    val positionA = positionE._2.iterator.next()
    val values = positionA._2.asInstanceOf[java.util.Map[java.lang.Integer, java.util.List[Object]]]

    val mapper: ObjectMapper = new ObjectMapper()
    mapper.getSerializationConfig().setSerializationView(classOf[Views.DynamicFieldsStandartForm]);
    //val map = patientBean.getKLADRAddressMapForPatient(result)
    //val appType = dbFDRecordBean.getIdValueFDRecordByEventTypeId(25, positionE._1.getEventType.getId.intValue())
    mapper.writeValueAsString(new AppealData( positionE._1,
      positionA._1,
      values,
      null,
      "standart",
      null,
      null,
      null,
      actionBean.getLastActionByActionTypeIdAndEventId _,  //havePrimary
      dbClientRelation.getClientRelationByRelativeId _,
      actionPropertyBean.getActionPropertiesByActionIdAndRbCoreActionPropertyIds _,
      dbRbCoreActionPropertyBean.getRbCoreActionPropertiesByIds _,                    //таблица соответствия
      if (positionE._1.getContract != null) {
        dbContractBean.getContractById(positionE._1.getContract.getId.intValue())
      } else {null},
      dbDiagnosticBean.getDiagnosticsByEventIdAndTypes _
    ))
  }

  def getAppealPrintFormById(id: Int, auth: AuthData) = {
    val result = appealBean.getAppealById(id)
    val positionE = result.iterator.next()
    val positionA = positionE._2.iterator.next()
    val values = positionA._2.asInstanceOf[java.util.Map[java.lang.Integer, java.util.List[Object]]]
    val mapper: ObjectMapper = new ObjectMapper()

    mapper.getSerializationConfig().setSerializationView(classOf[Views.DynamicFieldsPrintForm])

    val map = patientBean.getKLADRAddressMapForPatient(positionE._1.getPatient)
    val street = patientBean.getKLADRStreetForPatient(positionE._1.getPatient)

    mapper.writeValueAsString(new AppealData( positionE._1,
      positionA._1,
      values,
      actionPropertyBean.getActionPropertiesForEventByActionTypes _,
      "print_form",
      map,
      street,
      null,
      actionBean.getLastActionByActionTypeIdAndEventId _, //havePrimary
      dbClientRelation.getClientRelationByRelativeId _,
      actionPropertyBean.getActionPropertiesByActionIdAndRbCoreActionPropertyIds _,  //в тч Admission Diagnosis
      dbRbCoreActionPropertyBean.getRbCoreActionPropertiesByIds _,          //таблица соответствия
      if (positionA._1.getContractId != null) {
        dbContractBean.getContractById(positionA._1.getContractId.intValue())
      } else {null},
      dbDiagnosticBean.getDiagnosticsByEventIdAndTypes _
    ))
  }

  def getAllAppealsByPatient(requestData: AppealSimplifiedRequestData, auth: AuthData): AppealSimplifiedDataList = {
    val set = appealBean.getAppealTypeCodesWithFlatDirectoryId(i18n("db.flatDirectory.eventType.hospitalization").toInt) //справочник госпитализаций
    requestData.filter.asInstanceOf[AppealSimplifiedRequestDataFilter].code = set.asInstanceOf[util.Collection[String]]
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
    if(requestData.filter.asInstanceOf[ReceivedRequestDataFilter].role<=0)
      requestData.filter.asInstanceOf[ReceivedRequestDataFilter].role = authData.getUserRole.getId.intValue()  //Роль из данных авторизации (клиент пока не использует)

    val mapper: ObjectMapper = new ObjectMapper()
    requestData.filter.asInstanceOf[ReceivedRequestDataFilter].role match {
      case 29 => mapper.getSerializationConfig().setSerializationView(classOf[ReceivedPatientsDataViews.AdmissionDepartmentsNurseView]) //Сестра приемного отделения
      case _ =>  mapper.getSerializationConfig().setSerializationView(classOf[ReceivedPatientsDataViews.AdmissionDepartmentsDoctorView]) //Доктор
    }

    requestData.setRecordsCount(appealBean.getCountOfAppealsForReceivedPatientByPeriod(requestData.filter))
    val data = if(requestData.recordsCount!=0){
      var received = appealBean.getAllAppealsForReceivedPatientByPeriod(requestData.page-1,
        requestData.limit,
        requestData.sortingFieldInternal,
        requestData.sortingMethod,
        requestData.filter)
      new ReceivedPatientsData( received,
        requestData,
        appealBean.getDiagnosisListByAppealId _,
        actionPropertyBean.getActionPropertiesForEventByActionTypes _,
        actionBean.getLastActionByActionTypeIdAndEventId _,
        appealBean.getPatientsHospitalizedStatus _,
        actionBean.getAppealActionByEventId _)
    } else new ReceivedPatientsData()

    mapper.writeValueAsString(data)
  }

  //запрос на структуру первичного мед. осмотра
  def getStructOfPrimaryMedExam(actionTypeId: Int, authData: AuthData) = {
    //TODO: подключить анализ авторизационных данных и доступных ролей
    //primaryAssessmentBean.getPrimaryAssessmentEmptyStruct("1_1_01", "PrimaryAssesment", null)
    var listForConverter = new java.util.ArrayList[String]
    listForConverter.add(ActionPropertyWrapperInfo.Value.toString)
    listForConverter.add(ActionPropertyWrapperInfo.ValueId.toString)
    listForConverter.add(ActionPropertyWrapperInfo.Norm.toString)
    listForConverter.add(ActionPropertyWrapperInfo.Unit.toString)

    var listForSummary = new java.util.ArrayList[StringId]
    listForSummary.add(ActionWrapperInfo.assessmentId)
    listForSummary.add(ActionWrapperInfo.assessmentName)
    listForSummary.add(ActionWrapperInfo.assessmentBeginDate)
    listForSummary.add(ActionWrapperInfo.assessmentEndDate)
    listForSummary.add(ActionWrapperInfo.doctorLastName)
    listForSummary.add(ActionWrapperInfo.doctorFirstName)
    listForSummary.add(ActionWrapperInfo.doctorMiddleName)
    listForSummary.add(ActionWrapperInfo.doctorSpecs)
    listForSummary.add(ActionWrapperInfo.urgent)
    listForSummary.add(ActionWrapperInfo.multiplicity)
    listForSummary.add(ActionWrapperInfo.finance)
    listForSummary.add(ActionWrapperInfo.plannedEndDate)
    //listForSummary.add(ActionWrapperInfo.toOrder)

    primaryAssessmentBean.getEmptyStructure(actionTypeId,
      "PrimaryAssesment",
      listForConverter,
      listForSummary,
      authData,
      postProcessing _,
      null)
  }

  //запрос на структуру первичного мед. осмотра с копированием данных из предыдущего осмотра
  def getStructOfPrimaryMedExamWithCopy(actionTypeId: Int, authData: AuthData, eventId: Int) = {
    var lastActionId = actionBean.getActionIdWithCopyByEventId(eventId, actionTypeId)
    try {
      primaryAssessmentBean.getPrimaryAssessmentById(lastActionId, "Assessment", authData, postProcessing _, false) //postProcessingWithCopy _, true)
    }
    catch {
      case e: Exception => {
        getStructOfPrimaryMedExam(actionTypeId, authData)
      }
    }

  }

  private def preProcessing (jData: JSONCommonData, reWriteId: java.lang.Boolean) = {
    //Предбработка (Сопоставление CoreAP с id APT в подветке details - id, typeId)
    jData.data.get(0).group.get(1).attribute.foreach(core => {
      core.typeId = dbRbCoreActionPropertyBean.getRbCoreActionPropertiesById(core.typeId.intValue()).getActionPropertyType.getId
      if(reWriteId.booleanValue) core.id = core.typeId
    })
    jData
  }

  private def postProcessing (jData: JSONCommonData, reWriteId: java.lang.Boolean) = {
    //Постобработка (Сопоставление id APT c CoreAP в подветке details - id, typeId)
    jData.data.get(0).group.get(1).attribute.foreach(ap => {
      var value = if(reWriteId.booleanValue)
        ap.id.intValue()
      else {
        if(ap.typeId!=null && ap.typeId.intValue()>0)
          ap.typeId.intValue()
        else
          actionPropertyBean.getActionPropertyById(ap.id.intValue()).getType.getId.intValue()
      }
      ap.typeId = dbRbCoreActionPropertyBean.getRbCoreActionPropertiesByActionPropertyTypeId(value).getId.intValue()
      if(reWriteId.booleanValue) ap.id =ap.typeId
    })
    jData
  }

  private def postProcessingForDiagnosis (jData: JSONCommonData, reWriteId: java.lang.Boolean) = {
    jData.data.get(0).group.get(1).attribute.foreach(ap => {
      val value = if(ap.typeId!=null && ap.typeId.intValue()>0)
        ap.typeId.intValue()
      else
        actionPropertyBean.getActionPropertyById(ap.id.intValue()).getType.getId.intValue()
      ap.typeId = value //Integer.valueOf(value).intValue()
    })
    jData
  }

  //создание первичного мед. осмотра
  def insertPrimaryMedExamForPatient(eventId: Int, data: JSONCommonData, authData: AuthData)  = {
    //создаем ответственного, если до этого был другой
    val eventPerson = dbEventPerson.getLastEventPersonForEventId(eventId)
    if (eventPerson == null || eventPerson.getPerson != authData.getUser) {
      dbEventPerson.insertOrUpdateEventPerson(if (eventPerson != null) {eventPerson.getId.intValue()} else 0,
        dbEventBean.getEventById(eventId),
        authData.getUser,
        false) //параметр для флаша
    }
    //создаем осмотр. ЕвентПерсон не флашится!!!
    val returnValue = primaryAssessmentBean.createPrimaryAssessmentForEventId(eventId,
      data,
      "Assessment",
      authData,
      preProcessing _,
      postProcessing _)
    dbEventBean.setExecPersonForEventWithId(eventId, authData.getUser)
    returnValue
  }

  //редактирование первичного мед. осмотра
  def modifyPrimaryMedExamForPatient(actionId: Int, data: JSONCommonData, authData: AuthData)  = {
    //создаем ответственного, если до этого был другой
    val eventPerson = dbEventPerson.getLastEventPersonForEventId(actionBean.getActionById(actionId).getEvent.getId.intValue())
    if (eventPerson.getPerson != authData.getUser) {
      dbEventPerson.insertOrUpdateEventPerson(if (eventPerson != null) {eventPerson.getId.intValue()} else 0,
        actionBean.getActionById(actionId).getEvent,
        authData.getUser,
        false)
    }
    //создаем осмотр. ЕвентПерсон не флашится!!!
    val returnValue = primaryAssessmentBean.modifyPrimaryAssessmentById(actionId,
      data,
      "Assessment",
      authData,
      preProcessing _,
      postProcessing _)
    returnValue
  }

  def getPrimaryAssessmentById (assessmentId: Int, authData: AuthData) = {

    //TODO: подключить анализ авторизационных данных и доступных ролей
    //val authData:AuthData = null

    val json_data = primaryAssessmentBean.getPrimaryAssessmentById(assessmentId,
      "Assessment",
      authData,
      postProcessing _, false)

    json_data
  }

  def getAllPatientsForDepartmentIdAndDoctorIdByPeriod(requestData: PatientsListRequestData, auth: AuthData) = {
    patientBean.getAllPatientsForDepartmentIdAndDoctorIdByPeriod(requestData, auth)
  }

  //Возвращает список осмотров по пациенту и обращению с фильтрацией по типу действия
  def getListOfAssessmentsForPatientByEvent(requestData: AssessmentsListRequestData, auth: AuthData) = {
    val action_list = actionBean.getActionsWithFilter(requestData.limit,
                                                      requestData.page-1,
                                                      requestData.sortingFieldInternal,
                                                      requestData.filter.unwrap(),
                                                      requestData.rewriteRecordsCount _,
                                                      auth)
    //actionBean.getActionsByEventIdWithFilter(requestData.eventId, auth, requestData)
    val assessments: AssessmentsListData = new AssessmentsListData(action_list, requestData)
    assessments
  }

  //<= Hospital Bed =>
  //Данные об регистрации на койке
  def getPatientToHospitalBedById (actionId: Int, authData: AuthData) = {

    val action = actionBean.getActionById(actionId)

    val mapper: ObjectMapper = new ObjectMapper()
    if(action.getEndDate==null) //Если действие закрыто (перевод)
      mapper.getSerializationConfig().setSerializationView(classOf[HospitalBedViews.RegistrationFormView])
    else
      mapper.getSerializationConfig().setSerializationView(classOf[HospitalBedViews.MoveView])
    mapper.writeValueAsString(hospitalBedBean.getRegistryFormWithChamberList(action, authData))
  }

  def getMovingListForEvent(request: HospitalBedDataRequest, authData: AuthData) = {

    val mapper: ObjectMapper = new ObjectMapper()
    mapper.getSerializationConfig().setSerializationView(classOf[HospitalBedViews.MovesListView])
    mapper.writeValueAsString(hospitalBedBean.getMovingListByEventIdAndFilter(request.filter, authData))
  }

  //Регистрирует пациента на койке
  def registryPatientToHospitalBed(eventId: Int, data: HospitalBedData, authData: AuthData) = {

    val action = hospitalBedBean.registryPatientToHospitalBed(eventId, data, authData)

    val mapper: ObjectMapper = new ObjectMapper()
    mapper.getSerializationConfig().setSerializationView(classOf[HospitalBedViews.RegistrationView])
    mapper.writeValueAsString(hospitalBedBean.getRegistryOriginalForm(action, authData))
  }

  //Редактирует регистрацию пациента на койке
  def modifyPatientToHospitalBed(actionId: Int, data: HospitalBedData, authData: AuthData) = {

    val action = hospitalBedBean.modifyPatientToHospitalBed(actionId, data, authData)

    val mapper: ObjectMapper = new ObjectMapper()
    mapper.getSerializationConfig().setSerializationView(classOf[HospitalBedViews.RegistrationView])
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

  /*  def getFormOfAccountingMovementOfPatients(departmentId: Int) = {
    val linear = seventhFormBean.fillInSeventhForm(departmentId, null, null/*previousMedDate, currentMedDate*/)
    new FormOfAccountingMovementOfPatientsData(linear, null)
  }*/

  //форма 007
  def getForm007(departmentId: Int,
                 beginDate: Long,
                 endDate: Long,
                 authData: AuthData) = seventhFormBean.getForm007LinearView(departmentId, beginDate, endDate)


  def movingPatientToDepartment(eventId: Int, data: HospitalBedData, authData: AuthData) = {

    val action = hospitalBedBean.movingPatientToDepartment(eventId, data, authData)

    val mapper: ObjectMapper = new ObjectMapper()
    mapper.getSerializationConfig().setSerializationView(classOf[HospitalBedViews.MoveView])
    mapper.writeValueAsString(hospitalBedBean.getRegistryOriginalForm(action, authData))
  }

  def insertOrUpdateQuota(quotaData: QuotaData, eventId: Int, auth: AuthData) = {
    var quota = appealBean.insertOrUpdateClientQuoting(quotaData.getData, eventId, auth)
    val mapper: ObjectMapper = new ObjectMapper()
    mapper.getSerializationConfig().setSerializationView(classOf[QuotaViews.DynamicFieldsQuotaCreate])
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
      request.page-1,
      request.limit,
      request.sortingFieldInternal,
      request.sortingMethod,
      request.filter,
      request.rewriteRecordsCount _)

    val mapper: ObjectMapper = new ObjectMapper()
    mapper.getSerializationConfig().setSerializationView(classOf[QuotaViews.DynamicFieldsQuotaHistory])
    mapper.writeValueAsString(new QuotaListData(quotaList, request))
  }

  /*
  def insertTalonSPOForPatient(data: Object) = {

    /*newEvent = eventBean.createEvent(/*appealData.data.patient.id.toInt*/patientId,
      /*appealData.data.appealType.id.toInt*/53, //дневной стационар
      authData) */

    null
  }*/

  def getAllTalonsForPatient(requestData: TalonSPOListRequestData) = {

    //TODO: подключить анализ авторизационных данных и доступных ролей
    //requestData.setRecordsCount(dbCustomQueryBean.getCountOfAppealsWithFilter(requestData.filter))
    val map = dbCustomQueryBean.getAllAppealsWithFilter( requestData.limit,
      requestData.page-1,
      requestData.sortingFieldInternal,
      requestData.sortingMethod,
      requestData.filter,
      requestData.rewriteRecordsCount _)
    new TalonSPODataList(map, requestData)
  }

  def getAllPersons(requestData: ListDataRequest) = {

    //TODO: подключить анализ авторизационных данных и доступных ролей
    requestData.setRecordsCount(dbStaff.getCountAllPersonsWithFilter(requestData.filter))
    val list = new AllPersonsListData(dbStaff.getAllPersonsByRequest(requestData.limit,
      requestData.page-1,
      requestData.sortingField,
      requestData.sortingMethod,
      requestData.filter
    ),
      requestData)
    list
  }

  def getAllDepartments(requestData: ListDataRequest) = {
    //TODO: подключить анализ авторизационных данных и доступных ролей
    requestData.setRecordsCount(dbOrgStructureBean.getCountAllOrgStructuresWithFilter(requestData.filter))
    val list = new AllDepartmentsListData(dbOrgStructureBean.getAllOrgStructuresByRequest(requestData.limit,
                                                                                          requestData.page-1,
                                                                                          requestData.sortingFieldInternal,
                                                                                          requestData.filter.unwrap()
                                                                                        ),
                                          requestData)
    list
  }

  def getAllDepartmentsByHasBeds(hasBeds: String, hasPatients: String) = { //Для медипада
  val flgBeds = hasBeds.toLowerCase.compareTo("true")==0 || hasBeds.toLowerCase.compareTo("yes")==0
    val flgPatients = hasPatients.toLowerCase.compareTo("true")==0 || hasPatients.toLowerCase.compareTo("yes")==0
    val filter = new DepartmentsDataFilter(flgBeds, flgPatients)
    new AllDepartmentsListDataMP(dbOrgStructureBean.getAllOrgStructuresByRequest(0, 0, "", filter.unwrap()), null)
  }


  def getListOfDiagnosticsForPatientByEvent(requestData: DiagnosticsListRequestData) = {

    //TODO: подключить анализ авторизационных данных и доступных ролей
    requestData.setRecordsCount(dbCustomQueryBean.getCountDiagnosticsWithFilter(requestData.filter))
    var actions: java.util.List[Action] = null
    if(requestData.getRecordsCount()>0) {
      actions = dbCustomQueryBean.getAllDiagnosticsWithFilter(requestData.page-1,
        requestData.limit,
        requestData.sortingFieldInternal,
        requestData.filter)
    }
    val list = new DiagnosticsListData(actions, requestData)
    list
  }

  def getInfoAboutDiagnosticsForPatientByEvent(actionId: Int) = {
    //TODO: подключить анализ авторизационных данных и доступных ролей
    val authData:AuthData = null

    val json_data = directionBean.getDirectionById(actionId, "Diagnostic", authData, null)
    json_data
  }

  def getFreePersons(requestData: ListDataRequest) = {

    //<= Изменить запрос (ждем отклик)
    //requestData.setRecordsCount(dbStaff.getCountAllPersonsWithFilter(requestData.filter))
    val list = new AllPersonsListData(dbStaff.getEmptyPersonsByRequest( requestData.limit,
      requestData.page-1,
      requestData.sortingFieldInternal,
      requestData.filter.unwrap()),
      requestData)
    list
  }

  def getListOfActionTypeIdNames(request: ListDataRequest, patientId: Int) = {

    //TODO: подключить анализ авторизационных данных и доступных ролей
    val count = actionTypeBean.getCountAllActionTypeWithFilter(request.filter)

    val result = count match {
      case 0 => {
        val actionType = if(request.filter.asInstanceOf[ActionTypesListRequestDataFilter].getGroupId()> 0){
          actionTypeBean.getActionTypeById(request.filter.asInstanceOf[ActionTypesListRequestDataFilter].getGroupId())
        } else {
          actionTypeBean.getActionTypeByCode(request.filter.asInstanceOf[ActionTypesListRequestDataFilter].getCode())
        }

        //empty action property
        val listForConverter = new java.util.ArrayList[String]
        listForConverter.add(ActionPropertyWrapperInfo.IsAssignable.toString)
        listForConverter.add(ActionPropertyWrapperInfo.IsAssigned.toString)

        val listForSummary = new java.util.ArrayList[StringId]
        listForSummary.add(ActionWrapperInfo.assessmentId)
        listForSummary.add(ActionWrapperInfo.assessmentName)
        listForSummary.add(ActionWrapperInfo.assessmentDate)
        listForSummary.add(ActionWrapperInfo.doctorLastName)
        listForSummary.add(ActionWrapperInfo.doctorFirstName)
        listForSummary.add(ActionWrapperInfo.doctorMiddleName)
        listForSummary.add(ActionWrapperInfo.doctorSpecs)
        listForSummary.add(ActionWrapperInfo.urgent)
        listForSummary.add(ActionWrapperInfo.multiplicity)
        listForSummary.add(ActionWrapperInfo.finance)
        listForSummary.add(ActionWrapperInfo.plannedEndDate)
        //listForSummary.add(ActionWrapperInfo.toOrder)

        val json = primaryAssessmentBean.getEmptyStructure(actionType.getId.intValue(), "Action", listForConverter, listForSummary,  null, null, patientBean.getPatientById(patientId))
        json
      }
      case _  => {
        val mapper: ObjectMapper = new ObjectMapper()
        mapper.getSerializationConfig().setSerializationView(classOf[ActionTypesListDataViews.OneLevelView]);  //плоская структурв
        mapper.writeValueAsString(new ActionTypesListData(request, actionTypeBean.getAllActionTypeWithFilter _))
      }
    }
    result
  }

  def getListOfActionTypes(request: ListDataRequest) = {
    val mapper: ObjectMapper = new ObjectMapper()
    if (request.filter.asInstanceOf[ActionTypesListRequestDataFilter].view.compareTo("tree") == 0) {
      mapper.getSerializationConfig().setSerializationView(classOf[ActionTypesListDataViews.DefaultView]);   //дерево
    } else {
      mapper.getSerializationConfig().setSerializationView(classOf[ActionTypesListDataViews.OneLevelView]);  //плоская структурв
    }
    mapper.writeValueAsString(new ActionTypesListData(request, actionTypeBean.getAllActionTypeWithFilter _))
  }

  def insertConsultation(request: ConsultationRequestData) = {
    //TODO: подключить анализ авторизационных данных и доступных ролей
    val authData:AuthData = null
    primaryAssessmentBean.insertAssessmentAsConsultation(request.eventId, request.actionTypeId, request.executorId, request.beginDate, request.endDate, request.urgent, request, authData)
  }

  def insertLaboratoryStudies(eventId: Int, data: CommonData, auth: AuthData) = {
    // проверка пользователя на ответственного за ивент
    directionBean.createDirectionsForEventIdFromCommonData(eventId, data, "Diagnostic", null, auth,  postProcessingForDiagnosis _)
    //primaryAssessmentBean.createAssessmentsForEventIdFromCommonData(eventId, data, "Diagnostic", null, auth,  postProcessingForDiagnosis _)// postProcessingForDiagnosis
  }

  def modifyLaboratoryStudies(eventId: Int, data: CommonData, auth: AuthData) = {
    directionBean.modifyDirectionsForEventIdFromCommonData(eventId, data, "Diagnostic", null, auth,  postProcessingForDiagnosis _)// postProcessingForDiagnosis
  }

  def removeLaboratoryStudies(data: AssignmentsToRemoveDataList, auth: AuthData) = {
    directionBean.removeDirections(data, auth)
  }

  def getFlatDirectories(request: FlatDirectoryRequestData) = {
    //TODO: подключить анализ авторизационных данных и доступных ролей
    val sorting = request.sortingFields.foldLeft(new java.util.LinkedHashMap[java.lang.Integer, java.lang.Integer])(
      (a, b) => {
        a.put(Integer.valueOf(b._1), Integer.valueOf(b._2))
        a
      })
    val flatRecords = flatDirectoryBean.getFlatDirectoriesWithFilterRecords(  request.page,
      request.limit,
      sorting,
      request.filter,
      request,
      null)

    new FlatDirectoryData(flatRecords, request)
  }

  def getAllMkbs(request: ListDataRequest, auth: AuthData) = {
    request.setRecordsCount(dbCustomQueryBean.getCountOfMkbsWithFilter(request.filter))
    val mkbs = dbCustomQueryBean.getAllMkbsWithFilter(  request.page,
      request.limit,
      request.sortingFieldInternal,
      request.filter.unwrap())

    val mkbs_display = dbCustomQueryBean.getDistinctMkbsWithFilter (request.sortingFieldInternal,
      request.filter.unwrap())

    val mapper: ObjectMapper = new ObjectMapper()
    val set = new java.util.HashSet[String]
    set.add("class")
    set.add("group")
    set.add("subgroup")
    set.add("mkb")

    if(set.contains(request.filter.asInstanceOf[MKBListRequestDataFilter].view)) {
      mapper.getSerializationConfig().setSerializationView(classOf[AllMKBListDataViews.OneLevelView]);   //плоская структурв
    } else {
      mapper.getSerializationConfig().setSerializationView(classOf[AllMKBListDataViews.DefaultView]);    //дерево
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

  def getDictionary(request: ListDataRequest, dictName: String/*, auth: AuthData*/) = {

    val mapper: ObjectMapper = new ObjectMapper()

    val list: java.util.LinkedList[Object] = dictName match {
      case null => {null}
      case "bloodTypes" => { //Группы крови
        mapper.getSerializationConfig().setSerializationView(classOf[DictionaryDataViews.DefaultView]);
        request.setRecordsCount(dbBloodTypeBean.getCountOfBloodTypesWithFilter(request.filter))
        dbBloodTypeBean.getAllBloodTypesWithFilter( request.page-1,
          request.limit,
          request.sortingFieldInternal,
          request.filter.unwrap())
      }
      case "relationships" => { //Типы родственных связей
        mapper.getSerializationConfig().setSerializationView(classOf[DictionaryDataViews.DefaultView]);
        request.setRecordsCount(dbRelationTypeBean.getCountOfRelationsWithFilter(request.filter))
        dbRelationTypeBean.getAllRelationsWithFilter( request.page-1,
          request.limit,
          request.sortingFieldInternal,
          request.filter.unwrap())
      }
      case "citizenships" => { //Гражданство
        mapper.getSerializationConfig().setSerializationView(classOf[DictionaryDataViews.DefaultView]);
        request.setRecordsCount(dbRbSocTypeBean.getCountOfSocStatusTypesWithFilter(request.filter))
        dbRbSocTypeBean.getAllSocStatusTypesWithFilter( request.page-1,
          request.limit,
          request.sortingFieldInternal,
          request.filter.unwrap())
      }
      case "citizenships2" => { //Второе гражданство
        mapper.getSerializationConfig().setSerializationView(classOf[DictionaryDataViews.DefaultView]);
        request.setRecordsCount(dbRbSocTypeBean.getCountOfSocStatusTypesWithFilter(request.filter))
        dbRbSocTypeBean.getAllSocStatusTypesWithFilter( request.page-1,
          request.limit,
          request.sortingFieldInternal,
          request.filter.unwrap())
      }
      case "socStatus" => { //Соц статусы
        mapper.getSerializationConfig().setSerializationView(classOf[DictionaryDataViews.DefaultView]);
        request.setRecordsCount(dbRbSocTypeBean.getCountOfSocStatusTypesWithFilter(request.filter))
        dbRbSocTypeBean.getAllSocStatusTypesWithFilter( request.page-1,
          request.limit,
          request.sortingFieldInternal,
          request.filter.unwrap())
      }
      case "TFOMS" => { //ТФОМС
        mapper.getSerializationConfig().setSerializationView(classOf[DictionaryDataViews.TFOMSView]);
        request.setRecordsCount(dbOrganizationBean.getCountOfOrganizationWithFilter(request.filter))
        dbOrganizationBean.getAllOrganizationWithFilter(request.page-1,
          request.limit,
          request.sortingFieldInternal,
          request.filter.unwrap())
      }
      case "clientDocument" => {  //Типы документов, удостоверяющих личность
        mapper.getSerializationConfig().setSerializationView(classOf[DictionaryDataViews.ClientDocumentView]);
        request.setRecordsCount(dbDocumentTypeBean.getCountOfDocumentTypesWithFilter(request.filter))
        dbDocumentTypeBean.getAllDocumentTypesWithFilter( request.page-1,
          request.limit,
          request.sortingFieldInternal,
          request.filter.unwrap())
      }
      case "insurance" => { //Страховые компании
        mapper.getSerializationConfig().setSerializationView(classOf[DictionaryDataViews.InsuranceView]);
        request.setRecordsCount(dbOrganizationBean.getCountOfOrganizationWithFilter(request.filter))
        dbOrganizationBean.getAllOrganizationWithFilter(request.page-1,
          request.limit,
          request.sortingFieldInternal,
          request.filter.unwrap())
      }
      case "policyTypes" => { //Тип полиса
        mapper.getSerializationConfig().setSerializationView(classOf[DictionaryDataViews.PolicyTypeView]);
        request.setRecordsCount(dbRbPolicyTypeBean.getCountOfRbPolicyTypeWithFilter(request.filter))
        dbRbPolicyTypeBean.getAllRbPolicyTypeWithFilter(request.page-1,
          request.limit,
          request.sortingFieldInternal,
          request.filter.unwrap())
      }
      case "disabilityTypes" => {   //Тип инвалидности
        mapper.getSerializationConfig().setSerializationView(classOf[DictionaryDataViews.DefaultView]);
        request.setRecordsCount(dbRbSocTypeBean.getCountOfSocStatusTypesWithFilter(request.filter))
        dbRbSocTypeBean.getAllSocStatusTypesWithFilter( request.page-1,
          request.limit,
          request.sortingFieldInternal,
          request.filter.unwrap())
      }
      case "KLADR" => {    //адреса по кладру
        mapper.getSerializationConfig().setSerializationView(classOf[DictionaryDataViews.KLADRView]);
        request.setRecordsCount(dbSchemeKladrBean.getCountOfKladrRecordsWithFilter(request.filter))
        dbSchemeKladrBean.getAllKladrRecordsWithFilter( request.page-1,
          request.limit,
          request.sortingFieldInternal,
          request.filter.unwrap())
      }
      case "valueDomain" => {
        mapper.getSerializationConfig().setSerializationView(classOf[DictionaryDataViews.ValueDomainView]);
        //request.setRecordsCount(dbSchemeKladrBean.getCountOfKladrRecordsWithFilter(request.filter))
        actionPropertyTypeBean.getActionPropertyTypeValueDomainsWithFilter( request.page-1,
          request.limit,
          request.sortingFieldInternal,
          request.filter.unwrap())
      }
      case "specialities" => {  //  Специальности
        mapper.getSerializationConfig().setSerializationView(classOf[DictionaryDataViews.DefaultView]);
        request.setRecordsCount(dbSpeciality.getCountOfBloodTypesWithFilter(request.filter))
        dbSpeciality.getAllSpecialitiesWithFilter(request.page-1,
          request.limit,
          request.sortingFieldInternal,
          request.filter.unwrap())
      }
      case "contactTypes" => {  //  Типы контактов
        mapper.getSerializationConfig().setSerializationView(classOf[DictionaryDataViews.DefaultView])
        request.setRecordsCount(dbRbContactType.getCountOfAllRbContactTypesWithFilter(request.filter))
        dbRbContactType.getAllRbContactTypesWithFilter( request.page-1,
          request.limit,
          request.sortingFieldInternal,
          request.filter.unwrap())
      }
      case "requestTypes" => {  //  Типы обращений
        mapper.getSerializationConfig().setSerializationView(classOf[DictionaryDataViews.RequestTypesView])
        dbRbRequestTypes.getAllRbRequestTypesWithFilter(request.page-1,
          request.limit,
          request.sortingFieldInternal,
          request.filter.unwrap(),
          request.rewriteRecordsCount _)
      }
      case "finance" => {  //  Типы оплаты
        mapper.getSerializationConfig().setSerializationView(classOf[DictionaryDataViews.DefaultView])
        dbRbFinance.getAllRbFinanceWithFilter(request.page-1,
          request.limit,
          request.sortingFieldInternal,
          request.filter.unwrap(),
          request.rewriteRecordsCount _)
      }
      case "quotaStatus" => { //   Статусы квот
        mapper.getSerializationConfig().setSerializationView(classOf[DictionaryDataViews.DefaultView])
        dbRbQuotaStatus.getAllRbQuotaStatusWithFilter(request.page-1,
          request.limit,
          request.sortingFieldInternal,
          request.filter.unwrap(),
          request.rewriteRecordsCount _)
      }
      case "tissueTypes" => {  //Типы исследования
        mapper.getSerializationConfig().setSerializationView(classOf[DictionaryDataViews.DefaultView])
        dbRbTissueType.getAllRbTissueTypeWithFilter(request.page-1,
          request.limit,
          request.sortingFieldInternal,
          request.filter.unwrap(),
          request.rewriteRecordsCount _)
      }
    }
    mapper.writeValueAsString(new DictionaryListData(list, request))
  }

  def getQuotaTypes(request: ListDataRequest) = {
    val quotaTypes = dbQuotaTypeBean.getAllQuotaTypesWithFilter(request.page-1,
      request.limit,
      request.sortingFieldInternal,
      request.filter.unwrap(),
      request.rewriteRecordsCount _)
    new GroupTypesListData(quotaTypes, request)
  }

  //Сервисы по назначениям
  def insertAssignment(assignmentData: AssignmentData, eventId: Int, auth: AuthData) = {

    val action = assignmentBean.insertAssignmentForPatient(assignmentData, eventId, auth)
    assignmentBean.getAssignmentById(action.getId.intValue())
  }

  def getAssignmentById(actionId: Int, auth: AuthData) = assignmentBean.getAssignmentById(actionId)

  def getFilteredRlsList(request: RlsDataListRequestData) = {
    request.setRecordsCount(dbRlsBean.getCountOfRlsRecordsWithFilter(request.filter))
    val list = dbRlsBean.getRlsListWithFilter(request.page,
      request.limit,
      request.sortingFieldInternal,
      request.sortingMethod,
      request.filter)
    if (list!=null)
      new RlsDataList(list, request)
    else
      new RlsDataList()
  }

  def getEventTypes(request: ListDataRequest, authData: AuthData) = {
    val mapper: ObjectMapper = new ObjectMapper()
    mapper.getSerializationConfig().setSerializationView(classOf[DictionaryDataViews.DefaultView])

    val list = dbEventTypeBean.getEventTypesByRequestTypeIdAndFinanceId(request.page-1,
      request.limit,
      request.sortingFieldInternal,
      request.filter.unwrap(),
      request.rewriteRecordsCount _)

    mapper.writeValueAsString(new EventTypesListData(list, request))
  }

  def getPatientsFromOpenAppealsWhatHasBedsByDepartmentId(departmentId: Int) = {
    patientBean.getCurrentPatientsByDepartmentId(departmentId)
  }

  def getTakingOfBiomaterial(request: TakingOfBiomaterialRequesData, authData: AuthData) = {

    val res = dbJobTicketBean.getDirectionsWithJobTicketsBetweenDate(request.sortingFieldInternal, request.filter)
    //пересоберем мапу и сгруппируем по жобТикету
    var actions = new java.util.LinkedList[(Action, ActionTypeTissueType)]()
    var map = new mutable.LinkedHashMap[JobTicket, LinkedList[(Action, ActionTypeTissueType)]]
    if (res != null) {
      request.rewriteRecordsCount(res.asInstanceOf[java.util.LinkedList[(Action, ActionTypeTissueType, JobTicket)]].size())
      var firstJobTicket = res.asInstanceOf[java.util.LinkedList[(Action, ActionTypeTissueType, JobTicket)]].iterator.next()._3
      res.asInstanceOf[java.util.LinkedList[(Action, ActionTypeTissueType, JobTicket)]].foreach(f => {
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
      // actions.size() == 1 --- для него есть только один акшен. Если акшенов больше, он добавится в цикле.
      // map.size == 0 --- если нашли только один жоб тикет, так как мапа пустая и все акшены остались в actions
      if (actions.size() == 1 || map.size == 0) map += (firstJobTicket -> actions)
    }
    new TakingOfBiomaterialData(map,
      hospitalBedBean.getLastMovingActionForEventId _,
      actionPropertyBean.getActionPropertiesByActionIdAndRbCoreActionPropertyIds _,
      request)
  }

  def updateJobTicketsStatuses(data: JobTicketStatusDataList, authData: AuthData) = {

    var isSuccess: Boolean = true
    data.getData.foreach(f=> {
      val res = dbJobTicketBean.modifyJobTicketStatus(f.getId, f.getStatus, authData)
      if(!res)
        isSuccess = res
    })
    isSuccess
  }

  def deletePatientInfo(id: Int) = patientBean.deletePatientInfo(id)


  //__________________________________________________________________________________________________
  //***************  AUTHDATA  *******************
  //__________________________________________________________________________________________________

  def checkTokenCookies(srvletRequest: HttpServletRequest): AuthData = {
    authStorage.checkTokenCookies(srvletRequest)
  }

  def getStorageAuthData(token: AuthToken) = {
    authStorage.getAuthData(token)
  }
}
