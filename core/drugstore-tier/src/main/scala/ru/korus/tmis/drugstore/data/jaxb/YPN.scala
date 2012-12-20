package ru.korus.tmis.drugstore.data.jaxb

import org.hl7.v3.{EnPrefix, PN, EnFamily, EnGiven, ObjectFactory}
import ru.korus.tmis.core.entity.model.{Staff, Patient}

import javax.xml.bind.annotation.XmlTransient

class YPatientPN(@XmlTransient p: Patient) extends PN {
  @XmlTransient
  val factory = new ObjectFactory

  val given = new EnGiven
  val family = new EnFamily

 // given.getContent.add(p.getFirstName + " " + p.getPatrName)
//  family.getContent.add(p.getLastName)

  getContent.add(factory.createENGiven(given))
  getContent.add(factory.createENFamily(family))
}

class YStaffPN(@XmlTransient s: Staff) extends PN {
  @XmlTransient
  val factory = new ObjectFactory

  val prefix = new EnPrefix
  val given = new EnGiven
  val family = new EnFamily

//  prefix.getContent.add(s.getSpeciality.getName)
//  given.getContent.add(s.getFirstName + " " + s.getPatrName)
//  family.getContent.add(s.getLastName)

  getContent.add(factory.createENPrefix(prefix))
  getContent.add(factory.createENGiven(given))
  getContent.add(factory.createENFamily(family))
}
