package ru.korus.tmis.core.data

import javax.xml.bind.annotation.{XmlRootElement, XmlType}
import org.codehaus.jackson.annotate.JsonIgnoreProperties
import reflect.BeanProperty
import java.util.Date

/**
 * Контейнер с данными о заборе биоматериала
 * Author: idmitriev Systema-Soft
 * Date: 2/12/13 3:37 PM
 * Since: 1.0.0.64
 */
@XmlType(name = "takingOfBiomaterialData")
@XmlRootElement(name = "takingOfBiomaterialData")
@JsonIgnoreProperties(ignoreUnknown = true)
class TakingOfBiomaterialData {

  @BeanProperty
  var requestData: TakingOfBiomaterialRequesData = _
  //@BeanProperty
  //var data: LinkedList[TakingOfBiomaterialEntry] = new LinkedList[TakingOfBiomaterialEntry]

}

@XmlType(name = "takingOfBiomaterialRequesData")
@XmlRootElement(name = "takingOfBiomaterialRequesData")
@JsonIgnoreProperties(ignoreUnknown = true)
class TakingOfBiomaterialRequesData {

  @BeanProperty
  var filter: TakingOfBiomaterialRequesDataFilter = _
  @BeanProperty
  var sortingField: String = _
  @BeanProperty
  var sortingMethod: String = _
  @BeanProperty
  var limit: Int = 10
  @BeanProperty
  var page: Int = 1
  @BeanProperty
  var recordsCount: Int = 0
  @BeanProperty
  var coreVersion: String = _

  def this(sortingField: String,
           sortingMethod: String,
           limit: Int,
           page: Int,
           filter: TakingOfBiomaterialRequesDataFilter){
    this()
    this.sortingField = sortingField
    this.sortingMethod = sortingMethod
    this.limit = if(limit>0)limit else 10
    this.page = if(page>1) page else 1
    this.filter = filter
  }
}

@XmlType(name = "takingOfBiomaterialRequesDataFilter")
@XmlRootElement(name = "takingOfBiomaterialRequesDataFilter")
@JsonIgnoreProperties(ignoreUnknown = true)
class TakingOfBiomaterialRequesDataFilter {

  @BeanProperty
  var departmentId: Int = _
  @BeanProperty
  var beginDate: Date = _
  @BeanProperty
  var endDate: Date = _
  @BeanProperty
  var status: Short = 0
  @BeanProperty
  var biomaterial: String = ""

  def this( departmentId: Int,
            beginDate: Long,
            endDate: Long,
            status: Short,
            biomaterial:String) {
    this()
    this.departmentId = departmentId
    this.status = status
    this.biomaterial = biomaterial

  }

}