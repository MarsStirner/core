package ru.korus.tmis.pharmacy;

import org.hl7.v3.AcknowledgementType;
import org.hl7.v3.MCCIIN000002UV01;
import org.hl7.v3.MCCIMT000200UV01Acknowledgement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.DbActionBeanLocal;
import ru.korus.tmis.core.database.DbActionPropertyBeanLocal;
import ru.korus.tmis.core.database.DbOrgStructureBeanLocal;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.entity.model.hl7.Pharmacy;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.hl7db.DbPharmacyBeanLocal;
import ru.korus.tmis.core.hl7db.DbUUIDBeanLocal;
import ru.korus.tmis.core.hl7db.PharmacyStatus;
import ru.korus.tmis.core.logging.LoggingInterceptor;
import ru.korus.tmis.pharmacy.exception.SoapConnectionException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import java.util.Date;
import java.util.List;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        29.10.12, 17:30 <br>
 * Company:     Korus Consulting IT<br>
 * Revision:    \$Id$ <br>
 * Description: <br>
 */
@Interceptors(LoggingInterceptor.class)
@Stateless
public class PharmacyBean implements PharmacyBeanLocal {

    final static Logger logger = LoggerFactory.getLogger(PharmacyBean.class);
    public static final int LAST_ACTIONS = 10;

    @EJB
    private DbActionBeanLocal dbAction = null;

    @EJB
    private DbOrgStructureBeanLocal dbOrgStructureBeanLocal = null;

    @EJB
    private DbActionPropertyBeanLocal dbActionProperty = null;

    @EJB
    private DbPharmacyBeanLocal dbPharmacy = null;

    @EJB
    private DbUUIDBeanLocal dbUUIDBeanLocal = null;


    private Date lastDateUpdate = null;


    @PostConstruct
    public void init() {
        logger.info("");
        logger.info("-------------------------------");
        logger.info("====== start {} ======", this.getClass().getName());
        logger.info("-------------------------------");
        logger.info("");
    }

    @PreDestroy
    public void destroy() {
        logger.info("");
        logger.info("-------------------------------");
        logger.info("====== stop {} ======", this.getClass().getName());
        logger.info("-------------------------------");
        logger.info("");
    }

    private Date updateLastDate(Date date, Date lastDate) {
        if (lastDate == null) {
            return date;
        }
        return date.before(lastDate) ? lastDate : date;
    }


    @Override
    @Schedule(minute = "*/2", hour = "*")
    public void pooling() {
        logger.info("pooling...last date update {}", lastDateUpdate);
        try {
            if (lastDateUpdate == null) {

                final List<Action> actionList = dbPharmacy.getLastMaxAction(LAST_ACTIONS);
                if (!actionList.isEmpty()) {
                    logger.info("Pooling db, fetch last {} actions. Size [{}]", LAST_ACTIONS, actionList.size());

                    for (Action action : actionList) {

                        Pharmacy checkPharmacy = dbPharmacy.getPharmacyByAction(action);
                        logger.info("check pharmacy [{}] action [{}], date [{}], flatCode [{}]",
                                checkPharmacy, action, action.getCreateDatetime(), action.getActionType().getFlatCode());
                        if (checkPharmacy != null) {
                            if (!checkPharmacy.getStatus().equals(PharmacyStatus.COMPLETE.toString())) {
                                // повторная отправка в 1с
                                logger.info("repeate send message Pharmacy {}", checkPharmacy);
                                checkMessageAndSend(action);
                            }
                        } else {
                            checkMessageAndSend(action);
                        }
                        lastDateUpdate = updateLastDate(action.getCreateDatetime(), lastDateUpdate);
                    }
                } else {
                    lastDateUpdate = new Date();
                    logger.info("last date update {}", lastDateUpdate);
                }
            }

            final List<Action> actionList = dbPharmacy.getActionAfterDate(lastDateUpdate);
            logger.info("Found {} newest actions after date {}", actionList.size(), lastDateUpdate);

            for (Action action : actionList) {
                lastDateUpdate = checkMessageAndSend(action);
            }
            logger.info("Update last date, new value {}", lastDateUpdate);


        } catch (Throwable e) {
            e.printStackTrace();
            logger.error("exception e" + e, e);
        }
    }

    private Date checkMessageAndSend(Action action) {
        logger.info("------------check message-------------");
        try {
            final ActionType actionType = action.getActionType();
            if (actionType.getFlatCode().equals("received")) {
                // госпитализация в стационар
                logger.info("--- found received ---");
                final Event event = action.getEvent();
                final OrgStructure orgStructure = getOrgStructure(event);
                final Patient client = action.getEvent().getPatient();
                final Pharmacy pharmacy = dbPharmacy.getOrCreate(action);
                final String externalUUID = java.util.UUID.randomUUID().toString();//dbUUIDBeanLocal.getUUIDById(event.getUUID());
                final String orgUUID = java.util.UUID.randomUUID().toString();//dbUUIDBeanLocal.getUUIDById(orgStructure.getUUID());
                final String clientUUID = java.util.UUID.randomUUID().toString();//dbUUIDBeanLocal.getUUIDById(client.getUUID());

                processResult(
                        pharmacy,
                        HL7PacketBuilder.processReceived(
                                action, event.getExternalId(), externalUUID, orgUUID, client, clientUUID));

            } else if (actionType.getFlatCode().equals("leaved")) {
                // выписка из стационара
                logger.info("--- found leaved ---");
                final Event event = action.getEvent();
//                final OrgStructure orgStructure = getOrgStructure(event);
                final Patient client = action.getEvent().getPatient();
                final Pharmacy pharmacy = dbPharmacy.getOrCreate(action);
                final String displayName = "Стационар";
                final String clientUUID = java.util.UUID.randomUUID().toString();

                processResult(
                        pharmacy,
                        HL7PacketBuilder.processLeaved(
                                action, event.getExternalId(), clientUUID, client, displayName));

            } else if (actionType.getFlatCode().equals("del_received")) {
                // отмена сообщения о госпитализации
                logger.info("--- found del_received ---");
                final Event event = action.getEvent();
                final OrgStructure orgStructure = getOrgStructure(event);
                final Patient client = action.getEvent().getPatient();
                final Pharmacy pharmacy = dbPharmacy.getOrCreate(action);
                final String uuidExternalId = java.util.UUID.randomUUID().toString();//dbUUIDBeanLocal.getUUIDById(event.getUUID());
                final String externalId = java.util.UUID.randomUUID().toString();//dbUUIDBeanLocal.getUUIDById(event.getUUID());
                final String uuidClient = java.util.UUID.randomUUID().toString();//dbUUIDBeanLocal.getUUIDById(event.getUUID());

                processResult(
                        pharmacy,
                        HL7PacketBuilder.processDelReceived(
                                action, uuidExternalId, externalId, uuidClient, client));

            } else if (actionType.getFlatCode().equals("moving")) {
                // перевод пациента между отделениями
                logger.info("--- found moving ---");
                final Event event = action.getEvent();
                final OrgStructure orgStructure = getOrgStructure(event);
                final Patient client = action.getEvent().getPatient();
                final Pharmacy pharmacy = dbPharmacy.getOrCreate(action);
                final String uuidExternalId = java.util.UUID.randomUUID().toString();//dbUUIDBeanLocal.getUUIDById(event.getUUID());
                final String externalId = java.util.UUID.randomUUID().toString();//dbUUIDBeanLocal.getUUIDById(event.getUUID());
                final String uuidClient = java.util.UUID.randomUUID().toString();//dbUUIDBeanLocal.getUUIDById(event.getUUID());
                final String uuidLocationOut = java.util.UUID.randomUUID().toString();//dbUUIDBeanLocal.getUUIDById(event.getUUID());
                final String uuidLocationIn = java.util.UUID.randomUUID().toString();//dbUUIDBeanLocal.getUUIDById(event.getUUID());

                processResult(
                        pharmacy,
                        HL7PacketBuilder.processMoving(
                                action, uuidExternalId, externalId, uuidClient, uuidLocationOut, uuidLocationIn));

            } else if (actionType.getFlatCode().equals("del_moving")) {
                // отмена сообщения о переводе пациента между отделениями
                logger.info("--- found del_moving ---");
                final Event event = action.getEvent();
                final OrgStructure orgStructure = getOrgStructure(event);
                final Patient client = action.getEvent().getPatient();
                final Pharmacy pharmacy = dbPharmacy.getOrCreate(action);
                final String uuidExternalId = java.util.UUID.randomUUID().toString();//dbUUIDBeanLocal.getUUIDById(event.getUUID());
                final String externalId = java.util.UUID.randomUUID().toString();//dbUUIDBeanLocal.getUUIDById(event.getUUID());
                final String uuidClient = java.util.UUID.randomUUID().toString();//dbUUIDBeanLocal.getUUIDById(event.getUUID());
                final String uuidLocationOut = java.util.UUID.randomUUID().toString();//dbUUIDBeanLocal.getUUIDById(event.getUUID());
                final String uuidLocationIn = java.util.UUID.randomUUID().toString();//dbUUIDBeanLocal.getUUIDById(event.getUUID());

                processResult(
                        pharmacy,
                        HL7PacketBuilder.processDelMoving(
                                action, uuidExternalId, externalId, uuidClient, uuidLocationOut, uuidLocationIn));

            } else {
                logger.info("--- actionType flatCode is not found. Skip ---");
            }

            // фильтруем все назначения
            if (actionType.getTypeClass() == 1 && actionType.getCode().equals("3_1_05")) {
                logger.info("--- found clinical document (code:{}) ---", actionType.getCode());

                final Pharmacy pharmacy = dbPharmacy.getOrCreate(action);
                final Patient client = action.getEvent().getPatient();
                final Staff createPerson = action.getCreatePerson();
                final String externalId = java.util.UUID.randomUUID().toString();//dbUUIDBeanLocal.getUUIDById(event.getUUID());
                final String uuidClient = java.util.UUID.randomUUID().toString();//dbUUIDBeanLocal.getUUIDById(event.getUUID());
                final OrgStructure orgStructure = getOrgStructure(action.getEvent());
                final String organizationName = orgStructure.getName();

                processResult(pharmacy,
                        HL7PacketBuilder.processRCMRIN000002UV02(
                                action, uuidClient, externalId, client, createPerson, organizationName));
            }

        } catch (CoreException e) {
            logger.error("core error " + e, e);
        } catch (SoapConnectionException e) {
            logger.error("core error " + e, e);
        }
        return action.getCreateDatetime();
    }

    private void processResult(Pharmacy pharmacy, MCCIIN000002UV01 result) throws CoreException {
        if (result != null && !result.getAcknowledgement().isEmpty()) {
            final MCCIMT000200UV01Acknowledgement ack = result.getAcknowledgement().get(0);
            pharmacy.setDocumentUUID(ack.getTargetMessage().getId().getRoot());
            pharmacy.setResult(ack.getTypeCode().value());
            if (ack.getTypeCode().equals(AcknowledgementType.AA)) {
                pharmacy.setStatus(PharmacyStatus.COMPLETE.toString());
            }
        }
        logger.info("---update message {} ", pharmacy);
        dbPharmacy.updateMessage(pharmacy);
    }

    private OrgStructure getOrgStructure(Event event) {
        OrgStructure orgStructure = null;
        try {
            orgStructure = dbOrgStructureBeanLocal.getOrgStructureById(event.getOrgId());
        } catch (CoreException e) {
            orgStructure = new OrgStructure();
            orgStructure.setName("none");
        }
        logger.info("getOrgStructure {}, by Event {}", orgStructure, event);
        return orgStructure;
    }

}
