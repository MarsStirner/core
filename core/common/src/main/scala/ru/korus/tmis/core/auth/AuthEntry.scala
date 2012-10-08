package ru.korus.tmis.core.auth

import javax.xml.bind.annotation.{XmlRootElement, XmlType}
import reflect.BeanProperty

/**
 * Created with IntelliJ IDEA.
 * User: mmakankov
 * Date: 18.06.12
 * Time: 17:42
 * To change this template use File | Settings | File Templates.
 */
@XmlType(name = "auth2")
@XmlRootElement(name = "auth2")
class AuthEntry {

  @BeanProperty
  var login: String = _

  @BeanProperty
  var password: String = _

  @BeanProperty
  var roleId: Int = _

  def this(login: String, password: String, roleId: Int) = {
    this()
    this.login = login
    this.password = password
    this.roleId = roleId
  }
}
