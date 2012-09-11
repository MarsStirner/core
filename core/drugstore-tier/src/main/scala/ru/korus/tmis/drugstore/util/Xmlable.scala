package ru.korus.tmis.drugstore.util

import java.io.StringReader
import javax.xml.bind.annotation.XmlTransient
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.sax.SAXResult
import org.w3c.dom.{Node => DomNode, Document}
import org.xml.sax.InputSource

import xml.Node
import xml.parsing.NoBindingFactoryAdapter

object Xmlable {
  val domBuilderFactory = DocumentBuilderFactory.newInstance()
  domBuilderFactory.setNamespaceAware(true)

  lazy val domBuilder = domBuilderFactory.newDocumentBuilder()

  def asScalaXml(dom: DomNode): Node = {
    val adapter = new NoBindingFactoryAdapter
    TransformerFactory
    .newInstance()
    .newTransformer()
    .transform(new DOMSource(dom), new SAXResult(adapter))
    adapter.rootElem
  }

  def asJavaXml(node: Node): Document = {
    // it's lame but it works
    val is = new InputSource(new StringReader(node.toString));
    Xmlable.domBuilder.parse(is)
  }
}

trait Xmlable {
  @XmlTransient
  lazy val domBuilder = Xmlable.domBuilder

  def toXmlString: String

  def toXmlDom: Document

  override def toString = toXmlString
}
