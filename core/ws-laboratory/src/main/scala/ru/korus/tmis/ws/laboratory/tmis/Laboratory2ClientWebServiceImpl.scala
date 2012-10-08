package ru.korus.tmis.ws.laboratory.tmis

import javax.jws.{HandlerChain, WebService}
import grizzled.slf4j.Logging
import ru.korus.tmis.util.I18nable
import javax.ejb.EJB
import ru.korus.tmis.laboratory.business.LaboratoryBeanLocal

import CompileTimeConfigManager.Laboratory2Client._

@WebService(
  endpointInterface = "ru.korus.tmis.ws.laboratory.tmis.Laboratory2ClientWebService",
  targetNamespace = Namespace,
  serviceName = ServiceName,
  portName = PortName,
  name = PortName)
@HandlerChain(file = "tmis-ws-lab-logging-handlers.xml")
class Laboratory2ClientWebServiceImpl
  extends Laboratory2ClientWebService
  with Logging
  with I18nable {

  @EJB
  var labBean: LaboratoryBeanLocal = _

  def sendAnalysisRequest(actionId: Int) {
    try {
      labBean.sendLis2AnalysisRequest(actionId)
    }
    catch {
      case e => ()
    }
  }
}