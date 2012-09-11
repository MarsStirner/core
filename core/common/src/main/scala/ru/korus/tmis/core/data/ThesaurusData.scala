package ru.korus.tmis.core.data

import reflect.BeanProperty
import java.util.LinkedList
import javax.xml.bind.annotation.{XmlType, XmlRootElement, XmlAttribute}

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
    this ()
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
    this ()
    this.id = id
    this.groupId = groupId
    this.code = code
    this.name = name
    this.template = template
  }
}
