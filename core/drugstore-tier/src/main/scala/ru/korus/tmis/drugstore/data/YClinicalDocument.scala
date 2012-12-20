package ru.korus.tmis.drugstore.data

import org.hl7.v3._
import ru.korus.tmis.core.entity.model._
import ru.korus.tmis.drugstore.util.Xmlable

import ru.korus.tmis.drugstore.data.jaxb._

import java.io.ByteArrayOutputStream
import javax.xml.bind.JAXBContext
import javax.xml.bind.annotation.XmlTransient

import reflect.BeanProperty
import xml.{PrettyPrinter, XML}

class YClinicalDocument extends Xmlable {
  @XmlTransient
  val hl7Factory = new ObjectFactory

  @BeanProperty
  var inner = new POCDMT000040ClinicalDocument()

  inner.setId(new YClinicalDocumentId(this))
  inner.getRealmCode.add(new YRealmCodeCS)
  inner.setTypeId(new POCDMT000040InfrastructureRootTypeId)
  inner.setCode(new YAdministrationCodeCE)
  inner.setEffectiveTime(new YGenericTimeTS)
  inner.setConfidentialityCode(new YConfidentialityCodeCE)
  inner.setLanguageCode(new YLanguageCodeCS)

  def setOrgRef(orgRef: String) = {
    inner.setCustodian(new POCDMT000040Custodian(/*orgRef*/))
  }

  def setPatient(patient: Patient) = {
    val rt = new POCDMT000040RecordTarget
    val pr = new POCDMT000040PatientRole
    val p = new POCDMT000040Patient

    p.getName.add(new YPatientPN(patient))
    p.setAdministrativeGenderCode(new YGenderCodeCE(patient))
    p.setBirthTime(new YBirthTimeTS(patient))

    pr.getId.add(new YPatientId(patient))
    pr.setPatient(p)

    rt.setPatientRole(pr)

    inner.getRecordTarget.add(rt)
  }

  def setAuthor(doctor: Staff) = {
    val a = new POCDMT000040Author
    val aa = new POCDMT000040AssignedAuthor
    val p = new POCDMT000040Person

    p.getName.add(new YStaffPN(doctor))

    aa.getId.add(new YStaffId(doctor))
    aa.setAssignedPerson(p)

    a.setTime(new YAuthorTimeTS)
    a.setAssignedAuthor(aa)

    inner.getAuthor.add(a)
  }

  def setEvent(event: Event) = {
    val c1 = new POCDMT000040Component1
    val enc = new POCDMT000040EncompassingEncounter

    enc.getId.add(new YEventId(event))
    enc.setEffectiveTime(new YNullTimeTS)

    c1.setEncompassingEncounter(enc)

    inner.setComponentOf(c1)
  }

  def addDrugRequest(treatment: Action,
                     dosage: List[(ActionProperty, java.util.List[APValue])],
                     drug: Nomenclature,
                     timing: Iterable[AssignmentHour]) = {
    addDrug(treatment,
      dosage,
      drug,
      timing,
      XDocumentSubstanceMood.RQO,
      false)
  }

  def addDrugEvent(treatment: Action,
                   dosage: List[(ActionProperty, java.util.List[APValue])],
                   drug: Nomenclature,
                   timing: Iterable[AssignmentHour]) = {
    addDrug(treatment,
      dosage,
      drug,
      timing,
      XDocumentSubstanceMood.EVN,
      false)
  }

  def addDrugRequestCancel(treatment: Action,
                           dosage: List[(ActionProperty, java.util.List[APValue])],
                           drug: Nomenclature,
                           timing: Iterable[AssignmentHour]) = {
    addDrug(treatment,
      dosage,
      drug,
      timing,
      XDocumentSubstanceMood.RQO,
      true)
  }

  def addDrugEventCancel(treatment: Action,
                         dosage: List[(ActionProperty, java.util.List[APValue])],
                         drug: Nomenclature,
                         timing: Iterable[AssignmentHour]) = {
    addDrug(treatment,
      dosage,
      drug,
      timing,
      XDocumentSubstanceMood.EVN,
      true)
  }

  def addDrug(treatment: Action,
              dosage: List[(ActionProperty, java.util.List[APValue])],
              drug: Nomenclature,
              timing: Iterable[AssignmentHour],
              moodCode: XDocumentSubstanceMood,
              negation: Boolean): Unit = {
    val c = new POCDMT000040Component3
    val s = new POCDMT000040Section
    val e = new POCDMT000040Entry
    val administration = new POCDMT000040SubstanceAdministration
    val consumable = new POCDMT000040Consumable
    val mp = new POCDMT000040ManufacturedProduct
    val mld = new POCDMT000040LabeledDrug

    administration.setClassCode(ActClass.valueOf("SBADM"))
    administration.setMoodCode(moodCode)
    administration.setNegationInd(negation)
    administration.getId.add(new YDrugId(treatment))
    administration.getId.add(new YFinanceId)

    timing.foldLeft(administration)(
      (a, t) => {
        a.getEffectiveTime.add(new YDrugTimeTS(t))
        a
      })

    dosage.foldLeft(administration)(
      (a, d) => {
        a.setDoseQuantity(new YDrugDosagePQ(drug, d))
        a
      })

    mld.setCode(new YDrugCodeCE(drug))

    mp.setManufacturedLabeledDrug(mld)
    consumable.setManufacturedProduct(mp)
    administration.setConsumable(consumable)
    administration.setRouteCode(new YRouteCodeCE)

    e.setSubstanceAdministration(administration)
    s.getEntry.add(e)
    c.setSection(s)

    val rootComponent = new POCDMT000040Component2
    val sBody = new POCDMT000040StructuredBody
    sBody.getComponent.add(c)
    rootComponent.setStructuredBody(sBody)

    inner.setComponent(rootComponent)
  }

  def innerJaxbElement = {
    hl7Factory.createClinicalDocument(inner)
  }

  val m = JAXBContext.newInstance(classOf[YClinicalDocument]).createMarshaller
  val pp = new PrettyPrinter(160, 2)

  def toXmlString = {
    val s = new ByteArrayOutputStream()
    val sb = new StringBuilder
    m.marshal(this.innerJaxbElement, s)
    pp.format(XML.loadString(s.toString), sb)
    sb.toString
  }

  def toXmlDom = {
    var root = domBuilder.newDocument()
    m.marshal(this.innerJaxbElement, root)
    root
  }
}
