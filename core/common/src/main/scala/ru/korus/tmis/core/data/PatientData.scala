package ru.korus.tmis.core.data

import javax.xml.bind.annotation.{XmlType, XmlRootElement}
import scala.collection.JavaConversions._
import ru.korus.tmis.core.entity.model._
import kladr.{Street, Kladr}
import org.codehaus.jackson.annotate.JsonIgnoreProperties
import javax.xml.bind.annotation.XmlRootElement._
import java.util.{Date, LinkedList}
import reflect.BeanProperty
import scala.Predef._
import ru.korus.tmis.util.ConfigManager
import org.codehaus.jackson.annotate.JsonIgnoreProperties._
import org.codehaus.jackson.map.annotate.JsonView
import java.util

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
  var data: LinkedList[PatientEntry] = new LinkedList[PatientEntry]

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
  var filter: RequestDataFilter = _
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
  @BeanProperty
  var coreVersion: String = _

  def this(patientCode: String,
           fullName: String, 
           birthDate: Date,
           document: String,
           withRelations: String,
           sortingField: String,
           sortingMethod: String,
           limit: String,
           page: String) = {
    this()
    val flgRelations = !((withRelations!=null) &&
                       (!withRelations.isEmpty) &&
                       ((withRelations.toLowerCase.compareTo("no")==0) || (withRelations.toLowerCase.compareTo("false")==0)))
    this.filter = new RequestDataFilter(patientCode, fullName, birthDate, document, flgRelations)
    this.sortingField = sortingField
    this.sortingMethod = sortingMethod
    this.limit = limit
    this.page = page
    this.coreVersion = ConfigManager.Messages("misCore.assembly.version")
  }

}

@XmlType(name = "requestDataFilter")
@XmlRootElement(name = "requestDataFilter")
@JsonIgnoreProperties(ignoreUnknown = true)
class RequestDataFilter {
  @BeanProperty
  var patientCode: String = _ // — Код пациента
  @BeanProperty
  var fullName: String = _ // — ФИО
  @BeanProperty
  var birthDate: Date = _  // — Дата рождения
  @BeanProperty
  var document: String = _ // — Фильтр по любому документу
  @BeanProperty
  var withRelations: Boolean = false  //Фильтр по ClientRelation

  def this(patientCode: String,
           fullName: String,
           birthDate: Date,
           document: String) = {
    this()
    this.patientCode = patientCode
    this.fullName = fullName
    this.birthDate = birthDate
    this.document = document
  }

  def this(patientCode: String,
           fullName: String,
           birthDate: Date,
           document: String,
           withRelations: Boolean) = {
    this(patientCode, fullName, birthDate, document)
    this.withRelations = withRelations
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
  var phones = new LinkedList[ClientContactContainer]
  @BeanProperty
  var payments = new LinkedList[PolicyEntryContainer]
  @BeanProperty
  var relations = new LinkedList[RelationEntryContainer]
  @BeanProperty
  var idCards = new LinkedList[DocumentEntryContainer]
  @BeanProperty
  var address: AddressContainer = _
  @BeanProperty
  var medicalInfo: MedicalInfoContainer = _
  @BeanProperty
  var name: PersonNameContainer = _
  @BeanProperty
  var disabilities = new LinkedList[TempInvalidContainer]
  @BeanProperty
  var occupations = new LinkedList[OccupationContainer]
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
    this.birthDate = patient.getBirthDate()
    this.birthPlace = patient.getBirthPlace()
    this.snils = patient.getSnils()
    this.sex = patient.getSex() match {
      case 1 => "male"                   //TODO: вынести в настройки
      case 2 => "female"
      case _ => "unknown"
    }
    this.name = new PersonNameContainer(patient)

    patient.getActiveClientContacts().foreach(c => this.phones.add(new ClientContactContainer(c)))
    patient.getActiveClientPolicies().foreach(p => this.payments.add(new PolicyEntryContainer (p)))
    patient.getActiveClientRelatives().foreach(r => this.relations.add(new RelationEntryContainer(r))) // getClientRelatives
    patient.getActiveClientDocuments().foreach(d =>
      if (d.getDocumentType != null && d.getDocumentType.getDocumentTypeGroup.getId.intValue() == 1) {  //getDocumentTypeGroup == 1 - тип документа удостоверяющего личность    //d.getDocumentType.getId != 20 &&
        this.idCards.add(new DocumentEntryContainer(d))
      }
    )
    val allSocStatuses = patient.getActiveClientSocStatuses()
    allSocStatuses.foreach(t => {  //TODO: нужно вынести проверку типов в entity
      if (t.getSocStatusClass() != null){ //getSocStatusType
        //t.getSocStatusType().getCode() match {
        t.getSocStatusClass().getCode() match {
          case "2" => { //086
            this.disabilities.add(new TempInvalidContainer(t))
          }
          case "1" | "3"  => {//087   //TODO: Сейчас берется два типа Соц. статус и занятость (запись ведется как соц.статус)
            this.occupations.add(new OccupationContainer(t, patient.getActiveClientWorks))       //32 - инвалидность
          }
          case _ => {}
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
      patient.getActiveClientSocStatuses().foreach(t => {  //TODO: нужно вынести проверку типов в entity
        if (t.getSocStatusClass() != null){
          t.getSocStatusClass().getCode() match {
            case "4" => {
              if (first == null) {
                this.first = new NumberedCitizenshipContainer(t)
              } else {
                this.second = new NumberedCitizenshipContainer(t)
              }
            }
            case _ => {}
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
  var allergies = new LinkedList[AllergyInfoContainer]
  @BeanProperty
  var drugIntolerances = new LinkedList[AllergyInfoContainer]

  def this(patient: Patient) {
    this()
    this.blood = new BloodInfoContainer(patient)
    patient.getClientAllergies().foreach(a => {
      if (a.isDeleted == false) {
        this.allergies.add(new AllergyInfoContainer(a.getId().intValue(), a.getNameSubstance(), a.getPower(), a.getCreateDate(), a.getNotes() ))
      }
    })

    patient.getClientIntoleranceMedicaments().foreach(a => {
      if (a.isDeleted == false) {
        this.drugIntolerances.add(new AllergyInfoContainer(a.getId().intValue(), a.getNameMedicament() , a.getPower(), a.getCreateDate(), a.getNotes() ))
      }
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

  def this(patient: Patient) {
    this()
    this.id = patient.getBloodType().getId().intValue()
    this.group = patient.getBloodType().getName()
    this.checkingDate = patient.getBloodDate()
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
    patient.getClientAddresses().foreach(a => a.getAddressType() match {
      case 0 => {if(!a.isDeleted)this.registered = new AddressEntryContainer(a, map, street)}
      case 1 => {if(!a.isDeleted)this.residential = new AddressEntryContainer(a, map, street)}
    })
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
  //@BeanProperty
  //var area: IdValueContainer = _        //Не используется


  def this(clientAddress: ClientAddress, map: java.util.LinkedHashMap[java.lang.Integer, java.util.LinkedList[Kladr]], street: java.util.LinkedHashMap[java.lang.Integer, Street]) {
    this()
    this.localityType = clientAddress.getLocalityType().intValue()
    this.fullAddress = clientAddress.getFreeInput()
    val address = clientAddress.getAddress()
    if (address != null) {
      val house = address.getHouse()
      if (house != null) {
        if(house.getKLADRCode()!=null){
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
              case 0 => {}
              case 1 => { if (list.get(0).getCode.compareTo("7800000000000") == 0 || list.get(0).getCode.compareTo("7700000000000") == 0) {
                            //костылик, чтобы Питер и Москва возвращались в поле репаблик
                            this.republic = new KladrNameContainer(list.get(0).getCode, list.get(0).getName, list.get(0).getSocr, list.get(0).getIndex)
                          } else {
                            if(this.localityType != 0){
                              this.city = new KladrNameContainer(list.get(0).getCode, list.get(0).getName, list.get(0).getSocr, list.get(0).getIndex)
                            } else {
                              this.locality = new KladrNameContainer(list.get(0).getCode, list.get(0).getName, list.get(0).getSocr, list.get(0).getIndex)
                            }
                          }
                        }
              case 2 => { if(this.localityType != 0){
                            this.city = new KladrNameContainer(list.get(0).getCode, list.get(0).getName, list.get(0).getSocr, list.get(0).getIndex)
                          } else {
                            this.locality = new KladrNameContainer(list.get(0).getCode, list.get(0).getName, list.get(0).getSocr, list.get(0).getIndex)
                          }
                         this.republic = new KladrNameContainer(list.get(1).getCode, list.get(1).getName, list.get(1).getSocr, list.get(1).getIndex)}
              case 3 => { if(this.localityType != 0){
                            this.city = new KladrNameContainer(list.get(0).getCode, list.get(0).getName, list.get(0).getSocr, list.get(0).getIndex)
                          } else {
                            this.locality = new KladrNameContainer(list.get(0).getCode, list.get(0).getName, list.get(0).getSocr, list.get(0).getIndex)
                          }
                         this.district = new KladrNameContainer(list.get(1).getCode, list.get(1).getName, list.get(1).getSocr, list.get(1).getIndex)
                         this.republic = new KladrNameContainer(list.get(2).getCode, list.get(2).getName, list.get(2).getSocr, list.get(2).getIndex)}
              case 4 => {this.locality = new KladrNameContainer(list.get(0).getCode, list.get(0).getName, list.get(0).getSocr, list.get(0).getIndex)
                         this.city = new KladrNameContainer(list.get(1).getCode, list.get(1).getName, list.get(1).getSocr, list.get(1).getIndex)
                         this.district = new KladrNameContainer(list.get(2).getCode, list.get(2).getName, list.get(2).getSocr, list.get(2).getIndex)
                         this.republic = new KladrNameContainer(list.get(3).getCode, list.get(3).getName, list.get(3).getSocr, list.get(3).getIndex)}
              case _ => {this.locality = new KladrNameContainer(list.get(0).getCode, list.get(0).getName, list.get(0).getSocr, list.get(0).getIndex)
                         this.city = new KladrNameContainer(list.get(1).getCode, list.get(1).getName, list.get(1).getSocr, list.get(1).getIndex)
                         this.district = new KladrNameContainer(list.get(2).getCode, list.get(2).getName, list.get(2).getSocr, list.get(2).getIndex)
                         this.republic = new KladrNameContainer(list.get(3).getCode, list.get(3).getName, list.get(3).getSocr, list.get(3).getIndex)}
            }
          } else {
            this.city = new KladrNameContainer(house.getKLADRCode, "", "","")
          }

        } else {
          this.kladr = false
        }
        this.house = house.getNumber()
        this.building = house.getCorpus()
      }
      this.flat = address.getFlat()
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
    this.id = policy.getId().intValue()
    policy.getPolicyType() match {
      case null => {}
      case policyType: RbPolicyType => {
        this.policyType = new IdNameContainer(policyType.getId().intValue(),policyType.getName())
      }
    }
    this.series = policy.getSerial()
    this.number = policy.getNumber()
    this.comment = policy.getNote()
    this.rangePolicyDate = new DatePeriodContainer(policy.getBegDate(),policy.getEndDate())
    policy.getInsurer() match {
      case null => {}
      case organisation: Organisation => {
        this.smo = new IdNameContainer(organisation.getId().intValue(), organisation.getFullName())
        this.issued = organisation.getFullName()
      }
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
  var phones = new LinkedList[ClientContactContainer]
  @BeanProperty
  var name: PersonNameContainer = _
  @BeanProperty
  var relationType: IdNameContainer = _

  def this(relative: ClientRelation) {
    this()
    this.id = relative.getId().intValue()
    relative.getRelative().getClientContacts().foreach(c => this.phones.add(new ClientContactContainer(c)))
    this.name = new PersonNameContainer(relative.getRelative())
    relative.getRelativeType() match {
      case null => {}
      case relationType: RbRelationType => {
        this.relationType = new IdNameContainer(relationType.getId().intValue(), relationType.getLeftName())
      }
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
    this.id = contact.getId().intValue()
    val contactType: RbContactType = contact.getContactType()
    contactType match {
      case null => {}
      case _ => {
        this.typeId = contactType.getId().intValue()
        this.typeName = contactType.getName()
      }
    }
    this.number = contact.getContact()
    this.comment = contact.getNotes()
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
    this.id = document.getId().intValue()
    this.series = document.getSerial()
    this.number = document.getNumber()
    this.issued = document.getIssued()
    this.rangeDocumentDate = new DatePeriodContainer(document.getDate(), document.getEndDate())
    document.getDocumentType() match {
      case null => {}
      case docType: RbDocumentType => {
        this.docType = new IdNameContainer(docType.getId().intValue(), docType.getName())
      }
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
    this.first = patient.getFirstName()
    this.last = patient.getLastName()
    this.middle = patient.getPatrName()
    this.raw = this.last + " " + this.first + " " + this.middle
  }

  def this(person: Staff) = {
    this ()
    this.first = person.getFirstName()
    this.last = person.getLastName()
    this.middle = person.getPatrName()
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
    var map = new java.util.HashMap[String, Object]
    map.put("first", this.first)
    map.put("last", this.last)
    map.put("middle", this.middle)
    map.put("raw", this.raw)
    map
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
    this.id = socStatus.getId().intValue()
    this.comment = socStatus.getNote()
    socStatus.getSocStatusType() match {
      case null => {}
      case a => {
        this.disabilityType = new IdNameContainer(
          a.getId().intValue(),
          a.getName())

      }
    }
    socStatus.getDocument() match {
      case null => {}
      case doc => {
        val docType = doc.getDocumentType
        this.document = new DocumentContainer(docType.getId().intValue(), docType.getName, doc.getNumber, doc.getSerial, doc.getIssued, doc.getDate)
      }
    }
    this.rangeDisabilityDate = new DatePeriodContainer(socStatus.getBegDate(), socStatus.getEndDate());
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
  var works : LinkedList[ClientWorkContainer] = new  LinkedList[ClientWorkContainer]
	
	def this(socStatus : ClientSocStatus, clientWork: java.util.List[ClientWork]) = {
	  this()
	  this.id = socStatus.getId().intValue();
    this.comment = socStatus.getNote()
	  socStatus.getSocStatusType() match {
	    case null => {}
	    case a => {
	      	  this.socialStatus = new SocialStatusContainer(
			  			a.getId().intValue(),
			  			a.getName())
	      
	    }
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
      case 310 => {                //работающий
        this.workingPlace = work.getFreeInput
        this.position = work.getPost
      }
     // case 311 => {}            //неработающий
     // case 312 => {}            //неработающий пенсионер
      case 313 => {               //Дошкольник
        this.preschoolNumber = work.getFreeInput
      }
      case 314 => {               //Учащийся
        this.schoolNumber = work.getFreeInput
        this.classNumber = work.getPost
      }
      case 315 => {               //Военнослужащий
        this.militaryUnit = work.getFreeInput
        if (work.getRankId!=null){
          this.rank = new RankContainer(work.getRankId.intValue(), "")
        }
        if (work.getArmId!=null){
          this.forceBranch = new ForceBranchContainer(work.getArmId.intValue(), "") //work.getArmId.getId.intValue(),
        }
      }
      //case 318 => {}              //Организован
      //case 319 => {}              //Неорганизован
      case _ => {
        this.workingPlace = work.getFreeInput
        this.position = work.getPost
      }
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
	  this.id = id;
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
	  this.id = id;
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
	  this.id = id;
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
	  this.id = id;
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

@XmlType(name = "quotaListData")
@XmlRootElement(name = "quotaListData")
@JsonIgnoreProperties(ignoreUnknown = true)
class QuotaListData {

  @BeanProperty
  var requestData: QuotaRequestData = _
  @BeanProperty
  var data: util.List[QuotaEntry] = _

  def this(quotaEntries: util.List[ClientQuoting],
           requestData: QuotaRequestData) = {
    this()
    this.requestData = requestData
    this.data = new util.LinkedList[QuotaEntry]
    quotaEntries.foreach(f => data.add(new QuotaEntry(f, classOf[QuotaViews.DynamicFieldsQuotaHistory])))
  }
}

@XmlType(name = "quotaData")
@XmlRootElement(name = "quotaData")
@JsonIgnoreProperties(ignoreUnknown = true)
class QuotaData {

  @BeanProperty
  var requestData: QuotaRequestData = _
  @BeanProperty
  var data: QuotaEntry = _

  def this(quotaEntry: QuotaEntry,
           requestData: QuotaRequestData) = {
    this()
    this.requestData = requestData
    this.data = quotaEntry
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
      case null => {"id"}
      case _ => {sortingField}
    }
    this.sortingFieldInternal = this.toSortingString(this.sortingField)

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

  def this(patientCode: String,
    fullName: String,
    birthDate: Date,
    document: String,
    sortingField: String,
    sortingMethod: String,
    limit: Int,
    page: Int) = {
    this()
    this.filter = new RequestDataFilter(patientCode, fullName, birthDate, document)
    this.sortingField = sortingField
    this.sortingMethod = sortingMethod
    this.limit = limit
    this.page = page
    this.coreVersion = ConfigManager.Messages("misCore.assembly.version")
  }

  def toSortingString (sortingField: String) = {
    sortingField match {
      case "quotaTicket" => {"cq.quotaTicket"}
      case "request" => {"cq.request"}
      case "amount" => {"cq.amount"}
      case "diagnosis" => {"cq.mkb.diagID"}
      case "quotaType" => {"cq.quotaType.code"}
      case _ => {"cq.id"}
    }
  }
}

@XmlType(name = "quotaEntry")
@XmlRootElement(name = "quotaEntry")
@JsonIgnoreProperties(ignoreUnknown = true)
class QuotaEntry  {
  @BeanProperty
  var id : Int = _
  @BeanProperty
  var version : Int = _
  //@JsonView(Array(classOf[QuotaViews.DynamicFieldsQuotaCreate]))
  @BeanProperty
  var appealNumber : String = _
  @BeanProperty
  var talonNumber : String = _
  //@JsonView(Array(classOf[QuotaViews.DynamicFieldsQuotaCreate]))
  @BeanProperty
  var stage : IdNameContainer = _
  //@JsonView(Array(classOf[QuotaViews.DynamicFieldsQuotaHistory]))
  @BeanProperty
  var stageSum : Int = _
  @BeanProperty
  var request: IdNameContainer = _
  @BeanProperty
  var mkb : MKBContainer = _
  //@JsonView(Array(classOf[QuotaViews.DynamicFieldsQuotaCreate]))
  @BeanProperty
  var quotaType : QuotaTypeContainer = _
  //@JsonView(Array(classOf[QuotaViews.DynamicFieldsQuotaCreate]))
  @BeanProperty
  var department : IdNameContainer = _
  //@JsonView(Array(classOf[QuotaViews.DynamicFieldsQuotaCreate]))
  @BeanProperty
  var status : IdNameContainer = _

  def this(id: Int, version: Int, appealNumber: String, talonNumber: String, stage: Int, request: Int, mkb: Mkb, quotaType: Int, department: Int, status: Int) = {
    this()
    this.id = id
    this.version = version
    this.appealNumber = appealNumber
    this.talonNumber = talonNumber
    this.stage = new IdNameContainer(stage, "")
    this.request = new IdNameContainer(request, "")
    this.mkb = new MKBContainer(mkb)
    this.quotaType = new QuotaTypeContainer(quotaType, "", "")
    this.department = new IdNameContainer(department, "")
    this.status = new IdNameContainer(status, "")
  }

  def this(clientQuoting: ClientQuoting, classic: Class[_]) = {
    this()
    this.id = clientQuoting.getId.intValue()
    this.version = clientQuoting.getVersion
    this.talonNumber = clientQuoting.getQuotaTicket
    this.request = new IdNameContainer(clientQuoting.getRequest.intValue(), "")
    if (clientQuoting.getMkb != null) {
      this.mkb = new MKBContainer(clientQuoting.getMkb)
    }

    //if (classic == classOf[QuotaViews.DynamicFieldsQuotaCreate]) {
      this.appealNumber = clientQuoting.getIdentifier
      this.stage = new IdNameContainer(clientQuoting.getStage.intValue(), "")
      this.quotaType = new QuotaTypeContainer(clientQuoting.getQuotaType)
    if (clientQuoting.getOrgStructure != null) {
      this.department = new IdNameContainer(clientQuoting.getOrgStructure.getId.intValue(), clientQuoting.getOrgStructure.getName)
    }
      //this.department = new IdNameContainer(clientQuoting.getOrgStructure.getId.intValue(), clientQuoting.getOrgStructure.getName)
      this.status = new IdNameContainer(clientQuoting.getStatus.getId.intValue(), clientQuoting.getStatus.getName)
    //}
    //else if (classic == classOf[QuotaViews.DynamicFieldsQuotaHistory]) {
      this.stageSum = clientQuoting.getAmount + 1
    //}
  }
}
