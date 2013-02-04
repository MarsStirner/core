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
  def this(action: Action,
           values: java.util.Map[ActionProperty, java.util.List[APValue]],
           beds: java.util.Map[OrgStructureHospitalBed, java.lang.Boolean],
           corrMap: java.util.HashMap[String, java.util.List[RbCoreActionProperty]],
           requestData: HospitalBedDataRequest) = {
    this ()
    this.requestData = requestData
    this.data = new HospitalBedEntry(action, values, beds, corrMap)
  }

  /**
   * Конструктор HospitalBedData для получения списка 'движений' пациента.
   * @param map Упорядоченный список с данными о движении.
   * @param corrMap Таблица соответствия с s11r64.RbCoreActionProperty.
   * @param requestData Данные из запроса с клиента.
   */
  def this(map: java.util.LinkedHashMap[Action, java.util.Map[ActionProperty, java.util.List[APValue]]],
           corrMap: java.util.HashMap[String, java.util.List[RbCoreActionProperty]],
           requestData: HospitalBedDataRequest)  = {
    this ()
    this.requestData = requestData
    this.data = new HospitalBedEntry(map, corrMap)
  }
}

/**
 * Контейнер с информацией из запроса с клиента
 */
@XmlType(name = "hospitalBedRequestData")
@XmlRootElement(name = "hospitalBedRequestData")
class HospitalBedDataRequest {
  @BeanProperty
  var filter:  AnyRef = _
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

  /**
   * Конструктор класса HospitalBedDataRequest
   * @param sortingField Наименование поля для сортировки.
   * @param sortingMethod Метод сортировки.
   * @param limit Максимальное количество элементов в списке.
   * @param page Выводимая странице в списке.
   * @param filter Данные об фильтрации значений в списке как Object
   */
  def this(sortingField: String,
           sortingMethod: String,
           limit: Int,
           page: Int,
           filter: AnyRef) = {
    this()
    this.filter = if(filter!=null) {filter} else {null}
    this.sortingField = sortingField match {
      case null => {"id"}
      case _ => {sortingField}
    }

    if(this.filter.isInstanceOf[HospitalBedDataListFilter])
      this.sortingFieldInternal = this.filter.asInstanceOf[HospitalBedDataListFilter].toSortingString(this.sortingField)


    this.sortingMethod = sortingMethod match {
      case null => {"asc"}
      case _ => {sortingMethod}
    }
    this.limit = limit
    this.page = page
    this.coreVersion = ConfigManager.Messages("misCore.assembly.version")
  }
}

/**
 * Контейнер для фильтра списка движений пациента.
 */
@XmlType(name = "hospitalBedDataListFilter")
@XmlRootElement(name = "hospitalBedDataListFilter")
class HospitalBedDataListFilter {

  @BeanProperty
  var eventId:  Int = _

  /**
   * Конструктор класса HospitalBedDataListFilter
   * @param eventId Фильтр по идентификатору обращения.
   */
  def this(eventId:  Int){
    this()
    this.eventId = eventId
  }

  /**
   * Метод по формированию структуры запроса, фильтрующего значения в списке движений
   * @return Строка для jpql-запроса.
   */
  def toQueryStructure() = {
    var qs = new QueryDataStructure()

    if(this.eventId>0){
      qs.query += ("AND a.event.id =  :eventId\n")
      qs.add("eventId", this.eventId:java.lang.Integer)
    }
    qs
  }

  /**
   * Метод по формированию строки сортировки
   * @param sortingField Наименование поля для сортировки.
   * @return Строка сортировки.
   */
  def toSortingString (sortingField: String) = {
    sortingField match {
      case _ => {"a.createDatetime"}
    }
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
  def this(action: Action,
           values: java.util.Map[ActionProperty, java.util.List[APValue]],
           beds: java.util.Map[OrgStructureHospitalBed, java.lang.Boolean],
           corrMap: java.util.HashMap[String, java.util.List[RbCoreActionProperty]]) = {
    this ()
    this.move = new MoveHospitalBedContainer(action, values, corrMap)
    this.bedRegistration = new RegistrationHospitalBedContainer(action, values, beds, corrMap)
    this.registrationForm = new RegistrationHospitalBedContainer(action, values, beds, corrMap)
  }

  /**
   * Конструктор класса HospitalBedEntry для списка движений
   * Спецификация https://docs.google.com/spreadsheet/ccc?key=0AgE0ILPv06JcdEE0ajBZdmk1a29ncjlteUp3VUI2MEE#gid=3
   * @param map Список движений со списком свойств и их значений.
   * @param corrMap Список соответствия с s11r64.RbCoreActionProperty.
   * @return
   */
  def this(map: java.util.LinkedHashMap[Action, java.util.Map[ActionProperty, java.util.List[APValue]]],
           corrMap: java.util.HashMap[String, java.util.List[RbCoreActionProperty]])  = {
    this ()
    //Первая запись всегда Приемное отделение если существует экшн с типом 112
    var departmentTo: OrgStructure = null
    val primary = map.filter(p=> p._1.getActionType.getId.compareTo(ConfigManager.Messages("db.actionType.hospitalization.primary").toInt)==0)
    if (primary!=null && primary.size>0){
      //заполняем первую позицию
      val actionNVal = primary.iterator.next()
      val action = actionNVal._1
      val apValues = actionNVal._2

      this.moves.add(new MovesListHospitalBedContainer(action.getId.intValue(), 28, "Приемное отделение", action.getBegDate, null))

      //ищу, куда направлен
      val coreAPT = corrMap.get(action.getActionType.getId.toString)
                           .find(p=>p.getId.compareTo(ConfigManager.Messages("db.rbCAP.hosp.primary.id.sentTo").toInt)==0)
                           .getOrElse(null)
      if(coreAPT!=null){
        val result = apValues.find(element => element._1.getType.getId.intValue() == coreAPT.getActionPropertyType.getId).getOrElse(null)
        if(result!=null && result._2!=null &&  result._2.size()>0){
          departmentTo = result._2.get(0).asInstanceOf[APValueOrgStructure].getValue
        }
      }
    }
    var i = 0
    map.foreach(moving => {
        val aType = moving._1.getActionType.getId
        if (aType.compareTo(ConfigManager.Messages("db.actionType.moving").toInt)==0){
          val mlhbc = new MovesListHospitalBedContainer()
          mlhbc.id = moving._1.getId.intValue()
          val flgClose = if (moving._1.getEndDate!=null)true else false
          val list = List(ConfigManager.Messages("db.rbCAP.moving.id.beginTime").toInt,
                          ConfigManager.Messages("db.rbCAP.moving.id.located").toInt,
                          ConfigManager.Messages("db.rbCAP.moving.id.bed").toInt,
                          ConfigManager.Messages("db.rbCAP.moving.id.endTime").toInt,
                          ConfigManager.Messages("db.rbCAP.moving.id.movedIn").toInt)
          val listNdx = new IndexOf(list)
          corrMap.get(aType.toString).foreach((coreAPT) => {
            coreAPT.getId.intValue() match {
              case listNdx(0) => {  //Время поступления
                mlhbc.admission = this.getFormattedDate(moving._1.getBegDate, moving._2, coreAPT.getActionPropertyType.getId.intValue())
                if (i == 0) {
                  this.moves.get(0).leave = mlhbc.admission
                }
              }
              case listNdx(1) => {  //Отделение пребывания
                val result = moving._2.find(element => element._1.getType.getId.intValue() == coreAPT.getActionPropertyType.getId.intValue()).getOrElse(null)
                if(result!=null && result._2!=null &&  result._2.size()>0){
                  val temp = result._2.get(0).asInstanceOf[APValueOrgStructure].getValue
                  mlhbc.unitId = temp.getId.intValue()
                  mlhbc.unit = temp.getName
                }
              }
              case listNdx(2) => {  //Койка
                 val result = moving._2.find(element => element._1.getType.getId.intValue() == coreAPT.getActionPropertyType.getId.intValue()).getOrElse(null)
                 if(result!=null && result._2!=null &&  result._2.size()>0){
                   mlhbc.bed = result._2.get(0).asInstanceOf[APValueHospitalBed].getValue.getCode
                 }
                 else mlhbc.bed = "Положить на койку"
              }
              case listNdx(3) => {  //Время выбытия
                mlhbc.leave = this.getFormattedDate(moving._1.getEndDate, moving._2, coreAPT.getActionPropertyType.getId.intValue())
              }
              case listNdx(4) => {  //Переведен в отделение
                if(flgClose){
                  val result = moving._2.find(element => element._1.getType.getId.intValue() == coreAPT.getActionPropertyType.getId.intValue()).getOrElse(null)
                  if(result!=null && result._2!=null &&  result._2.size()>0){
                    departmentTo = result._2.get(0).asInstanceOf[APValueOrgStructure].getValue
                  } else departmentTo = null
                } else departmentTo = null
              }
              case _ => null
            }
          })
          if (mlhbc.leave!=null && mlhbc.admission!=null) {
            mlhbc.days = (mlhbc.leave.getTime - mlhbc.admission.getTime)/(1000*60*60*24)
            mlhbc.bedDays = mlhbc.days + 1
          }
          this.moves.add(mlhbc)
          i = i+1
        }
    })

    //Направлен в отделение, но не дошел
    if (departmentTo!=null)
      this.moves.add(new MovesListHospitalBedContainer(departmentTo, "Положить на койку"))
  }

  /**
   * Метод для конкатенации даты из Action с временем из ActionProperty
   * @param actionDate Дата действия пациента.
   * @param values Список значений свойств действия.
   * @param aptId  Идентификатор типа свойства действия s11r64.ActionPropertyType.id.
   * @return Обобщенная дата.
   */
  private def getFormattedDate(actionDate: Date,
                               values: java.util.Map[ActionProperty, java.util.List[APValue]],
                               aptId: Int) = {
    if (actionDate!=null) {
      var tDate = Calendar.getInstance()
      tDate.setTime(actionDate)

      val result = values.find(element => element._1.getType.getId.intValue() == aptId).getOrElse(null)
      if(result!=null && result._2!=null &&  result._2.size()>0){
        val time = result._2.get(0).asInstanceOf[APValueTime].getValue
        if (time!=null){
          var tTime = Calendar.getInstance()
          tTime.setTime(time)
          val hour = tTime.get(Calendar.HOUR_OF_DAY)
          val minutes = tTime.get(Calendar.MINUTE)

          tDate.set(Calendar.HOUR_OF_DAY, hour)
          tDate.set(Calendar.MINUTE, minutes)
        }
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
   * @param corrMap Список соответствия с s11r64.RbCoreActionProperty.
   */
  def this(action: Action,
           values: java.util.Map[ActionProperty, java.util.List[APValue]],
           corrMap: java.util.HashMap[String, java.util.List[RbCoreActionProperty]]){
    this()
    this.id = action.getId.intValue()
    this.clientId = action.getEvent.getPatient.getId.intValue()
    val flgAction = if(action.getActionType.getId.compareTo(ConfigManager.Messages("db.actionType.moving").toInt)==0)
                      movingInDepartment
                    else if(action.getActionType.getId.compareTo(ConfigManager.Messages("db.actionType.hospitalization.primary").toInt)==0)
                      directionInDepartment
                    else unknownOperation

    if (values!=null && flgAction!=unknownOperation){
      val list = if(flgAction == movingInDepartment) List(ConfigManager.Messages("db.rbCAP.moving.id.movedIn").toInt,
                                                          ConfigManager.Messages("db.rbCAP.moving.id.beginTime").toInt)
                 else List(ConfigManager.Messages("db.rbCAP.moving.id.movedIn").toInt)

      val listNdx = new IndexOf(list)

      corrMap.get(action.getActionType.getId.toString).foreach((coreAPT) => {
        coreAPT.getId.intValue() match {
          case listNdx(0) => {  //Переведен в отделение
            val result = values.find {element => element._1.getType.getId.intValue() == coreAPT.getActionPropertyType.getId.intValue()}
            val res = result.getOrElse(null)
            if(res!=null && res._2!=null &&  res._2.size()>0){
              this.unitId = res._2.get(0).asInstanceOf[APValueOrgStructure].getValue.getId.intValue()
            }
            if(flgAction != movingInDepartment) this.moveDatetime = action.getBegDate
          }
          case listNdx(1) => {  //Время поступления
            if (action.getBegDate!=null) {
              var tDate = Calendar.getInstance()
              tDate.setTime(action.getBegDate)

              val result = values.find {element => element._1.getType.getId.intValue() == coreAPT.getActionPropertyType.getId.intValue()}
              val res = result.getOrElse(null)
              val time = if(res!=null && res._2!=null &&  res._2.size()>0) res._2.get(0).asInstanceOf[APValueTime].getValue else null
              if (time!=null){
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
   * @param bed Наименование койки.
   * @since 1.0.0.45
   */
  def this(department: OrgStructure, bed: String){
    this()
    if (department!=null){
      this.unitId = department.getId.intValue()
      this.unit = department.getName
    }
    this.bed = bed
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
           beds: java.util.Map[OrgStructureHospitalBed, java.lang.Boolean],
           corrMap: java.util.HashMap[String, java.util.List[RbCoreActionProperty]]){
    this()
    this.clientId = action.getEvent.getPatient.getId.intValue()

    val list = List(ConfigManager.Messages("db.rbCAP.moving.id.movedFrom").toInt,
                    ConfigManager.Messages("db.rbCAP.moving.id.beginTime").toInt,
                    ConfigManager.Messages("db.rbCAP.moving.id.bed").toInt,
                    ConfigManager.Messages("db.rbCAP.moving.id.patronage").toInt)
    val listNdx = new IndexOf(list)

    if (values!=null){
      corrMap.get(action.getActionType.getId.toString).foreach((coreAPT) => {
        coreAPT.getId.intValue() match {
          case listNdx(0) => {  //Переведен из отделения
            val result = values.find {element => element._1.getType.getId.intValue() == coreAPT.getActionPropertyType.getId.intValue()}
            val res = result.getOrElse(null)
            if(res!=null && res._2!=null &&  res._2.size()>0){
              this.movedFromUnitId = res._2.get(0).asInstanceOf[APValueOrgStructure].getValue.getId.intValue()
            }
          }
          case listNdx(1) => {  //Время поступления
            if (action.getBegDate!=null) {
              var tDate = Calendar.getInstance()
              tDate.setTime(action.getBegDate)

              val result = values.find {element => element._1.getType.getId.intValue() == coreAPT.getActionPropertyType.getId.intValue()}
              val res = result.getOrElse(null)
              val time = if(res!=null && res._2!=null &&  res._2.size()>0) res._2.get(0).asInstanceOf[APValueTime].getValue else null
              if (time!=null){
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
          case listNdx(2) => {   //Койка
            val result = values.find {element => element._1.getType.getId.intValue() == coreAPT.getActionPropertyType.getId.intValue()}
            val res = result.getOrElse(null)
            if(res!=null && res._2!=null &&  res._2.size()>0l)
              this.bedId = res._2.get(0).asInstanceOf[APValueHospitalBed].getValue.getId.intValue()
          }
          case listNdx(3) => {   //Патронаж
            val result = values.find {element => element._1.getType.getId.intValue() == coreAPT.getActionPropertyType.getId.intValue()}
            val res = result.getOrElse(null)
            if(res!=null && res._2!=null &&  res._2.size()>0)
              this.patronage = res._2.get(0).asInstanceOf[APValueString].getValue
          }
          case _ => null
        }
      })
    }
    if(beds!=null)  //TODO: В текущей реализации БД разбиения на палаты нету
      this.chamberList.add(new ChamberDataContainer(beds))
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
