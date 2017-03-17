package ru.korus.tmis.core.auth

import scala.beans.BeanProperty
import javax.xml.bind.annotation.{XmlType, XmlRootElement}

@XmlType(name = "authToken")
@XmlRootElement(name = "authToken")
class AuthToken() {

  @BeanProperty
  var id: String = _

  def this(id: String) = {
    this()
    this.id = id
  }

  override def toString: String = {
    id
  }

  override def equals(obj: Any): Boolean = {
    obj match {
      case other: AuthToken => {
        other.getClass() == getClass() && other.id == id
      }
      case _ => false
    }
  }

  override def hashCode: Int = {
    id.hashCode
  }
}
