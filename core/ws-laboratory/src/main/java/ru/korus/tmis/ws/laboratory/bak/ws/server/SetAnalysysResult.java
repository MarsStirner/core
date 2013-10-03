package ru.korus.tmis.ws.laboratory.bak.ws.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.bak.*;
import ru.korus.tmis.core.entity.model.bak.*;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.util.CompileTimeConfigManager;
import ru.korus.tmis.util.logs.ToLog;
import ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex.*;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.JAXBElement;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
        endpointInterface = "ru.korus.tmis.ws.laboratory.bak.ws.server.SetAnalysysResultWS",
        targetNamespace = CompileTimeConfigManager.Laboratory.Namespace,
        serviceName = SetAnalysysResultWS.SERVICE_NAME,
        portName = SetAnalysysResultWS.PORT_NAME,
        name = SetAnalysysResultWS.PORT_NAME)
public class SetAnalysysResult implements SetAnalysysResultWS {

    private static final Logger logger = LoggerFactory.getLogger(SetAnalysysResult.class);


    @EJB
    private DbBbtResponseBeanLocal dbBbtResponseBean;

    @EJB
    private DbBbtResultTableBeanLocal dbBbtResultTableBean;

    @EJB
    private DbBbtOrganismSensValuesBeanLocal dbBbtOrganismSensValuesBean;

    @EJB
    private DbRbAntibioticBeanLocal dbRbAntibioticBean;

    @EJB
    private DbRbBacIndicatorBeanLocal dbRbBacIndicatorBean;

    @EJB
    private DbRbMicroorganismBeanLocal dbRbMicroorganismBean;


    /**
     * Получение результатов исследования
     *
     * @param request
     * @return
     * @throws CoreException
     */
    @Override
    @WebMethod(operationName = "setAnalysisResults")
    @WebResult(name = SUCCESS_ACCEPT_EVENT, targetNamespace = NAMESPACE, partName = "Body")
    public MCCIIN000002UV01 setAnalysisResults(
            @WebParam(name = "POLB_IN224100UV01", targetNamespace = NAMESPACE, partName = "Body")
            final POLBIN224100UV01 request) throws CoreException {
        final ToLog toLog = new ToLog("setAnalysisResults");
        MCCIIN000002UV01 response;
        try {
            toLog.add("Get result: \n" + Utils.marshallMessage(request, "ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex"));
            flushToDB(request);
            response = createSuccessResponse();
            toLog.add("Response: \n" + Utils.marshallMessage(response, "ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex"));

            return response;
        } catch (Exception e) {
            logger.error("Exception: " + e, e);
            response = createErrorResponse();
            toLog.add("Response: \n" + Utils.marshallMessage(response, "ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex"));
        } finally {
            logger.info(toLog.releaseString());
        }
        return response;
    }

    /**
     * Запись данных результата в БД
     *
     * @param request
     */
    private void flushToDB(POLBIN224100UV01 request) {
        // заполняем справочники

        final List<POLBIN224100UV01MCAIMT700201UV01Subject2> subjectList = request.getControlActProcess().getSubject();
        for (POLBIN224100UV01MCAIMT700201UV01Subject2 subj : subjectList) {

            if (subj.getObservationBattery() != null) {

                final JAXBElement<POLBMT004000UV01ObservationBattery> battery = subj.getObservationBattery();
                final POLBMT004000UV01ObservationBattery value = battery.getValue();

                final String displayName = value.getCode().getDisplayName();


            } else if (subj.getObservationEvent() != null) {

                final JAXBElement<POLBMT004000UV01ObservationEvent> event = subj.getObservationEvent();
                final POLBMT004000UV01ObservationEvent value = event.getValue();
                final String code = value.getCode().getCode();


            } else if (subj.getObservationReport() != null) {

                final JAXBElement<POLBMT004000UV01ObservationReport> report = subj.getObservationReport();
                final POLBMT004000UV01ObservationReport value = report.getValue();
                final String code = value.getCode().getCode();


            } else if (subj.getSpecimenObservationCluster() != null) {

                final JAXBElement<POLBMT004000UV01SpecimenObservationCluster> cluster = subj.getSpecimenObservationCluster();
                final POLBMT004000UV01SpecimenObservationCluster value = cluster.getValue();

                final List<POLBMT004000UV01Specimen> specimenList = value.getSpecimen();
                for (POLBMT004000UV01Specimen specimen : specimenList) {
                    final String code = specimen.getSpecimen().getCode().getCode();


                }

            }


        }

        final RbAntibiotic rbAntibiotic = new RbAntibiotic();
        rbAntibiotic.setCode("1");
        rbAntibiotic.setId(1);
        rbAntibiotic.setName("abc");
        dbRbAntibioticBean.add(rbAntibiotic);

        final RbBacIndicator rbBacIndicator = new RbBacIndicator();
        rbBacIndicator.setCode("2");
        rbBacIndicator.setId(2);
        rbBacIndicator.setName("cde");
        dbRbBacIndicatorBean.add(rbBacIndicator);

        final RbMicroorganism rbMicroorganism = new RbMicroorganism();
        rbMicroorganism.setCode("3");
        rbMicroorganism.setId(3);
        rbMicroorganism.setName("efg");
        dbRbMicroorganismBean.add(rbMicroorganism);


        final BbtResultTable bbtResultTable = new BbtResultTable();

        dbBbtResultTableBean.add(bbtResultTable);

        final BbtResponse bbtResponse = new BbtResponse();
        //    bbtResponse.setId(request.);

        dbBbtResponseBean.add(bbtResponse);

        final BbtOrganismSensValues bbtOrganismSensValues = new BbtOrganismSensValues();

        dbBbtOrganismSensValuesBean.add(bbtOrganismSensValues);
    }

    /**
     * Создание ответного сообщения о успешном принятии результатов
     */
    private MCCIIN000002UV01 createSuccessResponse() {
        final MCCIIN000002UV01 response = new MCCIIN000002UV01();

        final II id2 = new II();
        id2.setRoot(UUID.randomUUID().toString());
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
        acceptAskCode.setCode("AL");

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
        id1.setRoot(UUID.randomUUID().toString());
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

    /**
     * Создание ответного сообщения о неуспешном принятии результатов
     */
    private MCCIIN000002UV01 createErrorResponse() {
        final MCCIIN000002UV01 response = new MCCIIN000002UV01();

        final II id2 = new II();
        id2.setRoot(UUID.randomUUID().toString());
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
        acceptAskCode.setCode("AL");

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
        typeCode.setCode("AE");
        acknowledgement.setTypeCode(typeCode);
        final MCCIMT000200UV01TargetMessage targetMessage = new MCCIMT000200UV01TargetMessage();
        final II id1 = new II();
        id1.setRoot(UUID.randomUUID().toString());
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
        reference.setValue("Данные не приняты");
        ed.setReference(reference);
        acknowledgementDetail.setText(ed);
        acknowledgement.getAcknowledgementDetail().add(acknowledgementDetail);

        response.getAcknowledgement().add(acknowledgement);
        return response;
    }

    /**
     * Сообщение от ЛИС о доставке материала. Факт завершения забора биоматериала.
     *
     * @param orderMisId           - штрих-код на контейнере c биоматериалом (десятичное представление считанного штрих-кода)
     * @param takenTissueJournal   - TakenTissueJournal.id – номер заказа
     * @param getTissueTime        - Дата и время регистрации биоматериала в лаборатории
     * @param orderBiomaterialName - название  биоматериала из справочника биоматериалов
     * @param orderLIS             - Номер заказа в ЛИС
     * @return
     * @throws CoreException
     */
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
                            String orderLIS) throws CoreException {

        return 0;
    }


}
