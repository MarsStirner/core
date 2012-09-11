package ru.korus.tmis.core.auth

import ru.korus.tmis.core.entity.model.Role
import ru.korus.tmis.util.ConfigManager

import org.apache.shiro.authc.{SimpleAuthenticationInfo, AuthenticationToken}
import org.apache.shiro.authz.SimpleAuthorizationInfo
import org.apache.shiro.realm.AuthorizingRealm
import org.apache.shiro.subject.PrincipalCollection

import grizzled.slf4j.Logging
import scala.collection.JavaConversions._

class TmisShiroRealm
  extends AuthorizingRealm
  with Logging {

  setAuthenticationTokenClass(classOf[TmisShiroToken])

  def doGetAuthenticationInfo(p1: AuthenticationToken) = {
    val (login, role) = p1.getPrincipal.asInstanceOf[Tuple2[String, Role]]
    val password = p1.getCredentials.asInstanceOf[String]

    new SimpleAuthenticationInfo(
      (login, role),
      password,
      ConfigManager.TmisAuth.RealmName
    )
  }

  def doGetAuthorizationInfo(p1: PrincipalCollection) = {
    val (login, role) = p1.getPrimaryPrincipal.asInstanceOf[Tuple2[String, Role]]

    val permissions = new SimpleAuthorizationInfo()
    permissions.addRole(role.getCode)
    permissions.addStringPermissions(role.getRights.map(_.getCode))
    permissions
  }
}
