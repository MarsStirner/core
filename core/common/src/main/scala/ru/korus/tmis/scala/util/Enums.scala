package ru.korus.tmis.scala.util

import scala.language.implicitConversions

sealed case class StringId(id: String) {
  override def toString = {
    id
  }

  implicit def wrapString(str: String) = {
    StringId(str)
  }
}

package object Package {
  type TypedStringId = (StringId, String)
}
