package ru.korus.tmis.core.database

import grizzled.slf4j.Logging
import ru.korus.tmis.util.{ConfigManager, I18nable}
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.entity.model.{Event, Staff, EventPerson}
import ru.korus.tmis.core.exception.CoreException
import scala.collection.JavaConversions._
import ru.korus.tmis.core.data.{QuotaTypesListRequestDataFilter, DictionaryListRequestDataFilter, QueryDataStructure}
import javax.interceptor.Interceptors
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.ejb.Stateless
import java.util.Date

/**
 * Класс с методами для работы с таблицей s11r64.Event_Persons
 * @author mmakankov
 * @since 1.0.0.55
 * @see DbEventPersonBeanLocal
 */
@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbEventPersonBean
  extends DbEventPersonBeanLocal
  with Logging
  with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def insertOrUpdateEventPerson(id: Int,
                                event: Event,
                                sessionUser: Staff): EventPerson = {
    var ep: EventPerson = null
    val now = new Date
    if (id > 0) {
      ep = getEventPersonById(id)
    }
    else {
      ep = new EventPerson
      ep.setPerson(sessionUser)
      if (event != null) {
        ep.setEvent(event)
        ep.setBegDate(event.getCreateDatetime)
      }
    }

    //ep.setDeleted(false)
    ep
  }

  def deleteEventPerson(id: Int,
                        sessionUser: Staff) = {
    val d = getClientRelationById(id)
    val now = new Date
    d.setDeleted(true)
    d.setModifyPerson(sessionUser)
    d.setModifyDatetime(now)
  }

  def getEventPersonById(id: Int) = {
    val query = em.createQuery(EventPersonFindQuery,
      classOf[EventPerson])
      .setParameter("id", id)

    val result = query.getResultList
    result.size match {
      case 0 => {
        throw new CoreException(
          ConfigManager.ErrorCodes.EventPersonNotFound,
          i18n("error.eventPersonNotFound").format(id))
      }
      case size => {
        result.foreach(rbType => {
          em.detach(rbType)
        })
        result(0)
      }
    }
  }

  val EventPersonFindQuery = """
    SELECT r
    FROM
      EventPerson r
    WHERE
      r.id = :id
                           """

  val AllEventPersonsWithFilterQuery = """
  SELECT %s
  FROM EventPerson r
  %s
  %s
                                     """
}