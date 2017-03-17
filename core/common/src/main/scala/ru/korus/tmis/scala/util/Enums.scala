package ru.korus.tmis.scala.util

import scala.language.implicitConversions

sealed case class StringId(id: String) {
  override def toString: String = {
    id
  }

  implicit def wrapString(str: String): StringId = {
    StringId(str)
  }
}

package object Package {
  type TypedStringId = (StringId, String)
}
