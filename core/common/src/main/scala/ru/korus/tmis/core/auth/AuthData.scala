package ru.korus.tmis.core.auth

import ru.korus.tmis.core.entity.model.{Staff, Role}

import scala.beans.BeanProperty
import javax.xml.bind.annotation.{XmlType, XmlRootElement, XmlTransient}
import ru.korus.tmis.core.data.DoctorSpecsContainer

/**
 * Контейнер с данным о авторизации
 */
@XmlType(name = "authData")
@XmlRootElement(name = "authData")
class AuthData() {

  @BeanProperty
  var authToken: AuthToken = _

  @BeanProperty
  var doctor: DoctorSpecsContainer = _

  @BeanProperty
  var userId: Int = _                               //Оставлено для медипад

  @BeanProperty
  var userFirstName: String = _                     //Оставлено для медипад

  @BeanProperty
  var userLastName: String = _                      //Оставлено для медипад

  @BeanProperty
  var userPatronymicName: String = _                //Оставлено для медипад

  @BeanProperty
  var userSpecs: String = _                         //Оставлено для медипад


  @BeanProperty
  var roles: java.util.Set[Role] = _

  var userRole: Role = _

  /**
   * Получение Роли как Role entity
   * @return Role entity
   */
  @XmlTransient
  def getUserRole = {
    userRole
  }

  /**
   * Установить роль пользователю
   * @param userRole  Роль пользователя как Role
   */
  def setUserRole(userRole: Role) = {
    this.userRole = userRole
  }

  /**
   * Конструктор AuthData
   * @param token Токен как AuthToken
   * @param user Пользователь как Staff
   * @param userId Идентификатор пользователя (для mediPad)
   * @param userRole Роль пользователя (для mediPad)
   * @param userFirstName Имя пользователя (для mediPad)
   * @param userLastName Фамилия пользователя (для mediPad)
   * @param userPatronymicName Отчество пользователя (для mediPad)
   * @param userSpecs Специальность пользователя (для mediPad)
   */
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
    this.userId = userId
    this.userFirstName = userFirstName
    this.userLastName = userLastName
    this.userPatronymicName = userPatronymicName
    this.userSpecs = userSpecs
    this.userRole = userRole
    this.doctor = new DoctorSpecsContainer(user)
    this.roles = user.getRoles;
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
