package ru.bars.open.pacs.multivox.logic;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import ru.bars.open.pacs.multivox.dao.DbConnectorDaoImpl;
import ru.korus.tmis.core.database.DbStaffBeanLocal;
import ru.korus.tmis.core.database.common.DbActionBeanLocal;
import ru.korus.tmis.core.database.common.DbActionPropertyBeanLocal;
import ru.korus.tmis.core.database.common.DbEventBeanLocal;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.entity.model.multivox.DbConnector;

import javax.ejb.*;
import javax.ejb.Schedule;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author: Upatov Egor <br>
 * Date: 26.08.2016, 16:21 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */


@Stateless
public class PacsIntegrationBean {
    private static final Logger log = LoggerFactory.getLogger("PACS");
    private AtomicInteger counter = new AtomicInteger(0);
    @EJB
    private DbStaffBeanLocal dbStaff;

    @EJB
    private DbEventBeanLocal dbEvent;

    @EJB
    private DbActionBeanLocal dbAction;

    @EJB
    private DbConnectorDaoImpl dbConnector;

    @EJB
    private DbActionPropertyBeanLocal dbActionProperty;

    @Schedule(hour = "*", minute = "*/3", second = "57", persistent = false)
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void periodicalPoll() {
        final int requestNumber = counter.incrementAndGet();
        log.info("#{} periodical launch polling for send", requestNumber);
        poll(requestNumber, null);
        log.info("#{} End. Successfuly ", requestNumber);

    }

    public Map<Integer, String> poll(final int requestNumber, final Staff sender) {
        final List<Action> actions = dbAction.getActionsByActionTypFlatCodePrefixAndStatus("multivox_", ActionStatus.STARTED);
        log.debug("#{} founded {} action to send", requestNumber, actions.size());
        final Map<Integer, String> result = new HashMap<>(actions.size());
        for (Action action : actions) {
            result.put(action.getId(), sendAction(action, sender, requestNumber) ? "SENT" : "ERROR");
        }
        log.info("#{} Result is {}", requestNumber, result);
        return result;
    }


    public boolean sendAction(final Action action, final Staff user, final int requestNumber) {
        // "#{requestNumber}-{Action.id}"
        final String logId = String.format("#%d-%d", requestNumber, action.getId());
        final ActionType actionType = action.getActionType();
        if (actionType == null) {
            log.error("{} Action has not ActionType", logId);
            dbAction.setActionNoteAndStatus(action, "Action has not ActionType", ActionStatus.STARTED);
            return false;
        } else if (actionType.getDeleted()) {
            log.warn("{} ActionType[{}] is deleted", logId);
        }
        //TODO constants from CCS
        if (!actionType.getFlatCode().startsWith("multivox_")) {
            log.error("{} ActionType[{}] has wrong flatCode for multivox = \'{}\'", logId, actionType.getId(), actionType.getFlatCode());
            //dbAction.setActionNoteAndStatus(action, "ActionType has wrong flatCode for Multivox", ActionStatus.STARTED);
            return false;
        }
        if (MessageFactory.DicomModality.getByCode(actionType.getMnemonic()) == null) {
            log.error("{} ActionType[{}] has wrong mnemonic for multivox = \'{}\'", logId, actionType.getId(), actionType.getMnemonic());
            dbAction.setActionNoteAndStatus(action, "ActionType has wrong mnemonic for Multivox", ActionStatus.STARTED);
            return false;
        }
        if (action.getPlannedEndDate() == null) {
            log.error("{} Action has empty plannedEndDate", logId);
            dbAction.setActionNoteAndStatus(action, "Action has empty plannedEndDate", ActionStatus.STARTED);
            return false;
        }
        log.info("{} Start sending Action - [{}] \'{}\'", logId, actionType.getId(), actionType.getName());
        final Event event = action.getEvent();
        if (event == null) {
            log.error("{} Action has no Event", logId);
            dbAction.setActionNoteAndStatus(action, "Action has no Event", ActionStatus.STARTED);
            return false;
        }
        final EventType eventType = event.getEventType();
        if (eventType == null) {
            log.error("{} Action->Event[{}] has no EventType", logId, event.getId());
            dbAction.setActionNoteAndStatus(action, String.format("Event[%d] has no EventType", event.getId()), ActionStatus.STARTED);
            return false;
        }
        final boolean isStationaryEvent = dbEvent.isStationaryEvent(eventType);
        log.info("{} Event[{}] - {} [{}]\'{}\'", logId, event.getId(), isStationaryEvent ? "STAT" : "AMB", eventType.getId(), eventType.getName());
        final Patient client = event.getPatient();
        if (client == null) {
            log.error("{} Action->Event[{}] has no Client", logId, event.getId());
            dbAction.setActionNoteAndStatus(action, String.format("Event[%d] has no Client", event.getId()), ActionStatus.STARTED);
            return false;
        }
        log.info("{} Event->Client[{}] \'{}\'", logId, client.getId(), client.getFullName());
        final Staff doctor = action.getAssigner();
        if (doctor == null) {
            log.error("{} Action has no Assigner(setPerson_id)", logId);
            dbAction.setActionNoteAndStatus(action, "Action has no Assigner(setPerson_id)", ActionStatus.STARTED);
            return false;
        }
        log.info("{} Action->Assigner(setPerson_id)[{}] \'{}\'", logId, doctor.getId(), doctor.getFullName());
        final OrgStructure orgStructure = dbEvent.getOrgStructureForEvent(event.getId());
        if (orgStructure == null) {
            log.error("{} Action->Event[{}] has no OrgStructure", logId, event.getId());
            dbAction.setActionNoteAndStatus(action, String.format("Event[%d] has no OrgStructure", event.getId()), ActionStatus.STARTED);
            return false;
        }
        log.info("{} Event->OrgStructure[{}] - \'{}\'", logId, orgStructure.getId(), orgStructure.getName());
        final OrgStructure orgStructureDirection = dbAction.getOrgStructureDirection(action);
        if (orgStructureDirection == null) {
            log.error("{} Action-> has no direction to OrgStructure", logId, action.getId());
            dbAction.setActionNoteAndStatus(action, String.format("Action[%d] has no direction OrgStructure", action.getId()), ActionStatus.STARTED);
            return false;
        }
        log.info(
                "{} Action directed (by executor) to OrgStructure[{}] - \'{}\'", logId, orgStructureDirection.getId(), orgStructureDirection.getName()
        );
        final Document doc = MessageFactory.constructDocument(
                action, event, client, doctor, orgStructure, orgStructureDirection, user, isStationaryEvent
        );
        final String messageAsString = MessageFactory.getDocumentAsString(doc, logId);
        if (StringUtils.isEmpty(messageAsString)) {
            log.error("{} Message is not constructed properly.", logId);
            dbAction.setActionNoteAndStatus(action, "Message is not constructed properly. Contact administration.", ActionStatus.STARTED);
            return false;
        } else {
            log.info("{} Result XML message:\n{}", logId, messageAsString);
        }
        final DbConnector messageForRIS = dbConnector.createMessageForRIS(action.getUuid(), messageAsString);
        if (messageForRIS == null) {
            log.error("{} Cannot create message for RIS (Row for 'DbConnector' table).", logId);
            dbAction.setActionNoteAndStatus(action, "Cannot create message for RIS (Row for \'DbConnector\' table)", ActionStatus.STARTED);
            return false;
        }
        dbConnector.persist(messageForRIS);
        setActionSendProperties(action, new Date());
        log.info("{} Sent OID=\'{}\'", logId, messageForRIS.getoID());
        dbAction.setActionNoteAndStatus(
                action, String.format("Message is in queue for RIS. OID=\'%s\'", messageForRIS.getoID()), ActionStatus.WAITING
        );
        return true;
    }

    private void setActionSendProperties(final Action action, final Date date) {
        boolean sendDatePropertyFounded = false;
        boolean sendTimePropertyFounded = false;
        try {
            for (ActionProperty property : action.getActionProperties()) {
                if (!sendDatePropertyFounded && "multivox_send_date".equalsIgnoreCase(property.getType().getCode())) {
                    dbActionProperty.setValue(property, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date), 0);
                    sendDatePropertyFounded = true;
                    continue;
                }
                if (!sendTimePropertyFounded && "multivox_send_time".equalsIgnoreCase(property.getType().getCode())) {
                    dbActionProperty.setValue(property, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date), 0);
                    sendTimePropertyFounded = true;
                    continue;
                }
                if (sendDatePropertyFounded && sendTimePropertyFounded) {
                    break;
                }
            }
        } catch (Exception e) {
            log.error("Cannot set ActionProperties (send_date + send_time)", e);
        }
    }

    private void setActionResultProperties(final Action action, final String result) {
        try {
            for (ActionProperty property : action.getActionProperties()) {
                if ("multivox_result".equalsIgnoreCase(property.getType().getCode())) {
                    dbActionProperty.setValue(property, result, 0);
                    break;
                }
            }
        } catch (Exception e) {
            log.error("Cannot set ActionProperty (multivox_result)", e);
        }
    }

    public Map<String, String> pollReceive(final int requestNumber, final Staff user) {
        final List<DbConnector> unprocessedMessages = dbConnector.getUnprocessedMessagesByDestination(DbConnector.System.MIS);
        log.info("#{} For MIS there {} unprocessed messages", requestNumber, unprocessedMessages.size());
        final Map<String, String> result = new HashMap<>(unprocessedMessages.size());
        int i = 0;
        for (DbConnector message : unprocessedMessages) {
            i++;
            result.put(message.getoID(), processMessage(message, "#" + requestNumber + "-" + i));
        }
        return result;
    }

    private String processMessage(final DbConnector message, final String logId) {
        log.info("{} Start processing message \'{}\'", logId, message.getUID());
        final List<DbConnector> sourceMessages = dbConnector.findByReplyUID(message.getUID());
        if (sourceMessages.isEmpty()) {
            log.error("{} End processing [{}]. No SourceMessage found!", logId, message.getUID());
            return "Not found source message";
        }
        for (DbConnector sourceMessage : sourceMessages) {
            final Action action = dbAction.getByUUID(sourceMessage.getUID());
            if (action != null) {
                final ActionType actionType = action.getActionType();
                log.info(
                        "{} By SourceMessage[{}] found Action[{}] - [{}] '{}'",
                        logId,
                        sourceMessage.getUID(),
                        action.getId(),
                        actionType.getId(),
                        actionType.getName()
                );
                setActionResultProperties(action, " http://ris/webpacs/#/images?StudyExternalID=".concat(String.valueOf(action.getId())));
                dbAction.setActionNoteAndStatus(action, "ПАКС отдал ответ.", ActionStatus.FINISHED);
            } else {
                log.warn("{} By SourceMessage[{}] no Action found!", logId, sourceMessage.getUID());
            }
        }
        dbConnector.setProcessed(message);
        log.info("{} End processing message \'{}\'.", logId, message.getUID());
        return "PROCESSED";
    }


}
