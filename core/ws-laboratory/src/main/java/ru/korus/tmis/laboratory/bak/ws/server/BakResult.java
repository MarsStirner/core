package ru.korus.tmis.laboratory.bak.ws.server;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.DbActionBeanLocal;
import ru.korus.tmis.core.database.DbActionPropertyBeanLocal;
import ru.korus.tmis.core.database.DbActionPropertyTypeBeanLocal;
import ru.korus.tmis.core.database.DbCustomQueryLocal;
import ru.korus.tmis.core.database.bak.*;
import ru.korus.tmis.core.database.dbutil.Database;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.entity.model.bak.*;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.laboratory.bak.BakResultService;
import ru.korus.tmis.laboratory.bak.ws.server.model.*;
import ru.korus.tmis.laboratory.bak.ws.server.model.hl7.complex.*;
import ru.korus.tmis.util.CompileTimeConfigManager;
import ru.korus.tmis.util.logs.ToLog;

import javax.annotation.Nullable;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.UUID;

import static ru.korus.tmis.laboratory.bak.ws.server.model.hl7.HL7Specification.NAMESPACE;
import static ru.korus.tmis.laboratory.bak.ws.server.model.hl7.HL7Specification.SUCCESS_ACCEPT_EVENT;
import static ru.korus.tmis.util.CompileTimeConfigManager.Laboratory.Namespace;

/**
 * Веб-сервис для сохранения результатов исследования из лаборатории
 * <p/>
 * test class @see {{ru.korus.tmis.laboratory.bak.BulkLISWebServiceImplSpec}}
 *
 * @author anosov@outlook.com
 *         date: 5/21/13
 */
@WebService(
        endpointInterface = "ru.korus.tmis.laboratory.bak.BakResultService",
        targetNamespace = "http://www.korusconsulting.ru",
        serviceName = "service-bak-results",
        portName = "service-bak-results",
        name = "service-bak-results")
public class BakResult implements BakResultService {

    private static final Logger logger = LoggerFactory.getLogger(BakResult.class);

    @EJB
    private DbActionBeanLocal dbAction;

    @EJB
    private DbActionPropertyTypeBeanLocal dbActionPropertyType;

    @EJB
    private DbActionPropertyBeanLocal dbActionProperty;

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

    @EJB
    private DbCustomQueryLocal dbCustomQuery;

    @EJB
    private Database db;


    /**
     * Получение результатов исследования
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
            toLog.addN("Request: \n#", Utils.marshallMessage(request, "ru.korus.tmis.laboratory.bak.ws.server.model.hl7.complex"));
            processRequest(request, toLog);
            //flushToDB(request, toLog);
            response = createSuccessResponse();
            toLog.addN("Response: \n#", Utils.marshallMessage(response, "ru.korus.tmis.laboratory.bak.ws.server.model.hl7.complex"));
            return response;
        } catch (Throwable e) {
            logger.error("Exception: " + e, e);
            toLog.addN("Exception: #", e);
            response = createErrorResponse();
            toLog.addN("Response: \n#", Utils.marshallMessage(response, "ru.korus.tmis.laboratory.bak.ws.server.model.hl7.complex"));
        } finally {
            logger.info(toLog.releaseString());
        }
        return response;
    }

    /**
     * Обработка результатов, определение типа ИФА или БАК-посев
     */
    private void processRequest(final POLBIN224100UV01 request, final ToLog toLog) throws CoreException {

        final List<IFA> ifaList = processIFA(request);
        if (!ifaList.isEmpty()) {
            for (IFA ifa : ifaList) {
                toLog.addN("Test #", ifa);
                saveIFA(ifa, toLog);
            }
        } else {
            final List<BakPosev> bakPosevList = processBakPosev(request);
            for (BakPosev bakPosev : bakPosevList) {
                toLog.addN("BAK posev #", bakPosev);
                saveBakPosev(bakPosev, toLog);
            }
        }
    }

    /**
     * Выборка данных по ИФА исследованию
     *
     * @param request
     * @return значение ИФА исследования, null - это не ИФА исследование
     */
    @NotNull
    private List<IFA> processIFA(final POLBIN224100UV01 request) {
        List<IFA> ifaList = new LinkedList<IFA>();
        try {
            for (POLBIN224100UV01MCAIMT700201UV01Subject2 subj : request.getControlActProcess().getSubject()) {
                IFA ifa = null;
                if (subj.getObservationBattery() != null) {
                    final String orderMisId = subj.getObservationBattery().getValue().getInFulfillmentOf().get(0).getPlacerOrder().getValue().getId().get(0).getExtension();
                    final List<CE> ceList = subj.getObservationBattery().getValue().getComponent1().get(0).getObservationEvent().getValue().getConfidentialityCode();
                    if (ceList != null && !ceList.isEmpty()) {
                        final String text = ceList.get(0).getDisplayName();
                        final String value = ceList.get(0).getCode();
                        if (!text.isEmpty() || !value.isEmpty()) {
                            ifa = new IFA();
                            ifa.setText(text);
                            ifa.setValue(value);
                            ifa.setActionId(Long.parseLong(orderMisId));
                            if (ifa.getActionId() != 0) {
                                ifaList.add(ifa);
                            }
                        }
                    }
                } else if (subj.getObservationReport() != null) {
                    final POLBMT004000UV01ObservationReport value = subj.getObservationReport().getValue();
                    for (IFA i : ifaList) {
                        if (i.getActionId() == Integer.parseInt(value.getId().get(0).getRoot())) {
                            i.setComplete(value.getStatusCode().getCode().equals("true"));
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Exception " + e, e);
        }
        return ifaList;
    }

    /**
     * Сохранение ИФА исседования
     *
     * @param ifa
     * @param toLog
     */
    private void saveIFA(final IFA ifa, final ToLog toLog) throws CoreException {
        try {
            final Action action = dbAction.getActionById(ifa.getActionId());
            int aptId = 0;
            for (ActionProperty property : action.getActionProperties()) {
                final ActionPropertyType type = property.getType();
                if (type.getCode() != null && "ifa".equals(type.getCode())) {
                    aptId = type.getId();
                }
            }
            db.addSinglePropBasic(ifa.getFullResult(), APValueString.class, ifa.getActionId(), aptId, true);
            toLog.addN("Save IFA result [#], aptId [#]", ifa.getFullResult(), aptId);
            // Изменяем статус действия на "Закончено"
            if (ifa.isComplete()) {
                dbAction.updateActionStatusWithFlush(action.getId(), ActionStatus.FINISHED.getCode());
                toLog.addN("Save status [#]", ActionStatus.FINISHED.getCode());
            }
        } catch (Exception e) {
            logger.error("Exception: " + e, e);
            toLog.add("Problem save to ActionProperty IFA values: " + e + "]\n");
            throw new CoreException("Не удалось сохранить данные по ИФА");
        }
    }

    private BakPosev getBakPosevByActionId(final Map<Integer, BakPosev> bakPosevMap, final int actionId) {
        BakPosev bakPosev = bakPosevMap.get(actionId);
        if (bakPosev == null) {
            bakPosev = new BakPosev(actionId);
        }
        return bakPosev;
    }

    /**
     * Выборка данных по БАК-посеву
     *
     * @param request
     * @return
     */
    @NotNull
    private List<BakPosev> processBakPosev(final POLBIN224100UV01 request) throws CoreException {
        final Map<Integer, BakPosev> bakMap = new LinkedHashMap<Integer, BakPosev>();
        for (POLBIN224100UV01MCAIMT700201UV01Subject2 subj : request.getControlActProcess().getSubject()) {
            if (subj.getObservationReport() != null) {
                try {
                    final POLBMT004000UV01ObservationReport value = subj.getObservationReport().getValue();
                    final int actionId = Integer.parseInt(value.getId().get(0).getRoot());
                    final BakPosev bakPosev = getBakPosevByActionId(bakMap, actionId);

                    bakPosev.setComplete(value.getStatusCode().getCode().equals("true"));
                    bakPosev.setBarCode(value.getSpecimen().get(0).getSpecimen().getValue().getId().getRoot());

                    final COCTMT090000UV01AssignedEntity assignedEntity = value.getAuthor().get(0).getAssignedEntity();
                    bakPosev.setDoctor(new Doctor(
                            Integer.parseInt(assignedEntity.getCode().getCode()),
                            assignedEntity.getCode().getDisplayName(),
                            getLisCode(request)));

                } catch (Exception e) {
                    logger.error("Exception: " + e, e);
                    throw new CoreException("Ошибка в формате тега observationReport");
                }

            } else if (subj.getObservationBattery() != null) {
                try {
                    final int actionIdObs = Integer.parseInt(
                            subj.getObservationBattery().getValue().getInFulfillmentOf().get(0).getPlacerOrder().getValue().getId().get(0).getExtension());
                    final BakPosev bakPosev = getBakPosevByActionId(bakMap, actionIdObs);

                    for (POLBMT004000UV01Component2 p : subj.getObservationBattery().getValue().getComponent1()) {
                        bakPosev.setGeneralComment(p.getObservationEvent().getValue().getCode().getCodeSystemName());
                        for (POLBMT004000UV01Component2 comp : p.getObservationEvent().getValue().getComponent1()) {
                            final String microorgCode = comp.getObservationEvent().getValue().getCode().getCode();
                            final String microorgName = comp.getObservationEvent().getValue().getCode().getDisplayName();
                            final String microorgComment = comp.getObservationEvent().getValue().getCode().getCodeSystem();
                            if (!"".equals(microorgCode) && !"".equals(microorgName)) {
                                bakPosev.addMicroorganism(new Microorganism(microorgCode, microorgName, microorgComment));
                            }
                        }
                    }
                } catch (Exception e) {
                    logger.error("Exception: " + e, e);
                    throw new CoreException("Ошибка в формате тега observationBattery");
                }

            } else if (subj.getSpecimenObservationCluster() != null) {
                try {
                    final POLBMT004000UV01SpecimenObservationCluster value = subj.getSpecimenObservationCluster().getValue();
                    final int actionId = Integer.parseInt(value.getSpecimen().get(0).getSpecimen().getValue().getCode().getCodeSystem());
                    final BakPosev bakPosev = getBakPosevByActionId(bakMap, actionId);
                    final String microorgCode = value.getSpecimen().get(0).getSpecimen().getValue().getCode().getCode();

                    for (POLBMT004000UV01Component2 component2 : value.getComponent1()) {
                        for (POLBMT004000UV01Component2 pp : component2.getObservationBattery().getValue().getComponent1()) {
                            final String antibioticCode = getValue(pp.getObservationEvent().getValue().getCode().getCode());
                            final String antibioticName = getValue(pp.getObservationEvent().getValue().getCode().getDisplayName());
                            final String antibioticConcentration = getValue(pp.getObservationEvent().getValue().getCode().getCodeSystem());
                            final String antibioticSensitivity = getValue(pp.getObservationEvent().getValue().getCode().getTranslation().get(0).getCode());
                            final String antibioticComment = getValue(pp.getObservationEvent().getValue().getStatusCode().getCode());

                            ru.korus.tmis.laboratory.bak.ws.server.model.Antibiotic antibiotic = new ru.korus.tmis.laboratory.bak.ws.server.model.Antibiotic(
                                    antibioticCode, antibioticName);
                            antibiotic.setConcentration(antibioticConcentration);
                            antibiotic.setSensitivity(antibioticSensitivity);
                            antibiotic.setComment(antibioticComment);

                            bakPosev.addAntibioticToOrganism(microorgCode, antibiotic);
                        }
                    }
                } catch (Exception e) {
                    logger.error("Exception: " + e, e);
                    throw new CoreException("Ошибка в формате тега specimenObservationCluster");
                }
            }
        }
        return new LinkedList<BakPosev>(bakMap.values());
    }

    private String getValue(final String value) {
        return value != null ? value : "";
    }

    /**
     * Запись результатов БАК-посева в БД. Метод необходимо оптимизировать, убрать лишние запросы для получения id
     */
    private void saveBakPosev(final BakPosev bakPosev, final ToLog toLog) throws CoreException {
        try {
            int actionId = bakPosev.getActionId();
            toLog.addN("Clean old actionId #", actionId);
            // пришли уточняющие данные, стираем старые данные
            dbBbtResponseBean.remove(actionId);
            for (BbtResultOrganism bbtResultOrganism1 : dbBbtResultOrganismBean.getByActionId(actionId)) {
                dbBbtOrganismSensValuesBean.removeByResultOrganismId(bbtResultOrganism1.getId());
                dbBbtResultOrganismBean.remove(bbtResultOrganism1.getId());
            }
            dbBbtResultTextBean.removeByActionId(actionId);

            // записываем новые данные в БД
            toLog.addN("Save new data");

            final BbtResponse response = new BbtResponse();
            response.setId(actionId);
            response.setDoctorId(bakPosev.getDoctor().getId());
            response.setFinalFlag(bakPosev.isComplete() ? 1 : 0);
            response.setDefects("нет");
            response.setCodeLIS(bakPosev.getDoctor().getCodeLis());
            dbBbtResponseBean.add(response);

            toLog.addN("Save response: [#]", response);

            if (!"".equals(bakPosev.getGeneralComment())) {
                final BbtResultText bbtResultText = new BbtResultText();
                bbtResultText.setActionId(actionId);
                bbtResultText.setValueText("Общий комментарий: " + bakPosev.getGeneralComment());
                dbBbtResultTextBean.add(bbtResultText);
            }

            for (Microorganism microorganism : bakPosev.getMicroorganismList()) {
                dbRbMicroorganismBean.add(new RbMicroorganism(microorganism.getCode(), microorganism.getName()));

                final RbMicroorganism mic = dbRbMicroorganismBean.get(microorganism.getCode());
                final BbtResultOrganism resultOrganism = new BbtResultOrganism();
                resultOrganism.setActionId(bakPosev.getActionId());
                resultOrganism.setConcentration(microorganism.getComment());
                resultOrganism.setOrganismId(mic.getId());
                dbBbtResultOrganismBean.add(resultOrganism);

                if (!"".equals(microorganism.getComment())) {
                    final BbtResultText bbtResultText = new BbtResultText();
                    bbtResultText.setActionId(actionId);
                    bbtResultText.setValueText(microorganism.getName() + " - " + microorganism.getComment());
                    dbBbtResultTextBean.add(bbtResultText);
                }

                for (Antibiotic antibiotic : microorganism.getAntibioticList()) {
                    dbRbAntibioticBean.add(new RbAntibiotic(antibiotic.getCode(), antibiotic.getName()));
                    final RbAntibiotic rbAntibiotic = dbRbAntibioticBean.get(antibiotic.getCode());
                    final RbMicroorganism rbMicroorganism = dbRbMicroorganismBean.get(microorganism.getCode());
                    final BbtResultOrganism bbtResultOrganism = dbBbtResultOrganismBean.get(rbMicroorganism.getId(), bakPosev.getActionId());

                    final BbtOrganismSensValues bbtOrganismSens = new BbtOrganismSensValues();
                    bbtOrganismSens.setActivity(antibiotic.getSensitivity());
                    bbtOrganismSens.setAntibioticId(rbAntibiotic.getId());
                    bbtOrganismSens.setBbtResultOrganismId(bbtResultOrganism.getId());
                    bbtOrganismSens.setMic(antibiotic.getConcentration());
                    dbBbtOrganismSensValuesBean.add(bbtOrganismSens);

                    if (!"".equals(antibiotic.getComment())) {
                        final BbtResultText bbtResultText2 = new BbtResultText();
                        bbtResultText2.setActionId(actionId);
                        bbtResultText2.setValueText(antibiotic.getName() + " - " + antibiotic.getComment());
                        dbBbtResultTextBean.add(bbtResultText2);
                    }
                }
            }

            // Изменяем статус действия на "Закончено"
            if (bakPosev.isComplete()) {
                dbAction.updateActionStatusWithFlush(actionId, ActionStatus.FINISHED.getCode());
                toLog.addN("Save status [#]", ActionStatus.FINISHED.getCode());
            }

        } catch (Exception e) {
            toLog.addN("Exception " + e);
            logger.error("Exception " + e, e);
            throw new CoreException("Не удалось сохранить данные по БАК-посеву");
        }
    }

    /**
     * Определение actionId, к которому прявязаны результаты
     */
//    private int getActionId(final POLBIN224100UV01 request) throws CoreException {
//        for (POLBIN224100UV01MCAIMT700201UV01Subject2 subj : request.getControlActProcess().getSubject()) {
//            if (subj.getObservationBattery() != null) {
//                return Integer.parseInt(subj.getObservationBattery().getValue().getInFulfillmentOf().get(0).getPlacerOrder().getValue().getId().get(0).getExtension());
//            }
//        }
//        throw new CoreException("Отсутствует actionId");
//    }

//    private void saveIfaToProperty(int actionId, String resultValue, String resultText, ToLog toLog) {
//        try {
//            final String ifaValue = resultValue + "/" + resultText;
//
//            final Action action = dbAction.getActionById(actionId);
//            int aptId = 0;
//            for (ActionProperty property : action.getActionProperties()) {
//                final ActionPropertyType type = property.getType();
//                if (type.getCode() != null && "ifa".equals(type.getCode())) {
//                    aptId = type.getId();
//                }
//            }
//            db.addSinglePropBasic(ifaValue, APValueString.class, actionId, aptId, true);
//            toLog.add("Save IFA result [" + ifaValue + "], aptId [" + aptId + "]\n");
//        } catch (Exception e) {
//            logger.error("Exception: " + e, e);
//            toLog.add("Problem save to ActionProperty IFA values: " + e + "]\n");
//        }
//    }

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
        acknowledgement.setTypeCode(AcknowledgementType.AA);
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
        acknowledgement.setTypeCode(AcknowledgementType.AE);
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

        logger.info("Bak Delivered [{}],[{}],[{}],[{}]", GUID, orderMisId, orderBarCode, DtTime);
        return 0;
    }
}
