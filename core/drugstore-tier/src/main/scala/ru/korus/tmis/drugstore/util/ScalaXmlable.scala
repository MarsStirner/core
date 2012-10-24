package ru.korus.tmis.drugstore.util

import scala.xml.Node

object ScalaXmlable {

  import Xmlable._

  def toDOM(node: ScalaXmlable) = asJavaXml(node.toXml)
}

trait ScalaXmlable extends Xmlable {
  def toXml: Node

  override def toXmlDom = ScalaXmlable.toDOM(this)

  override def toXmlString = toXml toString
}
