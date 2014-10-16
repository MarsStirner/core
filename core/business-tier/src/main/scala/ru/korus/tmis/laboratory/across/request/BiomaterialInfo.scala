package ru.korus.tmis.laboratory.across.request


import ru.korus.tmis.laboratory.across.{ws => lab2}
import java.util.Date

import DataConverter._

import ru.korus.tmis.scala.util.Defaultible
import Defaultible._
import ru.korus.tmis.core.exception.CoreException
import scala.language.implicitConversions

sealed case class BiomaterialInfo(
                                   orderBiomaterialCode: Option[String] = None,
                                   orderBiomaterialname: Option[String] = None,
                                   orderBarCode: Option[String] = None,
                                   orderBarCodePeriod: Option[Int] = None,
                                   orderTakenTissueId: Option[Int] = None,
                                   orderProbeDate: Option[Date] = None,
                                   orderBiomaterialComment: Option[String] = None
                                   )

object BiomaterialInfo {
  implicit def toLab2BI(v: BiomaterialInfo) = {
    import Utility._

    implicit def locallyDefaultString = setDefault("")
    implicit def locallyDefaultInt = setDefault(-1)

    import v._

    val bi = new lab2.BiomaterialInfo

    setAsOptional(orderBiomaterialCode) {
      bi.setOrderBiomaterialCode(_)
    }
    setAsOptional(orderBiomaterialname) {
      bi.setOrderBiomaterialName(_)
    }
    setAsDefaultible(orderBarCode) {
      it => bi.setOrderBarCode(it.toString)
    }
    setAsDefaultible(orderBarCodePeriod) {
      it => bi.setOrderPrefBarCode(it)
    }
    setAsRequired(new CoreException("BiomaterialInfo: orderProbeDate not specified"))(orderProbeDate) {
      it => bi.setOrderProbeDate(date2GC(it))
    }
    setAsOptional(orderBiomaterialComment) {
      bi.setOrderBiomaterialComment(_)
    }

    bi
  }
}

