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
  }

  val Drugstore = new Configuration {
    var OrgName = "ФНКЦ ДГОИ"

    var ServiceUrl = new URL("http://pharmacy3.fccho-moscow.ru/ws/MISExchange")
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

    var User = "admin"
    var Password = "1234"

    def HttpAuthToken = DatatypeConverter.printBase64Binary(
      (User + ":" + Password).getBytes)
  }

  val Laboratory = new Configuration {
    // LIS service URL
    // null means that URL should be acquired from the WSDL file
    var ServiceUrl: URL = null
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


    // NB: Altey's LIS does not conform to 'url' + '?wsdl' convention
    // WSDL url is a:
    // - RuntimeWSDLUrl if it's defined
    // - compile-time-defined (local WSDL from resources) otherwise
    def WSDLUrl: URL = Option(RuntimeWSDLUrl).getOrElse(null)
  }

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
