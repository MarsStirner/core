package ru.korus.tmis.laboratory.altey

import grizzled.slf4j.Logging

import javax.ejb.EJB
import ru.korus.tmis.laboratory.altey.accept._


import ru.korus.tmis.scala.util.{Types, General, I18nable}
import General.catchy
import ru.korus.tmis.core.exception.CoreException
import javax.jws.{HandlerChain, WebService}

import General.nullity_implicits


import Types._
import ru.korus.tmis.laboratory.altey.business.AlteyBusinessBeanLocal

/**
 * Получение результатов от ЛИС Алтей
 * @deprecated Сделано для Пензы, пока разработчики Алтея не уберут хардкод адреса ФТМИС для результатов
 */
@WebService(
  endpointInterface = "ru.korus.tmis.laboratory.altey.AlteyResultsService",
  targetNamespace = "http://korus.ru/tmis/tmis-laboratory-integration",
  serviceName = "tmis-laboratory-integration",
  portName = "tmis",
  name = "tmis")
@HandlerChain(file = "tmis-ws-lab-logging-handlers.xml")
class AlteyOldResults extends AlteyResultsService with Logging with I18nable {

  @EJB
  var labBean: AlteyBusinessBeanLocal = _

  def setAnalysisResults(orderMisId: String,
                         referralIsFinished: Boolean,
                         results: JList[ru.korus.tmis.laboratory.altey.accept2.AnalysisResult],
                         biomaterialDefects: JString) = {
    val intOrder: Int = catchy {
      orderMisId.toInt
    }.getOrElse {
      throw new CoreException("Получено некорректное значение orderMisId [" + orderMisId + "]")
    }
    labBean.setLisAnalysisResults(intOrder, referralIsFinished, results ?: new JLinked[ru.korus.tmis.laboratory.altey.accept2.AnalysisResult](), biomaterialDefects ?: "")
  }
}