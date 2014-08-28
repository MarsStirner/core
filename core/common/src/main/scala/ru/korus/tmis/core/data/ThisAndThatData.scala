package ru.korus.tmis.core.data

import reflect.BeanProperty
import javax.xml.bind.annotation.{XmlRootElement, XmlType}
import scala.collection.JavaConversions._
import java.text.{DateFormat, SimpleDateFormat}
import scala.Predef._
import ru.korus.tmis.core.entity.model._
import org.codehaus.jackson.annotate.{JsonTypeInfo, JsonSubTypes, JsonIgnoreProperties}
import org.codehaus.jackson.map.annotate.JsonView
import java.util
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.core.filter.{ListDataFilter, AbstractListDataFilter}
import org.codehaus.jackson.map.ObjectMapper
import java.io.IOException
import util.{Calendar, ArrayList, Date}
import ru.korus.tmis.scala.util.ConfigManager

@XmlType(name = "listRequestData")
@XmlRootElement(name = "listRequestData")
class ListDataRequest {
  @BeanProperty
  var filter: AbstractListDataFilter = new DefaultListDataFilter()
  @BeanProperty
  var sortingField: String = "id"
  @BeanProperty
  var sortingMethod: String = "asc"
  @BeanProperty
  var limit: Int = ConfigManager.Messages("misCore.pages.limit.default").toInt
  @BeanProperty
  var page: Int = 1
  @BeanProperty
  var recordsCount: Long = 0
  @BeanProperty
  var coreVersion: String = ConfigManager.Messages("misCore.assembly.version")

  var sortingFieldInternal: String = ""

  def this(sortingField: String,
           sortingMethod: String,
           limit: Int,
           page: Int,
           filter: AbstractListDataFilter) = {
    this()
    this.filter = if (filter != null) {
      filter
    } else {
      new DefaultListDataFilter()
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
    this.limit = limit
    this.page = if (page > 1) page else 1
    this.coreVersion = ConfigManager.Messages("misCore.assembly.version")
    this.sortingFieldInternal = this.filter.toSortingString(this.sortingField, this.sortingMethod)
  }

  def rewriteRecordsCount(recordsCount: java.lang.Long) = {
    this.recordsCount = recordsCount.longValue()
    true
  }
}

@XmlType(name = "allPersonsListData")
@XmlRootElement(name = "allPersonsListData")
class AllPersonsListData {

  @BeanProperty
  var requestData: ListDataRequest = _
  @BeanProperty
  var data: ArrayList[DoctorContainer] = new ArrayList[DoctorContainer]

  def this(persons: java.util.List[Staff], requestData: ListDataRequest) = {
    this()
    this.requestData = requestData
    persons.foreach(p => this.data.add(new DoctorContainer(p)))
  }
}

@XmlType(name = "freePersonsListData")
@XmlRootElement(name = "freePersonsListData")
class FreePersonsListData {

  @BeanProperty
  var requestData: ListDataRequest = _
  @BeanProperty
  var data: ArrayList[DoctorWithScheduleContainer] = new ArrayList[DoctorWithScheduleContainer]

  def this(personsWithSchedule: java.util.HashMap[Staff, java.util.LinkedList[APValueTime]], requestData: ListDataRequest) = {
    this()
    this.requestData = requestData
    personsWithSchedule.foreach(p => this.data.add(new DoctorWithScheduleContainer(p._1, p._2)))
  }
}

@XmlType(name = "doctorWithScheduleContainer")
@XmlRootElement(name = "doctorWithScheduleContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class DoctorWithScheduleContainer {
  @BeanProperty
  var doctor: DoctorContainer = _
  @BeanProperty
  var schedule: java.util.LinkedList[ScheduleContainer] = new java.util.LinkedList[ScheduleContainer]

  def this(staff: Staff, times: java.util.LinkedList[APValueTime]) {
    //
    this()
    this.doctor = new DoctorContainer(staff)
    times.foreach(t => this.schedule.add(new ScheduleContainer(t)))
  }
}

@XmlType(name = "scheduleContainer")
@XmlRootElement(name = "scheduleContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class ScheduleContainer {
  @BeanProperty
  var id: Int = _
  @BeanProperty
  var index: Int = _
  @BeanProperty
  var time: Date = _

  def this(time: APValueTime) {
    this()
    if (time != null) {
      val needTime = Calendar.getInstance()
      needTime.setTime(time.getValue)
      this.id = time.getId.getId
      this.index = time.getId.getIndex
      this.time = new Date(needTime.getTime.getTime + needTime.getTimeZone.getRawOffset) // new Date(time.getValue.getTime)

    }
  }

  def this(id: Int, index: Int, time: Date) {
    this()
    this.id = id
    this.index = index
    this.time = time
  }
}

@XmlType(name = "freePersonsListDataFilter")
@XmlRootElement(name = "freePersonsListDataFilter")
class FreePersonsListDataFilter extends AbstractListDataFilter {
  @BeanProperty
  var speciality: Int = _
  @BeanProperty
  var doctorId: Int = _
  @BeanProperty
  var actionType: Int = _
  @BeanProperty
  var beginDate: Date = _
  @BeanProperty
  var endDate: Date = _

  var beginOnlyDate: Date = _
  var beginOnlyTime: Date = _
  var endOnlyTime: Date = _

  def this(speciality: Int, doctorId: Int, actionType: Int, beginDate: Long, endDate: Long) {
    this()
    this.speciality = speciality
    this.doctorId = doctorId
    this.actionType = actionType
    this.beginDate = if (beginDate == 0) {
      null
    } else {
      new Date(beginDate)
    }
    this.endDate = if (endDate == 0) {
      null
    } else {
      new Date(endDate)
    }

    //парсинг чистой даты и чистого времени    (дату достаем только начала(дб этот день))
    val formatter: DateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
    val formatter2: DateFormat = new SimpleDateFormat("0000-00-00 HH:mm:ss");
    val date: Date = formatter.parse(formatter.format(beginDate));
    val btime: Date = formatter2.parse(formatter2.format(beginDate));
    val etime: Date = formatter2.parse(formatter2.format(endDate));
    this.beginOnlyDate = date
    this.beginOnlyTime = btime
    this.endOnlyTime = etime
  }

  @Override
  def toQueryStructure() = {
    var qs = new QueryDataStructure()
    if (this.speciality > 0) {
      qs.query += "AND s.speciality.id = :speciality\n"
      qs.add("speciality", this.speciality: java.lang.Integer)
    } else if (this.actionType > 0) {
      qs.query += "AND at.id = :actionType\n AND sProfile.service.id = at.service.id\n AND sProfile.speciality.id = s.speciality.id\n"
      qs.add("actionType", this.actionType: java.lang.Integer)
    }
    if (this.doctorId > 0) {
      qs.query += ("AND s.id = :doctorId\n")
      qs.add("doctorId", this.doctorId: java.lang.Integer)
    }
    if (this.beginOnlyDate != null) {
      qs.query += "AND e.setDate = :beginOnlyDate\n"
      qs.add("beginOnlyDate", this.beginOnlyDate)
    }
    qs
  }

  @Override
  def toSortingString(sortingField: String, sortingMethod: String) = {
    var sorting = sortingField match {
      case "fio" | "fullname" => {
        "s.lastName %s, s.firstName %s, s.patrName %s".format(sortingMethod, sortingMethod, sortingMethod)
      }
      case "lastname" => {
        "s.lastName %s".format(sortingMethod)
      }
      case "firstname" | "name" => {
        "s.firstName %s".format(sortingMethod)
      }
      case "patrname" => {
        "s.patrName %s".format(sortingMethod)
      }
      case _ => {
        "s.id %s".format(sortingMethod)
      }
    }
    sorting = "ORDER BY " + sorting
    sorting
  }
}

@XmlType(name = "personsListDataFilter")
@XmlRootElement(name = "personsListDataFilter")
class PersonsListDataFilter extends AbstractListDataFilter {
  @BeanProperty
  var department: Int = _

  def this(departmentId: Int) {
    this()
    this.department = departmentId
  }

  @Override
  def toQueryStructure() = {
    var qs = new QueryDataStructure()
    if (this.department > 0) {
      qs.query += "AND s.orgStructure.id = :department\n"
      qs.add("department", this.department: java.lang.Integer)
    }
    qs
  }

  @Override
  def toSortingString(sortingField: String, sortingMethod: String) = {
    var sorting = sortingField match {
      case "fio" | "fullname" => {
        "s.lastName %s, s.firstName %s, s.patrName %s".format(sortingMethod, sortingMethod, sortingMethod)
      }
      case "lastname" => {
        "s.lastName %s".format(sortingMethod)
      }
      case "firstname" | "name" => {
        "s.firstName %s".format(sortingMethod)
      }
      case "patrname" => {
        "s.patrName %s".format(sortingMethod)
      }
      case _ => {
        "s.id %s".format(sortingMethod)
      }
    }
    sorting = "ORDER BY " + sorting
    sorting
  }
}

@XmlType(name = "allDepartmentsListData")
@XmlRootElement(name = "allDepartmentsListData")
class AllDepartmentsListData extends AbstractDefaultData {

  @BeanProperty
  var requestData: ListDataRequest = _
  @BeanProperty
  var data: ArrayList[IdNameContainer] = new ArrayList[IdNameContainer]

  def this(departments: java.util.List[OrgStructure], requestData: ListDataRequest) = {
    this()
    this.requestData = requestData
    if (departments != null && !departments.isEmpty)
      departments.foreach(org => this.data.add(new IdNameContainer(org.getId.intValue(), org.getName)))
  }

  override def dataToString = {
    val mapper = new ObjectMapper()
    try {
      mapper.writeValueAsString(this)
    } catch {
      case e: IOException => {
        throw e
      }
    }
  }
}

//Для списка типов действий
@XmlType(name = "actionTypesListData")
@XmlRootElement(name = "actionTypesListData")
class ActionTypesListData {

  @BeanProperty
  var requestData: ListDataRequest = _
  @BeanProperty
  var data: ArrayList[ActionTypesListEntry] = new ArrayList[ActionTypesListEntry]

  def this(requestData: ListDataRequest, getAllActionTypeWithFilter: (Int, Int, String, ListDataFilter) => java.util.List[ActionType]) = {
    this()
    this.requestData = requestData

    var ats = getAllActionTypeWithFilter(0, 0, this.requestData.sortingFieldInternal, this.requestData.filter.unwrap())
    ats.foreach(at => {
      requestData.setFilter(new ActionTypesListRequestDataFilter("",
        at.getId.intValue(),
        this.requestData.filter.asInstanceOf[ActionTypesListRequestDataFilter].flatCodes,
        this.requestData.filter.asInstanceOf[ActionTypesListRequestDataFilter].mnemonics,
        this.requestData.filter.asInstanceOf[ActionTypesListRequestDataFilter].view,
        this.requestData.filter.asInstanceOf[ActionTypesListRequestDataFilter].showHidden,
        null))
      var elem: ActionType = null
      if (at.getGroupId != null && requestData.filter.asInstanceOf[ActionTypesListRequestDataFilter].view.compareTo("tree") == 0) {
        elem = ats.find(at2 => at2.getId.intValue() == at.getGroupId.intValue()).getOrElse(null)
      }
      if (elem == null) {
        this.data.add(new ActionTypesListEntry(at, requestData, getAllActionTypeWithFilter))
      }
    })
  }
}

@XmlType(name = "actionTypesListRequestDataFilter")
@XmlRootElement(name = "actionTypesListRequestDataFilter")
class ActionTypesListRequestDataFilter extends AbstractListDataFilter {

  @BeanProperty
  var code: String = _

  @BeanProperty
  var flatCodes: java.util.List[String] = new java.util.LinkedList[String]

  @BeanProperty
  var groupId: Int = 0

  @BeanProperty
  var mnemonics: java.util.List[String] = new java.util.LinkedList[String]

  @BeanProperty
  var view: String = "all"

  @BeanProperty
  var showHidden: Boolean = false

  @BeanProperty
  var orgStructId: Integer = null

  def this(code_x: String,
           groupId: Int,
           flatCodes: java.util.List[String],
           mnemonics: java.util.List[String],
           view: String,
           showHidden: Boolean,
           orgStructId: Integer) {
    this()
    if (code_x != null && code_x != "")
      this.code = code_x
    this.groupId = groupId
    if (flatCodes != null && flatCodes.size() > 0) {
      this.flatCodes = flatCodes.filter(p => (p != null && !p.isEmpty))
    }
    this.mnemonics = mnemonics.filter(p => (p != null && !p.isEmpty))
    if (view != null && !view.isEmpty) {
      this.view = view
    }
    this.showHidden = showHidden
    this.orgStructId = orgStructId
  }

  @Override
  def toQueryStructure() = {
    val qs = new QueryDataStructure()
    if (this.groupId > 0) {
      qs.query += ("AND at.groupId = :groupId\n")
      qs.add("groupId", this.groupId: java.lang.Integer)
    }
    else if (this.code != null && !this.code.isEmpty) {
      qs.query += ("AND at.groupId IN (SELECT at2.id FROM ActionType at2 WHERE at2.code = :code)\n")
      qs.add("code", this.code)
    }
    else if (this.flatCodes != null && this.flatCodes.size() > 0) {
      qs.query += ("AND at.flatCode IN :flatCodes\n")
      qs.add("flatCodes", asJavaCollection(this.flatCodes))
    }
    if (this.mnemonics != null && this.mnemonics.size() > 0) {
      qs.query += ("AND at.mnemonic IN :mnemonic\n")
      qs.add("mnemonic", asJavaCollection(this.mnemonics))
    }
    if (!showHidden) {
      qs.query += ("AND at.hidden = false\n")
    }
    if (orgStructId != null) {
      qs.query += ("AND at.id IN (SELECT sat.actionType.id FROM OrgStructureActionType sat WHERE sat.masterDepartment.id = :orgStructId)\n")
      qs.add("orgStructId", this.orgStructId)
    }
    qs
  }

  @Override
  def toSortingString(sortingField: String, sortingMethod: String) = {
    var sorting = sortingField match {
      case "groupId" => {
        "at.groupId %s"
      }
      case "code" => {
        "at.code %s"
      }
      case "name" => {
        "at.name %s"
      }
      case "flatCode" => {
        "at.flatCode %s"
      }
      case "mnem" => {
        "at.mnemonic %s"
      }
      case _ => {
        "at.id %s"
      }
    }
    sorting = "ORDER BY " + sorting.format(sortingMethod)
    sorting
  }
}

object ActionTypesListDataViews {

  class OneLevelView {
  }

  class DefaultView {
  }

}

class ActionTypesListDataViews {}

@XmlType(name = "actionTypesListEntry")
@XmlRootElement(name = "actionTypesListEntry")
class ActionTypesListEntry {

  @BeanProperty
  var id: Int = _

  @BeanProperty
  var groupId: Int = _

  @BeanProperty
  var code: String = _

  @BeanProperty
  var flatCode: String = _

  @BeanProperty
  var name: String = _

  @JsonView(Array(classOf[ActionTypesListDataViews.DefaultView]))
  @BeanProperty
  var groups: java.util.LinkedList[ActionTypesListEntry] = new java.util.LinkedList[ActionTypesListEntry]

  //@BeanProperty
  //var childrenCount: Long = _

  def this(actionType: ActionType, requestData: ListDataRequest, getAllActionTypeWithFilter: (Int, Int, String, ListDataFilter) => java.util.List[ActionType]) {
    this()
    this.id = actionType.getId.intValue()
    this.groupId = if (actionType.getGroupId != null) {
      actionType.getGroupId.intValue()
    } else {
      0
    }
    this.code = actionType.getCode
    this.flatCode = actionType.getFlatCode
    this.name = actionType.getName
    if (requestData.filter.asInstanceOf[ActionTypesListRequestDataFilter].view.compareTo("tree") == 0) {
      getAllActionTypeWithFilter(0, 0, requestData.sortingFieldInternal, requestData.filter.unwrap()).foreach(f => {
        val filter = new ActionTypesListRequestDataFilter("",
          f.getId.intValue(),
          requestData.filter.asInstanceOf[ActionTypesListRequestDataFilter].flatCodes,
          requestData.filter.asInstanceOf[ActionTypesListRequestDataFilter].mnemonics,
          requestData.filter.asInstanceOf[ActionTypesListRequestDataFilter].view,
          requestData.filter.asInstanceOf[ActionTypesListRequestDataFilter].showHidden,
          null)
        val request = new ListDataRequest(requestData.sortingField, requestData.sortingMethod, requestData.limit, requestData.page, filter)
        this.groups.add(new ActionTypesListEntry(f, request, getAllActionTypeWithFilter))
      })
    }
    //this.childrenCount = actionType
  }
}

object AllMKBListDataViews {

  class OneLevelView {
  }

  class DefaultView {
  }

}

class AllMKBListDataViews {}

@XmlType(name = "allMKBListData")
@XmlRootElement(name = "allMKBListData")
class AllMKBListData {

  @BeanProperty
  var requestData: ListDataRequest = _

  @BeanProperty
  var data: java.util.LinkedList[AbstractMKBContainer] = new java.util.LinkedList[AbstractMKBContainer] //AnyRef = _//java.util.LinkedList[ClassMKBContainer] = new java.util.LinkedList[ClassMKBContainer]


  def this(mkbs: java.util.List[Mkb],
           mkbs_display: Object,
           requestData: ListDataRequest) = {
    this()
    this.requestData = requestData

    //Анализ формы вывода
    requestData.filter.asInstanceOf[MKBListRequestDataFilter].view match {
      case "class" => {
        val classMap = getGroupedValuesByLevel(mkbs, CLASS_LEVEL)
        this.requestData.setRecordsCount(classMap.size)
        classMap.foreach(f => {
          val (minDiagId, maxDiagId) = this.getMinAndMaxDiagId(f._2.getClassName, mkbs)
          this.data.add(new ClassMKBContainer(f._1, f._2.getClassName, minDiagId, maxDiagId))
        })
      }
      case "group" => {
        val groupMap = getGroupedValuesByLevel(mkbs, GROUP_LEVEL)
        this.requestData.setRecordsCount(groupMap.size)
        groupMap.foreach(f => this.data.add(new GroupMKBContainer(f._1, f._2.getBlockName)))
      }
      case "subgroup" => {
        val subgroupMap = getGroupedValuesByLevel(mkbs, SUBGROUP_LEVEL)
        this.requestData.setRecordsCount(subgroupMap.size)
        subgroupMap.foreach(f => this.data.add(new SubGroupMKBContainer(f._1, f._2.getDiagName)))
      }
      case "mkb" => {
        val mkbMap = getGroupedValuesByLevel(mkbs, MKB_LEVEL)
        this.requestData.setRecordsCount(mkbMap.size)
        mkbMap.foreach(f => this.data.asInstanceOf[java.util.LinkedList[MKBContainerEx]].add(new MKBContainerEx(f._2)))
      }
      case _ => {
        //дерево тогда анализируем дисплей
        val classMap = getGroupedValuesByLevel(mkbs, CLASS_LEVEL)

        if (requestData.filter.asInstanceOf[MKBListRequestDataFilter].display) {
          val rolled = getRolledBrunch(mkbs_display.asInstanceOf[java.util.Map[String, java.util.Map[String, Mkb]]], CLASS_LEVEL)
          if (rolled != null) {
            rolled.foreach(f => {
              val (minDiagId, maxDiagId) = this.getMinAndMaxDiagId(f._2.getClassName, mkbs)
              if (!classMap.containsKey(f._1)) {
                this.data.asInstanceOf[java.util.LinkedList[ClassMKBContainer]]
                  .add(new ClassMKBContainer(f._1, f._2.getClassName, minDiagId, maxDiagId))
              }
              else {
                val filtredMkbs = getFilteredValuesByLevel(mkbs, classMap.get(f._1), CLASS_LEVEL)
                this.data.asInstanceOf[java.util.LinkedList[ClassMKBContainer]]
                  .add(new ClassMKBContainer(f._1,
                  f._2.getClassName,
                  minDiagId,
                  maxDiagId,
                  filtredMkbs,
                  mkbs_display.asInstanceOf[java.util.Map[String, java.util.Map[String, Mkb]]],
                  getGroupedValuesByLevel,
                  getFilteredValuesByLevel,
                  getRolledBrunch))
              }
            })
          }
          else {
            classMap.foreach(f => {
              val (minDiagId, maxDiagId) = this.getMinAndMaxDiagId(f._2.getClassName, mkbs)
              val filtredMkbs = getFilteredValuesByLevel(mkbs, f._2, CLASS_LEVEL)
              this.data.asInstanceOf[java.util.LinkedList[ClassMKBContainer]]
                .add(new ClassMKBContainer(f._1, f._2.getClassName, minDiagId, maxDiagId, filtredMkbs, null, getGroupedValuesByLevel _, getFilteredValuesByLevel _, null))
            })
          }
        }
        else {
          classMap.foreach(f => {
            val (minDiagId, maxDiagId) = this.getMinAndMaxDiagId(f._2.getClassName, mkbs)
            val filtredMkbs = getFilteredValuesByLevel(mkbs, f._2, CLASS_LEVEL)
            this.data.asInstanceOf[java.util.LinkedList[ClassMKBContainer]]
              .add(new ClassMKBContainer(f._1, f._2.getClassName, minDiagId, maxDiagId, filtredMkbs, null, getGroupedValuesByLevel _, getFilteredValuesByLevel _, null))
          })
        }
      }
    }
  }

  private val CLASS_LEVEL = 0
  private val GROUP_LEVEL = 1
  private val SUBGROUP_LEVEL = 2
  private val MKB_LEVEL = 3

  private def getGroupedValuesByLevel(mkbs: java.util.List[Mkb], level: Int) = {

    mkbs.foldLeft(new java.util.LinkedHashMap[String, Mkb])(
      (map, f) => {
        if ((level == SUBGROUP_LEVEL && f.getDiagID.indexOf(".") <= 0) ||
          (level != SUBGROUP_LEVEL)) {
          val levelId = levelConstruct(f, level)
          if (!map.contains(levelId))
            map.put(levelId, f)
          map
        }
        else map
      })
  }

  private def getFilteredValuesByLevel(mkbs: java.util.List[Mkb], compared: Mkb, level: Int) = {

    mkbs.foldLeft(new java.util.LinkedList[Mkb])(
      (list, p) => {
        if ((levelConstruct(p, level) == levelConstruct(compared, level) && (level != SUBGROUP_LEVEL)) ||
          ((level == SUBGROUP_LEVEL) && (levelConstruct(p, level).contains(levelConstruct(compared, level))) && (levelConstruct(p, level) != levelConstruct(compared, level)))) {
          list.add(p)
          list
        } else list
      })
  }

  private def levelConstruct(mkb: Mkb, level: Int) = {
    level match {
      case CLASS_LEVEL => mkb.getClassID
      case GROUP_LEVEL => mkb.getBlockID
      case SUBGROUP_LEVEL => mkb.getDiagID
      case MKB_LEVEL => mkb.getDiagID
      case _ => ""
    }
  }

  private def getRolledBrunch(mkbs_display: java.util.Map[String, java.util.Map[String, Mkb]], level: Int) = {
    if (mkbs_display != null && mkbs_display.size() > 0 && mkbs_display.containsKey(levelRolledBrunchesKey(level))) {
      val mkbs = mkbs_display.get(levelRolledBrunchesKey(level))
      if (mkbs.size() > 0)
        mkbs
      else
        null: java.util.Map[String, Mkb]
    }
    else
      null: java.util.Map[String, Mkb]
  }

  private def levelRolledBrunchesKey(level: Int) = {
    level match {
      case CLASS_LEVEL => "class"
      case GROUP_LEVEL => "block"
      case SUBGROUP_LEVEL => "code"
      case _ => ""
    }
  }

  private def getMinAndMaxDiagId(clsName: String, mkbs: java.util.List[Mkb]) = {
    val clsMkbs = mkbs.filter(mkb => mkb.getClassName.compareTo(clsName) == 0)
    val minMkb = clsMkbs.find(p => p.getId.intValue() == clsMkbs.map(_.getId.intValue()).foldLeft(Int.MaxValue)((i, m) => m.min(i))).getOrElse(null)
    val maxMkb = clsMkbs.find(p => p.getId.intValue() == clsMkbs.map(_.getId.intValue()).foldLeft(Int.MinValue)((i, m) => m.max(i))).getOrElse(null)
    val minDiagId: String = if (minMkb != null) minMkb.getDiagID else null
    val maxDiagId: String = if (maxMkb != null) maxMkb.getDiagID else null

    (minDiagId, maxDiagId)
  }
}

@XmlType(name = "mkbListRequestDataFilter")
@XmlRootElement(name = "mkbListRequestDataFilter")
class MKBListRequestDataFilter extends AbstractListDataFilter {

  @BeanProperty
  var mkbId: Int = _

  @BeanProperty
  var classId: String = _

  @BeanProperty
  var groupId: String = _

  @BeanProperty
  var code: String = _ //diagId

  @BeanProperty
  var diagnosis: String = _

  @BeanProperty
  var view: String = "all"

  @BeanProperty
  var display: Boolean = false

  @BeanProperty
  var sex: Int = 0

  def this(mkbId: Int,
           classId: String,
           blockId: String,
           code: String,
           diagnosis: String,
           view: String,
           flgDisplay: Boolean,
           sex: Int) {
    this()
    this.mkbId = mkbId
    this.classId = classId
    this.groupId = blockId
    this.code = code
    this.diagnosis = diagnosis
    if (view != null && !view.isEmpty) {
      this.view = view
    }
    this.display = flgDisplay
    this.sex = sex
  }

  @Override
  def toQueryStructure() = {
    var qs = new QueryDataStructure()

    if (this.mkbId > 0) {
      qs.query += ("AND mkb.id = :id\n")
      qs.add("id", this.mkbId: java.lang.Integer)
    }
    else {
      if (this.code != null && !this.code.isEmpty) {
        qs.query += ("AND upper(mkb.diagID) LIKE upper(:code)\n")
        qs.add("code", this.code + "%")
      } else {
        if (this.groupId != null && !this.groupId.isEmpty) {
          qs.query += ("AND upper(mkb.blockID) = upper(:blockId)\n")
          qs.add("blockId", this.groupId)
        } else {
          if (this.classId != null && !this.classId.isEmpty) {
            qs.query += ("AND upper(mkb.classID) = upper(:classId)\n")
            qs.add("classId", this.classId)
          }
        }
      }
      if (this.diagnosis != null && !this.diagnosis.isEmpty) {
        qs.query += ("AND upper(mkb.diagName) LIKE upper(:diagnosis)\n")
        qs.add("diagnosis", "%" + this.diagnosis + "%")
      }
    }
    if (this.sex > 0) {
      qs.query += ("AND (mkb.sex = :sex OR mkb.sex = '0')\n")
      qs.add("sex", this.sex: java.lang.Integer)
    }
    qs
  }

  @Override
  def toSortingString(sortingField: String, sortingMethod: String) = {
    var sorting = sortingField match {
      case "classId" => {
        "mkb.classID %s"
      }
      case "blockId" => {
        "mkb.blockID %s"
      }
      case "code" => {
        "mkb.diagID %s"
      }
      case "diagnosis" => {
        "mkb.diagName %s"
      }
      case _ => {
        "mkb.id %s"
      }
    }
    sorting = "ORDER BY " + sorting.format(sortingMethod)
    sorting
  }
}

/*@JsonTypeInfo(
use = JsonTypeInfo.Id.NAME,
include = JsonTypeInfo.As.PROPERTY,
property = "type")
@JsonSubTypes({
@JsonSubTypes.Type(value = DefaultListDataFilter.class, name = "DefaultListDataFilter"),
@JsonSubTypes.Type(value = DictionaryListRequestDataFilter.class, name = "DictionaryListRequestDataFilter"),
@JsonSubTypes.Type(value = MKBListRequestDataFilter.class, name = "MKBListRequestDataFilter")
})
public abstract class AbstractMKBContainer implements MKBContainerEx {

@Override
public abstract String toSortingString (String sortingField, String sortingMethod);

@Override
public abstract QueryDataStructure toQueryStructure();

@Override
public ListDataFilter unwrap() {
return this;
}
}  */

trait IMKBContainer {

  @BeanProperty
  var id: String = _

  @BeanProperty
  var code: String = _

  def getLEVEL: Int

  def unwrap: IMKBContainer
}

@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type")
@JsonSubTypes(
  Array(
    new JsonSubTypes.Type(value = classOf[ClassMKBContainer], name = "ClassMKBContainer"),
    new JsonSubTypes.Type(value = classOf[GroupMKBContainer], name = "GroupMKBContainer"),
    new JsonSubTypes.Type(value = classOf[SubGroupMKBContainer], name = "SubGroupMKBContainer"),
    new JsonSubTypes.Type(value = classOf[MKBContainerEx], name = "MKBContainerEx")
  )
)
abstract class AbstractMKBContainer extends IMKBContainer {

  @Override
  def getLEVEL = 0

  @Override
  def unwrap = this
}


@XmlType(name = "classMkbContainer")
@XmlRootElement(name = "classMkbContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class ClassMKBContainer extends AbstractMKBContainer {

  @BeanProperty
  var diagIdMin: String = _

  @BeanProperty
  var diagIdMax: String = _

  @JsonView(Array(classOf[AllMKBListDataViews.DefaultView]))
  @BeanProperty
  var groups: java.util.LinkedList[GroupMKBContainer] = new java.util.LinkedList[GroupMKBContainer]

  def this(id: String, code: String) {
    this()
    this.id = id
    this.code = code
  }

  def this(id: String, code: String, diagIdMin: String, diagIdMax: String) {
    this(id, code)
    this.diagIdMin = diagIdMin
    this.diagIdMax = diagIdMax
  }

  def this(id: String,
           code: String,
           diagIdMin: String,
           diagIdMax: String,
           mkbs: java.util.List[Mkb],
           mkbs_display: java.util.Map[String, java.util.Map[String, Mkb]],
           mGroupedValuesByLevel: (java.util.List[Mkb], Int) => java.util.LinkedHashMap[String, Mkb],
           mFilteredValuesByLevel: (java.util.List[Mkb], Mkb, Int) => java.util.LinkedList[Mkb],
           mRolledBrunch: (java.util.Map[String, java.util.Map[String, Mkb]], Int) => java.util.Map[String, Mkb]) {
    this(id, code, diagIdMin, diagIdMax)

    if (mkbs != null && mGroupedValuesByLevel != null && mFilteredValuesByLevel != null) {
      val groupMap = mGroupedValuesByLevel(mkbs, getLEVEL)
      if (mkbs_display != null && mRolledBrunch != null) {
        val rolled = mRolledBrunch(mkbs_display, getLEVEL)
        if (rolled != null) {
          rolled.foreach(f => {
            if (!groupMap.containsKey(f._1)) {
              this.groups.add(new GroupMKBContainer(f._1, f._2.getBlockName))
            }
            else {
              val filtredMkbs = mFilteredValuesByLevel(mkbs, groupMap.get(f._1), getLEVEL)
              this.groups.add(new GroupMKBContainer(f._1,
                f._2.getBlockName,
                filtredMkbs,
                mkbs_display,
                mGroupedValuesByLevel,
                mFilteredValuesByLevel,
                mRolledBrunch))
            }
          })
        }
        else {
          groupMap.foreach(f => {
            val filtredMkbs = mFilteredValuesByLevel(mkbs, f._2, getLEVEL)
            this.groups.add(new GroupMKBContainer(f._1, f._2.getBlockName, filtredMkbs, null, mGroupedValuesByLevel, mFilteredValuesByLevel, null))
          })
        }
      }
      else {
        groupMap.foreach(f => {
          val filtredMkbs = mFilteredValuesByLevel(mkbs, f._2, getLEVEL)
          this.groups.add(new GroupMKBContainer(f._1, f._2.getBlockName, filtredMkbs, null, mGroupedValuesByLevel, mFilteredValuesByLevel, null))
        })
      }
    }
  }

  override def getLEVEL = 1
}

@XmlType(name = "groupMkbContainer")
@XmlRootElement(name = "groupMkbContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class GroupMKBContainer extends AbstractMKBContainer {

  @JsonView(Array(classOf[AllMKBListDataViews.DefaultView]))
  @BeanProperty
  var subGroups: java.util.LinkedList[SubGroupMKBContainer] = new java.util.LinkedList[SubGroupMKBContainer]

  def this(id: String, code: String) {
    this()
    this.id = id
    this.code = code
  }

  def this(id: String,
           code: String,
           mkbs: java.util.LinkedList[Mkb],
           mkbs_display: java.util.Map[String, java.util.Map[String, Mkb]],
           mGroupedValuesByLevel: (java.util.List[Mkb], Int) => java.util.LinkedHashMap[String, Mkb],
           mFilteredValuesByLevel: (java.util.List[Mkb], Mkb, Int) => java.util.LinkedList[Mkb],
           mRolledBrunch: (java.util.Map[String, java.util.Map[String, Mkb]], Int) => java.util.Map[String, Mkb]) {
    this(id, code)

    if (mkbs != null && mGroupedValuesByLevel != null && mFilteredValuesByLevel != null) {
      val subgroupMap = mGroupedValuesByLevel(mkbs, getLEVEL)
      if (mkbs_display != null && mRolledBrunch != null) {
        val rolled = mRolledBrunch(mkbs_display, getLEVEL)
        if (rolled != null) {
          rolled.foreach(f => {
            if (!subgroupMap.containsKey(f._1)) {
              this.subGroups.add(new SubGroupMKBContainer(f._1, f._2.getDiagName))
            }
            else {
              val filtredMkbs = mFilteredValuesByLevel(mkbs, subgroupMap.get(f._1), getLEVEL)
              this.subGroups.add(new SubGroupMKBContainer(f._1,
                f._2.getDiagName,
                filtredMkbs,
                mGroupedValuesByLevel
              ))
            }
          })
        }
        else {
          subgroupMap.foreach(f => {
            val filtredMkbs = mFilteredValuesByLevel(mkbs, f._2, getLEVEL)
            this.subGroups.add(new SubGroupMKBContainer(f._1, f._2.getDiagName, filtredMkbs, mGroupedValuesByLevel))
          })
        }
      }
      else {
        subgroupMap.foreach(f => {
          val filtredMkbs = mFilteredValuesByLevel(mkbs, f._2, getLEVEL)
          this.subGroups.add(new SubGroupMKBContainer(f._1, f._2.getDiagName, filtredMkbs, mGroupedValuesByLevel))
        })
      }
    }
  }

  override def getLEVEL = 2
}

@XmlType(name = "subGroupMkbContainer")
@XmlRootElement(name = "subGroupMkbContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class SubGroupMKBContainer extends AbstractMKBContainer {

  @JsonView(Array(classOf[AllMKBListDataViews.DefaultView]))
  @BeanProperty
  var mkbs: java.util.LinkedList[MKBContainerEx] = new java.util.LinkedList[MKBContainerEx]

  def this(mkbSubGroup: (String, java.util.List[Mkb]),
           mkbs_display: Object,
           subGroupTitleValue: java.util.Map[String, String]) {
    this()
    this.id = mkbSubGroup._1
    this.code = subGroupTitleValue.get(mkbSubGroup._1)
    if (mkbSubGroup._2 != null && mkbSubGroup._2.size() > 0) {
      mkbSubGroup._2.foreach(mkb => this.mkbs.add(new MKBContainerEx(mkb)))
    }
  }

  def this(id: String, code: String) {
    this()
    this.id = id
    this.code = code
  }

  def this(id: String,
           code: String,
           mkbs: java.util.LinkedList[Mkb],
           mGroupedValuesByLevel: (java.util.List[Mkb], Int) => java.util.LinkedHashMap[String, Mkb]) {
    this(id, code)

    val mkbMap = mGroupedValuesByLevel(mkbs, getLEVEL)
    mkbMap.foreach(f => this.mkbs.add(new MKBContainerEx(f._2)))
  }

  override def getLEVEL = 3
}

@XmlType(name = "mKBContainerEx")
@XmlRootElement(name = "mKBContainerEx")
@JsonIgnoreProperties(ignoreUnknown = true)
class MKBContainerEx extends AbstractMKBContainer {

  @BeanProperty
  var diagnosis: String = _

  def this(mkb: Mkb) {
    this()
    if (mkb != null) {
      this.id = mkb.getId.toString
      this.code = mkb.getDiagID
      this.diagnosis = mkb.getDiagName
    }
  }

  override def getLEVEL = 4
}

@XmlType(name = "thesaurusListData")
@XmlRootElement(name = "thesaurusListData")
class ThesaurusListData {

  @BeanProperty
  var requestData: ListDataRequest = _
  @BeanProperty
  var data: ArrayList[ThesaurusContainer] = new ArrayList[ThesaurusContainer]

  def this(thesaurus: java.util.List[Thesaurus], requestData: ListDataRequest) = {
    this()
    this.requestData = requestData
    thesaurus.foreach(thes => this.data.add(new ThesaurusContainer(thes)))
  }
}

@XmlType(name = "thesaurusListRequestDataFilter")
@XmlRootElement(name = "thesaurusListRequestDataFilter")
class ThesaurusListRequestDataFilter extends AbstractListDataFilter {
  @BeanProperty
  var id: Int = _

  @BeanProperty
  var groupId: String = _

  @BeanProperty
  var code: String = _ //

  def this(id: Int,
           groupId: String,
           code: String) {
    this()
    this.id = id
    this.groupId = groupId
    this.code = code
  }

  @Override
  def toQueryStructure() = {
    var qs = new QueryDataStructure()

    if (this.id > 0) {
      qs.query += ("AND r.id = :id\n")
      qs.add("id", this.id: java.lang.Integer)
    }
    else {
      if (this.groupId != null && !this.groupId.isEmpty) {
        qs.query += ("AND upper(r.groupId) = upper(:groupId)\n")
        qs.add("groupId", this.groupId)
      }
      if (this.code != null && !this.code.isEmpty) {
        qs.query += ("AND upper(r.code) LIKE upper(:code)\n")
        qs.add("code", this.code + "%")
      }
    }
    qs
  }

  @Override
  def toSortingString(sortingField: String, sortingMethod: String) = {
    var sorting = sortingField match {
      case "groupId" => {
        "r.groupID %s"
      }
      case "code" => {
        "r.code %s"
      }
      case _ => {
        "r.id %s"
      }
    }
    sorting = "ORDER BY " + sorting.format(sortingMethod)
    sorting
  }
}

object DictionaryDataViews {

  class InsuranceView {}

  class DefaultView {}

  class PolicyTypeView {}

  class ClientDocumentView {}

  class TFOMSView {}

  class KLADRView {}

  class ValueDomainView {}

  class RequestTypesView {}

}

class DictionaryDataViews {}

@XmlType(name = "dictionaryListData")
@XmlRootElement(name = "dictionaryListData")
class DictionaryListData {

  @BeanProperty
  var requestData: ListDataRequest = _
  @BeanProperty
  var data: ArrayList[DictionaryContainer] = new ArrayList[DictionaryContainer]

  def this(requestData: ListDataRequest) = {
    this()
    this.requestData = requestData
  }

  def this(types: java.util.List[AnyRef], requestData: ListDataRequest) = {
    this(requestData)
    if (types != null) {
      types.foreach(dict => {

        this.requestData.filter.asInstanceOf[DictionaryListRequestDataFilter].dictName match {
          case "KLADR" => {
            if (dict.isInstanceOf[(java.lang.String, java.lang.String, java.lang.String, java.lang.String)]) {
              //KLADR
              var elem = dict.asInstanceOf[(java.lang.String, java.lang.String, java.lang.String, java.lang.String)]
              this.data.add(new DictionaryContainer(elem._1, elem._2, elem._3, elem._4))
            } else {
              this.data.add(new DictionaryContainer(0, ""))
            }
          }
          case _ => {
            if (dict.isInstanceOf[(java.lang.Integer, java.lang.String)]) {
              var elem = dict.asInstanceOf[(java.lang.Integer, java.lang.String)]
              this.data.add(new DictionaryContainer(elem._1.intValue(),
                elem._2))
            } else if (dict.isInstanceOf[(java.lang.Integer, java.lang.String, java.lang.Integer)]) {
              //Insurance
              var elem = dict.asInstanceOf[(java.lang.Integer, java.lang.String, java.lang.Integer)]
              if (elem._3.isInstanceOf[java.lang.Integer]) {
                this.data.add(new DictionaryContainer(elem._1.intValue(), elem._2, elem._3.intValue()))
              } else if (elem._3.isInstanceOf[java.lang.String]) {
                this.data.add(new DictionaryContainer(elem._1.intValue, elem._2, elem._3.asInstanceOf[java.lang.String]))
              }
            } else if (dict.isInstanceOf[java.lang.String]) {
              var elem = dict.asInstanceOf[java.lang.String]
              this.data.add(new DictionaryContainer(-1, elem))
            } else if (dict.isInstanceOf[(java.lang.Integer, java.lang.String, java.lang.String)]) {
              //requestTypes
              //var elem = dict.asInstanceOf[(java.lang.Integer, java.lang.String, java.lang.String)]
              //this.data.add(new DictionaryContainer(elem._1.intValue, elem._2, elem._3))
            }
            else {
              this.data.add(new DictionaryContainer(0, ""))
            }
          }
        }
      })
    } else {
      this.data.add(new DictionaryContainer(0, ""))
    }
  }
}

@XmlType(name = "dictionaryContainer")
@XmlRootElement(name = "dictionaryContainer")
class DictionaryContainer {

  @JsonView(Array(
    classOf[DictionaryDataViews.InsuranceView],
    classOf[DictionaryDataViews.DefaultView],
    classOf[DictionaryDataViews.PolicyTypeView],
    classOf[DictionaryDataViews.ClientDocumentView],
    classOf[DictionaryDataViews.TFOMSView],
    classOf[DictionaryDataViews.RequestTypesView]
  ))
  @BeanProperty
  var id: Int = _

  @JsonView(Array(classOf[DictionaryDataViews.KLADRView], classOf[DictionaryDataViews.RequestTypesView]))
  @BeanProperty
  var code: String = _

  @JsonView(Array(classOf[DictionaryDataViews.KLADRView]))
  @BeanProperty
  var sock: String = _

  @JsonView(Array(classOf[DictionaryDataViews.KLADRView]))
  @BeanProperty
  var index: String = _

  @JsonView(Array(classOf[DictionaryDataViews.InsuranceView]))
  @BeanProperty
  var headId: Int = _

  @BeanProperty
  var value: String = _

  def this(id: Int,
           value: String) {
    this()
    this.id = id
    this.value = value
  }

  def this(id: Int,
           value: String,
           groupId: Int) {
    this(id, value)
    this.headId = groupId
  }

  def this(id: Int,
           value: String,
           code: String) {
    this()
    this.id = id
    this.value = value
    this.code = code
    this.index = "12"
  }

  def this(code: String,
           value: String,
           sock: String,
           index: String) {
    this()
    this.code = code
    this.sock = sock
    this.value = value
    this.index = index
  }

  def toSortingString(sortingField: String) = {
    val sortingFieldInternal = sortingField match {
      case "value" => {
        "r.value"
      }
      case _ => {
        "r.id"
      }
    }
    sortingFieldInternal
  }
}

@XmlType(name = "dictionaryListRequestDataFilter")
@XmlRootElement(name = "dictionaryListRequestDataFilter")
class DictionaryListRequestDataFilter extends AbstractListDataFilter {

  @BeanProperty
  var dictName: String = _

  @JsonView(Array(classOf[DictionaryDataViews.PolicyTypeView]))
  @BeanProperty
  var value: String = ""

  @JsonView(Array(classOf[DictionaryDataViews.InsuranceView], classOf[DictionaryDataViews.TFOMSView]))
  @BeanProperty
  var headId: Int = _

  @JsonView(Array(classOf[DictionaryDataViews.ClientDocumentView]))
  @BeanProperty
  var groupId: Int = _

  @JsonView(Array(classOf[DictionaryDataViews.KLADRView]))
  @BeanProperty
  var level: String = _

  @JsonView(Array(classOf[DictionaryDataViews.KLADRView]))
  @BeanProperty
  var parent: String = _

  @JsonView(Array(classOf[DictionaryDataViews.ValueDomainView]))
  @BeanProperty
  var typeIs: String = _

  @JsonView(Array(classOf[DictionaryDataViews.ValueDomainView]))
  @BeanProperty
  var capId: Int = _

  def this(dictName: String, headId: Int, groupId: Int, name: String, level: String, parent: String, aType: String, capId: Int) {
    this()
    this.headId = headId
    this.groupId = groupId
    this.dictName = dictName
    this.value = name
    this.level = level
    this.parent = parent
    this.typeIs = aType
    this.capId = capId
  }

  @Override
  def toQueryStructure() = {
    var qs = new QueryDataStructure()
    if (this.headId > 0 && dictName.compare("insurance") == 0) {
      qs.query += ("AND r.headId = :headId\n")
      qs.add("headId", this.headId: java.lang.Integer)
    }
    else if (this.groupId > 0 && dictName.compare("clientDocument") == 0) {
      qs.query += ("AND r.documentTypeGroup.id = :headId\n")
      qs.add("headId", this.groupId: java.lang.Integer)
    }
    else if (this.value != null && !this.value.isEmpty && dictName.compare("policyTypes") == 0) {
      qs.query += ("AND upper(r.name) LIKE :name\n")
      qs.add("name", this.value + "%")
    }
    else if (dictName.compare("socStatus") == 0) {
      qs.query += ("AND ssc.code = '1'\n")
    }
    else if (dictName.compare("disabilityTypes") == 0) {
      qs.query += ("AND ssc.code = '2'\n")
    }
    else if (dictName.compare("citizenships") == 0) {
      qs.query += ("AND ssc.code = '4'\n")
    }
    else if (dictName.compare("citizenships2") == 0) {
      qs.query += ("AND ssc.code = '5'\n")
    }
    else if (this.level != null && !this.level.isEmpty && dictName.compare("KLADR") == 0) {
      //Спецификация алгоритма: https://docs.google.com/document/d/15HtJdZ1OwP0TaeyWB1R0MOjluaEQcpO3U7w8g2hssgA/edit#heading=h.jwlb9dw58lw6
      val res = this.level match {
        case "republic" => {
          //республика
          "AND kl.parent = ''\n " +
            "AND socr.level = '1'\n"
        }
        case "district" => {
          //район
          "AND kl.parent = '%s'\n ".format(substringWithZeroInput(this.parent, 0, 3)) +
            "AND socr.level = '2'\n"
        }
        case "city" => {
          //город
          "AND kl.parent = '%s'\n ".format(substringWithZeroInput(this.parent, 0, 3)) +
            "AND socr.level = '3'\n" //3
        }
        case "locality" => {
          //населенный пункт
          "AND kl.parent = '%s'\n ".format(substringWithZeroInput(this.parent, 0, 5)) +
            "AND socr.level = '4'\n" //4
        }
        case "street" => {
          //улица
          "AND str.code LIKE '%s%%'\n ".format(substringWithZeroInput(this.parent, 0, 8))
        }
        case _ => ""
      }
      qs.query += res
    }
    else if (this.capId >= 0 && dictName.compare("valueDomain") == 0) {
      val res = ("AND (at.id = (SELECT cap.actionType.id FROM RbCoreActionProperty cap WHERE cap.id = '%s'))\n" +
        "AND (apt.id = (SELECT cap2.actionPropertyType.id FROM RbCoreActionProperty cap2 WHERE cap2.id = '%s'))").format(this.capId, this.capId)
      qs.query += res
    }
    qs
  }

  @Override
  def toSortingString(sortingField: String, sortingMethod: String) = {
    var sorting = sortingField match {
      case "name" => {
        if (dictName.compare("disabilityTypes") == 0 ||
          dictName.compare("citizenships") == 0 ||
          dictName.compare("citizenships2") == 0 ||
          dictName.compare("socStatus") == 0) {
          "sst.name %s"
        } else {
          "r.name %s"
        }
      }
      case _ => {
        if (dictName.compare("disabilityTypes") == 0 ||
          dictName.compare("citizenships") == 0 ||
          dictName.compare("citizenships2") == 0 ||
          dictName.compare("socStatus") == 0) {
          "sst.id %s"
        } else {
          "r.id %s"
        }
      }
    }
    sorting = "ORDER BY " + sorting.format(sortingMethod)
    sorting
  }

  private def substringWithZeroInput(code: String, pos: Int, count: Int) = {

    if (code != null && !code.isEmpty) {
      val len = code.length
      val diff = pos + count - len
      if (diff < 0) {
        code.substring(pos, pos + count)
      } else {
        var code1 = code.substring(pos)
        while (code1.length < count) code1 = code1 + "0"
        code1
      }
    }
    else
      throw new CoreException(ConfigManager.Messages("error.invalidKladrRequest").format("Для filter[level] = %s Не задан обязательный непустой параметр filter[parent]".format(this.level)))
  }
}

@XmlType(name = "departmentsDataFilter")
@XmlRootElement(name = "departmentsDataFilter")
class DepartmentsDataFilter extends AbstractListDataFilter {

  @BeanProperty
  var hasBeds: Boolean = _

  @BeanProperty
  var hasPatients: Boolean = _

  def this(hasBeds: Boolean) {
    this()
    this.hasBeds = hasBeds //if (hasBeds) 1 else 0
  }

  def this(hasBeds: Boolean,
           hasPatients: Boolean) {
    this(hasBeds)
    this.hasPatients = hasPatients
  }

  @Override
  def toQueryStructure() = {
    var qs = new QueryDataStructure()

    if (hasBeds) {
      // qs.query += ("AND os.hasHospitalBeds = :hasBeds\n")   //Старый запрос (до баги WEBMIS-793)
      //qs.add("hasBeds", this.hasBeds: java.lang.Boolean)
      qs.query += ("AND exists (SELECT  oshb.masterDepartment.id FROM OrgStructureHospitalBed oshb WHERE oshb.masterDepartment.id = os.id)") //WEBMIS-793
    }
    if (hasPatients) {
      val res = """ AND exists(
          SELECT e
          FROM
            Event e,
            Action a,
            ActionProperty ap,
            APValueHospitalBed bed,
            OrgStructureHospitalBed org
          WHERE
            e.execDate is NULL AND
            e.id = a.event.id AND
            a.id = ap.action.id AND
            ap.id = bed.id.id AND
            bed.value.id = org.id AND
            org.masterDepartment.id = os.id
          AND
            a.actionType.id IN
            (
              SELECT actionType.id
              FROM
                ActionType actionType
              WHERE
                actionType.flatCode = '%s'
            )
          AND
            e.id NOT IN
            (
              SELECT leaved.event.id
              FROM
                Action leaved
              WHERE
                leaved.actionType.id IN
                (
                  SELECT at.id
                  FROM
                    ActionType at
                  WHERE
                    at.flatCode = '%s'
                )
              AND
                leaved.event.id = e.id
            )
          AND
            e.deleted = 0)
                """.format(ConfigManager.Messages("db.action.movingFlatCode"), ConfigManager.Messages("db.action.leavingFlatCode"))
      qs.query += res
    }
    qs
  }

  @Override
  def toSortingString(sortingField: String, sortingMethod: String) = {
    var sorting = sortingField match {
      case "name" => {
        "os.name %s"
      }
      case _ => {
        "os.id %s"
      }
    }
    sorting = "ORDER BY " + sorting.format(sortingMethod)
    sorting
  }
}

@XmlType(name = "trueFalseContainer")
@XmlRootElement(name = "trueFalseContainer")
class TrueFalseContainer {
  @BeanProperty
  var trueFalse: java.lang.Boolean = _

  def this(boolParam: java.lang.Boolean) {
    this()
    this.trueFalse = if (boolParam != null) {
      boolParam
    } else {
      false
    }
  }
}

@XmlType(name = "eventTypesListRequestDataFilter")
@XmlRootElement(name = "eventTypesListRequestDataFilter")
class EventTypesListRequestDataFilter extends AbstractListDataFilter {

  @BeanProperty
  var financeId: Int = _

  @BeanProperty
  var requestTypeId: Int = _

  def this(financeId: Int,
           requestTypeId: Int) {
    this()
    this.financeId = financeId
    this.requestTypeId = requestTypeId
  }

  @Override
  def toQueryStructure() = {
    var qs = new QueryDataStructure()

    if (this.financeId > 0) {
      qs.query += ("AND et.finance.id = :financeId\n")
      qs.add("financeId", this.financeId: java.lang.Integer)
    }
    if (this.requestTypeId > 0) {
      qs.query += ("AND et.requestType.id = :requestTypeId\n")
      qs.add("requestTypeId", this.requestTypeId: java.lang.Integer)
    }
    qs
  }

  @Override
  def toSortingString(sortingField: String, sortingMethod: String) = {
    var sorting = sortingField match {
      case "name" => {
        "et.name %s"
      }
      case _ => {
        "et.id %s"
      }
    }
    sorting = "ORDER BY " + sorting.format(sortingMethod)
    sorting
  }
}

@XmlType(name = "eventTypesListData")
@XmlRootElement(name = "eventTypesListData")
class EventTypesListData {

  @BeanProperty
  var requestData: ListDataRequest = _
  @BeanProperty
  var data: ArrayList[DictionaryContainer] = new ArrayList[DictionaryContainer]

  def this(requestData: ListDataRequest) {
    this()
    this.requestData = requestData
  }

  def this(types: java.util.List[EventType], requestData: ListDataRequest) {
    this(requestData)
    types.foreach(eType => this.data.add(new DictionaryContainer(eType.getId.intValue(), eType.getName)))
  }
}

@XmlType(name = "groupTypesListData")
@XmlRootElement(name = "groupTypesListData")
class GroupTypesListData {

  @BeanProperty
  var requestData: ListDataRequest = _

  @BeanProperty
  var data: AnyRef = _


  def this(quotaTypes: java.util.List[QuotaType],
           requestData: ListDataRequest) = {
    this()
    this.requestData = requestData

    if ((requestData.getFilter.isInstanceOf[QuotaTypesListRequestDataFilter]) &&
      (requestData.getFilter.asInstanceOf[QuotaTypesListRequestDataFilter].getId > 0 ||
        requestData.getFilter.asInstanceOf[QuotaTypesListRequestDataFilter].getCode != null &&
          requestData.getFilter.asInstanceOf[QuotaTypesListRequestDataFilter].getCode.compareTo("") != 0 ||
        requestData.getFilter.asInstanceOf[QuotaTypesListRequestDataFilter].getGroupCode != null &&
          requestData.getFilter.asInstanceOf[QuotaTypesListRequestDataFilter].getGroupCode.compareTo("") != 0)) {
      //this.requestData.setRecordsCount(classMap.size)
      this.data = new java.util.LinkedList[QuotaTypeContainer]
      quotaTypes.foreach(f => this.data.asInstanceOf[java.util.LinkedList[QuotaTypeContainer]].add(new QuotaTypeContainer(f)))
    } else {
      this.data = new java.util.LinkedList[GroupQuotaTypeContainer]
      quotaTypes.foreach(f => {
        if (f.getGroupCode == null) {
          val temp = new GroupQuotaTypeContainer(f, quotaTypes)
          this.data.asInstanceOf[java.util.LinkedList[GroupQuotaTypeContainer]].add(temp)
        }
      })
    }
  }
}

@XmlType(name = "groupQuotaTypeContainer")
@XmlRootElement(name = "groupQuotaTypeContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class GroupQuotaTypeContainer {

  @BeanProperty
  var id: Int = _

  @BeanProperty
  var code: String = _

  @BeanProperty
  var types: java.util.List[QuotaTypeContainer] = new java.util.LinkedList[QuotaTypeContainer]

  def this(groupQuotaType: QuotaType,
           quotaTypes: java.util.List[QuotaType]) {
    this()
    this.id = groupQuotaType.getId.intValue()
    this.code = groupQuotaType.getCode
    quotaTypes.foreach(f => {
      if (f.getGroupCode != null && f.getGroupCode.compareTo(groupQuotaType.getCode) == 0) {
        this.types.add(new QuotaTypeContainer(f))
      }
    })
  }
}

@XmlType(name = "quotaTypeContainer")
@XmlRootElement(name = "quotaTypeContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class QuotaTypeContainer {

  @BeanProperty
  var id: Int = _

  @BeanProperty
  var code: String = _

  @BeanProperty
  var name: String = _

  def this(id: Int, code: String, name: String) {
    this()
    this.id = id
    this.code = code
    this.name = name
  }

  def this(quotaType: QuotaType) {
    this()
    this.id = quotaType.getId.intValue()
    this.code = quotaType.getCode
    this.name = quotaType.getName
  }
}

@XmlType(name = "quotaTypesListRequestDataFilter")
@XmlRootElement(name = "quotaTypesListRequestDataFilter")
class QuotaTypesListRequestDataFilter extends AbstractListDataFilter {
  @BeanProperty
  var id: Int = _

  @BeanProperty
  var code: String = _

  @BeanProperty
  var groupCode: String = _

  def this(id_x: Int,
           code_x: String,
           groupCode_x: String) {
    this()
    this.id = id_x
    this.code = if (code_x != null && code_x != "") code_x else ""
    this.groupCode = if (groupCode_x != null && groupCode_x != "") groupCode_x else ""
  }

  @Override
  def toQueryStructure() = {
    var qs = new QueryDataStructure()
    qs.query += ("WHERE r.deleted = 0\n")

    if (this.id > 0) {
      qs.query += ("AND r.id = :id\n")
      qs.add("id", this.id: java.lang.Integer)
    }
    else if (this.code != null && !this.code.isEmpty) {
      qs.query += ("AND r.code = :code\n")
      qs.add("code", this.code)
    }
    else if (this.groupCode != null && !this.groupCode.isEmpty) {
      qs.query += ("AND r.groupCode =  :groupCode\n")
      qs.add("groupCode", this.groupCode)
    }
    qs
  }

  @Override
  def toSortingString(sortingField: String, sortingMethod: String) = {
    var sorting = sortingField match {
      case "code" => {
        "r.code %s"
      }
      case _ => {
        "r.id %s"
      }
    }
    sorting = "ORDER BY " + sorting.format(sortingMethod)
    sorting
  }
}

@XmlType(name = "defaultListDataFilter")
@XmlRootElement(name = "defaultListDataFilter")
class DefaultListDataFilter extends AbstractListDataFilter {

  @Override
  def toQueryStructure() = new QueryDataStructure()

  @Override
  def toSortingString(sortingField: String, sortingMethod: String) = ""
}