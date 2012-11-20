package ru.korus.tmis.core.logging

import ru.korus.tmis.core.database.InternalLoggerBeanLocal

import javax.ejb.EJB
import javax.interceptor.{AroundInvoke, InvocationContext}

import grizzled.slf4j.Logging
import ru.korus.tmis.util.reflect.TmisLogging
import ru.korus.tmis.core.exception.CoreException

class LoggingInterceptor extends Logging with TmisLogging {

  @EJB
  var internalLogger: InternalLoggerBeanLocal = _

  @AroundInvoke
  def logMethodCall(ctx: InvocationContext): Object = {
    val className = ctx.getMethod.getDeclaringClass.getSimpleName
    val methodName = ctx.getMethod.getName

    trace("Calling: " + className + "." + methodName)

    val isRoot = SharedContext.isRoot
    val sessionId = SharedContext.currentSessionId()
    val nestedLevel = SharedContext.pushNestedLevel()
    val number = SharedContext.nextNumber()
    val startTime = System.nanoTime

    try {
      ctx proceed
    } catch {
      case ex: CoreException => {
        var esese = ex.getStackTrace.toString
        var esese2 = ex.getStackTraceString
        logTmis.setValueForKey(logTmis.LoggingKeys.Error,
          ex.getClass.getSimpleName + " -> " + ex.getId + ": " + ex.getMessage + "/n" + ex.getStackTraceString,
          logTmis.StatusKeys.Failed)
        throw ex
      }
      case ex: Exception => {
        trace("Caught exception " + ex)
        logTmis.setValueForKey(logTmis.LoggingKeys.Error,
          ex.getClass.getSimpleName + " -> " + ex,
          logTmis.StatusKeys.Failed)
        throw ex
      }
    } finally {
      val endTime = System.nanoTime
      trace("Called: " + className + "." + methodName +
        " -> " + ((endTime - startTime) / 1000000000.0).toString)
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


      internalLogger.logMethodCall(sessionId,
        nestedLevel,
        number,
        endTime - startTime,
        className,
        methodName)

      SharedContext.popNestedLevel()

      if (isRoot) {
        SharedContext.release()
      }
    }
  }
}
