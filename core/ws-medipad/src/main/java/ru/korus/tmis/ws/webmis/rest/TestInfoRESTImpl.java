package ru.korus.tmis.ws.webmis.rest;

import com.sun.jersey.api.json.JSONWithPadding;
import ru.korus.tmis.core.data.ListDataRequest;
import ru.korus.tmis.core.data.PersonsListDataFilter;

import ru.korus.tmis.ws.impl.TestRESTClientImpl;
import ru.korus.tmis.ws.impl.WebMisRESTImpl;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.interceptor.Interceptors;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.io.Serializable;

/**
 * Базовый класс для test REST-сервисов
 * Author: idmitriev Systema-Soft
 * Date: 5/13/13 13:58 PM
 * Since: 1.0.1.10
 */

@Singleton
@Path("/tms-test/")
@Produces("application/json")
public class TestInfoRESTImpl implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    TestRESTClientImpl wsImpl;

    @GET
    @Path("/all")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object getAllTestConstruct() {
        return new JSONWithPadding(wsImpl.makeTestRESTData(),"");
    }
}
