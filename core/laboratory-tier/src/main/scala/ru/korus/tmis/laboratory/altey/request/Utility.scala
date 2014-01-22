package ru.korus.tmis.laboratory.altey.request

import ru.korus.tmis.scala.util.Defaultible
import Defaultible.defaultValue
import ru.korus.tmis.scala.util.Defaultible

object Utility {
  def setAsOptional[A](v: Option[A])(lam: A => Unit) = v match {
    case Some(x) => lam(x)
    case None => {}
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
