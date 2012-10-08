package ru.korus.tmis.core.data

import javax.xml.bind.annotation.{XmlRootElement, XmlType}
import reflect.BeanProperty
import ru.korus.tmis.core.entity.model._
import fd.FDRecord
import kladr.{Street, Kladr}
import scala.collection.JavaConversions._
import ru.korus.tmis.util.ConfigManager

import java.util.{Date, HashMap, LinkedList}
import org.codehaus.jackson.map.annotate.JsonView
import org.codehaus.jackson.annotate.JsonIgnoreProperties
import collection.mutable.LinkedHashSet

//Dynamic Filters
object Views {

  class DynamicFieldsStandartForm {
  }

  class DynamicFieldsPrintForm {
  }

}

class Views {}

@XmlType(name = "appealData")
@XmlRootElement(name = "appealData")
@JsonIgnoreProperties(ignoreUnknown = true)
class AppealData {
  @BeanProperty
  var requestData: AppealRequestData = _
  @BeanProperty
  var data: AppealEntry = _

  def this(appeal: Action, requestData: AppealRequestData) = {
    this()
    this.requestData = requestData
    this.data = new AppealEntry(appeal)
  }

  def this(event: Event,
           appeal: Action,
           appType: Object,
           values: java.util.Map[String, java.util.List[Object]],
           typeOfResponse: String,
           map: java.util.LinkedHashMap[java.lang.Integer, java.util.LinkedList[Kladr]],
           street: java.util.LinkedHashMap[java.lang.Integer, Street],
           requestData: AppealRequestData) {
    this()
    this.requestData = requestData
    this.data = new AppealEntry(event, appeal, appType, values, typeOfResponse, map, street)
  }

  def this(event: Event,
           appeal: Action,
           appType: Object,
           values: java.util.Map[String, java.util.List[Object]],
           aps: java.util.Map[ActionProperty, java.util.List[APValue]],
           typeOfResponse: String,
           map: java.util.LinkedHashMap[java.lang.Integer, java.util.LinkedList[Kladr]],
           street: java.util.LinkedHashMap[java.lang.Integer, Street],
           requestData: AppealRequestData) {
    this()
    this.requestData = requestData
    this.data = new AppealEntry(event, appeal, appType, values, aps, typeOfResponse, map, street)
  }
}

@XmlType(name = "appealRequestData")
@XmlRootElement(name = "appealRequestData")
@JsonIgnoreProperties(ignoreUnknown = true)
class AppealRequestData {
  @BeanProperty
  var filter: AppealRequestDataFilter = _
  @BeanProperty
  var sortingField: String = _
  @BeanProperty
  var sortingMethod: String = _
  @BeanProperty
  var limit: String = _
  @BeanProperty
  var page: String = _
  @BeanProperty
  var recordsCount: String = _

  def this(eventId: String,
           fullName: String,
           birthDate: Date,
           externalId: String,
           sortingField: String,
           sortingMethod: String,
           limit: String,
           page: String) = {
    this()
    this.filter = new AppealRequestDataFilter(eventId, fullName, birthDate, externalId)
    this.sortingField = sortingField
    this.sortingMethod = sortingMethod
    this.limit = limit
    this.page = page
  }
}

class AppealRequestDataFilter {
  @BeanProperty
  var eventId: String = _
  // — Номер обращения
  @BeanProperty
  var fullName: String = _
  // — ФИО
  @BeanProperty
  var birthDate: Date = _
  // — Дата рождения
  @BeanProperty
  var externalId: String = _ // — номер карточки пациента

  def this(eventId: String,
           fullName: String,
           birthDate: Date,
           externalId: String) = {
    this()
    this.eventId = eventId
    this.fullName = fullName
    this.birthDate = birthDate
    this.externalId = externalId
  }
}

@XmlType(name = "appealEntry")
@XmlRootElement(name = "appealEntry")
@JsonIgnoreProperties(ignoreUnknown = true)
//@JsonAutoDetect(Array(JsonMethod.FIELD))
class AppealEntry {
  @BeanProperty
  var id: Int = _
  @BeanProperty
  var number: String = _
  //Номер обращения
  @BeanProperty
  var urgent: Boolean = _
  //Срочно
  @BeanProperty
  var ambulanceNumber: String = _
  //Номер наряда СП
  @BeanProperty
  var rangeAppealDateTime: DatePeriodContainer = _
  //Дата начала и конца госпитализации
  @BeanProperty
  var patient: AnyRef = _
  @BeanProperty
  var appealType: IdNameContainer = _
  //Тип обращения
  @BeanProperty
  var agreedType: IdNameContainer = _
  //Тип согласования
  @BeanProperty
  var agreedDoctor: String = _
  //Комментарий к согласованию
  @BeanProperty
  var assignment: AppealAssignmentContainer = _
  //Назначения
  @BeanProperty
  var hospitalizationType: IdNameContainer = _
  //Госпитализация тип
  @BeanProperty
  var hospitalizationPointType: IdNameContainer = _
  //Цель госпитализации
  @BeanProperty
  var hospitalizationChannelType: IdNameContainer = _
  //Канал госпитализации
  @BeanProperty
  var hospitalizationWith: LinkedList[IdValueContainer] = new LinkedList[IdValueContainer]
  //С кем госпитализирован (законный представитель)
  @BeanProperty
  var deliveredType: String = _
  //IdNameContainer = _    //Кем доставлен
  @BeanProperty
  var deliveredAfterType: String = _
  //IdNameContainer = _    //Доставлен от начала заболевания через
  @BeanProperty
  var movingType: String = "Может идти"
  //Вид транспортировки  (!)
  @BeanProperty
  var stateType: String = _
  //IdNameContainer = _       //Состояние при поступлении
  @BeanProperty
  var physicalParameters: PhysicalParametersContainer = _
  //Физические параметры
  @BeanProperty
  var diagnoses: LinkedList[DiagnosisContainer] = new LinkedList[DiagnosisContainer]
  //Диагнозы
  @BeanProperty
  var injury: String = _ //Травма

  @BeanProperty
  var refuseAppealReason: String = _
  //Причина отказа в госпитализации
  @BeanProperty
  var appealWithDeseaseThisYear: String = _ //Госпитализирован по поводу данного заболевания в текущем году

  //TODO: временно, до выяснения судьбы поля relations
  @BeanProperty
  @JsonView(Array(classOf[Views.DynamicFieldsStandartForm]))
  var relations: LinkedList[RelationEntryContainer] = new LinkedList[RelationEntryContainer]

  @JsonView(Array(classOf[Views.DynamicFieldsPrintForm]))
  @BeanProperty
  var ward: String = _ //Diff with AppealData

  @JsonView(Array(classOf[Views.DynamicFieldsPrintForm]))
  @BeanProperty
  var totalDays: String = _ //Diff with AppealData

  def this(appeal: Action) {
    this()
  }

  def this(event: Event,
           action: Action,
           appType: Object,
           values: java.util.Map[String, java.util.List[Object]],
           typeOfResponse: String,
           map: java.util.LinkedHashMap[java.lang.Integer, java.util.LinkedList[Kladr]],
           street: java.util.LinkedHashMap[java.lang.Integer, Street]) {

    this()

    var exValue: java.util.List[Object] = null;

    //Обращение и Действие
    this.id = event.getId.intValue()
    this.number = event.getExternalId
    this.urgent = action.getIsUrgent

    exValue = this.extractValuesInNumberedMap(Set(ConfigManager.Messages("appeal.db.actionPropertyType.name.ambulanceNumber").toString), values).get("0")
    this.ambulanceNumber = exValue.get(0).asInstanceOf[String]

    this.rangeAppealDateTime = new DatePeriodContainer(action.getBegDate, action.getEndDate)

    this.patient = typeOfResponse match {
      case "standart" => {
        new IdValueContainer(event.getPatient.getId.toString)
      }
      case "print_form" => {
        new PatientEntry(event.getPatient, map, street)
      }
      case _ => {
        new IdValueContainer(event.getPatient.getId.toString)
      }
    }

    if (appType.isInstanceOf[(java.lang.Integer, String)] && appType.asInstanceOf[(java.lang.Integer, String)]._1 != null) {
      //TODO приходят нулы! спросить у Вани!
      //this.appealType = new IdNameContainer(event.getEventType.getId.intValue(), event.getEventType.getName)
      this.appealType = new IdNameContainer(appType.asInstanceOf[(java.lang.Integer, String)]._1.intValue(), appType.asInstanceOf[(java.lang.Integer, String)]._2)
    }
    //TODO мапинг по имени акшенПроперти... Если имена будут не уникальны, то нужно будет переделать
    exValue = this.extractValuesInNumberedMap(Set(ConfigManager.Messages("appeal.db.actionPropertyType.name.agreedType").toString), values).get("0")
    if (exValue.get(0).isInstanceOf[FDRecord])
      this.agreedType = new IdNameContainer(exValue.get(0).asInstanceOf[FDRecord].getId.intValue(), "")

    exValue = this.extractValuesInNumberedMap(Set(ConfigManager.Messages("appeal.db.actionPropertyType.name.agreedDoctor").toString), values).get("0")
    this.agreedDoctor = exValue.get(0).asInstanceOf[String]


    val exAssignment = this.extractValuesInNumberedMap(LinkedHashSet(ConfigManager.Messages("appeal.db.actionPropertyType.name.directed").toString,
      ConfigManager.Messages("appeal.db.actionPropertyType.name.number").toString,
      ConfigManager.Messages("appeal.db.actionPropertyType.name.assignmentDate").toString,
      ConfigManager.Messages("appeal.db.actionPropertyType.name.doctor").toString
    ), values)
    var assignmentDate: Date = null
    if (exAssignment.get("2").get(0).isInstanceOf[Date]) {
      assignmentDate = exAssignment.get("2").get(0).asInstanceOf[Date]
    }

    this.assignment = new AppealAssignmentContainer(assignmentDate,
      exAssignment.get("1").get(0).asInstanceOf[String],
      exAssignment.get("0").get(0).asInstanceOf[String],
      exAssignment.get("3").get(0).asInstanceOf[String]
    )

    exValue = this.extractValuesInNumberedMap(Set(ConfigManager.Messages("appeal.db.actionPropertyType.name.hospitalizationType").toString), values).get("0")
    if (exValue.get(0).isInstanceOf[FDRecord])
      this.hospitalizationType = new IdNameContainer(exValue.get(0).asInstanceOf[FDRecord].getId.intValue(), "")

    exValue = this.extractValuesInNumberedMap(Set(ConfigManager.Messages("appeal.db.actionPropertyType.name.hospitalizationPointType").toString), values).get("0")
    if (exValue.get(0).isInstanceOf[FDRecord])
      this.hospitalizationPointType = new IdNameContainer(exValue.get(0).asInstanceOf[FDRecord].getId.intValue(), "")

    exValue = this.extractValuesInNumberedMap(Set(ConfigManager.Messages("appeal.db.actionPropertyType.name.hospitalizationChannelType").toString), values).get("0")
    if (exValue.get(0).isInstanceOf[Organisation]) {
      val organisationChannel = exValue.get(0).asInstanceOf[Organisation]
      this.hospitalizationChannelType = new IdNameContainer(organisationChannel.getId.intValue(), organisationChannel.getShortName)
    }
    else
      this.hospitalizationChannelType = new IdNameContainer(-1, exValue.get(0).asInstanceOf[String])

    val exWith = this.extractValuesInNumberedMap(Set(ConfigManager.Messages("appeal.db.actionPropertyType.name.hospitalizationWith").toString), values).get("0")
    exWith.foreach(e => {
      if (e.isInstanceOf[java.lang.Integer] && e.asInstanceOf[java.lang.Integer] != null)
        this.hospitalizationWith += new IdValueContainer(e.asInstanceOf[java.lang.Integer].toString)
    })

    exValue = this.extractValuesInNumberedMap(Set(ConfigManager.Messages("appeal.db.actionPropertyType.name.deliveredType").toString), values).get("0")
    this.deliveredType = exValue.get(0).asInstanceOf[String]

    exValue = this.extractValuesInNumberedMap(Set(ConfigManager.Messages("appeal.db.actionPropertyType.name.deliveredAfterType").toString), values).get("0")
    this.deliveredAfterType = exValue.get(0).asInstanceOf[String]

    exValue = this.extractValuesInNumberedMap(Set(ConfigManager.Messages("appeal.db.actionPropertyType.name.stateType").toString), values).get("0")
    this.stateType = exValue.get(0).asInstanceOf[String]

    val exPhysical = this.extractValuesInNumberedMap(LinkedHashSet(ConfigManager.Messages("appeal.db.actionPropertyType.name.height").toString,
      ConfigManager.Messages("appeal.db.actionPropertyType.name.weight").toString,
      ConfigManager.Messages("appeal.db.actionPropertyType.name.temperature").toString,
      ConfigManager.Messages("appeal.db.actionPropertyType.name.bloodPressure.left").toString,
      ConfigManager.Messages("appeal.db.actionPropertyType.name.bloodPressure.right").toString
    ), values)

    var d1, d2, d3: Double = 0.0
    if (exPhysical.get("0").get(0).isInstanceOf[Double]) {
      d1 = (exPhysical.get("0").get(0).asInstanceOf[Double])
    }
    if (exPhysical.get("1").get(0).isInstanceOf[Double]) {
      d2 = (exPhysical.get("1").get(0).asInstanceOf[Double])
    }
    if (exPhysical.get("2").get(0).isInstanceOf[Double]) {
      d3 = (exPhysical.get("2").get(0).asInstanceOf[Double])
    }

    this.physicalParameters = new PhysicalParametersContainer(d1,
      d2,
      d3,
      exPhysical.get("3").get(0).asInstanceOf[String],
      exPhysical.get("4").get(0).asInstanceOf[String]
    )

    val exDiagnosis = this.extractValuesInNumberedMap(LinkedHashSet(
      ConfigManager.Messages("appeal.db.actionPropertyType.name.diagnosis.assigment.code").toString,
      ConfigManager.Messages("appeal.db.actionPropertyType.name.diagnosis.aftereffect.code").toString,
      ConfigManager.Messages("appeal.db.actionPropertyType.name.diagnosis.attendant.code").toString,
      ConfigManager.Messages("appeal.db.actionPropertyType.name.diagnosis.assignment.description").toString,
      ConfigManager.Messages("appeal.db.actionPropertyType.name.diagnosis.aftereffect.description").toString,
      ConfigManager.Messages("appeal.db.actionPropertyType.name.diagnosis.attendant.description").toString
    ), values)

    Set("assignment", "aftereffect", "attendant").foreach(pos => {
      val key = "code_%s".format(pos)
      val key_desc = "description_%s".format(pos)
      var i: Int = 0
      if (exDiagnosis.get(key) != null && exDiagnosis.get(key).size() > 0) {
        exDiagnosis.get(key).foreach(diagnosis => {
          if (diagnosis.isInstanceOf[Mkb]) {
            var description: String = ""
            if (exDiagnosis.get(key_desc) != null && exDiagnosis.get(key_desc).size() >= i) {
              description = exDiagnosis.get(key_desc).get(i).asInstanceOf[String]
            }

            this.diagnoses += new DiagnosisContainer(pos,
              description,
              "",
              diagnosis.asInstanceOf[Mkb])
          }
          i = i + 1
        })
        i = 0
      }
    })

    exValue = this.extractValuesInNumberedMap(Set(ConfigManager.Messages("appeal.db.actionPropertyType.name.injury").toString), values).get("0")
    this.injury = exValue.get(0).asInstanceOf[String]

    exValue = this.extractValuesInNumberedMap(Set(ConfigManager.Messages("appeal.db.actionPropertyType.name.refuseAppealReason").toString), values).get("0")
    this.refuseAppealReason = exValue.get(0).asInstanceOf[String]

    exValue = this.extractValuesInNumberedMap(Set(ConfigManager.Messages("appeal.db.actionPropertyType.name.appealWithDeseaseThisYear").toString), values).get("0")
    this.appealWithDeseaseThisYear = exValue.get(0).asInstanceOf[String]

  }

  def this(event: Event,
           action: Action,
           appType: Object,
           values: java.util.Map[String, java.util.List[Object]],
           aps: java.util.Map[ActionProperty, java.util.List[APValue]],
           typeOfResponse: String,
           map: java.util.LinkedHashMap[java.lang.Integer, java.util.LinkedList[Kladr]],
           street: java.util.LinkedHashMap[java.lang.Integer, Street]) = {
    this(event, action, appType, values, typeOfResponse, map, street)
    if (aps != null && aps.size > 0) {
      aps.foreach(c => {
        val (ap, apvs) = c
        ap.getType.getName match {
          case "Переведен в отделение" => {
            this.ward = apvs.size() match {
              case 0 => ""
              case size => {
                val place = apvs.get(0).getValue
                if (place.isInstanceOf[OrgStructure]) {
                  place.asInstanceOf[OrgStructure].getName
                }
                else ""
              }
            }
            val msecInDay = 1000 * 60 * 60 * 24
            val beginDate = ap.getAction.getBegDate.getTime
            val nowDate = (new Date()).getTime
            val diffOfDays = (nowDate - beginDate) / msecInDay + 1
            this.totalDays = "Проведено %d койко-дней".format(diffOfDays)
          }
          case _ => {}
        }
      })
    }
    //this.totalDays

  }

  //Достаем мапу значений под контейнер
  private def extractValuesInNumberedMap(set: java.util.Set[String], values: java.util.Map[String, java.util.List[Object]]) = {

    var map: java.util.Map[String, java.util.List[Object]] = new HashMap[String, java.util.List[Object]]

    var counter: Int = 0
    set.foreach(e => {
      if (values.containsKey(e)) {
        var dStr: String = null
        if (e.compareTo(ConfigManager.Messages("appeal.db.actionPropertyType.name.diagnosis.assigment.code").toString) == 0) {
          dStr = "code_assignment"
        }
        else if (e.compareTo(ConfigManager.Messages("appeal.db.actionPropertyType.name.diagnosis.aftereffect.code").toString) == 0) {
          dStr = "code_aftereffect"
        }
        else if (e.compareTo(ConfigManager.Messages("appeal.db.actionPropertyType.name.diagnosis.attendant.code").toString) == 0) {
          dStr = "code_attendant"
        }
        else if (e.compareTo(ConfigManager.Messages("appeal.db.actionPropertyType.name.diagnosis.assignment.description").toString) == 0) {
          dStr = "description_assignment"
        }
        else if (e.compareTo(ConfigManager.Messages("appeal.db.actionPropertyType.name.diagnosis.aftereffect.description").toString) == 0) {
          dStr = "description_aftereffect"
        }
        else if (e.compareTo(ConfigManager.Messages("appeal.db.actionPropertyType.name.diagnosis.attendant.description").toString) == 0) {
          dStr = "description_attendant"
        }
        else dStr = counter.toString
        map.put(dStr, values.get(e))
      }
      else {
        map.put(counter.toString, List(""))
      }
      counter = counter + 1
    })
    map
  }
}

@XmlType(name = "diagnosisContainer")
@XmlRootElement(name = "diagnosisContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class DiagnosisContainer {
  @BeanProperty
  var diagnosisKind: String = _
  @BeanProperty
  var description: String = _
  @BeanProperty
  var injury: String = _
  @BeanProperty
  var mkb: MKBContainer = new MKBContainer()

  def this(diagnosisKind: String, description: String, injury: String, mkb: Mkb) {
    this()
    this.diagnosisKind = diagnosisKind
    this.description = description
    this.injury = injury
    this.mkb = new MKBContainer(mkb)
  }

  def this(diagnosis: Diagnostic, mkb: Mkb) {
    this()
    if (diagnosis != null) {
      this.diagnosisKind = diagnosis.getDiagnosisType.getName
      this.description = diagnosis.getNotes
      this.injury = if (diagnosis.getTraumaType != null) {
        diagnosis.getTraumaType.getName
      } else {
        ""
      }
    }
    if (mkb != null) {
      this.mkb = new MKBContainer(mkb)
    }
  }

  def this(diagnosis: Object, mkb: Object) {
    this()
    if (diagnosis != null) {
      if (diagnosis.isInstanceOf[Diagnostic]) {
        this.diagnosisKind = diagnosis.asInstanceOf[Diagnostic].getDiagnosisType.getName
        this.description = diagnosis.asInstanceOf[Diagnostic].getNotes
        this.injury = if (diagnosis.asInstanceOf[Diagnostic].getTraumaType != null) {
          diagnosis.asInstanceOf[Diagnostic].getTraumaType.getName
        } else {
          ""
        }
      } else if (diagnosis.isInstanceOf[ActionProperty]) {
        this.diagnosisKind = diagnosis.asInstanceOf[ActionProperty].getAction.getActionType.getCode match {
          case "4501" => "клинический"
          case "1_1_01" => "при поступлении"
          case "4201" => "направительный"
          case _ => ""
        }
        //TODO: Нужно или нет передавать description и injury в этом случае???
      }
    }
    if (mkb != null) {
      if (mkb.isInstanceOf[Mkb]) {
        this.mkb = new MKBContainer(mkb.asInstanceOf[Mkb])
      } else if (mkb.isInstanceOf[String]) {
        this.mkb = new MKBContainer("", mkb.asInstanceOf[String])
      }
    }
  }

  def toMap = {
    var map = new java.util.HashMap[String, Object]
    map.put("diagnosisKind", this.diagnosisKind)
    map.put("description", this.description)
    map.put("injury", this.injury)
    map.put("mkb", this.mkb.toMap)
    map
  }
}

@XmlType(name = "physicalParametersContainer")
@XmlRootElement(name = "physicalParametersContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class PhysicalParametersContainer {
  @BeanProperty
  var height: Double = _
  @BeanProperty
  var weight: Double = _
  @BeanProperty
  var temperature: Double = _
  @BeanProperty
  var bloodPressure: RangeLeftRightContainer = new RangeLeftRightContainer()

  def this(height: Double,
           weight: Double,
           temperature: Double,
           bloodPressureLeft: String,
           bloodPressureRight: String) {
    this()
    this.height = height
    this.weight = weight
    this.temperature = temperature
    this.bloodPressure = new RangeLeftRightContainer(bloodPressureLeft, bloodPressureRight)
  }

  def toMap = {
    var map = new java.util.HashMap[String, Object]
    map.put("height", this.height.toString)
    map.put("weight", this.weight.toString)
    map.put("temperature", this.temperature.toString)
    map.put("bloodPressure", this.bloodPressure.toMap)
    map
  }
}

@XmlType(name = "appealAssignmentContainer")
@XmlRootElement(name = "appealAssignmentContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class AppealAssignmentContainer {
  @BeanProperty
  var assignmentDate: Date = null
  @BeanProperty
  var number: String = null
  @BeanProperty
  var directed: String = _
  //IdNameContainer = new IdNameContainer()
  @BeanProperty
  var doctor: String = _ //DoctorContainer = new DoctorContainer()

  def this(assignmentDate: Date,
           number: String,
           directedName: String,
           doctor: Staff) {
    this()
    this.assignmentDate = assignmentDate
    this.number = number
    this.directed = directedName
    //if(doctor!=null) {
    //  this.doctor =  new DoctorContainer(doctor)
    //}
  }

  def this(assignmentDate: Date,
           number: String,
           directedName: String,
           doctor: String) {
    this()
    this.assignmentDate = assignmentDate
    this.number = number
    this.directed = directedName
    this.doctor = doctor
  }

  def toMap = {
    var map = new java.util.HashMap[String, Object]
    map.put("assignmentDate", this.assignmentDate)
    map.put("number", this.number)
    map.put("directed", this.directed)
    map.put("doctor", this.doctor)
    map
  }
}

@XmlType(name = "doctorContainer")
@XmlRootElement(name = "doctorContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class DoctorContainer {
  @BeanProperty
  var id: Int = _
  @BeanProperty
  var name: PersonNameContainer = new PersonNameContainer()

  def this(staff: Staff) {
    this()
    if (staff != null) {
      this.id = staff.getId.intValue()
      this.name = new PersonNameContainer(staff)
    }
  }

  def this(fullName: String) {
    this()
    this.id = 0
    this.name = new PersonNameContainer(fullName)
  }

  def toMap = {
    var map = new java.util.HashMap[String, Object]
    map.put("id", this.id.toString)
    map.put("name", this.name.toMap)
    map
  }
}

@XmlType(name = "mKBContainer")
@XmlRootElement(name = "mKBContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class MKBContainer {
  @BeanProperty
  var code: String = _
  @BeanProperty
  var diagnosis: String = _

  def this(code: String, diagnosis: String) {
    this()
    this.code = code
    this.diagnosis = diagnosis
  }

  def this(mkb: Mkb) {
    this()
    this.code = mkb.getDiagID
    this.diagnosis = mkb.getDiagName
  }

  def toMap = {
    var map = new java.util.HashMap[String, Object]
    map.put("code", this.code)
    map.put("diagnosis", this.diagnosis)
    map
  }
}
