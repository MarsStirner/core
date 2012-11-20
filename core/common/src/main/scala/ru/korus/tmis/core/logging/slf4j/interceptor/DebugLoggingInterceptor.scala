package ru.korus.tmis.core.logging.slf4j.interceptor

import ru.korus.tmis.core.exception.CoreException
import javax.interceptor.{InvocationContext, AroundInvoke}
import grizzled.slf4j.Logging
import ru.korus.tmis.util.reflect.TmisLogging

/**
 * Класс для логирования всего
 * @author mmakankov
 * @since 1.0.0.43
 */
class DebugLoggingInterceptor extends Logging with TmisLogging {
  /**
   * Список движения по отделениям
   * @param ctx Контекст как javax.interceptor.InvocationContext
   * @return void
   * @throws CoreException
   * @see CoreException
   */
  @AroundInvoke
  def logMethodCall(ctx: InvocationContext): Object = {


    val className = ctx.getMethod.getDeclaringClass.getSimpleName
    val methodName = ctx.getMethod.getName
    val params = ctx.getParameters
    val startTime = System.nanoTime

    try {
      ctx proceed
    } catch {
      case ex: CoreException => {
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
      logTmis.setValueForKey(logTmis.LoggingKeys.Called,
        " " + className + "." + methodName + " -> " + ((endTime - startTime) / 1000000000.0).toString,
        logTmis.StatusKeys.Success)
      val currStatus = logTmis.getStatus()
      if (currStatus==null || currStatus.compareTo(logTmis.StatusKeys.Success.toString)==0)
        logger.info(logTmis.getLogStringByValues())
      else if(currStatus.compareTo(logTmis.StatusKeys.Warning.toString)==0)
        logger.warn(logTmis.getLogStringByValues())
      else
        logger.error(logTmis.getLogStringByValues())
      logTmis.clearLog()
    }

  }
}
