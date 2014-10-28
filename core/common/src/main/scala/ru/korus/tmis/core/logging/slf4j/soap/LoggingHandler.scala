package ru.korus.tmis.core.logging.slf4j.soap


import java.io.ByteArrayOutputStream
import javax.xml.ws.handler.MessageContext
import javax.xml.ws.handler.soap.{SOAPHandler, SOAPMessageContext}

import grizzled.slf4j.Logging
import javax.servlet.http.HttpServletRequest

import ru.korus.tmis.scala.util.{General, I18nable}
import General.nullity_implicits
import javax.xml.rpc.handler.HandlerInfo
import javax.xml.rpc.handler.soap.{SOAPMessageContext => RPCSOAPMessageContext}
import javax.xml.rpc.handler
import javax.xml.soap.SOAPMessage
import scala.language.reflectiveCalls


// slf4j to soap logging adapter
class LoggingHandler
  extends SOAPHandler[SOAPMessageContext]
  with javax.xml.rpc.handler.Handler
  with Logging
  with I18nable {

  import General.flow_implicits
  import ru.korus.tmis.util.reflect.Manifests.{actuallyWorkingClassOf => classOf}

  val handlerInfo: HandlerInfo = new HandlerInfo().stating {
    _.setHandlerClass(classOf[LoggingHandler])
  }

  override def getHeaders = null

  override def handleMessage(context: SOAPMessageContext) = logMessage(context, fault = false)

  override def handleFault(context: SOAPMessageContext) = logMessage(context, fault = true)

  def logMessage(context: SOAPMessageContext, fault: Boolean) = {

    if (fault) {
      info("SOAP Fault!")
    }
    val strop = context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY).asInstanceOf[Boolean] match {
      case true => "=>"
      case false => "<=[" +
        (context.get(MessageContext.SERVLET_REQUEST)
          ?!! {
          _.asInstanceOf[HttpServletRequest].getRemoteAddr
        }
          getOrElse "Unknown host") +
        "]"
    }

    logMessageBody(context.getMessage, "SOAP" + strop + ":\n--\n" + "%s" + "\n--\n")



    true
  }

  def logMessageBody(mes: SOAPMessage, pattern: String) {
    val req = new ByteArrayOutputStream()
    mes match {
      case mes => {
        mes.writeTo(req)
        info(pattern.format(req.toString))
      }
    }
  }


  override def close(context: MessageContext) {}

  override def destroy() {}

  override def handleFault(context: handler.MessageContext) = {
    info("AXIS SOAP Fault!")
    logMessageBody(context.asInstanceOf[RPCSOAPMessageContext].getMessage, "SOAP:\n--\n" + "%s" + "\n--\n")
    true
  }

  override def handleRequest(context: handler.MessageContext) = {
    val con = context.asInstanceOf[RPCSOAPMessageContext]
    logMessageBody(con.getMessage, "AXIS SOAP=>:\n--\n" + "%s" + "\n--\n")
    true
  }

  override def handleResponse(context: handler.MessageContext) = {
    logMessageBody(context.asInstanceOf[RPCSOAPMessageContext].getMessage, "AXIS SOAP<=:\n--\n" + "%s" + "\n--\n")
    true
  }

  override def init(p1: HandlerInfo) {}
}

object LoggingHandler extends LoggingHandler
