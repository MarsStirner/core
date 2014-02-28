package ru.korus.tmis.core.logging


import javax.ejb.{Stateless, EJB}
import javax.interceptor.{AroundInvoke, InvocationContext}

import grizzled.slf4j.Logging
import ru.korus.tmis.util.reflect.TmisLogging
import ru.korus.tmis.core.exception.CoreException

@Stateless
class LoggingInterceptor extends Logging with TmisLogging {

  @AroundInvoke
  def logMethodCall(ctx: InvocationContext): Object = {

    val loggerType = logTmis.LoggingTypes.Debug

    val className = ctx.getMethod.getDeclaringClass.getSimpleName
    val methodName = ctx.getMethod.getName

    trace("Calling: " + className + "." + methodName)

    val isRoot = SharedContext.isRoot
    val sessionId = SharedContext.currentSessionId()
    val nestedLevel = SharedContext.pushNestedLevel()
    val number = SharedContext.nextNumber()
    val startTime = System.nanoTime

    var message: String = ""

    try {
      ctx proceed
    } catch {
      case ex: CoreException => {
        message = ex.getClass.getSimpleName + " -> " + ex.getId + ": " + ex.getMessage + "\n" + ex.getStackTraceString
        logTmis.setStatusByPriority(logTmis.StatusKeys.Failed)
        throw ex
      }
      case ex: Exception => {
        trace("Caught exception " + ex)
        message = ex.getClass.getSimpleName + " -> " + ex
        logTmis.setStatusByPriority(logTmis.StatusKeys.Failed)
        throw ex
      }
    } finally {
      val endTime = System.nanoTime
      trace("Called: " + className + "." + methodName + " -> " + ((endTime - startTime) / 1000000000.0).toString)
      logTmis.setValueForKey(logTmis.LoggingKeys.ClassCalled, className, logTmis.StatusKeys.Success)
      logTmis.setValueForKey(logTmis.LoggingKeys.MethodCalled, methodName, logTmis.StatusKeys.Success)
      logTmis.setValueForKey(logTmis.LoggingKeys.WorkTime, ((endTime - startTime) / 1000000000.0).toString, logTmis.StatusKeys.Success)

      val currStatus = logTmis.getStatus()

      //Проверка на первый вызов (отключена)
      //var needAllParams = false
      //if (logTmis.getValueForKey(logTmis.LoggingKeys.FirstCall) != null && !logTmis.getValueForKey(logTmis.LoggingKeys.FirstCall).isEmpty) {
      //  logTmis.removeValueForKey(logTmis.LoggingKeys.FirstCall)
      //  needAllParams = true
      //}

      logTmis.setLoggerType(loggerType)
      if (currStatus==null || currStatus.compareTo(logTmis.StatusKeys.Success.toString)==0) {
        logTmis.info(message)//(logTmis.getLogStringByValues(needAllParams))
      }
      else if(currStatus.compareTo(logTmis.StatusKeys.Warning.toString)==0)
        logTmis.warning(message)//(logTmis.getLogStringByValues(needAllParams))
      else
        logTmis.error(message)//(logTmis.getLogStringByValues(needAllParams))
      //logTmis.clearLog()

      SharedContext.popNestedLevel()

      if (isRoot) {
        SharedContext.release()
      }
    }
  }

  def logMessage(className: String, methodName: String, message: String) {
    val loggerType = logTmis.LoggingTypes.Debug
    logTmis.setValueForKey(logTmis.LoggingKeys.ClassCalled, className, logTmis.StatusKeys.Success)
    logTmis.setValueForKey(logTmis.LoggingKeys.MethodCalled, methodName, logTmis.StatusKeys.Success)
    logTmis.setLoggerType(loggerType)
    logTmis.info(message)
  }
}
