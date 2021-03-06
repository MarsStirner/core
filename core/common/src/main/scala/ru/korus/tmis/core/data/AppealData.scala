package ru.korus.tmis.core.data

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter
import javax.xml.bind.annotation.{XmlSeeAlso, XmlRootElement, XmlType}
import ru.korus.tmis.core.data.adapters.DateAdapter

import scala.beans.BeanProperty
import javax.xml.bind.annotation.XmlType._
import ru.korus.tmis.core.entity.model._
import fd.FDRecord
import kladr.{Street, Kladr}
import collection.JavaConversions._

import java.util.{Date, HashMap, LinkedList}
import javax.xml.bind.annotation.XmlRootElement._
import org.codehaus.jackson.map.annotate.JsonView
import org.codehaus.jackson.annotate.{JsonAutoDetect, JsonMethod, JsonIgnoreProperties, JsonProperty}
import collection.mutable.LinkedHashSet
import java.util
import collection.JavaConversions
import ru.korus.tmis.scala.util.{I18nable, ConfigManager}
import beans.BeanProperty
import scala.language.reflectiveCalls

/**
 * Dynamic Filters
 */
object Views {
  class DynamicFieldsStandartForm {
  }
  class DynamicFieldsPrintForm {
  }
}
class Views {}

/**
 * Контейнер для хранения данных об обращении на госпитализацию<br>
 * С данными из запроса с клиента.
 */
@XmlType(name = "appealData")
@XmlRootElement(name = "appealData")
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlSeeAlso(Array(classOf[IdValueContainer]))
class AppealData extends I18nable {
  @BeanProperty
  var requestData: AppealRequestData = _
  @BeanProperty
  var data: AppealEntry = new AppealEntry()

  /**
   * Конструктор класса AppealData
   * @param event Обращение на госпитализацию
   * @param appeal Первичный осмотр при поступлении
   * @param values Значения свойств действий
   * @param mMovingProperties Делегируемый метод поиска  указанных свойств последних действий указанного типа для заданного Event.
   * @param typeOfResponse Тип запроса.<pre>
   * &#15;Возможные значения:
   * &#15;"standart" - (по умолчанию) Данные об госпитализации.
   * &#15;"print_form" - Печатная форма госпитализации. Данные об госпитализации + данные об пациенте</pre>
   * @param map Информация об адресах КЛАДР
   * @param street Информация об адресах Street
   * @param requestData Данные из запроса с клиента как AppealRequestData
   * @param postProcessing Делегируемый метод по поиску идентификатора первичного осмотра по идентификатору обращения.

   * @param contract Контракт
   * @param mDiagnosticList Делегируемый метод по получению диагностик госпитализации
   */

  def this(event: Event,
           appeal: Action,
           values: java.util.Map[(java.lang.Integer, ActionProperty), java.util.List[Object]],
           mMovingProperties: (java.util.List[java.lang.Integer], java.util.Set[String], Int, Boolean) => java.util.LinkedHashMap[java.lang.Integer, java.util.LinkedHashMap[ActionProperty, java.util.List[APValue]]],
           typeOfResponse: String,
           map: java.util.LinkedHashMap[java.lang.Integer, java.util.LinkedList[Kladr]],
           street: java.util.LinkedHashMap[java.lang.Integer, Street],
           requestData: AppealRequestData,
           postProcessing: (Int, java.util.Set[java.lang.Integer]) => Int,
           eventClientRelations: java.util.List[EventClientRelation],
           mAdmissionDiagnosis: (Int , java.util.Set[String]) => java.util.Map[ActionProperty, java.util.List[APValue]],
           contract: Contract,
           currentDepartment: OrgStructure,
           mDiagnosticList: (Int, java.util.Set[String]) => java.util.List[Diagnostic],
           tempInvalid: TempInvalid,
           eventLocalContract: EventLocalContract) {
    this ()

    setRequestData(requestData)

    val setMovingIds = JavaConversions.setAsJavaSet(Set(i18n("db.apt.received.codes.orgStructDirection"),
                                                     i18n("db.apt.moving.codes.orgStructTransfer")))

    val diagnostics = if(mDiagnosticList!=null)mDiagnosticList(event.getId.intValue(), Set(i18n("appeal.diagnosis.diagnosisKind.diagReceivedMkb"),
                                                                                           i18n("appeal.diagnosis.diagnosisKind.admissionMkb"),
                                                                                           i18n("appeal.diagnosis.diagnosisKind.finalMkb"),
                                                                                           i18n("appeal.diagnosis.diagnosisKind.aftereffectMkb"),
                                                                                           i18n("appeal.diagnosis.diagnosisKind.attendantMkb")))
                      else new java.util.ArrayList[Diagnostic]

      //Set("assignment", "aftereffect", "attendant", "final", "admission")) else new java.util.ArrayList[Diagnostic]

    this.data =
      if (postProcessing != null) {
      // Первичный и повторный осмотр
      // (список идентификаторов типов действий)
      val setATIds = JavaConversions.setAsJavaSet(Set(ConfigManager.Messages("db.actionType.primary").toInt :java.lang.Integer,
                                                   ConfigManager.Messages("db.actionType.secondary").toInt :java.lang.Integer))
      // (список рассматриваемых свойств действия)
      val setAdmissionIds = JavaConversions.setAsJavaSet(Set(i18n("appeal.diagnosis.diagnosisKind.assignment"),
                                                          i18n("appeal.diagnosis.diagnosisKind.mainDiag"),
                                                          i18n("db.apt.received.codes.orgStructDirection"),
                                                          i18n("db.apt.moving.codes.orgStructTransfer")
      ))

      //Выписка
      // (список идентификаторов типов действий)
      val setExtractATIds = JavaConversions.setAsJavaSet(Set(ConfigManager.Messages("db.actionType.extract").toInt :java.lang.Integer))
      // (список рассматриваемых свойств действия)
      val setExtractIds = JavaConversions.setAsJavaSet(Set(i18n("db.apt.moving.codes.hospOrgStruct"),
                                                        i18n("db.apt.leaved.codes.hospOutcome"),
                                                        i18n("db.apt.leaved.codes.nextHospDate"),
                                                        i18n("db.apt.leaved.codes.nextHospFinance"),
                                                        i18n("db.apt.leaved.codes.hospLength")
      ))

       // val oldLastValues = actionPropertyBean.getActionPropertiesByActionIdAndActionPropertyTypeCodes(oldLastAction.getId.intValue,
      //   listMovAP)


      val lstAllIds = new java.util.ArrayList[java.lang.String](setAdmissionIds)
      lstAllIds.addAll(setExtractIds)
      lstAllIds.addAll(setMovingIds)

      val primaryId = postProcessing(event.getId.intValue(), setATIds)
      val admissions = if (mAdmissionDiagnosis!=null && primaryId>0) mAdmissionDiagnosis(primaryId, setAdmissionIds) else null

      val extractId = postProcessing(event.getId.intValue(), setExtractATIds)
      val extractProperties = if (mAdmissionDiagnosis!=null && extractId>0) mAdmissionDiagnosis(extractId, setExtractIds) else null

      new AppealEntry(event, appeal, values, mMovingProperties, typeOfResponse, map, street, (primaryId>0), eventClientRelations, admissions, extractProperties, null, contract, currentDepartment, diagnostics, tempInvalid, eventLocalContract)
    } else {
      new AppealEntry(event, appeal, values, mMovingProperties, typeOfResponse, map, street, false, eventClientRelations, null, null, null, contract, currentDepartment, diagnostics, tempInvalid, eventLocalContract)
     }
  }
}

/**
 * Контейнер для хранения данных запроса с клиента
 */
@XmlType(name = "appealRequestData")
@XmlRootElement(name = "appealRequestData")
@JsonIgnoreProperties(ignoreUnknown = true)
class AppealRequestData {
  @BeanProperty
  var filter: AppealRequestDataFilter = _
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

  /**
   * Конструктор AppealRequestData
   * @param eventId   Идентификатор обращения.
   * @param fullName  Значение фильтра по ФИО пациента.
   * @param birthDate Значение фильтра по дате рождения пациента.
   * @param externalId Значение фильтра по номеру истории болезни (НИБ)
   * @param sortingField Поле для сортировки
   * @param sortingMethod Метод сортировки
   * @param limit Количество записей на странице
   * @param page Порядковый номер страницы
   */
  def this(eventId: String,
           fullName: String,
           birthDate: Date,
           externalId: String,
           sortingField: String,
           sortingMethod: String,
           limit: String,
           page: String) = {
    this()
    this.filter = new AppealRequestDataFilter(eventId, fullName, birthDate, externalId)
    this.sortingField = sortingField
    this.sortingMethod = sortingMethod
    this.limit = limit
    this.page = page
  }
}

/**
 * Контейнер с данными фильтрации поискового запроса с клиента
 */
class AppealRequestDataFilter {
  @BeanProperty
  var eventId: String = _ // — Номер обращения
  @BeanProperty
  var fullName: String = _ // — ФИО
  @BeanProperty
  var birthDate: Date = _  // — Дата рождения
  @BeanProperty
  var externalId: String = _ // — номер карточки пациента

  /**
   * Конструктор AppealRequestDataFilter
   * @param eventId Идентификатор обращения
   * @param fullName ФИО пациента
   * @param birthDate Дата рождения
   * @param externalId Номер истории болезни(НИБ)
   */
  def this(eventId: String,
           fullName: String,
           birthDate: Date,
           externalId: String) = {
    this()
    this.eventId = eventId
    this.fullName = fullName
    this.birthDate = birthDate
    this.externalId = externalId
  }
}

/**
 * Контейнер для хранения данных об госпитализации
 */
@XmlType(name = "appealEntry")
@XmlRootElement(name = "appealEntry")
@JsonIgnoreProperties(ignoreUnknown = true)
class AppealEntry extends I18nable {
  @BeanProperty
  var id: Int = _
  @BeanProperty
  var version: Int = _
  @BeanProperty
  var number: String = _                   //Номер обращения
  @BeanProperty
  var urgent: Boolean = _                  //Срочно
  @BeanProperty
  var setPerson: ComplexPersonContainer = _            //Данные о враче и отделении
  @BeanProperty
  var execPerson: DoctorContainer = _            //Лечащий врач
  @BeanProperty
  var ambulanceNumber: String = _          //Номер наряда СП
  @BeanProperty
  var rangeAppealDateTime: DatePeriodContainer = _    //Дата начала и конца госпитализации
  @BeanProperty
  var patient: AnyRef = _
  @BeanProperty
  var appealType: AppealTypeContainer = _      //Тип обращен
  //var appealType: IdNameContainer = _      //Тип обращения
  @BeanProperty
  var agreedType: IdNameContainer = _      //Тип согласования
  @BeanProperty
  var agreedDoctor: String = _      //Комментарий к согласованию
  @BeanProperty
  var assignment: AppealAssignmentContainer = _  //Назначения
  @BeanProperty
  var hospitalizationType: IdNameContainer = _   //Госпитализация тип
  @BeanProperty
  var hospitalizationPointType: IdNameContainer = _  //Цель госпитализации
  @BeanProperty
  var hospitalizationChannelType: IdNameContainer = _  //Канал госпитализации
  @BeanProperty
  var hospitalizationWith: LinkedList[LegalRepresentativeContainer] = new LinkedList[LegalRepresentativeContainer] //С кем госпитализирован (законный представитель)
  @BeanProperty
  var deliveredType: String = _//IdNameContainer = _    //Кем доставлен
  @BeanProperty
  var deliveredAfterType: String = _ //IdNameContainer = _    //Доставлен от начала заболевания через
  @BeanProperty
  var movingType: String = "может идти"     //Вид транспортировки  (!)
  @BeanProperty
  var stateType: String =_//IdNameContainer = _       //Состояние при поступлении
  @BeanProperty
  var physicalParameters: PhysicalParametersContainer =_    //Физические параметры
  @BeanProperty
  var diagnoses: LinkedList[DiagnosisContainer] = new LinkedList[DiagnosisContainer]    //Диагнозы
  @BeanProperty
  var injury: String =_       //Травма
  @BeanProperty
  var refuseAppealReason: String =_       //Причина отказа в госпитализации
  @BeanProperty
  var appealWithDeseaseThisYear: String =_       //Госпитализирован по поводу данного заболевания в текущем году
  @BeanProperty
  var havePrimary: Boolean = false
  @BeanProperty
  var contract: ContractContainer = _            //Контракт

  @JsonView(Array(classOf[Views.DynamicFieldsPrintForm]))
  @BeanProperty
  var ward: OrgStructureContainer = _                                           //Отделение
  @JsonView(Array(classOf[Views.DynamicFieldsPrintForm]))
  @BeanProperty
  var totalDays: String = _                                                     //Проведено койко-дней
  @BeanProperty
  var currentDepartment: IdNameContainer = _
  //данные о последующей госпитализации (reeadonly)
  //согласно спецификации: https://docs.google.com/spreadsheet/ccc?key=0Au-ED6EnawLcdHo0Z3BiSkRJRVYtLUxhaG5uYkNWaGc#gid=5
  @BeanProperty
  var leaved: LeavedInfoContainer = _
  @BeanProperty
  var result: IdNameContainer = _
  @BeanProperty
  var acheResult: IdNameContainer = _
  @BeanProperty
  var tempInvalid: TempInvalidAppealContainer = _   //
  @BeanProperty
  var laboratory: LaboratoryPropertiesContainer = _ //
  @BeanProperty
  var preHospitalDefects: String = _                //
  @BeanProperty
  var closed: Boolean = false                       //Флаг закрыта ли госпитализация
  @BeanProperty
  var closeDateTime: Date = _                       //Дата закрытия госпитализации госпитализация
  @BeanProperty
  var orgStructStay: Int = _                        //Отделение поступления
  @BeanProperty
  var orgStructDirectedFrom: Int = _                //Направлен из
  @BeanProperty
  var reopening: String = _                         //Повторное обращение
  @BeanProperty
  var context: String = _                           //Контекст печати
  @BeanProperty
  var payer: PayerInfo = _                          //Плательщик
  @BeanProperty
  var paymentContract: PaymentContractInfo = _

  /**
   * Конструктор класса AppealEntry
   * @param event Обращение на госпитализацию
   * @param action Первичный осмотр при поступлении
   * @param values Значения свойств действий
   * @param mMovingProperties Делегируемый метод поиска  указанных свойств последних действий указанного типа для заданного Event.
   * @param typeOfResponse Тип запроса.<pre>
   * &#15;Возможные значения:
   * &#15;"standart" - (по умолчанию) Данные об госпитализации.
   * &#15;"print_form" - Печатная форма госпитализации. Данные об госпитализации + данные об пациенте</pre>
   * @param map Информация об адресах КЛАДР
   * @param street Информация об адресах Street
   * @param havePrimary Флаг, имеется ли в текущей госпитализации первичный осмотр
   * @param eventClientRelations представители
   * @param extractProperties Список свойств действия с данными из выписки
   * @param corrList Список RbCoreActionProperty для поиска соответствия идентификаторов
   * @param contract Контракт
   * @param diagnostics Список диагнозов
   */
  def this(event: Event,
           action: Action,
           values: java.util.Map[(java.lang.Integer, ActionProperty), java.util.List[Object]],
           mMovingProperties: (java.util.List[java.lang.Integer], java.util.Set[String], Int, Boolean) => util.LinkedHashMap[java.lang.Integer, util.LinkedHashMap[ActionProperty, java.util.List[APValue]]],
           typeOfResponse: String,
           map: java.util.LinkedHashMap[java.lang.Integer, java.util.LinkedList[Kladr]],
           street: java.util.LinkedHashMap[java.lang.Integer, Street],
           havePrimary: Boolean,
           eventClientRelations: java.util.List[EventClientRelation],
           admissions: java.util.Map[ActionProperty, java.util.List[APValue]],
           extractProperties: java.util.Map[ActionProperty, java.util.List[APValue]],
           corrList: java.util.List[RbCoreActionProperty],
           contract: Contract,
           currentDepartment: OrgStructure,
           diagnostics: java.util.List[Diagnostic],
           tempInvalid: TempInvalid,
           eventLocalContract: EventLocalContract) {
    this()
    var exValue: java.util.List[Object] = null

    //Обращение и Действие
    this.id = event.getId.intValue()
    this.closed =  (event.getExecDate!=null)
    this.closeDateTime = event.getExecDate
    this.version = event.getVersion
    this.number = event.getExternalId
    this.setPerson = if (event.getAssigner != null) {new ComplexPersonContainer(event.getAssigner)} else {new ComplexPersonContainer}
    this.execPerson = new DoctorContainer(event.getExecutor)
    this.urgent = if(action == null) false else action.getIsUrgent
    this.context = if (event.getEventType != null) event.getEventType.getContext else null
    this.payer = if (eventLocalContract == null) null else new PayerInfo(eventLocalContract)
    this.paymentContract = if (eventLocalContract == null) null else new PaymentContractInfo(eventLocalContract)

    val getOrgStructPropByCode: String => Integer = (code: String) => {
      val orgs = values.entrySet.filter(e => {
        val ap = e.getKey._2
        if(ap != null && ap.getType != null && ap.getType.getCode != null && ap.getType.getCode.equals(i18n(code))) {
          true
        } else
          false
      })
      if(orgs.size == 1 && orgs.head.getValue.size == 1  && orgs.head.getValue.head.isInstanceOf[OrgStructure])
        orgs.head.getValue.head.asInstanceOf[OrgStructure].getId
      else
        0
    }
    this.orgStructStay = getOrgStructPropByCode("db.apt.moving.codes.hospOrgStruct")
    this.orgStructDirectedFrom = getOrgStructPropByCode("db.apt.received.codes.orgStructDirectedFrom")
    this.reopening = {
      val vals = values.entrySet.filter(e => {
        val ap = e.getKey._2
        if(ap != null && ap.getType != null && ap.getType.getCode != null && ap.getType.getCode.equals(i18n("db.apt.received.codes.reopening"))) {
          true
        } else
          false
      })
      if(vals.size == 1 && vals.head.getValue.size == 1  && vals.head.getValue.head.isInstanceOf[String])
        vals.head.getValue.head.toString
      else
        "null"
    }


    exValue = this.extractValuesInNumberedMap(Set(ConfigManager.RbCAPIds("db.rbCAP.hosp.primary.id.ambulanceNumber").toInt :java.lang.Integer), values).get("0")
    this.ambulanceNumber = exValue.get(0).asInstanceOf[String]

    //this.rangeAppealDateTime = new DatePeriodContainer(action.getBegDate, action.getEndDate)//(action.getBegDate, action.getEndDate) //event.getSetDate, event.getExecDate

    typeOfResponse match {
      case "standart"  => {
        this.patient = new IdValueContainer(event.getPatient.getId.toString)
        if(action != null) {
          this.rangeAppealDateTime = new DatePeriodContainer(action.getBegDate, action.getEndDate)
        } else {
          this.rangeAppealDateTime = new DatePeriodContainer(event.getCreateDatetime, event.getCreateDatetime)
        }
      }
      case "print_form" => {
        this.patient = new PatientEntry(event.getPatient, map, street)
        this.rangeAppealDateTime = new DatePeriodContainer(event.getSetDate, event.getExecDate)
      }
      case _ => {
        this.patient = new IdValueContainer(event.getPatient.getId.toString)
        if(action != null) {
          this.rangeAppealDateTime = new DatePeriodContainer(action.getBegDate, action.getEndDate)
        } else {
          this.rangeAppealDateTime = new DatePeriodContainer(event.getCreateDatetime, event.getCreateDatetime)
        }
      }
    }

    this.appealType = new AppealTypeContainer(event.getEventType)

    exValue = this.extractValuesInNumberedMap(Set(ConfigManager.RbCAPIds("db.rbCAP.hosp.primary.id.agreedType").toInt :java.lang.Integer), values).get("0")
    if(exValue.get(0).isInstanceOf[FDRecord])
      this.agreedType = new IdNameContainer(exValue.get(0).asInstanceOf[FDRecord].getId.intValue(), "")

    exValue = this.extractValuesInNumberedMap(Set(ConfigManager.RbCAPIds("db.rbCAP.hosp.primary.id.agreedDoctor").toInt :java.lang.Integer), values).get("0")
    this.agreedDoctor = exValue.get(0).asInstanceOf[String]


    val exAssignment = this.extractValuesInNumberedMap(LinkedHashSet(ConfigManager.RbCAPIds("db.rbCAP.hosp.primary.id.directed").toInt :java.lang.Integer,
      ConfigManager.RbCAPIds("db.rbCAP.hosp.primary.id.number").toInt :java.lang.Integer,
      ConfigManager.RbCAPIds("db.rbCAP.hosp.primary.id.assignmentDate").toInt :java.lang.Integer,
      ConfigManager.RbCAPIds("db.rbCAP.hosp.primary.id.doctor").toInt :java.lang.Integer
    ), values)
    var assignmentDate: Date = null
    if(exAssignment.get("2").get(0).isInstanceOf[Date]){
      assignmentDate = exAssignment.get("2").get(0).asInstanceOf[Date]
    }

    this.assignment = new AppealAssignmentContainer(assignmentDate,
      exAssignment.get("1").get(0).asInstanceOf[String],
      exAssignment.get("0").get(0).asInstanceOf[String],
      exAssignment.get("3").get(0).asInstanceOf[String]
    )

    exValue = this.extractValuesInNumberedMap(Set(ConfigManager.RbCAPIds("db.rbCAP.hosp.primary.id.hospitalizationType").toInt :java.lang.Integer), values).get("0")
    if(exValue.get(0).isInstanceOf[FDRecord])
      this.hospitalizationType = new IdNameContainer(exValue.get(0).asInstanceOf[FDRecord].getId.intValue(), "")

    exValue = this.extractValuesInNumberedMap(Set(ConfigManager.RbCAPIds("db.rbCAP.hosp.primary.id.hospitalizationPointType").toInt :java.lang.Integer), values).get("0")
    if(exValue.get(0).isInstanceOf[FDRecord])
      this.hospitalizationPointType = new IdNameContainer(exValue.get(0).asInstanceOf[FDRecord].getId.intValue(), "")

    exValue = this.extractValuesInNumberedMap(Set(ConfigManager.RbCAPIds("db.rbCAP.hosp.primary.id.hospitalizationChannelType").toInt :java.lang.Integer), values).get("0")
    if(exValue.get(0).isInstanceOf[Organisation]){
      val organisationChannel = exValue.get(0).asInstanceOf[Organisation]
      this.hospitalizationChannelType = new IdNameContainer(organisationChannel.getId.intValue(), organisationChannel.getShortName)
    }
    else
      this.hospitalizationChannelType = new IdNameContainer(-1, exValue.get(0).asInstanceOf[String])


    eventClientRelations.foreach(ecr =>  this.hospitalizationWith += new LegalRepresentativeContainer(ecr.getClientRelation, ecr.getNote))

    exValue = this.extractValuesInNumberedMap(Set(ConfigManager.RbCAPIds("db.rbCAP.hosp.primary.id.deliveredType").toInt :java.lang.Integer), values).get("0")
    this.deliveredType = exValue.get(0).asInstanceOf[String]

    exValue = this.extractValuesInNumberedMap(Set(ConfigManager.RbCAPIds("db.rbCAP.hosp.primary.id.transportationType").toInt :java.lang.Integer), values).get("0")
    this.movingType = exValue.get(0).asInstanceOf[String]

    exValue = this.extractValuesInNumberedMap(Set(ConfigManager.RbCAPIds("db.rbCAP.hosp.primary.id.deliveredAfterType").toInt :java.lang.Integer), values).get("0")
    this.deliveredAfterType = exValue.get(0).asInstanceOf[String]

    exValue = this.extractValuesInNumberedMap(Set(ConfigManager.RbCAPIds("db.rbCAP.hosp.primary.id.drugsType").toInt :java.lang.Integer), values).get("0")
    this.stateType = exValue.get(0).asInstanceOf[String]

    val exPhysical = this.extractValuesInNumberedMap(LinkedHashSet(ConfigManager.RbCAPIds("db.rbCAP.hosp.primary.id.height").toInt :java.lang.Integer,
      ConfigManager.RbCAPIds("db.rbCAP.hosp.primary.id.weight").toInt :java.lang.Integer,
      ConfigManager.RbCAPIds("db.rbCAP.hosp.primary.id.temperature").toInt :java.lang.Integer,
      ConfigManager.RbCAPIds("db.rbCAP.hosp.primary.id.bloodPressure.left.ADdiast").toInt :java.lang.Integer,
      ConfigManager.RbCAPIds("db.rbCAP.hosp.primary.id.bloodPressure.left.ADsyst").toInt :java.lang.Integer,
      ConfigManager.RbCAPIds("db.rbCAP.hosp.primary.id.bloodPressure.right.ADdiast").toInt :java.lang.Integer,
      ConfigManager.RbCAPIds("db.rbCAP.hosp.primary.id.bloodPressure.right.ADsyst").toInt :java.lang.Integer
    ), values)

    var d1, d2, d3, d4, d5, d6, d7 :Double = 0.0
    if(exPhysical.get("0").get(0).isInstanceOf[Double]) {d1 = (exPhysical.get("0").get(0).asInstanceOf[Double])}
    if(exPhysical.get("1").get(0).isInstanceOf[Double]) {d2 = (exPhysical.get("1").get(0).asInstanceOf[Double])}
    if(exPhysical.get("2").get(0).isInstanceOf[Double]) {d3 = (exPhysical.get("2").get(0).asInstanceOf[Double])}
    if(exPhysical.get("3").get(0).isInstanceOf[Double]) {d4 = (exPhysical.get("3").get(0).asInstanceOf[Double])}
    if(exPhysical.get("4").get(0).isInstanceOf[Double]) {d5 = (exPhysical.get("4").get(0).asInstanceOf[Double])}
    if(exPhysical.get("5").get(0).isInstanceOf[Double]) {d6 = (exPhysical.get("5").get(0).asInstanceOf[Double])}
    if(exPhysical.get("6").get(0).isInstanceOf[Double]) {d7 = (exPhysical.get("6").get(0).asInstanceOf[Double])}
    this.physicalParameters= new PhysicalParametersContainer(d1,d2,d3,d4,d5,d6,d7)

    exValue = this.extractValuesInNumberedMap(Set(ConfigManager.RbCAPIds("db.rbCAP.hosp.primary.id.injury").toInt :java.lang.Integer), values).get("0")
    this.injury = exValue.get(0).asInstanceOf[String]

    exValue = this.extractValuesInNumberedMap(Set(ConfigManager.RbCAPIds("db.rbCAP.hosp.primary.id.cancel").toInt :java.lang.Integer), values).get("0")
    this.refuseAppealReason = exValue.get(0).asInstanceOf[String]

    exValue = this.extractValuesInNumberedMap(Set(ConfigManager.RbCAPIds("db.rbCAP.hosp.primary.id.appealWithDeseaseThisYear").toInt :java.lang.Integer), values).get("0")
    this.appealWithDeseaseThisYear = exValue.get(0).asInstanceOf[String]

    //Диагнозы по новому
    if (diagnostics.size()>0){
      Set(i18n("appeal.diagnosis.diagnosisKind.diagReceivedMkb"),
          i18n("appeal.diagnosis.diagnosisKind.admissionMkb"),
          i18n("appeal.diagnosis.diagnosisKind.finalMkb"),
          i18n("appeal.diagnosis.diagnosisKind.aftereffectMkb"),
          i18n("appeal.diagnosis.diagnosisKind.attendantMkb")).foreach( diaType => {
        val allByType = diagnostics.filter(p=>p.getDiagnosisType.getFlatCode.compareTo(diaType)==0)  //Все диагностики данного типа
        val diaByLastDate = allByType.find(p=> p.getCreateDatetime.getTime==allByType.map(_.getCreateDatetime.getTime).foldLeft(Long.MinValue)((i,m)=>m.max(i))).getOrElse(null) //Диагностика последняя по дате создания
        if (diaByLastDate!=null){
          this.diagnoses += new DiagnosisContainer(diaByLastDate)
        }
      })
    }
    //*******************Доп сведения***************
    //Движения
    val setATCodes = JavaConversions.setAsJavaSet(Set(i18n("db.apt.received.codes.orgStructDirection"),
                                                   i18n("db.apt.moving.codes.orgStructTransfer"),
                                                   i18n("db.apt.documents.codes.RW"),
                                                   i18n("db.apt.documents.codes.preHospitalDefects"),
                                                   i18n("db.apt.moving.codes.hospOrgStruct"),
                                                   i18n("db.apt.received.codes.orgStructDirectedFrom")
        ))
    if (mMovingProperties!=null){
      val eventIds: java.util.List[java.lang.Integer] = new util.LinkedList[java.lang.Integer]();
      eventIds.add(event.getId.intValue())
      val moveProps = mMovingProperties(eventIds, setATCodes, 1, true)
      if (moveProps!=null && moveProps.size>0) {
        var res = moveProps.get(event.getId.intValue()).find(f => {f._1.getType.getCode.compareTo(i18n("db.apt.moving.codes.orgStructTransfer"))==0}).getOrElse(null)
        if (res==null) {
          res = moveProps.get(event.getId.intValue()).find(f => {f._1.getType.getCode.compareTo(i18n("db.apt.received.codes.orgStructDirection"))==0}).getOrElse(null)
        }
        var labProp = moveProps.get(event.getId.intValue()).find(f => {f._1.getType.getCode.compareTo(i18n("db.apt.documents.codes.RW"))==0}).getOrElse(null)
        if (labProp != null) {
          this.laboratory = new LaboratoryPropertiesContainer(labProp._2.get(0).getValueAsString)
        }
        val orgStructStay = moveProps.get(event.getId.intValue()).find(f => {f._1.getType.getCode.compareTo(i18n("db.apt.moving.codes.hospOrgStruct"))==0}).getOrElse(null)
        if(orgStructStay != null) {
          this.orgStructStay = orgStructStay._2.get(0).getValueAsId.toInt
        }
        val orgStructDirectedFrom = moveProps.get(event.getId.intValue()).find(f => {f._1.getType.getCode.compareTo(i18n("db.apt.received.codes.orgStructDirectedFrom"))==0}).getOrElse(null)
        if(orgStructDirectedFrom != null) {
          this.orgStructDirectedFrom = orgStructDirectedFrom._2.get(0).getValueAsId.toInt
        }
        val preHospitalDefects = moveProps.get(event.getId.intValue()).find(f => {f._1.getType.getCode.compareTo(i18n("db.apt.documents.codes.preHospitalDefects"))==0}).getOrElse(null)
        if (labProp != null) {
          this.preHospitalDefects = preHospitalDefects._2.get(0).asInstanceOf[String]
        }
        if (res!=null) {   //Запись
          this.ward = new OrgStructureContainer(res._2.get(0).getValue.asInstanceOf[OrgStructure])
          val diffOfDays = ((new Date()).getTime - res._1.getAction.getBegDate.getTime)/(1000 * 60 * 60 * 24) + 1
          this.totalDays = "Проведено %d койко-дней".format(diffOfDays)
        }
      }
    }
    // Текущее отделение пребывания пациента
    if (currentDepartment != null && action != null) {
      this.currentDepartment = new IdNameContainer(currentDepartment.getId.intValue(), currentDepartment.getName)
    } else {
      this.currentDepartment = new IdNameContainer(0, "")
    }
    //Имеет первичный осмотр
    this.havePrimary = havePrimary
    //Данные о последующей госпитализации
    this.leaved = new LeavedInfoContainer(extractProperties)

    //Контракт
    if (contract != null)
      this.contract = new ContractContainer(contract)
    //Результат какой-то
    if (event.getResult != null) {
      this.result = new IdNameContainer(event.getResult.getId.intValue(), event.getResult.getName)
    }
    //Временная нетрудоспособность
    if (tempInvalid != null) {
      this.tempInvalid = new TempInvalidAppealContainer(tempInvalid)
    }
}

  /**
   * Внутренний метод, возвращает нумерованный список значений в контейнер из набора свойств
   * @param set Набор искомых свойств действия
   * @param values Все значения свойств действия
   * @return Список значений для свойств действия из искомого списка
   */
  private def extractValuesInNumberedMap(set: java.util.Set[java.lang.Integer], values: java.util.Map[(java.lang.Integer, ActionProperty), java.util.List[Object]]) = {

    var map: java.util.Map[String, java.util.List[Object]] = new HashMap[String, java.util.List[Object]]

    var counter: Int = 0
    set.foreach(e => {
      values.foreach(f => {
        var dStr: String = null
        if (f._1._1.intValue() == e.intValue()) {
          dStr = counter.toString
          map.put(dStr, values.get(e, f._1._2))
        } //else {map.put(counter.toString, List(""))}
      })
      if (map.size()==counter) map.put(counter.toString, List(""))  //если не нашлась пропертя, то для рбкори запишем пустое значение
      counter = counter + 1
      /*
      if(values.containsKey(e)){
        var dStr: String = null
        if(e.compareTo(ConfigManager.RbCAPIds("db.rbCAP.hosp.primary.id.diagnosis.assigment.code").toInt :java.lang.Integer)==0){
          dStr = "code_assignment"
        }
        else if(e.compareTo(ConfigManager.RbCAPIds("db.rbCAP.hosp.primary.id.diagnosis.aftereffect.code").toInt :java.lang.Integer)==0){
          dStr ="code_aftereffect"
        }
        else if(e.compareTo(ConfigManager.RbCAPIds("db.rbCAP.hosp.primary.id.diagnosis.attendant.code").toInt :java.lang.Integer)==0){
          dStr ="code_attendant"
        }
        else if(e.compareTo(ConfigManager.RbCAPIds("db.rbCAP.hosp.primary.id.diagnosis.assignment.description").toInt :java.lang.Integer)==0){
          dStr ="description_assignment"
        }
        else if(e.compareTo(ConfigManager.RbCAPIds("db.rbCAP.hosp.primary.id.diagnosis.aftereffect.description").toInt :java.lang.Integer)==0){
          dStr ="description_aftereffect"
        }
        else if(e.compareTo(ConfigManager.RbCAPIds("db.rbCAP.hosp.primary.id.diagnosis.attendant.description").toInt :java.lang.Integer)==0){
          dStr ="description_attendant"
        }
        else dStr = counter.toString
        map.put(dStr, values.get(e))
      }
      else {
        map.put(counter.toString, List(""))
      }
      counter = counter + 1  */
    })
    map
  }
}

/**
 * Контейнер для хранения данных о диагнозе
 */
@XmlType(name = "diagnosisContainer")
@XmlRootElement(name = "diagnosisContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class DiagnosisContainer {
  @BeanProperty
  var diagnosticId: Int = -1
  @BeanProperty
  var diagnosisKind: String = _
  @BeanProperty
  var description: String = _
  @BeanProperty
  var injury: String = _
  @BeanProperty
  var mkb: MKBContainer = new MKBContainer()

  /**
   * Конструктор DiagnosisContainer
   * @param diagnostic Данные о диагнозе как Diagnostic entity
   */
  def this(diagnostic: Diagnostic){
    this()
    if(diagnostic!=null) {
      this.diagnosticId = diagnostic.getId.intValue()
      this.diagnosisKind =
        if(diagnostic.getDiagnosisType.getFlatCode.isEmpty)
          diagnostic.getDiagnosisType.getName
        else diagnostic.getDiagnosisType.getFlatCode
      this.description = diagnostic.getNotes
      this.injury = if(diagnostic.getTraumaType!=null) {diagnostic.getTraumaType.getName} else {""}
      this.mkb =  if(diagnostic.getDiagnosis!=null && diagnostic.getDiagnosis.getMkb!=null) new MKBContainer(diagnostic.getDiagnosis.getMkb) else new MKBContainer()
    }
  }
/*
  /**
   * Конструктор DiagnosisContainer
   * @param diagnosisKind Мнемоника диагноза (описание типа)
   * @param description Примечание
   * @param injury  Травма
   * @param mkb Данные о диагнозе как MKB
   * @see MKBContainer
   */
  def this(diagnosisKind: String, description: String, injury: String, mkb: Mkb){
    this()
    this.diagnosisKind = diagnosisKind
    this.description = description
    this.injury = injury
    if(mkb!=null)
      this.mkb = new MKBContainer(mkb)
  }

  /**
   * Конструктор DiagnosisContainer
   * @param diagnosis Данные о диагнозе как Diagnostic entity
   * @param mkb Данные о диагнозе как MKB
   * @see MKBContainer
   * @deprecated Использовать след. конструктор {@link #this(Diagnostic)}
   */
  def this(diagnosis: Diagnostic, mkb: Mkb){
    this()
    if(diagnosis!=null) {
      this.diagnosisKind = diagnosis.getDiagnosisType.getName
      this.description = diagnosis.getNotes
      this.injury = if(diagnosis.getTraumaType!=null) {diagnosis.getTraumaType.getName} else {""}
    }
    if(mkb!=null) {
      this.mkb = new MKBContainer(mkb)
    }
  }

  /**
   * Конструктор DiagnosisContainer с анализом типа diagnosis и типа mkb
   * @param diagnosis Данные о диагнозе в виде Diagnostic или в виде ActionProperty как Object
   * @param mkb Данные о диагнозе в виде MKB или String как Object
   * @see MKBContainer
   */
  def this(diagnosis: Object, mkb: Object){
    this()
    if(diagnosis!=null) {
      if(diagnosis.isInstanceOf[Diagnostic]) {
        this.diagnosisKind = diagnosis.asInstanceOf[Diagnostic].getDiagnosisType.getName
        this.description = diagnosis.asInstanceOf[Diagnostic].getNotes
        this.injury = if(diagnosis.asInstanceOf[Diagnostic].getTraumaType!=null) {diagnosis.asInstanceOf[Diagnostic].getTraumaType.getName} else {""}
      } else if(diagnosis.isInstanceOf[ActionProperty]) {
        this.diagnosisKind = diagnosis.asInstanceOf[ActionProperty].getAction.getActionType.getCode match {
          case "4501" => "клинический"
          case "1_1_01" => "при поступлении"
          case "4201" => "направительный"
          case _ => ""
        }
        //TODO: Нужно или нет передавать description и injury в этом случае???
      }
    }
    if(mkb!=null) {
      if(mkb.isInstanceOf[Mkb]) {
        this.mkb = new MKBContainer(mkb.asInstanceOf[Mkb])
      } else if(mkb.isInstanceOf[String]){
        this.mkb = new MKBContainer("", mkb.asInstanceOf[String])
      }
    }
  }
  */
  /**
   * Метод представляет контейнер в виде Map
   * @return Возвращает список свойств контейнера  как Map[String, Object]
   */
  def toMap = {
    var map = new java.util.HashMap[String, Object]
    map.put("diagnosisKind", this.diagnosisKind)
    map.put("description", this.description)
    map.put("injury", this.injury)
    map.put("mkb", this.mkb.toMap)
    map
  }
}

/**
 * Контейнер для данных о физических характеристиках пациента
 */
@XmlType(name = "physicalParametersContainer")
@XmlRootElement(name = "physicalParametersContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class PhysicalParametersContainer {
  @BeanProperty
  var height: Double = _
  @BeanProperty
  var weight: Double = _
  @BeanProperty
  var temperature: Double = _
  @BeanProperty
  var bloodPressure: RangeLeftRightContainer = new RangeLeftRightContainer()

  /**
   * Конструктор для PhysicalParametersContainer
   * @param height Рост
   * @param weight Вес
   * @param temperature Температура тела
   * @param bloodPressureLeftDiast Артериальное давление. Левая рука: АД диаст.
   * @param bloodPressureLeftSyst  Артериальное давление. Левая рука: АД сист.
   * @param bloodPressureRightDiast Артериальное давление. Правая рука: АД диаст.
   * @param bloodPressureRightSyst Артериальное давление. Правая рука: АД сист.
   */
  def this(height: Double,
           weight: Double,
           temperature: Double,
           bloodPressureLeftDiast: Double,
           bloodPressureLeftSyst: Double,
           bloodPressureRightDiast: Double,
           bloodPressureRightSyst: Double){
    this()
    this.height = height
    this.weight = weight
    this.temperature = temperature
    this.bloodPressure = new RangeLeftRightContainer( bloodPressureLeftDiast,
                                                      bloodPressureLeftSyst,
                                                      bloodPressureRightDiast,
                                                      bloodPressureRightSyst)
  }

  /**
   * Метод представляет контейнер в виде Map
   * @return Возвращает список свойств контейнера  как Map[String, Object]
   */
  def toMap = {
    var map = new java.util.HashMap[String, Object]
    map.put("height", this.height.toString)
    map.put("weight", this.weight.toString)
    map.put("temperature", this.temperature.toString)
    map.put("bloodPressure", this.bloodPressure.toMap)
    map
  }
}

/**
 * Контейнер с информацией о назначениях
 */
@XmlType(name = "appealAssignmentContainer")
@XmlRootElement(name = "appealAssignmentContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class AppealAssignmentContainer {
  @BeanProperty
  var assignmentDate: Date = null
  @BeanProperty
  var number: String = null
  @BeanProperty
  var directed: String = _ //IdNameContainer = new IdNameContainer()
  @BeanProperty
  var doctor: String = _//DoctorContainer = new DoctorContainer()

  /**
   * Конструктор AppealAssignmentContainer
   * @param assignmentDate Дата назначения
   * @param number Количество
   * @param directedName Наименование
   * @param doctor Назначивший врач как Staff
   */
  def this(assignmentDate: Date,
           number: String,
           directedName: String,
           doctor: Staff) {
    this()
    this.assignmentDate = assignmentDate
    this.number = number
    this.directed = directedName
    //if(doctor!=null) {
    //  this.doctor =  new DoctorContainer(doctor)
    //}
  }

  /**
   * Конструктор AppealAssignmentContainer
   * @param assignmentDate Дата назначения
   * @param number Количество
   * @param directedName Наименование
   * @param doctor ФИО врача
   */
  def this(assignmentDate: Date,
           number: String,
           directedName: String,
           doctor: String) {
    this()
    this.assignmentDate = assignmentDate
    this.number = number
    this.directed = directedName
    this.doctor =  doctor
  }

  /**
   * Метод представляет контейнер в виде Map
   * @return Возвращает список свойств контейнера  как Map[String, Object]
   */
  def toMap = {
    var map = new java.util.HashMap[String, Object]
    map.put("assignmentDate", this.assignmentDate)
    map.put("number", this.number)
    map.put("directed", this.directed)
    map.put("doctor", this.doctor)
    map
  }
}

/**
 * Контейнер с информацией о докторе
 */
@XmlType(name = "doctorContainer")
@XmlRootElement(name = "doctorContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class DoctorContainer {
  @BeanProperty
  var id: Int = _
  @BeanProperty
  var name: PersonNameContainer = new PersonNameContainer()

  /**
   * Конструктор DoctorContainer
   * @param staff Врач как Staff entity
   */
  def this(staff: Staff){
    this()
    if(staff!=null)  {
      this.id = staff.getId.intValue()
      this.name = new PersonNameContainer(staff)

    }
  }

  /**
   * Конструктор DoctorContainer
   * @param fullName ФИО врача
   */
  def this(fullName: String){
    this()
    this.id = 0
    this.name = new PersonNameContainer(fullName)
  }

  /**
   * Метод представляет контейнер в виде Map
   * @return Возвращает список свойств контейнера  как Map[String, Object]
   */
  def toMap = {
    var map = new java.util.HashMap[String, Object]
    map.put("id", this.id.toString)
    map.put("name", this.name.toMap)
    map
  }
}

/**
 * Контейнер для МКВ диагнозов
 */
@XmlType(name = "mKBContainer")
@XmlRootElement(name = "mKBContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class MKBContainer {
  @BeanProperty
  var id: Int = -1
  @BeanProperty
  var code: String = _
  @BeanProperty
  var diagnosis: String = _

  /**
   * Конструктор MKBContainer
   * @param code Код диагноза по МКВ
   * @param diagnosis Расшифровка диагноза
   */
  def this(code: String, diagnosis: String){
    this()
    this.code = code
    this.diagnosis = diagnosis
  }

  /**
   * Конструктор MKBContainer
   * @param mkb ЬКВ диагноз как Mkb entity
   */
  def this(mkb: Mkb){
    this()
    if (mkb!=null){
      this.id = mkb.getId.intValue()
      this.code = mkb.getDiagID
      this.diagnosis = mkb.getDiagName
    }
  }

  /**
   * Метод представляет контейнер в виде Map
   * @return Возвращает список свойств контейнера  как Map[String, Object]
   */
  def toMap = {
    var map = new java.util.HashMap[String, Object]
    //map.put("id", this.id)
    map.put("code", this.code)
    map.put("diagnosis", this.diagnosis)
    map
  }
}

/**
 * Контейнер для хранения данных о типе обращения
 */
@XmlType(name = "appealTypeContainer")
@XmlRootElement(name = "appealTypeContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class AppealTypeContainer {
  @BeanProperty
  var eventType: IdNameContainer = _

  @BeanProperty
  var requestType: IdNameContainer = _

  @BeanProperty
  var finance: IdNameContainer = _

  /**
   * Конструктор AppealTypeContainer
   * @param eventType Тип обращения как EventType
   */
  def this(eventType: EventType){
    this()
    this.eventType = new IdNameContainer(eventType.getId.intValue(), eventType.getName)
    this.requestType = new IdNameContainer(eventType.getRequestType.getId.intValue(), eventType.getRequestType.getName)
    this.finance = new IdNameContainer(eventType.getFinance.getId.intValue(), eventType.getFinance.getName)
  }
}

/**
 * Контейнер для представления информации о законном представителе
 */
@XmlType(name = "legalRepresentativeContainer")
@XmlRootElement(name = "legalRepresentativeContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class LegalRepresentativeContainer {

  @BeanProperty
  var relative: IdNameDateContainer = _                   //Законный представитель (ИД + ФИО)

  @BeanProperty
  var relativeType: IdNameContainer = _     //Тип связи

  @BeanProperty
  var note: String = ""                      //Комментарий

  /**
   * Конструктор LegalRepresentativeContainer
   * @param relation Связь пациента с законным представителем как ClientRelation
   * @param note Примечание к законному представителю
   */
  def this(relation: ClientRelation,
           note: String){
    this()

    if (relation!=null){
      val rel = relation.getRelative
      this.relative =
        if(rel!=null)
          new IdNameDateContainer(rel.getId.intValue(),
                                  "%s %s %s".format(rel.getLastName, rel.getFirstName, rel.getPatrName),
                                  relation.getRelative.getBirthDate, relation.getRelative.getSex)
        else
          new IdNameDateContainer()

      this.relativeType =
        if(relation.getRelativeType!=null)
          new IdNameContainer(relation.getRelativeType.getId.intValue(),
                              "%s(%s)".format(relation.getRelativeType.getLeftName, relation.getRelativeType.getRightName))
        else null
    }
    this.note = note
  }
}

/**
 * Контейнер для представления информации в виде: Идентификатор, Имя, Дата рождения
 */
@XmlType(name = "idNameDateContainer")
@XmlRootElement(name = "idNameDateContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class IdNameDateContainer {

  @BeanProperty
  var id : Int = _         //ИД

  @BeanProperty
  var name : String = _    //ФИО

  @BeanProperty
  var birthDate: Date = _  // — Дата рождения

  @BeanProperty
  var sex: String = _  // — Дата рождения

  def this( id : Int, name : String, birthDate : Date, sex: Int) = {
    this()
    this.id = id
    this.name = name
    this.birthDate = birthDate
    this.sex = sex match {
      case 1 => "male"
      case 2 => "female"
      case _ => null
    }
  }

}

/**
 * Контейнер для представления информации о контракте
 */
@XmlType(name = "contractContainer")
@XmlRootElement(name = "contractContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class ContractContainer {

  @BeanProperty
  var id: Int = _

  @BeanProperty
  var number : String = _    //Номер контракта

  @BeanProperty
  var begDate: Date = _      // Дата открытия договора

  @BeanProperty
  var finance : IdNameContainer = _      //ИД

  def this(contract: Contract) = {
    this()
    this.id = contract.getId
    this.number = contract.getNumber
    this.begDate = contract.getBegDate
    this.finance = new IdNameContainer(contract.getFinance.getId.intValue(), contract.getFinance.getName)
  }
}

/**
 * Контейнер для представления информации о временной нетрудоспособности
 */
@XmlType(name = "tempInvalidAppealContainer")
@XmlRootElement(name = "tempInvalidAppealContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class TempInvalidAppealContainer {
  @BeanProperty
  var serial: String = _
  @BeanProperty
  var number: String = _
  @BeanProperty
  var isByService: Boolean = _ // флаг "По уходу за больным"
  @XmlJavaTypeAdapter(classOf[DateAdapter]) var begDate : Date = _    //
  @XmlJavaTypeAdapter(classOf[DateAdapter]) var endDate: Date = _     //
  @BeanProperty
  var age : Int = _      //
  @BeanProperty
  var sex : Int = _      //

  def this(tempInvalid: TempInvalid) = {
    this()
    this.begDate = tempInvalid.getBegDate
    this.endDate = tempInvalid.getEndDate
    this.age = tempInvalid.getAge
    this.sex = tempInvalid.getSex
    val reason: RbTempInvalidReason = tempInvalid.getTempInvalidReason
    this.isByService = reason != null && "09".equals(reason.getCode)
    this.serial = tempInvalid.getSerial
    this.number = tempInvalid.getNumber
  }
}

/**
 * Контейнер для представления информации о лабораторных исследованиях
 */
@XmlType(name = "laboratoryPropertiesContainer")
@XmlRootElement(name = "laboratoryPropertiesContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class LaboratoryPropertiesContainer {

  @BeanProperty
  var rw : String = _

  def this(rw: String) = {
    this()
    this.rw = rw
  }
}

/**
 * Контейнер для представления информации о лабораторных исследованиях
 */
@XmlType(name = "leavedInfoContainer")
@XmlRootElement(name = "leavedInfoContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class LeavedInfoContainer extends I18nable {

  @BeanProperty
  var nextHospDate: String = _
  @BeanProperty
  var nextHospDepartment: IdNameContainer = _
  @BeanProperty
  var nextHospFinanceType: String = _
  @BeanProperty
  var hospOutcome: String = _
  @BeanProperty
  var hospLength: String = _
  @BeanProperty
  var outcomeDate: Date = _
  @BeanProperty
  var leavedDoctor: DoctorContainer = _

  def this(extractProperties: java.util.Map[ActionProperty, java.util.List[APValue]]) = {
    this()
    if (extractProperties!=null) {
      extractProperties.foreach(prop => {
        if (prop != null && prop._1 != null && prop._2 != null && prop._2.size() > 0) {
          if (this.outcomeDate == null && this.leavedDoctor == null) {
            this.outcomeDate = prop._1.getAction.getEndDate
            this.leavedDoctor = new DoctorContainer(prop._1.getAction.getExecutor)
          }
          if (prop._1.getType.getCode.compareTo(i18n("db.apt.leaved.codes.nextHospDate"))==0){
            this.nextHospDate = prop._2.get(0).getValueAsString
          } else if (prop._1.getType.getCode.compareTo(i18n("db.apt.moving.codes.hospOrgStruct"))==0
            && prop._2.get(0).getValue != null && prop._2.get(0).getValue.asInstanceOf[OrgStructure].getName != null){
            this.nextHospDepartment = new IdNameContainer(prop._2.get(0).getValue.asInstanceOf[OrgStructure].getId.intValue() ,
                                                          "%s(%s)".format(prop._2.get(0).getValue.asInstanceOf[OrgStructure].getName, prop._2.get(0).getValue.asInstanceOf[OrgStructure].getAddress))
          } else if (prop._1.getType.getCode.compareTo(i18n("db.apt.leaved.codes.nextHospFinance"))==0){
            this.nextHospFinanceType = prop._2.get(0).getValueAsString
          } else if (prop._1.getType.getCode.compareTo(i18n("db.apt.leaved.codes.hospOutcome"))==0){
            this.hospOutcome = prop._2.get(0).getValueAsString
          } else if (prop._1.getType.getCode.compareTo(i18n("db.apt.leaved.codes.hospLength"))==0){
            this.hospLength = prop._2.get(0).getValueAsString
          }
        }
      })
    }
  }
}