package ru.korus.tmis.drugstore.util

import ru.korus.tmis.util.ConfigManager

import java.io.ByteArrayOutputStream
import java.net.URL
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.soap._
import org.w3c.dom.Document

import grizzled.slf4j.Logging

trait Soaping extends Logging {

  val domBuilderFactory = DocumentBuilderFactory.newInstance()
  domBuilderFactory.setNamespaceAware(true)

  lazy val domBuilder = domBuilderFactory.newDocumentBuilder()

  val CMD = ConfigManager.Drugstore

  def validateResponse(response: SOAPMessage) {}

  def sendSoapMessage(document: Document,
                      rootElementName: String,
                      soapAction: String,
                      soapOperation: String,
                      xsiType: String,
                      url: URL = CMD.ServiceUrl,
                      httpAuthToken: String = CMD.HttpAuthToken,
                      xmlNamespace: String = CMD.XmlNamespace): SOAPMessage = {

    var connection: SOAPConnection = null
    try {
      connection = SOAPConnectionFactory.newInstance().createConnection()

      var message = MessageFactory.newInstance().createMessage()
      message.getMimeHeaders.setHeader("SOAPAction",
        soapAction)
      message.getMimeHeaders.setHeader("Authorization",
        "Basic " + httpAuthToken)

      var soapBody = domBuilder.newDocument()
      var rootElement = soapBody.createElementNS(xmlNamespace, soapOperation)

      if (document.getDocumentElement != null) {
        document.renameNode(document.getDocumentElement,
          xmlNamespace,
          rootElementName)
        if (
          !document.getDocumentElement.hasAttributeNS(CMD.XsiNamespace, "type") &&
            xsiType != null && !xsiType.isEmpty
        ) {
          document.getDocumentElement.setAttributeNS(CMD.XsiNamespace, "type", xsiType)
        }
        var importedYcd = soapBody.importNode(document.getDocumentElement, true)
        rootElement.appendChild(importedYcd)
      }

      soapBody.appendChild(rootElement)

      var body = message.getSOAPBody
      body.addDocument(soapBody)
      message.saveChanges()

      val req = new ByteArrayOutputStream()
      message.writeTo(req)
      info("Sending to Drugstore: " + req.toString)

      var response = connection.call(message, url)

      val res = new ByteArrayOutputStream()
      response.writeTo(res)
      info("Got from Drugstore: " + res.toString)

      validateResponse(response)

      response
    }
    catch {
      case e: Exception => {
        error("Exception when sending message to 1C drugstore server", e)
        null
      }
    }
    finally {
      if (connection != null) {
        connection.close()
      }
    }
  }
}
