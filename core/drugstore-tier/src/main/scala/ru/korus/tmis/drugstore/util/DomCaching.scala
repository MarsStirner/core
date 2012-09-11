package ru.korus.tmis.drugstore.util

import javax.xml.bind.annotation.XmlTransient

trait DomCaching extends Xmlable {
  @XmlTransient
  abstract override lazy val toXmlDom = super.toXmlDom
}
