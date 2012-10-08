package ru.korus.tmis.drugstore.data

import ru.korus.tmis.drugstore.util.ScalaXmlable
import ru.korus.tmis.drugstore.util.Xmlable._

import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import org.w3c.dom.Document

class YRcmrWrapperDocument(dom: Document) extends ScalaXmlable {

  val timeFormat = new SimpleDateFormat("yyyyMMddHHmmss")

  def base64(text: String) = {
    val ss = new ByteArrayOutputStream()

    val s = javax.mail.internet.MimeUtility.encode(ss, "base64")
    s.write(text.getBytes("UTF-8"))
    s.close()

    ss.toString
  }

  val xmlDeclaration = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"

  val xml = <RCMR_IN000002UV02
  ITSVersion="XML_1.0"
  xsi:schemaLocation="urn:hl7-org:v3 RCMR_IN000002UV02.xsd"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns="urn:hl7-org:v3">
    <id root={YUUID.generateRandom.toString}/>
    <creationTime value={timeFormat.format(new Date)}/>
    <interactionId extension='RCMR_IN000002UV02' root='2.16.840.1.113883.1.18'/>
    <processingCode code='P'/>
    <processingModeCode code="T"/>
    <acceptAckCode code="AL"/>
    <receiver typeCode="RCV">
      <device classCode="DEV" determinerCode="INSTANCE">
        <id nullFlavor="NI"/>
      </device>
    </receiver>
    <sender typeCode="SND">
      <device classCode="DEV" determinerCode="INSTANCE">
        <id nullFlavor="NI"/>
      </device>
    </sender>
    <controlActProcess classCode="CACT" moodCode="EVN">
      <text mediaType="multipart/related">
        MIME-Version: 1.0
        Content-Type: multipart/related; boundary="HL7-CDA-boundary";
        type="text/xml";
        Content-Transfer-Encoding: BASE64

        --HL7-CDA-boundary
        Content-Type: text/xml; charset=UTF-8

        {base64(xmlDeclaration + asScalaXml(dom).toString)}
        --HL7-CDA-boundary--
      </text>
    </controlActProcess>
  </RCMR_IN000002UV02>

  override def toXml = xml

}
