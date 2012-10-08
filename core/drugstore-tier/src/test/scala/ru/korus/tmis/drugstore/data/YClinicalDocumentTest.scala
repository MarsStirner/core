package ru.korus.tmis.drugstore.data

import ru.korus.tmis.core.entity.model._

import java.io.{StringReader, ByteArrayOutputStream}
import java.util.{LinkedList, Date}
import javax.xml.bind.JAXBContext
import org.junit.Test

import xml.{XML, PrettyPrinter}

class YClinicalDocumentTest {
  @Test
  def testJAXB() {
    val ycd = new YClinicalDocument

    ycd.setOrgRef("11111111-2222-3333-4444-555555555555")

    val e = new Event
    e.setId(1488)

    val a = new Action()
    a.setId(15)

    val p = new Patient
    p.setId(317)
    p.setFirstName("Иван")
    p.setPatrName("Петрович")
    p.setLastName("Сидоров")
    p.setBirthDate(new Date)
    p.setSex(3)

    val spec = new Speciality
    spec.setName("Менеджер")

    val d = new Staff
    d.setId(17)
    d.setFirstName("Мария")
    d.setPatrName("Мережевна")
    d.setLastName("Мартынова")
    d.setSpeciality(spec)

    val drug = new Nomenclature
    drug.setId(666)
    drug.setForm("табл.")
    val timing = List(new AssignmentHour(666, new Date),
      new AssignmentHour(777, new Date))

    val apv: APValue = new APValueString()
    apv.setValueFromString("25 мг")
    val apvs = new LinkedList[APValue]
    apvs.add(apv)
    val dosage = List((new ActionProperty, apvs))

    ycd.setPatient(p)
    ycd.setAuthor(d)
    ycd.setEvent(e)
    ycd.addDrugRequest(a, dosage, drug, timing)

    val jaxb = JAXBContext.newInstance(classOf[YClinicalDocument])
    val m = jaxb createMarshaller
    val um = jaxb createUnmarshaller

    val s = new ByteArrayOutputStream()
    m.marshal(ycd.innerJaxbElement, s)

    val pp = new PrettyPrinter(160, 2)
    val sb = new StringBuilder
    pp.format(XML.loadString(s.toString), sb)
    println(sb.toString)
    val dc = um.unmarshal(new StringReader(s.toString()))

    val wrapped = new YRcmrWrapperDocument(ycd.toXmlDom)
    println(wrapped.toXml)
  }
}
