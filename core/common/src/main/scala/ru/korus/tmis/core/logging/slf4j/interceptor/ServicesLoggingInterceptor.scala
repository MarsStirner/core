package ru.korus.tmis.core.logging.slf4j.interceptor

import grizzled.slf4j.Logging
import ru.korus.tmis.util.reflect.TmisLogging
import javax.interceptor.{InvocationContext, AroundInvoke}
import ru.korus.tmis.core.exception.CoreException

/**
 * Класс для логирования всех сервисов кроме авторизации (PatientRegistryRESTImpl)
 * @author mmakankov
 * @since 1.0.0.43 Last Revision: idmitriev 1.0.0.50
 */
class ServicesLoggingInterceptor extends Logging with TmisLogging {
  /**
   * Список движения по отделениям
   * @param ctx Контекст как javax.interceptor.InvocationContext
   * @return void
   * @throws CoreException
   * @see CoreException
   */
  @AroundInvoke
  def logMethodCall(ctx: InvocationContext): Object = {

    val loggerType = logTmis.LoggingTypes.Services

    val className = ctx.getMethod.getDeclaringClass.getSimpleName
    val methodName = ctx.getMethod.getName
    val startTime = System.nanoTime

    var message: String = ""

    try {
      ctx proceed
    } catch {
      case ex: CoreException => {
        message = ex.getClass.getSimpleName + " -> " + ex.getId + ": " + ex.getMessage
        logTmis.setStatusByPriority(logTmis.StatusKeys.Failed)
        throw ex
      }
      case ex: Exception => {
        message = ex.getClass.getSimpleName + " -> " + ex.getMessage
        logTmis.setStatusByPriority(logTmis.StatusKeys.Failed)
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
        logTmis.info(message)
      else if(currStatus.compareTo(logTmis.StatusKeys.Warning.toString)==0)
        logTmis.warning(message)
      else
        logTmis.error(message)
      //logTmis.clearLog()
    }
  }
}
