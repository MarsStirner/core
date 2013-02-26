package ru.korus.tmis.core.data

import javax.xml.bind.annotation.{XmlRootElement, XmlType}
import org.codehaus.jackson.annotate.JsonIgnoreProperties
import reflect.BeanProperty
import java.util.{Calendar, Date}
import ru.korus.tmis.core.entity.model.{Action, JobTicket, RbTestTubeType, Patient}
import ru.korus.tmis.util.reflect.{LoggingManager, TmisLogging}
import ru.korus.tmis.util.{I18nable, ConfigManager}
import java.text.SimpleDateFormat
import java.util.LinkedList
import scala.collection.JavaConversions._
import java.util

/**
 * Контейнер с данными о заборе биоматериала
 * Author: idmitriev Systema-Soft
 * Date: 2/12/13 3:37 PM
 * Since: 1.0.0.64
 */
@XmlType(name = "takingOfBiomaterialData")
@XmlRootElement(name = "takingOfBiomaterialData")
@JsonIgnoreProperties(ignoreUnknown = true)
class TakingOfBiomaterialData {

  @BeanProperty
  var requestData: TakingOfBiomaterialRequesData = _
  @BeanProperty
  var data: LinkedList[JobTicketInfoContainer] = new LinkedList[JobTicketInfoContainer]

  def this(values: java.util.Map[JobTicket, LinkedList[Action]],
           request: TakingOfBiomaterialRequesData) {
    this()
    this.requestData = request
    values.foreach(f => {
      this.data += new JobTicketInfoContainer(f._1, f._2)
    })
  }
}

@XmlType(name = "takingOfBiomaterialRequesData")
@XmlRootElement(name = "takingOfBiomaterialRequesData")
@JsonIgnoreProperties(ignoreUnknown = true)
class TakingOfBiomaterialRequesData {

  @BeanProperty
  var filter: TakingOfBiomaterialRequesDataFilter = _
  @BeanProperty
  var sortingField: String = _
  @BeanProperty
  var sortingMethod: String = _
  @BeanProperty
  var recordsCount: Long = _
  @BeanProperty
  var coreVersion: String = _

  var sortingFieldInternal: String = _

  def this(sortingField: String,
           sortingMethod: String,
           filter: TakingOfBiomaterialRequesDataFilter){
    this()
    this.sortingField = sortingField match {
      case null => {"fio"}
      case _ => {sortingField}
    }
    this.sortingMethod = sortingMethod match {
      case null => {"asc"}
      case _ => {sortingMethod}
    }
    this.filter = filter
    this.coreVersion = ConfigManager.Messages("misCore.assembly.version")
    this.sortingFieldInternal = this.filter.toSortingString(this.sortingField, this.sortingMethod)
  }

  def rewriteRecordsCount(recordsCount: java.lang.Long) = {
    this.recordsCount = recordsCount.longValue()
    true
  }
}

@XmlType(name = "takingOfBiomaterialRequesDataFilter")
@XmlRootElement(name = "takingOfBiomaterialRequesDataFilter")
@JsonIgnoreProperties(ignoreUnknown = true)
class TakingOfBiomaterialRequesDataFilter {

  @BeanProperty
  var departmentId: Int = _
  @BeanProperty
  var beginDate: Date = _
  @BeanProperty
  var endDate: Date = _
  @BeanProperty
  var status: Short = -1
  @BeanProperty
  var biomaterial: Int = -1

  def this( departmentId: Int,
            beginDate: Long,
            endDate: Long,
            status: Short,
            biomaterial: Int) {
    this()
    this.departmentId = departmentId
    this.status = status
    this.biomaterial = biomaterial

    //Анализ дат
    if(beginDate>0 && endDate>0) {
      this.beginDate = new Date(beginDate)
      this.endDate = new Date(endDate)
    }
    else if(beginDate<=0){
      if(endDate<=0)
        this.endDate = this.getDefaultEndDate(beginDate)
      else
        this.endDate = new Date(endDate)
      this.beginDate = this.getDefaultBeginDate(this.endDate.getTime)
    }
    else {
      this.beginDate = new Date(beginDate)
      this.endDate = this.getDefaultEndDate(this.beginDate.getTime)
    }
  }

  //class TBDefaultDateKeys{}
  private object TBDefaultDateKeys extends Enumeration {

    type TBDefaultDateKeys = Value

    val TBDDK_BEGIN_DATE,                  //Дата начала
        TBDDK_END_DATE                     //Дата окончания
    = Value
  }

  import TBDefaultDateKeys._

  private def getDefaultEndDate(begDate: Long) = getDefaultDate(TBDDK_END_DATE, begDate)

  private def getDefaultBeginDate(endDate: Long) = getDefaultDate(TBDDK_BEGIN_DATE, endDate)

  private def getDefaultDate(key: TBDefaultDateKeys, date: Long) = {

    val formatter = new SimpleDateFormat("yyyy-MM-dd")
    val today = Calendar.getInstance()

    date match {
      case x if (x <= 0) => today.setTime(formatter.parse(formatter.format(new Date())))
      case _ => today.setTime(formatter.parse(formatter.format(new Date(date))))
    }

    key match {
      case TBDDK_BEGIN_DATE => {
        today.set(Calendar.HOUR_OF_DAY, 0)
        today.set(Calendar.MINUTE, 0)
      }
      case TBDDK_END_DATE => {
        today.set(Calendar.HOUR_OF_DAY, 23)
        today.set(Calendar.MINUTE, 59)
      }
      case _ => {}
    }
    today.getTime
  }

  /**
   * Формирование строки сортировки для подстановки в запрос
   * @param sortingField Наименование поля сортировки
   * @return
   */
  def toSortingString (sortingField: String, sortingMethod: String) = {
    val lex =
      sortingMethod.toLowerCase match {
        case "desc" => "desc"
        case _ => "asc"
      }

    var query =
      sortingField.toLowerCase match {
        case "sex" => {"e.patient.sex %s".format(lex)}
        case "actiontype" | "at" => {"a.actionType.name %s".format(lex)}
        case "urgent" => {"a.isUrgent %s".format(lex)}
        case "tube" => {"a.actionType.testTubeType.name %s".format(lex)}
        case "status" => {"jt.status %s".format(lex)}
        case "fullname" | "fio" | "patient" => {"e.patient.lastName %s, e.patient.firstName %s, e.patient.patrName %s".format(lex,lex,lex)}
        case "birthdate" => {"e.patient.birthDate %s".format(lex)}
        case _ => {"e.patient.lastName %s, e.patient.firstName %s, e.patient.patrName %s".format(lex,lex,lex)}
      }
    query = "ORDER BY " + query
    query
  }

  def toQueryStructure() = {
    val qs = new QueryDataStructure()
    if(this.status>=0){
      qs.query += "AND jt.status = :status\n"
      qs.add("status",this.status:java.lang.Integer)
    }
    if(this.biomaterial>0){
      qs.query += ("AND attp.actionType.id = a.actionType.id AND attp.tissueType.id = :biomaterial")
      qs.add("biomaterial",this.biomaterial:java.lang.Integer)
    }
    qs
  }
}

@XmlType(name = "actionInfoDataContainer")
@XmlRootElement(name = "actionInfoDataContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class ActionInfoDataContainer {

  @BeanProperty
  var id: Int = _                              //Action.id
  @BeanProperty
  var actionType: IdNameContainer = _           //ActionType.id + ActionType.name
  @BeanProperty
  var patient: PatientInfoDataContainer = _     //Основная информация о пациенте
  @BeanProperty
  var urgent: Boolean = false                   //Срочность
  @BeanProperty
  var tubeType: TestTubeTypeInfoContainer = _   //Тип пробирки
  @BeanProperty
  var assigner: DoctorContainer = _             //Основная информация о назначевшем забор враче

  def this(action: Action) {
    this()
    this.id = action.getId.intValue()
    this.actionType = new IdNameContainer(action.getActionType.getId.intValue(),
      action.getActionType.getName)
    this.patient = new PatientInfoDataContainer(action.getEvent.getPatient)
    this.urgent = action.getIsUrgent
    this.tubeType = new TestTubeTypeInfoContainer(action.getActionType.getTestTubeType)
    this.assigner = new DoctorContainer(action.getAssigner)
  }
}

/**
 * Контейнер с данными об пациенте (ФИО, дата рождения, пол)
 */
@XmlType(name = "patientInfoDataContainer")
@XmlRootElement(name = "patientInfoDataContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class PatientInfoDataContainer {

  @BeanProperty
  var id: Int = _
  @BeanProperty
  var name: PersonNameContainer = new PersonNameContainer()
  @BeanProperty
  var birthDate: Date = _
  @BeanProperty
  var sex: String = _

  /**
   * Конструктор класса PatientInfoDataContainer
   * @param patient Информация о пациенте как Patient entity
   * @see Patient
   */
  def this(patient: Patient) {
    this()
    if(patient!=null){
      this.id = patient.getId.intValue()
      this.name = new PersonNameContainer(patient)
      this.birthDate = patient.getBirthDate
      this.sex = patient.getSex match {
        case 1 => "male"
        case 2 => "female"
        case _ => "unknown"
      }
    } else {
      LoggingManager.setLoggerType(LoggingManager.LoggingTypes.Debug)
      LoggingManager.warning("code " + ConfigManager.ErrorCodes.PatientIsNull +
        "PatientInfoDataContainer не заполнен данными: " + ConfigManager.Messages("error.patientIsNull"))
    }

  }
}

@XmlType(name = "testTubeTypeInfoContainer")
@XmlRootElement(name = "testTubeTypeInfoContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class TestTubeTypeInfoContainer {
  @BeanProperty
  var id: Int = _
  @BeanProperty
  var name: String = ""
  @BeanProperty
  var volume: Double = _
  @BeanProperty
  var covCol: String = ""
  @BeanProperty
  var color: String = ""

  /**
   * Конструктор класса TestTubeTypeInfoContainer
   * @param tubeType Информация о типе пробирки
   * @see RbTestTubeType
   */
  def this(tubeType: RbTestTubeType) {
    this()
    if(tubeType!=null) {
      this.id = tubeType.getId.intValue()
      this.name = tubeType.getName
      this.volume = tubeType.getVolume
      this.covCol = tubeType.getCovCol
      this.color = tubeType.getColor
    } else {
      LoggingManager.setLoggerType(LoggingManager.LoggingTypes.Debug)
      LoggingManager.warning("code " + ConfigManager.ErrorCodes.RbTestTubeTypeIsNull +
        "TestTubeTypeInfoContainer не заполнен данными: " + ConfigManager.Messages("error.rbTestTubeTypeIsNull"))
    }
  }
}

@XmlType(name = "jobTicketInfoContainer")
@XmlRootElement(name = "jobTicketInfoContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class JobTicketInfoContainer {

  @BeanProperty
  var id: Int = _
  @BeanProperty
  var date: Date = _                            //Дата и время
  @BeanProperty
  var status: Int = _                           //Статус
  @BeanProperty
  var label: String = _                         //Пометка(номер пробы)
  @BeanProperty
  var note: String = _                          //Примечание
  @BeanProperty
  var laboratory: IdNameContainer = _           //Лаборатория
  @BeanProperty
  var actions: LinkedList[ActionInfoDataContainer] = new LinkedList[ActionInfoDataContainer]  //Список акшенов для этого тикета

  def this(ticket: JobTicket, actionValues: LinkedList[Action]){
    this()
    if(ticket!=null) {
      this.id = ticket.getId.intValue()
      this.date = ticket.getDatetime
      this.status = ticket.getStatus
      this.label = ticket.getLabel
      this.note = ticket.getNote
      this.laboratory = if (ticket.getJob!=null && ticket.getJob.getOrgStructure!=null)
        new IdNameContainer(ticket.getJob.getOrgStructure.getId.intValue(),
          ticket.getJob.getOrgStructure.getName)
      else
        new IdNameContainer()
      actionValues.foreach(a => this.actions += new ActionInfoDataContainer(a))
    } else {
      LoggingManager.setLoggerType(LoggingManager.LoggingTypes.Debug)
      LoggingManager.warning("code " + ConfigManager.ErrorCodes.JobTicketIsNull +
        "JobTicketInfoContainer не заполнен данными: " + ConfigManager.Messages("error.rbTestTubeTypeIsNull"))
    }
  }
}

@XmlType(name = "jobTicketStatusDataList")
@XmlRootElement(name = "jobTicketStatusDataList")
class JobTicketStatusDataList {
  @BeanProperty
  var data: LinkedList[JobTicketStatusDataEntry] = new LinkedList[JobTicketStatusDataEntry]

  def this(values: LinkedList[JobTicket]) {
    this()
    values.foreach(f => this.data += new JobTicketStatusDataEntry(f))
  }
}

@XmlType(name = "jobTicketStatusDataEntry")
@XmlRootElement(name = "jobTicketStatusDataEntry")
class JobTicketStatusDataEntry {

  @BeanProperty
  var id: Int = _
  @BeanProperty
  var status: Int = _

  def this(ticket: JobTicket) {
    this()
    this.id = ticket.getId.intValue()
    this.status = ticket.getStatus
  }
}
