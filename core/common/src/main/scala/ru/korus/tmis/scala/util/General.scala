package ru.korus.tmis.scala.util

import scala.util.control.Exception.allCatch

import Types.{JInteger, JDouble, JFloat, JBoolean}
import scala.Some
import scala.language.reflectiveCalls

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


  // equality operator that only works for values of the same type
  // the reason for this is that you can do in scala:
  // "X" == Some("X") // false
  // 1 == "T" // false
  // Sometimes these cases should be considered type errors:
  // import General.typedEquality
  // "X" =!= Some("X") // type mismatch: expected String, found Option[String]
  // -------------------------------------------------------
  // why separate type TypedEquality?
  //   cos of this bug =>
  //        https://issues.scala-lang.org/browse/SI-1906
  // and
  //   cos of stupid scala creators =>
  //        http://scala-programming-language.1934581.n4.nabble.com/scala-Structural-types-with-generic-type-question-td1992248.html#a1992249
  sealed case class TypedEquality[A](v: A) {
    def =!=(v1: A) = v == v1
  }

  implicit def typedEquality[A](v: A) = TypedEquality(v)

  implicit def nullity_implicits[A >: Null](nullity: => A) = new {
    // poor man's safe navigation operators
    //-------------------------------------
    // when the res is nullable
    // usage:
    // rewrite the code: if(a != null && a.b != null && a.b.c != null) { a.b.c.d } else null
    // to: a ?! {_.b} ?! {_.c} ?! {_.d}
    def ?![B >: Null](f: A => B): B = {
      nullity match {
        case null => null.asInstanceOf[B]
        case v => f(v)
      }
    }

    // when the res is not nullable
    // usage:
    // rewrite the code: if(a != null && a.b != null && a.b.c != null) { a.b.c.d } else 0
    // to: a ?! {_.b} ?! {_.c} ?!! {_.d} getOrElse 0
    def ?!![B](f: A => B): Option[B] = {
      nullity match {
        case null => None
        case v => Some(f(v))
      }
    }

    // groovy elvis operator
    // NB: the args are inverted as it ends in ':'  !
    // usage:
    // rewrite the code: if(a != null) a else b
    // to: a ?: b
    def ?:[B >: A](pred: B) = if (pred == null) nullity else pred
  }

  implicit def flow_implicits[A](v: A) = new {
    // example
    // before:
    // var j = new JList[Int]()
    // j.add(1)
    // j.add(2)
    // j.add(3)
    // j = j.map(_+1).filter(...) ...
    // after:
    // val j = new JList[Int]() stating (_.add(1),_.add(2)) map (_+1) filter (...)
    def stating(f: (A => Any)*): A = {
      f.foreach {
        _(v)
      }
      v
    }

    def whether(f: (A => Boolean)) = {
      if (f(v)) Some(v) else None
    }


  }

  implicit def cast_implicits[A](v: A) = new {
    type CastableFromA[B] = A => B

    def castTo[B: CastableFromA]: B = v

    def asSafe[B]: Option[B] = {
      if (v.isInstanceOf[B]) Some(v.asInstanceOf[B])
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
