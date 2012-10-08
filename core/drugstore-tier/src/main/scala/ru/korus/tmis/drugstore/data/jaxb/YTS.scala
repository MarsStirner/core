package ru.korus.tmis.drugstore.data.jaxb

import org.hl7.v3._
import ru.korus.tmis.core.entity.model.{AssignmentHour, Patient}

import java.text.SimpleDateFormat
import java.util.{Calendar, GregorianCalendar, Date}
import javax.xml.bind.annotation.XmlTransient

class YAuthorTimeTS extends TS {
  @XmlTransient
  val timeFormatter = new SimpleDateFormat("yyyyMMddhhmmssZ")

  setValue(timeFormatter.format(new Date))
}

class YBirthTimeTS(@XmlTransient p: Patient) extends TS {
  @XmlTransient
  val timeFormatter = new SimpleDateFormat("yyyyMMdd")

  setValue(timeFormatter.format(p.getBirthDate))
}

class YGenericTimeTS(@XmlTransient d: Date) extends TS {
  @XmlTransient
  val timeFormatter = new SimpleDateFormat("yyyyMMdd")

  setValue(timeFormatter.format(d))

  def this() = {
    this(new Date())
  }
}

class YDrugTimeTS(@XmlTransient hour: AssignmentHour) extends SXCMTS {
  @XmlTransient
  val calendar = new GregorianCalendar

  @XmlTransient
  val factory = new ObjectFactory

  @XmlTransient
  val timeFormatter = new SimpleDateFormat("yyyyMMddHHmmss")

  val fixedDatetime = new GregorianCalendar
  fixedDatetime.setTime(hour.getId.getCreateDatetime)

  fixedDatetime.set(Calendar.HOUR_OF_DAY, hour.getId.getHour)
  fixedDatetime.set(Calendar.MINUTE, 0)
  fixedDatetime.set(Calendar.SECOND, 0)

  setValue(timeFormatter.format(fixedDatetime.getTime))
}

class YNullTimeTS extends IVLTS {
  getNullFlavor.add("NI")
}
