package ru.korus.tmis.ws.laboratory.bak.ws.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.util.CompileTimeConfigManager;
import ru.korus.tmis.ws.laboratory.bak.model.params.ParamBakDelivered;
import ru.korus.tmis.ws.laboratory.bak.ws.server.model.ResponseHL7;
import ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex.*;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.SimpleDateFormat;
import java.util.Date;

import static ru.korus.tmis.util.CompileTimeConfigManager.Laboratory.Namespace;
import static ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.HL7Specification.NAMESPACE;
import static ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.HL7Specification.SUCCESS_ACCEPT_EVENT;

/**
 * Веб-сервис для сохранения результатов исследования из лаборатории
 * <p/>
 * test class @see {{ru.korus.tmis.ws.laboratory.bak.BulkLISWebServiceImplSpec}}
 *
 * @author anosov@outlook.com
 *         date: 5/21/13
 */
@WebService(
  endpointInterface = "ru.korus.tmis.ws.laboratory.bak.ws.server.IBakMISWebService",
  targetNamespace = CompileTimeConfigManager.Laboratory.Namespace,
  serviceName = IBakMISWebService.SERVICE_NAME,
  portName = IBakMISWebService.PORT_NAME,
  name = IBakMISWebService.PORT_NAME)
public class BakMISWebService implements IBakMISWebService {

    private static final Logger log = LoggerFactory.getLogger(BakMISWebService.class);
//
//    @Override
//    public int setAnalysisResults(
//          ) {
//
//        log.info("\n========QUERY setAnalysisResults============\n" +
//                "orderMisId          [" + orderMisId + "]\n" +
//                "orderBarCode        [" + orderBarCode + "]\n" +
//                "takenTissueJournal  [" + takenTissueJournal + "]\n" +
//                "referralIsFinished  [" + referralIsFinished + "]\n" +
//                "result              [" + result + "]\n" +
//                "biomaterialDefects  [" + biomaterialDefects + "]\n" +
//                "ResultDoctorLisId   [" + resultDoctorLisId + "]\n" +
//                "resultDoctorLisName [" + resultDoctorLisName + "]\n" +
//                "codeLIS             [" + codeLIS + "]\n");
//
//        ParamSetResult in = initInParams(orderMisId,
//                orderBarCode,
//                takenTissueJournal,
//                referralIsFinished,
//                result,
//                biomaterialDefects,
//                resultDoctorLisId,
//                resultDoctorLisName,
//                codeLIS);
//        boolean valid = in.validate();
//        if (!valid) {
//            // do
//        }
//        return 0;
//    }

    @Override
    @WebMethod(operationName = "LIS_SetAnalysisResults")
    @WebResult(name = SUCCESS_ACCEPT_EVENT, targetNamespace = NAMESPACE, partName = "Body")
    public MCCIIN000002UV01 setAnalysisResults(
            @WebParam(name = "RESPONSE", targetNamespace = NAMESPACE, partName = "Body")
            ResponseHL7 request) {
        return createResponse();
    }

    private MCCIIN000002UV01 createResponse() {
        final MCCIIN000002UV01 response = new MCCIIN000002UV01();

        final II id2 = new II();
        id2.setRoot("XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXX");
        response.setId(id2);

        final TS creationTime = new TS();
        creationTime.setValue(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));

        final II interactionId = new II();
        interactionId.setExtension("MCCI_IN000002UV01");
        interactionId.setRoot("2.16.840.1.113883.1.6");

        final CS processingCode = new CS();
        processingCode.setCode("P");

        final CS processingModeCode = new CS();
        processingModeCode.setCode("T");

        final CS acceptAskCode = new CS();
        processingModeCode.setCode("AL");

        final MCCIMT000200UV01Sender sender = new MCCIMT000200UV01Sender();
        sender.setTypeCode(CommunicationFunctionType.SND);
        final MCCIMT000200UV01Device device = new MCCIMT000200UV01Device();
        device.setClassCode(EntityClassDevice.DEV);
        device.setDeterminerCode("INSTANCE");
        final II id = new II();
        id.getNullFlavor().add("NULL");
        device.getId().add(id);
        sender.setDevice(device);


        final MCCIMT000200UV01Receiver receiver = new MCCIMT000200UV01Receiver();
        receiver.setTypeCode(CommunicationFunctionType.RCV);
        receiver.setDevice(device);

        response.setCreationTime(creationTime);
        response.setInteractionId(interactionId);
        response.setProcessingCode(processingCode);
        response.setProcessingModeCode(processingModeCode);
        response.setAcceptAckCode(acceptAskCode);
        response.setSender(sender);
        response.getReceiver().add(receiver);

        final MCCIMT000200UV01Acknowledgement acknowledgement = new MCCIMT000200UV01Acknowledgement();
        final CS typeCode = new CS();
        typeCode.setCode("AA");
        acknowledgement.setTypeCode(typeCode);
        final MCCIMT000200UV01TargetMessage targetMessage = new MCCIMT000200UV01TargetMessage();
        final II id1 = new II();
        id1.setRoot("XXXXXXXX-XXXX-XXXX-XXXX-XXXXXXXXXXXX");
        targetMessage.setId(id1);
        acknowledgement.setTargetMessage(targetMessage);

        final MCCIMT000200UV01AcknowledgementDetail acknowledgementDetail = new MCCIMT000200UV01AcknowledgementDetail();
        acknowledgementDetail.setTypeCode(AcknowledgementDetailType.E);
        final CE code = new CE();
        code.setCode("INTERR");
        code.setCodeSystem("2.16.840.1.113883.5.1100");
        acknowledgementDetail.setCode(code);
        final ED ed = new ED();
        final TEL reference = new TEL();
        reference.setValue("Данные приняты успешно");
        ed.setReference(reference);
        acknowledgementDetail.setText(ed);
        acknowledgement.getAcknowledgementDetail().add(acknowledgementDetail);

        response.getAcknowledgement().add(acknowledgement);
        return response;
    }

    @Override
    public int bakDelivered(@WebParam(name = "orderBarCode", targetNamespace = Namespace)
                            Integer orderMisId,
                            @WebParam(name = "TakenTissueJournal", targetNamespace = Namespace)
                            String takenTissueJournal,
                            @WebParam(name = "getTissueTime", targetNamespace = Namespace)
                            XMLGregorianCalendar getTissueTime,
                            @WebParam(name = "orderBiomaterialName", targetNamespace = Namespace)
                            String orderBiomaterialName,
                            @WebParam(name = "orderLIS", targetNamespace = Namespace)
                            String orderLIS) {
        ParamBakDelivered in =
                ParamBakDelivered.initInParams(orderMisId, takenTissueJournal, getTissueTime, orderBiomaterialName, orderLIS);

        boolean valid = in.validate();
        if (valid) {
            // do
        }
        return 0;
    }


}
