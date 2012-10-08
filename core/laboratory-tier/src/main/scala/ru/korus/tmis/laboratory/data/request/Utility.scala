package ru.korus.tmis.laboratory.data.request

import ru.korus.tmis.util.Defaultible
import ru.korus.tmis.util.Defaultible.defaultValue

object Utility {
  def setAsOptional[A](v: Option[A])(lam: A => Unit) = {
    v.foreach {
      lam(_)
    }
  }

  def setAsDefaultible[A: Defaultible](v: Option[A])(lam: A => Unit) = v match {
    case Some(x) => lam(x)
    case None => lam(defaultValue[A])
  }

  def setAsRequired[A](e: => Throwable)(v: Option[A])(lam: A => Unit) = v match {
    case Some(x) => lam(x)
    case None => throw e
  }
}
