package ru.korus.tmis.ws.webmis.rest;

import com.sun.jersey.api.json.JSONWithPadding;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.AssignmentData;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.util.Arrays;

/**
 * Список REST-сервисов для работы с назначениями
 * Author: idmitriev Systema-Soft
 * Date: 3/20/13 5:06 PM
 * Since: 1.0.0.74
 */
@Stateless
public class AssignmentsRegistryRESTImpl {

    @EJB
    private WebMisREST wsImpl;

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
    public Object insertAssignment(@Context HttpServletRequest servRequest,
                                   @PathParam("eventId") int eventId,
                                   @QueryParam("callback") String callback,
                                   AssignmentData data) throws CoreException {
        return new JSONWithPadding(wsImpl.insertAssignment(data, eventId, mkAuth(servRequest)), callback);
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
    public Object modifyAssignment(@Context HttpServletRequest servRequest,
                                   @PathParam("eventId") int eventId,
                                   @QueryParam("callback") String callback,
                                   AssignmentData data,
                                   @PathParam("actionId")int actionId) throws CoreException {
        return new JSONWithPadding(wsImpl.insertAssignment(data, eventId, mkAuth(servRequest)), callback);  //TODO: подкл actionId
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
    public Object getAssignmentById(@Context HttpServletRequest servRequest,
                                    @QueryParam("callback") String callback,
                                    @PathParam("actionId")int actionId) throws CoreException {
        return new JSONWithPadding(wsImpl.getAssignmentById(actionId, mkAuth(servRequest)), callback);
    }

    private AuthData mkAuth(HttpServletRequest servRequest) {
        return wsImpl.checkTokenCookies(servRequest.getCookies());
    }

}
