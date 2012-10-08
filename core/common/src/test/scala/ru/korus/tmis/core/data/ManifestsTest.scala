package ru.korus.tmis.core.data


import org.junit.Test
import ru.korus.tmis.util.reflect.Manifests
import Manifests._
import java.util.Set

class ManifestsTest {
  @Test
  def test_manifestFromClass {
    // primitive
    assert(manifest[Int] == manifestFromClass(classOf[Int]))
    // class
    assert(manifest[String] == manifestFromClass(classOf[String]))
    // generic
    assert(manifest[Set[_]] == manifestFromClass(classOf[Set[_]]))

    assert(manifest[Int] == manifestFromClass(java.lang.Integer.TYPE))
    assert(manifest[String] == manifestFromClass("".getClass))

    //assert( manifest[Set[_]] >:> manifestFromClass(Set(1,2,3).getClass) )
  }
}