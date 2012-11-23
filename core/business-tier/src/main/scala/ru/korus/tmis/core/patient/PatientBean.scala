package ru.korus.tmis.core.patient

import ru.korus.tmis.core.auth.AuthData
import ru.korus.tmis.core.data._
import ru.korus.tmis.core.database._
import ru.korus.tmis.core.entity.model._

import grizzled.slf4j.Logging
import java.lang.{Double => JDouble}
import javax.ejb.{EJB, Stateless}
import javax.interceptor.Interceptors

import kladr.{Street, Kladr}
import scala.collection.JavaConversions._
import java.util.{LinkedList, Date}
import ru.korus.tmis.core.entity.model._
import ru.korus.tmis.util.{ConfigManager, I18nable}
import ru.korus.tmis.core.exception.CoreException
import org.codehaus.jackson.map.ObjectMapper
import javax.ejb._
import scala.util.control.Breaks._
import ru.korus.tmis.core.logging.LoggingInterceptor
import ru.korus.tmis.core.logging.slf4j.interceptor.DebugLoggingInterceptor

@Interceptors(Array(classOf[LoggingInterceptor], classOf[DebugLoggingInterceptor]))
@Stateless
class PatientBean
  extends PatientBeanLocal
  with Logging
  with I18nable {

  type CA = CommonAttribute

  @EJB
  var dbPatient: DbPatientBeanLocal = _

  @EJB
  var dbClientDocument: DbClientDocumentBeanLocal = _

  @EJB
  var dbTempInvalid: DbTempInvalidBeanLocal = _

  @EJB
  var dbClientPolicy: DbClientPolicyBeanLocal = _

  @EJB
  var dbClientContact: DbClientContactBeanLocal = _

  @EJB
  var dbClientAddress: DbClientAddressBeanLocal = _

  @EJB
  var dbClientAllergy: DbClientAllergyBeanLocal = _

  @EJB
  var dbClientRelation: DbClientRelationBeanLocal = _

  @EJB
  var dbClientIntoleranceMedicament: DbClientIntoleranceMedicamentBeanLocal = _

  @EJB
  var actionBean: DbActionBeanLocal = _

  @EJB
  var dbActionProperty: DbActionPropertyBeanLocal = _

  @EJB
  var dbActionPropertyType: DbActionPropertyTypeBeanLocal = _

  @EJB
  var customQuery: DbCustomQueryLocal = _

  @EJB
  var dbManager: DbManagerBeanLocal = _

  @EJB
  var dbSocStatus : DbClientSocStatusBeanLocal = _

  @EJB
  var dbClientWorks: DbClientWorkBeanLocal = _

  @EJB
  var dbSchemeKladrBean: DbSchemeKladrBeanLocal = _

  @EJB
  var appLock: AppLockBeanLocal = _

  @EJB
  var dbStaff: DbStaffBeanLocal = _

  @EJB
  private var dbRbCoreActionPropertyBean: DbRbCoreActionPropertyBeanLocal = _
  //////////////////////////////////////////////////////////////////////////////

  def getCurrentPatientsForDoctor(userData: AuthData) = {
    buildCommonData(customQuery.getActiveEventsForDoctor(userData.doctor.id))
  }

  def getCurrentPatientsForDepartment(userData: AuthData) = {
    import ru.korus.tmis.util.General.nullity_implicits

    val id = userData.user.getOrgStructure ?!! { _.getId.intValue } getOrElse {
      throw new CoreException(i18n("error.user.noOrgStructureFound"))
    }

    buildCommonData(customQuery.getActiveEventsForDepartment(id))
  }

  def buildCommonData(events: java.util.List[Event]) = {
    val admissions = customQuery.getAdmissionsByEvents(events)
    val hospitalBeds = customQuery.getHospitalBedsByEvents(events)
    val anamneses = customQuery.getAnamnesesByEvents(events)
    val allergoAnamneses = customQuery.getAllergoAnamnesesByEvents(events)
    val diagnoses = customQuery.getDiagnosesByEvents(events)

    events.foldLeft(new CommonData)((data, event) => {
      val entity = new CommonEntity(event.getId,
                                    event.getVersion,
                                    event.getEventType.getName,
                                    "Event",
                                    event.getEventType.getId,
                                    null,
                                    null)
      val group = new CommonGroup

      group add new CA(event.getId,
                       event.getVersion.intValue,
                       "patientCaseHistoryId",
                       ConfigManager.Types.Integer,
                       null,
                       event.getExternalId.toString)


      val patient = event.getPatient

//      // Вычисление площади поверхности тела
//      var height: JDouble = null
//      var weight: JDouble = null
//      try {
//        height = JDouble.valueOf(patient.getHeight)
//        weight = JDouble.valueOf(patient.getWeight)
//      } catch {
//        case e: NumberFormatException => {}
//      }
      //      val BSA = if (height != null && weight != null) {
      //        scala.math.sqrt(height.doubleValue() * weight.doubleValue() / 3600.0).toString
      //      } else {
      //        ""
      //      }

      val height = Option(customQuery.getHeightForPatient(patient))
      val weight = Option(customQuery.getWeightForPatient(patient))

      val BSA = for (h <- height; w <- weight)
                yield scala.math.sqrt(h.doubleValue() * w.doubleValue() / 3600.0).toString

      val SBSA = BSA.getOrElse("")
      val sheight = height.map{_.toString}.getOrElse(patient.getHeight.toString)
      val sweight = weight.map{_.toString}.getOrElse(patient.getWeight.toString)

      info("height=" + sheight)
      info("weight=" + sweight)
      info("BSA=" + SBSA)

      group add new CA(patient.getId,
                       0,
                       "patientId",
                       ConfigManager.Types.Integer,
                       null,
                       patient.getId.toString) ::
        new CA(patient.getId,
               0,
               "patientLastName",
               ConfigManager.Types.String,
               null,
               patient.getLastName) ::
        new CA(patient.getId,
               0,
               "patientFirstName",
               ConfigManager.Types.String,
               null,
               patient.getFirstName) ::
        new CA(patient.getId,
               0,
               "patientMiddleName",
               ConfigManager.Types.String,
               null,
               patient.getPatrName) ::
        new CA(patient.getId,
               0,
               "patientBirthDate",
               ConfigManager.Types.Datetime,
               null,
               patient.getBirthDate) ::
        new CA(patient.getId,
               0,
               "patientHeight",
               ConfigManager.Types.String,
               null,
               Map("value" -> sheight, "unit" -> i18n("patient.height.unit"))) ::
        new CA(patient.getId,
               0,
               "patientWeight",
               ConfigManager.Types.String,
               null,
               Map("value" -> sweight, "unit" -> i18n("patient.weight.unit"))) ::
        new CA(patient.getId,
               0,
               "patientBSA",
               ConfigManager.Types.String,
               null,
               Map("value" -> SBSA, "unit" -> i18n("patient.BSA.unit"))) ::
        new CA(patient.getId,
               0,
               "patientSex",
               ConfigManager.Types.String,
               null,
               patient.getSex.toString) ::
        new CA(patient.getId,
               0,
               "patientBloodType",
               ConfigManager.Types.String,
               null,
               patient.getBloodType.getName) ::
        Nil

      val doctor = event.getExecutor
      group add new CA(doctor.getId,
                       0,
                       "doctorId",
                       ConfigManager.Types.Integer,
                       null,
                       doctor.getId.toString) ::
        new CA(doctor.getId,
               0,
               "doctorLastName",
               ConfigManager.Types.String,
               null,
               doctor.getLastName) ::
        new CA(doctor.getId,
               0,
               "doctorFirstName",
               ConfigManager.Types.String,
               null,
               doctor.getFirstName) ::
        new CA(doctor.getId,
               0,
               "doctorMiddleName",
               ConfigManager.Types.String,
               null,
               doctor.getPatrName) ::
        new CA(doctor.getId,
               0,
               "doctorSpeciality",
               ConfigManager.Types.String,
               null,
               doctor.getSpeciality.getName) ::
        Nil

      admissions.get(event) match {
        case null => {}
        case admission: Action => {
          group add new CA(admission.getId,
                           admission.getVersion.intValue,
                           "patientAdmissionDate",
                           ConfigManager.Types.Datetime,
                           null,
                           admission.getCreateDatetime)
        }
      }

      hospitalBeds.get(event) match {
        case null => {}
        case hospitalBed: ActionProperty => {
          val apvs = dbActionProperty.getActionPropertyValue(hospitalBed)
          apvs.foreach(
            (apv) => {
              if (apv.getValue.isInstanceOf[OrgStructureHospitalBed]) {
                val bed = apv.getValue.asInstanceOf[OrgStructureHospitalBed]
                group add new CA(bed.getId,
                                 hospitalBed.getVersion.intValue,
                                 "patientCurrentBed",
                                 ConfigManager.Types.String,
                                 null,
                                 bed.getName)
                group add new CA(bed.getId,
                                 hospitalBed.getVersion.intValue,
                                 "patientCurrentDepartment",
                                 ConfigManager.Types.String,
                                 null,
                                 bed.getMasterDepartment.getName)
              }
            })
        }
      }

      anamneses.get(event) match {
        case null => {}
        case anamnesis: ActionProperty => {
          val apvs = dbActionProperty.getActionPropertyValue(anamnesis)
          apvs.foreach(
            (apv) => {
              group add new CA(anamnesis.getId,
                               anamnesis.getVersion.intValue,
                               "patientAnamnesis",
                               ConfigManager.Types.String,
                               null,
                               apv.getValueAsString)
            })
        }
      }

      allergoAnamneses.get(event) match {
        case null => {}
        case allergoAnamnesis: ActionProperty => {
          val apvs = dbActionProperty.getActionPropertyValue(allergoAnamnesis)
          apvs.foreach(
            (apv) => {
              group add new CA(allergoAnamnesis.getId,
                               allergoAnamnesis.getVersion.intValue,
                               "patientAllergoanamnesis",
                               ConfigManager.Types.String,
                               null,
                               apv.getValueAsString)
            })
        }
      }

      diagnoses.get(event) match {
        case null => {}
        case diagnosis: ActionProperty => {
          val apvs = dbActionProperty.getActionPropertyValue(diagnosis)
          apvs.foreach(
            (apv) => {
              group add new CA(diagnosis.getId,
                               diagnosis.getVersion.intValue,
                               "patientDiagnosis",
                               ConfigManager.Types.String,
                               null,
                               apv.getValueAsString)
            })
        }
      }

      data add (entity add group)
    })
  }

  def getAllPatientsForDepartmentIdAndDoctorIdByPeriod(requestData: PatientsListRequestData, role: Int, authData: AuthData) = {

    val mapper: ObjectMapper = new ObjectMapper()

    requestData.setRecordsCount(customQuery.getCountActiveEventsForDepartmentAndDoctor(requestData.filter))
    val events = customQuery.getActiveEventsForDepartmentAndDoctor( requestData.page-1,
                                                                    requestData.limit,
                                                                    requestData.sortingFieldInternal,
                                                                    requestData.sortingMethod,
                                                                    requestData.filter)
    //дозаполним структуру данными о палате\койке
    /*val hospitalBeds = customQuery.getHospitalBedsByEvents(events)    //проверить что каунт эвентов до и после равны
    val hospitalInfo: Map[Event, OrgStructureHospitalBed]  =
    hospitalBeds.foldLeft(Map.empty[Event, OrgStructureHospitalBed])(
    (key, element) => {
      var (event,  ap) = element
      var value: OrgStructureHospitalBed =
      ap match {
        case null => {null}
        case bed: ActionProperty => {
          var value_x: OrgStructureHospitalBed = null
          val apvs = dbActionProperty.getActionPropertyValue(bed)
          apvs.foreach(
            (apv)=> {
              if (apv.getValue.isInstanceOf[OrgStructureHospitalBed]) {
                value_x  = apv.getValue.asInstanceOf[OrgStructureHospitalBed]
              }
            }
          )
          value_x
        }
      }
      key + (event -> value)
    })*/
    var conditionsInfo = new java.util.HashMap[Event, java.util.Map[ActionProperty, java.util.List[APValue]]]
    if(role == 1) {  //Для сестры отделения только
      val conditions = customQuery.getLastAssessmentByEvents(events)
      conditions.foreach(
        c => {
          val apList = dbActionProperty.getActionPropertiesByActionIdAndTypeNames(c._2.getId.intValue,List("Состояние", "ЧСС", "АД нижн.","АД верхн."))
          conditionsInfo.put(c._1, apList)
        }
      )
      mapper.getSerializationConfig().withView(classOf[PatientsListDataViews.NurseView])
    }
    else mapper.getSerializationConfig().withView(classOf[PatientsListDataViews.AttendingDoctorView])

    mapper.writeValueAsString(new PatientsListData(events,
                                                   requestData,
                                                   role,
                                                   conditionsInfo,
                                                   actionBean.getLastActionByActionTypeIdAndEventId _,
                                                   dbActionProperty.getActionPropertiesByActionIdAndRbCoreActionPropertyIds _,
                                                   dbRbCoreActionPropertyBean.getRbCoreActionPropertiesByIds _))
  }

  def getPatientById(id: Int) = {
    dbPatient.getPatientById(id)
  }

  def getAllPatients(requestData: PatientRequestData) = {
    //
    //  TODO: разобрать контейнер перед вызовом:
    //
    //  patientCode — Код пациента
    //  document    — Фильтр по любому документу
    //  fullName    — ФИО
    //  birthDate   — Дата рождения (кстати, как передавать будем?)

    // Если заполнен документ или код - ищем только по одному из них (по коду, думаю.
    // то есть по всей видимости есть приоритеты:

    val limit = requestData.limit match {
      case null => {0}
      case _ => {requestData.limit.toInt}
    }

    val page = requestData.page match {
      case null => {0}
      case _ => {requestData.page.toInt-1} //c клиента приходит номер страницы начиная с 1
    }

    val sortField = requestData.sortingField match {
      case null => {"id"}
      case "middleName" => {"patrName"}
      case "patientCode"=> {"id"}         //TODO: в бд нет поля "код пациента"
      case _ => {requestData.sortingField}
    }

    val sortMethod = requestData.sortingMethod match {
      case null => {"asc"}
      case _ => {requestData.sortingMethod}
    }

    if (requestData.filter.patientCode != null)
    {
      dbPatient.getPatientsWithCode(
        limit, page, sortField, sortMethod,
        requestData.filter.patientCode.toInt,
        requestData)
    }
    else if (requestData.filter.document != null)
    {
      dbPatient.getPatientsWithDocumentPattern(
        limit, page, sortField, sortMethod,
        requestData.filter.document,
        requestData)
    }
    else if (requestData.filter.fullName != null && requestData.filter.birthDate == null)
    {
      dbPatient.getPatientsWithFullNamePattern(
        limit, page, sortField, sortMethod,
        requestData.filter.fullName,
        requestData)
    }
    else if (requestData.filter.fullName == null && requestData.filter.birthDate != null)
    {
      dbPatient.getPatientsWithBirthDate(
        limit, page, sortField, sortMethod,
        requestData.filter.birthDate,
        requestData)
    }
    else if (requestData.filter.fullName != null && requestData.filter.birthDate != null)
    {
      dbPatient.getPatientsWithBirthDateAndFullNamePattern(
        limit, page, sortField, sortMethod,
        requestData.filter.birthDate,
        requestData.filter.fullName,
        requestData)
    }
    else
    {
      dbPatient.getAllPatients(
        limit, page, sortField, sortMethod,
        requestData
      )
    }
  }

  def savePatient(patientEntry: PatientEntry, userData: AuthData) : PatientEntry = {
    val usver = dbStaff.getStaffById(userData.doctor.id)
    var lockId: Int = -1
    var oldPatient : Patient = null
    var patientVersion : Int = 0
    if (patientEntry.getId() > 0) {
      patientVersion = patientEntry.getVersion()
      oldPatient = Patient.clone(dbPatient.getPatientById(patientEntry.getId()))
      lockId = appLock.acquireLock("Client", oldPatient.getId.intValue(), oldPatient.getId.intValue(), userData)//oldAction.getIdx
    }
    var patient : Patient = null
    try {
      //create or update patient data
      val medInfo: MedicalInfoContainer = patientEntry.getMedicalInfo()
      var bloodDate: Date = new Date()
      var bloodType: Int = 0

      medInfo match {
        case null => {}
        case _ => {
          medInfo.getBlood() match {
            case null => {}
            case blood: BloodInfoContainer => {
              bloodDate = blood.getCheckingDate()
              bloodType = blood.getId()
            }
          }
        }
      }

      patient = dbPatient.insertOrUpdatePatient(
        patientEntry.getId(),
        patientEntry.getName().getFirst(),
        patientEntry.getName().getMiddle(),
        patientEntry.getName().getLast(),
        patientEntry.getBirthDate(),
        patientEntry.getBirthPlace(),
        patientEntry.getSex(),
        "",   //weight, required
        "",   //height, required
        patientEntry.getSnils(),
        bloodDate, //bloodDate
        bloodType, //blood type
        "",   //bloodnotes required
        "",   //notes, required
        usver,
        patientVersion
      )

      //create or update document data
      var docStartDate = new Date();
      var docEndDate = new Date();
      var set = Set.empty[Int]
      val clientIdCards = patientEntry.getIdCards()
      val serverIdCards = patient.getActiveClientDocuments()
      serverIdCards.foreach(
        (serverIdCard)  => {
          val result = clientIdCards.find {element => element.getId() == serverIdCard.getId().intValue()}
          val clientIdCard = result.getOrElse(null)
          if (clientIdCard != null) {
            set = set + clientIdCard.getId()
            if (clientIdCard.getRangeDocumentDate() != null) {
              docStartDate = clientIdCard.getRangeDocumentDate().getStart()
              docEndDate = clientIdCard.getRangeDocumentDate().getEnd()
            }
            var tempServerIdCards = serverIdCard
            var docTypeId = clientIdCard.getDocType().getId()
            if (docStartDate != null && docTypeId > 0) {
              tempServerIdCards = dbClientDocument.insertOrUpdateClientDocument(
                clientIdCard.getId(),
                docTypeId,
                clientIdCard.getIssued(),
                clientIdCard.getNumber(),
                clientIdCard.getSeries(),
                docStartDate,
                docEndDate,
                patient,
                usver
              )
            }
          } else {
            dbClientDocument.deleteClientDocument(serverIdCard.getId().intValue(), usver)
          }
        }
      )
      clientIdCards.foreach((clientIdCard) => {
        var j = 0
        breakable{
          set.foreach(i => {
            if (i == clientIdCard.getId()) {
              j = j+1
              break
            }
          })
        }
        if (j == 0) {
          var docTypeId = clientIdCard.getDocType().getId()
          if (clientIdCard.getRangeDocumentDate() != null) {
            docStartDate = clientIdCard.getRangeDocumentDate().getStart()
            docEndDate = clientIdCard.getRangeDocumentDate().getEnd()
          }
          if (docStartDate != null && docTypeId > 0) {
            dbClientDocument.insertOrUpdateClientDocument(
              clientIdCard.getId(),
              docTypeId,
              clientIdCard.getIssued(),
              clientIdCard.getNumber(),
              clientIdCard.getSeries(),
              docStartDate,
              docEndDate,
              patient,
              usver
            )
          }
        }
      })
      //

      //create or update insurance policy data
      set = Set.empty[Int]
      val clientPolicies = patientEntry.getPayments()
      val serverPolicies = patient.getActiveClientPolicies()
      serverPolicies.foreach(
        (serverPolicy) => {
          val result = clientPolicies.find {element => element.getId() == serverPolicy.getId().intValue()}
          val clientPolicy = result.getOrElse(null)
          if (clientPolicy != null) {
            set = set + clientPolicy.getId()
            var policyTypeId = clientPolicy.getPolicyType() match {
              case null => {0}
              case policyType: IdNameContainer=> {policyType.getId()}
            }
            var policyDate = clientPolicy.getRangePolicyDate()
            if (policyTypeId > 0 && policyDate != null && policyDate.getStart() != null) {
              dbClientPolicy.insertOrUpdateClientPolicy(
                clientPolicy.getId(),
                policyTypeId,
                clientPolicy.getSmo() match {
                  case null => {0}
                  case smo: IdNameContainer => {smo.getId()}
                },
                clientPolicy.getNumber(),
                clientPolicy.getSeries(),
                policyDate.getStart(),
                policyDate.getEnd(),
                "",//name
                clientPolicy.getComment(),//note
                patient,
                usver
              )
            }
          } else {
            dbClientPolicy.deleteClientPolicy(serverPolicy.getId().intValue(), null)
          }
        }
      )
      clientPolicies.foreach((clientPolicy) => {
        var j = 0
        breakable{
          set.foreach(i => {
            if (i == clientPolicy.getId()) {
              j = j+1
              break
            }
          })
        }
        if (j == 0) {
          var policyTypeId = clientPolicy.getPolicyType() match {
            case null => {0}
            case policyType: IdNameContainer=> {policyType.getId()}
          }
          var policyDate = clientPolicy.getRangePolicyDate()
          if (policyTypeId > 0 && policyDate != null && policyDate.getStart() != null) {
            dbClientPolicy.insertOrUpdateClientPolicy(
              clientPolicy.getId(),
              policyTypeId,
              clientPolicy.getSmo() match {
                case null => {0}
                case smo: IdNameContainer => {smo.getId()}
              },
              clientPolicy.getNumber(),
              clientPolicy.getSeries(),
              policyDate.getStart(),
              policyDate.getEnd(),
              "",//name
              clientPolicy.getComment(),//note
              patient,
              usver
            )
          }
        }
      })
      //

      //create or update Contact data
      set = Set.empty[Int]
      val clientContacts = patientEntry.getPhones()
      val patientContacts = patient.getActiveClientContacts()
      patientContacts.foreach(
        (serverContact) => {
          val result = clientContacts.find {element => element.getId() == serverContact.getId().intValue()}
          val clientContact = result.getOrElse(null)
          if (clientContact != null) {
            set = set + clientContact.getId()
            var tempServContact = serverContact
            tempServContact = dbClientContact.insertOrUpdateClientContact(
              clientContact.getId(),
              clientContact.getTypeId(),
              clientContact.getNumber(),
              clientContact.getComment(),
              patient,
              usver
            )
          } else {
            dbClientContact.deleteClientContact(serverContact.getId().intValue(), usver)
          }
        }
      )
      clientContacts.foreach((clientContact) => {
        var j = 0
        breakable{
          set.foreach(i => {
            if (i == clientContact.getId()) {
              j = j+1
              break
            }
          })
        }
        if (j == 0) {
          dbClientContact.insertOrUpdateClientContact(
            clientContact.getId(),
            clientContact.getTypeId(),
            clientContact.getNumber(),
            clientContact.getComment(),
            patient,
            usver
          )
        }
      })
      //

      //create or update Address data
      val clientAddresses = patientEntry.getAddress()
      val serverAddresses = patient.getActiveClientAddresses()
      var result = serverAddresses.find {element => element.getAddressType == 0}
      var registeredAddres = result.getOrElse(null)
      result = serverAddresses.find {element => element.getAddressType == 1}
      var residentialAddres = result.getOrElse(null)
      clientAddresses.getRegistered() match {
        case null => {
          if (registeredAddres != null) {
            dbClientAddress.deleteClientAddress(registeredAddres.getId().intValue(), usver)
          }
        }
        case addressEntry: AddressEntryContainer => {
          registeredAddres = dbClientAddress.insertOrUpdateClientAddress(
            0,//TODO: вынести в настройки идентификатор типа адреса
            addressEntry,
            patient,
            usver
          )
        }
      }
      clientAddresses.getResidential() match {
        case null => {
          if (residentialAddres != null) {
            dbClientAddress.deleteClientAddress(residentialAddres.getId().intValue(), usver)
          }
        }
        case addressEntry: AddressEntryContainer => {
          residentialAddres = dbClientAddress.insertOrUpdateClientAddress(
            1,//TODO: вынести в настройки идентификатор типа адреса
            addressEntry,
            patient,
            usver
          )
        }
      }
      //

      //create or update ClientAllergy data
      set = Set.empty[Int]
      var clientAllergies : LinkedList[AllergyInfoContainer] = null
      if (medInfo != null) {
        clientAllergies = medInfo.getAllergies()
      }
      val serverAllergies = patient.getActiveClientAllergies()
      serverAllergies.foreach(
        (serverAllergy) => {
          val result = clientAllergies.find {element => element.getId() == serverAllergy.getId().intValue()}
          val clientAllergy = result.getOrElse(null)
          if (clientAllergy != null) {
            set = set + clientAllergy.getId().intValue()
            var tempServerAllergy = serverAllergy
            tempServerAllergy = dbClientAllergy.insertOrUpdateClientAllergy(
              clientAllergy.getId().intValue(),
              clientAllergy.getDegree(),
              clientAllergy.getSubstance(),
              clientAllergy.getCheckingDate(),
              clientAllergy.getComment(),
              patient,
              usver
            )
          } else {
            dbClientAllergy.deleteClientAllergy(serverAllergy.getId().intValue(), usver)
          }

        }
      )
      clientAllergies.foreach((clientAllergy) => {
        var j = 0
        breakable{
          set.foreach(i => {
            if (i == clientAllergy.getId()) {
              j = j+1
              break
            }
          })
        }
        if (j == 0) {
          dbClientAllergy.insertOrUpdateClientAllergy(
            clientAllergy.getId(),
            clientAllergy.getDegree(),
            clientAllergy.getSubstance(),
            clientAllergy.getCheckingDate(),
            clientAllergy.getComment(),
            patient,
            usver
          )
        }
      })
      //

      //Непереносимости
      set = Set.empty[Int]
      var clientDrugIntolerances : LinkedList[AllergyInfoContainer] = null
      if (medInfo != null) {
        clientDrugIntolerances = medInfo.getDrugIntolerances()
      }
      val patientIntolerances = patient.getActiveClientIntoleranceMedicaments()
      patientIntolerances.foreach(
        (serverIntolerance) => {
          val result = clientDrugIntolerances.find {element => element.getId() == serverIntolerance.getId().intValue()}
          val clientDrugIntolerance = result.getOrElse(null)
          if (clientDrugIntolerance != null) {
            set = set + clientDrugIntolerance.getId().intValue()
            var tempServerIntolerance = serverIntolerance
            tempServerIntolerance = dbClientIntoleranceMedicament.insertOrUpdateClientIntoleranceMedicament(
              clientDrugIntolerance.getId(),
              clientDrugIntolerance.getDegree(),
              clientDrugIntolerance.getSubstance(),
              clientDrugIntolerance.getCheckingDate(),
              clientDrugIntolerance.getComment(),
              patient,
              usver
            )
          } else {
            dbClientIntoleranceMedicament.deleteClientIntoleranceMedicament(serverIntolerance.getId().intValue(), usver)
          }
        }
      )
      clientDrugIntolerances.foreach((clientDrugIntolerance) => {
        var j = 0
        breakable{
          set.foreach(i => {
            if (i == clientDrugIntolerance.getId()) {
              j = j+1
              break
            }
          })
        }
        if (j == 0) {
          dbClientIntoleranceMedicament.insertOrUpdateClientIntoleranceMedicament(
            clientDrugIntolerance.getId(),
            clientDrugIntolerance.getDegree(),
            clientDrugIntolerance.getSubstance(),
            clientDrugIntolerance.getCheckingDate(),
            clientDrugIntolerance.getComment(),
            patient,
            usver
          )
        }
      })
      //

      //create or update ClientRelation data
      set = Set.empty[Int]
      val clientRelations = patientEntry.getRelations()
      val serverRelations = patient.getActiveClientRelatives()
      serverRelations.foreach(
        (serverRelation) => {
          val result = clientRelations.find {element => element.getId() == serverRelation.getId().intValue()}
          val clientRelation = result.getOrElse(null)
          if (clientRelation != null) {
            set = set + clientRelation.getId().intValue()
            var tempServerRelation = serverRelation
            tempServerRelation = dbClientRelation.insertOrUpdateClientRelation(   //внутри себя будет работать с персонами и их контактами
              clientRelation.getId(),
              clientRelation.getRelationType().getId(),
              clientRelation.getName().getFirst(),
              clientRelation.getName().getLast(),
              clientRelation.getName().getMiddle(),
              clientRelation.getPhones(),
              patient,
              usver
            )
          } else {
            dbClientRelation.deleteClientRelation(serverRelation.getId().intValue(), usver)
          }
        }
      )
      clientRelations.foreach((clientRelation) => {
        var j = 0
        breakable{
          set.foreach(i => {
            if (i == clientRelation.getId()) {
              j = j+1
              break
            }
          })
        }
        if (j == 0) {
          if (clientRelation.getRelationType() != null && clientRelation.getRelationType().getId() > 0) {
            dbClientRelation.insertOrUpdateClientRelation(   //внутри себя будет работать с персонами и их контактами
              clientRelation.getId(),
              clientRelation.getRelationType().getId(),
              clientRelation.getName().getFirst(),
              clientRelation.getName().getLast(),
              clientRelation.getName().getMiddle(),
              clientRelation.getPhones(),
              patient,
              usver
            )
          }
        }
      })
      //

      //SocStatuses
      var disabSet = Set.empty[Int]
      var occupSet = Set.empty[Int]
      var clientOccupations = patientEntry.getOccupations();
      var clientCitizenships = patientEntry.getCitizenship();
      var clientDisabilities = patientEntry.getDisabilities()
      var serverSocScatuses = patient.getActiveClientSocStatuses();
      serverSocScatuses.foreach(serverStatus => {
        serverStatus.getSocStatusClass.getId.intValue() match {
          case 32 => { //Инвалидности
            val result = clientDisabilities.find {element => element.getId() == serverStatus.getId().intValue()}
            val clientDisability = result.getOrElse(null)
            if (clientDisability != null) {
              var socStatusTypeId = clientDisability.getDisabilityType() match {
                case null => {0}
                case socialStatus => {socialStatus.getId()}
              }
              if (socStatusTypeId > 0) {
                disabSet = disabSet + clientDisability.getId().intValue()
                var tempServerStatus = serverStatus
                tempServerStatus = dbSocStatus.insertOrUpdateClientSocStatus(
                  clientDisability.getId(),
                  32,
                  socStatusTypeId,
                  clientDisability.getDocument(),//if (clientDisability.getDocument() != null) {clientDisability.getDocument().getId()} else {0},
                  clientDisability.getRangeDisabilityDate().getStart(),
                  clientDisability.getRangeDisabilityDate().getEnd(),
                  patient,
                  if (clientDisability.getBenefitsCategory() != null) {clientDisability.getBenefitsCategory().getId()} else {0},
                  clientDisability.getComment(),
                  usver
                )
              } else {
                dbSocStatus.deleteClientSocStatus(serverStatus.getId().intValue(), usver)
              }
            }
          }
          case 33 => {}
          case 34 => {//Занятость
          val result = clientOccupations.find {element => element.getId() == serverStatus.getId().intValue()}
            val clientOccupation = result.getOrElse(null)
            if (clientOccupation != null) {
              var socStatusTypeId = clientOccupation.getSocialStatus() match {
                case null => {0}
                case socialStatus => {socialStatus.getId()}
              }
              if (socStatusTypeId>0) {
                occupSet = occupSet + clientOccupation.getId().intValue()
                var tempServerStatus = serverStatus
                tempServerStatus = dbSocStatus.insertOrUpdateClientSocStatus(
                  clientOccupation.getId(),
                  34,
                  socStatusTypeId,
                  null,
                  new Date,
                  null,
                  patient,
                  0,
                  clientOccupation.getComment(),
                  usver
                )
              }
              //работа
              var workSet = Set.empty[Int]
              var clientWorks = clientOccupation.getWorks()
              var serverWorks = patient.getActiveClientWorks();
              serverWorks.foreach(serverWork => {
                //занятости
                val result = clientWorks.find {element => element.getId() == serverWork.getId().intValue()}
                val clientWork = result.getOrElse(null)
                if (clientWork != null) {
                  workSet = workSet + clientWork.getId().intValue()
                  var tempServerWork = serverWork
                  tempServerWork = dbClientWorks.insertOrUpdateClientWork(
                    clientWork.getId(),
                    patient,
                    if(clientWork.getMilitaryUnit()!=null && !clientWork.getMilitaryUnit.isEmpty)
                    {
                      clientWork.getMilitaryUnit
                    }else if(clientWork.getWorkingPlace()!=null && !clientWork.getWorkingPlace.isEmpty)
                    {
                      clientWork.getWorkingPlace
                    }else if (clientWork.getSchoolNumber()!=null && !clientWork.getSchoolNumber().isEmpty) {
                      clientWork.getSchoolNumber()
                    } else if (clientWork.getPreschoolNumber()!=null && !clientWork.getPreschoolNumber().isEmpty) {
                      clientWork.getPreschoolNumber()
                    } else {""},
                    if (clientWork.getClassNumber()!=null && !clientWork.getClassNumber().isEmpty) {
                      clientWork.getClassNumber()
                    } else {
                      clientWork.getPosition()
                    },
                    if (clientWork.getRank() != null) {clientWork.getRank().getId()} else {0},
                    if (clientWork.getForceBranch() != null) {clientWork.getForceBranch().getId()} else {0},
                    usver
                  )
                } else {
                  dbClientWorks.deleteClientWork(serverWork.getId().intValue(), usver)
                }
              })
              clientWorks.foreach((clientWork) => {
                var j = 0
                breakable{
                  workSet.foreach(i => {
                    if (i == clientWork.getId()) {
                      j = j+1
                      break
                    }
                  })
                }
                if (j == 0) {
                  dbClientWorks.insertOrUpdateClientWork(
                    clientWork.getId(),
                    patient,
                    if(clientWork.getMilitaryUnit()!=null && !clientWork.getMilitaryUnit.isEmpty)
                    {
                      clientWork.getMilitaryUnit
                    }else if(clientWork.getWorkingPlace()!=null && !clientWork.getWorkingPlace.isEmpty)
                    {
                      clientWork.getWorkingPlace
                    }else if (clientWork.getSchoolNumber()!=null && !clientWork.getSchoolNumber().isEmpty) {
                      clientWork.getSchoolNumber()
                    } else if (clientWork.getPreschoolNumber()!=null && !clientWork.getPreschoolNumber().isEmpty) {
                      clientWork.getPreschoolNumber()
                    } else {""},
                    if (clientWork.getClassNumber()!=null && !clientWork.getClassNumber().isEmpty) {
                      clientWork.getClassNumber()
                    } else {
                      clientWork.getPosition()
                    },
                    if (clientWork.getRank() != null) {clientWork.getRank().getId()} else {0},
                    if (clientWork.getForceBranch() != null) {clientWork.getForceBranch().getId()} else {0},
                    usver
                  )
                }
              })
            } else {
              dbSocStatus.deleteClientSocStatus(serverStatus.getId().intValue(), usver)
              var serverWorks = patient.getActiveClientWorks();
              serverWorks.foreach(serverWork => {
                dbClientWorks.deleteClientWork(serverWork.getId().intValue(), usver)
              })
            }
          }
          case 35 => {//Гражданства
            if (clientCitizenships != null) {
              if (clientCitizenships.getFirst() != null && clientCitizenships.getFirst().getId() == serverStatus.getId().intValue()) {
                var tempServerStatus = serverStatus
                tempServerStatus = dbSocStatus.insertOrUpdateClientSocStatus(
                  clientCitizenships.getFirst().getId(),
                  35, //гражданство
                  clientCitizenships.getFirst().getSocStatusType().getId(),  //первое
                  null,
                  new Date,
                  null,
                  patient,
                  0,
                  clientCitizenships.getFirst().getComment(),
                  usver
                )
              }
              else if (clientCitizenships.getSecond() != null && clientCitizenships.getSecond().getId() == serverStatus.getId().intValue()) {
                var tempServerStatus = serverStatus
                tempServerStatus = dbSocStatus.insertOrUpdateClientSocStatus(
                  clientCitizenships.getSecond().getId(),
                  35, //гражданство
                  clientCitizenships.getSecond().getSocStatusType().getId(),  //второе
                  null,
                  new Date,
                  null,
                  patient,
                  0,
                  clientCitizenships.getSecond().getComment(),
                  usver
                )
              } else {
                dbSocStatus.deleteClientSocStatus(serverStatus.getId().intValue(), usver)
              }
            }
          }
        }
      })
      //Создание новых
      if (clientCitizenships.getFirst() != null && clientCitizenships.getFirst().getId() < 1 &&
          clientCitizenships.getFirst().getSocStatusType() != null && clientCitizenships.getFirst().getSocStatusType().getId() > 0) {
        dbSocStatus.insertOrUpdateClientSocStatus(
          clientCitizenships.getFirst().getId(),
          35, //гражданство
          clientCitizenships.getFirst().getSocStatusType().getId(),  //первое
          null,
          new Date,
          null,
          patient,
          0,
          clientCitizenships.getFirst().getComment(),
          usver
        )
      }
      if (clientCitizenships.getSecond() != null && clientCitizenships.getSecond().getId() < 1 &&
          clientCitizenships.getSecond().getSocStatusType() != null && clientCitizenships.getSecond().getSocStatusType().getId() > 0) {
        dbSocStatus.insertOrUpdateClientSocStatus(
          clientCitizenships.getSecond().getId(),
          35, //гражданство
          clientCitizenships.getSecond().getSocStatusType().getId(),  //первое
          null,
          new Date,
          null,
          patient,
          0,
          clientCitizenships.getSecond().getComment(),
          usver
        )
      }
      clientDisabilities.foreach((clientDisability) => {
        var j = 0
        breakable{
          disabSet.foreach(i => {
            if (i == clientDisability.getId()) {
              j = j+1
              break
            }
          })
        }
        if (j == 0) {
          var socStatusTypeId = clientDisability.getDisabilityType() match {
            case null => {0}
            case socialStatus => {socialStatus.getId()}
          }
          if (socStatusTypeId>0) {
            dbSocStatus.insertOrUpdateClientSocStatus(
              clientDisability.getId(),
              32,
              socStatusTypeId,
              clientDisability.getDocument(),
              clientDisability.getRangeDisabilityDate().getStart(),
              clientDisability.getRangeDisabilityDate().getEnd(),
              patient,
              if (clientDisability.getBenefitsCategory() != null) {clientDisability.getBenefitsCategory().getId()} else {0},
              clientDisability.getComment(),
              usver
            )
          }
        }
      })
      clientOccupations.foreach((clientOccupation) => {
        var j = 0
        breakable{
          occupSet.foreach(i => {
            if (i == clientOccupation.getId()) {
              j = j+1
              break
            }
          })
        }
        if (j == 0) {
          var socStatusTypeId = clientOccupation.getSocialStatus() match {
            case null => {0}
            case socialStatus => {socialStatus.getId()}
          }
          if (socStatusTypeId>0) {
            dbSocStatus.insertOrUpdateClientSocStatus(
              clientOccupation.getId(),
              34,
              socStatusTypeId,
              null,
              new Date,
              null,
              patient,
              0,
              clientOccupation.getComment(),
              usver
            )
          }
          //работа
          var clientWorks = clientOccupation.getWorks()
          clientWorks.foreach(clientWork => {
            if (clientWork != null) {
              dbClientWorks.insertOrUpdateClientWork(
                clientWork.getId().intValue(),
                patient,
                if(clientWork.getMilitaryUnit()!=null && !clientWork.getMilitaryUnit.isEmpty) {
                  clientWork.getMilitaryUnit
                } else if(clientWork.getWorkingPlace()!=null && !clientWork.getWorkingPlace.isEmpty) {
                  clientWork.getWorkingPlace
                } else if (clientWork.getSchoolNumber()!=null && !clientWork.getSchoolNumber().isEmpty) {
                  clientWork.getSchoolNumber()
                } else if (clientWork.getPreschoolNumber()!=null && !clientWork.getPreschoolNumber().isEmpty) {
                  clientWork.getPreschoolNumber()
                } else {""},
                if (clientWork.getClassNumber()!=null && !clientWork.getClassNumber().isEmpty) {
                  clientWork.getClassNumber()
                } else {
                  clientWork.getPosition()
                },
                if (clientWork.getRank() != null) {clientWork.getRank().getId()} else {0},
                if (clientWork.getForceBranch() != null) {clientWork.getForceBranch().getId()} else {0},
                usver
              )
            }
          })
        }
      })
      //

      if (patientEntry.getId() > 0) {
        patient = dbManager.merge(patient)
      } else {
        dbManager.persist(patient)
      }
      new PatientEntry(patient, this.getKLADRAddressMapForPatient(patient), this.getKLADRStreetForPatient(patient));
    }/* catch {
      case e: CoreException => {
        //dbManager.rollbackTransaction()
        throw new CoreException(
          i18n("error.cantSavePatient").format()
        )
        null
      }
    }    */
    finally {
      if (lockId > 0) {
        appLock.releaseLock(lockId)
      }
    }
  }

  def getKLADRAddressMapForPatient (patient: Patient) = {

    val map = new java.util.LinkedHashMap[java.lang.Integer, java.util.LinkedList[Kladr]]()
    patient.getActiveClientAddresses().foreach(a=> {
      if (a.getAddress!=null && a.getAddress.getHouse!=null && !a.getAddress.getHouse.getKLADRCode.isEmpty){

        val list = new java.util.LinkedList[Kladr]
        val house = a.getAddress.getHouse
        var kladr = dbSchemeKladrBean.getKladrByCode(house.getKLADRCode)
        var flg = true

        while(kladr!=null && flg){
          list.add(kladr)
          var parent = kladr.getParent
          if(!parent.isEmpty){
            while(parent.size < 13){
              parent = parent + "0"
            }
            kladr = dbSchemeKladrBean.getKladrByCode(parent)
          }
          else {
            flg = false
          }
        }
        map.put(house.getId.intValue(), list)
      }
    })
    map
  }

  def getKLADRStreetForPatient (patient: Patient) = {

    val map = new java.util.LinkedHashMap[java.lang.Integer, Street]()
    val addresses = patient.getClientAddresses.filter(p => p.isDeleted==false)
    if(addresses!=null && addresses.size>0){
      addresses.foreach(f => {
        val address = f.getAddress
        if (address!=null) {
          val house = address.getHouse
          if(house!=null){
            val code = house.getKLADRStreetCode
            val street = dbSchemeKladrBean.getStreetByCode(code)
            if(street!=null){
              map.put(house.getId.intValue(), street)
            }
          }
        }
      })
    }
    map
  }

  def checkSNILSNumber(number: String) = {
    dbPatient.checkSNILSNumber(number)
  }

  def checkPolicyNumber(number: String, serial: String, typeId: Int) = {
    dbClientPolicy.checkPolicyNumber(number: String, serial: String, typeId: Int)
  }
}
