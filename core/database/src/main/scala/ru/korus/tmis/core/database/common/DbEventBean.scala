package ru.korus.tmis.core.database.common


import java.util
import java.util.{Calendar, Date, UUID}
import javax.ejb.{EJB, Stateless}
import javax.persistence.{EntityManager, PersistenceContext, TypedQuery}

import com.google.common.collect.{HashMultimap, Multimap}
import ru.korus.tmis.core.data._
import ru.korus.tmis.core.database._
import ru.korus.tmis.core.entity.model.{ActionType, Event, OrgStructure, _}
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.scala.util.I18nable

import scala.collection.JavaConversions._
import scala.language.reflectiveCalls

//
@Stateless
class DbEventBean
  extends DbEventBeanLocal
  with I18nable{

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  @EJB
  var patientBean: DbPatientBeanLocal = _

  @EJB
  var rbCounterBean: DbRbCounterBeanLocal = _

  @EJB
  private var actionTypeBean: DbActionTypeBeanLocal = _

  @EJB
  private var contractBean: DbContractBeanLocal = _

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
    em.createQuery(curentRequest.toString, classOf[Long])
  }


  def setExecPersonForEventWithId(eventId: Int, execPerson: Staff) {
    val event = this.getEventById(eventId)
    event.setExecutor(execPerson)
    em.merge(event)
    em.flush()
  }

  def getEventById(id: Int): Event = {
    em.find(classOf[Event], id)
  }

  def getActionTypeFilter(eventId: Int): util.HashSet[ActionType] = {
    val result = em.createQuery(ActionTypeFilterByEventIdQuery,
      classOf[ActionType])
      .setParameter("id", eventId)
      .getResultList

    new java.util.HashSet(result)
  }

  override def getOrgStructureForEvent(eventId: Int): OrgStructure = {
    Option(getEventById(eventId)) match {
      case Some(x) => getOrgStructureForEvent(x)
      case None => null
    }
  }

  def getOrgStructureForEvent(event: Event): OrgStructure = {
    if(isStationaryEvent(event)) {
      // Если обращение стационарное - то ищем в движениях (если нет в движениях, то в поступлении)
      val result = em.createQuery(AllOrgStructuresForEventQuery, classOf[OrgStructure])
        .setParameter("eventId", event.getId)
        .setParameter("actionTypeFlatCode", i18n("db.action.movingFlatCode"))
        .setParameter("actionPropertyTypeCode", i18n("db.apt.moving.codes.hospOrgStruct"))
        .getResultList.headOption
      result match {
        case Some(org) => org
        case None => getOrgStructureFromReceivedActionInEvent(event)
      }
    } else {
      //Если обращение амбулаторное - то из поля в обращении
       event.getOrgStructure
    }
  }


  def getOrgStructureFromReceivedActionInEvent(event: Event): OrgStructure = {
    val result = em.createQuery(AllOrgStructuresForEventQuery, classOf[OrgStructure])
      .setParameter("eventId", event.getId)
      .setParameter("actionTypeFlatCode", i18n("db.action.admissionFlatCode"))
      .setParameter("actionPropertyTypeCode", i18n("db.apt.admission.codes.hospOrgStruct"))
      .getResultList.headOption
    result match {
      case Some(org) => org
      case None => null
    }
  }

  //@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  def createEvent(patientId: Int, appealTypeId: Int, begDate: Date, endDate: Date, contractId: Int, result: RbResult, acheResult: RbAcheResult, execPerson: Staff, createPerson: Staff): Event = {

    val newEvent = new Event
    val patient = patientBean.getPatientById(patientId)
    val eventType = this.getEventTypeById(appealTypeId)

    val now = new Date
    val contract: Contract = contractBean.getContractById(contractId)
    //1. Инсертим /Инициализируем структуру Event по пациенту
    try {
      //Анализ и инкремент счетчика
      val externalId = if (eventType.getCounterId != null) {
        val rbCounter = rbCounterBean.getRbCounterById(eventType.getCounterId.intValue())
        val result = em.merge(rbCounterBean.setRbCounterValue(rbCounter, rbCounter.getValue.intValue() + 1))
        //берем коунтер и получаем НИБ
        val externalId = Calendar.getInstance()
          .get(Calendar.YEAR).toString
          .concat(rbCounter.getSeparator)
          .concat(rbCounter.getValue.toString)
        externalId
      } else {
        ""
      }

      newEvent.setExternalId(if (externalId == null) "" else externalId) //НИБ
      newEvent.setPatient(patient)
      newEvent.setEventType(eventType)
      newEvent.setCreateDatetime(now)
      newEvent.setModifyDatetime(now)
      newEvent.setCreatePerson(createPerson)
      newEvent.setModifyPerson(createPerson)
      newEvent.setExecutor(createPerson)
      newEvent.setAssigner(createPerson)
      newEvent.setNote(" ")
      newEvent.setSetDate(begDate)
      newEvent.setIsPrimary(1)
      newEvent.setOrder(0)
      newEvent.setDeleted(false)
      newEvent.setPayStatus(0)
      //val contract = contractBean.getContractForEventType(eventType)
      newEvent.setContract(contract)
      newEvent.setUuid(UUID.randomUUID())
      newEvent.setResult(result)
      newEvent.setAcheResult(acheResult)
      newEvent.setExecutor(execPerson)
      //newEvent.setExecDate(endDate)
    }
    catch {
      case ex: Exception =>  throw new CoreException("error while creating event ");
    }
    newEvent
  }

  /**
   * Создает и записывает в БД новый event с указанными значениями и   (CreatePerson & ModifyPerson = null )
   * @param patient Пациент для которого создается Event
   * @param eventType Тип события
   * @param person Врач, которому назначено событие                  
   * @param begDate Время начала события
   * @param endDate Время окончания события
   * @return Новый экземпляр события, сохраненный в БД и не открепленный (without detach)
   */
  def createEvent(patient: Patient, eventType: EventType, person: Staff, begDate: Date, endDate: Date): Event = {
    val now = new Date
    val newEvent = new Event
    //Инициализируем структуру Event
    try {
      newEvent.setIsPrimary(1)
      newEvent.setCreateDatetime(now)
      newEvent.setCreatePerson(null)
      newEvent.setModifyPerson(null)
      newEvent.setEventType(eventType)
      newEvent.setPatient(patient)
      newEvent.setSetDate(begDate)
      newEvent.setExternalId("")
      newEvent.setModifyDatetime(now)
      newEvent.setNote("")
      newEvent.setOrder(0)
      newEvent.setDeleted(false)
      newEvent.setPayStatus(0)
      newEvent.setExecutor(person)
      newEvent.setAssigner(person)
      newEvent.setUuid(UUID.randomUUID())
      //1. Инсертим
      em.persist(newEvent);
    }
    catch {
      case ex: Exception => throw new CoreException("error while creating event ");
    }
    em.flush()
    newEvent
  }

  def getEventTypeById(eventTypeId: Int): EventType = {

    val result = em.createQuery(EventTypeByIdQuery, classOf[EventType])
      .setParameter("id", eventTypeId)
      .getResultList

    val et = result(0)
    et
  }

  def getEventsForPatient(patientId: Int): util.List[Event] = {
    //Получаем пациента
    var patient = patientBean.getPatientById(patientId)

    val result = em.createQuery(EventByPatientQuery, classOf[Event])
      .setParameter("patient", patient)
      .getResultList
    result
  }

  def getEventsForPatientWithExistsActionByType(patientId: Int, code: String): util.List[Event] = {

    val patient = patientBean.getPatientById(patientId)
    val actionType = actionTypeBean.getActionTypeByCode(code)

    val result = em.createQuery(EventByPatientWithExistsActionByTypeQuery, classOf[Event])
      .setParameter("patient", patient)
      .setParameter("actionType", actionType)
      .getResultList
    result
  }

  def getEventTypeIdByFDRecordId(fdRecordId: Int): Int = {

    val result = em.createQuery(EventTypeIdByFDRecordIdQuery.format(fdRecordId), classOf[Int]).getSingleResult
    result
  }

  def getEventTypesByRequestTypeIdAndFinanceId(page: Int, limit: Int, sortingField: String, sortingMethod: String, filter: Object, records: (java.lang.Long) => java.lang.Boolean): util.List[EventType] = {
    val queryStr: QueryDataStructure = filter match {
      case filter1: EventTypesListRequestDataFilter => filter1.toQueryStructure()
      case _ => new QueryDataStructure()
    }

    val sorting = "ORDER BY %s %s".format(sortingField, sortingMethod)

    if (records != null) {
      //Перепишем количество записей для структуры
      val recC = em.createQuery(EventTypeIdByRequestTypeIdAndFinanceIdQuery.format("count(et)", queryStr.query, ""), classOf[Long])
      if (queryStr.data.size() > 0) queryStr.data.foreach(qdp => recC.setParameter(qdp.name, qdp.value))
      records(recC.getSingleResult)
    }

    val typed = em.createQuery(EventTypeIdByRequestTypeIdAndFinanceIdQuery.format("et", queryStr.query, sorting), classOf[EventType])
      .setMaxResults(limit)
      .setFirstResult(limit * page)
    if (queryStr.data.size() > 0) queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))

    val result = typed.getResultList
    result
  }

  def getCountOfAppealsForReceivedPatientByPeriod(filter: Object): Long = {

    val queryStr: QueryDataStructure = filter match {
      case filter1: ReceivedRequestDataFilter =>
        filter1.toQueryStructure()
      case _ =>
        new QueryDataStructure()
    }

    val typed = em.createQuery(AllAppealsWithFilterQuery.format("count(e)", i18n("db.flatDirectory.eventType.hospitalization"), queryStr.query, ""), classOf[Long])

    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }
    typed.getSingleResult
  }

  def getAllAppealsForReceivedPatientByPeriod(page: Int, limit: Int, sortingField: String, sortingMethod: String, filter: Object): util.List[Event] = {

    val queryStr: QueryDataStructure = filter match {
      case x: ReceivedRequestDataFilter =>
        x.toQueryStructure()
      case _ =>
        new QueryDataStructure()
    }

    val sorting = "ORDER BY %s %s".format(sortingField, sortingMethod)

    val q = AllAppealsWithFilterQuery.format("e", i18n("db.flatDirectory.eventType.hospitalization"), queryStr.query, sorting)
    val typed = em.createQuery(q, classOf[Event])
      .setMaxResults(limit)
      .setFirstResult(limit * page)

    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }

    val result = typed.getResultList
    result
  }


  def getEventTypeByCode(code: String): EventType = {
    val result = em.createQuery(EventTypeByCodeQuery, classOf[EventType])
      .setParameter("code", code)
      .getResultList
    val et = result(0)
    et
  }

  /* def getActionsByTypeCode(event: Event, codes: java.util.Set[String]) {     */
  def getActionsByTypeCode(event: Event, codes: util.Set[String]): Multimap[String, Action] = {
    val res: Multimap[String, Action] = HashMultimap.create()
    val result = em.createNamedQuery("Action.ActionsByFlatCode", classOf[Action])
      .setParameter("id", event.getId)
      .setParameter("codes", codes)
      .getResultList
    result.foreach(action => res.put(action.getActionType.getFlatCode, action))
    res
  }


  val getLastOrgStructureForEventFromMovingsSQLQuery: String =
    """
      |SELECT os.*
      |FROM Event e
      |INNER JOIN Action a ON a.event_id = e.id
      |INNER JOIN ActionType aty ON aty.id = a.actionType_id
      |INNER JOIN ActionProperty ap ON ap.action_id = a.id
      |INNER JOIN ActionPropertyType apt ON apt.id = ap.type_id
      |INNER JOIN ActionProperty_OrgStructure ap_os ON ap_os.id = ap.id
      |INNER JOIN OrgStructure os ON os.id = ap_os.value
      |WHERE a.deleted = 0
      |AND aty.flatCode = ?
      |AND apt.code = ?
      |AND e.id = ?
      |ORDER BY a.begDate DESC
    """.stripMargin


  val EventGetCountRecords =
    """
  SELECT count(e)
  FROM
    Event e
  WHERE
    %s
    """

  val EventFindActiveByBirthDateAndFullNameQuery =
    """
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

  val EventFindActiveByBirthDateQuery =
    """
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

  val EventFindActiveByFullNameQuery =
    """
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


  val EventFindActiveByExternalIdQuery =
    """
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

  val EventFindActiveByEventIdQuery =
    """
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

  val AllEventsBetweenDate =
    """
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

  val AllOrgStructuresForEventQuery: String =
    """
      | SELECT org
      | FROM
      | Action a,
      | ActionProperty ap,
      | APValueOrgStructure apvos,
      | OrgStructure org
      | WHERE
      |  a.deleted = 0
      |  AND ap.deleted = 0
      |  AND a.id = ap.action.id
      |  AND ap.id = apvos.id.id
      |  AND apvos.value.id = org.id
      |  AND a.actionType.flatCode = :actionTypeFlatCode
      |  AND ap.actionPropertyType.code = :actionPropertyTypeCode
      |  AND a.event.id = :eventId
      | ORDER BY a.begDate DESC
    """.stripMargin


  val ActionTypeFilterByEventIdQuery =
    """
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

  val EventByIdQuery =
    """
    SELECT e
    FROM
      Event e
    WHERE
      e.id = :id
    AND
      e.deleted = 0
    """

  val EventTypeByIdQuery =
    """
    SELECT et
    FROM
      EventType et
    WHERE
      et.id = :id
    AND
      et.deleted = 0
    """
  val EventTypeByCodeQuery =
    """
    SELECT et
    FROM
      EventType et
    WHERE
      et.code = :code
    AND
      et.deleted = 0
    """

  val RbCounterByEventTypeCntQuery =
    """
    SELECT
      rbc.id
    FROM
      rbCounter rbc
    WHERE
      rbc.id = :id
    """

  val EventByPatientQuery =
    """
    SELECT e
    FROM
      Event e
    WHERE
      e.patient = :patient
    AND
      e.deleted = 0
    """

  val EventByPatientWithExistsActionByTypeQuery =
    """
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
  val EventTypeIdByFDRecordIdQuery =
    """
  SELECT Max(et.id)
  FROM
    EventType et,
    FDFieldValue fdfv
  WHERE
    fdfv.record.id = '%s'
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

  val AllAppealsWithFilterQuery =
    """
    SELECT %s
    FROM
      Event e
    WHERE
      e.deleted = 0
    AND
      e.eventType.code IN (
        SELECT fdfv.value
        FROM
          FDFieldValue fdfv
        WHERE
          fdfv.record.flatDirectory.id = '%s'
      )
    %s
    %s
    """

  override def isStationaryEvent(event: Event): Boolean = {
    isStationaryEvent(event.getEventType)
  }

  override def isStationaryEvent(eventType: EventType): Boolean = {
    isStationaryEvent(eventType.getRequestType)
  }

  override def isStationaryEvent(requestType: RbRequestType): Boolean = {
    requestType.getCode match {
      case "clinic" | "hospital" | "stationary" => true
      case _ => false
    }
  }


}
