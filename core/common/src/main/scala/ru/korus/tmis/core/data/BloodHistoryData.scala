package ru.korus.tmis.core.data

import javax.xml.bind.annotation.{XmlRootElement, XmlType}
import org.codehaus.jackson.annotate.JsonIgnoreProperties
import reflect.BeanProperty
import java.util.{Date, LinkedList}
import ru.korus.tmis.core.entity.model.BloodHistory
import scala.collection.JavaConversions._

/**
 * Сервисы для работы с BloodHistory
 * Спецификация: https://docs.google.com/document/d/1y3qprunilr88wDMQbyT4TVY1GgNr9qAwkVerGd5NLQI
 * Author: idmitriev Systema-Soft
 * Date: 4/11/13 12:55 PM
 * Since: 1.0.1.1
 */
@XmlType(name = "bloodHistoryData")
@XmlRootElement(name = "bloodHistoryData")
@JsonIgnoreProperties(ignoreUnknown = true)
class BloodHistoryData {
  @BeanProperty
  var data: BloodHistoryContainer = new BloodHistoryContainer()

  def this(record: BloodHistory){
    this()
    this.data = new BloodHistoryContainer(record)
  }
}

@XmlType(name = "bloodHistoryListData")
@XmlRootElement(name = "bloodHistoryListData")
@JsonIgnoreProperties(ignoreUnknown = true)
class BloodHistoryListData {
  @BeanProperty
  var data: LinkedList[BloodHistoryContainer] = new LinkedList[BloodHistoryContainer]

  def this(records: java.util.List[BloodHistory]){
    this()
    records.foreach(p=>this.data.add(new BloodHistoryContainer(p)))
  }
}

@XmlType(name = "bloodHistoryContainer")
@XmlRootElement(name = "bloodHistoryContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class BloodHistoryContainer {

  @BeanProperty
  var id: Int = -1
  @BeanProperty
  var bloodDate: Date = _
  @BeanProperty
  var bloodType: IdNameContainer = _
  @BeanProperty
  var person: DoctorContainer = _

  def this(record: BloodHistory){
    this()
    if(record!=null){
      this.id = record.getId.intValue()
      this.bloodDate = record.getBloodDate
      this.bloodType = if(record.getBloodType!=null) new IdNameContainer(record.getBloodType.getId.intValue(),
                                                                         record.getBloodType.getName)
                       else new IdNameContainer()
      this.person = if(record.getPerson!=null) new DoctorContainer(record.getPerson)
                    else new DoctorContainer()
    }
  }
}