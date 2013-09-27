package ru.korus.tmis.core.patient

import grizzled.slf4j.Logging
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.interceptor.Interceptors
import ru.korus.tmis.core.auth.AuthData
import ru.korus.tmis.core.common.CommonDataProcessorBeanLocal
import javax.ejb.{EJB, Stateless}
import scala.collection.JavaConversions._
import ru.korus.tmis.core.entity.model._
import ru.korus.tmis.util.ConfigManager.APWI
import java.util.{Calendar, LinkedList, HashSet, Date}
import ru.korus.tmis.core.database._
import ru.korus.tmis.core.data._
import collection.immutable.HashMap
import ru.korus.tmis.util.{StringId, ConfigManager, ActionPropertyWrapperInfo, I18nable}
import ru.korus.tmis.util.StringId

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
        val apw = new ActionPropertyWrapper(ap)

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
        val apw = new ActionPropertyWrapper(ap)

        apvs.size match {
          case 0 => {
            group add apw.get(null, List(APWI.Unit, APWI.Norm))
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

  def converterFromList(list: java.util.List[String], apt: ActionPropertyType) = {

    val map = list.foldLeft(Map.empty[String,String])(
      (str_key, el) => {
        val key = el
        val value  =   if(key == APWI.Value.toString){apt.getDefaultValue}
        else if(key == APWI.ValueId.toString){apt.getDefaultValue}
        else if(key == APWI.Unit.toString){apt.getUnit.getName}
        else if(key == APWI.Norm.toString){apt.getNorm}
        else if(key == APWI.IsAssignable.toString){apt.getIsAssignable.toString}
        else if(key == APWI.IsAssigned.toString){""}
        else {""}
        str_key + (key -> value)
      })

    new CommonAttributeWithLayout(apt.getId,
                                  0,
                                  apt.getName,
                                  apt.getTypeName,
                                  apt.getValueDomain,//apt.getConstructorValueDomain,
                                  map,
                                  dbLayoutAttributeValueBean.getLayoutAttributeValuesByActionPropertyTypeId(apt.getId.intValue()).toList,
                                  apt.isMandatory.toString,
                                  apt.isReadOnly.toString)
  }

  def getEmptyStructure(atId: Int,
                        title: String,
                        listForConverter: java.util.List[String],
                        listForSummary: java.util.List[StringId],
                        userData: AuthData,
                        postProcessing: (JSONCommonData, java.lang.Boolean) => JSONCommonData,
                        patient: Patient) = {

    var json_data = new JSONCommonData()
    val cd = commonDataProcessor.fromActionTypesForWebClient(Set(actionTypeBean.getActionTypeById(atId)),
                                                             title,
                                                             listForSummary,
                                                             listForConverter,
                                                             converterFromList,
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
    /*
    //Если первичный осмотр, то записать диагноз при поступлении
    val primaries = json_data.data.filter(ce => ce.getTypeId().compareTo(i18n("db.actionType.primary").toInt)==0)
    primaries.foreach(ce => {
      val attr = ce.group.get(1).attribute.find(p=>p.getType().compareTo("MKB")==0).getOrElse(null)
      if (attr!=null) {
        val mkbId = attr.properties.get("valueId").getOrElse(null)
        if(mkbId!=null && (!mkbId.isEmpty)){
          var map = Map.empty[String, java.util.Set[AnyRef]]
          map += ("admission" -> Set[AnyRef]((-1, "", Integer.valueOf(mkbId.toString))))
          val diag = diagnosisBean.insertDiagnoses(eventId, asJavaMap(map), userData)
          dbManager.persistAll(diag)
        }
      }
    })
    */
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
