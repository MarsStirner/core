package ru.korus.tmis.core.data

import javax.xml.bind.annotation._
import ru.korus.tmis.util.ConfigManager
import reflect.BeanProperty
import javax.xml.bind.annotation.adapters.{XmlAdapter, XmlJavaTypeAdapter}
import java.util.ArrayList
import ru.korus.tmis.core.entity.model.OrgStructure
import org.codehaus.jackson.annotate.JsonIgnoreProperties
import scala.collection.JavaConversions._

/**
 * Created with IntelliJ IDEA.
 * User: mmakankov
 * Date: 10.12.12
 * Time: 16:44
 * To change this template use File | Settings | File Templates.
 */

@XmlType(propOrder = Array("department"), name = "allDepartmentsListDataMP")
@XmlRootElement(name = "allDepartmentsListDataMP")
class AllDepartmentsListDataMP {

  @BeanProperty
  var department: java.util.ArrayList[IdNameContainerMP] = new java.util.ArrayList[IdNameContainerMP]

  def this(departments: java.util.List[OrgStructure], requestData: ListDataRequest) = {
    this ()
    departments.foreach(org => this.department.add(new IdNameContainerMP(org.getId.intValue(), org.getCode)))
  }
}

@XmlType(propOrder = Array("id", "name"), name = "IdNameContainerMP")
@XmlRootElement(name = "idNameContainerMP")
@JsonIgnoreProperties(ignoreUnknown = true)
class IdNameContainerMP {

  var id : Int = _

  var name : String = _

  @XmlAttribute(name = "id")
  def getId() = {
    id
  }

  def setId(id: Int) = {
    this.id = id
  }

  @XmlAttribute(name = "name")
  def getName() = {
    name
  }

  def setName(name: String) = {
    this.name = name
  }

  def this( id : Int, name : String) = {
    this()
    this.id = id;
    this.name = name
  }

}