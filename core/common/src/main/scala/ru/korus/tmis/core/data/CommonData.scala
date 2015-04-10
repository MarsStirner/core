package ru.korus.tmis.core.data


import scala.beans.BeanProperty
import java.lang.Integer
import java.util.Date
import javax.xml.bind.annotation._
import org.codehaus.jackson.annotate.JsonIgnoreProperties
import ru.korus.tmis.core.entity.model.layout.LayoutAttributeValue
import ru.korus.tmis.scala.util.ConfigManager
import scala.beans.BooleanBeanProperty
import java.util
import scala.collection.JavaConverters._


@XmlType(name = "entities")
@XmlRootElement(name = "entities")
@JsonIgnoreProperties(ignoreUnknown = true)
class CommonData {

  @BeanProperty var id: Integer = _

  @BeanProperty var version: String = _

  @BeanProperty var entity = new util.LinkedList[CommonEntity]

  def this(id: Integer) = {
    this()
    this.id = id
  }

  def this(id: Integer, version: String) = {
    this(id)
    this.version = version
  }

  def add(that: CommonEntity) = {
    entity.add(that)
    this
  }

}

@XmlType(name = "entity")
@XmlRootElement(name = "entity")
@JsonIgnoreProperties(ignoreUnknown = true)
class CommonEntity {

  @BeanProperty var id: Integer = _

  @BeanProperty var version: Integer = _

  @BeanProperty var name: String = _

  @BeanProperty var `type`: String = _

  @BeanProperty var typeId: Integer = _

  @BeanProperty var status: Integer = _

  @BeanProperty var code: String = _

  @BooleanBeanProperty var isEditable: Boolean = true

  @BeanProperty var flatCode: String = _

  @BeanProperty var mnem: String = _

  @BeanProperty var context: String = _

  @BeanProperty var group = new util.LinkedList[CommonGroup]

  @BeanProperty
  var lockInfo: LockInfoContainer = _

  private def this(id: Integer,
                   name: String,
                   eType: String,
                   eTypeId: Integer) = {
    this()
    this.id = id
    this.name = name
    this.`type` = eType
    this.typeId = eTypeId
  }

  private def this(id: Integer,
                   version: Integer,
                   name: String,
                   eType: String,
                   eTypeId: Integer) = {
    this(id, name, eType, eTypeId)
    this.version = version
  }

  private def this(id: Integer,
                   version: Integer,
                   name: String,
                   eType: String,
                   eTypeId: Integer,
                   status: Integer) = {
    this(id, version, name, eType, eTypeId)
    this.status = status
  }

  def this(id: Integer,
           version: Integer,
           name: String,
           eType: String,
           eTypeId: Integer,
           status: Integer,
           code: String) = {
    this(id, version, name, eType, eTypeId, status)
    this.code = code
  }

  def this(id: Integer,
           version: Integer,
           name: String,
           eType: String,
           eTypeId: Integer,
           status: Integer,
           code: String,
           flatCode: String,
           mnem: String,
           context: String) = {
    this(id, version, name, eType, eTypeId, status, code)
    this.flatCode = flatCode
    this.mnem = mnem
    this.context = context
  }

  def add(that: CommonGroup) = {
    group.add(that)
    this
  }
}

@XmlType(name = "group")
@XmlRootElement(name = "group")
@JsonIgnoreProperties(ignoreUnknown = true)
class CommonGroup {

  @BeanProperty var id: Integer = _

  @BeanProperty var version: Integer = _

  @BeanProperty var name: String = _

  @BeanProperty var attribute = new util.LinkedList[CommonAttribute]

  def this(id: Integer, name: String) = {
    this()
    this.id = id
    this.name = name
  }

  def this(id: Integer, version: Integer, name: String) = {
    this(id, name)
    this.version = version
  }

  def add(that: CommonAttribute): CommonGroup = {
    if(that != null) attribute.add(that)
    this
  }

  def add(that: Iterable[CommonAttribute]): CommonGroup = {
    that.foldLeft(this)((group, cp) => group.add(cp))
    this
  }
}
