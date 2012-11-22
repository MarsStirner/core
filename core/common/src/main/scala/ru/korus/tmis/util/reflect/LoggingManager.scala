package ru.korus.tmis.util.reflect

import java.text.SimpleDateFormat
import org.slf4j.MDC

//import org.apache.log4j.MDC
import scala.collection.JavaConversions._

/**
 * Created with IntelliJ IDEA.
 * User: idmitriev
 * Date: 10/30/12
 * Time: 12:13 PM
 * To change this template use File | Settings | File Templates.
 */

object LoggingManager {

  var DateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

  /**
   * Значения категорий для подстрок логирования
   */
  object LoggingKeys extends Enumeration {
    type LoggingKeys = Value
    val Ip = Value("IP")
    val URL = Value("URL")
    val Login = Value("Lgn")
    val Password= Value("Pass")
    val User = Value("UserId")
    val Role = Value("Role")
    val CountOfIncorrectInput= Value("Count")
    val LastTime = Value("lTime")

    val UserAgent = Value("user-agent")

    val Session = Value("Sess")
    val Count = Value("Num")
    val Called = Value("Called")
    val Status = Value("Status")
    val Error = Value("Err")

    val FirstCall = Value
  }

  /**
   * Статусы логирования
   */
  object StatusKeys extends Enumeration {
    type StatusKeys = Value
    val Success = Value("SUCCESS")
    val Failed = Value("FAILED")
    val Warning = Value("WARNING")

    /**
     * Метод проверяет приоритетность статуса.
     * @param first Текущий статус.
     * @param second Проверяемый статус.
     * @return Boolean Возвращается true, если приоритет second статуса выше, чем first статуса.
     */
    def isPriority(first: String, second: StatusKeys) = {
      getStatusPriority(first) < getStatusPriority(second.toString)
    }

    /**
     * Метод возвращает приоритет статуса.
     * @param value Проверяемый статус.
     * @return Int Возвращается приоритет статуса.
     */
    private def getStatusPriority(value: String) = {
      value match {
        case "SUCCESS" => 0
        case "WARNING" => 1
        case "FAILED" => 2
        case _ => 0
      }
    }

    override def toString() = {
      StatusKeys.toString()
    }
  }

  import LoggingKeys._
  import StatusKeys._

  /**
   * Метод создает категорию логгирования и сохраняет ее значение со статусом в ассоциативный диагностический контекст (org.slf4j.MDC) для текущей сессии
   * @param key Категория логирования (значения из LoggingKeys).
   * @param value Значение выводимое в лог в категории, определенной в key.
   * @param status Статус строки лога (значения из StatusKeys).
   * @return void
   * @see LoggingKeys
   * @see StatusKeys
   * @see org.slf4j.MDC
   */
  def setValueForKey(key: LoggingKeys, value: AnyRef, status: StatusKeys) {
     MDC.put(key.toString(), value.toString)
     if(getStatus()==null ||
        StatusKeys.isPriority(getStatus(),status)) {//приоритет выше
         MDC.put(LoggingKeys.Status.toString, status.toString)
     }
  }

  /**
   * Метод удаляет значение по ключу из ассоциативного диагностического контекста (org.slf4j.MDC) для текущей сессии
   * @param key Категория логирования (значения из LoggingKeys).
   * @return void
   * @see LoggingKeys
   * @see StatusKeys
   * @see org.slf4j.MDC
   */
  def removeValueForKey(key: LoggingKeys) {
    MDC.remove(key.toString)
  }

  /**
   * Метод возвращает значение по ключу из ассоциативного диагностического контекста (org.slf4j.MDC) для текущей сессии
   * @param key Категория логирования (значения из LoggingKeys).
   * @return java.lang.String
   * @see LoggingKeys
   * @see StatusKeys
   * @see org.slf4j.MDC
   */
  def getValueForKey(key: LoggingKeys) = {
    MDC.get(key.toString)
  }

  /**
   * Метод формирует строку лога из ассоциативного диагностического контекста (org.slf4j.MDC) для текущей сессии.
   * @return String Строка лога, выводимая с помощью паттерна @msg
   */
  def getLogStringByValues(isAllParams: Boolean) = {
    var output = ""
    LoggingKeys.values.filter(value => MDC.get(value.toString)!=null)
                      .foreach(key => {
      if (isAllParams) {
        output = output + key.toString + ": " + MDC.get(key.toString).toString + "; "
      } else {
        if (LoggingKeys.Ip != key && LoggingKeys.URL != key && LoggingKeys.UserAgent != key) {
          output = output + key.toString + ": " + MDC.get(key.toString).toString + "; "
        }
      }

    })
    output
  }

  /**
   * Метод зачищает ассоциативный диагностический контекст (org.slf4j.MDC) для текущей сессии.
   * @return void
   */
  def clearLog() {
    MDC.clear()
  }

  /**
   * Метод возвращает статус из ассоциативного диагностического контекста (org.slf4j.MDC) для текущей сессии.
   * @return String
   */
  def getStatus() = MDC.get(LoggingKeys.Status.toString)

  /**
   * Метод возвращает LoggingKeys.URL
   * @return LoggingKeys.Value
   * @see LoggingKeys
   */
  def getURL = LoggingKeys.URL

  /**
   * Метод возвращает LoggingKeys.Ip
   * @return LoggingKeys.Value
   * @see LoggingKeys
   */
  def getIP = LoggingKeys.Ip

  /**
   * Метод возвращает LoggingKeys.UserAgent
   * @return LoggingKeys.Value
   * @see LoggingKeys
   */
  def getUserAgent = LoggingKeys.UserAgent

  /**
   * Метод возвращает LoggingKeys.FirstCall
   * @return LoggingKeys.Value
   * @see LoggingKeys
   */
  def getFirstCall = LoggingKeys.FirstCall

  /**
   * Метод возвращает StatusKeys.Success
   * @return StatusKeys.Value
   * @see StatusKeys
   */
  def getOkStatus = StatusKeys.Success

  /**
   * Метод возвращает StatusKeys.Warning
   * @return StatusKeys.Value
   * @see StatusKeys
   */
  def getWarningStatus = StatusKeys.Warning

  /**
   * Метод возвращает StatusKeys.Failed
   * @return StatusKeys.Value
   * @see StatusKeys
   */
  def getErrorStatus = StatusKeys.Failed
}

trait TmisLogging {
  val logTmis = LoggingManager
}
