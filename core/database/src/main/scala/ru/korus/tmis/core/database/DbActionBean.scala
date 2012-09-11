package ru.korus.tmis.core.database

import ru.korus.tmis.core.auth.AuthData
import ru.korus.tmis.core.entity.model.{ActionStatus, Action}
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.core.logging.db.LoggingInterceptor
import ru.korus.tmis.util.{ConfigManager, I18nable}

import grizzled.slf4j.Logging
import java.util.Date
import javax.ejb.{TransactionAttributeType, TransactionAttribute, EJB, Stateless}
import javax.interceptor.Interceptors
import javax.persistence.{PersistenceContext, EntityManager}

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

  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  def getActionById(id: Int) = {
    info("Requested action id["+ id + "]")
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

    a.setCreatePerson(userData.user)
    a.setCreateDatetime(now)
    a.setModifyPerson(userData.user)
    a.setModifyDatetime(now)

    a.setAssigner(userData.user)
    a.setExecutor(userData.user)
    a.setBegDate(now)
    a.setEndDate(now)

    a.setEvent(e)
    a.setActionType(at)

    a.setStatus(ActionStatus.STARTED.getCode)

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

  val ActionFindQuery = """
    SELECT a
    FROM
      Action a
    WHERE
      a.id = :id
    AND
      a.deleted = 0
  """
}
