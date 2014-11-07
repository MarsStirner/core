package ru.korus.tmis.core.assessment

import ru.korus.tmis.core.auth.AuthData
import ru.korus.tmis.core.common.{CommonDataProcessorBeanLocal, TypeFilterBeanLocal}
import ru.korus.tmis.core.data._
import ru.korus.tmis.core.database._
import common.{DbCustomQueryLocal, DbActionPropertyBeanLocal, DbActionBeanLocal}
import ru.korus.tmis.core.entity.model._
import ru.korus.tmis.core.logging.LoggingInterceptor
import ru.korus.tmis.scala.util.{I18nable, ConfigManager}
import ConfigManager.APWI

import grizzled.slf4j.Logging
import javax.ejb.{EJB, Stateless}
import javax.interceptor.Interceptors

import scala.collection.JavaConversions._
import java.util.Date
import scala.language.reflectiveCalls

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class AssessmentBean
  extends AssessmentBeanLocal
  with Logging
  with I18nable {

  type CA = CommonAttribute

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
  var dbVersion: DbVersionBeanLocal = _

  @EJB
  var typeFilter: TypeFilterBeanLocal = _

  def getAssessmentTypes(eventId: Int,
                         userData: AuthData) = {
    var types = dbActionType.getAssessmentTypes

    if (ConfigManager.Filter.isOn) {
      types = typeFilter.filterActionTypes(
        types,
        userData.user.getOrgStructure.getId.intValue,
        eventId)
    }

    commonDataProcessor.fromActionTypes(types,
      "AssessmentType",
      converter)
  }

  def getAllAssessmentTypes = {
    commonDataProcessor.fromActionTypes(dbActionType.getAssessmentTypes,
      "AssessmentType",
      converter)
  }

  def converter(apt: ActionPropertyType) = {
    val unit = apt.getUnit match {
      case null => ""
      case u: RbUnit => u.getName
    }
    new CA(apt.getId,
      0,
      apt.getName,
      apt.getTypeName,
      apt.getConstructorValueDomain,
      Map("unit" -> unit)
    )
  }

  def getAllAssessmentsByEventId(eventId: Int) = {
    commonDataProcessor.fromActions(
      customQuery.getAllAssessmentsByEventId(eventId),
      "Assessment",
      List(summary _, details _))
  }

  def getAssessmentById(assessmentId: Int) = {
    commonDataProcessor.fromActions(
      List(dbAction.getActionById(assessmentId)),
      "Assessment",
      List(summary _, details _))
  }

  def summary(assessment: Action) = {
    val group = new CommonGroup(0, "Summary")

    val attributes = List(
      AWI.assessmentId,
      AWI.assessmentName,
      AWI.assessmentDate,
      AWI.doctorLastName,
      AWI.doctorFirstName,
      AWI.doctorMiddleName,
      AWI.doctorSpecs
    )

    commonDataProcessor.addAttributes(
      group,
      new ActionWrapper(assessment),
      attributes)
  }

  def details(assessment: Action) = {
    val propertiesMap =
      dbActionProperty.getActionPropertiesByActionId(assessment.getId.intValue)

    val group = new CommonGroup(1, "Details")

    propertiesMap.foreach(
      (p) => {
        val (ap, apvs) = p
        val apw = new ActionPropertyWrapper(ap, dbActionProperty.convertValue, dbActionProperty.convertScope)

        apvs.size match {
          case 0 => {
            group add apw.get(null, List(APWI.Unit,
              APWI.Norm))
          }
          case _ => {
            apvs.foreach((apv) => {
              group add apw.get(apv, List(APWI.Value,
                APWI.ValueId,
                APWI.Unit,
                APWI.Norm))
            })
          }
        }
      })

    group
  }

  def getIndicators(eventId: Int, beginDate: Date, endDate: Date) = {
    val cd = new CommonData
    val e = new CommonEntity(eventId, null, "Индикаторы", "EventIndicators", null, null, null)
    cd.add(e)

    val cdTemperatureId = 0
    val cdPressureLowId = 1
    val cdPressureHighId = 2
    val cdBreathingId = 3
    val cdHeartBeatId = 4

    // Добавляем значения температуры
    val temps = customQuery.getIndicatorsByEventIdAndIndicatorNameAndDates(
      eventId,
      i18n("db.apt.temperatureName"),
      beginDate,
      endDate)
    val gT = temps.foldLeft(
      new CommonGroup(cdTemperatureId, "Temperature values"))(
      (g, t) => {
        g.add(new CA(t.getId,
          0,
          t.getName,
          ConfigManager.Types.Double,
          null,
          Map("value" -> t.getValue.toString,
            "datetime" -> CMDF.format(t.getDate))))
      })
    e.add(gT)

    // Добавляем значения АД верхн. - верхней границы артериального давления
    val pressHigh = customQuery.getIndicatorsByEventIdAndIndicatorNameAndDates(
      eventId,
      i18n("db.apt.bloodPressureHighName"),
      beginDate,
      endDate)
    val gph = pressHigh.foldLeft(
      new CommonGroup(cdPressureHighId, "Blood pressure high values"))(
      (g, p) => {
        g.add(new CA(p.getId,
          0,
          p.getName,
          ConfigManager.Types.Double,
          null,
          Map("value" -> p.getValue.toString,
            "datetime" -> CMDF.format(p.getDate))))
      })
    e.add(gph)

    // Добавляем значения АД верхн. - верхней границы артериального давления
    val pressLow = customQuery.getIndicatorsByEventIdAndIndicatorNameAndDates(
      eventId,
      i18n("db.apt.bloodPressureLowName"),
      beginDate,
      endDate)
    val gpl = pressLow.foldLeft(
      new CommonGroup(cdPressureLowId, "Blood pressure low values"))(
      (g, p) => {
        g.add(new CA(p.getId,
          0,
          p.getName,
          ConfigManager.Types.Double,
          null,
          Map("value" -> p.getValue.toString,
            "datetime" -> CMDF.format(p.getDate))))
      })
    e.add(gpl)

    // Добавляем значения ЧДД - частоты дыхательных движений
    val breath = customQuery.getIndicatorsByEventIdAndIndicatorNameAndDates(
      eventId,
      i18n("db.apt.breathingFrequencyName"),
      beginDate,
      endDate)
    val gB = breath.foldLeft(
      new CommonGroup(cdBreathingId, "Breathing frequency values"))(
      (g, b) => {
        g.add(new CA(b.getId,
          0,
          b.getName,
          ConfigManager.Types.Double,
          null,
          Map("value" -> b.getValue.toString,
            "datetime" -> CMDF.format(b.getDate))))
      })
    e.add(gB)

    // Добавляем значения ЧСС - частоты сердечных сокращений
    val heartBeat = customQuery.getIndicatorsByEventIdAndIndicatorNameAndDates(
      eventId,
      i18n("db.apt.heartBeatFrequencyName"),
      beginDate,
      endDate)
    val gH = heartBeat.foldLeft(
      new CommonGroup(cdHeartBeatId, "Heart beat values"))(
      (g, h) => {
        g.add(new CA(h.getId,
          0,
          h.getName,
          ConfigManager.Types.Double,
          null,
          Map("value" -> h.getValue.toString,
            "datetime" -> CMDF.format(h.getDate))))
      })
    e.add(gH)

    cd
  }

  def createAssessmentForEventId(eventId: Int,
                                 assessment: CommonData,
                                 userData: AuthData) = {
    val createdActions =
      commonDataProcessor.createActionForEventFromCommonData(
        eventId,
        assessment,
        userData)

    commonDataProcessor.fromActions(
      createdActions,
      "Assessment",
      List(summary _, details _))
  }

  def modifyAssessmentById(assessmentId: Int,
                           assessment: CommonData,
                           userData: AuthData) = {
    val modifiedActions = commonDataProcessor.modifyActionFromCommonData(
      assessmentId,
      assessment,
      userData)

    commonDataProcessor.fromActions(
      modifiedActions,
      "Assessment",
      List(summary _, details _))
  }
}
