package ru.korus.tmis.ws.webmis.rest;

import com.sun.jersey.api.json.JSONWithPadding;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.QuotaData;
import ru.korus.tmis.core.data.QuotaRequestData;
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
 * Список REST-сервисов для работы с квотами
 * Author: idmitriev Systema-Soft
 * Date: 3/20/13 5:25 PM
 * Since: 1.0.0.74
 */
@Stateless
@Interceptors(ServicesLoggingInterceptor.class)
public class QuotesRegistryRESTImpl {

    @EJB
    private WebMisREST wsImpl;

    /**
     * Сервис сохранения квоты <br>
     * url: /appeals/{appealId}/quotes
     * @param data Json с данными о квоте как QuotaData
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     */
    @POST
    @Produces("application/x-javascript")
    public Object createQuota(@Context HttpServletRequest servRequest,
                              @PathParam("eventId") int eventId,
                              @QueryParam("callback") String callback,
                              QuotaData data) throws CoreException {
        return new JSONWithPadding(wsImpl.insertOrUpdateQuota(data, eventId, mkAuth(servRequest)), callback);
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
    public Object modifyQuota(@Context HttpServletRequest servRequest,
                              @PathParam("eventId") int eventId,
                              @QueryParam("callback") String callback,
                              QuotaData data,
                              @PathParam("id") int id) throws CoreException {        //TODO: insert quotes id
        return new JSONWithPadding(wsImpl.insertOrUpdateQuota(data, eventId, mkAuth(servRequest)), callback);
    }

    /**
     * Сервис получения данных о квоте <br>
     * url: /appeals/{appealId}/quotes
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     */
    @GET
    @Produces("application/x-javascript")
    public Object getQuotaHistory(@Context HttpServletRequest servRequest,
                                  @PathParam("eventId") int eventId,
                                  @QueryParam("callback") String callback,
                                  @QueryParam("limit")int limit,
                                  @QueryParam("page")int  page,
                                  @QueryParam("sortingField")String sortingField,      //сортировки вкл.
                                  @QueryParam("sortingMethod")String sortingMethod) throws CoreException {

        QuotaRequestData request = new QuotaRequestData(null, sortingField, sortingMethod, limit, page);
        return new JSONWithPadding(wsImpl.getQuotaHistory(eventId, request), callback);
    }

    private AuthData mkAuth(HttpServletRequest servRequest) {
        return wsImpl.checkTokenCookies(Arrays.asList(servRequest.getCookies()));
    }
}
