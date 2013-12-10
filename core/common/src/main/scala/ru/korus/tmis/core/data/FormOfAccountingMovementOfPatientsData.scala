package ru.korus.tmis.core.data

import javax.xml.bind.annotation.{XmlRootElement, XmlType}
import org.codehaus.jackson.annotate.JsonIgnoreProperties
import reflect.BeanProperty
import scala.collection.JavaConversions._
import ru.korus.tmis.core.entity.model._
import java.util.Date

/**
 * Список возможных ключей для запросов формы 007
 */
class Form007QueryStatuses {}

object Form007QueryStatuses extends Enumeration {

  type Form007QueryStatuses = Value

  val F007QS_PERMANENT_BEDS = Value(1,"countPermanents") //1.3  Количество развернутых коек (колнка №3 в приказе)
  val F007QS_BEDS_REPAIR = Value(2,"countBedsRepair") // кол-во коек на ремонте (колнка №4 в приказе)
  val F007QS_ALL_PATIENTS_LOCATED_AT_BEGIN_DATE = Value(3,"countLocated") //1.5  Состояло на начало суток (колнка №5 в приказе)
  val F007QS_RECEIVED_ALL = Value(4,"FIOinput007") //1.6  Поступило больных всего (колнка №6 в приказе)
  val F007QS_RECEIVED_DAY_HOSPITAL = Value(5,"FIOinpuFrom12") //1.7  В том числе из дневного стационара. (колнка №7 в приказе)
  val F007QS_RECEIVED_VILLAGERS = Value(6,"countReceivedVillages") //1.8  Кол-во сельских жителей (колнка №8 в приказе)
  val F007QS_RECEIVED_CHILDREN = Value(7,"countReceivedChildren") //1.9  Кол-во детей в возрасте от 0 до 17 лет (колнка №9 в приказе)
  val F007QS_RECEIVED_AFTER60 = Value(8,"countReceivedAfter60") //1.10 Кол-во граждан, старше 60 лет (колнка №10 в приказе)
  val F007QS_MOVING_FROM = Value(9,"countMovingForm") //1.11 Кол-во переводов из отделений (колнка №11 в приказе)
  val F007QS_MOVING_IN = Value(10,"FIOoutToOtherUnit") //1.12 Кол-во переводов в отделения (колнка №12 в приказе)
  val F007QS_LEAVED_ALL = Value(11,"FIOoutTotal") //1.13 Выписано всего (колнка №13 в приказе))
  val F007QS_LEAVED_ANOTHER_HOSPITAL = Value(12,"FIOoutToOtherHospital") //1.14 Переведено в другие стационары (колнка №14 в приказе)
  val F007QS_LEAVED_HOUR_HOSPITAL = Value(13,"countLeavedHourHosp") //1.15 Переведено в круглосуточный стационар (колнка №15 в приказе)
  val F007QS_LEAVED_DAY_HOSPITAL = Value(14,"countDayHosp") //1.16 Переведено в дневной стационар (колнка №16 в приказе)
  val F007QS_LEAVED_DEAD = Value(15,"FIOtotalDeath") //1.17 Умерло (колнка №17 в приказе)
  val F007QS_ALL_PATIENTS_LOCATED_AT_END_DATE = Value(16,"countAllEndDay") //1.18 Состоит на начало текущего дня (колнка №18 в приказе))
  val F007QS_PATRONAGE = Value(17,"countPatronage") //1.19 Состоит матерей (колнка №19 в приказе)
  val F007QS_FREE_BEDS_MALE = Value(18,"countMaleBeds") // кол-во свободных мужских мест
  val F007QS_FREE_BEDS_FEMALE = Value(19,"countFemaleBeds")// кол-во свободных женских мест

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
           cnts: Map[String, scala.collection.mutable.Map[Form007QueryStatuses.Form007QueryStatuses, Long]],
           pat: Map[Form007QueryStatuses.Form007QueryStatuses, List[String]],
           request: SeventhFormRequestData) {
    this()
    this.requestData = request
    this.data = new FormOfAccountingMovementOfPatientsEntry(request, department, cnts, pat)
  }
}

@XmlType(name = "seventhFormRequestData")
@XmlRootElement(name = "seventhFormRequestData")
@JsonIgnoreProperties(ignoreUnknown = true)
class SeventhFormRequestData {

  @BeanProperty
  var departmentId: Int = _ //Отделение

  @BeanProperty
  var beginDate: Date = _ //Дата начала

  @BeanProperty
  var endDate: Date = _ //Дата окончания

  def this(departmentId: Int,
           beginDate: Date,
           endDate: Date) {
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
           cnts: Map[String, scala.collection.mutable.Map[Form007QueryStatuses.Form007QueryStatuses, Long]],
           pat: Map[Form007QueryStatuses.Form007QueryStatuses, List[String]]) {
    this()
    this.rangeReportDateTime = new DatePeriodContainer(request.getBeginDate, request.getEndDate)
    val totalCnts = scala.collection.mutable.Map.empty[Form007QueryStatuses.Form007QueryStatuses, Long]
    cnts.foreach(cnt => {
      cnt._2.foreach(c => totalCnts.put(c._1, totalCnts.getOrElse[Long](c._1, 0) + c._2))
      this.counts.add(new SeventhFormFrontPage(0, RbHospitalBedProfile.newInstance(cnt._1), cnt._2))
    })
    this.counts.add(new SeventhFormFrontPage(0, null, totalCnts)) //Всего
    this.patients = new SeventhFormReversePage(pat)
    this.department = new OrgStructureContainer(department)
  }
}

@XmlType(name = "seventhFormFrontPage")
@XmlRootElement(name = "seventhFormFrontPage")
@JsonIgnoreProperties(ignoreUnknown = true)
class SeventhFormFrontPage {
  //Аверс формы 007

  @BeanProperty
  var pos: Int = -1 //Номер строки формы

  @BeanProperty
  var name: IdNameContainer = new IdNameContainer //Профиль койки

  @BeanProperty
  var code: String = "" //Код

  @BeanProperty
  var deployedBed: Long = 0 //Развернуто коек всего

  @BeanProperty
  var closedBed: Long = 0 //Свернуто на ремонт

  @BeanProperty
  var movement: MovementDataContainer = new MovementDataContainer //Движение больных за истекшие сутки

  @BeanProperty
  var atBeginingOfDay: AtBeginingOfDayContainer = _ //На начало текущего дня

  def this(id: Int,
           name: RbHospitalBedProfile,
           that: scala.collection.mutable.Map[Form007QueryStatuses.Form007QueryStatuses, Long]) {
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

  def this(that: Map[Form007QueryStatuses.Form007QueryStatuses, List[String]]) {
    this()

    import Form007QueryStatuses._

    that.foreach(f => {
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
  var moving: CountOfTransferredPatientsContainer = _ //new CountOfTransferredPatientsContainer

  @BeanProperty
  var leaved: CountOfDischargedPatientsContainer = new CountOfDischargedPatientsContainer

  @BeanProperty
  var died: Long = 0

  def this(that: scala.collection.mutable.Map[Form007QueryStatuses.Form007QueryStatuses, Long]) {
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

  def this(that:  scala.collection.mutable.Map[Form007QueryStatuses.Form007QueryStatuses, Long]) {
    this()

    import Form007QueryStatuses._
    this.summary = that.get(F007QS_ALL_PATIENTS_LOCATED_AT_END_DATE).getOrElse(0)
    this.mothers = that.get(F007QS_ALL_PATIENTS_LOCATED_AT_END_DATE).getOrElse(0)
    this.freePlaces = new CountOfFreePlacesContainer(0, 0) //Не используется в этой реализации
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

  def this(that: scala.collection.mutable.Map[Form007QueryStatuses.Form007QueryStatuses, Long]) {
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

  def this(that: scala.collection.mutable.Map[Form007QueryStatuses.Form007QueryStatuses, Long]) {
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

  def add(value: String,
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

  @BeanProperty
  var patientInfo: String = _

  def this(event: Event) {
    this()
    this.appealId = event.getId.intValue()
    this.externalId = event.getExternalId
    this.patient = new PersonNameContainer(event.getPatient)
  }

  def this(patientInfo: String) {
    this()
    this.patientInfo = patientInfo
  }
}

