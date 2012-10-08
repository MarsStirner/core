package ru.korus.tmis.ws.laboratory.lis1

import grizzled.slf4j.Logging

import ru.korus.tmis.util.I18nable
import javax.ejb.EJB
import ru.korus.tmis.laboratory.data.lis.accept._


import ru.korus.tmis.util.General.catchy
import ru.korus.tmis.core.exception.CoreException
import javax.jws.{HandlerChain, WebService}

import ru.korus.tmis.util.General.nullity_implicits
import ru.korus.tmis.laboratory.business.LaboratoryBeanLocal


import ru.korus.tmis.util.Types._

@WebService(
  endpointInterface = "ru.korus.tmis.ws.laboratory.lis1.TMISWebService",
  targetNamespace = "http://korus.ru/tmis/tmis-laboratory-integration",
  serviceName = "tmis-laboratory-integration",
  portName = "tmis",
  name = "tmis")
@HandlerChain(file = "tmis-ws-lab-logging-handlers.xml")
class TMISWebServiceImpl
  extends TMISWebService
  with Logging
  with I18nable {

  @EJB
  var labBean: LaboratoryBeanLocal = _

  def setAnalysisResults(
                          orderMisId: String,
                          referralIsFinished: Boolean,
                          results: JList[AnalysisResult],
                          biomaterialDefects: JString) = {
    val intOrder: Int = catchy {
      orderMisId.toInt
    }.getOrElse {
      throw new CoreException("Получено некорректное значение " +
        "orderMisId")
    }

    labBean.setLisAnalysisResults(intOrder, referralIsFinished, results ?: new JLinked[AnalysisResult](), biomaterialDefects ?: "")
  }
}