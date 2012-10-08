package ru.korus.tmis.core.data

import ru.korus.tmis.core.entity.model.Role
import ru.korus.tmis.util.ConfigManager

import java.util.{LinkedList, Set}
import javax.xml.bind.annotation.{XmlType, XmlRootElement}

import reflect.BeanProperty
import scala.collection.JavaConversions._
import ru.korus.tmis.core.auth.AuthData
import javax.xml.bind.annotation.XmlType._
import ru.korus.tmis.core.entity.model.{Staff, Role}

@XmlType(name = "roles")
@XmlRootElement(name = "roles")
class RoleData {

  @BeanProperty
  //var user: StaffEntity = _
  var doctor: DoctorSpecsContainer = _

  @BeanProperty
  var roles = new LinkedList[RoleEntry]

  def this(staffEn: Staff, roles: Set[Role]) = {
    this ()
    this.doctor = new DoctorSpecsContainer(staffEn)
    roles.foreach(r => this.roles.add(new RoleEntry(r)))
  }
}

@XmlType(name = "role")
@XmlRootElement(name = "role")
class RoleEntry {

  @BeanProperty
  var id: Int = _

  @BeanProperty
  var code: String = _

  @BeanProperty
  var name: String = _

  @BeanProperty
  var right = new LinkedList[UserRightEntry]()

  def this(role: Role) = {
    this ()
    this.id = role.getId.intValue()
    this.code = role.getCode
    this.name = role.getName

    val rolePermissions = role.getRights.map(_.getCode)
    ConfigManager.TmisAuth.SupportedPermissions.foreach(
      p => {
        this.right.add(new UserRightEntry(p, rolePermissions.contains(p)))
      })
  }
}

@XmlType(name = "right")
@XmlRootElement(name = "right")
class UserRightEntry {

  @BeanProperty
  var code: String = _

  @BeanProperty
  var isPermitted: Boolean = _

  def this(code: String, isPermitted: Boolean) = {
    this ()
    this.code = code
    this.isPermitted = isPermitted
  }
}

@XmlType(name = "staffEntity")
@XmlRootElement(name = "staffEntity")
class StaffEntity {

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

  def this(userId: Int,
           userFirstName: String,
           userLastName: String,
           userPatronymicName: String,
           userSpecs: String) = {
    this ()
    this.userId = userId
    this.userFirstName = userFirstName
    this.userLastName = userLastName
    this.userPatronymicName = userPatronymicName
    this.userSpecs = userSpecs
  }
}
