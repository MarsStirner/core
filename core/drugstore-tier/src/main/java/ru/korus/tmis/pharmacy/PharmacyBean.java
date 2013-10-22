package ru.korus.tmis.pharmacy;

import misexchange.MISExchange;
import misexchange.Request;
import org.hl7.v3.*;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.*;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.entity.model.pharmacy.Pharmacy;
import ru.korus.tmis.core.entity.model.pharmacy.PharmacyStatus;
import ru.korus.tmis.core.entity.model.pharmacy.PrescriptionSendingRes;
import ru.korus.tmis.core.entity.model.pharmacy.PrescriptionsTo1C;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.pharmacy.*;
import ru.korus.tmis.pharmacy.exception.MessageProcessException;
import ru.korus.tmis.pharmacy.exception.NoSuchOrgStructureException;
import ru.korus.tmis.pharmacy.exception.SkipMessageProcessException;
import ru.korus.tmis.prescription.Constants;
import ru.korus.tmis.util.ConfigManager;
import ru.korus.tmis.util.logs.ToLog;

import javax.ejb.EJB;
import javax.ejb.Schedule;
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

    @EJB(beanName = "DbActionBean")
    private DbActionBeanLocal dbAction = null;

    @EJB(beanName = "DbOrgStructureBean")
    private DbOrgStructureBeanLocal dbOrgStructureBeanLocal = null;

    @EJB(beanName = "DbActionPropertyBean")
    private DbActionPropertyBeanLocal dbActionProperty = null;

    @EJB(beanName = "DbPharmacyBean")
    private DbPharmacyBeanLocal dbPharmacy = null;

    @EJB(beanName = "DbUUIDBean")
    private DbUUIDBeanLocal dbUUIDBeanLocal = null;

    @EJB(beanName = "DbCustomQueryBean")
    private DbCustomQueryLocal dbCustomQueryLocal = null;

    @EJB(beanName = "DbActionPropertyBean")
    private DbActionPropertyBeanLocal dbActionPropertyBeanLocal = null;

    @EJB(beanName = "DbActionPropertyTypeBean")
    private DbActionPropertyTypeBeanLocal dbActionPropertyTypeBeanLocal = null;

    @EJB(beanName = "DbOrganizationBean")
    private DbOrganizationBeanLocal dbOrganizationBeanLocal = null;

    @EJB
    private DbPrescriptionsTo1CBeanLocal dbPrescriptionsTo1CBeanLocal = null;

    @EJB
    private DbRbMethodOfAdministrationLocal dbRbMethodOfAdministrationLocal = null;

    @EJB
    private DbPrescriptionSendingResBeanLocal dbPrescriptionSendingResBean = null;

    private DateTime lastDateUpdate = null;

    /**
     * Назначен/Не исполнен (ещё или уже)
     */
    private static final int PS_NEW = 0;

    /**
     * исполнен
     */
    private static final int PS_FINISHED = 1;

    /**
     * Отменён
     */
    private static final int PS_CANCELED = 2;

    /**
     * Пауза
     */
    private static final int PS_PAUSE = 3;

    /**
     * Полинг базы данных для поиска событий по движениям пациентов и назначениям ЛС
     */
    @Override
    @Schedule(minute = "*/1", hour = "*", persistent = false)
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
                        final ToLog toLog = new ToLog();
                        try {
                            if (isActionForSend(action, toLog)) {
                                send(action, toLog);
                                lastDateUpdate = new DateTime(action.getCreateDatetime());
                            }
                        } finally {
                            logger.info(toLog.releaseString());
                        }
                    }
                }
                // повторная отправка неотправленных сообщений
                resendMessages();
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
                    final Action action = dbAction.getActionById(pharmacy.getActionId());
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

            toLog.add("prepare message... \n\n #", HL7PacketBuilder.marshallMessage(request, "misexchange"));
            // отправка сообщения в 1С
            // final MCCIIN000002UV01 result = new MISExchange().getMISExchangeSoap().processHL7V3Message(request);
            final MCCIIN000002UV012 result = new MISExchange().getMISExchangeSoap().processHL7V3Message(request);
            if (result != null) {
                // обработка результата
                toLog.add("Connection successful. Result: # \n\n #",
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
                toLog.add("Error connection with 1C Pharmacy. Message is setup ERROR status");
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
    private boolean isActionForSend(final Action action, final ToLog toLog) {
        final ActionType actionType = action.getActionType();
        toLog.add(actionType.getFlatCode());
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
            final OrgStructure structure = getReceivedOrgStructure(action, toLog);
            // поступление в стационар
            return HL7PacketBuilder.processReceived(action, structure);

        } else if (FlatCode.DEL_RECEIVED.getCode().equalsIgnoreCase(actionType.getFlatCode())) {
            // отмена поступления
            return HL7PacketBuilder.processDelReceived(action);

        } else if (FlatCode.MOVING.getCode().equalsIgnoreCase(actionType.getFlatCode())) {
            // движение пациента между отделениями
            final OrgStructure orgStructureOut = getOrgStructureOut(action, toLog);
            final OrgStructure orgStructureIn = getOrgStructureIn(action, toLog);

            return HL7PacketBuilder.processMoving(action, orgStructureOut, orgStructureIn);

        } else if (FlatCode.DEL_MOVING.getCode().equalsIgnoreCase(actionType.getFlatCode())) {
            // отмена движения
            final OrgStructure orgStructureOut = getOrgStructureOutWithDel(action, toLog);
            final OrgStructure orgStructureIn = getOrgStructureInWithDel(action, toLog);

            return HL7PacketBuilder.processDelMoving(action, orgStructureOut, orgStructureIn);

        } else if (FlatCode.LEAVED.getCode().equalsIgnoreCase(actionType.getFlatCode())) {
            // выписка из стационара
            return HL7PacketBuilder.processLeaved(action);

        }

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
                toLog.add("Fetch last actions with size [" + actionList.size() + "]");
                for (Action action : actionList) {
                    final Pharmacy checkPharmacy = dbPharmacy.getPharmacyByAction(action);
                    if (checkPharmacy == null) {
                        toLog.add("Found non sending action " + action
                                + ", flatCode [" + action.getActionType().getFlatCode() + "]");
                        send(action, toLog);
                    }
                }
            }
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
    private OrgStructure getOrgStructureOut(final Action action, final ToLog toLog) throws SkipMessageProcessException {
        try {
            final Set<String> codes = new HashSet<String>();
            codes.add("orgStructReceived");

            final Map<ActionProperty, List<APValue>> names =
                    dbActionPropertyBeanLocal.getActionPropertiesByActionIdAndActionPropertyTypeCodesWithoutDel(action.getId(), codes);

            toLog.add("getOrgStructureOut Action properties and type #", names);
            for (ActionProperty property : names.keySet()) {
                final List<APValue> apValues = names.get(property);
                for (APValue apValue : apValues) {
                    if (apValue instanceof APValueOrgStructure) {
                        final OrgStructure orgStructure = (OrgStructure) apValue.getValue();
                        toLog.add("orgStructure [#], [#]", orgStructure.getId(), orgStructure.getName());
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
    private OrgStructure getOrgStructureOutWithDel(final Action action, final ToLog toLog) throws SkipMessageProcessException {
        try {
            if (action.getParentActionId() != 0) {
                toLog.add("try recursive call # by actionParentId [#]", action, action.getParentActionId());
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
                                final OrgStructure orgStructure = (OrgStructure) apValue.getValue();
                                toLog.add("orgStructure [#], [#]", orgStructure.getId(), orgStructure.getName());
                                return orgStructure;
                            }
                        }
                    }
                }
            } else {
                toLog.add("OrgStructure is not found # by actionParentId [#]", action, action.getParentActionId());
            }
        } catch (CoreException e) {
        }
        throw new SkipMessageProcessException();
    }

    /**
     * Поиск OrgStructure для конкретного Action
     */
    private OrgStructure getOrgStructureIn(final Action action, final ToLog toLog) throws SkipMessageProcessException {
        try {
            final Set<String> codes = new HashSet<String>();
            codes.add("orgStructStay");

            final Map<ActionProperty, List<APValue>> names
                    = dbActionPropertyBeanLocal.getActionPropertiesByActionIdAndActionPropertyTypeCodesWithoutDel(action.getId(), codes);
            toLog.add("getOrgStructureIn Action properties and type #", names);

            for (ActionProperty property : names.keySet()) {
                final List<APValue> apValues = names.get(property);
                for (APValue apValue : apValues) {
                    if (apValue instanceof APValueOrgStructure) {
                        final OrgStructure orgStructure = (OrgStructure) apValue.getValue();
                        toLog.add("orgStructureOut [#], [#]", orgStructure.getId(), orgStructure.getName());
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
    private OrgStructure getOrgStructureInWithDel(final Action action, final ToLog toLog) throws SkipMessageProcessException {
        try {
            if (action.getParentActionId() != 0) {
                toLog.add("try recursive call # by actionParentId [#]", action, action.getParentActionId());
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
                                final OrgStructure orgStructure = (OrgStructure) apValue.getValue();
                                toLog.add("orgStructureIn [#], [#]", orgStructure.getId(), orgStructure.getName());
                                return orgStructure;
                            }
                        }
                    }
                } else {
                    toLog.add("OrgStructure is not found # by actionParentId [#]", action, action.getParentActionId());
                }
            }
        } catch (CoreException e) {
        }
        throw new SkipMessageProcessException();
    }

    /**
     * Поиск OrgStructure для конкретного Action
     */
    private OrgStructure getReceivedOrgStructure(final Action action, final ToLog toLog) throws SkipMessageProcessException {
        try {
            final Set<String> codesSet = new HashSet<String>();
            codesSet.add("orgStructStay");

            final Map<ActionProperty, List<APValue>> names
                    = dbActionPropertyBeanLocal.getActionPropertiesByActionIdAndActionPropertyTypeCodesWithoutDel(action.getId(), codesSet);
            for (ActionProperty property : names.keySet()) {
                final List<APValue> apValues = names.get(property);
                for (APValue apValue : apValues) {
                    if (apValue instanceof APValueOrgStructure) {
                        final OrgStructure orgStructure = (OrgStructure) apValue.getValue();
                        toLog.add("orgStructure [#], [#]", orgStructure.getId(), orgStructure.getName());
                        return orgStructure;
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
            ToLog toLog = new ToLog("PRESCRIPTION");
            try {
                toLog.add("found #", prescription);

                int errCount = prescription.getErrCount();
                long step = 89 * 1000; // время до слейдующей попытки передачи данных
                prescription.setErrCount(errCount + 1);
                prescription.setSendTime(new Timestamp((new java.util.Date()).getTime() + (long) (errCount) * step));

                if (sendPrescription(prescription, toLog)) {
                    toLog.add("remove prescription item [#]", prescription.getIntervalId());
                    dbPrescriptionsTo1CBeanLocal.remove(prescription);
                }
            } catch (Exception e) {
                toLog.add("Exception: " + e);
                logger.error("Exception: " + e, e);
            } finally {
                logger.info(toLog.releaseString());
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
    private boolean sendPrescription(PrescriptionsTo1C prescription, ToLog toLog) throws CoreException {
        boolean res = false;
        try {
            final Action action = prescription.getDrugChart().getAction();
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

            final Map<ActionProperty, List<APValue>> actionProp = dbActionProperty.getActionPropertiesByActionIdAndTypeCodes(action.getId(), Arrays.asList(code));
            if (!actionProp.isEmpty() && !actionProp.entrySet().iterator().next().getValue().isEmpty()) {
                Object codeId = actionProp.entrySet().iterator().next().getValue().iterator().next().getValue();
                if (codeId instanceof Integer) {
                    routeOfAdministration = dbRbMethodOfAdministrationLocal.getById((Integer) codeId).getCode();
                }
            }
            Request request = null;

            for (DrugComponent comp : drugComponents) {
                RlsNomen rlsNomen = comp.getNomen();
                PrescriptionSendingRes prescriptionSendingResBean = dbPrescriptionSendingResBean.getPrescriptionSendingRes(prescription.getDrugChart(), comp);
                if (prescription.isPrescription()) { // передача нового / отмена назначения
                    request = HL7PacketBuilder.processPrescription(
                            prescription.getDrugChart(),
                            comp,
                            routeOfAdministration,
                            organisation,
                            AssignmentType.ASSIGNMENT,
                            prescription.getNewStatus() == PS_CANCELED,
                            prescriptionSendingResBean,
                            toLog);
                } else if (prescription.getOldStatus() == PS_NEW && prescription.getNewStatus() == PS_FINISHED) {// если статус изменился с "Назначен" на "Исполнен", то передаем исполнение
                    request = HL7PacketBuilder.processPrescription(
                            prescription.getDrugChart(),
                            comp,
                            routeOfAdministration,
                            organisation,
                            AssignmentType.EXECUTION,
                            false,
                            prescriptionSendingResBean,
                            toLog);
                } else if (prescription.getOldStatus() == PS_FINISHED && prescription.getNewStatus() == PS_NEW) { // если статус изменился с "Исполнен" на "Назначен" , то передаем отмену исполнения
                    request = HL7PacketBuilder.processPrescription(
                            prescription.getDrugChart(),
                            comp,
                            routeOfAdministration,
                            organisation,
                            AssignmentType.EXECUTION,
                            true,
                            prescriptionSendingResBean,
                            toLog);
                }
                if (request != null) {

                    toLog.add("prepare message... \n\n #", HL7PacketBuilder.marshallMessage(request, "misexchange"));
                    final MCCIIN000002UV012 result = new MISExchange().getMISExchangeSoap().processHL7V3Message(request);
                    toLog.add("Connection successful. Result: # \n\n #",
                            result, HL7PacketBuilder.marshallMessage(result, "org.hl7.v3"));

                    if (isOk(result)) {
                        prescriptionSendingResBean.setUuid(result.getAcknowledgement().iterator().next().getTargetMessage().getId().getRoot());
                        prescriptionSendingResBean.setVersion(prescriptionSendingResBean.getVersion() == null ? 1 : (prescriptionSendingResBean.getVersion() + 1));
                        res = true;
                    }
                }
            }

        } catch (Exception e) {
            final String errorString = e.toString();
            prescription.setInfo(errorString);
            logger.error("sending prescription to 1C issue: ", e);
            toLog.add("Exception: " + e);
        }
        return res;
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
