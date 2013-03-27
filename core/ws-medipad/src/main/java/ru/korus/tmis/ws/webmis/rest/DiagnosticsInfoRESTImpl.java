package ru.korus.tmis.ws.webmis.rest;

import com.sun.jersey.api.json.JSONWithPadding;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.logging.slf4j.interceptor.ServicesLoggingInterceptor;
import ru.korus.tmis.ws.impl.WebMisRESTImpl;
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
    private WebMisRESTImpl wsImpl;
    private AuthData auth;
    private String callback;

    public DiagnosticsInfoRESTImpl(WebMisRESTImpl wsImpl, String callback, AuthData auth) {
        this.auth = auth;
        this.wsImpl = wsImpl;
        this.callback = callback;
    }

    /**
     * Просмотр результатов лабораторных исследований
     * @param actionId идентификатор исследования.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @GET
    @Path("/laboratory/{actionId}")
    @Produces("application/x-javascript")
    public Object getInfoAboutDiagnosticsForPatientByEvent(@PathParam("actionId")int actionId) {
        return new JSONWithPadding(wsImpl.getInfoAboutDiagnosticsForPatientByEvent(actionId), this.callback);
    }
}
