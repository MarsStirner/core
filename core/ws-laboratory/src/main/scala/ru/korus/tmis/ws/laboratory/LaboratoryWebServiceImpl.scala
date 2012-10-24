package ru.korus.tmis.ws.laboratory

import ru.korus.tmis.lis.data.{DiagnosticRequestInfo}
import javax.jws.WebService
import ru.korus.tmis.lis.data._

/**
 * Пустая реализация только для того, чтобы сгенерировать WSDL
 */
@WebService(
  endpointInterface = "ru.korus.tmis.ws.laboratory.LaboratoryWebService",
  targetNamespace = "http://korus.ru/laboratory/labisws",
  serviceName = "labisws",
  portName = "labis",
  name = "labisws")
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