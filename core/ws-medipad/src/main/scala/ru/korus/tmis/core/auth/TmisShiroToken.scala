package ru.korus.tmis.core.auth

import org.apache.shiro.authc.AuthenticationToken
import ru.korus.tmis.core.entity.model.Staff

class TmisShiroToken(authData: AuthData, staff: Staff) extends AuthenticationToken {
  def getPrincipal = {
    (staff.getLogin, authData.getUserRole)
  }

  def getCredentials = {
    staff.getPassword
  }
}
