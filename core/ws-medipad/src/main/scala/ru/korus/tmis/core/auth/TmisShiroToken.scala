package ru.korus.tmis.core.auth

import org.apache.shiro.authc.AuthenticationToken

class TmisShiroToken(authData: AuthData) extends AuthenticationToken {
  def getPrincipal = {
    (authData.getUser.getLogin, authData.getUserRole)
  }

  def getCredentials = {
    authData.getUser.getPassword
  }
}
