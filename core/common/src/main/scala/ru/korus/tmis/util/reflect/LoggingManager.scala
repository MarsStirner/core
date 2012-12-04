package ru.korus.tmis.util.reflect

import java.text.SimpleDateFormat
import org.slf4j.{LoggerFactory, MDC}
import java.util

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

  val debugLogger = LoggerFactory.getLogger("ru.korus.tmis.core.logging._LoggingInterceptor_Serializable")
  val servicesLogger = LoggerFactory.getLogger("ru.korus.tmis.core.logging.slf4j.interceptor.ServicesLoggingInterceptor")
  val authLogger = LoggerFactory.getLogger("ru.korus.tmis.core.logging.slf4j.interceptor.AuthLoggingInterceptor")

  val debugInfoFormat = "[&ip] [&url] [&u-a] Called: [&cl].[&mtd]-->[&wtime]; [&message]%s [&status]"
  val debugWarningFormat = "[&ip] [&url] [&u-a] Called: [&cl].[&mtd]; [&message]%s[&warning]"
  val debugErrorFormat = "[&ip] [&url] [&u-a] Called: [&cl].[&mtd]-->[&wtime]; [&message]%s[&err] [&status]"

  val authInfoFormat = "[&ip] [&url] [&lgn] [&pass] [&role] [&u-a] Called: [&cl].[&mtd]-->[&wtime]; [&message]%s [&status]"
  val authWarningFormat = "[&ip] [&url] [&lgn] [&pass] [&role] [&u-a] Called: [&cl].[&mtd]-->[&wtime]; [&message]%s[&err] [&status]"
  val authErrorFormat = "[&ip] [&url] [&lgn] [&pass] [&role] [&u-a] Called: [&cl].[&mtd]-->[&wtime]; [&message]%s[&err] [&status]"

  var LoggerType = LoggingTypes.Debug

  object LoggingTypes extends Enumeration {
    type LoggingTypes = Value
    val Auth = Value("1")
    val Services = Value("2")
    val Debug = Value("3")
  }
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
    val ClassCalled = Value
    val MethodCalled = Value
    val WorkTime = Value
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
  import LoggingTypes._

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
   * @param needAllParams Переменная определяет, нужно ли записать в сообщение лога все параметры? если нет,
   *                      то не записывается IP, URL и User-Agent
   */
  def getLogStringByValues(needAllParams: Boolean) = {
    var output = ""
    LoggingKeys.values.filter(value => MDC.get(value.toString)!=null)
                      .foreach(key => {
      if (needAllParams) {
        output = output + key.toString + ": " + MDC.get(key.toString).toString + "; "
      } else {
        if (LoggingKeys.Ip != key && LoggingKeys.URL != key && LoggingKeys.UserAgent != key && LoggingKeys.Status != key) {
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

  def setLoggerType(key: LoggingTypes) {
    LoggerType = key
  }

  def getLoggerType() = LoggerType

  def currentLogger = {
    LoggerType.toString match {
      case "1" /*LoggingTypes.Auth.toString*/ => authLogger
      case "2" /*LoggingTypes.Services.toString*/ => servicesLogger
      case "3" /*LoggingTypes.Debug.toString*/ => debugLogger
      case _ => debugLogger
    }
  }

  def warningFormat = {
    LoggerType.toString match {
      case "1" /*LoggingTypes.Auth.toString*/ => authWarningFormat
      case "2" /*LoggingTypes.Services.toString*/ => ""
      case "3" /*LoggingTypes.Debug.toString*/ => debugWarningFormat
      case _ => debugWarningFormat
    }
  }

  def infoFormat = {
    LoggerType.toString match {
      case "1" /*LoggingTypes.Auth.toString*/ => authInfoFormat
      case "2" /*LoggingTypes.Services.toString*/ => ""
      case "3" /*LoggingTypes.Debug.toString*/ => debugInfoFormat
      case _ => debugWarningFormat
    }
  }

  def errorFormat = {
    LoggerType.toString match {
      case "1" /*LoggingTypes.Auth.toString*/ => authErrorFormat
      case "2" /*LoggingTypes.Services.toString*/ => ""
      case "3" /*LoggingTypes.Debug.toString*/ => debugErrorFormat
      case _ => debugWarningFormat
    }
  }

  def info(message: String){
    preOutputMessage()
    currentLogger.info(parseFormatString(infoFormat.format(message)))
    postOutputMessage()
  }

  def info() {info("")}

  /**
   * Вывод предупреждения в дебаг лог
   * @param message Текст предупреждения
   */
  def warning(message: String){
    preOutputMessage()
    currentLogger.warn(parseFormatString(warningFormat.format(message)))
    postOutputMessage()
   }

  def warning(){warning("")}

  def error(message: String){
    preOutputMessage()
    currentLogger.info(parseFormatString(errorFormat.format(message)))
    postOutputMessage()
  }

  def error(){error("")}

  private def preOutputMessage() {
    val methodicStack = Thread.currentThread().getStackTrace
    if(MDC.get(LoggingKeys.ClassCalled.toString)==null || MDC.get(LoggingKeys.ClassCalled.toString).isEmpty)
      this.setValueForKey(this.LoggingKeys.ClassCalled, methodicStack(3).getClassName, this.StatusKeys.Success)
    if(MDC.get(LoggingKeys.MethodCalled.toString)==null || MDC.get(LoggingKeys.MethodCalled.toString).isEmpty)
      this.setValueForKey(this.LoggingKeys.MethodCalled, methodicStack(3).getMethodName, this.StatusKeys.Success)
  }

  private def postOutputMessage() { //Зачищаем временные поля в диагностическом контексте после вывода сообщения
    Set(LoggingKeys.Called.toString,
        LoggingKeys.ClassCalled.toString,
        LoggingKeys.MethodCalled.toString,
        LoggingKeys.Error.toString,
        LoggingKeys.WorkTime.toString).foreach(f => MDC.remove(f))
  }

  private def parseFormatString (parsing_input: String) = {
    var parsing = parsing_input
    val beginChars = "[&"
    val endChars = "]"

    //val parameters = new java.util.LinkedList[String]
    var formatString = ""

    var pos: Int = parsing.indexOf(beginChars)
    while (pos >= 0) {
      formatString = formatString + parsing.substring(0, pos)
      pos = pos + beginChars.length()
      parsing = parsing.substring(pos)
      pos = parsing.indexOf(endChars)
      if (pos >= 0) {
        val key = parsing.substring(0, pos)
        val value = this.getValueForMnemonicKey(key)
        formatString = formatString + value
        pos = pos + endChars.length()
        parsing = parsing.substring(pos)
      } //else break;
      pos = parsing.indexOf(beginChars)
    }
    formatString
  }

  private def getValueForMnemonicKey(key: String) = {
    key match {
      case "warning" => {"Frtyu!"}
      case "cl" => {"%s".format(MDC.get(LoggingKeys.ClassCalled.toString))}  //имя класса
      case "mtd" => {"%s".format(MDC.get(LoggingKeys.MethodCalled.toString))} //имя метода
      case "ip" => {dynamicCategoryMarked(LoggingKeys.Ip.toString)}
      case "url" => {dynamicCategoryMarked(LoggingKeys.URL.toString)}
      case "lgn" => {dynamicCategoryMarked(LoggingKeys.Login.toString)}
      case "pass" => {dynamicCategoryMarked(LoggingKeys.Password.toString)}
      case "role" => {dynamicCategoryMarked(LoggingKeys.Role.toString)}
      case "status" => {dynamicCategoryMarked(LoggingKeys.Status.toString)}
      case "err" => {dynamicCategoryMarked(LoggingKeys.Error.toString)}
      case "u-a" => {dynamicCategoryMarked(LoggingKeys.UserAgent.toString)}
      case "wtime" => {"%s".format(MDC.get(LoggingKeys.WorkTime.toString))}
      case _ => {""}
    }
  }

  private def dynamicCategoryMarked(key: String) = {
    val value = MDC.get(key)
    if (value!=null && !value.isEmpty){
      "%s: %s;".format(key, value)
    } else ""
  }
}

trait TmisLogging {
  val logTmis = LoggingManager
}
