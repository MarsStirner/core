package ru.korus.tmis.core.data

import javax.xml.bind.annotation.{XmlType, XmlRootElement}
import reflect.BeanProperty
import java.util.LinkedList
import ru.korus.tmis.util.ConfigManager
import scala.collection.JavaConversions._
import ru.korus.tmis.core.entity.model._

/*========== Контейнер для просмотра полных данных о талоне СПО ========== */

@XmlType(name = "talonspoData")
@XmlRootElement(name = "talonspoData")
class TalonSPOData {

  @BeanProperty
  var requestData: TalonSPORequestData = _
  @BeanProperty
  var data: TalonSPOEntry = _

  def this(event: Event) {
    this()
    this.requestData = new TalonSPORequestData()
    this.data = new TalonSPOEntry(event)
  }
}

@XmlType(name = "talonSPORequestData")
@XmlRootElement(name = "talonSPORequestData")
class TalonSPORequestData {

}

@XmlType(name = "talonSPOEntry")
@XmlRootElement(name = "talonSPOEntry")
class TalonSPOEntry {

  @BeanProperty
  var id: Int = _                                       //Ид обращения

  @BeanProperty
  var rangeTalonDateTime: DatePeriodContainer = _       //Даты открытия и закрытия талона

  @BeanProperty
  var privilege: IdNameContainer = _                    //Код льготы ClientSocStatus

  @BeanProperty
  var execPerson: ComplexPersonContainer = _            //Данные о враче и отделении

  @BeanProperty
  var typeOfPayment: IdNameContainer = _                //Вид оплаты (rbFinance)

  @BeanProperty
  var appealMotive: IdNameContainer = _                 //Повод обращения

  @BeanProperty
  var appealCase: IdNameContainer = _                   //Случай обращения

  //@BeanProperty
  //var diagnoses: LinkedList[ComplexDiagnosisContainer] = new LinkedList[ComplexDiagnosisContainer]

  @BeanProperty
  var treatmentResult: IdNameContainer = _              //Результат лечения rbResult

  //Еще нужно Посещения и Случай завершен, но их пока нет в базе

  def this(event: Event){
    this()
    if(event!=null) {
      this.id = event.getId.intValue()
      this.rangeTalonDateTime = new DatePeriodContainer(event.getSetDate, event.getExecDate)
      this.privilege = null //TODO: Неясно пока где этот ClientSocStatus в эвенте
      this.execPerson = new ComplexPersonContainer(event.getExecutor)
      this.typeOfPayment = new IdNameContainer(event.getEventType.getFinance.getId.intValue(), event.getEventType.getFinance.getName)
      this.appealMotive = new IdNameContainer()    //event.getEventType...????
      this.appealCase = new IdNameContainer()     //event.getIsPrimary<-boolean  ???
      this.treatmentResult = new IdNameContainer(event.getResultId.intValue(), "")   //достать rbResult.name
      //<=достаем диагнозы
    }
  }
}

/*========== Контейнер для просмотра списка талонов СПО ==========*/

@XmlType(name = "talonSPODataList")
@XmlRootElement(name = "talonSPODataList")
class TalonSPODataList {

  @BeanProperty
  var requestData: TalonSPOListRequestData = _
  @BeanProperty
  var data: java.util.LinkedList[TalonSPOListEntry] = new java.util.LinkedList[TalonSPOListEntry]

  def this(mutableEvents: java.util.Map[Event, Object],
           request: TalonSPOListRequestData) {
    this()
    this.requestData = request
    mutableEvents.foreach(f=>{
      if (f._2.isInstanceOf[(java.util.Map[Object,  Object],OrgStructure)]) {
        val value = f._2.asInstanceOf[(java.util.Map[Object,  Object],OrgStructure)]
        this.data += new TalonSPOListEntry(f._1, value._1)
      }
    })
  }
}

@XmlType(name = "talonSPOListRequestData")
@XmlRootElement(name = "talonSPOListRequestData")
class TalonSPOListRequestData {
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

    this.sortingFieldInternal = this.filter.asInstanceOf[TalonSPODataListFilter].toSortingString(this.sortingField)

    this.sortingMethod = sortingMethod match {
      case null => {"asc"}
      case _ => {sortingMethod}
    }
    this.limit = if(limit>0){limit} else{ConfigManager.Messages("misCore.pages.limit.default").toInt}
    this.page = if(page>1){page} else{1}
    this.coreVersion = ConfigManager.Messages("misCore.assembly.version")
  }

  def rewriteRecordsCount(recordsCount: java.lang.Long) = {
    this.recordsCount = recordsCount.longValue()
    true
  }
}

@XmlType(name = "talonSPODataListFilter")
@XmlRootElement(name = "talonSPODataListFilter")
class TalonSPODataListFilter {
  @BeanProperty
  var patientId:  Int = _

  var code:  String = _

  def this(patientId:  Int, code: String){
    this()
    this.patientId = patientId
    this.code = code
  }

  def toQueryStructure() = {
    var qs = new QueryDataStructure()

    if(this.patientId>0){
      qs.query += ("AND e.patient.id =  :patientId\n")
      qs.add("patientId", this.patientId:java.lang.Integer)
    }
    qs.query += ("AND e.eventType.code =  :code\n")
    qs.add("code", this.code)
    qs
  }
  def toSortingString (sortingField: String) = {
    sortingField match {
      case "start" | "begDate" => {"e.setDate"}
      case "end" | "endDate" => {"e.execDate"}
      case "doctor" => {"e.executor.lastName, e.executor.firstName, e.executor.patrName"}
      case "department" => {"e.executor.orgStructure.name"}
      //case "diagnosis" => {"ds.mkbCode"}
      case _ => {"e.id"}
    }
  }
}


@XmlType(name = "talonSPOListEntry")
@XmlRootElement(name = "talonSPOListEntry")
class TalonSPOListEntry {

  @BeanProperty
  var id: Int = _                                       //Ид обращения

  @BeanProperty
  var rangeTalonDateTime: DatePeriodContainer = _       //Даты открытия и закрытия талона

  @BeanProperty
  var execPerson: ComplexPersonContainer = _            //Данные о враче и отделении

  @BeanProperty
  var diagnoses: java.util.List[DiagnosisContainer] = new java.util.LinkedList[DiagnosisContainer] // Диагноз

  def this(event: Event, diagnoses: java.util.Map[Object, Object]){
    this()
    this.id = event.getId.intValue()
    this.rangeTalonDateTime = new DatePeriodContainer(event.getSetDate, event.getExecDate)
    this.execPerson = new ComplexPersonContainer(event.getExecutor)
    diagnoses.foreach(d=> this.diagnoses += new DiagnosisContainer(d._1, d._2))
  }

}

//========== Вспомогательные контейнеры ==========

@XmlType(name = "complexPersonContainer")
@XmlRootElement(name = "complexPersonContainer")
class ComplexPersonContainer {

  @BeanProperty
  var doctor: DoctorContainer = _                  //доктор и специальность

  @BeanProperty
  var department: IdNameContainer = _              //отделение

  def this(person: Staff) {
    this()
    this.doctor = new DoctorContainer(person)
    this.department = if(person.getOrgStructure!=null) new IdNameContainer(person.getOrgStructure.getId.intValue(), person.getOrgStructure.getName)
                      else new IdNameContainer()
  }
}

/*
@XmlType(name = "ComplexDiagnosisContainer")
@XmlRootElement(name = "ComplexDiagnosisContainer")
class ComplexDiagnosisContainer {

  @BeanProperty                                          //диагноз
  var diagnosis: DiagnosisContainer = _

  @BeanProperty
  var mes: IdNameContainer = _                          //МЭС

  def this(){
    this()
    this.mes = mes_x
    this.diagnosis = null   //!! заполнить контейнер
  }
}  */
