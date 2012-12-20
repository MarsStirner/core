package ru.korus.tmis.drugstore.data.jaxb

import javax.xml.bind.annotation.XmlTransient
import org.hl7.v3._

class YPocdCustodian(@XmlTransient orgRef: String) extends POCDMT000040Custodian {
  val a = new POCDMT000040AssignedCustodian
  val o = new POCDMT000040CustodianOrganization

  o.getId.add(new YCustomId(orgRef))

  a.setRepresentedCustodianOrganization(o)
  setAssignedCustodian(a)
}
