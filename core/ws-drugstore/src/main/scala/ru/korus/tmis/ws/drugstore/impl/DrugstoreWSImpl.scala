package ru.korus.tmis.ws.drugstore.impl

import ru.korus.tmis.ws.drugstore.DrugstoreWebService
import ru.korus.tmis.drugstore.data.PrescriptionInfoList

import java.lang.{Integer => JInteger}
import java.util.{Collection => JCollection, List => JList, LinkedList => JLinkedList, Map => JMap, HashMap => JHashMap}
import javax.ejb.EJB
import javax.enterprise.inject.{Any => AnyEE}
import javax.jws.{HandlerChain, WebService}

import grizzled.slf4j.Logging
import ru.korus.tmis.drugstore.business.DrugstoreBeanLocal

@WebService(
  endpointInterface = "ru.korus.tmis.ws.drugstore.DrugstoreWebService",
  targetNamespace = "http://korus.ru/tmis/drugstore",
  serviceName = "tmis-drugstore",
  portName = "drugstore",
  name = "drugstore"
)
@HandlerChain(file = "tmis-ws-drugs-logging-handlers.xml")
class DrugstoreWSImpl
  extends DrugstoreWebService
  with Logging {

  @EJB
  var drugstore: DrugstoreBeanLocal = _

  def notifyActionCreated(actionId: Int) = drugstore.notifyActionCreated(actionId)

  def notifyPrescriptionChanged(changes: PrescriptionInfoList): Unit = drugstore.notifyPrescriptionChanged(changes)
}
