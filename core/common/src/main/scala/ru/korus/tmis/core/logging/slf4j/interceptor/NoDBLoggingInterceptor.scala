package ru.korus.tmis.core.logging.slf4j.interceptor

import grizzled.slf4j.Logging
import javax.interceptor.{InvocationContext, AroundInvoke}

import scala.compat.Platform._

class NoDBLoggingInterceptor extends Logging {

  @AroundInvoke
  def logMethodCall(ctx: InvocationContext): Object = {
    val className = ctx.getMethod.getDeclaringClass.getSimpleName
    val methodName = ctx.getMethod.getName

    trace("Calling: " + className + "." + methodName)

    val startTime = System.nanoTime

    try {
      ctx.proceed
    } catch {
      case ex: Exception => {
        trace("Caught exception " + ex)
        trace(ex.getStackTrace.mkString("", EOL, EOL))
        throw ex
      }
    } finally {
      val endTime = System.nanoTime
      trace("Called: " + className + "." + methodName +
        " -> " + ((endTime - startTime) / 1000000000.0).toString)
    }
  }
}
