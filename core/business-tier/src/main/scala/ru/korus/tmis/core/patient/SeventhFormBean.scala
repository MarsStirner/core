package ru.korus.tmis.core.patient

import javax.interceptor.Interceptors
import ru.korus.tmis.core.logging.LoggingInterceptor
import grizzled.slf4j.Logging
import ru.korus.tmis.util.{CAPids, I18nable}
import javax.persistence.{EntityManager, PersistenceContext}
import scala.collection.JavaConversions._
import javax.ejb.{TransactionAttribute, TransactionAttributeType, EJB, Stateless}
import ru.korus.tmis.core.exception.CoreException
import java.text.SimpleDateFormat
import java.util.{Calendar, Date}
import ru.korus.tmis.core.entity.model.{Patient, RbHospitalBedProfile, Action}
import ru.korus.tmis.core.data.{FormOfAccountingMovementOfPatientsData, SeventhFormLinearView}


@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class SeventhFormBean extends SeventhFormBeanLocal
with Logging
with I18nable
with CAPids {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  private val msecInDay: Long = 1000 * 60 * 60 * 24
  //милисекунд в сутках
  private val formatter = new SimpleDateFormat("yyyy-MM-dd")

  private val dayHospital = "Дневной стационар"
  private val transferredFromDepartment = "Переведен из отделения"
  private val transferredInDepartment = "Переведен в отделение"
  private val departmentOfLocated = "Отделение пребывания"
  private val appealIsEnd = "Исход госпитализации"
  private val hospitalBed = "койка"

  private val isDeadValue = "умер"
  private val toAnotherHospitalValue = "переведен в другой стационар"
  private val toHourHospitalValue = "выписан в круглосуточный стационар"
  private val toDayHospitalDeadValue = "выписан в дневной стационар"

  private val hospitalBedMale = "1"
  private val hospitalBedFemale = "2"

  //Дата начала текущих мед. суток
  private def getDefaultEndDate() = {

    var today = Calendar.getInstance();
    today.setTime(formatter.parse(formatter.format(new Date())))
    today.set(Calendar.HOUR_OF_DAY, 9); //смещение между астрономическими и медицинскими сутками

    today.getTime
  }

  //Дата начала предыдущих мед. суток
  private def getDefaultBeginDate() = {
    new Date(this.getDefaultEndDate.getTime - msecInDay.longValue()) //предыдущие мед сутки
  }

  //Получение поисковой даты по возрасту пациента
  private def getDateByAge(age: Int, begin: Date) = {

    val now = Calendar.getInstance()
    now.setTime(begin)
    val birth = Calendar.getInstance()
    birth.set(Calendar.YEAR, now.get(Calendar.YEAR) - age)

    birth.getTime
  }

  //Формирование формы 007 (метод обобщает поисковые запросы)
  def fillInSeventhForm(departmentId: Int, beginDate: Date, endDate: Date) = {

    //Вчера, 9 утра
    val bDate = if (beginDate == null) {
      this.getDefaultBeginDate
    } else {
      beginDate
    }
    //Сегодня, 9 утра
    val eDate = if (endDate == null) {
      this.getDefaultEndDate
    } else {
      endDate
    }

    if (bDate.after(eDate)) {
      throw new CoreException("Некорректная дата в методе fillInSeventhForm")
    }

    //Общая информация
    val linear = new SeventhFormLinearView()
    //1. Койки (Профили) развернутые, свернутые
    val profiles = this.getOrgStructureHospitalBedByDepartmentIdBetweenDate(departmentId, bDate, eDate)
    linear.initProfileMapStructure(profiles.keySet) //Инициализация структуры формы
    linear.add(profiles, 0)
    //2. Пациенты на начало истекших суток
    linear.add(this.getCountsMovingActionStateByDepartmentIdAndDate(departmentId, bDate), 1)
    //3. Пациенты на начало текущих суток
    linear.add(this.getCountsMovingActionStateByDepartmentIdAndDate(departmentId, eDate), 2)
    //4. Пациенты у которых АР Патронаж = Да
    linear.add(this.getCountsPatronageMovingActionStateByDepartmentIdAndDate(departmentId, eDate), 3)
    //Свободные койки, мужские и женские
    linear.add(this.getCountsEmptyHospitalBedsByDepartmentIdAndDate(departmentId, eDate, this.hospitalBedMale), 12)
    linear.add(this.getCountsEmptyHospitalBedsByDepartmentIdAndDate(departmentId, eDate, this.hospitalBedFemale), 13)
    //5. Переведен из/в отделение (движение внутри стационара)
    linear.add(this.getTransferredMovingActionsByDepartmentIdAndBetweenDate(departmentId, bDate, eDate, false), 4) //  ???
    linear.add(this.getTransferredMovingActionsByDepartmentIdAndBetweenDate(departmentId, bDate, eDate, true), 5) //  ???

    //6. Поступившие/выписанные в/из отделение
    val now = new Date()
    linear.add(this.getReceivedMovingActionsByDepartmentIdAndBetweenDate(departmentId, bDate, eDate, true, null, false, false), 6)
    linear.add(this.getReceivedMovingActionsByDepartmentIdAndBetweenDate(departmentId, bDate, eDate, false, null, false, false), 14)
    linear.add(this.getReceivedMovingActionsByDepartmentIdAndBetweenDate(departmentId, bDate, eDate, true, this.getDateByAge(17, bDate), true, false), 15)
    linear.add(this.getReceivedMovingActionsByDepartmentIdAndBetweenDate(departmentId, bDate, eDate, true, this.getDateByAge(60, bDate), false, false), 16)
    linear.add(this.getReceivedMovingActionsByDepartmentIdAndBetweenDate(departmentId, bDate, eDate, true, null, false, true), 17)

    //linear.add(this.getDischargedMovingActionsByDepartmentIdAndBetweenDate(departmentId, bDate, eDate, "all"),7)
    //linear.add(this.getDischargedMovingActionsByDepartmentIdAndBetweenDate(departmentId, bDate, eDate, this.dayHospital),8)
    //linear.add(this.getDischargedMovingActionsByDepartmentIdAndBetweenDate(departmentId, bDate, eDate, "Другие стационары"),9)

    //действия типа Выписка
    linear.add(this.getLeavedActionsByDepartmentIdAndBetweenDate(departmentId, bDate, eDate, this.isDeadValue), 10) //умер
    linear.add(this.getLeavedActionsByDepartmentIdAndBetweenDate(departmentId, bDate, eDate, this.toAnotherHospitalValue), 9) //переведен в другой стационар
    linear.add(this.getLeavedActionsByDepartmentIdAndBetweenDate(departmentId, bDate, eDate, this.toHourHospitalValue), 11) //выписан в круглосуточный стационар
    linear.add(this.getLeavedActionsByDepartmentIdAndBetweenDate(departmentId, bDate, eDate, this.toDayHospitalDeadValue), 8) //выписан в дневной стационар
    linear.add(this.getLeavedActionsByDepartmentIdAndBetweenDate(departmentId, bDate, eDate, ""), 7) //всего

    linear
  }


  def getActionsByDepartmentIdAndFlatCodeBetweenDate(departmentId: Int,
                                                     flatCodes: java.util.Set[String],
                                                     beginDate: Date,
                                                     endDate: Date) = {

    val result = em.createQuery(ActionsByDepartmentIdAndFlatCodeBetweenDateQuery, classOf[Action])
      .setParameter("departmentId", departmentId)
      .setParameter("beginDate", beginDate)
      .setParameter("endDate", endDate)
      .setParameter("codes", asJavaCollection(flatCodes))
      .getResultList

    result.foreach(a => em.detach(a))
    result
  }

  def getLeavedActionsByDepartmentIdAndBetweenDate(departmentId: Int,
                                                   beginDate: Date,
                                                   endDate: Date,
                                                   value: String) = {
    val query = DiedByHospitalBedsQuery.format(
      PatientsLievedBetweenDateQuery.format(
        //this.isDeadValue,
        value + "%",
        i18n("db.action.leavingFlatCode"),
        this.appealIsEnd
      ),
      i18n("db.action.movingFlatCode"),
      this.departmentOfLocated //,
      //this.hospitalBed
    )
    //Все умершие за период
    val result = em.createQuery(query,
      classOf[Array[AnyRef]])
      .setParameter("departmentId", departmentId)
      .setParameter("beginDate", beginDate)
      .setParameter("endDate", endDate)
      .getResultList

    //Умершие в отделении по профилю коек


    val map = new java.util.HashMap[RbHospitalBedProfile, Object]
    result.foreach((f) => {
      if (f(0).isInstanceOf[RbHospitalBedProfile]) {
        em.detach(f(0).asInstanceOf[RbHospitalBedProfile])
        em.detach(f(2).asInstanceOf[Patient])

        var obj: (java.lang.Long, java.util.List[Patient]) = (0, new java.util.ArrayList[Patient])
        if (!map.containsKey(f(0).asInstanceOf[RbHospitalBedProfile])) {
          map.put(f(0).asInstanceOf[RbHospitalBedProfile], obj)
        }

        obj = map.get(f(0).asInstanceOf[RbHospitalBedProfile]).asInstanceOf[(java.lang.Long, java.util.List[Patient])]
        var first = obj._1.longValue() + f(1).asInstanceOf[java.lang.Long].longValue()
        var second = obj._2
        second.add(f(2).asInstanceOf[Patient])
        map.remove(f(0).asInstanceOf[RbHospitalBedProfile])
        map.put(f(0).asInstanceOf[RbHospitalBedProfile], (first, second))
      }
    })
    map
  }

  def getTransferredMovingActionsByDepartmentIdAndBetweenDate(departmentId: Int,
                                                              beginDate: Date,
                                                              endDate: Date,
                                                              isIn: Boolean) = {
    var ex1, ex2, subQuery2: String = ""
    if (isIn) {
      ex1 = transferredInDepartment
      ex2 = transferredFromDepartment
      subQuery2 = TransferredInDepartmentDateSubQuery
    } else {
      ex2 = transferredInDepartment
      ex1 = transferredFromDepartment
      subQuery2 = TransferredFromDepartmentDateSubQuery
    }

    val query = UniMovingActionsByDepartmentIdAndBetweenDateQuery.format(
      "orghb.profileId, count(a2.id), e.patient",
      i18n("db.action.movingFlatCode"),
      ex1,
      MovingActionsByHospitalSubQuery.format(
        "=",
        ex2,
        "AND org3.name <> '%s'".format(dayHospital)
      ) + " " + subQuery2,
      "GROUP BY e.patient" //"GROUP BY orghb.profileId"
    )
    val result = em.createQuery(query, classOf[Array[AnyRef]])
      .setParameter("departmentId", departmentId)
      .setParameter("beginDate", beginDate)
      .setParameter("endDate", endDate)
      .getResultList

    val map = new java.util.HashMap[RbHospitalBedProfile, Object]
    result.foreach((f) => {
      if (f(0).isInstanceOf[RbHospitalBedProfile]) {
        em.detach(f(0).asInstanceOf[RbHospitalBedProfile])
        em.detach(f(2).asInstanceOf[Patient])

        var obj: (java.lang.Long, java.util.List[Patient]) = (0, new java.util.ArrayList[Patient])
        if (!map.containsKey(f(0).asInstanceOf[RbHospitalBedProfile])) {
          map.put(f(0).asInstanceOf[RbHospitalBedProfile], obj)
        }

        obj = map.get(f(0).asInstanceOf[RbHospitalBedProfile]).asInstanceOf[(java.lang.Long, java.util.List[Patient])]
        var first = obj._1.longValue() + f(1).asInstanceOf[java.lang.Long].longValue()
        var second = obj._2
        second.add(f(2).asInstanceOf[Patient])
        map.remove(f(0).asInstanceOf[RbHospitalBedProfile])
        map.put(f(0).asInstanceOf[RbHospitalBedProfile], (first, second))
      }
    })
    map
  }

  def getReceivedMovingActionsByDepartmentIdAndBetweenDate(departmentId: Int,
                                                           beginDate: Date,
                                                           endDate: Date,
                                                           flgDayHospital: Boolean,
                                                           leftLimit: Date,
                                                           flgMore: Boolean,
                                                           flgIsVillager: Boolean) = {


    val subQuery = if (!flgDayHospital) {
      "AND org5.code = '%s'".format(this.dayHospital)
    } else {
      ""
    }
    var subQuery2 = if (leftLimit != null) {
      if (flgMore) {
        "AND a2.event.patient.birthDate > :leftLimit"
      } else {
        "AND a2.event.patient.birthDate < :leftLimit"
      }
    } else {
      ""
    }
    if (flgIsVillager) {
      subQuery2 = subQuery2 + " " + KLADRVillagersQuery
    }
    var typed = em.createQuery(HospitalBedsStateQuery.format("orghb.profileId,\n" +
      "count(a2.id),\n" +
      "a2.event.patient\n",
      "AND (a.begDate IS NOT NULL AND a.begDate BETWEEN :beginDate AND :endDate)",
      i18n("db.action.movingFlatCode"),
      subQuery,
      subQuery2,
      "GROUP BY a2.event.patient ORDER BY a2.event.patient.lastName"), classOf[Array[AnyRef]])
      .setParameter("departmentId", departmentId)
      .setParameter("beginDate", beginDate)
      .setParameter("endDate", endDate)

    if (subQuery2.indexOf(":leftLimit") > 0) {
      typed.setParameter("leftLimit", leftLimit)
    }
    var result = typed.getResultList

    val map = new java.util.HashMap[RbHospitalBedProfile, Object]
    result.foreach((f) => {
      if (f(0).isInstanceOf[RbHospitalBedProfile]) {
        em.detach(f(0).asInstanceOf[RbHospitalBedProfile])
        em.detach(f(2).asInstanceOf[Patient])

        var obj: (java.lang.Long, java.util.List[Patient]) = (0, new java.util.ArrayList[Patient])
        if (!map.containsKey(f(0).asInstanceOf[RbHospitalBedProfile])) {
          map.put(f(0).asInstanceOf[RbHospitalBedProfile], obj)
        }

        obj = map.get(f(0).asInstanceOf[RbHospitalBedProfile]).asInstanceOf[(java.lang.Long, java.util.List[Patient])]
        var first = obj._1.longValue() + f(1).asInstanceOf[java.lang.Long].longValue()
        var second = obj._2
        second.add(f(2).asInstanceOf[Patient])
        map.remove(f(0).asInstanceOf[RbHospitalBedProfile])
        map.put(f(0).asInstanceOf[RbHospitalBedProfile], (first, second))
      }
    })
    map
  }

  def getDischargedMovingActionsByDepartmentIdAndBetweenDate(departmentId: Int,
                                                             beginDate: Date,
                                                             endDate: Date,
                                                             to: String) = {
    /* val subQuery: String = to match {
     case this.dayHospital => {
       MovingActionsByHospitalSubQuery.format(
         "=",
         transferredInDepartment,
         "AND org3.name = '%s'".format(dayHospital)
       )
     }
     case "Круглосуточный стационар" => {""}  //TODO: ????
     case "Другие стационары" => {
       MovingActionsByHospitalSubQuery.format(
         "<>",
         transferredInDepartment,
         ""
       )
     }
     case _ =>{
       MovingActionsByHospitalSubQuery2.format(
         transferredInDepartment,
         transferredInDepartment,
         "AND org4.name = '%s'".format(dayHospital)
       )
     }
   } */

    val query = UniMovingActionsByDepartmentIdAndBetweenDateQuery.format(
      "orghb.profileId,\n" +
        "count(a2.id) AS countPatients,\n" +
        "e.patient",
      i18n("db.action.movingFlatCode"),
      this.transferredFromDepartment,
      TransferredFromDepartmentDateSubQuery,
      "GROUP BY e.patient" //"GROUP BY orghb.profileId"
    )

    val result = em.createQuery(query, classOf[Array[AnyRef]])
      .setParameter("departmentId", departmentId)
      .setParameter("beginDate", beginDate)
      .setParameter("endDate", endDate)
      .getResultList

    val map = new java.util.HashMap[RbHospitalBedProfile, Object]
    result.foreach((f) => {
      if (f(0).isInstanceOf[RbHospitalBedProfile]) {
        em.detach(f(0).asInstanceOf[RbHospitalBedProfile])
        em.detach(f(2).asInstanceOf[Patient])

        var obj: (java.lang.Long, java.util.List[Patient]) = (0, new java.util.ArrayList[Patient])
        if (!map.containsKey(f(0).asInstanceOf[RbHospitalBedProfile])) {
          map.put(f(0).asInstanceOf[RbHospitalBedProfile], obj)
        }

        obj = map.get(f(0).asInstanceOf[RbHospitalBedProfile]).asInstanceOf[(java.lang.Long, java.util.List[Patient])]
        var first = obj._1.longValue() + f(1).asInstanceOf[java.lang.Long].longValue()
        var second = obj._2
        second.add(f(2).asInstanceOf[Patient])
        map.remove(f(0).asInstanceOf[RbHospitalBedProfile])
        map.put(f(0).asInstanceOf[RbHospitalBedProfile], (first, second))
      }
    })
    map
  }

  def getOrgStructureHospitalBedByDepartmentIdBetweenDate(departmentId: Int,
                                                          beginDate: Date,
                                                          endDate: Date) = {

    val result = em.createQuery(OrgStructureHospitalBedByOrgStructureIdBetweenDateQuery, classOf[Array[AnyRef]])
      .setParameter("id", departmentId)
      .setParameter("date", endDate)
      .getResultList

    val map = new java.util.HashMap[RbHospitalBedProfile, Object]
    result.foreach((f) => {
      if (f(0).isInstanceOf[RbHospitalBedProfile]) {
        em.detach(f(0).asInstanceOf[RbHospitalBedProfile])
        map.put(f(0).asInstanceOf[RbHospitalBedProfile],
          (
            f(1).asInstanceOf[Long], //всего
            f(2).asInstanceOf[java.math.BigDecimal].longValue()
            )
        )
      }
    })
    map
  }

  def getCountsMovingActionStateByDepartmentIdAndDate(departmentId: Int,
                                                      date: Date) = {

    var result = em.createQuery(HospitalBedsStateQuery.format("orghb.profileId,\ncount(a2.id)\n",
      "AND (a.begDate IS NOT NULL AND (a.endDate IS NULL OR a.endDate > :date))",
      i18n("db.action.movingFlatCode"),
      "",
      "",
      "GROUP BY orghb.profileId"), classOf[Array[AnyRef]])
      .setParameter("departmentId", departmentId)
      .setParameter("date", date)
      .getResultList

    result.foldLeft(new java.util.HashMap[RbHospitalBedProfile, Object])(
      (map, f) => {
        em.detach(f(0).asInstanceOf[RbHospitalBedProfile])
        map.put(f(0).asInstanceOf[RbHospitalBedProfile], (f(1).asInstanceOf[java.lang.Long]))
        map
      })
  }

  def getCountsPatronageMovingActionStateByDepartmentIdAndDate(departmentId: Int,
                                                               date: Date) = {

    var result = em.createQuery(HospitalBedsStateQuery.format("orghb.profileId,\ncount(a2.id)\n",
      "AND (a.begDate IS NOT NULL AND (a.endDate IS NULL OR a.endDate > :date))",
      i18n("db.action.movingFlatCode"),
      "",
      MovingActionStateByDepartmentIdAndDateWithMotherSubQuery,
      "GROUP BY orghb.profileId"), classOf[Array[AnyRef]])
      .setParameter("departmentId", departmentId)
      .setParameter("date", date)
      .getResultList

    result.foldLeft(new java.util.HashMap[RbHospitalBedProfile, Object])(
      (map, f) => {
        em.detach(f(0).asInstanceOf[RbHospitalBedProfile])
        map.put(f(0).asInstanceOf[RbHospitalBedProfile], (f(1).asInstanceOf[java.lang.Long]))
        map
      })
  }

  def getCountsEmptyHospitalBedsByDepartmentIdAndDate(departmentId: Int,
                                                      date: Date,
                                                      sex: String) = {
    var bedIds = em.createQuery(HospitalBedsStateQuery.format("DISTINCT orghb.id",
      "AND (a.begDate IS NOT NULL AND (a.endDate IS NULL OR a.endDate > :date))",
      i18n("db.action.movingFlatCode"),
      "",
      MovingActionStateByDepartmentIdAndDateWithMotherSubQuery,
      ""), classOf[Long])
      .setParameter("departmentId", departmentId)
      .setParameter("date", date)
      .getResultList

    var result = em.createQuery(EmptyHospitalBedBySexQuery.format(sex), classOf[Array[AnyRef]])
      .setParameter("id", departmentId)
      .setParameter("date", date)
      .setParameter("bedIds", asJavaCollection(bedIds))
      .getResultList

    val map = new java.util.HashMap[RbHospitalBedProfile, Object]
    result.foreach((f) => {
      if (f(0).isInstanceOf[RbHospitalBedProfile]) {
        em.detach(f(0).asInstanceOf[RbHospitalBedProfile])
        map.put(f(0).asInstanceOf[RbHospitalBedProfile],
          (
            //f(1).asInstanceOf[Long],                                   //всего
            new java.lang.Long(f(2).asInstanceOf[java.math.BigDecimal].longValue())
            )
        )
      }
    })
    map
  }

  val ActionsByDepartmentIdAndFlatCodeBetweenDateQuery = """
  SELECT a
  FROM
    Action a JOIN a.actionType at,
    ActionProperty ap,
    APValueOrgStructure val JOIN val.value org
  WHERE
    a.id = ap.action.id
  AND
    ap.id = val.id.id
  AND
    org.id = :departmentId
  AND
    a.createDatetime BETWEEN :beginDate AND :endDate
  AND
    at.id IN
    (
      SELECT actionType.id
      FROM
        ActionType actionType
      WHERE
        actionType.code IN :codes
    )
  AND
    a.deleted = 0
  AND
    ap.deleted = 0
                                                         """


  //Запрос на умерших
  val PatientsLievedBetweenDateQuery = """
  SELECT p0.id
  FROM
    ActionProperty ap0
      JOIN ap0.actionPropertyType apt0
      JOIN ap0.action a0
      JOIN a0.actionType at0
      JOIN a0.event e0
      JOIN e0.patient p0,
    APValueString apv0
  WHERE
    ap0.id = apv0.id.id
  AND
    apv0.value LIKE '%s'
  AND
  (
    (a0.begDate IS NOT NULL AND a0.begDate BETWEEN :beginDate AND :endDate)
    AND
    ((a0.endDate BETWEEN :beginDate AND :endDate) OR (a0.endDate IS NULL))
  )
  AND at0.flatCode = '%s'
  AND apt0.name =   '%s'
  AND a0.deleted = 0
  AND at0.deleted = 0
  AND apt0.deleted = 0
                                       """

  val DiedByHospitalBedsQuery = """
  SELECT orghb.profileId, 1, p, MAX(a.createDatetime)
  FROM
      APValueHospitalBed bed
        JOIN bed.value orghb,
      ActionProperty ap
        JOIN ap.action a
        JOIN a.actionType at
        JOIN a.event e
        JOIN e.patient p
  WHERE
    bed.id.id = ap.id
  AND
    p.id IN (%s)
  AND
    at.flatCode = '%s'
  AND
    exists(
        SELECT ap2
        FROM ActionProperty ap2
                JOIN ap2.actionPropertyType apt2,
             APValueOrgStructure apv2
                JOIN apv2.value org2
        WHERE
          ap2.id = apv2.id.id
        AND
          ap2.action.id = a.id
        AND
          org2.id = :departmentId
        AND
          apt2.name = '%s'
    )
  AND
    a.deleted = 0
  AND
    at.deleted = 0
  GROUP BY p
  ORDER BY p.lastName
                                """

  // orghb.profileId, count(a2.id) $ flatCode &  'Переведен из отделения' & MovingActionsByHospitalSubQuery & GROUP BY orghb.profileId
  val UniMovingActionsByDepartmentIdAndBetweenDateQuery = """
    SELECT %s
    FROM
      OrgStructureHospitalBed orghb,
      APValueHospitalBed bed,
      ActionProperty ap2,
      Action a2,
      Event e
    WHERE
      bed.value.id = orghb.id
    AND
      bed.id.id = ap2.id
    AND
      a2.id = ap2.action.id
    AND
      e.id = a2.event.id
    AND
      a2.id IN
        (
           SELECT a.id
           FROM
              ActionProperty ap
                JOIN ap.actionPropertyType apt
                JOIN ap.action a
                JOIN a.actionType at,
              APValueOrgStructure apv
                JOIN apv.value org
           WHERE
             ap.id = apv.id.id
             AND org.id = :departmentId
             AND at.flatCode = '%s'
             AND apt.name =   '%s'
             %s
             AND org.deleted = 0
             AND a.deleted = 0
             AND at.deleted = 0
             AND apt.deleted = 0
        )
    %s
                                                          """
  val TransferredFromDepartmentDateSubQuery = """
      AND (
          a.begDate IS NOT NULL
         AND
          a.begDate < :endDate
         AND
          a.begDate >= :beginDate
         AND
         (
            a.endDate IS NULL
            OR
            (
                a.endDate IS NOT NULL
              AND
                a.endDate > :beginDate
              AND
                a.endDate <= :endDate
            )
         )
      )
                                              """

  val TransferredInDepartmentDateSubQuery = """
      AND (
          a.begDate IS NOT NULL
         AND
          a.begDate < :endDate
         AND
          a.endDate IS NOT NULL
         AND
          a.endDate > :beginDate
         AND
          a.endDate <= :endDate
      )
                                            """

  val MovingActionsByHospitalSubQuery = """

  AND org.parentId %s
        (
          SELECT org3.parentId
          FROM
            ActionProperty ap3
              JOIN ap3.actionPropertyType apt3
              JOIN ap3.action a3,
            APValueOrgStructure apv3
            JOIN apv3.value org3
          WHERE
            a3.id = a.id
          AND
            ap3.id = apv3.id.id
          AND
            apt3.name =  '%s'
          AND
            org3.deleted = 0
          %s
        )
                                        """ // = , 'Переведен в отделение',  AND org3.name <> "Дневной стационар"

  //проверить нужен ли мах
  val MovingActionsByHospitalSubQuery2 = """
        AND (
          org.parentId <>
          (
            SELECT MAX(org3.parentId)
            FROM
              ActionProperty ap3
                JOIN ap3.actionPropertyType apt3
                JOIN ap3.action a3,
              APValueOrgStructure apv3
              JOIN apv3.value org3
            WHERE
              a3.id = a.id
            AND
              ap3.id = apv3.id.id
            AND
              apt3.name =  '%s'
            AND
              org3.deleted = 0
          )
        OR
          org.parentId =
          (
            SELECT MAX(org4.parentId)
            FROM
              ActionProperty ap4
                JOIN ap4.actionPropertyType apt4
                JOIN ap4.action a4,
              APValueOrgStructure apv4
              JOIN apv4.value org4
            WHERE
              a4.id = a.id
            AND
              ap4.id = apv4.id.id
            AND
              apt4.name =  '%s'
            AND
              org4.deleted = 0
            %s
          )
        )
                                         """

  val OrgStructureHospitalBedByOrgStructureIdBetweenDateQuery = """
     SELECT ohb.profileId,
            count(ohb.id) AS countBeds,
            SUM(
               CASE
               WHEN
                 ohb.involution = true
                 AND
                 (
                   (ohb.begDateInvolute IS NULL AND ohb.endDateInvolute IS NULL)
                   OR
                   (ohb.begDateInvolute IS NULL AND ohb.endDateInvolute >= :date)
                   OR
                   (ohb.begDateInvolute <=:date AND ohb.endDateInvolute IS NULL)
                   OR
                   (ohb.begDateInvolute <=:date AND ohb.endDateInvolute >= :date)
                 )
               THEN 1
               ELSE 0
               END
            )
     FROM OrgStructureHospitalBed ohb
     WHERE
      ohb.masterDepartment.id = :id
     GROUP BY ohb.profileId
                                                                """
  val EmptyHospitalBedBySexQuery = """
     SELECT ohb.profileId,
            count(ohb.id) AS countBeds,
            SUM(CASE WHEN ohb.sex = '%s' THEN 1 ELSE 0 END)
     FROM OrgStructureHospitalBed ohb
     WHERE
        ohb.masterDepartment.id = :id
     AND
        (
          ohb.involution = false
         OR
          (
          ohb.involution = true
          AND (
            (ohb.begDateInvolute IS NOT NULL AND ohb.begDateInvolute >= :date)
            OR
            (ohb.endDateInvolute IS NOT NULL AND ohb.endDateInvolute <= :date)
          )
        )
     AND ohb.id NOT IN :bedIds
     )
     GROUP BY ohb.profileId
                                   """

  val MovingActionStateByDepartmentIdAndDateWithMotherSubQuery = """
    AND
      exists(
        SELECT ap3
        FROM ActionProperty ap3
                JOIN ap3.actionPropertyType apt3,
             APValueString apstr
        WHERE
          ap3.action = a2
        AND
          apt3.name = 'Патронаж'
        AND
          ap3.id = apstr.id.id
        AND
          apstr.value = 'Да'
      )
                                                                 """

  val HospitalBedsStateQuery = """
    SELECT %s
    FROM
      OrgStructureHospitalBed orghb,
      APValueHospitalBed bed,
      ActionProperty ap2
        JOIN ap2.action a2
    WHERE
      bed.value.id = orghb.id
    AND
      bed.id.id = ap2.id
    AND
      a2.id IN
        (
           SELECT a.id
           FROM
              ActionProperty ap
                JOIN ap.actionPropertyType apt
                JOIN ap.action a
                JOIN a.actionType at,
              APValueOrgStructure apv
                JOIN apv.value org
           WHERE
             ap.id = apv.id.id
             AND org.id = :departmentId
             %s
             AND at.flatCode = '%s'
             AND apt.name = 'Отделение пребывания'
             AND org.deleted = 0
             AND (
                NOT exists(
                  SELECT apv4.value
                  FROM
                     ActionProperty ap4
                       JOIN ap4.actionPropertyType apt4
                       JOIN ap4.action a4,
                     APValueOrgStructure apv4
                       JOIN apv4.value org4
                  WHERE
                     a4 = a
                  AND
                     ap4.id = apv4.id.id
                  AND
                     apt4.name = 'Переведен из отделения'
                  AND
                     org4.deleted = 0
                )
                OR (
                  apv.value.id <> (
                    SELECT apv5.value.id
                    FROM
                       ActionProperty ap5
                         JOIN ap5.actionPropertyType apt5
                         JOIN ap5.action a5,
                       APValueOrgStructure apv5
                         JOIN apv5.value org5
                    WHERE
                       a5.id = a.id
                    AND
                       ap5.id = apv5.id.id
                    AND
                       apt5.name = 'Переведен из отделения'
                    AND
                       org5.deleted = 0
                    %s
                  )
                )
             )
             AND a.deleted = 0
             AND at.deleted = 0
             AND apt.deleted = 0
        )
    %s
    %s
                               """

  val KLADRVillagersQuery = """
    AND EXISTS(
      SELECT kl.ocatd
      FROM
        ClientAddress ca
          JOIN ca.address addr
          JOIN addr.house hs,
        Kladr kl
      WHERE
        kl.code = hs.KLADRCode
      AND
        ca.patient.id = a2.event.patient.id
      AND
      (
        (
            kl.ocatd LIKE '__1__8%'
           OR
            kl.ocatd LIKE '__2__8%'
           OR
            kl.ocatd LIKE '__4__8%'
        )
        OR
        (
          kl.ocatd LIKE '_____9%'
          AND (
            kl.ocatd NOT LIKE '__1%'
            AND
            kl.ocatd NOT LIKE '__2%'
            AND
            kl.ocatd NOT LIKE '__4%'
          )
        )
      )
    )
                            """

  //**************************** Новая реализация формы 007 ****************************
  //https://docs.google.com/document/d/1a0AYF8QVpEMl_pKRcFDnP2vQzRmO-IkcG5JNStEcjMI/edit#heading=h.a2hialy1qshb
  def getForm007LinearView(departmentId: Int, beginDate: Date, endDate: Date) = {

    //1. Количество развернутых коек в отделении
    val cntOfPermanentBeds = this.getCountOfPermanentBeds(departmentId)

    //2. Количество пациентов состоящих в отделении на начало периода выборки (Состояло на начало суток)
    val cntOfPatientsFromBegDate = this.getCountOfPatientsForBeginDate(departmentId, beginDate)

    //-------Поступление больных
    val RECEIVED_ALL = 0
    val RECEIVED_DAY_HOSPITAL = 1
    val RECEIVED_VILLAGERS = 2

    //3. Всего поступило больных
    val cntOfAllReceivedPatients = this.getCountOfReceivedPatients(departmentId, beginDate, endDate, RECEIVED_ALL)   //????

    //4. В том числе из дневного стационара
    val cntOfReceivedPatientsFromDayHospital = this.getCountOfReceivedPatients(departmentId, beginDate, endDate, RECEIVED_DAY_HOSPITAL)    //????

    //5. Кол-во сельских жителей
    val cntOfVillagers = this.getCountOfReceivedPatients(departmentId, beginDate, endDate, RECEIVED_VILLAGERS)

    null
  }

  /**
  * Получение количества развернутых коек по идентификатору отделения
  * @author idmitriev Sistema-Soft
  * @since 1.0.0.57
  * @param departmentId
  * @return Количество развернутых коек в отделении как Long
  */
  private def getCountOfPermanentBeds(departmentId: Int) = {
    em.createQuery(countOfPermanentBedsQuery, classOf[Long])
      .setParameter("departmentId", departmentId)
      .getSingleResult
  }

  /**
   * Получение количества пациентов в отделении на начало мед.суток
   * @author idmitriev Sistema-Soft
   * @since 1.0.0.57
   * @param departmentId Идентификатор отделения
   * @param beginDate Дата начала мед.суток
   * @return Количество пациентов в отделении на начало мед.суток
   */
  private def getCountOfPatientsForBeginDate(departmentId: Int, beginDate: Date) = {
    em.createQuery(countOfPatientForBeginDateQuery.format(i18n("db.action.movingFlatCode")), classOf[Long])
      .setParameter("beginDate", beginDate)
      .setParameter("departmentId", departmentId)
      .getSingleResult
  }

  private def getCountOfReceivedPatients(departmentId: Int, beginDate: Date, endDate: Date, status: Int) = {
    var inLex: String = ""
    var mergeLex: String = ""
    var selectAdditionalLex: String = ""
    var conditionAdditionalLex: String = ""

    status match {
       case 0 => {  //Всего поступило больных
         inLex = "NOT IN"
         mergeLex = "<>"
       }
       case 1 => {  //В том числе из дневного стационара
         inLex = "IN"
         mergeLex = "="
       }
       case 2 => {  //Кол-во сельских жителей
         inLex = "NOT IN"
         mergeLex = "<>"
         selectAdditionalLex = villagersSelectSubquery
         conditionAdditionalLex = villagersConditionsSubquery
       }
       case _ => {
         inLex = "NOT IN"
         mergeLex = "<>"
       }
    }

    em.createQuery(countOfAllReceivedPatientsQuery.format(selectAdditionalLex,
                                                          inLex,
                                                          i18n("db.action.movingFlatCode"),
                                                          iCapIds("db.rbCAP.moving.id.movedFrom"),
                                                          mergeLex,
                                                          i18n("db.dayHospital.id"),
                                                          i18n("db.action.movingFlatCode"),
                                                          conditionAdditionalLex), classOf[Long])
      .setParameter("beginDate", beginDate)
      .setParameter("endDate", endDate)
      .setParameter("departmentId", departmentId)
      .getSingleResult
  }

  //Количество развернутых коек
  val countOfPermanentBedsQuery = """
  SELECT count(orghb.id)
  FROM
    OrgStructureHospitalBed orghb
  WHERE
    orghb.masterDepartment.id = :departmentId
  AND
    orghb.isPermanent = '0'
                                  """

  //Состояло на начало суток
  val countOfPatientForBeginDateQuery = """
  SELECT count(a.id)
  FROM
    ActionProperty ap
      JOIN ap.action a
      JOIN a.actionType at
      JOIN a.event e,
    APValueHospitalBed bed
      JOIN bed.value orghb
  WHERE
    ap.id = bed.id.id
  AND
  (
    (
      a.begDate <= :beginDate
      AND
        a.endDate  >= :beginDate
    )
    OR
    (
      a.begDate <= :beginDate
      AND
        a.endDate IS NULL
    )
  )
  AND
    at.flatCode = '%s'
  AND
    orghb.masterDepartment.id = :departmentId
  AND
    a.deleted = '0'
  AND
    ap.deleted = '0'
  AND
    e.deleted = '0'
                                        """

  // Всего поступило больных
  // В том числе из дневного стационара
  val countOfAllReceivedPatientsQuery = """
  SELECT count(a.id)
  FROM
  Action a
    JOIN a.actionType at
    JOIN a.event e,
  ActionProperty ap,
  APValueHospitalBed bed
    JOIN bed.value orghb
  %s
  WHERE
    a.id %s
    (
      SELECT a2.id
      FROM
        Action a2
          JOIN a2.actionType at2
          JOIN a2.event e2,
        ActionProperty ap2
          JOIN ap2.actionPropertyType apt2,
        APValueOrgStructure apv
          JOIN apv.value org,
        RbCoreActionProperty cap
      WHERE
        ap2.action.id = a2.id
      AND
        ap2.id = apv.id.id
      AND
        at2.flatCode = '%s'
      AND
        apt2.id = cap.actionPropertyType.id
      AND
        cap.id = '%s'
      AND
        org.id %s '%s'
      AND
      (
        a2.begDate >= :beginDate
        AND
          a2.begDate <= :endDate
      )
      AND
        a2.deleted = '0'
      AND
        ap2.deleted = '0'
      AND
        e2.deleted = '0'
    )
  AND
  (
    a.begDate >= :beginDate
    AND
      a.begDate <= :endDate
  )
  AND
    at.flatCode = '%s'
  AND
    ap.action.id = a.id
  AND
    ap.id = bed.id.id
  AND
    orghb.masterDepartment.id = :departmentId
  AND
    a.deleted = '0'
  AND
    ap.deleted = '0'
  AND
    e.deleted = '0'
  %s
                                        """

  //Кол-во сельских жителей
  val villagersSelectSubquery =
    """
    ,
    ClientAddress ca
      LEFT JOIN ca.address address
      LEFT JOIN ca.patient client
      LEFT JOIN address.house house
    """

  val villagersConditionsSubquery =
    """
    AND
      client.id = e.patient.id
    AND
      ca.localityType = '2'
    AND
      ca.addressType = '0'
    AND
      house.deleted = '0'
    AND
      address.deleted = '0'
    AND
      ca.deleted = '0'
    """
}