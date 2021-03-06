package ru.korus.tmis.laboratory

import javax.annotation.Resource
import javax.ejb.{ActivationConfigProperty, EJB, MessageDriven}
import javax.jms._
import javax.persistence.{EntityManager, PersistenceContext}

import org.slf4j.{LoggerFactory, Logger}
import ru.korus.tmis.core.database.DbJobTicketBeanLocal
import ru.korus.tmis.core.database.common.{DbActionBeanLocal, DbActionPropertyBeanLocal}
import ru.korus.tmis.core.entity.model._
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.lis.data.jms.{LabModuleSendingResponse, MISResultProcessingResponse}
import ru.korus.tmis.lis.data.model.hl7.complex.POLBIN224100UV01
import ru.korus.tmis.util.logs.ToLog

import scala.collection.JavaConverters._
/**
 * Эта схема была ошибочна - требуется заменить наследование каким-либо другим способом предосталения поведения.
 * Одновременно с этим требуется вынести его в common, который следует использовать как общий для всех jar - будет
 * возможность его модернизации без пересборки всех зависимых компонент,
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

  private final val logger: Logger = LoggerFactory.getLogger(classOf[MISMessageReceiver])

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
    // Creating response
    val c = qcf.createQueueConnection()
    val s = c.createQueueSession(false, Session.AUTO_ACKNOWLEDGE)
    var p: MessageProducer = null
    if (message.getJMSReplyTo != null)
      p = s.createProducer(message.getJMSReplyTo)
    val replyMessage = s.createObjectMessage()
    replyMessage.setJMSCorrelationID(message.getJMSCorrelationID)

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
                  if(o.getThrowable != null)
                    logger.error(o.getThrowable.toString)  // TODO to log
                } else {
                  setJobTicketStatusOfResearch(action, JobTicket.STATUS_IN_PROGRESS, o.getThrowable)
                }
              } catch {
                case e: CoreException => logger.error(e.toString)
                case t: Throwable => logger.error(t.toString)
              }
          }
        }
      }
    } finally { // Always sending reply if possible
      if (p != null) {
        try {
          p.send(replyMessage)
        } catch {
          case t: Throwable => logger.error(t.toString); throw t
        }
      }
      if (s != null) try { s.close() } catch {case t: Throwable =>  logger.error(t.toString) }
      if (c != null) try { c.close() } catch {case t: Throwable =>  logger.error(t.toString) }
    }
  }

  private def setJobTicketStatusOfResearch(action: Action, status: Int, t: Throwable = null) = {
    var hasBeenUpdated = false
    for(l: ActionProperty <- action.getActionProperties.asScala) {
      if(l.getType.getCode != null && l.getType.getCode.equals("TAKINGTIME")) {
        val values = s.getActionPropertyValue(l)
        if(values.size() == 1) {
          values.get(0) match {
            case jtv: APValueJobTicket =>
              val jt = jtv.getJobTicket
              jt.setStatus(status)
              if(status.equals(JobTicket.STATUS_IN_PROGRESS)) { // Статус возвращается только в случае ошибки
                jt.setNote(jt.getNote + "Невозможно передать данные об исследовании '" + action.getId + "'. "+
                  {if(t != null) t.getMessage else "" } + "\n")
                jt.setLabel("##Ошибка отправки в ЛИС##")
              }
              em merge jt
              em.flush()
              hasBeenUpdated = true
            case _ =>
          }
        } else {
          //TODO Log - что-то не так, несколько Job_Tiket-ов на одно исследование
        }
      }
    }
    if(!hasBeenUpdated)
      logger.error("Не удалось проставить статус JobTicket-у. Возможно, у ActionType["
        + action.getActionType.getId + "] нет свойства \"Время забора\" с кодом TAKINGTIME" )
  }

}
