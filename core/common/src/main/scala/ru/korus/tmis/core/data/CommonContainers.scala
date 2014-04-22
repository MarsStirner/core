package ru.korus.tmis.core.data

import javax.xml.bind.annotation.{XmlRootElement, XmlType}
import reflect.BeanProperty
import java.util.Date

import org.codehaus.jackson.annotate.JsonIgnoreProperties
import org.codehaus.jackson.annotate.JsonIgnoreProperties._
import ru.korus.tmis.core.entity.model.OrgStructure
import ru.korus.tmis.scala.util.ConfigManager

@XmlType(name = "idNameContainer")
@XmlRootElement(name = "idNameContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class IdNameContainer {
  @BeanProperty
  var id : Int = _
  @BeanProperty
  var name : String = _

  def this( id : Int, name : String) = {
    this()
    this.id = id;
    this.name = name
  }

  def toMap = {
    var map = new java.util.HashMap[String, Object]
    map.put("id", this.id.toString)
    map.put("name", this.name)

    map
  }
}

class IdNameAndCodeContainer {

  @BeanProperty
  var id : Int = _
  @BeanProperty
  var name : String = _
  @BeanProperty
  var code : String = _

  def this( id : Int, name : String, code : String) = {
    this()
    this.id = id
    this.name = name
    this.code = code
  }
}

@XmlType(name = "idValContainer")
@XmlRootElement(name = "idValContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class IdValueContainer {
  @BeanProperty
  var id: String = _

  def this(idValue: String) {
    this()
    this.id = idValue
  }

  def toMap = {
    var map = new java.util.HashMap[String, Object]
    map.put("id", this.id)

    map
  }
}

@XmlType(name = "datePeriodContainer")
@XmlRootElement(name = "datePeriodContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class DatePeriodContainer {
  @BeanProperty
  var start : Date = _
  @BeanProperty
  var end : Date = _

  def this( start : Date, end : Date) = {
    this()
    this.start = start
    this.end = end
  }

  def toMap = {
    var map = new java.util.HashMap[String, Object]

    map.put("start", this.DateToString(this.start))
    map.put("end", this.DateToString(this.end))

    map
  }

  private def DateToString(date: Date) = {
    val CMDF = ConfigManager.DateFormatter
    if(date!=null){
      CMDF.format(date)
    } else {
      ""
    }
  }
}

@XmlType(name = "rangeLeftRightContainer")
@XmlRootElement(name = "rangeLeftRightContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class RangeLeftRightContainer {
  @BeanProperty
  var left: HandPreassureContainer = _
  @BeanProperty
  var right: HandPreassureContainer = _

  def this(leftDiast:Double,
           leftSyst:Double,
           rightDiast:Double,
           rightSyst: Double) {
    this()
    this.left = new HandPreassureContainer(leftDiast, leftSyst)
    this.right = new HandPreassureContainer(rightDiast, rightSyst)
  }

  def toMap = {
    var map = new java.util.HashMap[String, Object]
    map.put("left", this.left)
    map.put("right", this.right)
    map
  }
}
@XmlType(name = "handPreassureContainer")
@XmlRootElement(name = "handPreassureContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class HandPreassureContainer {
  @BeanProperty
  var diast: Double = _
  @BeanProperty
  var syst: Double = _

  def this(diast: Double, syst: Double) {
    this()
    this.diast = diast
    this.syst = syst
  }
}

@XmlType(name = "orgStructureContainer")
@XmlRootElement(name = "orgStructureContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class OrgStructureContainer {
  @BeanProperty
  var id : Int = _
  @BeanProperty
  var name : String = _
  @BeanProperty
  var code : String = _
  @BeanProperty
  var address : String = _

  def this(department: OrgStructure){
    this()
    if (department!=null){
      this.id = department.getId.intValue()
      this.name = department.getName
      this.code = department.getCode
      this.address = department.getAddress
    }
  }
}

@XmlType(name = "booleanContainer")
@XmlRootElement(name = "booleanContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
case class BooleanContainer(@BeanProperty var value: Boolean)