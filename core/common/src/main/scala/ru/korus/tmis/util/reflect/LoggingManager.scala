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

  val debugInfoFormat = "[&ip] [&url] [&u-a] Called: [&cl].[&mtd] -> [&wtime]; [&message]"
  val debugWarningFormat = "[&ip] [&url] [&u-a] Called: [&cl].[&mtd]; [&message]"
  val debugErrorFormat = "[&ip] [&url] [&u-a] Called: [&cl].[&mtd] -> [&wtime]; [&message]"

  val servicesInfoFormat = "[&ip] [&url] [&u-a] Called: [&cl].[&mtd] -> [&wtime]; [&message] [&status]"
  val servicesWarningFormat = "[&ip] [&url] [&u-a] Called: [&cl].[&mtd] -> [&wtime]; [&message] [&status]"
  val servicesErrorFormat = "[&ip] [&url] [&u-a] Called: [&cl].[&mtd] -> [&wtime]; [&message] [&status]"

  val authInfoFormat = "[&ip] [&url] [&lgn] [&pass] [&role] [&u-a] Called: [&cl].[&mtd] -> [&wtime]; [&message] [&status]"
  val authWarningFormat = "[&ip] [&url] [&lgn] [&pass] [&role] [&u-a] Called: [&cl].[&mtd] -> [&wtime]; [&message] [&status]"
  val authErrorFormat = "[&ip] [&url] [&lgn] [&pass] [&role] [&u-a] Called: [&cl].[&mtd] -> [&wtime]; [&message] [&status]"

  var LoggerType = LoggingTypes.Debug

  object LoggingTypes extends Enumeration {
    type LoggingTypes = Value
    val Auth = Value("Auth")
    val Services = Value("Services")
    val Debug = Value("Debug")
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
    val Status = Value("Status")

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

  //________________________________________________________________________________________________________________
  //                       Методы определения текущего логгера и шаблонов вывода сообщений
  //________________________________________________________________________________________________________________

  /**
   * Получение текущего логгера по его типу
   * @return Логгер как Logger
   */
  def currentLogger = {
    LoggerType.toString match {
      case "Auth" => authLogger
      case "Services" => servicesLogger
      case "Debug" => debugLogger
      case _ => debugLogger
    }
  }

  /**
   * Получение строки формата для предупреждений по типу логгера
   * @return Шаблон специального формата, в которую оборачивается предупреждение как String
   */
  def warningFormat = {
    LoggerType.toString match {
      case "Auth" => authWarningFormat
      case "Services" => servicesWarningFormat
      case "Debug" => debugWarningFormat
      case _ => debugWarningFormat
    }
  }

  /**
   * Получение строки формата для информационного сообщения по типу логгера
   * @return Шаблон специального формата, в которую оборачивается сообщение как String
   */
  def infoFormat = {
    LoggerType.toString match {
      case "Auth" => authInfoFormat
      case "Services" => servicesInfoFormat
      case "Debug" => debugInfoFormat
      case _ => debugInfoFormat
    }
  }

  /**
   * Получение строки формата для сообщения  об ошибке по типу логгера
   * @return Шаблон специального формата, в которую оборачивается сообщение об ошибке как String
   */
  def errorFormat = {
    LoggerType.toString match {
      case "Auth" => authErrorFormat
      case "Services" => servicesErrorFormat
      case "Debug" => debugErrorFormat
      case _ => debugErrorFormat
    }
  }

  //________________________________________________________________________________________________________________
  //                                      Методы вывода сообщений в лог
  //________________________________________________________________________________________________________________

  /**
   * Запись сообщения в текущий лог
   * @param message Сообщение
   */
  def info(message: String){
    preOutputMessage()
    currentLogger.info(parseFormatString(infoFormat, message))
    postOutputMessage()
  }

  /**
   * Вывод предупреждения в текущий лог
   * @param message Текст предупреждения
   */
  def warning(message: String){
    preOutputMessage()
    currentLogger.warn(parseFormatString(warningFormat, message))
    postOutputMessage()
  }

  /**
   * Вывод сообщения об ошибке в текущий лог
   * @param message Текст сообщения об ошибке
   */
  def error(message: String){
    preOutputMessage()
    currentLogger.error(parseFormatString(errorFormat, message))
    postOutputMessage()
  }

  //________________________________________________________________________________________________________________
  //                       Методы работы с ассоциативным диагностическим контекстом (org.slf4j.MDC)
  //________________________________________________________________________________________________________________

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
     setStatusByPriority(status)
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
   * Запись статуса с проверкой приоритета
   * @param status Статус сообщения
   */
  def setStatusByPriority(status: StatusKeys){
    if(getStatus()==null ||
      StatusKeys.isPriority(getStatus(),status)) {//приоритет выше
      MDC.put(LoggingKeys.Status.toString, status.toString)
    }
  }

  /**
   * Метод зачищает ассоциативный диагностический контекст (org.slf4j.MDC) для текущей сессии.
   * @return void
   */
  def clearLog() {
    MDC.clear()
  }

  //________________________________________________________________________________________________________________
  //                                               Getters and Setters
  //________________________________________________________________________________________________________________

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

  /**
   * Установка логгера, который отрабатывает сообщение
   * @param key Ключ логгера как LoggingTypes
   */
  def setLoggerType(key: LoggingTypes) {
    LoggerType = key
  }

  /**
   * Получение текущего логгера
   * @return тип логгера как LoggingManager.LoggingTypes.Value
   */
  def getLoggerType() = LoggerType

  //________________________________________________________________________________________________________________
  //                                  Методы для внутреннего использования
  //________________________________________________________________________________________________________________
  /**
   * Предообработка перед выводом сообщения в лог
   */
  private def preOutputMessage() {
    val methodicStack = Thread.currentThread().getStackTrace
    if(MDC.get(LoggingKeys.ClassCalled.toString)==null || MDC.get(LoggingKeys.ClassCalled.toString).isEmpty)
      this.setValueForKey(this.LoggingKeys.ClassCalled, methodicStack(3).getClassName, this.StatusKeys.Success)
    if(MDC.get(LoggingKeys.MethodCalled.toString)==null || MDC.get(LoggingKeys.MethodCalled.toString).isEmpty)
      this.setValueForKey(this.LoggingKeys.MethodCalled, methodicStack(3).getMethodName, this.StatusKeys.Success)
  }

  /**
   * Зачистка временного контекста после вывода сообщения в лог
   */
  private def postOutputMessage() { //Зачищаем временные поля в диагностическом контексте после вывода сообщения
    Set(/*LoggingKeys.Called,*/
        LoggingKeys.ClassCalled,
        LoggingKeys.MethodCalled,
        /*LoggingKeys.Error,*/
        LoggingKeys.WorkTime,
        LoggingKeys.Status).foreach(f => removeValueForKey(f))
  }

  /**
   * Парсинг шаблона вывода сообщения в лог и заполнение данными из ассоциативного диагностического контекста (org.slf4j.MDC) для текущей сессии.
   * @param parsing_input Шаблон сообщения
   * @param message Выводимое сообщение [&message]
   * @return String Строка лога, выводимая с помощью паттерна @msg
   */
  private def parseFormatString (parsing_input: String, message: String) = {
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
        val value = this.getValueForMnemonicKey(key, message)
        formatString = formatString + value
        pos = pos + endChars.length()
        parsing = parsing.substring(pos)
      } //else break;
      pos = parsing.indexOf(beginChars)
    }
    formatString
  }

  /**
   * Получение данных из ассоциативного диагностического контекста (org.slf4j.MDC) по ключу в шаблоне
   * @param key Ключ в шаблоне сообщения
   * @param message Текст сообщения
   * @return Значение из ассоциативного диагностического контекста (org.slf4j.MDC) как String
   */
  private def getValueForMnemonicKey(key: String, message: String) = {
    key match {
      case "cl" => {"%s".format(MDC.get(LoggingKeys.ClassCalled.toString))}  //имя класса
      case "mtd" => {"%s".format(MDC.get(LoggingKeys.MethodCalled.toString))} //имя метода
      case "ip" => {dynamicCategoryMarked(LoggingKeys.Ip.toString)}
      case "url" => {dynamicCategoryMarked(LoggingKeys.URL.toString)}
      case "lgn" => {dynamicCategoryMarked(LoggingKeys.Login.toString)}
      case "pass" => {dynamicCategoryMarked(LoggingKeys.Password.toString)}
      case "role" => {dynamicCategoryMarked(LoggingKeys.Role.toString)}
      case "u-a" => {dynamicCategoryMarked(LoggingKeys.UserAgent.toString)}
      case "wtime" => {"%s".format(MDC.get(LoggingKeys.WorkTime.toString))}
      case "message" => message
      case _ => {""}
    }
  }

  /**
   * Шаблон для вывода из ассоциативного диагностического контекста (org.slf4j.MDC) значения с ключом
   * @param key Ключ-заголовок в ассоциативном диагностическом контексте
   * @return Форматированная строка со значением параметра key из org.slf4j.MDC как String
   */
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
