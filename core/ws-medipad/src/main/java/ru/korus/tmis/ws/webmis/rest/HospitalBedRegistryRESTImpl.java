package ru.korus.tmis.ws.webmis.rest;

import com.sun.jersey.api.json.JSONWithPadding;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.HospitalBedData;
import ru.korus.tmis.core.data.HospitalBedDataListFilter;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.util.Arrays;

/**
 * Список REST-сервисов для работы с коечным фондом
 * Author: idmitriev Systema-Soft
 * Date: 3/20/13 5:58 PM
 * Since: 1.0.0.74
 */
@Stateless
public class HospitalBedRegistryRESTImpl {

    @EJB
    private WebMisREST wsImpl;

    /**
     * Регистрация на койке
     *
     * @param data Json c данными о койке как HospitalBedData
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.data.HospitalBedData
     */
    @POST
    @Consumes("application/json")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object registryPatientToHospitalBed(@Context HttpServletRequest servRequest,
                                               @PathParam("eventId") int eventId,
                                               @QueryParam("callback") String callback,
                                               HospitalBedData data) throws CoreException {
        return new JSONWithPadding(wsImpl.registryPatientToHospitalBed(eventId, data, mkAuth(servRequest)), callback);
    }

    /**
     * Регистрация на койке (редактирование)
     *
     * @param data     Json c данными о койке как HospitalBedData
     * @param actionId Идентификатор редактируемого действия типа 'Движение'.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     * @see HospitalBedData
     */
    @PUT
    @Path("/{actionId}/")
    @Consumes("application/json")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object modifyPatientToHospitalBed(@Context HttpServletRequest servRequest,
                                             @QueryParam("callback") String callback,
                                             HospitalBedData data,
                                             @PathParam("actionId") int actionId) throws CoreException {
        return new JSONWithPadding(wsImpl.modifyPatientToHospitalBed(actionId, data, mkAuth(servRequest)), callback);
    }

    /**
     * Список движения по отделениям
     *
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @GET
    @Produces({"application/javascript", "application/x-javascript"})
    public Object getMovingListForEvent(@Context HttpServletRequest servRequest,
                                        @PathParam("eventId") int eventId,
                                        @QueryParam("callback") String callback) throws CoreException {

        HospitalBedDataListFilter filter = new HospitalBedDataListFilter(eventId);
        //HospitalBedDataRequest request= new HospitalBedDataRequest(sortingField, sortingMethod, limit, page, filter);
        return new JSONWithPadding(wsImpl.getMovingListForEvent(filter, mkAuth(servRequest)), callback);
    }


    /**
     * Создание направления/перевода в отделение.
     *
     * @param data json данные о движении пациента как HospitalBedData
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     * @see HospitalBedData
     */
    @POST
    @Path("/moving/")
    @Consumes("application/json")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object movingPatientToDepartment(@Context HttpServletRequest servRequest,
                                            @PathParam("eventId") int eventId,
                                            @QueryParam("callback") String callback,
                                            HospitalBedData data) throws CoreException {
        return new JSONWithPadding(wsImpl.movingPatientToDepartment(eventId, data, mkAuth(servRequest)), callback);
    }

    /**
     * Данные об регистрации на койке
     *
     * @param actionId Идентификатор действия типа 'Движение' для которого отменяется регистрация на койке.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @GET
    @Path("/{actionId}")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object getInfoHospitalBedForPatient(@Context HttpServletRequest servRequest,
                                               @QueryParam("callback") String callback,
                                               @PathParam("actionId") int actionId) throws CoreException {
        return new JSONWithPadding(wsImpl.getPatientToHospitalBedById(actionId, mkAuth(servRequest)), callback);
    }

    /**
     * Отмена регистрациии на койке
     *
     * @param actionId Идентификатор действия типа 'Движение' для которого отменяется регистрация на койке.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @DELETE
    @Path("/{actionId}")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object callOffHospitalBedForPatient(@Context HttpServletRequest servRequest,
                                               @PathParam("eventId") int eventId,
                                               @QueryParam("callback") String callback,
                                               @PathParam("actionId") int actionId) throws CoreException {
        return new JSONWithPadding(wsImpl.callOffHospitalBedForPatient(actionId, mkAuth(servRequest)), callback);
    }

    private AuthData mkAuth(HttpServletRequest servRequest) {
        return wsImpl.checkTokenCookies(Arrays.asList(servRequest.getCookies()));
    }
}
