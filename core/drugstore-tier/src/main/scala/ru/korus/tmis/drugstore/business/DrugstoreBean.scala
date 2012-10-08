package ru.korus.tmis.drugstore.business

import javax.ejb.{Stateless, EJB}
import ru.korus.tmis.core.database.{DbActionPropertyBeanLocal, DbActionBeanLocal}
import ru.korus.tmis.drugstore.event.ExternalEventFacadeBeanLocal
import ru.korus.tmis.core.event.{PrescriptionChangedNotification, CreateActionNotification}
import com.google.common.collect.LinkedListMultimap
import java.lang.{Integer => JInteger}
import java.util.{LinkedList => JLinkedList, Collection => JCollection}
import ru.korus.tmis.core.entity.model.AssignmentHour
import ru.korus.tmis.drugstore.data.{PrescriptionInfo, PrescriptionInfoList}
import javax.interceptor.Interceptors
import ru.korus.tmis.core.logging.db.LoggingInterceptor
import collection.JavaConversions._

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DrugstoreBean extends DrugstoreBeanLocal {

  @EJB
  var dbAction: DbActionBeanLocal = _

  @EJB
  var dbActionProperty: DbActionPropertyBeanLocal = _

  @EJB
  var externalEventFacade: ExternalEventFacadeBeanLocal = _

  def notifyActionCreated(actionId: Int) = {
    val action = dbAction.getActionById(actionId)
    val values = dbActionProperty.getActionPropertiesByActionId(action.getId.intValue)
    externalEventFacade.triggerCreateActionNotification(new CreateActionNotification(action, values))
  }

  def notifyPrescriptionChanged(changes: PrescriptionInfoList): Unit = {
    if (changes == null || changes.prescriptionInfoList == null) {
      return
    }

    (LinkedListMultimap.create[JInteger, PrescriptionInfo]() /: changes.prescriptionInfoList)((m, pi) => {
      m.put(pi.actionId, pi)
      m
    }).asMap.foreach(t => {
      val (actionId, infos) = t
      val action = dbAction.getActionById(actionId.intValue)
      val values = dbActionProperty.getActionPropertiesByActionId(actionId.intValue)

      def toAssignmentHoursByType(infos: JCollection[PrescriptionInfo],
                                  typeId: Int) = {
        Option(infos)
          .getOrElse(new JLinkedList)
          .filter(_.`type` == typeId)
          .map(info => new AssignmentHour(actionId.intValue, info.date, info.hour))
          .toSet
      }

      val created = toAssignmentHoursByType(infos, 1)
      val done = toAssignmentHoursByType(infos, 2)
      val notDone = toAssignmentHoursByType(infos, 3)
      val unDone = toAssignmentHoursByType(infos, 6)
      val removed = toAssignmentHoursByType(infos, 4)
      val deleted = toAssignmentHoursByType(infos, 5)

      externalEventFacade.triggerPrescriptionChangedNotification(
        new PrescriptionChangedNotification(action, values, created, done, notDone ++ unDone, removed)
      )
    })
  }
}
