package ru.korus.tmis.laboratory.across.request

import java.util
import java.text.SimpleDateFormat
import javax.xml.datatype.{DatatypeFactory, XMLGregorianCalendar}
import util.GregorianCalendar
import ru.korus.tmis.core.entity.model.Sex


object DataConverter {

  def date2string(date: java.util.Date): String = {
    val c = new SimpleDateFormat("dd.MM.yyyy")
    c.format(date)
  }

  def date2GC(date: java.util.Date): GregorianCalendar = {
    val c = new GregorianCalendar
    c.setTime(date)
    c
  }

  def date2xmlGC(date: java.util.Date): XMLGregorianCalendar = {
    val c = new GregorianCalendar
    c.setTime(date)
    val gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(c)

    gc
  }

  def sex2int(s: Sex): Int = {
    s match {
      case Sex.UNDEFINED => 3
      case Sex.MEN => 1
      case Sex.WOMEN => 2
    }
  }

  def tmisSex2int(sex: Short): Int = {
    sex2int(Sex.valueOf(sex))
  }


}
