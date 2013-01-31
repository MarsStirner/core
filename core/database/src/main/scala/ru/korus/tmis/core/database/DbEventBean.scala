package ru.korus.tmis.core.database

import ru.korus.tmis.core.logging.LoggingInterceptor
import ru.korus.tmis.util.I18nable

import grizzled.slf4j.Logging
import java.util.{Calendar, Date}
import javax.interceptor.Interceptors

import scala.collection.JavaConversions._
import javax.ejb.{EJB, TransactionAttributeType, TransactionAttribute, Stateless}
import ru.korus.tmis.core.auth.AuthData
import scala._
import javax.persistence.{TypedQuery, EntityManager, PersistenceContext}
import ru.korus.tmis.core.entity.model._
import ru.korus.tmis.core.data._
import ru.korus.tmis.core.exception.CoreException
import scala.Some
import ru.korus.tmis.core.hl7db.DbUUIDBeanLocal

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbEventBean
  extends DbEventBeanLocal
  with I18nable
  with Logging {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  @EJB
  var patientBean: DbPatientBeanLocal = _

  @EJB
  var rbCounterBean: DbRbCounterBeanLocal = _

  @EJB
  private var actionTypeBean: DbActionTypeBeanLocal = _

  @EJB
  private var dbUUIDBeanLocal: DbUUIDBeanLocal = _

  def getCountRecordsOrPagesQuery(enterPosition: String): TypedQuery[Long] = {

    val cntMacroStr = "count(e)"
    val sortField = ""
    val sortMethod = ""
    //выберем нужный запрос

    var curentRequest = enterPosition.format(cntMacroStr, sortField, sortMethod)

    //уберем из запроса фильтрацию
    val index = curentRequest.indexOf("ORDER BY")
    if (index > 0) {
      curentRequest = curentRequest.substring(0, index)
    }
    em.createQuery(curentRequest.toString(), classOf[Long])
  }

  @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  def getEventById(id: Int) = {
    val result = em.createQuery(EventByIdQuery,
      classOf[Event])
      .setParameter("id", id)
      .getResultList

    result.size() match {
      case 0 => {
        null //ексепшн нужен
      }
      case size => {
        val e = result.get(0)
        em.detach(e)
        e
      }
    }
  }

  def getActionTypeFilter(eventId: Int) = {
    val result = em.createQuery(ActionTypeFilterByEventIdQuery,
      classOf[ActionType])
      .setParameter("id", eventId)
      .getResultList

    result.foreach(em.detach(_))
    new java.util.HashSet(result)
  }

  def getOrgStructureForEvent(eventId: Int) = {
    val result = em.createQuery(AllOrgStructuresForEventQuery,
      classOf[Array[AnyRef]])
      .setParameter("eid", eventId)
      .getResultList
      .sortBy {
      case (Array(org, date)) => date.asInstanceOf[Date].getTime
    }
      .lastOption.map {
      _.head
    }
      .asInstanceOf[Option[OrgStructure]]

    result match {
      case Some(org) => em detach org; org
      case None => throw new CoreException("OrgStructure for Event id = " + eventId + " not found")
    }
  }

  //@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  def createEvent(patientId: Int, appealTypeId: Int, begDate: Date, endDate: Date, authData: AuthData): Event = {

    val patient = patientBean.getPatientById(patientId)
    val eventType = this.getEventTypeById(appealTypeId)

    //Анализ и инкремент счетчика
    val rbCounter = rbCounterBean.getRbCounterById(eventType.getCounterId.intValue())
    val result = em.merge(rbCounterBean.setRbCounterValue(rbCounter, rbCounter.getValue.intValue() + 1))

    //берем коунтер и получаем НИБ
    val externalId = Calendar.getInstance()
      .get(Calendar.YEAR).toString
      .concat(rbCounter.getSeparator)
      .concat(rbCounter.getValue.toString)

    val now = new Date
    var newEvent = new Event
    //1. Инсертим /Инициализируем структуру Event по пациенту
    try {
      newEvent.setPatient(patient)
      newEvent.setEventType(eventType)
      newEvent.setCreateDatetime(now)
      newEvent.setModifyDatetime(now)
      newEvent.setExternalId(externalId) //НИБ
      newEvent.setCreatePerson(authData.user)
      newEvent.setModifyPerson(authData.user)
      newEvent.setExecutor(authData.user)
      newEvent.setAssigner(authData.user)
      newEvent.setNote(" ")
      newEvent.setSetDate(begDate)
      newEvent.setUuid(dbUUIDBeanLocal.createUUID())
      //newEvent.setExecDate(endDate)
    }
    catch {
      case ex: Exception => {
      }
      em.refresh(newEvent)
    }
    return newEvent
  }

  /**
   * Создает и записывает в БД новый event с указанными значениями и   (CreatePerson & ModifyPerson = null )
   * @param patient Пациент для которого создается Event
   * @param eventType Тип события
   * @param person Врач, которому назначено событие                  
   * @param begDate Время начала события
   * @param endDate Время окончания события
   * @return Новый экземпляр события, сохраненный в БД и не открепленный (without  em.detach())
   */
  def createEvent(patient: Patient, eventType: EventType, person: Staff, begDate: Date, endDate: Date): Event = {
    val now = new Date
    var newEvent = new Event
    //Инициализируем структуру Event
    try {
      newEvent.setIsPrimary(1);
      newEvent.setCreateDatetime(now);
      newEvent.setCreatePerson(null);
      newEvent.setModifyPerson(null);
      newEvent.setEventType(eventType);
      newEvent.setOrgId(0);
      newEvent.setPatient(patient);
      newEvent.setSetDate(begDate);
      newEvent.setExternalId("");
      newEvent.setModifyDatetime(now);
      newEvent.setNote("");
      newEvent.setOrder(0);
      newEvent.setDeleted(false);
      newEvent.setPayStatus(0);
      newEvent.setExecutor(person)
      newEvent.setAssigner(person)
      newEvent.setUuid(dbUUIDBeanLocal.createUUID());
      //1. Инсертим
      em.persist(newEvent);
    }
    catch {
      case ex: Exception => throw new CoreException("error while creating event ");
    }
    newEvent
  }

  def getEventTypeById(eventTypeId: Int): EventType = {

    val result = em.createQuery(EventTypeByIdQuery, classOf[EventType])
      .setParameter("id", eventTypeId)
      .getResultList

    val et = result(0)
    result.foreach(em.detach(_))
    et
  }

  def getEventsForPatient(patientId: Int) = {
    //Получаем пациента
    var patient = patientBean.getPatientById(patientId)

    val result = em.createQuery(EventByPatientQuery, classOf[Event])
      .setParameter("patient", patient)
      .getResultList
    result.foreach(em.detach(_))
    result
  }

  def getEventsForPatientWithExistsActionByType(patientId: Int, code: String) = {

    val patient = patientBean.getPatientById(patientId)
    val actionType = actionTypeBean.getActionTypeByCode(code)

    val result = em.createQuery(EventByPatientWithExistsActionByTypeQuery, classOf[Event])
      .setParameter("patient", patient)
      .setParameter("actionType", actionType)
      .getResultList
    result.foreach(em.detach(_))
    result
  }

  def getEventTypeIdByFDRecordId(fdRecordId: Int) = {

    val result = em.createQuery(EventTypeIdByFDRecordIdQuery.format(fdRecordId), classOf[Int]).getSingleResult
    result
  }

  def getEventTypesByRequestTypeIdAndFinanceId(page: Int, limit: Int, sortingField: String, sortingMethod: String, filter: Object, records: (java.lang.Long) => java.lang.Boolean) = {
    val queryStr: QueryDataStructure = if (filter.isInstanceOf[EventTypesListRequestDataFilter])
      filter.asInstanceOf[EventTypesListRequestDataFilter].toQueryStructure()
    else new QueryDataStructure()

    val sorting = "ORDER BY %s %s".format(sortingField, sortingMethod)

    if (records != null) {
      //Перепишем количество записей для структуры
      val recC = em.createQuery(EventTypeIdByRequestTypeIdAndFinanceIdQuery.format("count(et)", queryStr.query, ""), classOf[Long])
      if (queryStr.data.size() > 0) queryStr.data.foreach(qdp => recC.setParameter(qdp.name, qdp.value))
      records(recC.getSingleResult)
    }

    var typed = em.createQuery(EventTypeIdByRequestTypeIdAndFinanceIdQuery.format("et", queryStr.query, sorting), classOf[EventType])
      .setMaxResults(limit)
      .setFirstResult(limit * page)
    if (queryStr.data.size() > 0) queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))

    val result = typed.getResultList
    result.foreach(em.detach(_))
    result
  }

  val EventGetCountRecords = """
  SELECT count(e)
  FROM
    Event e
  WHERE
    %s
                             """

  val EventFindActiveByBirthDateAndFullNameQuery = """
  SELECT %s
    FROM
  Event e
    WHERE
  exists (
    SELECT a
      FROM
      Action a
      WHERE
      a.event = e
      AND
      a.actionType = :actionType
  AND
    upper(e.patient.lastName) LIKE upper(:fullName)
  AND
    e.patient.birthDate = :birthDate
  AND
    a.deleted = 0)
  AND
    e.createDatetime BETWEEN :beginDate AND :endDate
  AND
    e.deleted = 0
  ORDER BY e.%s %s
                                                   """

  val EventFindActiveByBirthDateQuery = """
  SELECT %s
    FROM
  Event e
    WHERE
  exists (
    SELECT a
      FROM
      Action a
      WHERE
      a.event = e
      AND
      a.actionType = :actionType
  AND
    e.patient.birthDate = :birthDate
  AND
    a.deleted = 0)
  AND
    e.createDatetime BETWEEN :beginDate AND :endDate
  AND
    e.deleted = 0
  ORDER BY e.%s %s
                                        """

  val EventFindActiveByFullNameQuery = """
  SELECT %s
    FROM
  Event e
    WHERE
  exists (
    SELECT a
      FROM
      Action a
      WHERE
      a.event = e
      AND
      a.actionType = :actionType
  AND
    upper(e.patient.lastName) LIKE upper(:fullName)
  AND
    a.deleted = 0)
  AND
    e.createDatetime BETWEEN :beginDate AND :endDate
  AND
    e.deleted = 0
  ORDER BY e.%s %s
                                       """


  val EventFindActiveByExternalIdQuery = """
  SELECT %s
    FROM
  Event e
    WHERE
  exists (
    SELECT a
      FROM
      Action a
      WHERE
      a.event = e
      AND
      a.actionType = :actionType
  AND
    e.externalId = :externalId
  AND
    a.deleted = 0)
  AND
    e.createDatetime BETWEEN :beginDate AND :endDate
  AND
    e.deleted = 0
  ORDER BY e.%s %s
                                         """

  val EventFindActiveByEventIdQuery = """
  SELECT %s
  FROM
    Event e
  WHERE
    exists (
      SELECT a
      FROM
        Action a
      WHERE
        a.event = e
      AND
        a.actionType = :actionType
      AND
        e.id = :eventId
      AND
        a.deleted = 0)
  AND
    e.createDatetime BETWEEN :beginDate AND :endDate
  AND
    e.deleted = 0
  ORDER BY e.%s %s
                                      """

  val AllEventsBetweenDate = """
  SELECT %s
  FROM
    Event e
  WHERE
    exists (
      SELECT a
      FROM
        Action a
      WHERE
        a.event = e
      AND
        a.actionType = :actionType
      AND
        a.deleted = 0)
  AND
      e.createDatetime BETWEEN :beginDate AND :endDate
  AND
      e.deleted = 0
  ORDER BY e.%s %s
                             """

  val AllOrgStructuresForEventQuery = """
    SELECT org, a.begDate
    FROM
      Action a,
      ActionProperty ap,
      APValueOrgStructure apvos,
      OrgStructure org
    WHERE
      a.event.id = :eid AND
      a.id = ap.action.id AND
      ap.id = apvos.id.id AND
      apvos.value.id = org.id
    AND
      a.actionType.flatCode = '%s'
    AND
      ap.actionPropertyType.name = '%s'
    AND
      ap.deleted = 0 AND
      a.deleted = 0
                                      """.format(i18n("db.action.movingFlatCode"), i18n("db.apt.departmentName"))


  val ActionTypeFilterByEventIdQuery = """
    SELECT at
    FROM
      EventTypeAction eta JOIN eta.actionType at,
      Event e
    WHERE
      e.id = :id
    AND
      eta.eventType = e.eventType
    AND
      at.deleted = 0
                                       """

  val EventByIdQuery = """
    SELECT e
    FROM
      Event e
    WHERE
      e.id = :id
    AND
      e.deleted = 0
                       """

  val EventTypeByIdQuery = """
    SELECT et
    FROM
      EventType et
    WHERE
      et.id = :id
    AND
      et.deleted = 0
                           """
  val EventTypeByCodeQuery = """
    SELECT et
    FROM
      EventType et
    WHERE
      et.code = :code
    AND
      et.deleted = 0
                             """

  val RbCounterByEventTypeCntQuery = """
    SELECT
      rbc.id
    FROM
      rbCounter rbc
    WHERE
      rbc.id = :id
                                     """

  val EventByPatientQuery = """
    SELECT e
    FROM
      Event e
    WHERE
      e.patient = :patient
    AND
      e.deleted = 0
                            """

  val EventByPatientWithExistsActionByTypeQuery = """
    SELECT e
    FROM
      Event e
    WHERE
      e.patient = :patient
    AND
      e.deleted = 0
    AND
      exists (
        SELECT a
        FROM
          Action a
        WHERE
          a.event = e
        AND
          a.actionType = :actionType
        AND
          a.deleted = 0)
                                                  """
  val EventTypeIdByFDRecordIdQuery = """
  SELECT Max(et.id)
  FROM
    EventType et,
    FDFieldValue fdfv
  WHERE
    fdfv.pk.fdRecord.id = '%s'
  AND
    et.code = fdfv.value
                                     """

  val EventTypeIdByRequestTypeIdAndFinanceIdQuery =
    """
    SELECT %s
    FROM
      EventType et
    WHERE
      et.deleted = '0'
      %s
      %s
    """

  def getEventTypeByCode(code: String): EventType = {
    val result = em.createQuery(EventTypeByCodeQuery, classOf[EventType])
      .setParameter("code", code)
      .getResultList
    val et = result(0)
    result.foreach(em.detach(_))
    et
  }
}
