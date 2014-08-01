package ru.korus.tmis.laboratory.across.request

import java.util.Date

import ru.korus.tmis.laboratory.across.{ws => lab2}


import DataConverter._
import ru.korus.tmis.core.exception.CoreException

import ru.korus.tmis.scala.util.Defaultible
import Defaultible.setDefault
import ru.korus.tmis.laboratory.across.request.Utility._

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

  implicit def toLab2DRI(v: DiagnosticRequestInfo): lab2.DiagnosticRequestInfo = {
    implicit def locallyDefaultString = setDefault("")
    implicit def locallyDefaultInt = setDefault(-1)

    import v._
    val ret = new lab2.DiagnosticRequestInfo

    ret.setOrderMisId(orderMisId)

    setAsOptional(orderCaseId) {
      ret.setOrderCaseId(_)
    }

    setAsOptional(orderFinanceId) {
      ret.setOrderFinanceId(_)
    }

    setAsRequired(new CoreException("OrderMisDate: not found"))(orderMisDate) {
      it => ret.setOrderMisDate(date2GC(it))
    }

    for (min <- orderPregnatMin; max <- orderPregnatMax) yield ret.setOrderPregnat(min / 2 + max / 2)

    setAsOptional(orderDiagCode) {
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

