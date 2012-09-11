package ru.korus.tmis.util

import scala.util.control.Exception.allCatch

import java.lang.{Integer => JInteger, Float => JFloat, Double => JDouble, Boolean => JBoolean}

object General {
  type MayThrow[A] = Either[Throwable, A]

  def catchy[A](code: => A): Option[A] = allCatch.opt(code)

  def catchAndStore[A](code: => A): MayThrow[A] = allCatch.either(code)


  object NumberImplicits {
    implicit def optInt(v: JInteger): Option[Int] = if (v == null) None else Some(v.intValue)
    implicit def optDouble(v: JDouble): Option[Double] = if (v == null) None else Some(v.doubleValue)
    implicit def optFloat(v: JFloat): Option[Float] = if (v == null) None else Some(v.floatValue)
    implicit def optBoolean(v: JBoolean): Option[Boolean] = if (v == null) None else Some(v.booleanValue)
  }


  implicit def nullity_implicits[A >: Null](nullity: =>A) = new {
    // poor man's safe navigation operators
    // when the res is nullable
    def ?![B >: Null](f: A=>B): B = {
      nullity match {
        case null => null.asInstanceOf[B]
        case v => f(v)
      }
    }
    // when the res is not nullable
    def ?!![B](f: A=>B): Option[B] = {
      nullity match {
        case null => None
        case v => Some(f(v))
      }
    }

    // groovy elvis operator
    // NB: the args are inverted as it ends in ':'  !
    def ?:[B >: A](pred: B) = if (pred == null) nullity else pred
  }

  implicit def flow_implicits[A](v: A) = new {
    def stating(f: (A => Any)*): A = {
      f.foreach{ _(v) }
      v
    }

    def whether(f: (A => Boolean)) = {
      if(f(v)) Some(v) else None
    }


  }

  implicit def cast_implicits[A](v: A) = new {
    type CastableFromA[B] = A => B

    def castTo[B: CastableFromA ]: B = v

    def asSafe[B]: Option[B] = {
      if(v.isInstanceOf[B]) Some(v.asInstanceOf[B])
      else None
    }
  }



}

object StringUtils {
  val nmap = Map.empty[Char, Char] +
    ('«' -> '\"') +
    ('»' -> '\"')

  def normalize(s: String) = {
    (s /: nmap)((a, e) => a.replace(e._1, e._2))
  }
}
