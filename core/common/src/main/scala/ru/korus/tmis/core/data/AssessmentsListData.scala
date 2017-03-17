package ru.korus.tmis.core.data

import javax.xml.bind.annotation.{XmlRootElement, XmlType}
import beans.BeanProperty
import java.util.Date
import java.{util => ju}
import ru.korus.tmis.core.entity.model.{Staff, Action}
import scala.collection.JavaConversions._
import ru.korus.tmis.core.filter.AbstractListDataFilter
import ru.korus.tmis.scala.util.ConfigManager
import ru.korus.tmis.core.lock.{EntityLockInfo, ActionWithLockInfo}
import scala.language.reflectiveCalls

//Контейнер для списка осмотров
@XmlType(name = "assessmentsListData")
@XmlRootElement(name = "assessmentsListData")
class AssessmentsListData {

  @BeanProperty
  var requestData: AssessmentsListRequestData = _
  @BeanProperty
  var data: ju.LinkedList[AssessmentsListEntry] = new ju.LinkedList[AssessmentsListEntry]

  def this(actions: ju.List[ActionWithLockInfo], requestData: AssessmentsListRequestData) {
    this()
    this.requestData = requestData
    actions.foreach(actionWithLockInfo => this.data.add(new AssessmentsListEntry(actionWithLockInfo)))
  }
}

@XmlType(name = "assessmentsListRequestData")
@XmlRootElement(name = "assessmentsListRequestData")
class AssessmentsListRequestData {

  @BeanProperty
  var filter: AbstractListDataFilter = new DefaultListDataFilter()
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
    this.filter = if(filter!=null) filter else {new AssessmentsListRequestDataFilter()}
    this.sortingField = sortingField match {
      case null => {"id"}
      case _ => sortingField
    }
    this.sortingMethod = sortingMethod match {
      case null => {"asc"}
      case _ => sortingMethod
    }
    this.limit = if (limit > 0) limit else ConfigManager.Messages("misCore.pages.limit.default").toInt
    this.page = if(page>1) page else 1
    this.coreVersion = ConfigManager.Messages("misCore.assembly.version")
    this.sortingFieldInternal = this.filter.toSortingString(this.sortingField, this.sortingMethod)
  }

  def rewriteRecordsCount(recordsCount: java.lang.Long): Boolean = {
    this.recordsCount = recordsCount.longValue()
    true
  }
}

class AssessmentsListRequestDataFilter extends AbstractListDataFilter{

  //   =>Фильтрация по коду типа действия<=
  @BeanProperty
  var codes: ju.Set[String] = new ju.HashSet[String]()
  @BeanProperty
  var actionTypeId: Int = _
  @BeanProperty
  var begDate: Date = _
  @BeanProperty
  var endDate: Date = _
  @BeanProperty
  var doctorId: Int = _
  @BeanProperty
  var doctorName: String = _
  @BeanProperty
  var speciality: String = _
  @BeanProperty
  var assessmentName: String = _
  @BeanProperty
  var departmentName: String = _
  @BeanProperty
  var eventId: Int = _
  @BeanProperty
  var patientId: Int = _
  @BeanProperty
  var mnemonics: ju.List[String] = new ju.ArrayList[String]
  @BeanProperty
  var flatCodes: ju.List[String] = new ju.ArrayList[String]

  def this(eventId: Int,
           patientId: Int,
           actionTypeId: Int,
           code_x: ju.Set[String],
           begDate: Long,
           endDate: Long,
           doctorId: Int,
           doctorName: String,
           speciality: String,
           assessmentName: String,
           departmentName: String,
           mnemonics: ju.List[String],
           flatCodes: ju.List[String]) {
    this()
    this.eventId = eventId
    this.patientId = patientId
    this.actionTypeId = actionTypeId
    this.codes = code_x.filter(p=> p != null && !p.isEmpty)
    this.begDate = if (begDate == 0) null else new Date(begDate)
    this.endDate = if (endDate == 0) null else new Date(endDate)
    this.doctorId= doctorId
    this.doctorName = doctorName
    this.speciality = speciality
    this.assessmentName = assessmentName
    this.departmentName = departmentName
    this.mnemonics = mnemonics.filter(p=> p != null && !p.isEmpty )
    this.flatCodes = flatCodes.filter(p=> p != null && !p.isEmpty )
  }

  def toQueryStructure: QueryDataStructure = {
    val qs = new QueryDataStructure()
    if(this.eventId>0) {
      qs.query += "AND a.event.id = :eventId\n"
      qs.add("eventId",this.eventId:java.lang.Integer)
    }
    if(this.patientId > 0) {
      qs.query += "AND a.event.patient.id = :patientId\n"
      qs.add("patientId",this.patientId:java.lang.Integer)
    }
    if (this.actionTypeId > 0) {
      qs.query += "AND a.actionType.id = :actionTypeId\n"
      qs.add("actionTypeId", this.actionTypeId:java.lang.Integer)
    }
    if (this.codes != null && !this.codes.isEmpty) {
      qs.query += "AND a.actionType.code IN  :code\n"
      qs.add("code",asJavaCollection(this.codes))
    }
    if (this.doctorId > 0) {
      qs.query += "AND a.createPerson.id = :doctorId\n"
      qs.add("doctorId", this.doctorId:java.lang.Integer)
    }
    if (this.doctorName != null && !this.doctorName.isEmpty) {
      qs.query += "AND upper(a.createPerson.lastName) LIKE upper(:doctorName)\n"
      qs.add("doctorName", "%" + this.doctorName + "%")
    }
    if (this.begDate != null) {
      qs.query += "AND a.createDatetime >= :begDate\n"
      qs.add("begDate", this.begDate)
    }
    if (this.endDate != null) {
      qs.query += "AND a.createDatetime <= :endDate\n"
      qs.add("endDate", this.endDate)
    }
    if (this.speciality != null && !this.speciality.isEmpty) {
      qs.query += "AND upper(a.createPerson.speciality.name) LIKE upper(:speciality)\n"
      qs.add("speciality", "%" + this.speciality + "%")
    }
    if (this.assessmentName != null && !this.assessmentName.isEmpty) {
      qs.query += "AND upper(a.actionType.name) LIKE upper(:assessmentName)\n"
      qs.add("assessmentName", "%" + this.assessmentName + "%")
    }
    if (this.departmentName != null && !this.departmentName.isEmpty) {
      qs.query += "AND upper(a.createPerson.orgStructure.name) LIKE upper(:departmentName)\n"
      qs.add("departmentName", "%" + this.departmentName + "%")
    }
    if (this.mnemonics!=null && this.mnemonics.size() > 0) {
      qs.query += "AND a.actionType.mnemonic IN  :mnemonic\n"
      qs.add("mnemonic",asJavaCollection(this.mnemonics))
    }
    if (this.flatCodes!=null && this.flatCodes.size() > 0) {
      qs.query += "AND a.actionType.flatCode IN  :flatCodes\n"
      qs.add("flatCodes",asJavaCollection(this.flatCodes))
    }
    qs
  }

  /**
   * Бешеный спагетти-код совсем обезумел, совладаешь ли ты с ним?
   * Весь запрос размазан по проекту.  В запросе делается отдельный left join на поле для сортировки.
   * Ведь если speciality - null, то пропадет вся строка, т.к. таблицы соединяются по inner join.
   * Тебе все мало, хочешь еще? Эта ситуация описана и на нее заведен баг EclipseLink #363798 - https://bugs.eclipse.org/bugs/show_bug.cgi?id=363798.
   * Но не жди, что он когда-нибудь поправит положение - использование ORDER BY на параметрах, которые не отражены в SELECT - запрещено в соответствии
   * со спецификацией Java TM Persistence API, Version 2.1. Запросы подобного рода (с сортировкой по тому, чего нет в SELECT) будут отваливаться через Exception.
   *
   *
   * P.S. (подпись смотреть в коммите)
   * Другие поля тоже нужно поправить, т.е. сделать отдельный LEFT JOIN. У department, doctor и assessmentName
   *
   * @param sortingField Поле для сортировки
   * @param sortingMethod Метод сортировки
   * @return Строка сортировки
   */
  @Override
  def toSortingString (sortingField: String, sortingMethod: String): String = {
    var sorting = sortingField match {
      case "assessmentDate" | "start" | "createDatetime" => {"a.createDatetime %s".format(sortingMethod)}
      case "assessmentName" | "name" => {"a.actionType.name %s".format(sortingMethod)}
      case "doctor" => {"a.createPerson.lastName %s, a.createPerson.firstName %s, a.createPerson.patrName %s".format(sortingMethod, sortingMethod, sortingMethod)}
      case "department" => {"a.createPerson.orgStructure.name %s".format(sortingMethod)}
      case "specs" => {"s.name %s".format(sortingMethod)}
      case _ => {"a.id %s".format(sortingMethod)}
    }
    sorting = "ORDER BY " + sorting
    sorting
  }
}

@XmlType(name = "assessmentsListEntry")
@XmlRootElement(name = "assessmentsListEntry")
class AssessmentsListEntry {

  @BeanProperty
  var id: Int = _ //Action Id

  @BeanProperty
  var typeId: Int = _ //Тип действия

  @BeanProperty
  var assessmentDate: Date = _ //дата создания

  @BeanProperty
  var beginDate: Date = _ //дата создания

  @BeanProperty
  var closeDate: Date = _ //дата закрытия

  @BeanProperty
  var assessmentName: IdNameAndCodeContainer = _ //Наименование

  @BeanProperty
  var doctor: DoctorSpecsContainer = new DoctorSpecsContainer() //Врач

  @BeanProperty
  var executor: DoctorSpecsContainer = new DoctorSpecsContainer() //Исполнитель

  @BeanProperty
  var mnemonic: String = _

  @BeanProperty
  var flatCode: String = _

  @BeanProperty
  var readOnly: Boolean = _

  @BeanProperty
  var lockInfo: LockInfoContainer = _

  def this(actionWithLockInfo: ActionWithLockInfo) {
    this()
    val action = actionWithLockInfo.action
    id = action.getId.intValue()
    typeId = action.getActionType.getId.intValue()
    assessmentDate = action.getCreateDatetime
    beginDate = action.getBegDate
    closeDate = action.getEndDate
    assessmentName = new IdNameAndCodeContainer(action.getActionType.getId.intValue(), action.getActionType.getName, action.getActionType.getCode)
    doctor = new DoctorSpecsContainer(action.getCreatePerson)
    executor = new DoctorSpecsContainer(action.getExecutor)
    flatCode = action.getActionType.getFlatCode
    mnemonic = action.getActionType.getMnemonic
    readOnly = actionWithLockInfo.lockInfo != null
    if (actionWithLockInfo.lockInfo != null) {
      lockInfo = new LockInfoContainer(actionWithLockInfo.lockInfo.person.getId, actionWithLockInfo.lockInfo.person.getFullName)
    }
  }
}

@XmlType
class LockInfoContainer {

  @BeanProperty
  var personId: Integer = _

  @BeanProperty
  var personInfo: String = _

  def this(id: Integer, personInfo: String) {
    this()
    personId = id
    this.personInfo = personInfo
  }


}

@XmlType(name = "doctorSpecsContainer")
@XmlRootElement(name = "doctorSpecsContainer")
class DoctorSpecsContainer {
  @BeanProperty
  var id: Int = -1 //Staff Id

  @BeanProperty
  var name: PersonNameContainer = new PersonNameContainer() //Staff Value

  @BeanProperty
  var specs: IdNameContainer = _ //Staff speciality

  @BeanProperty
  var department: IdNameContainer = _ //отделение

  def this(person: Staff) {
    this()
    if(person != null) {
      this.id = person.getId.intValue()
      this.name = new PersonNameContainer(person)
      if (person.getSpeciality != null) {
        this.specs = new IdNameContainer(person.getSpeciality.getId.intValue(), person.getSpeciality.getName)
      }
      if (person.getOrgStructure != null) {
        this.department = new IdNameContainer(person.getOrgStructure.getId.intValue(), person.getOrgStructure.getName)
      }
    }
  }
}
