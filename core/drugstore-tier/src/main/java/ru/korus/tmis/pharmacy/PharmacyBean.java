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
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.logging.LoggingInterceptor;
import ru.korus.tmis.core.pharmacy.DbPharmacyBeanLocal;
import ru.korus.tmis.core.pharmacy.DbUUIDBeanLocal;
import ru.korus.tmis.core.pharmacy.FlatCode;
import ru.korus.tmis.pharmacy.exception.MessageProcessException;
import ru.korus.tmis.pharmacy.exception.NoSuchOrgStructureException;
import ru.korus.tmis.pharmacy.exception.SkipMessageProcessException;
import ru.korus.tmis.util.logs.ToLog;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        29.10.12, 17:30 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Работа по отправке сообщений в сторону 1С Аптеки<br>
 */
@Interceptors(LoggingInterceptor.class)
@Stateless
public class PharmacyBean implements PharmacyBeanLocal {

    private static final Logger logger = LoggerFactory.getLogger(PharmacyBean.class);

    private static final int LAST_ACTIONS = 10;

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd hh:mm:ss";

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

    private DateTime lastDateUpdate = null;

    /**
     * Выгрузка данных по назначениям за текущие сутки
     */
    @Override
    @Schedule(second = "59", minute = "59", hour = "23")
    public void flushAssignment() {
        logger.info("");
        logger.info("Begin flush assignment for today...");
        for (Action action : dbPharmacy.getAssignmentForToday(DateTime.now())) {
            try {
                final ActionType actionType = action.getActionType();
                if (FlatCode.PRESCRIPTION.getCode().equalsIgnoreCase(actionType.getFlatCode())) {
                    send(action);
                }
            } catch (Exception e) {
                logger.error("Error");
            }
        }
        logger.info("End flush assignment for today...");

        // просмотреть назначения на сегодня и отправить исполненное
        logger.info("Begin flush release assignment for today...");
        for (Action action : dbPharmacy.getAssignmentForToday(DateTime.now())) {

        }
        logger.info("End flush release assignment for today...");


    }

    /**
     * Полинг базы данных для поиска событий по движениям пациентов
     */
    @Override
    @Schedule(minute = "*/1", hour = "*")
    public void pooling() {
        logger.info("pooling... last modify date {}", getLastDate());
        try {
            if (lastDateUpdate == null) {
                lastDateUpdate = firstPolling();
            }

            final List<Action> actionAfterDate = dbPharmacy.getVirtualActionsAfterDate(lastDateUpdate);
            if (!actionAfterDate.isEmpty()) {
                logger.info("Found {} newest actions after date {}", actionAfterDate.size(), getLastDate());
                for (Action action : actionAfterDate) {
                    if (isMovingAction(action)) {
                        send(action);
                        lastDateUpdate = new DateTime(action.getModifyDatetime());
                    }
                }
            }
            resendMessages();
        } catch (Exception e) {
            logger.error("Exception e: " + e, e);
        }
    }

    /**
     * Повторная отправка сообщений, которые имеют статус отличный от COMPETE
     *
     * @throws CoreException
     */
    private void resendMessages() throws CoreException {
        // resend old messages
        final List<Pharmacy> nonCompletedItems = dbPharmacy.getNonCompletedItems();
        if (!nonCompletedItems.isEmpty()) {
            logger.info("Resend old message....");
            for (Pharmacy pharmacy : nonCompletedItems) {
                final Action action = dbAction.getActionById(pharmacy.getActionId());
                send(action);
            }
        }
    }

    /**
     * Отправка события в 1С Аптеку по событию Action
     */
    private void send(final Action action) {
        Pharmacy pharmacy = null;
        try {
            pharmacy = dbPharmacy.getOrCreate(action);
            final Request request = createRequest(action);

            logger.info("prepare message... \n\n {}", HL7PacketBuilder.marshallMessage(request, "misexchange"));
            final MCCIIN000002UV01 result = new MISExchange().getMISExchangeSoap().processHL7V3Message(request);
            if (result != null) {
                logger.info("Connection successful. Result: {} \n\n {}",
                        result, HL7PacketBuilder.marshallMessage(result, "org.hl7.v3"));

                for (MCCIMT000200UV01Acknowledgement ack : result.getAcknowledgement()) {
                    pharmacy.setDocumentUUID(ack.getTargetMessage().getId().getRoot());
                    pharmacy.setResult(ack.getTypeCode().value());
                    if (AcknowledgementType.AA.equals(ack.getTypeCode())) {
                        pharmacy.setStatus(PharmacyStatus.COMPLETE);
                    } else {
                        final StringBuilder sb = new StringBuilder();
                        final List<MCCIMT000200UV01AcknowledgementDetail> detailList = ack.getAcknowledgementDetail();
                        for (MCCIMT000200UV01AcknowledgementDetail detail : detailList) {
                            final ED text = detail.getText();
                            for (Object o : text.getContent()) {
                                sb.append(o);
                            }
                        }
                        pharmacy.setErrorString(sb.toString());
                        pharmacy.setStatus(PharmacyStatus.ERROR);
                    }
                }
            } else {
                logger.error("Error connection with 1C Pharmacy. Message is setup ERROR status");
                pharmacy.setStatus(PharmacyStatus.ERROR);
            }
        } catch (NoSuchOrgStructureException e) {
            if (pharmacy != null) {
                pharmacy.setStatus(PharmacyStatus.ERROR);
            }
            logger.info("OrgStructure not found. Skip message: " + e);
        } catch (SkipMessageProcessException e) {
            if (pharmacy != null) {
                pharmacy.setStatus(PharmacyStatus.ERROR);
            }
            logger.info("Skip message: " + e);
        } catch (Exception e) {
            if (pharmacy != null) {
                pharmacy.setStatus(PharmacyStatus.ERROR);
            }
            logger.error("Exception: " + e, e);
        } finally {
            if (pharmacy != null) {
                try {
                    dbPharmacy.updateMessage(pharmacy);
                } catch (CoreException ce) {
                    logger.error("CoreException on set Pharmacy in error! " + ce, ce);
                }
            }
        }
    }

    /**
     * Проверка на код по движению пациентов
     */
    private boolean isMovingAction(final Action action) {
        final ActionType actionType = action.getActionType();
        return FlatCode.RECEIVED.getCode().equalsIgnoreCase(actionType.getFlatCode())
                || FlatCode.DEL_RECEIVED.getCode().equalsIgnoreCase(actionType.getFlatCode())
                || FlatCode.MOVING.getCode().equalsIgnoreCase(actionType.getFlatCode())
                || FlatCode.DEL_MOVING.getCode().equalsIgnoreCase(actionType.getFlatCode())
                || FlatCode.LEAVED.getCode().equalsIgnoreCase(actionType.getFlatCode());
    }

    /**
     * Создание объектной модели сообщения для 1С Аптеки
     *
     * @param action событие на основе которого отправляется сообщение в 1С Аптеку
     * @return возвращает класс готовый к отправке в 1С Аптеку
     * @throws MessageProcessException проблемы при создании сообщения
     */
    public Request createRequest(final Action action)
            throws MessageProcessException, SkipMessageProcessException, NoSuchOrgStructureException {

        final ActionType actionType = action.getActionType();

        if (FlatCode.RECEIVED.getCode().equalsIgnoreCase(actionType.getFlatCode())) {
            // поступление в стационар
            return HL7PacketBuilder.processReceived(action, getOrgStructure(action));
        } else if (FlatCode.DEL_RECEIVED.getCode().equalsIgnoreCase(actionType.getFlatCode())) {
            return HL7PacketBuilder.processDelReceived(action);
        } else if (FlatCode.MOVING.getCode().equalsIgnoreCase(actionType.getFlatCode())) {

            final OrgStructure orgStructureOut = getOrgStructureOut(action);
            final OrgStructure orgStructureIn = getOrgStructureIn(action);

            return HL7PacketBuilder.processMoving(action, orgStructureOut, orgStructureIn);
        } else if (FlatCode.DEL_MOVING.getCode().equalsIgnoreCase(actionType.getFlatCode())) {

            final OrgStructure orgStructureOut = getOrgStructureOut(action);
            final OrgStructure orgStructureIn = getOrgStructureIn(action);

            return HL7PacketBuilder.processDelMoving(action, orgStructureOut, orgStructureIn);
        } else if (FlatCode.LEAVED.getCode().equalsIgnoreCase(actionType.getFlatCode())) {
            return HL7PacketBuilder.processLeaved(action);
        } else if (FlatCode.PRESCRIPTION.getCode().equalsIgnoreCase(actionType.getFlatCode())) {
            // Пациент
            final Patient client = action.getEvent().getPatient();
            // Врач, сделавший обращение
            final Staff executorStaff = action.getExecutor();
            // Организация, которой принадлежит документ
            final Organisation organisation = getCustodianOrgStructure(action);
            // Определяем код назначенного препарата
            final String drugCode = dbPharmacy.getDrugCode(action);

            return HL7PacketBuilder.processPrescription(
                    action, client, executorStaff, organisation, drugCode, AssignmentType.ASSIGNMENT);
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
                        send(action);
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
        try {
            final Event event = action.getEvent();
            return dbOrganizationBeanLocal.getOrganizationById(event.getOrgId());
        } catch (CoreException e) {
            throw new NoSuchOrgStructureException(e);
        }
    }

    /**
     * Поиск OrgStructure для конкретного Action
     */
    private OrgStructure getOrgStructureOut(final Action action) throws SkipMessageProcessException {
        try {
            final Map<ActionProperty, List<APValue>> names
                    = dbActionPropertyBeanLocal.getActionPropertiesByActionIdAndTypeNames(
                    action.getId(), Arrays.asList("Отделение пребывания"));
            logger.info("getOrgStructureOut Action properties and type {}", names);
            for (ActionProperty property : names.keySet()) {
                final List<APValue> apValues = names.get(property);
                for (APValue apValue : apValues) {
                    if (apValue instanceof APValueOrgStructure) {
                        final OrgStructure orgStructure = (OrgStructure) apValue.getValue();
                        logger.info("Found OrgStructureOut: {}, {}", orgStructure, orgStructure.getName());
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
    private OrgStructure getOrgStructureIn(final Action action) throws SkipMessageProcessException {
        try {
            final Map<ActionProperty, List<APValue>> names
                    = dbActionPropertyBeanLocal.getActionPropertiesByActionIdAndTypeNames(
                    action.getId(), Arrays.asList("Переведен в отделение"));
            logger.info("getOrgStructureIn Action properties and type {}", names);
            for (ActionProperty property : names.keySet()) {
                final List<APValue> apValues = names.get(property);
                for (APValue apValue : apValues) {
                    if (apValue instanceof APValueOrgStructure) {
                        final OrgStructure orgStructure = (OrgStructure) apValue.getValue();
                        logger.info("Found OrgStructureIn: {}, {}", orgStructure, orgStructure.getName());
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
    private OrgStructure getOrgStructure(final Action action) throws SkipMessageProcessException {
        try {
            final Map<ActionProperty, List<APValue>> names
                    = dbActionPropertyBeanLocal.getActionPropertiesByActionIdAndTypeNames(
                    action.getId(), Arrays.asList("Переведен в отделение", "Переведен из отделения", "Отделение пребывания"));
            logger.info("Action properties and type {}", names);


            final Map<ActionProperty, List<APValue>> actionPropertiesMap = dbActionPropertyBeanLocal.getActionPropertiesByActionId(action.getId());
            for (ActionProperty property : actionPropertiesMap.keySet()) {
                final List<APValue> apValues = actionPropertiesMap.get(property);
                for (APValue apValue : apValues) {
                    if (apValue instanceof APValueOrgStructure) {
                        logger.info("Found OrgStructure property: {}, apvalue: {}, value: {}", property, apValue, apValue.getValue());
                        return (OrgStructure) apValue.getValue();
                    }
                }
            }
            if (action.getParentActionId() != 0) {
                logger.info("try recursive call {} by actionParentId [{}]", action, action.getParentActionId());
                final Action parentAction = dbAction.getActionByIdWithIgnoreDeleted(action.getParentActionId());
                if (parentAction != null) {
                    return getOrgStructure(parentAction);
                }
            } else {
                logger.info("OrgStructure is not found {} by actionParentId [{}]", action, action.getParentActionId());
            }
//            throw new NoSuchOrgStructureException("OrgStructure for " + action + " is not found");
        } catch (CoreException e) {
            // skip
        }
        throw new SkipMessageProcessException("OrgStructure for " + action + " is not found");
    }


}
