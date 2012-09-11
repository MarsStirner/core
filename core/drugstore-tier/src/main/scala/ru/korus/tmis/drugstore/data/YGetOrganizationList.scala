package ru.korus.tmis.drugstore.data

import javax.xml.parsers.DocumentBuilderFactory

class YGetOrganizationListRequest {

  val domBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()

  val xml = null

  def toXml = xml

  def toXmlDom = domBuilder.newDocument()
}
