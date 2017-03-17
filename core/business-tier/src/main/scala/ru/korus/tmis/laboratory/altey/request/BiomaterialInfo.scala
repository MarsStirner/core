package ru.korus.tmis.laboratory.altey.request

import java.lang.{Integer => JInteger}
import java.util.Date

import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.laboratory.altey.request.DataConverter._
import ru.korus.tmis.laboratory.altey.ws.BiomaterialInfo
import ru.korus.tmis.laboratory.altey.{ws => lab}
import ru.korus.tmis.scala.util.Defaultible
import ru.korus.tmis.scala.util.Defaultible._

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
}

