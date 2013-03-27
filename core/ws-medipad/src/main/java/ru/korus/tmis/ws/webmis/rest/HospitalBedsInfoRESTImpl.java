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

    /**
     * Данные об регистрации на койке
     * @param actionId Идентификатор действия типа 'Движение' для которого отменяется регистрация на койке.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @GET
    @Path("/{actionId}")
    @Produces("application/x-javascript")
    public Object getInfoHospitalBedForPatient(@PathParam("actionId") int actionId) {
        return new JSONWithPadding(wsImpl.getPatientToHospitalBedById(actionId, this.auth), this.callback);
    }

    /**
     * Отмена регистрациии на койке
     * @param actionId Идентификатор действия типа 'Движение' для которого отменяется регистрация на койке.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.exception.CoreException
     */
    @GET
    @Path("/{actionId}/calloff")
    @Produces("application/x-javascript")
    public Object callOffHospitalBedForPatient(@PathParam("actionId") int actionId) {
        return new JSONWithPadding(wsImpl.callOffHospitalBedForPatient(actionId, this.auth), this.callback);
    }

    /**
     * Сервис на получение списка коек с меткой свободно/занято.
     * Url: .../hospitalbed/vacant/{departmentId}
     * Since: ver 1.0.0.57
     * @param departmentId Идентификатор отделения.
     * @return Список коек в json-формате.
     */
    @GET
    @Path("/vacant/")
    @Produces("application/x-javascript")
    public Object getVacantHospitalBeds(@QueryParam("filter[departmentId]") int departmentId) {
        //Отделение обязательное поле, если не задано в запросе, то берем из роли специалиста
        int depId = (departmentId>0) ? departmentId : this.auth.getUser().getOrgStructure().getId().intValue();
        return new JSONWithPadding(wsImpl.getVacantHospitalBeds(depId, this.auth));
    }


}
