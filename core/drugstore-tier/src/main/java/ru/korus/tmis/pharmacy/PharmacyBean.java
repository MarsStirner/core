package ru.korus.tmis.pharmacy;

import org.hl7.v3.AcknowledgementType;
import org.hl7.v3.MCCIIN000002UV01;
import org.hl7.v3.MCCIMT000200UV01Acknowledgement;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.DbActionBeanLocal;
import ru.korus.tmis.core.database.DbActionPropertyBeanLocal;
import ru.korus.tmis.core.database.DbCustomQueryLocal;
import ru.korus.tmis.core.database.DbOrgStructureBeanLocal;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.entity.model.pharmacy.Pharmacy;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.hl7db.DbPharmacyBeanLocal;
import ru.korus.tmis.core.hl7db.DbUUIDBeanLocal;
import ru.korus.tmis.core.entity.model.pharmacy.PharmacyStatus;
import ru.korus.tmis.core.logging.LoggingInterceptor;
import ru.korus.tmis.pharmacy.exception.SoapConnectionException;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import java.util.Date;
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

    static final Logger logger = LoggerFactory.getLogger(PharmacyBean.class);
    public static final int LAST_ACTIONS = 10;

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
    @Schedule(minute = "*/2", hour = "*")
    public void pooling() {
        logger.info("pooling...last date update {}", getLastDate());
//        logger.error("fake error", new Exception("ee"));
        try {
            if (lastDateUpdate == null) {

                final List<Action> actionList = dbPharmacy.getLastMaxAction(LAST_ACTIONS);
                if (!actionList.isEmpty()) {
                    logger.info("Pooling db, fetch last {} actions. Size [{}]", LAST_ACTIONS, actionList.size());

                    for (Action action : actionList) {
                        final DateTime actionDateTime = new DateTime(action.getCreateDatetime());

                        Pharmacy checkPharmacy = dbPharmacy.getPharmacyByAction(action);
                        logger.info("check pharmacy [{}] action [{}], date [{}], flatCode [{}]",
                                checkPharmacy,
                                action,
                                actionDateTime.toString("yyyy-MM-dd hh:mm:ss"),
                                action.getActionType().getFlatCode());

                        if (checkPharmacy != null) {
                            if (!checkPharmacy.getStatus().equals(PharmacyStatus.COMPLETE.toString())) {
                                // повторная отправка в 1с
                                logger.info("...repeate send message Pharmacy {}", checkPharmacy);
                                checkMessageAndSend(action);
                            }
                        } else {
                            checkMessageAndSend(action);
                        }
                        lastDateUpdate = updateLastDate(actionDateTime, lastDateUpdate);
                    }
                } else {
                    lastDateUpdate = DateTime.now();
                    logger.info("last date update {}", getLastDate());
                }
            }

            final List<Action> actionList = dbPharmacy.getActionAfterDate(lastDateUpdate);
            logger.info("Found {} newest actions after date {}", actionList.size(), getLastDate());

            for (Action action : actionList) {
                lastDateUpdate = checkMessageAndSend(action);
            }
            logger.info("Update last date, new value {}", getLastDate());


        } catch (Throwable e) {
            e.printStackTrace();
            logger.error("exception e: " + e, e);
        }
    }

    private String getLastDate() {
        return lastDateUpdate != null ? lastDateUpdate.toString("yyyy-MM-dd hh:mm:ss") : "null";
    }

    /**
     * Проверка action на присутствие flatCode и обработка если это сообщение по движению пациентов
     *
     * @param action событие
     * @return время последнего обработанного сообщения
     */
    private DateTime checkMessageAndSend(final Action action) {
        logger.info("check message...");
        try {
            final ActionType actionType = action.getActionType();
            if (actionType.getFlatCode().equals(FlatCode.RECEIVED.getCode())) {
                // госпитализация в стационар
                logger.info("--- found received ---");

                final OrgStructure orgStructure = getOrgStructure(action);
                final Pharmacy pharmacy = dbPharmacy.getOrCreate(action);

                processResult(pharmacy, HL7PacketBuilder.processReceived(action, orgStructure));

            } else if (actionType.getFlatCode().equals(FlatCode.LEAVED.getCode())) {
                // выписка из стационара
                logger.info("--- found leaved ---");

                final Pharmacy pharmacy = dbPharmacy.getOrCreate(action);
                final String displayName = "Стационар";

                processResult(pharmacy, HL7PacketBuilder.processLeaved(action, displayName));

            } else if (actionType.getFlatCode().equals(FlatCode.DEL_RECEIVED.getCode())) {
                // отмена сообщения о госпитализации
                logger.info("--- found del_received ---");

                final Pharmacy pharmacy = dbPharmacy.getOrCreate(action);

                processResult(pharmacy, HL7PacketBuilder.processDelReceived(action));

            } else if (actionType.getFlatCode().equals(FlatCode.MOVING.getCode())) {
                // перевод пациента между отделениями
                logger.info("--- found moving ---");

                final OrgStructure orgStructureOut = getOrgStructure(action);
                final OrgStructure orgStructureIn = getOrgStructure(action);
                final Pharmacy pharmacy = dbPharmacy.getOrCreate(action);

                processResult(pharmacy, HL7PacketBuilder.processMoving(action, orgStructureOut, orgStructureIn));

            } else if (actionType.getFlatCode().equals(FlatCode.DEL_MOVING.getCode())) {
                // отмена сообщения о переводе пациента между отделениями
                logger.info("--- found del_moving ---");

                final OrgStructure orgStructureOut = getOrgStructure(action);
                final OrgStructure orgStructureIn = getOrgStructure(action);
                final Pharmacy pharmacy = dbPharmacy.getOrCreate(action);

                processResult(pharmacy, HL7PacketBuilder.processDelMoving(action, orgStructureOut, orgStructureIn));

            } else {
                logger.info("--- actionType.flatCode is not found. Skip ---");
            }

            // фильтруем все назначения
            if (actionType.getTypeClass() == 1 && actionType.getCode().equals("3_1_05")) {
                logger.info("--- found clinical document (code:{}) ---", actionType.getCode());

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
        return new DateTime(action.getCreateDatetime());
    }

    /**
     * Поиск OrgStructure для конкретного Action
     *
     * @param action
     * @return
     * @throws CoreException
     */
    private OrgStructure getOrgStructure(final Action action) throws CoreException {
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
        throw new CoreException("OrgStructure for " + action + " is not found");
    }

    /**
     * Обработка результата от 1С
     */
    private void processResult(final Pharmacy pharmacy, final MCCIIN000002UV01 result) throws CoreException {
        if (result != null && !result.getAcknowledgement().isEmpty()) {
            final MCCIMT000200UV01Acknowledgement ack = result.getAcknowledgement().get(0);
            pharmacy.setDocumentUUID(ack.getTargetMessage().getId().getRoot());
            pharmacy.setResult(ack.getTypeCode().value());
            if (ack.getTypeCode().equals(AcknowledgementType.AA)) {
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
