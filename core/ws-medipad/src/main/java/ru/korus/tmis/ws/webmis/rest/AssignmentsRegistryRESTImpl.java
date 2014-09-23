package ru.korus.tmis.ws.webmis.rest;

import com.sun.jersey.api.json.JSONWithPadding;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.AssignmentData;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.logging.slf4j.interceptor.ServicesLoggingInterceptor;
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
    private WebMisREST wsImpl;
    private int eventId;
    private AuthData auth;
    private String callback;

    public AssignmentsRegistryRESTImpl(WebMisREST wsImpl, int eventId, String callback, AuthData auth) {
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
    @Produces({"application/javascript", "application/x-javascript"})
    public Object insertAssignment(AssignmentData data) throws CoreException {
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
    @Path("/{actionId}")
    @Consumes("application/json")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object modifyAssignment(AssignmentData data,
                                   @PathParam("actionId")int actionId) throws CoreException {
        return new JSONWithPadding(wsImpl.insertAssignment(data, this.eventId, this.auth), this.callback);  //TODO: подкл actionId
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
    @Produces({"application/javascript", "application/x-javascript"})
    public Object getAssignmentById(@PathParam("actionId")int actionId) throws CoreException {
        return new JSONWithPadding(wsImpl.getAssignmentById(actionId, this.auth), this.callback);
    }

}
