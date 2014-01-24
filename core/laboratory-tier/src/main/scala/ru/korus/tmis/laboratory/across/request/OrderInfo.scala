package ru.korus.tmis.laboratory.across.request

import java.util.{List => JList}

import ru.korus.tmis.laboratory.across.{ws => lab2}

import scala.collection.JavaConversions._
import ru.korus.tmis.util.Defaultible._
import ru.korus.tmis.util.Defaultible
import ru.korus.tmis.laboratory.across.ws.Tindicator

sealed case class OrderInfo(
                             diagnosticCode: Option[String] = None,
                             diagnosticName: Option[String] = None,
                             orderPriority: Option[OrderInfo.OrderPriority] = None,
                             indicators: JList[IndicatorMetodic] = java.util.Collections.emptyList[IndicatorMetodic]
                             )

object OrderInfo {

  object OrderPriority extends Enumeration(1, "Urgent", "Normal") {
    type OrderPriority = Value
    val Urgent, Normal = Value
  }

  type OrderPriority = OrderPriority.OrderPriority

  implicit def toLab2OI(v: OrderInfo) = {

    import Utility._

    implicit def locallyDefaultString = setDefault("")
    implicit def locallyDefaultOrderPriority: Defaultible[OrderPriority] = setDefault(OrderPriority.Normal)

    import v._
    val ret = new lab2.OrderInfo
    setAsOptional(diagnosticCode) {
      ret.setDiagnosticCode(_)
    }
    setAsOptional(diagnosticName) {
      ret.setDiagnosticName(_)
    }
    setAsDefaultible(orderPriority) {
      it => ret.setOrderPriority(it.id)
    }

    val indics: JList[lab2.Tindicator] = v.indicators.map {
      IndicatorMetodic.toLab2IM(_)
    }

    val arrayOfIndics = indics.toArray(new Array[Tindicator](indics.size))
    ret.setIndicators(arrayOfIndics)

    ret
  }
}

