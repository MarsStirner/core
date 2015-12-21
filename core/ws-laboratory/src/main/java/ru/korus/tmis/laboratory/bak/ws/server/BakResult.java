package ru.korus.tmis.laboratory.bak.ws.server;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.bak.*;
import ru.korus.tmis.core.database.common.DbActionBeanLocal;
import ru.korus.tmis.core.database.common.DbActionPropertyBeanLocal;
import ru.korus.tmis.core.database.common.DbActionPropertyTypeBeanLocal;
import ru.korus.tmis.core.database.common.DbCustomQueryLocal;
import ru.korus.tmis.core.database.dbutil.Database;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.entity.model.bak.*;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.laboratory.bak.BakResultService;
import ru.korus.tmis.lis.data.model.*;
import ru.korus.tmis.lis.data.model.hl7.complex.*;
import ru.korus.tmis.util.Utils;
import ru.korus.tmis.util.logs.ToLog;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.validation.constraints.NotNull;
import javax.xml.bind.JAXBElement;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.UUID;

import static ru.korus.tmis.lis.data.model.hl7.HL7Specification.NAMESPACE;
import static ru.korus.tmis.lis.data.model.hl7.HL7Specification.SUCCESS_ACCEPT_EVENT;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(BakResult.class);

    private static final String CONTEXT_PATH__RU_KORUS_TMIS_LIS_DATA_MODEL_HL7_COMPLEX = "ru.korus.tmis.lis.data.model.hl7.complex";
    public static final String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

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
            toLog.addN("Request: " + Utils.marshallMessage(request, CONTEXT_PATH__RU_KORUS_TMIS_LIS_DATA_MODEL_HL7_COMPLEX));
            processRequest(request, toLog);
            response = createSuccessResponse();
            toLog.addN("Response: " + Utils.marshallMessage(response, CONTEXT_PATH__RU_KORUS_TMIS_LIS_DATA_MODEL_HL7_COMPLEX));
            return response;
        } catch (Exception e) {
            LOGGER.error("Exception in setAnalysisResults:", e);
            toLog.addN("Exception: #", e);
            response = createErrorResponse();
            toLog.addN("Response:" + Utils.marshallMessage(response, CONTEXT_PATH__RU_KORUS_TMIS_LIS_DATA_MODEL_HL7_COMPLEX));
        } finally {
            LOGGER.info(toLog.releaseString());
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
     * @param request  тело запроса  POLBIN224100UV01
     * @return значение ИФА исследования, null - это не ИФА исследование
     */
    @NotNull
    private static List<IFA> processIFA(final POLBIN224100UV01 request) {
        final List<IFA> result = new ArrayList<>();
        try {
            for (POLBIN224100UV01MCAIMT700201UV01Subject2 subj : request.getControlActProcess().getSubject()) {
                final JAXBElement<POLBMT004000UV01ObservationBattery> observationBattery = subj.getObservationBattery();
                if (observationBattery != null) {
                    final POLBMT004000UV01ObservationBattery observationBatteryValue = observationBattery.getValue();
                    final String orderMisId = observationBatteryValue.getInFulfillmentOf().get(0).getPlacerOrder().getValue().getId().get(0).getExtension();
                    final POLBMT004000UV01ObservationEvent componentObservationEventValue = observationBatteryValue.getComponent1().get(0).getObservationEvent().getValue();
                    final boolean finalFlag = "true".equals(componentObservationEventValue.getStatusCode().getCode());
                    final String code = componentObservationEventValue.getCode().getCode();
                    final List<CE> ceList = componentObservationEventValue.getConfidentialityCode();
                    if (ceList != null && !ceList.isEmpty()) {
                        final String text = ceList.get(0).getDisplayName();
                        final String value = ceList.get(0).getCode();
                        if (!text.isEmpty() || !value.isEmpty()) {
                            final int actionId = Integer.parseInt(orderMisId);
                            final IFA ifa = new IFA();
                            ifa.setText(text);
                            ifa.setValue(value);
                            ifa.setActionId(actionId);
                            ifa.setComplete(finalFlag);
                            ifa.setCode(code);
                            result.add(ifa);
                        }
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Exception in processIFA", e);
        }
        return result;
    }

    /**
     * Сохранение ИФА исседования
     *
     * @param ifa Ифа исследование с результатом лабораторного теста
     * @param toLog логгер
     */
    private void saveIFA(final IFA ifa, final ToLog toLog) throws CoreException {
        try {
            final Action action = dbAction.getActionById(ifa.getActionId());
            if(action == null){
                LOGGER.error("Action[{}] not found. Args: {}", ifa.getActionId(), ifa);
                throw new CoreException("Не удалось сохранить данные по ИФА");
            }  else if(ActionStatus.FINISHED.getCode() == action.getStatus()){
                LOGGER.error("Action[{}] has status = FINISHED and no more modification is allowed. Args: {}", ifa.getActionId(), ifa);
                throw new CoreException("Не удалось сохранить данные по ИФА");
            }
            int ifaResultPropId = 0;
            int ifaCommentPropId = 0;
            for (ActionProperty property : action.getActionProperties()) {
                final ActionPropertyType type = property.getType();
                //Если у свойства есть Тест И его код совпадает с кодом ИФА-исследования, то нужно устаналивать значение этого свойства
                if(property.getType().getTest() != null && StringUtils.equals(ifa.getCode(), property.getType().getTest().getCode())){
                        ifaResultPropId = type.getId();
                }
                //Если среди свойст экшена есть совства с ActionPropertyType.code = 'ifa', то заполняются они (приоритетнее совпадения по коду теста)
                if ("ifa".equals(type.getCode())) {
                    ifaResultPropId = type.getId();
                }
                //Поиск комментария, куда будет записан комментарий к ИФА-исследованию
                if("comment".equals(type.getCode())) {
                    ifaCommentPropId = type.getId();
                }
            }
            if(ifaResultPropId > 0) {
                db.addSinglePropBasic(ifa.getFullResult(), APValueString.class, ifa.getActionId(), ifaResultPropId, true);
            }
            if(ifaCommentPropId > 0 && ifa.getComment() != null)
                db.addSinglePropBasic(ifa.getComment(), APValueString.class, ifa.getActionId(), ifaCommentPropId, true);
            toLog.addN("Save IFA result [#], ifaResultPropId [#]", ifa.getFullResult(), ifaResultPropId);
            // Изменяем статус действия на "Закончено"
            if (ifa.isComplete()) {
                dbAction.updateActionStatusWithFlush(action.getId(), ActionStatus.FINISHED.getCode());
                toLog.addN("Save status [#]", ActionStatus.FINISHED.getCode());
            }
        } catch (Exception e) {
            LOGGER.error("Exception in saveIFA:", e);
            toLog.add("Problem save to ActionProperty IFA values: " + e + "]\n");
            throw new CoreException("Не удалось сохранить данные по ИФА", e);
        }
    }

    private static BakPosev getBakPosevByActionId(final Map<Integer, BakPosev> bakPosevMap, final int actionId) {
        BakPosev bakPosev = bakPosevMap.get(actionId);
        if (bakPosev == null) {
            bakPosev = new BakPosev(actionId);
            bakPosevMap.put(actionId, bakPosev);
        }
        return bakPosev;
    }

    /**
     * Выборка данных по БАК-посеву
     *
     * @param request тело запроса POLBIN224100UV01
     * @return списко структур данных по БАК посевам
     */
    @NotNull
    private List<BakPosev> processBakPosev(final POLBIN224100UV01 request) throws CoreException {
        final Map<Integer, BakPosev> bakMap = new LinkedHashMap<>();
        for (POLBIN224100UV01MCAIMT700201UV01Subject2 subj : request.getControlActProcess().getSubject()) {
            if (subj.getObservationReport() != null) {
                try {
                    final POLBMT004000UV01ObservationReport value = subj.getObservationReport().getValue();
                    final int actionId = Integer.parseInt(value.getId().get(0).getRoot());
                    final BakPosev bakPosev = getBakPosevByActionId(bakMap, actionId);

                    bakPosev.setBarCode(value.getSpecimen().get(0).getSpecimen().getValue().getId().getRoot());

                    final COCTMT090000UV01AssignedEntity assignedEntity = value.getAuthor().get(0).getAssignedEntity();
                    bakPosev.setDoctor(new Doctor(
                            Integer.parseInt(assignedEntity.getCode().getCode()),
                            assignedEntity.getCode().getDisplayName(),
                            getLisCode(request)));

                } catch (Exception e) {
                    LOGGER.error("Exception: " + e, e);
                    throw new CoreException("Ошибка в формате тега observationReport");
                }

            } else if (subj.getObservationBattery() != null) {
                try {
                    final int actionIdObs = Integer.parseInt(
                            subj.getObservationBattery().getValue().getInFulfillmentOf().get(0).getPlacerOrder().getValue().getId().get(0).getExtension());
                    final BakPosev bakPosev = getBakPosevByActionId(bakMap, actionIdObs);

                    for (POLBMT004000UV01Component2 p : subj.getObservationBattery().getValue().getComponent1()) {
                        bakPosev.setGeneralComment(p.getObservationEvent().getValue().getCode().getCodeSystemName());
                        bakPosev.setComplete("true".equals(p.getObservationEvent().getValue().getStatusCode().getCode()));
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
                    LOGGER.error("Exception: " + e, e);
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
                            final CD componentObservationEventCodeValue = pp.getObservationEvent().getValue().getCode();
                            final String antibioticCode = StringUtils.defaultString(componentObservationEventCodeValue.getCode());
                            final String antibioticName =  StringUtils.defaultString(componentObservationEventCodeValue.getDisplayName());
                            final String antibioticConcentration =  StringUtils.defaultString(componentObservationEventCodeValue.getCodeSystem());
                            final String antibioticSensitivity =  StringUtils.defaultString(componentObservationEventCodeValue.getTranslation().get(0).getCode());
                            final String antibioticComment =  StringUtils.defaultString(pp.getObservationEvent().getValue().getStatusCode().getCode());
                            Antibiotic antibiotic = new Antibiotic(antibioticCode, antibioticName);
                            antibiotic.setConcentration(antibioticConcentration);
                            antibiotic.setSensitivity(antibioticSensitivity);
                            antibiotic.setComment(antibioticComment);

                            bakPosev.addAntibioticToOrganism(microorgCode, antibiotic);
                        }
                    }
                } catch (Exception e) {
                    LOGGER.error("Exception in processBakPosev:", e);
                    throw new CoreException("Ошибка в формате тега specimenObservationCluster");
                }
            }
        }
        return new LinkedList<>(bakMap.values());
    }

    /**
     * Запись результатов БАК-посева в БД.
     */
    private void saveBakPosev(final BakPosev bakPosev, final ToLog toLog) throws CoreException {
        try {
            final Action action = dbAction.getActionById(bakPosev.getActionId());
            if(action == null){
                LOGGER.error("Action[{}] not found. Args: {}", bakPosev.getActionId(), bakPosev);
                throw new CoreException("Не удалось сохранить данные по БАК-посеву");
            }  else if(ActionStatus.FINISHED.getCode() == action.getStatus()){
                LOGGER.error("Action[{}] has status = FINISHED and no more modification is allowed. Args: {}", bakPosev.getActionId(), bakPosev);
                throw new CoreException("Не удалось сохранить данные по БАК-посеву");
            }
            int actionId = action.getId();
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
                final RbMicroorganism mic = dbRbMicroorganismBean.add(new RbMicroorganism(microorganism.getCode(), microorganism.getName()));

                final BbtResultOrganism resultOrganism = new BbtResultOrganism();
                resultOrganism.setActionId(bakPosev.getActionId());
                resultOrganism.setConcentration(microorganism.getComment());
                resultOrganism.setOrganism(mic);
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
                    final BbtResultOrganism bbtResultOrganism = dbBbtResultOrganismBean.get(mic.getId(), bakPosev.getActionId());

                    final BbtOrganismSensValues bbtOrganismSens = new BbtOrganismSensValues();
                    bbtOrganismSens.setActivity(antibiotic.getSensitivity());
                    bbtOrganismSens.setAntibioticId(rbAntibiotic);
                    bbtOrganismSens.setBbtResultOrganism(bbtResultOrganism);
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
            LOGGER.error("Exception " + e, e);
            throw new CoreException("Не удалось сохранить данные по БАК-посеву");
        }
    }

    /**
     * Получить код лаборатории
     *
     * @param request  тело запроса POLBIN224100UV01
     * @return  Строковое значение кода лаборатории
     */
    private static String getLisCode(POLBIN224100UV01 request) {
        // Код лаборатории
        return request.getControlActProcess().getAuthorOrPerformer()
                .get(0)
                .getAssignedPerson().getValue()
                .getRepresentedOrganization()
                .getValue().getCode().getCode();
    }


    /**
     * Создание ответного сообщения о успешном принятии результатов
     */
    private static MCCIIN000002UV01 createSuccessResponse() {
        final MCCIIN000002UV01 response = new MCCIIN000002UV01();

        final II id2 = new II();
        id2.setRoot(UUID.randomUUID().toString());
        response.setId(id2);

        final TS creationTime = new TS();
        creationTime.setValue(new SimpleDateFormat(DATE_FORMAT_YYYYMMDDHHMMSS).format(new Date()));

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
    private static MCCIIN000002UV01 createErrorResponse() {
        final MCCIIN000002UV01 response = new MCCIIN000002UV01();

        final II id2 = new II();
        id2.setRoot(UUID.randomUUID().toString());
        response.setId(id2);

        final TS creationTime = new TS();
        creationTime.setValue(new SimpleDateFormat(DATE_FORMAT_YYYYMMDDHHMMSS).format(new Date()));

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
    public int bakDelivered(@WebParam(name = "orderBarCode", targetNamespace = Namespace)
                            Integer orderBarCode,
                            @WebParam(name = "TakenTissueJournal", targetNamespace = Namespace)
                            String takenTissueJournal,
                            @WebParam(name = "getTissueTime", targetNamespace = Namespace)
                            String tissueTime,
                            @WebParam(name = "orderBiomaterialName", targetNamespace = Namespace)
                            String orderBiomaterialName) throws CoreException {

        LOGGER.info("Bak Delivered [{}],[{}],[{}],[{}]", orderBarCode, takenTissueJournal, tissueTime, orderBiomaterialName);
        return 0;
    }
}
