package ru.korus.tmis.ws.webmis.rest;

import com.sun.jersey.api.json.JSONWithPadding;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.HospitalBedData;
import ru.korus.tmis.core.data.HospitalBedDataListFilter;
import ru.korus.tmis.core.exception.CoreException;

import javax.interceptor.Interceptors;
import javax.ws.rs.*;

/**
 * Список REST-сервисов для работы с коечным фондом
 * Author: idmitriev Systema-Soft
 * Date: 3/20/13 5:58 PM
 * Since: 1.0.0.74
 */

public class HospitalBedRegistryRESTImpl {

    private WebMisREST wsImpl;
    private int eventId;
    private AuthData auth;
    private String callback;

    public HospitalBedRegistryRESTImpl(WebMisREST wsImpl, int eventId, String callback, AuthData auth) {
       this.eventId = eventId;
       this.auth = auth;
       this.wsImpl = wsImpl;
       this.callback = callback;
    }
    /**
     * Регистрация на койке
     * @param data Json c данными о койке как HospitalBedData
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.data.HospitalBedData
     */
    @POST
    @Consumes("application/json")
    @Produces({"application/javascript", "application/x-javascript"})
    public  Object registryPatientToHospitalBed (HospitalBedData data) throws CoreException {
        return new JSONWithPadding(wsImpl.registryPatientToHospitalBed(this.eventId, data, this.auth), this.callback);
    }

    /**
     * Регистрация на койке (редактирование)
     * @param data Json c данными о койке как HospitalBedData
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
    public  Object modifyPatientToHospitalBed(HospitalBedData data,
                                              @PathParam("actionId") int actionId) throws CoreException {
        return new JSONWithPadding(wsImpl.modifyPatientToHospitalBed(actionId, data, this.auth), this.callback);
    }

    /**
     * Список движения по отделениям
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @GET
    @Produces({"application/javascript", "application/x-javascript"})
    public Object getMovingListForEvent() throws CoreException {

        HospitalBedDataListFilter filter = new HospitalBedDataListFilter(this.eventId);
        //HospitalBedDataRequest request= new HospitalBedDataRequest(sortingField, sortingMethod, limit, page, filter);
        return new JSONWithPadding(wsImpl.getMovingListForEvent(filter, this.auth), this.callback);
    }


    /**
     * Создание направления/перевода в отделение.
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
    public  Object movingPatientToDepartment(HospitalBedData data) throws CoreException {
        return new JSONWithPadding(wsImpl.movingPatientToDepartment(this.eventId, data, this.auth), this.callback);
    }

    /**
     * Данные об регистрации на койке
     * @param actionId Идентификатор действия типа 'Движение' для которого отменяется регистрация на койке.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @GET
    @Path("/{actionId}")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object getInfoHospitalBedForPatient(@PathParam("actionId") int actionId) throws CoreException {
        return new JSONWithPadding(wsImpl.getPatientToHospitalBedById(actionId, this.auth), this.callback);
    }

    /**
     * Отмена регистрациии на койке
     * @param actionId Идентификатор действия типа 'Движение' для которого отменяется регистрация на койке.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @DELETE
    @Path("/{actionId}")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object callOffHospitalBedForPatient(@PathParam("actionId") int actionId) throws CoreException {
        return new JSONWithPadding(wsImpl.callOffHospitalBedForPatient(actionId, this.auth), this.callback);
    }
}
