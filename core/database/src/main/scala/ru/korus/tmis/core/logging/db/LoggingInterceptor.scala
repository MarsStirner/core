package ru.korus.tmis.core.logging.db


import javax.ejb.EJB
import javax.interceptor.{AroundInvoke, InvocationContext}

import grizzled.slf4j.Logging

class LoggingInterceptor extends Logging {


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
      case ex: Exception => {
        trace("Caught exception " + ex)
        trace(ex.getStackTraceString)
        throw ex
      }
    } finally {
      val endTime = System.nanoTime
      trace("Called: " + className + "." + methodName +
        " -> " + ((endTime - startTime) / 1000000000.0).toString)


      SharedContext.popNestedLevel()

      if (isRoot) {
        SharedContext.release()
      }
    }
  }
}
