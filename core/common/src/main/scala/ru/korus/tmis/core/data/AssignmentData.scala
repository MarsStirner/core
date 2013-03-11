package ru.korus.tmis.core.data

import javax.xml.bind.annotation.{XmlRootElement, XmlType}
import javax.xml.bind.annotation.XmlType._
import javax.xml.bind.annotation.XmlRootElement._
import org.codehaus.jackson.annotate.JsonIgnoreProperties
import reflect.BeanProperty
import ru.korus.tmis.core.entity.model._
import java.util.{LinkedList, Date}
import ru.korus.tmis.util.ConfigManager
import scala.collection.JavaConversions._
import ru.korus.tmis.core.entity.model.fd.{FDField, FDFieldValue, FDRecord}

@XmlType(name = "assignmentData")
@XmlRootElement(name = "assignmentData")
@JsonIgnoreProperties(ignoreUnknown = true)
class AssignmentData {

  @BeanProperty
  var requestData: AssignmentRequestData = _
  @BeanProperty
  var data: AssignmentEntry = _

  def this(action: Action,
           values: java.util.Map[ActionProperty, java.util.List[APValue]],
           corrMap: java.util.HashMap[String, java.util.List[RbCoreActionProperty]],
           requestData: AssignmentRequestData) = {
    this()
    this.requestData = requestData
    this.data = new AssignmentEntry(action, values, corrMap)
  }
}

@XmlType(name = "assignmentRequestData")
@XmlRootElement(name = "assignmentRequestData")
@JsonIgnoreProperties(ignoreUnknown = true)
class AssignmentRequestData {
  @BeanProperty
  var filter: AssignmentRequestDataFilter = _
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

  def this(filter: AssignmentRequestDataFilter,
           sortingField: String,
           sortingMethod: String,
           limit: String,
           page: String) = {
    this()
    this.filter = filter
    this.sortingField = sortingField
    this.sortingMethod = sortingMethod
    this.limit = limit
    this.page = page
  }
}

class AssignmentRequestDataFilter {

}

@XmlType(name = "assignmentEntry")
@XmlRootElement(name = "assignmentEntry")
@JsonIgnoreProperties(ignoreUnknown = true)
class AssignmentEntry {

  //Action cells

  @BeanProperty
  var id: Int = _
  //Идентификатор
  @BeanProperty
  var assignmentType: IdNameContainer = _
  //Тип действия
  @BeanProperty
  var rangeDateTime: DatePeriodContainer = _
  //Дата начала и конца назначения
  @BeanProperty
  var assignmentDoctor: PersonNameContainer = _
  //Тип действия
  @BeanProperty
  var urgent: Boolean = _ //Срочность

  //ActionProperty cells

  @BeanProperty
  var injectionMethod: String = _
  //Метод введения
  @BeanProperty
  var source: FlatDirectoryContainer = _
  //Источник
  @BeanProperty
  var description: RlsContainer = _
  //Наименование
  @BeanProperty
  var injectionSpeed: String = _
  //Скорость введения
  @BeanProperty
  var dropOilierNumber: Int = _
  //Номер капельницы
  @BeanProperty
  var dosage: String = _
  //Доза
  @BeanProperty
  var quantity: Int = _
  //Количество
  @BeanProperty
  var units: String = _
  //Единицы
  @BeanProperty
  var assignmentScheme: FlatDirectoryContainer = _
  //Схема назначения
  @BeanProperty
  var medication: DatePeriodContainer = _
  //Прием (Начало/Конец)
  @BeanProperty
  var weekDays: java.util.LinkedList[Int] = new java.util.LinkedList[Int]
  //Дни недели
  @BeanProperty
  var hospitalizationDays: java.util.LinkedList[Int] = new java.util.LinkedList[Int]
  //Дни госпитализации
  @BeanProperty
  var allDays: Int = _
  //Дней
  @BeanProperty
  var afterDays: Int = _
  //Через дней
  @BeanProperty
  var onceDaily: Int = _
  //Раз в день
  @BeanProperty
  var medicationTimes: java.util.LinkedList[Date] = new java.util.LinkedList[Date]
  //Время приема
  @BeanProperty
  var admissibleChange: RlsContainer = _
  //Допустимость замены
  @BeanProperty
  var medicationMethod: FlatDirectoryContainer = _
  //Способ приема
  @BeanProperty
  var paymentType: FlatDirectoryContainer = _
  //Вид оплаты
  @BeanProperty
  var departmentHead: PersonIdNameContainer = _
  //Заведующий отделением
  @BeanProperty
  var note: String = _

  //Комментарий

  private class IndexOf[T](seq: Seq[T]) {
    def unapply(pos: T) = seq find (pos ==) map (seq indexOf _)
  }

  final val list = List(ConfigManager.Messages("db.actionPropertyType.assignment.name.injectionMethod").toInt,
    ConfigManager.Messages("db.actionPropertyType.assignment.name.source").toInt,
    ConfigManager.Messages("db.actionPropertyType.assignment.name.description").toInt,
    ConfigManager.Messages("db.actionPropertyType.assignment.name.injectionSpeed").toInt,
    ConfigManager.Messages("db.actionPropertyType.assignment.name.dropOilierNumber").toInt,
    ConfigManager.Messages("db.actionPropertyType.assignment.name.dosage").toInt,
    ConfigManager.Messages("db.actionPropertyType.assignment.name.quantity").toInt,
    ConfigManager.Messages("db.actionPropertyType.assignment.name.units").toInt,
    ConfigManager.Messages("db.actionPropertyType.assignment.name.assignmentScheme").toInt,
    ConfigManager.Messages("db.actionPropertyType.assignment.name.medicationBegin").toInt,
    ConfigManager.Messages("db.actionPropertyType.assignment.name.medicationEnd").toInt,
    ConfigManager.Messages("db.actionPropertyType.assignment.name.weekDays").toInt,
    ConfigManager.Messages("db.actionPropertyType.assignment.name.hospitalizationDays").toInt,
    ConfigManager.Messages("db.actionPropertyType.assignment.name.allDays").toInt,
    ConfigManager.Messages("db.actionPropertyType.assignment.name.afterDays").toInt,
    ConfigManager.Messages("db.actionPropertyType.assignment.name.onceDaily").toInt,
    ConfigManager.Messages("db.actionPropertyType.assignment.name.medicationTimes").toInt,
    ConfigManager.Messages("db.actionPropertyType.assignment.name.admissibleChange").toInt,
    ConfigManager.Messages("db.actionPropertyType.assignment.name.medicationMethod").toInt,
    ConfigManager.Messages("db.actionPropertyType.assignment.name.paymentType").toInt,
    ConfigManager.Messages("db.actionPropertyType.assignment.name.departmentHead").toInt,
    ConfigManager.Messages("db.actionPropertyType.assignment.name.note").toInt)

  def this(action: Action,
           values: java.util.Map[ActionProperty, java.util.List[APValue]],
           corrMap: java.util.HashMap[String, java.util.List[RbCoreActionProperty]]) {
    this()
    this.id = action.getId.intValue()
    this.assignmentType = new IdNameContainer(action.getActionType.getId.intValue(), action.getActionType.getName)
    this.assignmentDoctor = new PersonNameContainer(action.getCreatePerson)
    this.rangeDateTime = new DatePeriodContainer(action.getBegDate, action.getEndDate)
    this.urgent = action.getIsUrgent

    if (values != null) {
      val listNdx = new IndexOf(list)

      corrMap.get(action.getActionType.getId.toString).foreach((coreAPT) => {
        if (coreAPT.getActionPropertyType != null) {
          val apValues = this.findValueListByActionPropertyTypeId(values, coreAPT.getActionPropertyType.getId.intValue())
          if (apValues.size > 0) {
            coreAPT.getId.intValue() match {
              case listNdx(0) => this.injectionMethod = apValues.get(0).getValueAsString //Метод введения
              case listNdx(1) => this.source = new FlatDirectoryContainer(apValues.get(0).getValueAsString.toInt, apValues.get(0).asInstanceOf[APValueFlatDirectory].getValue.getFieldValues.toList) //Источник
              case listNdx(2) => this.description = new RlsContainer(apValues.get(0).unwrap.getValueAsId.toInt,
                apValues.get(0).getValue().asInstanceOf[Nomenclature].getTradeName,
                apValues.get(0).getValue().asInstanceOf[Nomenclature].getForm) //Наименование
              case listNdx(3) => this.injectionSpeed = apValues.get(0).getValueAsString //Скорость введения
              case listNdx(4) => this.dropOilierNumber = apValues.get(0).getValueAsString.toInt //Номер капельницы
              case listNdx(5) => this.dosage = apValues.get(0).getValueAsString //Доза
              case listNdx(6) => this.quantity = apValues.get(0).getValueAsString.toInt //Количество
              case listNdx(7) => this.units = apValues.get(0).getValueAsString //Единицы
              case listNdx(8) => this.assignmentScheme = new FlatDirectoryContainer(apValues.get(0).getValueAsString.toInt, apValues.get(0).asInstanceOf[APValueFlatDirectory].getValue.getFieldValues.toList) //Схема назначения
              case listNdx(9) => {
                //Начало приема
                if (this.medication == null)
                  this.medication = new DatePeriodContainer(apValues.get(0).asInstanceOf[APValueDate].getValue, null)
                else
                  this.medication.start = apValues.get(0).asInstanceOf[APValueDate].getValue
              }
              case listNdx(10) => {
                //Конец приема
                if (this.medication == null)
                  this.medication = new DatePeriodContainer(null, apValues.get(0).asInstanceOf[APValueDate].getValue)
                else
                  this.medication.end = apValues.get(0).asInstanceOf[APValueDate].getValue
              }
              case listNdx(11) => {
                //Дни недели
                this.weekDays = new java.util.LinkedList[Int]
                apValues.foreach(value => this.weekDays.add(value.getValueAsString.toInt))
              }
              case listNdx(12) => {
                //Дни госпитализации
                this.hospitalizationDays = new java.util.LinkedList[Int]
                apValues.foreach(value => this.hospitalizationDays.add(value.getValueAsString.toInt))
              }
              case listNdx(13) => this.allDays = apValues.get(0).getValueAsString.toInt //Дней
              case listNdx(14) => this.afterDays = apValues.get(0).getValueAsString.toInt //Через дней
              case listNdx(15) => this.onceDaily = apValues.get(0).getValueAsString.toInt //Раз в день
              case listNdx(16) => {
                //Время приема
                this.medicationTimes = new java.util.LinkedList[Date]
                apValues.foreach(value => this.medicationTimes.add(value.asInstanceOf[APValueTime].getValue))
              }
              case listNdx(17) => this.admissibleChange = new RlsContainer(apValues.get(0).unwrap.getValueAsId.toInt,
                apValues.get(0).getValue().asInstanceOf[Nomenclature].getTradeName,
                apValues.get(0).getValue().asInstanceOf[Nomenclature].getForm) //Допустимость замены
              case listNdx(18) => this.medicationMethod = new FlatDirectoryContainer(apValues.get(0).getValueAsString.toInt, apValues.get(0).asInstanceOf[APValueFlatDirectory].getValue.getFieldValues.toList) //Способ приема
              case listNdx(19) => this.paymentType = new FlatDirectoryContainer(apValues.get(0).getValueAsString.toInt, apValues.get(0).asInstanceOf[APValueFlatDirectory].getValue.getFieldValues.toList) //Вид оплаты                                                             //Назначивший врач
              case listNdx(20) => this.departmentHead = new PersonIdNameContainer(apValues.get(0).asInstanceOf[APValuePerson].getValue) //Заведующий отделением
              case listNdx(21) => this.note = apValues.get(0).getValueAsString //Комментарий

              case _ => null
            }
          }
        }
      })
    }
  }

  private def findValueListByActionPropertyTypeId(values: java.util.Map[ActionProperty, java.util.List[APValue]],
                                                  aptId: Int): List[APValue] = {
    val result = values.find {
      element => element._1.getType.getId.intValue() == aptId
    }
    val res = result.getOrElse(null)
    if (res != null && res._2 != null) {
      res._2.toList
    } else {
      List.empty[APValue]
    }
  }
}

@XmlType(name = "flatDirectoryContainer")
@XmlRootElement(name = "flatDirectoryContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class FlatDirectoryContainer {

  @BeanProperty
  var id: Int = _
  //Идентификатор
  @BeanProperty
  var values: java.util.LinkedList[FDValueContainer] = _ //Наименование

  def this(id: Int, list: List[FDFieldValue]) = {
    this()
    this.id = id
    this.values = new java.util.LinkedList[FDValueContainer]
    list.foreach(f => this.values.add(new FDValueContainer(f.getFDField, f.getValue)))
  }
}

@XmlType(name = "fDValueContainer")
@XmlRootElement(name = "fDValueContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class FDValueContainer {

  @BeanProperty
  var field: IdNameContainer = _
  //Идентификатор
  @BeanProperty
  var value: String = _ //Наименование

  def this(field: FDField, value: String) = {
    this()
    this.field = new IdNameContainer(field.getId.intValue(), field.getName)
    this.value = value
  }
}

@XmlType(name = "rlsContainer")
@XmlRootElement(name = "rlsContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class RlsContainer {

  @BeanProperty
  var id: Int = _
  //Идентификатор
  @BeanProperty
  var name: String = _
  //Наименование
  @BeanProperty
  var unit: String = _ //Наименование

  def this(id: Int, name: String, unit: String) = {
    this()
    this.id = id
    this.name = name
    this.unit = unit
  }
}

@XmlType(name = "personIdNameContainer")
@XmlRootElement(name = "personIdNameContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class PersonIdNameContainer {

  @BeanProperty
  var id: Int = _
  @BeanProperty
  var first: String = _
  @BeanProperty
  var last: String = _
  @BeanProperty
  var middle: String = _
  @BeanProperty
  var raw: String = _

  def this(person: Staff) = {
    this()
    this.id = person.getId.intValue()
    this.first = person.getFirstName()
    this.last = person.getLastName()
    this.middle = person.getPatrName()
    this.raw = this.last + " " + this.first + " " + this.middle
  }
}

@XmlType(name = "assignmentsToRemoveDataList")
@XmlRootElement(name = "assignmentsToRemoveDataList")
class AssignmentsToRemoveDataList {
  @BeanProperty
  var data: LinkedList[AssignmentToRemoveDataEntry] = new LinkedList[AssignmentToRemoveDataEntry]

  def this(values: LinkedList[Int]) {
    this()
    values.foreach(f => this.data += new AssignmentToRemoveDataEntry(f))
  }
}

@XmlType(name = "assignmentToRemoveDataEntry")
@XmlRootElement(name = "assignmentToRemoveDataEntry")
class AssignmentToRemoveDataEntry {

  @BeanProperty
  var id: Int = _

  def this(assignmentId: Int) {
    this()
    this.id = assignmentId
  }
}
