package ru.korus.tmis.core.data

import ru.korus.tmis.core.entity.model.JobTicket
import java.util.Date
import scala.beans.BeanProperty
import javax.xml.bind.annotation.{XmlRootElement, XmlType}

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 5/19/14
 * Time: 5:09 PM
 */
@XmlType(name = "JobTicketContainer")
@XmlRootElement(name = "JobTicketContainer")
class JobTicketContainer(jobTicket: JobTicket) {

  @BeanProperty
  var id: Integer = jobTicket.getId

  @BeanProperty
  var jobId: Int = jobTicket.getJob.getId

  @BeanProperty
  var idx: Int = jobTicket.getIdx

  @BeanProperty
  var datetime: Date = jobTicket.getDatetime

  @BeanProperty
  var resTimestamp: Date = jobTicket.getResTimestamp

  @BeanProperty
  var resConnectionId: Int = jobTicket.getResConnectionId

  @BeanProperty
  var status: Int = jobTicket.getStatus

  @BeanProperty
  var begDateTime: Date = jobTicket.getBegDateTime

  @BeanProperty
  var endDateTime: Date = jobTicket.getEndDateTime

  @BeanProperty
  var label: String = jobTicket.getLabel

  @BeanProperty
  var note: String = jobTicket.getNote

}
