package ru.korus.tmis.ws.impl

import ru.korus.tmis.core.exception.{AuthenticationException, CoreException}
import ru.korus.tmis.ws.medipad.AuthenticationWebService
import java.lang.String
import javax.ejb.{EJB, Stateless}
import javax.jws.HandlerChain

import scala.collection.JavaConversions._
import ru.korus.tmis.core.database.DbStaffBeanLocal
import ru.korus.tmis.core.auth.{AuthData, AuthStorageBeanLocal}
import ru.korus.tmis.core.data.RoleData
import ru.korus.tmis.scala.util.I18nable


@Stateless
@HandlerChain(file = "tmis-ws-logging-handlers.xml")
class AuthenticationWSImpl
  extends AuthenticationWebService
  with I18nable {
  @EJB
  var authStorage: AuthStorageBeanLocal = _

  @EJB
  var dbStaff: DbStaffBeanLocal = _

  def authenticate(userName: String, password: String, roleId: Int): AuthData = {
    try {
      authStorage.createToken(userName, password, roleId)
    } catch {
      case e: CoreException =>
        throw new AuthenticationException(e.getId, e.getMessage)
    }
  }

  def getRoles(login: String, password: String): RoleData = try {
    val roles = authStorage.getRoles(login, password).filter(_.getCode != "")
    val staff = dbStaff.getStaffByLogin(login)

    new RoleData(staff, roles)
  } catch {
    case e: CoreException =>
      throw new AuthenticationException(e.getId, e.getMessage)
  }
}
