package ru.korus.tmis.core.logging.slf4j.interceptor

import javax.interceptor.{InvocationContext, AroundInvoke}
import grizzled.slf4j.Logging
import ru.korus.tmis.util.reflect.TmisLogging
import ru.korus.tmis.core.exception.AuthenticationException

/**
 * Класс для логирования всех сервисов кроме авторизации (AuthenticationRESTImpl)
 * @author mmakankov
 * @since 1.0.0.42
 */
class AuthLoggingInterceptor extends Logging with TmisLogging {

  val loggerType = logTmis.LoggingTypes.Auth

  @AroundInvoke
  def logMethodCall(ctx: InvocationContext): Object = {

    //val authLogger: Logger = LoggerFactory.getLogger("auth")
    val className = ctx.getMethod.getDeclaringClass.getSimpleName
    val methodName = ctx.getMethod.getName
    val params = ctx.getParameters
    val startTime = System.nanoTime

    try {
      ctx proceed
    } catch {
      case ex: AuthenticationException => {
        logTmis.setValueForKey(logTmis.LoggingKeys.Error,
                              ex.getClass.getSimpleName + " -> " + ex.getId + ": " + ex.getMessage,
                              logTmis.StatusKeys.Failed)
        throw ex
      }
      case ex: Exception => {
        logTmis.setValueForKey(logTmis.LoggingKeys.Error,
                               ex.getClass.getSimpleName + " -> " + ex.getMessage,
                               logTmis.StatusKeys.Failed)
        throw ex
      }
    } finally {
      val endTime = System.nanoTime
      logTmis.setValueForKey(logTmis.LoggingKeys.ClassCalled, className, logTmis.StatusKeys.Success)
      logTmis.setValueForKey(logTmis.LoggingKeys.MethodCalled, methodName, logTmis.StatusKeys.Success)
      logTmis.setValueForKey(logTmis.LoggingKeys.WorkTime, ((endTime - startTime) / 1000000000.0).toString, logTmis.StatusKeys.Success)
      val currStatus = logTmis.getStatus()

      logTmis.setLoggerType(loggerType)
      if (currStatus==null || currStatus.compareTo(logTmis.StatusKeys.Success.toString)==0)
        logTmis.info()
      else if(currStatus.compareTo(logTmis.StatusKeys.Warning.toString)==0)
        logTmis.warning()
      else
        logTmis.error()
      //logTmis.clearLog()
    }
  }
}
