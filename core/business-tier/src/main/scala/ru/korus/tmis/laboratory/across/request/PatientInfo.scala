package ru.korus.tmis.laboratory.across.request

import ru.korus.tmis.laboratory.across.{ws => lab2}

import ru.korus.tmis.scala.util.{General, Defaultible}
import General.nullity_implicits

import DataConverter._
import java.util.Date
import ru.korus.tmis.core.entity.model.Sex
import java.util
import ru.korus.tmis.scala.util.Defaultible
import Defaultible._
import ru.korus.tmis.core.exception.CoreException
import scala.language.implicitConversions

sealed case class PatientInfo(
                               patientMisId: Int,
                               patientFamily: Option[String] = None,
                               patientName: Option[String] = None,
                               patientPatronum: Option[String] = None,
                               patientBirthDate: Option[Date] = None,
                               patientSex: Sex = Sex.UNDEFINED
                               )

object PatientInfo {

  implicit def toLab2PI(v: PatientInfo) = {
    val ret = new lab2.PatientInfo
    import v._

    implicit def locallyDefaultString = setDefault("")

    import Utility._

    ret.setPatientMisId(patientMisId)
    ret.setPatientSex(sex2int(patientSex))

    setAsOptional(patientFamily) {
      ret.setPatientFamily(_)
    }
    setAsOptional(patientName) {
      ret.setPatientName(_)
    }
    setAsOptional(patientPatronum) {
      ret.setPatientPatronum(_)
    }

    setAsRequired(new CoreException("PatientInfo: no birthdate found"))(patientBirthDate) {
      it =>
        ret.setPatientBirthDate(date2string(it))
    }
    ret
  }
}

