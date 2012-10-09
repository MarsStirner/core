package ru.korus.tmis.core.data

import javax.xml.bind.annotation.XmlType._
import javax.xml.bind.annotation.{XmlRootElement, XmlType}
import javax.xml.bind.annotation.XmlRootElement._
import reflect.BeanProperty
import java.util.{Calendar, Date, LinkedList}
import ru.korus.tmis.core.entity.model._
import ru.korus.tmis.util.ConfigManager
import scala.collection.JavaConversions._
import java.text.SimpleDateFormat
import org.codehaus.jackson.map.annotate.JsonView

object ReceivedPatientsDataViews {

  class AdmissionDepartmentsNurseView {
  }

  class AdmissionDepartmentsDoctorView {
  }

}

class ReceivedPatientsDataViews {}

//Контейнер для данных об пациентах поступивших за период
@XmlType(name = "receivedPatientsData")
@XmlRootElement(name = "receivedPatientsData")
class ReceivedPatientsData {
  @BeanProperty
  var requestData: ReceivedRequestData = new ReceivedRequestData()
  @BeanProperty
  var data: LinkedList[ReceivedPatientsEntry] = new LinkedList[ReceivedPatientsEntry]

  def this(map: java.util.Map[Event, Object],
           request: ReceivedRequestData) {
    this()
    this.requestData = request

    map.foreach(f => {
      if (f._2.isInstanceOf[(Action, java.util.Map[_, java.util.List[_]])]) {
        this.data.add(new ReceivedPatientsEntry(f._1,
          f._2.asInstanceOf[(Action, java.util.Map[_, java.util.List[_]])]._1,
          f._2.asInstanceOf[(Action, java.util.Map[_, java.util.List[_]])]._2))
      }
    })
  }
}

@XmlType(name = "receivedRequestData")
@XmlRootElement(name = "receivedRequestData")
class ReceivedRequestData {
  @BeanProperty
  var filter: AnyRef = _
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

  def this(sortingField: String,
           sortingMethod: String,
           limit: Int,
           page: Int,
           filter: AnyRef) = {
    this()
    this.filter = filter
    this.sortingField = sortingField match {
      case null => {
        "id"
      }
      case _ => {
        sortingField
      }
    }

    this.sortingFieldInternal = this.filter.asInstanceOf[ReceivedRequestDataFilter].toSortingString(this.sortingField)

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

  }
}

@XmlType(name = "receivedRequestDataFilter")
@XmlRootElement(name = "receivedRequestDataFilter")
class ReceivedRequestDataFilter {
  @BeanProperty
  var eventId: Int = _
  // — Номер обращения
  @BeanProperty
  var fullName: String = _
  // — ФИО
  @BeanProperty
  var birthDate: Date = _
  // — Дата рождения
  @BeanProperty
  var externalId: String = _
  // — номер карточки пациента
  @BeanProperty
  var beginDate: Date = _
  @BeanProperty
  var endDate: Date = _
  @BeanProperty
  var diagnosis: String = _


  var role: Int = _

  def this(eventId: Int,
           fullName: String,
           birthDate: java.lang.Long,
           externalId: String,
           bDate: java.lang.Long,
           eDate: java.lang.Long) = {
    this()
    this.eventId = eventId
    this.fullName = fullName
    this.birthDate = if (birthDate == null) {
      null
    } else {
      new Date(birthDate.longValue())
    }
    this.externalId = externalId
    this.beginDate = if (bDate == null) {
      null
    } else {
      new Date(bDate.longValue())
    }
    this.endDate = if (eDate == null) {
      null
    } else {
      new Date(eDate.longValue())
    }
  }

  def this(eventId: Int,
           fullName: String,
           birthDate: java.lang.Long,
           externalId: String,
           bDate: java.lang.Long,
           eDate: java.lang.Long,
           diagnosis: String) = {
    this(eventId, fullName, birthDate, externalId, bDate, eDate)
    this.diagnosis = diagnosis
  }

  def this(eventId: Int,
           fullName: String,
           birthDate: java.lang.Long,
           externalId: String,
           bDate: java.lang.Long,
           eDate: java.lang.Long,
           diagnosis: String,
           role: Int) = {
    this(eventId, fullName, birthDate, externalId, bDate, eDate, diagnosis)
    this.role = role
  }

  def toQueryStructure() = {
    val formatter = new SimpleDateFormat("yyyy-MM-dd")
    var qs = new QueryDataStructure()
    if (this.eventId > 0) {
      qs.query += "AND e.id = :eventId\n"
      qs.add("eventId", this.eventId: java.lang.Integer)
    }
    if (this.birthDate != null) {
      qs.query += ("AND e.patient.birthDate = :birthDate\n")
      qs.add("birthDate", this.birthDate)
    }
    if (this.externalId != null && !this.externalId.isEmpty) {
      qs.query += "AND e.externalId LIKE :externalId\n"
      qs.add("externalId", "%" + this.externalId + "%")
    }
    if (this.fullName != null && !this.fullName.isEmpty) {
      qs.query += "AND upper(e.patient.lastName) LIKE upper(:fullName)\n"
      qs.add("fullName", "%" + this.fullName + "%")
    }
    if (this.beginDate != null && this.endDate == null) {
      var begDay = Calendar.getInstance()
      begDay.setTime(formatter.parse(formatter.format(this.beginDate)))
      begDay.set(Calendar.HOUR_OF_DAY, 0)
      val endDay = begDay.getTime.getTime + 24 * 60 * 60 * 1000 - 1
      qs.query += "AND e.setDate BETWEEN :beginDate AND :endDate\n"
      qs.add("beginDate", begDay.getTime)
      qs.add("endDate", new Date(endDay))
    }
    else if (this.endDate != null && this.beginDate == null) {
      var begDay = Calendar.getInstance()
      begDay.setTime(formatter.parse(formatter.format(this.endDate)))
      begDay.set(Calendar.HOUR_OF_DAY, 0)
      val endDay = begDay.getTime.getTime + 24 * 60 * 60 * 1000 - 1
      qs.query += "AND e.execDate BETWEEN :beginDate AND :endDate\n"
      qs.add("beginDate", begDay.getTime)
      qs.add("endDate", new Date(endDay))
    } else if (this.endDate != null && this.beginDate != null) {
      qs.query += "AND e.setDate BETWEEN :beginDate AND :endDate\n"
      qs.add("beginDate", this.beginDate)
      qs.add("endDate", this.endDate)
    }
    if (this.diagnosis != null && !this.diagnosis.isEmpty) {
      qs.query += "AND exists( " +
        "SELECT ap " +
        "FROM ActionProperty ap JOIN ap.actionPropertyType apt, APValueMKB mkb " +
        "WHERE ap.action = a " +
        "AND apt.name LIKE 'Диагноз направившего учреждения%' " +
        "AND ap.id = mkb.id.id " +
        "AND (mkb.mkb.diagID LIKE :diagnosis OR mkb.mkb.diagName LIKE :diagnosis))"
      qs.add("diagnosis", "%" + this.diagnosis + "%")
    }
    if (this.role != 29)
      qs.query += "AND a.actionType.code = '4201'\n"
    else {
      qs.query += "AND (\n" +
        "(a.actionType.id = '113'\n" +
        "AND\n" +
        "exists(\n" +
        "  SELECT ap2\n" +
        "    FROM ActionProperty ap2\n" +
        "    JOIN ap2.actionPropertyType apt2,\n" +
        "  APValueOrgStructure apstr\n" +
        "    WHERE ap2.action = a\n" +
        "AND apt2.name = 'Отделение пребывания'\n" +
        "AND ap2.id = apstr.id.id\n" +
        "AND apstr.value IS NOT NULL\n" +
        //"  AND apstr.value.id <> ''\n" +
        ")\n" +
        ")\n" +
        "OR (\n" +
        "  a.actionType.id = '112'\n" +
        "AND\n" +
        "exists(\n" +
        "  SELECT ap3\n" +
        "    FROM ActionProperty ap3\n" +
        "    JOIN ap3.actionPropertyType apt3,\n" +
        "  APValueOrgStructure apstr2\n" +
        "    WHERE ap3.action = a\n" +
        "AND apt3.name = 'Направлен в отделение'\n" +
        "AND ap3.id = apstr2.id.id\n" +
        "AND apstr2.value IS NOT NULL\n" +
        //"  AND apstr2.value.id <> ''\n"
        ")\n" +
        "))\n"
    }
    qs
  }

  def toSortingString(sortingField: String) = {
    sortingField match {
      case "createDatetime" => {
        "e.setDate"
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
  }
}

@XmlType(name = "receivedPatientsEntry")
@XmlRootElement(name = "receivedPatientsEntry")
class ReceivedPatientsEntry {

  @BeanProperty
  var id: Int = -1
  @BeanProperty
  var createDatetime: Date = _
  @BeanProperty
  var number: String = _
  @JsonView(Array(classOf[ReceivedPatientsDataViews.AdmissionDepartmentsDoctorView]))
  @BeanProperty
  var urgent: Boolean = _
  @BeanProperty
  var patient: IdPatientDataContainer = new IdPatientDataContainer
  @JsonView(Array(classOf[ReceivedPatientsDataViews.AdmissionDepartmentsDoctorView]))
  @BeanProperty
  var diagnoses: LinkedList[DiagnosisSimplifyContainer] = new LinkedList[DiagnosisSimplifyContainer]
  @JsonView(Array(classOf[ReceivedPatientsDataViews.AdmissionDepartmentsNurseView]))
  @BeanProperty
  var moving: MovingContainer = _

  def this(event: Event, action: Action) {
    this()
    this.id = event.getId.intValue()
    this.createDatetime = event.getSetDate
    this.number = event.getExternalId
    this.urgent = action.getIsUrgent
    this.patient = new IdPatientDataContainer(event.getPatient)
  }

  /*def this(event: Event, action: Action, map: java.util.Map[String, java.util.List[Mkb]]) {
    this(event, action)
    map.foreach(f => {
      f._2.foreach(mkb => this.diagnoses += new DiagnosisSimplifyContainer(f._1, mkb))
    })
  }*/

  def this(event: Event, action: Action, map: java.util.Map[_, java.util.List[_]]) {
    //ActionProperty, java.util.List[APValue]
    this(event, action)
    val element = map.iterator.next
    if (element._1.isInstanceOf[ActionProperty] && element._2.isInstanceOf[java.util.List[APValue]]) {
      this.moving = new MovingContainer()
      if (event.getExecDate != null) {
        action.getActionType.getId.intValue() match {
          case 113 => {
            val res = map.asInstanceOf[java.util.Map[ActionProperty, java.util.List[APValue]]].find(element => element._1.getType.getName.compareTo("Отделение пребывания") == 0).getOrElse(null)
            if (res != null)
              this.moving.department = new IdNameContainer(res._2.get(0).getValue.asInstanceOf[OrgStructure].getId.intValue(), res._2.get(0).getValue.asInstanceOf[OrgStructure].getName)
            val res2 = map.asInstanceOf[java.util.Map[ActionProperty, java.util.List[APValue]]].find(element => element._1.getType.getName.compareTo("койка") == 0).getOrElse(null)
            if (res2 != null)
              this.moving.bed = res2._2.get(0).getValueAsString
          }
          case 112 => {
            val res = map.asInstanceOf[java.util.Map[ActionProperty, java.util.List[APValue]]].find(element => element._1.getType.getName.compareTo("Направлен в отделение") == 0).getOrElse(null)
            if (res != null)
              this.moving.department = new IdNameContainer(res._2.get(0).getValue.asInstanceOf[OrgStructure].getId.intValue(), res._2.get(0).getValue.asInstanceOf[OrgStructure].getName)
          }
          case _ => {}
        }
      }
    }
    else if (element._1.isInstanceOf[String] && element._2.isInstanceOf[java.util.List[Mkb]]) {
      map.asInstanceOf[java.util.Map[String, java.util.List[Mkb]]].foreach(f => {
        f._2.foreach(mkb => this.diagnoses += new DiagnosisSimplifyContainer(f._1, mkb))
      })
    }
  }
}

@XmlType(name = "idPatientDataContainer")
@XmlRootElement(name = "idPatientDataContainer")
class IdPatientDataContainer {

  @BeanProperty
  var id: Int = -1
  @BeanProperty
  var name: PersonNameContainer = new PersonNameContainer()
  @BeanProperty
  var birthDate: Date = _
  @BeanProperty
  var sex: String = _
  @BeanProperty
  var phones: LinkedList[ClientContactContainer] = new LinkedList[ClientContactContainer]()

  def this(patient: Patient) {
    this()
    this.id = patient.getId.intValue()
    this.name = new PersonNameContainer(patient)
    this.birthDate = patient.getBirthDate
    this.sex = patient.getSex match {
      case 1 => "male"
      case 2 => "female"
      case _ => "unknown"
    }
    patient.getClientContacts.foreach(c => this.phones += new ClientContactContainer(c))
  }
}

@XmlType(name = "diagnosisSimplifyContainer")
@XmlRootElement(name = "diagnosisSimplifyContainer")
class DiagnosisSimplifyContainer {
  @BeanProperty
  var diagnosisKind: String = _
  @BeanProperty
  var mkb: MKBContainer = new MKBContainer()

  def this(kind: String, mkb: Mkb) {
    this()
    this.diagnosisKind = kind
    this.mkb = new MKBContainer(mkb)
  }
}

@XmlType(name = "movingContainer")
@XmlRootElement(name = "movingContainer")
class MovingContainer {
  @BeanProperty
  var department: IdNameContainer = _
  @BeanProperty
  var room: String = _
  @BeanProperty
  var bed: String = _

  def this(departmentId: Int,
           room: String,
           bed: String) {
    this()
    this.department = new IdNameContainer(departmentId, "")
    this.room = room
    this.bed = bed
  }
}