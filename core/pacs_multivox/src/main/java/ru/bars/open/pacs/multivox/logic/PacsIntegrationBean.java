package ru.bars.open.pacs.multivox.logic;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import ru.bars.open.pacs.multivox.config.PacsSettings;
import ru.bars.open.pacs.multivox.dao.DbConnectorDaoImpl;
import ru.korus.tmis.core.database.DbStaffBeanLocal;
import ru.korus.tmis.core.database.common.DbActionBeanLocal;
import ru.korus.tmis.core.database.common.DbActionPropertyBeanLocal;
import ru.korus.tmis.core.database.common.DbEventBeanLocal;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.entity.model.multivox.DbConnector;

import javax.ejb.*;
import javax.ejb.Schedule;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.text.SimpleDateFormat;
import java.util.*;
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
        final int requestNumberForSend = counter.incrementAndGet();
        log.info("#{} periodical launch polling for send", requestNumberForSend);
        try {
            pollSend(requestNumberForSend, null);
        } catch (Exception e) {
            log.info("#{} Exception", requestNumberForSend, e);
        }
        log.info("#{} End. Successfuly ", requestNumberForSend);

        final int requestNumberForReceive = counter.incrementAndGet();
        log.info("#{} periodical launch polling for receive", requestNumberForReceive);
        try {
            pollReceive(requestNumberForReceive, null);
        } catch (Exception e) {
            log.info("#{} Exception", requestNumberForReceive, e);
        }
        log.info("#{} End. Successfuly ", requestNumberForReceive);

    }

    public Map<Integer, String> pollSend(final int requestNumber, final Staff sender) {
        final List<Action> actions = dbAction.getActionsByActionTypFlatCodePrefixAndStatus(
                PacsSettings.getAT_FLAT_CODE_PREFIX(), ActionStatus.STARTED
        );
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
        try {
            final ActionType actionType = action.getActionType();
            if (actionType == null) {
                log.error("{} Action has not ActionType", logId);
                dbAction.setActionNoteAndStatus(action, "Action has not ActionType", ActionStatus.STARTED);
                return false;
            } else if (actionType.getDeleted()) {
                log.warn("{} ActionType[{}] is deleted", logId, actionType.getId());
            }
            if (!actionType.getFlatCode().startsWith(PacsSettings.getAT_FLAT_CODE_PREFIX())) {
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
                dbAction.setActionNoteAndStatus(action, "Не указана плановая дата исследования", ActionStatus.STARTED);
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
            log.info(
                    "{} Event[{}] - {} [{}]\'{}\'", logId, event.getId(), isStationaryEvent ? "STAT" : "AMB", eventType.getId(), eventType.getName()
            );
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
                dbAction.setActionNoteAndStatus(
                        action, String.format("Action[%d] has no direction OrgStructure", action.getId()), ActionStatus.STARTED
                );
                return false;
            }
            log.info(
                    "{} Action directed (by executor) to OrgStructure[{}] - \'{}\'",
                    logId,
                    orgStructureDirection.getId(),
                    orgStructureDirection.getName()
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
            final DbConnector messageForRIS = dbConnector.createMessageForRIS(UUID.randomUUID(), messageAsString);
            if (messageForRIS == null) {
                log.error("{} Cannot create message for RIS (Row for 'DbConnector' table).", logId);
                dbAction.setActionNoteAndStatus(action, "Cannot create message for RIS (Row for \'DbConnector\' table)", ActionStatus.STARTED);
                return false;
            }
            dbConnector.persist(messageForRIS);
            setActionSendProperties(action, new Date());
            log.info("{} Sent OID=\'{}\'", logId, messageForRIS.getoID());
            dbAction.setActionNoteAndStatus(action, action.getNote(), ActionStatus.WAITING);
            return true;
        } catch (final Exception e) {
            log.error("{} Error", logId, e);
            return false;
        }
    }

    private void setActionSendProperties(final Action action, final Date date) {
        boolean sendDatePropertyFounded = false;
        boolean sendTimePropertyFounded = false;
        try {
            for (ActionProperty property : action.getActionProperties()) {
                if (!sendDatePropertyFounded && PacsSettings.getAPT_CODE_SEND_DATE().equalsIgnoreCase(property.getType().getCode())) {
                    dbActionProperty.setValue(property, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date), 0);
                    sendDatePropertyFounded = true;
                    continue;
                }
                if (!sendTimePropertyFounded && PacsSettings.getAPT_CODE_SEND_TIME().equalsIgnoreCase(property.getType().getCode())) {
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
        boolean weblinkFounded = false;
        boolean applinkFounded = false;
        try {
            for (ActionProperty property : action.getActionProperties()) {
                if (!weblinkFounded && PacsSettings.getAPT_CODE_RESULT().equalsIgnoreCase(property.getType().getCode())) {
                    dbActionProperty.setValue(property, PacsSettings.getAPV_RESULT() + result, 0);
                    weblinkFounded = true;
                    continue;
                }
                if (!applinkFounded && PacsSettings.getAPT_CODE_APP_LINK().equalsIgnoreCase(property.getType().getCode())) {
                    dbActionProperty.setValue(property, PacsSettings.getAPV_APP_LINK() + result, 0);
                    applinkFounded = true;
                    continue;
                }
                if (weblinkFounded && applinkFounded) {
                    break;
                }
            }
        } catch (Exception e) {
            log.error("Cannot set ActionProperty (multivox_result + multivox_app_link )", e);
        }
    }

    public Map<String, String> pollReceive(final int requestNumber, final Staff user) {
        final List<DbConnector> unprocessedMessages = dbConnector.getUnprocessedMessagesByDestination(DbConnector.System.MIS);
        log.info("#{} For MIS there {} unprocessed messages", requestNumber, unprocessedMessages.size());
        final Map<String, String> result = new HashMap<>(unprocessedMessages.size());
        int i = 1;
        for (DbConnector message : unprocessedMessages) {
            String logId = "#" + requestNumber + "-" + i++;
            String messageProcessResult;
            try {
                messageProcessResult = processMessage(message, logId);
            } catch (Exception e) {
                log.error("{} Error. Cause ", logId, e);
                messageProcessResult = "Error. Cause= " + e.getMessage();
            }
            result.put(message.getoID(), messageProcessResult);
        }
        return result;
    }

    private String processMessage(final DbConnector message, final String logId) throws XPathExpressionException {
        log.info("{} Start processing message \'{}\'", logId, message.getUID());
        try {
            final Document doc = MessageFactory.getDocumentFromBytes(message.getMessage(), "CP1251");
            final String messageAsString = MessageFactory.getDocumentAsString(doc, logId);
            if (doc == null || StringUtils.isEmpty(messageAsString)) {
                log.error("{} Cannot parse message from byte array", logId);
                dbConnector.setProcessed(message, "Cannot parse message from byte array");
                return "Cannot parse message from byte array";
            } else {
                log.info("{} Result XML message:\n{}", logId, messageAsString);
            }
            XPath xPath = XPathFactory.newInstance().newXPath();
            final String xpathExpression = "/ORM_O01/ORM_O01.ORCOBRRQDRQ1ODSODTRXONTEDG1OBXNTECTIBLG/ORC/ORC.2/EI.1";
            final Node item = (Node) xPath.compile(xpathExpression).evaluate(doc, XPathConstants.NODE);
            log.info("XPath[\'{}\'] : nodeName=\'{}\', txtContent=\'{}\'", xpathExpression, item.getNodeName(), item.getTextContent());
            final String actionIdStr = item.getTextContent();
            Integer actionId = null;
            if (StringUtils.isNotEmpty(actionIdStr)) {
                try {
                    actionId = Integer.parseInt(actionIdStr);
                } catch (NumberFormatException e) {
                    log.error("{} Cannot parse \'{}\' to Integer", logId, actionIdStr);
                    actionId = null;
                }
            }
            if (actionId == null) {
                dbConnector.setProcessed(message, "Cannot parse MIS ID from String to Integer");
                return "Cannot parse MIS ID from String to Integer";
            }
            final Action action = dbAction.getById(actionId);
            if (action == null) {
                dbConnector.setProcessed(message, "Cannot find Action by MIS ID from message");
                return "Cannot find Action by MIS ID from message";
            }
            log.info("{} message is for Action[{}]", logId, action.getId());
            final ActionType actionType = action.getActionType();
            if (actionType == null) {
                log.error("{} Action has not ActionType", logId);
                dbConnector.setProcessed(message, "Action has not ActionType");
                return "Action has not ActionType";
            } else if (actionType.getDeleted()) {
                log.warn("{} ActionType[{}] is deleted", logId, actionType.getId());
            }
            if (!actionType.getFlatCode().startsWith(PacsSettings.getAT_FLAT_CODE_PREFIX())) {
                log.error("{} ActionType[{}] has wrong flatCode for multivox = \'{}\'", logId, actionType.getId(), actionType.getFlatCode());
                dbConnector.setProcessed(message, "ActionType has wrong flatCode for multivox");
                return "ActionType has wrong flatCode for multivox";
            }
            if (MessageFactory.DicomModality.getByCode(actionType.getMnemonic()) == null) {
                log.error("{} ActionType[{}] has wrong mnemonic for multivox = \'{}\'", logId, actionType.getId(), actionType.getMnemonic());
                dbConnector.setProcessed(message, "ActionType has wrong mnemonic for Multivox");
                return "ActionType has wrong mnemonic for Multivox";
            }
            setActionResultProperties(action, String.valueOf(action.getId()));
            dbAction.setActionNoteAndStatus(action, action.getNote(), ActionStatus.WAITING);
            dbConnector.setProcessed(message, null);
            log.info("{} End processing message \'{}\'.", logId, message.getUID());
            return "PROCESSED";
        } catch (final Exception e) {
            log.error("{} Error", logId, e);
            return "ERROR";
        }
    }
}
