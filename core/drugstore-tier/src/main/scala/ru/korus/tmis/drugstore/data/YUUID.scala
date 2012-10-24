package ru.korus.tmis.drugstore.data

import java.lang.{Integer => JInteger}

object YUUID {
  def generate(o: AnyRef, id: JInteger) = {
    val longId = if (id != null) {
      id.longValue
    } else {
      -1
    }
    new java.util.UUID(o.getClass.getCanonicalName.hashCode, longId).toString
  }

  def generateRandom() = {
    java.util.UUID.randomUUID().toString
  }

  def generateById[A <: AnyRef {def getId() : JInteger}](o: A): String = generate(o, o.getId)
}
