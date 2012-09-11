package ru.korus.tmis.ws.medipad.impl

import ru.korus.tmis.core.auth.AuthStorageBeanLocal
import ru.korus.tmis.core.data.RoleData
import ru.korus.tmis.core.exception.{CoreException, AuthenticationException}
import ru.korus.tmis.ws.medipad.AuthenticationWebService
import ru.korus.tmis.util.I18nable

import java.lang.String
import javax.ejb.EJB
import javax.jws.{HandlerChain, WebService}

import scala.collection.JavaConversions._

@WebService(
  endpointInterface = "ru.korus.tmis.ws.medipad.AuthenticationWebService",
  targetNamespace = "http://korus.ru/tmis/authentication",
  serviceName = "tmis-auth",
  portName = "authentication",
  name = "authentication"
)
@HandlerChain(file = "tmis-ws-logging-handlers.xml")
class AuthenticationWSImpl
  extends AuthenticationWebService
  with I18nable {
  @EJB
  var authStorage: AuthStorageBeanLocal = _

  def authenticate(userName: String, password: String, roleId: Int) = {
    try {
      authStorage.createToken(userName, password, roleId)
    } catch {
      case e: CoreException => {
        throw new AuthenticationException(e.getId, e.getMessage())
      }
    }
  }

  def getRoles(login: String, password: String) = {
    try {
      val roles = authStorage.getRoles(login, password).filter(_.getCode != "")
      new RoleData(roles)
    } catch {
      case e: CoreException => {
        throw new AuthenticationException(e.getId, e.getMessage())
      }
    }
  }
}
