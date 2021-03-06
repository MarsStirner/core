package ru.korus.tmis.core.patient

import java.util
import java.util.{Collections, Date}
import javax.annotation.Resource
import javax.ejb.{EJB, Stateless}

import javax.jms._
import javax.persistence.{EntityManager, PersistenceContext}

import grizzled.slf4j.Logging
import org.apache.commons.collections.CollectionUtils
import org.joda.time.DateTime
import ru.korus.tmis.core.auth.AuthData
import ru.korus.tmis.core.common.CommonDataProcessorBeanLocal
import ru.korus.tmis.core.data._
import ru.korus.tmis.core.database._
import ru.korus.tmis.core.database.bak.{DbBakCustomQueryBeanLocal, BakDiagnosis}
import ru.korus.tmis.core.database.common._
import ru.korus.tmis.core.entity.model._
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.core.filter.ActionsListDataFilter
import ru.korus.tmis.laboratory.across.business.AcrossBusinessBeanLocal
import ru.korus.tmis.lis.data._
import ru.korus.tmis.lis.data.jms.LISMessageReceiver
import ru.korus.tmis.scala.util.ConfigManager._
import ru.korus.tmis.scala.util.{CAPids, ConfigManager, I18nable}
import ru.korus.tmis.schedule.PersonScheduleBean.PersonSchedule
import ru.korus.tmis.schedule.{PacientInQueueType, PersonScheduleBeanLocal, QueueActionParam}

import scala.collection.JavaConversions._
import scala.collection.mutable.ListBuffer
import scala.language.reflectiveCalls

/**
 * Методы для работы с Направлениями
 * @author mmakankov Systema-Soft
 * @since 1.0.0.70
 */
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
  private var actionBean: DbActionBeanLocal = _
  @EJB
  private var dbMkbBean: DbMkbBeanLocal = _
  @EJB
  private var dbEventBean: DbEventBeanLocal = _
  @EJB
  private var dbStaffBean: DbStaffBeanLocal = _
  @EJB
  private var personScheduleBean: PersonScheduleBeanLocal = _
  @EJB
  private var patientBean: DbPatientBeanLocal = _
  @EJB
  private var dbActionProperty: DbActionPropertyBeanLocal = _
  @EJB
  private var dbCustomQuery: DbCustomQueryLocal = _
  @EJB
  private var dbActionTypeBean: DbActionTypeBeanLocal = _
  @EJB
  private var dbBakCustomQueryBean: DbBakCustomQueryBeanLocal = _


  @Resource(lookup = "DefaultConnectionFactory")
  private var connectionFactory: ConnectionFactory = _
  @Resource(lookup = "LaboratoryTopic")
  private var topic: Topic = _
  @Resource(lookup = "LaboratoryQueue")
  private var queue: Queue = _


  //todo должен быть вызов веб-сервиса с передачей actionId
  @EJB
  var lisAcross: AcrossBusinessBeanLocal = _


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
      AWI.Status,
      AWI.Finance,
      AWI.PlannedEndDate,
      AWI.ExecutorId,
      AWI.AssignerId,
      AWI.PacientInQueueType
    )
    commonDataProcessor.addAttributes(group, new ActionWrapper(direction), attributes)
  }

  def detailsWithAge(direction: Action) = {
    var attributes = List(APWI.Value,
      APWI.ValueId,
      APWI.Unit,
      APWI.Norm)
    if (direction.getActionType.getMnemonic.toUpperCase.equals("LAB") ||
      direction.getActionType.getMnemonic.toUpperCase.equals("BAK_LAB"))
      attributes = attributes ::: List(APWI.IsAssignable, APWI.IsAssigned)

    val propertiesMap = actionPropertyBean.getActionPropertiesByActionId(direction.getId.intValue)
    val group = new CommonGroup(1, "Details")

    propertiesMap.foreach(
      p => {
        val (ap, apvs) = p
        if (apvs.size > 0 /* Отдаем совойство со значением, даже если оно не подходит по типу*/
          || commonDataProcessor.checkActionPropertyTypeForPatientAgeAndSex(direction.getEvent.getPatient, ap.getType)) {
          val apw = new ActionPropertyWrapper(ap, dbActionProperty.convertValue, dbActionProperty.convertScope, dbActionProperty.convertColType)
          group.add(apw.get(apvs.toList, attributes))
        }
      }
    )
    group
  }

  def getDirectionById(directionId: Int,
                       title: String,
                       postProcessingForDiagnosis: (JSONCommonData, java.lang.Boolean) => JSONCommonData,
                       staff: Staff) = {

    val action = actionBean.getActionById(directionId)
    val actions: java.util.List[Action] = new util.LinkedList[Action]
    actions.add(action)

    val com_data = commonDataProcessor.fromActions(
      actions,
      title,
      List(summary _, detailsWithAge _))

    val isTrueDoctor = staff.getId.intValue() == action.getCreatePerson.getId.intValue() ||
      (action.getAssigner != null && staff.getId.intValue() == action.getAssigner.getId.intValue())
    val jt = dbJobTicketBean.getJobTicketForAction(action.getId.intValue())
    com_data.getEntity.get(0).setIsEditable(action.getStatus == 0 && action.getEvent.getExecDate == null && isTrueDoctor && (jt == null || (jt != null && jt.getStatus == 0)))

    var json_data = new JSONCommonData()
    json_data.data = com_data.entity
    if (postProcessingForDiagnosis != null) {
      json_data = postProcessingForDiagnosis(json_data, false)
    }
    json_data
  }

  private def createJobTicketsForActions(actions: java.util.List[Action], eventId: Int) = {

    val department = hospitalBedBean.getCurrentDepartmentForAppeal(eventId)
    val list = new java.util.LinkedList[(Job, JobTicket, TakenTissue, Action)]
    val apvList = new java.util.LinkedList[(ActionProperty, JobTicket)]
    val apvMKBList = new java.util.LinkedList[(ActionProperty, Mkb)]
    var jtForAp: JobTicket = null

    val actionsSendToLIS = ListBuffer[(Action, Int)]()

    actions.foreach((a) => {
      val jobAndTicket = dbJobTicketBean.getJobTicketAndTakenTissueForAction(a.getEvent.getId.intValue(),
        a.getActionType.getId.intValue(),
        a.getPlannedEndDate,
        department.getId.intValue(),
        a.getIsUrgent)

      val tissueType = dbTakenTissue.getActionTypeTissueTypeByMasterId(a.getActionType.getId.intValue())
      if (tissueType == null)
        throw new CoreException(
          ConfigManager.ErrorCodes.TakenTissueNotFound, i18n("error.TissueTypeNotFound").format(
          actions.filter(_.getActionType.getActionTypeTissueType == null).map(_.getActionType.getName).toSet.mkString(", ")
          ))

      if (jobAndTicket == null) {
        val fromList = list.find((p) => p._1.getId == null &&
          p._2.getDatetime == a.getPlannedEndDate &&
          p._3.getType.getId == tissueType.getTissueType.getId &&
          p._4.getIsUrgent == a.getIsUrgent).orNull //срочные на одну дату и тип биоматериала должны создаваться с одним жобТикетом
        if (fromList != null) {
          val (j, jt, tt) = (fromList._1, fromList._2, fromList._3)
          j.setQuantity(j.getQuantity + 1)
          if (tt != null) a.setTakenTissue(tt)
          jtForAp = jt
        } else {
          val j = dbJobBean.insertOrUpdateJob(0, a, department)
          val jt = dbJobTicketBean.insertOrUpdateJobTicket(0, a, j)
          val tt = dbTakenTissue.insertOrUpdateTakenTissue(0, a)
          if (tt != null) a.setTakenTissue(tt)
          list.add(j, jt, tt, a)
          jtForAp = jt
        }
      } else {
        val (jobTicket, takenTissue) = jobAndTicket.asInstanceOf[(JobTicket, TakenTissue)]
        if (jobTicket != null && jobTicket.getJob != null) {
          val fromList = list.find((p) => p._1.getId != null && p._1.getId.intValue() == jobTicket.getJob.getId.intValue()).orNull
          if (fromList == null) {
            val j = dbJobBean.insertOrUpdateJob(jobTicket.getJob.getId.intValue(), a, department)
            val jt = dbJobTicketBean.insertOrUpdateJobTicket(jobTicket.getId.intValue(), a, j)
            if (takenTissue != null) a.setTakenTissue(takenTissue)
            list.add(j, jt, takenTissue, a)
            jtForAp = jt
            if(jobTicket.getStatus == JobTicket.STATUS_IS_FINISHED) actionsSendToLIS += Tuple2(a, jt.getId)
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
        val last = actionBean.getActionsWithFilter(0, 0, "", filter.unwrap(), null)
        if (last != null && last.size() > 0 && jobTicket.getStatus == 2 && !a.getIsUrgent) {
          a.setStatus(2)
        }
      }

      a.getActionProperties.foreach((ap) => {
        if (ap.getType.getTypeName.compareTo("JobTicket") == 0) {
          apvList.add((ap, jtForAp))
        }
      })
    })

    if (list != null && list.size() > 0) {
      list.foreach((f) => {
        var (j, jt, tt) = (f._1, f._2, f._3)
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
        val (ap, jt) = value
        em.merge(actionPropertyBean.setActionPropertyValue(ap, jt.getId.intValue().toString, 0))
      })
      //сохраняем пропертиВалуе МКБ
      apvMKBList.foreach((value) => {
        val (ap, mkb) = value
        em.merge(actionPropertyBean.setActionPropertyValue(ap, mkb.getId.intValue().toString, 0))
      })
      //сохраняем изменения в акшенах (setTakenTissue) (TakenTissueJournal)
      actions.map(em.merge)
      em.flush()
    }


    actionsSendToLIS.foreach(p => {
      val labCode = dbJobTicketBean.getLaboratoryCodeForActionId(p._1.getId.intValue())
      if (labCode != null && labCode.compareTo("0101") == 0) {
        // отправка назначения в Акросс
        try {
          //todo должен быть вызов веб-сервиса с передачей actionId
          lisAcross.sendAnalysisRequestToAcross(p._1.getId.intValue())
          // Устанавливаем статус "Ожидание" на Action, если была произведена отправка в лабораторию
          actionBean.updateActionStatusWithFlush(p._1.getId, ru.korus.tmis.core.entity.model.ActionStatus.WAITING.getCode)
        }
        catch {
          case e: Exception => {
            val jt = dbJobTicketBean.getJobTicketById(p._2)
            val oldNote = jt.getNote match {case null => "" case _ => jt.getNote}
            jt.setNote(oldNote + "Невозможно передать данные об исследовании '" + p._1.getId + "'." + e.getMessage + "\n")
            jt.setLabel("##Ошибка отправки в ЛИС##")
            jt.setStatus(JobTicket.STATUS_IN_PROGRESS)
            em.merge(jt)
          }
        }
      } else if (labCode != null) {
        // Отправка назначения в Bak CGM или любую другую лабораторию-модуль
        try {
          sendJMSLabRequest(p._1.getId.intValue(), labCode)
          dbJobTicketBean.modifyJobTicketStatus(p._2, JobTicket.STATUS_SENDING)
        }
        catch {
          case e: Exception => {
            val jt = dbJobTicketBean.getJobTicketById(p._2)
            val oldNote = jt.getNote match {case null => "" case _ => jt.getNote}
            jt.setNote(oldNote + "Невозможно передать данные об исследовании '" + p._1.getId + "'." + e.getMessage + "\n")
            jt.setLabel("##Ошибка отправки в ЛИС## ")
            jt.setStatus(JobTicket.STATUS_IN_PROGRESS)
            em.merge(jt)
          }
        }
      }
    })

    actions
  }

  def createDirectionsForEventIdFromCommonData(eventId: Int,
                                               directions: CommonData,
                                               title: String,
                                               request: Object,
                                               mnem: String,
                                               userData: AuthData,
                                               staff: Staff,
                                               postProcessingForDiagnosis: (JSONCommonData, java.lang.Boolean) => JSONCommonData) = {
    var actions: java.util.List[Action] = commonDataProcessor.createActionForEventFromCommonData(eventId, directions, userData, staff)
    //Для лабораторных исследований отработаем с JobTicket
    if (mnem.toUpperCase.equals("LAB") || mnem.toUpperCase.equals("BAK_LAB")) {
      try {
        actions = createJobTicketsForActions(actions, eventId)
      } catch {
        // Если произошла ошибка в процессе проставления JobTickets -
        // то помечаем действие лабораторного исследования как удаленное
        case e: Exception => {
          actions.foreach(a => {
            val act = em.find(classOf[Action], a.getId)
            if(act != null) { act.setDeleted(true); em.flush() }
          })
          throw e
        }
      }
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
                                               staff: Staff,
                                               postProcessingForDiagnosis: (JSONCommonData, java.lang.Boolean) => JSONCommonData) = {
    var actions: java.util.List[Action] = null
    val userId = staff.getId
    val flgLab = mnem.toUpperCase.equals("LAB") || mnem.toUpperCase.equals("BAK_LAB")

    directions.getEntity.foreach((action) => {
      //Проверка прав у пользователя на редактирование направления
      var oldJT = 0
      val a = actionBean.getActionById(action.getId.intValue())
      if (flgLab) {
        a.getActionProperties.foreach((ap) => {
          if (ap.getType.getTypeName.equals("JobTicket") && !actionPropertyBean.getActionPropertyValue(ap).isEmpty) {
            oldJT = actionPropertyBean.getActionPropertyValue(ap).head.asInstanceOf[APValueJobTicket].getValue.intValue()
          }
        })
      }

      if ((a.getCreatePerson != null && a.getCreatePerson.getId.compareTo(userId) == 0) ||
        (a.getModifyPerson != null && a.getModifyPerson.getId.compareTo(userId) == 0) ||
        (a.getAssigner != null && a.getAssigner.getId.compareTo(userId) == 0)) {
        actions = commonDataProcessor.modifyActionFromCommonData(action.getId.intValue(), directions, userData, staff)
        if (flgLab) {
          actions = createJobTicketsForActions(actions, a.getEvent.getId.intValue())
          //редактирование или удаление старого жобТикета
          val jobTicket = if (oldJT > 0) dbJobTicketBean.getJobTicketById(oldJT) else null
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

  def createServiceForConsultation(request: ConsultationRequestData, plannedDate: Date, userData: AuthData, staff: Staff): Action = {
    val action: Action = actionBean.createAction(request.eventId.intValue(), request.actionTypeId.intValue(), userData, staff)
    action.setIsUrgent(request.urgent) //если постановка в очередь срочная, то и услуга срочная
    val createPerson: Staff = dbStaffBean.getStaffById(request.createPerson)
    if (request.createPerson > 0 &&  createPerson != null) {
      action.setCreatePerson(createPerson)
    }
    if (request.createDateTime != null) {
      action.setCreateDatetime(request.createDateTime)
      action.setBegDate(request.createDateTime)
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
      val ap = actionPropertyBean.createActionProperty(action, apt.getId.intValue(), staff)
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
          val props = actionPropertyBean.getActionPropertyValue(ap)
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
    action
  }

  def createConsultation(request: ConsultationRequestData, userData: AuthData, staff: Staff) = {

    val plannedDate = if (request.plannedTime != null && request.plannedTime.getTime != null) {
      new Date(request.plannedEndDate.getTime + request.plannedTime.getTime.getTime)
    } else {
      new Date(request.plannedEndDate.getTime)
    }
    // Создание услуги
    val action: Action = createServiceForConsultation(request, plannedDate, userData, staff)

    // Записываем пациента в очередь на время
    val personAction = dbStaffBean.getPersonActionsByDateAndType(request.getExecutorId, request.getPlannedEndDate, "amb")

    val personSchedule: PersonSchedule = personScheduleBean.newInstanceOfPersonSchedule(personAction)
    personScheduleBean.formTickets(personSchedule)
    val pacientInQueueType = if (request.getUrgent) {
      PacientInQueueType.URGENT
    } else if (request.getOverQueue) {
      PacientInQueueType.OVERQUEUE
    } else {
      PacientInQueueType.QUEUE
    }
    val queueActionParam: QueueActionParam = new QueueActionParam().setAppointmentType(AppointmentType.HOSPITAL).setPacientInQueueType(pacientInQueueType)
    val res = personScheduleBean.enqueuePatientToTime(personSchedule, plannedDate, patientBean.getPatientById(request.getPatientId), queueActionParam)
    if (!res.isSuccess) {
      throw new CoreException(res.getMessage)
    }
    action.getId.intValue()
  }

  def removeDirections(directions: AssignmentsToRemoveDataList, directionType: String, userData: Staff) = {
    val userId = userData.getId

    directions.getData.foreach((f) => {
      val a = actionBean.getActionById(f.getId)

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
            val action19 = actionBean.getEvent29AndAction19ForAction(a)
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
                        val ap2 = em.merge(apvv)
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

              em.merge(action19)
            }
          }
          case _ => {

          }
        }
        a.setDeleted(true)

        em.merge(a)
      }
      else {
        throw new CoreException(ConfigManager.ErrorCodes.noRightForAction, "Удалять диагностику может только врач, создавший направление, или лечащий врач, или заведующий отделением")
      }
    })
    em.flush()
    true
  }

  def updateJobTicketsStatuses(data: JobTicketStatusDataList, authData: Staff) = {
    var isSuccess: Boolean = true
    data.getData.foreach(f => {
      if (f.getStatus == 1) {
        dbJobTicketBean.getActionsForJobTicket(f.getId).foreach(a => {
          val tt = a.getTakenTissue
          if (tt != null) {
            tt.setPerson(authData)
            em.merge(tt)
          }
        })
      }
      val res: Boolean = dbJobTicketBean.modifyJobTicketStatus(f.getId, f.getStatus)
      if (!res)
        isSuccess = res
    })
    em.flush()
    isSuccess
  }

  def sendActionsToLaboratory(data: SendActionsToLaboratoryDataList, authData: Staff) = {
    var isSuccess: Boolean = true
    data.getData.foreach(f => {
      val allActions: util.List[Action] = dbJobTicketBean.getActionsForJobTicket(f.getId)
      //По умолчанию считается что посылаются все не отправленные экшены из жобтикета (filter еще не отправленные),
      // соответственно если коллекция A (Неотправленные экшены из жобтикета) является подколлекцией Б (все экшены для отправки), то посылаются  все экшены
      // В данном случае подколлекция- это когда частота встречи E(элемент) в A всегда меньше или равна частоте встречи E в Б.
      var isAllActionSent: Boolean = CollectionUtils.isSubCollection(allActions.filter(_.getStatus == 0).map(_.getId), f.getData.map(_.getId))
       f.getData.foreach(a => {
          val labCode = dbJobTicketBean.getLaboratoryCodeForActionId(a.getId.intValue())
          if (labCode != null && labCode.compareTo("0101") == 0) {
            // отправка назначения в Акросс
            try {
              //todo должен быть вызов веб-сервиса с передачей actionId
              lisAcross.sendAnalysisRequestToAcross(a.getId.intValue())
              // Устанавливаем статус "Ожидание" на Action, если была произведена отправка в лабораторию
              actionBean.updateActionStatusWithFlush(a.getId, ru.korus.tmis.core.entity.model.ActionStatus.WAITING.getCode)
            }
            catch {
              case e: Throwable =>
                val jt = dbJobTicketBean.getJobTicketById(f.getId)
                val oldNote = jt.getNote match {case null => "" case _ => jt.getNote}
                jt.setNote(oldNote + "Невозможно передать данные об исследовании '" + a.getId + "'." + e.getMessage + "\n")
                jt.setLabel("##Ошибка отправки в ЛИС##")
                isAllActionSent = false
                em.merge(jt)
            }
          } else if (labCode != null) {
            // Отправка назначения в Bak CGM или любую другую лабораторию-модуль
            try {
              sendJMSLabRequest(a.getId.intValue(), labCode)
              isAllActionSent = false // Статус проставляется автоматически по возвращению сообщения JMS
              dbJobTicketBean.modifyJobTicketStatus(f.getId, JobTicket.STATUS_SENDING)
            }
            catch {
              case e: Exception => {
                val jt = dbJobTicketBean.getJobTicketById(f.getId)
                val oldNote = jt.getNote match {case null => "" case _ => jt.getNote}
                jt.setNote(oldNote + "Невозможно передать данные об исследовании '" + a.getId + "'." + e.getMessage + "\n")
                jt.setLabel("##Ошибка отправки в ЛИС##")
                isAllActionSent = false
                em.merge(jt)
              }
            }
          }
        })
      var res: Boolean = true
      if (isAllActionSent) {
        res = dbJobTicketBean.modifyJobTicketStatus(f.getId, JobTicket.STATUS_IS_FINISHED)
      }
      if (!res)
        isSuccess = res
    })
    em.flush()
    isSuccess
  }


  /**
   * Отправка назначения в Акросс
   * @param actionId Идентификатор действия
   */
  def sendActionToLis(actionId: Int) {
    val jt = dbJobTicketBean.getJobTicketForAction(actionId)
    if (jt != null) {
      val labCode = dbJobTicketBean.getLaboratoryCodeForActionId(actionId)
      if (labCode != null && labCode.compareTo("0101") == 0) {
        // отправка назначения в Акросс
        try {
          //todo должен быть вызов веб-сервиса с передачей actionId
          lisAcross.sendAnalysisRequestToAcross(actionId)
          // Устанавливаем статус "Ожидание" на Action, если была произведена отправка в лабораторию
          actionBean.updateActionStatusWithFlush(actionId, ru.korus.tmis.core.entity.model.ActionStatus.WAITING.getCode)
        }
        catch {
          case e: Exception => {
            val oldNote = jt.getNote match {case null => "" case _ => jt.getNote}
            jt.setNote(oldNote + "Невозможно передать данные об исследовании '" + actionId + "'." + e.getMessage + "\n")
            jt.setLabel("##Ошибка отправки в ЛИС##")
            jt.setStatus(JobTicket.STATUS_IN_PROGRESS)
            em.merge(jt)
            em.flush()
          }
        }
      } else if (labCode != null) {
        // Отправка назначения в Bak CGM или любую другую лабораторию-модуль
        try {
          sendJMSLabRequest(actionId, labCode)
          dbJobTicketBean.modifyJobTicketStatus(actionId, JobTicket.STATUS_SENDING)
        }
        catch {
          case e: Exception => {
            val oldNote = jt.getNote match {case null => "" case _ => jt.getNote}
            jt.setNote(oldNote + "Невозможно передать данные об исследовании '" + actionId + "'." + e.getMessage + "\n")
            jt.setLabel("##Ошибка отправки в ЛИС## ")
            jt.setStatus(JobTicket.STATUS_IN_PROGRESS)
            em.merge(jt)
            em.flush()
          }
        }
      }
    }
  }

  def sendJMSLabRequest(actionId: Int) = {
    sendJMSLabRequest(actionId, dbJobTicketBean.getLaboratoryCodeForActionId(actionId))
  }

  private def sendJMSLabRequest(actionId: Int, labCode: String) = {
    val action = actionBean.getActionById(actionId)


    if(action == null)
      throw new Exception("Action ")

    val takingTime = action.getActionProperties.find(p => p.getType.getCode != null && p.getType.getCode.equals("TAKINGTIME"))
    if(takingTime.isEmpty)
      throw new CoreException("Невозможно сформировать запрос в лабораторию, т.к. не удалось найти свойство " +
        "\"Время забора\" с кодом [TAKINGTIME] для исследования " + action.getActionType.getName + " [id=" +
        action.getActionType.getId + "]")

    var connection: Connection = null
    var session: Session = null
    try {
      val actionType: ActionType = getActionType(action)
      val eventInfo: Event = action.getEvent
      val patientInfo: Patient = eventInfo.getPatient
      val requestInfo: DiagnosticRequestInfo = getDiagnosticRequestInfo(action)
      val biomaterialInfo: BiomaterialInfo = getBiomaterialInfo(action)
      val orderInfo: OrderInfo = getOrderInfo(action, actionType)



      val requestObject = new LaboratoryCreateRequestData(action, requestInfo, patientInfo, eventInfo, biomaterialInfo, orderInfo)
      connection = connectionFactory.createConnection()
      session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE)
      val publisher = session.createProducer(topic)
      val message = session.createObjectMessage()
      message.setJMSType(LISMessageReceiver.JMS_TYPE)
      message.setStringProperty(LISMessageReceiver.JMS_LAB_PROPERTY_NAME, labCode)
      message.setObject(requestObject)
      message.setJMSReplyTo(queue)
      publisher.send(message)
    }
    catch {
      case t: Throwable => t.printStackTrace();
    }
    finally {
      if (session != null) {
        try {
          session.close()
        }
        catch {
          case e: JMSException =>
        }
      }
      if (connection != null) {
        try {
          connection.close()
        }
        catch {
          case e: Exception => e.printStackTrace()
        }
      }
    }
  }

  private def getActionType(action: Action): ActionType = {
    val actionType: ActionType = action.getActionType
    if (actionType.getId == -1) {
      throw new CoreException("Error no Type For Action" + action.getId)
    }
    actionType
  }

  private def getDiagnosticRequestInfo(action: Action): DiagnosticRequestInfo = {
    val diag: DiagnosticRequestInfo = new DiagnosticRequestInfo
    val id: Int = action.getId
    diag.setOrderMisId(id)
    val orderCaseId: String = "" + action.getEvent.getExternalId
    diag.setOrderCaseId(orderCaseId)
    val orderFinanceId: Int = dbCustomQuery.getFinanceId(action.getEvent)
    diag.setOrderFinanceId(orderFinanceId)
    val date: DateTime = new DateTime(action.getCreateDatetime)
    diag.setOrderMisDate(date)
    val pregMin: Int = action.getEvent.getPregnancyWeek * 7
    val pregMax: Int = action.getEvent.getPregnancyWeek * 7
    diag.setOrderPregnatMin(pregMin)
    diag.setOrderPregnatMax(pregMax)
    val diagnosisBak: BakDiagnosis = dbBakCustomQueryBean.getBakDiagnosis(action)
    if (diagnosisBak != null) {
      diag.setOrderDiagCode(diagnosisBak.getCode)
      diag.setOrderDiagText(diagnosisBak.getName)
    }
    val comment: String = action.getNote
    diag.setOrderComment(comment)
    var department: OrgStructure = getOrgStructureByEvent(action.getEvent)
    if(department == null){
      warn("No department found for Action[%s] in moving's, search in RECEIVE Action".format(action.getId))
      department = getOrgStructureFromRecievedActionInEvent(action.getEvent)
    }
    if (department != null) {
      diag.setOrderDepartmentMisCode(String.valueOf(department.getId))
      diag.setOrderDepartmentName(department.getName)
    } else {
       error("No department found for Action[%s]".format(action.getId))
    }
    val doctorLastname: String = action.getAssigner.getLastName
    val doctorFirstname: String = action.getAssigner.getFirstName
    val doctorPartname: String = action.getAssigner.getPatrName
    val doctorCode: Int = action.getAssigner.getId
    diag.setOrderDoctorFamily(doctorLastname)
    diag.setOrderDoctorName(doctorFirstname)
    diag.setOrderDoctorPatronum(doctorPartname)
    diag.setOrderDoctorMisId(action.getAssigner.getId)
    diag.setOrderDoctorMis(action.getAssigner)
    diag
  }

  private def getBiomaterialInfo(action: Action): BiomaterialInfo = {
    val tt: TakenTissue = action.getTakenTissue
    val `type`: RbTissueType = tt.getType
    if (`type` != null) {
      val bi: BiomaterialInfo = new BiomaterialInfo(`type`.getCode, `type`.getName, String.valueOf(tt.getBarcode), tt.getPeriod, 0, new DateTime(tt.getDatetimeTaken), tt.getNote)
      return bi
    }
    throw new CoreException("Не найден RbTissueType для actionId " + action.getId)
  }

  private def getOrderInfo(action: Action, actionType: ActionType): OrderInfo = {
    val orderInfo: OrderInfo = new OrderInfo
    val code: String = actionType.getCode
    orderInfo.setDiagnosticCode(code)
    val name: String = actionType.getName
    orderInfo.setDiagnosticName(name)
    val priority: OrderInfo.Priority = if (action.getIsUrgent) OrderInfo.Priority.URGENT else OrderInfo.Priority.NORMAL
    orderInfo.setOrderPriority(priority)
    var aa: ActionPropertyType = null
    val apts: util.List[ActionPropertyType] = dbActionTypeBean.getActionTypePropertiesById(actionType.getId)
    for (apt <- apts) {
      if (apt.getTest != null) {
        aa = apt
      }
    }
    if (aa == null) {
      throw new CoreException("не определены показатели из rbTest, не нужно отправлять анализ в ЛИС actionId: " + action.getId)
    }
    val aptsSet: util.Set[ActionPropertyType] = new util.HashSet[ActionPropertyType]
    for (apt <- apts) {
      aptsSet.add(apt)
    }
    val apsMap: util.Map[ActionPropertyType, ActionProperty] = action.getActionPropertiesByTypes(aptsSet)
    for (entry <- apsMap.entrySet) {
      val ap: ActionProperty = entry.getValue
      val apt: ActionPropertyType = entry.getKey
      if (apt.getTest != null && (!apt.getIsAssignable || ap.getIsAssigned)) {
        orderInfo.addIndicatorList(new IndicatorMetodic(apt.getTest.getName, apt.getTest.getCode))
      }
    }
    orderInfo
  }

  private def getOrgStructureByEvent(e: Event): OrgStructure = {
    val hospitalBeds: util.Map[Event, ActionProperty] = dbCustomQuery.getHospitalBedsByEvents(Collections.singletonList(e))
    if (hospitalBeds == null) {
      return null
    }
    for (event <- hospitalBeds.keySet) {
      val actionProperty: ActionProperty = hospitalBeds.get(event)
      val actionPropertyValue: util.List[APValue] = dbActionProperty.getActionPropertyValue(actionProperty)
      for (apValue <- actionPropertyValue) {
        apValue.getValue match {
          case bed: OrgStructureHospitalBed =>
            return bed.getMasterDepartment
          case _ =>
        }
      }
    }
    null
  }

  def getOrgStructureFromRecievedActionInEvent(e: Event): OrgStructure = {
    val orgStructureAP = dbCustomQuery.getOrgStructureByReceivedActionByEvents(Collections.singletonList(e))
    orgStructureAP.get(e) match {
      case null => {
        warn("OrgStructure from received Action not found for " + e)
      }
      case ap: ActionProperty => {
        val apvs = dbActionProperty.getActionPropertyValue(ap)
        apvs.foreach(
          (apv) => {
            apv.getValue match {
              case os: OrgStructure =>
                return os
              case _ =>
            }
          })
      }
    }

    null
  }
}
