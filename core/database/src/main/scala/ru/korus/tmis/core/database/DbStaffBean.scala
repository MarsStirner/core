package ru.korus.tmis.core.database

import ru.korus.tmis.core.exception.{CoreException, NoSuchUserException}
import ru.korus.tmis.core.logging.LoggingInterceptor

import grizzled.slf4j.Logging
import javax.ejb.Stateless
import javax.interceptor.Interceptors
import scala.collection.JavaConversions._
import java.util.Date
import javax.persistence.{Query, TemporalType, EntityManager, PersistenceContext}
import ru.korus.tmis.core.entity.model.{APValueTime, ActionProperty, Action, Staff}
import ru.korus.tmis.core.data.{FreePersonsListDataFilter, QueryDataStructure}
import ru.korus.tmis.core.filter.ListDataFilter
import org.slf4j.{LoggerFactory, Logger}
import java.util

import org.eclipse.persistence.jpa.JpaEntityManager
import org.eclipse.persistence.sessions.{DatabaseRecord, Session}
import org.eclipse.persistence.internal.jpa.EJBQueryImpl
import org.eclipse.persistence.queries.DatabaseQuery
import ru.korus.tmis.scala.util.{I18nable, ConfigManager}



@Stateless
class DbStaffBean
  extends DbStaffBeanLocal
  with Logging
  with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  private final val commlogger: Logger = LoggerFactory.getLogger("ru.korus.tmis.communication");

  def getStaffByLogin(login: String) = {
    val staffs = em.createNamedQuery("Staff.findByLogin", classOf[Staff])
      .setParameter("login", login)
      .getResultList
    if (staffs.size() == 0) {
      error("Staff not found: " + login)
      throw new NoSuchUserException(
        ConfigManager.TmisAuth.ErrorCodes.LoginIncorrect,
        login,
        i18n("error.staffNotFound"))
    }
    staffs.foreach(em.detach(_))
    staffs(0)
  }

  def getStaffById(id: Int) = {

    val result = em.createQuery(StaffByIdQuery, classOf[Staff])
      .setParameter("id", id)
      .getResultList

    result.foreach(em.detach(_))
    result.get(0)
  }

  def getStaffByIdWithoutDetach(id: Int) = {
    val result = em.createQuery(StaffByIdQuery, classOf[Staff])
      .setParameter("id", id)
      .getResultList
    result.get(0)
  }

  def getAllPersons = {
    em.createNamedQuery("Staff.findAll", classOf[Staff]).getResultList
  }

  def getCountAllPersonsWithFilter(filter: Object) = {

    val queryStr: QueryDataStructure = if (filter.isInstanceOf[FreePersonsListDataFilter]) {
      filter.asInstanceOf[FreePersonsListDataFilter].toQueryStructure()
    }
    else {
      new QueryDataStructure()
    }

    var typed = em.createQuery(StaffsAndCountRecordsWithFilterQuery.format("count(s)", queryStr.query, ""), classOf[Long])
    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }

    val result = typed.getSingleResult
    result
  }


  def getAllPersonsByRequest(limit: Int, page: Int, sorting: String, filter: ListDataFilter, records: (java.lang.Long) => java.lang.Boolean) = {

    val queryStr = filter.toQueryStructure()
    if (records != null) {
      val querytyped = em.createQuery(StaffsAndCountRecordsWithFilterQuery.format("count(s)", queryStr.query, ""), classOf[Long])
      if (queryStr.data.size() > 0)
        queryStr.data.foreach(qdp => querytyped.setParameter(qdp.name, qdp.value))
      records(querytyped.getSingleResult)
    }
    val typed = em.createQuery(StaffsAndCountRecordsWithFilterQuery.format("s", queryStr.query, sorting), classOf[Staff])
    if (queryStr.data.size() > 0)
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))

    val result = typed.setMaxResults(limit)
      .setFirstResult(limit * page)
      .getResultList
    result
  }

  def getEmptyPersonsByRequest(limit: Int, page: Int, sorting: String, filter: ListDataFilter, citoActionsCount: Int) = {

    //TODO: как то надо подрубить пэйджинг, сортировки и общее кол-во
    val queryStr = filter.toQueryStructure()

    //Получение всех врачей по графику
    val sqlRequest = AllEmptyStaffWithFilterQuery.format("s, time", queryStr.query, sorting)
    var typed = em.createQuery(sqlRequest, classOf[Array[AnyRef]]).setParameter("citoActionsCount", citoActionsCount)

    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }

    val result = typed.getResultList
    /*
    if (result != null || result.size() != 0) {
      throw new CoreException(i18n("error.staffsNotFound"))
    }                     */

    var retMap = new java.util.HashMap[Staff, java.util.LinkedList[APValueTime]]
    result.foreach(f => {
      if (f(0).isInstanceOf[Staff]) {
        val staff = f(0).asInstanceOf[Staff]
        if (retMap.containsKey(staff) == false) {
          if (f(1).isInstanceOf[APValueTime]) {
            val time = f(1).asInstanceOf[APValueTime]
            var timeList = new util.LinkedList[APValueTime]
            em.detach(time)
            timeList.add(time)
            retMap.put(staff, timeList)
          }
        }
        else {
          if (f(1).isInstanceOf[APValueTime]) {
            val time = (f(1).asInstanceOf[APValueTime])
            em.detach(time)
            //retMap.remove(staff)
            //retMap.put(staff, time)
            var curList = retMap.get(staff)
            curList.add(time)
          }
        }

        em.detach(staff)
        staff
      }
    })
    /*
   var retList = new java.util.LinkedList[Staff]
   retMap.foreach(f => {
     if (f._2.getTime <= filter.asInstanceOf[FreePersonsListDataFilter].beginOnlyTime.getTime &&
       f._2.getTime >= filter.asInstanceOf[FreePersonsListDataFilter].endOnlyTime.getTime &&
       filter.asInstanceOf[FreePersonsListDataFilter].beginOnlyTime.getTime < filter.asInstanceOf[FreePersonsListDataFilter].endOnlyTime.getTime) {
       retList.add(f._1)
     }
   })

   //Возвращаем врачей у кого консультации в это время уже запланированы
   val ids = retList.map((st) => st.getId.intValue)
   var result2 = em.createQuery(AllStaffWithoutCurrentConsultancyQuery, classOf[Staff])
     .setParameter("executorIds", asJavaCollection(ids))
     .setParameter("code", "4104")
     .setParameter("begDate", filter.asInstanceOf[FreePersonsListDataFilter].beginDate)
     .setParameter("endDate", filter.asInstanceOf[FreePersonsListDataFilter].endDate)
     .getResultList

   //Сопоставляем график и занятых врачей
   result2.foreach(s => {
     if (retList.contains(s)) {
       retList.remove(s)
     }
     em.detach(s)
   })
   */
    retMap
  }

  def getActionPropertyForPersonByRequest(filter: ListDataFilter) = {
    //TODO: как то надо подрубить пэйджинг, сортировки и общее кол-во
    val queryStr = filter.toQueryStructure()

    //Получение всех врачей по графику
    val sqlRequest = ActionPropertyForStaffWithFilterQuery.format(queryStr.query)
    var typed = em.createQuery(sqlRequest, classOf[ActionProperty])

    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }
    val result = typed.getResultList
    result.foreach(em.detach(_))
    if (result.size() > 0) {
      result.get(0)
    } else {
      null
    }
  }

  val StaffByIdQuery = """
  SELECT s
  FROM Staff s
  WHERE s.id = :id
                       """

  val AllEmptyStaffWithFilterQuery = """
  SELECT %s
  FROM
    ActionProperty ap
      JOIN ap.action a
      JOIN a.event e
      JOIN e.assigner s
      JOIN ap.actionPropertyType apt,
    APValueTime time,
    RbServiceProfile sProfile,
    ActionType at
  WHERE
    e.deleted = 0
  AND
    e.eventType.code = '0'
  %s

  AND
    s.deleted = 0
  AND
    a.actionType.code = 'amb'
  AND
    a.deleted = 0
  AND
    time.id.id = ap.id
  AND
    apt.name = 'times'
  AND
    (NOT exists (
      SELECT ap2
      FROM
        ActionProperty ap2,
        APValueAction apvAction
      WHERE
        ap2.action.id = a.id
      AND
        ap2.actionPropertyType.name = 'queue'
      AND
        apvAction.id.id = ap2.id
      AND
        apvAction.id.index - :citoActionsCount = time.id.index
      )
    OR
      NOT exists (
        SELECT ap3
        FROM
          ActionProperty ap3,
          APValueAction apvAction2
        WHERE
          ap3.action.id = a.id
        AND
          ap3.actionPropertyType.name = 'queue'
        AND
          apvAction2.id.id = ap3.id
        AND
          apvAction2.id.index - :citoActionsCount = time.id.index
        AND
          apvAction2.value is not null
        )
      )

  GROUP BY s.id, ap.id, time.value
                                     """

  val ActionPropertyForStaffWithFilterQuery = """
  SELECT ap
  FROM
    ActionProperty ap
      JOIN ap.action a
      JOIN a.event e
      JOIN e.assigner s
      JOIN ap.actionPropertyType apt,
    APValueTime time,
    RbServiceProfile sProfile,
    ActionType at
  WHERE
    e.deleted = 0
  AND
    e.eventType.code = '0'
  %s
  AND
    s.deleted = 0
  AND
    a.actionType.code = 'amb'
  AND
    a.deleted = 0
  AND
    time.id.id = ap.id
  AND
    apt.name = 'times'
                                              """

  val AllStaffWithoutCurrentConsultancyQuery = """
    SELECT DISTINCT s
    FROM
      Action a
      JOIN a.executor s
    WHERE
      s.id IN :executorIds
    AND
      a.actionType.code = :code
    AND
      (
        (a.begDate>=:begDate AND a.begDate<:endDate)
      OR
        (a.plannedEndDate>:begDate AND a.plannedEndDate<=:endDate)
      OR
        (a.begDate<=:begDate AND a.plannedEndDate>=:endDate)
      )
    AND
      s.deleted = '0'
    AND
      a.deleted = '0'
                                               """


  val StaffsAndCountRecordsWithFilterQuery = """
  SELECT %s
  FROM Staff s
  WHERE s.deleted = 0
  %s
  %s
                                             """

  val filtersPartForFreePersonsQuery = """
  AND
    s.id IN (
      SELECT
        ptt.masterPerson.id
      FROM
        PersonTimeTemplate ptt
      WHERE
        ptt.ambBegTime <= :beginDate
      AND
        ptt.ambEndTime >= :endDate
     AND
        ptt.deleted = 0
    )
  AND
    s.speciality.id = :speciality
  AND
    s.consultancyQuota > 0
                                       """

  /**
   * Получение действия(Action) по заданному типу, времени и владельцу
   * @param personId  Владелец действия
   * @param date      Дата на момент которой ищется действие
   * @param actionType    Тип искомого действия
   * @return  Найденое действие
   * @throws CoreException   Если действие не найдено
   */
  def getPersonActionsByDateAndType(personId: Int, date: Date, actionType: String): Action = {
    val query = em.createQuery(getPersonActionsByDateAndTypeQuery, classOf[Action]).setParameter("ACTIONTYPECODE", actionType)
      .setParameter("PERSONID", personId).setParameter("SETDATE", date, TemporalType.DATE)

    val resultList = query.getResultList
    if (resultList.size() == 1) {
      val timelineAccessibleDays = resultList.get(0).getEvent.getExecutor.getTimelineAccessibleDays
      val lastAccessibleDate = new java.util.GregorianCalendar()
      lastAccessibleDate.add(java.util.Calendar.DATE, timelineAccessibleDays)
      if (timelineAccessibleDays <= 0 || resultList.get(0).getEvent.getSetDate.before(lastAccessibleDate.getTime)) {
        //AND (Person.timelineAccessibleDays IS NULL OR Person.timelineAccessibleDays <= 0 OR DATE(Event.setDate)<=ADDDATE(CURRENT_DATE(), Person.timelineAccessibleDays))
        return resultList.get(0)
      }
    }
    throw new CoreException("Not found any actual actions");
  }


  val getPersonActionsByDateAndTypeQuery = """
    SELECT action
    FROM Action action
    LEFT JOIN action.actionType actiontype
    LEFT JOIN action.event event
    LEFT JOIN event.eventType eventtype
    LEFT JOIN event.executor  pers
    WHERE event.deleted=0
    AND action.deleted=0
    AND eventtype.code = '0'
    AND actiontype.code= :ACTIONTYPECODE
    AND pers.id = :PERSONID
    AND ( pers.lastAccessibleTimelineDate IS NULL OR pers.lastAccessibleTimelineDate = '0000-00-00' OR event.setDate <= pers.lastAccessibleTimelineDate )
    AND event.setDate = :SETDATE
                                           """

  val getDoctorByClientQueueActionQuery =
    """
     SELECT doctorEvent.executor
     FROM ActionProperty doctorAP, Action queueAction, APValueAction ap_a
     JOIN doctorAP.action doctorAction
     JOIN doctorAction.event doctorEvent
     WHERE doctorEvent.executor.deleted=0
     AND queueAction = :QUEUEACTION
     AND doctorEvent.deleted=0
     AND doctorAction.deleted=0
     AND doctorAP.deleted=0
     AND doctorAction.actionType.code = 'amb'
     AND doctorAP.actionPropertyType.name = 'queue'
     AND queueAction = ap_a.value
     AND ap_a.id.id = doctorAP.id
    """

  def getDoctorByClientAmbulatoryAction(queueAction: Action): Staff = {
    em.createQuery(getDoctorByClientQueueActionQuery, classOf[Staff])
      .setParameter("QUEUEACTION", queueAction)
      .getSingleResult
  }


  val getPersonSheduleQuery =
    """
      SELECT action
      FROM Action action
      INNER JOIN action.actionType actiontype
      INNER JOIN action.event event
      INNER JOIN event.eventType eventtype
      INNER JOIN event.executor  pers
      WHERE event.deleted=0
      AND action.deleted=0
      AND eventtype.code = '0'
      AND actiontype.code= 'amb'
      AND pers.id = :personId
      AND ( pers.lastAccessibleTimelineDate IS NULL OR pers.lastAccessibleTimelineDate = '0000-00-00' OR event.setDate <= pers.lastAccessibleTimelineDate )
      AND event.setDate BETWEEN :begDate AND :endDate
      ORDER BY event.setDate ASC
    """
  def getPersonShedule(personId: Int, begDate: Date, endDate: Date): util.List[Action] = {
    em.createQuery(getPersonSheduleQuery, classOf[Action])
      .setParameter("personId", personId)
      .setParameter("begDate", begDate, TemporalType.DATE)
      .setParameter("endDate", endDate, TemporalType.DATE)
    .getResultList
  }


  def getCoreUser: Staff = {
    val coreLogin: String = ConfigManager.UsersMgr.CoreUserLogin
    if (coreLogin != null) {
      val coreUsers = em.createNamedQuery("Staff.findByLogin", classOf[Staff])
        .setParameter("login", coreLogin)
        .getResultList
      return if (coreUsers.isEmpty) null else coreUsers.get(0)
    }
    return null
  }
}


