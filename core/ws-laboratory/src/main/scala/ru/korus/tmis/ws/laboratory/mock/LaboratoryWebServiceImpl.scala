package ru.korus.tmis.ws.laboratory.mock

import javax.jws.{HandlerChain, WebService}
import ru.korus.tmis.util.CompileTimeConfigManager
import ru.korus.tmis.laboratory.data.lis.mock.{PatientInfo, OrderInfo, DiagnosticRequestInfo, BiomaterialInfo}

/**
 * Пустая реализация только для того, чтобы сгенерировать WSDL
 */
@WebService(
  endpointInterface = "ru.korus.tmis.ws.laboratory.mock.LaboratoryWebService",
  targetNamespace = CompileTimeConfigManager.Laboratory.Namespace,
  serviceName = CompileTimeConfigManager.Laboratory.ServiceName,
  portName = "IntegrationCorusPortType",
  name = "IntegrationCorus")
@HandlerChain(file = "tmis-ws-lab-logging-handlers.xml")
class LaboratoryWebServiceImpl
  extends LaboratoryWebService {
  def queryAnalysis(
                     patientInfo: PatientInfo,
                     requestInfo: DiagnosticRequestInfo,
                     biomaterialInfo: BiomaterialInfo,
                     orderInfo: OrderInfo) = {
    //empty
  }
}