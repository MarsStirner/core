package ru.korus.tmis.laboratory

import javax.annotation.Resource
import javax.ejb.{ActivationConfigProperty, EJB, MessageDriven}
import javax.jms._
import javax.persistence.{EntityManager, PersistenceContext}

import ru.korus.tmis.core.database.DbJobTicketBeanLocal
import ru.korus.tmis.core.database.common.{DbActionBeanLocal, DbActionPropertyBeanLocal}
import ru.korus.tmis.core.entity.model._
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.lis.data.jms.LabModuleSendingResponse
import ru.korus.tmis.lis.data.model.hl7.complex.POLBIN224100UV01

import scala.collection.JavaConverters._
/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 8/5/14
 * Time: 7:25 PM
 */
@MessageDriven(
  mappedName = "LaboratoryQueue",
  activationConfig = Array(
    new ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"))
)
class MISMessageReceiver extends MessageListener {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  @EJB
  private var dbActionBean: DbActionBeanLocal = _

  @EJB
  private var dbJobTicketBean: DbJobTicketBeanLocal = _

  @EJB
  private var s: DbActionPropertyBeanLocal = _

  @Resource(lookup = "QueueConnectionFactory")
  var qcf: QueueConnectionFactory = _

  override def onMessage(message: Message) = {
    try {
      Option(message) collect {
        case m: ObjectMessage => Option(m.getObject) collect {
          case o: LabModuleSendingResponse => Option(message.getJMSType) collect {
            case LabModuleSendingResponse.JMS_TYPE =>
              try {
                val action = dbActionBean.getActionById(o.getActionId) // "Проверка" что такой Action существует
                if (o.isSuccess) {
                  dbActionBean.updateActionStatusWithFlush(o.getActionId, ActionStatus.WAITING.getCode)
                  setJobTicketStatusOfResearch(action, JobTicket.STATUS_IS_FINISHED)
                } else {
                  setJobTicketStatusOfResearch(action, JobTicket.STATUS_IN_PROGRESS)
                }
              } catch {
                case e: CoreException => e.printStackTrace()

                case t: Throwable => t.printStackTrace()
              }
          }
          case o: POLBIN224100UV01 =>

        }
      }
    } finally { // Always sending reply if possible
      if (message.getJMSReplyTo != null) {
        val c = qcf.createQueueConnection()
        val s = c.createQueueSession(false, Session.AUTO_ACKNOWLEDGE)
        val p = s.createProducer(message.getJMSReplyTo)
        val replyMessage = s.createTextMessage("Hello!")
        replyMessage.setJMSCorrelationID(message.getJMSCorrelationID)
        p.send(replyMessage)
      }
    }
  }

  private def setJobTicketStatusOfResearch(action: Action, status: Int) = {
    for(l: ActionProperty <- action.getActionProperties.asScala) {
      if(l.getType.getCode != null && l.getType.getCode.equals("TAKINGTIME")) {
        val values = s.getActionPropertyValue(l)
        if(values.size() == 1) {
          values.get(0) match {
            case jtv: APValueJobTicket => {
              val jt = jtv.getJobTicket
              jt.setStatus(status)
              jt.setNote(jt.getNote + "Невозможно передать данные об исследовании '%s'. ".format(action.getId))
              jt.setLabel("##Ошибка отправки в ЛИС##")
              em merge jt
              em.flush()
            }
            case _ =>
          }
        } else {
          //TODO Log - что-то не так, несколько Job_Tiket-ов на одно исследование
        }
      }
    }
  }

}
