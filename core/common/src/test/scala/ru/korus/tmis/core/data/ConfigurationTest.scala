package ru.korus.tmis.core.data

import org.testng.annotations.Test
import ru.korus.tmis.util.reflect.Configuration

import reflect.Manifest
import ru.korus.tmis.scala.util.StringId
import scala.language.reflectiveCalls

class ConfigurationTest {

  object MockConfigManager extends Configuration {
    var setting0 = "defaultValue0"
    var setting1_int = 123
    var setting2_short: Short = 123
    var setting3_stringId: StringId = _
    var settings_set = Set("1", "2", "3")

    val MockChild = new Configuration {
      var setting0 = "defaultValue0"
      var setting1_int = 123
      var setting2_short: Short = 123
    }
  }

  def manifestOf[M](v: M)(implicit man: Manifest[M]) = man

  def id[A](v: A): A = v

  @Test
  def testSetSetting() {


    val mcm = MockConfigManager
    val child = mcm.MockChild
    val truthness: Boolean => Boolean = id
    val falseness: Boolean => Boolean = !_

    mcm setSetting("", "") ensuring falseness
    mcm setSetting("setting0", "lorem ipsum") ensuring truthness
    mcm ensuring (it => it.setting0 == "lorem ipsum")

    mcm setSetting("MockChild.setting0", "dolor sit amet") ensuring truthness
    child ensuring (it => it.setting0 == "dolor sit amet")

    mcm setSetting("setting1_int", "100500") ensuring truthness

    mcm setSetting("setting1_int", "100500") ensuring truthness
    mcm ensuring (it => it.setting1_int == 100500)

    mcm setSetting("MockChild.setting1_int", "42") ensuring truthness
    child ensuring (it => it.setting1_int == 42)

    mcm setSetting("setting3_stringId", "Hello") ensuring truthness
    mcm ensuring (it => it.setting3_stringId == StringId("Hello"))

    mcm setSetting("setting1_int", "something completely inappropriate as integer") ensuring falseness
    mcm ensuring (it => it.setting1_int == 100500)

    mcm setSetting("settings_set", "1,2,3,4,5") ensuring truthness
    mcm ensuring (it => it.settings_set == Set("1", "2", "3", "4", "5"))

  }


}