package ru.korus.tmis.core.data

import reflect.BeanProperty
import javax.xml.bind.annotation.{XmlRootElement, XmlType}
import scala.collection.JavaConversions._
import ru.korus.tmis.util.ConfigManager
import java.util.{Calendar, TimeZone, Date, ArrayList}
import java.text.{DateFormat, SimpleDateFormat}
import scala.Predef._
import ru.korus.tmis.core.entity.model._
import org.codehaus.jackson.annotate.JsonIgnoreProperties
import org.codehaus.jackson.annotate.JsonIgnoreProperties._
import org.codehaus.jackson.map.annotate.JsonView
import ru.korus.tmis.auxiliary.AuxiliaryFunctions
import java.util

@XmlType(name = "listRequestData")
@XmlRootElement(name = "listRequestData")
class ListDataRequest {
  @BeanProperty
  var filter:  AnyRef = _
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
           filter: AnyRef) = {
    this()
    this.filter = if(filter!=null) {filter} else {null}
    this.sortingField = sortingField match {
      case null => {"id"}
      case _ => {sortingField}
    }

    this.sortingFieldInternal =
      if(this.filter.isInstanceOf[MKBListRequestDataFilter]) {
        this.filter.asInstanceOf[MKBListRequestDataFilter].toSortingString(this.sortingField)
      }
      else if (this.filter.isInstanceOf[ThesaurusListRequestDataFilter]) {
        this.filter.asInstanceOf[ThesaurusListRequestDataFilter].toSortingString(this.sortingField)
      }
      else if (this.filter.isInstanceOf[DictionaryListRequestDataFilter]) {
        this.filter.asInstanceOf[DictionaryListRequestDataFilter].toSortingString(this.sortingField)
      }
      else if (this.filter.isInstanceOf[ActionTypesListRequestDataFilter]) {
        this.filter.asInstanceOf[ActionTypesListRequestDataFilter].toSortingString(this.sortingField)
      }
      else if (this.filter.isInstanceOf[DepartmentsDataFilter]) {
        this.filter.asInstanceOf[DepartmentsDataFilter].toSortingString(this.sortingField)
      }
      else if (this.filter.isInstanceOf[EventTypesListRequestDataFilter]) {
        this.filter.asInstanceOf[EventTypesListRequestDataFilter].toSortingString(this.sortingField)
      }
      else {
        this.sortingField
      }

    this.sortingMethod = sortingMethod match {
      case null => {"asc"}
      case _ => {sortingMethod}
    }
    this.limit = limit
    this.page = if(page>1)page else 1
    this.coreVersion = ConfigManager.Messages("misCore.assembly.version")
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
    this ()
    this.requestData = requestData
    persons.foreach(p => this.data.add(new DoctorContainer(p)))
  }

}

@XmlType(name = "freePersonsListDataFilter")
@XmlRootElement(name = "freePersonsListDataFilter")
class FreePersonsListDataFilter {
  @BeanProperty
  var speciality:  Int = _
  @BeanProperty
  var doctorId:  Int = _
  @BeanProperty
  var beginDate: Date = _
  @BeanProperty
  var endDate: Date = _

  var beginOnlyDate: Date = _
  var beginOnlyTime: Date = _
  var endOnlyTime: Date = _

  def this(speciality:  Int, doctorId:  Int, beginDate: Long, endDate: Long){
    this()
    this.speciality = speciality
    this.doctorId = doctorId
    this.beginDate = if(beginDate==0) {null} else {new Date(beginDate)}
    this.endDate = if(endDate==0) {null} else {new Date(endDate)}

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

  def toQueryStructure() = {
    var qs = new QueryDataStructure()
    if(this.speciality>0){
      qs.query += "AND s.speciality.id = :speciality\n"
      qs.add("speciality",this.speciality:java.lang.Integer)
    }
    if(this.doctorId>0){
      qs.query += ("AND s.id = :doctorId\n")
      qs.add("doctorId",this.doctorId:java.lang.Integer)
    }
    if(this.beginOnlyDate!=null){
      qs.query += "AND e.setDate = :beginOnlyDate\n"
      qs.add("beginOnlyDate",this.beginOnlyDate)
    }
    qs
  }
}

@XmlType(name = "allDepartmentsListData")
@XmlRootElement(name = "allDepartmentsListData")
class AllDepartmentsListData {

  @BeanProperty
  var requestData: ListDataRequest = _
  @BeanProperty
  var data: ArrayList[IdNameContainer] = new ArrayList[IdNameContainer]

  def this(departments: java.util.List[OrgStructure], requestData: ListDataRequest) = {
    this ()
    this.requestData = requestData
    departments.foreach(org => this.data.add(new IdNameContainer(org.getId.intValue(), org.getName)))
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

  def this(actionTypes: java.util.List[ActionType], requestData: ListDataRequest) = {
    this ()
    this.requestData = requestData
    actionTypes.foreach(at => this.data.add(new ActionTypesListEntry(at)))
  }
}

@XmlType(name = "actionTypesListRequestDataFilter")
@XmlRootElement(name = "actionTypesListRequestDataFilter")
class ActionTypesListRequestDataFilter {

  @BeanProperty
  var code: String = _

  @BeanProperty
  var groupId: Int = _

  def this(code_x: String,
           groupId: Int,
           diaType_x: String) {
    this()
    this.code = if(code_x!=null && code_x!="") {
                  code_x
                }
                else {
                        diaType_x match {
                                            case "laboratory" => {"2"}
                                            case "instrumental" => {"3"}
                                            case _ => {""}
                                        }
                }
    this.groupId = groupId
  }

  def toQueryStructure() = {
    var qs = new QueryDataStructure()

    if(this.groupId>0){
      qs.query += ("AND at.groupId =  :groupId\n")
      qs.add("groupId", this.groupId:java.lang.Integer)
    }
    else  {
      if(this.code!=null && !this.code.isEmpty){
        qs.query += ("AND at.groupId IN (SELECT at2.id FROM ActionType at2 WHERE at2.code = :code)\n")
        qs.add("code",this.code)
      }
    }
    qs
  }

  def toSortingString (sortingField: String) = {
    sortingField match {
      case "groupId" => {"at.groupId"}
      case "code" => {"at.code"}
      case "name" => {"at.name"}
      case _ => {"at.id"}
    }
  }
}

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
  var name: String = _

  //@BeanProperty
  //var childrenCount: Long = _

  def this(actionType: ActionType) {
    this()
    this.id = actionType.getId.intValue()
    this.groupId = if(actionType.getGroupId!=null) {actionType.getGroupId.intValue()} else{0}
    this.code = actionType.getCode
    this.name = actionType.getName
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
  var data: AnyRef = _//java.util.LinkedList[ClassMKBContainer] = new java.util.LinkedList[ClassMKBContainer]


  def this(mkbs: java.util.List[Mkb],
           mkbs_display: Object,
           requestData: ListDataRequest) = {
   this ()
   this.requestData = requestData

   //Анализ формы вывода
   requestData.filter.asInstanceOf[MKBListRequestDataFilter].view match {
     case "class" => {
       val classMap = getGroupedValuesByLevel(mkbs, CLASS_LEVEL)
       this.requestData.setRecordsCount(classMap.size)
       this.data = new java.util.LinkedList[ClassMKBContainer]
       classMap.foreach(f => this.data.asInstanceOf[java.util.LinkedList[ClassMKBContainer]].add(new ClassMKBContainer(f._1, f._2.getClassName)))
     }
     case "group" => {
       val groupMap = getGroupedValuesByLevel(mkbs, GROUP_LEVEL)
       this.requestData.setRecordsCount(groupMap.size)
       this.data = new java.util.LinkedList[GroupMKBContainer]
       groupMap.foreach(f => this.data.asInstanceOf[java.util.LinkedList[GroupMKBContainer]].add(new GroupMKBContainer(f._1, f._2.getBlockName)))
     }
     case "subgroup" => {
       val subgroupMap = getGroupedValuesByLevel(mkbs, SUBGROUP_LEVEL)
       this.requestData.setRecordsCount(subgroupMap.size)
       this.data = new java.util.LinkedList[SubGroupMKBContainer]
       subgroupMap.foreach(f => this.data.asInstanceOf[java.util.LinkedList[SubGroupMKBContainer]].add(new SubGroupMKBContainer(f._1, f._2.getDiagName)))
     }
     case "mkb" => {
       val mkbMap = getGroupedValuesByLevel(mkbs, MKB_LEVEL)
       this.requestData.setRecordsCount(mkbMap.size)
       this.data = new java.util.LinkedList[MKBContainer]
       mkbMap.foreach(f => this.data.asInstanceOf[java.util.LinkedList[MKBContainer]].add(new MKBContainer(f._2)))
     }
     case _ => {  //дерево тогда анализируем дисплей
       this.data = new java.util.LinkedList[ClassMKBContainer]
       val classMap = getGroupedValuesByLevel(mkbs, CLASS_LEVEL)

       if (requestData.filter.asInstanceOf[MKBListRequestDataFilter].display) {
         val rolled = getRolledBrunch(mkbs_display.asInstanceOf[java.util.Map[String, java.util.Map[String, Mkb]]], CLASS_LEVEL)
         if (rolled!=null){
           rolled.foreach(f => {
             if (!classMap.containsKey(f._1)){
               this.data.asInstanceOf[java.util.LinkedList[ClassMKBContainer]]
                 .add(new ClassMKBContainer(f._1, f._2.getClassName))
             }
             else {
               val filtredMkbs = getFilteredValuesByLevel(mkbs, classMap.get(f._1), CLASS_LEVEL)
               this.data.asInstanceOf[java.util.LinkedList[ClassMKBContainer]]
                 .add(new ClassMKBContainer( f._1,
                                             f._2.getClassName,
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
             val filtredMkbs = getFilteredValuesByLevel(mkbs, f._2, CLASS_LEVEL)
             this.data.asInstanceOf[java.util.LinkedList[ClassMKBContainer]]
               .add(new ClassMKBContainer(f._1, f._2.getClassName, filtredMkbs, null, getGroupedValuesByLevel _, getFilteredValuesByLevel _, null))
           })
         }
       }
       else {
         classMap.foreach(f => {
           val filtredMkbs = getFilteredValuesByLevel(mkbs, f._2, CLASS_LEVEL)
           this.data.asInstanceOf[java.util.LinkedList[ClassMKBContainer]]
                    .add(new ClassMKBContainer(f._1, f._2.getClassName, filtredMkbs, null, getGroupedValuesByLevel _, getFilteredValuesByLevel _, null))
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
       if ((level == SUBGROUP_LEVEL && f.getDiagID.indexOf(".")<=0) ||
           (level != SUBGROUP_LEVEL)){
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
      if((levelConstruct(p, level) == levelConstruct(compared, level) && (level != SUBGROUP_LEVEL)) ||
         ((level == SUBGROUP_LEVEL)&&(levelConstruct(p, level).contains(levelConstruct(compared, level))) && (levelConstruct(p, level) != levelConstruct(compared, level)))) {
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
   if(mkbs_display!= null && mkbs_display.size()>0 && mkbs_display.containsKey(levelRolledBrunchesKey(level))) {
     val mkbs = mkbs_display.get(levelRolledBrunchesKey(level))
     if (mkbs.size()>0)
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
}

@XmlType(name = "mkbListRequestDataFilter")
@XmlRootElement(name = "mkbListRequestDataFilter")
class MKBListRequestDataFilter {

  @BeanProperty
  var mkbId: Int = _

  @BeanProperty
  var classId: String = _

  @BeanProperty
  var groupId: String = _

  @BeanProperty
  var code: String = _      //diagId

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
    if (view!=null && !view.isEmpty){
      this.view = view
    }
    this.display = flgDisplay
    this.sex = sex
  }

  def toQueryStructure() = {
    var qs = new QueryDataStructure()

    if(this.mkbId>0){
      qs.query += ("AND mkb.id = :id\n")
      qs.add("id", this.mkbId:java.lang.Integer)
    }
    else {
      if(this.code!=null && !this.code.isEmpty){
        qs.query += ("AND upper(mkb.diagID) LIKE upper(:code)\n")
        qs.add("code",this.code+"%")
      } else {
        if(this.groupId!=null && !this.groupId.isEmpty){
          qs.query += ("AND upper(mkb.blockID) = upper(:blockId)\n")
          qs.add("blockId",this.groupId)
        } else {
          if(this.classId!=null && !this.classId.isEmpty){
            qs.query += ("AND upper(mkb.classID) = upper(:classId)\n")
            qs.add("classId",this.classId)
          }
        }
      }
      if(this.diagnosis!=null && !this.diagnosis.isEmpty){
        qs.query += ("AND upper(mkb.diagName) LIKE upper(:diagnosis)\n")
        qs.add("diagnosis","%" + this.diagnosis + "%")
      }
    }
    if(this.sex>0){
      qs.query += ("AND (mkb.sex = :sex OR mkb.sex = '0')\n")
      qs.add("sex", this.sex:java.lang.Integer)
    }
    qs
  }

  def toSortingString (sortingField: String) = {
    val sortingFieldInternal = sortingField match {
      case "classId" => {"mkb.classID"}
      case "blockId" => {"mkb.blockID"}
      case "code" => {"mkb.diagID"}
      case "diagnosis" => {"mkb.diagName"}
      case _ => {"mkb.id"}
    }
    sortingFieldInternal
  }
}

@XmlType(name = "classMkbContainer")
@XmlRootElement(name = "classMkbContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class ClassMKBContainer {

  @BeanProperty
  var id: String = _

  @BeanProperty
  var code: String = _

  @JsonView(Array(classOf[AllMKBListDataViews.DefaultView]))
  @BeanProperty
  var groups: java.util.LinkedList[GroupMKBContainer] = new java.util.LinkedList[GroupMKBContainer]

  def this (id: String, code: String){
    this()
    this.id = id
    this.code = code
  }

  def this (id: String,
            code: String,
            mkbs: java.util.List[Mkb],
            mkbs_display: java.util.Map[String, java.util.Map[String, Mkb]],
            mGroupedValuesByLevel: (java.util.List[Mkb], Int) => java.util.LinkedHashMap[String, Mkb],
            mFilteredValuesByLevel: (java.util.List[Mkb], Mkb, Int) => java.util.LinkedList[Mkb],
            mRolledBrunch: (java.util.Map[String, java.util.Map[String, Mkb]], Int) => java.util.Map[String, Mkb]){
    this(id, code)

    if (mkbs!=null && mGroupedValuesByLevel!=null && mFilteredValuesByLevel!= null) {
      val groupMap = mGroupedValuesByLevel(mkbs, LEVEL)
      if (mkbs_display!=null && mRolledBrunch!=null) {
        val rolled = mRolledBrunch(mkbs_display, LEVEL)
        if (rolled!=null){
          rolled.foreach(f => {
            if (!groupMap.containsKey(f._1)){
              this.groups.add(new GroupMKBContainer(f._1, f._2.getBlockName))
            }
            else {
              val filtredMkbs = mFilteredValuesByLevel(mkbs, groupMap.get(f._1), LEVEL)
              this.groups.add(new GroupMKBContainer( f._1,
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
            val filtredMkbs = mFilteredValuesByLevel(mkbs, f._2, LEVEL)
            this.groups.add(new GroupMKBContainer(f._1, f._2.getBlockName, filtredMkbs, null, mGroupedValuesByLevel, mFilteredValuesByLevel, null))
          })
        }
      }
      else {
        groupMap.foreach(f => {
          val filtredMkbs = mFilteredValuesByLevel(mkbs, f._2, LEVEL)
          this.groups.add(new GroupMKBContainer(f._1, f._2.getBlockName, filtredMkbs, null, mGroupedValuesByLevel, mFilteredValuesByLevel, null))
        })
      }
    }
  }

  private val LEVEL = 1
}

@XmlType(name = "groupMkbContainer")
@XmlRootElement(name = "groupMkbContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class GroupMKBContainer {

  @BeanProperty
  var id: String = _

  @BeanProperty
  var code: String = _

  @JsonView(Array(classOf[AllMKBListDataViews.DefaultView]))
  @BeanProperty
  var subGroups: java.util.LinkedList[SubGroupMKBContainer] = new java.util.LinkedList[SubGroupMKBContainer]

  def this (id: String, code: String){
    this()
    this.id = id
    this.code = code
  }

  def this (id: String,
            code: String,
            mkbs: java.util.LinkedList[Mkb],
            mkbs_display: java.util.Map[String, java.util.Map[String, Mkb]],
            mGroupedValuesByLevel: (java.util.List[Mkb], Int) => java.util.LinkedHashMap[String, Mkb],
            mFilteredValuesByLevel: (java.util.List[Mkb], Mkb, Int) => java.util.LinkedList[Mkb],
            mRolledBrunch: (java.util.Map[String, java.util.Map[String, Mkb]], Int) => java.util.Map[String, Mkb]){
    this(id, code)

    if (mkbs!=null && mGroupedValuesByLevel!=null && mFilteredValuesByLevel!= null) {
      val subgroupMap = mGroupedValuesByLevel(mkbs, LEVEL)
      if (mkbs_display!=null && mRolledBrunch!=null) {
        val rolled = mRolledBrunch(mkbs_display, LEVEL)
        if (rolled!=null){
          rolled.foreach(f => {
            if (!subgroupMap.containsKey(f._1)){
              this.subGroups.add(new SubGroupMKBContainer(f._1, f._2.getDiagName))
            }
            else {
              val filtredMkbs = mFilteredValuesByLevel(mkbs, subgroupMap.get(f._1), LEVEL)
              this.subGroups.add(new SubGroupMKBContainer( f._1,
                                                 f._2.getDiagName,
                                                 filtredMkbs,
                                                 mGroupedValuesByLevel
                                                 ))
            }
          })
        }
        else {
          subgroupMap.foreach(f => {
            val filtredMkbs = mFilteredValuesByLevel(mkbs, f._2, LEVEL)
            this.subGroups.add(new SubGroupMKBContainer(f._1, f._2.getDiagName, filtredMkbs, mGroupedValuesByLevel))
          })
        }
      }
      else {
        subgroupMap.foreach(f => {
          val filtredMkbs = mFilteredValuesByLevel(mkbs, f._2, LEVEL)
          this.subGroups.add(new SubGroupMKBContainer(f._1, f._2.getDiagName, filtredMkbs, mGroupedValuesByLevel))
        })
      }
    }
  }

  private val LEVEL = 2
}

@XmlType(name = "subGroupMkbContainer")
@XmlRootElement(name = "subGroupMkbContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class SubGroupMKBContainer {

  @BeanProperty
  var id: String = _

  @BeanProperty
  var code: String = _

  @JsonView(Array(classOf[AllMKBListDataViews.DefaultView]))
  @BeanProperty
  var mkbs: java.util.LinkedList[MKBContainer] = new java.util.LinkedList[MKBContainer]

  def this(mkbSubGroup: (String, java.util.List[Mkb]),
           mkbs_display: Object,
           subGroupTitleValue: java.util.Map[String, String]){
    this()
    this.id = mkbSubGroup._1
    this.code = subGroupTitleValue.get(mkbSubGroup._1)
    if (mkbSubGroup._2!=null && mkbSubGroup._2.size()>0) {
      mkbSubGroup._2.foreach(mkb =>this.mkbs.add(new MKBContainer(mkb)))
    }
  }

  def this (id: String, code: String){
    this()
    this.id = id
    this.code = code
  }

  def this (id: String,
            code: String,
            mkbs: java.util.LinkedList[Mkb],
            mGroupedValuesByLevel: (java.util.List[Mkb], Int) => java.util.LinkedHashMap[String, Mkb]) {
    this(id, code)

    val mkbMap = mGroupedValuesByLevel(mkbs, LEVEL)
    mkbMap.foreach(f => this.mkbs.add(new MKBContainer(f._2)))
  }

  private val LEVEL = 3
}

@XmlType(name = "thesaurusListData")
@XmlRootElement(name = "thesaurusListData")
class ThesaurusListData {

  @BeanProperty
  var requestData: ListDataRequest = _
  @BeanProperty
  var data: ArrayList[ThesaurusContainer] = new ArrayList[ThesaurusContainer]

  def this(thesaurus: java.util.List[Thesaurus], requestData: ListDataRequest) = {
    this ()
    this.requestData = requestData
    thesaurus.foreach(thes => this.data.add(new ThesaurusContainer(thes)))
  }
}

@XmlType(name = "thesaurusListRequestDataFilter")
@XmlRootElement(name = "thesaurusListRequestDataFilter")
class ThesaurusListRequestDataFilter {
  @BeanProperty
  var id: Int = _

  @BeanProperty
  var groupId: String = _

  @BeanProperty
  var code: String = _      //

  def this(id: Int,
           groupId: String,
           code: String) {
    this()
    this.id = id
    this.groupId = groupId
    this.code = code
  }

  def toQueryStructure() = {
    var qs = new QueryDataStructure()

    if(this.id>0){
      qs.query += ("AND r.id = :id\n")
      qs.add("id", this.id:java.lang.Integer)
    }
    else {
      if(this.groupId!=null && !this.groupId.isEmpty){
        qs.query += ("AND upper(r.groupId) = upper(:groupId)\n")
        qs.add("groupId",this.groupId)
      }
      if(this.code!=null && !this.code.isEmpty){
        qs.query += ("AND upper(r.code) LIKE upper(:code)\n")
        qs.add("code",this.code+"%")
      }
    }
    qs
  }

  def toSortingString (sortingField: String) = {
    val sortingFieldInternal = sortingField match {
      case "groupId" => {"r.groupID"}
      case "code" => {"r.code"}
      case _ => {"r.id"}
    }
    sortingFieldInternal
  }
}

object DictionaryDataViews {
  class InsuranceView {}
  class DefaultView {}
  class PolicyTypeView {}
  class ClientDocumentView {}
  class TFOMSView{}
  class KLADRView {}
  class ValueDomainView {}
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
    this ()
    this.requestData = requestData
  }

  def this(types: java.util.List[AnyRef], requestData: ListDataRequest) = {
    this (requestData)
    if (types != null) {
      types.foreach(dict => {

        this.requestData.filter.asInstanceOf[DictionaryListRequestDataFilter].dictName match {
          case "KLADR" => {
            if(dict.isInstanceOf[(java.lang.String, java.lang.String, java.lang.String, java.lang.String)]) {     //KLADR
              var elem = dict.asInstanceOf[(java.lang.String, java.lang.String, java.lang.String, java.lang.String)]
              this.data.add(new DictionaryContainer(elem._1, elem._2, elem._3, elem._4))
            } else {
              this.data.add(new DictionaryContainer(0, ""))
            }
          }
          case _ => {
            if(dict.isInstanceOf[(java.lang.Integer, java.lang.String)]){
              var elem = dict.asInstanceOf[(java.lang.Integer, java.lang.String)]
              this.data.add(new DictionaryContainer(elem._1.intValue(),
                elem._2))
            } else if(dict.isInstanceOf[(java.lang.Integer, java.lang.String, java.lang.Integer)]) {  //Insurance
              var elem = dict.asInstanceOf[(java.lang.Integer, java.lang.String, java.lang.Integer)]
              this.data.add(new DictionaryContainer(elem._1.intValue(),
                elem._2, elem._3.intValue()))
            } else if(dict.isInstanceOf[java.lang.String]) {
              var elem = dict.asInstanceOf[java.lang.String]
              this.data.add(new DictionaryContainer(-1, elem))
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
    classOf[DictionaryDataViews.TFOMSView]
  ))
  @BeanProperty
  var id: Int = _

  @JsonView(Array(classOf[DictionaryDataViews.KLADRView]))
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

  def toSortingString (sortingField: String) = {
    val sortingFieldInternal = sortingField match {
      case "value" => {"r.value"}
      case _ => {"r.id"}
    }
    sortingFieldInternal
  }
}

@XmlType(name = "dictionaryListRequestDataFilter")
@XmlRootElement(name = "dictionaryListRequestDataFilter")
class DictionaryListRequestDataFilter {

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

  def this(dictName: String, headId: Int, groupId: Int,  name: String,  level: String,  parent: String, aType: String, capId: Int) {
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

  def toQueryStructure() = {
    var qs = new QueryDataStructure()
    if(this.headId>0 && dictName.compare("insurance") == 0){
      qs.query += ("AND r.headId = :headId\n")
      qs.add("headId", this.headId:java.lang.Integer)
    }
    else if(this.groupId>0 && dictName.compare("clientDocument") == 0){
      qs.query += ("AND r.documentTypeGroup.id = :headId\n")
      qs.add("headId", this.groupId:java.lang.Integer)
    }
    else if(this.value!=null && !this.value.isEmpty && dictName.compare("policyTypes") == 0){
      qs.query += ("AND upper(r.name) LIKE :name\n")
      qs.add("name", this.value + "%")
    }
    else if(dictName.compare("socStatus") == 0){
      qs.query += ("AND ssc.code = '1'\n")
    }
    else if(dictName.compare("disabilityTypes") == 0){
      qs.query += ("AND ssc.code = '2'\n")
    }
    else if(dictName.compare("citizenships") == 0){
      qs.query += ("AND ssc.code = '4'\n")
    }
    else if(dictName.compare("citizenships2") == 0){
      qs.query += ("AND ssc.code = '5'\n")
    }
    else if(this.level!=null && !this.level.isEmpty && dictName.compare("KLADR") == 0){

      var isParent = 0

      val res =this.level match {
        case "republic" => {
          isParent = 2
          "AND socr.level = '1'\n"}       //республика
        case "district" => {"AND socr.level = '2'\n"}       //район
        case "city" => {"AND socr.level = '3'\n"}           //город
        case "locality" => {"AND socr.level = '4'\n"}       //населенный пункт
        case "street" => {isParent = 1
          ""}       //улица
        case _ => {
          isParent = 3
          ""}
      }
      qs.query += res

      val res2 =isParent match {
        case 0 => {if(this.parent!=null && !this.parent.isEmpty ){
          var lexem = AuxiliaryFunctions.trimRight(this.parent, '0')
          while(lexem.size<3){lexem = lexem + "0"}
          "AND kl.parent = '%s'".format(lexem)
          } else {""}
        }
        case 1 => {if(this.parent!=null && !this.parent.isEmpty ){
          "AND str.index IN (SELECT kl.index FROM Kladr kl WHERE kl.code = '%s')".format(this.parent)
        } else {""}}
        case 2 => {"AND kl.parent = ''"}
        case _ => {""}
      }
      qs.query += res2
    }
    else if(this.capId>=0 && dictName.compare("valueDomain") == 0){
      val res = ("AND (at.id = (SELECT cap.actionType.id FROM RbCoreActionProperty cap WHERE cap.id = '%s'))\n" +
                 "AND (apt.id = (SELECT cap2.actionPropertyType.id FROM RbCoreActionProperty cap2 WHERE cap2.id = '%s'))").format(this.capId, this.capId)
      qs.query += res
    }
    qs
  }

  def toSortingString (sortingField: String) = {
    val sortingFieldInternal = sortingField match {
      case "name" => {if(dictName.compare("disabilityTypes") == 0 ||
                         dictName.compare("citizenships") == 0 ||
                         dictName.compare("citizenships2") == 0 ||
                         dictName.compare("socStatus") == 0) {"sst.name"} else{"r.name"}}
      case _ => {if(dictName.compare("disabilityTypes") == 0 ||
                    dictName.compare("citizenships") == 0 ||
                    dictName.compare("citizenships2") == 0 ||
                    dictName.compare("socStatus") == 0) {"sst.id"} else{"r.id"}}
    }
    sortingFieldInternal
  }
}

@XmlType(name = "mkbListRequestDataFilter")
@XmlRootElement(name = "mkbListRequestDataFilter")
class DepartmentsDataFilter {

  @BeanProperty
  var hasBeds: Boolean = _

  def this(hasBeds: Boolean) {
    this()
    this.hasBeds = hasBeds//if (hasBeds) 1 else 0
  }

  def toQueryStructure() = {
    var qs = new QueryDataStructure()

    if(hasBeds){
      qs.query += ("AND os.hasHospitalBeds = :hasBeds\n")
      qs.add("hasBeds", this.hasBeds: java.lang.Boolean)
    }
    qs
  }

  def toSortingString (sortingField: String) = {
    val sortingFieldInternal = sortingField match {
      case "name" => {"os.name"}
      case _ => {"os.id"}
    }
    sortingFieldInternal
  }
}

@XmlType(name = "trueFalseContainer")
@XmlRootElement(name = "trueFalseContainer")
class TrueFalseContainer {
  @BeanProperty
  var trueFalse: java.lang.Boolean = _

  def this(boolParam: java.lang.Boolean) {
    this()
    this.trueFalse = if (boolParam != null) {boolParam} else {false}
  }
}

@XmlType(name = "eventTypesListRequestDataFilter")
@XmlRootElement(name = "eventTypesListRequestDataFilter")
class EventTypesListRequestDataFilter {

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

  def toQueryStructure() = {
    var qs = new QueryDataStructure()

    if(this.financeId>0){
      qs.query += ("AND et.finance.id = :financeId\n")
      qs.add("financeId", this.financeId:java.lang.Integer)
    }
    if(this.requestTypeId>0){
        qs.query += ("AND et.requestType.id = :requestTypeId\n")
        qs.add("requestTypeId",this.requestTypeId:java.lang.Integer)
    }
    qs
  }

  def toSortingString (sortingField: String) = {
    sortingField match {
      case "name" => {"et.name"}
      case _ => {"et.id"}
    }
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
    this ()
    this.requestData = requestData
  }

  def this(types: java.util.List[EventType], requestData: ListDataRequest) {
    this (requestData)
    types.foreach(eType => this.data.add(new DictionaryContainer(eType.getId.intValue(), eType.getName)))
  }
}