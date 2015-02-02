package ru.korus.tmis.core.data

import java.{util => util}
import java.util.Date
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter
import javax.xml.bind.annotation.{XmlRootElement, XmlType}

import org.codehaus.jackson.annotate.JsonIgnoreProperties
import ru.korus.tmis.core.data.adapters.{DateTimeAdapter, DateAdapter}

import scala.beans.BeanProperty

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 10/29/14
 * Time: 6:31 PM
 */
@XmlType(name = "therapyContainer", propOrder = Array[String]("titleId", "title", "link", "beginDate", "endDate", "phases"))
@XmlRootElement(name = "therapyContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class TherapyContainer() {

  @BeanProperty var titleId: Int= _
  @BeanProperty var title: String= _
  @BeanProperty var link: String= _
  @XmlJavaTypeAdapter(classOf[DateAdapter]) var beginDate: Date= _
  @XmlJavaTypeAdapter(classOf[DateAdapter]) var endDate: Date= _
  @BeanProperty var phases: util.List[TherapyPhase] = _

  def this(titleId: Int, title: String, link: String, beginDate: Date, endDate: Date, phases: util.List[TherapyPhase]) {
    this()
    this.titleId = titleId
    this.title = title
    this.link = link
    this.beginDate = beginDate
    this.endDate = endDate
    this.phases = phases
  }
}

@XmlType(name = "therapyPhase", propOrder = Array[String]("eventId", "title", "link","titleId", "beginDate", "endDate", "days"))
@XmlRootElement(name = "therapyPhase")
@JsonIgnoreProperties(ignoreUnknown = true)
class TherapyPhase() {

  @BeanProperty var eventId: Int = _
  @BeanProperty var title: String = _
  @BeanProperty var link: String = _
  @BeanProperty var titleId: Int = _
  @XmlJavaTypeAdapter(classOf[DateAdapter]) var beginDate: Date = _
  @XmlJavaTypeAdapter(classOf[DateAdapter]) var endDate: Date = _
  @BeanProperty var days: util.List[TherapyDay] = _

  def this(eventId: Int, title: String, link: String, titleId: Int, beginDate: Date, endDate: Date, days: util.List[TherapyDay]) {
    this()
    this.eventId = eventId
    this.title = title
    this.link = link
    this.titleId = titleId
    this.beginDate = beginDate
    this.endDate = endDate
    this.days = days
  }
}

@XmlType(name = "therapyDay", propOrder = Array[String]("day", "createDate", "eventId","docId"))
@XmlRootElement(name = "therapyDay")
@JsonIgnoreProperties(ignoreUnknown = true)
class TherapyDay() {

  def this(day: String, createDate: Date, eventId: Int, docId: Int) {
    this()
    this.day = day
    this.createDate = createDate
    this.eventId = eventId
    this.docId = docId
  }

  @BeanProperty var day: String = _
  @XmlJavaTypeAdapter(classOf[DateTimeAdapter]) var createDate: Date = _
  @BeanProperty var eventId: Int = _
  @BeanProperty var docId: Int = _

}


