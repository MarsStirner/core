package ru.korus.tmis.ws.pharmacy.impl

import ru.korus.tmis.core.entity.model.Medicament
import ru.korus.tmis.util.I18nable
import ru.korus.tmis.ws.pharmacy.PharmacyWebService

import grizzled.slf4j.Logging
import java.util.LinkedList
import javax.jws.WebService

@WebService(
  endpointInterface = "ru.korus.tmis.ws.pharmacy.PharmacyWebService",
  targetNamespace = "http://korus.ru/tmis/pharmacy",
  serviceName = "tmis-pharmacy",
  portName = "pharmacy",
  name = "pharmacy")
class PharmacyWebServiceImpl
  extends PharmacyWebService
  with Logging
  with I18nable {

  def getAvailableMedicaments(medicament: String) = {
    val result = new LinkedList[Medicament]
    result.add(new Medicament(42, "Ацитонаногликогель 0.03 мл."))
    result
  }

  def getIsMedicamentAvailable(code: Int): Boolean = {
    return true
  }

  def setPrescription(prescriptionId: Int): Unit = {
  }
}
