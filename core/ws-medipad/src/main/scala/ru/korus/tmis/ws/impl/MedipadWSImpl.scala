package ru.korus.tmis.ws.impl

import ru.korus.tmis.core.assessment.AssessmentBeanLocal
import ru.korus.tmis.core.diagnostic.DiagnosticBeanLocal
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.core.thesaurus.ThesaurusBeanLocal
import ru.korus.tmis.core.treatment.TreatmentBeanLocal
import ru.korus.tmis.ws.medipad.MedipadWebService
import javax.inject.Named

import grizzled.slf4j.Logging
import javax.annotation.Resource
import javax.ejb.EJB
import javax.jws.{HandlerChain, WebService}
import javax.xml.ws.WebServiceContext
import org.apache.shiro.SecurityUtils
import ru.korus.tmis.core.data._

import com.google.common.collect.Lists

import ru.korus.tmis.ws.webmis.rest.CurrentAuthContext;
import ru.korus.tmis.ws.webmis.rest.ThreadLocalByRequest;
import ru.korus.tmis.core.database._
import ru.korus.tmis.core.entity.model._

import scala.collection.JavaConversions._
import ru.korus.tmis.util._
import ru.korus.tmis.core.auth.{AuthToken, AuthStorageBeanLocal, AuthData}
import javax.servlet.http.HttpServletRequest;
import org.codehaus.jackson.map.ObjectMapper
import ru.korus.tmis.core.patient._
import java.util._
import ru.korus.tmis.util.StringId
import java.util

@Named
@WebService(
  endpointInterface = "ru.korus.tmis.ws.medipad.MedipadWebService",
  targetNamespace = "http://korus.ru/tmis/medipad",
  serviceName = "tmis-medipad",
  portName = "medipad",
  name = "medipad")
@HandlerChain(file = "tmis-ws-auth-handlers.xml")
class MedipadWSImpl
  extends MedipadWebService

  with Logging
  with I18nable {

  @Resource
  var ctx: WebServiceContext = _

  @EJB
  private var authStorage: AuthStorageBeanLocal = _

  @EJB
  private var dbVersionBean: DbVersionBeanLocal = _

  @EJB
  private var patientBean: PatientBeanLocal = _

  @EJB
  private var assessmentBean: AssessmentBeanLocal = _

  @EJB
  private var diagnosticBean: DiagnosticBeanLocal = _

  @EJB
  private var thesaurusBean: ThesaurusBeanLocal = _

  @EJB
  private var treatmentBean: TreatmentBeanLocal = _

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

  //@EJB
  //private var commonDataProcessor: CommonDataProcessorBeanLocal = _

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

  //@EJB
  //private var dbRbHospitalBedProfileBean: DbRbHospitalBedProfileBeanLocal = _

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
  private var dbFDRecordBean: DbFDRecordBeanLocal = _

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
  private  var dbRbQuotaStatus: DbRbQuotaStatusBeanLocal = _
  //////////////////////////////////////////////////////////////////////////////

  def checkTokenCookies(srvletRequest: HttpServletRequest): AuthData = {
    authStorage.checkTokenCookies(srvletRequest)
  }

  def getStorageAuthData(token: AuthToken) = {
    authStorage.getAuthData(token)
  }

  def currentAuthData() = {
    val mc = ctx.getMessageContext()
    val ff = mc.get(ConfigManager.TmisAuth.AuthDataPropertyName)
    ff.asInstanceOf[AuthData]
  }

  def currentUser() = {
    SecurityUtils.getSubject
  }

  def requiresPermissions(permissions: Array[String]) = {
    if (!currentUser.isPermitted("adm")) {
      permissions.foreach(p => {
        if (!currentUser.isPermitted(p)) {
          throw new CoreException(
            ConfigManager.TmisAuth.ErrorCodes.PermissionNotAllowed,
            i18n("error.permissionNotAllowed").format(p))
        }
      })
    }
  }

  //////////////////////////////////////////////////////////////////////////////

  def getCurrentPatients(): CommonData = {
    requiresPermissions(Array("clientEventRead", "clientActionRead"))

    if (currentUser.isPermitted("existsSeesStructure")
      || currentUser().isPermitted("inflowSeesStructure")) {
      return patientBean.getCurrentPatientsForDepartment(currentAuthData)
    } else if (currentUser.isPermitted("existsSeesSelf")
      || currentUser().isPermitted("inflowSeesSelf")) {
      return patientBean.getCurrentPatientsForDoctor(currentAuthData)
    }

    throw new CoreException(
      i18n("error.invalidRole").format(currentAuthData.getUserRole.getCode)
    )
  }

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
        val outPatientEntry: PatientEntry = patientBean.savePatient(inPatientEntry, auth)  //currentAuthData
        patientData.setData(outPatientEntry)
        return patientData
      }
    }

    throw new CoreException(
      i18n("error.cantSavePatient").format()
    )
  }

  def updatePatient(patientData: PatientCardData, auth: AuthData) : PatientCardData = {
    //requiresPermissions(Array("clientAssessmentCreate"))
    val inPatientEntry = patientData.getData()
    if (inPatientEntry != null) {
      if (auth != null) {
        val outPatientEntry: PatientEntry = patientBean.savePatient(inPatientEntry, auth)
        patientData.setData(outPatientEntry)
        return patientData
      }
    }

    throw new CoreException(
      i18n("error.cantSavePatient").format()
    )
  }

  //////////////////////////////////////////////////////////////////////////////
  import Defaultible._

  private def checkingVersion[A: Defaultible](globalVersion: String)(td: =>A) = {
    if (globalVersion == dbVersionBean.getGlobalVersion) defaultValue[A]
    else td
  }

  private implicit val commonDataDefault = setDefault(new CommonData)
  private implicit val rlsDataDefault = setDefault(new RlsData)
  private implicit val crlsDataDefault = setDefault(new CompactRlsData)
  private implicit val thesaurusDefault = setDefault(new ThesaurusData)
    
  //////////////////////////////////////////////////////////////////////////////

  def getAssessmentTypes(globalVersion: String,
                         eventId: Int): CommonData = checkingVersion(globalVersion) {
      assessmentBean.getAssessmentTypes(eventId, currentAuthData)
  }

  def getAllAssessmentTypes(globalVersion: String) = checkingVersion(globalVersion) {
      assessmentBean.getAllAssessmentTypes
  }

  def getAllAssessmentsForPatient(eventId: Int) = {
    requiresPermissions(Array("clientAssessmentRead"))
    assessmentBean.getAllAssessmentsByEventId(eventId)
  }

  def getAssessmentForPatient(eventId: Int, assessmentId: Int) = {
    requiresPermissions(Array("clientAssessmentRead"))
    assessmentBean.getAssessmentById(assessmentId)
  }

  def getIndicators(eventId: Int, beginDate: Date, endDate: Date) = {
    requiresPermissions(Array("clientAssessmentRead"))
    assessmentBean.getIndicators(eventId, beginDate, endDate)
  }

  def createAssessmentForPatient(eventId: Int,
                                 assessment: CommonData) = {
    requiresPermissions(Array("clientAssessmentCreate"))
    assessmentBean.createAssessmentForEventId(eventId,
                                              assessment,
                                              currentAuthData);
  }

  def modifyAssessmentForPatient(eventId: Int,
                                 assessmentId: Int,
                                 assessment: CommonData) = {
    requiresPermissions(Array("clientAssessmentUpdate"))
    assessmentBean.modifyAssessmentById(assessmentId,
                                        assessment,
                                        currentAuthData);
  }

  //////////////////////////////////////////////////////////////////////////////

  def getDiagnosticTypes(globalVersion: String,
                         eventId: Int) = checkingVersion(globalVersion) {
    diagnosticBean.getDiagnosticTypes(eventId, currentAuthData)
  }

  def getAllDiagnosticTypes(globalVersion: String) = checkingVersion(globalVersion) {
    diagnosticBean.getAllDiagnosticTypes
  }

  def getAllDiagnosticsForPatient(eventId: Int) = {
    requiresPermissions(Array("clientDiagnosticRead"))
    diagnosticBean.getAllDiagnosticsByEventId(eventId)
  }

  def getDiagnosticForPatient(eventId: Int, diagnosticId: Int) = {
    requiresPermissions(Array("clientDiagnosticRead"))
    diagnosticBean.getDiagnosticById(diagnosticId)
  }

  def createDiagnosticForPatient(eventId: Int,
                                 diagnostic: CommonData) = {
    requiresPermissions(Array("clientDiagnosticCreate"))
    val auth = currentAuthData
    diagnosticBean.createDiagnosticForEventId(eventId,
                                              diagnostic,
                                              auth)
  }

  def modifyDiagnosticForPatient(eventId: Int,
                                 diagnosticId: Int,
                                 diagnostic: CommonData) = {
    requiresPermissions(Array("clientDiagnosticUpdate"))
    diagnosticBean.modifyDiagnosticById(diagnosticId,
                                        diagnostic,
                                        currentAuthData)
  }

  def callOffDiagnosticForPatient(eventId: Int,
                                  diagnosticId: Int) = {
    requiresPermissions(Array("clientDiagnosticUpdate"))
    diagnosticBean.updateDiagnosticStatusById(eventId,
                                              diagnosticId,
                                              ConfigManager.ActionStatus.Canceled)
  }

  //////////////////////////////////////////////////////////////////////////////

  def getThesaurus(globalVersion: String) = checkingVersion(globalVersion){
    thesaurusBean.getThesaurus
  }

  def getThesaurusByCode(globalVersion: String, code: Int) = checkingVersion(globalVersion){
    thesaurusBean.getThesaurusByCode(code)
  }

  def getMkb(globalVersion: String) = checkingVersion(globalVersion){
    thesaurusBean.getMkb
  }

  //////////////////////////////////////////////////////////////////////////////

  def getRlsList(globalVersion: String) = checkingVersion(globalVersion) { treatmentBean.getRlsList }

  def getCompactRlsList(globalVersion: String) = checkingVersion(globalVersion) { treatmentBean.getCompactRlsList }

  def getTreatmentTypes(globalVersion: String, eventId: Int) = checkingVersion(globalVersion) {
    treatmentBean.getTreatmentTypes(eventId, currentAuthData)
  }

  def getAllTreatmentTypes(globalVersion: String) = checkingVersion(globalVersion) {
    treatmentBean.getAllTreatmentTypes
  }

  def createTreatmentForPatient(eventId: Int,
                                treatment: CommonData) = {
    requiresPermissions(Array("clientTreatmentCreate"))
    treatmentBean.createTreatmentForEventId(eventId,
                                            treatment,
                                            currentAuthData)
  }

  def modifyTreatmentForPatient(eventId: Int,
                                treatmentId: Int,
                                treatment: CommonData) = {
    requiresPermissions(Array("clientTreatmentUpdate"))
    treatmentBean.modifyTreatmentById(treatmentId,
                                      treatment,
                                      currentAuthData)
  }

  def getTreatmentInfo(eventId: Int,
                       actionTypeId: java.lang.Integer,
                       beginDate: Date,
                       endTime: Date) = {
    requiresPermissions(Array("clientTreatmentRead"))
    treatmentBean.getTreatmentInfo(eventId, actionTypeId, beginDate, endTime);
  }

  def getTreatmentForPatient(eventId: Int,
                             treatmentId: Int) = {
    requiresPermissions(Array("clientTreatmentRead"))
    treatmentBean.getTreatmentById(treatmentId);
  }

  def verifyDrugTreatment(eventId: Int,
                          actionId: Int,
                          drugId: Int) = {
    requiresPermissions(Array("clientTreatmentRead"))
    treatmentBean.verifyDrugTreatment(eventId, actionId, drugId)
  }

  def revokeTreatment(eventId: Int, actionId: Int) = {
    requiresPermissions(Array("clientTreatmentUpdate"))
    treatmentBean.revokeTreatment(eventId, actionId)
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

    if(ide>0) {
      val result = appealBean.getAppealById(ide)

      val positionE = result.iterator.next()
      val positionA = positionE._2.iterator.next()
      val values = positionA._2.asInstanceOf[java.util.Map[java.lang.Integer, java.util.List[Object]]]

      val mapper: ObjectMapper = new ObjectMapper()
      mapper.getSerializationConfig().setSerializationView(classOf[Views.DynamicFieldsStandartForm]);
      val patient = patientBean.getPatientById(patientId)
      val map = patientBean.getKLADRAddressMapForPatient(patient)
      val street = patientBean.getKLADRStreetForPatient(patient)
      //val appType = dbFDRecordBean.getIdValueFDRecordByEventTypeId(25, positionE._1.getEventType.getId.intValue())
      mapper.writeValueAsString(new AppealData( positionE._1,
                                                positionA._1,
                                                //appType,
                                                values,
                                                "standart",
                                                map,
                                                street,
                                                null,
                                                actionBean.getLastActionByActionTypeIdAndEventId _,  //havePrimary
                                                dbClientRelation.getClientRelationByRelativeId _
                                ))
    } else {
      throw new CoreException("Не удачная попытка сохранения(изменения) обращения")
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
                                              //appType,
                                              values,
                                              "standart",
                                              null,
                                              null,
                                              null,
                                              actionBean.getLastActionByActionTypeIdAndEventId _,  //havePrimary
                                              dbClientRelation.getClientRelationByRelativeId _
                              ))
  }

  def getAppealPrintFormById(id: Int, auth: AuthData) = {
    val result = appealBean.getAppealById(id)

    val positionE = result.iterator.next()
    val positionA = positionE._2.iterator.next()
    val values = positionA._2.asInstanceOf[java.util.Map[java.lang.Integer, java.util.List[Object]]]

    val ward = dbCustomQueryBean.getLastActionByTypeCodeAndAPTypeName(id, "4202", "Переведен в отделение")
    var aps: java.util.Map[ActionProperty, java.util.List[APValue]] = null
    if(ward!=null){
      aps = actionPropertyBean.getActionPropertiesByActionId(ward.getId.intValue())
    }

    val mapper: ObjectMapper = new ObjectMapper()
    mapper.getSerializationConfig().setSerializationView(classOf[Views.DynamicFieldsPrintForm])

    val map = patientBean.getKLADRAddressMapForPatient(positionE._1.getPatient)
    val street = patientBean.getKLADRStreetForPatient(positionE._1.getPatient)

    //val appType = dbFDRecordBean.getIdValueFDRecordByEventTypeId(i18n("db.flatDirectory.eventType.hospitalization").toInt,
    //                                                             positionE._1.getEventType.getId.intValue())
    mapper.writeValueAsString(new AppealData( positionE._1,
                                              positionA._1,
                                              //appType,
                                              values,
                                              aps,
                                              "print_form",
                                              map,
                                              street,
                                              null,
                                              actionBean.getLastActionByActionTypeIdAndEventId _, //havePrimary
                                              dbClientRelation.getClientRelationByRelativeId _,
                                              actionPropertyBean.getActionPropertiesByActionIdAndRbCoreActionPropertyIds _,  //Admission Diagnosis
                                              dbRbCoreActionPropertyBean.getRbCoreActionPropertiesByIds _          //таблица соответствия
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
       //case 29 => mapper.getSerializationConfig().setSerializationView(classOf[ReceivedPatientsDataViews.AdmissionDepartmentsNurseView]) //Сестра приемного отделения
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

     primaryAssessmentBean.getEmptyStructure(actionTypeId,
                                             "PrimaryAssesment",
                                             listForConverter,
                                             listForSummary,
                                             authData,
                                             postProcessing _)
   }

  //запрос на структуру первичного мед. осмотра с копированием данных из предыдущего осмотра
   def getStructOfPrimaryMedExamWithCopy(actionTypeId: Int, authData: AuthData, eventId: Int) = {
    var lastActionId = actionBean.getActionIdWithCopyByEventId(eventId, actionTypeId)
    try {
      primaryAssessmentBean.getPrimaryAssessmentById(lastActionId, "Assessment", authData, postProcessing _)
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



   //создание первичного мед. осмотра
   def insertPrimaryMedExamForPatient(eventId: Int, data: JSONCommonData, authData: AuthData)  = {
     //TODO: подключить анализ авторизационных данных и доступных ролей
     primaryAssessmentBean.createPrimaryAssessmentForEventId(eventId,
                                                             data,
                                                             "Assessment",
                                                             authData,
                                                             preProcessing _,
                                                             postProcessing _)
   }

   //редактирование первичного мед. осмотра
   def modifyPrimaryMedExamForPatient(actionId: Int, data: JSONCommonData, authData: AuthData)  = {
     //TODO: подключить анализ авторизационных данных и доступных ролей
     primaryAssessmentBean.modifyPrimaryAssessmentById(actionId,
                                                       data,
                                                       "Assessment",
                                                       authData,
                                                       preProcessing _,
                                                       postProcessing _)
   }

   def getPrimaryAssessmentById (assessmentId: Int, authData: AuthData) = {

     //TODO: подключить анализ авторизационных данных и доступных ролей
     //val authData:AuthData = null

     val json_data = primaryAssessmentBean.getPrimaryAssessmentById(assessmentId,
                                                                     "Assessment",
                                                                     authData,
                                                                     postProcessing _)

     json_data
   }

  def getAllPatientsForDepartmentIdAndDoctorIdByPeriod(requestData: PatientsListRequestData, role: Int, auth: AuthData) = {
    patientBean.getAllPatientsForDepartmentIdAndDoctorIdByPeriod(requestData, role, auth)
  }

  //Возвращает список осмотров по пациенту и обращению с фильтрацией по типу действия
  def getListOfAssessmentsForPatientByEvent(requestData: AssessmentsListRequestData, auth: AuthData) = {

    val action_list = actionBean.getActionsByEventIdWithFilter(requestData.eventId, auth, requestData)
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
    mapper.getSerializationConfig().setSerializationView(classOf[HospitalBedViews.RegistrationView]);
    mapper.writeValueAsString(hospitalBedBean.getRegistryOriginalForm(action, authData))
  }

  //Снятие пациента с койки
  def callOffHospitalBedForPatient(actionId: Int, authData: AuthData) = {
    hospitalBedBean.callOffHospitalBedForPatient(actionId, authData)
  }

  def getFormOfAccountingMovementOfPatients(departmentId: Int) = {
    val linear = seventhFormBean.fillInSeventhForm(departmentId, null, null/*previousMedDate, currentMedDate*/)
    new FormOfAccountingMovementOfPatientsData(linear, null)
  }

  def movingPatientToDepartment(eventId: Int, data: HospitalBedData, authData: AuthData) = {

    val action = hospitalBedBean.movingPatientToDepartment(eventId, data, authData)

    val mapper: ObjectMapper = new ObjectMapper()
    mapper.getSerializationConfig().setSerializationView(classOf[HospitalBedViews.MoveView])
    mapper.writeValueAsString(hospitalBedBean.getRegistryOriginalForm(action, authData))
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
                                                                                      requestData.sortingMethod,
                                                                                      requestData.filter
                                                                                     ),
                                      requestData)
    list
  }

  def getListOfDiagnosticsForPatientByEvent(requestData: DiagnosticsListRequestData) = {

    //TODO: подключить анализ авторизационных данных и доступных ролей
    requestData.setRecordsCount(dbCustomQueryBean.getCountDiagnosticsWithFilter(requestData.filter))
    var actions: java.util.List[Action] = null
    if(requestData.getRecordsCount()>0) {
      actions = dbCustomQueryBean.getAllDiagnosticsWithFilter(requestData.page-1,
                                                              requestData.limit,
                                                              requestData.sortingFieldInternal,
                                                              requestData.sortingMethod,
                                                              requestData.filter)
    }
    val list = new DiagnosticsListData(actions, requestData)
    list
  }

  def getInfoAboutDiagnosticsForPatientByEvent(actionId: Int) = {
    //TODO: подключить анализ авторизационных данных и доступных ролей
    val authData:AuthData = null

    val json_data = primaryAssessmentBean.getPrimaryAssessmentById(actionId, "Diagnostic", authData, null)
    json_data
  }

  def getFreePersons(requestData: ListDataRequest) = {

    //<= Изменить запрос (ждем отклик)
    //requestData.setRecordsCount(dbStaff.getCountAllPersonsWithFilter(requestData.filter))
    val list = new AllPersonsListData(dbStaff.getEmptyPersonsByRequest( requestData.limit,
                                                                        requestData.page-1,
                                                                        requestData.sortingField,
                                                                        requestData.sortingMethod,
                                                                        requestData.filter),
                                      requestData)
    list
  }

  def getListOfActionTypeIdNames(request: ListDataRequest) = {

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
          var listForConverter = new java.util.ArrayList[String]
          listForConverter.add(ActionPropertyWrapperInfo.IsAssignable.toString)
          listForConverter.add(ActionPropertyWrapperInfo.IsAssigned.toString)

          var listForSummary = new java.util.ArrayList[StringId]
          listForSummary.add(ActionWrapperInfo.assessmentId)
          listForSummary.add(ActionWrapperInfo.assessmentName)
          listForSummary.add(ActionWrapperInfo.assessmentDate)
          listForSummary.add(ActionWrapperInfo.doctorLastName)
          listForSummary.add(ActionWrapperInfo.doctorFirstName)
          listForSummary.add(ActionWrapperInfo.doctorMiddleName)
          listForSummary.add(ActionWrapperInfo.doctorSpecs)
          listForSummary.add(ActionWrapperInfo.urgent)
          listForSummary.add(ActionWrapperInfo.multiplicity)

          val json = primaryAssessmentBean.getEmptyStructure(actionType.getId.intValue(), "Action", listForConverter, listForSummary,  null, null)
          json
        }
        case _  => {
          val atList = actionTypeBean.getAllActionTypeWithFilter( request.page-1,
            request.limit,
            request.sortingField,
            request.sortingMethod,
            request.filter)
          val list = new ActionTypesListData(atList, request)
          list
        }
    }
    result
  }

  def insertConsultation(request: ConsultationRequestData) = {
    //TODO: подключить анализ авторизационных данных и доступных ролей
    val authData:AuthData = null
    primaryAssessmentBean.insertAssessmentAsConsultation(request.eventId, request.actionTypeId, request.executorId, request.beginDate, request.endDate, request.urgent, request, authData)
  }

  def insertLaboratoryStudies(eventId: Int, data: CommonData) = {
    //TODO: подключить анализ авторизационных данных и доступных ролей
    val authData:AuthData = null
    primaryAssessmentBean.createAssessmentsForEventIdFromCommonData(eventId, data, "Diagnostic", null, authData)
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
                                            request.sortingMethod,
                                            request.filter)

    val mkbs_display = dbCustomQueryBean.getDistinctMkbsWithFilter (request.sortingFieldInternal,
                                                                    request.sortingMethod,
                                                                    request.filter)

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
      request.sortingMethod,
      request.filter)
    new ThesaurusListData(thesaurus, request)
  }

  def getDictionary(request: ListDataRequest, dictName: String/*, auth: AuthData*/) = {

      val mapper: ObjectMapper = new ObjectMapper()

      val list: java.util.LinkedList[Object] = dictName match {
      case null => {null}
      case "bloodTypes" => { //Группы крови
        mapper.getSerializationConfig().setSerializationView(classOf[DictionaryDataViews.DefaultView]);
        request.setRecordsCount(dbBloodTypeBean.getCountOfBloodTypesWithFilter(request.filter))
        dbBloodTypeBean.getAllBloodTypesWithFilter(
          request.page,
          request.limit,
          request.sortingFieldInternal,
          request.sortingMethod,
          request.filter)
      }
      case "relationships" => { //Типы родственных связей
        mapper.getSerializationConfig().setSerializationView(classOf[DictionaryDataViews.DefaultView]);
        request.setRecordsCount(dbRelationTypeBean.getCountOfRelationsWithFilter(request.filter))
        dbRelationTypeBean.getAllRelationsWithFilter(
          request.page,
          request.limit,
          request.sortingFieldInternal,
          request.sortingMethod,
          request.filter)
      }
      case "citizenships" => { //Гражданство
        mapper.getSerializationConfig().setSerializationView(classOf[DictionaryDataViews.DefaultView]);
        request.setRecordsCount(dbRbSocTypeBean.getCountOfSocStatusTypesWithFilter(request.filter))
        dbRbSocTypeBean.getAllSocStatusTypesWithFilter(
          request.page,
          request.limit,
          request.sortingFieldInternal,
          request.sortingMethod,
          request.filter)
      }
      case "citizenships2" => { //Второе гражданство
        mapper.getSerializationConfig().setSerializationView(classOf[DictionaryDataViews.DefaultView]);
        request.setRecordsCount(dbRbSocTypeBean.getCountOfSocStatusTypesWithFilter(request.filter))
        dbRbSocTypeBean.getAllSocStatusTypesWithFilter(
          request.page,
          request.limit,
          request.sortingFieldInternal,
          request.sortingMethod,
          request.filter)
      }
      case "socStatus" => { //Соц статусы
        mapper.getSerializationConfig().setSerializationView(classOf[DictionaryDataViews.DefaultView]);
        request.setRecordsCount(dbRbSocTypeBean.getCountOfSocStatusTypesWithFilter(request.filter))
        dbRbSocTypeBean.getAllSocStatusTypesWithFilter(
          request.page,
          request.limit,
          request.sortingFieldInternal,
          request.sortingMethod,
          request.filter)
      }
      case "TFOMS" => { //ТФОМС
        mapper.getSerializationConfig().setSerializationView(classOf[DictionaryDataViews.TFOMSView]);
        request.setRecordsCount(dbOrganizationBean.getCountOfOrganizationWithFilter(request.filter))
        dbOrganizationBean.getAllOrganizationWithFilter(
          request.page,
          request.limit,
          request.sortingFieldInternal,
          request.sortingMethod,
          request.filter)
      }
      case "clientDocument" => {  //Типы документов, удостоверяющих личность
        mapper.getSerializationConfig().setSerializationView(classOf[DictionaryDataViews.ClientDocumentView]);
        request.setRecordsCount(dbDocumentTypeBean.getCountOfDocumentTypesWithFilter(request.filter))
        dbDocumentTypeBean.getAllDocumentTypesWithFilter(
          request.page,
          request.limit,
          request.sortingFieldInternal,
          request.sortingMethod,
          request.filter)
      }
      case "insurance" => { //Страховые компании
        mapper.getSerializationConfig().setSerializationView(classOf[DictionaryDataViews.InsuranceView]);
        request.setRecordsCount(dbOrganizationBean.getCountOfOrganizationWithFilter(request.filter))
        dbOrganizationBean.getAllOrganizationWithFilter(
          request.page,
          request.limit,
          request.sortingFieldInternal,
          request.sortingMethod,
          request.filter)
      }
      case "policyTypes" => { //Тип полиса
        mapper.getSerializationConfig().setSerializationView(classOf[DictionaryDataViews.PolicyTypeView]);
        request.setRecordsCount(dbRbPolicyTypeBean.getCountOfRbPolicyTypeWithFilter(request.filter))
        dbRbPolicyTypeBean.getAllRbPolicyTypeWithFilter(
          request.page,
          request.limit,
          request.sortingFieldInternal,
          request.sortingMethod,
          request.filter)
      }
      case "disabilityTypes" => {   //Тип инвалидности
        mapper.getSerializationConfig().setSerializationView(classOf[DictionaryDataViews.DefaultView]);
        request.setRecordsCount(dbRbSocTypeBean.getCountOfSocStatusTypesWithFilter(request.filter))
        dbRbSocTypeBean.getAllSocStatusTypesWithFilter(
          request.page,
          request.limit,
          request.sortingFieldInternal,
          request.sortingMethod,
          request.filter)
      }
      case "KLADR" => {    //адреса по кладру
        mapper.getSerializationConfig().setSerializationView(classOf[DictionaryDataViews.KLADRView]);
        request.setRecordsCount(dbSchemeKladrBean.getCountOfKladrRecordsWithFilter(request.filter))
        dbSchemeKladrBean.getAllKladrRecordsWithFilter(
          request.page,
          request.limit,
          request.sortingFieldInternal,
          request.sortingMethod,
          request.filter)
      }
      case "valueDomain" => {
        mapper.getSerializationConfig().setSerializationView(classOf[DictionaryDataViews.ValueDomainView]);
        //request.setRecordsCount(dbSchemeKladrBean.getCountOfKladrRecordsWithFilter(request.filter))
        actionPropertyTypeBean.getActionPropertyTypeValueDomainsWithFilter(
          request.page,
          request.limit,
          request.sortingFieldInternal,
          request.sortingMethod,
          request.filter)
      }
      case "specialities" => {  //  Специальности
        mapper.getSerializationConfig().setSerializationView(classOf[DictionaryDataViews.DefaultView]);
        request.setRecordsCount(dbSpeciality.getCountOfBloodTypesWithFilter(request.filter))
        dbSpeciality.getAllSpecialitiesWithFilter(request.page,
                                                  request.limit,
                                                  request.sortingFieldInternal,
                                                  request.sortingMethod,
                                                  request.filter)
      }
      case "contactTypes" => {  //  Типы контактов
        mapper.getSerializationConfig().setSerializationView(classOf[DictionaryDataViews.DefaultView])
        request.setRecordsCount(dbRbContactType.getCountOfAllRbContactTypesWithFilter(request.filter))
        dbRbContactType.getAllRbContactTypesWithFilter( request.page,
                                                        request.limit,
                                                        request.sortingFieldInternal,
                                                        request.sortingMethod,
                                                        request.filter)
      }
      case "requestTypes" => {  //  Типы обращений
        mapper.getSerializationConfig().setSerializationView(classOf[DictionaryDataViews.DefaultView])
        dbRbRequestTypes.getAllRbRequestTypesWithFilter(request.page,
                                                        request.limit,
                                                        request.sortingFieldInternal,
                                                        request.sortingMethod,
                                                        request.filter,
                                                        request.rewriteRecordsCount _)
      }
      case "finance" => {  //  Типы оплаты
        mapper.getSerializationConfig().setSerializationView(classOf[DictionaryDataViews.DefaultView])
        dbRbFinance.getAllRbFinanceWithFilter(request.page,
                                              request.limit,
                                              request.sortingFieldInternal,
                                              request.sortingMethod,
                                              request.filter,
                                              request.rewriteRecordsCount _)
      }
      case "quotaStatus" => { //   Статусы квот
        mapper.getSerializationConfig().setSerializationView(classOf[DictionaryDataViews.DefaultView])
        request.setRecordsCount(dbRbQuotaStatus.getCountOfRbQuotaStatusWithFilter(request.filter))
        dbRbQuotaStatus.getAllRbQuotaStatusWithFilter(request.page,
                                              request.limit,
                                              request.sortingFieldInternal,
                                              request.sortingMethod,
                                              request.filter)
      }
    }
    mapper.writeValueAsString(new DictionaryListData(list, request))
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

    val list = dbEventBean.getEventTypesByRequestTypeIdAndFinanceId(request.page,
                                                                    request.limit,
                                                                    request.sortingFieldInternal,
                                                                    request.sortingMethod,
                                                                    request.filter,
                                                                    request.rewriteRecordsCount _)

    mapper.writeValueAsString(new EventTypesListData(list, request))
  }

}