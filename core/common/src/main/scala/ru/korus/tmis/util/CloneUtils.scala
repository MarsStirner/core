package ru.korus.tmis.util

import ru.korus.tmis.util.reflect.clones.Cloner
import java.util._

object CloneUtils {
  def clone(that: Date): Date = {
    that.clone.asInstanceOf[Date]
  }

  def clone(that: Calendar): Calendar = {
    that.clone.asInstanceOf[Calendar]
  }

  def clone(that: String): String = {
    that
  }

  def clone(that: Int): Int = {
    that
  }

  def clone(that: Short): Short = {
    that
  }

  def clone(that: Long): Long = {
    that
  }

  def clone(that: Char): Char = {
    that
  }

  def clone[T](that: T): T = {
    type Clonable = PublicClonable[_]
    that match {
      // for some reason, this does not work
      /* case that: Clonable => that.clone.asInstanceOf[T] */
      case _ => Cloner.deepClone(that)
    }
  }
}


