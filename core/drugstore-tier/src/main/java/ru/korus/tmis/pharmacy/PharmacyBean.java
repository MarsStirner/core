package ru.korus.tmis.pharmacy;

import misexchange.MISExchange;
import misexchange.Request;
import org.hl7.v3.*;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.DbRbFinance1CBeanLocal;
import ru.korus.tmis.core.database.common.*;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.entity.model.pharmacy.*;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.pharmacy.*;
import ru.korus.tmis.pharmacy.exception.MessageProcessException;
import ru.korus.tmis.pharmacy.exception.NoSuchOrgStructureException;
import ru.korus.tmis.pharmacy.exception.SkipMessageProcessException;
import ru.korus.tmis.prescription.Constants;
import ru.korus.tmis.scala.util.ConfigManager;
import ru.korus.tmis.util.logs.ToLog;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.sql.Timestamp;
import java.util.*;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        29.10.12, 17:30 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Работа по отправке сообщений в сторону 1С Аптеки<br>
 */
//@Interceptors(LoggingInterceptor.class)
@Stateless
public class PharmacyBean implements PharmacyBeanLocal {

    private static final Logger logger = LoggerFactory.getLogger(PharmacyBean.class);

    private static final int LAST_ACTIONS = 10;

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @EJB
    private DbActionBeanLocal dbAction = null;

    @EJB
    private DbOrgStructureBeanLocal dbOrgStructureBeanLocal = null;

    @EJB
    private DbPharmacyBeanLocal dbPharmacy = null;

    @EJB
    private DbUUIDBeanLocal dbUUIDBeanLocal = null;

    @EJB
    private DbCustomQueryLocal dbCustomQueryLocal = null;

    @EJB
    private DbActionPropertyBeanLocal dbActionPropertyBeanLocal = null;

    @EJB
    private DbActionPropertyTypeBeanLocal dbActionPropertyTypeBeanLocal = null;

    @EJB
    private DbOrganizationBeanLocal dbOrganizationBeanLocal = null;

    @EJB
    private DbRbFinance1CBeanLocal dbRbFinance1CBeanLocal = null;

    @EJB
    private DbPrescriptionsTo1CBeanLocal dbPrescriptionsTo1CBeanLocal = null;

    @EJB
    private DbRbMethodOfAdministrationLocal dbRbMethodOfAdministrationLocal = null;

    @EJB
    private DbPrescriptionSendingResBeanLocal dbPrescriptionSendingResBeanLocal = null;

    @EJB
    private DbDrugChartBeanLocal dbDrugChartBeanLocal = null;

    private DateTime lastDateUpdate = null;

    /**
     * Назначен/Не исполнен (ещё или уже)
     */
    private static final int PS_NEW = 0;

    /**
     * Полинг базы данных для поиска событий по движениям пациентов и назначениям ЛС
     */
    @Override
    //@Schedule(minute = "*/1", hour = "*", persistent = false)
    public void pooling() {
        if (ConfigManager.Drugstore().isActive()) {
            try {
                logger.info("pooling... last modify date {}", getLastDate());
                if (lastDateUpdate == null) {
                    lastDateUpdate = firstPolling();
                }
                final List<Action> actionAfterDate = dbPharmacy.getVirtualActionsAfterDate(lastDateUpdate);
                if (!actionAfterDate.isEmpty()) {
                    logger.info("Found {} newest actions after date {}", actionAfterDate.size(), getLastDate());
                    for (Action action : actionAfterDate) {
                        final ToLog toLog = new ToLog(action.getActionType().getFlatCode());
                        try {
                            if (isActionForSend(action)) {
                                send(action, toLog);
                                lastDateUpdate = new DateTime(action.getCreateDatetime());
                            }
                        } finally {
                            logger.info(toLog.releaseString());
                        }
                    }
                }
                // повторная отправка неотправленных сообщений
              //todo Специально для Даши на время теста resendMessages();
                //Отправка назначений ЛС
                //logger.info("sending prescription start...");
                sendPrescriptionTo1C();
                //logger.info("sending prescription stop");
            } catch (Throwable e) {
                logger.error("Throwable e: " + e, e);
            }
        } else {
            logger.info("pooling... {}", ConfigManager.Drugstore().Active());
        }
    }

    /**
     * Повторная отправка сообщений, которые имеют статус отличный от COMPLETE
     *
     * @throws CoreException
     */
    private void resendMessages() {
        // resend old messages
        final List<Pharmacy> nonCompletedItems = dbPharmacy.getNonCompletedItems();
        if (!nonCompletedItems.isEmpty()) {
            ToLog toLog = new ToLog("Resend old message....");
            for (Pharmacy pharmacy : nonCompletedItems) {
                try {
                    final Action action = dbAction.getActionByIdWithIgnoreDeleted(pharmacy.getActionId());
                    send(action, toLog);
                } catch (Exception e) {
                    logger.error("Resend old Exception e: " + e, e);
                    toLog.add("Resend old Exception e: " + e);
                }
            }
            logger.info(toLog.releaseString());
        }
    }

    /**
     * Отправка события в 1С Аптеку по событию Action
     */
    private void send(final Action action, final ToLog toLog) {
        Pharmacy pharmacy = null;
        try {
            // сохранение данных об отправке
            pharmacy = dbPharmacy.getOrCreate(action);
            // формирование сообщения для отправки
            final Request request = createRequest(action, toLog);

            toLog.addN("prepare message... \n\n #", HL7PacketBuilder.marshallMessage(request, "misexchange"));
            // отправка сообщения в 1С
            // final MCCIIN000002UV01 result = new MISExchange().getMISExchangeSoap().processHL7V3Message(request);
            final MCCIIN000002UV012 result = new MISExchange().getMISExchangeSoap().processHL7V3Message(request);
            if (result != null) {
                // обработка результата
                toLog.addN("Connection successful. Result: # \n\n #",
                        result, HL7PacketBuilder.marshallMessage(result, "org.hl7.v3"));

                for (MCCIMT000200UV01Acknowledgement ack : result.getAcknowledgement()) {
                    pharmacy.setDocumentUUID(ack.getTargetMessage().getId().getRoot());
                    pharmacy.setResult(ack.getTypeCode().value());
                    if (AcknowledgementType.AA.equals(ack.getTypeCode())) {
                        pharmacy.setStatus(PharmacyStatus.COMPLETE);
                    } else {
                        final StringBuilder sb = getAckString(ack);
                        pharmacy.setErrorString(sb.toString());
                        pharmacy.setStatus(PharmacyStatus.ERROR);
                    }
                }
            } else {
                toLog.add("Error connection with 1C Pharmacy. Message was set in ERROR status");
                pharmacy.setStatus(PharmacyStatus.ERROR);
            }
        } catch (NoSuchOrgStructureException e) {
            if (pharmacy != null) {
                pharmacy.setErrorString("NoSuchOrgStructureException " + e);
                pharmacy.setStatus(PharmacyStatus.ERROR);
            }
            toLog.add("OrgStructure not found. Skip message: " + e);
        } catch (SkipMessageProcessException e) {
            if (pharmacy != null) {
                pharmacy.setErrorString("SkipMessageProcessException " + e);
                pharmacy.setStatus(PharmacyStatus.ERROR);
            }
            toLog.add("Skip message: " + e);
        } catch (Exception e) {
            if (pharmacy != null) {
                pharmacy.setErrorString("Exception " + e);
                pharmacy.setStatus(PharmacyStatus.ERROR);
            }
            toLog.add("Exception: " + e, e);
        } finally {
            if (pharmacy != null) {
                try {
                    dbPharmacy.updateMessage(pharmacy);
                } catch (CoreException ce) {
                    toLog.add("CoreException on set Pharmacy in error! " + ce, ce);
                }
            }
        }
    }

    private StringBuilder getAckString(MCCIMT000200UV01Acknowledgement ack) {
        final StringBuilder sb = new StringBuilder();
        final List<MCCIMT000200UV01AcknowledgementDetail> detailList = ack.getAcknowledgementDetail();
        for (MCCIMT000200UV01AcknowledgementDetail detail : detailList) {
            final ED text = detail.getText();
            for (Object o : text.getContent()) {
                sb.append(o);
            }
        }
        return sb;
    }

    /**
     * Проверка на код по движению пациентов
     */
    private boolean isActionForSend(final Action action) {
        final ActionType actionType = action.getActionType();
        return FlatCode.RECEIVED.getCode().equalsIgnoreCase(actionType.getFlatCode())
                || FlatCode.DEL_RECEIVED.getCode().equalsIgnoreCase(actionType.getFlatCode())
                || FlatCode.MOVING.getCode().equalsIgnoreCase(actionType.getFlatCode())
                || FlatCode.DEL_MOVING.getCode().equalsIgnoreCase(actionType.getFlatCode())
                || FlatCode.LEAVED.getCode().equalsIgnoreCase(actionType.getFlatCode());
        // || FlatCode.PRESCRIPTION.getCode().equalsIgnoreCase(actionType.getFlatCode());
    }

    /**
     * Создание объектной модели сообщения для 1С Аптеки в соответсвии с типом сообщения
     *
     * @param action событие на основе которого отправляется сообщение в 1С Аптеку
     * @return возвращает класс готовый к отправке в 1С Аптеку
     * @throws MessageProcessException проблемы при создании сообщения
     */
    public Request createRequest(final Action action, final ToLog toLog)
            throws MessageProcessException, SkipMessageProcessException, NoSuchOrgStructureException {

        final ActionType actionType = action.getActionType();

        if (FlatCode.RECEIVED.getCode().equalsIgnoreCase(actionType.getFlatCode())) {
            final OrgStructure structure = getReceivedOrgStructure(action);
            toLog.addN("receive orgStructure [#], [#]", structure.getId(), structure.getName());
            return HL7PacketBuilder.processReceived(action, structure, getFinaceType(action));

        } else if (FlatCode.DEL_RECEIVED.getCode().equalsIgnoreCase(actionType.getFlatCode())) {
            // отмена поступления
            return HL7PacketBuilder.processDelReceived(action);

        } else if (FlatCode.MOVING.getCode().equalsIgnoreCase(actionType.getFlatCode())) {
            // движение пациента между отделениями
            final OrgStructure outStruct = getOrgStructureOut(action);
            toLog.addN("out orgStructure [#], [#]", outStruct.getId(), outStruct.getName());
            final OrgStructure inStruct = getOrgStructureIn(action);
            toLog.addN("in orgStructure [#], [#]", inStruct.getId(), inStruct.getName());
            return HL7PacketBuilder.processMoving(action, outStruct, inStruct);

        } else if (FlatCode.DEL_MOVING.getCode().equalsIgnoreCase(actionType.getFlatCode())) {
            // отмена движения
            final OrgStructure outStruct = getOrgStructureOutWithDel(action);
            toLog.addN("out orgStructure [#], [#]", outStruct.getId(), outStruct.getName());
            final OrgStructure inStruct = getOrgStructureInWithDel(action);
            toLog.addN("in orgStructure [#], [#]", inStruct.getId(), inStruct.getName());
            return HL7PacketBuilder.processDelMoving(action, outStruct, inStruct);

        } else if (FlatCode.LEAVED.getCode().equalsIgnoreCase(actionType.getFlatCode())) {
            // выписка из стационара
            return HL7PacketBuilder.processLeaved(action);

        }
        toLog.addN("No such flat code type [#]", actionType.getFlatCode());
        throw new MessageProcessException();
    }

    /**
     * Поллинг запустился первый раз, производится поиск подходящих сообщений по движению пациентов
     *
     * @return - дата и время последеней просмотренной записи
     */
    private DateTime firstPolling() {
        final DateTime modifyDate = DateTime.now();
        final ToLog toLog = new ToLog("First pooling");
        try {
            final List<Action> actionList = dbPharmacy.getVirtualActions(LAST_ACTIONS);
            if (!actionList.isEmpty()) {
                toLog.addN("Fetch last actions with size [#]", actionList.size());
                for (Action action : actionList) {
                    final Pharmacy checkPharmacy = dbPharmacy.getPharmacyByAction(action);
                    if (checkPharmacy == null) {
                        toLog.addN("Found non sending action [#], flatCode [#]", action, action.getActionType().getFlatCode());
                        send(action, toLog);
                    }
                }
            }
        } catch (Exception ex) {
            logger.error("Error in PharmacyBean.firstPolling: {}", ex);
        } finally {
            logger.info(toLog.releaseString());
        }
        return modifyDate;
    }

    private String getLastDate() {
        return lastDateUpdate != null ? lastDateUpdate.toString(DATE_TIME_FORMAT) : "none";
    }

    private Organisation getCustodianOrgStructure(final Action action) throws NoSuchOrgStructureException {
        final Event event = action.getEvent();
        Organisation res = event.getOrganisation();
        if (res == null) {
            throw new NoSuchOrgStructureException();
        }
        return res;
    }

    /**
     * Поиск OrgStructure для конкретного Action
     */
    private OrgStructure getOrgStructureOut(final Action action) throws SkipMessageProcessException {
        try {
            final Set<String> codes = new HashSet<String>();
            codes.add("orgStructReceived");
            final Map<ActionProperty, List<APValue>> names =
                    dbActionPropertyBeanLocal.getActionPropertiesByActionIdAndActionPropertyTypeCodesWithoutDel(action.getId(), codes);

            for (ActionProperty property : names.keySet()) {
                final List<APValue> apValues = names.get(property);
                for (APValue apValue : apValues) {
                    if (apValue instanceof APValueOrgStructure) {
                        final OrgStructure orgStructure = (OrgStructure) apValue.getValue();
                        return orgStructure;
                    }
                }
            }
        } catch (CoreException e) {
        }
        throw new SkipMessageProcessException();
    }

    /**
     * Поиск OrgStructure для конкретного Action
     */
    private OrgStructure getOrgStructureOutWithDel(final Action action) throws SkipMessageProcessException {
        try {
            if (action.getParentActionId() != null && action.getParentActionId() != 0) {
                final Action parentAction = dbAction.getActionByIdWithIgnoreDeleted(action.getParentActionId());
                if (parentAction != null) {
                    final Set<String> codes = new HashSet<String>();
                    codes.add("orgStructReceived");

                    final Map<ActionProperty, List<APValue>> names =
                            dbActionPropertyBeanLocal.getActionPropertiesByActionIdAndActionPropertyTypeCodesWithoutDel(parentAction.getId(), codes);
                    for (ActionProperty property : names.keySet()) {
                        final List<APValue> apValues = names.get(property);
                        for (APValue apValue : apValues) {
                            if (apValue instanceof APValueOrgStructure) {
                                return (OrgStructure) apValue.getValue();
                            }
                        }
                    }
                }
            }
        } catch (CoreException e) {
        }
        throw new SkipMessageProcessException();
    }

    /**
     * Поиск OrgStructure для конкретного Action
     */
    private OrgStructure getOrgStructureIn(final Action action) throws SkipMessageProcessException {
        try {
            final Set<String> codes = new HashSet<String>();
            codes.add("orgStructStay");
            final Map<ActionProperty, List<APValue>> names
                    = dbActionPropertyBeanLocal.getActionPropertiesByActionIdAndActionPropertyTypeCodesWithoutDel(action.getId(), codes);

            for (ActionProperty property : names.keySet()) {
                final List<APValue> apValues = names.get(property);
                for (APValue apValue : apValues) {
                    if (apValue instanceof APValueOrgStructure) {
                        return (OrgStructure) apValue.getValue();
                    }
                }
            }
        } catch (CoreException e) {
        }
        throw new SkipMessageProcessException();
    }

    /**
     * Поиск OrgStructure для конкретного Action
     */
    private OrgStructure getOrgStructureInWithDel(final Action action) throws SkipMessageProcessException {
        try {
            if (action.getParentActionId() != null && action.getParentActionId() != 0) {
                final Action parentAction = dbAction.getActionByIdWithIgnoreDeleted(action.getParentActionId());
                if (parentAction != null) {
                    final Set<String> codes = new HashSet<String>();
                    codes.add("orgStructStay");

                    final Map<ActionProperty, List<APValue>> names
                            = dbActionPropertyBeanLocal.getActionPropertiesByActionIdAndActionPropertyTypeCodesWithoutDel(parentAction.getId(), codes);

                    for (ActionProperty property : names.keySet()) {
                        final List<APValue> apValues = names.get(property);
                        for (APValue apValue : apValues) {
                            if (apValue instanceof APValueOrgStructure) {
                                return (OrgStructure) apValue.getValue();
                            }
                        }
                    }
                }
            }
        } catch (CoreException e) {
        }
        throw new SkipMessageProcessException();
    }

    /**
     * Поиск OrgStructure для конкретного Action
     */
    private OrgStructure getReceivedOrgStructure(final Action action) throws SkipMessageProcessException {
        try {
            final Set<String> codesSet = new HashSet<String>();
            codesSet.add("orgStructStay");

            final Map<ActionProperty, List<APValue>> names
                    = dbActionPropertyBeanLocal.getActionPropertiesByActionIdAndActionPropertyTypeCodesWithoutDel(action.getId(), codesSet);
            for (ActionProperty property : names.keySet()) {
                final List<APValue> apValues = names.get(property);
                for (APValue apValue : apValues) {
                    if (apValue instanceof APValueOrgStructure) {
                        return (OrgStructure) apValue.getValue();
                    }
                }
            }
        } catch (CoreException e) {
            // skip
        }
        throw new SkipMessageProcessException("OrgStructure for " + action + " is not found");
    }

    /**
     * Отправка интервалов назначений ЛС и их исполнений
     *
     * @throws CoreException
     */
    public void sendPrescriptionTo1C() {
        Iterable<PrescriptionsTo1C> prescriptions = dbPrescriptionsTo1CBeanLocal.getPrescriptions();
        for (PrescriptionsTo1C prescription : prescriptions) {
            try {
                int errCount = prescription.getErrCount();
                long step = 89 * 1000; // время до слейдующей попытки передачи данных
                prescription.setSendTime(new Timestamp((new java.util.Date()).getTime() + (long) (errCount) * step));
                if (sendPrescription(prescription)) {
                    dbPrescriptionsTo1CBeanLocal.remove(prescription);
                }
            } catch (Exception e) {
                logger.error("Exception: " + e, e);
            }
        }
    }

    /**
     * Отправка одного интервала назначения/исполнения в ЛС
     *
     * @param prescription
     * @return - true - интервал успешно передан в 1С
     * @throws CoreException
     */
    private boolean sendPrescription(PrescriptionsTo1C prescription) throws CoreException {
        boolean res = false;
        try {
            final Action action = prescription.getDrugChart().getAction();
            final Event event = action.getEvent();
            // Пациент
            final Patient client = action.getEvent().getPatient();
            // Организация, которой принадлежит документ
            final Organisation organisation;
            organisation = getCustodianOrgStructure(action);
            // Определяем код назначенного препарата
            final List<DrugComponent> drugComponents = dbPharmacy.getDrugComponent(action);
            // Способ применения
            final String code[] = {Constants.MOA};
            String routeOfAdministration = null;

            final Map<ActionProperty, List<APValue>> actionProp = dbActionPropertyBeanLocal.getActionPropertiesByActionIdAndTypeCodes(action.getId(), Arrays.asList(code));
            if (!actionProp.isEmpty() && !actionProp.entrySet().iterator().next().getValue().isEmpty()) {
                Object codeId = actionProp.entrySet().iterator().next().getValue().iterator().next().getValue();
                if (codeId instanceof Integer) {
                    routeOfAdministration = dbRbMethodOfAdministrationLocal.getById((Integer) codeId).getCode();
                }
            }
            Request request = null;
            final String financeType = getFinaceType(action);
            final Iterable<DrugChart> intervalsByEvent = this.dbDrugChartBeanLocal.getIntervalsByEvent(event);
            final Map<DrugChart, List<DrugComponent>> intervalsWithDrugComp = new HashMap<DrugChart, List<DrugComponent>>();
            for (DrugChart interval : intervalsByEvent) {
                intervalsWithDrugComp.put(interval, dbPharmacy.getDrugComponent(interval.getAction()));
            }
            PrescriptionInfo prescriptionInfo = new PrescriptionInfo(event,
                    action,
                    intervalsWithDrugComp,
                    routeOfAdministration,
                    financeType,
                    dbPrescriptionSendingResBeanLocal);

            for (DrugComponent comp : drugComponents) {
                ToLog toLog = new ToLog("PRESCRIPTION");
                try {
                    final PrescriptionSendingRes prescriptionSendingRes = dbPrescriptionSendingResBeanLocal.getPrescriptionSendingRes(prescription.getDrugChart(), comp);
                    prescriptionInfo.setPrescrUUID(this.dbPrescriptionSendingResBeanLocal.getIntervalUUID(prescription.getDrugChart(), comp));
                    if (prescription.isPrescription()) { // передача нового / отмена назначения
                        prescriptionInfo.setAssignmentType(AssignmentType.ASSIGNMENT)
                                .setNegationInd(prescription.getNewStatus().equals(PrescriptionStatus.PS_CANCELED));
                    } else if (prescription.getOldStatus().equals(PrescriptionStatus.PS_NEW)
                            && prescription.getNewStatus().equals(PrescriptionStatus.PS_FINISHED)) {// если статус изменился с "Назначен" на "Исполнен", то передаем исполнение
                        prescriptionInfo.setAssignmentType(AssignmentType.EXECUTION).setNegationInd(false);
                    } else if (prescription.getOldStatus().equals(PrescriptionStatus.PS_FINISHED)
                            && prescription.getNewStatus().equals(PrescriptionStatus.PS_NEW)) { // если статус изменился с "Исполнен" на "Назначен" , то передаем отмену исполнения
                        prescriptionInfo.setAssignmentType(AssignmentType.EXECUTION).setNegationInd(true);
                    } else {
                        prescriptionInfo.setAssignmentType(null);
                    }

                    if (prescriptionInfo.getAssignmentType() != null) {
                        request = HL7PacketBuilder.processPrescription(
                                action,
                                prescriptionInfo,
                                organisation,
                                prescriptionSendingRes,
                                toLog);
                    }
                    if (request != null) {
                        prescription.setErrCount(prescription.getErrCount() + 1);
                        toLog.add("prepare message... \n\n # \n", HL7PacketBuilder.marshallMessage(request, "misexchange"));
                        if (!HL7PacketBuilder.isTestMode()) {
                            final MCCIIN000002UV012 result = new MISExchange().getMISExchangeSoap().processHL7V3Message(request);
                            toLog.add("Connection successful. Result: # \n\n # \n",
                                    result, HL7PacketBuilder.marshallMessage(result, "org.hl7.v3"));

                            if (isOk(result)) {
                                prescriptionSendingRes.setUuid(prescriptionInfo.getPrescrUUID());
                                prescriptionSendingRes.setVersion(prescriptionSendingRes.getVersion() == null ? 1 : (prescriptionSendingRes.getVersion() + 1));
                                res = true;
                            }
                        }
                    }
                } finally {
                    logger.info(toLog.releaseString());
                }
            }
        } catch (Exception e) {
            final String errorString = e.toString();
            prescription.setInfo(errorString);
            logger.error("sending prescription to 1C issue: ", e);
        }
        return res;
    }

    private String getFinaceType(Action action) {
        Integer id = action.getFinanceId();
        if (id == null) {
            final Event event = action.getEvent();
            if (event != null) {
                final RbFinance finance = event.getEventType().getFinance();
                id = finance == null ? null : finance.getId();
            }
        }
        final RbFinance1C rbFinance1C = dbRbFinance1CBeanLocal.getByFianceId(id);
        if (rbFinance1C == null) {
            return null;
        }
        return rbFinance1C.getCode1C();
    }

    private boolean isOk(MCCIIN000002UV012 result) throws CoreException {
        boolean res = false;
        for (MCCIMT000200UV01Acknowledgement ack : result.getAcknowledgement()) {
            if (AcknowledgementType.AA.equals(ack.getTypeCode())) {
                res = true;
            } else {
                final StringBuilder sb = getAckString(ack);
                throw new CoreException(sb.toString());
            }
        }
        return res;
    }


}
