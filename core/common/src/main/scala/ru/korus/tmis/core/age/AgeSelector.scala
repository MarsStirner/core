package ru.korus.tmis.core.age

import util.parsing.combinator.JavaTokenParsers

import scala.math.abs
import java.util.{Calendar, GregorianCalendar, Date}
import ru.korus.tmis.util.CloneUtils

import ru.korus.tmis.util.General.nullity_implicits

case class AgeSelector(age: String) {

  object AgeSelectorParsers extends JavaTokenParsers {
    def sel: Parser[(Option[BrokenAge], Option[BrokenAge])] =
      (dd.? <~ '-') ~ dd.? ^^ {
        case (f: Option[BrokenAge]) ~ (t: Option[BrokenAge]) => (f, t)
      }

    def integral = """[0-9]*""".r

    def unit = elem('д') | 'Д' | 'г' | 'Г' | 'м' | 'М'

    def dd: Parser[BrokenAge] = integral ~ unit ^^ {
      case i ~ ('д' | 'Д') => BrokenAge(i.toInt, 0, 0)
      case i ~ ('н' | 'Н') => BrokenAge(i.toInt * 7, 0, 0)
      case i ~ ('м' | 'М') => BrokenAge(0, i.toInt, 0)
      case i ~ ('г' | 'Г') => BrokenAge(0, 0, i.toInt)
    }
  }

  case class BrokenAge(d: Int, m: Int, y: Int) extends Ordered[BrokenAge] {
    override def compare(that: BrokenAge) = {
      that match {
        case BrokenAge(_, _, ty) if (y < ty) => -1
        case BrokenAge(_, _, ty) if (y > ty) => 1
        // y == ty
        case BrokenAge(_, tm, _) if (m < tm) => -1
        case BrokenAge(_, tm, _) if (m > tm) => 1
        // m == tm
        case BrokenAge(td, _, _) if (d < td) => -1
        case BrokenAge(td, _, _) if (d > td) => 1
        case BrokenAge(td, _, _) if (d == td) => 0
        case _ => -1
      }
    }
  }

  val pat = AgeSelectorParsers.parse(AgeSelectorParsers.sel, age ?: "").getOrElse((None, None))
  val time_now = new Date()
  val now = new Date(time_now.getYear, time_now.getMonth, time_now.getDate)

  implicit def dateImplicits(dt: Date) = new AnyRef with Ordered[Date] {
    def +(age: BrokenAge) = {
      import Calendar._

      val gc = new GregorianCalendar

      gc setTime CloneUtils.clone(dt)
      gc.add(DATE, age.d)
      gc.add(MONTH, age.m)
      gc.add(YEAR, age.y)

      gc.getTime
    }

    def compare(that: Date) = dt match {
      case d if d == that => 0
      case d if d.before(that) => -1
      case _ => 1
    }
  }

  def check(d: Date) = {

    val checkFrom = pat match {
      case (Some(f), _) => d + f <= now
      case _ => true
    }

    val checkTo = pat match {
      case (_, Some(f)) => d + f >= now
      case _ => true
    }

    checkFrom && checkTo
  }

}
