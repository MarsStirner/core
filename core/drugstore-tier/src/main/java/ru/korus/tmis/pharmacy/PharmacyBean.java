package ru.korus.tmis.pharmacy;

import org.hl7.v3.AcknowledgementType;
import org.hl7.v3.MCCIIN000002UV01;
import org.hl7.v3.MCCIMT000200UV01Acknowledgement;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.DbActionBeanLocal;
import ru.korus.tmis.core.database.DbActionPropertyBeanLocal;
import ru.korus.tmis.core.database.DbCustomQueryLocal;
import ru.korus.tmis.core.database.DbOrgStructureBeanLocal;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.entity.model.pharmacy.Pharmacy;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.pharmacy.DbPharmacyBeanLocal;
import ru.korus.tmis.core.pharmacy.DbUUIDBeanLocal;
import ru.korus.tmis.core.entity.model.pharmacy.PharmacyStatus;
import ru.korus.tmis.core.pharmacy.FlatCode;
import ru.korus.tmis.core.logging.LoggingInterceptor;
import ru.korus.tmis.pharmacy.exception.NoSuchOrgStructureException;
import ru.korus.tmis.pharmacy.exception.SoapConnectionException;
import ru.korus.tmis.util.logs.ToLog;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
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

    private DateTime lastDateUpdate = null;

    /**
     * Полинг базы данных для поиска событий по движениям пациентов
     */
    @Override
    @Schedule(minute = "*/1", hour = "*")
    public void pooling() {
        logger.info("");
        logger.info("pooling... last modify date {}", getLastDate());
//        logger.error("fake error", new Exception("ee"));
        try {
            if (lastDateUpdate == null) {
                lastDateUpdate = firstPolling();
            }

            final List<Action> actionAfterDate = dbPharmacy.getVirtualActionsAfterDate(lastDateUpdate);
            if (!actionAfterDate.isEmpty()) {
                logger.info("Found {} newest actions after date {}", actionAfterDate.size(), getLastDate());
                for (Action action : actionAfterDate) {
                    lastDateUpdate = checkMessageAndSend(action, lastDateUpdate);
                }
            }
        } catch (Throwable e) {
            logger.error("Exception e: " + e, e);
        }
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
                    final DateTime modifyDateTime = new DateTime(action.getModifyDatetime());

                    final Pharmacy checkPharmacy = dbPharmacy.getPharmacyByAction(action);
                    toLog.add("Found [" + checkPharmacy + "], flatCode [" + action.getActionType().getFlatCode() + "]");

                    if (checkPharmacy != null) {
                        if (!PharmacyStatus.COMPLETE.toString().equals(checkPharmacy.getStatus())) {
                            // повторная отправка в 1с
                            toLog.add("...repeate send message");
                            lastDateUpdate = checkMessageAndSend(action, modifyDateTime);
                        }
                    } else {
                        lastDateUpdate = checkMessageAndSend(action, modifyDateTime);
                    }
                    lastDateUpdate = updateLastDate(modifyDateTime, lastDateUpdate);
                }
            }
        } finally {
            logger.info(toLog.releaseString());
        }
        return modifyDate;
    }

    private String getLastDate() {
        return lastDateUpdate != null ? lastDateUpdate.toString(DATE_TIME_FORMAT) : "null";
    }

    /**
     * Проверка action на присутствие flatCode и обработка если это сообщение по движению пациентов
     *
     * @param action         событие
     * @param lastDateModify
     * @return время последнего обработанного сообщения
     */
    private DateTime checkMessageAndSend(final Action action, final DateTime lastDateModify) {
        logger.info("");
        try {
            final ActionType actionType = action.getActionType();
            if (FlatCode.RECEIVED.getCode().equals(actionType.getFlatCode())) {
                // госпитализация в стационар
                logger.info("--- found received ---");
                try {
                    final OrgStructure orgStructure = getOrgStructure(action);
                    final Pharmacy pharmacy = dbPharmacy.getOrCreate(action);

                    processResult(pharmacy, HL7PacketBuilder.processReceived(action, orgStructure));
                } catch (NoSuchOrgStructureException e) {
                    logger.info("OrgStructure not found");
                }

            } else if (FlatCode.LEAVED.getCode().equals(actionType.getFlatCode())) {
                // выписка из стационара
                logger.info("--- found leaved ---");

                final Pharmacy pharmacy = dbPharmacy.getOrCreate(action);
                final String displayName = "Стационар";

                processResult(pharmacy, HL7PacketBuilder.processLeaved(action, displayName));

            } else if (FlatCode.DEL_RECEIVED.getCode().equals(actionType.getFlatCode())) {
                // отмена сообщения о госпитализации
                logger.info("--- found del_received ---");

                final Pharmacy pharmacy = dbPharmacy.getOrCreate(action);

                processResult(pharmacy, HL7PacketBuilder.processDelReceived(action));

            } else if (FlatCode.MOVING.getCode().equals(actionType.getFlatCode())) {
                // перевод пациента между отделениями
                logger.info("--- found moving ---");
                try {
                    final OrgStructure orgStructureOut = getOrgStructure(action);
                    final OrgStructure orgStructureIn = getOrgStructure(action);
                    final Pharmacy pharmacy = dbPharmacy.getOrCreate(action);

                    processResult(pharmacy, HL7PacketBuilder.processMoving(action, orgStructureOut, orgStructureIn));
                } catch (NoSuchOrgStructureException e) {
                    logger.info("OrgStructure not found");
                }
            } else if (FlatCode.DEL_MOVING.getCode().equals(actionType.getFlatCode())) {
                // отмена сообщения о переводе пациента между отделениями
                logger.info("--- found del_moving ---");
                try {
                    final OrgStructure orgStructureOut = getOrgStructure(action);
                    final OrgStructure orgStructureIn = getOrgStructure(action);
                    final Pharmacy pharmacy = dbPharmacy.getOrCreate(action);

                    processResult(pharmacy, HL7PacketBuilder.processDelMoving(action, orgStructureOut, orgStructureIn));
                } catch (NoSuchOrgStructureException e) {
                    logger.info("OrgStructure not found");
                }
            } else {
                logger.info("flatCode not found, skip...");
            }

            // фильтруем все назначения
            if (actionType.getTypeClass() == 1 && actionType.getCode().equals("3_1_05")) {
                logger.info("--- found clinical document (code:{}) ---", actionType.getCode());
                try {
                    final Pharmacy pharmacy = dbPharmacy.getOrCreate(action);
                    final Patient client = action.getEvent().getPatient();
                    final Staff createPerson = action.getCreatePerson();
                    final String externalId = java.util.UUID.randomUUID().toString(); //dbUUIDBeanLocal.getUUIDById(event.getUUID());
                    final String externalUUID = java.util.UUID.randomUUID().toString(); //dbUUIDBeanLocal.getUUIDById(event.getUUID());
                    final String custodianUUID = java.util.UUID.randomUUID().toString(); //dbUUIDBeanLocal.getUUIDById(event.getUUID());
                    final String uuidClient = java.util.UUID.randomUUID().toString(); //dbUUIDBeanLocal.getUUIDById(event.getUUID());
                    final OrgStructure orgStructure = getOrgStructure(action);
                    final String organizationName = orgStructure.getName();
                    final Staff doctorPerson = new Staff(11);

                    processResult(pharmacy,
                            HL7PacketBuilder.processRCMRIN000002UV02(
                                    action,
                                    uuidClient,
                                    externalId,
                                    client,
                                    createPerson,
                                    organizationName,
                                    externalUUID,
                                    custodianUUID,
                                    doctorPerson));
                } catch (NoSuchOrgStructureException e) {
                    logger.info("OrgStructure not found");
                }
            }

        } catch (CoreException e) {
            try {    // todo переделать !!!!!!!
                final Pharmacy pharmacy = dbPharmacy.getOrCreate(action);
                pharmacy.setStatus(PharmacyStatus.ERROR);
                dbPharmacy.updateMessage(pharmacy);
                logger.info("update error status {}", pharmacy);
            } catch (Exception e1) {
                logger.error("core error " + e1, e1);
            }
            logger.error("core error " + e, e);
        } catch (SoapConnectionException e) {
            logger.error("core error " + e, e);
        }
        return lastDateModify;
    }

    /**
     * Поиск OrgStructure для конкретного Action
     *
     * @param action
     * @return
     * @throws CoreException
     */
    private OrgStructure getOrgStructure(final Action action) throws NoSuchOrgStructureException {
        try {
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
        throw new NoSuchOrgStructureException("OrgStructure for " + action + " is not found");
    }

    /**
     * Обработка результата от 1С
     */
    private void processResult(final Pharmacy pharmacy, final MCCIIN000002UV01 result) throws CoreException {
        if (result != null && !result.getAcknowledgement().isEmpty()) {
            final MCCIMT000200UV01Acknowledgement ack = result.getAcknowledgement().get(0);
            pharmacy.setDocumentUUID(ack.getTargetMessage().getId().getRoot());
            pharmacy.setResult(ack.getTypeCode().value());
            if (AcknowledgementType.AA.equals(ack.getTypeCode())) {
                pharmacy.setStatus(PharmacyStatus.COMPLETE);
            } else {
                pharmacy.setStatus(PharmacyStatus.ERROR);
            }
        }
        logger.info("---update message {} ", pharmacy);
        dbPharmacy.updateMessage(pharmacy);
    }


    private DateTime updateLastDate(final DateTime date, final DateTime lastDate) {
        if (lastDate == null) {
            return date;
        }
        return date.toDate().before(lastDate.toDate()) ? lastDate : date;
    }

}
