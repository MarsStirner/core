package ru.korus.tmis.core.data

import javax.xml.bind.annotation.{XmlRootElement, XmlType}
import org.codehaus.jackson.annotate.JsonIgnoreProperties
import reflect.BeanProperty
import ru.korus.tmis.core.entity.model.Nomenclature
import ru.korus.tmis.util.ConfigManager
import scala.collection.JavaConversions._


@XmlType(name = "rlsDataList")
@XmlRootElement(name = "rlsDataList")
@JsonIgnoreProperties(ignoreUnknown = true)
class RlsDataList {
  @BeanProperty
  var requestData: RlsDataListRequestData = _
  @BeanProperty
  var data: java.util.LinkedList[RlsDataListEntry] = new java.util.LinkedList[RlsDataListEntry]

  def this(rls: java.util.List[Nomenclature], requestData: RlsDataListRequestData) = {
    this()
    this.requestData = requestData
    rls.foreach(r => this.data.add(new RlsDataListEntry(r)))
  }
}

@XmlType(name = "rlsDataListRequestData")
@XmlRootElement(name = "rlsDataListRequestData")
class RlsDataListRequestData {
  @BeanProperty
  var filter: RlsDataListFilter = _
  @BeanProperty
  var sortingField: String = _
  @BeanProperty
  var sortingMethod: String = _
  @BeanProperty
  var limit: Int = _
  @BeanProperty
  var page: Int = _
  @BeanProperty
  var recordsCount: Long = _
  @BeanProperty
  var coreVersion: String = _

  var sortingFieldInternal: String = _

  def this(sortingField: String,
           sortingMethod: String,
           limit: Int,
           page: Int,
           filter: RlsDataListFilter) = {
    this()
    this.filter = if (filter != null) {
      filter
    } else {
      null
    }
    this.sortingField = sortingField match {
      case null => {
        "id"
      }
      case _ => {
        sortingField
      }
    }

    this.sortingFieldInternal = this.filter.toSortingString(this.sortingField)


    this.sortingMethod = sortingMethod match {
      case null => {
        "asc"
      }
      case _ => {
        sortingMethod
      }
    }
    this.limit = limit
    this.page = page
    this.coreVersion = ConfigManager.Messages("misCore.assembly.version")
  }
}

@XmlType(name = "rlsDataListFilter")
@XmlRootElement(name = "rlsDataListFilter")
class RlsDataListFilter {

  @BeanProperty
  var code: Int = _
  @BeanProperty
  var name: String = _
  @BeanProperty
  var dosage: String = _
  @BeanProperty
  var form: String = _

  def this(code: Int,
           name: String,
           dosage: String,
           form: String) {
    this()
    this.code = code
    this.name = name
    this.dosage = dosage
    this.form = form
  }

  def toQueryStructure() = {
    var qs = new QueryDataStructure()

    if (this.code > 0) {
      qs.query += ("AND n.code =  :code\n")
      qs.add("code", this.code: java.lang.Integer)
    }
    else {
      if (this.name != null && !this.name.isEmpty) {
        qs.query += ("AND (n.tradeName LIKE :name OR n.tradeNameLat LIKE :name)\n")
        qs.add("name", "%" + this.name + "%")
      }
      if (this.dosage != null && !this.dosage.isEmpty) {
        qs.query += ("AND n.dosage LIKE :dosage\n")
        qs.add("dosage", "%" + this.dosage + "%")
      }
      if (this.form != null && !this.form.isEmpty) {
        qs.query += ("AND n.form LIKE :form\n")
        qs.add("form", "%" + this.form + "%")
      }
    }
    qs
  }

  def toSortingString(sortingField: String) = {
    sortingField match {
      case "code" => {
        "n.code"
      }
      case "name" | "tradeName" => {
        "n.tradeName"
      }
      case "dosage" => {
        "n.dosage"
      }
      case "form" => {
        "n.form"
      }
      case _ => {
        "n.tradeName"
      }
    }
  }
}

@XmlType(name = "rlsDataListEntry")
@XmlRootElement(name = "rlsDataListEntry")
@JsonIgnoreProperties(ignoreUnknown = true)
class RlsDataListEntry {
  @BeanProperty
  var id: Int = _
  @BeanProperty
  var code: Int = _
  @BeanProperty
  var tradeName: String = _
  @BeanProperty
  var tradeNameLatin: String = _
  @BeanProperty
  var dosage: String = _
  @BeanProperty
  var form: String = _

  def this(rls: Nomenclature) = {
    this()
    this.id = rls.getId
    this.code = rls.getCode
    this.tradeName = rls.getTradeName
    this.tradeNameLatin = rls.getTradeNameLat
    this.dosage = rls.getDosage
    this.form = rls.getForm
  }
}