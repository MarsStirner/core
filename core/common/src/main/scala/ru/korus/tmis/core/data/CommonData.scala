package ru.korus.tmis.core.data

import ru.korus.tmis.util.ConfigManager

import reflect.BeanProperty
import java.lang.Integer
import java.util.{Date, LinkedList}
import javax.xml.bind.annotation._
import javax.xml.bind.annotation.adapters.{XmlJavaTypeAdapter, XmlAdapter}
import org.codehaus.jackson.annotate.JsonIgnoreProperties
import java.util
import ru.korus.tmis.core.entity.model.layout.LayoutAttributeValue
import javax.management.remote.rmi._RMIConnectionImpl_Tie

@XmlType(name = "entities")
@XmlRootElement(name = "entities")
@JsonIgnoreProperties(ignoreUnknown = true)
class CommonData {
  var id: Integer = _

  @XmlAttribute
  def getId() = {
    id
  }

  def setId(id: Integer) = {
    this.id = id
  }

  var version: String = _

  @XmlAttribute
  def getVersion() = {
    version
  }

  def setVersion(version: String) = {
    this.version = version
  }

  def this(id: Integer) = {
    this()
    this.id = id
  }

  def this(id: Integer, version: String) = {
    this(id)
    this.version = version
  }

  @BeanProperty
  var entity = new LinkedList[CommonEntity]

  def add(that: CommonEntity) = {
    entity.add(that)
    this
  }
}

@XmlType(name = "entity")
@XmlRootElement(name = "entity")
@JsonIgnoreProperties(ignoreUnknown = true)
class CommonEntity {
  var id: Integer = _

  @XmlAttribute
  def getId() = {
    id
  }

  def setId(id: Integer) = {
    this.id = id
  }

  var version: Integer = _

  @XmlAttribute
  def getVersion() = {
    version
  }

  def setVersion(version: Integer) = {
    this.version = version
  }

  var name: String = _

  @XmlAttribute
  def getName() = {
    name
  }

  def setName(name: String) = {
    this.name = name
  }

  var eType: String = _

  @XmlAttribute(name = "type")
  def getType() = {
    eType
  }

  def setType(eType: String) = {
    this.eType = eType
  }

  var eTypeId: Integer = _

  @XmlAttribute(name = "typeId")
  def getTypeId() = {
    eTypeId
  }

  def setTypeId(eTypeId: Integer) = {
    this.eTypeId = eTypeId
  }

  var status: Integer = _

  @XmlAttribute(name = "status")
  def getStatus() = {
    status
  }

  def setStatus(status: Integer) = {
    this.status = status
  }

  var code: String = _

  @XmlAttribute(name = "code")
  def getCode() = {
    code
  }

  def setCode(code: String) = {
    this.code = code
  }

  var isEditable: Boolean = true

  @XmlAttribute(name = "isEditable")
  def getIsEditable() = {
    isEditable
  }

  def setIsEditable(isEditable: Boolean) = {
    this.isEditable = isEditable
  }

  var flatCode: String = _

  @XmlAttribute(name = "flatCode")
  def getFlatCode() = {
    flatCode
  }

  def setFlatCode(flatCode: String) = {
    this.flatCode = flatCode
  }

  private def this(id: Integer,
                   name: String,
                   eType: String,
                   eTypeId: Integer) = {
    this()
    this.id = id
    this.name = name
    this.eType = eType
    this.eTypeId = eTypeId
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
           flatCode: String) = {
    this(id, version, name, eType, eTypeId, status, code)
    this.flatCode = flatCode
  }

  @BeanProperty
  var group = new LinkedList[CommonGroup]

  def add(that: CommonGroup) = {
    group.add(that)
    this
  }
}

@XmlType(name = "group")
@XmlRootElement(name = "group")
@JsonIgnoreProperties(ignoreUnknown = true)
class CommonGroup {
  var id: Integer = _

  @XmlAttribute
  def getId() = {
    id
  }

  def setId(id: Integer) = {
    this.id = id
  }

  var version: Integer = _

  @XmlAttribute
  def getVersion() = {
    version
  }

  def setVersion(version: Integer) = {
    this.version = version
  }

  var name: String = _

  @XmlAttribute
  def getName() = {
    name
  }

  def setName(name: String) = {
    this.name = name
  }

  def this(id: Integer, name: String) = {
    this()
    this.id = id
    this.name = name
  }

  def this(id: Integer, version: Integer, name: String) = {
    this(id, name)
    this.version = version
  }

  @BeanProperty
  var attribute = new LinkedList[CommonAttribute]

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
trait AbstractCommonAttribute {

  @XmlTransient
  var dateFormatter = ConfigManager.DateFormatter

  var id: Integer = _

  @XmlAttribute
  def getId() = {
    id
  }

  def setId(id: Integer) = {
    this.id = id
  }

  var version: Integer = _

  @XmlAttribute
  def getVersion() = {
    version
  }

  def setVersion(version: Integer) = {
    this.version = version
  }

  var name: String = _

  @XmlAttribute
  def getName() = {
    name
  }

  def setName(name: String) = {
    this.name = name
  }

  var code: String = _

  @XmlAttribute(name = "code")
  def getCode() = {
    code
  }

  def setCode(code: String) = {
    this.code = code
  }

  var aType: String = _

  @XmlAttribute(name = "type")
  def getType() = {
    aType
  }

  def setType(aType: String) = {
    this.aType = aType
  }

  var scope: String = _

  @XmlAttribute
  def getScope() = {
    scope
  }

  def setScope(scope: String) = {
    this.scope = scope
  }

  var typeId: Integer = _

  @XmlAttribute(name = "typeId")
  def getTypeId() = {
    typeId
  }

  def setTypeId(eTypeId: Integer) = {
    this.typeId = eTypeId
  }

  var mandatory: String = _

  @XmlAttribute(name = "mandatory")
  def getMandatory() = {
    mandatory
  }

  def setMandatory(mandatory: String) = {
    this.mandatory = mandatory
  }

  var readOnly: String = _

  @XmlAttribute(name = "readOnly")
  def getReadOnly() = {
    readOnly
  }

  def setReadOnly(readOnly: String) = {
    this.readOnly = readOnly
  }

  @XmlJavaTypeAdapter(value = classOf[PropertyMapAdapter])
  var properties: Map[String, String] = Map.empty[String, String]

  def addProperty(key: String, value: String) = {
    value match {
      case null => {}
      case _ => properties += (key -> value)
    }
  }

  def apply(map: Map[String, String]) = {
    map.foreach((property) => {
      addProperty(property._1, property._2)
    })
    this
  }
}

class CommonAttribute  extends AbstractCommonAttribute{

  private def this(id: Integer,
                   name: String,
                   aType: String,
                   scope: String) = {
    this()
    this.id = id
    this.name = name
    this.aType = aType
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
           props: Map[String, String]) = {
    this(id, version, name, aType, scope, props)
    this.mandatory = mandatory
    this.readOnly = readOnly
    this.code = code
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
                                props: Map[String, String]) extends CommonAttribute (id, version, name, code, aType, mandatory, readOnly, scope, props){

  @BeanProperty
  var layoutAttributeValues = new LinkedList[LayoutAttributeSimplifyDataContainer]

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
    this(ca.id, ca.version, ca.name, ca.code, ca.aType, ca.mandatory, ca.readOnly, ca.scope, ca.properties)
    layout.foreach(f=> this.layoutAttributeValues.add(new LayoutAttributeSimplifyDataContainer(f)))
  }
}

@XmlType(name = "propertyMapAdapter")
@XmlRootElement(name = "propertyMapAdapter")
@JsonIgnoreProperties(ignoreUnknown = true)
class PropertyMapAdapter
  extends XmlAdapter[Array[PropertyPair], Map[String, String]] {

  def marshal(v: Map[String, String]) = {
    val pairs = new Array[PropertyPair](v.size)
    var i: Int = 0

    v.foreach(
      entry => {
        pairs(i) = new PropertyPair(entry._1, entry._2)
        i = i + 1
      }
    )
    pairs
  }

  def unmarshal(v: Array[PropertyPair]) = {
    v.foldLeft(Map.empty[String, String])(
      (map, pp) => map + (pp.name -> pp.value))
  }
}

@XmlType(name = "property")
@XmlRootElement(name = "property")
@XmlElement(name = "property")
class PropertyPair {
  var name: String = _

  @XmlAttribute
  def getName() = {
    name
  }

  def setName(name: String) = {
    this.name = name
  }

  var value: String = _

  @XmlValue
  def getValue() = {
    value
  }

  def setValue(value: String) = {
    this.value = value
  }

  def this(name: String, value: String) = {
    this()
    this.name = name
    this.value = value
  }
}
