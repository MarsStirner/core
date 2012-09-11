package ru.korus.tmis.util.reflect.clones

import java.util.Map
import com.rits.cloning.{IFastCloner}
import scala.reflect.Manifest
import ru.korus.tmis.util.reflect.Manifests
import scala.annotation.implicitNotFound

@implicitNotFound(msg = "No static cloner found for class ${T}.")
trait StaticCloner[T] {
  def clone(v:T): T
  def cloneExcluding(v: T, excluding: AnyRef*): T
}

object Cloner{
  private[this] val cloner = new com.rits.cloning.Cloner()
  // but I really don't give a fuck about what happens if you try to clone a scala case class
  // maybe just don't do that?..
  cloner.dontCloneInstanceOf(classOf[scala.Immutable])

  def deepCloner[T] = new StaticCloner[T] {
    def clone(v: T) = cloner.deepClone(v)
    def cloneExcluding(v: T, excluding: AnyRef*) = cloner.deepCloneDontCloneInstances(v, excluding : _*)
  }

  def deepClone[T](v: T)(implicit cloningRes: StaticCloner[T] = deepCloner[T]):T = cloningRes.clone(v)
  def deepCloneExcluding[T](v: T, excluding: AnyRef*)(implicit cloningRes: StaticCloner[T] = deepCloner[T]) =
    cloningRes.cloneExcluding(v, excluding: _*)

  object Java {
    def deepClone[T](v: T):T = Cloner.deepClone(v)(deepCloner)
    def deepCloneExcluding[T](v: T, excluding: AnyRef*) = Cloner.deepCloneExcluding(v, excluding: _*)
  }

  def deepCloneJ[T](v: T) = Java.deepClone(v)
  def deepCloneExcludingJ[T](v: T, excluding: AnyRef*) = Java.deepCloneExcluding(v, excluding:_*)

  //

}
