package ru.korus.tmis.ws.webmis.rest;

import com.sun.jersey.api.json.JSONWithPadding;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.AssignmentData;
import ru.korus.tmis.core.logging.slf4j.interceptor.ServicesLoggingInterceptor;
import ru.korus.tmis.ws.impl.WebMisRESTImpl;
import javax.interceptor.Interceptors;
import javax.ws.rs.*;

/**
 * Список REST-сервисов для работы с назначениями
 * Author: idmitriev Systema-Soft
 * Date: 3/20/13 5:06 PM
 * Since: 1.0.0.74
 */
@Interceptors(ServicesLoggingInterceptor.class)
public class AssignmentsRegistryRESTImpl {

    //protected static final String PATH = AppealsInfoRESTImpl.PATH + "{eventId}/assignment/";
    private WebMisRESTImpl wsImpl;
    private int eventId;
    private AuthData auth;
    private String callback;

    public AssignmentsRegistryRESTImpl(WebMisRESTImpl wsImpl, int eventId, String callback, AuthData auth) {
        this.eventId = eventId;
        this.auth = auth;
        this.wsImpl = wsImpl;
        this.callback = callback;
    }

    /**
     * Создание нового назначения
     * @param data Json с данными о назначении как AssignmentData
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.data.AssignmentData
     */
    @POST
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public Object insertAssignment(AssignmentData data) {
        return new JSONWithPadding(wsImpl.insertAssignment(data, this.eventId, this.auth), this.callback);
    }

    /**
     * Редактирование назначения
     * @param data Json с данными о назначении как AssignmentData
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     * @see AssignmentData
     */
    @PUT
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public Object modifyAssignment(AssignmentData data) {
        return new JSONWithPadding(wsImpl.insertAssignment(data, this.eventId, this.auth), this.callback);
    }

    //Получение назначения по идентификатору
    /**
     * Получение данных об назначении
     * @param actionId Идентификатор назначения.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @GET
    @Path("/{actionId}")
    @Produces("application/x-javascript")
    public Object getAssignmentById(@PathParam("actionId")int actionId) {
        return new JSONWithPadding(wsImpl.getAssignmentById(actionId, this.auth), this.callback);
    }

}
