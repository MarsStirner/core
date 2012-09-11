package ru.korus.tmis.laboratory.business

import javax.xml.ws.{BindingProvider, Service}
import java.net.URL
import javax.xml.namespace.QName

import ru.korus.tmis.util.General.cast_implicits
import javax.xml.ws.handler.Handler
import javax.xml.ws.handler.MessageContext
import java.util.LinkedList

/**
 * Wrapper for web service with EXACTLY one port
 */

import ru.korus.tmis.util.reflect.Manifests.{actuallyWorkingClassOf=>classOf}

class WebServiceWrapper[Port: Manifest](wsdl: URL, serviceAddr: QName) {
  import BindingProvider._

  private[this] val service = new Service(wsdl, serviceAddr)
  val port: Port = service.getPort[Port](service.getPorts.next, classOf[Port])
  private[this] val bp = port.asSafe[BindingProvider]

  def serviceUrl_=(v: URL) = bp foreach { it => it.getRequestContext.put(ENDPOINT_ADDRESS_PROPERTY,v.toString) }
  def username_=(v: String) = bp foreach { it => it.getRequestContext.put(USERNAME_PROPERTY,v) }
  def password_=(v: String) = bp foreach { it => it.getRequestContext.put(PASSWORD_PROPERTY,v) }

  type MessageHandler = Handler[_ <: MessageContext]

  def addHandler(handler: MessageHandler) = {
    bp foreach  { bp =>
      val handlerChain = Option(bp.getBinding.getHandlerChain).getOrElse(new LinkedList[MessageHandler]())
      if(!handlerChain.contains(handler)) handlerChain.add(handler)
      bp.getBinding.setHandlerChain(handlerChain)
    }
  }

}
