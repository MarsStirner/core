package ru.korus.tmis.core.data

import javax.xml.bind.annotation.{XmlRootElement, XmlType}
import org.codehaus.jackson.annotate.JsonIgnoreProperties
import ru.korus.tmis.core.entity.model.{APValue, ActionProperty}
import reflect.BeanProperty
import scala.collection.JavaConversions._
import java.util.Date
import collection.mutable

/**
 * Контейнер для мониторинга значений измерений и экспресс-анализов
 * Спецификация: https://docs.google.com/spreadsheet/ccc?key=0Amfvj7P4xELWdFRJRnR1LVhTdG5BSFZKRnZnNWNlNHc#gid=1
 * Author: idmitriev Systema-Soft
 * Date: 4/12/13 14:12 PM
 * Since: 1.0.1.2
 */
@XmlType(name = "monitoringInfoListData")
@XmlRootElement(name = "monitoringInfoListData")
@JsonIgnoreProperties(ignoreUnknown = true)
class MonitoringInfoListData {
  @BeanProperty
  var data: java.util.LinkedList[MonitoringInfoContainer] = new java.util.LinkedList[MonitoringInfoContainer]

  def this(records: java.util.Map[ActionProperty, java.util.List[APValue]], codes: java.util.Set[String]){
    this()
    if (records!=null && records.size()>0){
      codes.foreach(code => {
        //Фильтруем все ActionProperty с ActionPropertyType.code = code
        val filtred = records.filter(ap => (ap._2!=null && ap._2.size()>0 && ap._1.getType.getCode.compareTo(code)==0))
        if (filtred!=null && filtred.size>0) {
          val name = filtred.toList.get(0)._1.getType.getName
          val values  = new java.util.LinkedList[(Date, APValue)]
          try {
            val iterator = filtred.iterator
            for(line <- Iterator.continually(iterator.next()).takeWhile(_ != null && values.size()<5)) {
              values.add((line._1.getAction.getCreateDatetime,line._2.get(0)))
            }
            this.data.add(new MonitoringInfoContainer(code, name, values))
          } finally {
            values.clear()
          }
        }
      })
    }
  }
}

@XmlType(name = "monitoringInfoContainer")
@XmlRootElement(name = "monitoringInfoContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class MonitoringInfoContainer {

  @BeanProperty
  var code: String = ""            //Код ActionPropertyType.code
  @BeanProperty
  var name: String = ""            //Наименования исследования ActionPropertyType.name
  @BeanProperty
  var values: java.util.LinkedList[MonitoringInfoGroupContainer] = new java.util.LinkedList[MonitoringInfoGroupContainer]

  def this( code: String, name: String, values: java.util.List[(Date, APValue)]){
    this()
    this.code = code
    this.name = name
    values.foreach(value => this.values.add(new MonitoringInfoGroupContainer(value._1, value._2)))
  }
}

@XmlType(name = "monitoringInfoContainer")
@XmlRootElement(name = "monitoringInfoContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class MonitoringInfoGroupContainer {
  @BeanProperty
  var date: Date = _                //Дата исследования
  @BeanProperty
  var value: String = ""            //Значение

  def this(date: Date, value: APValue){
    this()
    this.date = date
    this.value = value.getValueAsString
  }
}
