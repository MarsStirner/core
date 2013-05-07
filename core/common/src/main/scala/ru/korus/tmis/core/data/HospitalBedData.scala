package ru.korus.tmis.core.data

import javax.xml.bind.annotation.{XmlType, XmlRootElement}
import reflect.BeanProperty
import ru.korus.tmis.core.entity.model._
import org.codehaus.jackson.annotate.JsonIgnoreProperties
import org.codehaus.jackson.map.annotate.JsonView
import java.util.{Calendar, Date}
import ru.korus.tmis.util.ConfigManager
import scala.collection.JavaConversions._
import java.text.{DateFormat, SimpleDateFormat}
import ru.korus.tmis.core.filter.AbstractListDataFilter
import java.util
import ru.korus.tmis.core.exception.CoreException

/**
 * Набор View для сериализации HospitalBedEntry
 */
object HospitalBedViews {
  class MoveView {}
  class MovesListView {}
  class RegistrationView {}
  class RegistrationFormView {}
}
class HospitalBedViews {}

/**
 * Контейнер с информацией о движении пациентов и запросе с клиента
 */
@XmlType(name = "hospitalBedData")
@XmlRootElement(name = "hospitalBedData")
class HospitalBedData {

  @BeanProperty
  var requestData: HospitalBedDataRequest = _
  @BeanProperty
  var data: HospitalBedEntry = _

  /**
   * Конструктор HospitalBedData для получения информации об действии 'движение' пациента.
   * @param action Действие 'Движение'.
   * @param values Список свойст движения со значениями.
   * @param beds Список обозначений коек с меткой занято или нет.
   * @param corrMap Список соответствия идентификаторов свойств действия с таблицей RbCoreActionProperty.
   * @param requestData Данные из запроса.
   */
  /*def this(action: Action,
           values: java.util.Map[ActionProperty, java.util.List[APValue]],
           beds: java.util.Map[OrgStructureHospitalBed, java.lang.Boolean],
           corrMap: java.util.HashMap[String, java.util.List[RbCoreActionProperty]],
           requestData: HospitalBedDataRequest) = {
    this ()
    this.requestData = requestData
    this.data = new HospitalBedEntry(action, values, beds, corrMap)
  } */

  def this(actions: java.util.List[Action],
           mGetPropertiesWithValuesByCodes: (Int, java.util.Set[String]) => java.util.Map[ActionProperty, java.util.List[APValue]],
           requestData: HospitalBedDataRequest)  = {
    this ()
    this.requestData = requestData
    this.data = new HospitalBedEntry(actions, mGetPropertiesWithValuesByCodes)
  }

  def this(action: Action,
           mGetPropertiesWithValuesByCodes: (Int, java.util.Set[String]) => java.util.Map[ActionProperty, java.util.List[APValue]],
           mGetCaseHospitalBedsByDepartment: (Int) => java.util.Map[OrgStructureHospitalBed, java.lang.Boolean],
           requestData: HospitalBedDataRequest)  = {
    this ()
    this.requestData = requestData
    this.data = new HospitalBedEntry(action, mGetPropertiesWithValuesByCodes, mGetCaseHospitalBedsByDepartment)
  }
}

/**
 * Контейнер с информацией из запроса с клиента
 */
@XmlType(name = "hospitalBedRequestData")
@XmlRootElement(name = "hospitalBedRequestData")
class HospitalBedDataRequest {
  @BeanProperty
  var filter:  AbstractListDataFilter = new DefaultListDataFilter()
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
           filter: AbstractListDataFilter) = {
    this()
    this.filter = if(filter!=null) {filter} else {new HospitalBedDataListFilter()}
    this.sortingField = sortingField match {
      case null => {"id"}
      case _ => {sortingField}
    }
    this.sortingMethod = sortingMethod match {
      case null => {"asc"}
      case _ => {sortingMethod}
    }
    this.limit = if (limit > 0) limit else ConfigManager.Messages("misCore.pages.limit.default").toInt
    this.page = if(page>1) page else 1
    this.coreVersion = ConfigManager.Messages("misCore.assembly.version")
    this.sortingFieldInternal = this.filter.toSortingString(this.sortingField, this.sortingMethod)
  }

  def rewriteRecordsCount(recordsCount: java.lang.Long) = {
    this.recordsCount = recordsCount.longValue()
    true
  }
}

/**
 * Контейнер для фильтра списка движений пациента.
 */
@XmlType(name = "hospitalBedDataListFilter")
@XmlRootElement(name = "hospitalBedDataListFilter")
class HospitalBedDataListFilter extends AbstractListDataFilter {

  @BeanProperty
  var eventId:  Int = _

  def this(eventId:  Int){
    this()
    this.eventId = eventId
  }

  @Override
  def toQueryStructure() = {
    var qs = new QueryDataStructure()
    if(this.eventId>0){
      qs.query += "AND a.event.id = :eventId\n"
      qs.add("eventId",this.eventId:java.lang.Integer)
    }
    qs.query += "AND a.actionType.flatCode IN :codes\n"
    qs.add("codes", asJavaCollection(Set(ConfigManager.Messages("db.action.admissionFlatCode"),
                                         ConfigManager.Messages("db.action.movingFlatCode"))))
    qs
  }

  @Override
  def toSortingString (sortingField: String, sortingMethod: String) = {
    var sorting = sortingField match {
      case "assessmentDate" | "start" | "createDatetime" => {"a.createDatetime %s".format(sortingMethod)}
      case _ => {"a.id %s".format(sortingMethod)}
    }
    sorting = "ORDER BY " + sorting
    sorting
  }
}

/**
 * Контейнер для хранения данных о движениях пациента.
 */
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

  /**
   * Возвращает позицию найденного в списке значения
   * @param seq Список
   * @tparam T  Тип искомых данных
   */
  private class IndexOf[T] (seq: Seq[T]) {
    def unapply(pos: T) = seq find (pos ==) map (seq indexOf _)
  }

  /**
   * Конструктор класса HospitalBedEntry
   * @param action Действие пациента.
   * @param values Список свойств действия со значениями.
   * @param beds  Список занятых/свободных коек.
   * @param corrMap Список соответствия с s11r64.RbCoreActionProperty.
   */
  /*def this(action: Action,
           values: java.util.Map[ActionProperty, java.util.List[APValue]],
           beds: java.util.Map[OrgStructureHospitalBed, java.lang.Boolean],
           corrMap: java.util.HashMap[String, java.util.List[RbCoreActionProperty]]) = {
    this ()
    this.move = new MoveHospitalBedContainer(action, values, corrMap)
    this.bedRegistration = new RegistrationHospitalBedContainer(action, values, beds, corrMap)
    this.registrationForm = new RegistrationHospitalBedContainer(action, values, beds, corrMap)
  } */

  def this(action: Action,
           mGetPropertiesWithValuesByCodes: (Int, java.util.Set[String]) => java.util.Map[ActionProperty, java.util.List[APValue]],
           mGetCaseHospitalBedsByDepartment: (Int) => java.util.Map[OrgStructureHospitalBed, java.lang.Boolean]) = {
    this ()

    val values =
      if(mGetPropertiesWithValuesByCodes!=null) {
        val codes = Set[String](ConfigManager.Messages("db.apt.received.codes.orgStructDirection"),
                                ConfigManager.Messages("db.apt.moving.codes.orgStructTransfer"),
                                ConfigManager.Messages("db.apt.moving.codes.timeArrival"),
                                ConfigManager.Messages("db.apt.moving.codes.patronage"),
                                ConfigManager.Messages("db.apt.moving.codes.hospitalBed"),
                                ConfigManager.Messages("db.apt.moving.codes.orgStructReceived"),
                                ConfigManager.Messages("db.apt.moving.codes.hospOrgStruct"))
        mGetPropertiesWithValuesByCodes(action.getId.intValue(), asJavaSet(codes))
      }
      else new java.util.LinkedHashMap[ActionProperty, java.util.List[APValue]]

    this.move = new MoveHospitalBedContainer(action, values, getFormattedDate _)
    this.bedRegistration = new RegistrationHospitalBedContainer(action, values, mGetCaseHospitalBedsByDepartment, getFormattedDate _)
    this.registrationForm = new RegistrationHospitalBedContainer(action, values, mGetCaseHospitalBedsByDepartment, getFormattedDate _)
  }

  //https://docs.google.com/spreadsheet/ccc?key=0AgE0ILPv06JcdEE0ajBZdmk1a29ncjlteUp3VUI2MEE#gid=3
  def this(actions: java.util.List[Action],
           mGetPropertiesWithValuesByCodes: (Int, java.util.Set[String]) => java.util.Map[ActionProperty, java.util.List[APValue]])  = {
    this()

    var departmentTo: OrgStructure = null
    var flgUpdateReceivedEndDate: Boolean = true
    //1. Received
    val received = actions.filter(p => p.getActionType.getFlatCode.compareTo(ConfigManager.Messages("db.action.admissionFlatCode"))==0)
    if(received!=null && received.size>0) {
      val action = received.get(0)
      //Первая запись всегда "Приемное отделение"
      this.moves.add(new MovesListHospitalBedContainer(action.getId.intValue(),
                                                       28,
                                                       "Приемное отделение",
                                                       action.getBegDate,
                                                       null))
      //Смотрим, куда направлен (apt.code - db.apt.received.codes.sentTo)
      if(mGetPropertiesWithValuesByCodes!=null) {
        val properties = mGetPropertiesWithValuesByCodes(action.getId.intValue(), asJavaSet(Set(ConfigManager.Messages("db.apt.received.codes.orgStructDirection"))))
        if(properties!=null && properties.size()>0){
          val property = properties.iterator.next()
          if (property!=null && property._2!=null && property._2.size()>0)
            departmentTo = properties.iterator.next()._2.get(0).asInstanceOf[APValueOrgStructure].getValue
        }
      }
    }

    if (actions.size()>received.size) {  //Есть движения
      //2. Moving
      actions.foreach(action => {
        if (action.getActionType.getFlatCode.compareTo(ConfigManager.Messages("db.action.movingFlatCode"))==0){
          var bed: OrgStructureHospitalBed = null
          var departmentTransfer: OrgStructure = null
          var timeArrival: Date = null
          var timeLeaved: Date = null
          val codes = Set[String](ConfigManager.Messages("db.apt.moving.codes.hospitalBed"),
                                  ConfigManager.Messages("db.apt.moving.codes.orgStructTransfer"),
                                  ConfigManager.Messages("db.apt.moving.codes.timeArrival"),
                                  ConfigManager.Messages("db.apt.moving.codes.timeLeaved"))
          val properties = mGetPropertiesWithValuesByCodes(action.getId.intValue(), asJavaSet(codes))
          val flgClose = if (action.getEndDate!=null)true else false

          properties.foreach(ap => {
            val code = ap._1.getType.getCode
            if(code.compareTo(ConfigManager.Messages("db.apt.moving.codes.hospitalBed"))==0){
              if (ap._2!=null && ap._2.size()>0)
                bed = ap._2.get(0).asInstanceOf[APValueHospitalBed].getValue
            }
            else if(code.compareTo(ConfigManager.Messages("db.apt.moving.codes.orgStructTransfer"))==0){
              if (ap._2!=null && ap._2.size()>0)
                departmentTransfer = ap._2.get(0).asInstanceOf[APValueOrgStructure].getValue
            }
            else if(code.compareTo(ConfigManager.Messages("db.apt.moving.codes.timeArrival"))==0){
              if (ap._2!=null && ap._2.size()>0)
                timeArrival = ap._2.get(0).asInstanceOf[APValueTime].getValue
            }
            else if(code.compareTo(ConfigManager.Messages("db.apt.moving.codes.timeLeaved"))==0){
              if (ap._2!=null && ap._2.size()>0)
                timeLeaved = ap._2.get(0).asInstanceOf[APValueTime].getValue
            }
          })

          if (flgUpdateReceivedEndDate){  //Из первого движения запишем дату поступления как дату выбытия из приемного отделения
            this.moves.get(0).setLeave(getFormattedDate(action.getBegDate, timeArrival))
            this.moves.get(0).calculate()
            flgUpdateReceivedEndDate = false
          }

          //Запись движения
          this.moves.add(new MovesListHospitalBedContainer(action.getId.intValue(),
                                                           bed,
                                                           getFormattedDate(action.getBegDate, timeArrival),
                                                           getFormattedDate(action.getEndDate, timeLeaved)))

          departmentTo = if(flgClose) departmentTransfer else null
        }
      })
    }
    if (departmentTo!=null) { //Последняя строка куда направлен/переведен
      this.moves.add(new MovesListHospitalBedContainer(departmentTo))
    }
  }

  private def getFormattedDate(date: Date,
                               time: Date) = {
    if (date!=null) {
      var tDate = Calendar.getInstance()
      tDate.setTime(date)
      if (time!=null){
        var tTime = Calendar.getInstance()
        tTime.setTime(time)
        val hour = tTime.get(Calendar.HOUR_OF_DAY)
        val minutes = tTime.get(Calendar.MINUTE)
        tDate.set(Calendar.HOUR_OF_DAY, hour)
        tDate.set(Calendar.MINUTE, minutes)
      }
      tDate.getTime
    } else {
      null: Date
    }
  }
}

/**
 * Контейнер для данных о движении пациента.
 */
@XmlType(name = "moveHospitalBedContainer")
@XmlRootElement(name = "moveHospitalBedContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class MoveHospitalBedContainer {
  @BeanProperty
  var id: Int = _
  @BeanProperty
  var clientId: Int = _
  @BeanProperty
  var unitId: Int = _
  @BeanProperty
  var moveDatetime: Date = _

  private val unknownOperation = 0
  private val directionInDepartment = 1    //Направление в отделение
  private val movingInDepartment = 2       //Перевод в отделение

  /**
   * Возвращает позицию найденного в списке значения
   * @param seq Список
   * @tparam T  Тип искомых данных
   */
  private class IndexOf[T] (seq: Seq[T]) {
    def unapply(pos: T) = seq find (pos ==) map (seq indexOf _)
  }

  /**
   * Конструктор класса MoveHospitalBedContainer
   * @param action Действие.
   * @param values Список значений свойств действия со значениями.
   */
  def this(action: Action,
           values: java.util.Map[ActionProperty, java.util.List[APValue]],
           mFormattedDate: (Date, Date) => Date){
    this()
    this.id = action.getId.intValue()
    this.clientId = action.getEvent.getPatient.getId.intValue()

    var departmentTransfer: OrgStructure = null
    var timeArrival: Date = null

    values.foreach(ap => {
      val code = ap._1.getType.getCode
      if(code.compareTo(ConfigManager.Messages("db.apt.received.codes.orgStructDirection"))==0 ||
         code.compareTo(ConfigManager.Messages("db.apt.moving.codes.orgStructTransfer"))==0) {
        if (ap._2!=null && ap._2.size()>0)
          departmentTransfer = ap._2.get(0).asInstanceOf[APValueOrgStructure].getValue
      }
      else if(code.compareTo(ConfigManager.Messages("db.apt.moving.codes.timeArrival"))==0){
        if (ap._2!=null && ap._2.size()>0)
          timeArrival = ap._2.get(0).asInstanceOf[APValueTime].getValue
      }
    })

    this.unitId = if (departmentTransfer==null) 0 else departmentTransfer.getId.intValue()
    this.moveDatetime = mFormattedDate(action.getBegDate, timeArrival)
  }
}

/**
 * Контейнер для списка движений пациента.
 */
@XmlType(name = "movesListHospitalBedContainer")
@XmlRootElement(name = "movesListHospitalBedContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class MovesListHospitalBedContainer {

  @BeanProperty
  var id: Int = _
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
  var chamber: String = _  //TODO: Не используется в текущей реализации БД
  @BeanProperty
  var bed: String = _

  /**
   * Конструктор MovesListHospitalBedContainer.
   * @param department Отделение как OrgStructure.
   * @since 1.0.0.45
   */
  def this(department: OrgStructure){
    this()
    if (department!=null){
      this.unitId = department.getId.intValue()
      this.unit = "Направлен в " + department.getName
    }
    this.bed = "Положить на койку"
  }

  /**
   * Конструктор MovesListHospitalBedContainer.
   * @param unitId Идентификатор отделения.
   * @param unit Обозначение отделения.
   * @param admission Дата поступления.
   * @param leave Дата выбытия.
   * @since 1.0.0.45
   */
  def this(id: Int, unitId: Int, unit: String, admission: Date, leave: Date){
    this()
    this.id = id
    this.unitId = unitId
    this.unit = unit
    this.admission = admission
    this.leave = leave
  }

  def this(id: Int, bed: OrgStructureHospitalBed, admission: Date, leave: Date){
    this()
    this.id = id
    if (bed!=null){
      this.unitId = bed.getMasterDepartment.getId.intValue()
      this.unit = bed.getMasterDepartment.getCode
      this.bed = bed.getCode
    }
    this.admission = admission
    this.leave = leave
    this.calculate()
  }

  def calculate() = {
    if (this.leave!=null && this.admission!=null) {
      this.days = (this.leave.getTime - this.admission.getTime)/(1000*60*60*24)
      this.bedDays = this.days + 1
    }
  }
}

/**
 * Контейнер для данных об регистрации пациента на койке
 */
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

  /**
   * Возвращает позицию найденного в списке значения
   * @param seq Список
   * @tparam T  Тип искомых данных
   */
  private class IndexOf[T] (seq: Seq[T]) {
    def unapply(pos: T) = seq find (pos ==) map (seq indexOf _)
  }

  /**
   * Конструктор класса RegistrationHospitalBedContainer
   * @param action Действие пациента типа 'движение'.
   * @param values Значения свойств действия в виде списка по ключам.
   * @param beds Список занятых/свободных коек.
   * @param corrMap Список соответствия с s11r64.RbCoreActionProperty.
   */
  def this(action: Action,
           values: java.util.Map[ActionProperty, java.util.List[APValue]],
           mGetCaseHospitalBedsByDepartment: (Int) => java.util.Map[OrgStructureHospitalBed, java.lang.Boolean],
           mFormattedDate: (Date, Date) => Date){
    this()
    this.clientId = action.getEvent.getPatient.getId.intValue()

    var timeArrival: Date = null
    var department: OrgStructure = null

    values.foreach(ap => {
      val code = ap._1.getType.getCode
      if(code.compareTo(ConfigManager.Messages("db.apt.moving.codes.orgStructReceived"))==0) {
        if (ap._2!=null && ap._2.size()>0)
          this.movedFromUnitId = ap._2.get(0).asInstanceOf[APValueOrgStructure].getValue.getId.intValue()
      }
      else if(code.compareTo(ConfigManager.Messages("db.apt.moving.codes.timeArrival"))==0){
        if (ap._2!=null && ap._2.size()>0)
          timeArrival = ap._2.get(0).asInstanceOf[APValueTime].getValue
      }
      else if(code.compareTo(ConfigManager.Messages("db.apt.moving.codes.hospitalBed"))==0){
        if (ap._2!=null && ap._2.size()>0)
          this.bedId  = ap._2.get(0).asInstanceOf[APValueHospitalBed].getValue.getId.intValue()
      }
      else if(code.compareTo(ConfigManager.Messages("db.apt.moving.codes.patronage"))==0){
        if (ap._2!=null && ap._2.size()>0)
          this.patronage = ap._2.get(0).asInstanceOf[APValueString].getValue
      }
      else if(code.compareTo(ConfigManager.Messages("db.apt.moving.codes.hospOrgStruct"))==0){
        if (ap._2!=null && ap._2.size()>0 && action.getEndDate==null)
          department = ap._2.get(0).asInstanceOf[APValueOrgStructure].getValue
      }
      else if(code.compareTo(ConfigManager.Messages("db.apt.received.codes.orgStructDirection"))==0 ||
              code.compareTo(ConfigManager.Messages("db.apt.moving.codes.orgStructTransfer"))==0) {
        if (ap._2!=null && ap._2.size()>0 && action.getEndDate!=null)
          department = ap._2.get(0).asInstanceOf[APValueOrgStructure].getValue
      }
    })

    this.moveDatetime = mFormattedDate(action.getBegDate, timeArrival)

    if(mGetCaseHospitalBedsByDepartment!=null) { //TODO: В текущей реализации БД разбиения на палаты нету
      if (department!=null)
        this.chamberList.add(new ChamberDataContainer(mGetCaseHospitalBedsByDepartment(department.getId.intValue())))
      else
        throw new CoreException("Для Action c id = %s не удалось найти отделение, где находится пациент".format(action.getId.toString))
    }
  }
}

/**
 * Контейнер для данных о палате с свободными/занятыми койками
 */
@XmlType(name = "chamberDataContainer")
@XmlRootElement(name = "chamberDataContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class ChamberDataContainer {
  @BeanProperty
  var chamberId: Int = _          //TODO: нету в БД в текущей реализации
  @BeanProperty
  var chamber: String = _         //TODO: нету в БД в текущей реализации
  @BeanProperty
  var bedList: java.util.LinkedList[BedDataContainer] = new java.util.LinkedList[BedDataContainer]

  /**
   * Конструктор ChamberDataContainer
   * @param beds Список занятых/свободных коек.
   */
  def this(beds: java.util.Map[OrgStructureHospitalBed, java.lang.Boolean]) {
    this()
    beds.foreach(bed=>{
      this.getBedList().add(new BedDataContainer(bed._1, bed._2.booleanValue()))
    })
  }
}

/**
 * Контейнер с данными о койке
 */
@XmlType(name = "bedDataContainer")
@XmlRootElement(name = "bedDataContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class BedDataContainer {
  @BeanProperty
  var bedId: Int = _
  @BeanProperty
  var name: String = _
  @BeanProperty
  var code: String = _
  @BeanProperty
  var busy: String = _

  /**
   * Конструктор BedDataContainer
   * @param bed Койка
   * @param busy Флаг статуса койки: занята("yes")/свободна("no")
   */
  def this(bed: OrgStructureHospitalBed, busy: Boolean){
    this()
    if(bed!=null){
      this.bedId = bed.getId.intValue()
      this.name = bed.getName
      this.code = bed.getCode
      this.busy = if(busy) "yes" else "no"
    }
  }
}

@XmlType(name = "bedDataListContainer")
@XmlRootElement(name = "bedDataListContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class BedDataListContainer {
  @BeanProperty
  var data: java.util.LinkedList[BedDataContainer] = new java.util.LinkedList[BedDataContainer]
  @BeanProperty
  var requestData: BedDataListRequest = _
  /**
   * Конструктор BedDataListContainer
   * @param beds Список занятых/свободных коек.
   */
  def this(beds: java.util.Map[OrgStructureHospitalBed, java.lang.Boolean], departmentId: Int) {
    this()
    this.requestData = new BedDataListRequest(departmentId)
    beds.foreach(bed=>{
      this.data.add(new BedDataContainer(bed._1, bed._2.booleanValue()))
    })
  }
}

@XmlType(name = "bedDataListRequest")
@XmlRootElement(name = "bedDataListRequest")
@JsonIgnoreProperties(ignoreUnknown = true)
class BedDataListRequest {
  @BeanProperty
  var filter: BedDataListFilter = _

  def this(departmentId: Int){
    this()
    this.filter = new BedDataListFilter(departmentId)
  }
}

@XmlType(name = "bedDataListFilter")
@XmlRootElement(name = "bedDataListFilter")
@JsonIgnoreProperties(ignoreUnknown = true)
class BedDataListFilter {

  @BeanProperty
  var departmentId: Int = _

  def this(departmentId: Int){
    this()
    this.departmentId = departmentId
  }

}
