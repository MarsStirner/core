package ru.korus.tmis.core.auth

import org.apache.shiro.authc.AuthenticationToken
import ru.korus.tmis.core.entity.model.{Role, Staff}

class TmisShiroToken(authData: AuthData, staff: Staff) extends AuthenticationToken {
  def getPrincipal: (String, Role) = {
    (staff.getLogin, authData.getUserRole)
  }

  def getCredentials: String = {
    staff.getPassword
  }
}
