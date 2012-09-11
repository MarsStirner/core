package ru.korus.tmis.core.auth

import ru.korus.tmis.core.entity.model.{Staff, Role}

import reflect.BeanProperty
import javax.xml.bind.annotation.{XmlType, XmlRootElement, XmlTransient}

@XmlType(name = "authData")
@XmlRootElement(name = "authData")
class AuthData() {

  @BeanProperty
  var authToken: AuthToken = _

  @BeanProperty
  var userId: Int = _

  @BeanProperty
  var userFirstName: String = _

  @BeanProperty
  var userLastName: String = _

  @BeanProperty
  var userPatronymicName: String = _

  @BeanProperty
  var userSpecs: String = _

  var userRole: Role = _

  @XmlTransient
  def getUserRole = {
    userRole
  }

  def setUserRole(userRole: Role) = {
    this.userRole = userRole
  }

  var user: Staff = _

  @XmlTransient
  def getUser = {
    user
  }

  def setUser(user: Staff) = {
    this.user = user
  }

  def this(token: AuthToken,
           user: Staff,
           userId: Int,
           userRole: Role,
           userFirstName: String,
           userLastName: String,
           userPatronymicName: String,
           userSpecs: String) = {
    this ()
    this.authToken = token
    this.user = user
    this.userId = userId
    this.userFirstName = userFirstName
    this.userLastName = userLastName
    this.userPatronymicName = userPatronymicName
    this.userSpecs = userSpecs
    this.userRole = userRole
  }

  override def toString = {
    val sb = new StringBuilder

    sb.append("AuthData {")
    sb.append(" authToken: ")
    sb.append(authToken)
    sb.append(" userId: ")
    sb.append(userId)
    sb.append(" firstName: ")
    sb.append(userFirstName)
    sb.append(" lastName: ")
    sb.append(userLastName)
    sb.append(" patrName: ")
    sb.append(userPatronymicName)
    sb.append(" role: ")
    sb.append(userRole)
    sb.append(" spec: ")
    sb.append(userSpecs)
    sb.append(" }")

    sb.toString
  }
}
