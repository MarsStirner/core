package ru.korus.tmis.ws.webmis.rest;

import com.sun.jersey.api.json.JSONWithPadding;
import ru.korus.tmis.core.auth.AuthEntry;
import ru.korus.tmis.ws.impl.AuthenticationWSImpl;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;


@Singleton
@Path("/tms-auth/")
@Produces("application/json")
public class AuthenticationRESTImpl {


    @Inject
    AuthenticationWSImpl wsImpl;

    @GET
    @Path("/roles")
    @Produces("application/json")
    public Object getRoles(@QueryParam("login") String login,
                           @QueryParam("passwd") String password) {
        return wsImpl.getRoles(login, password);
    }

    @GET
    @Path("/auth")
    @Produces("application/json")
    public Object authenticate(@QueryParam("login") String userName,
                               @QueryParam("passwd") String password,
                               @QueryParam("role") int roleId) {
        return wsImpl.authenticate(userName, password, roleId);
    }

    //Первичная авторизация
    @POST
    @Path("/roles")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public Object getRoles2(AuthEntry request,
                            @QueryParam("callback") String callback) {

        JSONWithPadding returnValue = new JSONWithPadding(wsImpl.getRoles(request.login(), request.password()), callback);
        return returnValue;
    }

    //авторизация с ролью
    @POST
    @Path("/auth")
    @Consumes("application/json")
    @Produces("application/x-javascript")
    public Object authenticate2(AuthEntry request,
                                @QueryParam("callback") String callback) {
        JSONWithPadding returnValue = new JSONWithPadding(wsImpl.authenticate(request.login(), request.password(), request.roleId()), callback);
        return returnValue;
    }
}
