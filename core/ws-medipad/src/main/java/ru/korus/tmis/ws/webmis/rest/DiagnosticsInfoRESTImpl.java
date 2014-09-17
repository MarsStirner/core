package ru.korus.tmis.ws.webmis.rest;

import com.sun.jersey.api.json.JSONWithPadding;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.logging.slf4j.interceptor.ServicesLoggingInterceptor;
import javax.interceptor.Interceptors;
import javax.ws.rs.*;

/**
 * Список REST-сервисов для работы с данными о диагностиках
 * Author: idmitriev Systema-Soft
 * Date: 3/20/13 1:43PM
 * Since: 1.0.0.74
 */
@Interceptors(ServicesLoggingInterceptor.class)
public class DiagnosticsInfoRESTImpl {

    //protected static final String PATH = BaseRegistryRESTImpl.PATH + "diagnostics/";
    private WebMisREST wsImpl;
    private AuthData auth;
    private String callback;

    public DiagnosticsInfoRESTImpl(WebMisREST wsImpl, String callback, AuthData auth) {
        this.auth = auth;
        this.wsImpl = wsImpl;
        this.callback = callback;
    }

    /**
     * Получение результатов исследований БАК-лаборатории
     * @param actionId Идентификатор лабораторного исследования
     * @return
     */
    @GET
    @Path("/laboratory/bak/{actionId}")
    @Produces("application/x-javascript")
    public Object getVacantHospitalBeds(@PathParam("actionId") int actionId) throws CoreException {
        return new JSONWithPadding(wsImpl.getBakResult(actionId, auth), this.callback);
    }
}
