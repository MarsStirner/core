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
        return new JSONWithPadding(wsImpl.getVacantHospitalBeds(depId, this.auth), this.callback);
    }

    /**
     * Сервис для получения информации о доступных для отделения профилей коек
     * Url: .../hospitalbed/avaliable_profiles
     * @param departmentId Идентификатор отделения.
     * @return Список коек в json-формате.
     */
     @GET
     @Path("/avaliable_profiles")
     @Produces("application/x-javascript")
     public Object getAvaliableProfiles(@QueryParam("filter[departmentId]") int departmentId) {
         return new JSONWithPadding("{ \"avaliable\": \"none\"}");
     }

    /**
     * Сервис для получения текстового представления профиля койки по id
     * Url: .../hospitalbed/profilebyid
     * @param profileId Идентификатор профиля койки.
     * @return Текстовое представления профиля (название профиля).
     */
    @GET
    @Path("/profile_by_id/")
    @Produces("application/x-javascript")
    public Object getProfileNameById(@QueryParam("id")int profileId) {
        return new JSONWithPadding(wsImpl.getBedProfileNameById(profileId));
    }

}
