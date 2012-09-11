package ru.korus.tmis.drugstore.event

import ru.korus.tmis.core.event._
import ru.korus.tmis.core.logging.db.LoggingInterceptor

import javax.ejb.Stateless
import javax.enterprise.event.Event
import javax.inject.Inject
import javax.interceptor.Interceptors

import grizzled.slf4j.Logging

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class ExternalEventFacadeBean
  extends ExternalEventFacadeBeanLocal
  with Logging {

  @Inject
  var actionEvent: Event[Notification] = _

  def triggerCreateActionNotification(can: CreateActionNotification) = {
    actionEvent.fire(can)
  }

  def triggerPrescriptionChangedNotification(pcn: PrescriptionChangedNotification) = {
    actionEvent.fire(pcn)
  }
}
