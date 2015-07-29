package ru.korus.tmis.core.data

import javax.xml.bind.annotation.{XmlRootElement, XmlType}
import scala.beans.BeanProperty
import java.util.{LinkedList, Date}
import java.{lang => jl}
import scala.collection.JavaConversions._
import ru.korus.tmis.core.entity.model._
import ru.korus.tmis.core.filter.AbstractListDataFilter
import ru.korus.tmis.core.auth.AuthData
import ru.korus.tmis.scala.util.ConfigManager
import scala.language.reflectiveCalls

//Контейнер для списка диагностик
@XmlType(name = "diagnosticsListData")
@XmlRootElement(name = "diagnosticsListData")
class DiagnosticsListData {

  @BeanProperty
  var requestData: DiagnosticsListRequestData = _
  @BeanProperty
  var data: AnyRef = _ //new LinkedList[DiagnosticsListEntry]

  def this(list: java.util.List[(Action, JobTicket)], requestData: DiagnosticsListRequestData, staff: Staff) {
    this()
    this.requestData = requestData

    this.requestData.filter.asInstanceOf[DiagnosticsListRequestDataFilter].diagnosticType match {
      case "laboratory" => {
        val d = new LinkedList[LaboratoryDiagnosticsListEntry]
        if (list != null && list.size > 0) {
          list.foreach(ajt => d.add(new LaboratoryDiagnosticsListEntry(ajt._1, ajt._2, staff)))
        }
        this.data = new LinkedList[LaboratoryDiagnosticsListEntry]
        d.sortWith((l, r) => l.takingTime.before(r.takingTime)).foreach(this.data.asInstanceOf[LinkedList[LaboratoryDiagnosticsListEntry]].add(_))
      }
      case "instrumental" => {
        val d = new LinkedList[InstrumentalDiagnosticsListEntry]
        if (list != null && list.size > 0) {
          list.foreach(ajt => d.add(new InstrumentalDiagnosticsListEntry(ajt._1, ajt._2, staff)))
        }
        this.data = new LinkedList[InstrumentalDiagnosticsListEntry]
        d.sortWith((l, r) => l.assessmentBeginDate.after(r.assessmentBeginDate))
          .foreach(this.data.asInstanceOf[LinkedList[InstrumentalDiagnosticsListEntry]].add(_))
      }
      case "consultations" => {
        val d = new LinkedList[InstrumentalDiagnosticsListEntry]
        if (list != null && list.size > 0) {
          list.foreach(ajt => d.add(new InstrumentalDiagnosticsListEntry(ajt._1, ajt._2, staff)))
        }
        this.data  = new LinkedList[InstrumentalDiagnosticsListEntry]
        d.sortWith((l, r) => l.assessmentBeginDate.after(r.assessmentBeginDate))
          .foreach(this.data.asInstanceOf[LinkedList[InstrumentalDiagnosticsListEntry]].add(_))
      }
      case _ => {
        this.data = new LinkedList[DiagnosticsListEntry]
        if (list != null && list.size > 0) {
          list.foreach(ajt => this.data.asInstanceOf[LinkedList[DiagnosticsListEntry]].add(new DiagnosticsListEntry(ajt._1)))
        }
      }
    }
  }
}

@XmlType(name = "diagnosticsListRequestData")
@XmlRootElement(name = "diagnosticsListRequestData")
class DiagnosticsListRequestData {

  @BeanProperty
  var filter: AbstractListDataFilter = new DiagnosticsListRequestDataFilter()
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

    this.filter = if (filter != null) {
      filter
    } else {
      null
    }
    this.sortingField = sortingField match {
      case null => {
        "id"
      }
      case _ => {
        sortingField
      }
    }
    this.sortingMethod = sortingMethod match {
      case null => {
        "asc"
      }
      case _ => {
        sortingMethod
      }
    }

    this.sortingFieldInternal =
      if (filter.isInstanceOf[DiagnosticsListRequestDataFilter])
        this.filter.asInstanceOf[DiagnosticsListRequestDataFilter].toSortingString(this.sortingField, this.sortingMethod)
      else
        this.sortingField

    this.limit = if (limit > 0) {
      limit
    } else {
      10
    }
    this.page = if (page > 1) {
      page
    } else {
      1
    }
    this.coreVersion = ConfigManager.Messages("misCore.assembly.version")
  }
}

@XmlType(name = "diagnosticsListRequestDataFilter")
@XmlRootElement(name = "diagnosticsListRequestDataFilter")
class DiagnosticsListRequestDataFilter extends AbstractListDataFilter {

  //   =>Фильтрация по коду типа действия<=
  @BeanProperty
  var code: String = _

  @BeanProperty
  var eventId: Int = _

 /* @BeanProperty
  var diagnosticDate: Date = _

  @BeanProperty
  var directionDate: Date = _ */

  @BeanProperty
  var plannedEndDate: Date = _

  @BeanProperty
  var diagnosticName: String = _

  @BeanProperty
  var assignPersonId: Int = _

  @BeanProperty
  var execPersonId: Int = _

  @BeanProperty
  var office: String = _

  @BeanProperty
  var statusId: Int = _

  @BeanProperty
  var urgent: Int = _

  @BeanProperty
  var mnemonic: Iterable[String] = _

  @BeanProperty
  var clazz: Short = -1

  var diagnosticType: String = _

  def this(code_x: String,
           eventId: Int,
           plannedEndDate: Long,
           diagnosticName: String,
           assignPersonId: Int,
           execPersonId: Int,
           office: String,
           statusId: Int,
           urgent: Int,
           diaType_x: String,
           mnemonic: jl.Iterable[String],
           clazz: Short) {

    this()
    this.diagnosticType = diaType_x
    this.code = if (code_x != null && code_x != "") {
      code_x
    }
    else {
      diaType_x match {  //больше не ищем по коду
          /*
        case "laboratory" => {
          "2_"
        }
        case "instrumental" => {
          "3_"
        }
        case "consultations" => {
          "1_3_"
        }         */
        case _ => {
          ""
        }
      }
    }
    this.eventId = eventId
    this.plannedEndDate = if (plannedEndDate == 0) null else new Date(plannedEndDate)
    this.diagnosticName = diagnosticName
    this.assignPersonId = assignPersonId
    this.execPersonId = execPersonId
    this.office = office
    this.statusId = statusId
    this.urgent = urgent
    this.mnemonic = mnemonic
    this.clazz = clazz
  }

  @Override
  def toQueryStructure() = {
    var qs = new QueryDataStructure()
    if (this.code != null && !this.code.isEmpty) {
      qs.query += "AND a.actionType.code LIKE :code\n"
      qs.add("code", this.code + "%")
    }
    if (this.eventId > 0) {
      qs.query += ("AND a.event.id = :eventId\n")
      qs.add("eventId", this.eventId: java.lang.Integer)
    }
    /*if (this.diagnosticDate != null) {
      qs.query += "AND a.createDatetime = :diagnosticDate\n"
      qs.add("diagnosticDate", this.diagnosticDate)
    }
    if (this.directionDate != null) {
      qs.query += "AND a.directionDate= :directionDate\n"
      qs.add("directionDate", this.directionDate)
    } */

    if (this.plannedEndDate != null) {
      qs.query += "AND a.plannedEndDate= :plannedEndDate\n"
      qs.add("plannedEndDate", this.plannedEndDate)
    }
    if (this.diagnosticName != null && !this.diagnosticName.isEmpty) {
      qs.query += "AND upper(a.actionType.name) LIKE upper(:diagnosticName)\n"
      qs.add("diagnosticName", "%" + this.diagnosticName + "%")
    }
    if (this.assignPersonId > 0) {
      qs.query += "AND a.assigner.id = :assignPersonId\n"
      qs.add("assignPersonId", this.assignPersonId: java.lang.Integer)
    }
    if (this.execPersonId > 0) {
      qs.query += "AND a.executor.id = :execPersonId\n"
      qs.add("execPersonId", this.execPersonId: java.lang.Integer)
    }
    if (this.office != null && !this.office.isEmpty) {
      qs.query += "AND upper(a.office) LIKE upper(:office)\n"
      qs.add("office", "%" + this.office + "%")
    }
    if (this.statusId > 0) {
      qs.query += "AND a.status = :statusId\n"
      qs.add("statusId", this.statusId: java.lang.Integer)
    }
    if (this.urgent != (-1)) {
      qs.query += "AND a.isUrgent = :urgent\n"
      qs.add("urgent", (this.urgent != 0): java.lang.Boolean)
    }
    if (this.mnemonic != null && !this.mnemonic.isEmpty) {
      qs.query += "AND a.actionType.mnemonic IN :mnemonic\n"
      qs.add("mnemonic", asJavaCollection(this.mnemonic))
    }
    if (this.clazz >= 0) {
      qs.query += "AND a.actionType.clazz = :clazz\n"
      qs.add("clazz", this.clazz: java.lang.Integer)
    }
    qs
  }

  @Override
  def toSortingString (sortingField: String, sortingMethod: String) = {
    var sorting = sortingField match {
      case "plannedEndDate" => {"a.plannedEndDate %s".format(sortingMethod)}
      case "diagnosticName" => {"a.actionType.name %s".format(sortingMethod)}
      case "execPerson" => {"a.executor.lastName %s, a.executor.firstName %s, a.executor.patrName %s".format(sortingMethod, sortingMethod, sortingMethod)}
      case "assignPerson" => {"a.assigner.lastName %s, a.assigner.firstName %s, a.assigner.patrName %s".format(sortingMethod, sortingMethod, sortingMethod)}
      case "office" => {"a.office %s".format(sortingMethod)}
      case "status" => {"a.status %s".format(sortingMethod)}
      case "cito" => {"a.isUrgent %s".format(sortingMethod)}
      case _ => {"a.id %s".format(sortingMethod)}
    }
    sorting = "ORDER BY " + sorting
    sorting
  }
}

@XmlType(name = "diagnosticsListEntry")
@XmlRootElement(name = "diagnosticsListEntry")
class DiagnosticsListEntry {

  @BeanProperty
  var id: Int = _ //Ид действия

  @BeanProperty
  var diagnosticDate: Date = _ //Дата диагностики

  @BeanProperty
  var plannedEndDate: Date = _ //Дата направления (Дата забора БМ)

  @BeanProperty
  var diagnosticName: IdNameContainer = _ //Направление исследований

  @BeanProperty
  var assignPerson: DoctorContainer = new DoctorContainer() //Направивший Врач

  @BeanProperty
  var execPerson: DoctorContainer = new DoctorContainer() //Исполнивший Врач

  @BeanProperty
  var createPerson: DoctorContainer = new DoctorContainer() //Создавший направление Врач

  @BeanProperty
  var cito: Boolean = _ //Срочность исследования

  @BeanProperty
  var office: String = _ //Кабинет

  @BeanProperty
  var status: IdNameContainer = _ //Статус

  def this(action: Action) {
    this()
    this.id = action.getId.intValue()
    this.diagnosticDate = action.getCreateDatetime
    this.assignPerson = new DoctorContainer(action.getAssigner)
    this.execPerson = new DoctorContainer(action.getExecutor)
    this.office = action.getOffice
    this.status = new IdNameContainer(action.getStatus, ActionStatus.fromShort(action.getStatus).getName)
    this.diagnosticName = new IdNameContainer(action.getActionType.getId.intValue(), action.getActionType.getName)
    this.plannedEndDate = action.getPlannedEndDate
    this.createPerson = new DoctorContainer(action.getCreatePerson)
    this.cito = action.getIsUrgent
  }
}

@XmlType(name = "laboratoryDiagnosticsListEntry")
@XmlRootElement(name = "laboratoryDiagnosticsListEntry")
class LaboratoryDiagnosticsListEntry {

  @BeanProperty
  var id: Int = _ //Ид действия

  @BeanProperty
  var plannedEndDate: Date = _ //Дата направления (Дата забора БМ)

  @BeanProperty
  var diagnosticName: IdNameContainer = _ //Направление лабораторных исследований

  @BeanProperty
  var assignPerson: DoctorContainer = new DoctorContainer() //Направивший Врач

  @BeanProperty
  var execPerson: DoctorContainer = new DoctorContainer() //Исполнивший Врач

  @BeanProperty
  var createPerson: DoctorContainer = new DoctorContainer() //Создавший направление Врач

  @BeanProperty
  var cito: Boolean = _ //Срочность исследования

  @BeanProperty
  var status: IdNameContainer = _ //Статус

  @BeanProperty
  var isEditable: Boolean = _ //Признак возможности редактирования

  @BeanProperty
  var laboratoryTitle: String = _ //Имя лаборатории

  @BeanProperty
  var takingTime: Date = _ //Время забора

  @BeanProperty
  var mnem: String = _

  def this(action: Action, jt: JobTicket, staff: Staff) {
    this()
    this.id = action.getId.intValue()
    this.plannedEndDate = action.getPlannedEndDate
    this.diagnosticName = new IdNameContainer(action.getActionType.getId.intValue, action.getActionType.getName)
    this.assignPerson = new DoctorContainer(action.getAssigner)
    this.createPerson = new DoctorContainer(action.getCreatePerson)
    this.execPerson = new DoctorContainer(action.getExecutor)
    this.cito = action.getIsUrgent
    this.status = new IdNameContainer(action.getStatus, ActionStatus.fromShort(action.getStatus).getName)
    val isTrueDoctor = (staff.getId.intValue() == action.getCreatePerson.getId.intValue() ||
                        staff.getId.intValue() == action.getAssigner.getId.intValue() )
    this.isEditable = (action.getStatus == 0 && action.getEvent.getExecDate == null && isTrueDoctor && (jt == null || (jt != null && jt.getStatus == 0)))
    laboratoryTitle = getLabNameByAction(action)
    this.takingTime = if( jt == null ) null else jt.getDatetime
    this.mnem = if (action.getActionType == null ) null else action.getActionType.getMnemonic
  }

  /**
   * Получаем имя лаборатории по исследованию
   * Связь достаточно хитрая - ActionProperty -> ActionPropertyType.test_id ->
   * RbTest -> RbLaboratoryTest -> RbLaboratory
   * @param a Исследование, относительно которого отпределяем имя лаборатории
   * @return Поле labName таблицы rbLaboratory соответствующей лаборатории
   *         или пустую строку, если лабораторию не удалось достоверно определить
   *
   */
  private def getLabNameByAction(a: Action): String = {
    val labs = a.getActionProperties.map(ap => {
      if(
        ap.getType.getTest != null && ap.getType.getIsAssignable &&      // Поле test_id заполнено (как и таблица rbTest) и поставлен флаг is_Assignable
        ap.getType.getTest.getRbLaboratoryTest != null &&                // Заполнена таблица rbLaboratory_Test
        ap.getType.getTest.getRbLaboratoryTest.getRbLaboratory != null)  // Заполнена таблица rbLaboratory
        Some(ap.getType.getTest.getRbLaboratoryTest.getRbLaboratory)
      else
        None
    }).flatten

    val names = labs.groupBy(_.getName)
    if(names.size == 1)
      names.head._1
    else
      ""
  }

}

@XmlType(name = "instrumentalDiagnosticsListEntry")
@XmlRootElement(name = "instrumentalDiagnosticsListEntry")
class InstrumentalDiagnosticsListEntry {

  @BeanProperty
  var id: Int = _ //Ид действия

  //@BeanProperty
  //var directionDate: Date = _ //Дата направления

  //@BeanProperty
  //var diagnosticDate: Date = _ //Дата диагностики   (выполнения)

  @BeanProperty
  var plannedEndDate: Date = _ //Дата направления (Дата забора БМ)

  @BeanProperty
  var diagnosticName: IdNameContainer = _ //Направление лабораторных исследований

  @BeanProperty
  var assignPerson: DoctorContainer = new DoctorContainer() //Направивший Врач

  @BeanProperty
  var execPerson: DoctorContainer = new DoctorContainer() //Исполнивший Врач

  @BeanProperty
  var createPerson: DoctorContainer = new DoctorContainer() //Создавший направление Врач

  @BeanProperty
  var cito: Boolean = _ //Срочность исследования

  @BeanProperty
  var status: IdNameContainer = _ //Статус

  @BeanProperty
  var isEditable: Boolean = _ //Признак возможности редактирования

  @BeanProperty
  var mnem: String = _

  @BeanProperty
  var assessmentBeginDate: Date = _


  //@BeanProperty
  //var toOrder: Boolean = _ //Дозаказ  (не используется)

  def this(action: Action, jt: JobTicket, staff: Staff) {
    this()
    this.id = action.getId.intValue()
    //this.diagnosticDate = action.getEndDate
    //this.directionDate = action.getBegDate //getDirectionDate
    this.plannedEndDate = action.getPlannedEndDate
    this.diagnosticName = new IdNameContainer(action.getActionType.getId.intValue, action.getActionType.getName)
    this.assignPerson = new DoctorContainer(action.getAssigner)
    this.execPerson = new DoctorContainer(action.getExecutor)
    this.createPerson = new DoctorContainer(action.getCreatePerson)
    this.cito = action.getIsUrgent
    this.status = new IdNameContainer(action.getStatus, ActionStatus.fromShort(action.getStatus).getName)
    val isTrueDoctor = ((action.getCreatePerson != null &&
                         staff.getId.intValue() == action.getCreatePerson.getId.intValue()) ||
                        (action.getAssigner != null &&
                         staff.getId.intValue() == action.getAssigner.getId.intValue()))
    this.isEditable = (action.getStatus == 0 && action.getEvent.getExecDate == null && isTrueDoctor && (jt == null || (jt != null && jt.getStatus == 0)))
    this.mnem = if (action.getActionType == null ) null else action.getActionType.getMnemonic
    this.assessmentBeginDate = action.getBegDate
    //this.toOrder = action.getToOrder
  }
}