package ru.korus.tmis.ws.impl

import ru.korus.tmis.core.exception.{CoreException, AuthenticationException}
import ru.korus.tmis.ws.medipad.AuthenticationWebService
import ru.korus.tmis.util.I18nable
import java.lang.String
import javax.ejb.EJB
import javax.jws.{HandlerChain, WebService}
import scala.collection.JavaConversions._
import javax.inject.Named
import ru.korus.tmis.core.database.DbStaffBeanLocal
import ru.korus.tmis.core.auth.{AuthToken, AuthData, AuthStorageBeanLocal}
import ru.korus.tmis.core.data.{DoctorSpecsContainer, StaffEntity, RoleData}


@Named
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

  @EJB
  var dbStaff: DbStaffBeanLocal = _

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
      val staff = dbStaff.getStaffByLogin(login)

      new RoleData(staff, roles)
    } catch {
      case e: CoreException => {
        throw new AuthenticationException(e.getId, e.getMessage())
      }
    }
  }
}
