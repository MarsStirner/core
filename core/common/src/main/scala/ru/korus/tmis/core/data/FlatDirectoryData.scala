package ru.korus.tmis.core.data

import javax.xml.bind.annotation.{XmlRootElement, XmlType}
import scala.beans.BeanProperty
import java.util.{LinkedList, Date}
import scala.collection.JavaConversions._
import ru.korus.tmis.core.entity.model.{ActionStatus, Staff, Action}
import ru.korus.tmis.core.entity.model.fd.{FDFieldValue, FDRecord, FDField, FlatDirectory}
import ru.korus.tmis.core.exception.CoreException
import java.util
import ru.korus.tmis.scala.util.ConfigManager

//Контейнер для справочника плоских структур
@XmlType(name = "flatDirectoryData")
@XmlRootElement(name = "flatDirectoryData")
class FlatDirectoryData {

  @BeanProperty
  var requestData: FlatDirectoryRequestData = _
  @BeanProperty
  var data: LinkedList[FlatDirectoryEntry] = new LinkedList[FlatDirectoryEntry]

  //All
  def this(directories: java.util.List[FlatDirectory], request: FlatDirectoryRequestData) {
    this()
    this.requestData = request
    directories.foreach(directory => this.data += new FlatDirectoryEntry(directory, request))
  }

  //Filtred
  def this(directories: java.util.LinkedHashMap[FlatDirectory, java.util.LinkedHashMap[FDRecord, java.util.LinkedList[FDFieldValue]]], request: FlatDirectoryRequestData) {
    this()
    this.requestData = request
    directories.foreach(directory => {
      this.data += new FlatDirectoryEntry(directory, request)
    })
  }

}

@XmlType(name = "flatDirectoryRequestData")
@XmlRootElement(name = "flatDirectoryRequestData")
class FlatDirectoryRequestData {
  @BeanProperty
  var filter: AnyRef = _
  @BeanProperty
  var sortingFields: java.util.LinkedHashMap[Int, Int] = _
  @BeanProperty
  var limit: Int = _
  @BeanProperty
  var page: Int = _
  @BeanProperty
  var recordsCount: Long = _
  @BeanProperty
  var coreVersion: String = _

  var sortingFieldInternal: String = _

  def this(sortingFields: java.util.LinkedHashMap[java.lang.Integer, java.lang.Integer],
           limit: Int,
           page: Int,
           filter: AnyRef) = {
    this()

    this.filter = if (filter != null) {
      filter
    } else {
      null
    }
    this.sortingFields = if (sortingFields != null)
      sortingFields.foldLeft(new util.LinkedHashMap[Int, Int]())((list, elem) => {
      list.put(elem._1.intValue(), elem._2.intValue())
      list})
    else
      null

    this.limit = if (limit > 0) {
      limit
    } else {
      10
    }
    this.page = if (page > 1) {
      page
    } else {
      1
    }
    this.coreVersion = ConfigManager.Messages("misCore.assembly.version")
  }
}

@XmlType(name = "flatDirectoryRequestDataListFilter")
@XmlRootElement(name = "flatDirectoryRequestDataListFilter")
class FlatDirectoryRequestDataListFilter {

  @BeanProperty
  var flatDictionaryIds: java.util.List[Int] = _

  @BeanProperty
  var includeMeta: Boolean = _

  @BeanProperty
  var includeRecordList: Boolean = _

  @BeanProperty
  var includeFDRecord: Boolean = _

  @BeanProperty
  var filterFields: java.util.Map[Int, java.util.List[String]] = _

  @BeanProperty
  var filterValue: String = _

  @BeanProperty
  var filterRecordIds: java.util.List[Int] = _

  def this(flatDictionaryIds: java.util.List[java.lang.Integer],
           includeMeta: String,
           includeRecordList: String,
           includeFDRecord: String,
           filterFields: java.util.Map[java.lang.Integer, java.util.List[String]],
           filterValue: String,
           filterRecordIds: java.util.List[java.lang.Integer]) {
    this()
    this.flatDictionaryIds = if(flatDictionaryIds != null)
      flatDictionaryIds.foldLeft[java.util.List[Int]](new util.ArrayList[Int]())((list, elem) => {
      list.add(elem.intValue())
      list})
    else
      throw new CoreException("Ошибка, не заданы идентификаторы справочников")

    this.includeMeta = (includeMeta != null && includeMeta.isEmpty != true && includeMeta.compare("yes") == 0)
    this.includeRecordList = (includeRecordList != null && includeRecordList.isEmpty != true && includeRecordList.compare("yes") == 0)
    this.includeFDRecord = (includeFDRecord != null && includeFDRecord.isEmpty != true && includeFDRecord.compare("yes") == 0)
    this.filterFields =
      if(filterFields != null)
        filterFields.collect{case (i: java.lang.Integer, l : java.util.List[String]) => (i.intValue(), l)}
      else
        null
    this.filterValue = filterValue
    this.filterRecordIds =
      if(filterRecordIds != null)
        filterRecordIds.foldLeft[java.util.List[Int]](new util.ArrayList[Int]())((list, elem) => {
        list.add(elem.intValue())
        list})
      else
        null
  }

  def toQueryStructure() = {
    var qs = new QueryDataStructure()
    if (this.flatDictionaryIds != null && this.flatDictionaryIds.size > 0 && flatDictionaryIds.get(0) > 0) {
      //отсекаем случай -1 - вывод всех словарей
      qs.query += "WHERE fd.id IN :ids\n"
      qs.add("ids", asJavaCollection(this.flatDictionaryIds))
    }
    qs
  }

  def toQueryStructureForRecordsRequest() = {
    var qs = new QueryDataStructure()
    if (this.flatDictionaryIds != null && this.flatDictionaryIds.size > 0 && flatDictionaryIds.get(0) > 0) {
      //отсекаем случай -1 - вывод всех словарей
      qs.query = this.lexemsToQueryString(qs.query)
      qs.query += "fd.id IN :ids\n"
      qs.add("ids", asJavaCollection(this.flatDictionaryIds))
    }
    if (this.filterRecordIds != null && this.filterRecordIds.size > 0) {
      qs.query = this.lexemsToQueryString(qs.query)
      qs.query += "fdr.id IN :ids2\n"
      qs.add("ids2", asJavaCollection(this.filterRecordIds))
    }
    else if (this.filterValue != null && !this.filterValue.isEmpty) {
      qs.query = this.lexemsToQueryString(qs.query)
      qs.query += "fv.value = :value\n"
      qs.add("value", this.filterValue)
    }
    else if (this.filterFields != null && this.filterFields.size > 0) {
      var lexems: String = ""
      var i = 0
      qs.query = this.lexemsToQueryString(qs.query)
      qs.query += "("
      filterFields.foreach(element => {
        var lexems2: String = ""
        var j = 0
        qs.query += lexems
        qs.query += "fv.pk.fdField.id = :fieldId%d AND (".format(i)
        qs.add("fieldId%d".format(i), element._1.asInstanceOf[Integer])
        element._2.foreach(element2 => {
          qs.query += lexems2
          qs.query += "fv.value = :value%d%d\n".format(i, j)
          qs.add("value%d%d".format(i, j), element2)
          lexems2 = " OR "
          j = j + 1
        })
        qs.query += ")"
        lexems = " OR "
        i = i + 1
      })
      qs.query += ")"
    }
    qs
  }

  private def lexemsToQueryString(qss: String) = {
    var query = qss
    if (query.isEmpty()) {
      query += "WHERE "
    } else {
      query += "AND "
    }
    query
  }
}

@XmlType(name = "flatDirectoryEntry")
@XmlRootElement(name = "flatDirectoryEntry")
class FlatDirectoryEntry {

  @BeanProperty
  var id: Int = _

  @BeanProperty
  var name: String = _

  @BeanProperty
  var description: String = _

  @BeanProperty
  var fieldDescriptionList: LinkedList[FieldDescriptionData] = new LinkedList[FieldDescriptionData]

  @BeanProperty
  var recordList: LinkedList[RecordData] = new LinkedList[RecordData]

  def this(directory: FlatDirectory, request: FlatDirectoryRequestData) {
    this()
    this.id = directory.getId.intValue()
    this.name = directory.getName
    this.description = directory.getDescription
    if (request.filter.isInstanceOf[FlatDirectoryRequestDataListFilter] &&
      request.filter.asInstanceOf[FlatDirectoryRequestDataListFilter].includeMeta) {
      directory.getFdFields.foreach(fdField => if(fdField!=null) this.fieldDescriptionList += new FieldDescriptionData(fdField))
    }
    if (request.filter.isInstanceOf[FlatDirectoryRequestDataListFilter] &&
      request.filter.asInstanceOf[FlatDirectoryRequestDataListFilter].includeRecordList) {
      directory.getFdRecords.foreach(record => this.recordList += new RecordData(record, request.filter.asInstanceOf[FlatDirectoryRequestDataListFilter].includeFDRecord))
    }
  }

  //FlatDirectory, java.util.LinkedHashMap[FDRecord,  java.util.LinkedList[FDFieldValue]]
  def this(directory: (FlatDirectory, java.util.LinkedHashMap[FDRecord, java.util.LinkedList[FDFieldValue]]), request: FlatDirectoryRequestData) {
    this()
    this.id = directory._1.getId.intValue()
    this.name = directory._1.getName
    this.description = directory._1.getDescription
    if (request.filter.isInstanceOf[FlatDirectoryRequestDataListFilter] &&
      request.filter.asInstanceOf[FlatDirectoryRequestDataListFilter].includeMeta) {
      directory._1.getFdFields.foreach(fdField => if(fdField!=null) this.fieldDescriptionList += new FieldDescriptionData(fdField))
    }
    if (request.filter.isInstanceOf[FlatDirectoryRequestDataListFilter] &&
      request.filter.asInstanceOf[FlatDirectoryRequestDataListFilter].includeRecordList) {
      directory._2.foreach(record => this.recordList += new RecordData(record, request.filter.asInstanceOf[FlatDirectoryRequestDataListFilter].includeFDRecord))
    }
  }
}

@XmlType(name = "fieldDescriptionData")
@XmlRootElement(name = "fieldDescriptionData")
class FieldDescriptionData {

  @BeanProperty
  var id: Int = _

  @BeanProperty
  var name: String = _

  @BeanProperty
  var description: String = _

  @BeanProperty
  var order: Int = _

  def this(description: FDField) {
    this()
    this.id = description.getId.intValue()
    this.name = description.getName()
    this.description = description.getDescription
    this.order = if (description.getOrder != null) {
      description.getOrder.intValue()
    } else {
      0
    }
  }
}

@XmlType(name = "idOrderFieldDescriptionData")
@XmlRootElement(name = "idOrderFieldDescriptionData")
class IdOrderFieldDescriptionData {

  @BeanProperty
  var id: Int = _

  @BeanProperty
  var order: Int = _

  def this(description: FDField) {
    this()
    this.id = description.getId.intValue()
    this.order = if (description.getOrder != null) {
      description.getOrder.intValue()
    } else {
      0
    }
  }
}

@XmlType(name = "recordData")
@XmlRootElement(name = "recordData")
class RecordData {

  @BeanProperty
  var id: Int = _

  @BeanProperty
  var order: Int = _

  @BeanProperty
  var fieldValueList: LinkedList[FieldValueData] = new LinkedList[FieldValueData]

  def this(record: FDRecord, flagFDRecord: Boolean) {
    this()
    this.id = record.getId.intValue()
    this.order = if (record.getOrder != null) {
      record.getOrder.intValue()
    } else {
      0
    }
    record.getFieldValues.foreach(value => this.fieldValueList += new FieldValueData(value, flagFDRecord))
  }

  //FDRecord,  java.util.LinkedList[FDFieldValue]
  def this(record: (FDRecord, java.util.LinkedList[FDFieldValue]), flagFDRecord: Boolean) {
    this()
    this.id = record._1.getId.intValue()
    this.order = if (record._1.getOrder != null) {
      record._1.getOrder.intValue()
    } else {
      0
    }
    record._2.foreach(value => this.fieldValueList += new FieldValueData(value, flagFDRecord))
  }
}

@XmlType(name = "fieldValueData")
@XmlRootElement(name = "fieldValueData")
class FieldValueData {

  @BeanProperty
  var fieldDescription: AnyRef = _

  @BeanProperty
  var fieldValue: FieldIdValueData = _

  def this(value: FDFieldValue, flagFDRecord: Boolean) {
    this()
    if (flagFDRecord) {
      this.fieldDescription =
        if(value!=null && value.getPk!=null && value.getPk.getFDField!=null)
          new FieldDescriptionData(value.getPk.getFDField)
        else
          new FieldDescriptionData()
    } else {
      this.fieldDescription = new IdOrderFieldDescriptionData(value.getPk.getFDField)
    }
    this.fieldValue = new FieldIdValueData(value.getId.intValue(), value.getValue)
  }
}

@XmlType(name = "fieldIdValueData")
@XmlRootElement(name = "fieldIdValueData")
class FieldIdValueData {

  @BeanProperty
  var id: Int = _

  @BeanProperty
  var value: AnyRef = _

  def this(id: Int, value: AnyRef) {
    this()
    this.id = id
    this.value = value
  }
}