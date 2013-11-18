package ru.korus.tmis.laboratory.altey.request

import ru.korus.tmis.laboratory.altey.{ws => lab}

import ru.korus.tmis.util.General.nullity_implicits

import DataConverter._
import ru.korus.tmis.util.Defaultible._


sealed case class IndicatorMetodic(
                                    indicatorName: Option[String] = None,
                                    indicatorCode: Option[String] = None
                                    )

object IndicatorMetodic {
  implicit def toLab1IM(v: IndicatorMetodic) = {
    val ret = new lab.IndicatorMetodic

    import Utility._

    implicit def locallyDefaultString = setDefault("")
    implicit def locallyDefaultInt = setDefault(-1)

    import v._

    setAsOptional(indicatorCode) {
      ret.setIndicatorCode(_)
    }
    setAsDefaultible(indicatorName) {
      ret.setIndicatorName(_)
    }

    ret
  }
}

