package ru.korus.tmis.core.database

import ru.korus.tmis.core.entity.model._
import ru.korus.tmis.core.indicators.IndicatorValue
import ru.korus.tmis.core.logging.db.LoggingInterceptor
import ru.korus.tmis.util.I18nable

import grizzled.slf4j.Logging
import java.util.Date
import javax.ejb.{TransactionAttributeType, TransactionAttribute, Stateless}
import javax.interceptor.Interceptors
import javax.persistence.{TemporalType, EntityManager, PersistenceContext}

import scala.collection.JavaConversions._
import scala.collection.mutable.LinkedHashMap
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.util.General._

import java.lang.{Double => JDouble}

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
class DbCustomQueryBean
  extends DbCustomQueryLocal
  with Logging
  with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def getFinanceId(eventId: Int) = {
    val result = em.createQuery(finance, classOf[Int])
      .setParameter("externalId", eventId)
      .getSingleResult
    result
  }

  def getTakenTissueByBarcode(id: Int, period: Int) = {
    val result = em.createQuery(takenTissueByBarcodeQuery, classOf[TakenTissue])
      .setParameter("barcode", id)
      .setParameter("period", period)
      .getResultList.headOption
    result.map {
      it => em.detach(it); it
    }.orNull
  }

  def getActiveEventsForDoctor(id: Int) = {
    val result = em.createQuery(ActiveEventsByDoctorIdQuery,
      classOf[Event])
      .setParameter("doctorId", id)
      .getResultList

    result.foreach((event) => em.detach(event))
    result
  }

  def getActiveEventsForDepartment(id: Int) = {
    val result = em.createQuery(ActiveEventsByDepartmentIdQuery,
      classOf[Array[AnyRef]])
      .setParameter("departmentId", id)
      .getResultList

    val events = result.map((e) => e(0).asInstanceOf[Event])
    events.foreach((event) => em.detach(event))
    events
  }

  def getAdmissionsByEvents(events: java.util.List[Event]) = {
    getEntitiesByEvents[Action](events, AdmissionsByEventIdsQuery)
  }

  def getHospitalBedsByEvents(events: java.util.List[Event]) = {
    getEntitiesByEvents[ActionProperty](events, HospitalBedsByEventIdsQuery)
  }

  def getAnamnesesByEvents(events: java.util.List[Event]) = {
    getEntitiesByEvents[ActionProperty](events, AnamnesesByEventIdsQuery)
  }

  def getAllergoAnamnesesByEvents(events: java.util.List[Event]) = {
    getEntitiesByEvents[ActionProperty](events, AllergoAnamnesesByEventIdsQuery)
  }

  def getDiagnosesByEvents(events: java.util.List[Event]) = {
    getEntitiesByEvents[ActionProperty](events, DiagnosesByEventIdsQuery)
  }

  def getDiagnosisSubstantiationByEvents(events: java.util.List[Event]) = {
    getEntitiesByEvents[Action](events, DiagnosisSubstantiationByEventIdsQuery)
  }

  def getEntitiesByEvents[T](events: java.util.List[Event],
                             query: String): collection.mutable.Map[Event, T] = {
    if (events.isEmpty) {
      return LinkedHashMap.empty[Event, T]
    }

    val ids = events.map((event) => event.getId.intValue)
    val result = em.createQuery(query,
      classOf[Array[AnyRef]])
      .setParameter("eventIds",
      asJavaCollection(ids))
      .getResultList

    result.foldLeft(LinkedHashMap.empty[Event, T])(
      (map, pair) => {
        val event = pair(0).asInstanceOf[Event]
        val entity = pair(1).asInstanceOf[T]
        em.detach(event)
        em.detach(entity)
        map.put(event, entity)
        map
      }
    )
  }

  def getAllAssessmentsByEventId(eventId: Int) = {
    getAllActionsByQueryAndEventId(AllAssessmentsByEventIdQuery, eventId)
  }

  def getAllDiagnosticsByEventId(eventId: Int) = {
    getAllActionsByQueryAndEventId(AllDiagnosticsByEventIdQuery, eventId)
  }

  def getAllActionsByQueryAndEventId(query: String,
                                     eventId: Int) = {
    val result = em.createQuery(query, classOf[Action])
      .setParameter("eventId", eventId)
      .getResultList

    result.foreach((a) => em.detach(a))
    result
  }

  def getIndicatorsByEventIdAndIndicatorNameAndDates(eventId: Int,
                                                     indicatorName: String,
                                                     beginDate: Date,
                                                     endDate: Date) = {
    val result = em.createQuery(IndicatorByEventIdAndIndicatorNameQuery,
      classOf[Array[AnyRef]])
      .setParameter("eventId", eventId)
      .setParameter("indicatorName", indicatorName)
      .setParameter("beginDate", beginDate, TemporalType.TIMESTAMP)
      .setParameter("endDate", endDate, TemporalType.TIMESTAMP)
      .getResultList

    val indicators =
      result.foldLeft(List.empty[IndicatorValue[JDouble]])(
        (inds, a) => {
          val iv = new IndicatorValue[JDouble](
            a(0).asInstanceOf[Int],
            a(1).asInstanceOf[String],
            a(2).asInstanceOf[JDouble],
            a(3).asInstanceOf[Date])
          iv :: inds
        }
      )

    asJavaList(indicators)
  }

  def getTreatmentInfo(eventId: Int,
                       actionTypeId: Int,
                       beginDate: Date,
                       endDate: Date) = {
    val ts = if (beginDate != null && endDate != null) {
      em.createQuery(TreatmentActionsByEventId +
        filterActionTypeId +
        filterTimePeriod,
        classOf[Action])
        .setParameter("eventId", eventId)
        .setParameter("actionTypeId", actionTypeId)
        .setParameter("beginDate", beginDate)
        .setParameter("endDate", endDate)
        .getResultList
    } else {
      em.createQuery(TreatmentActionsByEventId +
        filterActionTypeId,
        classOf[Action])
        .setParameter("eventId", eventId)
        .setParameter("actionTypeId", actionTypeId)
        .getResultList
    }
    ts.foreach(em.detach(_))
    ts
  }

  def getTreatmentInfo(eventId: Int, beginDate: Date, endDate: Date) = {
    val ts = if (beginDate != null && endDate != null) {
      em.createQuery(TreatmentActionsByEventId + filterTimePeriod,
        classOf[Action])
        .setParameter("eventId", eventId)
        .setParameter("beginDate", beginDate)
        .setParameter("endDate", endDate)
        .getResultList
    } else {
      em.createQuery(TreatmentActionsByEventId,
        classOf[Action])
        .setParameter("eventId", eventId)
        .getResultList
    }
    ts.foreach(em.detach(_))
    ts
  }

  def getUnitByCode(code: String) = {
    em.createNamedQuery[RbUnit]("RbUnit.findByCode", classOf[RbUnit])
      .setParameter("code", code)
      .getResultList
      .headOption
      .orNull
  }

  def getHeightForPatient(p: Patient): JDouble = {
    import ru.korus.tmis.util.General.catchy

    em.createQuery(HeightQuery,
      classOf[Array[AnyRef]])
      .setParameter("pid", p.getId)
      .getResultList
      .collect {
      case Array(v: JDouble, date: java.util.Date) => (v, date)
    }
      .sortBy(_._2.getTime)
      .lastOption
      .map(_._1)
      .orElse(catchy {
      new JDouble(p.getWeight.toDouble)
    })
      .orNull
    //.getOrElse{ throw new CoreException(i18n("error.noHeightForPatientFound").format(p.getId)) }
  }

  def getWeightForPatient(p: Patient): JDouble = {
    import ru.korus.tmis.util.General.catchy

    em.createQuery(WeightQuery,
      classOf[Array[AnyRef]])
      .setParameter("pid", p.getId)
      .getResultList
      .collect {
      case Array(v: JDouble, date: java.util.Date) => (v, date)
    }
      .sortBy(_._2.getTime)
      .lastOption
      .map(_._1)
      .orElse(catchy {
      new JDouble(p.getWeight.toDouble)
    })
      .orNull
    //.getOrElse{ throw new CoreException(i18n("error.noWeightForPatientFound").format(p.getId)) }
  }

  val HeightQuery = """
    SELECT apd.value, ap.action.begDate
    FROM ActionProperty ap, APValueDouble apd
    WHERE ap.id = apd.id.id
    AND ap.actionPropertyType.name = 'Рост'
    AND ap.action.event.patient.id = :pid
    AND ap.actionPropertyType.deleted = false
    AND ap.deleted = false
    AND ap.action.deleted = false
    AND ap.action.event.deleted = false
    AND ap.action.event.patient.deleted = false
                    """

  val WeightQuery = """
    SELECT apd.value, ap.action.begDate
    FROM ActionProperty ap, APValueDouble apd
    WHERE ap.id = apd.id.id
    AND (ap.actionPropertyType.name = 'Вес' OR ap.actionPropertyType.name = 'Масса')
    AND ap.action.event.patient.id = :pid
    AND ap.actionPropertyType.deleted = false
    AND ap.deleted = false
    AND ap.action.deleted = false
    AND ap.action.event.deleted = false
    AND ap.action.event.patient.deleted = false
                    """

  val ActiveEventsQuery = """
    SELECT e
    FROM
      Event e
    WHERE
      e.execDate is NULL
    AND
      e.eventType.id IN
      (
        SELECT et.id
        FROM
          EventType et
        WHERE
          et.form = '003'
      )
    AND
      e.id NOT IN
      (
        SELECT a.event.id
        FROM
          Action a
        WHERE
          a.actionType.id IN
          (
            SELECT at.id
            FROM
              ActionType at
            WHERE
              at.flatCode = 'leaved'
          )
        AND
          a.event.id = e.id
      )
    AND
      e.deleted = 0
                          """

  val ActiveEventsByDoctorIdQuery = ActiveEventsQuery + """
    AND
      e.executor.id = :doctorId
                                                        """

  val ActiveEventsByDepartmentIdQuery = """
    SELECT e, MAX(a.createDatetime)
    FROM
      Event e,
      Action a,
      ActionProperty ap,
      APValueHospitalBed bed,
      OrgStructureHospitalBed org
    WHERE
      e.execDate is NULL AND
      e.id = a.event.id AND
      a.id = ap.action.id AND
      ap.id = bed.id.id AND
      bed.value.id = org.id AND
      org.masterDepartment.id = :departmentId
    AND
      a.actionType.id IN
      (
        SELECT actionType.id
        FROM
          ActionType actionType
        WHERE
          actionType.flatCode = '%s'
      )
    AND
      e.id NOT IN
      (
        SELECT leaved.event.id
        FROM
          Action leaved
        WHERE
          leaved.actionType.id IN
          (
            SELECT at.id
            FROM
              ActionType at
            WHERE
              at.flatCode = 'leaved'
          )
        AND
          leaved.event.id = e.id
      )
    AND
      e.deleted = 0
    GROUP BY e
                                        """.format(i18n("db.action.movingFlatCode"))

  val ActionsByEventIdsAndFlatCodeQuery = """
    SELECT e, a
    FROM
      Action a JOIN a.event e
    WHERE
      a.event.id IN :eventIds
    AND
      a.actionType.id IN
      (
        SELECT actionType.id
        FROM
          ActionType actionType
        WHERE
          actionType.flatCode = '%s'
      )
    AND
      a.deleted = 0
    ORDER BY
      a.createDatetime %s
                                          """

  val AdmissionsByEventIdsQuery =
    ActionsByEventIdsAndFlatCodeQuery.format(
      i18n("db.action.admissionFlatCode"),
      "DESC")

  val HospitalBedsByEventIdsQuery = """
    SELECT e, ap
    FROM
      ActionProperty ap JOIN ap.action a JOIN a.event e
    WHERE
      e.id IN :eventIds
    AND
      a.actionType.id IN
      (
        SELECT actionType.id
        FROM
          ActionType actionType
        WHERE
          actionType.flatCode = '%s'
      )
    AND
      ap.actionPropertyType.name = '%s'
    AND
      a.deleted = 0
    AND
      ap.deleted = 0
    ORDER BY
      ap.createDatetime ASC
                                    """.format(
    i18n("db.action.movingFlatCode"),
    i18n("db.apt.hospitalBedName"))

  val AnamnesesByEventIdsQuery = """
    SELECT e, ap
    FROM
      ActionProperty ap JOIN ap.action a JOIN a.event e
    WHERE
      e.id IN :eventIds
    AND
      a.actionType.groupId IN
      (
        SELECT assessmentType.id
        FROM
          ActionType assessmentType
        WHERE
          assessmentType.name like '%s'
      )
    AND
      (
        ap.actionPropertyType.name like '%s'
      AND
        ap.actionPropertyType.name not like '%s'
      AND
        ap.actionPropertyType.name not like '%s'
      )
    AND
      a.deleted = 0
    AND
      ap.deleted = 0
    AND
      e.deleted = 0
    ORDER BY
      ap.createDatetime ASC
                                 """.format(
    i18n("db.action.preAssessmentGroupName"),
    i18n("db.apt.anamnesisName"),
    i18n("db.apt.anamnesisDename"),
    i18n("db.apt.allergoanamnesisName"))

  val AllergoAnamnesesByEventIdsQuery = """
    SELECT e, ap
    FROM
      ActionProperty ap JOIN ap.action a JOIN a.event e
    WHERE
      e.id IN :eventIds
    AND
      a.actionType.groupId IN
      (
        SELECT assessmentType.id
        FROM
          ActionType assessmentType
        WHERE
          assessmentType.deleted = 0 AND
          assessmentType.name like '%s'
      )
    AND
      (
        ap.actionPropertyType.deleted = 0 AND
        ap.actionPropertyType.name like '%s'
      )
    AND
      e.deleted = 0
    AND
      a.deleted = 0
    AND
      ap.deleted = 0
    ORDER BY
      ap.createDatetime ASC
                                        """.format(
    i18n("db.action.preAssessmentGroupName"),
    i18n("db.apt.allergoanamnesisName"))

  val DiagnosesByEventIdsQuery = """
    SELECT e, ap
    FROM
      ActionProperty ap JOIN ap.action a JOIN a.event e
    WHERE
      e.id IN :eventIds
    AND
      a.actionType.groupId IN
      (
        SELECT epicrisisType.id
        FROM
          ActionType epicrisisType
        WHERE epicrisisType.deleted = 0 AND (
          epicrisisType.name like '%s'
        OR
          epicrisisType.name like '%s'
        )
      )
    AND ap.actionPropertyType.deleted = 0
    AND
      (
        ap.actionPropertyType.name like '%s'
      OR
        ap.actionPropertyType.name like '%s'
      )
    AND a.deleted = 0
    AND ap.deleted = 0
    AND e.deleted = 0
    ORDER BY
      ap.createDatetime ASC
                                 """.format(
    i18n("db.action.preAssessmentGroupName"),
    i18n("db.action.epicrisisGroupName"),
    i18n("db.apt.diagnosisName01"),
    i18n("db.apt.diagnosisName02"))

  val DiagnosisSubstantiationByEventIdsQuery = """
    SELECT e, a
    FROM
      Action a JOIN a.event e JOIN a.actionType at
    WHERE
      a.event.id IN :eventIds
    AND
      (
        at.groupId IN
          (
            SELECT epicrisisType.id
            FROM ActionType epicrisisType
            WHERE epicrisisType.name like '%s'
            AND epicrisisType.deleted = 0
          )
        OR
        at.name like '%s'
      )
    AND a.deleted = 0
    AND e.deleted = 0
    AND at.deleted = 0
    ORDER BY
      a.begDate DESC
                                               """.format(i18n("db.action.preAssessmentGroupName"),
    i18n("db.action.diagnosisSubstantiation"))

  val AllAssessmentsByEventIdQuery = """
    SELECT a
    FROM
      Action a
    WHERE
      a.event.id = :eventId
    AND a.actionType.clazz = %s
    AND a.deleted = 0
    AND a.event.deleted = 0
    AND a.actionType.deleted = 0
                                     """.format(i18n("db.action.assessmentClass"))

  val AllDiagnosticsByEventIdQuery = """
    SELECT a
    FROM
      Action a
    WHERE
      a.event.id = :eventId AND
      a.actionType.clazz = %s AND
      a.deleted = 0 AND
      a.event.deleted = 0 AND
      a.actionType.deleted = 0
                                     """.format(i18n("db.action.diagnosticClass"))

  val IndicatorByEventIdAndIndicatorNameQuery = """
    SELECT ap.id, apt.name, apd.value, ap.createDatetime FROM
      ActionPropertyType apt,
      ActionProperty ap,
      Action a,
      APValueDouble apd
    WHERE
      ap.actionPropertyType.id = apt.id AND
      apd.id.id = ap.id AND
      ap.action.id = a.id AND
      a.event.id = :eventId AND
      ap.createDatetime >= :beginDate AND
      ap.createDatetime <= :endDate AND
      apt.name like :indicatorName AND
      a.deleted = 0 AND
      a.event.deleted = 0 AND
      ap.deleted = 0 AND
      apt.deleted = 0
                                                """

  val TreatmentActionsByEventId = """
    SELECT a FROM
      Event e,
      Action a,
      ActionType at
    WHERE
      e.id = :eventId AND
      a.event = e AND
      a.actionType = at AND
      at.clazz = %s AND
      e.deleted = 0 AND
      a.deleted = 0 AND
      at.deleted = 0
                                  """.format(i18n("db.action.treatmentClass"))

  val filterActionTypeId = " AND at.id = :actionTypeId"
  val filterTimePeriod = """
      AND (a.begDate IS NULL OR a.begDate BETWEEN :beginDate AND :endDate)
      AND (a.endDate IS NULL OR a.endDate BETWEEN :beginDate AND :endDate)
                         """

  val takenTissueByBarcodeQuery = """
      SELECT t from TakenTissue t where t.barcode = :barcode and t.period = :period
                                  """

  val finance = """
      SELECT RbFinance rf

                """
}
