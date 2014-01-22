package ru.korus.tmis.core.logging.slf4j.soap.slick


import java.io.ByteArrayOutputStream
import javax.xml.ws.handler.MessageContext
import javax.xml.ws.handler.soap.{SOAPHandler, SOAPMessageContext}

import grizzled.slf4j.Logging
import javax.servlet.http.HttpServletRequest

import ru.korus.tmis.scala.util.{General, I18nable}
import General.nullity_implicits
import ru.korus.tmis.scala.util.I18nable

// slf4j to soap logging adapter, logging only ips
class LoggingHandler
  extends SOAPHandler[SOAPMessageContext]
  with Logging
  with I18nable {

  override def getHeaders() = null

  override def handleMessage(context: SOAPMessageContext) = logMessage(context)

  override def handleFault(context: SOAPMessageContext) = logMessage(context)

  def logMessage(context: SOAPMessageContext) = {

    val strop = context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY).asInstanceOf[Boolean] match {
      case true => "=>"
      case false => "<=[" +
        (context.get(MessageContext.SERVLET_REQUEST)
          ?!! {
          _.asInstanceOf[HttpServletRequest].getRemoteAddr
        }
          getOrElse "Unknown host") +
        "]"
      case _ => "???"
    }


    val req = new ByteArrayOutputStream()
    context.getMessage match {
      case mes => {
        mes.writeTo(req)
        info("SOAP" + strop)
      }
    }

    true
  }


  override def close(context: MessageContext) {}
}

object LoggingHandler extends LoggingHandler
