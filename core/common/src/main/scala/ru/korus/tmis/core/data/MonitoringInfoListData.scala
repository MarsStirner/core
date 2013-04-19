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

  def this(records: java.util.LinkedHashMap[ActionProperty, java.util.List[APValue]]){
    this()
    val map = new java.util.LinkedHashMap[String,(String, java.util.LinkedList[(Date, APValue)])]
    if (records!=null && records.size()>0){
      try {
        records.foreach(record => {
          if (record._1!=null && record._2!=null && record._2.size()>0) {
            val code = record._1.getType.getCode
            val name = record._1.getType.getName
            if (!map.contains(code)){
              val values  = new java.util.LinkedList[(Date, APValue)]
              map.put(code,(name, values))
            }
            map.get(code)._2.add((record._1.getCreateDatetime, record._2.get(0)))
          }
        })

        map.foreach(f=>{
          if (f._2!=null)
            this.data.add(new MonitoringInfoContainer(f._1, f._2._1, f._2._2))
        })
      } finally {
        map.clear()
      }
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
