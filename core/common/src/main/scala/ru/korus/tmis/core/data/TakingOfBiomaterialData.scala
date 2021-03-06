package ru.korus.tmis.core.data

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter
import javax.xml.bind.annotation.{XmlRootElement, XmlType}
import org.codehaus.jackson.annotate.JsonIgnoreProperties
import ru.korus.tmis.core.data.adapters.DateTimeAdapter
import scala.beans.BeanProperty
import java.util.{Calendar, Date}
import ru.korus.tmis.core.entity.model._
import java.text.SimpleDateFormat
import java.util.LinkedList
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import java.util
import collection.JavaConversions
import ru.korus.tmis.scala.util.ConfigManager
import scala.language.reflectiveCalls

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

  def this(values: java.util.Map[JobTicket, java.util.LinkedList[(Action, ActionTypeTissueType)]],
           mLastMovingAction: (Int) => Action,
           mActionPropertiesWithValues: (Int, java.util.List[java.lang.Integer]) =>  java.util.Map[ActionProperty, java.util.List[APValue]],
           request: TakingOfBiomaterialRequesData) {
    this()
    this.requestData = request
    values.foreach(f => {
      this.data += new JobTicketInfoContainer(f._1, f._2, mLastMovingAction, mActionPropertiesWithValues)
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
  var jobTicketId: Int = -1
  @BeanProperty
  var departmentId: Int = _
  @XmlJavaTypeAdapter(classOf[DateTimeAdapter]) @BeanProperty var beginDate: Date = _
  @XmlJavaTypeAdapter(classOf[DateTimeAdapter]) @BeanProperty var endDate: Date = _
  @BeanProperty
  var status: Short = -1
  @BeanProperty
  var biomaterial: Int = -1
  @BeanProperty
  var labs: util.List[String] = _

  def this( jobTicketId: Int,
            departmentId: Int,
            beginDate: Long,
            endDate: Long,
            status: Short,
            biomaterial: Int,
            lab: util.List[String] = null) {
    this()
    this.jobTicketId = jobTicketId
    this.departmentId = departmentId
    this.status = status
    this.biomaterial = biomaterial
    this.labs = lab

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

}

@XmlType(name = "actionInfoDataContainer")
@XmlRootElement(name = "actionInfoDataContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class ActionInfoDataContainer {

  @BeanProperty
  var id: Int = _                               //Action.id
  @BeanProperty
  var actionType: IdNameContainer = _           //ActionType.id + ActionType.name
  @BeanProperty
  var takenTissueJournal: Int = _               //номер истолии болезни
  @BeanProperty
  var urgent: Boolean = false                   //Срочность
  @BeanProperty
  var biomaterial: TissueTypeContainer = _      //биоматериал
  @BeanProperty
  var tubeType: TestTubeTypeInfoContainer = _   //Тип пробирки
  @BeanProperty
  var patient: PatientInfoDataContainer = _     //Основная информация о пациенте
  @BeanProperty
  var labs: util.Set[String] = _                //Лаборатории, к которым привязан Action через ActionPropertyTypes

  def this(action: Action, tissueType: ActionTypeTissueType) {
    this()
    if (action!=null){
      this.id = action.getId.intValue()
      this.actionType = new IdNameContainer(action.getActionType.getId.intValue(), action.getActionType.getName)
      this.urgent = action.getIsUrgent
      this.tubeType = new TestTubeTypeInfoContainer(action.getActionType.getTestTubeType)
      if (action.getEvent!=null && action.getEvent.getPatient!=null)
        this.patient = new PatientInfoDataContainer(action.getEvent.getPatient)
      if (action.getTakenTissue!=null)
        this.takenTissueJournal = action.getTakenTissue.getBarcode
      labs = action.getActionType.getActionPropertyTypes
        .flatMap(p => Option(p.getTest))
        .map(_.getRbLaboratoryTest.getRbLaboratory.getName).toSet.asJava
    }
    if(tissueType!=null)
      this.biomaterial = new TissueTypeContainer(tissueType)
    //this.assigner = new DoctorContainer(action.getAssigner)
  }
}

@XmlType(name = "tissueTypeContainer")
@XmlRootElement(name = "tissueTypeContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class TissueTypeContainer {

  @BeanProperty
  var id: Int = _                              //ActionTypeTissueType.id
  @BeanProperty
  var tissueType: IdNameContainer = _           //RbTissueType.id + RbTissueType.name
  @BeanProperty
  var amount: Int = _                           //объем материала   ActionTypeTissueType.amount
  @BeanProperty
  var unit: IdNameContainer = _                 //Единицы измерения    rbUnit.id + rbUnit.name

  def this(tissueType: ActionTypeTissueType) {
    this()
    this.id = tissueType.getId.intValue()
    if (tissueType.getTissueType != null) {
      this.tissueType = new IdNameContainer(tissueType.getTissueType.getId, tissueType.getTissueType.getName)
    }
    this.amount = tissueType.getAmount
    if (tissueType.getUnit != null) {
      this.unit = new IdNameContainer(tissueType.getUnit.getId.intValue(), tissueType.getUnit.getName)
    }
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
    }
  }
}

@XmlType(name = "jobTicketInfoContainer")
@XmlRootElement(name = "jobTicketInfoContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class JobTicketInfoContainer {

  @BeanProperty
  var id: Int = _
  @XmlJavaTypeAdapter(classOf[DateTimeAdapter]) @BeanProperty var date: Date = _                            //Дата и время
  @BeanProperty
  var status: Int = _                           //Статус
  @BeanProperty
  var label: String = _                         //Пометка(номер пробы)
  @BeanProperty
  var note: String = _                          //Примечание
  @BeanProperty
  var appealNumber: String = _                  //номер истолии болезни
  @BeanProperty
  var department: IdNameContainer = _           //Лаборатория
  @BeanProperty
  var patient: PatientInfoDataContainer = _     //Основная информация о пациенте
  @BeanProperty
  var biomaterial: TissueTypeContainer = _      //биоматериал
  @BeanProperty
  var assigner: DoctorContainer = _             //Основная информация о назначевшем забор враче
  @BeanProperty
  var actions: LinkedList[ActionInfoDataContainer] = new LinkedList[ActionInfoDataContainer]  //Список акшенов для этого тикета
  @BeanProperty
  var bed: IdNameAndCodeContainer = _     //Палата/Койка

  def this(ticket: JobTicket,
           actionValues: LinkedList[(Action, ActionTypeTissueType)],
           mLastMovingAction: (Int) => Action,
           mActionPropertiesWithValues: (Int, java.util.List[java.lang.Integer]) =>  java.util.Map[ActionProperty, java.util.List[APValue]]){
    this()
    if(ticket!=null) {
      this.id = ticket.getId.intValue()
      this.date = ticket.getDatetime
      this.status = ticket.getStatus
      this.label = ticket.getLabel
      this.note = ticket.getNote
      this.department =
        if (ticket.getJob!=null && ticket.getJob.getOrgStructure!=null)
          new IdNameContainer(ticket.getJob.getOrgStructure.getId.intValue(), ticket.getJob.getOrgStructure.getName)
        else new IdNameContainer()

      if (actionValues!=null && actionValues.size()>0){
        this.patient = new PatientInfoDataContainer(actionValues.get(0)._1.getEvent.getPatient)
        this.biomaterial = new TissueTypeContainer(actionValues.get(0)._2)
        this.assigner = new DoctorContainer(actionValues.get(0)._1.getAssigner)
        this.appealNumber = actionValues.get(0)._1.getEvent.getExternalId
        if (mLastMovingAction!=null && mActionPropertiesWithValues != 0){ //TODO Проверить поведение и убрать warning
          val moving = mLastMovingAction(actionValues.get(0)._1.getEvent.getId.intValue())
          if (moving!=null){
            val listMovingAP = JavaConversions.seqAsJavaList(List(ConfigManager.RbCAPIds("db.rbCAP.moving.id.bed").toInt :java.lang.Integer))
            val apValues = mActionPropertiesWithValues(moving.getId.intValue(), listMovingAP)
            if (apValues!=null && apValues.size>0){
              val values = apValues.iterator.next()._2
              if (values!=null && values.size()>0){
                val bed = values.get(0).asInstanceOf[APValueHospitalBed].getValue
                this.bed = new IdNameAndCodeContainer(bed.getId.intValue(),
                                                      bed.getName,
                                                      bed.getCode)
                this.department = new IdNameContainer(bed.getMasterDepartment.getId.intValue(),
                                                      bed.getMasterDepartment.getName)
              }
            }
          }
        }
        actionValues.foreach(a => this.actions += new ActionInfoDataContainer(a._1, a._2))
      }
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


@XmlType(name = "SendActionsToLaboratoryDataList")
@XmlRootElement(name = "SendActionsToLaboratoryDataList")
class SendActionsToLaboratoryDataList {
  @BeanProperty
  var data: util.LinkedList[JobTicketWithActions] = new util.LinkedList[JobTicketWithActions]
}

@XmlType(name = "JobTicketWithAction")
@XmlRootElement(name = "JobTicketWithAction")
class JobTicketWithActions{
  @BeanProperty
  var id: Int = _
  @BeanProperty
  var data: util.LinkedList[ActionIdentifier] = new util.LinkedList[ActionIdentifier]
}

@XmlType(name = "ActionIdentifier")
@XmlRootElement(name = "ActionIdentifier")
class ActionIdentifier{
  @BeanProperty
  var id: Int = _
}

