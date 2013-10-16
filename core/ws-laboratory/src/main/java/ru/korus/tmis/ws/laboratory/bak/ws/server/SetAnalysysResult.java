package ru.korus.tmis.ws.laboratory.bak.ws.server;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.bak.*;
import ru.korus.tmis.core.entity.model.bak.*;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.util.CompileTimeConfigManager;
import ru.korus.tmis.util.logs.ToLog;
import ru.korus.tmis.ws.laboratory.bak.ws.server.model.fake.Antibiotic;
import ru.korus.tmis.ws.laboratory.bak.ws.server.model.fake.FakeResult;
import ru.korus.tmis.ws.laboratory.bak.ws.server.model.fake.MicroOrg;
import ru.korus.tmis.ws.laboratory.bak.ws.server.model.fake.Result;
import ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex.*;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.JAXBElement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
//@XmlSeeAlso({ObjectFactory.class})
public class SetAnalysysResult implements SetAnalysysResultWS {

    private static final Logger logger = LoggerFactory.getLogger(SetAnalysysResult.class);


    @EJB
    private DbBbtResponseBeanLocal dbBbtResponseBean;

    @EJB
    private DbBbtResultTableBeanLocal dbBbtResultTableBean;

    @EJB
    private DbBbtResultOrganismBeanLocal dbBbtResultOrganismBean;

    @EJB
    private DbBbtResultTextBeanLocal dbBbtResultTextBean;

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
            flushToDB(request, toLog);
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
    private void flushToDB(POLBIN224100UV01 request, ToLog toLog) throws CoreException {
        try {
            toLog.add("\n----Flush to DB-----\n");
            final String uuidDocument = request.getId().getRoot();
            toLog.add("uuidDocument: [" + uuidDocument + "]\n");

            final DateTime createTime = createDate(request.getCreationTime().getValue());
            toLog.add("createTime: [" + createTime + "]\n");

            // true - табличное представление, false - с микроорганизмами
            boolean isTable = detectTableForm(request);
            toLog.add("isTable: [" + isTable + "]\n");

            // идентификатор направления на анализы
            int id = 0;
            // штрих-код на контейнере c биоматериалом
            String barCode;
            // уникальный идентификационный номер врача лаборатории подписавшего результаты исследования
            String doctorId = "0";
            // ФИО врача лаборатории подписавшего результаты исследования
            String doctorName;
            // отметка об окончании исследований по направлению, true-окончательный, заказ закрывается, false - предварительный
            boolean isComplete = false;


            String defects = getDefects(request);
            toLog.add("defects: [" + defects + "]\n");

            for (POLBIN224100UV01MCAIMT700201UV01Subject2 subj : request.getControlActProcess().getSubject()) {

                if (subj.getObservationReport() != null) {
                    try {
                        final JAXBElement<POLBMT004000UV01ObservationReport> report = subj.getObservationReport();
                        final POLBMT004000UV01ObservationReport value = report.getValue();

                        final II ii = !value.getId().isEmpty() ? value.getId().get(0) : new II();
                        toLog.add("ii: [" + ii.getRoot() + "]\n");

                        id = Integer.parseInt(ii.getRoot());

//                        if (dbBbtResponseBean.get(id) != null) {
//                            throw new CoreException("Результаты с идентификатором [" + id + "] уже приняты");
//                        }

                        // код исследования
                        final String codeIsled = value.getCode().getCode();
                        toLog.add("codeIsled: [" + codeIsled + "]\n");

                        // название исследования
                        final String nameIsled = value.getCode().getDisplayName();
                        toLog.add("nameIsled: [" + nameIsled + "]\n");

                        isComplete = value.getStatusCode().getCode().equals("true");
                        toLog.add("isComplete: [" + isComplete + "]\n");

                        barCode = value.getSpecimen().get(0).getSpecimen().getValue().getId().getRoot();
                        toLog.add("barCode: [" + barCode + "]\n");

                        final COCTMT090000UV01AssignedEntity assignedEntity = value.getAuthor().get(0).getAssignedEntity();
                        doctorId = assignedEntity.getCode().getCode();
                        toLog.add("doctorId: [" + doctorId + "]\n");
                        doctorName = assignedEntity.getCode().getDisplayName();
                        toLog.add("doctorName: [" + doctorName + "]\n");

                    } catch (Exception e) {
                        logger.error("Exception: " + e, e);
                        throw new CoreException("Ошибка в формате тега observationReport");
                    }

                } else if (subj.getObservationBattery() != null) {
                    try {
                        final JAXBElement<POLBMT004000UV01ObservationBattery> battery = subj.getObservationBattery();
                        final POLBMT004000UV01ObservationBattery value = battery.getValue();

                        // название исследования
                        final String nameIsled = value.getCode().getDisplayName();
                        toLog.add("nameIsled: [" + nameIsled + "]\n");

                        // идентификатор направления на анализы id=orderMisId
                        String orderMisId;
                        for (POLBMT004000UV01InFulfillmentOf2 i : value.getInFulfillmentOf()) {
                            for (II ii : i.getPlacerOrder().getValue().getId()) {
                                orderMisId = ii.getExtension();
                                toLog.add("orderMisId: [" + orderMisId + "]\n");
                            }
                        }

                        final List<POLBMT004000UV01Component2> component1 = value.getComponent1();
                        for (POLBMT004000UV01Component2 p : component1) {

                            final JAXBElement<POLBMT004000UV01ObservationEvent> observationEvent = p.getObservationEvent();
                            // код исследования
                            final String codeIsled = observationEvent.getValue().getCode().getCode();
                            toLog.add("codeIsled: [" + codeIsled + "]\n");
                            // название исследования
                            final String nameIsledd = observationEvent.getValue().getCode().getDisplayName();
                            toLog.add("nameIsledd: [" + nameIsledd + "]\n");
                            // дата исследования
                            final DateTime effectiveTime = createDate(observationEvent.getValue().getEffectiveTime().get(0).getValue());
                            toLog.add("effectiveTime: [" + effectiveTime + "]\n");

                            List<MicroOrg> microOrgs = new ArrayList<MicroOrg>();

                            final List<CE> ceList = observationEvent.getValue().getConfidentialityCode();
                            if (ceList != null && !ceList.isEmpty()) {
                                final String resultValue = ceList.get(0).getCode();
                                toLog.add("resultValue: [" + resultValue + "]\n");
                                final String resultText = ceList.get(0).getDisplayName();
                                toLog.add("resultText: [" + resultText + "]\n");

                                if (!"".equals(resultText) && !"".equals(resultValue)) {
                                    final BbtResultText text = new BbtResultText();
                                    text.setValueText(resultValue + " / " + resultText);
                                    text.setActionId(id);
                                    dbBbtResultTextBean.add(text);
                                }
                            }

                            for (POLBMT004000UV01Component2 comp : observationEvent.getValue().getComponent1()) {
                                // код микроорганизма
                                final String codeMicroOrg = comp.getObservationEvent().getValue().getCode().getCode();
                                toLog.add("codeMicroOrg: [" + codeMicroOrg + "]\n");

                                // название микроорганизма
                                final String nameMicroOrg = comp.getObservationEvent().getValue().getCode().getDisplayName();
                                toLog.add("nameMicroOrg: [" + nameMicroOrg + "]\n");

                                // чувствительность
                                final String sensMicroOrg = comp.getObservationEvent().getValue().getCode().getCodeSystem();
                                toLog.add("sensMicroOrg: [" + sensMicroOrg + "]\n");

                                if (codeMicroOrg != null && !"".equals(codeMicroOrg) && nameMicroOrg != null && !"".equals(nameMicroOrg)) {
                                    RbMicroorganism org = new RbMicroorganism(codeMicroOrg, nameMicroOrg);
                                    dbRbMicroorganismBean.add(org);

                                    final RbMicroorganism mic = dbRbMicroorganismBean.get(codeMicroOrg);

                                    final BbtResultOrganism resultOrganism = new BbtResultOrganism();
                                    resultOrganism.setActionId(id);
                                    resultOrganism.setConcentration(sensMicroOrg);
                                    resultOrganism.setOrganismId(mic.getId());
                                    dbBbtResultOrganismBean.add(resultOrganism);
                                }
                            }
                        }
                    } catch (Exception e) {
                        logger.error("Exception: " + e, e);
                        toLog.add("Exception " + e);
                        throw new CoreException("Ошибка в формате тега observationBattery");
                    }

                } else if (subj.getObservationEvent() != null) {
                    try {
                        final JAXBElement<POLBMT004000UV01ObservationEvent> event = subj.getObservationEvent();
                        final POLBMT004000UV01ObservationEvent value = event.getValue();

                        // код методики/показателя/микроорганизма
                        final String codeMethod = value.getCode().getCode();
                        toLog.add("codeMethod: [" + codeMethod + "]\n");

                        // название методики/показателя/микроорганизма
                        final String nameMethod = value.getCode().getDisplayName();
                        toLog.add("nameMethod: [" + nameMethod + "]\n");

                        // произвольный текстовый комментарий
                        final String commentMethod = value.getCode().getCodeSystem();
                        toLog.add("commentMethod: [" + commentMethod + "]\n");

                        // если результата нет, здесь указана причина
                        final String statusCode = value.getStatusCode().getCode();
                        toLog.add("statusCode: [" + statusCode + "]\n");

                        // дата исследования
                        final DateTime effectiveTime = createDate(value.getEffectiveTime().get(0).getValue());
                        toLog.add("effectiveTime: [" + effectiveTime + "]\n");

                        // единица измерения
                        final PQ pq = (PQ) value.getValue();
                        final String unUnit = pq.getUnit();
                        toLog.add("unUnit: [" + unUnit + "]\n");
                        final String valueUnit = pq.getValue();
                        toLog.add("valueUnit: [" + valueUnit + "]\n");

                        for (POLBMT004000UV01Device dd : value.getDevice()) {
                            // название прибора
                            final String pribor = dd.getLabTestKit().getManufacturedTestKit().getValue().getCode().getDisplayName();
                            toLog.add("pribor: [" + pribor + "]\n");

                        }

                        for (POLBMT004000UV01ReferenceRange range : value.getReferenceRange()) {
                            // норма, т.е. диапазон допустимых значений в строковом вид
                            final String interCode = range.getInterpretationRange().getInterpretationCode().getDisplayName();
                            for (POLBMT004000UV01Precondition precondition1 : range.getInterpretationRange().getPrecondition()) {
                                // значение результата относительно нормы
                                final String norma = precondition1.getCriterion().getCode().getDisplayName();
                                toLog.add("norma: [" + norma + "]\n");
                            }
                        }
                    } catch (Exception e) {
                        logger.error("Exception: " + e, e);
                        toLog.add("Exception " + e);
                        throw new CoreException("Ошибка в формате тега observationEvent");
                    }

                } else if (subj.getSpecimenObservationCluster() != null) {
                    try {
                        final JAXBElement<POLBMT004000UV01SpecimenObservationCluster> cluster = subj.getSpecimenObservationCluster();
                        final POLBMT004000UV01SpecimenObservationCluster value = cluster.getValue();

                        String codeMicroOrg = "";

                        if (value.getSpecimen().isEmpty()) {
                            throw new CoreException("В формате тега specimenObservationCluster указано более 1 микроогранизма");
                        }

                        POLBMT004000UV01Specimen s = value.getSpecimen().get(0);
                        // код микроорганизма
                        codeMicroOrg = s.getSpecimen().getValue().getCode().getCode();
                        toLog.add("  codeMicroOrg: [" + codeMicroOrg + "]\n");
                        // название микрооранизма
                        final String nameMicroOrg = s.getSpecimen().getValue().getCode().getDisplayName();
                        toLog.add("  nameMicroOrg: [" + nameMicroOrg + "]\n");

//                        final RbMicroorganism microorganism = new RbMicroorganism();
//                        microorganism.setCode(codeMicroOrg);
//                        microorganism.setName(nameMicroOrg);
//                        dbRbMicroorganismBean.add(microorganism);

                        for (POLBMT004000UV01Component2 component2 : value.getComponent1()) {
                            final JAXBElement<POLBMT004000UV01ObservationBattery> ob = component2.getObservationBattery();

                            for (POLBMT004000UV01Component2 pp : ob.getValue().getComponent1()) {

                                // код антибиотика
                                final String codeAntib = pp.getObservationEvent().getValue().getCode().getCode();
                                toLog.add("codeAntib: [" + codeAntib + "]\n");

                                // название антибиотика
                                final String nameAntib = pp.getObservationEvent().getValue().getCode().getDisplayName();
                                toLog.add("nameAntib: [" + nameAntib + "]\n");

                                // величина концентрации
                                final String concAntib = pp.getObservationEvent().getValue().getCode().getCodeSystem();
                                toLog.add("concAntib: [" + concAntib + "]\n");

                                // чувствительность
                                final String sensAntib = pp.getObservationEvent().getValue().getCode().getTranslation().get(0).getCode();
                                toLog.add("sensAntib: [" + sensAntib + "]\n");


                                final RbAntibiotic antibiotic = new RbAntibiotic();
                                antibiotic.setCode(codeAntib);
                                antibiotic.setName(nameAntib);
                                dbRbAntibioticBean.add(antibiotic);

                                final RbAntibiotic a = dbRbAntibioticBean.get(codeAntib);


                                final RbMicroorganism m = dbRbMicroorganismBean.get(codeMicroOrg);
                                final BbtResultOrganism organism = dbBbtResultOrganismBean.get(m.getId(), id);

                                final BbtOrganismSensValues sensAntib1 = new BbtOrganismSensValues();

                                sensAntib1.setActivity(sensAntib);
                                sensAntib1.setAntibioticId(a.getId());
                                sensAntib1.setBbtResultOrganismId(organism.getId());
                                sensAntib1.setMic(concAntib);

                                dbBbtOrganismSensValuesBean.add(sensAntib1);
                            }
                        }
                    } catch (Exception e) {
                        logger.error("Exception: " + e, e);
                        toLog.add("Exception " + e);
                        throw new CoreException("Ошибка в формате тега specimenObservationCluster");
                    }
                }
            }

            // записываем данные в БД
            final BbtResponse response = new BbtResponse();
            response.setId(id);
            response.setDoctorId(Integer.parseInt(doctorId));
            response.setFinalFlag(isComplete ? 1 : 0);
            response.setDefects(getDefects(request));
            response.setCodeLIS(Integer.parseInt(getLisCode(request)));
            dbBbtResponseBean.add(response);


        } catch (Exception e) {
            logger.error("Exception: " + e, e);
            toLog.add("Exception " + e);
            throw new CoreException("Ошибка формата результата");
        }
    }

    /**
     * Описание дефектов биоматериала
     *
     * @param request
     * @return
     */
    private String getDefects(POLBIN224100UV01 request) {
        for (POLBIN224100UV01MCAIMT700201UV01Subject2 subj : request.getControlActProcess().getSubject()) {
            if (subj.getObservationBattery() != null) {
                return subj.getObservationBattery().getValue().getCode().getCode();
            }
        }
        return "";
    }

    /**
     * Получить код лаборатории
     *
     * @param request
     * @return
     */
    private String getLisCode(POLBIN224100UV01 request) {
        // Код лаборатории
        return request.getControlActProcess().getAuthorOrPerformer()
                .get(0)
                .getAssignedPerson().getValue()
                .getRepresentedOrganization()
                .getValue().getCode().getCode();
    }

    /**
     * Метод определения табличная форма или нет
     *
     * @param request
     * @return true - табличная форма представления (есть табличка с дефектами и прибор), false - микроорганизмы
     */
    private boolean detectTableForm(POLBIN224100UV01 request) {
        for (POLBIN224100UV01MCAIMT700201UV01Subject2 subj : request.getControlActProcess().getSubject()) {
            if (subj.getObservationEvent() != null) {
                return true;
            }
        }
        return false;
    }

    private DateTime createDate(String date) {
        final DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMddHHmm");
        return formatter.parseDateTime(date);
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
//        device.setDeterminerCode("INSTANCE");
        final II id = new II();
//        id.getNullFlavor().add("NULL");
//        device.getId().add(id);
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
//        acknowledgement.setTypeCode(typeCode);
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
//        device.setDeterminerCode("INSTANCE");
        final II id = new II();
//        id.getNullFlavor().add("NULL");
//        device.getId().add(id);
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
//        acknowledgement.setTypeCode(typeCode);
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
     * {@inheritDoc}
     */
    @Override
    public int bakDelivered(@WebParam(name = "GUID ", targetNamespace = Namespace)
                            String GUID,
                            @WebParam(name = "DtTime", targetNamespace = Namespace)
                            String DtTime,
                            @WebParam(name = "orderMisId", targetNamespace = Namespace)
                            Integer orderMisId,
                            @WebParam(name = "orderBiomaterialName", targetNamespace = Namespace)
                            Integer orderBarCode) throws CoreException {

        return 0;
    }


    @Override
    @WebMethod(operationName = "setAnalysisResults2")
    @WebResult(name = SUCCESS_ACCEPT_EVENT, targetNamespace = NAMESPACE, partName = "Body")
    public MCCIIN000002UV01 setAnalysisResults2(
            @WebParam(name = "FakeResult", targetNamespace = NAMESPACE, partName = "Body")
            final FakeResult request) throws CoreException {

        validateRequest(request);

        try {
            for (Result result : request.getResults().getResults()) {
                for (Antibiotic a : result.getAntibiotics().getAntibiotics()) {
                    final RbAntibiotic rbAntibiotic = new RbAntibiotic();
                    rbAntibiotic.setCode(a.getIdAntibiotic());
                    rbAntibiotic.setName(a.getNameAntibiotic());
                    dbRbAntibioticBean.add(rbAntibiotic);
                }
            }

//        final RbBacIndicator rbBacIndicator = new RbBacIndicator();
//        rbBacIndicator.setCode("2");
//        rbBacIndicator.setName("cde");
//        dbRbBacIndicatorBean.add(rbBacIndicator);

            for (MicroOrg microOrg : request.getMicroOrgs().getMicroOrgs()) {
                final RbMicroorganism rbMicroorganism = new RbMicroorganism();
                rbMicroorganism.setCode(microOrg.getCode());
                rbMicroorganism.setName(microOrg.getName());
                dbRbMicroorganismBean.add(rbMicroorganism);
            }

//        final BbtResultTable bbtResultTable = new BbtResultTable();

//        dbBbtResultTableBean.add(bbtResultTable);

            final BbtResponse bbtResponse = new BbtResponse();
            bbtResponse.setId(Integer.parseInt(request.getOrderMISId()));
            bbtResponse.setDoctorId(Integer.parseInt(request.getDoctorId()));
            bbtResponse.setFinalFlag(1);
            bbtResponse.setCodeLIS(Integer.parseInt(request.getOrderMISId()));
            bbtResponse.setDefects("----");

            dbBbtResponseBean.add(bbtResponse);

//            for (request.getResults().getResults()) {
//                final BbtOrganismSensValues sens = new BbtOrganismSensValues();
//                sens.setBbtResultOrganismId();
//                dbBbtOrganismSensValuesBean.add(sens);
//            }

        } catch (Exception e) {
            throw new CoreException(400, "Ошибка обработки запроса");
        }
        return createSuccessResponse();
    }


    /**
     * Проверка входных данных
     *
     * @param request
     * @throws CoreException
     */
    private void validateRequest(FakeResult request) throws CoreException {
        try {
            if (request.getBarCode() == null) {
//                throw new CoreException(new SOAPFaultInfo("400", "Отсутствует штрих-код"));
            }
            if (request.getCodeIsl() == null) {
//                throw new BakIntegrationException(400, "Отсутствует код исследования");
            }
            if (request.getDate() == null) {
//                throw new BakIntegrationException(400, "Отсутствует дата исследования");
            }
            if (request.getDoctorId() == null) {
//                throw new BakIntegrationException(400, "Отсутствует идентификатор врача");
            }
            if (request.getNameIsl() == null) {
//                throw new BakIntegrationException(400, "Отсутствует название исследования");
            }
            if (request.getMicroOrgs().getMicroOrgs().isEmpty()) {
//                throw new BakIntegrationException(400, "Отсутствует список микроорганизмов");
            }
            if (request.getResults().getResults().isEmpty()) {
//                throw new BakIntegrationException(400, "Отсутствует список результатов и антибиотиков");
            }
        } catch (Exception e) {
            throw new CoreException(400, "Ошибка в формате результатов анализов");
        }

    }
}
