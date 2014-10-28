package ru.korus.tmis.laboratory.across.request

import ru.korus.tmis.laboratory.across.{ws => lab2}

import ru.korus.tmis.scala.util.{General, Defaultible}
import General.nullity_implicits

import DataConverter._
import ru.korus.tmis.scala.util.Defaultible
import Defaultible._
import scala.language.implicitConversions


sealed case class IndicatorMetodic(
                                    indicatorName: Option[String] = None,
                                    indicatorCode: Option[String] = None
                                    )

object IndicatorMetodic {

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

