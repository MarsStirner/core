package ru.korus.tmis.drugstore.data

import ru.korus.tmis.drugstore.util.ScalaXmlable

import java.text.SimpleDateFormat

object YRelocationDocument {
  lazy val timeFormat = new SimpleDateFormat("yyyyMMddHHmmss")
  lazy val dateFormat = new SimpleDateFormat("yyyyMMdd")
}

trait YRelocationDocument extends ScalaXmlable {
  // implemented stuff
  def timeFormat = YRelocationDocument.timeFormat

  def dateFormat = YRelocationDocument.dateFormat

  def rootElement: String = "Message"

  def soapAction: String

  def soapOperation: String

  def xsiType: String
}
