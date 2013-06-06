package ru.korus.tmis.core.database

import ru.korus.tmis.core.auth.AuthData
import ru.korus.tmis.core.entity.model._
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.core.logging.LoggingInterceptor
import ru.korus.tmis.util.{ConfigManager, I18nable}

import grizzled.slf4j.Logging
import java.util.Date
import javax.ejb.{TransactionAttributeType, TransactionAttribute, EJB, Stateless}
import javax.interceptor.Interceptors
import scala.collection.JavaConversions._
import javax.persistence.{TypedQuery, PersistenceContext, EntityManager}
import ru.korus.tmis.core.data.{QueryDataStructure, AssessmentsListRequestDataFilter, AssessmentsListRequestData}
import ru.korus.tmis.core.pharmacy.DbUUIDBeanLocal
import java.util
import ru.korus.tmis.core.filter.ListDataFilter

@Interceptors(Array(classOf[LoggingInterceptor]))
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
    em.createQuery(curentRequest.toString(), classOf[Long])
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  def getActionById(id: Int) = {
    info("Requested action id[" + id + "]")
    val result = em.createQuery(ActionFindQuery,
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

  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
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
      //a.setExecutor(userData.user)
      a.setExecutor(a.getActionType.getDefaultExecutor)
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

    val queryStr: QueryDataStructure = filter.toQueryStructure()

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
    val result = em.createQuery(ActionsIdFindQuery, classOf[Int])
      .setParameter("id", eventId)
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

  val ActionsIdFindQuery = """
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
                           """

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
      Action a
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

  def createAction(actionType: ActionType, event: Event, person: Staff, date: Date, hospitalUidFrom: String, note: String): Action = {
    val now = new Date
    var newAction = new Action()
    //Инициализируем структуру Event
    try {
      newAction.setCreateDatetime(now);
      newAction.setCreatePerson(null);
      newAction.setModifyPerson(null);
      newAction.setActionType(actionType);
      newAction.setModifyDatetime(now);
      newAction.setEvent(event);
      newAction.setNote(note);
      newAction.setBegDate(date);
      newAction.setEndDate(now);
      newAction.setDirectionDate(date)
      newAction.setDeleted(false);
      newAction.setPayStatus(0);
      newAction.setExecutor(person)
      newAction.setAssigner(person)
      newAction.setUuid(dbUUIDBeanLocal.createUUID());
      if (!hospitalUidFrom.isEmpty) {
        newAction.setHospitalUidFrom(hospitalUidFrom);
      }
      //1. Инсертим
      em.persist(newAction);
    }
    catch {
      case ex: Exception => throw new CoreException("error while creating action ");
    }
    newAction
  }

  def getActionsByTypeCode(code: String, userData: AuthData) = {
    val result = em.createQuery(ActionsByCodeQuery,
      classOf[Action])
      .setParameter("code", code)
      .getResultList

    result.foreach(a => em.detach(a))
    result
  }
}
