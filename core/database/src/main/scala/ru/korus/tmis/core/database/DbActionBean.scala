package ru.korus.tmis.core.database

import ru.korus.tmis.core.auth.AuthData
import ru.korus.tmis.core.entity.model.{ActionStatus, Action}
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.core.logging.LoggingInterceptor
import ru.korus.tmis.util.{ConfigManager, I18nable}

import grizzled.slf4j.Logging
import java.util.Date
import javax.ejb.{TransactionAttributeType, TransactionAttribute, EJB, Stateless}
import javax.interceptor.Interceptors
import ru.korus.tmis.core.entity.model.{ActionType, ActionStatus, Action, Staff}
import scala.collection.JavaConversions._
import javax.persistence.{TypedQuery, PersistenceContext, EntityManager}
import ru.korus.tmis.core.data.{AssessmentsListRequestDataFilter, AssessmentsListRequestData}
import ru.korus.tmis.core.hl7db.DbUUIDBeanLocal

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

  def createAction(eventId: Int, actionTypeId: Int, userData: AuthData) = {
    val e = dbEvent.getEventById(eventId)
    val at = dbActionType.getActionTypeById(actionTypeId)

    val now = new Date

    val a = new Action

    //TODO: временнр подсовываю пустой Staff когда нет AuthData
    if (userData != null) {
      a.setCreatePerson(userData.user)
      a.setCreateDatetime(now)
      a.setModifyPerson(userData.user)
      a.setModifyDatetime(now)

      a.setAssigner(userData.user)
      a.setExecutor(userData.user)
    }
    else {
      //var staff = new Staff
      //a.setCreatePerson(staff)
      // a.setModifyPerson(staff)
      // a.setAssigner(staff)
      //a.setExecutor(staff)
    }

    a.setCreateDatetime(now)
    a.setModifyDatetime(now)
    a.setBegDate(now)
    a.setEndDate(now)

    a.setEvent(e)
    a.setActionType(at)

    a.setStatus(ActionStatus.STARTED.getCode)
    a.setUuid(dbUUIDBeanLocal.createUUID())

    a
  }

  def updateAction(id: Int, version: Int, userData: AuthData) = {
    val a = getActionById(id)

    val now = new Date

    a.setModifyPerson(userData.user)
    a.setModifyDatetime(now)
    a.setVersion(version)

    a.setAssigner(userData.user)
    a.setExecutor(userData.user)
    a.setBegDate(now)
    a.setEndDate(now)

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

  def getActionsByEventIdWithFilter(eventId: Int, userData: AuthData, requestData: AssessmentsListRequestData) = {

    val queryStr = requestData.filter.asInstanceOf[AssessmentsListRequestDataFilter].toQueryStructure()

    var typed = getCountRecordsOrPagesQuery(ActionsForSwitchPatientByEventQuery, queryStr.query)
      .setParameter("eventId", eventId)

    val sorting = "ORDER BY %s %s".format(requestData.sortingFieldInternal, requestData.sortingMethod)
    var typed2 = em.createQuery(ActionsForSwitchPatientByEventQuery.format("a", queryStr.query, sorting), classOf[Action])
      .setMaxResults(requestData.limit)
      .setFirstResult(requestData.limit * (requestData.page - 1))
      .setParameter("eventId", eventId)

    queryStr.data.foreach(qdp => {
      //проставляем динамическую фильтрацию
      typed.setParameter(qdp.name, qdp.value)
      typed2.setParameter(qdp.name, qdp.value)
    })
    requestData.setRecordsCount(typed.getSingleResult)
    val result = typed2.getResultList

    result.foreach(a => em.detach(a))
    result
  }

  def getActionsByTypeCode(code: String, userData: AuthData) = {
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
      .setParameter("atIds", asJavaCollection(actionTypeIds) )
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
      a.event.id = :eventId
    AND
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
}
