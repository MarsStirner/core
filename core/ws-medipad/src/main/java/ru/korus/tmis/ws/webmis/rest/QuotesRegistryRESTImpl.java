package ru.korus.tmis.ws.webmis.rest;

import com.sun.jersey.api.json.JSONWithPadding;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.QuotaData;
import ru.korus.tmis.core.data.QuotaRequestData;
import ru.korus.tmis.core.logging.slf4j.interceptor.ServicesLoggingInterceptor;
import ru.korus.tmis.ws.impl.WebMisRESTImpl;
import javax.interceptor.Interceptors;
import javax.ws.rs.*;

/**
 * Список REST-сервисов для работы с квотами
 * Author: idmitriev Systema-Soft
 * Date: 3/20/13 5:25 PM
 * Since: 1.0.0.74
 */
@Interceptors(ServicesLoggingInterceptor.class)
public class QuotesRegistryRESTImpl {

    //protected static final String PATH = AppealsInfoRESTImpl.PATH + "{eventId}/quotes/";
    private WebMisRESTImpl wsImpl;
    private int eventId;
    private AuthData auth;
    private String callback;

    public QuotesRegistryRESTImpl(WebMisRESTImpl wsImpl, int eventId, String callback, AuthData auth) {
        this.eventId = eventId;
        this.auth = auth;
        this.wsImpl = wsImpl;
        this.callback = callback;
    }

    /**
     * Сервис сохранения квоты <br>
     * url: /appeals/{appealId}/quotes
     * @param data Json с данными о квоте как QuotaData
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     */
    @POST
    @Produces("application/x-javascript")
    public Object createQuota(QuotaData data) {
        return new JSONWithPadding(wsImpl.insertOrUpdateQuota(data, eventId, this.auth), this.callback);
    }

    /**
     * Сервис редактирования квоты <br>
     * url: /appeals/{appealId}/quotes
     * @param data Json с данными о квоте как QuotaData
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     */
    @PUT
    @Path("/{id}")
    @Produces("application/x-javascript")
    public Object modifyQuota(QuotaData data,
                              @PathParam("id") int id) {        //TODO: insert quotes id
        return new JSONWithPadding(wsImpl.insertOrUpdateQuota(data, eventId, this.auth), this.callback);
    }

    /**
     * Сервис получения данных о квоте <br>
     * url: /appeals/{appealId}/quotes
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     */
    @GET
    @Produces("application/x-javascript")
    public Object getQuotaHistory(@QueryParam("limit")int limit,
                                  @QueryParam("page")int  page,
                                  @QueryParam("sortingField")String sortingField,      //сортировки вкл.
                                  @QueryParam("sortingMethod")String sortingMethod) {

        QuotaRequestData request = new QuotaRequestData(null, sortingField, sortingMethod, limit, page);
        return new JSONWithPadding(wsImpl.getQuotaHistory(eventId, request), this.callback);
    }
}
