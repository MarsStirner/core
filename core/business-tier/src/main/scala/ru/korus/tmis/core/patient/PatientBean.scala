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
import java.util

@Interceptors(Array(classOf[LoggingInterceptor]))
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

  @EJB
  private var dbDiagnocticsBean: DbDiagnosticBeanLocal = _

  @EJB
  private var dbOrgStructureBean: DbOrgStructureBeanLocal = _
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

  def getCurrentPatientsByDepartmentId(id: Int) = {
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
               //doctor.getSpeciality.getName) ::
               if (doctor.getSpeciality != null) {doctor.getSpeciality.getName} else {""}) ::
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

  //https://docs.google.com/spreadsheet/ccc?key=0AgE0ILPv06JcdEE0ajBZdmk1a29ncjlteUp3VUI2MEE#gid=0
  def getAllPatientsForDepartmentIdAndDoctorIdByPeriod(requestData: PatientsListRequestData, authData: AuthData) = {

    val role = requestData.filter.roleId
    val mapper: ObjectMapper = new ObjectMapper()

    val eventsMap = customQuery.getActiveEventsForDepartmentAndDoctor(requestData.page-1,
                                                                      requestData.limit,
                                                                      requestData.sortingFieldInternal,
                                                                      requestData.sortingMethod,
                                                                      requestData.filter,
                                                                      requestData.rewriteRecordsCount _)

    var conditionsInfo = new java.util.HashMap[Event, java.util.Map[ActionProperty, java.util.List[APValue]]]
    if(role == 25) {  //Для сестры отделения только
      val conditions = customQuery.getLastAssessmentByEvents(eventsMap.keySet().toList)   //Последний экшн осмотра
      conditions.foreach(
        c => {
          val apList = dbActionProperty.getActionPropertiesByActionIdAndTypeCodes(c._2.getId.intValue,List("STATE", "PULS", "BPRAS","BPRAD"))
          conditionsInfo.put(c._1, apList)
        }
      )
      mapper.getSerializationConfig().setSerializationView(classOf[PatientsListDataViews.NurseView])
    }
    else mapper.getSerializationConfig().setSerializationView(classOf[PatientsListDataViews.AttendingDoctorView])

    mapper.writeValueAsString(new PatientsListData(eventsMap,
                                                   requestData,
                                                   role,
                                                   conditionsInfo,
                                                   dbOrgStructureBean.getOrgStructureById _,
                                                   dbActionProperty.getActionPropertiesByActionIdAndRbCoreActionPropertyIds _,
                                                   dbRbCoreActionPropertyBean.getRbCoreActionPropertiesByIds _,
                                                   dbDiagnocticsBean.getDiagnosticsByEventId _))
  }

  def getPatientById(id: Int) = {
    dbPatient.getPatientById(id)
  }

  def getAllPatients(requestData: PatientRequestData) = {
      dbPatient.getAllPatients( requestData.page-1,
                                requestData.limit,
                                requestData.sortingFieldInternal,
                                requestData.filter.unwrap(),
                                requestData.rewriteRecordsCount _)
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

      //create or update quota


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
      val serverSocScatuses = patient.getActiveClientSocStatuses
      val serverWorks = patient.getActiveClientWorks

      val clientDisabilities = patientEntry.getDisabilities
      var clientOccupations = patientEntry.getOccupations
      var clientCitizenships = patientEntry.getCitizenship

      //---Добавление + обновление---
      //Инвалидности
      clientDisabilities.foreach(clientDisability => {
        if(clientDisability.getDisabilityType!=null && clientDisability.getDisabilityType.getId>0) {
          dbSocStatus.insertOrUpdateClientSocStatus(
              clientDisability.getId,
              32,
              clientDisability.getDisabilityType.getId,
              clientDisability.getDocument,
              clientDisability.getRangeDisabilityDate.getStart,
              clientDisability.getRangeDisabilityDate.getEnd,
              patient,
              if (clientDisability.getBenefitsCategory != null) {clientDisability.getBenefitsCategory.getId} else {0},
              clientDisability.getComment,
              usver
          )
        }
      })

      //Социальный статус
      clientOccupations.foreach(clientOccupation => {
        if(clientOccupation.getSocialStatus!=null && clientOccupation.getSocialStatus.getId>0) {
          dbSocStatus.insertOrUpdateClientSocStatus(
              clientOccupation.getId,
              33,
              clientOccupation.getSocialStatus.getId,
              null,
              new Date,
              null,
              patient,
              0,
              clientOccupation.getComment,
              usver
          )
          //Работа
          val clientWorks = clientOccupation.getWorks
          clientWorks.foreach(clientWork => {
            val freeInput = clientOccupation.getSocialStatus.getId match {
              case 310 => clientWork.getWorkingPlace    //Работающий
              case 313 => clientWork.getPreschoolNumber //Дошкольник
              case 314 => clientWork.getSchoolNumber    //Учащийся
              case 315 => clientWork.getMilitaryUnit    //Военнослужащий
              case _ => ""
            }
            val post = clientOccupation.getSocialStatus.getId match {
              case 310 => clientWork.getPosition        //Работающий
              case 314 => clientWork.getClassNumber     //Учащийся
              case _ => ""
            }
            dbClientWorks.insertOrUpdateClientWork( clientWork.getId,
              patient,
              freeInput,
              post,
              if (clientWork.getRank != null) clientWork.getRank.getId else 0,
              if (clientWork.getForceBranch!=null) clientWork.getForceBranch.getId else 0,
              usver)
          })
          //Удаление
          serverWorks.filter(sw => {
            val result = clientWorks.find(cw => cw.getId.compareTo(sw.getId)==0)
            if (result==None) true else false
          }).foreach(f => dbClientWorks.deleteClientWork(f.getId.intValue(), usver))
        }
      })

      //Гражданства
      if(clientCitizenships!=null) {
        Set(clientCitizenships.getFirst, clientCitizenships.getSecond).foreach(f=> {
          if (f!=null && f.getSocStatusType!=null && f.getSocStatusType.getId>0){
            dbSocStatus.insertOrUpdateClientSocStatus(
              f.getId,
              35,
              f.getSocStatusType.getId,
              null,
              new Date,
              null,
              patient,
              0,
              f.getComment,
              usver)
          }
        })
      }

      //---Удаление---
      serverSocScatuses.filter(sw => {
        val result = clientDisabilities.find(cw => cw.getId.compareTo(sw.getId)==0)
        if (result==None) true else false
      }).foreach(f => dbSocStatus.deleteClientSocStatus(f.getId.intValue(), usver))

      serverSocScatuses.filter(sw => {
        val result = clientOccupations.find(cw => cw.getId.compareTo(sw.getId)==0)
        if (result==None) true else false
      }).foreach(f => {
        dbSocStatus.deleteClientSocStatus(f.getId.intValue(), usver)
        val serverWorks = patient.getActiveClientWorks
        serverWorks.foreach(serverWork => {
          if (serverWork != null && serverWork.getId != null) {
            dbClientWorks.deleteClientWork(serverWork.getId.intValue(), usver)
          }
        })
      })

      //-------------- костыль для НТК (нужно создавать пустой клиентВорк если нет ни одного)
      val serverWorks2 = patient.getActiveClientWorks
      if (serverWorks2 == null || serverWorks2.size() <= 0) {
        dbClientWorks.insertOrUpdateClientWork(0,
          patient,
          "",
          "",
          0,
          0,
          usver)
      }
      //--------------

      serverSocScatuses.filter(sw => {
        if (sw.getSocStatusType.getId.compareTo(35)==0) {
          val first = if(clientCitizenships.getFirst!=null) clientCitizenships.getFirst.getId else 0
          val second = if(clientCitizenships.getSecond!=null) clientCitizenships.getSecond.getId else 0
          if (sw.getId.compareTo(first)==0 || sw.getId.compareTo(second)==0) false else true
        } else false
      }).foreach(f => dbSocStatus.deleteClientSocStatus(f.getId.intValue(), usver))

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

  def deletePatientInfo(id: Int) = {
    dbPatient.deletePatient(id)
  }
}
