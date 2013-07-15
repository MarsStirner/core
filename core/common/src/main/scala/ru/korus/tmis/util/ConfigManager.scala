package ru.korus.tmis.util

import java.net.URL
import java.text.SimpleDateFormat
import java.util.ResourceBundle
import javax.xml.bind.DatatypeConverter
import javax.xml.namespace.QName
import reflect.Configuration
import grizzled.slf4j.Logging


object ConfigManager extends Configuration {

  var DateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

  /**
   * Общие параметры работы ядра
   */
  class CommonClass extends Configuration {
    var OrgId = 3479 // индекс организации в табл Organization (по умолчанию id ФНКЦ для БД ФНКЦ)
  }

  var Common = new CommonClass

  val Filter = new Configuration {
    var isOn = false
  }

  val AWI = ActionWrapperInfo

  val APWI = ActionPropertyWrapperInfo
  val APPEALWI = AppealWrapperInfo
  val APPEALPWI = AppealPropertyWrapperInfo

  val Types = new Configuration {
    var Integer = "Integer"
    var Double = "Double"

    var String = "String"

    var Datetime = "Datetime"
    var Date = "Date"
    var Time = "Time"
    var Boolean = "Boolean"
    var Object = "Object"
  }

  val ActionStatus = new Configuration {
    as =>
    // Статусы действия из кодов толстого клиента
    var Started: Short = 0
    var Wait: Short = 1
    var Finished: Short = 2
    var Canceled: Short = 3

    def immutable = new {
      val Started = as.Started
      val Wait = as.Wait
      val Finished = as.Finished
      val Canceled = as.Canceled
    }
  }

  val VeriDrug = new Configuration {
    var Compatible = 1
  }

  val ErrorCodes = new Configuration {
    var PatientNotFound = 0x0101
    var ActionNotFound = 0x0102
    var ActionPropertyNotFound = 0x0103
    var InvalidCommonData = 0x0104
    var RecordChanged = 0x0105
    var IllegalPropertyValue = 0x0106
    var ClientDocumentNotFound = 0x107
    var RbDocumentTypeNotFound = 0x108
    var ClientPolicyNotFound = 0x109
    var RbPolicyTypeNotFound = 0x110
    var OrganisationNotFound = 0x111
    var RbContactTypeNotFound = 0x112
    var ClientContactNotFound = 0x113
    var ClientAddressNotFound = 0x114
    var ClientAllergyNotFound = 0x115
    var ClientIntoleranceMedicamentNotFound = 0x116
    var RbBloodTypeNotFound = 0x117
    var RbRelationTypeNotFound = 0x118
    var ClientRelationNotFound = 0x119
    var RbTempInvalidDocumentNotFound = 0x120
    var RbTempInvalidReasonNotFound = 0x121
    var ClientSocStatusNotFound = 0x122
    var ClientSocStatusTypeNotFound = 0x123
    var ClientSocStatusClassNotFound = 0x124
    var ClientSocStatusTypeAssocNotFound = 0x125
    var RbCounterNotFound = 0x126
    var RbQuotaStatusNotFound = 0x127
    var ClientQuotingNotFound = 0x128
    var ClientQuotingAllNotFound = 0x129
    var QuotaTypeNotFound = 0x130
    var OrgStructureNotFound = 0x131
    var EventPersonNotFound = 0x132
    var EventTypeNotFound = 0x133
    var EventPersonForEventAndUserNotFound = 0x134
    var ContractNotFound = 0x135
    var PatientIsNull = 0x136
    var RbTestTubeTypeIsNull = 0x137
    var JobTicketIsNull = 0x138
    var JobTicketNotFound = 0x139
    var RbDiagnosisTypeNotFound = 0x140
    var DiagnosisNotFound = 0x141
    var DiagnosticNotFound = 0x142
    var JobNotFound = 0x143
    var TakenTissueNotFound = 0x144
    var ActionTypeNotFound = 0x145
    var noRightForAction = 0x146
    var BloodTypeIsNull = 0x147
    var InvalidAuthData = 0x148
    var MkbNotFound = 0x149
  }

  /**
   * Параметры для 1С Аптека
   */
  class DrugstoreClass extends Configuration {
    /**
     * Включен ли сервис
     * on - включен
     * off - выключен (по умолчанию)
     */
    var Active = "off"

    var ServiceUrl = new URL("http://pharmacy3.fccho-moscow.ru/ws/MISExchange")
    var User = ""
    var Password = ""


    var OrgName = "ФНКЦ ДГОИ"

    var XmlNamespace = "urn:hl7-org:v3"
    var DefaultXsiType = ""

    val XsiNamespace = "http://www.w3.org/2001/XMLSchema-instance"

    var Hl7_SoapAction = "urn:hl7-org:v3#MISExchange:ProcessHL7v3Message"
    var Hl7_SoapOperation = "ProcessHL7v3Message"
    var Hl7_RequestRootElement = "Message"
    var Hl7_XsiType = "RCMR_IN000002UV02"

    var GetOrgList_SoapAction = "urn:hl7-org:v3#MISExchange:GetOrganizationList"
    var GetOrgList_SoapOperation = "GetOrganizationList"
    var GetOrgList_RequestRootElement = "None"

    var GetDepList_SoapAction = "urn:hl7-org:v3#MISExchange:GetDepartmentList"
    var GetDepList_SoapOperation = "GetDepartmentList"
    var GetDepList_RequestRootElement = "OrganizationRef"

    var UpdateRLS = "off"

    def isUpdateRLS = "on".equals(UpdateRLS)

    def HttpAuthToken = DatatypeConverter.printBase64Binary(
      (User + ":" + Password).getBytes)
  }

  var Drugstore = new DrugstoreClass


  /**
   * Параметры для HealthShare
   */
  class HealthShareClass extends Configuration {
    /**
     * Включен ли сервис
     * on - включен
     * off - выключен (по умолчанию)
     */
    var Active = "off"

    /**
     * Синхронизация справочников
     * on - включен
     * off - выключен
     */
    var ReferenceBookActive = "off"

    def isHealthShareReferenceBook = "on".equals(HealthShare.ReferenceBookActive)

    var ServiceUrl = new URL("http://188.127.249.29:57772/csp/healthshare/hsregistry/korus.NsiService.cls")
    var User = "demo"
    var Password = "demo"

    var ServiceUrlSda = new URL("http://188.127.249.29:57772/csp/healthshare/hsedgesda/isc.SDASoapService.cls")

    /**
     * Передача карточки пациента по SDA
     * on - включен
     * off - выключен
     */
    var SdaActive = "off"

    def isSdaActive = "on".equals(SdaActive)

  }

  var HealthShare = new HealthShareClass


  /**
   *
   */
  var Core = new Configuration {
    var RequestLaboratoryUrl = "http://localhost:8080/tmis-ws-laboratory/tmis-client-laboratory"
  }

  /**
   * Параметры сервиса управления пользователями
   */
  class UsersMgrClass extends Configuration {
    var CoreUserLogin: String = "core"
    var KeepAliveDays = 1
    var MaxConnections = 10000
  }

  var UsersMgr = new UsersMgrClass

  /**
   * Параметры модуля интегрции с подсистемой ТРФУ
   */
  class TrfuPropClass extends Configuration {
    var ServiceUrl = ""
    var Enable = "off"

    def isEnable = "on".equals(Enable)
  }

  var TrfuProp = new TrfuPropClass


  /**
   * Метод хелпер, создан из-за невозможности вызвать класс-конфиг из джава кода
   */
  def getDrugUser: String = Drugstore.User

  /**
   * Метод хелпер, создан из-за невозможности вызвать класс-конфиг из джава кода
   */
  def getDrugPassword: String = Drugstore.Password

  /**
   * Метод хелпер, создан из-за невозможности вызвать класс-конфиг из джава кода
   */
  def getDrugUrl: URL = Drugstore.ServiceUrl

  /**
   * Метод хелпер, создан из-за невозможности вызвать класс-конфиг из джава кода
   */
  def isActive: Boolean = Drugstore.Active.equals("on")


  val Laboratory = new Configuration {
    // LIS service URL  Алтей
    // null means that URL should be acquired from the WSDL file
    var ServiceUrl: URL = new URL("http://10.128.131.114:8090/CGM_SOAP")
    var User: String = null
    var Password: String = null

    var RuntimeWSDLUrl: URL = null


    // WSDL url is a:
    // - RuntimeWSDLUrl if it's defined
    // - ServiceUrl + "?wsdl" if RuntimeWSDLUrl is not defined and ServiceUrl is defined
    // - compile-time-defined otherwise
    def WSDLUrl: URL = Option(RuntimeWSDLUrl).getOrElse {
      Option(ServiceUrl).map {
        it => new URL(it.toString + "?wsdl")
      }.orNull
    }

    object CompileTime extends CompileTimeConfigManager.Laboratory

  }

  val Laboratory2 = new Configuration {
    // LIS service URL
    // null means that URL should be acquired from the WSDL file
    var ServiceUrl: URL = null
    var User: String = null
    var Password: String = null

    var RuntimeWSDLUrl: URL = null


    // NB: Across's LIS does not conform to 'url' + '?wsdl' convention
    // WSDL url is a:
    // - RuntimeWSDLUrl if it's defined
    // - compile-time-defined (local WSDL from resources) otherwise
    def WSDLUrl: URL = Option(RuntimeWSDLUrl).getOrElse(null)
  }

  /** Конфигурация для БАК лаборатории */
  val LaboratoryBak = new Configuration {
    var ServiceUrl: URL = new URL("http://10.128.131.114:8090/CGM_SOAP")
    var User: String = null
    var Password: String = null

    var RuntimeWSDLUrl: URL = null

    def WSDLUrl: URL = Option(RuntimeWSDLUrl).getOrElse(null)

    var AssignmentTemplate: String = "<ClinicalDocument xmlns='urn:hl7-org:v3' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xsi:schemaLocation='urn:hl7-org:v3 CDA.xsd'>" + "<typeId extension='POCD_HD000040' root='2.16.840.1.113883.1.3'/>" + "<id root='${uuid}'/>" + "<setID root='id'/>" + "<versionNumber orderStatus='2'/>" + "<code>${diagnosticCode}, ${diagnosticName}</code>" + "<title>${diagnosticName}</title>" + "<effectiveTime value='${orderMisDate}'/>" + "<confidentialityCode code='N' codeSystem='2.16.840.1.113883.5.25' displayName='Normal'/>" + "<recordTarget>" + "<patientRole>" + "<id root='${patientMisId}'/>" + "<addr>${patientAddress}</addr>" + "<telecom nullFlavor='NI'/>" + "<patient>" + "<id root='${uuid}' extension='${patientNumber}' assigningAuthorityName='${custodian}' displayable='true'/>" + "<name> ${patientFamily} ${patientName} ${patientPatronum}" + "<family>${patientFamily}</family>" + "<given>${patientName}</given>" + "<given>${patientPatronum}</given>" + "</name>" + "<birthTime value='${patientBirthDate}'/>" + "<administrativeGenderCode code='${patientSex}'/>" + "</patient>" + "<providerOrganization>${custodian}</providerOrganization>" + "</patientRole>" + "</recordTarget>" + "<author>" + "<time value='${orderMisDate}'/>" + "<assignedAuthor>" + "<id root='${orderDoctorMisId}' extension='${orderDoctorMisId}'/>" + "<assignedAuthor>" + "<code code='DolgCode' displayName='DolgName'/>" + "</assignedAuthor>" + "<assignedPerson>" + "<prefix>${orderDepartmentMisId}</prefix>" + "<name>${orderDoctorFamily} ${orderDoctorName} ${orderDoctorPatronum}" + "<family>${orderDoctorFamily}</family>" + "<given>${orderDoctorName}</given>" + "<given>${orderDoctorPatronum}</given>" + "</name>" + "</assignedPerson>" + "</assignedAuthor>" + "</author>" + "<custodian>" + "<assignedCustodian>" + "<representedCustodianOrganization>" + "<id root='${uuid}'/>" + "<name>${сustodian}</name>" + "</representedCustodianOrganization>" + "</assignedCustodian>" + "</custodian>" + "<componentOf>" + "<encompassingEncounter>" + "<id root='${uuid}' extension='${patientNumber}'/>" + "<effectiveTime nullFlavor='NI'/>" + "</encompassingEncounter>" + "</componentOf>" + "<component>" + "<structuredBody>" + "<component>" + "<section>${orderDiagText}" + "<entry>" + "${orderDiagCode}" + "</entry>" + "<component>" + "<section>" + "<title>срочность заказа</title>" + "<text>${isUrgent}</text>" + "</section>" + "</component>" + "<component>" + "<section>" + "<title>средний срок беременности, в неделях</title>" + "<text>${orderPregnat}</text>" + "</section>" + "</component>" + "<component>" + "<section>" + "<title>Комментарий к анализу</title>" + "<text>${orderComment}</text>" + "</section>" + "</component>" + "</section>" + "</component>" + "<component>" + "<entry>" + "<observation classCode='OBS' moodCode='ENT'/>" + "<effectiveTime value='${orderProbeDate}'/>" + "</entry>" + "<specimen>" + "<specimenRole>" + "<id root='${orderBarCode}|${TakenTissueJournal}'/>" + "<specimenPlayingEntity>" + "<code code='${orderBiomaterialCode}'>" + "<translation displayName='${orderBiomaterialName}'/>" + "</code>" + "<quantity value='${orderBiomaterialVolume}'/>" + "<text value='${orderBiomaterialComment}'/>" + "</specimenPlayingEntity>" + "</specimenRole>" + "</specimen>" + "</component>" + "<component>" + "<entry>" + "<observation classCode='OBS' moodCode='RQO' negationInd='false'/>" + "<id root='${uuid}'/>" + "<id type='${FinanceCode}' extension='${typeFinanceName}'/>" + "<code code='${diagnosticCode}' displayName='${diagnosticName}'/>" + "</entry>" + "</component>" + "<component>" + "<section> indicators" + "<entry>" + "<code code='indicatorCode1' displayName='indicatorName1'/>" + "</entry>" + "<entry>" + "<code code='indicatorCoden' displayName='indicatorNamen'/>" + "</entry>" + "</section>" + "</component>" + "</structuredBody>" + "</component>" + "</ClinicalDocument>"
  }

  /**
   * Метод хелпер, создан из-за невозможности вызвать класс-конфиг из джава кода
   */
  def getBakServiceUrl: URL = LaboratoryBak.ServiceUrl

  /**
   * Метод хелпер, создан из-за невозможности вызвать класс-конфиг из джава кода
   */
  def getBakUser: String = LaboratoryBak.User

  /**
   * Метод хелпер, создан из-за невозможности вызвать класс-конфиг из джава кода
   */
  def getBakPassword: String = LaboratoryBak.Password

  /**
   * Метод хелпер, создан из-за невозможности вызвать класс-конфиг из джава кода
   */
  def getBakRuntimeWsdl: URL = LaboratoryBak.RuntimeWSDLUrl

  /**
   * Метод хелпер, создан из-за невозможности вызвать класс-конфиг из джава кода
   */
  def getBakAssignmentTemplate: String = LaboratoryBak.AssignmentTemplate


  val TmisAuth = new Configuration {
    var RealmName = "TMIS-Core-Server"

    var Namespace = "http://korus.ru/tmis/auth"
    var NamespacePrefix = "ta"
    var Token = "tmisAuthToken"

    def QName = new QName(Namespace,
      Token,
      NamespacePrefix)

    val ErrorCodes = new {
      var LoginIncorrect = 0x01
      var RoleNotAllowed = 0x02
      var InvalidToken = 0x03
      var PermissionNotAllowed = 0x04
    }

    // Время действия токена в мс
    var AuthTokenPeriod = 60 * 60 * 1000 // 30 * 60 * 1000 = 30 мин

    var AuthDataPropertyName = "ru.korus.tmis.authData"

    // Sets not supported yet
    val SupportedPermissions = Set(
      "clientAssessmentCreate",
      "clientAssessmentRead",
      "clientAssessmentUpdate",
      "clientAssessmentDelete",

      "clientDiagnosticCreate",
      "clientDiagnosticRead",
      "clientDiagnosticUpdate",
      "clientDiagnosticDelete",

      "clientTreatmentCreate",
      "clientTreatmentRead",
      "clientTreatmentUpdate",
      "clientTreatmentDelete"
    )
  }

  val Messages = new Logging {
    val bundle = ResourceBundle.getBundle("messages",
      Utf8ResourceBundleControl.Singleton)

    def apply(msg: String, params: Any*) = {
      bundle.getString(msg) match {
        case null => "<EMPTY>"
        case result => if (params.isEmpty) result
        else try {
          result.format(params: _*)
        } catch {
          case e: Throwable => error("Could not format pattern " + msg + ". Using it as-is."); msg
        }
      }
    }
  }

  val RbCAPIds = new Logging {
    val bundle = ResourceBundle.getBundle("rbcap",
      Utf8ResourceBundleControl.Singleton)

    def apply(msg: String, params: Any*) = {
      bundle.getString(msg) match {
        case null => "<EMPTY>"
        case result => if (params.isEmpty) result
        else try {
          result.format(params: _*)
        } catch {
          case e: Throwable => error("Could not format pattern " + msg + ". Using it as-is."); msg
        }
      }
    }
  }

}

trait I18nable {
  val i18n = ConfigManager.Messages
}

trait CAPids {
  val iCapIds = ConfigManager.RbCAPIds
}
