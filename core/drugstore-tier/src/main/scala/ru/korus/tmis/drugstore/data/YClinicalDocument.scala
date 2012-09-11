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
  var inner = new PocdClinicalDocument()

  inner.setId(new YClinicalDocumentId(this))
  inner.getRealmCode.add(new YRealmCodeCS)
  inner.setTypeId(new YClinicalDocumentTypeId)
  inner.setCode(new YAdministrationCodeCE)
  inner.setEffectiveTime(new YGenericTimeTS)
  inner.setConfidentialityCode(new YConfidentialityCodeCE)
  inner.setLanguageCode(new YLanguageCodeCS)

  def setOrgRef(orgRef: String) = {
    inner.setCustodian(new YPocdCustodian(orgRef))
  }

  def setPatient(patient: Patient) = {
    val rt = new PocdRecordTarget
    val pr = new PocdPatientRole
    val p = new PocdPatient

    p.getName.add(new YPatientPN(patient))
    p.setAdministrativeGenderCode(new YGenderCodeCE(patient))
    p.setBirthTime(new YBirthTimeTS(patient))

    pr.getId.add(new YPatientId(patient))
    pr.setPatient(p)

    rt.setPatientRole(pr)

    inner.getRecordTarget.add(rt)
  }

  def setAuthor(doctor: Staff) = {
    val a = new PocdAuthor
    val aa = new PocdAssignedAuthor
    val p = new PocdPerson

    p.getName.add(new YStaffPN(doctor))

    aa.getId.add(new YStaffId(doctor))
    aa.setAssignedPerson(p)

    a.setTime(new YAuthorTimeTS)
    a.setAssignedAuthor(aa)

    inner.getAuthor.add(a)
  }

  def setEvent(event: Event) = {
    val c1 = new PocdComponent1
    val enc = new PocdEncompassingEncounter

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
    val c = new PocdComponent3
    val s = new PocdSection
    val e = new PocdEntry
    val administration = new PocdSubstanceAdministration
    val consumable = new PocdConsumable
    val mp = new PocdManufacturedProduct
    val mld = new PocdLabeledDrug

    administration.getClassCode.add("SBADM")
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

    val rootComponent = new PocdComponent2
    val sBody = new PocdStructuredBody
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
