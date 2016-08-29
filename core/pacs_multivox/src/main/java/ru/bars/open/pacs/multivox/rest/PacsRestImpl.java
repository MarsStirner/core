package ru.bars.open.pacs.multivox.rest;


import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.bars.open.pacs.multivox.logic.PacsIntegrationBean;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.auth.AuthStorageBeanLocal;
import ru.korus.tmis.core.database.DbStaffBeanLocal;
import ru.korus.tmis.core.database.common.DbActionBeanLocal;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
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
    private DbActionBeanLocal dbAction;

    @EJB
    private DbStaffBeanLocal dbStaff;

    @EJB
    private PacsIntegrationBean pacsIntegration;


    /**
     * Отправка всех экшенов для ПАКС
     * @param servRequest  запрос
     * @return JSON карта отправленных экшенов
     * @throws JSONException
     */
    @GET
    @Path("/send")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Response poll( @Context final HttpServletRequest servRequest) throws JSONException {
        final int requestNumber = counter.incrementAndGet();
        log.info("#{} manual launch polling for send", requestNumber);
        final Staff user = getUserFromRequest(servRequest);
        if (user == null) {
            log.error("#{} End. Launched by unauthorized user, forbidden.", requestNumber);
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        log.info("#{} launched by [{}]\'{}\'", requestNumber, user.getId(), user.getFullName());
        final Map<Integer, String> result = pacsIntegration.poll(requestNumber, user);
        final JSONObject jsonResult = new JSONObject(result);
        log.info("#{} End.", requestNumber);
        return Response.ok(jsonResult).build();
    }

    @GET
    @Path("/send/{actionId}")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8", MediaType.APPLICATION_XML + ";charset=utf-8", MediaType.TEXT_PLAIN + ";charset=utf-8"})
    public Object sendAction(
            @Context HttpServletRequest servRequest, @PathParam("actionId") int actionId, @QueryParam("callback") String callback
    ) {
        final int requestNumber = counter.incrementAndGet();
        log.info("#{} manual launch sendAction({})", requestNumber, actionId);
        final Staff user = null; /*TODO production uncomment
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
            final boolean result = pacsIntegration.sendAction(action, user, requestNumber);
            log.info("#{} End. Sent = {}. Message=\'{}\'", requestNumber, result, action.getNote());
            return Response.ok("<result>" + action.getNote() + "</result>").build();
        }
    }

    /**
     * Отправка всех экшенов для ПАКС
     * @param servRequest  запрос
     * @return JSON карта отправленных экшенов
     * @throws JSONException
     */
    @GET
    @Path("/receive")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Response pollReceive( @Context final HttpServletRequest servRequest) throws JSONException {
        final int requestNumber = counter.incrementAndGet();
        log.info("#{} manual launch polling for receive", requestNumber);
        final Staff user = null; /*TODO production uncomment
         getUserFromRequest(servRequest);
        if (user == null) {
            log.error("#{} End. Launched by unauthorized user, forbidden.", requestNumber);
            return Response.status(Response.Status.FORBIDDEN).build();
        }
        log.info("#{} launched by [{}]\'{}\'", requestNumber, user.getId(), user.getFullName());
        */
        final Map<String, String> result = pacsIntegration.pollReceive(requestNumber, user);
        final JSONObject jsonResult = new JSONObject(result);
        log.info("#{} End.", requestNumber);
        return Response.ok(jsonResult).build();
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
