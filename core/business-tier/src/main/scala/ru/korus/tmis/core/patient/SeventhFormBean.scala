package ru.korus.tmis.core.patient

import grizzled.slf4j.Logging
import ru.korus.tmis.util.{CAPids, I18nable}
import javax.persistence.{EntityManager, PersistenceContext}
import scala.collection.JavaConversions._
import javax.ejb.{EJB, Stateless}
import java.text.SimpleDateFormat
import java.util.{Calendar, Date}
import ru.korus.tmis.core.data.{SeventhFormRequestData, FormOfAccountingMovementOfPatientsData}
import ru.korus.tmis.core.database.DbOrgStructureBeanLocal
import java.util.List
import scala.collection.JavaConverters._


/**
 * Класс для работы с формой 007
 */
//@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class SeventhFormBean extends SeventhFormBeanLocal
with Logging
with I18nable
with CAPids {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  @EJB
  private var dbOrgStructureBean: DbOrgStructureBeanLocal = _

  private val msecInDay: Long = 1000 * 60 * 60 * 24
  private val msecInMinute: Long = 1000 * 60
  //милисекунд в сутках
  private val formatter = new SimpleDateFormat("yyyy-MM-dd")

  private val isDeadValue = "умер"
  private val toAnotherHospitalValue = "переведен в другой стационар"
  private val toAnotherHospitalValue2 = "переведен в другой стационар - 4"
  private val toHourHospitalValue = "выписан в круглосуточный стационар"
  private val toHourHospitalValue2 = "выписан в круглосуточный стационар - 3"
  private val toDayHospitalValue = "выписан в дневной стационар"
  private val toDayHospitalValue2 = "выписан в дневной стационар - 2"

  //**************************** Новая реализация формы 007 ****************************
  //Спецификация: https://docs.google.com/document/d/1a0AYF8QVpEMl_pKRcFDnP2vQzRmO-IkcG5JNStEcjMI/edit#heading=h.a2hialy1qshb

  import ru.korus.tmis.core.data.Form007QueryStatuses._

  private var linearLongMap = Map.empty[String, scala.collection.mutable.Map[Form007QueryStatuses, Long]]
  private var linearListString = Map.empty[Form007QueryStatuses, scala.collection.immutable.List[String]]


  def getForm007LinearView(departmentId: Int, beginDate: Long, endDate: Long, profileBeds: java.util.List[Integer]) = {

    var bDate: Date = null
    var eDate: Date = null

    //Анализ дат
    if (beginDate > 0 && endDate > 0) {
      bDate = new Date(beginDate)
      eDate = new Date(endDate)
    }
    else if (beginDate <= 0) {
      if (endDate <= 0)
        eDate = this.getDefaultEndDate()
      else
        eDate = new Date(endDate)
      bDate = this.getDefaultBeginDate(eDate.getTime)
    }
    else {
      bDate = new Date(beginDate)
      eDate = this.getDefaultEndDate(bDate.getTime)
    }

    //Получение инфо об отделении
    val department = dbOrgStructureBean.getOrgStructureById(departmentId)
    //Преобразование профеля коек в строку
    var profileBedsStr = Array(profileBeds).mkString(",")
    profileBedsStr = profileBedsStr.substring(1, profileBedsStr.length - 1);
    val endDateStr = "'" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(endDate) ) + "'"
    //Получение данных
    //ru.korus.tmis.core.data.Form007QueryStatuses.values.foreach(status => this.addDataToLinearMapByCellNumber(departmentId, bDate, eDate, profileBeds, status))
    //Получение информации о кол-ве
    val res = em.createNativeQuery("CALL form007front(\"%s\", \"%d\", \"%s\")".format(endDateStr, departmentId, profileBedsStr)).getResultList
    val resList: List[Array[Object]] = res.asInstanceOf[List[Array[Object]]]
    resList.foreach(resSql => {
      this.linearLongMap += resSql(0).asInstanceOf[String] -> scala.collection.mutable.Map.empty[Form007QueryStatuses, Long]
      ru.korus.tmis.core.data.Form007QueryStatuses.values.foreach(status => this.addDataToLinearMapByCellNumber(resSql, status, resSql(0).asInstanceOf[String]))
    })
    ru.korus.tmis.core.data.Form007QueryStatuses.values.foreach(status => {
      if (status.toString != null && !status.toString.startsWith("count")) {
        val resFIOinput = em.createNativeQuery("CALL  %s(\"%s\", \"%d\", \"%s\")".format(status.toString, endDateStr, departmentId, profileBedsStr)).getResultList
        val resListFIOinput: List[String] = resFIOinput.asInstanceOf[List[String]]
        linearListString += status -> resListFIOinput.asScala.toList
      }
    })


    //Заполнение Entity
    new FormOfAccountingMovementOfPatientsData(department, this.linearLongMap, this.linearListString, new SeventhFormRequestData(departmentId, bDate, eDate))
  }

  /**
   * Дата конца текущих мед. суток (Сегодня 7:59)
   */
  private def getDefaultEndDate() = {

    val today = Calendar.getInstance()
    today.setTime(formatter.parse(formatter.format(new Date())))
    today.set(Calendar.HOUR_OF_DAY, 8); //смещение между астрономическими и медицинскими сутками

    today.getTime
  }

  /**
   * Дата конца текущих мед. суток (beginDate плюс сутки)
   * @param beginDate Дата начала периода выборки
   * @return Дата конца периода выборки как Date
   */
  private def getDefaultEndDate(beginDate: Long) = new Date(beginDate + msecInDay.longValue() - msecInMinute)

  /**
   * Дата начала предыдущих мед. суток (Вчера 8:00)
   * @return Дата начала периода выборки как Date
   */
  private def getDefaultBeginDate() = new Date(this.getDefaultEndDate.getTime - msecInDay.longValue()) //предыдущие мед сутки

  /**
   * Дата начала предыдущих мед. суток (endDate минус сутки)
   * @param endDate  Дата окончания периода выборки
   * @return
   */
  private def getDefaultBeginDate(endDate: Long) = new Date(endDate - msecInDay.longValue() + msecInMinute) //предыдущие мед сутки

  /**
   * Метод вызова запросов для заполнения именнованного списка с данными для формы 007
   * @author idmitriev Sistema-Soft
   * @since 1.0.0.57
   * @param departmentId Идентификатор отделения.
   * @param beginDate  Дата начала выборки данных.
   * @param endDate  Дата окончания выборки данных.
   * @param status  Идентификатор запроса как Form007QueryStatuses
   */
  /*private def addDataToLinearMapByCellNumber(departmentId: Int, beginDate: Date, endDate: Date,  profileBeds: java.util.List[Integer], status: Form007QueryStatuses) {

     val pp = status match {
       case F007QS_PERMANENT_BEDS => (this.getCountOfPermanentBeds(departmentId, profileBeds), List.empty[Event])
       case F007QS_ALL_PATIENTS_LOCATED_AT_BEGIN_DATE => (this.getCountOfPatientsForBeginDate(departmentId, beginDate), List.empty[Event])
       case F007QS_RECEIVED_ALL |
            F007QS_LEAVED_ALL |
            F007QS_MOVING_FROM |
            F007QS_MOVING_IN |
            F007QS_RECEIVED_DAY_HOSPITAL |
            F007QS_RECEIVED_VILLAGERS |
            F007QS_RECEIVED_CHILDREN |
            F007QS_RECEIVED_AFTER60  => this.getFullDataByQueryStatuses(departmentId, beginDate, endDate, status) //Количества + [Список действий (для ФИО + НИБ)]
       case F007QS_LEAVED_ANOTHER_HOSPITAL |
            F007QS_LEAVED_HOUR_HOSPITAL |
            F007QS_LEAVED_DAY_HOSPITAL |
            F007QS_LEAVED_DEAD => this.getDataOfMovingPatients(departmentId, beginDate, endDate, status)
       case F007QS_ALL_PATIENTS_LOCATED_AT_END_DATE => (this.getCountOfPatientsForEndDate(departmentId, endDate), List.empty[Event])
       case F007QS_PATRONAGE => (this.getCountOfMothers(departmentId, /*beginDate*/endDate), List.empty[Event])
       case _ => null
     }
     linear += status -> pp
   }*/

  private def addDataToLinearMapByCellNumber(resQuery: Array[Object], status: Form007QueryStatuses, profileName: String) {

    val pp = status match {
      case F007QS_PERMANENT_BEDS => resQuery(1)
      case F007QS_BEDS_REPAIR => resQuery(2)
      case F007QS_ALL_PATIENTS_LOCATED_AT_BEGIN_DATE => resQuery(3)
      case F007QS_RECEIVED_ALL => resQuery(4)
      case F007QS_RECEIVED_DAY_HOSPITAL => resQuery(5)
      case F007QS_RECEIVED_VILLAGERS => resQuery(6)
      case F007QS_RECEIVED_CHILDREN => resQuery(7)
      case F007QS_RECEIVED_AFTER60 => resQuery(8)
      case F007QS_MOVING_FROM => resQuery(9)
      case F007QS_MOVING_IN => resQuery(10)
      case F007QS_LEAVED_ALL => resQuery(11)
      case F007QS_LEAVED_ANOTHER_HOSPITAL => resQuery(12)
      case F007QS_LEAVED_HOUR_HOSPITAL => resQuery(13)
      case F007QS_LEAVED_DAY_HOSPITAL => resQuery(14)
      case F007QS_LEAVED_DEAD => resQuery(15)
      case F007QS_ALL_PATIENTS_LOCATED_AT_END_DATE => resQuery(16)
      case F007QS_PATRONAGE => resQuery(17)
      case F007QS_FREE_BEDS_MALE => resQuery(18)
      case F007QS_FREE_BEDS_FEMALE => resQuery(19)
      case _ => null
    }
    this.linearLongMap(profileName) += status -> pp.asInstanceOf[Long]
  }

  /**
   * Получение количества развернутых коек по идентификатору отделения
   * @author idmitriev Sistema-Soft
   * @since 1.0.0.57
   * @param departmentId
   * @return Количество развернутых коек в отделении как Long
   */
  private def getCountOfPermanentBeds(departmentId: Int, profileBeds: java.util.List[Integer]) = {
    em.createQuery(countOfPermanentBedsQuery, classOf[Long])
      .setParameter("departmentId", departmentId)
      .setParameter("profileBeds", profileBeds)
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
    em.createQuery(countOfPatientForBeginDateQuery.format("count(a.id)", i18n("db.action.movingFlatCode")), classOf[Long])
      .setParameter("beginDate", beginDate)
      .setParameter("departmentId", departmentId)
      .getSingleResult
  }

  /**
   * Получение данных о поступивших и переведенных пациентах.
   * @author idmitriev Sistema-Soft
   * @since 1.0.0.57
   * @param departmentId Идентификатор отделения.
   * @param beginDate  Дата начала выборки данных.
   * @param endDate  Дата окончания выборки данных.
   * @param status  Идентификатор запроса как Form007QueryStatuses
   * @return Количество записей и данные о госпитализациях(если нужно) как (Long, List[Event])
   */
  /* private def getFullDataByQueryStatuses(departmentId: Int, beginDate: Date, endDate: Date, status: Form007QueryStatuses) = {

     var inLex: String = "" //Лексема подмножества, в котором идет поиск
     var mergeLex: String = "" //Операция сравнения
     var selectCond: String = "count(a.id)" //Условие подблока SELECT
     var selectAdditionalLex: String = "" //Дополнительное условие подблока FROM
     var conditionAdditionalLex: String = "" //Дополнительное условие подблока WHERE
     var cell: String = "" //ActionProperty, по которому идет поиск
     var isNative: Boolean = false //Флаг нативный (SQL) запрос или JPA/HQL запрос
     var isHasActionsList: Boolean = false //Флаг необходимости возврата списка Event
     var dataSubquery = "AND (a%s.begDate >= :beginDate AND a%s.begDate <= :endDate)"

     status match {
       case F007QS_RECEIVED_ALL => {
         //2.1. Фамилия И.О. поступивших.
         isHasActionsList = true
         selectCond = "e"
         inLex = "NOT IN"
         mergeLex = "<>"
         cell = iCapIds("db.rbCAP.moving.id.movedFrom")
       }
       case F007QS_MOVING_FROM => {
         //2.2. Фамилия И.О. поступивших из других отделений.
         isHasActionsList = true
         selectCond = "e"
         inLex = "IN"
         mergeLex = "<>"
         cell = iCapIds("db.rbCAP.moving.id.movedFrom")
       }
       case F007QS_LEAVED_ALL => {
         //2.3. Фамилия И.О. выписанных.
         isHasActionsList = true
         selectCond = "e"
         inLex = "NOT IN"
         mergeLex = "<>"
         cell = iCapIds("db.rbCAP.moving.id.movedIn")
         dataSubquery = "AND (a%s.endDate >= :beginDate AND a%s.endDate <= :endDate)"
         conditionAdditionalLex =
           leavedAllSubquery.format(
             leavedSubquery.format("e3.id",
               leavedFromSubquery.format(i18n("db.action.leavingFlatCode"),
                 iCapIds("db.rbCAP.extract.id.hospResult"),
                 "AND apstr3.value = '%s'".format(isDeadValue)),
               i18n("db.action.movingFlatCode"),
               iCapIds("db.rbCAP.moving.id.movedIn"),
               i18n("db.dayHospital.id"),
               i18n("db.action.movingFlatCode"),
               ""
             ))
       }
       case F007QS_MOVING_IN => {
         //2.4. Фамилия И.О. переведенных в другие отделения
         isHasActionsList = true
         selectCond = "e"
         inLex = "IN"
         mergeLex = "<>"
         cell = iCapIds("db.rbCAP.moving.id.movedIn")
         dataSubquery = "AND (a%s.endDate >= :beginDate AND a%s.endDate <= :endDate)"
       }
       case F007QS_RECEIVED_DAY_HOSPITAL => {
         //В том числе из дневного стационара
         inLex = "IN"
         mergeLex = "="
         cell = iCapIds("db.rbCAP.moving.id.movedFrom")
       }
       case F007QS_RECEIVED_VILLAGERS => {
         //Кол-во сельских жителей
         inLex = "NOT IN"
         mergeLex = "<>"
         selectAdditionalLex = villagersSelectSubquery
         conditionAdditionalLex = villagersConditionsSubquery
         cell = iCapIds("db.rbCAP.moving.id.movedFrom")
       }
       case F007QS_RECEIVED_CHILDREN => {
         //Кол-во детей в возрасте от 0 до 17 лет
         conditionAdditionalLex = childrensConditionsSubquery
         isNative = true
         cell = iCapIds("db.rbCAP.moving.id.movedFrom")
       }
       case F007QS_RECEIVED_AFTER60 => {
         //Кол-во старше 60 лет
         conditionAdditionalLex = after60ConditionsSubquery
         isNative = true
         cell = iCapIds("db.rbCAP.moving.id.movedFrom")
       }
       case _ => null
     }
     if (!isNative) {
       val query = countOfAllReceivedPatientsQuery.format(selectCond,
         selectAdditionalLex,
         inLex,
         i18n("db.action.movingFlatCode"),
         cell,
         mergeLex,
         i18n("db.dayHospital.id"),
         dataSubquery.format("2", "2"),
         dataSubquery.format("", ""),
         i18n("db.action.movingFlatCode"),
         conditionAdditionalLex)
       if (!isHasActionsList) {
         val count =
           em.createQuery(query, classOf[Long])
             .setParameter("beginDate", beginDate)
             .setParameter("endDate", endDate)
             .setParameter("departmentId", departmentId)
             .getSingleResult
         (count, List.empty[Event])
       }
       else {
         val list =
           em.createQuery(query, classOf[Event])
             .setParameter("beginDate", beginDate)
             .setParameter("endDate", endDate)
             .setParameter("departmentId", departmentId)
             .getResultList
             .toList
         list.foreach(event => em.detach(_))
         (list.size.toLong, list)
       }
     } else {
       val count = em.createNativeQuery(countOfAllReceivedPatientsNativeQuery.format(i18n("db.action.movingFlatCode"),
         iCapIds("db.rbCAP.moving.id.movedFrom"),
         i18n("db.dayHospital.id"),
         i18n("db.action.movingFlatCode"),
         conditionAdditionalLex))
         .setParameter(1, beginDate)
         .setParameter(2, endDate)
         .setParameter(3, departmentId)
         .getSingleResult
       (count.asInstanceOf[Long], List.empty[Event])
     }
   }*/

  /**
   * Получение данных о выбытии пациентов.
   * @author idmitriev Sistema-Soft
   * @since 1.0.0.57
   * @param departmentId Идентификатор отделения.
   * @param beginDate  Дата начала выборки данных.
   * @param endDate  Дата окончания выборки данных.
   * @param status  Идентификатор запроса как Form007QueryStatuses
   * @return Количество записей и данные о госпитализациях(если нужно) как (Long, List[Event])
   */
  /*private def getDataOfMovingPatients(departmentId: Int, beginDate: Date, endDate: Date, status: Form007QueryStatuses) = {
    var selectCond: String = "count(a3.id)"
    var isHasActionsList: Boolean = false
    var additional: String = ""
    val condition =
      status match {
        case F007QS_LEAVED_ANOTHER_HOSPITAL => {
          //2.5. Фамилия И.О. переведенных в другие стационары
          selectCond = "e3"
          isHasActionsList = true
          leavedFromSubquery.format(i18n("db.action.leavingFlatCode"),
            iCapIds("db.rbCAP.extract.id.hospResult"),
            "AND (apstr3.value = '%s' OR apstr3.value = '%s')".format(toAnotherHospitalValue,
              toAnotherHospitalValue2))
        }
        case F007QS_LEAVED_HOUR_HOSPITAL => {
          leavedFromSubquery.format(i18n("db.action.leavingFlatCode"),
            iCapIds("db.rbCAP.extract.id.hospResult"),
            "AND (apstr3.value = '%s' OR apstr3.value = '%s')".format(toHourHospitalValue,
              toHourHospitalValue2))
        }
        case F007QS_LEAVED_DAY_HOSPITAL => {
          leavedFromSubquery.format(i18n("db.action.leavingFlatCode"),
            iCapIds("db.rbCAP.extract.id.hospResult"),
            "AND (apstr3.value = '%s' OR apstr3.value = '%s')".format(toDayHospitalValue,
              toDayHospitalValue2))
        }
        case F007QS_LEAVED_DEAD => {
          //TODO: !Рефакторинг быстродействия
          selectCond = "e3"
          isHasActionsList = true
          additional = leavedFromDeadAdditionalSubquery.format("%" + isDeadValue + "%")
          leavedFromDeadSubquery
        }
        case _ => ""
      }

    val query = leavedSubquery.format(selectCond,
      condition,
      i18n("db.action.movingFlatCode"),
      iCapIds("db.rbCAP.moving.id.movedIn"),
      i18n("db.dayHospital.id"),
      i18n("db.action.movingFlatCode"),
      additional
    )

    if (!isHasActionsList) {
      val count =
        em.createQuery(query, classOf[Long])
          .setParameter("beginDate", beginDate)
          .setParameter("endDate", endDate)
          .setParameter("departmentId", departmentId)
          .getSingleResult
      (count, List.empty[Event])
    }
    else {
      val list =
        em.createQuery(query, classOf[Event])
          .setParameter("beginDate", beginDate)
          .setParameter("endDate", endDate)
          .setParameter("departmentId", departmentId)
          .getResultList
          .toList
      list.foreach(event => em.detach(_))
      (list.size().toLong, list)
    }
  }*/

  /**
   * Получение количества пациентов в отделении на начало текущего дня
   * @author idmitriev Sistema-Soft
   * @since 1.0.0.57
   * @param departmentId Идентификатор отделения
   * @param endDate Дата начала текущего дня
   * @return Количество пациентов в отделении на начало мед.суток
   */
  private def getCountOfPatientsForEndDate(departmentId: Int, endDate: Date) = {
    em.createQuery(countOfPatientsOnBegDateQuery.format("count(a.id)",
      i18n("db.action.movingFlatCode")), classOf[Long])
      .setParameter("endDate", endDate)
      .setParameter("departmentId", departmentId)
      .getSingleResult
  }

  /**
   * Получение количества пациентов в отделении, которые госпитализированы с матерями
   * @author idmitriev Sistema-Soft
   * @since 1.0.0.57
   * @param departmentId Идентификатор отделения
   * @param endDate Дата начала текущего дня
   * @return Количества пациентов в отделении, которые госпитализированы с матерями на начало мед.суток
   */
  private def getCountOfMothers(departmentId: Int, /*beginDate*/ endDate: Date) = {
    em.createQuery(countOfMothersQuery.format(i18n("db.action.movingFlatCode"),
      iCapIds("db.rbCAP.moving.id.patronage"),
      "Да",
      /*countOfPatientForBeginDateQuery.format("a.id",
                                              i18n("db.action.movingFlatCode"))*/
      //это по спеке но так не правильно
      countOfPatientsOnBegDateQuery.format("a.id",
        i18n("db.action.movingFlatCode"))

    ), classOf[Long])
      .setParameter(/*"beginDate", beginDate*/ "endDate", endDate)
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
    orghb.isPermanent = '1'
  AND
    orghb.profileId.id IN :profileBeds
                                  """

  //Состояло на начало суток
  val countOfPatientForBeginDateQuery = """
  SELECT %s
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
  SELECT %s
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
      %s
      AND
        a2.deleted = '0'
      AND
        ap2.deleted = '0'
      AND
        e2.deleted = '0'
    )
  %s
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

  val countOfAllReceivedPatientsNativeQuery =
    """
    SELECT count(a.id) countAll
    FROM
      Action a
    INNER JOIN ActionType at ON a.actionType_id = at.id
    INNER JOIN ActionProperty ap ON ap.action_id = a.id
    INNER JOIN ActionProperty_HospitalBed bed ON ap.id = bed.id
    INNER JOIN OrgStructure_HospitalBed orghb ON bed.value = orghb.id
    INNER JOIN Event e ON e.id = a.event_id
    INNER JOIN Client patient ON patient.id = e.client_id
    WHERE
      a.id NOT IN (
        SELECT a2.id
        FROM
          Action a2
            INNER JOIN Event e2 ON a2.event_id = e2.id
            INNER JOIN ActionProperty ap2 ON a2.id = ap2.action_id
            INNER JOIN ActionProperty_OrgStructure org ON ap2.id = org.id
            INNER JOIN ActionType at2 ON a2.actionType_id = at2.id
            INNER JOIN ActionPropertyType apt2 ON ap2.type_id = apt2.id
            INNER JOIN rbCoreActionProperty cap ON apt2.id = cap.actionPropertyType_id
        WHERE
          at2.flatCode = '%s'
        AND cap.id = '%s'
        AND org.value <> '%s'
        AND(a2.begDate >= ?1 AND a2.begDate <= ?2)
        AND a2.deleted = '0'
        AND ap2.deleted = '0'
        AND e2.deleted = '0'
      )
      AND(a.begDate >= ?1 AND a.begDate <= ?2)
      AND at.flatCode = '%s'
      AND orghb.master_id = ?3
      AND a.deleted = '0'
      AND ap.deleted = '0'
      AND e.deleted = '0'
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

  //Кол-во детей до 17 лет
  val childrensConditionsSubquery =
    """
      AND ((year(a.begDate) - year(patient.birthDate)) -
        (DATE_FORMAT(current_date, '%m%d') < DATE_FORMAT(patient.birthDate, '%m%d'))) <= '17'
    """

  //Кол-во старше 60 лет
  val after60ConditionsSubquery =
    """
      AND ((year(a.begDate) - year(patient.birthDate)) -
        (DATE_FORMAT(current_date, '%m%d') < DATE_FORMAT(patient.birthDate, '%m%d'))) > '60'
    """

  //Выписано всего
  val leavedAllSubquery =
    """
      AND
        e.id NOT IN (
          %s
        )
    """

  val leavedSubquery =
    """
      SELECT %s
      FROM
        %s
      AND
        e3.id IN (
          SELECT e4.id
          FROM
            Action a4
              JOIN a4.actionType at4
              JOIN a4.event e4,
            ActionProperty ap4,
            APValueHospitalBed bed4
              JOIN bed4.value orghb4
          WHERE
            a4.id NOT IN (
              SELECT a5.id
              FROM
                Action a5
                  JOIN a5.actionType at5
                  JOIN a5.event e5,
                ActionProperty ap5
                  JOIN ap5.actionPropertyType apt5,
                APValueOrgStructure apv5
                  JOIN apv5.value org5,
                RbCoreActionProperty cap5
              WHERE
                ap5.action.id = a5.id
              AND
                ap5.id = apv5.id.id
              AND
                at5.flatCode = '%s'
              AND
                apt5.id = cap5.actionPropertyType.id
              AND
                cap5.id = '%s'
              AND
                org5.id <> '%s'
              AND
              (
                a5.endDate >= :beginDate
                AND
                  a5.endDate <= :endDate
              )
              AND
                a5.deleted = '0'
              AND
                ap5.deleted = '0'
              AND
                e5.deleted = '0'
            )
          AND
          (
            a4.endDate >= :beginDate
            AND
              a4.endDate <= :endDate
          )
          AND
            at4.flatCode = '%s'
          AND
            ap4.action.id = a4.id
          AND
            ap4.id = bed4.id.id
          AND
            orghb4.masterDepartment.id = :departmentId
          AND
            a4.deleted = '0'
          AND
            ap4.deleted = '0'
          AND
            e4.deleted = '0'
        )
        %s
    """

  val leavedFromSubquery =
    """
      Action a3
        JOIN a3.actionType at3
        JOIN a3.event e3,
      ActionProperty ap3,
        APValueString apstr3,
        RbCoreActionProperty cap3
      WHERE
        at3.flatCode = '%s'
      AND
        ap3.action.id = a3.id
      AND
        ap3.id = apstr3.id.id
      AND
        ap3.actionPropertyType.id = cap3.actionPropertyType.id
      AND
        cap3.id = '%s'
      %s
      AND
      (
        a3.endDate >= :beginDate
        AND
          a3.endDate <= :endDate
      )
    """

  val leavedFromDeadSubquery = """
      Event e3,
      RbResult res
      WHERE
        res.id = e3.result.id
                               """
  val leavedFromDeadAdditionalSubquery = """
      AND
        res.name LIKE '%s'
                                         """

  //Состоит на начало текущего дня
  val countOfPatientsOnBegDateQuery = """
  SELECT %s
  FROM
  Action a
    JOIN a.actionType at
    JOIN a.event e,
  ActionProperty ap,
  APValueHospitalBed bed
    JOIN bed.value orghb
  WHERE
  (
    (a.endDate >= :endDate AND a.begDate <= :endDate)
    OR
      (a.endDate IS NULL AND a.begDate <= :endDate)
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
                                      """

  //Состоит на начало текущего дня
  val countOfMothersQuery = """
  SELECT count(a3.id)
  FROM
    Action a3
      JOIN a3.actionType at3
      JOIN a3.event e3,
    ActionProperty ap3,
    APValueString apstr3,
    RbCoreActionProperty cap3
  WHERE
    at3.flatCode = '%s'
  AND
    ap3.action.id = a3.id
  AND
    ap3.id = apstr3.id.id
  AND
    ap3.actionPropertyType.id = cap3.actionPropertyType.id
  AND
    cap3.id = '%s'
  AND
    apstr3.value = '%s'
  AND
    a3.deleted = '0'
  AND
    ap3.deleted = '0'
  AND
    e3.deleted = '0'
  AND
    a3.id IN (
      %s
    )
                            """
}

