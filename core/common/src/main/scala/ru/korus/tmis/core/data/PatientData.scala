package ru.korus.tmis.core.data

import javax.xml.bind.annotation.{XmlType, XmlRootElement}
import scala.collection.JavaConversions._
import ru.korus.tmis.core.entity.model._
import fd.ClientSocStatus
import kladr.{Street, Kladr}
import org.codehaus.jackson.annotate.JsonIgnoreProperties
import java.util.Date
import beans.BeanProperty
import scala.Predef._
import java.util
import ru.korus.tmis.core.filter.AbstractListDataFilter
import ru.korus.tmis.scala.util.ConfigManager
import scala.language.reflectiveCalls

//Dynamic Filters
object QuotaViews {
  class DynamicFieldsQuotaCreate {
  }
  class DynamicFieldsQuotaHistory {
  }
}
class QuotaViews {}

@XmlType(name = "patientData")
@XmlRootElement(name = "patientData")
@JsonIgnoreProperties(ignoreUnknown = true)
class PatientData {
  @BeanProperty
  var requestData: PatientRequestData = _
  @BeanProperty
  var data: util.LinkedList[PatientEntry] = new util.LinkedList[PatientEntry]

  def this(patients: java.util.List[Patient], requestData: PatientRequestData) = {
    this ()
    patients.foreach(p => this.data.add(new PatientEntry(p, null, null)))
    this.requestData = requestData
  }
}

@XmlType(name = "patientCardData")
@XmlRootElement(name = "patientCardData")
@JsonIgnoreProperties(ignoreUnknown = true)
class PatientCardData {
  @BeanProperty
  var requestData: EmptyObjectContainer = _
  @BeanProperty
  var data: PatientEntry = _

  def this(patient: Patient,
           map: java.util.LinkedHashMap[java.lang.Integer, java.util.LinkedList[Kladr]],
           street: java.util.LinkedHashMap[java.lang.Integer, Street]) = {
    this ()
    this.requestData = new EmptyObjectContainer()
    this.data = new PatientEntry(patient, map, street)
  }
}

@XmlType(name = "emptyObjectContainer")
@XmlRootElement(name = "emptyObjectContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class EmptyObjectContainer {

  @BeanProperty
  var patientId: Int = _

  def this(id: Int) {
    this()
    this.patientId  = id
  }
}

@XmlType(name = "patientRequestData")
@XmlRootElement(name = "patientRequestData")
@JsonIgnoreProperties(ignoreUnknown = true)
class PatientRequestData {
  @BeanProperty
  var filter: RequestDataFilter = new RequestDataFilter()
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

  def this(patientCode: Int,
           fullName: String, 
           birthDate: Date,
           document: String,
           withRelations: String,
           sortingField: String,
           sortingMethod: String,
           limit: Int,
           page: Int) = {
    this()
    val flgRelations = !((withRelations!=null) &&
                       (!withRelations.isEmpty) &&
                       ((withRelations.toLowerCase.compareTo("no")==0) || (withRelations.toLowerCase.compareTo("false")==0)))
    this.filter = new RequestDataFilter(patientCode, fullName, birthDate, document, flgRelations)
    this.sortingField = sortingField match {
      case null => "id"
      case _ => sortingField
    }
    this.sortingMethod = sortingMethod match {
      case null => "asc"
      case _ => sortingMethod
    }
    this.limit = limit
    this.page = if(page>1)page else 1
    this.coreVersion = ConfigManager.Messages("misCore.assembly.version")
    this.sortingFieldInternal = this.filter.toSortingString(this.sortingField, this.sortingMethod)
  }

  def rewriteRecordsCount(recordsCount: java.lang.Long) = {
    this.recordsCount = recordsCount.longValue()
    true
  }
}

@XmlType(name = "requestDataFilter")
@XmlRootElement(name = "requestDataFilter")
@JsonIgnoreProperties(ignoreUnknown = true)
class RequestDataFilter extends AbstractListDataFilter {
  @BeanProperty
  var patientCode: Int = _ // — Код пациента
  @BeanProperty
  var fullName: String = _ // — ФИО
  @BeanProperty
  var birthDate: Date = _  // — Дата рождения
  @BeanProperty
  var document: String = _ // — Фильтр по любому документу
  @BeanProperty
  var withRelations: Boolean = false  //Фильтр по ClientRelation

  def this(patientCode: Int,
           fullName: String,
           birthDate: Date,
           document: String) = {
    this()
    this.patientCode = patientCode
    this.fullName = fullName
    this.birthDate = birthDate
    this.document = document
  }

  def this(patientCode: Int,
           fullName: String,
           birthDate: Date,
           document: String,
           withRelations: Boolean) = {
    this(patientCode, fullName, birthDate, document)
    this.withRelations = withRelations
  }


  override def toQueryStructure = {
    val qs = new QueryDataStructure()

    if(this.patientCode>0){
      qs.query += "AND p.id = :patientCode\n"
      qs.add("patientCode", this.patientCode:java.lang.Integer)
    }
    if(this.fullName!=null && !this.fullName.isEmpty){
      qs.query += "AND upper(CONCAT(p.lastName, ' ', p.firstName, ' ', p.patrName)) LIKE upper(:fullName)\n"
      if(this.fullName.indexOf('%') > 0) {
        qs.add("fullName", this.fullName)
      } else {
        qs.add("fullName", this.fullName + "%")
      }
    }
    if(this.document!=null && !this.document.isEmpty){
      qs.query += "AND exists (SELECT d FROM ClientDocument d WHERE d.patient = p AND (upper(d.serial) LIKE upper(:documentPattern) OR upper(d.number) LIKE upper(:documentPattern)))\n"
      qs.add("documentPattern", "%" + this.document + "%")
    }
    if(this.birthDate!=null){
      qs.query += "AND p.birthDate = :birthDate\n"
      qs.add("birthDate", this.birthDate)
    }
    if(!this.withRelations){
      qs.query += "AND NOT exists (SELECT r FROM ClientRelation r WHERE r.relative.id != '0' AND r.relative.id = p.id)\n"
    }
    qs
  }

  @Override
  def toSortingString (sortingField: String, sortingMethod: String) = {
    var sorting = sortingField.toLowerCase match {
      case "fio" | "fullname"=> "p.lastName %s, p.firstName %s, p.patrName %s".format(sortingMethod,sortingMethod,sortingMethod)
      case "lastname" => "p.lastName %s".format(sortingMethod)
      case "firstname" | "name" => "p.firstName %s".format(sortingMethod)
      case "patrname" | "middlename" => "p.patrName %s".format(sortingMethod)
      case "sex" => "p.sex %s".format(sortingMethod)
      case "birthdate" => "p.birthDate %s".format(sortingMethod)
      case _ => "p.id %s".format(sortingMethod)
    }
    sorting = "ORDER BY " + sorting.format(sortingMethod)
    sorting
  }
}

@XmlType(name = "patientEntry")
@XmlRootElement(name = "patientEntry")
@JsonIgnoreProperties(ignoreUnknown = true)
class PatientEntry {
  @BeanProperty
  var id: Int = _
  @BeanProperty
  var patientCode : Int = _
  @BeanProperty
  var version: Int = _
  @BeanProperty
  var birthDate: Date = _
  @BeanProperty
  var birthPlace: String = _
  @BeanProperty
  var snils: String = _
  @BeanProperty
  var sex: String = _
  @BeanProperty
  var phones = new util.LinkedList[ClientContactContainer]
  @BeanProperty
  var payments = new util.LinkedList[PolicyEntryContainer]
  @BeanProperty
  var relations = new util.LinkedList[RelationEntryContainer]
  @BeanProperty
  var idCards = new util.LinkedList[DocumentEntryContainer]
  @BeanProperty
  var address: AddressContainer = _
  @BeanProperty
  var medicalInfo: MedicalInfoContainer = _
  @BeanProperty
  var name: PersonNameContainer = _
  @BeanProperty
  var disabilities = new util.LinkedList[TempInvalidContainer]
  @BeanProperty
  var occupations = new util.LinkedList[OccupationContainer]
  @BeanProperty
  var citizenship: CitizenshipContainer = _
  //@BeanProperty
  //var quotaHistory = new LinkedList[QuotaEntry]

def this(patient: Patient,
         map: java.util.LinkedHashMap[java.lang.Integer, java.util.LinkedList[Kladr]],
         street: java.util.LinkedHashMap[java.lang.Integer, Street]) = {
    this ()
    this.id = patient.getId.intValue()
    this.patientCode = this.id //TODO: что есть код пациента?
    this.version = patient.getVersion
    this.birthDate = patient.getBirthDate
    this.birthPlace = patient.getBirthPlace
    this.snils = patient.getSnils
    this.sex = patient.getSex match {
      case 1 => "male"                   //TODO: вынести в настройки
      case 2 => "female"
      case _ => "unknown"
    }
    this.name = new PersonNameContainer(patient)

    patient.getActiveClientContacts.foreach(c => this.phones.add(new ClientContactContainer(c)))
    patient.getActiveClientPolicies.foreach(p => this.payments.add(new PolicyEntryContainer (p)))
    patient.getActiveClientRelatives.foreach(r => this.relations.add(new RelationEntryContainer(r))) // getClientRelatives
    patient.getActiveClientDocuments.foreach(d =>
      if (d.getDocumentType != null && d.getDocumentType.getDocumentTypeGroup.getId.intValue() == 1) {  //getDocumentTypeGroup == 1 - тип документа удостоверяющего личность    //d.getDocumentType.getId != 20 &&
        this.idCards.add(new DocumentEntryContainer(d))
      }
    )
    val allSocStatuses = patient.getActiveClientSocStatuses
    allSocStatuses.foreach(t => {  //TODO: нужно вынести проверку типов в entity
      if (t.getSocStatusClass != null){ //getSocStatusType
        //t.getSocStatusType().getCode() match {
        t.getSocStatusClass.getCode match {
          case "2" =>  //086
            this.disabilities.add(new TempInvalidContainer(t))

          case "1" | "3"  => //087   //TODO: Сейчас берется два типа Соц. статус и занятость (запись ведется как соц.статус)
            this.occupations.add(new OccupationContainer(t, patient.getActiveClientWorks))       //32 - инвалидность

          case _ =>
        }
      }
    })

    this.address = new AddressContainer(patient, map, street)
    this.medicalInfo = new MedicalInfoContainer(patient)
    this.citizenship = new CitizenshipContainer(patient)
  }
}

@XmlType(name = "citizenshipContainer")
@XmlRootElement(name = "citizenshipContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class CitizenshipContainer {
  @BeanProperty
  var first: NumberedCitizenshipContainer = _
  @BeanProperty
  var second: NumberedCitizenshipContainer = _

    def this(patient: Patient) {
      this()
      patient.getActiveClientSocStatuses.foreach(t => {  //TODO: нужно вынести проверку типов в entity
        if (t.getSocStatusClass != null){
          t.getSocStatusClass.getCode match {
            case "4" =>
              if (first == null) {
                this.first = new NumberedCitizenshipContainer(t)
              } else {
                this.second = new NumberedCitizenshipContainer(t)
              }
            case _ =>
          }
        }
      })
    }
    /*
    def this(firstId: Int, firstName: String, secondId: Int, secondName: String) {
    this()
    this.first = new NumberedCitizenshipContainer(firstId, firstName)
    this.second = new NumberedCitizenshipContainer(secondId, secondName)
  }  */
}

@XmlType(name = "numberedCitizenshipContainer")
@XmlRootElement(name = "numberedCitizenshipContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class NumberedCitizenshipContainer {
  @BeanProperty
  var id: Int = _
  @BeanProperty
  var socStatusType: IdNameContainer = _
  @BeanProperty
  var comment: String = _

  def this(status: ClientSocStatus) {
    this()
    this.id = status.getId.intValue()
    this.comment = status.getNote
    this.socStatusType = new IdNameContainer(status.getSocStatusType.getId.intValue(), status.getSocStatusType.getName)
  }
}

@XmlType(name = "medicalInfoContainer")
@XmlRootElement(name = "medicalInfoContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class MedicalInfoContainer {

  @BeanProperty
  var blood: BloodInfoContainer = _
  @BeanProperty
  var allergies = new util.LinkedList[AllergyInfoContainer]
  @BeanProperty
  var drugIntolerances = new util.LinkedList[AllergyInfoContainer]

  def this(patient: Patient) {
    this()
    this.blood = new BloodInfoContainer(patient)
    patient.getActiveClientAllergies.sortWith(_.getId < _.getId).foreach(a => {
        this.allergies.add(new AllergyInfoContainer(a.getId, a.getNameSubstance, a.getPower, a.getCreateDate, a.getNotes))
    })

    patient.getActiveClientIntoleranceMedicaments.sortWith(_.getId < _.getId).foreach(a => {
        this.drugIntolerances.add(new AllergyInfoContainer(a.getId, a.getNameMedicament , a.getPower, a.getCreateDate, a.getNotes ))
    })
  }
}

@XmlType(name = "bloodInfoContainer")
@XmlRootElement(name = "bloodInfoContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class BloodInfoContainer {
  @BeanProperty
  var id: Int = _
  @BeanProperty
  var group: String = _
  @BeanProperty
  var checkingDate: Date = _
  @BeanProperty
  var bloodPhenotype: BloodPhenoTypeContainer = _
  @BeanProperty
  var bloodKell: String = _


  def this(patient: Patient) {
    this()
    this.id = patient.getBloodType.getId
    this.group = patient.getBloodType.getName
    this.checkingDate = patient.getBloodDate
    if (patient.getRbBloodPhenotype != null) {
      this.bloodPhenotype = new BloodPhenoTypeContainer(patient.getRbBloodPhenotype)
    }
    if (patient.getBloodKell != null ) {
      this.bloodKell = patient.getBloodKell.name()
    }

  }
}

@XmlType(name = "BloodPhenoTypeContainer")
@XmlRootElement(name = "BloodPhenoTypeContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class BloodPhenoTypeContainer {
  @BeanProperty
  var id: Int = _
  @BeanProperty
  var phenotype: String = _
  @BeanProperty
  var code: String = _

  def this(rbBloodPhenotype: RbBloodPhenotype) {
    this()
    this.id = rbBloodPhenotype.getId
    this.phenotype = rbBloodPhenotype.getName
    this.code = rbBloodPhenotype.getCode
  }
}

@XmlType(name = "allergyInfoContainer")
@XmlRootElement(name = "allergyInfoContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class AllergyInfoContainer {
  @BeanProperty
  var id: Int = _
  @BeanProperty
  var substance: String = _
  @BeanProperty
  var degree: Int = _
  @BeanProperty
  var checkingDate: Date = _
  @BeanProperty
  var comment: String = _

  def this(id: Int,  substance: String,  degree: Int,  checkingDate: Date,  comment: String) {
    this()
    this.id = id
    this.substance = substance
    this.degree = degree
    this.checkingDate = checkingDate
    this.comment = comment
  }
}

@XmlType(name = "addressContainer")
@XmlRootElement(name = "addressContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class AddressContainer {
  @BeanProperty
  var registered: AddressEntryContainer = _
  @BeanProperty
  var residential: AddressEntryContainer = _
  
  def this(patient: Patient, map: java.util.LinkedHashMap[java.lang.Integer, java.util.LinkedList[Kladr]], street: java.util.LinkedHashMap[java.lang.Integer, Street]) {
    this()

    patient.getClientAddresses
      .filter(p => !p.isDeleted && p.getAddressType == 0)
      .sortBy(_.getModifyDatetime).reverse.toList match {
      case h :: tail => this.registered = new AddressEntryContainer(h, map, street)
      case Nil =>
    }

    patient.getClientAddresses
      .filter(p => !p.isDeleted && p.getAddressType == 1)
      .sortBy(_.getModifyDatetime).reverse.toList match {
      case h :: tail => this.residential = new AddressEntryContainer(h, map, street)
      case Nil =>
    }
  }
}

@XmlType(name = "addressEntryContainer")
@XmlRootElement(name = "addressEntryContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class AddressEntryContainer {

  @BeanProperty
  var localityType: Int = _            //КЛАДР (признак)
  @BeanProperty
  var kladr: Boolean = false            //КЛАДР (признак)
  @BeanProperty
  var republic: KladrNameContainer = _      //республика
  @BeanProperty
  var district: KladrNameContainer = _      //район
  @BeanProperty
  var city: KladrNameContainer = _          //город
  @BeanProperty
  var locality: KladrNameContainer = _      //населеннный пункт
  @BeanProperty
  var street: KladrNameContainer = _        //улица
  @BeanProperty
  var house: String = _                   //дом
  @BeanProperty
  var building: String = _                //корпус
  @BeanProperty
  var flat: String = _                    //квартира
  @BeanProperty
  var fullAddress: String = _             //полный адрес


  def this(clientAddress: ClientAddress, map: java.util.LinkedHashMap[java.lang.Integer, java.util.LinkedList[Kladr]], street: java.util.LinkedHashMap[java.lang.Integer, Street]) {
    this()
    this.localityType = clientAddress.getLocalityType.intValue()
    this.fullAddress = clientAddress.getFreeInput
    val address = clientAddress.getAddress
    if (address != null) {
      val house = address.getHouse
      if (house != null) {
        if(house.getKLADRCode!=null){
          this.kladr = true
          this.street = if (street!=null && street.containsKey(house.getId.intValue())) {
            new KladrNameContainer( street.get(house.getId.intValue()).getCode,
                                    street.get(house.getId.intValue()).getName,
                                    street.get(house.getId.intValue()).getSocr,
                                    street.get(house.getId.intValue()).getIndex)
          } else
            new KladrNameContainer(house.getKLADRStreetCode, "", "", "")
          if(map!=null && map.containsKey(house.getId.intValue())){
            val list = map.get(house.getId.intValue())
            list.size() match{
              case 0 =>
              case 1 => if (list.get(0).getCode.compareTo("7800000000000") == 0 || list.get(0).getCode.compareTo("7700000000000") == 0) {
                            //костылик, чтобы Питер и Москва возвращались в поле репаблик
                            this.republic = new KladrNameContainer(list.get(0).getCode, list.get(0).getName, list.get(0).getSocr, list.get(0).getIndex)
                          } else {
                            if(this.localityType != 0){
                              this.city = new KladrNameContainer(list.get(0).getCode, list.get(0).getName, list.get(0).getSocr, list.get(0).getIndex)
                            } else {
                              this.locality = new KladrNameContainer(list.get(0).getCode, list.get(0).getName, list.get(0).getSocr, list.get(0).getIndex)
                            }
                          }
              case 2 => if(this.localityType != 0){
                            this.city = new KladrNameContainer(list.get(0).getCode, list.get(0).getName, list.get(0).getSocr, list.get(0).getIndex)
                          } else {
                            this.locality = new KladrNameContainer(list.get(0).getCode, list.get(0).getName, list.get(0).getSocr, list.get(0).getIndex)
                          }
                this.republic = new KladrNameContainer(list.get(1).getCode, list.get(1).getName, list.get(1).getSocr, list.get(1).getIndex)
              case 3 => if(this.localityType != 0){
                            this.city = new KladrNameContainer(list.get(0).getCode, list.get(0).getName, list.get(0).getSocr, list.get(0).getIndex)
                          } else {
                            this.locality = new KladrNameContainer(list.get(0).getCode, list.get(0).getName, list.get(0).getSocr, list.get(0).getIndex)
                          }
                this.district = new KladrNameContainer(list.get(1).getCode, list.get(1).getName, list.get(1).getSocr, list.get(1).getIndex)
                this.republic = new KladrNameContainer(list.get(2).getCode, list.get(2).getName, list.get(2).getSocr, list.get(2).getIndex)
              case 4 => this.locality = new KladrNameContainer(list.get(0).getCode, list.get(0).getName, list.get(0).getSocr, list.get(0).getIndex)
                this.city = new KladrNameContainer(list.get(1).getCode, list.get(1).getName, list.get(1).getSocr, list.get(1).getIndex)
                this.district = new KladrNameContainer(list.get(2).getCode, list.get(2).getName, list.get(2).getSocr, list.get(2).getIndex)
                this.republic = new KladrNameContainer(list.get(3).getCode, list.get(3).getName, list.get(3).getSocr, list.get(3).getIndex)
              case _ => this.locality = new KladrNameContainer(list.get(0).getCode, list.get(0).getName, list.get(0).getSocr, list.get(0).getIndex)
                this.city = new KladrNameContainer(list.get(1).getCode, list.get(1).getName, list.get(1).getSocr, list.get(1).getIndex)
                this.district = new KladrNameContainer(list.get(2).getCode, list.get(2).getName, list.get(2).getSocr, list.get(2).getIndex)
                this.republic = new KladrNameContainer(list.get(3).getCode, list.get(3).getName, list.get(3).getSocr, list.get(3).getIndex)
            }
          } else {
            this.city = new KladrNameContainer(house.getKLADRCode, "", "","")
          }

        } else {
          this.kladr = false
        }
        this.house = house.getNumber
        this.building = house.getCorpus
      }
      this.flat = address.getFlat
    }
  }
}

@XmlType(name = "policyEntryContainer")
@XmlRootElement(name = "policyEntryContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class PolicyEntryContainer {
  @BeanProperty
  var id: Int = _
  @BeanProperty
  var policyType: IdNameContainer = _
  @BeanProperty
  var series: String = _
  @BeanProperty
  var number: String = _
  @BeanProperty
  var smo: IdNameContainer = _
  @BeanProperty
  var issued: String = _
  @BeanProperty
  var rangePolicyDate: DatePeriodContainer = _
  @BeanProperty
  var comment: String = _

  def this(policy: ClientPolicy) {
    this ()
    this.id = policy.getId.intValue()
    policy.getPolicyType match {
      case null =>
      case policyType: RbPolicyType =>
        this.policyType = new IdNameContainer(policyType.getId.intValue(), policyType.getCode, policyType.getName)
    }
    this.series = policy.getSerial
    this.number = policy.getNumber
    this.comment = policy.getNote
    this.rangePolicyDate = new DatePeriodContainer(policy.getBegDate,policy.getEndDate)
    policy.getInsurer match {
      case null =>
      case organisation: Organisation =>
        this.smo = new IdNameContainer(organisation.getId.intValue(), organisation.getFullName)
        this.issued = organisation.getFullName
    }
  }
}

@XmlType(name = "relationEntryContainer")
@XmlRootElement(name = "relationEntryContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class RelationEntryContainer {
  @BeanProperty
  var id: Int = _
  @BeanProperty
  var phones = new util.LinkedList[ClientContactContainer]
  @BeanProperty
  var name: PersonNameContainer = _
  @BeanProperty
  var relationType: IdNameContainer = _

  def this(relative: ClientRelation) {
    this()
    this.id = relative.getId.intValue()
    relative.getRelative.getClientContacts.foreach(c => this.phones.add(new ClientContactContainer(c)))
    this.name = new PersonNameContainer(relative.getRelative)
    relative.getRelativeType match {
      case null =>
      case relationType: RbRelationType =>
        this.relationType = new IdNameContainer(relationType.getId.intValue(), relationType.getLeftName)
    }
  }
}

@XmlType(name = "clientContactContainer")
@XmlRootElement(name = "clientContactContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class ClientContactContainer {
  @BeanProperty
  var id: Int = _
  @BeanProperty
  var typeId: Int = _
  @BeanProperty
  var typeName: String = _
  @BeanProperty
  var number: String = _
  @BeanProperty
  var comment: String = _

  def this(contact: ClientContact) {
    this()
    this.id = contact.getId.intValue()
    val contactType: RbContactType = contact.getContactType
    contactType match {
      case null =>
      case _ =>
        this.typeId = contactType.getId.intValue()
        this.typeName = contactType.getName
    }
    this.number = contact.getContact
    this.comment = contact.getNotes
  }
}

@XmlType(name = "documentEntryContainer")
@XmlRootElement(name = "documentEntryContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class DocumentEntryContainer {
  @BeanProperty
  var id: Int = _
  @BeanProperty
  var series: String = _
  @BeanProperty
  var number: String = _
  @BeanProperty
  var issued: String = _
  @BeanProperty
  var rangeDocumentDate: DatePeriodContainer = _
  @BeanProperty
  var docType: IdNameContainer = _

  def this(document: ClientDocument) {
    this ()
    this.id = document.getId.intValue()
    this.series = document.getSerial
    this.number = document.getNumber
    this.issued = document.getIssued
    this.rangeDocumentDate = new DatePeriodContainer(document.getDate, document.getEndDate)
    document.getDocumentType match {
      case null =>
      case docType: RbDocumentType =>
        this.docType = new IdNameContainer(docType.getId.intValue(), docType.getName)
    }
  }
}

@XmlType(name = "personNameContainer")
@XmlRootElement(name = "personNameContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class PersonNameContainer {
  @BeanProperty
  var first: String = _
  @BeanProperty
  var last: String = _
  @BeanProperty
  var middle: String = _
  @BeanProperty
  var raw: String = _

  def this(patient: Patient) = {
    this ()
    this.first = patient.getFirstName
    this.last = patient.getLastName
    this.middle = patient.getPatrName
    this.raw = this.last + " " + this.first + " " + this.middle
  }

  def this(person: Staff) = {
    this ()
    this.first = person.getFirstName
    this.last = person.getLastName
    this.middle = person.getPatrName
    this.raw = this.last + " " + this.first + " " + this.middle
  }

  def this(fullName: String) = {
    this ()
    this.first = ""
    this.last = ""
    this.middle = ""
    this.raw = fullName
  }

  def toMap = {
    val map = new java.util.HashMap[String, Object]
    map.put("first", this.first)
    map.put("last", this.last)
    map.put("middle", this.middle)
    map.put("raw", this.raw)
    map
  }
}

@XmlType(name = "personNameContainer")
@XmlRootElement(name = "personNameContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class ExtendedPersonNameContainer extends PersonNameContainer {
  @BeanProperty
  var code: String = _

  @BeanProperty
  var department: OrgStructureContainer = _

  def this(person: Staff) = {
    this ()
    if (person!=null) {
      this.code = person.getCode
      this.department = new OrgStructureContainer(person.getOrgStructure)
      this.first = person.getFirstName
      this.last = person.getLastName
      this.middle = person.getPatrName
      this.raw = this.last + " " + this.first + " " + this.middle
    }
  }
}

@XmlType(name = "tempInvalidContainer") //disability
@XmlRootElement(name = "tempInvalidContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class TempInvalidContainer{
  @BeanProperty
  var id : Int = _
  @BeanProperty
  var comment : String = _
  @BeanProperty
  var disabilityType : IdNameContainer = _    //reason???
  @BeanProperty
  var rangeDisabilityDate : DatePeriodContainer = _
  @BeanProperty
  var document : DocumentContainer = _
  @BeanProperty
  var benefitsCategory : BenefitsCategoryContainer = _

  def this(socStatus : ClientSocStatus) = {
    this()
    this.id = socStatus.getId.intValue()
    this.comment = socStatus.getNote
    socStatus.getSocStatusType match {
      case null =>
      case a => this.disabilityType = new IdNameContainer(a.getId,a.getName)
    }
    socStatus.getDocument match {
      case null =>
      case doc =>
        val docType = doc.getDocumentType
        this.document = new DocumentContainer(docType.getId.intValue(), docType.getName, doc.getNumber, doc.getSerial, doc.getIssued, doc.getDate)
    }
    this.rangeDisabilityDate = new DatePeriodContainer(socStatus.getBegDate, socStatus.getEndDate)
    if (socStatus.getBenefitCategoryId!=null) {
      this.benefitsCategory = new BenefitsCategoryContainer(socStatus.getBenefitCategoryId.getId.intValue(), "")
    }
  }
}


@XmlType(name = "occupationContainer")
@XmlRootElement(name = "occupationContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class OccupationContainer{
	@BeanProperty 
	var id : Int = _
	@BeanProperty
	var comment : String = _
  @BeanProperty
  var socialStatus : SocialStatusContainer = _
  @BeanProperty
  var works : util.LinkedList[ClientWorkContainer] = new  util.LinkedList[ClientWorkContainer]
	
	def this(socStatus : ClientSocStatus, clientWork: java.util.List[ClientWork]) = {
	  this()
	  this.id = socStatus.getId
    this.comment = socStatus.getNote
	  socStatus.getSocStatusType match {
	    case null =>
      case a =>
        this.socialStatus = new SocialStatusContainer(
          a.getId.intValue(),
          a.getName)
    }
    clientWork.foreach(w => this.works.add(new ClientWorkContainer(w, socStatus.getSocStatusType.getId.intValue())))
	}
}
@XmlType(name = "clientWorkContainer")   //socialStatus
@XmlRootElement(name = "clientWorkContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class ClientWorkContainer {
  @BeanProperty
  var id: Int =_
  @BeanProperty
  var preschoolNumber : String =_
  @BeanProperty
  var schoolNumber : String = _
  @BeanProperty
  var classNumber : String = _
  @BeanProperty
  var militaryUnit : String = _
  @BeanProperty
  var workingPlace : String = _
  @BeanProperty
  var position : String = _
  @BeanProperty
  var rank : RankContainer = _
  @BeanProperty
  var forceBranch : ForceBranchContainer = _
  @BeanProperty
  var relationType : IdNameContainer = _

  def this(work: ClientWork, socStatusTypeId: Int){
    this()
    this.id = work.getId.intValue()

    socStatusTypeId match {
      case 310 => //работающий
        this.workingPlace = work.getFreeInput
        this.position = work.getPost
      // case 311 => {}            //неработающий
     // case 312 => {}            //неработающий пенсионер
      case 313 => //Дошкольник
        this.preschoolNumber = work.getFreeInput
      case 314 => //Учащийся
        this.schoolNumber = work.getFreeInput
        this.classNumber = work.getPost
      case 315 => //Военнослужащий
        this.militaryUnit = work.getFreeInput
        if (work.getRankId!=null){
          this.rank = new RankContainer(work.getRankId.intValue(), "")
        }
        if (work.getArmId!=null){
          this.forceBranch = new ForceBranchContainer(work.getArmId.intValue(), "") //work.getArmId.getId.intValue(),
        }
      //case 318 => {}              //Организован
      //case 319 => {}              //Неорганизован
      case _ =>
        this.workingPlace = work.getFreeInput
        this.position = work.getPost
    }
  }
}

@XmlType(name = "socialStatusContainer")
@XmlRootElement(name = "socialStatusContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class SocialStatusContainer {
	@BeanProperty 
	var id : Int = _
	@BeanProperty 
	var status : String = _
	
	def this( id : Int, status : String) = {
	  this()
	  this.id = id
    this.status = status
	}
}

@XmlType(name = "rankContainer")
@XmlRootElement(name = "rankContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class RankContainer {
	@BeanProperty 
	var id : Int = _
	@BeanProperty 
	var rank : String = _
	
	def this( id : Int, rank : String) = {
	  this()
	  this.id = id
    this.rank = rank
	}
}

@XmlType(name = "forceBranchContainer")
@XmlRootElement(name = "forceBranchContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class ForceBranchContainer {
	@BeanProperty 
	var id : Int = _
	@BeanProperty 
	var branch : String = _
	
	def this( id : Int, branch : String) = {
	  this()
	  this.id = id
    this.branch = branch
	}
}

@XmlType(name = "benefitsCategoryContainer")
@XmlRootElement(name = "benefitsCategoryContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class BenefitsCategoryContainer  {
	@BeanProperty 
	var id : Int = _
	@BeanProperty 
	var category : String = _
	
	def this( id : Int, category : String) = {
	  this()
	  this.id = id
    this.category = category
	}
}

@XmlType(name = "documentContainer")
@XmlRootElement(name = "documentContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class DocumentContainer  {
	@BeanProperty 
	var id : Int = _
	@BeanProperty 
	var document : String = _
  @BeanProperty
  var number : String = _
  @BeanProperty
  var series: String = _
  @BeanProperty
  var comment : String = _
  @BeanProperty
  var date : Date = _
	
	def this( id : Int, document : String, number : String, series : String, comment : String, date : Date) = {
	  this()
	  this.id = id
	  this.document = document
    this.number = number
    this.series = series
    this.comment = comment
    this.date = date
	}
}

@XmlType(name = "kladrNameContainer")
@XmlRootElement(name = "kladrNameContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class KladrNameContainer {

  @BeanProperty
  var code : String = _
  @BeanProperty
  var name : String = _
  @BeanProperty
  var socr: String = _
  @BeanProperty
  var index: String = _

  def this( code : String, name : String, socr: String ) = {
    this()
    this.name = name
    this.code = code
    this.socr = socr
  }

  def this( code : String, name : String, socr: String, index: String) = {
    this(code, name, socr)
    this.index = index
  }
}

@XmlType(name = "quotaRequestData")
@XmlRootElement(name = "quotaRequestData")
@JsonIgnoreProperties(ignoreUnknown = true)
class QuotaRequestData {
  @BeanProperty
  var filter: RequestDataFilter = _
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

  def this(filter: RequestDataFilter,
           sortingField: String,
           sortingMethod: String,
           limit: Int,
           page: Int) = {
    this()
    this.filter = if(filter!=null) {filter} else {null}
    this.sortingField = sortingField match {
      case null => "id"
      case _ => sortingField
    }
    this.sortingFieldInternal = this.toSortingString(this.sortingField)

    this.sortingMethod = sortingMethod match {
      case null => "asc"
      case _ => sortingMethod
    }
    this.limit = limit
    this.page = if(page>1)page else 1
    this.coreVersion = ConfigManager.Messages("misCore.assembly.version")
  }

  def rewriteRecordsCount(recordsCount: java.lang.Long) = {
    this.recordsCount = recordsCount.longValue()
    true
  }

  def this(patientCode: String,
    fullName: String,
    birthDate: Date,
    document: String,
    sortingField: String,
    sortingMethod: String,
    limit: Int,
    page: Int) = {
    this()
    this.filter = new RequestDataFilter(patientCode.toInt, fullName, birthDate, document)
    this.sortingField = sortingField
    this.sortingMethod = sortingMethod
    this.limit = limit
    this.page = page
    this.coreVersion = ConfigManager.Messages("misCore.assembly.version")
  }

  def toSortingString (sortingField: String) = {
    sortingField match {
      case "quotaTicket" => "cq.quotaTicket"
      case "request" => "cq.request"
      case "amount" => "cq.amount"
      case "diagnosis" => "cq.mkb.diagID"
      case "quotaType" => "cq.quotaType.code"
      case _ => "cq.id"
    }
  }
}

