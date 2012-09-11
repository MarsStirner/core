package ru.korus.tmis.core.patient

import ru.korus.tmis.core.auth.AuthData
import ru.korus.tmis.core.data._
import ru.korus.tmis.core.database._
import ru.korus.tmis.core.entity.model._

import grizzled.slf4j.Logging
import java.lang.{Double => JDouble}
import javax.ejb.{EJB, Stateless}
import javax.interceptor.Interceptors

import scala.collection.JavaConversions._
import ru.korus.tmis.core.logging.db.LoggingInterceptor
import ru.korus.tmis.util.{ConfigManager, I18nable}
import ru.korus.tmis.core.exception.CoreException

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
  var dbActionProperty: DbActionPropertyBeanLocal = _

  @EJB
  var dbActionPropertyType: DbActionPropertyTypeBeanLocal = _

  @EJB
  var customQuery: DbCustomQueryLocal = _

  //////////////////////////////////////////////////////////////////////////////

  def getCurrentPatientsForDoctor(userData: AuthData) = {
    buildCommonData(customQuery.getActiveEventsForDoctor(userData.userId))
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

  //////////////////////////////////////////////////////////////////////////////

  def getPatientById(id: Int) = {
    dbPatient.getPatientById(id)
  }
}
