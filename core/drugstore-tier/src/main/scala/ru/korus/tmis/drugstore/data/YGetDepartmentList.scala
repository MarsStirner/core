package ru.korus.tmis.drugstore.data

import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory
import org.xml.sax.InputSource

class YGetDepartmentListRequest(orgRefId: String) {

  val domBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()

  val xml =
    <OrganizationRef>
      {orgRefId}
    </OrganizationRef>

  def toXml = xml

  def toXmlDom = domBuilder.parse(new InputSource(new StringReader(xml.toString)))
}
