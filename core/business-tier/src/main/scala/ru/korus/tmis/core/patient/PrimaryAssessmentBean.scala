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
  private var actionPropertyTypeBean: DbActionPropertyTypeBeanLocal = _

  @EJB
  private var actionTypeBean: DbActionTypeBeanLocal = _

  @EJB
  private var commonDataProcessor: CommonDataProcessorBeanLocal = _

  @EJB
  var dbStaff: DbStaffBeanLocal = _

  @EJB
  private var dbManager: DbManagerBeanLocal = _

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
      AWI.Status,
      AWI.Finance,
      AWI.PlannedEndDate,
      AWI.ToOrder
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

  def detailsWithAge(assessment: Action) = {
    val propertiesMap = actionPropertyBean.getActionPropertiesByActionId(assessment.getId.intValue)
    val group = new CommonGroup(1, "Details")

    val age = commonDataProcessor.defineAgeOfPatient(assessment.getEvent.getPatient)

    propertiesMap.foreach(
      (p) => {
        val (ap, apvs) = p
        val apw = new ActionPropertyWrapper(ap)
        if (commonDataProcessor.checkActionPropertyTypeForPatientAge(age, ap.getType)) {
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
        }
      })

    group
  }

  def converterFromList(list: java.util.List[String], apt: ActionPropertyType) = {

    var map = list.foldLeft(Map.empty[String,String])(
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

    new CommonAttribute(apt.getId,
      0,
      apt.getName,
      apt.getTypeName,
      apt.getConstructorValueDomain,
      map)
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

  def  createAssessmentsForEventIdFromCommonData(eventId: Int,
                                                 assessments: CommonData,
                                                 title: String,
                                                 request: Object,
                                                 userData: AuthData,
                                                 postProcessing: (JSONCommonData, java.lang.Boolean) => JSONCommonData) = {

     val actions: java.util.List[Action] = commonDataProcessor.createActionForEventFromCommonData(eventId, assessments, userData)
     val com_data = commonDataProcessor.fromActions( actions, title, List(summary _, detailsWithAge _))

     var json_data = new JSONCommonData(request, com_data)
     if (postProcessing != null) {
       json_data =  postProcessing(json_data, false)
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
      List(summary _, details _))

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

  //временно расположим тут
  def insertAssessmentAsConsultation(eventId: Int,
                                     actionTypeId: Int,
                                     executorId: Int,
                                     bDate: Date,
                                     eDate:Date,
                                     urgent:Boolean,
                                     request: Object,
                                     userData: AuthData) = {

    //action
    var action: Action = actionBean.createAction(eventId.intValue(),
                                                 actionTypeId.intValue(),
                                                 userData)
    action.setIsUrgent(urgent)
    action.setExecutor(dbStaff.getStaffById(executorId))
    action.setBegDate(bDate)
    action.setPlannedEndDate(eDate)
    dbManager.persist(action)

    //empty action property
    val apSet = new HashSet[ActionProperty]

    actionPropertyTypeBean.getActionPropertyTypesByActionTypeId(actionTypeId.intValue())
      .toList
      .foreach((apt) => {
        val property = actionPropertyBean.createActionProperty(action,
                                                               apt.getId.intValue(),
                                                               userData)
        apSet += property
    })

    dbManager.mergeAll(apSet)

    var json = this.getPrimaryAssessmentById(action.getId.intValue(), "Consultation", userData, null, false)
    json.setRequestData(request) //по идее эта штука должна быть в конструкторе вызываемая в методе гет
    json
  }
}
