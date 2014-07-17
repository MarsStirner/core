package ru.korus.tmis.ws.impl

import scala.collection.JavaConverters._
import ru.korus.tmis.core.data._
import ru.korus.tmis.core.auth.{AuthToken, AuthStorageBeanLocal, AuthData}
import org.codehaus.jackson.map.ObjectMapper
import ru.korus.tmis.core.exception.CoreException
import java.{util => ju, lang}
import ru.korus.tmis.core.entity.model._
import collection.mutable
import java.util.{Date, LinkedList}
import grizzled.slf4j.Logging
import ru.korus.tmis.ws.webmis.rest.{LockData, WebMisREST}
import javax.ejb.{Stateless, EJB}
import ru.korus.tmis.core.database._
import ru.korus.tmis.core.database.common._
import ru.korus.tmis.core.patient._
import scala.collection.JavaConversions._
import com.google.common.collect.Lists
import javax.servlet.http.Cookie
import scala.Predef._

import ru.korus.tmis.scala.util._
import ru.korus.tmis.core.database.bak.{DbBbtResultOrganismBeanLocal, DbBbtResponseBeanLocal, DbBbtResultTextBeanLocal}
import org.joda.time.DateTime
import ru.korus.tmis.core.lock.ActionWithLockInfo
import ru.korus.tmis.scala.util.StringId

/**
 * User: idmitriev
 * Date: 3/19/13
 * Time: 11:53 AM
 */

@Stateless
class WebMisRESTImpl  extends WebMisREST
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
    val inPatientEntry = patientData.getData
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

  private def constructAppealData(ide: Int): java.lang.String = {
    if(ide>0) {
      val result = appealBean.getAppealById(ide)

      val positionE = result.iterator.next()
      val positionA = positionE._2.iterator.next()
      val values = positionA._2.asInstanceOf[java.util.Map[(java.lang.Integer, ActionProperty), java.util.List[Object]]]

      val mapper: ObjectMapper = new ObjectMapper()
      //mapper.getSerializationConfig.withView(classOf[Views.DynamicFieldsStandartForm])
      val patient = positionE._1.getPatient
      val map = patientBean.getKLADRAddressMapForPatient(patient)
      val street = patientBean.getKLADRStreetForPatient(patient)
      //val appType = dbFDRecordBean.getIdValueFDRecordByEventTypeId(25, positionE._1.getEventType.getId.intValue())
      val currentDepartment = hospitalBedBean.getCurrentDepartmentForAppeal(ide)
      //
      mapper.writeValueAsString(new AppealData( positionE._1,
        positionA._1,
        values,
        null,
        "standart",
        map,
        street,
        null,
        actionBean.getLastActionByActionTypeIdAndEventId,  //havePrimary
        dbClientRelation.getClientRelationByRelativeId,
        null,
        if (positionA._1.getContractId != null) {
          dbContractBean.getContractById(positionA._1.getContractId.intValue())
        } else {null},
        currentDepartment,
        dbDiagnosticBean.getDiagnosticsByEventIdAndTypes,
        dbTempInvalidBean.getTempInvalidByEventId(positionE._1.getId.intValue())
      ))
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
    mapper.getSerializationConfig().setSerializationView(classOf[Views.DynamicFieldsStandartForm]);
    //val map = patientBean.getKLADRAddressMapForPatient(result)
    //val appType = dbFDRecordBean.getIdValueFDRecordByEventTypeId(25, positionE._1.getEventType.getId.intValue())
    val currentDepartment = hospitalBedBean.getCurrentDepartmentForAppeal(id)

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
      actionPropertyBean.getActionPropertiesByActionIdAndActionPropertyTypeCodes _,
      if (positionE._1.getContract != null) {
        dbContractBean.getContractById(positionE._1.getContract.getId.intValue())
      } else {null},
      currentDepartment,
      dbDiagnosticBean.getDiagnosticsByEventIdAndTypes _,
      dbTempInvalidBean.getTempInvalidByEventId(positionE._1.getId.intValue())
    ))
  }

  def getAppealPrintFormById(id: Int, auth: AuthData) = {
    val result = appealBean.getAppealById(id)
    val positionE = result.iterator.next()
    val positionA = positionE._2.iterator.next()
    val values = positionA._2.asInstanceOf[java.util.Map[(java.lang.Integer, ActionProperty), java.util.List[Object]]]
    val mapper: ObjectMapper = new ObjectMapper()

    mapper.getSerializationConfig().setSerializationView(classOf[Views.DynamicFieldsPrintForm])

    val map = patientBean.getKLADRAddressMapForPatient(positionE._1.getPatient)
    val street = patientBean.getKLADRStreetForPatient(positionE._1.getPatient)
    val currentDepartment = hospitalBedBean.getCurrentDepartmentForAppeal(id)

    mapper.writeValueAsString(new AppealData( positionE._1,
      positionA._1,
      values,
      actionPropertyBean.getActionPropertiesByEventIdsAndActionPropertyTypeCodes _,
      "print_form",
      map,
      street,
      null,
      actionBean.getLastActionByActionTypeIdAndEventId _, //havePrimary
      dbClientRelation.getClientRelationByRelativeId _,
      actionPropertyBean.getActionPropertiesByActionIdAndActionPropertyTypeCodes _,  //в тч Admission Diagnosis
      if (positionA._1.getContractId != null) {
        dbContractBean.getContractById(positionA._1.getContractId.intValue())
      } else {null},
      currentDepartment,
      dbDiagnosticBean.getDiagnosticsByEventIdAndTypes _,
      dbTempInvalidBean.getTempInvalidByEventId(positionE._1.getId.intValue())
    ))

  }

  def getAllAppealsByPatient(requestData: AppealSimplifiedRequestData, auth: AuthData): AppealSimplifiedDataList = {
    val set = appealBean.getSupportedAppealTypeCodes //справочник госпитализаций
    requestData.filter.asInstanceOf[AppealSimplifiedRequestDataFilter].code = set.asInstanceOf[ju.Collection[String]]
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

    requestData.setRecordsCount(dbEventBean.getCountOfAppealsForReceivedPatientByPeriod(requestData.filter))
    val data = if(requestData.recordsCount!=0){
      var received = dbEventBean.getAllAppealsForReceivedPatientByPeriod(requestData.page-1,
        requestData.limit,
        requestData.sortingFieldInternal,
        requestData.sortingMethod,
        requestData.filter)
      new ReceivedPatientsData( received,
        requestData,
        appealBean.getDiagnosisListByAppealId _,
        actionPropertyBean.getActionPropertiesByEventIdsAndActionPropertyTypeCodes _,
        //actionPropertyBean.getActionPropertiesForEventByActionTypes _,
        actionBean.getLastActionByActionTypeIdAndEventId _,
        appealBean.getPatientsHospitalizedStatus _,
        actionBean.getAppealActionByEventId _)
    } else new ReceivedPatientsData()

    mapper.writeValueAsString(data)
  }

  //запрос на структуру первичного мед. осмотра
  def getStructOfPrimaryMedExam(actionTypeId: Int, eventId: Int, authData: AuthData) = {
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
    val flgDiagnostics = (at!=null &&
                          (at.getMnemonic.toUpperCase().compareTo("LAB")==0 ||
                           at.getMnemonic.toUpperCase().compareTo("BAK_LAB")==0 ||
                           at.getMnemonic.toUpperCase().compareTo("DIAG")==0 ||
                           at.getMnemonic.toUpperCase().compareTo("CONS")==0))
    if(flgDiagnostics){
      listForSummary.add(ActionWrapperInfo.executorId)
      listForSummary.add(ActionWrapperInfo.assignerId)
    }
    //listForSummary.add(ActionWrapperInfo.toOrder)

    val event = if(eventId > 0)dbEventBean.getEventById(eventId) else null

    primaryAssessmentBean.getEmptyStructure(actionTypeId,
                                            "Document",
                                            listForConverter,
                                            listForSummary,
                                            authData,
                                            postProcessing(event) _,
                                            null)
  }

  //запрос на структуру первичного мед. осмотра с копированием данных из предыдущего осмотра
  def getStructOfPrimaryMedExamWithCopy(actionTypeId: Int, authData: AuthData, eventId: Int) = {
    var lastActionId = actionBean.getActionIdWithCopyByEventId(eventId, actionTypeId)
    try {
      primaryAssessmentBean.getPrimaryAssessmentById(lastActionId, "Document", authData, postProcessing() _, false) //postProcessingWithCopy _, true)
    }
    catch {
      case e: Exception => {
        getStructOfPrimaryMedExam(actionTypeId, -1, authData)
      }
    }

  }

  private def postProcessing (event: Event = null)(jData: JSONCommonData, reWriteId: java.lang.Boolean) = {
    jData.data.get(0).group.get(1).attribute.foreach(ap => {
      if(ap.typeId==null || ap.typeId.intValue()<=0) {
        if(reWriteId.booleanValue)  //в id -> apt.id
          ap.typeId = ap.id
        else
          ap.typeId = actionPropertyBean.getActionPropertyById(ap.id.intValue()).getType.getId.intValue()
      }

      // Вычисляем "подтягивающиеся" из прошлых документов значение
      if(event != null) {
        val aProp = try { actionPropertyTypeBean.getActionPropertyTypeById(ap.getId())} catch {
          case e: Throwable => null
        }
        val at = try {actionTypeBean.getActionTypeById(jData.getData.get(0).getId())} catch {
          case e: Throwable => null
        }

        if(aProp != null && at != null)
          ap setCalculatedValue new APValueContainer(calculateActionPropertyValue(event, at, aProp))
      }
    }
    )



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

  /**
   * Метод для вставки аттрибутов, которые должны подтягиваться при создании новых документов
   * (например диагноз из предыдущего документа)
   * Так как данный метод "сам в себе" то и константы-идентификаторы я буду хранить рядом для лучшей ясности.
   * Метод костылен по своей природе, т.к. костыльна сама поставленная задача.
   * @param event Экземпляр события, для которого создается документ
   * @param at Тип создаваемого документа
   * @param ap Свойство создаваемого документа
   */
  private def calculateActionPropertyValue(event: Event, at: ActionType, ap: ActionPropertyType): APValue = {

    val infectionPropsSet = Set("infectFever", "infectBacteremia", "infectSepsis", "infectSepticShok", "infectLocal", "infectDocumental", "infectCephalopyosis", "infectMeningitis", "infectMeningoencephalitis", "infectEncephalitis", "infectCNSComment", "infectConjunctivitis", "infectPeriorbital", "infectBlepharitis", "infectChorioretinitis", "infectEyeComment", "infectSkinLight", "infectSkinHard", "infectSkinComment", "infectMucositis12", "infectMucositis34", "infectEsophagitis", "infectGingivitis", "infectMucousComment", "infectRhinitis", "infectTonsillitis", "infectOtitis", "infectDefeatPPN", "infectLORComment", "infectBronchitis", "infectInterstitialPneumonia", "infectLobarPneumonia", "infectPleurisy", "infectLungsComment", "infectPericarditis", "infectMioardit", "infectEndocarditis", "infectHeartComment", "infectGastritis", "infectPancreatitis", "infectCholecystitis", "infecThepatitis", "infectGepatolienalnyCandidiasis", "infectAbscess", "infectEnterocolitis", "infectCecitis", "infectAppendicitis", "infectPeritonitis", "infectAbdomenComment", "infectGlomerulonephritis", "infectPyelonephritis", "infectCystitis", "infectUrethritis", "infectEndometritis", "infectAdnexitis", "infectVulvovaginitis", "infectUrogenitalComment", "infectOsteomyelitis", "infectMyositis", "infectMusculoskeletalComment", "infectEtiologyBacterial", "infectEtiologyFungal", "infectEtiologyVirus", "infectEtiologyUnknown")

    val therapySet = Set("therapyTitle", "therapyBegDate", "therapyPhaseTitle", "therapyPhaseBegDate")

    // Получение значения свойства у предыдущих действий
    def getProperty(oldDocumentCodes: Set[String], actionTypeCodes: Set[String]): APValue = {
        if(oldDocumentCodes.contains(at.getCode)) {
          val pastActions = actionBean.getActionsByTypeCodeAndEventId(oldDocumentCodes, event.getId, "a.begDate DESC", null)
          if(pastActions != null && !pastActions.isEmpty)
            pastActions.head.getActionProperties.foreach(e => {
              if(e.getType.getCode != null && e.getType.getCode.equals(ap.getCode)) {
                val values = actionPropertyBean.getActionPropertyValue(e)
                if(actionTypeCodes.contains(e.getType.getCode)) {
                  if(values.size() > 0)
                    return values.head
                }
              }
            })
          null
        }
      null
    }

    // Получение значений свойства у предыдущих дневниковых осмотров для нового дневникового осмотра
    def getPropertyCustom1(oldDocumentCodes: Set[String], actionTypeCodes: Set[String]): APValue = {

      def getTherapyPhaseEndDate(action: Action): Date = {
        action.getActionProperties.foreach(p =>
          if (p.getType.getCode != null && p.getType.getCode.equals("therapyPhaseEndDate"))
          actionPropertyBean.getActionPropertyValue(p).foreach {
            case d: Date => return d
            case _ =>
          }
        )
        null
      }

      if(oldDocumentCodes.contains(at.getCode)) {
        val pastActions = actionBean.getActionsByTypeCodeAndEventId(oldDocumentCodes, event.getId, "a.begDate DESC", null)
        if(pastActions != null && !pastActions.isEmpty && getTherapyPhaseEndDate(pastActions.head) != null)
          pastActions.head.getActionProperties.foreach(e => {
            if(e.getType.getCode != null && e.getType.getCode.equals(ap.getCode)) {
              val values = actionPropertyBean.getActionPropertyValue(e)
              if(actionTypeCodes.contains(e.getType.getCode)) {
                if(values.size() > 0)
                  return values.head
              }
            }
          })
        null
      }
      null
    }

    // Получение значений свойств по свойствам инфекционного контроля
    def getPropertyCustom2(oldDocumentCodes: Set[String], actionTypeCodes: Set[String]): APValue = {

      if(!(actionTypeCodes contains ap.getCode) || !(oldDocumentCodes contains at.getCode))
        return null

      //Получаем последний дневниковый осмотр из всех историй болезни
      def getLastAction(): Action = {
        val patientId = event.getPatient.getId
        val events = dbEventBean.getEventsForPatient(patientId)
        val list = events.flatMap(e => actionBean.getActionsByEvent(e.getId))
          .filter(a => oldDocumentCodes.contains(a.getActionType.getCode))
          .sortBy(_.getCreateDatetime)

        if(!list.isEmpty)
          list.last
        else
          null

      }

      val lastAction = getLastAction()
      val endDateProperty = lastAction.getActionProperties.find(ap => ap.getType.getCode != null && ap.getType.getCode.equals("infectEndDate"))

      val endDateValue: Date = {
        if (endDateProperty.isDefined) {
          val ap = endDateProperty.get
          actionPropertyBean.getActionPropertyValue(ap).head match {
            case p: APValueDate => p.getValue
            case _ => null
          }
        } else
          null
      }

      // Даты конца нет, нужно подтянуть значения
      if(endDateValue == null) {
        lastAction.getActionProperties.foreach(p => {
          if(p.getType.getCode != null && p.getType.getCode.equals(ap.getCode)) {
            val values = actionPropertyBean.getActionPropertyValue(p)
            if(values.size() > 0)
              return values.head
          }
        })
        null
      } else
        null

    }

    at.getCode match {

      // Заключительный эпикриз
      case "4504" => getProperty(Set("4501", "4502", "4503", "4504", "4505", "4506", "4507", "4508", "4509", "4510", "4511"), Set("mainDiag","mainDiagMkb"))

      // Дневниковый осмотр
      case "1_2_18" => {
        if(therapySet.contains(at.getCode()))
          getPropertyCustom1(Set("1_2_18"), therapySet)
        else if(infectionPropsSet.contains(at.getCode())) {
          getPropertyCustom2(Set("1_2_18"), infectionPropsSet)
        }
        else
          null

      }
      case _ => null
    }
  }

  //создание первичного мед. осмотра
  def insertPrimaryMedExamForPatient(eventId: Int, data: JSONCommonData, authData: AuthData)  = {

    validateDocumentsAvailability(eventId)

    val isPrimary = (data.getData.find(ce => ce.getTypeId().compareTo(i18n("db.actionType.primary").toInt)==0).getOrElse(null)!=null) //Врач прописывается только для первичного осмотра  (ид=139)
    if(isPrimary)
      appealBean.setExecPersonForAppeal(eventId, 0, authData, ExecPersonSetType.EP_CREATE_PRIMARY)

    //создаем осмотр. ЕвентПерсон не флашится!!!
    val returnValue = primaryAssessmentBean createPrimaryAssessmentForEventId(eventId,
      data,
      "Document",
      authData,
      /*preProcessing _*/null,
      postProcessing() _)
    returnValue
  }

  //редактирование первичного мед. осмотра
  def modifyPrimaryMedExamForPatient(actionId: Int, data: JSONCommonData, authData: AuthData)  = {

    validateDocumentsAvailability(actionBean.getActionById(actionId).getEvent.getId)

    //создаем ответственного, если до этого был другой
    if(data.getData.find(ce => ce.getTypeId().compareTo(i18n("db.actionType.primary").toInt)==0).getOrElse(null)!=null) //Врач прописывается только для первичного осмотра  (ид=139)
      appealBean.setExecPersonForAppeal(actionId, 0, authData, ExecPersonSetType.EP_MODIFY_PRIMARY)

    //создаем осмотр. ЕвентПерсон не флашится!!!
    val returnValue = primaryAssessmentBean.modifyPrimaryAssessmentById(actionId,
      data,
      "Document",
      authData,
      /*preProcessing _*/null,
      postProcessing() _)
    returnValue
  }

  private def validateDocumentsAvailability(eventId: Int) = {
    val event = dbEventBean.getEventById(eventId)
    if(event == null)
      throw new CoreException(i18n("settings.path.eventBlockTime").format(eventId));

    //, Нельзя создавать документы для закрытой госпитализации,
    // если прошло больше дней, чем указанно в конфигурации
    val closeDate = event.getExecDate
    if(closeDate != null) {
      val availableDays =  dbSettingsBean.getSettingByPathInMainSettings(i18n("settings.path.eventBlockTime")).getValue
      try {
        if(new DateTime(closeDate).plusDays(Integer.parseInt(availableDays)).getMillis < new DateTime().getMillis) {
          throw new CoreException("Редактирование документов разрешено только в течении " + Integer.parseInt(availableDays) + " после закрытия истории болезни")
        }
      } catch {
        case e: NumberFormatException => {
          throw new CoreException("Невозможно обработать значение свойства " + i18n("settings.path.eventBlockTime") + " - " + availableDays)
        }
        case e: Throwable => {
          throw new CoreException(i18n("error.unknownError"))
        }
      }
    }
  }


  def getPrimaryAssessmentById (assessmentId: Int, authData: AuthData) = {

    //TODO: подключить анализ авторизационных данных и доступных ролей
    //val authData:AuthData = null

    val json_data = primaryAssessmentBean.getPrimaryAssessmentById(assessmentId,
      "Assessment",
      authData,
      postProcessing() _, false)

    json_data
  }

  def getAllPatientsForDepartmentIdAndDoctorIdByPeriod(requestData: PatientsListRequestData, auth: AuthData) = {
    patientBean.getAllPatientsForDepartmentIdAndDoctorIdByPeriod(requestData, auth)
  }

  def getActionWithLockInfoList(list: java.util.List[Action]) : java.util.List[ActionWithLockInfo] =  {
    val res :  java.util.List[ActionWithLockInfo] = new java.util.LinkedList[ActionWithLockInfo]
    list.foreach(action => res.add(authStorage.getLockInfo(action)))
    res
  }

  //Возвращает список осмотров по пациенту и обращению с фильтрацией по типу действия
  def getListOfAssessmentsForPatientByEvent(requestData: AssessmentsListRequestData, auth: AuthData) = {
    val action_list = actionBean.getActionsWithFilter(requestData.limit,
                                                      requestData.page-1,
                                                      requestData.sortingFieldInternal,
                                                      requestData.filter.unwrap(),
                                                      requestData.rewriteRecordsCount _,
                                                      auth)
    val actionWithLockInfoList = getActionWithLockInfoList(action_list);
    //actionBean.getActionsByEventIdWithFilter(requestData.eventId, auth, requestData)
    val assessments: AssessmentsListData = new AssessmentsListData(actionWithLockInfoList, requestData)
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

  def getMovingListForEvent(filter: HospitalBedDataListFilter, authData: AuthData) = {

    val mapper: ObjectMapper = new ObjectMapper()
    mapper.getSerializationConfig().setSerializationView(classOf[HospitalBedViews.MovesListView])
    mapper.writeValueAsString(hospitalBedBean.getMovingListByEventIdAndFilter(filter, authData))
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

  def getAllAvailableBedProfiles() = hospitalBedProfileBean.getAllRbHospitalBedProfiles

  /*  def getFormOfAccountingMovementOfPatients(departmentId: Int) = {
    val linear = seventhFormBean.fillInSeventhForm(departmentId, null, null/*previousMedDate, currentMedDate*/)
    new FormOfAccountingMovementOfPatientsData(linear, null)
  }*/

  //форма 007
  def getForm007(departmentId: Int,
                 beginDate: Long,
                 endDate: Long,
                 profileBeds: java.util.List[Integer],
                 authData: AuthData) = seventhFormBean.getForm007LinearView(departmentId, beginDate, endDate, profileBeds)


  def movingPatientToDepartment(eventId: Int, data: HospitalBedData, authData: AuthData) = {

    val action = hospitalBedBean.movingPatientToDepartment(eventId, data, authData)

    val mapper: ObjectMapper = new ObjectMapper()
    mapper.getSerializationConfig().setSerializationView(classOf[HospitalBedViews.MoveView])
    mapper.writeValueAsString(hospitalBedBean.getRegistryOriginalForm(action, authData))
  }

  def closeLastMovingOfAppeal(eventId: Int, authData: AuthData, date: Date) = {
    val actionTypes = new java.util.HashSet[Integer]
    actionTypes.add(i18n("db.actionType.moving").toInt)
    val lastAction = actionBean.getLastActionByActionTypeIdAndEventId(eventId, actionTypes)
    val action = actionBean.getActionById(lastAction)

    // Проверяем входные значения
    if(actionBean.getActionsByTypeFlatCodeAndEventId(eventId, "leaved").size < 1)
      throw new CoreException("Для закрытия последнего движения требуется наличие выписки")
    if(date.before(action.getBegDate))
      throw new CoreException("Дата закрытия движения не может быть раньше даты начала движения")

    action.setEndDate(date)
    action.setStatus(2)           //A little piece of magic - 2 is a status of CLOSED action
    actionBean.updateAction(action)
    val mapper: ObjectMapper = new ObjectMapper()
    mapper.getSerializationConfig().setSerializationView(classOf[ActionDataContainer])
    mapper.writeValueAsString(new ActionDataContainer(action))
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
    new AllPersonsListData(dbStaff.getAllPersonsByRequest(requestData.limit,
                                                          requestData.page-1,
                                                          requestData.sortingFieldInternal,
                                                          requestData.filter.unwrap(),
                                                          requestData.rewriteRecordsCount _),
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


  def getListOfDiagnosticsForPatientByEvent(requestData: DiagnosticsListRequestData, authData: AuthData) = {

    //TODO: подключить анализ авторизационных данных и доступных ролей
    requestData.setRecordsCount(dbCustomQueryBean.getCountDiagnosticsWithFilter(requestData.filter))
    var actions: java.util.List[Action] = null
    if(requestData.getRecordsCount()>0) {
      actions = dbCustomQueryBean.getAllDiagnosticsWithFilter(requestData.page-1,
                                                              requestData.limit,
                                                              requestData.sortingFieldInternal,
                                                              requestData.filter.unwrap())
    }
    var ajtList = new ju.LinkedList[(Action, JobTicket)]()
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
    var citoActionsCount = 0;
    if (beginDate > 0) {
      citoActionsCount = actionBean.getActionForDateAndPacientInQueueType(beginDate, 1).toInt;
    }

    new FreePersonsListDataFilter()
    val list = new FreePersonsListData(dbStaff.getEmptyPersonsByRequest( requestData.limit,
      requestData.page-1,
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
      case 0 => {
        val actionType = if(request.filter.asInstanceOf[ActionTypesListRequestDataFilter].getGroupId()> 0){
          actionTypeBean.getActionTypeById(request.filter.asInstanceOf[ActionTypesListRequestDataFilter].getGroupId())
        } else if ( request.filter.asInstanceOf[ActionTypesListRequestDataFilter].getCode() != null  &&
                    request.filter.asInstanceOf[ActionTypesListRequestDataFilter].getCode().compareTo("") != 0) {
          val types = actionTypeBean.getActionTypesByCode(request.filter.asInstanceOf[ActionTypesListRequestDataFilter].getCode())
          if (types!=null && types.size()>0){
            val mnems = request.filter.asInstanceOf[ActionTypesListRequestDataFilter].getMnemonics
            types.find(p => {
               if(mnems!=null && mnems.size()>0){
                 val ress = mnems.find(mnem => mnem.compareTo(p.getMnemonic)==0).getOrElse(null)
                 if (ress!=null)true else false
               }
               else {
                 p.getMnemonic.compareTo("")==0
               }
            }).getOrElse(null)
          }  else null
        } else null

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
        listForSummary.add(ActionWrapperInfo.assignerLastName)
        listForSummary.add(ActionWrapperInfo.assignerFirstName)
        listForSummary.add(ActionWrapperInfo.assignerMiddleName)
        listForSummary.add(ActionWrapperInfo.assignerSpecs)
        listForSummary.add(ActionWrapperInfo.assignerPost)
        listForSummary.add(ActionWrapperInfo.urgent)
        listForSummary.add(ActionWrapperInfo.multiplicity)
        listForSummary.add(ActionWrapperInfo.finance)
        listForSummary.add(ActionWrapperInfo.plannedEndDate)
        //listForSummary.add(ActionWrapperInfo.toOrder)

        //Для направлений на лабисследования, консультации и инструментальные иследования выводить поле "Направивший врач"
        val mnemonics = request.filter.asInstanceOf[ActionTypesListRequestDataFilter].getMnemonics
        val flgDiagnostics = (
          mnemonics!=null &&
          mnemonics.size()>0 &&
          (mnemonics.filter(p=>(p.toUpperCase().compareTo("LAB")==0 ||
                                p.toUpperCase().compareTo("BAK_LAB")==0 ||
                                p.toUpperCase().compareTo("DIAG")==0 ||
                                p.toUpperCase().compareTo("CONS")==0))
          ).size>0
        )

        if(flgDiagnostics){
          listForSummary.add(ActionWrapperInfo.executorId)
          listForSummary.add(ActionWrapperInfo.assignerId)
        }
        var json = new JSONCommonData()
        if (actionType != null) {
          json = primaryAssessmentBean.getEmptyStructure(actionType.getId.intValue(), "Action", listForConverter, listForSummary,  null, null, patientBean.getPatientById(patientId))
        }
        json.setRequestData(request)
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
    mapper.getSerializationConfig().setSerializationView(classOf[ActionTypesListDataViews.DefaultView]);   //дерево
    mapper.writeValueAsString(new ActionTypesListData(request, actionTypeBean.getAllActionTypeWithFilter _))
  }

  //********* Диагнозтические исследования **********
  def insertLaboratoryStudies(eventId: Int, data: CommonData, auth: AuthData) = {
    val json = directionBean.createDirectionsForEventIdFromCommonData(eventId, data, "Diagnostic", null, "LAB", auth,  postProcessingForDiagnosis _)
    json.getData().map(entity => entity.getId().intValue()).foreach(a_id => {
      val action = actionBean.getActionById(a_id)
      if (action.getStatus == 2 && !action.getIsUrgent) {
        directionBean.sendActionToLis(a_id)
      }
    })
    json
    //primaryAssessmentBean.createAssessmentsForEventIdFromCommonData(eventId, data, "Diagnostic", null, auth,  postProcessingForDiagnosis _)// postProcessingForDiagnosis
  }

  def modifyLaboratoryStudies(eventId: Int, data: CommonData, auth: AuthData) = {
    directionBean.modifyDirectionsForEventIdFromCommonData(eventId, data, "Diagnostic", null, "LAB", auth,  postProcessingForDiagnosis _)// postProcessingForDiagnosis
  }

  def insertInstrumentalStudies(eventId: Int, data: CommonData, auth: AuthData) = {
    directionBean.createDirectionsForEventIdFromCommonData(eventId, data, "Diagnostic", null, "DIAG", auth,  postProcessingForDiagnosis _)// postProcessingForDiagnosis
  }

  def modifyInstrumentalStudies(eventId: Int, data: CommonData, auth: AuthData) = {
    directionBean.modifyDirectionsForEventIdFromCommonData(eventId, data, "Diagnostic", null, "DIAG", auth,  postProcessingForDiagnosis _)// postProcessingForDiagnosis
  }

  def insertConsultation(request: ConsultationRequestData, authData: AuthData) = {
    val actionId = directionBean.createConsultation(request, authData)
    val json = directionBean.getDirectionById(actionId, "Consultation", null, authData)
    json.setRequestData(request) //по идее эта штука должна быть в конструкторе вызываемая в методе гет
    json
  }

  def modifyConsultation(request: ConsultationRequestData, authData: AuthData) = {
    val actionId = directionBean.createConsultation(request, authData)
    var json = directionBean.getDirectionById(actionId, "Consultation", null, authData)
    json.setRequestData(request) //по идее эта штука должна быть в конструкторе вызываемая в методе гет
    json
  }

  def removeDirection(data: AssignmentsToRemoveDataList, directionType: String, auth: AuthData) = {
    directionBean.removeDirections(data, directionType, auth)
  }

  def checkCountOfConsultations(eventId: Int, pqt: Int, executorId: Int, data: Long) {
    var executor = dbStaff.getStaffById(executorId)
    var actionsCount = actionBean.getActionForEventAndPacientInQueueType(eventId, data, pqt)
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
    var a = actionBean.getActionById(actionId)
    var action19 = actionBean.getEvent29AndAction19ForAction(a)
    val apva = actionPropertyBean.getActionPropertyValue_ActionByValue(action19)
    val filter = new FreePersonsListDataFilter( 0,
      a.getExecutor.getId.intValue(),
      a.getActionType.getId.intValue(),
      a.getPlannedEndDate.getTime,
      0)
    val timesAP = dbStaff.getActionPropertyForPersonByRequest(filter)
    if (timesAP != null) {
      val timesAPV = actionPropertyBean.getActionPropertyValue(timesAP)
      var data = new ScheduleContainer(timesAPV.get(apva.getId.getIndex).asInstanceOf[APValueTime])
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
      case "operationTypes" => {  //Типы операций
        mapper.getSerializationConfig().setSerializationView(classOf[DictionaryDataViews.DefaultView])
        dbRbOperationType.getAllRbOperationTypeWithFilter(request.page-1,
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

  def getContracts(eventTypeId: Int, showDeleted: Boolean, showExpired: Boolean) = {
    if(eventTypeId < 1)
      throw new CoreException("Идентификатор типа события не может быть меньше 1")

    val e = dbEventTypeBean.getEventTypeById(eventTypeId)
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
      // actions.size() > 0 --- остались акшен не добавленные в мапу.
      // map.size == 0 --- если нашли только один жоб тикет, так как мапа пустая и все акшены остались в actions
      if (actions.size() > 0 || map.size == 0) map += (firstJobTicket -> actions)
    }
    new TakingOfBiomaterialData(map,
                                hospitalBedBean.getLastMovingActionForEventId _,
                                actionPropertyBean.getActionPropertiesByActionIdAndRbCoreActionPropertyIds _,
                                request)
  }


  override def getJobTicketById(id: Int, authData: AuthData): JobTicket = {
    dbJobTicketBean getJobTicketById id
  }

  def updateJobTicketsStatuses(data: JobTicketStatusDataList, authData: AuthData) = {
    directionBean.updateJobTicketsStatuses(data, authData)
  }

  def deletePatientInfo(id: Int) = patientBean.deletePatientInfo(id)

  def getDiagnosesByAppeal (appealId: Int, authData: AuthData) = diagnosisBean.getDiagnosesByAppeal(appealId)

  def getBloodTypesHistory (patientId: Int, authData: AuthData) = patientBean.getBloodHistory(patientId)

  def insertBloodTypeForPatient(patientId: Int, data: BloodHistoryData, authData: AuthData) = {
    patientBean.insertBloodTypeForPatient(patientId,
                                          data,
                                          authData)
  }

  def getMonitoringInfoByAppeal(eventId: Int, condition: Int, authData: AuthData) = {
    appealBean.getMonitoringInfo(eventId, condition, authData)
  }

  def getSurgicalOperationsByAppeal(eventId: Int, authData: AuthData) = {
    appealBean.getSurgicalOperations(eventId, authData)
  }

  def setExecPersonForAppeal(eventId: Int, personId: Int, authData: AuthData) = {
    appealBean.setExecPersonForAppeal(eventId, personId, authData, ExecPersonSetType.EP_SET_IN_LPU)
  }

  def getLayoutAttributes() = new LayoutAttributeListData(dbLayoutAttributeBean.getAllLayoutAttributes)

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
    if(fRender != null)
      templates = templates.filter(_.getRender == fRender)


    // Убираем значения не заданных полей, если задан параметр fields
    if(fields != null && !fields.isEmpty) {
      templates.foreach(template => {
        classOf[RbPrintTemplate].getDeclaredFields.foreach(f => {
          if(!fields.contains(f.getName)) {
            f.getType match {
              case t if(t.equals(classOf[String])) => {f.setAccessible(true); f.set(template, null)}
              case t if(t.equals(classOf[Integer])) => {f.setAccessible(true); f.set(template, null)}
              case _ => {}
            }
          }
        })

      })
    }
    seqAsJavaList(templates)
  }

  def getOrganizationById(id: Int, authData: AuthData): OrganizationContainer = {
    val org = dbOrganizationBean.getOrganizationById(id)
    if(org != null)
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

  override def getRlsById(id: Int): Nomenclature = {
    dbRlsBean.getRlsById(id)
  }

  override def getRlsByText(text: String): ju.List[Nomenclature] = {
    dbRlsBean.getRlsByText(text)
  }


  def lock(actionId: Int, auth: AuthData): LockData = {
    val appLockDetail: AppLockDetail = authStorage.getAppLock(auth.getAuthToken, "Action", actionId)
    return new LockData(appLockDetail.getId.getMasterId)
  }

  def prolongLock(actionId: Int, auth: AuthData): LockData = {
    val appLockDetail: AppLockDetail = authStorage.prolongAppLock(auth.getAuthToken, "Action", actionId)
    return new LockData(appLockDetail.getId.getMasterId)
  }

  def releaseLock(actionId: Int, auth: AuthData) {
    authStorage.releaseAppLock(auth.getAuthToken, "Action", actionId)
  }

}
