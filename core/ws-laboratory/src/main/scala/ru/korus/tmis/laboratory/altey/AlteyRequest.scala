package ru.korus.tmis.laboratory.altey

import javax.jws.{HandlerChain, WebService}

import ru.korus.tmis.util.{CompileTimeConfigManager}
import javax.ejb.EJB
import ru.korus.tmis.laboratory.altey.business.AlteyBusinessBeanLocal
import ru.korus.tmis.scala.util.I18nable


/**
 * Запрос на анализ в ЛИС Алтей
 */
@WebService(
  endpointInterface = "ru.korus.tmis.laboratory.altey.AlteyRequestService",
  targetNamespace = "http://korus.ru/tmis/client-laboratory-altey",
  serviceName = "service-altey-request",
  portName = "service-altey-request",
  name = "service-altey-request")
@HandlerChain(file = "tmis-ws-lab-logging-handlers.xml")
class AlteyRequest extends AlteyRequestService with Logging with I18nable {

  @EJB
  var labBean: AlteyBusinessBeanLocal = _

  def sendAnalysisRequest(actionId: Int) {
    try {
      labBean.sendLisAnalysisRequest(actionId)
    }
    catch {
      case e: Throwable => ()
    }
  }
}