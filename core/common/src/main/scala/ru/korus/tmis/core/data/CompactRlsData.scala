package ru.korus.tmis.core.data

import ru.korus.tmis.core.entity.model.Nomenclature

import com.google.common.collect.LinkedListMultimap
import java.util.{Collection, List, LinkedList}
import javax.xml.bind.annotation._

import scala.collection.JavaConversions._
import scala.reflect.BeanProperty

@XmlType(name = "compactRls")
@XmlRootElement(name = "compactRls")
class CompactRlsData {
  var version: String = _

  @XmlAttribute
  def getVersion() = {
    version
  }

  def setVersion(version: String) = {
    this.version = version
  }

  @BeanProperty
  var drug = new LinkedList[CompactRlsEntry]

  def add(e: CompactRlsEntry) = {
    drug.add(e)
    this
  }

  def this(version: String) {
    this();
    this.version = version
  }
}

@XmlType(name = "compactRlsEntry")
@XmlRootElement(name = "compactRlsEntry")
class CompactRlsEntry {
  @XmlAttribute
  var tn: String = _
  @XmlAttribute
  var in: String = _

  @BeanProperty
  var v = new LinkedList[CompactRlsVarEntry]

  def this(id: (String, String),
           vars: Collection[Nomenclature]) = {
    this()
    this.tn = id._1
    this.in = id._2
    (this.v /: vars)((v, e) => {
      v.add(new CompactRlsVarEntry(e))
      v
    })
  }
}

@XmlType(name = "compactRlsVarEntry")
@XmlRootElement(name = "compactRlsVarEntry")
class CompactRlsVarEntry {
  @XmlAttribute
  var code: Int = _
  @XmlAttribute
  var dose: String = _

  def this(code: Int, dose: String) = {
    this()
    this.code = code
    this.dose = dose
  }

  def this(n: Nomenclature) = {
    this(n.getCode, n.getDosage)
  }
}

object CompactRlsDataBuilder {
  def fromNomenclature(n: List[Nomenclature], version: String) = {
    val map = LinkedListMultimap.create[(String, String), Nomenclature]()
    val data = (map /: n)((m, e) => {
      m.put((e.getTradeName, e.getINPNameLat), e)
      m
    })
    val result = (new CompactRlsData(version) /: data.asMap().entrySet())((rls, e) => {
      rls.add(new CompactRlsEntry(e.getKey, e.getValue))
    })
    result
  }
}
