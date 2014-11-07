package ru.korus.tmis.ws.webmis.rest;

import com.sun.jersey.api.json.JSONWithPadding;
import ru.korus.tmis.core.auth.AuthData;
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
 * Список REST-сервисов для просмотра движений
 * Author: idmitriev Systema-Soft
 * Date: 3/22/13 6:34 PM
 * Since: 1.0.0.74
 */
@Stateless
@Interceptors(ServicesLoggingInterceptor.class)
public class HospitalBedsInfoRESTImpl {

    @EJB
    private WebMisREST wsImpl;

    /**
     * Сервис на получение списка коек с меткой свободно/занято.
     * Url: .../hospitalbed/vacant/{departmentId}
     * Since: ver 1.0.0.57
     * @param departmentId Идентификатор отделения.
     * @return Список коек в json-формате.
     */
    @GET
    @Path("/vacant/")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object getVacantHospitalBeds(@Context HttpServletRequest servRequest,
                                        @QueryParam("callback") String callback,
                                        @QueryParam("filter[departmentId]") int departmentId) throws CoreException {
        //Отделение обязательное поле, если не задано в запросе, то берем из роли специалиста
        AuthData authData = mkAuth(servRequest);
        int depId = (departmentId>0) ? departmentId : authData.getUser().getOrgStructure().getId();
        return new JSONWithPadding(wsImpl.getVacantHospitalBeds(depId, authData), callback);
    }

    /**
     * Сервис для получения информации о доступных профилях коек
     * Url: .../hospitalbed/avaliable_profiles
     * @return Список коек в json-формате.
     */
     @GET
     @Path("/avaliable_profiles")
     @Produces({"application/javascript", "application/x-javascript"})
     public Object getAvailableProfiles(@Context HttpServletRequest servRequest,
                                        @QueryParam("callback") String callback) throws CoreException {
         return new JSONWithPadding(wsImpl.getAllAvailableBedProfiles(mkAuth(servRequest)), callback);
     }

    /**
     * Сервис для получения описания профиля койки
     * Url: .../hospitalbed/profile_by_id
     * @param profileId Идентификатор профиля койки.
     * @return Представления описания профиля койки в JSON.
     */
    @GET
    @Path("/profile_by_id/")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object getProfileNameById(@Context HttpServletRequest servRequest,
                                     @QueryParam("callback") String callback,
                                     @QueryParam("id")int profileId) throws CoreException {
        return new JSONWithPadding(wsImpl.getBedProfileById(profileId, mkAuth(servRequest)), callback);
    }

    private AuthData mkAuth(HttpServletRequest servRequest) {
        return wsImpl.checkTokenCookies(Arrays.asList(servRequest.getCookies()));
    }

}
