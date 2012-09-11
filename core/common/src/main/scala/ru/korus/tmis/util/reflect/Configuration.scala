package ru.korus.tmis.util.reflect

import ru.korus.tmis.util.StringId
import java.net.URL
import java.text.SimpleDateFormat
import reflect.Manifest

import Manifests.manifestFromClass

trait Configuration extends Reflective {
  private lazy val conversions = Map[Manifest[_], String => Any](
                                                          manifest[String] -> {x: String => x},
                                                          manifest[Int] -> {x: String => x.toInt},
                                                          manifest[StringId] -> {StringId(_)},
                                                          manifest[Short] -> {x: String => x.toShort},
                                                          manifest[URL] -> {new URL(_)},
                                                          manifest[SimpleDateFormat] -> {new SimpleDateFormat(_)},
                                                          manifest[Set[_]] -> { it: String => it.split(",").toSet[String] }
                                                        )

  private def convert(m: Manifest[_]): Option[String => Any] = {
    conversions.get(m).orElse{
      conversions.find{ case (k,_) => k >:> m  }.map(_._2)
    }
  }

  lazy val children =
    this.
    methods.
    filter {case (_, field) => classOf[Configuration].isAssignableFrom(field.getReturnType)}.
    collect {case (name, field) => (name, field.invoke(this).asInstanceOf[Configuration])}

  lazy val fields =
    this.
    methods.
    filter {case (_, field) => !classOf[Configuration].isAssignableFrom(field.getReturnType)}.
    collect {case (name, field) => (name, field)}

  def setSetting(path: String, value: String): Boolean = {
    def catchy[A](v: => A): Option[A] = try Some(v) catch {case ex => None}

    def tokens = path.split("\\.", 2) // take only the first token, leave others
    tokens match {
      case Array() => false
      case Array(name) => fields.
                          get(name). // fetch the getter by name
                          flatMap {it => convert(manifestFromClass(it.getReturnType))}. // take the
                          // conversion function
                          flatMap {func => catchy {func(value)}}. // apply conversion to string value,
                          // returning the value of needed type
                          map {it => this.setField(name, it)}. // set the field with the function result
                          isDefined // return true on success, false otherwise
      case Array(head, tail) => children.
                                get(head). // fetch child of name `head`
                                map {child => child.setSetting(tail, value)}. // recurse to child node
                                getOrElse(false) // if child returned false, or we were screwed some time before, return false

      // split("whatever",2) can only return w0 - 2 tokens on any string
      case _ => error("Configuration.setSetting: should never happen")
    }
  }
}