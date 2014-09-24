package ru.korus.tmis.core.patient

import grizzled.slf4j.Logging
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.interceptor.Interceptors
import ru.korus.tmis.core.auth.AuthData
import ru.korus.tmis.core.common.CommonDataProcessorBeanLocal
import javax.ejb.{EJB, Stateless}
import scala.collection.JavaConversions._
import ru.korus.tmis.core.entity.model._
import ru.korus.tmis.scala.util.{StringId, I18nable, ConfigManager}
import ConfigManager.APWI
import java.util.{Calendar, LinkedList, HashSet, Date}
import ru.korus.tmis.core.database._
import common.{DbActionPropertyBeanLocal, DbManagerBeanLocal, DbActionBeanLocal}
import ru.korus.tmis.core.data._
import collection.immutable.HashMap

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class PrimaryAssessmentBean
  extends PrimaryAssessmentBeanLocal
  with Logging
  with I18nable {

  val AWI = ConfigManager.AWI

  @EJB
  private var actionBean: DbActionBeanLocal = _

  @EJB
  private var actionPropertyBean: DbActionPropertyBeanLocal = _

  @EJB
  private var actionTypeBean: DbActionTypeBeanLocal = _

  @EJB
  private var commonDataProcessor: CommonDataProcessorBeanLocal = _

  @EJB
  var dbStaff: DbStaffBeanLocal = _

  @EJB
  private var dbManager: DbManagerBeanLocal = _

  @EJB
  var dbLayoutAttributeValueBean: DbLayoutAttributeValueBeanLocal = _

  @EJB
  var diagnosisBean: DiagnosisBeanLocal = _

  @EJB
  private var dbActionProperty: DbActionPropertyBeanLocal = _

  def summary(assessment: Action) = {
    val group = new CommonGroup(0, "Summary")

    val attributes = List(
      AWI.assessmentId,
      AWI.assessmentName,
      AWI.assessmentBeginDate,
      AWI.assessmentEndDate,
      AWI.doctorLastName,
      AWI.doctorFirstName,
      AWI.doctorMiddleName,
      AWI.doctorSpecs,
      AWI.executorPost,
      AWI.urgent,
      AWI.multiplicity,
      AWI.Status,
      AWI.Finance,
      AWI.PlannedEndDate//,
      //AWI.ToOrder
    )

    commonDataProcessor.addAttributes(
      group,
      new ActionWrapper(assessment),
      attributes)
  }

  def details(assessment: Action) = {
    val propertiesMap =
      actionPropertyBean.getActionPropertiesByActionId(assessment.getId.intValue)

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

  def detailsWithLayouts(assessment: Action) = {
    val propertiesMap =
      actionPropertyBean.getActionPropertiesByActionId(assessment.getId.intValue)

    val group = new CommonGroup(1, "Details")

    propertiesMap.foreach(
      (p) => {
        val (ap, apvs) = p
        val apw = new ActionPropertyWrapper(ap, dbActionProperty.convertValue, dbActionProperty.convertScope)

        apvs.size match {
          case 0 => {
            val ca = apw.get(null, List(APWI.Unit, APWI.Norm))
            group add new CommonAttributeWithLayout(ca, dbLayoutAttributeValueBean.getLayoutAttributeValuesByActionPropertyTypeId(ap.getType.getId.intValue()).toList)
          }
          case _ => {
            apvs.foreach((apv) => {
              val ca = apw.get(apv, List(APWI.Value, APWI.ValueId, APWI.Unit, APWI.Norm))
              group add new CommonAttributeWithLayout(ca, dbLayoutAttributeValueBean.getLayoutAttributeValuesByActionPropertyTypeId(ap.getType.getId.intValue()).toList)
            })
          }
        }
      })

    group
  }

  def getEmptyStructure(atId: Int,
                        title: String,
                        listForConverter: java.util.List[String],
                        listForSummary: java.util.List[StringId],
                        userData: AuthData,
                        postProcessing: (JSONCommonData, java.lang.Boolean) => JSONCommonData,
                        patient: Patient) = {

    var json_data = new JSONCommonData()
    val cd = commonDataProcessor.fromActionTypesForWebClient(actionTypeBean.getActionTypeById(atId),
                                                             title,
                                                             listForSummary,
                                                             listForConverter,
                                                             patient)
    json_data.data = cd.entity
    if (postProcessing != null) {
      json_data =  postProcessing(json_data, true)
    }
    json_data
  }

  /*
     Примечание:
     При создании нового первичного осмотра  actionTypeId приходит в поле id и дублируется в поле typeId
     При редактировании сущ. первичного осмотра actionTypeId приходит в поле typeId в поле id - actionId
  */

  def createPrimaryAssessmentForEventId(eventId: Int,
                                        assessment: JSONCommonData,
                                        title: String,
                                        userData: AuthData,
                                        preProcessing: (JSONCommonData, java.lang.Boolean) => JSONCommonData,
                                        postProcessing: (JSONCommonData, java.lang.Boolean) => JSONCommonData) = {

    var json_data = assessment

    if (preProcessing != null) {
      json_data =  preProcessing(json_data, true)
    }

    var com_data = new CommonData()
    com_data.entity = json_data.data

    var actions: java.util.List[Action] = commonDataProcessor.createActionForEventFromCommonData(eventId, com_data, userData)

    com_data = commonDataProcessor.fromActions(
      actions,
      title,
      List(summary _, details _))

    json_data.data = com_data.entity
    if (postProcessing != null) {
      json_data =  postProcessing(json_data, false)
    }
    json_data
  }


  def modifyPrimaryAssessmentById(assessmentId: Int,
                                  assessment: JSONCommonData,
                                  title: String,
                                  userData: AuthData,
                                  preProcessing: (JSONCommonData, java.lang.Boolean) => JSONCommonData,
                                  postProcessing: (JSONCommonData, java.lang.Boolean) => JSONCommonData) = {

    var json_data = assessment

    if (preProcessing != null) {
      json_data =  preProcessing(json_data, false)
    }

    var com_data = new CommonData()
    com_data.entity = json_data.data

    var actions: java.util.List[Action] = commonDataProcessor.modifyActionFromCommonData(assessmentId, com_data, userData)

    com_data = commonDataProcessor.fromActions(
      actions,
      title,
      List(summary _, details _))

    json_data.data = com_data.entity
    if (postProcessing != null) {
      json_data =  postProcessing(json_data, false)
    }
    json_data
  }

  def getPrimaryAssessmentById(assessmentId: Int,
                               title: String,
                               userData: AuthData,
                               postProcessing: (JSONCommonData, java.lang.Boolean) => JSONCommonData,
                               reId: java.lang.Boolean) = {
    val action = actionBean.getActionById(assessmentId)
    var actions: java.util.List[Action] = new LinkedList[Action]
    actions.add(action)

    val com_data = commonDataProcessor.fromActions(
      actions,
      title,
      List(summary _, detailsWithLayouts _))

    var json_data = new JSONCommonData()
    json_data.data = com_data.entity
    if (postProcessing != null) {
      json_data =  postProcessing(json_data, reId)
    }
    json_data
  }

  def updateStatusById(eventId: Int, actionId: Int, status: Short) = {
    commonDataProcessor.changeActionStatus(eventId, actionId, status)
  }

  def deletePreviousAssessmentById(assessmentId: Int, userData: AuthData) {

    val now = new Date()

    var findAction = actionBean.getActionById(assessmentId)
    if(findAction!=null) {
      findAction.setDeleted(true)
      if(userData!=null) {
        findAction.setModifyPerson(userData.getUser)
      }
      findAction.setModifyDatetime(now)

      val apSet = new HashSet[ActionProperty]
      var findMapActionProperty = actionPropertyBean.getActionPropertiesByActionId(findAction.getId.intValue())
      findMapActionProperty.foreach(
        (element) => {
          val (ap, listApVal) = element
          ap.setDeleted(true)
          if(userData!=null) {
            ap.setModifyPerson(userData.getUser)
          }
          ap.setModifyDatetime(now)
          apSet+=ap
        })
      dbManager.mergeAll(apSet)
    }
  }
}
