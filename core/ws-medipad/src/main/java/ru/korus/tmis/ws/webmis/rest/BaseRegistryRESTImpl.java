package ru.korus.tmis.ws.webmis.rest;

import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.auth.AuthToken;
import ru.korus.tmis.core.logging.slf4j.interceptor.ServicesLoggingInterceptor;
import ru.korus.tmis.ws.impl.WebMisRESTImpl;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.interceptor.Interceptors;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import java.io.Serializable;

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

    @Inject
    WebMisRESTImpl wsImpl;

    @Context
    HttpServletRequest servRequest;

    @Path("/")
    public CustomInfoRESTImpl getCustomInfoRESTImpl(@QueryParam("token") String token,
                                                    @QueryParam("callback") String callback,
                                                    @QueryParam("limit") int limit,
                                                    @QueryParam("page") int  page,
                                                    @QueryParam("sortingField") String sortingField,
                                                    @QueryParam("sortingMethod") String sortingMethod) {
        return new CustomInfoRESTImpl(wsImpl, callback, limit, page, sortingField, sortingMethod, makeAuth(token));
    }

    @Path("/dir/")
    public DirectoryInfoRESTImpl getDirectoryInfoRESTImpl(@QueryParam("token") String token,
                                                          @QueryParam("test") String test,
                                                          @QueryParam("callback") String callback,
                                                          @QueryParam("limit") int limit,
                                                          @QueryParam("page") int  page,
                                                          @QueryParam("sortingField") String sortingField,
                                                          @QueryParam("sortingMethod") String sortingMethod) {
        //TODO: Вставлен кэйс для тестов (нужно ли?)
        this.testConstruct = (test!=null &&
                              !test.isEmpty() &&
                              (test.toLowerCase().compareTo("yes")==0 || test.toLowerCase().compareTo("true")==0)) ? true : false;
        return new DirectoryInfoRESTImpl(wsImpl, servRequest, callback, limit, page, sortingField, sortingMethod, makeAuth(token), testConstruct);
    }

    @Path("/patients/")
    public PatientRegistryRESTImpl getPatientRegistryRESTImpl(@QueryParam("token") String token,
                                                              @QueryParam("callback") String callback){
        return new PatientRegistryRESTImpl(wsImpl, callback, makeAuth(token));
    }

    @Path("/appeals/")
    public AppealsInfoRESTImpl getAppealsInfoRESTImpl(@QueryParam("token") String token,
                                                      @QueryParam("callback") String callback){
        return new AppealsInfoRESTImpl(wsImpl, callback, makeAuth(token));
    }

    @Path("/diagnostics/")
    public DiagnosticsInfoRESTImpl getDiagnosticsInfoRESTImpl(@QueryParam("token") String token,
                                                              @QueryParam("callback") String callback){
        return new DiagnosticsInfoRESTImpl(wsImpl, callback, makeAuth(token));
    }

    @Path("/hospitalbed/")
    public HospitalBedsInfoRESTImpl getHospitalBedsInfoRESTImpl(@QueryParam("token") String token,
                                                                @QueryParam("callback") String callback){
        return new HospitalBedsInfoRESTImpl(wsImpl, callback, makeAuth(token));
    }

    //__________________________________________________________________________________________________________________

    private AuthData makeAuth(String token) {
       if(TEST_MODE && token!=null && !token.isEmpty()) { //Тестовый режим
         return wsImpl.getStorageAuthData(new AuthToken(token));
       } else { //Боевой режим
         return this.wsImpl.checkTokenCookies(this.servRequest);
       }
    }

    /*protected JSONWithPadding convertToJson(Object in, String callback) {
      return new JSONWithPadding(in, callback);
    }*/

}
