package ru.korus.tmis.ws.webmis.rest;

import com.sun.jersey.api.json.JSONWithPadding;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.logging.slf4j.interceptor.ServicesLoggingInterceptor;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.util.Arrays;

/**
 * Список REST-сервисов для работы с данными о диагностиках
 * Author: idmitriev Systema-Soft
 * Date: 3/20/13 1:43PM
 * Since: 1.0.0.74
 */
@Stateless
@Interceptors(ServicesLoggingInterceptor.class)
public class DiagnosticsInfoRESTImpl {

    @EJB private WebMisREST wsImpl;

    /**
     * Получение результатов исследований БАК-лаборатории
     * @param actionId Идентификатор лабораторного исследования
     * @return
     */
    @GET
    @Path("/laboratory/bak/{actionId}")
    @Produces("application/x-javascript")
    public Object getVacantHospitalBeds(@Context HttpServletRequest servRequest,
                                        @QueryParam("callback") String callback,
                                        @PathParam("actionId") int actionId) throws CoreException {
        return new JSONWithPadding(wsImpl.getBakResult(actionId,
                wsImpl.checkTokenCookies(Arrays.asList(servRequest.getCookies()))), callback);
    }
}
