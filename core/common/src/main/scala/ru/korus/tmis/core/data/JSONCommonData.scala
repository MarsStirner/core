package ru.korus.tmis.core.data


import scala.beans.BeanProperty
import java.lang.Integer
import java.util.{Date, LinkedList}
import javax.xml.bind.annotation._
import javax.xml.bind.annotation.adapters.{XmlJavaTypeAdapter, XmlAdapter}
import ru.korus.tmis.core.entity.model.Mkb
import ru.korus.tmis.scala.util.ConfigManager
import scala.language.reflectiveCalls

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


/**
 * Параметры записи на прием к врачу
 */
@XmlType(name = "consultationRequestData")
@XmlRootElement(name = "consultationRequestData")
class ConsultationRequestData {

  /**
   * госпитализация, в рамках которой назначается прием
   */
  @BeanProperty
  var eventId: Int = _

  /**
   *  Тип действия для выбранной услуги
   */
  @BeanProperty
  var actionTypeId: Int = _

  /**
   * Врач, к которому производится запись
   */
  @BeanProperty
  var executorId: Int = _

  /**
   * Персона, которая производит запись
   */
  @BeanProperty
  var assignerId: Int = _

  /**
   * ID пациента
   */
  @BeanProperty
  var patientId: Int = _

  /**
   * ???
   */
  @BeanProperty
  var createPerson: Int = _

  /**
   * Время создания направления к врачу
   */
  @BeanProperty
  var createDateTime: Date = _

  /**
   * День, на который производится запись к врачу
   */
  @BeanProperty
  var plannedEndDate: Date = _

  /**
   * Время записи
   */
  @BeanProperty
  var plannedTime: ScheduleContainer = _

  /**
   * Срочная запись
   */
  @BeanProperty
  var urgent: Boolean = _

  /**
   * Запись свер очереди
   */
  @BeanProperty
  var overQueue: Boolean = _

  /**
   * не используется? дублирует urgent и overQueue?
   */
  @BeanProperty
  var pacientInQueue: Int = _

  /**
   * источник финансирования
   */
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