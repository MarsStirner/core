package ru.korus.tmis.drugstore.data

import ru.korus.tmis.core.entity.model.Action

object YRelocationDocumentBuilder {

  def createIncoming(a: Action, orgRef: String): YRelocationDocument = {
    new YIncomingRelocationDocument(a, orgRef)
  }

  def createOutgoing(a: Action): YRelocationDocument = {
    new YOutgoingRelocationDocument(a)
  }

  def createMoving(a: Action, fromRef: String, toRef: String): YRelocationDocument = {
    new YMovingRelocationDocument(a, fromRef, toRef)
  }

}
