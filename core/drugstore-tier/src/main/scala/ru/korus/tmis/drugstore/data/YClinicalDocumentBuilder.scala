package ru.korus.tmis.drugstore.data

import ru.korus.tmis.core.database.{DbEventBeanLocal, DbActionPropertyTypeBeanLocal}
import ru.korus.tmis.core.entity.model._
import ru.korus.tmis.core.logging.db.LoggingInterceptor
import ru.korus.tmis.drugstore.sync.RefResolverBeanLocal

import java.util.{Set => JSet, Map => JMap, List => JList}
import javax.ejb.{Stateless, EJB}
import javax.interceptor.Interceptors
import org.w3c.dom.Document

import grizzled.slf4j.Logging
import collection.JavaConversions._

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class YClinicalDocumentBuilder
  extends YClinicalDocumentBuilderLocal
  with Logging {

  val CreateDrugRequest = 0x01
  val CreateDrugEvent = 0x02
  val DeleteDrugRequest = 0x03
  val DeleteDrugEvent = 0x04

  @EJB
  var dbEvent: DbEventBeanLocal = _

  @EJB
  var dbActionPropertyType: DbActionPropertyTypeBeanLocal = _

  @EJB
  var refResolver: RefResolverBeanLocal = _

  def buildCreateDrugRequest(action: Action,
                             values: JMap[ActionProperty, JList[APValue]])
  : Document = {

    processCreateDrugRequest(action,
      action.getAssignmentHours,
      values)
  }

  def buildDeleteDrugRequest(action: Action,
                             values: JMap[ActionProperty, JList[APValue]])
  : Document = {

    processDeleteDrugRequest(action,
      action.getAssignmentHours,
      values)
  }

  def buildCreateDrugEvent(oldAction: Action,
                           newAction: Action,
                           values: JMap[ActionProperty, JList[APValue]])
  : Document = {

    val oldTimings = oldAction.getAssignmentHours.foldLeft(
      Map.empty[AssignmentHour, Boolean]
    )((map, ah) => map + (ah -> ah.isComplete))
    val newTimings = newAction.getAssignmentHours.foldLeft(
      Map.empty[AssignmentHour, Boolean]
    )((map, ah) => map + (ah -> ah.isComplete))

    val createDrugEventTimings = newTimings.filter(t => {
      val (ah, newCompleted) = t
      newCompleted == true &&
        oldTimings.get(ah) != None &&
        oldTimings.get(ah).get == false
    }).keySet

    processCreateDrugEvent(newAction,
      createDrugEventTimings,
      values)
  }

  def buildDeleteDrugEvent(oldAction: Action,
                           newAction: Action,
                           values: JMap[ActionProperty, JList[APValue]])
  : Document = {

    val oldTimings = oldAction.getAssignmentHours.foldLeft(
      Map.empty[AssignmentHour, Boolean]
    )((map, ah) => map + (ah -> ah.isComplete))
    val newTimings = newAction.getAssignmentHours.foldLeft(
      Map.empty[AssignmentHour, Boolean]
    )((map, ah) => map + (ah -> ah.isComplete))

    val deleteDrugEventTimings = newTimings.filter(t => {
      val (ah, newCompleted) = t
      newCompleted == false &&
        oldTimings.get(ah) != None &&
        oldTimings.get(ah).get == true
    }).keySet

    processDeleteDrugEvent(newAction,
      deleteDrugEventTimings,
      values)
  }

  def processCreateDrugRequest(action: Action,
                               timing: JSet[AssignmentHour],
                               properties: JMap[ActionProperty, JList[APValue]]) = {
    timing.size match {
      case 0 => null
      case _ => processDrugAdministration(action,
        timing,
        properties,
        CreateDrugRequest)
    }
  }

  def processDeleteDrugRequest(action: Action,
                               timing: JSet[AssignmentHour],
                               properties: JMap[ActionProperty, JList[APValue]]) = {
    timing.size match {
      case 0 => null
      case _ => processDrugAdministration(action,
        timing,
        properties,
        DeleteDrugRequest)
    }
  }

  def processCreateDrugEvent(action: Action,
                             timing: JSet[AssignmentHour],
                             properties: JMap[ActionProperty, JList[APValue]]) = {
    timing.size match {
      case 0 => null
      case _ => processDrugAdministration(action,
        timing,
        properties,
        CreateDrugEvent)
    }
  }

  def processDeleteDrugEvent(action: Action,
                             timing: JSet[AssignmentHour],
                             properties: JMap[ActionProperty, JList[APValue]]) = {
    timing.size match {
      case 0 => null
      case _ => processDrugAdministration(action,
        timing,
        properties,
        DeleteDrugEvent)
    }
  }

  private def processDrugAdministration(action: Action,
                                        timing: JSet[AssignmentHour],
                                        properties: JMap[ActionProperty, JList[APValue]],
                                        mode: Int): Document = {

    val drugAPT = dbActionPropertyType.getDrugNomenAPT
    val dosageAPT = dbActionPropertyType.getDosageAPT

    val dosage = properties.filter(tuple => {
      val (ap, apvs) = tuple
      dosageAPT(ap.getType)
    }).toList

    val drugProperties = action.getActionPropertiesByTypes(drugAPT)
    if (drugProperties.isEmpty) {
      error("No drug for action: " + action)
      return null
    }

    val drugValueList = drugProperties.values.foldLeft(List[APValue]())(
      (list, dp) => {
        list ++ properties.get(dp)
      })

    val drugValue = drugValueList.size match {
      case 0 => {
        error("No drug set for action: " + action)
        return null
      }
      case 1 => {
        drugValueList.get(0)
      }
      case _ => {
        error("Several drugs set for action: " + action)
        return null
      }
    }

    if (drugValue.isInstanceOf[APValueRLSWrapper]) {
      val xml = new YClinicalDocument

      val org = dbEvent.getOrgStructureForEvent(action.getEvent.getId.intValue)
      val orgRef = refResolver.getOrganizationRef(org.getOrganization)
      if (orgRef == null) {
        error("Cannot find ref for " + org)
        return null
      }
      xml.setOrgRef(orgRef)

      val drug = drugValue.asInstanceOf[APValueRLSWrapper].getNomenclature

      val event = action.getEvent match {
        case null => {
          error("No event set for action: " + action)
          return null
        }
        case e: Event => e
      }
      xml.setPatient(event.getPatient)
      xml.setAuthor(event.getExecutor)
      xml.setEvent(event)

      mode match {
        case CreateDrugRequest => {
          xml.addDrugRequest(action, dosage, drug, timing)
        }
        case CreateDrugEvent => {
          xml.addDrugEvent(action, dosage, drug, timing)
        }
        case DeleteDrugRequest => {
          xml.addDrugRequestCancel(action, dosage, drug, timing)
        }
        case DeleteDrugEvent => {
          xml.addDrugEventCancel(action, dosage, drug, timing)
        }
      }

      info("processDrugAdministration " + xml.toXmlString)

      return xml.toXmlDom
    } else {
      error("Invalid drug set for action: " + action)
      return null
    }
  }
}
