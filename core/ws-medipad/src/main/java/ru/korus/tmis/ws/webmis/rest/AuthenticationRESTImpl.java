package ru.korus.tmis.ws.webmis.rest;

import com.sun.jersey.api.json.JSONWithPadding;
import org.codehaus.jackson.map.ObjectMapper;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.auth.AuthEntry;
import ru.korus.tmis.core.auth.AuthStorageBeanLocal;
import ru.korus.tmis.core.auth.TokenInfoRequest;
import ru.korus.tmis.core.data.RoleData;
import ru.korus.tmis.core.database.DbStaffBeanLocal;
import ru.korus.tmis.core.entity.model.Role;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.exception.AuthenticationException;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.ws.medipad.AuthenticationWebService;
import ru.korus.tmis.ws.webmis.rest.interceptors.ExceptionJSONMessage;

import javax.ejb.EJB;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * Description: Сервисы авторизации
 */
@Singleton
@Path("/tms-auth/")
@Produces("application/json")
public class AuthenticationRESTImpl {


    @EJB
    AuthenticationWebService wsImpl;

    @EJB
    WebMisREST webmisImpl;
    @EJB
    AuthStorageBeanLocal authStorage;
    @EJB
    private DbStaffBeanLocal dbStaffBeanLocal;

    /**
     * Сервис по получению доступных ролей для пользователя (первичная авторизация)
     *
     * @param login    Логин пользователя.
     * @param password Пароль пользователя.
     * @return ru.korus.tmis.core.data.RoleData как Object. Контейнер с данными о пользователе и списком доступных ролей.
     * @throws AuthenticationException
     * @see AuthenticationException
     * @see RoleData
     */
    @GET
    @Path("/roles")
    @Produces("application/json")
    public Object getRoles(
            @QueryParam("login") String login, @QueryParam("passwd") String password
    ) throws AuthenticationException {
        return wsImpl.getRoles(login, password);
    }

    /**
     * Сервис авторизации пользователя в системе TMIS (авторизация с ролью)
     *
     * @param userName Логин пользователя.
     * @param password Пароль пользователя.
     * @param roleId   Идентификатор роли пользователя.
     * @return ru.korus.tmis.core.auth.AuthData как Object. Контейнер с авторизационными данными о пользователе.
     * @throws AuthenticationException
     * @see AuthenticationException
     * @see AuthData
     */
    @GET
    @Path("/auth")
    @Produces("application/json")
    public Object authenticate(
            @QueryParam("login") String userName, @QueryParam("passwd") String password, @QueryParam("role") int roleId
    ) throws AuthenticationException {
        return wsImpl.authenticate(userName, password, roleId);
    }

    /**
     * Сервис по получению доступных ролей для пользователя (первичная авторизация)
     *
     * @param request  Авторизационные данные пользователя в виде контейнера: AuthEntry.
     * @param callback callback запроса.
     * @return ru.korus.tmis.core.data.RoleData как Object. Контейнер с данными о пользователе и списком доступных ролей.
     * @see AuthenticationException
     * @see ru.korus.tmis.core.data.RoleData
     * @see AuthEntry
     */
    @POST
    @Path("/roles")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/javascript", "application/x-javascript", "application/xml"})
    public Object getRoles2(
            AuthEntry request, @QueryParam("callback") String callback
    ) {
        try {
            return new JSONWithPadding(wsImpl.getRoles(request.login(), request.password()), callback);
        } catch (AuthenticationException e) {
            return new ExceptionJSONMessage(e);
        }
    }

    @GET
    @Path("/user-info")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/javascript", "application/x-javascript", "application/xml"})
    public Object getAuthData(
            @Context HttpServletRequest servletRequest, @QueryParam("callback") String callback
    ) throws AuthenticationException {
        try {
            return new JSONWithPadding(authStorage.checkTokenCookies(servletRequest.getCookies()), callback);
        } catch (CoreException e) {
            throw new AuthenticationException("Недопустимый токен");
        }
    }

    /**
     * Сервис авторизации пользователя в системе TMIS (авторизация с ролью)
     *
     * @param request  Авторизационные данные пользователя в виде контейнера: AuthEntry.
     * @param callback callback запроса.
     * @return ru.korus.tmis.core.auth.AuthData как Object. Контейнер с авторизационными данными о пользователе.
     * @throws AuthenticationException
     * @see AuthenticationException
     * @see AuthData
     * @see AuthEntry
     */
    @POST
    @Path("/auth")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/javascript", "application/x-javascript", "application/xml"})
    public Object authenticate2(
            AuthEntry request, @QueryParam("callback") String callback
    ) throws AuthenticationException {
        return new JSONWithPadding(wsImpl.authenticate(request.login(), request.password(), request.roleId()), callback);
    }

    /**
     * Сервис авторизации пользователя в системе TMIS (авторизация по токену с ролью)
     *
     * @param callback callback запроса.
     * @return ru.korus.tmis.core.auth.AuthData как Object. Контейнер с авторизационными данными о пользователе.
     * @throws AuthenticationException
     * @see AuthenticationException
     * @see AuthData
     * @see AuthEntry
     */
    @POST
    @Path("/changeRole")
    @Consumes("application/json")
    @Produces({"application/javascript", "application/x-javascript"})
    public Object authenticate3(
            @Context HttpServletRequest servRequest, @QueryParam("roleId") int roleId, @QueryParam("callback") String callback
    ) throws AuthenticationException {
        AuthData authData = webmisImpl.checkTokenCookies(servRequest.getCookies());
        Staff staff =  dbStaffBeanLocal.getStaffById(authData.getUserId());
        if (staff == null) {
            throw new AuthenticationException();
        }
        for (Role r : staff.getRoles()) {
            if (r.getId().equals(roleId)) {
                authData.setUserRole(r);
                return new JSONWithPadding(authData, callback);
            }
        }
        throw new AuthenticationException("Недопустимая роль (id = " + roleId + ") для сотрудника " + staff.getFullName());
    }



    @POST
    @Path("/getTokenInfo")
    @Consumes({"application/json", "application/xml"})
    @Produces({"application/javascript", "application/x-javascript", "application/xml", MediaType.APPLICATION_JSON})
    public Object getTokenInfo(TokenInfoRequest request) throws AuthenticationException, IOException {
        try {
            return new ObjectMapper().writeValueAsString(authStorage.checkToken(request.getToken(), request.getProlong()));
        } catch (CoreException e) {
            throw new AuthenticationException("Недопустимый токен");
        }
    }

}
