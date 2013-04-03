package ru.korus.tmis.ws.webmis.rest;

import com.sun.jersey.api.json.JSONWithPadding;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.logging.slf4j.interceptor.ServicesLoggingInterceptor;
import ru.korus.tmis.ws.impl.WebMisRESTImpl;
import javax.interceptor.Interceptors;
import javax.ws.rs.*;

/**
 * Список REST-сервисов для просмотра движений
 * Author: idmitriev Systema-Soft
 * Date: 3/22/13 6:34 PM
 * Since: 1.0.0.74
 */
@Interceptors(ServicesLoggingInterceptor.class)
public class HospitalBedsInfoRESTImpl {

    //protected static final String PATH = BaseRegistryRESTImpl.PATH + "hospitalbed/";
    private WebMisRESTImpl wsImpl;
    private AuthData auth;
    private String callback;

    public HospitalBedsInfoRESTImpl(WebMisRESTImpl wsImpl, String callback, AuthData auth) {
        this.auth = auth;
        this.wsImpl = wsImpl;
        this.callback = callback;
    }
}
