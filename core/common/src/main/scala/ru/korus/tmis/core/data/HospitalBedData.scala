package ru.korus.tmis.core.data

import javax.xml.bind.annotation.{XmlType, XmlRootElement}
import reflect.BeanProperty
import ru.korus.tmis.core.entity.model._
import org.codehaus.jackson.annotate.JsonIgnoreProperties
import org.codehaus.jackson.map.annotate.JsonView
import java.util.{Calendar, Date}
import ru.korus.tmis.util.ConfigManager
import scala.collection.JavaConversions._

object HospitalBedViews {

  class MoveView {}

  class MovesListView {}

  class RegistrationView {}

  class RegistrationFormView {}

}

class HospitalBedViews {}

@XmlType(name = "hospitalBedData")
@XmlRootElement(name = "hospitalBedData")
class HospitalBedData {

  @BeanProperty
  var requestData: HospitalBedDataRequest = _
  @BeanProperty
  var data: HospitalBedEntry = _

  //Конструктор ед. значения
  def this(action: Action,
           values: java.util.Map[ActionProperty, java.util.List[APValue]],
           beds: java.util.Map[java.lang.Integer, java.lang.Boolean],
           corrMap: java.util.HashMap[String, java.util.List[RbCoreActionProperty]],
           requestData: HospitalBedDataRequest) = {
    this()
    this.requestData = requestData
    this.data = new HospitalBedEntry(action, values, beds, corrMap)
  }

  //Конструктор для листа
  def this(map: java.util.LinkedHashMap[Action, java.util.Map[ActionProperty, java.util.List[APValue]]],
           corrMap: java.util.HashMap[String, java.util.List[RbCoreActionProperty]],
           requestData: HospitalBedDataRequest) = {
    this()
    this.requestData = requestData
    this.data = new HospitalBedEntry(map, corrMap)
  }
}

@XmlType(name = "hospitalBedRequestData")
@XmlRootElement(name = "hospitalBedRequestData")
class HospitalBedDataRequest {
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

    if (this.filter.isInstanceOf[HospitalBedDataFilter])
      this.sortingFieldInternal = this.filter.asInstanceOf[HospitalBedDataFilter].toSortingString(this.sortingField)
    else if (this.filter.isInstanceOf[HospitalBedDataListFilter])
      this.sortingFieldInternal = this.filter.asInstanceOf[HospitalBedDataListFilter].toSortingString(this.sortingField)


    this.sortingMethod = sortingMethod match {
      case null => {
        "asc"
      }
      case _ => {
        sortingMethod
      }
    }
    this.limit = limit
    this.page = page
    this.coreVersion = ConfigManager.Messages("misCore.assembly.version")
  }
}

@XmlType(name = "hospitalBedDataFilter")
@XmlRootElement(name = "hospitalBedDataFilter")
class HospitalBedDataFilter {

  def toQueryStructure() = {
    var qs = new QueryDataStructure()
    qs
  }

  def toSortingString(sortingField: String) = {
    /*val sortingFieldInternal = sortingField match {
      case _ => {"mkb.id"}
    }
    sortingFieldInternal */
    ""
  }
}

@XmlType(name = "hospitalBedDataListFilter")
@XmlRootElement(name = "hospitalBedDataListFilter")
class HospitalBedDataListFilter {

  @BeanProperty
  var eventId: Int = _

  def this(eventId: Int) {
    this()
    this.eventId = eventId
  }

  def toQueryStructure() = {
    var qs = new QueryDataStructure()

    if (this.eventId > 0) {
      qs.query += ("AND a.event.id =  :eventId\n")
      qs.add("eventId", this.eventId: java.lang.Integer)
    }
    qs
  }

  def toSortingString(sortingField: String) = {
    sortingField match {
      case _ => {
        "a.createDatetime"
      }
    }
  }
}

@XmlType(name = "hospitalBedEntry")
@XmlRootElement(name = "hospitalBedEntry")
@JsonIgnoreProperties(ignoreUnknown = true)
class HospitalBedEntry {

  @JsonView(Array(classOf[HospitalBedViews.MoveView]))
  @BeanProperty
  var move: MoveHospitalBedContainer = _

  @JsonView(Array(classOf[HospitalBedViews.MovesListView]))
  @BeanProperty
  var moves: java.util.LinkedList[MovesListHospitalBedContainer] = new java.util.LinkedList[MovesListHospitalBedContainer]

  @JsonView(Array(classOf[HospitalBedViews.RegistrationView]))
  @BeanProperty
  var bedRegistration: RegistrationHospitalBedContainer = _

  @JsonView(Array(classOf[HospitalBedViews.RegistrationFormView]))
  @BeanProperty
  var registrationForm: RegistrationHospitalBedContainer = _

  def this(action: Action,
           values: java.util.Map[ActionProperty, java.util.List[APValue]],
           beds: java.util.Map[java.lang.Integer, java.lang.Boolean],
           corrMap: java.util.HashMap[String, java.util.List[RbCoreActionProperty]]) = {
    this()
    this.move = new MoveHospitalBedContainer(action, values, corrMap)
    this.bedRegistration = new RegistrationHospitalBedContainer(action, values, beds, corrMap)
    this.registrationForm = new RegistrationHospitalBedContainer(action, values, beds, corrMap)
  }

  def this(map: java.util.LinkedHashMap[Action, java.util.Map[ActionProperty, java.util.List[APValue]]],
           corrMap: java.util.HashMap[String, java.util.List[RbCoreActionProperty]]) = {
    this()
    map.foreach(action => {
      this.moves.add(new MovesListHospitalBedContainer(action._1, action._2, corrMap))
    })
  }

}

@XmlType(name = "moveHospitalBedContainer")
@XmlRootElement(name = "moveHospitalBedContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class MoveHospitalBedContainer {
  @BeanProperty
  var clientId: Int = _
  @BeanProperty
  var unitId: Int = _
  @BeanProperty
  var moveDatetime: Date = _

  private val unknownOperation = 0
  private val directionInDepartment = 1
  //Направление в отделение
  private val movingInDepartment = 2

  //Перевод в отделение

  private class IndexOf[T](seq: Seq[T]) {
    def unapply(pos: T) = seq find (pos ==) map (seq indexOf _)
  }

  def this(action: Action,
           values: java.util.Map[ActionProperty, java.util.List[APValue]],
           corrMap: java.util.HashMap[String, java.util.List[RbCoreActionProperty]]) {
    this()
    this.clientId = action.getEvent.getPatient.getId.intValue()
    val flgAction = if (action.getActionType.getId.compareTo(ConfigManager.Messages("db.actionType.moving").toInt) == 0)
      movingInDepartment
    else if (action.getActionType.getId.compareTo(ConfigManager.Messages("db.actionType.hospitalization.primary").toInt) == 0)
      directionInDepartment
    else unknownOperation

    if (values != null && flgAction != unknownOperation) {
      val list = if (flgAction == movingInDepartment) List(ConfigManager.Messages("db.actionPropertyType.moving.name.movedIn").toString,
        ConfigManager.Messages("db.actionPropertyType.moving.name.beginTime").toString)
      else List(ConfigManager.Messages("db.actionPropertyType.hospitalization.name.movedIn").toString)

      val listNdx = new IndexOf(list)

      corrMap.get(action.getActionType.getId.toString).foreach((coreAPT) => {
        coreAPT.getName match {
          case listNdx(0) => {
            //Переведен в отделение
            val result = values.find {
              element => element._1.getType.getId.intValue() == coreAPT.getActionPropertyType.getId
            }
            val res = result.getOrElse(null)
            if (res != null && res._2 != null && res._2.size() > 0) {
              this.unitId = res._2.get(0).asInstanceOf[APValueOrgStructure].getValue.getId.intValue()
            }
            if (flgAction != movingInDepartment) this.moveDatetime = action.getBegDate
          }
          case listNdx(1) => {
            //Время поступления
            if (action.getBegDate != null) {
              var tDate = Calendar.getInstance()
              tDate.setTime(action.getBegDate)

              val result = values.find {
                element => element._1.getType.getId.intValue() == coreAPT.getActionPropertyType.getId
              }
              val res = result.getOrElse(null)
              val time = if (res != null && res._2 != null && res._2.size() > 0) res._2.get(0).asInstanceOf[APValueTime].getValue else null
              if (time != null) {
                var tTime = Calendar.getInstance()
                tTime.setTime(time)
                val hour = tTime.get(Calendar.HOUR_OF_DAY)
                val minutes = tTime.get(Calendar.MINUTE)

                tDate.set(Calendar.HOUR_OF_DAY, hour)
                tDate.set(Calendar.MINUTE, minutes)
              }
              this.moveDatetime = tDate.getTime
            }
          }
          case _ => null
        }
      })
    }
  }
}

@XmlType(name = "movesListHospitalBedContainer")
@XmlRootElement(name = "movesListHospitalBedContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class MovesListHospitalBedContainer {

  @BeanProperty
  var unitId: Int = _
  @BeanProperty
  var unit: String = _
  @BeanProperty
  var admission: Date = _
  @BeanProperty
  var leave: Date = _
  @BeanProperty
  var days: Long = _
  @BeanProperty
  var bedDays: Long = _
  @BeanProperty
  var chamber: String = _
  //TODO: Не используется в текущей реализации БД
  @BeanProperty
  var bed: String = _

  private class IndexOf[T](seq: Seq[T]) {
    def unapply(pos: T) = seq find (pos ==) map (seq indexOf _)
  }

  def this(action: Action,
           values: java.util.Map[ActionProperty, java.util.List[APValue]],
           corrMap: java.util.HashMap[String, java.util.List[RbCoreActionProperty]]) = {
    this()

    val aTypeList = List(ConfigManager.Messages("db.actionType.hospitalization.primary").toString,
      ConfigManager.Messages("db.actionType.moving").toString)
    val listNdxExt = new IndexOf(aTypeList)
    action.getActionType.getId.toString match {
      case listNdxExt(0) => {
        this.unitId = 28
        this.unit = "Приемное отделение"
        this.admission = action.getBegDate
        this.leave = action.getEndDate
      }
      case listNdxExt(1) => {
        if (values != null) {
          val flgClose = if (action.getEndDate != null) true else false
          val list = List(ConfigManager.Messages("db.actionPropertyType.moving.name.beginTime").toString,
            ConfigManager.Messages("db.actionPropertyType.moving.name.located").toString,
            ConfigManager.Messages("db.actionPropertyType.moving.name.bed").toString,
            ConfigManager.Messages("db.actionPropertyType.moving.name.endTime").toString,
            ConfigManager.Messages("db.actionPropertyType.moving.name.movedIn").toString)
          val listNdx = new IndexOf(list)

          corrMap.get(action.getActionType.getId.toString).foreach((coreAPT) => {
            coreAPT.getName match {
              case listNdx(0) => {
                //Время поступления
                if (action.getBegDate != null) {
                  var tDate = Calendar.getInstance()
                  tDate.setTime(action.getBegDate)

                  val result = values.find {
                    element => element._1.getType.getId.intValue() == coreAPT.getActionPropertyType.getId
                  }
                  val res = result.getOrElse(null)
                  val time = if (res != null && res._2 != null && res._2.size() > 0) res._2.get(0).asInstanceOf[APValueTime].getValue else null
                  if (time != null) {
                    var tTime = Calendar.getInstance()
                    tTime.setTime(time)
                    val hour = tTime.get(Calendar.HOUR_OF_DAY)
                    val minutes = tTime.get(Calendar.MINUTE)

                    tDate.set(Calendar.HOUR_OF_DAY, hour)
                    tDate.set(Calendar.MINUTE, minutes)
                  }
                  this.admission = tDate.getTime
                }
              }
              case listNdx(1) => {
                //Отделение пребывания
                if (!flgClose) {
                  val result = values.find {
                    element => element._1.getType.getId.intValue() == coreAPT.getActionPropertyType.getId
                  }
                  val res = result.getOrElse(null)
                  if (res != null && res._2 != null && res._2.size() > 0) {
                    this.unit = res._2.get(0).asInstanceOf[APValueOrgStructure].getValue.getName
                    this.unitId = res._2.get(0).asInstanceOf[APValueOrgStructure].getValue.getId.intValue()
                  }
                }
              }
              case listNdx(2) => {
                //Койка
                if (!flgClose) {
                  val result = values.find {
                    element => element._1.getType.getId.intValue() == coreAPT.getActionPropertyType.getId
                  }
                  val res = result.getOrElse(null)
                  if (res != null && res._2 != null && res._2.size() > 0)
                    this.bed = res._2.get(0).asInstanceOf[APValueHospitalBed].getValue.getCode
                } else {
                  this.bed = "Положить на койку"
                }
              }
              case listNdx(3) => {
                //Время выбытия
                if (action.getEndDate != null) {
                  var tDate = Calendar.getInstance()
                  tDate.setTime(action.getEndDate)

                  val result = values.find {
                    element => element._1.getType.getId.intValue() == coreAPT.getActionPropertyType.getId
                  }
                  val res = result.getOrElse(null)
                  val time = if (res != null && res._2 != null && res._2.size() > 0) res._2.get(0).asInstanceOf[APValueTime].getValue else null
                  if (time != null) {
                    var tTime = Calendar.getInstance()
                    tTime.setTime(time)
                    val hour = tTime.get(Calendar.HOUR_OF_DAY)
                    val minutes = tTime.get(Calendar.MINUTE)

                    tDate.set(Calendar.HOUR_OF_DAY, hour)
                    tDate.set(Calendar.MINUTE, minutes)
                  }
                  this.leave = tDate.getTime
                }
              }
              case listNdx(4) => {
                //Переведен в отделение
                if (flgClose) {
                  val result = values.find {
                    element => element._1.getType.getId.intValue() == coreAPT.getActionPropertyType.getId
                  }
                  val res = result.getOrElse(null)
                  if (res != null && res._2 != null && res._2.size() > 0) {
                    this.unit = res._2.get(0).asInstanceOf[APValueOrgStructure].getValue.getName
                    this.unitId = res._2.get(0).asInstanceOf[APValueOrgStructure].getValue.getId.intValue()
                  }
                }
              }
              case _ => {
                null
              }
            }
          })
        }
      }
    }
    if (this.leave != null && this.admission != null) {
      this.days = (this.leave.getTime - this.admission.getTime) / (1000 * 60 * 60 * 24)
      this.bedDays = this.days + 1
    }
  }
}

@XmlType(name = "registrationHospitalBedContainer")
@XmlRootElement(name = "registrationHospitalBedContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class RegistrationHospitalBedContainer {
  @BeanProperty
  var clientId: Int = _
  @BeanProperty
  var bedId: Int = _
  @BeanProperty
  var moveDatetime: Date = _
  @BeanProperty
  var movedFromUnitId: Int = _
  @BeanProperty
  var patronage: String = _
  @JsonView(Array(classOf[HospitalBedViews.RegistrationFormView]))
  @BeanProperty
  var chamberList: java.util.LinkedList[ChamberDataContainer] = new java.util.LinkedList[ChamberDataContainer]

  private class IndexOf[T](seq: Seq[T]) {
    def unapply(pos: T) = seq find (pos ==) map (seq indexOf _)
  }

  def this(action: Action,
           values: java.util.Map[ActionProperty, java.util.List[APValue]],
           beds: java.util.Map[java.lang.Integer, java.lang.Boolean],
           corrMap: java.util.HashMap[String, java.util.List[RbCoreActionProperty]]) {
    this()
    this.clientId = action.getEvent.getPatient.getId.intValue()

    val list = List(ConfigManager.Messages("db.actionPropertyType.moving.name.movedFrom").toString,
      ConfigManager.Messages("db.actionPropertyType.moving.name.beginTime").toString,
      ConfigManager.Messages("db.actionPropertyType.moving.name.bed").toString,
      ConfigManager.Messages("db.actionPropertyType.moving.name.patronage").toString)
    val listNdx = new IndexOf(list)

    if (values != null) {
      corrMap.get(action.getActionType.getId.toString).foreach((coreAPT) => {
        coreAPT.getName match {
          case listNdx(0) => {
            //Переведен из отделения
            val result = values.find {
              element => element._1.getType.getId.intValue() == coreAPT.getActionPropertyType.getId
            }
            val res = result.getOrElse(null)
            if (res != null && res._2 != null && res._2.size() > 0) {
              this.movedFromUnitId = res._2.get(0).asInstanceOf[APValueOrgStructure].getValue.getId.intValue()
            }
          }
          case listNdx(1) => {
            //Время поступления
            if (action.getBegDate != null) {
              var tDate = Calendar.getInstance()
              tDate.setTime(action.getBegDate)

              val result = values.find {
                element => element._1.getType.getId.intValue() == coreAPT.getActionPropertyType.getId
              }
              val res = result.getOrElse(null)
              val time = if (res != null && res._2 != null && res._2.size() > 0) res._2.get(0).asInstanceOf[APValueTime].getValue else null
              if (time != null) {
                var tTime = Calendar.getInstance()
                tTime.setTime(time)
                val hour = tTime.get(Calendar.HOUR_OF_DAY)
                val minutes = tTime.get(Calendar.MINUTE)

                tDate.set(Calendar.HOUR_OF_DAY, hour)
                tDate.set(Calendar.MINUTE, minutes)
              }
              this.moveDatetime = tDate.getTime
            }
          }
          case listNdx(2) => {
            //Койка
            val result = values.find {
              element => element._1.getType.getId.intValue() == coreAPT.getActionPropertyType.getId
            }
            val res = result.getOrElse(null)
            if (res != null && res._2 != null && res._2.size() > 0l)
              this.bedId = res._2.get(0).asInstanceOf[APValueHospitalBed].getValue.getId.intValue()
          }
          case listNdx(3) => {
            //Патронаж
            val result = values.find {
              element => element._1.getType.getId.intValue() == coreAPT.getActionPropertyType.getId
            }
            val res = result.getOrElse(null)
            if (res != null && res._2 != null && res._2.size() > 0)
              this.patronage = res._2.get(0).asInstanceOf[APValueString].getValue
          }
          case _ => null
        }
      })
    }
    if (beds != null) //TODO: В текущей реализации БД разбиения на палаты нету
      this.chamberList.add(new ChamberDataContainer(beds))
  }
}

@XmlType(name = "chamberDataContainer")
@XmlRootElement(name = "chamberDataContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class ChamberDataContainer {
  @BeanProperty
  var chamberId: Int = _
  //TODO: нету в БД в текущей реализации
  @BeanProperty
  var chamber: String = _
  //TODO: нету в БД в текущей реализации
  @BeanProperty
  var bedList: java.util.LinkedList[BedDataContainer] = new java.util.LinkedList[BedDataContainer]

  def this(beds: java.util.Map[java.lang.Integer, java.lang.Boolean]) {
    this()
    beds.foreach(bed => {
      this.getBedList().add(new BedDataContainer(bed._1.intValue(), bed._2.booleanValue()))
    })
  }
}

@XmlType(name = "bedDataContainer")
@XmlRootElement(name = "bedDataContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class BedDataContainer {
  @BeanProperty
  var bedId: Int = _
  @BeanProperty
  var busy: String = _

  def this(id: Int, busy: Boolean) {
    this()
    this.bedId = id
    this.busy = if (busy) "yes" else "no"
  }
}
