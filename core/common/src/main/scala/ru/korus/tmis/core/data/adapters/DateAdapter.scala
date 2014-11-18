package ru.korus.tmis.core.data.adapters

import java.text.SimpleDateFormat
import java.util.Date
import javax.xml.bind.annotation.adapters.{XmlJavaTypeAdapter, XmlAdapter}

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 10/31/14
 * Time: 7:22 PM
 */
class DateAdapter extends XmlAdapter[String, Date] {

private val dateFormat = new SimpleDateFormat("yyyy-MM-dd") // ISO 8601 date

  @throws(classOf[Exception])
  override def marshal(v: Date) = {
    dateFormat.format(v)
  }

  @throws(classOf[Exception])
  override def unmarshal(v: String) = {
    dateFormat.parse(v)
  }

}

class DateTimeAdapter extends XmlAdapter[String, Date] {

  private val dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss") // ISO 8601 local datetime

  @throws(classOf[Exception])
  override def marshal(v: Date) = {
    dateFormat.format(v)
  }

  @throws(classOf[Exception])
  override def unmarshal(v: String) = {
    dateFormat.parse(v)
  }

}

@XmlJavaTypeAdapter(classOf[DateAdapter])
class ISODate(d: Date) extends Date(d.getTime)
object ISODate { def apply(d: Date) = if(d != null) new ISODate(d) else null }

@XmlJavaTypeAdapter(classOf[DateTimeAdapter])
class ISODateTime(d: Date) extends Date(d.getTime)
object ISODateTime { def apply(d: Date) = if(d != null) new ISODate(d) else null }