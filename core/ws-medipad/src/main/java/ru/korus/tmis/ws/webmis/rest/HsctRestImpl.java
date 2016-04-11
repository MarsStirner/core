package ru.korus.tmis.ws.webmis.rest;

import com.sun.jersey.api.json.JSONWithPadding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.hsct.HsctBean;
import ru.korus.tmis.hsct.HsctRequestActionContainer;
import ru.korus.tmis.hsct.HsctResponse;
import ru.korus.tmis.hsct.external.HsctExternalRequestForComplete;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Author: Upatov Egor <br>
 * Date: 23.12.2015, 16:55 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: REST для ТГСК (отправка заявок во внешнюю систему при запросе с фронтенда <br>
 */
@Stateless
public class HsctRestImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger("HSCT");

    @EJB
    private HsctBean hsctBean;

    @EJB
    private WebMisREST wsImpl;

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8", MediaType.APPLICATION_XML + ";charset=utf-8"})
    public Object modifyQueue(
            @Context HttpServletRequest servRequest, @QueryParam("callback") String callback, HsctRequestActionContainer data
    ) throws CoreException {
        final AuthData authData = wsImpl.checkTokenCookies(servRequest.getCookies());
        final Staff user = authData.getUser();
        LOGGER.info("call modifyQueue({}) by {}", data, authData);
        if (data.isEnqueueAction()) {
            return new JSONWithPadding(hsctBean.enqueueAction(data.getId(), user), callback);
        } else if (data.isDequeueAction()) {
            return new JSONWithPadding(hsctBean.dequeueAction(data.getId(), user), callback);
        } else {
            final HsctResponse errorResponse = new HsctResponse();
            errorResponse.setError(true);
            errorResponse.setErrorMessage(String.format("InvalidArgument: data.action=\'%s\' is unknown action", data.getAction()));
            return new JSONWithPadding(errorResponse, callback);
        }
    }

    @GET
    @Path("{actionId}")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8", MediaType.APPLICATION_XML + ";charset=utf-8"})
    public Object checkInQueue(
            @PathParam("actionId") int actionId, @QueryParam("callback") String callback
    ) {
        return new JSONWithPadding(hsctBean.isInQueue(actionId), callback);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8", MediaType.APPLICATION_XML + ";charset=utf-8"})
    public Object getCurrentActive(@QueryParam("callback") String callback) {
        return new JSONWithPadding(hsctBean.getCurrentActive(), callback);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/response")
    public Response setHsctRequestStatusFromExternalSystem(
            @QueryParam("user_name") String userName, @QueryParam("user_token") String userToken, HsctExternalRequestForComplete request
    ) {
        return hsctBean.setRequestStatusFromExternalSystem(userName, userToken, request);
    }


}
