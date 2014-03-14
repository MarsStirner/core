package ru.korus.tmis.core.treatment

import ru.korus.tmis.core.auth.AuthData
import ru.korus.tmis.core.common.{CommonDataProcessorBeanLocal, TypeFilterBeanLocal}
import ru.korus.tmis.core.data._
import ru.korus.tmis.core.database._
import common.{DbCustomQueryLocal, DbActionPropertyBeanLocal, DbManagerBeanLocal, DbActionBeanLocal}
import ru.korus.tmis.core.entity.model._
import ru.korus.tmis.core.logging.LoggingInterceptor
import ru.korus.tmis.scala.util.ConfigManager
import ConfigManager.APWI

import grizzled.slf4j.Logging
import java.util.Date
import javax.ejb.{EJB, Stateless}
import javax.interceptor.Interceptors

import scala.collection.JavaConversions._
import ru.korus.tmis.core.logging.LoggingInterceptor
import ru.korus.tmis.core.entity.model.{ActionPropertyWrapper, ActionWrapper, ActionPropertyType, Nomenclature}
import ru.korus.tmis.scala.util.ConfigManager

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class TreatmentBean
  extends TreatmentBeanLocal
  with Logging {

  val AWI = ConfigManager.AWI
  val CMDF = ConfigManager.DateFormatter

  @EJB
  var commonDataProcessor: CommonDataProcessorBeanLocal = _

  @EJB
  var customQuery: DbCustomQueryLocal = _

  @EJB
  var dbAction: DbActionBeanLocal = _

  @EJB
  var dbActionType: DbActionTypeBeanLocal = _

  @EJB
  var dbActionProperty: DbActionPropertyBeanLocal = _

  @EJB
  var dbManager: DbManagerBeanLocal = _

  @EJB
  var dbRls: DbRlsBeanLocal = _

  @EJB
  var dbVersion: DbVersionBeanLocal = _

  @EJB
  var typeFilter: TypeFilterBeanLocal = _

  def getRlsList = {
    Nomenclature.getCachedRlsList.foldLeft(new RlsData(dbVersion.getGlobalVersion))(
      (data, drug) => {
        data add new RlsEntry(
          null, //drug.getId,
          drug.getCode,
          drug.getTradeName,
          null, //drug.getTradeNameLat,
          null, //drug.getINPName,
          drug.getINPNameLat,
          null, //drug.getForm,
          drug.getDosage,
          null, //drug.getFilling,
          null) //drug.getPacking)
        data
      })
  }

  def getCompactRlsList = {
    CompactRlsDataBuilder.fromNomenclature(Nomenclature.getCachedRlsList, dbVersion.getGlobalVersion)
  }

  def getTreatmentTypes(eventId: Int,
                        userData: AuthData) = {
    var types = dbActionType.getTreatmentTypes

    if (ConfigManager.Filter.isOn) {
      types = typeFilter.filterActionTypes(
        types,
        userData.user.getOrgStructure.getId.intValue,
        eventId)
    }

    commonDataProcessor.fromActionTypes(types,
      "TreatmentType",
      converter)
  }

  def getAllTreatmentTypes = {
    commonDataProcessor.fromActionTypes(dbActionType.getTreatmentTypes,
      "TreatmentType",
      converter)
  }

  def converter(apt: ActionPropertyType) = {
    new CommonAttribute(
      apt.getId,
      0,
      apt.getName,
      apt.getTypeName,
      apt.getConstructorValueDomain)
  }

  def createTreatmentForEventId(eventId: Int,
                                treatment: CommonData,
                                userData: AuthData) = {
    val createdActions =
      commonDataProcessor.createActionForEventFromCommonData(
        eventId,
        treatment,
        userData)

    commonDataProcessor.fromActions(
      createdActions,
      "Treatment",
      List(summary _, details _))
  }

  def modifyTreatmentById(treatmentId: Int,
                          treatment: CommonData,
                          userData: AuthData) = {
    val modifiedActions = commonDataProcessor.modifyActionFromCommonData(
      treatmentId,
      treatment,
      userData)

    commonDataProcessor.fromActions(
      modifiedActions,
      "Treatment",
      List(summary _, details _))
  }

  def getTreatmentInfo(eventId: Int,
                       actionTypeId: java.lang.Integer,
                       beginDate: Date,
                       endData: Date) = {
    val ts = actionTypeId match {
      case null => {
        customQuery.getTreatmentInfo(eventId,
          beginDate,
          endData)
      }
      case _ => {
        customQuery.getTreatmentInfo(eventId,
          actionTypeId.intValue,
          beginDate,
          endData)
      }
    }

    commonDataProcessor.fromActions(
      ts,
      "Treatment",
      List(summary _, details _))
  }

  def getTreatmentById(treatmentId: Int) = {
    commonDataProcessor.fromActions(
      List(dbAction.getActionById(treatmentId)),
      "Treatment",
      List(summary _, details _))
  }

  def summary(treatment: Action) = {
    val group = new CommonGroup(0, "Summary")

    val attributes = List(
      AWI.treatmentName,
      AWI.treatmentBeginDate,
      AWI.treatmentEndDate,
      AWI.treatmentDates,
      AWI.doctorLastName,
      AWI.doctorFirstName,
      AWI.doctorMiddleName,
      AWI.doctorSpecs
    )

    commonDataProcessor.addAttributes(
      group,
      new ActionWrapper(treatment),
      attributes)
  }

  def details(treatment: Action) = {
    val propertiesMap =
      dbActionProperty.getActionPropertiesByActionId(treatment.getId.intValue)

    val group = new CommonGroup(1, "Details")

    propertiesMap.foreach(
      (p) => {
        val (ap, apvs) = p
        val apw = new ActionPropertyWrapper(ap, dbActionProperty.fromRefValue, dbActionProperty.getScopeForReference)

        apvs.size match {
          case 0 => {
            group add apw.get(null, List.empty)
          }
          case _ => {
            apvs.foreach((apv) => {
              group add apw.get(apv, List(APWI.Value, APWI.ValueId))
            })
          }
        }
      })

    group
  }

  def verifyDrugTreatment(eventId: Int,
                          actionId: Int,
                          drugId: Int) = {
    new CommonData add (
      new CommonEntity(ConfigManager.VeriDrug.Compatible,
        null,
        null,
        null,
        null,
        null,
        null) add (
        new CommonGroup add (
          new CommonAttribute(null,
            null,
            "ResultText",
            null,
            null,
            "<COMMENT TEXT>")
          )
        )
      )
  }

  def revokeTreatment(eventId: Int, actionId: Int) = {
    commonDataProcessor.changeActionStatus(eventId,
      actionId,
      ConfigManager.ActionStatus.Canceled)
  }
}
