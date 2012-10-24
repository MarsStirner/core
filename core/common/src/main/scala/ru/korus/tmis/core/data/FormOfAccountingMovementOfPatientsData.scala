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

@XmlType(name = "formOfAccountingMovementOfPatientsData")
@XmlRootElement(name = "formOfAccountingMovementOfPatientsData")
@JsonIgnoreProperties(ignoreUnknown = true)
class FormOfAccountingMovementOfPatientsData {
  @BeanProperty
  var requestData: SeventhFormRequestData = _
  @BeanProperty
  var data: FormOfAccountingMovementOfPatientsEntry = _

  def this(that: SeventhFormLinearView,
           request: SeventhFormRequestData) {
    this()
    this.requestData = request
    this.data = new FormOfAccountingMovementOfPatientsEntry(that.getProfileMap(), that.getPatientMap())
  }
}

@XmlType(name = "seventhFormRequestData")
@XmlRootElement(name = "seventhFormRequestData")
@JsonIgnoreProperties(ignoreUnknown = true)
class SeventhFormRequestData {

}

@XmlType(name = "formOfAccountingMovementOfPatientsEntry")
@XmlRootElement(name = "formOfAccountingMovementOfPatientsEntry")
@JsonIgnoreProperties(ignoreUnknown = true)
class FormOfAccountingMovementOfPatientsEntry {

  @BeanProperty
  var counts: java.util.LinkedList[SeventhFormFrontPage] = new java.util.LinkedList[SeventhFormFrontPage]

  @BeanProperty
  var patients: SeventhFormReversePage = new SeventhFormReversePage()

  def this(counts: java.util.Map[RbHospitalBedProfile, SeventhFormLinearViewEntry],
           patients: java.util.Map[Int, java.util.List[Patient]]) {
    this()
    var it = 0
    counts.foreach(profile => {
      it = it + 1
      this.counts.add(new SeventhFormFrontPage(it, profile._1, profile._2))
    })
    this.patients = new SeventhFormReversePage(patients)
  }
}

@XmlType(name = "seventhFormFrontPage")
@XmlRootElement(name = "seventhFormFrontPage")
@JsonIgnoreProperties(ignoreUnknown = true)
class SeventhFormFrontPage {

  @BeanProperty
  var id: Int = -1

  @BeanProperty
  var name: IdNameContainer = new IdNameContainer

  @BeanProperty
  var code: String = ""

  @BeanProperty
  var deployedBed: Int = 0

  @BeanProperty
  var closedBed: Int = 0

  @BeanProperty
  var movement: MovementDataContainer = new MovementDataContainer

  @BeanProperty
  var atBeginingOfDay: AtBeginingOfDayContainer = _

  def this(departmentId: Int,
           id: Int,
           name: RbHospitalBedProfile,
           bed: java.util.Map[String, java.lang.Integer],
           receive: java.util.Map[Patient, String],
           transfer: java.util.Map[Patient, java.util.Map[String, OrgStructure]]
            ) {
    this()
    this.id = id
    if (name != null) {
      this.name = new IdNameContainer(name.getId.intValue(), name.getName)
      this.code = name.getCode
    }
    else {
      this.name = new IdNameContainer(-1, "Всего")
      this.code = ""
    }
    if (bed != null) {
      this.deployedBed = bed.get("deployed").intValue()
      this.closedBed = bed.get("involution").intValue()
    }
    this.movement.calculate(departmentId, receive, transfer) //считаем
  }

  def this(id: Int,
           name: RbHospitalBedProfile,
           counts: SeventhFormLinearViewEntry) {
    this()
    this.id = id
    if (name != null) {
      this.name = new IdNameContainer(name.getId.intValue(), name.getName)
      this.code = name.getCode
    }
    else {
      this.name = new IdNameContainer(-1, "Всего")
      this.code = ""
    }
    this.deployedBed = counts.deployedBed
    this.closedBed = counts.closedBed
    this.movement = new MovementDataContainer(counts)
    this.atBeginingOfDay = new AtBeginingOfDayContainer(counts.summary, counts.mothers, counts.male, counts.female)
  }
}

@XmlType(name = "seventhFormReversePage")
@XmlRootElement(name = "seventhFormReversePage")
@JsonIgnoreProperties(ignoreUnknown = true)
class SeventhFormReversePage {

  @BeanProperty
  var received: java.util.LinkedList[IdNameContainer] = new java.util.LinkedList[IdNameContainer]

  @BeanProperty
  var receivedFromHourHospital: java.util.LinkedList[IdNameContainer] = new java.util.LinkedList[IdNameContainer]

  @BeanProperty
  var leaved: java.util.LinkedList[IdNameContainer] = new java.util.LinkedList[IdNameContainer]

  @BeanProperty
  var moving: IdNameTransferredPatientsContainer = new IdNameTransferredPatientsContainer

  @BeanProperty
  var died: java.util.LinkedList[IdNameContainer] = new java.util.LinkedList[IdNameContainer]

  @BeanProperty
  var toVacation: java.util.LinkedList[IdNameContainer] = new java.util.LinkedList[IdNameContainer]

  def this(patients: java.util.Map[Int, java.util.List[Patient]]) {
    this()
    var it: java.util.Iterator[Int] = patients.keySet().iterator()
    while (it.hasNext()) {
      val key = it.next();
      patients.get(key).foreach(p => {
        val value = new IdNameContainer(p.getId.intValue(),
          "%s %s.%s.".format(p.getLastName,
            p.getFirstName.substring(0, 1),
            p.getPatrName.substring(0, 1)
          )
        )
        key match {
          case 0 => {
            this.received.add(value)
          }
          case 1 => {
            this.receivedFromHourHospital.add(value)
          }
          case 2 => {
            this.leaved.add(value)
          }
          case 3 => {
            this.moving.add(value, 0)
          }
          case 4 => {
            this.moving.add(value, 1)
          }
          case 5 => {
            this.died.add(value)
          }
          case 6 => {
            this.toVacation.add(value)
          }
          case _ => {}
        }
      })
    }
  }
}

@XmlType(name = "movementDataContainer")
@XmlRootElement(name = "movementDataContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class MovementDataContainer {

  @BeanProperty
  var atBeginingLastDay: Int = 0

  @BeanProperty
  var received: CountOfReceivedPatientsContainer = new CountOfReceivedPatientsContainer

  @BeanProperty
  var moving: CountOfTransferredPatientsContainer = new CountOfTransferredPatientsContainer

  @BeanProperty
  var leaved: CountOfDischargedPatientsContainer = new CountOfDischargedPatientsContainer

  @BeanProperty
  var died: Int = 0

  def calculate(departmentId: Int,
                receive: java.util.Map[Patient, String],
                transfer: java.util.Map[Patient, java.util.Map[String, OrgStructure]]) {
    if (receive != null) {
      this.received = new CountOfReceivedPatientsContainer(receive)
    }
    if (transfer != null) {
      this.moving = new CountOfTransferredPatientsContainer(departmentId, transfer)
    }
  }

  def this(counts: SeventhFormLinearViewEntry) {
    this()
    this.atBeginingLastDay = counts.atBeginingLastDay
    this.received = new CountOfReceivedPatientsContainer(counts)
    this.moving = new CountOfTransferredPatientsContainer(counts.from, counts.in)
    this.leaved = new CountOfDischargedPatientsContainer(counts)
    this.died = counts.died
  }
}

@XmlType(name = "atBeginingOfDayContainer")
@XmlRootElement(name = "atBeginingOfDayContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class AtBeginingOfDayContainer {

  @BeanProperty
  var summary: Int = _

  @BeanProperty
  var mothers: Int = _

  @BeanProperty
  var freePlaces: CountOfFreePlacesContainer = _

  def this(summary: Int,
           mothers: Int,
           male: Int,
           female: Int) {
    this()
    this.summary = summary
    this.mothers = mothers
    this.freePlaces = new CountOfFreePlacesContainer(male, female)
  }
}

@XmlType(name = "countOfReceivedPatientsContainer")
@XmlRootElement(name = "countOfReceivedPatientsContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class CountOfReceivedPatientsContainer {

  @BeanProperty
  var summary: Int = _

  @BeanProperty
  var fromDayHospital: Int = _

  @BeanProperty
  var including: IncludingReceivedPatientsContainer = _

  def this(counts: SeventhFormLinearViewEntry) = {
    this()
    this.summary = counts.summaryReceived
    this.fromDayHospital = counts.fromDayHospital
    this.including = new IncludingReceivedPatientsContainer(counts.villagers, counts.before17age, counts.after60age)
  }

  def this(receive: java.util.Map[Patient, String]) = {
    this()
    var it = 0
    var ageBefore17 = 0
    var ageAfter60 = 0
    var villager = 0

    this.summary = receive.size
    if (this.summary > 0) {
      receive.foreach(r => {
        if (this.getAgeByBirthDate(r._1.getBirthDate) < 17) {
          ageBefore17 += 1
        } else if (this.getAgeByBirthDate(r._1.getBirthDate) > 60) {
          ageAfter60 += 1
        }
        if (r._2.compareTo("Дневной стационар") == 0) {
          it += 1
        }
      })
    }
    this.fromDayHospital = it
    this.including = new IncludingReceivedPatientsContainer(villager, ageBefore17, ageAfter60) //TODO: villager??? где в базе
  }

  private def getAgeByBirthDate(birthDate: Date) = {

    val now = Calendar.getInstance()
    val birth = Calendar.getInstance()
    birth.setTime(birthDate)

    if (birth.after(now)) {
      error("Can't be born in the future")
    }

    var age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR)

    val month1 = now.get(Calendar.MONTH)
    val month2 = birth.get(Calendar.MONTH)
    if (month2 > month1) {
      age += -1
    }
    else if (month1 == month2) {
      if (now.get(Calendar.DAY_OF_MONTH) > birth.get(Calendar.DAY_OF_MONTH)) {
        age += -1
      }
    }
    age
  }
}


@XmlType(name = "countOfTransferredPatientsContainer")
@XmlRootElement(name = "countOfTransferredPatientsContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class CountOfTransferredPatientsContainer {

  @BeanProperty
  var from: Int = 0

  @BeanProperty
  var in: Int = 0

  def this(from: Int,
           in: Int) {
    this()
    this.from = from
    this.in = in
  }

  def this(departmentId: Int,
           transfer: java.util.Map[Patient, java.util.Map[String, OrgStructure]]) {
    this()
    transfer.foreach(el => {
      el._2.foreach(org => {

        if (org._2.getId.intValue() == departmentId) {
          org._1 match {
            case "in" => {
              this.in += 1
            }
            case "from" => {
              this.from += 1
            }
            case _ => {}
          }
        }
      })
    })
  }
}

@XmlType(name = "countOfDischargedPatientsContainer")
@XmlRootElement(name = "countOfDischargedPatientsContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class CountOfDischargedPatientsContainer {

  @BeanProperty
  var summary: Int = _

  @BeanProperty
  var including: IncludingDischargedPatientsContainer = _

  def this(counts: SeventhFormLinearViewEntry) {
    this()
    this.summary = counts.summaryDisharged
    this.including = new IncludingDischargedPatientsContainer(counts.toOtherHospital, counts.toHourHospital, counts.toDayHospital)
  }
}

@XmlType(name = "includingReceivedPatientsContainer")
@XmlRootElement(name = "includingReceivedPatientsContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class IncludingReceivedPatientsContainer {

  @BeanProperty
  var villagers: Int = _

  @BeanProperty
  var before17age: Int = _

  @BeanProperty
  var after60age: Int = _

  def this(villagers: Int,
           before17age: Int,
           after60age: Int) {
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
  var toOtherHospital: Int = _

  @BeanProperty
  var toHourHospital: Int = _

  @BeanProperty
  var toDayHospital: Int = _

  def this(toOtherHospital: Int,
           toHourHospital: Int,
           toDayHospital: Int) {
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
  var toDepartment: java.util.LinkedList[IdNameContainer] = new java.util.LinkedList[IdNameContainer]

  @BeanProperty
  var toHospital: java.util.LinkedList[IdNameContainer] = new java.util.LinkedList[IdNameContainer]

  def add(value: IdNameContainer,
          to: Int) {
    to match {
      case 0 => {
        this.toDepartment.add(value)
      }
      case 1 => {
        this.toHospital.add(value)
      }
      case _ => {}
    }
  }
}

class SeventhFormLinearView {

  private val profileMap = new java.util.HashMap[RbHospitalBedProfile, SeventhFormLinearViewEntry]()

  private val patientMap = new java.util.HashMap[Int, java.util.List[Patient]]()

  def initProfileMapStructure(keys: java.util.Set[RbHospitalBedProfile]) {
    if (profileMap.size() <= 0) {
      var entry = new SeventhFormLinearViewEntry()
      profileMap.put(null, entry)
      keys.foreach(key => {
        entry = new SeventhFormLinearViewEntry()
        profileMap.put(key, entry)
      })
    }
  }

  def add(values: java.util.Map[RbHospitalBedProfile, Object], pos: Int) {

    var entry: SeventhFormLinearViewEntry = null
    var summary = profileMap.get(null: RbHospitalBedProfile)

    values.foreach(value => {
      if (profileMap.containsKey(value._1)) {
        entry = profileMap.get(value._1)

        pos match {
          case 0 => {
            //deployedBed & closedBed
            if (value._2.isInstanceOf[(java.lang.Long, java.lang.Long)]) {
              val (first, second) = value._2.asInstanceOf[(java.lang.Long, java.lang.Long)]
              entry.deployedBed = first.intValue()
              entry.closedBed = second.intValue()
              summary.deployedBed += first.intValue()
              summary.closedBed += second.intValue()
            }
          }
          case 1 => {
            if (value._2.isInstanceOf[java.lang.Long]) {
              val first = value._2.asInstanceOf[java.lang.Long]
              entry.atBeginingLastDay = first.intValue()
              summary.atBeginingLastDay += first.intValue()
            }
          }
          case 2 => {
            if (value._2.isInstanceOf[java.lang.Long]) {
              val first = value._2.asInstanceOf[java.lang.Long]
              entry.summary = first.intValue()
              summary.summary += first.intValue()
            }
          }
          case 3 => {
            if (value._2.isInstanceOf[java.lang.Long]) {
              val first = value._2.asInstanceOf[java.lang.Long]
              entry.mothers = first.intValue()
              summary.mothers += first.intValue()
            }
          }
          case 4 => {
            //переведен из отделения
            if (value._2.isInstanceOf[(java.lang.Long, java.util.List[Patient])]) {
              val (first, second) = value._2.asInstanceOf[(java.lang.Long, java.util.List[Patient])]
              entry.from = first.intValue()
              if (!this.patientMap.containsKey(3: Int)) {
                this.patientMap.put(3: Int, second)
              } else {
                this.patientMap.get(3).addAll(second)
              }
              summary.from += first.intValue()
            }
          }
          case 5 => {
            //переведен в отделение
            if (value._2.isInstanceOf[(java.lang.Long, java.util.List[Patient])]) {
              val (first, second) = value._2.asInstanceOf[(java.lang.Long, java.util.List[Patient])]
              entry.in = first.intValue()
              if (!this.patientMap.containsKey(1: Int)) {
                this.patientMap.put(1: Int, second)
              } else {
                this.patientMap.get(1).addAll(second)
              }
              summary.in += first.intValue()
            }
          }
          case 6 => {
            //Поступило всего
            if (value._2.isInstanceOf[(java.lang.Long, java.util.List[Patient])]) {
              val (first, second) = value._2.asInstanceOf[(java.lang.Long, java.util.List[Patient])]
              entry.summaryReceived = first.intValue()
              if (!this.patientMap.containsKey(0: Int)) {
                this.patientMap.put(0: Int, second)
              } else {
                this.patientMap.get(0).addAll(second)
              }
              summary.summaryReceived += first.intValue()
            }
          }
          case 7 => {
            //выписано всего
            if (value._2.isInstanceOf[(java.lang.Long, java.util.List[Patient])]) {
              val (first, second) = value._2.asInstanceOf[(java.lang.Long, java.util.List[Patient])]
              entry.summaryDisharged = first.intValue()
              if (!this.patientMap.containsKey(2: Int)) {
                this.patientMap.put(2: Int, second)
              } else {
                this.patientMap.get(2).addAll(second)
              }
              summary.summaryDisharged += first.intValue()
            }
          }
          case 8 => {
            //выписано в дневной стационар
            if (value._2.isInstanceOf[(java.lang.Long, java.util.List[Patient])]) {
              val (first, second) = value._2.asInstanceOf[(java.lang.Long, java.util.List[Patient])]
              entry.toDayHospital = first.intValue()
              summary.toDayHospital += first.intValue()
            }
          }
          case 9 => {
            //выписано в другой стационар
            if (value._2.isInstanceOf[(java.lang.Long, java.util.List[Patient])]) {
              val (first, second) = value._2.asInstanceOf[(java.lang.Long, java.util.List[Patient])]
              entry.toOtherHospital = first.intValue()
              if (!this.patientMap.containsKey(4: Int)) {
                this.patientMap.put(4: Int, second)
              } else {
                this.patientMap.get(4).addAll(second)
              }
              summary.toOtherHospital += first.intValue()
            }
          }
          case 10 => {
            //умерло
            if (value._2.isInstanceOf[(java.lang.Long, java.util.List[Patient])]) {
              val (first, second) = value._2.asInstanceOf[(java.lang.Long, java.util.List[Patient])]
              entry.died = first.intValue()
              if (!this.patientMap.containsKey(5: Int)) {
                this.patientMap.put(5: Int, second)
              } else {
                this.patientMap.get(5).addAll(second)
              }
              summary.died += first.intValue()
            }
          }
          case 11 => {
            //выписано в круглосуточный стационар
            if (value._2.isInstanceOf[(java.lang.Long, java.util.List[Patient])]) {
              val (first, second) = value._2.asInstanceOf[(java.lang.Long, java.util.List[Patient])]
              entry.toHourHospital = first.intValue()
              summary.toHourHospital += first.intValue()
            }
          }
          case 12 => {
            //свободно мужских коек
            if (value._2.isInstanceOf[java.lang.Long]) {
              val first = value._2.asInstanceOf[java.lang.Long]
              entry.male = first.intValue()
              summary.male += first.intValue()
            }
          }
          case 13 => {
            //свободно женских коек
            if (value._2.isInstanceOf[java.lang.Long]) {
              val first = value._2.asInstanceOf[java.lang.Long]
              entry.female = first.intValue()
              summary.female += first.intValue()
            }
          }
          case 14 => {
            //поступившие из дневного стационара
            if (value._2.isInstanceOf[(java.lang.Long, java.util.List[Patient])]) {
              val (first, second) = value._2.asInstanceOf[(java.lang.Long, java.util.List[Patient])]
              entry.fromDayHospital = first.intValue()
              summary.fromDayHospital += first.intValue()
            }
          }
          case 15 => {
            //поступившие до 17 лет
            if (value._2.isInstanceOf[(java.lang.Long, java.util.List[Patient])]) {
              val (first, second) = value._2.asInstanceOf[(java.lang.Long, java.util.List[Patient])]
              entry.before17age = first.intValue()
              summary.before17age += first.intValue()
            }
          }
          case 16 => {
            //поступившие после 60 лет
            if (value._2.isInstanceOf[(java.lang.Long, java.util.List[Patient])]) {
              val (first, second) = value._2.asInstanceOf[(java.lang.Long, java.util.List[Patient])]
              entry.after60age = first.intValue()
              summary.after60age += first.intValue()
            }
          }
          case 17 => {
            //поступившие сельские жители
            if (value._2.isInstanceOf[(java.lang.Long, java.util.List[Patient])]) {
              val (first, second) = value._2.asInstanceOf[(java.lang.Long, java.util.List[Patient])]
              entry.villagers = first.intValue()
              summary.villagers += first.intValue()
            }
          }
          case _ => {}
        }
      }
    })
  }

  def getProfileMap() = {
    this.profileMap
  }

  def getPatientMap() = {
    this.patientMap
  }
}

class SeventhFormLinearViewEntry {

  var deployedBed: Int = 0
  var closedBed: Int = 0
  var atBeginingLastDay: Int = 0
  var summaryReceived: Int = 0
  var fromDayHospital: Int = 0
  var villagers: Int = 0
  var before17age: Int = 0
  var after60age: Int = 0
  var from: Int = 0
  var in: Int = 0
  var summaryDisharged: Int = 0
  var toOtherHospital: Int = 0
  var toHourHospital: Int = 0
  var toDayHospital: Int = 0
  var died: Int = 0
  var summary: Int = 0
  var mothers: Int = 0
  var male: Int = 0
  var female: Int = 0
}

