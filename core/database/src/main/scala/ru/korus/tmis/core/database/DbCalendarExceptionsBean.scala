package ru.korus.tmis.core.database

import javax.ejb.Stateless
import ru.korus.tmis.core.logging.LoggingInterceptor
import grizzled.slf4j.Logging
import javax.persistence.{EntityManager, PersistenceContext}
import javax.interceptor.Interceptors
import ru.korus.tmis.core.exception.CoreException
import java.util.{Calendar, Date}
import ru.korus.tmis.core.entity.model.CalendarExceptions
import scala.collection.JavaConversions._
import java.text.SimpleDateFormat
import ru.korus.tmis.scala.util.{CAPids, I18nable}

/**
 * Created with IntelliJ IDEA.
 * User: mmakankov
 * Date: 13.08.13
 * Time: 13:36
 * To change this template use File | Settings | File Templates.
 */

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbCalendarExceptionsBean extends DbCalendarExceptionsBeanLocal
    with Logging
    with CAPids
    with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def getCalendarExceptionById (id: Int) = {
    val result =  em.createQuery(CalendarExceptionByIdQuery, classOf[CalendarExceptions])
      .setParameter("id", id)
      .getResultList

    result.size match {
      case 0 => {
        //throw new CoreException(ConfigManager.ErrorCodes.DiagnosticNotFound,
        //  i18n("error.diagnosticNotFound").format(id))
        null
      }
      case size => {
        result.foreach(em.detach(_))
        result(0)
      }
    }
  }

  def getHolideyByDate (datef: Date) = {
    val date = Calendar.getInstance()
    date.setTime(datef)
    val year = date.get(Calendar.YEAR)

    val formatter2 = new SimpleDateFormat("MM-dd")
    val formatDateWithoutYear = formatter2.format(datef)

    val result =  em.createQuery(HolidayByDateQuery, classOf[CalendarExceptions])
      .setParameter("year", year)
      .setParameter("formatDateWithoutYear", formatDateWithoutYear)
      .getResultList

    result.size match {
      case 0 => {
        //throw new CoreException(ConfigManager.ErrorCodes.DiagnosticNotFound,
        //  i18n("error.diagnosticNotFound").format(id))
        null
      }
      case size => {
        result.foreach(em.detach(_))
        result(0)
      }
    }
  }

  def getPerenosByDate (datef: Date) = {
    val formatter1 = new SimpleDateFormat("yyyy-MM-dd")
    val date = formatter1.parse(formatter1.format(datef))

    val result =  em.createQuery(PerenosByDateQuery, classOf[CalendarExceptions])
      .setParameter("date", date)
      .getResultList

    result.size match {
      case 0 => {
        //throw new CoreException(ConfigManager.ErrorCodes.DiagnosticNotFound,
        //  i18n("error.diagnosticNotFound").format(id))
        null
      }
      case size => {
        result.foreach(em.detach(_))
        result(0)
      }
    }
  }

  val CalendarExceptionByIdQuery = """
    SELECT ce
    FROM CalendarExceptions ce
    WHERE
      ce.id = :id
                            """

  val HolidayByDateQuery =
    """
    SELECT ce
    FROM
      CalendarExceptions ce
    WHERE
      substring(ce.date, 6, 5) = :formatDateWithoutYear
    AND
    ((
        ce.isHoliday = 1
      AND
        (ce.startYear is null OR  ce.startYear < :year)
      AND
        (ce.finishYear is null OR ce.finishYear > :year)
    )
    OR
    (
        ce.isHoliday = 0
      AND
        ce.fromDate is not null
    ))
    AND
      ce.deleted = 0
    """

  val PerenosByDateQuery =
    """
    SELECT ce
    FROM
      CalendarExceptions ce
    WHERE
      ce.fromDate = :date
    AND
      ce.deleted = 0
    """
}
