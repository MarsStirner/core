package ru.korus.tmis.core.auth

import ru.korus.tmis.core.entity.model.Role

import org.apache.shiro.authc.{SimpleAuthenticationInfo, AuthenticationToken}
import org.apache.shiro.authz.SimpleAuthorizationInfo
import org.apache.shiro.realm.AuthorizingRealm
import org.apache.shiro.subject.PrincipalCollection


import scala.collection.JavaConversions._
import ru.korus.tmis.scala.util.ConfigManager

class TmisShiroRealm
  extends AuthorizingRealm
 {

  setAuthenticationTokenClass(classOf[TmisShiroToken])

  def doGetAuthenticationInfo(p1: AuthenticationToken): SimpleAuthenticationInfo = {
    val (login, role) = p1.getPrincipal.asInstanceOf[Tuple2[String, Role]]
    val password = p1.getCredentials.asInstanceOf[String]

    new SimpleAuthenticationInfo(
      (login, role),
      password,
      ConfigManager.TmisAuth.RealmName
    )
  }

  def doGetAuthorizationInfo(p1: PrincipalCollection): SimpleAuthorizationInfo = {
    val (login, role) = p1.getPrimaryPrincipal.asInstanceOf[Tuple2[String, Role]]

    val permissions = new SimpleAuthorizationInfo()
    permissions.addRole(role.getCode)
    permissions.addStringPermissions(role.getRights.map(_.getCode))
    permissions
  }
}
