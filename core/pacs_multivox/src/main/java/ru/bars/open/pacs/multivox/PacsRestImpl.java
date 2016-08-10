package ru.bars.open.pacs.multivox;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.auth.AuthStorageBeanLocal;
import ru.korus.tmis.core.database.DbStaffBeanLocal;
import ru.korus.tmis.core.database.common.DbActionBeanLocal;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.ActionStatus;
import ru.korus.tmis.core.entity.model.ActionType;
import ru.korus.tmis.core.entity.model.Staff;
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
    private DbActionBeanLocal dbAction;

    @GET
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8", MediaType.APPLICATION_XML + ";charset=utf-8"})
    public Response poll(@Context HttpServletRequest servRequest, @QueryParam("callback") String callback) {
        final int requestNumber = counter.incrementAndGet();
        log.info("#{} manual launch polling", requestNumber);
        final Staff user = getUserFromRequest(servRequest);
        if(user == null){
            log.error("#{} End. Launched by unauthorized user, forbidden.", requestNumber);
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        log.info("#{} launched by [{}]\'{}\'", requestNumber, user.getId(), user.getFullName());
        //TODO CONSTANTS FROM CCS
        final List<Action> actions = dbAction.getActionsByActionTypeCodeAndStatus("multivox", ActionStatus.STARTED);
        log.debug("#{} founded {} action to send", requestNumber, actions.size());
        int sended = 0;
        for (Action action : actions) {
            if(sendAction(action, user, requestNumber)){
                sended++;
            }
        }
        log.info("#{} sended {}/{} actions", requestNumber, sended, actions.size());
        final String responseEnitity = String.format("{\"sended\": %d, \"total\": %d}", sended, actions.size());
        log.info("#{} End.", requestNumber);
        return Response.ok(responseEnitity).build();
    }

    private boolean sendAction(final Action action, final Staff user, final int requestNumber) {
        final ActionType actionType = action.getActionType();
        log.info("#{} Start sending Action[{}] - \'{}\'", requestNumber, action.getId(), actionType.getName());
        final String logId = "#"+requestNumber+"-"+action.getId()+" ";  // "#{requestNumber}-{Action.id}"
        if(action.getDeleted()){
            log.warn("{} Skip. Cause action is deleted", logId);
            return false;
        } else if(actionType.getDeleted()){
            log.warn("{} Skip. Cause ActionType[{}] is deleted", logId, actionType.getId());
            return false;
        } else if(!"multivox".equalsIgnoreCase(actionType.getCode())){
            log.warn("{} Skip. Cause ActionType[{}] has non-send code", logId, actionType.getId());
            return false;
        }

        log.info("{} Sent.", logId);
        return true;
    }


    @GET
    @Path("{actionId}")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8", MediaType.APPLICATION_XML + ";charset=utf-8"})
    public Object sendAction(
            @Context HttpServletRequest servRequest,
            @PathParam("actionId") int actionId, @QueryParam("callback") String callback
    ) {
        final int requestNumber = counter.incrementAndGet();
        log.info("#{} manual launch sendAction({})", requestNumber, actionId);
        final Staff user = getUserFromRequest(servRequest);
        if(user == null){
            log.error("#{} End. Launched by unauthorized user, forbidden.", requestNumber);
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        log.info("#{} launched by [{}]\'{}\'", requestNumber, user.getId(), user.getFullName());
        final Action action = dbAction.getById(actionId);
        if(action == null || action.getDeleted()){
            log.info("#{} End. Action is null or deleted", requestNumber);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        sendAction(action, user, requestNumber);
        log.info("#{} End.", requestNumber);
        return Response.ok("{\"result\":\"OK\"}").build();
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
