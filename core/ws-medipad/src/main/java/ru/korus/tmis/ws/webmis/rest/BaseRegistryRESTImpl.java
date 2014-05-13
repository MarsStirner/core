package ru.korus.tmis.ws.webmis.rest;

import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.auth.AuthToken;
import ru.korus.tmis.core.logging.slf4j.interceptor.ServicesLoggingInterceptor;

import javax.ejb.EJB;
import javax.inject.Singleton;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Базовый класс для registry REST-сервисов
 * Author: idmitriev Systema-Soft
 * Date: 3/21/13 13:58 PM
 * Since: 1.0.0.74
 */
@Interceptors(ServicesLoggingInterceptor.class)
@Singleton
@Path("/tms-registry/")
@Produces("application/json")
public class BaseRegistryRESTImpl implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String PATH = "/tms-registry/";

    private static final boolean TEST_MODE = true;

    private boolean testConstruct = false;

    @EJB
    WebMisREST wsImpl;

    @Path("/")
    public CustomInfoRESTImpl getCustomInfoRESTImpl(@Context HttpServletRequest servRequest,
                                                    @QueryParam("token") String token,
                                                    @QueryParam("callback") String callback,
                                                    @QueryParam("limit") int limit,
                                                    @QueryParam("page") int  page,
                                                    @QueryParam("sortingField") String sortingField,
                                                    @QueryParam("sortingMethod") String sortingMethod) {
        return new CustomInfoRESTImpl(wsImpl, callback, limit, page, sortingField, sortingMethod, makeAuth(token, servRequest));
    }

    @Path("/dir/")
    public DirectoryInfoRESTImpl getDirectoryInfoRESTImpl(@Context HttpServletRequest servRequest,
                                                          @QueryParam("token") String token,
                                                          @QueryParam("test") String test,
                                                          @QueryParam("callback") String callback,
                                                          @QueryParam("limit") int limit,
                                                          @QueryParam("page") int  page,
                                                          @QueryParam("sortingField") String sortingField,
                                                          @QueryParam("sortingMethod") String sortingMethod) {
        //TODO: Вставлен кэйс для тестов (нужно ли?)
        this.testConstruct = (test != null &&
                !test.isEmpty() &&
                (test.toLowerCase().compareTo("yes") == 0 || test.toLowerCase().compareTo("true") == 0));
        return new DirectoryInfoRESTImpl(wsImpl, servRequest, callback, limit, page, sortingField, sortingMethod, makeAuth(token, servRequest), testConstruct);
    }

    @Path("/patients/")
    public PatientRegistryRESTImpl getPatientRegistryRESTImpl(@Context HttpServletRequest servRequest,
                                                              @QueryParam("token") String token,
                                                              @QueryParam("callback") String callback){
        return new PatientRegistryRESTImpl(wsImpl, callback, makeAuth(token, servRequest));
    }

    @Path("/appeals/")
    public AppealsInfoRESTImpl getAppealsInfoRESTImpl(@Context HttpServletRequest servRequest,
                                                      @QueryParam("token") String token,
                                                      @QueryParam("callback") String callback){
        return new AppealsInfoRESTImpl(wsImpl, callback, makeAuth(token, servRequest));
    }

    @Path("/diagnostics/")
    public DiagnosticsInfoRESTImpl getDiagnosticsInfoRESTImpl(@Context HttpServletRequest servRequest,
                                                              @QueryParam("token") String token,
                                                              @QueryParam("callback") String callback){
        return new DiagnosticsInfoRESTImpl(wsImpl, callback, makeAuth(token, servRequest));
    }

    @Path("/hospitalbed/")
    public HospitalBedsInfoRESTImpl getHospitalBedsInfoRESTImpl(@Context HttpServletRequest servRequest,
                                                                @QueryParam("token") String token,
                                                                @QueryParam("callback") String callback){
        return new HospitalBedsInfoRESTImpl(wsImpl, callback, makeAuth(token, servRequest));
    }

    @Path("/autosave")
    public AutoSaveStorageREST getAutoSaveStarage(
                                                @Context HttpServletRequest servRequest,
                                                @QueryParam("token") String token,
                                                @QueryParam("callback") String callback) {
        return new AutoSaveStorageREST(wsImpl, makeAuth(token, servRequest), callback);
    }


    //__________________________________________________________________________________________________________________

    private AuthData makeAuth(String token, HttpServletRequest servRequest) {
       if(TEST_MODE && token!=null && !token.isEmpty()) { //Тестовый режим
         return wsImpl.getStorageAuthData(new AuthToken(token));
       } else { //Боевой режим
         return this.wsImpl.checkTokenCookies(Arrays.asList(servRequest.getCookies()));
       }
    }

    /*protected JSONWithPadding convertToJson(Object in, String callback) {
      return new JSONWithPadding(in, callback);
    }*/

}
