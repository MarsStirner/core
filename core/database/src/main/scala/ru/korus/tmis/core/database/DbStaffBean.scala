package ru.korus.tmis.core.database

import ru.korus.tmis.core.exception.{CoreException, NoSuchUserException}
import ru.korus.tmis.core.logging.LoggingInterceptor
import ru.korus.tmis.util.{I18nable, ConfigManager}

import grizzled.slf4j.Logging
import javax.ejb.Stateless
import javax.interceptor.Interceptors
import scala.collection.JavaConversions._
import java.util.Date
import java.text.{DateFormat, SimpleDateFormat}
import javax.persistence.{TypedQuery, EntityManager, PersistenceContext}
import ru.korus.tmis.core.entity.model.Staff
import ru.korus.tmis.core.data.{FreePersonsListDataFilter, QueryDataStructure}

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbStaffBean
  extends DbStaffBeanLocal
  with Logging
  with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

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


  def getAllPersonsByRequest(limit: Int, page: Int, sortField: String, sortMethod: String, filter: Object) = {

    val sorting: String = "ORDER BY s.%s %s".format(sortField, sortMethod)

    val queryStr: QueryDataStructure = if (filter.isInstanceOf[FreePersonsListDataFilter]) {
      filter.asInstanceOf[FreePersonsListDataFilter].toQueryStructure()
    }
    else {
      new QueryDataStructure()
    }

    var typed = em.createQuery(StaffsAndCountRecordsWithFilterQuery.format("s", queryStr.query, sorting), classOf[Staff])
    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }
    val result = typed.setMaxResults(limit)
      .setFirstResult(limit * page)
      .getResultList
    result
  }

  def getEmptyPersonsByRequest(limit: Int, page: Int, sortField: String, sortMethod: String, filter: Object) = {

    //TODO: как то надо подрубить пэйджинг, сортировки и общее кол-во
    val queryStr: QueryDataStructure = if (filter.isInstanceOf[FreePersonsListDataFilter]) {
      filter.asInstanceOf[FreePersonsListDataFilter].toQueryStructure()
    }
    else {
      new QueryDataStructure()
    }

    //   val sorting = "ORDER BY at.%s %s".format(sortingField, sortingMethod)
    //Получение всех врачей по графику
    val sqlRequest = AllEmptyStaffWithFilterQuery.format("s, apt.name, time.value", queryStr.query, /*sorting*/ "")
    var typed = em.createQuery(sqlRequest, classOf[Array[AnyRef]])

    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }

    val result = typed.getResultList
    if (result == null || result.size() == 0) {
      throw new CoreException(i18n("error.staffsNotFound"))
    }

    var retMap = new java.util.HashMap[Staff, (Date, Date)]
    result.foreach(f => {
      if (f(0).isInstanceOf[Staff]) {
        val staff = f(0).asInstanceOf[Staff]
        if (retMap.containsKey(staff) == false) {
          if (f(1).isInstanceOf[String] && f(2).isInstanceOf[Date]) {
            val dates = f(1).asInstanceOf[String] match {
              case "begTime" => {
                (f(2).asInstanceOf[Date], null: java.util.Date)
              }
              case "endTime" => {
                (null: java.util.Date, f(2).asInstanceOf[Date])
              }
            }
            retMap.put(staff, dates)
          }
        }
        else {
          var curStaff = retMap.get(staff)
          if (f(1).isInstanceOf[String] && f(2).isInstanceOf[Date]) {
            val dates = f(1).asInstanceOf[String] match {
              case "begTime" => {
                (f(2).asInstanceOf[Date], curStaff._2)
              }
              case "endTime" => {
                (curStaff._1, f(2).asInstanceOf[Date])
              }
            }
            retMap.remove(staff)
            retMap.put(staff, dates)
          }
        }
        em.detach(staff)
        staff
      }
    })
    var retList = new java.util.LinkedList[Staff]
    retMap.foreach(f => {
      if (f._2._1.getTime <= filter.asInstanceOf[FreePersonsListDataFilter].beginOnlyTime.getTime &&
        f._2._2.getTime >= filter.asInstanceOf[FreePersonsListDataFilter].endOnlyTime.getTime &&
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
    retList
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
    APValueTime time
  WHERE
    e.deleted = 0
  AND
    e.eventType.code = '0'
  %s
  AND
    s.consultancyQuota > 0
  AND
    s.deleted = 0
  AND
    a.actionType.code = "amb"
  AND
    a.deleted = 0
  AND
    time.id.id = ap.id
  AND
    (
      apt.name = 'begTime'
    OR
      apt.name = 'endTime'
    )
  %s
  GROUP BY s, apt.name
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
}
