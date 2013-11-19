package ru.korus.tmis.laboratory.altey.request

import ru.korus.tmis.laboratory.altey.{ws => lab}


import DataConverter._
import java.util.Date
import ru.korus.tmis.core.entity.model.Sex
import ru.korus.tmis.util.Defaultible._
import ru.korus.tmis.core.exception.CoreException

sealed case class PatientInfo(
                               patientMisId: Int,
                               patientFamily: Option[String] = None,
                               patientName: Option[String] = None,
                               patientPatronum: Option[String] = None,
                               patientBirthDate: Option[Date] = None,
                               patientSex: Sex = Sex.UNDEFINED
                               )

object PatientInfo {
  implicit def toLab1PI(v: PatientInfo) = {
    val ret = new lab.PatientInfo
    import v._

    implicit def locallyDefaultString = setDefault("")

    import Utility._

    ret.setPatientMisId(patientMisId)
    ret.setPatientSex(sex2int(patientSex))

    setAsDefaultible(patientFamily) {
      ret.setPatientFamily(_)
    }
    setAsDefaultible(patientName) {
      ret.setPatientName(_)
    }
    setAsDefaultible(patientPatronum) {
      ret.setPatientPatronum(_)
    }

    setAsRequired(new CoreException("PatientInfo: no birthdate found"))(patientBirthDate) {
      it =>
        ret.setPatientBirthDate(date2string(it))
    }
    ret
  }
}

