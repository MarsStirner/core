package ru.korus.tmis.core.logging.db

import java.lang.ThreadLocal
import java.util.UUID

object SharedContext {
  var sessionId: ThreadLocal[UUID] = new ThreadLocal[UUID]()
  var nestedLevel: ThreadLocal[java.lang.Integer] = new ThreadLocal[java.lang.Integer]()
  var number: ThreadLocal[java.lang.Integer] = new ThreadLocal[java.lang.Integer]()

  def currentSessionId() = {
    if(sessionId.get() == null) {
      sessionId.set(UUID.randomUUID())
    }
    sessionId.get()
  }

  def pushNestedLevel() = {
    if(nestedLevel.get() == null) {
      nestedLevel.set(0)
    }
    val r = nestedLevel.get().intValue
    nestedLevel.set(r + 1)
    r
  }

  def popNestedLevel() = {
    if(nestedLevel.get() == null) {
      nestedLevel.set(0)
    }
    val r = nestedLevel.get().intValue
    nestedLevel.set(r - 1)
    r
  }

  def nextNumber() = {
    if(number.get() == null) {
      number.set(0)
    }
    val r = number.get().intValue
    number.set(r + 1)
    r
  }

  def release() = {
    sessionId.set(null)
    sessionId.remove()

    nestedLevel.set(null)
    nestedLevel.remove()

    number.set(null)
    number.remove()
  }

  def isRoot() = {
    sessionId.get() == null
  }
}
