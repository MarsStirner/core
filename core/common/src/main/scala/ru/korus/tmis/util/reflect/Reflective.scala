package ru.korus.tmis.util.reflect

import java.lang.reflect.{Method => CMethod}

import Manifests.classForValue
import Manifests.box
import Manifests.canAssign

trait Reflective {
  target =>
  lazy val methods = new Map[String, CMethod] {
    override def get(name: String) = target.getClass.getDeclaredMethods.find {
      _.getName == name
    }

    override def iterator = target.getClass.getDeclaredMethods.collect {
      case it => (it.getName, it)
    }.iterator

    override def +[B >: CMethod](kv: (String, B)) = error("Reflective.fields.+: operation unsupported")

    override def -(k: String) = error("Reflective.fields.-: operation unsupported")
  }

  lazy val fieldNames = target.
    getClass.
    getDeclaredFields.
    map {
    _.getName
  }.
    filterNot {
    _.contains("$")
  }.
    filterNot {
    it => it == "methods" || it == "fieldNames"
  }.
    toList

  def setField(name: String, value: Any) = {
    lazy val setter = methods.get(name + "_$eq")


    setter match {
      case (Some(f)) => {

        f.getParameterTypes match {
          case Array(sType) if canAssign(sType, classForValue(value)) => {
            f.invoke(target, box(value));
            true
          }
          case _ => false
        }
      }
      case _ => false
    }
  }

  def getField[T: Manifest](name: String) = invokeMethod[T](name)


  def invokeMethod[T: Manifest](name: String, args: AnyRef*): Option[T] = {
    lazy val classOfT = manifest[T].erasure
    try {
      methods.get(name).collect {
        case it if (classOfT.isAssignableFrom(it.getReturnType)) => it.invoke(target, args: _*).asInstanceOf[T]
      }
    } catch {
      case ex => ex.printStackTrace(); None
    }
  }
}