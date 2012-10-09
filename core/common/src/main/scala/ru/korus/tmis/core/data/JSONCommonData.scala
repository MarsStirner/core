package ru.korus.tmis.core.data

import ru.korus.tmis.util.ConfigManager

import reflect.BeanProperty
import java.lang.Integer
import java.util.{Date, LinkedList}
import javax.xml.bind.annotation._
import javax.xml.bind.annotation.adapters.{XmlJavaTypeAdapter, XmlAdapter}

@XmlType(name = "jsonCommonData")
@XmlRootElement(name = "jsonCommonData")
class JSONCommonData {
  @BeanProperty
  var requestData: AnyRef = _
  @BeanProperty
  var data = new LinkedList[CommonEntity]

  def this(request: AnyRef) {
    this()
    if (request.isInstanceOf[ConsultationRequestData]) {
      this.requestData = request
    }
    else
      this.requestData = request
  }

  def this(request: AnyRef, data: CommonData) {
    this(request)
    this.data = data.entity
  }
}

//дефолтный реквест
@XmlType(name = "commonRequestData")
@XmlRootElement(name = "commonRequestData")
class CommonRequestData {

}

//peквест на создание направления к врачу
@XmlType(name = "consultationRequestData")
@XmlRootElement(name = "consultationRequestData")
class ConsultationRequestData {

  @BeanProperty
  var eventId: Int = _
  @BeanProperty
  var actionTypeId: Int = _
  @BeanProperty
  var executorId: Int = _
  @BeanProperty
  var patientId: Int = _
  @BeanProperty
  var beginDate: Date = _
  @BeanProperty
  var endDate: Date = _
  @BeanProperty
  var urgent: Boolean = _
  @BeanProperty
  var coreVersion: String = _

  def this(eventId: Int,
           actionTypeId: Int,
           executorId: Int,
           patientId: Int,
           beginDate: Long,
           endDate: Long,
           urgent: Boolean) = {
    this()
    this.eventId = eventId
    this.actionTypeId = actionTypeId
    this.executorId = executorId
    this.patientId = patientId
    this.beginDate = if (beginDate == 0) {
      null
    } else {
      new Date(beginDate)
    }
    this.endDate = if (endDate == 0) {
      null
    } else {
      new Date(endDate)
    }
    this.urgent = urgent
    this.coreVersion = ConfigManager.Messages("misCore.assembly.version")
  }

  def rewriteDefault(request: ConsultationRequestData) = {
    request.coreVersion = ConfigManager.Messages("misCore.assembly.version")
    request
  }
}