package ru.korus.tmis.core.data

import javax.xml.bind.annotation.XmlType._
import javax.xml.bind.annotation.{XmlRootElement, XmlType}
import javax.xml.bind.annotation.XmlRootElement._
import scala.beans.BeanProperty
import java.util.{Calendar, Date, LinkedList}
import ru.korus.tmis.core.entity.model._
import scala.collection.JavaConversions._
import java.text.SimpleDateFormat
import org.codehaus.jackson.map.annotate.JsonView
import collection.JavaConversions
import java.util
import org.codehaus.jackson.map.ObjectMapper
import ru.korus.tmis.scala.util.{I18nable, ConfigManager}
import scala.language.reflectiveCalls

/**
 * Типы возможных View для сериализации данных в контейнерах ReceivedPatients
 * @author Ivan Dmitriev
 */
object ReceivedPatientsDataViews {
  class AdmissionDepartmentsNurseView {
  }
  class AdmissionDepartmentsDoctorView {
  }
}
class ReceivedPatientsDataViews {}

/**
 * Контейнер c данными об пациентах поступивших за период (с данными запроса)
 */
@XmlType(name = "receivedPatientsData")
@XmlRootElement(name = "receivedPatientsData")
class ReceivedPatientsData extends I18nable  {
  @BeanProperty
  var requestData: ReceivedRequestData = new ReceivedRequestData()
  @BeanProperty
  var data: LinkedList[ReceivedPatientsEntry] = new LinkedList[ReceivedPatientsEntry]

  /**
   * Конструктор класса ReceivedPatientsData
   * @author Ivan Dmitriev
   * @param events Список обращений на госпитализацию.
   * @param request Данные из запроса.
   * @param mDiagnoses Делегируемый метод на получение диагнозов в виде списка МКВ по ключам
   * @param mMovingProperties Делегируемый метод для получения данных о движении пациента в виде списка значений ActionProperty
   * @param mHasPrimary Делегируемый метод для получения идентификатора первичного осмотра
   * @param mHospStatus Делегируемый метод для получения данных о статусе госпитализации
   * @param mIntake Делегируемый метод для получения Action типа 'Поступление'
   * @since 1.0.0.43
   */
  def this(events: java.util.List[Event],
           request:  ReceivedRequestData,
           mDiagnoses: (Int, String) => java.util.HashMap[String, java.util.List[Mkb]],
           mMovingProperties: (java.util.List[java.lang.Integer], java.util.Set[String], Int, Boolean) => util.LinkedHashMap[java.lang.Integer, util.LinkedHashMap[ActionProperty, java.util.List[APValue]]],
           mHasPrimary: (Int, java.util.Set[java.lang.Integer]) => Int,
           mHospStatus: (Int) => String,
           mIntake: (Int, Int) => Action) {
    this()
    this.requestData = request

    events.foreach(e=>{
      this.data.add(new ReceivedPatientsEntry(e,
                                              mDiagnoses,
                                              mMovingProperties,
                                              mHospStatus,
                                              mHasPrimary,
                                              mIntake,
                                              request.filter.asInstanceOf[ReceivedRequestDataFilter].role,
                                              request.filter.asInstanceOf[ReceivedRequestDataFilter].diagnosis))
    })
  }
}

/**
 * Контейнер c данными запроса об пациентах поступивших за период
 */
@XmlType(name = "receivedRequestData")
@XmlRootElement(name = "receivedRequestData")
class ReceivedRequestData {
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

  /**
   * Конструктор класса ReceivedRequestData
   * @param sortingField Поле, по которому сортируется список.
   * @param sortingMethod Метод сортировки списка.
   * @param limit Количество элементов в выводимом списке.
   * @param page Номер выводимой страницы.
   * @param filter Набор фильтров применяемых к списку.
   * @see ReceivedRequestDataFilter
   */
  def this(sortingField: String,
           sortingMethod: String,
           limit: Int,
           page: Int,
           filter: AnyRef) = {
    this()
    this.filter = filter
    this.sortingField = sortingField match {
      case null => {"id"}
      case _ => {sortingField}
    }

    this.sortingFieldInternal = this.filter.asInstanceOf[ReceivedRequestDataFilter].toSortingString(this.sortingField)

    this.sortingMethod = sortingMethod match {
      case null => {"asc"}
      case _ => {sortingMethod}
    }
    this.limit = if(limit>0){limit} else{10}
    this.page = if(page>1){page} else{1}
    this.coreVersion = ConfigManager.Messages("misCore.assembly.version")

  }
}

/**
 * Контейнер с данными об фильтрации списка пациентов поступивших за период
 */
@XmlType(name = "receivedRequestDataFilter")
@XmlRootElement(name = "receivedRequestDataFilter")
class ReceivedRequestDataFilter {
  @BeanProperty
  var eventId: Int = _ // — Номер обращения
  @BeanProperty
  var fullName: String = _ // — ФИО
  @BeanProperty
  var birthDate: Date = _  // — Дата рождения
  @BeanProperty
  var externalId: String = _ // — номер карточки пациента
  @BeanProperty
  var beginDate: Date = _
  @BeanProperty
  var endDate: Date = _
  @BeanProperty
  var diagnosis: String = _

  var role: Int = _

  /**
   * Конструктор класса ReceivedRequestDataFilter
   * @param eventId Идентификатор обращения s11r64.Event.id
   * @param fullName ФИО пациента.
   * @param birthDate Дата рождения пациента.
   * @param externalId Номер истории болезни пациента.
   * @param bDate  Дата начала периода, на который запрос списка.
   * @param eDate  Дата окончания периода, на который запрос списка.
   */
  def this(eventId: Int,
           fullName: String,
           birthDate: java.lang.Long,
           externalId: String,
           bDate: java.lang.Long,
           eDate: java.lang.Long) = {
    this()
    this.eventId = eventId
    this.fullName = fullName
    this.birthDate = if(birthDate==null) {null} else {new Date(birthDate.longValue())}
    this.externalId = externalId
    this.beginDate = if(bDate==null) {null} else {new Date(bDate.longValue())}
    this.endDate = if(eDate==null) {null} else {new Date(eDate.longValue())}
  }

  /**
   * Конструктор класса ReceivedRequestDataFilter
   * @param eventId Идентификатор обращения s11r64.Event.id
   * @param fullName ФИО пациента.
   * @param birthDate Дата рождения пациента.
   * @param externalId Номер истории болезни пациента.
   * @param bDate  Дата начала периода, на который запрос списка.
   * @param diagnosis Наименование диагноза по MKB (фильтр LIKE).
   */
  def this(eventId: Int,
           fullName: String,
           birthDate: java.lang.Long,
           externalId: String,
           bDate: java.lang.Long,
           eDate: java.lang.Long,
           diagnosis: String) = {
    this(eventId, fullName, birthDate, externalId, bDate, eDate)
    this.diagnosis = diagnosis
  }

  /**
   * Конструктор класса ReceivedRequestDataFilter
   * @param eventId Идентификатор обращения s11r64.Event.id
   * @param fullName ФИО пациента.
   * @param birthDate Дата рождения пациента.
   * @param externalId Номер истории болезни пациента.
   * @param bDate  Дата начала периода, на который запрос списка.
   * @param diagnosis Наименование диагноза по MKB (фильтр LIKE).
   * @param role Роль отправителя запроса на список (фильтр по полям вывода (сериализация))
   */
  def this(eventId: Int,
           fullName: String,
           birthDate: java.lang.Long,
           externalId: String,
           bDate: java.lang.Long,
           eDate: java.lang.Long,
           diagnosis: String,
           role: Int) = {
    this(eventId, fullName, birthDate, externalId, bDate, eDate, diagnosis)
    this.role = role
  }

  def this(externalId: String) = {
    this()
    this.externalId = externalId
  }

  /**
   * Метод на формирование структуры поисковых запросов, с использование набора фильтров ReceivedRequestDataFilter
   * @return Структурированный поисковый запрос в виде QueryDataStructure
   * @see QueryDataStructure
   */
  def toQueryStructure() = {
    val formatter = new SimpleDateFormat("yyyy-MM-dd")
    var qs = new QueryDataStructure()
    if(this.eventId>0){
      qs.query += "AND e.id = :eventId\n"
      qs.add("eventId",this.eventId:java.lang.Integer)
    }
    if(this.birthDate!=null){
      qs.query += ("AND e.patient.birthDate = :birthDate\n")
      qs.add("birthDate",this.birthDate)
    }
    if(this.externalId!=null && !this.externalId.isEmpty){
      qs.query += "AND e.externalId LIKE :externalId\n"
      qs.add("externalId", "%" + this.externalId + "%")
    }
    if(this.fullName!=null && !this.fullName.isEmpty){
      qs.query += "AND upper(e.patient.lastName) LIKE upper(:fullName)\n"
      qs.add("fullName","%"+this.fullName+"%")
    }
    if(this.beginDate!=null && this.endDate==null){
      var begDay = Calendar.getInstance()
      begDay.setTime(formatter.parse(formatter.format(this.beginDate)))
      begDay.set(Calendar.HOUR_OF_DAY, 0)
      val endDay = begDay.getTime.getTime + 24*60*60*1000 - 1
      qs.query += "AND e.setDate BETWEEN :beginDate AND :endDate\n"
      qs.add("beginDate", begDay.getTime)
      qs.add("endDate", new Date(endDay))
    }
    else if(this.endDate!=null && this.beginDate==null){
      var begDay = Calendar.getInstance()
      begDay.setTime(formatter.parse(formatter.format(this.endDate)))
      begDay.set(Calendar.HOUR_OF_DAY, 0)
      val endDay = begDay.getTime.getTime + 24*60*60*1000 - 1
      qs.query += "AND e.execDate BETWEEN :beginDate AND :endDate\n"
      qs.add("beginDate", begDay.getTime)
      qs.add("endDate", new Date(endDay))
    } else if(this.endDate!=null && this.beginDate!=null){
      qs.query += "AND e.setDate BETWEEN :beginDate AND :endDate\n"
      qs.add("beginDate",this.beginDate)
      qs.add("endDate",this.endDate)
    }
    qs
  }

  /**
   * Формирование строки сортировки для подстановки в запрос
   * @param sortingField Наименование поля сортировки
   * @return
   */
  def toSortingString (sortingField: String) = {
    sortingField match {
      case "createDatetime" => {"e.setDate"}
      case "number" => {"e.externalId"}
      case "fullName" => {"e.patient.lastName, e.patient.firstName, e.patient.patrName"}
      case "birthDate" => {"e.patient.birthDate"}
      case _ => {"e.id"}
    }
  }
}

/**
 * Контейнер c данными об пациентах поступивших за период
 */
@XmlType(name = "receivedPatientsEntry")
@XmlRootElement(name = "receivedPatientsEntry")
class ReceivedPatientsEntry extends I18nable  {

  @BeanProperty
  var id: Int = -1

  @BeanProperty
  var createDatetime: Date = _

  @BeanProperty
  var number: String = _

  @JsonView(Array(classOf[ReceivedPatientsDataViews.AdmissionDepartmentsDoctorView]))
  @BeanProperty
  var urgent: Boolean = _

  @BeanProperty
  var patient: IdPatientDataContainer = new IdPatientDataContainer

  @JsonView(Array(classOf[ReceivedPatientsDataViews.AdmissionDepartmentsDoctorView]))
  @BeanProperty
  var diagnoses: LinkedList[DiagnosisSimplifyContainer] = new LinkedList[DiagnosisSimplifyContainer]

  @JsonView(Array(classOf[ReceivedPatientsDataViews.AdmissionDepartmentsNurseView]))
  @BeanProperty
  var moving: MovingContainer = _

  @JsonView(Array(classOf[ReceivedPatientsDataViews.AdmissionDepartmentsDoctorView]))
  @BeanProperty
  var havePrimary: Boolean = false

  @JsonView(Array(classOf[ReceivedPatientsDataViews.AdmissionDepartmentsNurseView]))
  @BeanProperty
  var status: String = _

  /**
   * Конструктор класса ReceivedPatientsEntry
   * @param event Идентификатор обращения.
   */
  def this(event: Event) {
    this()
    this.id = event.getId.intValue()
    this.createDatetime = event.getSetDate
    this.number = event.getExternalId
    this.patient = new IdPatientDataContainer(event.getPatient)
  }

  /**
   * Конструктор класса ReceivedPatientsEntry
   * @param event Обращение.
   * @param mDiagnoses Делегируемый метод на получение диагнозов в виде списка МКВ по ключам
   * @param mMovingProperties Делегируемый метод для получения данных о движении пациента в виде списка значений ActionProperty
   * @param mHasPrimary Делегируемый метод для получения идентификатора первичного осмотра
   * @param mHospStatus Делегируемый метод для получения данных о статусе госпитализации
   * @param mIntake Делегируемый метод для получения Action типа 'Поступление'
   * @param roleId Идентификатор роли отправителя запроса на список
   * @param filter Филдьтр по обозначению диагноза МКВ
   * @since 1.0.0.43
   */
  def this(event: Event,
           mDiagnoses: (Int, String) => java.util.HashMap[String, java.util.List[Mkb]],
           mMovingProperties: (java.util.List[java.lang.Integer], java.util.Set[String], Int, Boolean) => util.LinkedHashMap[java.lang.Integer, util.LinkedHashMap[ActionProperty, java.util.List[APValue]]],
           mHospStatus: (Int) => String,
           mHasPrimary: (Int, java.util.Set[java.lang.Integer]) => Int,
           mIntake: (Int, Int) => Action,
           roleId: Int,
           filter: String) {         //ургент из ар.экшн.гетургент
    this(event)

    roleId match {
      case 29 => {//Сестра приемного отделения
        this.status = if (mHospStatus != null) mHospStatus(event.getId.intValue()) else ""    //только для роли сестра приемного отделения
        val eventIds: java.util.List[java.lang.Integer] = new util.LinkedList[java.lang.Integer]();
        eventIds.add(event.getId.intValue())
        if (status.compareTo(ConfigManager.Messages("patient.status.sentTo"))==0) {
          //Движения
          val setATCodes = JavaConversions.setAsJavaSet(Set(i18n("db.apt.received.codes.orgStructDirection"),
                                                         i18n("db.apt.moving.codes.orgStructTransfer")))
          if (mMovingProperties!=null){
            val moveProps = mMovingProperties(eventIds, setATCodes, 1, false)
            if (moveProps!=null && moveProps.size>0) {
              val filtred = moveProps.get(event.getId.intValue()).filter(element=>element._2.size>0)
              if (filtred.size>0){
                var res = filtred.find(f => {f._1.getType.getCode.compareTo(i18n("db.apt.received.codes.orgStructDirection"))==0}).getOrElse(null)
                var res2 = filtred.find(f => {f._1.getType.getCode.compareTo(i18n("db.apt.moving.codes.orgStructTransfer"))==0}).getOrElse(null)
                if (res2 != null) {
                  this.moving = new MovingContainer(res2._2.get(0).getValue.asInstanceOf[OrgStructure])
                } else {
                  this.moving = new MovingContainer(res._2.get(0).getValue.asInstanceOf[OrgStructure])
                }
              }
            }
          }
        }
        else if (status.compareTo(ConfigManager.Messages("patient.status.regToBed"))==0){
          val setATCodes = JavaConversions.setAsJavaSet(Set(i18n("db.apt.moving.codes.hospitalBed"),
                                                         i18n("db.apt.moving.codes.hospOrgStruct")))
          if (mMovingProperties!=null){
            val moveProps = mMovingProperties(eventIds, setATCodes, 1, false)
            if (moveProps!=null && moveProps.size>0) {
              val filtred = moveProps.get(event.getId.intValue()).filter(element=>element._2.size>0)
              if (filtred.size>0){
                var res = filtred.find(f => {f._1.getType.getCode.compareTo(i18n("db.apt.moving.codes.hospitalBed"))==0}).getOrElse(null)
                var res2 = filtred.find(f => {f._1.getType.getCode.compareTo(i18n("db.apt.moving.codes.hospOrgStruct"))==0}).getOrElse(null)
                val department = if(res2!=null)res2._2.get(0).getValue.asInstanceOf[OrgStructure] else null
                val bed = if(res!=null)res._2.get(0).getValueAsString else null
                this.moving = new MovingContainer(department, bed)
              }
            }
          }
        }
      }
      case _  => {
        this.havePrimary = if (mHasPrimary != null) {
          val setATIds = JavaConversions.setAsJavaSet(Set(ConfigManager.Messages("db.actionType.primary").toInt :java.lang.Integer,
            ConfigManager.Messages("db.actionType.secondary").toInt :java.lang.Integer))
          val primaryId = mHasPrimary(event.getId.intValue(), setATIds)
          if(primaryId>0) true else false
        } else false

        this.urgent = if (mIntake != null) {
          val action = mIntake(event.getId.intValue(),ConfigManager.Messages("db.actionType.hospitalization.primary").toInt)
          if (action!=null) action.getIsUrgent else false
        } else false

          if (mDiagnoses!=null){
          mDiagnoses(event.getId.intValue(), filter).foreach(f => {
            f._2.foreach(mkb => this.diagnoses += new DiagnosisSimplifyContainer(f._1, mkb))
          })
        }
      }
    }
  }
}

/**
 * Контейнер с данными об пациенте (ФИО, дата рождения, пол, контактная информация)
 */
@XmlType(name = "idPatientDataContainer")
@XmlRootElement(name = "idPatientDataContainer")
class IdPatientDataContainer {

  @BeanProperty
  var id: Int = -1
  @BeanProperty
  var name: PersonNameContainer = new PersonNameContainer()
  @BeanProperty
  var birthDate: Date = _
  @BeanProperty
  var sex: String = _
  @BeanProperty
  var phones: LinkedList[ClientContactContainer] = new LinkedList[ClientContactContainer]()

  /**
   * Конструктор класса IdPatientDataContainer
   * @param patient Информация о пациенте как Patient entity
   * @see Patient
   */
  def this(patient: Patient) {
    this()
    this.id = patient.getId.intValue()
    this.name = new PersonNameContainer(patient)
    this.birthDate = patient.getBirthDate
    this.sex = patient.getSex match {
      case 1 => "male"
      case 2 => "female"
      case _ => "unknown"
    }
    patient.getClientContacts.foreach(c => this.phones += new ClientContactContainer(c))
  }
}

/**
 * Контейнер с данными об диагнозах по МКВ
 */
@XmlType(name = "diagnosisSimplifyContainer")
@XmlRootElement(name = "diagnosisSimplifyContainer")
class DiagnosisSimplifyContainer {
  @BeanProperty
  var diagnosisKind: String = _
  @BeanProperty
  var mkb: MKBContainer = new MKBContainer()

  /**
   * Конструктор класса DiagnosisSimplifyContainer
   * @param kind Тип диагноза.
   * @param mkb  Данные о диагнозе МКВ как MKBContainer
   * @see MKBContainer
   */
  def this (kind: String, mkb: Mkb){
    this()
    this.diagnosisKind = kind
    this.mkb = new MKBContainer(mkb)
  }
}

/**
 * Контейнер с данными о местоположении пациента
 */
@XmlType(name = "movingContainer")
@XmlRootElement(name = "movingContainer")
class MovingContainer {
  @BeanProperty
  var department: IdNameContainer = _
  @BeanProperty
  var room: String = _
  @BeanProperty
  var bed: String = _

  /**
   * Конструктор класса MovingContainer
   * @param departmentId Идентификатор отделения.
   * @param room Палата.
   * @param bed Койка.
   */
  def this (departmentId: Int,
            room: String,
            bed: String){
    this()
    this.department = new IdNameContainer(departmentId, "")
    this.room = room
    this.bed = bed
  }

  /**
   * Конструктор класса MovingContainer
   * @param department Отделение как OrgStructure
   * @see OrgStructure
   */
  def this (department: OrgStructure){
    this()
    if (department!=null)
      this.department = new IdNameContainer(department.getId.intValue(), department.getName)
  }

  /**
   * Конструктор класса MovingContainer
   * @param department Отделение как OrgStructure
   * @param bed Номер койки
   * @see OrgStructure
   */
  def this (department: OrgStructure, bed: String) {
    this(department)
    this.bed = bed
  }
}