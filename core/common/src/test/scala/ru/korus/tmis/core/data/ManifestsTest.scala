package ru.korus.tmis.core.data


import java.util

import org.scalatest.testng.TestNGSuite
import org.testng.annotations.Test
import ru.korus.tmis.util.reflect.Manifests
import Manifests._

class ManifestsTest extends TestNGSuite {
  @Test
  def test_manifestFromClass() {
    // primitive
    assert(manifest[Int] == manifestFromClass(classOf[Int]))
    // class
    assert(manifest[String] == manifestFromClass(classOf[String]))
    // generic
    assert(manifest[util.Set[_]] == manifestFromClass(classOf[util.Set[_]]))

    assert(manifest[Int] == manifestFromClass(java.lang.Integer.TYPE))
    assert(manifest[String] == manifestFromClass("".getClass))

    //assert( manifest[Set[_]] >:> manifestFromClass(Set(1,2,3).getClass) )
  }
}