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
    private void flushToDB(POLBIN224100UV01 request) throws CoreException {

        final String uuidDocument = request.getId().getRoot();
        final DateTime createTime = createDate(request.getCreationTime().getValue());

        boolean isTable = detectTableForm(request);

        // идентификатор направления на анализы
        String id = "";
        // штрих-код на контейнере c биоматериалом
        String barCode;
        // уникальный идентификационный номер врача лаборатории подписавшего результаты исследования
        String doctorId = "0";
        // ФИО врача лаборатории подписавшего результаты исследования
        String doctorName;
        // отметка об окончании исследований по направлению, true-окончательный, заказ закрывается, false - предварительный
        boolean isComplete = false;


        String defects = getDefects(request);


        for (POLBIN224100UV01MCAIMT700201UV01Subject2 subj : request.getControlActProcess().getSubject()) {

            if (subj.getObservationReport() != null) {
                try {
                    final JAXBElement<POLBMT004000UV01ObservationReport> report = subj.getObservationReport();
                    final POLBMT004000UV01ObservationReport value = report.getValue();

                    final II ii = !value.getId().isEmpty() ? value.getId().get(0) : new II();
                    id = ii.getRoot();
                    // код исследования
                    final String codeIsled = value.getCode().getCode();
                    // название исследования
                    final String nameIsled = value.getCode().getDisplayName();
                    isComplete = value.getStatusCode().getCode().equals("true");

                    barCode = value.getSpecimen().get(0).getSpecimen().getValue().getId().getRoot();

                    final COCTMT090000UV01AssignedEntity assignedEntity = value.getAuthor().get(0).getAssignedEntity();
                    doctorId = assignedEntity.getCode().getCode();
                    doctorName = assignedEntity.getCode().getDisplayName();

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

                    // идентификатор направления на анализы id=orderMisId
                    String orderMisId;
                    for (POLBMT004000UV01InFulfillmentOf2 i : value.getInFulfillmentOf()) {
                        for (II ii : i.getPlacerOrder().getValue().getId()) {
                            orderMisId = ii.getExtension();
                        }
                    }

                    final List<POLBMT004000UV01Component2> component1 = value.getComponent1();
                    for (POLBMT004000UV01Component2 p : component1) {

                        final JAXBElement<POLBMT004000UV01ObservationEvent> observationEvent = p.getObservationEvent();
                        // код исследования
                        final String codeIsled = observationEvent.getValue().getCode().getCode();
                        // название исследования
                        final String nameIsledd = observationEvent.getValue().getCode().getDisplayName();
                        // дата исследования
                        final DateTime effectiveTime = createDate(observationEvent.getValue().getEffectiveTime().get(0).getValue());

                        List<MicroOrg> microOrgs = new ArrayList<MicroOrg>();

                        for (POLBMT004000UV01Component2 comp : observationEvent.getValue().getComponent1()) {
                            // код микроорганизма
                            final String codeMicroOrg = comp.getObservationEvent().getValue().getCode().getCode();
                            // название микроорганизма
                            final String nameMicroOrg = comp.getObservationEvent().getValue().getCode().getDisplayName();
                            // чувствительность
                            final String sensMicroOrg = comp.getObservationEvent().getValue().getCode().getCodeSystem();

                            RbMicroorganism org = new RbMicroorganism(codeMicroOrg, nameMicroOrg);
                            dbRbMicroorganismBean.add(org);
                        }
                    }

                    // запись в бд


                } catch (Exception e) {
                    logger.error("Exception: " + e, e);
                    throw new CoreException("Ошибка в формате тега observationBattery");
                }

            } else if (subj.getObservationEvent() != null) {
                try {
                    final JAXBElement<POLBMT004000UV01ObservationEvent> event = subj.getObservationEvent();
                    final POLBMT004000UV01ObservationEvent value = event.getValue();

                    // код методики/показателя/микроорганизма
                    final String codeMethod = value.getCode().getCode();

                    // название методики/показателя/микроорганизма
                    final String nameMethod = value.getCode().getDisplayName();

                    // произвольный текстовый комментарий
                    final String commentMethod = value.getCode().getCodeSystem();

                    // если результата нет, здесь указана причина
                    final String statusCode = value.getStatusCode().getCode();

                    // дата исследования
                    final DateTime effectiveTime = createDate(value.getEffectiveTime().get(0).getValue());

                    // единица измерения
                    final PQ pq = (PQ) value.getValue();
                    final String unUnit = pq.getUnit();
                    final String valueUnit = pq.getValue();

                    for (POLBMT004000UV01Device dd : value.getDevice()) {
                        // название прибора
                        final String pribor = dd.getLabTestKit().getManufacturedTestKit().getValue().getCode().getDisplayName();


                    }

                    for (POLBMT004000UV01ReferenceRange range : value.getReferenceRange()) {
                        // норма, т.е. диапазон допустимых значений в строковом вид
                        final String interCode = range.getInterpretationRange().getInterpretationCode().getDisplayName();
                        for (POLBMT004000UV01Precondition precondition1 : range.getInterpretationRange().getPrecondition()) {
                            // значение результата относительно нормы
                            final String norma = precondition1.getCriterion().getCode().getDisplayName();
                        }
                    }
                } catch (Exception e) {
                    logger.error("Exception: " + e, e);
                    throw new CoreException("Ошибка в формате тега observationEvent");
                }

            } else if (subj.getSpecimenObservationCluster() != null) {
                try {
                    final JAXBElement<POLBMT004000UV01SpecimenObservationCluster> cluster = subj.getSpecimenObservationCluster();
                    final POLBMT004000UV01SpecimenObservationCluster value = cluster.getValue();

                    for (POLBMT004000UV01Specimen s : value.getSpecimen()) {
                        // код микроорганизма
                        final String codeMicroOrg = s.getSpecimen().getValue().getCode().getCode();
                        // название микрооранизма
                        final String nameMicroOrg = s.getSpecimen().getValue().getCode().getDisplayName();

                        final RbMicroorganism microorganism = new RbMicroorganism();
                        microorganism.setCode(codeMicroOrg);
                        microorganism.setName(nameMicroOrg);
                        dbRbMicroorganismBean.add(microorganism);
                    }


                    for (POLBMT004000UV01Component2 component2 : value.getComponent1()) {
                        final JAXBElement<POLBMT004000UV01ObservationBattery> ob = component2.getObservationBattery();

                        for (POLBMT004000UV01Component2 pp : ob.getValue().getComponent1()) {

                            // код антибиотика
                            final String codeAntib = pp.getObservationEvent().getValue().getCode().getCode();

                            // название антибиотика
                            final String nameAntib = pp.getObservationEvent().getValue().getCode().getDisplayName();

                            // величина концентрации
                            final String concAntib = pp.getObservationEvent().getValue().getCode().getCodeSystem();

                            // чувствительность
                            final String sensAntib = pp.getObservationEvent().getValue().getCode().getTranslation().get(0).getCode();


                            final RbAntibiotic antibiotic = new RbAntibiotic();
                            antibiotic.setCode(codeAntib);
                            antibiotic.setName(nameAntib);
                            dbRbAntibioticBean.add(antibiotic);


                            final BbtOrganismSensValues bbtOrganismSensValues = new BbtOrganismSensValues();

//                            dbRbMicroorganismBean.get()

//                            bbtOrganismSensValues.setBbtResultOrganismId();
//                            dbBbtOrganismSensValuesBean.add(bbtOrganismSensValues);

                        }

                    }
                } catch (Exception e) {
                    logger.error("Exception: " + e, e);
                    throw new CoreException("Ошибка в формате тега specimenObservationCluster");
                }
            }
        }

        // записываем данные в БД
        final BbtResponse response = new BbtResponse();
        response.setId(Integer.parseInt(id));
        response.setDoctorId(Integer.parseInt(doctorId));
        response.setFinalFlag(isComplete ? 1 : 0);
//                    response.setCodeLIS(Integer.parseInt(id));
        dbBbtResponseBean.add(response);



      /*  final RbAntibiotic rbAntibiotic = new RbAntibiotic();
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
        */
    }

    private String getDefects(POLBIN224100UV01 request) {
        return null;
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
