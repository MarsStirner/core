package ru.korus.tmis.core.util

import org.testng.annotations.Test
import ru.korus.tmis.scala.util.I18nable
import scala.language.reflectiveCalls


class I18nableTest {

  object Tester extends I18nable {
    def tissueNotFoundMessage: String = i18n("error.takenTissueNotFound", 18)
  }

  @Test
  def testFormatting() {
    assert(Tester.tissueNotFoundMessage.equals("Биоматериал # 18 не найден"))
  }

}
