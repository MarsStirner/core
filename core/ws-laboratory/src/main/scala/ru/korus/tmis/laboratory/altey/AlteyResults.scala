package ru.korus.tmis.laboratory.altey

import grizzled.slf4j.Logging

import ru.korus.tmis.util.I18nable
import javax.ejb.EJB
import ru.korus.tmis.laboratory.altey.accept._


import ru.korus.tmis.util.General.catchy
import ru.korus.tmis.core.exception.CoreException
import javax.jws.{HandlerChain, WebService}

import ru.korus.tmis.util.General.nullity_implicits


import ru.korus.tmis.util.Types._
import ru.korus.tmis.laboratory.altey.business.AlteyBusinessBeanLocal

/**
 * Получение результатов от ЛИС Алтей
 */
@WebService(
  endpointInterface = "ru.korus.tmis.laboratory.altey.AlteyResultsService",
  targetNamespace = "http://korus.ru/tmis/tmis-laboratory-altey-integration",
  serviceName = "service-altey-results",
  portName = "service-altey-results",
  name = "service-altey-results")
@HandlerChain(file = "tmis-ws-lab-logging-handlers.xml")
class AlteyResults extends AlteyResultsService with Logging with I18nable {

  @EJB
  var labBean: AlteyBusinessBeanLocal = _

  def setAnalysisResults(orderMisId: String,
                         referralIsFinished: Boolean,
                         results: JList[AnalysisResult],
                         biomaterialDefects: JString) = {
    val intOrder: Int = catchy {
      orderMisId.toInt
    }.getOrElse {
      throw new CoreException("Получено некорректное значение orderMisId [" + orderMisId + "]")
    }
    labBean.setLisAnalysisResults(intOrder, referralIsFinished, results ?: new JLinked[AnalysisResult](), biomaterialDefects ?: "")
  }
}