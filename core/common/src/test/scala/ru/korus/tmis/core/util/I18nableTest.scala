package ru.korus.tmis.core.util

import org.junit.Test
import java.util.{List => JList, LinkedList}

import org.junit.Assert._
import ru.korus.tmis.scala.util.I18nable


class I18nableTest {

  object Tester extends I18nable {
    def tissueNotFoundMessage = i18n("error.takenTissueNotFound", 18)
  }

  @Test
  def testFormatting() {
    assertEquals(Tester.tissueNotFoundMessage, "Биоматериал # 18 не найден")

  }

}
