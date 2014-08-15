package ru.korus.tmis.laboratory.bak

import java.text.SimpleDateFormat
import java.util.{Date, UUID}
import javax.annotation.Resource
import javax.ejb.{TransactionManagement, TransactionManagementType}
import javax.jms._
import javax.jws.WebService
import javax.xml.ws.WebServiceContext

import ru.korus.tmis.lis.data.model.hl7.complex._
import ru.korus.tmis.util.logs.ToLog

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 8/14/14
 * Time: 1:41 PM
 */
@WebService(endpointInterface = "ru.korus.tmis.laboratory.bak.BakResultService", targetNamespace = "http://www.korusconsulting.ru", serviceName = "service-bak-results", portName = "service-bak-results", name = "service-bak-results")
@TransactionManagement(TransactionManagementType.BEAN)
class BakResults extends BakResultService {

  @Resource(lookup = "QueueConnectionFactory")
  var qcf: QueueConnectionFactory = _

  @Resource(lookup = "LaboratoryQueue")
  var labQueue: Queue = _

  @Resource
  private var context: WebServiceContext = _

  override def setAnalysisResults(data: POLBIN224100UV01): MCCIIN000002UV01 = {
    val toLog: ToLog = new ToLog("setAnalysisResults")
    var response: MCCIIN000002UV01 = null

    try {
      val c: QueueConnection = qcf.createQueueConnection()
      val s: QueueSession = c.createQueueSession(false, Session.AUTO_ACKNOWLEDGE)
      val request = s.createObjectMessage()
      request.setObject(data)
      val tempq = s.createTemporaryQueue()
      request.setJMSReplyTo(tempq)
      s.createSender(labQueue).send(request)
      val consumer = s.createConsumer(tempq)

      c.start()
      val reply = consumer.receive()
      c.close()
      response = createSuccessResponse
    }
    catch {
      case e: Throwable => response = createErrorResponse
    }
    finally {
      //TODO Close session & connection
    }

    response
  }

  /**
   * Создание ответного сообщения о успешном принятии результатов
   */
  private def createSuccessResponse: MCCIIN000002UV01 = {
    val response: MCCIIN000002UV01 = new MCCIIN000002UV01
    val id2: II = new II
    id2.setRoot(UUID.randomUUID.toString)
    response.setId(id2)
    val creationTime: TS = new TS
    creationTime.setValue(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date))
    val interactionId: II = new II
    interactionId.setExtension("MCCI_IN000002UV01")
    interactionId.setRoot("2.16.840.1.113883.1.6")
    val processingCode: CS = new CS
    processingCode.setCode("P")
    val processingModeCode: CS = new CS
    processingModeCode.setCode("T")
    val acceptAskCode: CS = new CS
    acceptAskCode.setCode("AL")
    val sender: MCCIMT000200UV01Sender = new MCCIMT000200UV01Sender
    sender.setTypeCode(CommunicationFunctionType.SND)
    val device: MCCIMT000200UV01Device = new MCCIMT000200UV01Device
    device.setClassCode(EntityClassDevice.DEV)
    val id: II = new II
    sender.setDevice(device)
    val receiver: MCCIMT000200UV01Receiver = new MCCIMT000200UV01Receiver
    receiver.setTypeCode(CommunicationFunctionType.RCV)
    receiver.setDevice(device)
    response.setCreationTime(creationTime)
    response.setInteractionId(interactionId)
    response.setProcessingCode(processingCode)
    response.setProcessingModeCode(processingModeCode)
    response.setAcceptAckCode(acceptAskCode)
    response.setSender(sender)
    response.getReceiver.add(receiver)
    val acknowledgement: MCCIMT000200UV01Acknowledgement = new MCCIMT000200UV01Acknowledgement
    acknowledgement.setTypeCode(AcknowledgementType.AA)
    val targetMessage: MCCIMT000200UV01TargetMessage = new MCCIMT000200UV01TargetMessage
    val id1: II = new II
    id1.setRoot(UUID.randomUUID.toString)
    targetMessage.setId(id1)
    acknowledgement.setTargetMessage(targetMessage)
    val acknowledgementDetail: MCCIMT000200UV01AcknowledgementDetail = new MCCIMT000200UV01AcknowledgementDetail
    acknowledgementDetail.setTypeCode(AcknowledgementDetailType.E)
    val code: CE = new CE
    code.setCode("INTERR")
    code.setCodeSystem("2.16.840.1.113883.5.1100")
    acknowledgementDetail.setCode(code)
    val ed: ED = new ED
    val reference: TEL = new TEL
    reference.setValue("Данные приняты успешно")
    ed.setReference(reference)
    acknowledgementDetail.setText(ed)
    acknowledgement.getAcknowledgementDetail.add(acknowledgementDetail)
    response.getAcknowledgement.add(acknowledgement)
    response
  }

  /**
   * Создание ответного сообщения о неуспешном принятии результатов
   */
  private def createErrorResponse: MCCIIN000002UV01 = {
    val response: MCCIIN000002UV01 = new MCCIIN000002UV01
    val id2: II = new II
    id2.setRoot(UUID.randomUUID.toString)
    response.setId(id2)
    val creationTime: TS = new TS
    creationTime.setValue(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date))
    val interactionId: II = new II
    interactionId.setExtension("MCCI_IN000002UV01")
    interactionId.setRoot("2.16.840.1.113883.1.6")
    val processingCode: CS = new CS
    processingCode.setCode("P")
    val processingModeCode: CS = new CS
    processingModeCode.setCode("T")
    val acceptAskCode: CS = new CS
    acceptAskCode.setCode("AL")
    val sender: MCCIMT000200UV01Sender = new MCCIMT000200UV01Sender
    sender.setTypeCode(CommunicationFunctionType.SND)
    val device: MCCIMT000200UV01Device = new MCCIMT000200UV01Device
    device.setClassCode(EntityClassDevice.DEV)
    val id: II = new II
    sender.setDevice(device)
    val receiver: MCCIMT000200UV01Receiver = new MCCIMT000200UV01Receiver
    receiver.setTypeCode(CommunicationFunctionType.RCV)
    receiver.setDevice(device)
    response.setCreationTime(creationTime)
    response.setInteractionId(interactionId)
    response.setProcessingCode(processingCode)
    response.setProcessingModeCode(processingModeCode)
    response.setAcceptAckCode(acceptAskCode)
    response.setSender(sender)
    response.getReceiver.add(receiver)
    val acknowledgement: MCCIMT000200UV01Acknowledgement = new MCCIMT000200UV01Acknowledgement
    acknowledgement.setTypeCode(AcknowledgementType.AE)
    val targetMessage: MCCIMT000200UV01TargetMessage = new MCCIMT000200UV01TargetMessage
    val id1: II = new II
    id1.setRoot(UUID.randomUUID.toString)
    targetMessage.setId(id1)
    acknowledgement.setTargetMessage(targetMessage)
    val acknowledgementDetail: MCCIMT000200UV01AcknowledgementDetail = new MCCIMT000200UV01AcknowledgementDetail
    acknowledgementDetail.setTypeCode(AcknowledgementDetailType.E)
    val code: CE = new CE
    code.setCode("INTERR")
    code.setCodeSystem("2.16.840.1.113883.5.1100")
    acknowledgementDetail.setCode(code)
    val ed: ED = new ED
    val reference: TEL = new TEL
    reference.setValue("Данные не приняты")
    ed.setReference(reference)
    acknowledgementDetail.setText(ed)
    acknowledgement.getAcknowledgementDetail.add(acknowledgementDetail)
    response.getAcknowledgement.add(acknowledgement)
    response
  }



  override def bakDelivered(orderBarCode: Integer, takenTissueJournal: String, tissueTime: String, orderBiomaterialName: String): Int = {

    var i = 10

    i += 1

    0
  }

}
