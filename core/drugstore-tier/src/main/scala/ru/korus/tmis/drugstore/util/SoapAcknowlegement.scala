package ru.korus.tmis.drugstore.util

import javax.xml.soap.SOAPMessage

trait SoapAcknowlegement extends Soaping {

  override def validateResponse(response: SOAPMessage) = {
    val scXML = Xmlable.asScalaXml(response.getSOAPBody)
    scXML \\ "acknowledgement" \ "@typeCode" text match {
      case "AA" | "CA" => info("Succesfully interacted with 1C drugstore server")
      case _ => error("Error response from 1C drugstore server")
    }
  }

}
