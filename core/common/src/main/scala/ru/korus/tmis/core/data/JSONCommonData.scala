package ru.korus.tmis.core.data

import ru.korus.tmis.util.ConfigManager

import reflect.BeanProperty
import java.lang.Integer
import java.util.{Date, LinkedList}
import javax.xml.bind.annotation._
import javax.xml.bind.annotation.adapters.{XmlJavaTypeAdapter, XmlAdapter}
import ru.korus.tmis.core.entity.model.Mkb

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
  var assignerId: Int = _
  @BeanProperty
  var patientId: Int = _
  @BeanProperty
  var createPerson: Int = _
  @BeanProperty
  var createDateTime: Date = _
  @BeanProperty
  var plannedEndDate: Date = _
  @BeanProperty
  var plannedTime: ScheduleContainer = _
  @BeanProperty
  var urgent: Boolean = _
  @BeanProperty
  var overQueue: Boolean = _
  @BeanProperty
  var finance: IdNameContainer = _
  @BeanProperty
  var diagnosis: MKBContainer = _
  @BeanProperty
  var coreVersion: String = _

  def this(eventId: Int,
           actionTypeId: Int,
           executorId: Int,
           assignerId: Int,
           patientId: Int,
           plannedEndDate: Long,
           plannedTime: Long,
           mkb: Mkb,
           urgent: Boolean,
           overQueue: Boolean,
           financeId: Int,
           financeName: String) = {
    this()
    this.eventId = eventId
    this.actionTypeId = actionTypeId
    this.executorId = executorId
    this.assignerId = assignerId
    this.patientId = patientId
    this.plannedEndDate = if (plannedEndDate == 0) {
      null
    } else {
      new Date(plannedEndDate)
    }
    //this.plannedTime = new ScheduleContainer(0, 0, Date(plannedTime))
    this.urgent = urgent
    this.overQueue = overQueue
    //this.finance = new IdNameContainer(event.getEventType.getFinance.getId.intValue(), event.getEventType.getFinance.getName)
    this.finance = new IdNameContainer(financeId, financeName)
    this.diagnosis = new MKBContainer(mkb)
    this.coreVersion = ConfigManager.Messages("misCore.assembly.version")
  }

  def rewriteDefault(request: ConsultationRequestData) = {
    request.coreVersion = ConfigManager.Messages("misCore.assembly.version")
    request
  }
}