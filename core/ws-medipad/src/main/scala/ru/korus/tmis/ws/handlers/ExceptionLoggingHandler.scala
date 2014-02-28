package ru.korus.tmis.ws.handlers


import java.io.ByteArrayOutputStream
import javax.ejb.EJB
import javax.xml.soap.SOAPMessage
import javax.xml.ws.handler.MessageContext
import javax.xml.ws.handler.soap.{SOAPHandler, SOAPMessageContext}

import grizzled.slf4j.Logging
import ru.korus.tmis.scala.util.I18nable

class ExceptionLoggingHandler
  extends SOAPHandler[SOAPMessageContext]
  with Logging
  with I18nable {

  var msg: SOAPMessage = _

  def getHeaders() = {
    null
  }

  def handleMessage(context: SOAPMessageContext) = {
    val isOutbound = context
      .get(MessageContext.MESSAGE_OUTBOUND_PROPERTY)
      .asInstanceOf[Boolean]

    if (!isOutbound) {
      msg = context.getMessage
    }

    true
  }

  def handleFault(context: SOAPMessageContext) = {
    val isOutbound = context
      .get(MessageContext.MESSAGE_OUTBOUND_PROPERTY)
      .asInstanceOf[Boolean]

    val req = new ByteArrayOutputStream()
    val res = new ByteArrayOutputStream()

    if (isOutbound) {
      if (msg != null) {
        msg.writeTo(req)
        context.getMessage.writeTo(res)
      } else if (isOutbound) {
        context.getMessage.writeTo(res)
      }
    }

    true
  }

  def close(context: MessageContext) {}
}
