package ru.korus.tmis.core.patient


import javax.persistence.{EntityManager, PersistenceContext}
import scala.collection.JavaConversions._
import javax.ejb.{EJB, Stateless}
import java.text.SimpleDateFormat
import java.util.{TimeZone, Calendar, Date, List}
import ru.korus.tmis.core.data.{SeventhFormRequestData, FormOfAccountingMovementOfPatientsData}
import scala.collection.JavaConverters._
import ru.korus.tmis.scala.util.{CAPids, I18nable}
import ru.korus.tmis.core.database.common.DbOrgStructureBeanLocal


/**
 * Класс для работы с формой 007
 */
//
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

  //**************************** Новая реализация формы 007 ****************************
  //Спецификация: https://docs.google.com/document/d/1a0AYF8QVpEMl_pKRcFDnP2vQzRmO-IkcG5JNStEcjMI/edit#heading=h.a2hialy1qshb

  import ru.korus.tmis.core.data.Form007QueryStatuses._



  def getForm007LinearView(departmentId: Int, beginDate: Long, endDate: Long, profileBeds: java.util.List[Integer]) = {

    var linearLongMap = Map.empty[String, scala.collection.mutable.Map[Form007QueryStatuses, Long]]
    var linearListString = Map.empty[Form007QueryStatuses, scala.collection.immutable.List[String]]

    var bDate: Date = null
    var eDate: Date = null

    //Анализ дат
    if (beginDate > 0 && endDate > 0) {
      bDate = new Date(beginDate)
      eDate = new Date(endDate)
    }
    else if (beginDate <= 0) {
      if (endDate <= 0) {
        eDate = this.getDefaultEndDate()
      } else {
        eDate = new Date(endDate)
      }
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

    val endDateStr = "'" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(endDate)) + "'"
    //Получение данных
    //Получение информации о кол-ве (лицевая сторона формы 007)
    val query: String = "CALL form007front(\"%s\", \"%d\", \"%s\")".format(endDateStr, departmentId, profileBedsStr)
    logger.info("form 007 front SQL query: " + query);
    val res = em.createNativeQuery(query).getResultList
    val resList: List[Array[Object]] = res.asInstanceOf[List[Array[Object]]]
    resList.foreach(resSql => {
      linearLongMap += resSql(0).asInstanceOf[String] -> scala.collection.mutable.Map.empty[Form007QueryStatuses, Long]
      ru.korus.tmis.core.data.Form007QueryStatuses.values.foreach(status => this.addDataToLinearMapByCellNumber(resSql, status, resSql(0).asInstanceOf[String], linearLongMap))
    })
    //Заполняем обратную сторону формы 007
    ru.korus.tmis.core.data.Form007QueryStatuses.values.foreach(status => {
      if (status.toString != null && !status.toString.startsWith("count")) {
        val resFIOinput = em.createNativeQuery("CALL  %s(\"%s\", \"%d\", \"%s\")".format(status.toString, endDateStr, departmentId, profileBedsStr)).getResultList
        val resListFIOinput: List[String] = resFIOinput.asInstanceOf[List[String]]
        linearListString += status -> resListFIOinput.asScala.toList
      }
    })

    //Заполнение json
    new FormOfAccountingMovementOfPatientsData(department, linearLongMap, linearListString, new SeventhFormRequestData(departmentId, bDate, eDate))
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

  private def addDataToLinearMapByCellNumber(resQuery: Array[Object],
                                             status: Form007QueryStatuses,
                                             profileName: String,
                                             linearLongMap: scala.collection.immutable.Map[String, scala.collection.mutable.Map[Form007QueryStatuses, Long]]) {

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
    linearLongMap(profileName) += status -> pp.asInstanceOf[Long]
  }

}

