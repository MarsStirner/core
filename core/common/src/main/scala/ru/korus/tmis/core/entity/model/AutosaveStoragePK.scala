package ru.korus.tmis.core.entity.model

import scala.beans.BeanProperty

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 4/21/14
 * Time: 4:11 PM
 */
class AutosaveStoragePK extends Serializable {

  @BeanProperty
  var id: String = _

  @BeanProperty
  var userId: Int = _

  def this(id: String, userId: Int) = {
    this()
    this.id = id
    this.userId = userId
  }

  override def hashCode(): Int = {
    this.id.hashCode()
  }

  override def equals(obj: Any): Boolean = {
    if (obj == this) return true
    if (!obj.isInstanceOf[AutosaveStoragePK]) return false
    val pk = obj.asInstanceOf[AutosaveStoragePK]
    pk.id.equals(this.id) && pk.userId.equals(this.userId)
  }

}
