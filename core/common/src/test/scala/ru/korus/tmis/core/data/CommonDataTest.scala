package ru.korus.tmis.core.data

import java.io.{StringReader, ByteArrayOutputStream}
import javax.xml.bind.JAXBContext

import org.scalatest.testng.TestNGSuite
import org.testng.annotations.Test

class CommonDataTest extends TestNGSuite {
  @Test
  def testJAXB() {
    val cd = new CommonData add
      (new CommonEntity(2, 42, "Нейм", "Тайп", 28, 17, "Кодовый код") add
        (new CommonGroup(3, "Нейм оф групп") add
          new CommonAttribute(17,
            42,
            "Господь Бог",
            "Тайп оф ГАД",
            "6_6_6",
            "ЗНАЧЕНИЕ")))

    val jaxb = JAXBContext.newInstance("ru.korus.tmis.core.data")
    val m = jaxb.createMarshaller
    val um = jaxb.createUnmarshaller

    val s = new ByteArrayOutputStream()
    m.marshal(cd, s)
    val dc = um.unmarshal(new StringReader(s.toString))
  }
}
