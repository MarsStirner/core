package ru.korus.tmis.core.data

import reflect.BeanProperty
import java.util.LinkedList
import javax.xml.bind.annotation.{XmlType, XmlRootElement, XmlAttribute}
import org.codehaus.jackson.annotate.JsonIgnoreProperties
import ru.korus.tmis.core.entity.model.Thesaurus

@XmlType(name = "thesaurus")
@XmlRootElement(name = "thesaurus")
class ThesaurusData {

  var version: String = _

  @XmlAttribute
  def getVersion() = {
    version
  }

  def setVersion(version: String) = {
    this.version = version
  }

  @BeanProperty
  var entry = new LinkedList[ThesaurusEntry]

  def this(version: String) = {
    this()
    this.version = version
  }

  def add(e: ThesaurusEntry) {
    entry.add(e)
  }
}

@XmlType(name = "thesaurusEntry")
@XmlRootElement(name = "thesaurusEntry")
class ThesaurusEntry {
  @XmlAttribute
  var id: Int = _
  @XmlAttribute
  var groupId: Int = _
  @XmlAttribute
  var code: String = _
  @XmlAttribute
  var name: String = _
  @XmlAttribute
  var template: String = _

  def this(id: Int,
           groupId: Int,
           code: String,
           name: String,
           template: String) {
    this()
    this.id = id
    this.groupId = groupId
    this.code = code
    this.name = name
    this.template = template
  }
}

@XmlType(name = "thesaurusContainer")
@XmlRootElement(name = "thesaurusContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class ThesaurusContainer {
  @BeanProperty
  var id: java.lang.Integer = _
  @BeanProperty
  var name: String = _
  @BeanProperty
  var code: String = _
  @BeanProperty
  var groupId: java.lang.Integer = _
  @BeanProperty
  var template: String = _

  def this(id: java.lang.Integer, name: String, code: String, groupId: java.lang.Integer, template: String) {
    this()
    this.id = id
    this.name = name
    this.code = code
    this.groupId = groupId
    this.template = template
  }

  def this(thesaurus: Thesaurus) {
    this()
    this.id = thesaurus.getId
    this.name = thesaurus.getName
    this.code = thesaurus.getCode
    this.groupId = thesaurus.getGroupId
    this.template = thesaurus.getTemplate
  }

  def toMap = {
    var map = new java.util.HashMap[String, Object]
    map.put("id", this.id)
    map.put("name", this.name)
    map.put("code", this.code)
    map.put("groupId", this.groupId)
    map
  }
}
