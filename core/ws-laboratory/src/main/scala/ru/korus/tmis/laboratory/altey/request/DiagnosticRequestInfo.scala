package ru.korus.tmis.laboratory.altey.request

import javax.xml.bind.annotation._
import javax.xml.datatype.XMLGregorianCalendar
import java.util.Date

import ru.korus.tmis.laboratory.altey.{ws => lab}
import ru.korus.tmis.util.General.nullity_implicits

import DataConverter._
import java.util
import ru.korus.tmis.core.exception.CoreException

import ru.korus.tmis.util.Defaultible.setDefault
import ru.korus.tmis.laboratory.data.request.Utility._

sealed case class DiagnosticRequestInfo(
                                         orderMisId: Int,
                                         orderCaseId: Option[String],
                                         orderFinanceId: Option[Int],
                                         orderMisDate: Option[Date],
                                         orderPregnatMin: Option[Int],
                                         orderPregnatMax: Option[Int],
                                         orderDiagCode: Option[String],
                                         orderDiagText: Option[String],
                                         orderComment: Option[String],
                                         orderDepartmentName: Option[String],
                                         orderDepartmentMisCode: Option[String],
                                         orderDoctorFamily: Option[String],
                                         orderDoctorName: Option[String],
                                         orderDoctorPatronum: Option[String],
                                         orderDoctorMisId: Option[Int]
                                         )

object DiagnosticRequestInfo {

  implicit def toLab1DRI(v: DiagnosticRequestInfo): lab.DiagnosticRequestInfo = {
    import Utility._

    implicit def locallyDefaultString = setDefault("")
    implicit def locallyDefaultInt = setDefault(-1)

    import v._
    val ret = new lab.DiagnosticRequestInfo

    ret.setOrderMisId(orderMisId)

    setAsRequired(new CoreException("OrderMisDate: not found"))(orderMisDate) {
      it => ret.setOrderMisDate(date2xmlGC(it))
    }
    setAsOptional(orderPregnatMin) {
      ret.setOrderPregnatMin(_)
    }
    setAsOptional(orderPregnatMax) {
      ret.setOrderPregnatMax(_)
    }
    setAsDefaultible(orderDiagCode) {
      ret.setOrderDiagCode(_)
    }
    setAsOptional(orderDiagText) {
      ret.setOrderDiagText(_)
    }
    setAsOptional(orderComment) {
      ret.setOrderComment(_)
    }
    setAsOptional(orderDepartmentName) {
      ret.setOrderDepartmentName(_)
    }
    setAsDefaultible(orderDepartmentMisCode) {
      it => ret.setOrderDepartmentMisId(it)
    }
    setAsOptional(orderDoctorFamily) {
      ret.setOrderDoctorFamily(_)
    }
    setAsOptional(orderDoctorName) {
      ret.setOrderDoctorName(_)
    }
    setAsOptional(orderDoctorPatronum) {
      ret.setOrderDoctorPatronum(_)
    }
    setAsDefaultible(orderDoctorMisId) {
      it => ret.setOrderDoctorMisId(it.toString)
    }

    ret
  }
}

