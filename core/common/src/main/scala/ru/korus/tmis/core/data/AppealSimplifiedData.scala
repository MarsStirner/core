package ru.korus.tmis.core.data

import reflect.BeanProperty
import java.util.{Date, LinkedList}
import javax.xml.bind.annotation.{XmlRootElement, XmlType}
import ru.korus.tmis.util.ConfigManager
import scala.collection.JavaConversions._
import ru.korus.tmis.core.entity.model._

@XmlType(name = "appealSimplifiedDataList")
@XmlRootElement(name = "appealSimplifiedDataList")
class AppealSimplifiedDataList {

  @BeanProperty
  var requestData: AppealSimplifiedRequestData = _

  @BeanProperty
  var data: LinkedList[AppealSimplifiedData] = new LinkedList[AppealSimplifiedData]

  def add(that: AppealSimplifiedData) = {
    this.data.add(that)
    this
  }

  def this(requestData: AppealSimplifiedRequestData) {
    this()
    this.requestData = requestData
  }

  def this(mutableEvents: java.util.Map[Event, java.util.Map[Object, Object]],
           requestData: AppealSimplifiedRequestData) {
    this()
    this.requestData = requestData
    mutableEvents.foreach(f => this.data += new AppealSimplifiedData(f._1, f._2))
  }
}

@XmlType(name = "appealSimplifiedRequestData")
@XmlRootElement(name = "appealSimplifiedRequestData")
class AppealSimplifiedRequestData {
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
    this.filter = if (filter != null) {
      filter
    } else {
      null
    }
    this.sortingField = sortingField match {
      case null => {
        "id"
      }
      case _ => {
        sortingField
      }
    }
    this.sortingFieldInternal = this.filter.asInstanceOf[AppealSimplifiedRequestDataFilter].toSortingString(this.sortingField)

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
    this.coreVersion = ConfigManager.Messages("misCore.assembly.version")
  }
}

class AppealSimplifiedRequestDataFilter {

  @BeanProperty
  var patientId: Int = _

  @BeanProperty
  var beginDate: Date = _

  @BeanProperty
  var endDate: Date = _

  @BeanProperty
  var departmentId: Int = _

  @BeanProperty
  var doctorId: Int = _

  @BeanProperty
  var diagnosis: String = _

  @BeanProperty
  var number: String = _

  var code: java.util.Set[String] = _


  def this(patientId: Int,
           beginDate: Long,
           endDate: Long,
           departmentId: Int,
           doctorId: Int,
           diagnosis: String,
           number: String,
           code: java.util.Set[String]) {
    this()
    this.patientId = patientId
    this.beginDate = if (beginDate == 0) {
      null
    } else {
      new Date(beginDate)
    }
    this.endDate = if (endDate == 0) {
      null
    } else {
      new Date(endDate)
    }
    this.departmentId = departmentId
    this.doctorId = doctorId
    this.diagnosis = diagnosis
    this.number = number
    this.code = code
  }

  def toQueryStructure() = {
    var qs = new QueryDataStructure()
    if (this.patientId > 0) {
      qs.query += "AND e.patient.id = :patientId\n"
      qs.add("patientId", this.patientId: java.lang.Integer)
    }
    if (this.beginDate != null) {
      qs.query += "AND e.setDate = :beginDate\n"
      qs.add("beginDate", this.beginDate)
    }
    if (this.endDate != null) {
      qs.query += "AND e.execDate = :execDate\n"
      qs.add("endDate", this.endDate)
    }
    if (this.departmentId > 0) {
      qs.query += ("AND e.executor.orgStructure.id = :departmentId\n")
      qs.add("departmentId", this.departmentId: java.lang.Integer)
    }
    if (this.doctorId > 0) {
      qs.query += "AND e.executor.id = :doctorId\n"
      qs.add("doctorId", this.doctorId: java.lang.Integer)
    }
    /*if(this.diagnosis!=null && !this.diagnosis.isEmpty){
      qs.query += "AND upper(dia.diagnosis.mkbCode) LIKE upper(:diagnosis)\n"
      qs.add("diagnosis","%"+this.diagnosis+"%")
    }*/
    if (this.number != null && !this.number.isEmpty) {
      qs.query += "AND upper(e.externalId) LIKE upper(:number)\n"
      qs.add("number", "%" + this.number + "%")
    }
    qs.query += "AND e.eventType.code IN :code\n"
    qs.add("code", this.code)
    qs
  }

  def toSortingString(sortingField: String) = {
    sortingField match {
      case "start" | "begDate" => {
        "e.setDate"
      }
      case "end" | "endDate" => {
        "e.execDate"
      }
      case "doctor" => {
        "e.executor.lastName, e.executor.firstName, e.executor.patrName"
      }
      case "department" => {
        "e.executor.orgStructure.name"
      }
      case "number" => {
        "e.externalId"
      }
      //case "diagnosis" => {"ds.mkbCode"}
      case _ => {
        "e.id"
      }
    }
  }
}

@XmlType(name = "appealSimplifiedData")
@XmlRootElement(name = "appealSimplifiedData")
class AppealSimplifiedData {
  @BeanProperty
  var id: Int = _ //Ид обращения

  @BeanProperty
  var number: String = _ //Номер истории болезни

  @BeanProperty
  var rangeAppealDateTime: DatePeriodContainer = _ //Даты открытия и закрытия талона

  @BeanProperty
  var execPerson: ComplexPersonContainer = _ //Данные о враче и отделении

  @BeanProperty
  var diagnoses: java.util.List[DiagnosisContainer] = new LinkedList[DiagnosisContainer] // Диагнозы

  def this(event: Event, diagnoses: java.util.Map[Object, Object]) {
    this()
    this.id = event.getId.intValue()
    this.number = event.getExternalId
    this.rangeAppealDateTime = new DatePeriodContainer(event.getSetDate, event.getExecDate)
    this.execPerson = if (event.getExecutor != null) {
      new ComplexPersonContainer(event.getExecutor)
    } else {
      new ComplexPersonContainer
    }
    if (diagnoses != null || diagnoses.size() > 0)
      diagnoses.foreach(d => this.diagnoses += new DiagnosisContainer(d._1, d._2))
  }

}
