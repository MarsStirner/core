package ru.korus.tmis.core.data

import javax.xml.bind.annotation.{XmlType, XmlRootElement}
import reflect.BeanProperty
import scala.collection.JavaConversions._
import java.util.{Calendar, Date, LinkedList}
import ru.korus.tmis.core.entity.model._
import ru.korus.tmis.util.ConfigManager
import org.codehaus.jackson.map.annotate.JsonView
import collection.JavaConversions
import collection.immutable.ListMap
import java.util

object PatientsListDataViews {
  class AttendingDoctorView {}
  class NurseView {}
}

class PatientsListDataViews {}

//Лечащий врач
@XmlType(name = "patientsListData")
@XmlRootElement(name = "patientsListData")
class PatientsListData {

  @BeanProperty
  var requestData: PatientsListRequestData = _
  @BeanProperty
  var data: LinkedList[PatientsListEntry] = new LinkedList[PatientsListEntry]

  def this(actions: java.util.Map[Action, java.util.Map[ActionProperty, java.util.List[APValue]]],
           requestData: PatientsListRequestData,
           roleId: Int,
           condInfo: java.util.LinkedHashMap[java.lang.Integer, java.util.LinkedHashMap[ActionProperty, java.util.List[APValue]]],
           mAdmissionDepartment: (Int) => OrgStructure,
           mActionPropertiesWithValues: (Int, java.util.Set[String]) =>  java.util.Map[ActionProperty, java.util.List[APValue]],
           //mCorrList: (java.util.List[java.lang.Integer])=> java.util.List[RbCoreActionProperty],
           mDiagnostics: (Int) => java.util.List[Diagnostic]) = {
    this ()
    this.requestData = requestData

    actions.foreach(e => {

      val action = e._1
      val event = action.getEvent

      var bed: OrgStructureHospitalBed = null
      var from : OrgStructure = null
      var toDep : OrgStructure = null

      val value: Object = if (e._2!=null && e._2.size()>0) {
        val apvs = e._2.iterator.next()
        if (apvs._2!=null && apvs._2.size()>0){
          apvs._2.get(0).getValue
        } else null
      } else null

      if (value.isInstanceOf[OrgStructureHospitalBed]){
        bed = value.asInstanceOf[OrgStructureHospitalBed]
        //куда переведен
        if (mActionPropertiesWithValues!=null) {
          val codes = Set[String](ConfigManager.Messages("db.apt.moving.codes.orgStructTransfer"))
          val apValues = mActionPropertiesWithValues(action.getId.intValue(), asJavaSet(codes))
          if (apValues!=null && apValues.size()>0){
            val depart = apValues.iterator.next()
            if (depart._2!=null && depart._2.size()>0){
              toDep = depart._2.get(0).asInstanceOf[APValueOrgStructure].getValue
            }
          }
        }
      } else if (value.isInstanceOf[OrgStructure]){
        if (action.getActionType.getId.compareTo(ConfigManager.Messages("db.actionType.hospitalization.primary").toInt :java.lang.Integer)==0){
          from = if (mAdmissionDepartment!=null ) mAdmissionDepartment(28) else null // Приемное отделение
          bed = null
        } else if (action.getActionType.getId.compareTo(ConfigManager.Messages("db.actionType.moving").toInt :java.lang.Integer)==0){
          if (mActionPropertiesWithValues!=null) {
            val codes = Set[String](ConfigManager.Messages("db.apt.moving.codes.hospitalBed"))
            val apValues = mActionPropertiesWithValues(action.getId.intValue(), asJavaSet(codes))
            if (apValues!=null && apValues.size()>0){
              val bedvs = apValues.iterator.next()
              if (bedvs._2!=null && bedvs._2.size()>0){
                val bedVal = bedvs._2.get(0).getValue
                if (bedVal.isInstanceOf[OrgStructureHospitalBed]){
                  from = bedVal.asInstanceOf[OrgStructureHospitalBed].getMasterDepartment
                  bed = null
                }
              }
            }
          }
        }
      }
      /*var bed: OrgStructureHospitalBed = null
      var begDate: Date = null
      var from : OrgStructure = null

      if (action!=null && action.getId.intValue()>0 && mActionPropertiesWithValues!=null) {
        val listMovAP = JavaConversions.asJavaList(List(ConfigManager.RbCAPIds("db.rbCAP.moving.id.beginTime").toInt :java.lang.Integer,
                                                        ConfigManager.RbCAPIds("db.rbCAP.moving.id.bed").toInt :java.lang.Integer,
                                                        ConfigManager.RbCAPIds("db.rbCAP.moving.id.movedIn").toInt :java.lang.Integer,
                                                        ConfigManager.RbCAPIds("db.rbCAP.hosp.primary.id.sentTo").toInt :java.lang.Integer))

        val apValues = mActionPropertiesWithValues(action.getId.intValue(), listMovAP)
        val corrList = if(mCorrList!=null) mCorrList(listMovAP) else null

        if (apValues!=null && corrList!=null) {
          apValues.foreach(prop => {
            val result = corrList.find(p=> p.getActionPropertyType.getId.intValue()==prop._1.getType.getId.intValue()).getOrElse(null)
            if (result!=null) {

                if (result.getId.compareTo(ConfigManager.RbCAPIds("db.rbCAP.moving.id.beginTime").toInt :java.lang.Integer)==0) {
                  begDate = this.getFormattedDate(prop._1.getAction.getBegDate, apValues, result.getActionPropertyType.getId.intValue())
                }
                else if (result.getId.compareTo(ConfigManager.RbCAPIds("db.rbCAP.moving.id.bed").toInt :java.lang.Integer)==0 &&
                         prop._2!=null &&
                         prop._2.size()>0) {
                  bed = prop._2.get(0).getValue.asInstanceOf[OrgStructureHospitalBed]
                }
                else if (result.getId.compareTo(ConfigManager.RbCAPIds("db.rbCAP.moving.id.movedIn").toInt :java.lang.Integer)==0 &&
                  prop._2!=null &&
                  prop._2.size()>0 &&
                  prop._2.get(0).getValue.asInstanceOf[OrgStructure].getId.intValue() == requestData.filter.departmentId) {
                  from = if(bed!=null)bed.getMasterDepartment else null
                  bed = null
                }
                else if (result.getId.compareTo(ConfigManager.RbCAPIds("db.rbCAP.hosp.primary.id.sentTo").toInt :java.lang.Integer)==0) {
                  from = if (mAdmissionDepartment!=null ) mAdmissionDepartment(28) else null // Приемное отделение
                  bed = null
                }
            }
          })
        }
      } */
      val condition = if (roleId == 25) {
        //Состояние пациента (только для роли сестра отделения)
        condInfo.containsKey(event.getId) match {
          case false => null
          case condition => condInfo.get(event.getId)
        }
      } else null
      this.data.add(new PatientsListEntry(action, bed, action.getBegDate/*begDate*/, condition, from, toDep, mDiagnostics))
    })
  }

  /**
   * Возвращает позицию найденного в списке значения
   * @param seq Список
   * @tparam T  Тип искомых данных
   */
  private class IndexOf[T] (seq: Seq[T]) {
    def unapply(pos: T) = seq find (pos ==) map (seq indexOf _)
  }

  /**
   * Метод для конкатенации даты из Action с временем из ActionProperty
   * @param actionDate Дата действия пациента.
   * @param values Список значений свойств действия.
   * @param aptId  Идентификатор типа свойства действия s11r64.ActionPropertyType.id.
   * @return Обобщенная дата.
   */
  private def getFormattedDate(actionDate: Date,
                               values: java.util.Map[ActionProperty, java.util.List[APValue]],
                               aptId: Int) = {
    if (actionDate!=null) {
      var tDate = Calendar.getInstance()
      tDate.setTime(actionDate)

      val result = values.find(element => element._1.getType.getId.intValue() == aptId).getOrElse(null)
      if(result!=null && result._2!=null &&  result._2.size()>0){
        val time = result._2.get(0).asInstanceOf[APValueTime].getValue
        if (time!=null){
          var tTime = Calendar.getInstance()
          tTime.setTime(time)
          val hour = tTime.get(Calendar.HOUR_OF_DAY)
          val minutes = tTime.get(Calendar.MINUTE)

          tDate.set(Calendar.HOUR_OF_DAY, hour)
          tDate.set(Calendar.MINUTE, minutes)
        }
      }
      tDate.getTime
    } else {
      null: Date
    }
  }
}

@XmlType(name = "patientsListRequestData")
@XmlRootElement(name = "patientsListRequestData")
class PatientsListRequestData {
  @BeanProperty
  var filter: PatientsListRequestDataFilter = _
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

  def this(departmentId: Int,
           doctorId: Int,
           roleId: Int,
           endDate: Long,
           sortingField: String,
           sortingMethod: String,
           limit: Int,
           page: Int) = {
    this()
    this.filter = new PatientsListRequestDataFilter(departmentId, doctorId, roleId, endDate)
    this.sortingField = sortingField match {
      case null => {"bed"}
      case _ => {sortingField}
    }
    this.sortingMethod = sortingMethod match {
      case null => {"asc"}
      case _ => {sortingMethod}
    }
    this.limit = if(limit>0){limit} else{10}
    this.page = if(page>1){page} else{1}
    this.coreVersion = ConfigManager.Messages("misCore.assembly.version")

    this.sortingFieldInternal = this.filter.toSortingString(this.sortingField, this.sortingMethod)
  }

  def rewriteRecordsCount(recordsCount: java.lang.Long) = {
    this.recordsCount = recordsCount.longValue()
    true
  }
}

@XmlType(name = "patientsListRequestDataFilter")
@XmlRootElement(name = "patientsListRequestDataFilter")
class PatientsListRequestDataFilter {
  @BeanProperty
  var departmentId: Int = _     // — Отделение
  @BeanProperty
  var doctorId: Int = _        // — Врач
  @BeanProperty
  var roleId: Int = _         // — Авторизационная роль
  @BeanProperty
  var endDate: Date = _       // — Дата до которой выборка

  def this(departmentId: Int,
           doctorId: Int,
           roleId: Int,
           endDate: Long) = {

    this()
    this.departmentId = departmentId
    this.doctorId = doctorId
    this.roleId = roleId
    this.endDate =  if(endDate==0) new Date() else new Date(endDate)
  }

  def toQueryStructure() = {
    var qs = new QueryDataStructure()
    if(this.departmentId>0){
      qs.add("departmentId", this.departmentId:java.lang.Integer)
    }
    if(this.doctorId>0 && roleId!=25){  //Для сестры отделения выводим всех пациентов
      qs.query += ("AND a.event.executor.id = :doctorId\n")
      qs.add("doctorId", this.doctorId:java.lang.Integer)
    }
    if(this.endDate!=null){
      qs.add("endDate", this.endDate)
    }
    qs
  }
  @Override
  def toSortingString (sortingField: String, sortingMethod: String) = {
    var sorting = sortingField.toLowerCase match {
      case "createDatetime"| "start" | "begDate" => {"ap.action.begDate %s".format(sortingMethod)}
      case "end" | "endDate" => {"ap.action.event.execDate %s".format(sortingMethod)}
      case "doctor" => {"ap.action.event.executor.lastName %s, ap.action.event.executor.firstName %s, ap.action.event.executor.patrName %s".format(sortingMethod, sortingMethod, sortingMethod)}
      //case "department" => {"org.masterDepartment.name %s".format(sortingMethod)}
      //case "bed" => {"org.name %s".format(sortingMethod)}
      //case "number" => "CAST(SUBSTRING(e.externalId, 1, 4) AS UNSIGNED) %s, CAST(SUBSTRING(e.externalId, 6) AS UNSIGNED) %s".format(sortingMethod,sortingMethod)//{"e.externalId %s".format(sortingMethod)}
      case "fullname" => {"ap.action.event.patient.lastName %s, ap.action.event.patient.firstName %s, ap.action.event.patient.patrName %s".format(sortingMethod,sortingMethod,sortingMethod)}
      case "birthdate" => {"ap.action.event.patient.birthDate %s".format(sortingMethod)}
      case _ => {"ap.action.event.id %s".format(sortingMethod)}
    }
    //sortingFieldInternal
    sorting = "ORDER BY " + sorting.format(sortingMethod)
    sorting
  }
}


//https://docs.google.com/spreadsheet/ccc?key=0Amfvj7P4xELWdFRJRnR1LVhTdG5BSFZKRnZnNWNlNHc&pli=1#gid=0
@XmlType(name = "patientsListEntry")
@XmlRootElement(name = "patientsListEntry")
class PatientsListEntry {

  @BeanProperty
  var id: Int =_                                //Ид эвента госпитализации

  @BeanProperty
  var number: String = _                        //Номер истории болезни

  @BeanProperty
  var name: PersonNameContainer = _             //Пациент

  @JsonView(Array(classOf[PatientsListDataViews.AttendingDoctorView]))
  @BeanProperty
  var birthDate: Date =_                        //День рождения

  @BeanProperty
  var hospitalBed: HospitalBedContainer = _    //Палата/Койка

  @BeanProperty
  var movingFrom: IdNameContainer = _    //Переведен из (Заполняется когда пациент не лежит на койке (т.е находится в процессе движения))

  @BeanProperty
  var movingTo: IdNameContainer = _    //Переведен в (Заполняется когда пациента перевели из текущего отделение в другое, но еще не положили там на койку (т.е находится в процессе движения))

  @BeanProperty
  var doctor: DoctorSpecsContainer = _   //Врач + Специальность + Отделение

  @BeanProperty
  var createDateTime: Date = _           //Дата поступления

  @JsonView(Array(classOf[PatientsListDataViews.AttendingDoctorView]))
  @BeanProperty
  var checkOut: String = _               //Выписка через

  @JsonView(Array(classOf[PatientsListDataViews.AttendingDoctorView]))
  @BeanProperty
  var totalDays: String = _               //Проведено дней

  @JsonView(Array(classOf[PatientsListDataViews.AttendingDoctorView]))
  @BeanProperty
  var finance: IdNameContainer = _               //Вид оплаты

  @JsonView(Array(classOf[PatientsListDataViews.AttendingDoctorView]))
  @BeanProperty
  var diagnoses: LinkedList[DiagnosisContainer] = new LinkedList[DiagnosisContainer]               //Диагнозы

  @JsonView(Array(classOf[PatientsListDataViews.NurseView]))
  @BeanProperty
  var condition: PersonConditionContainer = _       //Состояние пациента

  def this(action: Action,
           bed: OrgStructureHospitalBed,
           condition: java.util.Map[ActionProperty, java.util.List[APValue]],
           from: OrgStructure,
           toDep: OrgStructure) {
     this()
     val patient = action.getEvent.getPatient
     this.id = action.getEvent.getId.intValue()
     this.number = action.getEvent.getExternalId
     this.name = new PersonNameContainer(patient)
     this.birthDate = patient.getBirthDate
     if (action.getEvent.getExecutor != null) {
       this.doctor =  new DoctorSpecsContainer(action.getEvent.getExecutor)
     }
     this.createDateTime = action.getBegDate
     this.checkOut = ""                               //TODO: "Выписка через" - расчетное поле, алгоритм не утвержден
     if(bed!=null)
       this.hospitalBed = new HospitalBedContainer(bed)
     if(condition!=null)
       this.condition = new PersonConditionContainer(condition)
     if(from!=null)
       this.movingFrom = new IdNameContainer(from.getId.intValue(),from.getName)
     if(toDep!=null)
       this.movingTo = new IdNameContainer(toDep.getId.intValue(),toDep.getName)
  }

  def this(action: Action,
           bed: OrgStructureHospitalBed,
           begDate: Date,
           condition: java.util.Map[ActionProperty, java.util.List[APValue]],
           from: OrgStructure,
           toDep: OrgStructure) {
    this(action, bed, condition, from, toDep)
    val eType = action.getEvent.getEventType
    if(eType!=null) {
      if (eType.getFinance!=null)
        this.finance = new IdNameContainer(eType.getFinance.getId.intValue(), eType.getFinance.getName)
      if (eType.getRequestType!=null){
        val msecInDay = 1000 * 60 * 60 * 24
        val nowDate = (new Date()).getTime
        val diffOfDays = if(begDate!=null)(nowDate - begDate.getTime)/msecInDay else 0

        this.totalDays = eType.getRequestType.getId.intValue() match {
          case 1 => "Проведено %d койко-дней".format(diffOfDays+1)
          case 2 => "Проведено %d койко-дней".format(diffOfDays)
          case _ => ""
        }
      }
    }
  }
  def this(action: Action,
           bed: OrgStructureHospitalBed,
           begDate: Date,
           condition: java.util.Map[ActionProperty, java.util.List[APValue]],
           from: OrgStructure,
           toDep: OrgStructure,
           mDiagnostics: (Int)=> java.util.List[Diagnostic]) {
    this(action, bed, begDate, condition, from, toDep)
    if (mDiagnostics!=null){
       val diagnostics = mDiagnostics(action.getEvent.getId.intValue())
       if (diagnostics!=null && diagnostics.size()>0) {
          diagnostics.foreach(dia => this.diagnoses.add(new DiagnosisContainer(dia)))
       }
    }
  }
}

@XmlType(name = "hospitalBedContainer")
@XmlRootElement(name = "hospitalBedContainer")
class HospitalBedContainer {

  @BeanProperty
  var id: Int =_

  @BeanProperty
  var department: IdNameContainer = _

  @BeanProperty
  var room: String = _

  @BeanProperty
  var bed: String = _

  @BeanProperty
  var code: String = _

  @BeanProperty
  var name: String = _

  @BeanProperty
  var raw: String = _

  def this(bed: OrgStructureHospitalBed){
    this()
    this.id = bed.getId.intValue()
    this.department = new IdNameContainer(bed.getMasterDepartment.getId.intValue(),
                                          bed.getMasterDepartment.getName)
    this.room = ""
    this.bed =  (bed.getIdx + 1).toString
    this.code = bed.getCode
    this.name = bed.getName
    this.raw =  ""
  }

  def this(raw: String) {  //Записываем отделение откуда переведен (если нет койки)
    this()
    this.raw =raw
  }
}

@XmlType(name = "personConditionContainer")
@XmlRootElement(name = "personConditionContainer")
class PersonConditionContainer {

  @BeanProperty
  var state: String = _

  @BeanProperty
  var breathingRate: String = _

  @BeanProperty
  var arterialBloodPressure: ArterialBloodPressureContainer = new ArterialBloodPressureContainer()

  def this(condition: java.util.Map[ActionProperty, java.util.List[APValue]]) {
    this()
    condition.foreach( c =>  {
       val value =  c._2.size() match {
         case 0 => ""
         case size => c._2.get(0).getValueAsString
       }
       c._1.getType.getCode match {
         case "STATE" =>  {    //"Состояние"
           this.state = value
         }
         case "PULS" =>  {         //"ЧСС"
           this.breathingRate = value
         }
         case "BPRAS" =>  {    //"АД нижн."
           this.arterialBloodPressure.low = value
         }
         case "BPRAD" =>  {   //"АД верхн."
           this.arterialBloodPressure.high = value
         }
         case _ => {}
       }
    })

  }

}
@XmlType(name = "arterialBloodPressureContainer")
@XmlRootElement(name = "arterialBloodPressureContainer")
class ArterialBloodPressureContainer {

  @BeanProperty
  var low: String = _

  @BeanProperty
  var high: String = _
}