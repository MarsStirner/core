package ru.korus.tmis.core.data

import reflect.BeanProperty
import java.lang.Integer
import java.util.LinkedList
import javax.xml.bind.annotation.{XmlType, XmlRootElement, XmlAttribute}

@XmlType(name = "rls")
@XmlRootElement(name = "rls")
class RlsData {
  var version: String = _

  @XmlAttribute
  def getVersion() = version

  def setVersion(version: String) = {
    this.version = version
  }

  @BeanProperty
  var entry = new LinkedList[RlsEntry]

  def add(e: RlsEntry) {
    entry.add(e)
  }

  def this(version: String) {
    this();
    this.version = version
  }
}

@XmlType(name = "rlsEntry")
@XmlRootElement(name = "rlsEntry")
class RlsEntry {
  @XmlAttribute
  var id: Integer = _
  @XmlAttribute
  var code: Int = _
  @XmlAttribute
  var tradeName: String = _
  @XmlAttribute
  var tradeNameLatin: String = _
  @XmlAttribute
  var inpName: String = _
  @XmlAttribute
  var inpNameLatin: String = _
  @XmlAttribute
  var form: String = _
  @XmlAttribute
  var dosage: String = _
  @XmlAttribute
  var filling: String = _
  @XmlAttribute
  var packing: String = _

  def this(id: Integer,
           code: Int,
           tradeName: String,
           tradeNameLatin: String,
           inpName: String,
           inpNameLatin: String,
           form: String,
           dosage: String,
           filling: String,
           packing: String) = {
    this()
    this.id = id
    this.code = code
    this.tradeName = tradeName
    this.tradeNameLatin = tradeNameLatin
    this.inpName = inpName
    this.inpNameLatin = inpNameLatin
    this.form = form
    this.dosage = dosage
    this.filling = filling
    this.packing = packing
  }
}
