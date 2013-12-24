package ru.korus.tmis.core.patient

import javax.interceptor.Interceptors
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.ejb.{EJB, Stateless}
import grizzled.slf4j.Logging
import ru.korus.tmis.util.{ConfigManager, CAPids, I18nable}
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.data._
import ru.korus.tmis.core.auth.AuthData
import ru.korus.tmis.core.entity.model._
import scala.collection.JavaConversions._
import ru.korus.tmis.core.common.CommonDataProcessorBeanLocal
import ru.korus.tmis.util.ConfigManager._
import ru.korus.tmis.core.database._
import java.{lang, util}
import ru.korus.tmis.core.filter.ActionsListDataFilter
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.laboratory.across.business.AcrossBusinessBeanLocal

import util.Date


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
  private var actionPropertyTypeBean: DbActionPropertyTypeBeanLocal = _
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
  @EJB
  private var dbMkbBean: DbMkbBeanLocal = _
  @EJB
  private var dbEventBean: DbEventBeanLocal = _
  @EJB
  private var dbStaffBean: DbStaffBeanLocal = _

  //todo это безобразие убрать, должен быть вызов веб-сервиса с передачей actionId
  @EJB
  var lisBean: AcrossBusinessBeanLocal = _

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
      AWI.assignerLastName,
      AWI.assignerFirstName,
      AWI.assignerMiddleName,
      AWI.assignerSpecs,
      AWI.urgent,
      AWI.multiplicity,
      AWI.Status,
      AWI.Finance,
      AWI.PlannedEndDate,
      AWI.ExecutorId,
      AWI.AssignerId,
      AWI.PacientInQueueType
      //AWI.ToOrder
    )
    commonDataProcessor.addAttributes(group, new ActionWrapper(direction), attributes)
  }

  def detailsWithAge(direction: Action) = {
    var attributes = List(APWI.Value,
      APWI.ValueId,
      APWI.Unit,
      APWI.Norm)
    if (direction.getActionType.getMnemonic.toUpperCase.compareTo("LAB") == 0)
      attributes = attributes ::: List(APWI.IsAssignable, APWI.IsAssigned)

    val propertiesMap = actionPropertyBean.getActionPropertiesByActionId(direction.getId.intValue)
    val group = new CommonGroup(1, "Details")

    val age = commonDataProcessor.defineAgeOfPatient(direction.getEvent.getPatient)
    //val (year, month, week, date) = age.asInstanceOf[(Int, Int, Int, Int)]

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
                group add apw.get(apv, attributes)
              })
            }
          }
        }
      }
    )
    group
  }

  def getDirectionById(directionId: Int,
                       title: String,
                       postProcessingForDiagnosis: (JSONCommonData, java.lang.Boolean) => JSONCommonData,
                       authData: AuthData) = {

    val action = actionBean.getActionById(directionId)
    var actions: java.util.List[Action] = new util.LinkedList[Action]
    actions.add(action)

    val com_data = commonDataProcessor.fromActions(
      actions,
      title,
      List(summary _, detailsWithAge _))

    val isTrueDoctor = (authData.getUser.getId.intValue() == action.getCreatePerson.getId.intValue() ||
      authData.getUser.getId.intValue() == action.getAssigner.getId.intValue())
    val jt = dbJobTicketBean.getJobTicketForAction(action.getId.intValue())
    com_data.getEntity().get(0).setIsEditable(action.getStatus == 0 && action.getEvent.getExecDate == null && isTrueDoctor && (jt == null || (jt != null && jt.getStatus == 0)))

    var json_data = new JSONCommonData()
    json_data.data = com_data.entity
    if (postProcessingForDiagnosis != null) {
      json_data = postProcessingForDiagnosis(json_data, false)
    }
    json_data
  }

  private def createJobTicketsForActions(actions: java.util.List[Action], eventId: Int) = {

    val department = hospitalBedBean.getCurrentDepartmentForAppeal(eventId)
    var list = new java.util.LinkedList[(Job, JobTicket, TakenTissue, Action)]
    var apvList = new java.util.LinkedList[(ActionProperty, JobTicket)]
    var apvMKBList = new java.util.LinkedList[(ActionProperty, Mkb)]
    var jtForAp: JobTicket = null
    actions.foreach((a) => {
      val jobAndTicket = dbJobTicketBean.getJobTicketAndTakenTissueForAction(a.getEvent.getId.intValue(),
        a.getActionType.getId.intValue(),
        a.getPlannedEndDate,
        department.getId.intValue(),
        a.getIsUrgent)
      if (jobAndTicket == null) {
        var fromList = list.find((p) => p._1.getId == null &&
          p._2.getDatetime == a.getPlannedEndDate &&
          p._3.getType.getId == dbTakenTissue.getActionTypeTissueTypeByMasterId(a.getActionType.getId.intValue()).getTissueType.getId &&
          p._4.getIsUrgent == a.getIsUrgent).getOrElse(null) //срочные на одну дату и тип биоматериала должны создаваться с одним жобТикетом
        if (fromList != null) {
          val (j, jt, tt) = (fromList._1, fromList._2, fromList._3)
          j.setQuantity(j.getQuantity + 1)
          if (tt != null) a.setTakenTissue(tt)
          jtForAp = jt
        } else if (dbTakenTissue.getActionTypeTissueTypeByMasterId(a.getActionType.getId.intValue()) != null) {
          val j = dbJobBean.insertOrUpdateJob(0, a, department)
          val jt = dbJobTicketBean.insertOrUpdateJobTicket(0, a, j)
          val tt = dbTakenTissue.insertOrUpdateTakenTissue(0, a)
          if (list != null && list.size() > 0) {
            if (list.getLast._3.getBarcode != 999999) {
              tt.setBarcode(list.getLast._3.getBarcode + 1)
              tt.setPeriod(list.getLast._3.getPeriod)
            } else {
              tt.setBarcode(100000)
              tt.setPeriod(list.getLast._3.getPeriod + 1)
            }
          }
          if (tt != null) a.setTakenTissue(tt)
          list.add(j, jt, tt, a)
          jtForAp = jt
        }
      } else {
        val (jobTicket, takenTissue) = jobAndTicket.asInstanceOf[(JobTicket, TakenTissue)]
        if (jobTicket != null && jobTicket.getJob != null) {
          var fromList = list.find((p) => p._1.getId != null && p._1.getId.intValue() == jobTicket.getJob.getId.intValue()).getOrElse(null)
          if (fromList == null) {
            val j = dbJobBean.insertOrUpdateJob(jobTicket.getJob.getId.intValue(), a, department)
            val jt = dbJobTicketBean.insertOrUpdateJobTicket(jobTicket.getId.intValue(), a, j)
            if (takenTissue != null) a.setTakenTissue(takenTissue)
            list.add(j, jt, takenTissue, a)
            jtForAp = jt
          } else {
            val (j, jt, tt) = (fromList._1, fromList._2, fromList._3)
            j.setQuantity(j.getQuantity + 1)
            if (tt != null) a.setTakenTissue(tt)
            list.add(j, jt, takenTissue, a)
            jtForAp = jt
          }

        }
        //*****
        //Проверка, есть ли подобный action за текущие сутки c другим временем
        //по коментарию Алехиной https://korusconsulting.atlassian.net/browse/WEBMIS-711
        val filter = new ActionsListDataFilter(a.getEvent.getId.intValue(), //ид обращения в теле запроса
          a.getActionType.getId.intValue(), //действия только данного типа
          -1,
          -1,
          false,
          true) //за текущий день
        val last = actionBean.getActionsWithFilter(0, 0, "", filter.unwrap(), null, null)
        if (last != null && last.size() > 0 && jobTicket.getStatus == 2 && !a.getIsUrgent()) {
          a.setStatus(2)
        }
      }

      //*****
      a.getActionProperties.foreach((ap) => {
        if (ap.getType.getTypeName.compareTo("JobTicket") == 0) {
          apvList.add((ap, jtForAp))
        }
      })
      /*  убрано https://korusconsulting.atlassian.net/browse/WEBMIS-1019
      изменена спека https://docs.google.com/spreadsheet/ccc?key=0Au-ED6EnawLcdHo0Z3BiSkRJRVYtLUxhaG5uYkNWaGc#gid=6
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
      }) */
    })
    if (list != null && list.size() > 0) {
      list.foreach((f) => {
        var (j, jt, tt, a) = f
        if (j != null) {
          if (j.getId != null && j.getId.intValue() > 0) {
            j = em.merge(j)
          } else {
            em.persist(j)
          }
        }
        if (jt != null) {
          if (jt.getId != null && jt.getId.intValue() > 0) {
            jt = em.merge(jt)
          } else {
            em.persist(jt)
          }
        }
        if (tt != null) {
          if (tt.getId != null && tt.getId.intValue() > 0) {
            tt = em.merge(tt)
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
      actions.map(em.merge(_))
      em.flush()
    }
    actions
  }

  def createDirectionsForEventIdFromCommonData(eventId: Int,
                                               directions: CommonData,
                                               title: String,
                                               request: Object,
                                               mnem: String,
                                               userData: AuthData,
                                               postProcessingForDiagnosis: (JSONCommonData, java.lang.Boolean) => JSONCommonData) = {
    var actions: java.util.List[Action] = commonDataProcessor.createActionForEventFromCommonData(eventId, directions, userData)

    //Для лабораторных исследований отработаем с JobTicket
    if (mnem.toUpperCase.compareTo("LAB") == 0) {
      actions = createJobTicketsForActions(actions, eventId)
    }

    val com_data = commonDataProcessor.fromActions(actions, title, List(summary _, detailsWithAge _))
    var json_data = new JSONCommonData(request, com_data)
    if (postProcessingForDiagnosis != null) {
      json_data = postProcessingForDiagnosis(json_data, false)
    }
    json_data
  }

  //TODO: метод работает только с одним ашеном. со списком будет работать не корректно (возможно:)
  def modifyDirectionsForEventIdFromCommonData(directionId: Int,
                                               directions: CommonData,
                                               title: String,
                                               request: Object,
                                               mnem: String,
                                               userData: AuthData,
                                               postProcessingForDiagnosis: (JSONCommonData, java.lang.Boolean) => JSONCommonData) = {
    var actions: java.util.List[Action] = null
    val userId = userData.getUser.getId
    val userRole = userData.getUserRole.getCode
    val flgLab = (mnem.toUpperCase.compareTo("LAB") == 0)

    directions.getEntity.foreach((action) => {
      //Проверка прав у пользователя на редактирование направления
      var oldjt = 0
      val a = actionBean.getActionById(action.getId.intValue())
      if (flgLab) {
        a.getActionProperties.foreach((ap) => {
          if (ap.getType.getTypeName.compareTo("JobTicket") == 0) {
            oldjt = actionPropertyBean.getActionPropertyValue(ap).get(0).asInstanceOf[APValueJobTicket].getValue.intValue()
          }
        })
      }

      if ((a.getCreatePerson != null && a.getCreatePerson.getId.compareTo(userId) == 0) ||
        (a.getModifyPerson != null && a.getModifyPerson.getId.compareTo(userId) == 0) ||
        (a.getAssigner != null && a.getAssigner.getId.compareTo(userId) == 0) /*||
         (a.getExecutor!=null && a.getExecutor.getId.compareTo(userId)==0) ||
         userRole.compareTo("strHead")==0*/ ) {
        actions = commonDataProcessor.modifyActionFromCommonData(action.getId().intValue(), directions, userData)
        if (flgLab) {
          actions = createJobTicketsForActions(actions, a.getEvent.getId.intValue())
          //редактирование или удаление старого жобТикета
          val jobTicket = if (oldjt > 0) dbJobTicketBean.getJobTicketById(oldjt) else null
          if (jobTicket != null) {
            if (jobTicket != null && jobTicket.getJob != null) {
              val job = jobTicket.getJob
              if (job.getQuantity == 1) {
                job.setDeleted(true)
              }
              job.setQuantity(job.getQuantity - 1)
              em.merge(job)
            }
          }
        }
      } else {
        throw new CoreException(ConfigManager.ErrorCodes.noRightForAction, i18n("Редактировать диагностику может только врач, создавший направление, или лечащий врач, или заведующий отделением"))
      }
    })
    if (flgLab)
      em.flush()

    val com_data = commonDataProcessor.fromActions(actions, title, List(summary _, detailsWithAge _))

    var json_data = new JSONCommonData(request, com_data)
    if (postProcessingForDiagnosis != null) {
      json_data = postProcessingForDiagnosis(json_data, false)
    }
    json_data
  }

  def createConsultation(request: ConsultationRequestData, userData: AuthData) = {

    var action: Action = actionBean.createAction(request.eventId.intValue(), request.actionTypeId.intValue(), userData)
    action.setIsUrgent(request.urgent)
    if (request.createPerson > 0 && dbStaffBean.getStaffById(request.createPerson) != null) {
      action.setCreatePerson(dbStaffBean.getStaffById(request.createPerson))
    }
    if (request.createDateTime != null) {
      action.setCreateDatetime(request.createDateTime)
      action.setBegDate(request.createDateTime)
    }
    val plannedDate = if (request.plannedTime != null && request.plannedTime.getTime != null) {
      new Date(request.plannedEndDate.getTime + request.plannedTime.getTime.getTime)
    } else {
      new Date(request.plannedEndDate.getTime)
    }
    action.setPlannedEndDate(plannedDate)
    if (request.getFinance.getId > 0) {
      action.setFinanceId(request.getFinance.getId)
    } else {
      action.setFinanceId(dbEventBean.getEventById(request.getEventId).getEventType.getFinance.getId.intValue())
    }
    if (request.executorId > 0)
      action.setExecutor(dbStaffBean.getStaffById(request.executorId))
    if (request.assignerId > 0)
      action.setAssigner(dbStaffBean.getStaffById(request.assignerId))

    em.persist(action)

    //empty action property
    var apSet = Set.empty[AnyRef]

    actionPropertyTypeBean.getActionPropertyTypesByActionTypeId(request.actionTypeId.intValue())
      .toList
      .foreach((apt) => {
      val ap = actionPropertyBean.createActionProperty(action, apt.getId.intValue(), userData)
      em.persist(ap)

      if (ap.getType.getTypeName.compareTo("MKB") == 0 && request.diagnosis != null && request.diagnosis.getCode != null) {
        //запишем диагноз, который пришел с клиента
        val mkb = dbMkbBean.getMkbByCode(request.diagnosis.getCode)
        if (mkb != null) {
          val apv = actionPropertyBean.setActionPropertyValue(ap, mkb.getId.intValue().toString, 0)
          if (apv != null)
            apSet = apSet + apv.unwrap
        } else {
          //если диагноз не пришел, то запишем дефолтный
          var props = actionPropertyBean.getActionPropertyValue(ap)
          if (props.get(0).getValueAsString.compareTo("") == 0) {
            val diagnosis = dbCustomQueryBean.getDiagnosisForMainDiagInAppeal(action.getEvent.getId.intValue())
            if (diagnosis != null) {
              val apv = actionPropertyBean.setActionPropertyValue(ap, diagnosis.getId.intValue().toString, 0)
              if (apv != null)
                apSet = apSet + apv.unwrap
            }
          }
        }
      } //else if (ap.getType.getTypeName.compareTo("queue") == 0) {
    })
    apSet.foreach(f => em.persist(f))
    em.flush()

    // Создаем ивент 29 и акшен 19 (по спеке)
    var event29 = dbEventBean.createEvent(request.patientId, 29, plannedDate, null, userData)
    event29.setExecutor(dbStaffBean.getStaffById(request.executorId))
    event29.setExternalId(dbEventBean.getEventById(request.getEventId).getExternalId)
    em.persist(event29)
    em.flush()
    var action19 = actionBean.createAction(event29.getId.intValue(), 19, userData)
    if (request.executorId > 0)
      action19.setExecutor(dbStaffBean.getStaffById(request.executorId))
    if (request.assignerId > 0)
      action19.setAssigner(dbStaffBean.getStaffById(request.assignerId))
    action19.setDirectionDate(plannedDate)
    action19.setEvent(event29)
    em.persist(action19)
    em.flush()
    // создаем и/или заполняем значение проперти 18
    val filter = new FreePersonsListDataFilter(0,
      request.getExecutorId,
      request.getActionTypeId,
      request.getPlannedEndDate.getTime,
      0)
    val timesAP = dbStaffBean.getActionPropertyForPersonByRequest(filter) //пропертя с расписанием должна быть всегда(по идее:-)

    val apSchedule = actionPropertyBean.getActionPropertyById(timesAP.getId.intValue())
    val a = apSchedule.getAction
    var aps = actionPropertyBean.getActionPropertiesByActionIdAndTypeId(a.getId.intValue(), 18) // 18 = queue
    var ap18: ActionProperty = null
    aps.size() match {
      case 0 => {
        ap18 = actionPropertyBean.createActionProperty(a, 18, userData)
        /*
        actionPropertyBean.getActionPropertyValue(apSchedule).foreach(f => {
          if (f.asInstanceOf[APValueDate].getId.getIndex != request.plannedTime.getIndex())
            em.merge(actionPropertyBean.setActionPropertyValue(ap18, null, f.asInstanceOf[APValueAction].getId.getIndex))
        })   */
        em.persist(ap18)
        em.flush()
      }
      case _ => {
        ap18 = aps(0)
      }
    }
    if (ap18 != null) {
      val ap18values = actionPropertyBean.getActionPropertyValue(ap18)
      actionPropertyBean.getActionPropertyValue(apSchedule).foreach(f => {
        if ( //f.asInstanceOf[APValueTime].getId.getIndex != request.plannedTime.getIndex() &&
          ap18values.find(p => p.asInstanceOf[APValueAction].getId.getIndex == f.asInstanceOf[APValueTime].getId.getIndex).getOrElse(null) == null) {
          val propValue = actionPropertyBean.setActionPropertyValue(ap18, null, f.asInstanceOf[APValueTime].getId.getIndex)
          if (propValue != null) em.merge(propValue)
        }
      })

      //*** Обработка срочности и сверх приема по новой спеке
      action.setAppointmentType("hospital")
      if (request.getPacientInQueue == 1) {                      //action.getIsUrgent
        var citoActionsCount = actionBean.getActionForEventAndPacientInQueueType(action.getEvent.getId.intValue(), action.getPlannedEndDate.getTime, 1) //срочные акшены
        /*
        ap18values.foreach(p => {
          if (p.asInstanceOf[APValueAction].getValue != null && p.asInstanceOf[APValueAction].getValue.getPacientInQueueType.intValue() == 1) {
            citoActionsCount = citoActionsCount + 1}
        })  */
        if (action.getExecutor != null && action.getExecutor.getMaxCito > 0 && action.getExecutor.getMaxCito > citoActionsCount) {
          if (request.plannedTime == null || request.plannedTime.getTime == null) {
            //сдвинуть все индексы
            //action.setAppointmentType(1)
            val odin: lang.Integer = 1
            action.setPacientInQueueType(odin.shortValue())
            //em.merge(action)
            ap18values.sortWith(_.asInstanceOf[APValueAction].getId.getIndex > _.asInstanceOf[APValueAction].getId.getIndex)
            ap18values.foreach(apvv => {
              val valueToSave = if (apvv.getValueAsString.compareTo("<EMPTY>") != 0) {
                apvv.asInstanceOf[APValueAction].getValue.getId.toString
              } else {
                null
              }
              em.merge(actionPropertyBean.setActionPropertyValue(ap18, valueToSave, apvv.asInstanceOf[APValueAction].getId.getIndex + 1))
            })
            em.merge(actionPropertyBean.setActionPropertyValue(ap18, action19.getId.toString, 0))
          } else {
            em.merge(actionPropertyBean.setActionPropertyValue(ap18, action19.getId.toString, request.plannedTime.getIndex))
          }
        } else {
          action.setDeleted(true)
          em.flush()
          throw new CoreException(ConfigManager.Messages("error.citoLimit"))
        }
      } else if (request.getPacientInQueue == 2) {                    //request.overQueue
        var overQueueActionsCount = actionBean.getActionForEventAndPacientInQueueType(action.getEvent.getId.intValue(), action.getPlannedEndDate.getTime, 2) //акшены сверх сетки приема
        /*
        ap18values.foreach(p => {
          if (p.asInstanceOf[APValueAction].getValue != null && p.asInstanceOf[APValueAction].getValue.getPacientInQueueType.intValue() == 1) {overQueueActionsCount = overQueueActionsCount + 1}
        }) */
        if (action.getExecutor != null && action.getExecutor.getMaxOverQueue > 0 && action.getExecutor.getMaxOverQueue > overQueueActionsCount) {
          //записать в конец
          //action.setAppointmentType(2)
          val odin: lang.Integer = 2
          action.setPacientInQueueType(odin.shortValue())
          //em.merge(action)
          //val maxIndex = ap18values.sortBy(_.asInstanceOf[APValueAction].getId.getIndex).last.asInstanceOf[APValueAction].getId.getIndex

          em.merge(actionPropertyBean.setActionPropertyValue(ap18, action19.getId.toString, ap18values.size()))
        } else {
          action.setDeleted(true)
          em.flush()
          throw new CoreException(ConfigManager.Messages("error.overQueueLimit"))
        }
      } else {
        if (request.plannedTime != null /* && request.plannedTime.getIndex != null*/ ) {
          em.merge(actionPropertyBean.setActionPropertyValue(ap18, action19.getId.toString, request.plannedTime.getIndex))
        }
      }
      em.merge(action)
    }
    // ****
    em.flush()
    action.getId.intValue()
  }

  def removeDirections(directions: AssignmentsToRemoveDataList, directionType: String, userData: AuthData) = {
    val userId = userData.getUser.getId
    val userRole = userData.getUserRole.getCode

    directions.getData.foreach((f) => {
      var a = actionBean.getActionById(f.getId)

      if ((a.getCreatePerson != null && a.getCreatePerson.getId.compareTo(userId) == 0) ||
        (a.getModifyPerson != null && a.getModifyPerson.getId.compareTo(userId) == 0) ||
        (a.getAssigner != null && a.getAssigner.getId.compareTo(userId) == 0) /*||
          (a.getExecutor!=null && a.getExecutor.getId.compareTo(userId)==0) ||
          userRole.compareTo("strHead")==0*/ ) {
        directionType match {
          case "laboratory" => {
            val res = dbJobTicketBean.getJobTicketAndTakenTissueForAction(a.getEvent.getId.intValue(),
              a.getActionType.getId.intValue(),
              a.getPlannedEndDate,
              hospitalBedBean.getCurrentDepartmentForAppeal(a.getEvent.getId.intValue()).getId.intValue(),
              a.getIsUrgent)
            if (res != null &&
              res.isInstanceOf[(JobTicket, TakenTissue)] &&
              res.asInstanceOf[(JobTicket, TakenTissue)]._1 != null &&
              res.asInstanceOf[(JobTicket, TakenTissue)]._1.getJob != null) {
              val job = res.asInstanceOf[(JobTicket, TakenTissue)]._1.getJob
              if (job.getQuantity == 1) {
                job.setDeleted(true)
              }
              job.setQuantity(job.getQuantity - 1)
              em.merge(job)
            }
          }
          case "consultations" => {
            var action19 = actionBean.getEvent29AndAction19ForAction(a)
            if (action19 != null) {
              //val actionId = action19.getId.intValue()
              var apv = actionPropertyBean.getActionPropertyValue_ActionByValue(action19)
              if (a.getPacientInQueueType.intValue() == 0) {
                if (apv != null) {
                  apv.setValueFromString(null)
                  em.merge(apv)
                }
              } else {
                val ap = actionPropertyBean.getActionPropertyById(apv.getId.getId)
                val currentIndex = apv.getId.getIndex
                if (apv != null) {
                  apv = em.merge(apv)
                  em.remove(apv)
                }
                if (a.getPacientInQueueType.intValue() == 1) {
                  //сдвинуть все индексы
                  val ap18values = actionPropertyBean.getActionPropertyValue(ap)
                  ap18values.sortWith(_.asInstanceOf[APValueAction].getId.getIndex < _.asInstanceOf[APValueAction].getId.getIndex)
                  ap18values.foreach(apvv => {
                    if (apvv.asInstanceOf[APValueAction].getId.getIndex > currentIndex) {
                      if (apvv.asInstanceOf[APValueAction].getId.getIndex == ap18values.size()) {
                        var ap2 = em.merge(apvv)
                        em.remove(ap2)
                      } else {
                        val valueToSave = if (apvv.getValueAsString.compareTo("<EMPTY>") != 0) {
                          apvv.asInstanceOf[APValueAction].getValue.getId.toString
                        } else {
                          null
                        }
                        em.merge(actionPropertyBean.setActionPropertyValue(ap, valueToSave, apvv.asInstanceOf[APValueAction].getId.getIndex - 1))
                      }
                    }
                  })
                }
              }
              //val event29 = action19.getEvent
              action19.setDeleted(true)
              //event29.setDeleted(true)
              //em.detach(event29)
              //em.merge(event29)
              em.merge(action19)
            }
          }
          case _ => {

          }
        }
        a.setDeleted(true)
        //em.detach(a)
        em.merge(a)
      }
      else {
        throw new CoreException(ConfigManager.ErrorCodes.noRightForAction, "Удалять диагностику может только врач, создавший направление, или лечащий врач, или заведующий отделением");
      }
    })
    em.flush()
    true
  }

  def updateJobTicketsStatuses(data: JobTicketStatusDataList, authData: AuthData) = {
    var isSuccess: Boolean = true
    data.getData.foreach(f => {
      var isAllActionSent: Boolean = true
      if (f.getStatus == 2) {
        dbJobTicketBean.getActionsForJobTicket(f.getId).foreach(a => {
          val labCode = dbJobTicketBean.getLaboratoryCodeForActionId(a.getId.intValue())
          if (labCode != null && labCode.compareTo("0101") == 0) {
            try {
              //todo это безобразие убрать, должен быть вызов веб-сервиса с передачей actionId
              lisBean.sendAnalysisRequestToAcross(a.getId.intValue())
            }
            catch {
              case e: Exception => {
                var jt = dbJobTicketBean.getJobTicketById(f.getId)
                jt.setNote(jt.getNote + "Невозможно передать данные об исследовании '%s'. ".format(a.getId.toString))
                jt.setLabel("##Ошибка отправки в ЛИС##")
                isAllActionSent = false
                em.merge(jt)
              }
            }
          }
        })
      }
      var res: Boolean = true
      if (isAllActionSent) {
        res = dbJobTicketBean.modifyJobTicketStatus(f.getId, f.getStatus, authData)
      }
      if (!res)
        isSuccess = res
    })
    em.flush()
    isSuccess
  }

  def sendActionToLis(actionId: Int) {
    var jt = dbJobTicketBean.getJobTicketForAction(actionId)
    if (jt != null) {
      try {
        //todo это безобразие убрать, должен быть вызов веб-сервиса с передачей actionId
        lisBean.sendAnalysisRequestToAcross(actionId)
      }
      catch {
        case e: Exception => {
          jt.setNote(jt.getNote + "Невозможно передать данные об исследовании '%s'. ".format(actionId.toString))
          jt.setLabel("##Ошибка отправки в ЛИС##")
          em.merge(jt)
          em.flush()
        }
      }
    }
  }
}
