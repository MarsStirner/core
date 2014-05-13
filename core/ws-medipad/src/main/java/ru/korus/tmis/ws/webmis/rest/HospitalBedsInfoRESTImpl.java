package ru.korus.tmis.ws.webmis.rest;

import com.sun.jersey.api.json.JSONWithPadding;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.logging.slf4j.interceptor.ServicesLoggingInterceptor;
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
    private WebMisREST wsImpl;
    private AuthData auth;
    private String callback;

    public HospitalBedsInfoRESTImpl(WebMisREST wsImpl, String callback, AuthData auth) {
        this.auth = auth;
        this.wsImpl = wsImpl;
        this.callback = callback;
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
    public Object getVacantHospitalBeds(@QueryParam("filter[departmentId]") int departmentId) throws CoreException {
        //Отделение обязательное поле, если не задано в запросе, то берем из роли специалиста
        int depId = (departmentId>0) ? departmentId : this.auth.getUser().getOrgStructure().getId().intValue();
        return new JSONWithPadding(wsImpl.getVacantHospitalBeds(depId, this.auth), this.callback);
    }

    /**
     * Сервис для получения информации о доступных профилях коек
     * Url: .../hospitalbed/avaliable_profiles
     * @return Список коек в json-формате.
     */
     @GET
     @Path("/avaliable_profiles")
     @Produces("application/x-javascript")
     public Object getAvailableProfiles() throws CoreException {
         return new JSONWithPadding(wsImpl.getAllAvailableBedProfiles(auth), this.callback);
     }

    /**
     * Сервис для получения описания профиля койки
     * Url: .../hospitalbed/profile_by_id
     * @param profileId Идентификатор профиля койки.
     * @return Представления описания профиля койки в JSON.
     */
    @GET
    @Path("/profile_by_id/")
    @Produces("application/x-javascript")
    public Object getProfileNameById(@QueryParam("id")int profileId) throws CoreException {
        return new JSONWithPadding(wsImpl.getBedProfileById(profileId, auth), this.callback);
    }

}
