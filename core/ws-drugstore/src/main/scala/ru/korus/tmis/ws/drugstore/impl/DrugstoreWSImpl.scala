package ru.korus.tmis.ws.drugstore.impl

import ru.korus.tmis.core.database.{DbActionBeanLocal, DbActionPropertyBeanLocal}
import ru.korus.tmis.core.entity.model.AssignmentHour
import ru.korus.tmis.core.event.{PrescriptionChangedNotification, CreateActionNotification}
import ru.korus.tmis.drugstore.event.ExternalEventFacadeBeanLocal
import ru.korus.tmis.ws.drugstore.DrugstoreWebService
import ru.korus.tmis.drugstore.data.{PrescriptionInfoList, PrescriptionInfo}

import com.google.common.collect.LinkedListMultimap
import java.lang.{Integer => JInteger}
import java.util.{Collection => JCollection, List => JList, LinkedList => JLinkedList, Map => JMap, HashMap => JHashMap}
import javax.ejb.EJB
import javax.enterprise.inject.{Any => AnyEE}
import javax.jws.{HandlerChain, WebService}

import grizzled.slf4j.Logging
import collection.JavaConversions._
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
  var dbAction: DbActionBeanLocal = _

  @EJB
  var dbActionProperty: DbActionPropertyBeanLocal = _

  @EJB
  var externalEventFacade: ExternalEventFacadeBeanLocal = _

  @EJB
  var drugstore: DrugstoreBeanLocal = _

  def notifyActionCreated(actionId: Int) = {
    val action = dbAction.getActionById(actionId)
    val values = dbActionProperty.getActionPropertiesByActionId(action.getId.intValue)
    externalEventFacade.triggerCreateActionNotification(
      new CreateActionNotification(action, values))
  }

  def notifyPrescriptionChanged(changes: PrescriptionInfoList) = {
    (LinkedListMultimap.create[JInteger, PrescriptionInfo]() /: changes.prescriptionInfoList)((m, pi) => {
      m.put(pi.actionId, pi)
      m
    }).asMap.foreach(t => {
      val (actionId, infos) = t
      val action = dbAction.getActionById(actionId.intValue)
      val values = dbActionProperty.getActionPropertiesByActionId(actionId.intValue)

      def toAssignmentHoursByType(infos: JCollection[PrescriptionInfo],
                                  typeId: Int) = {
        infos
          .filter(_.`type` == typeId)
          .map(info => new AssignmentHour(actionId.intValue, info.date, info.hour))
          .toSet
      }

      val created = toAssignmentHoursByType(infos, 1)
      val done = toAssignmentHoursByType(infos, 2)
      val notDone = toAssignmentHoursByType(infos, 3)
      val deleted = toAssignmentHoursByType(infos, 4)

      externalEventFacade.triggerPrescriptionChangedNotification(
        new PrescriptionChangedNotification(action, values, created, done, notDone, deleted)
      )
    })

  }

}
