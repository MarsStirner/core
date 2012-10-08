package ru.korus.tmis.laboratory.data.request


import ru.korus.ws.{laboratory => lab, laboratory2 => lab2}
import java.util.Date
import java.lang.{Integer => JInteger}

import DataConverter._

import ru.korus.tmis.util.Defaultible._
import ru.korus.tmis.core.exception.CoreException

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
  implicit def toLab1BI(v: BiomaterialInfo) = {
    import Utility._

    implicit def locallyDefaultString = setDefault("")
    implicit def locallyDefaultInt = setDefault(-1)

    import v._

    val bi = new lab.BiomaterialInfo

    setAsDefaultible(orderBiomaterialCode) {
      bi.setOrderBiomaterialCode(_)
    }
    setAsDefaultible(orderBiomaterialname) {
      bi.setOrderBiomaterialname(_)
    }
    setAsDefaultible(orderBarCode) {
      bi.setOrderBarCode(_)
    }
    setAsRequired(new CoreException("BiomaterialInfo: orderProbeDate not specified"))(orderProbeDate) {
      it => bi.setOrderProbeDate(date2xmlGC(it))
    }
    setAsOptional(orderBiomaterialComment) {
      bi.setOrderBiomaterialComment(_)
    }

    bi
  }

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

