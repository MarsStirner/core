package ru.korus.tmis.scala.util

import annotation.implicitNotFound

@implicitNotFound(msg = "No default value found for class ${D}.")
trait Defaultible[D] {
  def default: D
}


object Defaultible {

  // instances are expected to be implemented like this:
  // implicit def stringDefaultible = setDefault[String]("")
  def setDefault[D](v: D) = new Defaultible[D] {
    override val default = v
  }

  def defaultValue[D: Defaultible] = implicitly[Defaultible[D]].default
}

