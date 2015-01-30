package ru.korus.tmis.core.data


import scala.beans.BeanProperty
import java.lang.Integer
import java.util.{Date, LinkedList, HashMap}
import javax.xml.bind.annotation._
import javax.xml.bind.annotation.adapters.{XmlJavaTypeAdapter, XmlAdapter}
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
    that match {
      case null => {}
      case _ => attribute.add(that)
    }
    this
  }

  def add(that: Iterable[CommonAttribute]): CommonGroup = {
    that.foldLeft(this)((group, cp) => group.add(cp))
    this
  }
}

@XmlType(name = "attribute")
@XmlRootElement(name = "attribute")
@JsonIgnoreProperties(ignoreUnknown = true)
class CommonAttribute{

  @XmlTransient
  var dateFormatter = ConfigManager.DateFormatter

  @BeanProperty var id: Integer = _

  @BeanProperty var version: Integer = _

  @BeanProperty var name: String = _

  @BeanProperty var code: String = _

  @BeanProperty var `type`: String = _

  @BeanProperty var scope: String = _

  @BeanProperty var typeId: Integer = _

  @BeanProperty var mandatory: String = _

  @BeanProperty var readOnly: String = _

  @BeanProperty var calculatedValue:APValueContainer = _

  @BeanProperty var tableValues : java.util.LinkedList[java.util.LinkedList[String]] = _

  @BeanProperty var properties: java.util.List[PropertyPair] = new java.util.LinkedList[PropertyPair]

  @XmlTransient
  def getPropertiesMap = properties.asScala.map((p) => p.name -> p.value).toMap

  def addProperty(key: String, value: String) = if(value != null) properties.add(new PropertyPair(key, value))

  def apply(map: Map[String, String]) = {
    map.foreach((property) => {
      addProperty(property._1, property._2)
    })
    this
  }

  private def this(id: Integer,
                   name: String,
                   aType: String,
                   scope: String) = {
    this()
    this.id = id
    this.name = name
    this.`type` = aType
    this.scope = scope
  }

  def this(id: Integer,
           version: Integer,
           name: String,
           aType: String,
           scope: String) = {
    this(id, name, aType, scope)
    this.version = version
  }

  private def this(id: Integer,
                   name: String,
                   aType: String,
                   scope: String,
                   value: Date) = {
    this(id, name, aType, scope)
    value match {
      case null => {}
      case _ => addProperty("value", dateFormatter.format(value))
    }
  }

  def this(id: Integer,
           version: Integer,
           name: String,
           aType: String,
           scope: String,
           value: Date) = {
    this(id, version, name, aType, scope)
    value match {
      case null => {}
      case _ => addProperty("value", dateFormatter.format(value))
    }
  }

  private def this(id: Integer,
                   name: String,
                   aType: String,
                   scope: String,
                   value: String) = {
    this(id, name, aType, scope)
    addProperty("value", value)
  }

  def this(id: Integer, // now
           version: Integer,
           name: String,
           aType: String,
           scope: String,
           value: String) = {
    this(id, version, name, aType, scope)
    addProperty("value", value)
  }

  private def this(id: Integer,
                   name: String,
                   aType: String,
                   scope: String,
                   props: Map[String, String]) = {
    this(id, name, aType, scope)
    this.apply(props)
  }

  def this(id: Integer,
           version: Integer,
           name: String,
           aType: String,
           scope: String,
           props: Map[String, String]) = {
    this(id, version, name, aType, scope)
    this.apply(props)
  }

  def this(id: Integer,
           version: Integer,
           name: String,
           code: String,
           aType: String,
           mandatory: String,
           readOnly: String,
           scope: String,
           tableValues: java.util.LinkedList[java.util.LinkedList[String]],
           props: Map[String, String]) = {
    this(id, version, name, aType, scope, props)
    this.mandatory = mandatory
    this.readOnly = readOnly
    this.code = code
    this.tableValues = tableValues
  }
}

class CommonAttributeWithLayout(id: Integer,
                                version: Integer,
                                name: String,
                                code: String,
                                aType: String,
                                mandatory: String,
                                readOnly: String,
                                scope: String,
                                props: Map[String, String]) extends CommonAttribute (id, version, name, code, aType, mandatory, readOnly, scope, null, props){

  @BeanProperty
  var layoutAttributeValues = new util.LinkedList[LayoutAttributeSimplifyDataContainer]

  def this(id: Integer,
           version: Integer,
           name: String,
           code: String,
           aType: String,
           scope: String,
           props: Map[String, String],
           layout: List[LayoutAttributeValue],
           mandatory: String,
           readOnly: String) = {
    this(id, version, name, code, aType, mandatory, readOnly, scope, props)
    layout.foreach(f=> this.layoutAttributeValues.add(new LayoutAttributeSimplifyDataContainer(f)))
  }

  def this (ca: CommonAttribute,
            layout: List[LayoutAttributeValue] ) = {
    this(ca.id, ca.version, ca.name, ca.code, ca.`type`, ca.mandatory, ca.readOnly, ca.scope,  ca.getPropertiesMap)
    this.tableValues = ca.tableValues
    layout.foreach(f=> this.layoutAttributeValues.add(new LayoutAttributeSimplifyDataContainer(f)))
  }
}

@XmlType(name = "property")
@XmlRootElement(name = "property")
@XmlElement(name = "property")
class PropertyPair {

  @BeanProperty var name: String = _

  @BeanProperty var value: String = _

  def this(name: String, value: String) = {
    this()
    this.name = name
    this.value = value
  }
}
