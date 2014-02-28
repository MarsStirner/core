package ru.korus.tmis.scala.util

import java.net.URL
import java.text.SimpleDateFormat
import java.util.ResourceBundle
import javax.xml.bind.DatatypeConverter
import javax.xml.namespace.QName
import grizzled.slf4j.Logging
import ru.korus.tmis.util.reflect.Configuration
import ru.korus.tmis.util.{Utf8ResourceBundleControl, CompileTimeConfigManager}


object ConfigManager extends Configuration {

  /**
   * Формат даты по умолчанию
   */
  var DateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

  /**
   * Общие параметры работы ядра
   */
  var Common = new CommonClass

  class CommonClass extends Configuration {
    var OrgId = 3479 // индекс организации в табл Organization (по умолчанию id ФНКЦ для БД ФНКЦ)
  }

  /**
   *
   *
   * Параметры для 1С Аптека
   *
   *
   */
  var Drugstore = new DrugstoreClass

  class DrugstoreClass extends Configuration {
    /**
     * Включен ли сервис 1C Аптека (Движение пациентов, Назначения)
     * on - включен
     * off - выключен (по умолчанию)
     */
    var Active = "off"

    def isActive = "on".equals(Active)

    /**
     * URL сервиса 1C
     */
    var ServiceUrl = new URL("http://pharmacy3.fccho-moscow.ru/ws/MISExchange")
    /**
     * Login basic http auth
     */
    var User = "admin"
    /**
     * Password basic http auth
     */
    var Password = "1234"

    /**
     * Включен ли сервис Обновление данных о лекарственных средствах
     * on - включен
     * off - выключен (по умолчанию)
     */
    var UpdateRLS = "off"

    def isUpdateRLS = "on".equals(UpdateRLS)


    // Legacy area

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

    def HttpAuthToken = DatatypeConverter.printBase64Binary(
      (User + ":" + Password).getBytes)
  }

  /**
   *
   *
   * Параметры для интеграции с подсистемой HealthShare
   *
   *
   */
  var HealthShare = new HealthShareClass

  class HealthShareClass extends Configuration {
    /**
     * Включен ли сервис
     * on - включен
     * off - выключен (по умолчанию)
     */
    var Active = "off"

    /**
     * Синхронизация справочников HS
     * on - включен
     * off - выключен (по умолчанию)
     */
    var ReferenceBookActive = "off"

    def isHealthShareReferenceBook = "on".equals(HealthShare.ReferenceBookActive)

    /**
     * URL сервиса со справочниками HS
     */
    var ServiceUrl = new URL("http://37.139.9.166:57772/csp/healthshare/hsregistry/korus.NsiService.cls")
    /**
     * Login WSS auth header
     */
    var User = "demo"
    /**
     * Password WSS auth header
     */
    var Password = "demo"
    /**
     * URL сервиса по обмену информацией об пациентах
     */
    var ServiceUrlSda = new URL("http://37.139.9.166:57772/csp/healthshare/hsedgesda/isc.SDASoapService.cls")
    /**
     * Передача карточки пациента по SDA
     * on - включен
     * off - выключен
     */
    var SdaActive = "off"

    def isSdaActive = "on".equals(SdaActive)
  }

  /**
   *
   *
   * Настройки для Система-Софт (вызов отправки назначения на анализ в ЛИС)
   *
   *
   */
  var Core = new Configuration {
    /**
     * URL сервиса, который по action_id производит отправку в ЛИС
     */
    var RequestLaboratoryUrl = "http://localhost:8080/tmis-ws-laboratory/tmis-client-laboratory"
  }

  /**
   *
   *
   * Параметры сервиса управления пользователями
   *
   *
   */
  var UsersMgr = new UsersMgrClass

  class UsersMgrClass extends Configuration {
    var CoreUserLogin: String = "core"
    var KeepAliveDays = 1
    var MaxConnections = 10000
  }


  /**
   *
   *
   * Параметры модуля интеграции с подсистемой ТРФУ
   *
   *
   */
  var TrfuProp = new TrfuPropClass

  class TrfuPropClass extends Configuration {
    /**
     * Включен ли сервис
     * on - включен
     * off - выключен (по умолчанию)
     */
    var Active = "off"

    def isActive = "on".equals(Active)

    /**
     * URL сервиса
     */
    var ServiceUrl = ""
  }

  /**
   *
   *
   * Интеграция с ЛИС Алтей
   *
   *
   */

  val Laboratory = new Configuration {
    /**
     * URL сервиса Алтей
     */
    var ServiceUrl: URL = null
    /**
     * Login basic http auth
     */
    var User: String = null
    /**
     * Password basic http auth
     */
    var Password: String = null
    /**
     * URL к WSDL, когда система не может выдавать URL через http get c параметром ?wsdl
     */
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

  /**
   *
   *
   * Интеграция с ЛИС Акрос
   *
   *
   */
  val Laboratory2 = new Configuration {
    /**
     * URL сервиса Акрос
     */
    var ServiceUrl: URL = null
    /**
     * Login basic http auth
     */
    var User: String = null
    /**
     * Password basic http auth
     */
    var Password: String = null
    /**
     * URL к WSDL, когда система не может выдавать URL через http get c параметром ?wsdl
     */
    var RuntimeWSDLUrl: URL = null

    // NB: Across's LIS does not conform to 'url' + '?wsdl' convention
    // WSDL url is a:
    // - RuntimeWSDLUrl if it's defined
    // - compile-time-defined (local WSDL from resources) otherwise
    def WSDLUrl: URL = Option(RuntimeWSDLUrl).getOrElse(null)
  }

  /**
   *
   *
   * Интеграция с ЛИС CGM - БАК лаборатория
   *
   *
   **/
  var LaboratoryBak = new LaboratoryBakClass

  class LaboratoryBakClass extends Configuration {
    /**
     * URL сервиса CGM
     */
    var ServiceUrl: URL = new URL("http://10.128.131.114:8090/CGM_SOAP")
    /**
     * Login basic http auth
     */
    var User: String = null
    /**
     * Password basic http auth
     */
    var Password: String = null
    /**
     * URL к WSDL, когда система не может выдавать URL через http get c параметром ?wsdl
     */
    var RuntimeWSDLUrl: URL = null

    def WSDLUrl: URL = Option(RuntimeWSDLUrl).getOrElse(null)
  }


  /**
   *
   * Настройки thrift сервиса для обмена информацией с frontend системами: НТК, WebMIS
   *
   */
  var Prescription = new PrescriptionClass

  class PrescriptionClass extends Configuration {
    /**
     * Порт сервиса, к которому будут подключаться системы
     */
    var Port = 8383

    /**
     * Максимальное кол-во потоков, участвующее в работе
     */
    var MaxThreads = 5

  }


  /**
   * todo
   */
  val TmisAuth = new TmisAuthClass

  class TmisAuthClass extends Configuration {
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

  /**
   * todo
   */
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
    var RbStatusNotFound = 0x150
    var RbStatusForEventNotFound = 0x151
    var RbTempInvalidForEventNotFound = 0x152
    var RbResultNotFound = 0x153
    var RbDiseaseStageNotFound = 0x154
    var RbDiseaseCharacterNotFound = 0x155
  }


}

trait I18nable {
  val i18n = ConfigManager.Messages
}

trait CAPids {
  val iCapIds = ConfigManager.RbCAPIds
}
