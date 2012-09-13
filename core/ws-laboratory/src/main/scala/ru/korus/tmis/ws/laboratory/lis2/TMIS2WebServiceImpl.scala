package ru.korus.tmis.ws.laboratory.lis2

import grizzled.slf4j.Logging
import ru.korus.tmis.util.{CompileTimeConfigManager, ConfigManager, I18nable}
import ru.korus.tmis.util.Types.{JList, JLinked, JString, JBoolean, JInteger}
import javax.ejb.EJB
import ru.korus.tmis.laboratory.data.lis2.accept._

import ru.korus.tmis.util.General.catchy
import ru.korus.tmis.core.exception.CoreException
import javax.jws.{HandlerChain, WebService}

import ru.korus.tmis.util.General.nullity_implicits
import ru.korus.tmis.laboratory.business.LaboratoryBeanLocal


@WebService(
             endpointInterface = "ru.korus.tmis.ws.laboratory.lis2.TMIS2WebService",
             targetNamespace = CompileTimeConfigManager.Laboratory.Namespace,
             serviceName = "tmis2-laboratory-integration",
             portName = "tmis2",
             name = "tmis2")
@HandlerChain(file = "tmis-ws-lab-logging-handlers.xml")
class TMIS2WebServiceImpl
  extends TMIS2WebService
          with Logging
          with I18nable {

  @EJB
  var labBean: LaboratoryBeanLocal = _



  def setAnalysisResults(
                          orderMisId: String,
                          orderBarCode: Int,
                          orderPrefBarCode: Int,
                          referralIsFinished: JBoolean,
                          results: JList[AnalysisResult],
                          biomaterialDefects: JString,
                          doctorId: JInteger) = {

    import ru.korus.tmis.util.General.NumberImplicits._



    labBean.setLis2AnalysisResults(catchy(orderMisId.toInt).getOrElse(-1),
                                   orderBarCode,
                                   orderPrefBarCode,
                                   referralIsFinished.getOrElse(false),
                                   results ?: new JLinked[AnalysisResult](),
                                   biomaterialDefects ?: "")
  }
}