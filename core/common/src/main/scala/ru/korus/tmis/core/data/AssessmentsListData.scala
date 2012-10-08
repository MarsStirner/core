package ru.korus.tmis.core.data

import javax.xml.bind.annotation.{XmlRootElement, XmlType}
import reflect.BeanProperty
import java.util.{LinkedList, Date}
import ru.korus.tmis.core.entity.model.{Staff, Action}
import scala.collection.JavaConversions._
import ru.korus.tmis.util.ConfigManager

//Контейнер для списка осмотров
@XmlType(name = "assessmentsListData")
@XmlRootElement(name = "assessmentsListData")
class AssessmentsListData {

  @BeanProperty
  var requestData: AssessmentsListRequestData = _
  @BeanProperty
  var data: LinkedList[AssessmentsListEntry] = new LinkedList[AssessmentsListEntry]

  def this(actions: java.util.List[Action], requestData: AssessmentsListRequestData) {
    this()
    this.requestData = requestData
    actions.foreach(action => this.data.add(new AssessmentsListEntry(action)))
  }
}

@XmlType(name = "assessmentsListRequestData")
@XmlRootElement(name = "assessmentsListRequestData")
class AssessmentsListRequestData {

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
  var eventId: Int = _
  @BeanProperty
  var coreVersion: String = _

  var sortingFieldInternal: String = _

  def this(sortingField: String,
           sortingMethod: String,
           limit: Int,
           page: Int,
           eventId: Int,
           filter: AnyRef) = {
    this()
    this.filter = if (filter != null) {
      filter
    } else {
      new AssessmentsListRequestDataFilter()
    }
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
      ConfigManager.Messages("misCore.pages.limit.default").toInt
    }
    this.page = if (page > 1) {
      page
    } else {
      1
    }
    this.eventId = eventId
    this.coreVersion = ConfigManager.Messages("misCore.assembly.version")


    this.sortingFieldInternal =
      if (filter.isInstanceOf[AssessmentsListRequestDataFilter])
        this.filter.asInstanceOf[AssessmentsListRequestDataFilter].toSortingString(this.sortingField)
      else
        this.sortingField
  }
}

class AssessmentsListRequestDataFilter {

  //   =>Фильтрация по коду типа действия<=
  @BeanProperty
  var code: String = _

  @BeanProperty
  var assessmentDate: Date = _

  @BeanProperty
  var doctorName: String = _

  @BeanProperty
  var speciality: String = _

  @BeanProperty
  var assessmentName: String = _

  @BeanProperty
  var departmentName: String = _

  def this(code_x: String,
           assessmentDate: Long,
           doctorName: String,
           speciality: String,
           assessmentName: String,
           departmentName: String) {
    this()
    this.code = if (code_x != null && code_x != "") {
      code_x
    } else {
      "1_"
    }
    this.assessmentDate = if (assessmentDate == 0) {
      null
    } else {
      new Date(assessmentDate)
    }
    this.doctorName = doctorName
    this.speciality = speciality
    this.assessmentName = assessmentName
    this.departmentName = departmentName
  }

  def toQueryStructure() = {
    var qs = new QueryDataStructure()
    if (this.code != null && !this.code.isEmpty) {
      qs.query += "AND a.actionType.code LIKE :code\n"
      qs.add("code", this.code + "%")
    }
    if (this.doctorName != null && !this.doctorName.isEmpty) {
      qs.query += ("AND upper(a.createPerson.lastName) LIKE upper(:doctorName)\n")
      qs.add("doctorName", "%" + this.doctorName + "%")
    }
    if (this.assessmentDate != null) {
      qs.query += "AND a.createDatetime = :assessmentDate\n"
      qs.add("assessmentDate", this.assessmentDate)
    }
    if (this.speciality != null && !this.speciality.isEmpty) {
      qs.query += "AND upper(a.createPerson.speciality.name) LIKE upper(:speciality)\n"
      qs.add("speciality", "%" + this.speciality + "%")
    }
    if (this.assessmentName != null && !this.assessmentName.isEmpty) {
      qs.query += "AND upper(a.actionType.name) LIKE upper(:assessmentName)\n"
      qs.add("assessmentName", "%" + this.assessmentName + "%")
    }
    if (this.departmentName != null && !this.departmentName.isEmpty) {
      qs.query += "AND upper(a.createPerson.orgStructure.name) LIKE upper(:departmentName)\n"
      qs.add("departmentName", "%" + this.departmentName + "%")
    }
    qs
  }

  def toSortingString(sortingField: String) = {
    val sortingFieldInternal = sortingField match {
      case "assessmentDate" | "start" | "createDatetime" => {
        "a.createDatetime"
      }
      case "assessmentName" | "name" => {
        "a.actionType.name"
      }
      case "doctor" => {
        "a.createPerson.lastName, a.createPerson.firstName, a.createPerson.patrName"
      }
      case "department" => {
        "a.createPerson.orgStructure.name"
      }
      case "specs" => {
        "a.createPerson.speciality.name"
      }
      case _ => {
        "a.id"
      }
    }
    sortingFieldInternal
  }
}

@XmlType(name = "assessmentsListEntry")
@XmlRootElement(name = "assessmentsListEntry")
class AssessmentsListEntry {

  @BeanProperty
  var id: Int = _ //Action Id

  @BeanProperty
  var assessmentDate: Date = _ //date of assessment

  @BeanProperty
  var assessmentName: IdNameContainer = _ //Наименование

  @BeanProperty
  var doctor: DoctorSpecsContainer = new DoctorSpecsContainer() //Врач

  def this(action: Action) {
    this()
    this.id = action.getId.intValue()
    this.assessmentDate = action.getCreateDatetime
    this.assessmentName = new IdNameContainer(action.getActionType.getId.intValue(), action.getActionType.getName)
    this.doctor = new DoctorSpecsContainer(action.getCreatePerson)
  }
}

@XmlType(name = "doctorSpecsContainer")
@XmlRootElement(name = "doctorSpecsContainer")
class DoctorSpecsContainer {
  @BeanProperty
  var id: Int = -1 //Staff Id

  @BeanProperty
  var name: PersonNameContainer = new PersonNameContainer() //Staff Value

  @BeanProperty
  var specs: IdNameContainer = _ //Staff speciality

  @BeanProperty
  var department: IdNameContainer = _ //отделение

  def this(person: Staff) {
    this()
    this.id = person.getId.intValue()
    this.name = new PersonNameContainer(person)
    if (person.getSpeciality != null) {
      this.specs = new IdNameContainer(person.getSpeciality.getId.intValue(), person.getSpeciality.getName)
    }
    if (person.getOrgStructure != null) {
      this.department = new IdNameContainer(person.getOrgStructure.getId.intValue(), person.getOrgStructure.getName)
    }
  }
}
