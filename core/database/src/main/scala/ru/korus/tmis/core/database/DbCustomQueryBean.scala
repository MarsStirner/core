package ru.korus.tmis.core.database

import ru.korus.tmis.core.entity.model._
import ru.korus.tmis.core.indicators.IndicatorValue
import ru.korus.tmis.core.logging.LoggingInterceptor
import ru.korus.tmis.util.I18nable

import grizzled.slf4j.Logging
import java.util.Date
import javax.ejb.{TransactionAttributeType, TransactionAttribute, Stateless}
import javax.interceptor.Interceptors
import javax.persistence.{TemporalType, EntityManager, PersistenceContext}

import scala.collection.JavaConversions._
import scala.collection.mutable.LinkedHashMap
import java.lang.{String, Double}
import javax.persistence.{TemporalType, EntityManager, PersistenceContext}
import ru.korus.tmis.core.entity.model._
import scala.Predef._
import java.util.{Date, ArrayList, List}
import ru.korus.tmis.core.data._
import javax.ejb.{EJB, Stateless, TransactionAttributeType, TransactionAttribute}
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.util.General._

import java.lang.{Double => JDouble}
import collection.immutable.ListMap

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
class DbCustomQueryBean
  extends DbCustomQueryLocal
  with Logging
  with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  @EJB
  private var eventBean: DbEventBeanLocal = _

  @EJB
  private var orgStructure: DbOrgStructureBeanLocal = _

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

  def getActiveEventsForDepartmentAndDoctor(page: Int, limit: Int, sortingField: String, sortingMethod: String, filter: Object) = {

    val sorting = "ORDER BY %s %s".format(sortingField, sortingMethod)
    val queryStr: QueryDataStructure = if (filter.isInstanceOf[PatientsListRequestDataFilter]) {
      filter.asInstanceOf[PatientsListRequestDataFilter].toQueryStructure()
    }
    else {
      new QueryDataStructure()
    }

    var typed = em.createQuery(ActiveEventsByDepartmentIdAndDoctorIdBetweenDatesQuery.format("e", queryStr.query, i18n("db.action.movingFlatCode"), "GROUP BY e", sorting), classOf[Array[AnyRef]])
      .setMaxResults(limit)
      .setFirstResult(limit * page)

    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }
    val result = typed.getResultList

    val events = result.map((e) => e(0).asInstanceOf[Event])
    events.foreach((event) => em.detach(event))
    events
  }

  def getCountActiveEventsForDepartmentAndDoctor(filter: Object) = {

    val queryStr: QueryDataStructure = if (filter.isInstanceOf[PatientsListRequestDataFilter]) {
      filter.asInstanceOf[PatientsListRequestDataFilter].toQueryStructure()
    }
    else {
      new QueryDataStructure()
    }

    var typed = em.createQuery(ActiveEventsByDepartmentIdAndDoctorIdBetweenDatesQuery.format("count(e)", queryStr.query, i18n("db.action.movingFlatCode"), "", ""), classOf[Array[AnyRef]])

    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }
    val result = typed.getSingleResult
    result(0).asInstanceOf[Long]
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

  def getLastAssessmentByEvents(events: List[Event]) = {
    getEntitiesByEvents[Action](events, LastAssessmentByEventIdsQuery)
  }

  def getAllDiagnosticsByEventId(eventId: Int) = {
    getAllActionsByQueryAndEventId(AllDiagnosticsByEventIdQuery.format(
      "a",
      i18n("db.action.diagnosticClass")),
      eventId)
  }

  def getAllDiagnosticsWithFilter(page: Int, limit: Int, sortingField: String, sortingMethod: String, filter: Object) = {

    val queryStr: QueryDataStructure = if (filter.isInstanceOf[DiagnosticsListRequestDataFilter]) {
      filter.asInstanceOf[DiagnosticsListRequestDataFilter].toQueryStructure()
    }
    else {
      new QueryDataStructure()
    }

    val sorting = "ORDER BY %s %s".format(sortingField, sortingMethod)

    var typed = em.createQuery(AllDiagnosticsByEventIdAndFiltredByCodeQuery.format("a", i18n("db.action.diagnosticClass"), queryStr.query, sorting), classOf[Action])
      .setMaxResults(limit)
      .setFirstResult(limit * page)

    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }

    val result = typed.getResultList
    result.foreach(a => em.detach(a))
    result
  }

  def getCountDiagnosticsWithFilter(filter: Object) = {

    val queryStr: QueryDataStructure = if (filter.isInstanceOf[DiagnosticsListRequestDataFilter]) {
      filter.asInstanceOf[DiagnosticsListRequestDataFilter].toQueryStructure()
    }
    else {
      new QueryDataStructure()
    }

    var typed = em.createQuery(AllDiagnosticsByEventIdAndFiltredByCodeQuery.format("count(a)", i18n("db.action.diagnosticClass"), queryStr.query, ""), classOf[Long])

    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }
    typed.getSingleResult
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
      result.foldLeft(scala.List.empty[IndicatorValue[JDouble]])(
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

  def getCountOfAppealsWithFilter(filter: Object) = {

    val queryStr: QueryDataStructure = if (filter.isInstanceOf[TalonSPODataListFilter]) {
      filter.asInstanceOf[TalonSPODataListFilter].toQueryStructure()
    }
    else if (filter.isInstanceOf[AppealSimplifiedRequestDataFilter]) {
      filter.asInstanceOf[AppealSimplifiedRequestDataFilter].toQueryStructure()
    }
    else {
      new QueryDataStructure()
    }

    var typed = em.createQuery(AllAppealsWithFilterQuery.format("count(e)", queryStr.query, "", ""), classOf[Long])

    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }

    typed.getSingleResult
  }

  def getAllAppealsWithFilter(page: Int, limit: Int, sortingField: String, sortingMethod: String, filter: Object, records: (java.lang.Long) => java.lang.Boolean) = {

    var diagnostic_filter: String = ""
    var ap_string_filter: String = ""
    var ap_mkb_filter: String = ""
    var department_filter: String = ""
    var flgDiagnosesSwitched: Boolean = false
    var flgDepartmentSwitched: Boolean = false
    var sorting = "ORDER BY e.id %s".format(sortingMethod)
    var flgDepartmentSort: Boolean = false
    if (sortingField.compareTo("department") != 0)
      sorting = "ORDER BY %s %s".format(sortingField, sortingMethod)
    else
      flgDepartmentSort = true
    if (sortingField.contains("%s")) sorting = "ORDER BY %s".format(sortingField.format(sortingMethod, sortingMethod, sortingMethod)) //костыль для докторв

    val default_org = orgStructure.getAllOrgStructures.filter(element => element.getType == 4).toList.get(0) //Приемное отделение

    val queryStr: QueryDataStructure = if (filter.isInstanceOf[TalonSPODataListFilter]) {
      filter.asInstanceOf[TalonSPODataListFilter].toQueryStructure()
    }
    else if (filter.isInstanceOf[AppealSimplifiedRequestDataFilter]) {
      if (filter.asInstanceOf[AppealSimplifiedRequestDataFilter].diagnosis != null && !filter.asInstanceOf[AppealSimplifiedRequestDataFilter].diagnosis.isEmpty) {
        diagnostic_filter = "AND upper(mkb.diagID) LIKE upper(:diagnosis)\n"
        ap_string_filter = "AND upper(apv.value) LIKE upper(:diagnosis)\n"
        ap_mkb_filter = "AND upper(apv.mkb.diagID) LIKE upper(:diagnosis)\n"
        flgDiagnosesSwitched = true
      }
      if (filter.asInstanceOf[AppealSimplifiedRequestDataFilter].departmentId > 0 &&
        filter.asInstanceOf[AppealSimplifiedRequestDataFilter].departmentId != default_org.getId.intValue()) {
        department_filter = "AND apv.value.id = :departmentId"
        flgDepartmentSwitched = true
      }
      filter.asInstanceOf[AppealSimplifiedRequestDataFilter].toQueryStructure()
    }
    else {
      new QueryDataStructure()
    }

    //Получаем список всех обращений (без диагнозов) (сортированный)
    val query = AllAppealsWithFilterExQuery.format("e", queryStr.query, "", sorting)
    var typed = em.createQuery(query, classOf[Event])
    //     .setMaxResults(limit)
    //     .setFirstResult(limit * page)

    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }
    var res = typed.getResultList


    val ids = new java.util.HashSet[java.lang.Integer]
    val ret_value = new java.util.LinkedHashMap[Event, java.util.Map[Object, Object]]
    var org_value = new java.util.HashMap[Event, OrgStructure]

    if (res.size() > 0) {
      res.foreach(e => ids.add(e.getId))

      //Получение отделения из последнего из последнего экшена движения
      val depArrayTyped = em.createQuery(OrgStructureSubQuery.format(i18n("db.actionType.moving"),
        i18n("db.rbCAP.moving.id.located"),
        department_filter),
        classOf[Array[AnyRef]])
        .setParameter("ids", asJavaCollection(ids))
      if (flgDepartmentSwitched)
        depArrayTyped.setParameter("departmentId", filter.asInstanceOf[AppealSimplifiedRequestDataFilter].departmentId)

      val depArray = depArrayTyped.getResultList
      org_value = depArray.foldLeft(new java.util.HashMap[Event, OrgStructure])(
        (map, pair) => {
          val event = pair(0).asInstanceOf[Event]
          var department: OrgStructure = default_org
          if (pair(1).isInstanceOf[OrgStructure]) {
            department = pair(1).asInstanceOf[OrgStructure]
            em.detach(department)
          }
          em.detach(event)
          map.put(event, department)
          map
        }
      )

      //Получение диагнозов
      val map = new java.util.LinkedHashMap[java.lang.Integer, Object]()
      map.put(0, (MainDiagnosisQuery, i18n("db.diagnostics.diagnosisType.id.clinical"), ""))
      map.put(1, (MainDiagnosisQuery, i18n("db.diagnostics.diagnosisType.id.main"), ""))
      map.put(2, (ClinicalDiagnosisQuery, "4501", "Основной клинический диагноз"))
      map.put(3, (AttendantDiagnosisQuery, "1_1_01", "Основной клинический диагноз"))
      map.put(4, (AttendantDiagnosisQuery, "4201", "Диагноз направившего учреждения"))

      var i = 0
      while (ids.size() > 0 && i <= 4) {
        val map_value = map.get(Integer.valueOf(i)).asInstanceOf[(String, String, String)]
        val typed2 = i match {
          case 0 | 1 => em.createQuery(map_value._1.format(map_value._2, diagnostic_filter), classOf[Array[AnyRef]])
          case 2 => em.createQuery(map_value._1.format(map_value._2, map_value._3, ap_string_filter), classOf[Array[AnyRef]])
          case _ => em.createQuery(map_value._1.format(map_value._2, map_value._3, ap_mkb_filter), classOf[Array[AnyRef]])
        }

        if (flgDiagnosesSwitched) {
          typed2.setParameter("diagnosis", "%" + filter.asInstanceOf[AppealSimplifiedRequestDataFilter].diagnosis + "%")
        }
        val res2 = typed2.setParameter("ids", asJavaCollection(ids)).getResultList

        res2.foreach(f => {
          if (f(0).isInstanceOf[Event]) {
            ids.remove(f(0).asInstanceOf[Event].getId)
          }
          val internalMap = new java.util.HashMap[Object, Object]
          internalMap.put(f(1), f(2))
          ret_value.put(f(0).asInstanceOf[Event], internalMap)
          em.detach(f(0))
          em.detach(f(1))
          em.detach(f(2))
        })
        i = i + 1
      }
      map.clear()
      //эвенты без диагнозов
      if (ids.size() > 0 && !flgDiagnosesSwitched) {
        ids.foreach(f => ret_value.put(eventBean.getEventById(f.intValue()), new java.util.HashMap[Object, Object]))
      }
      ids.clear()
    }
    //вернуть первоначальную сортировку и прописать отделения (учитывая страницу и лимит)
    val ret_value_sorted = LinkedHashMap.empty[Event, Object]
    res.foreach(e => {
      val pos = ret_value.get(e)
      if (pos != null) {
        val pos_org = org_value.get(e)
        if (pos_org != null) {
          if (filter.asInstanceOf[AppealSimplifiedRequestDataFilter].departmentId != default_org.getId.intValue() ||
            pos_org.getId.intValue() == default_org.getId.intValue()) {
            ret_value_sorted.put(e, (pos, pos_org))
          }
        }
        else if (!flgDepartmentSwitched) {
          ret_value_sorted.put(e, (pos, default_org))
        }
      }
    })
    var ret_value_sorted_page = Map.empty[Event, Object]
    val ret_value_sorted2 = if (flgDepartmentSort) {
      //Cортировка по department name
      if (sortingMethod.compareTo("desc") == 0) //desc
        ListMap(ret_value_sorted.toList.sortWith(_._2.asInstanceOf[(Map[Object, Object], OrgStructure)]._2.getName > _._2.asInstanceOf[(Map[Object, Object], OrgStructure)]._2.getName): _*)
      else //asc
        ListMap(ret_value_sorted.toList.sortBy(_._2.asInstanceOf[(Map[Object, Object], OrgStructure)]._2.getName): _*)
    } else {
      ListMap(ret_value_sorted.toList: _*)
    }
    //проведем ручной пэйджинг
    ret_value_sorted_page = if ((ret_value_sorted2.size - limit * (page + 1)) > 0)
      ret_value_sorted2.dropRight(ret_value_sorted2.size - limit * (page + 1)).drop(page * limit)
    else ret_value_sorted2.drop(page * limit)


    if (records != null) records(ret_value_sorted.size) //Перепишем количество записей для структуры

    ret_value.clear()
    ret_value_sorted.clear()
    ret_value_sorted_page
  }

  def getDiagnosisForMainDiagInAppeal(appealId: Int): Mkb = {
    var diagnostic_filter: String = ""
    var ap_string_filter: String = ""
    var ap_mkb_filter: String = ""

    val ids = new java.util.HashSet[java.lang.Integer]
    ids.add(appealId)
    val ret_value = new java.util.LinkedHashMap[Event, java.util.Map[Object, Object]]
    val map = new java.util.LinkedHashMap[java.lang.Integer, Object]()
    //map.put(0, (MainDiagnosisQuery,"",""))
    map.put(0, (ClinicalDiagnosisQuery, "4501", "Основной клинический диагноз"))
    map.put(1, (AttendantDiagnosisQuery, "1_1_01", "Основной клинический диагноз"))
    map.put(2, (AttendantDiagnosisQuery, "4201", "Диагноз направившего учреждения"))

    var i = 0
    while (i <= 2) {
      val map_value = map.get(Integer.valueOf(i)).asInstanceOf[(String, String, String)]
      val typed2 = i match {
        case 0 => em.createQuery(map_value._1.format(map_value._2, map_value._3, ap_string_filter), classOf[Array[AnyRef]])
        case _ => em.createQuery(map_value._1.format(map_value._2, map_value._3, ap_mkb_filter), classOf[Array[AnyRef]])
      }
      val res2 = typed2.setParameter("ids", asJavaCollection(ids)).getResultList

      res2.foreach(f => {
        val internalMap = new java.util.HashMap[Object, Object]
        internalMap.put(f(1), f(2))
        ret_value.put(f(0).asInstanceOf[Event], internalMap)
        //em.detach(f(0))
        //em.detach(f(1))
        //em.detach(f(2))
      })
      i = i + 1
      if (res2 != null && res2.size() > 0) {
        if (res2(0)(2).asInstanceOf[Mkb] != null) {
          map.clear()
          ids.clear()
          val qwe = em.createNamedQuery("Mkb.findById", classOf[Mkb])
            .setParameter("id", res2(0)(2).asInstanceOf[Mkb].getId)
            .getResultList

          val asd = qwe.get(0)
          em.detach(asd)
          return asd
          //return res2(0)(2).asInstanceOf[Mkb]
        }
      }
    }
    map.clear()
    ids.clear()
    null
  }

  private def putValueToMap(key: String,
                            query: String,
                            parName: String,
                            parValue: AnyRef,
                            by: java.util.Map[String, java.util.Map[String, Mkb]]) {
    val res = em.createQuery(query, classOf[Array[AnyRef]])
    if (parName != null && !parName.isEmpty && parValue != null) {
      res.setParameter(parName, parValue)
    }

    val res1 = res.getResultList
      .foldLeft(new java.util.LinkedHashMap[String, Mkb])(
      (map, mkb) => {
        map.put(mkb(0).asInstanceOf[String], mkb(1).asInstanceOf[Mkb])
        map
      })
    by.put(key, res1)
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

  def getDistinctMkbsWithFilter(sortingField: String, sortingMethod: String, filter: Object) = {

    val retValue = new java.util.HashMap[String, java.util.Map[String, Mkb]]
    if (filter.asInstanceOf[MKBListRequestDataFilter].display == true) {
      var queryStr: QueryDataStructure = if (filter.isInstanceOf[MKBListRequestDataFilter]) {
        filter.asInstanceOf[MKBListRequestDataFilter].toQueryStructure()
      }
      else {
        new QueryDataStructure()
      }
      val sorting = "ORDER BY %s %s".format(sortingField, sortingMethod)
      if (queryStr.data.size() > 0) {
        var pos = 0
        var value: AnyRef = null
        queryStr.data.foreach(qdp => {
          qdp.name match {
            case "code" => {
              if (pos < 3) {
                pos = 3
                value = qdp.value
              }
            }
            case "blockId" => {
              if (pos < 2) {
                pos = 2
                value = qdp.value
              }
            }
            case "classId" => {
              if (pos < 1) {
                pos = 1
                value = qdp.value
              }
            }
            case _ => {
              if (pos < 0) pos = 0
            }
          }
        })

        pos match {
          case 1 => {
            this.putValueToMap("class",
              "SELECT DISTINCT(mkb.classID), mkb FROM Mkb mkb ORDER BY mkb.classID ASC",
              null,
              null,
              retValue)
          }
          case 2 => {
            this.putValueToMap("class",
              "SELECT DISTINCT(mkb.classID), mkb FROM Mkb mkb ORDER BY mkb.classID ASC",
              null,
              null,
              retValue)
            this.putValueToMap("block",
              "SELECT DISTINCT(mkb.blockID), mkb FROM Mkb mkb WHERE mkb.classID IN (\n" +
                "SELECT mkb2.classID FROM Mkb mkb2 WHERE mkb2.blockID = :blockId )\n" +
                "ORDER BY mkb.blockID ASC, mkb.classID ASC",
              "blockId",
              value,
              retValue)
          }
          case 3 => {
            this.putValueToMap("class",
              "SELECT DISTINCT(mkb.classID), mkb FROM Mkb mkb ORDER BY mkb.classID ASC",
              null,
              null,
              retValue)
            this.putValueToMap("block",
              "SELECT DISTINCT(mkb.blockID), mkb FROM Mkb mkb WHERE mkb.classID IN (\n" +
                "SELECT mkb2.classID FROM Mkb mkb2 WHERE mkb2.blockID = :blockId )\n" +
                "ORDER BY mkb.blockID ASC, mkb.classID ASC",
              "blockId",
              em.createQuery("SELECT MAX(mkb.blockId) FROM Mkb mkb ORDER BY mkb.diagID = :diagID", classOf[AnyRef])
                .getSingleResult,
              retValue)
            this.putValueToMap("code",
              "SELECT DISTINCT(mkb.diagID), mkb FROM Mkb mkb WHERE mkb.blockID IN \n" +
                "(SELECT mkb2.blockID FROM Mkb mkb2 WHERE mkb2.diagID = :diagID)\n" +
                "ORDER BY mkb.diagID ASC, mkb.blockID ASC, mkb.classID ASC",
              "diagID",
              value,
              retValue)
          }
          case _ => {}
        }
      }
    }
    retValue
  }

  def getAllMkbsWithFilter(page: Int, limit: Int, sortingField: String, sortingMethod: String, filter: Object) = {

    var queryStr: QueryDataStructure = if (filter.isInstanceOf[MKBListRequestDataFilter]) {
      filter.asInstanceOf[MKBListRequestDataFilter].toQueryStructure()
    }
    else {
      new QueryDataStructure()
    }

    val sorting = "ORDER BY %s %s".format(sortingField, sortingMethod)
    if (queryStr.data.size() > 0) {
      if (queryStr.query.indexOf("AND ") == 0) {
        queryStr.query = "WHERE " + queryStr.query.substring("AND ".length())
      }
    }

    var typed = em.createQuery(AllMkbWithFilterQuery.format("mkb", queryStr.query, sorting), classOf[Mkb])
      .setMaxResults(limit)
      .setFirstResult(limit * page)

    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }

    val result = typed.getResultList
    result.foreach(f => {
      em.detach(f)
    })
    result
  }

  def getCountOfMkbsWithFilter(filter: Object) = {

    var queryStr: QueryDataStructure = if (filter.isInstanceOf[MKBListRequestDataFilter]) {
      filter.asInstanceOf[MKBListRequestDataFilter].toQueryStructure()
    }
    else {
      new QueryDataStructure()
    }

    if (queryStr.data.size() > 0) {
      if (queryStr.query.indexOf("AND ") == 0) {
        queryStr.query = "WHERE " + queryStr.query.substring("AND ".length())
      }
    }

    var typed = em.createQuery(AllMkbWithFilterQuery.format("count(mkb)", queryStr.query, ""), classOf[Long])

    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }

    typed.getSingleResult
  }

  def getCountOfThesaurusWithFilter(filter: Object) = {
    var queryStr: QueryDataStructure = if (filter.isInstanceOf[ThesaurusListRequestDataFilter]) {
      filter.asInstanceOf[ThesaurusListRequestDataFilter].toQueryStructure()
    }
    else {
      new QueryDataStructure()
    }
    if (queryStr.data.size() > 0) {
      if (queryStr.query.indexOf("AND ") == 0) {
        queryStr.query = "WHERE " + queryStr.query.substring("AND ".length())
      }
    }
    var typed = em.createQuery(AllThesaurusWithFilterQuery.format("count(r)", queryStr.query, ""), classOf[Long])
    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }
    typed.getSingleResult
  }


  def getAllThesaurusWithFilter(page: Int, limit: Int, sortingField: String, sortingMethod: String, filter: Object) = {
    var queryStr: QueryDataStructure = if (filter.isInstanceOf[ThesaurusListRequestDataFilter]) {
      filter.asInstanceOf[ThesaurusListRequestDataFilter].toQueryStructure()
    }
    else {
      new QueryDataStructure()
    }
    val sorting = "ORDER BY %s %s".format(sortingField, sortingMethod)
    if (queryStr.data.size() > 0) {
      if (queryStr.query.indexOf("AND ") == 0) {
        queryStr.query = "WHERE " + queryStr.query.substring("AND ".length())
      }
    }
    var typed = em.createQuery(AllThesaurusWithFilterQuery.format("r", queryStr.query, sorting), classOf[Thesaurus])
      .setMaxResults(limit)
      .setFirstResult(limit * page)
    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }
    val result = typed.getResultList
    result.foreach(f => {
      em.detach(f)
    })
    result
  }

  def getLastActionByTypeCodeAndAPTypeName(eventId: Int, code: String, aptName: String) = {

    val result = em.createQuery(LastActionByTypeCodeAndAPTypeNameQuery, classOf[Action])
      .setParameter("id", eventId)
      .setParameter("code", code)
      .setParameter("name", aptName)
      .getResultList

    result.size match {
      case 0 => {
        null
      }
      case _ => {
        result.foreach(a => em.detach(a))
        result.iterator().next()
      }
    }
  }

  /**
   * Получить код источника финансирования
   * @param e - карточка пациента
   * @return - код источника финансирования
   * @throws CoreException
   */
  def getFinanceId(e: Event) = {
    val result = em.createQuery( """
     SELECT et.finance
     FROM   EventType et,
            Event e,
            RbFinance rf
     WHERE  e.id = :eventId
        AND e.eventType.id = et.id
        AND et.finance = rf
        AND et.deleted = 0
        AND e.deleted = 0
                                 """, classOf[RbFinance])
      //      .setParameter("eventTypeId", e.getEventType.getId)
      .setParameter("eventId", e.getId)
      .getSingleResult
    result.getId
  }

  /**
   * Получение uuid оргструктуры
   */
//  def getOrgStructureUuid(a: Action) = {
//    em.createQuery(
//      """
//         SELECT u.uuid
//         FROM   Action a, ActionType at, ActionProperty ap, APValueOrgStructure apos, OrgStructure os, UUID u
//         WHERE  a.id = :action1
//          AND a.actionType.id = at.id
//          AND ap.action.id = :action2
//          AND ap.id = apos.id
//          AND apos.value.id = os.id
//          AND os.uuid.id = u.id
//
//      """, classOf[String])
//      .setParameter("action1", a.getId)
//      .setParameter("action2", a.getId)
//      .getSingleResult
//  }


  // ---- Секция кастомных запросов

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


  val ActiveEventsByDepartmentIdAndDoctorIdBetweenDatesQuery = """
    SELECT %s, MAX(a.createDatetime), MAX(e.executor.id), MAX(org.masterDepartment.id)
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
      bed.value.id = org.id
      %s
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
    %s
    %s
                                                               """
  /*
       %s
    GROUP BY e
   */
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

  /*
  JOIN (select MAX(a1.createDatetime) maxCDT
       a1.actionType,
       a1.event
        from  Action a1
        where a1.deleted = 0
        group by a1.actionType, a1.event) maxDateAction

     a.createDatetime = maxDateAction.maxCDT
     AND a.actionType = maxDateAction.actionType
     AND a.event = maxDateAction.event
  * */
  /*  val LastAssessmentByEventIdsQuery = """
   SELECT e, ap, MAX(a.createDatetime)
   FROM
     ActionProperty ap
     JOIN ap.action a
     JOIN a.event e
   WHERE
     e.id IN :eventIds
   AND
     a.actionType.clazz = %s
   AND
     a.deleted = 0
     group by e
 """.format(i18n("db.action.assessmentClass")) */

  val LastAssessmentByEventIdsQuery = """
    SELECT e, a
    FROM
      Action a
      JOIN a.event e
    WHERE
      e.id IN :eventIds
    AND
      a.actionType.clazz = '%s'
    AND
      a.deleted = 0
    AND
      a.createDatetime = (
      SELECT MAX(a1.createDatetime)
      FROM
        Action a1
      WHERE
        a1.event = e
      AND
        a1.actionType.clazz = '%s'
      AND
        a1.deleted = 0
      )
                                      """.format(i18n("db.action.assessmentClass"), i18n("db.action.assessmentClass"))

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
      a.createDatetime ASC
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

  val AllDiagnosticsByEventIdAndFiltredByCodeQuery = """
    SELECT %s
    FROM
      Action a
    WHERE
      a.actionType.clazz = %s
    %s
    AND
      a.deleted = 0
    %s
                                                     """

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

  //TODO: тип диагноза установлен как заключительный (согласовать!)

  val AllAppealsWithFilterQuery = """
  SELECT %s
  FROM
    Event e
    LEFT JOIN e.diagnostics dia
    LEFT JOIN dia.diagnosis dias
    LEFT JOIN dias.mkb mkb
  WHERE
    e.deleted = 0
  %s
    AND (dia.diagnosisType IS NULL OR dia.diagnosisType.id = 1)
    AND (dia.deleted IS NULL OR dia.deleted = 0)
  %s
  %s
                                  """

  val AllAppealsWithFilterExQuery = """
  SELECT %s
  FROM
    Event e
  WHERE
    e.deleted = 0
  %s
  %s
  %s
                                    """

  val MainDiagnosisQuery = """
    SELECT e, dia, mkb
    FROM
      Event e
        JOIN e.diagnostics dia
        JOIN dia.diagnosis dias
        JOIN dias.mkb mkb
    WHERE
      e.id IN :ids
    AND (dia.diagnosisType IS NULL OR dia.diagnosisType.id = '%s')
    AND (dia.deleted IS NULL OR dia.deleted = 0)
    %s
    GROUP BY e
                           """

  val ClinicalDiagnosisQuery = """
    SELECT e, ap, apv.value
    FROM
      ActionProperty ap
        JOIN ap.actionPropertyType apt
        JOIN ap.action a
        JOIN a.event e
        JOIN a.actionType at,
      APValueString apv
    WHERE
      e.id IN :ids
    AND ap.id = apv.id.id
    AND at.code = '%s'
    AND apt.name = '%s'
    AND apv.value IS NOT NULL
    %s
    GROUP BY e
                               """
  val AttendantDiagnosisQuery = """
    SELECT e, ap, apv.mkb
    FROM
      ActionProperty ap
        JOIN ap.actionPropertyType apt
        JOIN ap.action a
        JOIN a.event e
        JOIN a.actionType at,
      APValueMKB apv
    WHERE
      e.id IN :ids
    AND ap.id = apv.id.id
    AND at.code = '%s'
    AND apt.name = '%s'
    AND apv.mkb IS NOT NULL
    %s
    GROUP BY e
                                """

  val AllMkbWithFilterQuery = """
  SELECT DISTINCT %s
  FROM
  Mkb mkb
  %s
  %s
                              """

  val AllThesaurusWithFilterQuery = """
  SELECT DISTINCT %s
  FROM
  Thesaurus r
  %s
  %s
                                    """

  val LastActionByTypeCodeAndAPTypeNameQuery = """
  SELECT a2
  FROM
    Action a2
  WHERE
    a2.createDatetime = (
  SELECT MAX(a.createDatetime)
    FROM
      Action a
    WHERE
      a.event.id = :id
    AND
      exists (
        SELECT at
        FROM
          ActionType at
        WHERE
          at = a.actionType
        AND
          at.code = :code
        AND
          at.deleted = 0
      )
    AND
      exists (
        SELECT apt
        FROM
          ActionProperty ap,
          ActionPropertyType apt
        WHERE
          ap.action = a
        AND
          ap.actionPropertyType.id = apt.id
        AND
          apt.name = :name
        AND
          ap.deleted = 0
        AND
          apt.deleted = 0
      )
    AND
      a.deleted = 0
  )
                                               """

  val DiagnosisByMkbQuery = """
  SELECT DISTINCT d
  FROM
    Diagnosis d
  WHERE
    d.mkb = :mkb
                            """
  //
  val OrgStructureSubQuery = """
    SELECT e, apv.value, MAX(a.createDatetime)
    FROM
      ActionProperty ap
        JOIN ap.action a
        JOIN a.event e
        JOIN a.actionType at
        JOIN ap.actionPropertyType apt,
      APValueOrgStructure apv
    WHERE
      e.id IN :ids
    AND
      at.id = '%s'
    AND
      apt.id IN (
         SELECT cap.actionPropertyType.id
         FROM RbCoreActionProperty cap
         WHERE cap.actionType.id = at.id
         AND cap.id = '%s'
      )
    AND ap.id = apv.id.id
    AND apv.value IS NOT NULL
    %s
    GROUP BY e
                             """
}