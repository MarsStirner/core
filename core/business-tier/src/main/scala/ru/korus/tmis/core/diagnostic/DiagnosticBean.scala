package ru.korus.tmis.core.diagnostic

import ru.korus.tmis.core.auth.AuthData
import ru.korus.tmis.core.common.{CommonDataProcessorBeanLocal, TypeFilterBeanLocal}
import ru.korus.tmis.core.data._
import ru.korus.tmis.core.database._
import common._
import ru.korus.tmis.core.entity.model._
import ru.korus.tmis.scala.util.{I18nable, ConfigManager}
import ConfigManager.APWI


import javax.ejb.{EJB, Stateless}
import javax.interceptor.Interceptors

import scala.collection.JavaConversions._
import scala.language.reflectiveCalls

@Stateless
class DiagnosticBean
  extends DiagnosticBeanLocal

  with I18nable {

  type CA = CommonAttribute

  val AWI = ConfigManager.AWI

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
  var dbOrgStructure: DbOrgStructureBeanLocal = _

  @EJB
  var dbVersion: DbVersionBeanLocal = _

  @EJB
  var typeFilter: TypeFilterBeanLocal = _

  def getDiagnosticTypes(eventId: Int,
                         userData: Staff) = {
    var types = dbActionType.getDiagnosticTypes

    if (ConfigManager.Filter.isOn) {
      types = typeFilter.filterActionTypes(
        types,
        userData.getOrgStructure.getId.intValue,
        eventId)
    }

    commonDataProcessor.fromActionTypes(types,
      "DiagnosticType",
      converter)
  }

  def getAllDiagnosticTypes = {
    commonDataProcessor.fromActionTypes(dbActionType.getDiagnosticTypes,
      "DiagnosticType",
      converter)
  }

  def converter(apt: ActionPropertyType) = {
    new CA(apt.getId,
      0,
      apt.getName,
      apt.getTypeName,
      apt.getConstructorValueDomain,
      Map(APWI.Norm.toString -> apt.getNorm,
        APWI.Unit.toString -> apt.getUnit.getName,
        APWI.IsAssignable.toString -> apt.getIsAssignable.toString))
  }

  def getAllDiagnosticsByEventId(eventId: Int) = {
    commonDataProcessor.fromActions(
      customQuery
        .getAllDiagnosticsByEventId(eventId)
        .filter(_.getStatus != ConfigManager.ActionStatus.Canceled),
      "Diagnostic",
      List(summary _, details _))
  }

  def getDiagnosticById(diagnosticId: Int) = {
    commonDataProcessor.fromActions(
      List(dbAction.getActionById(diagnosticId)),
      "Diagnostic",
      List(summary _, details _))
  }

  def summary(diagnostic: Action) = {
    info("Getting Action summary: " + diagnostic.getId)

    val group = new CommonGroup(0, "Summary")

    val attributes = List(
      AWI.diagnosticId,
      AWI.diagnosticName,
      AWI.diagnosticDate,
      AWI.executorLastName,
      AWI.executorFirstName,
      AWI.executorMiddleName,
      AWI.executorPost
    )

    commonDataProcessor.addAttributes(
      group,
      new ActionWrapper(diagnostic),
      attributes)
  }

  def details(diagnostic: Action) = {
    info("Getting Action details: " + diagnostic.getId)

    val propertiesMap =
      dbActionProperty.getActionPropertiesByActionId(diagnostic.getId.intValue)

    val group = new CommonGroup(1, "Details")

    debug("properties:" + propertiesMap)

    propertiesMap.foreach(
      (p) => {
        val (ap, apvs) = p
        val apw = new ActionPropertyWrapper(ap, dbActionProperty.convertValue, dbActionProperty.convertScope, dbActionProperty.convertColType)
        group add apw.get(apvs.toList, List(APWI.Value,
          APWI.ValueId,
          APWI.Norm,
          APWI.Unit,
          APWI.IsAssignable,
          APWI.IsAssigned))
      })

    group
  }

  def createDiagnosticForEventId(eventId: Int,
                                 diagnostic: CommonData,
                                 userData: AuthData,
                                 staff: Staff) = {
    val createdActions =
      commonDataProcessor.createActionForEventFromCommonData(
        eventId,
        diagnostic,
        userData,
        staff)

    commonDataProcessor.fromActions(
      createdActions,
      "Diagnostic",
      List(summary _, details _))
  }

  def modifyDiagnosticById(diagnosticId: Int,
                           diagnostic: CommonData,
                           userData: AuthData,
                           staff: Staff) = {
    val modifiedActions = commonDataProcessor.modifyActionFromCommonData(
      diagnosticId,
      diagnostic,
      userData,
      staff)

    commonDataProcessor.fromActions(
      modifiedActions,
      "Diagnostic",
      List(summary _, details _))
  }

  def updateDiagnosticStatusById(eventId: Int,
                                 diagnosticId: Int,
                                 status: Short) = {
    commonDataProcessor.changeActionStatus(eventId,
      diagnosticId,
      status)
  }
}
