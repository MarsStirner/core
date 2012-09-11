package ru.korus.tmis.ws.medipad.handlers

import ru.korus.tmis.core.auth.{TmisShiroRealm, TmisShiroToken, AuthToken, AuthStorageBeanLocal}
import ru.korus.tmis.core.exception.{FaultBean, AuthenticationException}
import ru.korus.tmis.util.{I18nable, ConfigManager}

import grizzled.slf4j.Logging
import java.util.Date
import javax.ejb.EJB
import javax.xml.soap._
import javax.xml.ws.handler.MessageContext
import javax.xml.ws.handler.soap.{SOAPMessageContext, SOAPHandler}
import javax.xml.ws.soap.SOAPFaultException
import org.apache.shiro.SecurityUtils
import org.apache.shiro.mgt.DefaultSecurityManager

class AuthenticatingHandler
  extends SOAPHandler[SOAPMessageContext]
  with Logging
  with I18nable {

  object StaticInitializer {
    SecurityUtils.setSecurityManager(
      new DefaultSecurityManager(new TmisShiroRealm()));
  }

  StaticInitializer

  @EJB
  var authStorage: AuthStorageBeanLocal = _

  var currentUser = SecurityUtils.getSubject()

  def getHeaders() = {
    null
  }

  /**
   * Обработчик сообщений.
   *
   * NB! Все исключения, которые должны быть обработаны на клиентской стороне,
   * должны быть перехвачены и переданы в виде SOAPFaultException
   * c полем Fault, содержащим детальную информацию об исключении
   * (ID и текстовое сообщение)
   */
  def handleMessage(context: SOAPMessageContext) = {
    val incoming =
      !context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY).asInstanceOf[Boolean]

    if (incoming) {
      try {
        val tmisAuthToken = getTmisAuthToken(
          context.getMessage.getSOAPPart.getEnvelope.getHeader)

        tmisAuthToken match {
          case Some(token) => checkToken(token, context)
          case None => {
            error("No token found")
            throw new AuthenticationException(
              ConfigManager.TmisAuth.ErrorCodes.InvalidToken,
              i18n("error.invalidToken"))
          }
        }
      } catch {
        case e: AuthenticationException => {
          // Преобразуем CoreException в SOAPFaultException,
          // чтобы видеть правильный StackTrace на клиентской стороне
          throw new SOAPFaultException(
            createSOAPFault(context, e.getFaultInfo))
        }
      }
    }

    true
  }

  def checkToken(token: String, context: SOAPMessageContext) = {
    info("Received token: " + token)
    val authToken = new AuthToken(token)

    val authData = authStorage.getAuthData(authToken)
    if (authData != null) {
      info("Authentication accept found: " + authData)
    } else {
      error("No authentication accept found")
      throw new AuthenticationException(
        ConfigManager.TmisAuth.ErrorCodes.InvalidToken,
        i18n("error.invalidToken"))
    }

    val tokenEndDate = authStorage.getAuthDateTime(authToken)
    if (tokenEndDate != null) {
      if (tokenEndDate.before(new Date())) {
        error("Token period exceeded")
        throw new AuthenticationException(
          ConfigManager.TmisAuth.ErrorCodes.InvalidToken,
          i18n("error.tokenExceeded"))
      } else {
        info("Token is valid")
      }
    } else {
      error("Token end date not found")
      throw new AuthenticationException(
        ConfigManager.TmisAuth.ErrorCodes.InvalidToken,
        i18n("error.invalidToken"))
    }

    context.put(
      ConfigManager.TmisAuth.AuthDataPropertyName,
      authData)
    context.setScope(
      ConfigManager.TmisAuth.AuthDataPropertyName,
      MessageContext.Scope.APPLICATION)

    currentUser.login(new TmisShiroToken(authData))
  }

  def handleFault(context: SOAPMessageContext) = {
    true
  }

  def close(context: MessageContext) = {
    currentUser.logout()
  }

  private def getTmisAuthToken(hdr: SOAPHeader): Option[String] = {
    val tmisAuthTokenHdr =
      hdr.getChildElements(ConfigManager.TmisAuth.QName)
    if (tmisAuthTokenHdr.hasNext) {
      return Some(tmisAuthTokenHdr.next().asInstanceOf[SOAPElement].getValue)
    } else {
      return None
    }
  }

  private def createSOAPFault(context: SOAPMessageContext,
                              faultBean: FaultBean): SOAPFault = {

    assert(faultBean != null, "FaultBean must be set")

    val id = faultBean.getId
    val message = faultBean.getMessage

    val factory = SOAPFactory.newInstance()
    val fault = context.getMessage.getSOAPBody.addFault()

    fault.setFaultCode(SOAPConstants.SOAP_SENDER_FAULT)
    fault.setFaultString(message)

    val detail = fault.addDetail()
    detail.addChildElement(
      factory.createName("id")).addTextNode(id.toString)
    detail.addChildElement(
      factory.createName("description")).addTextNode(message)

    return fault
  }
}
