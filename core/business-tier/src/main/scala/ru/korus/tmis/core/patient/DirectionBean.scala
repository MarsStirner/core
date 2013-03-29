package ru.korus.tmis.core.patient

import javax.interceptor.Interceptors
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.ejb.{EJB, Stateless}
import grizzled.slf4j.Logging
import ru.korus.tmis.util.{CAPids, I18nable}
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.data.{AssignmentsToRemoveDataList, CommonGroup, JSONCommonData, CommonData}
import ru.korus.tmis.core.auth.AuthData
import ru.korus.tmis.core.entity.model._
import scala.collection.JavaConversions._
import ru.korus.tmis.core.common.CommonDataProcessorBeanLocal
import ru.korus.tmis.util.ConfigManager._
import ru.korus.tmis.core.database._
import collection.JavaConversions
import java.util

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
  private var dbCustomQueryBean: DbCustomQueryLocal = _

  @EJB
  private var dbOrgStructure: DbOrgStructureBeanLocal = _

  @EJB
  private var actionBean: DbActionBeanLocal = _

  def summary(direction: Action) = {
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
    commonDataProcessor.addAttributes(group, new ActionWrapper(direction), attributes)
  }

  def detailsWithAge(direction: Action) = {
    val propertiesMap = actionPropertyBean.getActionPropertiesByActionId(direction.getId.intValue)
    val group = new CommonGroup(1, "Details")

    val age = commonDataProcessor.defineAgeOfPatient(direction.getEvent.getPatient)

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
                  APWI.IsAssignable,
                  APWI.IsAssigned,
                  APWI.Unit,
                  APWI.Norm))
              })
            }
          }
        }
      })

    group
  }

  def getDirectionById(directionId: Int,
                             title: String,
                          userData: AuthData,
        postProcessingForDiagnosis: (JSONCommonData, java.lang.Boolean) => JSONCommonData) = {

    val action = actionBean.getActionById(directionId)
    var actions: java.util.List[Action] = new util.LinkedList[Action]
    actions.add(action)

    val com_data = commonDataProcessor.fromActions(
      actions,
      title,
      List(summary _, detailsWithAge _))

    var json_data = new JSONCommonData()
    json_data.data = com_data.entity
    if (postProcessingForDiagnosis != null) {
      json_data =  postProcessingForDiagnosis(json_data, false)
    }
    json_data
  }

  def  createDirectionsForEventIdFromCommonData(eventId: Int,
                                             directions: CommonData,
                                                  title: String,
                                                request: Object,
                                               userData: AuthData,
                             postProcessingForDiagnosis: (JSONCommonData, java.lang.Boolean) => JSONCommonData) = {

    //создание жоб тикета тут должно быть
    val actions: java.util.List[Action] = commonDataProcessor.createActionForEventFromCommonData(eventId, directions, userData)
    var actions2 = new java.util.LinkedList[Action]
    val moving = hospitalBedBean.getLastMovingActionForEventId(eventId)
    var department = dbOrgStructure.getOrgStructureById(28)//приемное отделение
    //actionPropertyBean.getActionPropertiesByActionIdAndTypeId(moving.getId.intValue(), 1616)
    if (moving != null) {
      val listMovAP = JavaConversions.asJavaList(List(iCapIds("db.rbCAP.moving.id.bed").toInt: java.lang.Integer))
      val bedValues = actionPropertyBean.getActionPropertiesByActionIdAndRbCoreActionPropertyIds(moving.getId.intValue(), listMovAP)
      if (bedValues!=null && bedValues!=0 && bedValues.size()>0) {
        if (bedValues.get(0) != null) {
          department = bedValues.get(0).get(0).getValue.asInstanceOf[OrgStructureHospitalBed].getMasterDepartment
        }
      }
    }

    var list = new java.util.LinkedList[(Job, JobTicket, TakenTissue)]
    var apvList = new java.util.LinkedList[(ActionProperty, JobTicket)]
    var apvMKBList = new java.util.LinkedList[(ActionProperty, Mkb)]
    var tissueType: RbTissueType = null
    actions.foreach((a) => {
      val jobAndTicket = dbJobTicketBean.getJobTicketAndTakenTissueForAction(a)
      if (jobAndTicket != null) {
        val (jobTicket, takenTissue) = jobAndTicket.asInstanceOf[(JobTicket, TakenTissue)]
        if (jobTicket != null && jobTicket.getJob!=null) {
          var (lj, ljt, ltt) = if (list.size() > 0) list.get(list.size()-1) else (null, null, null)
          if (lj == null || lj.getId.intValue() != jobTicket.getJob.getId.intValue()) {
            val j = dbJobBean.insertOrUpdateJob(jobTicket.getJob.getId.intValue(), a, department)
            val jt = dbJobTicketBean.insertOrUpdateJobTicket(jobTicket.getId.intValue(), a, j)
            val tt = takenTissue
            if (takenTissue != null)
              a.setTakenTissue(takenTissue)
            a.getActionProperties.foreach((ap) => {
              if (ap.getType.getTypeName.compareTo("JobTicket") == 0) {
                apvList.add((ap, jt))
              }
            })
            list.add(j, jt, tt)
          } else {
            lj.asInstanceOf[Job].setQuantity(lj.asInstanceOf[Job].getQuantity +1)
            a.getActionProperties.foreach((ap) => {
              if (ap.getType.getTypeName.compareTo("JobTicket") == 0) {
                apvList.add((ap, ljt))
              }
            })
          }
        }
      } else {
        val nextTissueType = dbTakenTissue.getActionTypeTissueTypeByMasterId(a.getActionType.getId.intValue()).getTissueType
        if (nextTissueType != tissueType) {
          val j = dbJobBean.insertOrUpdateJob(0, a, department)
          val jt = dbJobTicketBean.insertOrUpdateJobTicket(0, a, j)
          val tt = dbTakenTissue.insertOrUpdateTakenTissue(0, a)
          if (tt != null) a.setTakenTissue(tt)
          list.add(j, jt, tt)
        } else {
          var (lj, ljt, ltt) = list.get(list.size()-1)
          lj.setQuantity(lj.getQuantity+1)
        }
        var (lj, ljt, ltt) = list.get(list.size()-1)
        a.getActionProperties.foreach((ap) => {
          if (ap.getType.getTypeName.compareTo("JobTicket") == 0) {
            apvList.add((ap, ljt))
          }
        })
        tissueType = nextTissueType
      }
      //пропишем диагноз в пропертю, если не пришел с клиента
      a.getActionProperties.foreach((ap) => {
        if (ap.getType.getTypeName.compareTo("MKB") == 0) {
          var props = actionPropertyBean.getActionPropertyValue(ap)
          if (props.get(0).getValueAsString.compareTo("") == 0) {
            val diagnosis = dbCustomQueryBean.getDiagnosisForMainDiagInAppeal(a.getEvent.getId.intValue())
            if (diagnosis != null) {
              apvMKBList.add((ap, diagnosis))
            }
          }
        }
      })
    })
    if (list != null && list.size() > 0) {
      list.foreach((f) => {
        val (j, jt, tt) = f
        if (j != null) {
          if (j.getId != null && j.getId.intValue() > 0) {
            em.merge(j)
          } else {
            em.persist(j)
          }
        }
        if (jt != null) {
          if (jt.getId != null && jt.getId.intValue() > 0) {
            em.merge(jt)
          } else {
            em.persist(jt)
          }
        }
        if (tt != null) {
          if (tt.getId != null && tt.getId.intValue() > 0) {
            em.merge(tt)
          } else {
            em.persist(tt)
          }
        }
      })
      em.flush()
      //сохраняем пропертиВалуе ЖобТикет
      apvList.foreach((value) => {
        var (ap, jt) = value
        em.merge(actionPropertyBean.setActionPropertyValue(ap, jt.getId.intValue().toString, 0))
      })
      //сохраняем пропертиВалуе МКБ
      apvMKBList.foreach((value) => {
        var (ap, mkb) = value
        em.merge(actionPropertyBean.setActionPropertyValue(ap, mkb.getId.intValue().toString, 0))
      })
      //сохраняем изменения в акшенах (setTakenTissue) (TakenTissueJournal)
      actions.foreach((a) => em.merge(a))
      em.flush()
    }

    var json_data = new JSONCommonData(request, com_data)
    if (postProcessingForDiagnosis != null) {
      json_data =  postProcessingForDiagnosis(json_data, false)
    }
    json_data

  }

  def modifyDirectionsForEventIdFromCommonData(directionId: Int,
                                                directions: CommonData,
                                                     title: String,
                                                   request: Object,
                                                  userData: AuthData,
                                postProcessingForDiagnosis: (JSONCommonData, java.lang.Boolean) => JSONCommonData) = {
    var actions: java.util.List[Action] = null
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

  def removeDirections(directions: AssignmentsToRemoveDataList, userData: AuthData) = {
    directions.getData.foreach((f) => {
      var a = actionBean.getActionById(f.getId)
      a.setDeleted(true)
      val res = dbJobTicketBean.getJobTicketAndTakenTissueForAction(a).asInstanceOf[(JobTicket, TakenTissue)]
      if (res._1!=null && res._1.getJob!=null) {
        val job = res._1.getJob
        if (job.getQuantity == 1) {
          job.setDeleted(true)
        }
        job.setQuantity(job.getQuantity - 1)
        em.merge(job)
      }
      em.merge(a)
    })
    em.flush()
    true
  }
}
