package ru.korus.tmis.laboratory.data.request

import ru.korus.ws.{laboratory => lab, laboratory2 => lab2}


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

  implicit def toLab2IM(v: IndicatorMetodic) = {
    val ret = new lab2.Tindicator

    import Utility._

    implicit def locallyDefaultString = setDefault("")
    implicit def locallyDefaultInt = setDefault(-1)

    import v._

    setAsDefaultible(indicatorCode) {
      ret.setIndicatorCode(_)
    }
    setAsOptional(indicatorName) {
      ret.setIndicatorName(_)
    }

    ret
  }
}

