package ru.korus.tmis.laboratory

import javax.ejb.{ActivationConfigProperty, MessageDriven}
import javax.jms.{Message, MessageListener, ObjectMessage}

import ru.korus.tmis.lis.data.jms.LabModuleSendingResponse

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 8/5/14
 * Time: 7:25 PM
 */
@MessageDriven(
  mappedName = "LaboratoryTopic",
  activationConfig = Array(
    new ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"))
)
class MISMessageReceiver extends MessageListener {

  override def onMessage(message: Message) = Option(message) collect {
    case m: ObjectMessage => Option(m.getObject) collect {
      case o: LabModuleSendingResponse => Option(message.getJMSType) collect {
        case LabModuleSendingResponse.JMS_TYPE  => {
          //TODO Processing: set action and JobTicket status
        }
      }
    }
  }

}
