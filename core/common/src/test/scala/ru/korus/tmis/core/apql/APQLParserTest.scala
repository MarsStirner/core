package ru.korus.tmis.core.apql

import org.junit.Test

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 9/15/14
 * Time: 2:41 PM
 */
class APQLParserTest {

  @Test def parseCondition(): Unit = parse(
    """IF (getActionsByEvent(12, "typeCode").first().hasProperty("propertyCode"))
      |THEN
      |  getActionsByEvent(12, "typeCode").first().getProperty("propertyCode")""".stripMargin
  )

  private def parse(s: String): Unit = {
    val p = new APQLParser
    p.parse(s) match {
      case x: p.NoSuccess => assert(assertion = false, "\n" + x)
      case x: AnyRef => System.out.println(x)
    }
  }

}
