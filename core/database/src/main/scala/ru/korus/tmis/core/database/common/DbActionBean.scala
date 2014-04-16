package ru.korus.tmis.core.database.common

import ru.korus.tmis.core.auth.AuthData
import ru.korus.tmis.core.entity.model._
import ru.korus.tmis.core.exception.CoreException

import grizzled.slf4j.Logging
import java.util.Date
import javax.ejb.{EJB, Stateless}
import scala.collection.JavaConversions._
import javax.persistence.{TypedQuery, PersistenceContext, EntityManager}
import ru.korus.tmis.core.data.QueryDataStructure
import java.{util, lang}
import ru.korus.tmis.core.filter.ListDataFilter
import java.text.SimpleDateFormat
import ru.korus.tmis.schedule.QueueActionParam
import ru.korus.tmis.scala.util.{I18nable, ConfigManager}
import ru.korus.tmis.core.database.{DbSettingsBeanLocal, DbActionTypeBeanLocal}
import org.joda.time.{DateTimeConstants, DateTime}

//@Interceptors(Array(classOf[LoggingInterceptor]))
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
  private var dbUUIDBeanLocal: DbUUIDBeanLocal = _

  @EJB
  private var dbEventPerson: DbEventPersonBeanLocal = _

  @EJB
  private var dbSetting: DbSettingsBeanLocal = _

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
    em.detach(res)
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
          i18n("error.actionNotFound") + " id:[" + id + "]")
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
    val result = em.createQuery( """
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
        em.detach(action)
        action
      }
    }
  }

  def createAction(eventId: Int, actionTypeId: Int, userData: AuthData) = {
    val e = dbEvent.getEventById(eventId)
    val at = dbActionType.getActionTypeById(actionTypeId)

    val now = new Date
    val a = new Action

    if (userData != null) {
      a.setCreatePerson(userData.user)
      a.setCreateDatetime(now)
      a.setModifyPerson(userData.user)
      a.setModifyDatetime(now)

      var eventPerson: EventPerson = null
      if (userData.getUserRole.getCode.compareTo("admNurse") == 0 || userData.getUserRole.getCode.compareTo("strNurse") == 0) {
        eventPerson = dbEventPerson.getLastEventPersonForEventId(eventId)
        if (eventPerson != null) {
          a.setAssigner(eventPerson.getPerson)
        } else {
          a.setAssigner(userData.user)
        }
      }
      else
        a.setAssigner(userData.user)

      // Исправление дефолтного значения от 03.07.2013 по задаче WEBMIS-873
      a.setExecutor(userData.user) //a.setExecutor(at.getDefaultExecutor)

    }

    a.setCreateDatetime(now)
    a.setModifyDatetime(now)
    a.setBegDate(now)
    //a.setEndDate(now)

    a.setEvent(e)
    a.setActionType(at)

    a.setStatus(ActionStatus.STARTED.getCode)
    a.setUuid(dbUUIDBeanLocal.createUUID())

    a
  }

  def updateAction(id: Int, version: Int, userData: AuthData) = {
    val a = getActionById(id)
    val now = new Date

    if (userData != null) {
      a.setModifyPerson(userData.user)
      var eventPerson: EventPerson = null
      if (userData.getUserRole.getCode.compareTo("admNurse") == 0 || userData.getUserRole.getCode.compareTo("strNurse") == 0) {
        eventPerson = dbEventPerson.getLastEventPersonForEventId(a.getEvent.getId.intValue())
        if (eventPerson != null) a.setAssigner(eventPerson.getPerson) else a.setAssigner(userData.user)
      }
      //a.setExecutor(userData.user)
    }

    a.setModifyDatetime(now)
    a.setVersion(version)
    a.setBegDate(now)
    //a.setEndDate(now)

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
        result.foreach(a => em.detach(a))
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
        result.foreach(a => em.detach(a))
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

    result.foreach(a => em.detach(a))
    result.iterator().next()
  }

  def getActionsWithFilter(limit: Int,
                           page: Int,
                           sorting: String,
                           filter: ListDataFilter,
                           records: (java.lang.Long) => java.lang.Boolean,
                           userData: AuthData) = {

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
    result.foreach(a => em.detach(a))
    result
  }

  def getActionsByTypeCode(code: String) = {
    val result = em.createQuery(ActionsByCodeQuery,
      classOf[Action])
      .setParameter("code", code)
      .getResultList

    result.foreach(a => em.detach(a))
    result
  }

  def getActionsByTypeCodeAndEventId(codes: java.util.Set[String], eventId: Int, sort: String, userData: AuthData) = {
    val result = em.createQuery(ActionsByCodeAndEventQuery.format(sort),
      classOf[Action])
      .setParameter("codes", asJavaCollection(codes))
      .setParameter("id", eventId)
      .getResultList

    result.size match {
      case 0 => null
      case size => {
        result.foreach(a => em.detach(a))
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
    //val subQuery = // if(actionTypeId == i18n("db.actionType.primary").toInt || actionTypeId == i18n("db.actionType.secondary").toInt)
    //   "e.patient.id IN (SELECT DISTINCT e2.patient.id FROM Event e2 WHERE e2.id = :id)"
    //else //"e.id = :id"
    //        "e.createDatetime IN (SELECT DISTINCT MAX(e2.createDatetime) FROM Event e2 WHERE e2.patient.id IN" +
    //          "(SELECT DISTINCT e3.patient.id FROM Event e3 WHERE e3.id = :id) AND e2.deleted = 0 AND e2.createDatetime < " +
    //          "(SELECT DISTINCT e4.createDatetime FROM Event e4 WHERE e4.id = :id))"

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
    var typed = em.createQuery(GetEvent29AndAction19ForAction, classOf[Action])
      //.setParameter("externalId", action.getEvent.getExternalId)
      .setParameter("directionDate", action.getPlannedEndDate)

    val result = typed.getResultList
    result.size match {
      case 0 => null
      case size => {
        result.foreach(em.detach(_))
        result(0)
      }
    }
  }

  //@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  def getActionForDateAndPacientInQueueType(beginDate: Long, pacientInQueueType: Int) = {
    val formatter = new SimpleDateFormat("yyyy-MM-dd")
    val strDate = formatter.format(new Date(beginDate))
    var typed = em.createQuery(GetActionForDateAndPacientInQueueType, classOf[Long])
      .setParameter("beginDate", strDate)
      .setParameter("pacientInQueueType", pacientInQueueType)

    typed.getSingleResult
  }

  //@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  def getActionForEventAndPacientInQueueType(eventId: Int, date: Long, pacientInQueueType: Int) = {
    val formatter = new SimpleDateFormat("yyyy-MM-dd")
    val strDate = formatter.format(new Date(date))
    var typed = em.createQuery(GetActionForEventAndPacientInQueueType, classOf[Long])
      .setParameter("eventId", eventId)
      .setParameter("date", strDate)
      .setParameter("pacientInQueueType", pacientInQueueType)

    typed.getSingleResult
  }

  val GetActionForEventAndPacientInQueueType = """
  SELECT COUNT(a)
  FROM
    Action a
  WHERE
    a.event.id = :eventId
  AND
    a.pacientInQueueType = :pacientInQueueType
  AND
    substring(a.plannedEndDate, 1, 10) = :date
  AND
    a.event.deleted = 0
  AND
    a.deleted = '0'
                                               """

  val GetActionForDateAndPacientInQueueType = """
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

  val GetEvent29AndAction19ForAction = """
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

  val ActionsByATypeIdAndEventId = """
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

  /*val ActionsIdFindQuery = """
    SELECT a.id
    FROM
      Action a
      JOIN a.event e
      JOIN a.actionType at
    WHERE
      a.deleted = 0
    AND
      at.id = :actionTypeId
    AND
      e.patient.id IN (SELECT DISTINCT e2.patient.id FROM Event e2 WHERE e2.id = :id AND e2.deleted = 0)
    ORDER BY a.createDatetime DESC
                           """*/
  val ActionsIdFindQuery = """
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

  val ActionFindQuery = """
    SELECT a
    FROM
      Action a
    WHERE
      a.id = :id
    AND
      a.deleted = 0
                        """

  val ActionByEventIdAndActionTypeQuery = """
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

  val ActionByEventExternalIdQuery = """
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

  val ActionLastByTypeId = """
    SELECT a
    FROM
      Action a
    WHERE
      a.actionType = :actionType
    AND
      a.deleted = 0
    ORDER BY a.createDatetime
                           """

  val ActionsForSwitchPatientByEventQuery = """
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

  val ActionsByCodeQuery = """
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

  val ActionsByCodeAndEventQuery = """
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

  val ActionTypeByCodeQuery = """
    SELECT at
    FROM
      ActionType at
    WHERE
      at.code = :code
    AND
      at.deleted = 0
                              """

  def getActionTypeByCode(code: String): ActionType = {
    val result = em.createQuery(ActionTypeByCodeQuery, classOf[ActionType]).setParameter("code", code)
      .getResultList
    val et = result(0)
    result.foreach(em.detach(_))
    et
  }


  def createAction(queueActionType: ActionType, queueEvent: Event, doctor: Staff, paramsDateTime: Date, hospitalUidFrom: String, note: String): Action = {
    val queueActionParam = (new QueueActionParam).setHospitalUidFrom(hospitalUidFrom).setNote(note)
    createAction(queueActionType, queueEvent, doctor, paramsDateTime, queueActionParam)
  }

  def createAction(actionType: ActionType, event: Event, person: Staff, date: Date, queueActionParam: QueueActionParam): Action = {
    val now = new Date
    var newAction = new Action()
    //Инициализируем структуру Event
    try {
      newAction.setCreateDatetime(now)
      newAction.setCreatePerson(null)
      newAction.setModifyPerson(null)
      newAction.setActionType(actionType)
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

      newAction.setUuid(dbUUIDBeanLocal.createUUID)
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

  def getActionsByTypeCode(code: String, userData: AuthData) = {
    val result = em.createQuery(ActionsByCodeQuery,
      classOf[Action])
      .setParameter("code", code)
      .getResultList

    result.foreach(a => em.detach(a))
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

  def removeAction(actionId: Int): lang.Boolean = {
    val action: Action = this.getActionIdWithoutDetach(actionId)
    if (!isOpenDoc(action)) {
      return false
    }
    action.setDeleted(true)
    em.merge(action)
    true
  }

  def closeAppealsDocs() {
    logger.info("Запущена задача закрытия документов в закрытых историях болезни")

    val defaultDaysValue = 2
    val docMnemonics = Seq("EXAM", "EPI", "JOUR", "ORD", "NOT", "OTH")

    val now = new DateTime()
    now.getDayOfWeek match {
      case  DateTimeConstants.SUNDAY | DateTimeConstants.SATURDAY => {
        logger.info("Skip closing documents in closed events. It's weekend! Go party!")
        return // Не следует запускать проверку в выходные дни
      }
      case _ => {}
    }

    val closeIntervalInDays: Int = try {
      dbSetting.getSettingByPathInMainSettings(i18n("settings.path.eventBlockTime")).getValue.toInt
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
    .foreach( a => {
      a setEndDate a.getEvent.getExecDate
      a setStatus ActionStatus.FINISHED.getCode
      a setModifyDatetime now.toDate
    } )
    em.flush()

  }

  def getServiceList(eventId: Integer): util.List[Action] = {
    em.createNamedQuery("Action.ServiceList",
      classOf[Action])
      .setParameter("eventId", eventId)
      .getResultList
  }
}
