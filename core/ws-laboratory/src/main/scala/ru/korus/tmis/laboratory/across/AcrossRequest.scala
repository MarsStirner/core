package ru.korus.tmis.laboratory.across

import javax.jws.{HandlerChain, WebService}
import javax.ejb.EJB

import grizzled.slf4j.Logging
import ru.korus.tmis.laboratory.across.business.AcrossBusinessBeanLocal
import ru.korus.tmis.scala.util.I18nable

/**
 *  Запрос на анализ в ЛИС Акрос
 */
@WebService(
  endpointInterface = "ru.korus.tmis.laboratory.across.AcrossRequestService",
  targetNamespace = "http://korus.ru/tmis/client-laboratory",
  serviceName = "service-across-request",
  portName = "service-across-request",
  name = "service-across-request")
@HandlerChain(file = "tmis-ws-lab-logging-handlers.xml")
class AcrossRequest extends AcrossRequestService with Logging with I18nable {

  @EJB
  var labBean: AcrossBusinessBeanLocal = _

  /**
   *  @see ru.korus.tmis.laboratory.across.AcrossBusinessBeanLocal
   **/
  def sendAnalysisRequest(actionId: Int) {
    try {
      labBean.sendAnalysisRequestToAcross(actionId)
    } catch {
      case e: Throwable => error(e)
    }
  }
}