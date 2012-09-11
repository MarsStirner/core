package ru.korus.tmis.drugstore.data

import ru.korus.tmis.drugstore.util.ScalaXmlable

import org.junit.Test
import org.w3c.dom.Document

import java.io.StringWriter
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

class XmlableTest {

  class FooXmlable extends ScalaXmlable {
    override val toXml = <foo><bar/></foo>
  }

  def xmlToString(node: Document): String = {
    val source = new DOMSource(node.getDocumentElement);
    val stringWriter = new StringWriter();
    val result = new StreamResult(stringWriter);
    val factory = TransformerFactory.newInstance();
    val transformer = factory.newTransformer();
    transformer.transform(source, result);
    stringWriter.getBuffer().toString();
  }

  @Test
  def testToDom = {
    val bar = new FooXmlable
    println(bar.toXml)
    println(xmlToString(bar.toXmlDom))
  }
}
