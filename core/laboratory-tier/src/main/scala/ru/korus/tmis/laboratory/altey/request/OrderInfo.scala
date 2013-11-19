package ru.korus.tmis.laboratory.altey.request

import java.util.{List => JList}

import ru.korus.tmis.laboratory.altey.{ws => lab}


import scala.collection.JavaConversions._
import ru.korus.tmis.util.Defaultible._
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.util.Defaultible

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

  implicit def toLab1OI(v: OrderInfo) = {

    import Utility._

    implicit def locallyDefaultString = setDefault("")
    implicit def locallyDefaultOrderPriority: Defaultible[OrderPriority] = setDefault(OrderPriority.Normal)

    import v._
    val ret = new lab.OrderInfo

    setAsRequired(throw new CoreException("OrderInfo: Diagnostic code not found"))(diagnosticCode) {
      ret.setDiagnosticCode(_)
    }
    setAsOptional(diagnosticName) {
      ret.setDiagnosticName(_)
    }
    setAsDefaultible(orderPriority) {
      it => ret.setOrderPriority(it.id)
    }

    val indics: JList[lab.IndicatorMetodic] = v.indicators.map {
      IndicatorMetodic.toLab1IM(_)
    }

    ret.getIndicators.addAll(indics)

    ret
  }


}

