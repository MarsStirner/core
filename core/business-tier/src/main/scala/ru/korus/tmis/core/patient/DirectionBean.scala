package ru.korus.tmis.core.patient

import javax.interceptor.Interceptors
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.ejb.{EJB, Stateless}
import grizzled.slf4j.Logging
import ru.korus.tmis.util.{CAPids, I18nable}
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.data.{CommonGroup, JSONCommonData, CommonData}
import ru.korus.tmis.core.auth.AuthData
import ru.korus.tmis.core.entity.model._
import scala.collection.JavaConversions._
import ru.korus.tmis.core.common.CommonDataProcessorBeanLocal
import ru.korus.tmis.util.ConfigManager._
import ru.korus.tmis.core.database._
import collection.JavaConversions

/**
 * Методы для работы с Направлениями
 * @author mmakankov Systema-Soft
 * @since 1.0.0.70
 */
@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DirectionBean extends DirectionBeanLocal
                    with Logging
                    with CAPids
                    with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  @EJB
  private var commonDataProcessor: CommonDataProcessorBeanLocal = _

  @EJB
  private var actionPropertyBean: DbActionPropertyBeanLocal = _

  @EJB
  private var dbJobBean: DbJobBeanLocal = _

  @EJB
  private var dbJobTicketBean: DbJobTicketBeanLocal = _

  @EJB
  private var dbTakenTissue: DbTakenTissueBeanLocal = _

  @EJB
  private var hospitalBedBean: HospitalBedBeanLocal = _

  @EJB
  private var dbActionPropertyBean: DbActionPropertyBeanLocal = _

  @EJB
  private var dbOrgStructure: DbOrgStructureBeanLocal = _

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
      AWI.urgent,
      AWI.multiplicity,
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
       /*
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
           */
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

  def  createDirectionsForEventIdFromCommonData(eventId: Int,
                                                 directions: CommonData,
                                                 title: String,
                                                 request: Object,
                                                 userData: AuthData,
                                                 postProcessingForDiagnosis: (JSONCommonData, java.lang.Boolean) => JSONCommonData) = {

    //создание жоб тикета тут должно быть
    val actions: java.util.List[Action] = commonDataProcessor.createActionForEventFromCommonData(eventId, directions, userData)
    val moving = hospitalBedBean.getLastMovingActionForEventId(eventId)
    var department: OrgStructure = null
    //dbActionPropertyBean.getActionPropertiesByActionIdAndTypeId(moving.getId.intValue(), 1616)
    if (moving != null) {
      val listMovAP = JavaConversions.asJavaList(List(iCapIds("db.rbCAP.moving.id.bed").toInt: java.lang.Integer))
      val bedValues = dbActionPropertyBean.getActionPropertiesByActionIdAndRbCoreActionPropertyIds(moving.getId.intValue(), listMovAP)

      if (bedValues!=null && bedValues!=0 && bedValues.size()>0) {
        val templates = bedValues.get(0)
        department = bedValues.get(0).get(0).getValue.asInstanceOf[OrgStructureHospitalBed].getMasterDepartment
      }
    } else {
      department = dbOrgStructure.getOrgStructureById(28)//приемное отделение
    }

    var list = new java.util.LinkedList[(Job, JobTicket, TakenTissue)]
    var tissueType: RbTissueType = null
    actions.foreach((a) => {
      val job = dbJobBean.getJobForAction(a)
      if (job != null) {
        em.merge(dbJobBean.insertOrUpdateJob(job.getId.intValue(), a, department))
      } else {
        val nextTissueType = dbTakenTissue.getActionTypeTissueTypeByMasterId(a.getActionType.getId.intValue()).getTissueType
        if (nextTissueType != tissueType) {
          val j = dbJobBean.insertOrUpdateJob(0, a, department)
          val jt = dbJobTicketBean.insertOrUpdateJobTicket(0, a, j)
          val tt = dbTakenTissue.insertOrUpdateTakenTissue(0, a)
          list.add(j, jt, tt)
        } else {
          var (lj, ljt, ltt) = list.get(list.size()-1)
          lj.setQuantity(lj.getQuantity+1)
        }
        tissueType = nextTissueType
      }

    })
    list.foreach((f) => {
      val (j, jt, tt) = f
      em.persist(j)
      em.persist(jt)
      em.persist(tt)
    })
    em.flush()
    val com_data = commonDataProcessor.fromActions( actions, title, List(summary _, detailsWithAge _))

    var json_data = new JSONCommonData(request, com_data)
    if (postProcessingForDiagnosis != null) {
      json_data =  postProcessingForDiagnosis(json_data, false)
    }
    json_data

  }

  def  modifyDirectionsForEventIdFromCommonData(directionId: Int,
                                                directions: CommonData,
                                                 title: String,
                                                 request: Object,
                                                 userData: AuthData,
                                                 postProcessingForDiagnosis: (JSONCommonData, java.lang.Boolean) => JSONCommonData) = {

    //val actions: java.util.List[Action] = commonDataProcessor.createActionForEventFromCommonData(eventId, assessments, userData)
    var actions: java.util.List[Action] = null// commonDataProcessor.modifyActionFromCommonData(assessmentId, assessments, userData)
    directions.getEntity.foreach((action) => {
      actions = commonDataProcessor.modifyActionFromCommonData(action.getId().intValue(), directions, userData)
    })
    val com_data = commonDataProcessor.fromActions( actions, title, List(summary _, detailsWithAge _))

    var json_data = new JSONCommonData(request, com_data)
    if (postProcessingForDiagnosis != null) {
      json_data =  postProcessingForDiagnosis(json_data, false)
    }
    json_data
  }
}
