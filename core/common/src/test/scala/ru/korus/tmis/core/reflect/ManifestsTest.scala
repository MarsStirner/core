package ru.korus.tmis.core.reflect

import java.lang.{Integer => JInteger}

import org.testng.annotations.Test
import ru.korus.tmis.util.reflect.Manifests
import Manifests._

class ManifestsTest {

  @Test
  def test_actuallyWorkingClassOf() {
    assert(actuallyWorkingClassOf[Int] == classOf[Int])

    assert(actuallyWorkingClassOf[JInteger] == classOf[JInteger])

  }

}
