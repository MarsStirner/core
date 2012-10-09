package ru.korus.tmis.drugstore.data


import java.io.{StringReader, ByteArrayOutputStream}
import javax.xml.bind.JAXBContext
import org.junit.Test

import ru.korus.tmis.drugstore.data.{YOutgoingRelocationDocument, YIncomingRelocationDocument, YMovingRelocationDocument, YRelocationDocument}
import xml.{XML, PrettyPrinter}
import ru.korus.tmis.core.entity.model._
import java.util.{Date, Calendar, LinkedList}

class YRelocationDocumentTest {
  @Test
  def testCreation() {
    val e = new Event
    e.setId(1488)

    val a = new Action()
    a.setId(15)
    a.setEvent(e)
    a.setBegDate(new Date(100500))
    a.setEndDate(new Date())

    val p = new Patient
    p.setId(317)
    p.setFirstName("Иван")
    p.setPatrName("Петрович")
    p.setLastName("Сидоров")
    p.setBirthDate(new Date)
    p.setSex(3)

    p.addEvent(e)
    e.setPatient(p)


    val org0 = new OrgStructure()
    org0.setId(42)
    val org1 = new OrgStructure()
    org1.setId(43)

    val mov = new YMovingRelocationDocument(a, org0.toString, org1.toString)
    val in = new YIncomingRelocationDocument(a, org1.toString)
    val out = new YOutgoingRelocationDocument(a)



    println(mov)
    println(in)
    println(out)

  }
}
