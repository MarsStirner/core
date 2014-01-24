package ru.korus.tmis.laboratory.across

import grizzled.slf4j.Logging
import ru.korus.tmis.util.{CompileTimeConfigManager, I18nable}
import ru.korus.tmis.util.Types.{JList, JLinked, JString, JBoolean, JInteger}
import javax.ejb.EJB


import ru.korus.tmis.util.General.catchy
import javax.jws.{HandlerChain, WebService}

import ru.korus.tmis.util.General.nullity_implicits
import ru.korus.tmis.laboratory.across.business.AcrossBusinessBeanLocal
import ru.korus.tmis.laboratory.across.accept2.AnalysisResult

/**
 * Получение результатов от ЛИС Акрос
 */
@WebService(
  endpointInterface = "ru.korus.tmis.laboratory.across.AcrossResultsService",
  targetNamespace = "http://www.korusconsulting.ru",
  serviceName = "service-across-results",
  portName = "service-across-results",
  name = "service-across-results")
@HandlerChain(file = "tmis-ws-lab-logging-handlers.xml")
class AcrossResults extends AcrossResultsService with Logging with I18nable {

  @EJB
  var labBean: AcrossBusinessBeanLocal = _

  def setAnalysisResults(orderMisId: String,
                         orderBarCode: Int,
                         orderPrefBarCode: Int,
                         referralIsFinished: JBoolean,
                         results: JList[AnalysisResult],
                         biomaterialDefects: JString,
                         doctorId: JInteger) = {

    import ru.korus.tmis.util.General.NumberImplicits._

    labBean.setLis2AnalysisResults(
      catchy(orderMisId.toInt).getOrElse(-1),
      orderBarCode,
      orderPrefBarCode,
      referralIsFinished.getOrElse(false),
      results ?: new JLinked[AnalysisResult](),
      biomaterialDefects ?: ""
    )
  }
}