package ru.korus.tmis.drugstore.data.jaxb

import javax.xml.bind.annotation.XmlTransient
import org.hl7.v3._

class YPocdCustodian(@XmlTransient orgRef: String) extends PocdCustodian {
  val a = new PocdAssignedCustodian
  val o = new PocdCustodianOrganization

  o.getId.add(new YCustomId(orgRef))

  a.setRepresentedCustodianOrganization(o)
  setAssignedCustodian(a)
}
