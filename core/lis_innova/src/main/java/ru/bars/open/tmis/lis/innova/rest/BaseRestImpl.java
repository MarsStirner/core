package ru.bars.open.tmis.lis.innova.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import ru.bars.open.tmis.lis.innova.logic.DbTakenTissueJournalBeanLocal;
import ru.bars.open.tmis.lis.innova.logic.LisInnovaBean;
import ru.bars.open.tmis.lis.innova.rest.entities.json.in.SendActionsToLaboratoryRequest;
import ru.bars.open.tmis.lis.innova.rest.entities.json.out.SendActionsToLaboratoryResponse;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.auth.AuthStorageBeanLocal;
import ru.korus.tmis.core.database.DbStaffBeanLocal;
import ru.korus.tmis.core.entity.model.TakenTissue;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Author: Upatov Egor <br>
 * Date: 12.05.2016, 16:40 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
@Path("/")
@Stateless
public class BaseRestImpl {

    private static final Logger log = LoggerFactory.getLogger("LIS_INNOVA");

    private static final AtomicLong counter = new AtomicLong();

    @EJB
    private AuthStorageBeanLocal authBean;

    @EJB
    private DbStaffBeanLocal dbStaff;

    @EJB
    private DbTakenTissueJournalBeanLocal dbTTJ;

    @EJB
    private LisInnovaBean lis;

    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8", MediaType.APPLICATION_XML + ";charset=utf-8"})
    public Response sendToLaboratory(
            @Context HttpServletRequest servRequest, final SendActionsToLaboratoryRequest request
    ) {
        MDC.clear();
        MDC.put("requestNumber", "#" + counter.incrementAndGet() + " ");
        log.info("Call PUT sendToLaboratory with payload=\'{}\'", request);
        final SendActionsToLaboratoryResponse response = new SendActionsToLaboratoryResponse();
        if (request == null || request.getIds().isEmpty()) {
            log.info("End. Empty request - return empty response \'{}\'", response);
            MDC.clear();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        final AuthData authData = authBean.getAuthDataFromRequest(servRequest);
        if (authData == null) {
            log.error("End with AuthException. Return empty result");
            for (Integer actionId : request.getIds()) {
                response.getData().put(actionId, "Not send: Authorization failed");
            }
            log.error("End with AuthException. Return \'{}\'", response);
            return Response.status(Response.Status.UNAUTHORIZED).entity(response).build();
        }
        for (Integer ttjId : request.getIds()) {
            try {
                final TakenTissue ttj = dbTTJ.get(ttjId);
                if (ttj != null) {
                    response.getData().put(ttjId, lis.sendTakenTissueJournal(ttj));
                } else {
                    response.getData().put(ttjId, "TakenTissueJournal not found");
                }
            } catch (Exception e) {
                response.getData().put(ttjId, "Not send: Exception = " + e.getMessage());
            }
        }
        log.info("End. Return \'{}\'", response);
        MDC.clear();
        return Response.ok(response).build();
    }
}
