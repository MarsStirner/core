package ru.korus.tmis.core.database.common

import java.text.SimpleDateFormat
import java.util
import java.util.{UUID, Date}
import javax.ejb.{EJB, Stateless}
import javax.persistence.{EntityManager, PersistenceContext, TypedQuery}

import grizzled.slf4j.Logging
import org.joda.time.{DateTime, DateTimeConstants}
import ru.korus.tmis.core.auth.AuthData
import ru.korus.tmis.core.data.QueryDataStructure
import ru.korus.tmis.core.database.DbActionTypeBeanLocal
import ru.korus.tmis.core.entity.model._
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.core.filter.ListDataFilter
import ru.korus.tmis.scala.util.{ConfigManager, I18nable}
import ru.korus.tmis.schedule.QueueActionParam

import scala.collection.JavaConversions._
import scala.language.reflectiveCalls

//
@Stateless
class DbActionBean
  extends DbActionBeanLocal
  with Logging
  with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  @EJB
  var dbEvent: DbEventBeanLocal = _

  @EJB
  var dbActionType: DbActionTypeBeanLocal = _

  @EJB
  var dbEventPerson: DbEventPersonBeanLocal = _

  @EJB
  var dbSetting: DbSettingsBeanLocal = _

  def getCountRecordsOrPagesQuery(enterPosition: String, filterQuery: String): TypedQuery[Long] = {

    val cntMacroStr = "count(a)"
    val sorting = ""

    //выберем нужный запрос
    var curentRequest = enterPosition.format(cntMacroStr, filterQuery, sorting)

    //уберем из запроса фильтрацию
    val index = curentRequest.indexOf("ORDER BY")
    if (index > 0) {
      curentRequest = curentRequest.substring(0, index)
    }
    em.createQuery(curentRequest.toString, classOf[Long])
  }

  //@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  def getActionById(id: Int) = {
    val res: Action = getActionIdWithoutDetach(id)

    res
  }


  def getActionIdWithoutDetach(id: Int): Action = {
    info("Requested action id[" + id + "]")
    val result = em.createQuery(ActionFindQuery,
      classOf[Action])
      .setParameter("id", id)
      .getResultList

    val res: Action = result.size match {
      case 0 => {
        throw new CoreException(
          ConfigManager.ErrorCodes.ActionNotFound,
          i18n("error.actionNotFound") + " id = " + id)
      }
      case size => {
        result.iterator.next()
      }
    }
    res
  }

  //@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  def getActionByIdWithIgnoreDeleted(id: Int) = {
    info("Requested action id[" + id + "]")
    val result = em.createQuery(
      """
                                  SELECT a
                                  FROM
                                    Action a
                                  WHERE
                                    a.id = :id
      """,
      classOf[Action])
      .setParameter("id", id)
      .getResultList

    result.size match {
      case 0 => {
        throw new CoreException(
          ConfigManager.ErrorCodes.ActionNotFound,
          i18n("error.actionNotFound"))
      }
      case size => {
        val action = result.iterator.next()

        action
      }
    }
  }

  def createAction(eventId: Int, actionTypeId: Int, userData: AuthData, staff: Staff) = {
    val e = dbEvent.getEventById(eventId)
    val at = dbActionType.getActionTypeById(actionTypeId)

    val now = new Date
    val a = new Action

    if (staff != null) {
      a.setCreatePerson(staff)
      a.setCreateDatetime(now)
      a.setModifyPerson(staff)
      a.setModifyDatetime(now)

      var eventPerson: EventPerson = null
      if (userData != null &&
        (userData.getUserRole.getCode.compareTo("admNurse") == 0 || userData.getUserRole.getCode.compareTo("strNurse") == 0)) {
        eventPerson = dbEventPerson.getLastEventPersonForEventId(eventId)
        if (eventPerson != null) {
          a.setAssigner(eventPerson.getPerson)
        } else {
          a.setAssigner(staff)
        }
      }
      else
        a.setAssigner(staff)

      // Исправление дефолтного значения от 03.07.2013 по задаче WEBMIS-873
      a.setExecutor(staff) //a.setExecutor(at.getDefaultExecutor)

    }

    a.setCreateDatetime(now)
    a.setModifyDatetime(now)
    a.setBegDate(now)
    //a.setEndDate(now)

    a.setEvent(e)
    a.setActionType(at)
    //WEBMIS-375  При создании нового action в поле Action.amount необходимо записывать значение из поля ActionType.amount
    a.setAmount(at.getAmount)

    a.setStatus(ActionStatus.STARTED.getCode)
    a.setUuid(UUID.randomUUID())
    em.persist(a)
    a
  }

  def updateAction(id: Int, version: Int, authData: AuthData, staff: Staff) = {
    val a = getActionById(id)
    val now = new Date

    if (staff != null) {
      var eventPerson: EventPerson = null
      if (authData != null && (authData.getUserRole.getCode.compareTo("admNurse") == 0 || authData.getUserRole.getCode.compareTo("strNurse") == 0)) {
        eventPerson = dbEventPerson.getLastEventPersonForEventId(a.getEvent.getId.intValue())
        if (eventPerson != null) a.setAssigner(eventPerson.getPerson) else a.setAssigner(staff)
      }
      a.setModifyPerson(staff)
      //a.setExecutor(userData.user)
    }

    a.setModifyDatetime(now)

    a.setBegDate(now)
    //a.setEndDate(now)

    em.merge(a)
    a
  }

  def updateActionStatus(id: Int, status: Short) = {
    val a = getActionById(id)
    a.setStatus(status)
    a
  }

  def updateActionStatusWithFlush(id: Int, status: Short) = {
    em.createQuery("UPDATE Action a SET a.status = :status WHERE a.id = :id")
      .setParameter("status", status)
      .setParameter("id", id)
      .executeUpdate()
    em.flush()
  }

  def getAppealActionByEventId(eventId: Int, atId: Int) = {

    val result = em.createQuery(ActionByEventIdAndActionTypeQuery,
      classOf[Action])
      .setParameter("id", eventId)
      .setParameter("atId", atId)
      .getResultList

    result.size match {
      case 0 => {
        null
      }
      case size => {
        result.iterator().next
      }
    }
  }

  def getActionByEventExternalId(externalId: String) = {
    val result = em.createQuery(ActionByEventExternalIdQuery,
      classOf[Action])
      .setParameter("externalId", externalId)
      .getResultList

    result.size match {
      case 0 => {
        null
      }
      case size => {

        val action = result.iterator().next
        action
      }
    }
  }

  def getLastActionWithTypeId(actionType: ActionType) = {
    val result = em.createQuery(ActionLastByTypeId,
      classOf[Action])
      .setParameter("actionType", actionType)
      .getResultList


    result.iterator().next()
  }

  def getActionsWithFilter(limit: Int,
                           page: Int,
                           sorting: String,
                           filter: ListDataFilter,
                           records: (java.lang.Long) => java.lang.Boolean) = {

    val queryStr: QueryDataStructure = filter.toQueryStructure

    if (records != null) {
      val countTyped = em.createQuery(ActionsForSwitchPatientByEventQuery.format("count(a)", queryStr.query, ""), classOf[Long])
      if (queryStr.data.size() > 0)
        queryStr.data.foreach(qdp => countTyped.setParameter(qdp.name, qdp.value))
      records(countTyped.getSingleResult)
    }

    val typed = em.createQuery(ActionsForSwitchPatientByEventQuery.format("a", queryStr.query, sorting), classOf[Action])
      .setMaxResults(limit)
      .setFirstResult(limit * page)
    queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))

    val result = typed.getResultList

    result
  }


  def getActionsByTypeCodeAndEventId(codes: java.util.Set[String], eventId: Int, sort: String) = {
    val result = em.createQuery(ActionsByCodeAndEventQuery.format(sort),
      classOf[Action])
      .setParameter("codes", asJavaCollection(codes))
      .setParameter("id", eventId)
      .getResultList

    result.size match {
      case 0 => null
      case size => {

        result
      }
    }
  }

  def getActionsByTypeCodeAndPatientOrderByDate(codes: java.util.Set[String], patient: Patient) = {
    val result = em.createNamedQuery("Action.actionByTypeCodeAndPatientOrderByDate",
      classOf[Action])
      .setParameter("codes", asJavaCollection(codes))
      .setParameter("patientId", patient.getId)
      .getResultList

    result.size match {
      case 0 => null
      case size => {

        result
      }
    }
  }


  def getActionIdWithCopyByEventId(eventId: Int, actionTypeId: Int) = {
    /*
     Для первичного осмотра ищется последний осмотр заданного типа во всех предыдущих обращениях
     Для остальных осмотров ищется последний осмотр заданного типа в данном обращении
     Выполнено согласно "ТРЕБОВАНИЯМ К РАБОТЕ С МЕДИЦИНСКИМИ ДОКУМЕНТАМИ"
     */
    val typed = em.createQuery(ActionsIdFindQuery /*.format(subQuery)*/ , classOf[Int])
    val result = typed.setParameter("id", eventId)
      .setParameter("actionTypeId", actionTypeId)
      .getResultList

    result.size match {
      case 0 => 0
      case size => {
        result(0)
      }
    }
  }

  def getLastActionByActionTypeIdAndEventId(eventId: Int, actionTypeIds: java.util.Set[java.lang.Integer]) = {
    val result = em.createQuery(ActionsByATypeIdAndEventId, classOf[Int])
      .setParameter("id", eventId)
      .setParameter("atIds", asJavaCollection(actionTypeIds))
      .setMaxResults(1)
      .getResultList


    result.size match {
      case 0 => 0
      case size => {
        result(0)
      }
    }
  }

  //@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  def getEvent29AndAction19ForAction(action: Action) = {
    val typed = em.createQuery(GetEvent29AndAction19ForAction, classOf[Action])
      .setParameter("directionDate", action.getPlannedEndDate)

    val result = typed.getResultList
    result.size match {
      case 0 => null
      case size => {
        result(0)
      }
    }
  }

  //@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  def getActionForDateAndPacientInQueueType(beginDate: Long, pacientInQueueType: Int) = {
    val formatter = new SimpleDateFormat("yyyy-MM-dd")
    val strDate = formatter.format(new Date(beginDate))
    val typed = em.createQuery(GetActionForDateAndPacientInQueueType, classOf[Long])
      .setParameter("beginDate", strDate)
      .setParameter("pacientInQueueType", pacientInQueueType)

    typed.getSingleResult
  }

  //@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  def getActionForEventAndPatientInQueueType(eventId: Int, date: Long, patientInQueueType: Int) = {
    val formatter = new SimpleDateFormat("yyyy-MM-dd")
    val strDate = formatter.format(new Date(date))
    val typed = em.createQuery(GetActionForEventAndPacientInQueueType, classOf[Long])
      .setParameter("eventId", eventId)
      .setParameter("date", strDate)
      .setParameter("patientInQueueType", patientInQueueType)

    typed.getSingleResult
  }

  val GetActionForEventAndPacientInQueueType =
    """
  SELECT COUNT(a)
  FROM
    Action a
  WHERE
    a.event.id = :eventId
  AND
    a.pacientInQueueType = :patientInQueueType
  AND
    substring(a.plannedEndDate, 1, 10) = :date
  AND
    a.event.deleted = 0
  AND
    a.deleted = '0'
    """

  val GetActionForDateAndPacientInQueueType =
    """
  SELECT COUNT(a)
  FROM
    Action a
  WHERE
    substring(a.plannedEndDate, 1, 10) = :beginDate
  AND
    a.pacientInQueueType = :pacientInQueueType
  AND
    a.event.deleted = 0
  AND
    a.deleted = '0'
    """

  val GetEvent29AndAction19ForAction =
    """
  SELECT a
  FROM
    Action a
  WHERE
    a.event.eventType.id = '29'
  AND
    a.directionDate = :directionDate
  AND
    a.event.deleted = 0
  AND
    a.actionType.id = '19'
  AND
    a.deleted = '0'
    """

  val ActionsByATypeIdAndEventId =
    """
    SELECT a.id
    FROM
      Action a
      JOIN a.event e
      JOIN a.actionType at
    WHERE
      e.id = :id
    AND
      a.deleted = 0
    AND
      at.id IN :atIds
    ORDER BY a.createDatetime DESC
    """

  val ActionsIdFindQuery =
    """
    SELECT a.id
    FROM
      Action a
      JOIN a.event e
      JOIN a.actionType at
    WHERE
      at.id = :actionTypeId
    AND
      e.patient.id IN
        (SELECT DISTINCT e3.patient.id FROM Event e3 WHERE e3.id = :id)
    AND
      e.createDatetime < (SELECT e4.createDatetime FROM Event e4 WHERE e4.id = :id)
    AND
      e.deleted = 0
    AND
      a.deleted = 0
    ORDER BY a.createDatetime DESC
    """ //

  val ActionFindQuery =
    """
    SELECT a
    FROM
      Action a
    WHERE
      a.id = :id
    AND
      a.deleted = 0
    """

  val ActionByEventIdAndActionTypeQuery =
    """
    SELECT a
    FROM
      Action a
      JOIN a.event e
      JOIN a.actionType at
    WHERE
      e.id = :id
    AND
      at.id = :atId
    AND
      a.deleted = 0
    ORDER BY a.createDatetime DESC
    """

  val ActionByEventExternalIdQuery =
    """
    SELECT a
    FROM Action a
    WHERE
    exists (
      SELECT e
      FROM Event e
      WHERE e.externalId = :externalId
      AND e.deleted = 0
      AND e = a.event
    )
    AND a.deleted = 0
    """

  val ActionLastByTypeId =
    """
    SELECT a
    FROM
      Action a
    WHERE
      a.actionType = :actionType
    AND
      a.deleted = 0
    ORDER BY a.createDatetime
    """

  val ActionsForSwitchPatientByEventQuery =
    """
    SELECT %s
    FROM
      Action a left join a.createPerson.speciality s
    WHERE
      a.event.deleted = 0
    %s
    AND
      a.deleted = 0
    %s
    """

  val ActionsByCodeQuery =
    """
    SELECT a
    FROM
      Action a
    WHERE
      a.actionType.code = :code
    AND
      a.deleted = 0
    ORDER BY
      a.createDatetime
    """

  val ActionsByCodeAndEventQuery =
    """
    SELECT a
    FROM
      Action a
    WHERE
      a.actionType.code IN :codes
    AND
      a.event.id = :id
    AND
      a.deleted = 0
    ORDER BY
      %s
    """

  val ActionTypeByCodeQuery =
    """
    SELECT at
    FROM
      ActionType at
    WHERE
      at.code = :code
    AND
      at.deleted = 0
    """


  def createAction(queueActionType: ActionType, queueEvent: Event, doctor: Staff, paramsDateTime: Date, hospitalUidFrom: String, note: String): Action = {
    val queueActionParam = (new QueueActionParam).setHospitalUidFrom(hospitalUidFrom).setNote(note)
    createAction(queueActionType, queueEvent, doctor, paramsDateTime, queueActionParam)
  }

  def createAction(actionType: ActionType, event: Event, person: Staff, date: Date, queueActionParam: QueueActionParam): Action = {
    val now = new Date
    val newAction = new Action()
    //Инициализируем структуру Event
    try {
      newAction.setCreateDatetime(now)
      newAction.setCreatePerson(null)
      newAction.setModifyPerson(null)
      newAction.setActionType(actionType)
      //WEBMIS-375  При создании нового action в поле Action.amount необходимо записывать значение из поля ActionType.amount
      newAction.setAmount(actionType.getAmount)
      newAction.setModifyDatetime(now)
      newAction.setEvent(event)
      newAction.setNote(queueActionParam.getNote)
      newAction.setBegDate(date)
      newAction.setEndDate(now)
      newAction.setDirectionDate(date)
      newAction.setDeleted(false)
      newAction.setPayStatus(0)
      newAction.setExecutor(person)
      newAction.setPacientInQueueType(queueActionParam.getPacientInQueueType.getValue)
      newAction.setAppointmentType(queueActionParam.getAppointmentType)

      //не менять на person, иначе нельзя будет отличить запись на прием к врачу с портала и других ЛПУ
      newAction.setAssigner(null)

      newAction.setUuid(UUID.randomUUID())
      newAction.setHospitalUidFrom(queueActionParam.getHospitalUidFrom)
      //1. Инсертим
      em.persist(newAction)
    }
    catch {
      case ex: Exception => throw new CoreException("error while creating action ")
    }
    em.flush()
    newAction
  }

  def updateAction(action: Action): Action = {
    //em.persist(action)
    em.merge(action)
    getActionById(action.getId)
  }

  def getActionsByTypeCode(code: String) = {
    val result = em.createQuery(ActionsByCodeQuery,
      classOf[Action])
      .setParameter("code", code)
      .getResultList

    result
  }

  val ActionsByTypeFlatCodeAndEventQuery: String =
    """
     SELECT act
     FROM Action act
     WHERE act.event.id = :EVENTID
     AND act.actionType.flatCode = :FLATCODE
    """

  def getActionsByTypeFlatCodeAndEventId(eventId: Int, actionTypeFlatCode: String): util.List[Action] = {
    em.createQuery(ActionsByTypeFlatCodeAndEventQuery, classOf[Action])
      .setParameter("EVENTID", eventId)
      .setParameter("FLATCODE", actionTypeFlatCode)
      .getResultList
  }

  def getMovings(eventId: Int): util.List[Action] = {
    em.createQuery("SELECT a FROM Action a " +
      "WHERE a.deleted = 0 AND a.actionType.deleted = 0 AND a.actionType.flatCode = 'moving' AND a.status != 2 AND a.event.id = :eventId",
      classOf[Action])
      .setParameter("eventId", eventId).getResultList
  }

  def isOpenDoc(action: Action): Boolean = {
    action.getStatus == 0
  }

  def removeAction(actionId: Int, person: Staff) {
    val action: Action = getById(actionId)
    if (!isOpenDoc(action)) {
      throw new CoreException("Невозможно удалить закрытый документ")
    }
    action.setDeleted(true)
    action.setModifyDatetime(new Date)
    action.setModifyPerson(person)
    em.merge(action)
  }

  def closeAppealsDocs() {
    logger.info("Запущена задача закрытия документов в закрытых историях болезни")

    val defaultDaysValue = 2
    val docMnemonics = Seq("EXAM", "EPI", "JOUR", "ORD", "NOT", "OTH")

    val now = new DateTime()
    now.getDayOfWeek match {
      case DateTimeConstants.SUNDAY | DateTimeConstants.SATURDAY => {
        logger.info("Skip closing documents in closed events. It's weekend! Go party!")
        return // Не следует запускать проверку в выходные дни
      }
      case _ => {}
    }

    val closeIntervalInDays: Int = try {
      ConfigManager.Common.eventEditableDays
    } catch {
      case e: Throwable => {
        logger.warn("Cannot receive value of " + i18n("settings.path.eventBlockTime") + " setting. Set as 2 days.")
        defaultDaysValue
      }
    }

    var i = closeIntervalInDays
    var date = new DateTime()
    // Отнимаем от даты нужное количество дней, пропуская выходные
    while (i != 0) {
      date = date.minusDays(1)
      date.getDayOfWeek match {
        case d if d != DateTimeConstants.SUNDAY | d != DateTimeConstants.SATURDAY => i -= 1
      }
    }

    em.createQuery("SELECT a FROM Action a WHERE a.event.execDate < :date AND a.endDate IS NULL AND a.actionType.mnemonic IN :docMnemonics", classOf[Action])
      .setParameter("date", date.toDate)
      .setParameter("docMnemonics", asJavaCollection(docMnemonics))
      .getResultList

      // Закрываем документы датой закрытия истории болезни
      .foreach(a => {
      a setEndDate a.getEvent.getExecDate
      a setStatus ActionStatus.FINISHED.getCode
      a setModifyDatetime now.toDate
    })
    em.flush()

  }

  def getServiceList(eventId: Integer): util.List[Action] = {
    em.createNamedQuery("Action.ServiceList",
      classOf[Action])
      .setParameter("eventId", eventId)
      .getResultList
  }

  def getActionsByEvent(eventId: Integer): util.List[Action] = {
    em.createNamedQuery("Action.findByEventId",
      classOf[Action])
      .setParameter("eventId", eventId)
      .getResultList
  }

  def getActionsByTypeFlatCodeAndEventId(eventId: Integer, flatCodeList: util.List[String]): util.List[Action] = {
    em.createNamedQuery("Action.findByFlatCodesAndEventId", classOf[Action])
      .setParameter("eventId", eventId)
      .setParameter("flatCodes", flatCodeList)
      .getResultList
  }

  def getLatestMove(event: Event): Action = {
    val actions = em.createNamedQuery("Action.findLatestMove", classOf[Action])
      .setParameter("eventId", event.getId)
      .setMaxResults(1).getResultList
    return if (actions.isEmpty) null else actions.get(0);
  }

  override def getLastActionByEventAndActionTypes(eventId: Integer, flatCodeList: util.List[String]): Action = {
    val actions = em.createNamedQuery("Action.findLastByFlatCodesAndEventId", classOf[Action])
      .setParameter("eventId", eventId)
      .setParameter("flatCodes", flatCodeList)
      .setMaxResults(1)
      .getResultList
    if (actions.isEmpty) {
      null
    } else {
      actions.get(0)
    }
  }

  override def getLastActionByActionTypesAndClientId(codeList: util.List[String], clientId: Integer): Action = {
    val actions = em.createNamedQuery("Action.findLastByActionTypesAndClientId", classOf[Action])
      .setParameter("codes", codeList)
      .setParameter("clientId", clientId)
      .setMaxResults(1)
      .getResultList
    if (actions.isEmpty) {
      null
    } else {
      actions.get(0)
    }
  }


  def getAllActionsOfPatientThatHasActionProperty(patientId: Int, actionPropertyCode: String): util.List[Action] = {

    val r = em.createNamedQuery("Action.AllActionsOfPatientThatHasActionProperty", classOf[Action])
      .setParameter("patientId", patientId)
      .setParameter("code", actionPropertyCode)
      .getResultList

    r
  }

  override def getById(id: Int): Action = {
    em.find(classOf[Action], id)
  }

  override def getActionsByActionTypeMnemonicAndStatus(actionTypeMnemonic: String, status: ActionStatus): util.List[Action] = {
    val query =
      """
        |SELECT a
        |FROM Action a
        |INNER JOIN a.actionType aty
        |WHERE a.deleted = 0
        |AND a.status = :status
        |AND aty.mnemonic = :mnemonic
        |ORDER BY a.id DESC
      """.stripMargin
    em.createQuery(query, classOf[Action]).setParameter("status", status.getCode).setParameter("mnemonic", actionTypeMnemonic).getResultList
  }
}
