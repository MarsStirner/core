package ru.korus.tmis.core.database.common

import java.lang.{Double => JDouble}
import java.util.{Date, List}
import java.{util => ju}
import javax.ejb.{EJB, Stateless}
import javax.persistence.{EntityManager, PersistenceContext, TemporalType}

import grizzled.slf4j.Logging
import ru.korus.tmis.core.data._
import ru.korus.tmis.core.database.bak.BakDiagnosis
import ru.korus.tmis.core.entity.model._
import ru.korus.tmis.core.filter.ListDataFilter
import ru.korus.tmis.core.indicators.IndicatorValue
import ru.korus.tmis.scala.util.{CAPids, ConfigManager, General, I18nable}

import scala.Predef._
import scala.collection.JavaConversions._
import scala.collection.convert.Wrappers.MapWrapper
import scala.collection.immutable.ListMap
import scala.collection.mutable
import scala.language.reflectiveCalls

//
@Stateless
//@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
class DbCustomQueryBean
  extends DbCustomQueryLocal
  with Logging
  with I18nable
  with CAPids {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  @EJB
  private var eventBean: DbEventBeanLocal = _

  @EJB
  private var orgStructure: DbOrgStructureBeanLocal = _

  @EJB
  private var dbActionPropertyBean: DbActionPropertyBeanLocal = _

  def getTakenTissueByBarcode(id: Int, period: Int) = {
    val result = em.createQuery(takenTissueByBarcodeQuery, classOf[TakenTissue])
      .setParameter("barcode", id)
      .setParameter("period", period)
      .getResultList.headOption
    result.map {
      it => it
    }.orNull
  }

  def getActiveEventsForDoctor(id: Int) = {
    val result = em.createQuery(ActiveEventsByDoctorIdQuery,
      classOf[Event])
      .setParameter("doctorId", id)
      .getResultList


    result
  }

  def getActiveEventsForDepartment(id: Int) = {
    val result = em.createQuery(ActiveEventsByDepartmentIdQuery,
      classOf[Array[AnyRef]])
      .setParameter("departmentId", id)
      .getResultList

    val events = result.map((e) => e(0).asInstanceOf[Event])

    events
  }

  def getActiveEventsForDepartmentAndDoctor(page: Int,
                                            limit: Int,
                                            sortingField: String,
                                            sortingMethod: String,
                                            filter: Object,
                                            records: (java.lang.Long) => java.lang.Boolean) = {

    val sorting = if (sortingField.compareTo("bed") == 0 || sortingField.compareTo("number") == 0) ""
    else filter.asInstanceOf[PatientsListRequestDataFilter].toSortingString(sortingField, sortingMethod)

    val queryStr: QueryDataStructure = filter match {
      case f: PatientsListRequestDataFilter => f.toQueryStructure()
      case _ => new QueryDataStructure()
    }

    val queryLastMovesByEvents = LastMovesByEvents.format(queryStr.query)
    val query: String = ActiveEventsByDepartmentIdAndDoctorIdBetweenDatesQueryEx
      .format(i18n("db.apt.moving.codes.hospitalBed"),
        i18n("db.apt.moving.codes.hospOrgStruct"),
        sorting)

    val typedLastMoves = em.createQuery(queryLastMovesByEvents, classOf[List[Integer]])
    typedLastMoves.setParameter("flatCodes", asJavaCollection(Set(i18n("db.action.admissionFlatCode"),
      i18n("db.action.movingFlatCode"))))
    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => {
        if (queryLastMovesByEvents.contains(qdp.name))
          typedLastMoves.setParameter(qdp.name, qdp.value)
      })
    }

    // if (limit * (page + 1) > 0) typedLastMoves.setMaxResults(limit * (page + 1))
    var lastMovedsId = typedLastMoves.getResultList
    val typed = em.createQuery(query, classOf[ActionProperty])

    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => {
        if (query.contains(qdp.name))
          typed.setParameter(qdp.name, qdp.value)
      })
    }

    typed.setParameter("lastMovedsId", lastMovedsId)

    typed.setParameter("transferCodes", asJavaCollection(Set(i18n("db.apt.moving.codes.orgStructTransfer"),
      i18n("db.apt.received.codes.orgStructDirection"))))

    //typed.setParameter("flatCodes", asJavaCollection(Set(i18n("db.action.admissionFlatCode"), i18n("db.action.movingFlatCode"))))
    //if (limit * (page + 1) > 0) typed.setMaxResults(limit * (page + 1))

    var result = if (lastMovedsId.isEmpty) new java.util.LinkedList[ActionProperty] else typed.getResultList

    var actions = result.foldLeft(mutable.LinkedHashMap.empty[Action, ju.Map[ActionProperty, ju.List[APValue]]])(
      (map, e) => {
        var entryMap = Map.empty[ActionProperty, ju.List[APValue]]
        entryMap += (e -> dbActionPropertyBean.getActionPropertyValue(e))
        map += (e.getAction -> entryMap)

        map
      })
    //Перепишем общее количество записей для запроса
    if (actions != null) records(actions.size)

    if (sortingField.compareTo("bed") == 0) {
      //предобработка
      val sorted = actions.toList.sortWith((a, b) => getSortingConditionByMethod(sortingField, sortingMethod, a._2, b._2))
      actions = sorted.foldLeft(mutable.LinkedHashMap.empty[Action, ju.Map[ActionProperty, ju.List[APValue]]])((map, e) => map += (e._1 -> e._2))
    } else if (sortingField.compareTo("number") == 0) {
      val sorted = actions.toList.sortWith((a, b) => getSortingConditionByMethod(sortingField, sortingMethod, a._1.getEvent, b._1.getEvent))
      actions = sorted.foldLeft(mutable.LinkedHashMap.empty[Action, ju.Map[ActionProperty, ju.List[APValue]]])((map, e) => map += (e._1 -> e._2))
    }
    //проведем  разбиение на страницы вручную (необходимо чтобы не использовать отдельный запрос на recordcounts)
    if ((actions.size - limit * (page + 1)) > 0) {
      actions.dropRight(actions.size - limit * (page + 1)).drop(page * limit)
    }
    else
      actions.drop(page * limit)
  }

  def getAdmissionsByEvents(events: java.util.List[Event]) = {
    getEntitiesByEvents[Action](events, AdmissionsByEventIdsQuery)
  }

  def getHospitalBedsByEvents(events: java.util.List[Event]) = {
    getEntitiesByEvents[ActionProperty](events, HospitalBedsByEventIdsQuery)
  }

  def getOrgStructureByReceivedActionByEvents(events: java.util.List[Event]) = {
    getEntitiesByEvents[ActionProperty](events, OrgStructureFromReceivedByEventIdsQuery)
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
      return mutable.LinkedHashMap.empty[Event, T]
    }

    val ids = events.map((event) => event.getId.intValue)
    val result = em.createQuery(query,
      classOf[Array[AnyRef]])
      .setParameter("eventIds",
        asJavaCollection(ids))
      .getResultList

    result.foldLeft(mutable.LinkedHashMap.empty[Event, T])(
      (map, pair) => {
        val event = pair(0).asInstanceOf[Event]
        val entity = pair(1).asInstanceOf[T]

        map.put(event, entity)
        map
      }
    )
  }

  def getAllAssessmentsByEventId(eventId: Int) = {
    getAllActionsByQueryAndEventId(AllAssessmentsByEventIdQuery, eventId)
  }

  def getLastAssessmentByEvents(events: ju.List[Event]) = {
    getEntitiesByEvents[Action](events, LastAssessmentByEventIdsQuery)
  }

  def getAllDiagnosticsByEventId(eventId: Int) = {
    getAllActionsByQueryAndEventId(AllDiagnosticsByEventIdQuery.format(
      "a",
      i18n("db.action.diagnosticClass")),
      eventId)
  }

  def getAllDiagnosticsWithFilter(page: Int, limit: Int, sorting: String, filter: Object) = {

    val queryStr: QueryDataStructure = filter match {
      case f: DiagnosticsListRequestDataFilter => f.toQueryStructure()
      case _ => new QueryDataStructure()
    }

    val query = if (queryStr.query.trim().toUpperCase.startsWith("AND")) queryStr.query.trim.substring(3) else queryStr.query

    val typed = em.createQuery(AllDiagnosticsByEventIdAndFiltredByCodeQuery.format("a", query, sorting), classOf[Action])
      .setMaxResults(limit)
      .setFirstResult(limit * page)

    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }

    val result = typed.getResultList

    result
  }

  def getCountDiagnosticsWithFilter(filter: Object) = {

    val queryStr: QueryDataStructure = filter match {
      case f: DiagnosticsListRequestDataFilter => f.toQueryStructure()
      case _ => new QueryDataStructure()
    }

    val query = if (queryStr.query.trim().toUpperCase.startsWith("AND")) queryStr.query.trim.substring(3) else queryStr.query

    val typed = em.createQuery(AllDiagnosticsByEventIdAndFiltredByCodeQuery.format("count(a)", query, ""), classOf[Long])

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

    seqAsJavaList(indicators)
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
    import General.catchy

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

    val queryStr: QueryDataStructure = filter match {
      case f: AppealSimplifiedRequestDataFilter => f.toQueryStructure
      case _ => new QueryDataStructure()
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
    var flgAppealNumberSort: Boolean = false
    if (sortingField.compareTo("e.externalId") == 0) {
      flgAppealNumberSort = true
    } else if (sortingField.compareTo("department") != 0)
      sorting = "ORDER BY %s %s".format(sortingField, sortingMethod)
    else
      flgDepartmentSort = true
    if (sortingField.contains("%s")) sorting = "ORDER BY %s".format(sortingField.format(sortingMethod, sortingMethod, sortingMethod)) //костыль для докторв

    val allOrgStructures: List[OrgStructure] = orgStructure.getAllOrgStructures
    val default_org: OrgStructure = allOrgStructures.filter(element => element.getType == 4).toList.get(0) //Приемное отделение
    val default_org_poly = allOrgStructures.find(s => s.getId.equals(ConfigManager.Common.defaultPoliclinicOrgStructureId))
        .getOrElse(default_org)

    val queryStr: QueryDataStructure = filter match {
      case f: AppealSimplifiedRequestDataFilter => {
        if (f.diagnosis != null && !f.diagnosis.isEmpty) {
          diagnostic_filter = "AND upper(mkb.diagID) LIKE upper(:diagnosis)\n"
          ap_string_filter = "AND upper(apv.value) LIKE upper(:diagnosis)\n"
          ap_mkb_filter = "AND upper(apv.mkb.diagID) LIKE upper(:diagnosis)\n"
          flgDiagnosesSwitched = true
        }
        if (f.departmentId > 0 &&
          f.departmentId != default_org.getId.intValue()) {
          department_filter = " AND exists ( SELECT val.id FROM APValueOrgStructure val WHERE val.id.id = ap.id AND val.value.id = :departmentId)"
          flgDepartmentSwitched = true
        }
        f.toQueryStructure
      }
      case _ => new QueryDataStructure()
    }

    //Получаем список всех обращений (без диагнозов) (сортированный)
    var typed = em.createQuery(AllAppealsWithFilterExQuery.format("e", queryStr.query, "", sorting), classOf[Event])
    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }
    var res = typed.getResultList

    val ret_value = new java.util.LinkedHashMap[Event, java.util.Map[Object, Object]]
    var org_value = new java.util.HashMap[Event, OrgStructure]

    if (res.size() > 0) {
      val ids = new java.util.HashSet[java.lang.Integer]
      res.foreach(e => ids.add(e.getId))

      //Получение отделения из последнего экшена движения
      val depArrayTypedMoves = em.createQuery(OrgStructureSubQueryMoves, classOf[List[Integer]])
        .setParameter("ids", asJavaCollection(ids))
        .setParameter("flatCode", setAsJavaSet(Set(i18n("db.action.movingFlatCode"), i18n("db.action.admissionFlatCode"))))
      val lastMovesIds = depArrayTypedMoves.getResultList

      val depArray = if (lastMovesIds.isEmpty) {
        new java.util.LinkedList[ActionProperty]()
      }
      else {
        val depArrayTyped = em.createQuery(OrgStructureSubQuery.format(department_filter), classOf[ActionProperty])
          .setParameter("actionIds", lastMovesIds)
          .setParameter("code", i18n("db.apt.moving.codes.hospOrgStruct"))
        if (flgDepartmentSwitched)
          depArrayTyped.setParameter("departmentId", filter.asInstanceOf[AppealSimplifiedRequestDataFilter].departmentId)
        depArrayTyped.getResultList
      }

      org_value = depArray.foldLeft(new java.util.HashMap[Event, OrgStructure])(
        (map, pair) => {

          val ap_values = dbActionPropertyBean.getActionPropertyValue(pair)
          val department: OrgStructure =
            if (ap_values != null && ap_values.size() > 0)
              ap_values.get(0).getValue.asInstanceOf[OrgStructure]
            else null
          map.put(pair.getAction.getEvent, department)
          map
        }
      )

      //Получение диагнозов
      val map = new java.util.LinkedHashMap[java.lang.Integer, Object]()
      map.put(0, (MainDiagnosisQuery, i18n("db.diagnostics.diagnosisType.id.clinical"), ""))
      map.put(1, (MainDiagnosisQuery, i18n("db.diagnostics.diagnosisType.id.main"), ""))
      map.put(2, (ClinicalDiagnosisQuery, "4501", i18n("appeal.diagnosis.diagnosisKind.mainDiagMkb")))
      map.put(3, (AttendantDiagnosisQuery, "1_1_01", i18n("appeal.diagnosis.diagnosisKind.mainDiagMkb")))
      map.put(4, (AttendantDiagnosisQuery, "4201", i18n("appeal.diagnosis.diagnosisKind.diagReceivedMkb")))

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
          f.head match {
            case e: Event => ids remove e.getId
          }

          val internalMap = new java.util.HashMap[Object, Object]
          internalMap.put(f(1), f(2))
          ret_value.put(f(0).asInstanceOf[Event], internalMap)

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
    val ret_value_sorted = mutable.LinkedHashMap.empty[Event, Object]
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
          if ("policlinic".equals(e.getEventType.getRequestType.getCode) || "diagnostic".equals(e.getEventType.getRequestType.getCode)) {
            ret_value_sorted.put(e, (pos, if (e.getOrgStructure == null) default_org_poly else e.getOrgStructure))
          } else {
            ret_value_sorted.put(e, (pos, default_org))
          }
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
    } else if (flgAppealNumberSort) {
      //Cортировка по AppealNumber (externalId) как int
      try {
        //TODO
        if (sortingMethod.compareTo("desc") == 0) //desc
          ListMap(ret_value_sorted.toList
            .sortWith(_._1.getExternalId.split("[^\\d]+").last.toInt > _._1.getExternalId.split("[^\\d]+").last.toInt)
            .sortWith(_._1.getExternalId.substring(0, 4).toInt > _._1.getExternalId.substring(0, 4).toInt): _*)
        else //asc
          ListMap(ret_value_sorted.toList.sortBy(m => (m._1.getExternalId.substring(0, 4).toInt, m._1.getExternalId.split("[^\\d]+").last.toInt)): _*)
      } catch {
        case e: StringIndexOutOfBoundsException => {
          ListMap(ret_value_sorted.toList: _*)
        }
      }
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
    map.put(0, (MainDiagnosisQuery, i18n("db.diagnostics.diagnosisType.id.clinical"), "")) //этих двух диагнозов тут не было
    map.put(1, (MainDiagnosisQuery, i18n("db.diagnostics.diagnosisType.id.main"), "")) //
    map.put(2, (ClinicalDiagnosisQuery, "4501", i18n("appeal.diagnosis.diagnosisKind.mainDiagMkb"))) //"Основной клинический диагноз"))
    map.put(3, (AttendantDiagnosisQuery, "1_1_01", i18n("appeal.diagnosis.diagnosisKind.mainDiagMkb")))
    map.put(4, (AttendantDiagnosisQuery, "4201", i18n("appeal.diagnosis.diagnosisKind.diagReceivedMkb")))

    var i = 0
    while (ids.size() > 0 && i <= 4) {
      val map_value = map.get(Integer.valueOf(i)).asInstanceOf[(String, String, String)]
      val typed2 = i match {
        case 0 | 1 => em.createQuery(map_value._1.format(map_value._2, diagnostic_filter), classOf[Array[AnyRef]])
        case 2 => em.createQuery(map_value._1.format(map_value._2, map_value._3, ap_string_filter), classOf[Array[AnyRef]])
        case _ => em.createQuery(map_value._1.format(map_value._2, map_value._3, ap_mkb_filter), classOf[Array[AnyRef]])
      }
      val res2 = typed2.setParameter("ids", asJavaCollection(ids)).getResultList

      res2.foreach(f => {
        val internalMap = new java.util.HashMap[Object, Object]
        internalMap.put(f(1), f(2))
        ret_value.put(f(0).asInstanceOf[Event], internalMap)

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
    import General.catchy

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

  def getDistinctMkbsWithFilter(sorting: String, filter: ListDataFilter) = {

    val retValue = new java.util.HashMap[String, java.util.Map[String, Mkb]]
    if (filter.asInstanceOf[MKBListRequestDataFilter].display) {
      val queryStr = filter.toQueryStructure
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

  def getAllMkbsWithFilter(page: Int, limit: Int, sorting: String, filter: ListDataFilter) = {

    val queryStr = filter.toQueryStructure

    if (queryStr.data.size() > 0) {
      if (queryStr.query.indexOf("AND ") == 0) {
        queryStr.query = "WHERE " + queryStr.query.substring("AND ".length())
      }
    }

    val typed = em.createQuery(AllMkbWithFilterQuery.format("mkb", queryStr.query, sorting), classOf[Mkb])
      .setMaxResults(limit)
      .setFirstResult(limit * page)

    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }

    val result = typed.getResultList

    result
  }

  def getCountOfMkbsWithFilter(filter: Object) = {

    val queryStr: QueryDataStructure = filter match {
      case f: MKBListRequestDataFilter => f.toQueryStructure
      case _ => new QueryDataStructure
    }

    if (queryStr.data.size() > 0) {
      if (queryStr.query.indexOf("AND ") == 0) {
        queryStr.query = "WHERE " + queryStr.query.substring("AND ".length())
      }
    }

    val typed = em.createQuery(AllMkbWithFilterQuery.format("count(mkb)", queryStr.query, ""), classOf[Long])

    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }

    typed.getSingleResult
  }

  def getCountOfThesaurusWithFilter(filter: Object) = {
    val queryStr: QueryDataStructure = filter match {
      case f: ThesaurusListRequestDataFilter => f.toQueryStructure()
      case _ => new QueryDataStructure()
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


  def getAllThesaurusWithFilter(page: Int, limit: Int, sorting: String, filter: ListDataFilter) = {

    val queryStr = filter.toQueryStructure
    if (queryStr.data.size() > 0) {
      if (queryStr.query.indexOf("AND ") == 0) {
        queryStr.query = "WHERE " + queryStr.query.substring("AND ".length())
      }
    }
    val typed = em.createQuery(AllThesaurusWithFilterQuery.format("r", queryStr.query, sorting), classOf[Thesaurus])
      .setMaxResults(limit)
      .setFirstResult(limit * page)
    if (queryStr.data.size() > 0) {
      queryStr.data.foreach(qdp => typed.setParameter(qdp.name, qdp.value))
    }
    val result = typed.getResultList

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

  /**
   * Сложная сортировка
   * @param field Поле сортировки
   * @param method Метод сортировки
   * @param a Первый параметр
   * @param b Второй параметр
   * @return
   */
  private def getSortingConditionByMethod(field: String, method: String, a: AnyRef, b: AnyRef) = {

    field match {
      case "bed" => {
        val aVal = getBedValueForSortingCondition(a)
        val bVal = getBedValueForSortingCondition(b)

        if (aVal == 0) true
        else {
          if (bVal == 0) false
          else {
            if (method.compareTo("desc") == 0)
              aVal > bVal
            else
              bVal > aVal
          }
        }
      }
      case "number" => {
        if (method.compareTo("desc") == 0)
          (a.asInstanceOf[Event].getExternalId.substring(0, 4).toInt > b.asInstanceOf[Event].getExternalId.substring(0, 4).toInt) ||
            (a.asInstanceOf[Event].getExternalId.substring(0, 4).toInt == b.asInstanceOf[Event].getExternalId.substring(0, 4).toInt && a.asInstanceOf[Event].getExternalId.split("[^\\d]+").last.toInt > b.asInstanceOf[Event].getExternalId.split("[^\\d]+").last.toInt)
        else
          (b.asInstanceOf[Event].getExternalId.substring(0, 4).toInt > a.asInstanceOf[Event].getExternalId.substring(0, 4).toInt) ||
            (b.asInstanceOf[Event].getExternalId.substring(0, 4).toInt == a.asInstanceOf[Event].getExternalId.substring(0, 4).toInt && b.asInstanceOf[Event].getExternalId.split("[^\\d]+").last.toInt > a.asInstanceOf[Event].getExternalId.split("[^\\d]+").last.toInt)
      }
      case _ => false
    }
  }

  private def getBedValueForSortingCondition(a: AnyRef) = {
    a match {
      case b: MapWrapper[ActionProperty, List[APValue]] => {
        val apvs = a.asInstanceOf[MapWrapper[ActionProperty, List[APValue]]]
        if (apvs != null && apvs.size > 0) {
          val values = apvs.iterator.next()._2
          if (values != null && values.size() > 0 && values.get(0).isInstanceOf[APValueHospitalBed])
            values.get(0).asInstanceOf[APValueHospitalBed].getValue.getId.intValue()
          else 0
        } else 0
      }
      case _ => 0
    }
  }

  // ---- Секция кастомных запросов

  val HeightQuery =
    """
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

  val WeightQuery =
    """
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

  val ActiveEventsQuery =
    """
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

  val ActiveEventsByDoctorIdQuery = ActiveEventsQuery +
    """
    AND
      e.executor.id = :doctorId
    """

  val ActiveEventsByDepartmentIdQuery =
    """
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


  //Спецификация https://docs.google.com/spreadsheet/ccc?key=0AgE0ILPv06JcdEE0ajBZdmk1a29ncjlteUp3VUI2MEE#gid=0
  val ActiveEventsByDepartmentIdAndDoctorIdBetweenDatesQuery =
    """
SELECT %s
FROM
  ActionProperty ap
    JOIN ap.action a
    JOIN ap.actionPropertyType apt
    JOIN a.actionType at
    JOIN a.event e
WHERE
  ( e.execDate IS NULL OR e.execDate > :endDate )
AND
  e.deleted = '0'
AND
  e.id NOT IN (
    SELECT leaved.event.id
    FROM Action leaved
    WHERE
      leaved.actionType.flatCode = '%s'
    AND
      leaved.event.id = e.id
    AND
      leaved.createDatetime < :endDate
  )
%s
AND
  a.begDate <= :endDate
AND
(
  (
    (a.endDate IS NULL OR a.endDate >= :endDate)
    AND
      at.flatCode = '%s'
    AND
      apt.id IN (
        SELECT cap.actionPropertyType.id
        FROM RbCoreActionProperty cap
        WHERE cap.id IN :capIds
      )
    AND
    (
      exists (
        SELECT orgBed.id
        FROM
          APValueHospitalBed bed
            JOIN bed.value orgBed
        WHERE
          bed.id.id = ap.id
        AND
          orgBed.masterDepartment.id = :departmentId
      )
      OR
        exists (
          SELECT val.id
          FROM APValueOrgStructure val
            JOIN val.value orgVal
          WHERE
            val.id.id = ap.id
          AND
            orgVal.id = :departmentId
        )
    )
  )
  OR (
    at.id = '%s'
    AND
      apt.id IN (
        SELECT cap2.actionPropertyType.id
        FROM RbCoreActionProperty cap2
        WHERE cap2.id = '%s'
      )
    AND
      NOT exists (
        SELECT moving
        FROM Action moving
        WHERE
          moving.actionType.flatCode = '%s'
        AND
          moving.event.id = e.id
        AND
          moving.createDatetime < :endDate
      )
    AND
      exists (
        SELECT val2.id
        FROM APValueOrgStructure val2
          JOIN val2.value orgVal2
        WHERE
          val2.id.id = ap.id
        AND
          orgVal2.id = :departmentId
      )
  )
)
AND
  a.deleted = '0'
AND
  ap.deleted = '0'
%s
%s
    """
  /*      в спеке нет проверки на выписку https://docs.google.com/spreadsheet/ccc?key=0AgE0ILPv06JcdEE0ajBZdmk1a29ncjlteUp3VUI2MEE#gid=0
  AND a.event.id NOT IN (
        SELECT leaved.event.id
        FROM Action leaved
        WHERE leaved.actionType.flatCode = '%s'
        AND leaved.event.id = a.event.id
        AND leaved.createDatetime < :endDate
        AND leaved.deleted = 0
    )
   */

  val LastMovesByEvents =
    """
SELECT Max(a.id)
FROM Action a
WHERE (a.event.execDate IS NULL OR a.event.execDate > :endDate )
%s
AND a.event.deleted = '0'
AND a.begDate <= :endDate
AND a.actionType.flatCode IN :flatCodes
AND a.deleted = '0'
GROUP BY a.event.id
    """

  val ActiveEventsByDepartmentIdAndDoctorIdBetweenDatesQueryEx =
    """
SELECT ap
FROM ActionProperty ap
WHERE ap.action.id IN :lastMovedsId
AND
(
    (
        ap.actionPropertyType.code IN :transferCodes
        AND ap.action.endDate < :endDate
        AND exists (
            SELECT valA.id
            FROM APValueOrgStructure valA
            WHERE valA.id.id = ap.id
            AND valA.value.id = :departmentId
        )
    )

    OR
    (
        ap.actionPropertyType.code = '%s'
        AND exists (
              SELECT ap3.id
              FROM ActionProperty ap3,
                   APValueOrgStructure valB
              WHERE ap3.action.id = ap.action.id
              AND ap3.actionPropertyType.code = '%s'
              AND valB.id.id = ap3.id
              AND valB.value.id = :departmentId
            )
        AND
        (
          (ap.action.endDate IS NULL OR  ap.action.endDate > :endDate)
          OR
          (
            exists (
               SELECT ap2.id
               FROM ActionProperty ap2,
                    APValueOrgStructure val2
               WHERE ap2.action.id = ap.action.id
               AND ap2.actionPropertyType.code IN :transferCodes
               AND ap2.id = val2.id.id
            )
            AND ap.action.endDate <= :endDate
          )
        )
    )
)
AND ap.deleted = 0
%s
    """

  val ActionsByEventIdsAndFlatCodeQuery =
    """
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

  val HospitalBedsByEventIdsQuery =
    """
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

  val OrgStructureFromReceivedByEventIdsQuery =
    """
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
      i18n("db.action.admissionFlatCode"),
      "Отделение поступления")

  val AnamnesesByEventIdsQuery =
    """
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

  val AllergoAnamnesesByEventIdsQuery =
    """
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

  val LastAssessmentByEventIdsQuery =
    """
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

  val DiagnosesByEventIdsQuery =
    """
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

  val DiagnosisSubstantiationByEventIdsQuery =
    """
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

  val AllAssessmentsByEventIdQuery =
    """
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

  val AllDiagnosticsByEventIdQuery =
    """
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

  val AllDiagnosticsByEventIdAndFiltredByCodeQuery =
    """
    SELECT %s
    FROM
      Action a
    WHERE
    %s
    AND
      a.deleted = 0
    %s
    """

  val IndicatorByEventIdAndIndicatorNameQuery =
    """
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

  val TreatmentActionsByEventId =
    """
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
  val filterTimePeriod =
    """
      AND (a.begDate IS NULL OR a.begDate BETWEEN :beginDate AND :endDate)
      AND (a.endDate IS NULL OR a.endDate BETWEEN :beginDate AND :endDate)
    """

  val takenTissueByBarcodeQuery =
    """
      SELECT t from TakenTissue t where t.barcode = :barcode and t.period = :period
    """

  //TODO: тип диагноза установлен как заключительный (согласовать!)

  val AllAppealsWithFilterQuery =
    """
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

  val AllAppealsWithFilterExQuery =
    """
  SELECT %s
  FROM
    Event e
  WHERE
    e.deleted = 0
  %s
  %s
  %s
    """

  val MainDiagnosisQuery =
    """
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

  val ClinicalDiagnosisQuery =
    """
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
    AND apt.code = '%s'
    AND apv.value IS NOT NULL
    %s
    GROUP BY e
    """
  val AttendantDiagnosisQuery =
    """
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
    AND apt.code = '%s'
    AND apv.mkb IS NOT NULL
    %s
    GROUP BY e
    """

  val AllMkbWithFilterQuery =
    """
  SELECT DISTINCT %s
  FROM
  Mkb mkb
  %s
  %s
    """

  val AllThesaurusWithFilterQuery =
    """
  SELECT DISTINCT %s
  FROM
  Thesaurus r
  %s
  %s
    """

  val LastActionByTypeCodeAndAPTypeNameQuery =
    """
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

  val DiagnosisByMkbQuery =
    """
  SELECT DISTINCT d
  FROM
    Diagnosis d
  WHERE
    d.mkb = :mkb
    """
  //
  val OrgStructureSubQueryMoves =
    """
    SELECT a.id
    FROM Action a
    WHERE a.event.id IN :ids
    AND a.actionType.flatCode IN :flatCode
    AND a.deleted = '0'
    ORDER BY a.createDatetime DESC"""

  val OrgStructureSubQuery =
    """
      SELECT ap
      FROM ActionProperty ap
      WHERE
        ap.action.id IN :actionIds
      AND ap.actionPropertyType.code = :code
      %s
      ORDER BY ap.id ASC
    """

  /**
   * @see
   * @param action
   */
  def getBakDiagnosis(action: Action): BakDiagnosis = {
    val res = em.createNativeQuery(
      """
         SELECT MKB.DiagID, MKB.DiagName
          FROM Action
          JOIN ActionProperty ON ActionProperty.action_id = Action.id
          JOIN ActionPropertyType ON ActionPropertyType.id = ActionProperty.type_id
          JOIN ActionProperty_MKB ON ActionProperty.id = ActionProperty_MKB.id
          JOIN MKB ON MKB.id = ActionProperty_MKB.value
          WHERE
          Action.createDatetime <= ?
          AND Action.event_id = ?
          AND ActionPropertyType.code = ?
          AND Action.deleted = 0
          ORDER BY
          Action.createDatetime DESC
          LIMIT 0, 1 """)
      .setParameter(1, action.getCreateDatetime)
      .setParameter(2, action.getEvent.getId)
      .setParameter(3, "mainDiagMkb")
      .getSingleResult

    if (res != null) {
      val objects: Array[Object] = res.asInstanceOf[Array[Object]]
      new BakDiagnosis(objects(0).asInstanceOf[java.lang.String], objects(1).asInstanceOf[java.lang.String])
    } else {
      null
    }
  }
}