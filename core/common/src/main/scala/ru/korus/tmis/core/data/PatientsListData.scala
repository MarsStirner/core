package ru.korus.tmis.core.data

import javax.xml.bind.annotation.{XmlType, XmlRootElement}
import reflect.BeanProperty
import scala.collection.JavaConversions._
import java.util.{Date, LinkedList}
import ru.korus.tmis.core.entity.model._
import ru.korus.tmis.util.ConfigManager
import org.codehaus.jackson.map.annotate.JsonView

object PatientsListDataViews {

  class AttendingDoctorView {}

  class NurseView {}

}

class PatientsListDataViews {}

//Лечащий врач
@XmlType(name = "patientsListData")
@XmlRootElement(name = "patientsListData")
class PatientsListData {

  @BeanProperty
  var requestData: PatientsListRequestData = _
  @BeanProperty
  var data: LinkedList[PatientsListEntry] = new LinkedList[PatientsListEntry]

  def this(events: java.util.List[Event],
           bedInfo: java.util.Map[Event, OrgStructureHospitalBed],
           condInfo: java.util.Map[Event, java.util.Map[ActionProperty, java.util.List[APValue]]],
           requestData: PatientsListRequestData) = {
    this()

    events.foreach(event => {

      val bed = bedInfo.containsKey(event) match {
        case false => null
        case bed => bedInfo.get(event)
      }

      val condition = condInfo.containsKey(event) match {
        case false => null
        case condition => {
          condInfo.get(event)
        }
      }
      this.data.add(new PatientsListEntry(event, bed, condition))
    })
    this.requestData = requestData
  }
}

@XmlType(name = "patientsListRequestData")
@XmlRootElement(name = "patientsListRequestData")
class PatientsListRequestData {
  @BeanProperty
  var filter: PatientsListRequestDataFilter = _
  @BeanProperty
  var sortingField: String = _
  @BeanProperty
  var sortingMethod: String = _
  @BeanProperty
  var limit: Int = _
  @BeanProperty
  var page: Int = _
  @BeanProperty
  var recordsCount: Long = _
  @BeanProperty
  var coreVersion: String = _

  var sortingFieldInternal: String = _

  def this(departmentId: Int,
           doctorId: Int,
           beginDate: Long,
           endDate: Long,
           sortingField: String,
           sortingMethod: String,
           limit: Int,
           page: Int) = {
    this()
    this.filter = new PatientsListRequestDataFilter(departmentId, doctorId, beginDate, endDate)
    this.sortingField = sortingField match {
      case null => {
        "id"
      }
      case _ => {
        sortingField
      }
    }
    this.sortingMethod = sortingMethod match {
      case null => {
        "asc"
      }
      case _ => {
        sortingMethod
      }
    }
    this.limit = if (limit > 0) {
      limit
    } else {
      10
    }
    this.page = if (page > 1) {
      page
    } else {
      1
    }
    this.coreVersion = ConfigManager.Messages("misCore.assembly.version")

    this.sortingFieldInternal = this.filter.toSortingString(this.sortingField)
  }
}

@XmlType(name = "patientsListRequestDataFilter")
@XmlRootElement(name = "patientsListRequestDataFilter")
class PatientsListRequestDataFilter {
  @BeanProperty
  var departmentId: Int = _
  // — Отделение
  @BeanProperty
  var doctorId: Int = _
  // — Врач
  @BeanProperty
  var beginDate: Date = _
  // — Дата начала выборки
  @BeanProperty
  var endDate: Date = _ // — Дата конца  выборки

  def this(departmentId: Int,
           doctorId: Int,
           beginDate: Long,
           endDate: Long) = {

    this()
    this.departmentId = departmentId
    this.doctorId = doctorId
    this.beginDate = new Date(beginDate) //Нулевая дата
    this.endDate = if (endDate == 0) {
      new Date()
    } //Дата на время запроса
    else new Date(endDate)
  }

  def toQueryStructure() = {
    var qs = new QueryDataStructure()
    if (this.departmentId > 0) {
      qs.query += ("AND org.masterDepartment.id = :departmentId\n")
      qs.add("departmentId", this.departmentId: java.lang.Integer)
    }
    if (this.doctorId > 0) {
      qs.query += ("AND e.executor.id = :doctorId\n")
      qs.add("doctorId", this.doctorId: java.lang.Integer)
    }
    if (this.beginDate != null && this.endDate != null) {
      qs.query += ("AND e.createDatetime BETWEEN :beginDate AND :endDate\n")
      qs.add("beginDate", this.beginDate)
      qs.add("endDate", this.endDate)
    }
    qs
  }

  def toSortingString(sortingField: String) = {
    val sortingFieldInternal = sortingField match {
      case "createDatetime" | "start" | "begDate" => {
        "e.setDate"
      }
      case "end" | "endDate" => {
        "e.execDate"
      }
      case "doctor" => {
        "e.executor.lastName, e.executor.firstName, e.executor.patrName"
      }
      case "department" => {
        "org.masterDepartment.name"
      }
      case "bed" => {
        "org.name"
      }
      case "number" => {
        "e.externalId"
      }
      case "fullName" => {
        "e.patient.lastName, e.patient.firstName, e.patient.patrName"
      }
      case "birthDate" => {
        "e.patient.birthDate"
      }
      case _ => {
        "e.id"
      }
    }
    sortingFieldInternal
  }
}

@XmlType(name = "patientsListEntry")
@XmlRootElement(name = "patientsListEntry")
class PatientsListEntry {

  @BeanProperty
  var id: Int = _ //Ид эвента госпитализации

  @BeanProperty
  var number: String = _ //Номер истории болезни

  @BeanProperty
  var name: PersonNameContainer = _ //Пациент

  @JsonView(Array(classOf[PatientsListDataViews.AttendingDoctorView]))
  @BeanProperty
  var birthDate: Date = _ //День рождения

  @BeanProperty
  var hospitalBed: HospitalBedContainer = _ //Палата/Койка

  @BeanProperty
  var doctor: DoctorSpecsContainer = _ //Врач + Специальность + Отделение

  @BeanProperty
  var createDateTime: Date = _ //Дата поступления

  @JsonView(Array(classOf[PatientsListDataViews.AttendingDoctorView]))
  @BeanProperty
  var checkOut: String = _ //Выписка через

  @JsonView(Array(classOf[PatientsListDataViews.NurseView]))
  @BeanProperty
  var condition: PersonConditionContainer = _

  def this(event: Event, bed: OrgStructureHospitalBed, condition: java.util.Map[ActionProperty, java.util.List[APValue]]) {
    this()
    val patient = event.getPatient
    this.id = event.getId.intValue()
    this.number = event.getExternalId
    this.name = new PersonNameContainer(patient)
    this.birthDate = patient.getBirthDate
    this.doctor = new DoctorSpecsContainer(event.getExecutor)
    this.createDateTime = event.getCreateDatetime
    this.checkOut = "" //TODO: "Выписка через" - расчетное поле, алгоритм не утвержден
    if (bed != null)
      this.hospitalBed = new HospitalBedContainer(bed)
    if (condition != null)
      this.condition = new PersonConditionContainer(condition)
  }
}

@XmlType(name = "hospitalBedContainer")
@XmlRootElement(name = "hospitalBedContainer")
class HospitalBedContainer {

  @BeanProperty
  var id: Int = _

  @BeanProperty
  var department: IdNameContainer = _

  @BeanProperty
  var room: String = _

  @BeanProperty
  var bed: String = _

  @BeanProperty
  var code: String = _

  @BeanProperty
  var name: String = _

  @BeanProperty
  var raw: String = _

  def this(bed: OrgStructureHospitalBed) {
    this()
    this.id = bed.getId.intValue()
    this.department = new IdNameContainer(bed.getMasterDepartment.getId.intValue(),
      bed.getMasterDepartment.getName)
    this.room = ""
    this.bed = (bed.getIdx + 1).toString
    this.code = bed.getCode
    this.name = bed.getName
    this.raw = ""
  }
}

@XmlType(name = "personConditionContainer")
@XmlRootElement(name = "personConditionContainer")
class PersonConditionContainer {

  @BeanProperty
  var state: String = _

  @BeanProperty
  var breathingRate: String = _

  @BeanProperty
  var arterialBloodPressure: ArterialBloodPressureContainer = new ArterialBloodPressureContainer()

  def this(condition: java.util.Map[ActionProperty, java.util.List[APValue]]) {
    this()
    condition.foreach(c => {
      val value = c._2.size() match {
        case 0 => ""
        case size => c._2.get(0).getValueAsString
      }
      c._1.getType.getName match {
        case "Состояние" => {
          this.state = value
        }
        case "ЧСС" => {
          this.breathingRate = value
        }
        case "АД нижн." => {
          this.arterialBloodPressure.low = value
        }
        case "АД верхн." => {
          this.arterialBloodPressure.high = value
        }
        case _ => {}
      }
    })

  }

}

@XmlType(name = "arterialBloodPressureContainer")
@XmlRootElement(name = "arterialBloodPressureContainer")
class ArterialBloodPressureContainer {

  @BeanProperty
  var low: String = _

  @BeanProperty
  var high: String = _
}