package ru.korus.tmis.laboratory.bak

import javax.jws.WebService

import ru.korus.tmis.lis.data.model.hl7.complex.{MCCIIN000002UV01, POLBIN224100UV01}

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 8/14/14
 * Time: 1:41 PM
 */
@WebService(endpointInterface = "ru.korus.tmis.laboratory.bak.BakResultService", targetNamespace = "http://www.korusconsulting.ru", serviceName = "service-bak-results", portName = "service-bak-results", name = "service-bak-results")
class BakResults extends BakResultService {

  override def setAnalysisResults(response: POLBIN224100UV01): MCCIIN000002UV01 = {

    var i = 10

    i += 1

    new MCCIIN000002UV01
  }

  override def bakDelivered(orderBarCode: Integer, takenTissueJournal: String, tissueTime: String, orderBiomaterialName: String): Int = {

    var i = 10

    i += 1

    0
  }

}
