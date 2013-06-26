package ru.korus.tmis.core.filter

import javax.xml.bind.annotation.{XmlRootElement, XmlType}
import reflect.BeanProperty
import java.util.Date
import java.text.{SimpleDateFormat, DateFormat}
import ru.korus.tmis.core.data.QueryDataStructure

/**
 * Фильтр для списка действий
 * Author: idmitriev Systema-Soft
 * Date: 4/01/13 6:34 PM
 * Since: 1.0.0.81
 */
@XmlType(name = "actionsListDataFilter")
@XmlRootElement(name = "actionsListDataFilter")
class ActionsListDataFilter  extends AbstractListDataFilter {
  @BeanProperty
  var eventId:  Int = _
  @BeanProperty
  var actionTypeId:  Int = _
  @BeanProperty
  var urgent: Boolean = _
  @BeanProperty
  var beginDate: Date = _
  @BeanProperty
  var endDate: Date = _
  @BeanProperty
  var currentMedDay: Boolean = false

  var beginCurrentMedDay: Date = _
  var endCurrentMedDay: Date = _

  def this(eventId:  Int,
           actionTypeId:  Int,
           beginDate: Long,
           endDate: Long,
           urgent: Boolean,
           currentMedDay: Boolean) {
    this()
    this.eventId = eventId
    this.actionTypeId = actionTypeId
    this.beginDate = if(beginDate==0) {null} else {new Date(beginDate)}
    this.endDate = if(endDate==0) {null} else {new Date(endDate)}
    this.urgent = urgent
    this.currentMedDay = currentMedDay

    if (currentMedDay==true) {
      val now = new Date()
      val formatter: DateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00:00")
      val date = formatter.parse(formatter.format(now.getTime))
      this.beginCurrentMedDay = date
      this.endCurrentMedDay = now
    } else {
      this.beginCurrentMedDay = null
      this.endCurrentMedDay = null
    }
  }

  @Override
  def toQueryStructure() = {
    val qs = new QueryDataStructure()
    if(this.eventId>0){
      qs.query += "AND a.event.id = :eventId\n"
      qs.add("eventId",this.eventId:java.lang.Integer)
    }
    if(this.actionTypeId>0){
      qs.query += ("AND a.actionType.id = :actionTypeId\n")
      qs.add("actionTypeId",this.actionTypeId:java.lang.Integer)
    }
    if(this.beginDate!=null && this.endDate!=null){
      qs.query += "AND a.createDatetime BETWEEN :beginDate AND :endDate\n"
      qs.add("beginDate",this.beginDate)
      qs.add("endDate",this.endDate)
    }
    if(this.urgent){
      qs.query += "AND a.isUrgent = :urgent\n"
      qs.add("urgent",this.urgent:java.lang.Boolean)
    }
    if(currentMedDay && this.beginCurrentMedDay!=null && this.endCurrentMedDay!=null){
      qs.query += "AND a.createDatetime BETWEEN :beginDate AND :endDate\n"
      qs.add("beginDate",this.beginCurrentMedDay)
      qs.add("endDate",this.endCurrentMedDay)
    }
    qs
  }

  @Override
  def toSortingString (sortingField: String, sortingMethod: String) = {
    var sorting = sortingField match {
      case "date" => {"a.createDatetime %s"}
      case _ => {"a.id %s"}
    }
    sorting = "ORDER BY " + sorting.format(sortingMethod)
    sorting
  }
}