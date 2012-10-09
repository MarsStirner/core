package ru.korus.tmis.drugstore.event

import ru.korus.tmis.core.database.{DbActionPropertyBeanLocal, DbActionTypeBeanLocal}
import ru.korus.tmis.core.entity.model.ActionType
import ru.korus.tmis.core.event._
import ru.korus.tmis.core.logging.LoggingInterceptor
import ru.korus.tmis.drugstore.data.{YRcmrWrapperDocument, YClinicalDocumentBuilderLocal}
import ru.korus.tmis.drugstore.util.SoapAcknowlegement

import java.util.{Set => JSet}
import javax.annotation.PostConstruct
import javax.ejb.{EJB, Asynchronous, Stateless}
import javax.enterprise.event.Observes
import javax.interceptor.Interceptors

import grizzled.slf4j.Logging
import ru.korus.tmis.drugstore.util.{SoapAcknowlegement, Soaping}

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class AdministrationEventProcessorBean
  extends AdministrationEventProcessorBeanLocal
  with SoapAcknowlegement
  with Logging {

  @EJB
  var dbActionType: DbActionTypeBeanLocal = _

  @EJB
  var dbActionProperty: DbActionPropertyBeanLocal = _

  @EJB
  var yClinicalDocumentBuilder: YClinicalDocumentBuilderLocal = _

  var drugTreatmentTypes: JSet[ActionType] = _

  @PostConstruct
  def init() = {
    drugTreatmentTypes = dbActionType.getDrugTreatmentTypes
  }

  @Asynchronous
  def trigger(@Observes n: Notification): Unit = {
    val ycds = n match {
      case can: CreateActionNotification => {
        if (drugTreatmentTypes.contains(can.a.getActionType)) {
          info("Triggered CreateActionNotification for drugstore: " + can)
          List(
            yClinicalDocumentBuilder.buildCreateDrugRequest(can.a, can.values)
          )
        } else {
          List.empty
        }
      }
      case man: ModifyActionNotification => {
        if (drugTreatmentTypes.contains(man.newAction.getActionType)) {
          info("Triggered ModifyActionNotification for drugstore: " + man)
          List(
            yClinicalDocumentBuilder.buildCreateDrugEvent(
              man.oldAction, man.newAction, man.newValues),
            yClinicalDocumentBuilder.buildDeleteDrugEvent(
              man.oldAction, man.newAction, man.newValues)
          )
        } else {
          List.empty
        }
      }
      case can: CancelActionNotification => {
        if (drugTreatmentTypes.contains(can.a.getActionType)) {
          info("Triggered CancelActionNotification for drugstore: " + can)
          List(
            yClinicalDocumentBuilder.buildDeleteDrugRequest(can.a, can.values)
          )
        } else {
          List.empty
        }
      }
      case pcn: PrescriptionChangedNotification => {
        if (drugTreatmentTypes.contains(pcn.a.getActionType)) {
          info("Triggered PrescriptionChangedNotification for drugstore: " + pcn)
          processPrescriptionChangedNotification(pcn)
        } else {
          List.empty
        }
      }
      case _ => {
        List.empty
      }
    }

    ycds.foreach(ycd => {
      ycd match {
        case null => {}
        case document => sendSoapMessage(new YRcmrWrapperDocument(document).toXmlDom,
          CMD.Hl7_RequestRootElement,
          CMD.Hl7_SoapAction,
          CMD.Hl7_SoapOperation,
          CMD.Hl7_XsiType)
      }
    })
  }

  def processPrescriptionChangedNotification(pcn: PrescriptionChangedNotification) = {
    val properties = dbActionProperty.getActionPropertiesByActionId(pcn.a.getId.intValue())
    List(
      yClinicalDocumentBuilder.processCreateDrugRequest(pcn.a, pcn.created, properties),
      yClinicalDocumentBuilder.processCreateDrugEvent(pcn.a, pcn.done, properties),
      yClinicalDocumentBuilder.processDeleteDrugEvent(pcn.a, pcn.notDone, properties),
      yClinicalDocumentBuilder.processDeleteDrugRequest(pcn.a, pcn.deleted, properties)
    )
  }

}
