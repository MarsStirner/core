package ru.korus.tmis.ws.laboratory.tmis

import javax.jws.{HandlerChain, WebService}
import grizzled.slf4j.Logging
import ru.korus.tmis.util.{CompileTimeConfigManager, I18nable}
import javax.ejb.EJB
import ru.korus.tmis.laboratory.business.LaboratoryBeanLocal
import javax.interceptor.Interceptors
import ru.korus.tmis.core.logging.slf4j.interceptor.NoDBLoggingInterceptor

import CompileTimeConfigManager.LaboratoryClient._

@WebService(
  endpointInterface = "ru.korus.tmis.ws.laboratory.tmis.LaboratoryClientWebService",
  targetNamespace = Namespace,
  serviceName = ServiceName,
  portName = PortName,
  name = PortName)
@HandlerChain(file = "tmis-ws-lab-logging-handlers.xml")
class LaboratoryClientWebServiceImpl
  extends LaboratoryClientWebService
  with Logging
  with I18nable {

  @EJB
  var labBean: LaboratoryBeanLocal = _

  def sendAnalysisRequest(actionId: Int) {
    try{ labBean.sendLisAnalysisRequest(actionId) }
    catch { case e => () }
  }
}