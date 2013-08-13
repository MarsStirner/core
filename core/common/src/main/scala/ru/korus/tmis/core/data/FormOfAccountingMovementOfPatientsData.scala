package ru.korus.tmis.core.data

import javax.xml.bind.annotation.XmlType._
import javax.xml.bind.annotation.{XmlRootElement, XmlType}
import javax.xml.bind.annotation.XmlRootElement._
import org.codehaus.jackson.annotate.JsonIgnoreProperties
import org.codehaus.jackson.annotate.JsonIgnoreProperties._
import reflect.BeanProperty
import scala.collection.JavaConversions._
import ru.korus.tmis.core.entity.model._
import java.util.{Calendar, Date}
import java.util
import ru.korus.tmis.lis.data.PatientInfo

/**
 * Список возможных ключей для запросов формы 007
 */
class Form007QueryStatuses{}

object Form007QueryStatuses extends Enumeration {

  type Form007QueryStatuses = Value

  val F007QS_PERMANENT_BEDS,                  //1.3  Количество развернутых коек
  F007QS_ALL_PATIENTS_LOCATED_AT_BEGIN_DATE,  //1.5  Состояло на начало суток
  F007QS_RECEIVED_ALL,                        //1.6  Поступило больных всего
  F007QS_RECEIVED_DAY_HOSPITAL,               //1.7  В том числе из дневного стационара.
  F007QS_RECEIVED_VILLAGERS,                  //1.8  Кол-во сельских жителей
  F007QS_RECEIVED_CHILDREN,                   //1.9  Кол-во детей в возрасте от 0 до 17 лет
  F007QS_RECEIVED_AFTER60,                    //1.10 Кол-во граждан, старше 60 лет
  F007QS_MOVING_FROM,                         //1.11 Кол-во переводов из отделений
  F007QS_MOVING_IN,                           //1.12 Кол-во переводов в отделения
  F007QS_LEAVED_ALL,                          //1.13 Выписано всего
  F007QS_LEAVED_ANOTHER_HOSPITAL,             //1.14 Переведено в другие стационары
  F007QS_LEAVED_HOUR_HOSPITAL,                //1.15 Переведено в другие отделения
  F007QS_LEAVED_DAY_HOSPITAL,                 //1.16 Переведено в дневной стационар
  F007QS_LEAVED_DEAD,                         //1.17 Умерло
  F007QS_ALL_PATIENTS_LOCATED_AT_END_DATE,    //1.18 Состоит на начало текущего дня
  F007QS_PATRONAGE                            //1.19 Состоит матерей
  = Value
}

@XmlType(name = "formOfAccountingMovementOfPatientsData")
@XmlRootElement(name = "formOfAccountingMovementOfPatientsData")
@JsonIgnoreProperties(ignoreUnknown = true)
class FormOfAccountingMovementOfPatientsData {
  @BeanProperty
  var requestData: SeventhFormRequestData = _
  @BeanProperty
  var data: FormOfAccountingMovementOfPatientsEntry = _

  def this(department: OrgStructure,
           that: Map[Form007QueryStatuses.Form007QueryStatuses, (Long, List[Event])],
           request: SeventhFormRequestData) {
    this()
    this.requestData = request
    this.data = new FormOfAccountingMovementOfPatientsEntry(request, department, that)
  }
}

@XmlType(name = "seventhFormRequestData")
@XmlRootElement(name = "seventhFormRequestData")
@JsonIgnoreProperties(ignoreUnknown = true)
class SeventhFormRequestData {

  @BeanProperty
  var departmentId: Int = _                                                   //Отделение

  @BeanProperty
  var beginDate: Date = _                                                     //Дата начала

  @BeanProperty
  var endDate: Date = _                                                       //Дата окончания

  def this(departmentId: Int,
           beginDate: Date,
           endDate: Date ){
    this()
    this.departmentId = departmentId
    this.beginDate = beginDate
    this.endDate = endDate
  }
}

@XmlType(name = "formOfAccountingMovementOfPatientsEntry")
@XmlRootElement(name = "formOfAccountingMovementOfPatientsEntry")
@JsonIgnoreProperties(ignoreUnknown = true)
class FormOfAccountingMovementOfPatientsEntry {

  @BeanProperty
  var rangeReportDateTime: DatePeriodContainer = _

  @BeanProperty
  var department: OrgStructureContainer = _

  @BeanProperty
  var counts: java.util.LinkedList[SeventhFormFrontPage] = new java.util.LinkedList[SeventhFormFrontPage]

  @BeanProperty
  var patients: SeventhFormReversePage = new SeventhFormReversePage()

  def this(request: SeventhFormRequestData,
           department: OrgStructure,
           that: Map[Form007QueryStatuses.Form007QueryStatuses, (Long, List[Event])]) {
    this()
    this.rangeReportDateTime = new DatePeriodContainer(request.getBeginDate, request.getEndDate)
    var cnt = Map.empty[Form007QueryStatuses.Form007QueryStatuses, Long]
    var pat = Map.empty[Form007QueryStatuses.Form007QueryStatuses, List[Event]]
    that.foreach(f=> {
      if (f._2!=null){
        cnt += f._1 -> f._2._1
        if (f._2._2!=null && f._2._2.size>0) pat += f._1 -> f._2._2
      }
    })
    this.counts.add(new SeventhFormFrontPage(0, null, cnt)) //Всего
    this.patients = new SeventhFormReversePage(pat)
    this.department = new OrgStructureContainer(department)
  }
}

@XmlType(name = "seventhFormFrontPage")
@XmlRootElement(name = "seventhFormFrontPage")
@JsonIgnoreProperties(ignoreUnknown = true)
class SeventhFormFrontPage {                                                    //Аверс формы 007

  @BeanProperty
  var pos: Int = -1                                                             //Номер строки формы

  @BeanProperty
  var name: IdNameContainer = new IdNameContainer                               //Профиль койки

  @BeanProperty
  var code: String = ""                                                         //Код

  @BeanProperty
  var deployedBed: Long = 0                                                     //Развернуто коек всего

  @BeanProperty
  var closedBed: Long = 0                                                       //Свернуто на ремонт

  @BeanProperty
  var movement: MovementDataContainer = new MovementDataContainer               //Движение больных за истекшие сутки

  @BeanProperty
  var atBeginingOfDay: AtBeginingOfDayContainer = _                             //На начало текущего дня

  def this(id: Int,
           name: RbHospitalBedProfile,
           that: Map[Form007QueryStatuses.Form007QueryStatuses, Long]) {
    this()
    this.pos = id
    if (name != null) {
      this.name = new IdNameContainer(name.getId.intValue(), name.getName)
      this.code = name.getCode
    }
    else {
      this.name = new IdNameContainer(-1, "Всего")
      this.code = ""
    }

    import Form007QueryStatuses._

    this.deployedBed = that.get(F007QS_PERMANENT_BEDS).getOrElse(0)
    this.closedBed = 0 //Не используется в этой реализации
    this.movement = new MovementDataContainer(that)
    this.atBeginingOfDay = new AtBeginingOfDayContainer(that)

  }
}

@XmlType(name = "seventhFormReversePage")
@XmlRootElement(name = "seventhFormReversePage")
@JsonIgnoreProperties(ignoreUnknown = true)
class SeventhFormReversePage {

  @BeanProperty
  var received: java.util.LinkedList[PatientInfoContainer] = new java.util.LinkedList[PatientInfoContainer]

  @BeanProperty
  var receivedFromHourHospital: java.util.LinkedList[PatientInfoContainer] = new java.util.LinkedList[PatientInfoContainer]

  @BeanProperty
  var leaved: java.util.LinkedList[PatientInfoContainer] = new java.util.LinkedList[PatientInfoContainer]

  @BeanProperty
  var moving: IdNameTransferredPatientsContainer = new IdNameTransferredPatientsContainer

  @BeanProperty
  var died: java.util.LinkedList[PatientInfoContainer] = new java.util.LinkedList[PatientInfoContainer]

  @BeanProperty
  var toVacation: java.util.LinkedList[PatientInfoContainer] = new java.util.LinkedList[PatientInfoContainer]

  def this(that: Map[Form007QueryStatuses.Form007QueryStatuses, List[Event]]) {
    this()

    import Form007QueryStatuses._

    that.foreach(f=>{
      f._1 match {
        case F007QS_RECEIVED_ALL => f._2.foreach(e => this.received.add(new PatientInfoContainer(e)))
        case F007QS_MOVING_FROM => f._2.foreach(e => this.receivedFromHourHospital.add(new PatientInfoContainer(e)))
        case F007QS_LEAVED_ALL => f._2.foreach(e => this.leaved.add(new PatientInfoContainer(e)))
        case F007QS_MOVING_IN => f._2.foreach(e => this.moving.add(e, 0))
        case F007QS_LEAVED_ANOTHER_HOSPITAL => f._2.foreach(e => this.moving.add(e, 1))
        case F007QS_LEAVED_DEAD => f._2.foreach(e => this.died.add(new PatientInfoContainer(e)))
        case _ => null
      }

    })
  }

}

@XmlType(name = "movementDataContainer")
@XmlRootElement(name = "movementDataContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class MovementDataContainer {

  @BeanProperty
  var atBeginingLastDay: Long = 0

  @BeanProperty
  var received: CountOfReceivedPatientsContainer = new CountOfReceivedPatientsContainer

  @BeanProperty
  var moving: CountOfTransferredPatientsContainer = _//new CountOfTransferredPatientsContainer

  @BeanProperty
  var leaved: CountOfDischargedPatientsContainer = new CountOfDischargedPatientsContainer

  @BeanProperty
  var died: Long = 0

  def this(that: Map[Form007QueryStatuses.Form007QueryStatuses, Long]) {
    this()

    import Form007QueryStatuses._
    this.atBeginingLastDay = that.get(F007QS_ALL_PATIENTS_LOCATED_AT_BEGIN_DATE).getOrElse(0)
    this.received = new CountOfReceivedPatientsContainer(that)
    this.moving = new CountOfTransferredPatientsContainer(that.get(F007QS_MOVING_FROM).getOrElse(0),
                                                          that.get(F007QS_MOVING_IN).getOrElse(0))
    this.leaved = new CountOfDischargedPatientsContainer(that)
    this.died = that.get(F007QS_LEAVED_DEAD).getOrElse(0)
  }
}

@XmlType(name = "atBeginingOfDayContainer")
@XmlRootElement(name = "atBeginingOfDayContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class AtBeginingOfDayContainer {

  @BeanProperty
  var summary: Long = _

  @BeanProperty
  var mothers: Long = _

  @BeanProperty
  var freePlaces: CountOfFreePlacesContainer = _

  def this(that: Map[Form007QueryStatuses.Form007QueryStatuses, Long]) {
    this()

    import Form007QueryStatuses._
    this.summary = that.get(F007QS_ALL_PATIENTS_LOCATED_AT_END_DATE).getOrElse(0)
    this.mothers = that.get(F007QS_ALL_PATIENTS_LOCATED_AT_END_DATE).getOrElse(0)
    this.freePlaces = new CountOfFreePlacesContainer(0, 0)  //Не используется в этой реализации
  }
}

@XmlType(name = "countOfReceivedPatientsContainer")
@XmlRootElement(name = "countOfReceivedPatientsContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class CountOfReceivedPatientsContainer {

  @BeanProperty
  var summary: Long = _

  @BeanProperty
  var fromDayHospital: Long = _

  @BeanProperty
  var including: IncludingReceivedPatientsContainer = _

  def this(that: Map[Form007QueryStatuses.Form007QueryStatuses, Long]) {
    this()

    import Form007QueryStatuses._
    this.summary = that.get(F007QS_RECEIVED_ALL).getOrElse(0)
    this.fromDayHospital = that.get(F007QS_RECEIVED_DAY_HOSPITAL).getOrElse(0)
    this.including = new IncludingReceivedPatientsContainer(that.get(F007QS_RECEIVED_VILLAGERS).getOrElse(0),
                                                            that.get(F007QS_RECEIVED_CHILDREN).getOrElse(0),
                                                            that.get(F007QS_RECEIVED_AFTER60).getOrElse(0))
  }
}


@XmlType(name = "countOfTransferredPatientsContainer")
@XmlRootElement(name = "countOfTransferredPatientsContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class CountOfTransferredPatientsContainer {

  @BeanProperty
  var from: Long = 0

  @BeanProperty
  var in: Long = 0

  def this(from: Long,
           in: Long) {
    this()
    this.from = from
    this.in = in
  }
}

@XmlType(name = "countOfDischargedPatientsContainer")
@XmlRootElement(name = "countOfDischargedPatientsContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class CountOfDischargedPatientsContainer {

  @BeanProperty
  var summary: Long = _

  @BeanProperty
  var including: IncludingDischargedPatientsContainer = _

  def this(that: Map[Form007QueryStatuses.Form007QueryStatuses, Long]) {
    this()

    import Form007QueryStatuses._
    this.summary = that.get(F007QS_LEAVED_ALL).getOrElse(0)
    this.including = new IncludingDischargedPatientsContainer(that.get(F007QS_LEAVED_ANOTHER_HOSPITAL).getOrElse(0),
                                                              that.get(F007QS_LEAVED_HOUR_HOSPITAL).getOrElse(0),
                                                              that.get(F007QS_LEAVED_DAY_HOSPITAL).getOrElse(0))
  }
}

@XmlType(name = "includingReceivedPatientsContainer")
@XmlRootElement(name = "includingReceivedPatientsContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class IncludingReceivedPatientsContainer {

  @BeanProperty
  var villagers: Long = _

  @BeanProperty
  var before17age: Long = _

  @BeanProperty
  var after60age: Long = _

  def this(villagers: Long,
           before17age: Long,
           after60age: Long) {
    this()
    this.villagers = villagers
    this.before17age = before17age
    this.after60age = after60age
  }
}

@XmlType(name = "includingDischargedPatientsContainer")
@XmlRootElement(name = "includingDischargedPatientsContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class IncludingDischargedPatientsContainer {

  @BeanProperty
  var toOtherHospital: Long = _

  @BeanProperty
  var toHourHospital: Long = _

  @BeanProperty
  var toDayHospital: Long = _

  def this(toOtherHospital: Long,
           toHourHospital: Long,
           toDayHospital: Long) {
    this()
    this.toOtherHospital = toOtherHospital
    this.toHourHospital = toHourHospital
    this.toDayHospital = toDayHospital
  }
}

@XmlType(name = "countOfFreePlacesContainer")
@XmlRootElement(name = "countOfFreePlacesContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class CountOfFreePlacesContainer {

  @BeanProperty
  var male: Int = _

  @BeanProperty
  var female: Int = _

  def this(male: Int,
           female: Int) {
    this()
    this.male = male
    this.female = female
  }
}

@XmlType(name = "idNameTransferredPatientsContainer")
@XmlRootElement(name = "idNameTransferredPatientsContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class IdNameTransferredPatientsContainer {

  @BeanProperty
  var toDepartment: java.util.LinkedList[PatientInfoContainer] = new java.util.LinkedList[PatientInfoContainer]

  @BeanProperty
  var toHospital: java.util.LinkedList[PatientInfoContainer] = new java.util.LinkedList[PatientInfoContainer]

  def add(value: Event,
          to: Int) {
    to match {
      case 0 => this.toDepartment.add(new PatientInfoContainer(value))
      case 1 => this.toHospital.add(new PatientInfoContainer(value))
      case _ => {}
    }
  }
}

@XmlType(name = "patientInfoContainer")
@XmlRootElement(name = "patientInfoContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class PatientInfoContainer {

  @BeanProperty
  var appealId: Int = _

  @BeanProperty
  var externalId: String = _

  @BeanProperty
  var patient: PersonNameContainer = _

  def this(event: Event){
    this()
    this.appealId = event.getId.intValue()
    this.externalId = event.getExternalId
    this.patient = new PersonNameContainer(event.getPatient)
  }
}

