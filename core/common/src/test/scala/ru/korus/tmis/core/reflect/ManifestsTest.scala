package ru.korus.tmis.core.reflect

import org.junit.Test

import org.junit.Assert._

import java.lang.{Integer => JInteger}

import ru.korus.tmis.util.reflect.Manifests
import Manifests._;

class ManifestsTest {

  @Test
  def test_actuallyWorkingClassOf {
    assertTrue(actuallyWorkingClassOf[Int] == classOf[Int])

    assertTrue(actuallyWorkingClassOf[JInteger] == classOf[JInteger])

  }

}
