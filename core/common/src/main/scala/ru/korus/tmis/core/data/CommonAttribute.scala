package ru.korus.tmis.core.data

import java.util
import java.util.Date
import javax.xml.bind.annotation._

import org.codehaus.jackson.annotate.JsonIgnoreProperties
import ru.korus.tmis.core.entity.model.layout.LayoutAttributeValue
import ru.korus.tmis.scala.util.ConfigManager

import scala.beans.BeanProperty
import scala.collection.JavaConverters._

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        09.04.2015, 15:12 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@XmlType(name = "attribute")
@XmlRootElement(name = "attribute")
@JsonIgnoreProperties(ignoreUnknown = true)
class CommonAttribute {

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

  @BeanProperty var calculatedValue: APValueContainer = _

  @BeanProperty var tableValues: java.util.List[TableCol] = _

  @BeanProperty var properties: java.util.List[PropertyPair] = new java.util.LinkedList[PropertyPair]

  @XmlTransient
  def getPropertiesMap = properties.asScala.map((p) => p.name -> p.value).toMap

  def addProperty(key: String, value: String) = if (value != null) properties.add(new PropertyPair(key, value))

  private def this(id: Integer,
                   name: String,
                   aType: String,
                   scope: String) = {
    this()
    this.id = id
    this.name = name
    this.`type` = if ("Diagnosis".equals(aType)) "Table" else aType
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
    if (value != null) addProperty("value", dateFormatter.format(value))
  }

  def this(id: Integer,
           version: Integer,
           name: String,
           aType: String,
           scope: String,
           value: Date) = {
    this(id, version, name, aType, scope)
    if (value != null) addProperty("value", dateFormatter.format(value))
  }

  private def this(id: Integer,
                   name: String,
                   aType: String,
                   scope: String,
                   value: String) = {
    this(id, name, aType, scope)
    addProperty("value", value)
  }

  def this(id: Integer,
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
    this.initProps(props)
  }

  def this(id: Integer,
           version: Integer,
           name: String,
           aType: String,
           scope: String,
           props: Map[String, String]) = {
    this(id, version, name, aType, scope)
    this.initProps(props)
  }

  def this(id: Integer,
           version: Integer,
           name: String,
           code: String,
           aType: String,
           mandatory: String,
           readOnly: String,
           scope: String,
           tableValues: java.util.List[TableCol],
           props: Map[String, String]) = {
    this(id, version, name, aType, scope, props)
    this.mandatory = mandatory
    this.readOnly = readOnly
    this.code = code
    this.tableValues = tableValues
  }

  private def initProps(map: Map[String, String]) = {
    map.foreach((property) => {
      addProperty(property._1, property._2)
    })
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

@XmlType
@JsonIgnoreProperties(ignoreUnknown = true)
class TableCol(id: Integer = null,
               val values: java.util.List[TableValue] = new java.util.LinkedList[TableValue]()) {
  def this() = {
    this(null)
  }

}

@XmlType
@JsonIgnoreProperties(ignoreUnknown = true)
class TableValue(val value: Object,
                 valueType: String = null) {
  def this() = {
    this(null)
  }
  def asString = {
    value match {
      case s: String => s
      case _ => ""
    }
  }
}
