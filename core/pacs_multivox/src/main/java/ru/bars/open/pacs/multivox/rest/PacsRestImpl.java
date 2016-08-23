package ru.bars.open.pacs.multivox.rest;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import ru.bars.open.pacs.multivox.dao.DbConnectorDaoImpl;
import ru.bars.open.pacs.multivox.logic.MessageFactory;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.auth.AuthStorageBeanLocal;
import ru.korus.tmis.core.database.DbStaffBeanLocal;
import ru.korus.tmis.core.database.common.DbActionBeanLocal;
import ru.korus.tmis.core.database.common.DbEventBeanLocal;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.entity.model.multivox.DbConnector;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author: Upatov Egor <br>
 * Date: 23.12.2015, 16:55 <br>
 * Company: Bars group <br>
 * Description: REST для ПАКС (отправка заявок во внешнюю систему при запросе с фронтенда <br>
 */
@Stateless
@Path("/")
public class PacsRestImpl {
    private static final Logger log = LoggerFactory.getLogger("PACS");
    private AtomicInteger counter = new AtomicInteger(0);

    @EJB
    private AuthStorageBeanLocal authBean;

    @EJB
    private DbStaffBeanLocal dbStaff;

    @EJB
    private DbEventBeanLocal dbEvent;

    @EJB
    private DbActionBeanLocal dbAction;

    @EJB
    private DbConnectorDaoImpl dbConnector;

    @GET
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8", MediaType.APPLICATION_XML + ";charset=utf-8"})
    public Response poll(@Context HttpServletRequest servRequest, @QueryParam("callback") String callback) {
        final int requestNumber = counter.incrementAndGet();
        log.info("#{} manual launch polling", requestNumber);
        final Staff user = getUserFromRequest(servRequest);
        if (user == null) {
            log.error("#{} End. Launched by unauthorized user, forbidden.", requestNumber);
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        log.info("#{} launched by [{}]\'{}\'", requestNumber, user.getId(), user.getFullName());
        //TODO CONSTANTS FROM CCS
        final List<Action> actions = dbAction.getActionsByActionTypeMnemonicAndStatus("multivox", ActionStatus.STARTED);
        log.debug("#{} founded {} action to send", requestNumber, actions.size());
        int sent = 0;
        for (Action action : actions) {
            if (sendAction(action, user, requestNumber)) {
                sent++;
            }
        }
        log.info("#{} sent {}/{} actions", requestNumber, sent, actions.size());
        final String responseEnitity = String.format("{\"sent\": %d, \"total\": %d}", sent, actions.size());
        log.info("#{} End.", requestNumber);
        return Response.ok(responseEnitity).build();
    }

    @GET
    @Path("{actionId}")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8", MediaType.APPLICATION_XML + ";charset=utf-8", MediaType.TEXT_PLAIN + ";charset=utf-8"})
    public Object sendAction(
            @Context HttpServletRequest servRequest, @PathParam("actionId") int actionId, @QueryParam("callback") String callback
    ) {
        final int requestNumber = counter.incrementAndGet();
        log.info("#{} manual launch sendAction({})", requestNumber, actionId);
        final Staff user = null;
        /*TODO production uncomment
         getUserFromRequest(servRequest);
        if (user == null) {
            log.error("#{} End. Launched by unauthorized user, forbidden.", requestNumber);
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        log.info("#{} launched by [{}]\'{}\'", requestNumber, user.getId(), user.getFullName());
        */
        final Action action = dbAction.getById(actionId);
        if (action == null || action.getDeleted()) {
            log.info("#{} End. Action is null or deleted", requestNumber);
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            final boolean result = sendAction(action, user, requestNumber);
            log.info("#{} End. Sent = {}. Message=\'{}\'", requestNumber, result, action.getNote());
            return Response.ok("<result>"+action.getNote()+"</result>").build();
        }
    }

    private boolean sendAction(final Action action, final Staff user, final int requestNumber) {
        // "#{requestNumber}-{Action.id}"
        final String logId = String.format("#%d-%d",requestNumber , action.getId());
        final ActionType actionType = action.getActionType();
        if (actionType == null) {
            log.error("{} Action has not ActionType", logId);
            dbAction.setActionNoteAndStatus(action, "Action has not ActionType", ActionStatus.STARTED);
            return false;
        } else if (actionType.getDeleted()) {
            log.warn("{} ActionType[{}] is deleted", logId);
        }
        //TODO constants from CCS
        if(actionType.getFlatCode().startsWith("multivox_")){
            log.error("{} ActionType[{}] has wrong flatCode for multivox = \'{}\'", logId, actionType.getId(), actionType.getFlatCode());
            dbAction.setActionNoteAndStatus(action, "ActionType has wrong flatCode for Multivox", ActionStatus.STARTED);
            return false;
        }
        if(MessageFactory.DicomModality.getByCode(actionType.getMnemonic()) == null){
            log.error("{} ActionType[{}] has wrong mnemonic for multivox = \'{}\'", logId, actionType.getId(), actionType.getMnemonic());
            dbAction.setActionNoteAndStatus(action, "ActionType has wrong mnemonic for Multivox", ActionStatus.STARTED);
            return false;
        }
        if(action.getPlannedEndDate() == null){
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
        log.info("{} Action directed (by executor) to OrgStructure[{}] - \'{}\'", logId, orgStructureDirection.getId(), orgStructureDirection.getName());
        final Document doc = MessageFactory.constructDocument(action, event, client, doctor, orgStructure, orgStructureDirection, user, isStationaryEvent);
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
        log.info("{} Sent OID=\'{}\'", logId, messageForRIS.getoID());
        dbAction.setActionNoteAndStatus(action, String.format("Message is in queue for RIS. OID=\'%s\'", messageForRIS.getoID()), ActionStatus.WAITING);
        return true;
    }


    private Staff getUserFromRequest(final @Context HttpServletRequest servRequest) {
        try {
            final AuthData authData = authBean.checkTokenCookies(servRequest.getCookies());
            return dbStaff.getStaffById(authData.getUserId());
        } catch (CoreException e) {
            return null;
        }
    }
}
