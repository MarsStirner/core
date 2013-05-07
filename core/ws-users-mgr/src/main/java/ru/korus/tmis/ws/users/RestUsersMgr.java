package ru.korus.tmis.ws.users;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import ru.korus.tmis.core.entity.model.Staff;

import com.google.gson.Gson;

@Stateless
@Path("/api")
public class RestUsersMgr {

    @EJB
    private UsersMgr usersMgr;

    @EJB
    private RolesMgr rolesMgr;

    @POST
    @Consumes("application/json")
    @Path("/users/auth")
    public Response auth(JsonAuthPerson jsonAuthPerson) {
        String errMsg = "Username or password are incorrect";
        if (jsonAuthPerson.getLogin() == null || jsonAuthPerson.getPassword() == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(UsersMgr.error(errMsg)).build();
        }
        String res = usersMgr.auth(jsonAuthPerson.getLogin(), jsonAuthPerson.getPassword());
        Response.Status status = Response.Status.OK;
        if (res == null) {
            res = UsersMgr.error(errMsg);
            status = Response.Status.INTERNAL_SERVER_ERROR;
        }
        return Response.status(status).entity(res).build();
    }

    @POST
    @Consumes("application/json")
    @Path("/users")
    public Response create(JsonPerson jsonNewPerson) {
        if (jsonNewPerson.getLogin() == null
                || jsonNewPerson.getPassword() == null
                || jsonNewPerson.getFname() == null
                || jsonNewPerson.getPname() == null
                || jsonNewPerson.getLname() == null
                || jsonNewPerson.getPosition() == null
                || jsonNewPerson.getSubdivision() == null) {
            return errorParamMissing();
        }

        String res = usersMgr.create(jsonNewPerson);
        Response.Status status = Response.Status.OK;
        if (res == null) {
            res = "unable to create the new person";
            status = Response.Status.INTERNAL_SERVER_ERROR;
        }
        return Response.status(status).entity(res).build();
    }

    /**
     * @return
     */
    private Response errorParamMissing() {
        return Response.status(Response.Status.BAD_REQUEST).entity(UsersMgr.error("Some mandatory parameters are missing")).build();
    }

    @GET
    @Path("/users")
    public Response getAll() {
        Gson gson = new Gson();
        String res = gson.toJson(usersMgr.getAll());
        return Response.status(Response.Status.OK).entity(res).build();
    }

    @GET
    @Path("/users/{token}")
    public Response get(@PathParam(value = "token") String token) {
        Gson gson = new Gson();
        final JsonPerson userData = usersMgr.getByUUID(token);
        String res = userData == null ? UsersMgr.error("User with selected UUID does not exist or disconnected") : gson.toJson(userData);
        return Response.status(Response.Status.OK).entity(res).build();
    }

    @PUT
    @Consumes("application/json")
    @Path("/users/{token}")
    public Response update(@PathParam(value = "token") String token, JsonPerson jsonNewPerson) {
        if (jsonNewPerson == null || (jsonNewPerson.getLogin() == null
                && jsonNewPerson.getPassword() == null
                && jsonNewPerson.getFname() == null
                && jsonNewPerson.getPname() == null
                && jsonNewPerson.getLname() == null
                && jsonNewPerson.getPosition() == null
                && jsonNewPerson.getSubdivision() == null
                && (jsonNewPerson.getRoles() == null || jsonNewPerson.getRoles().isEmpty())
                )) {
            return Response.status(Response.Status.OK).entity(UsersMgr.error("At least 1 param required")).build();
        }

        if (usersMgr.isLoginUsed(jsonNewPerson.getLogin())) {
            return Response.status(Response.Status.OK).entity(UsersMgr.error("The user's login already exists")).build();
        }

        final Staff user = usersMgr.getStaffByUUID(token);
        if (user == null) {
            return Response.status(Response.Status.OK).entity(UsersMgr.error("User with selected UUID does not exist or disconnected")).build();
        }

        usersMgr.updateStaff(user, jsonNewPerson);

        return Response.status(Response.Status.OK).entity(UsersMgr.ok()).build();
    }

    @DELETE
    @Path("/users/{token}")
    public Response delete(@PathParam(value = "token") String token) {
        final Staff user = usersMgr.getStaffByUUID(token);
        if (user == null) {
            return Response.status(Response.Status.OK).entity(UsersMgr.error("User with selected UUID does not exist or disconnected")).build();
        }

        usersMgr.deleteStaff(user, token);

        return Response.status(Response.Status.OK).entity(UsersMgr.ok()).build();
    }

    @POST
    @Consumes("application/json")
    @Path("/roles")
    public Response createRole(JsonRole jsonRole) {
        if (jsonRole == null ||
                jsonRole.getCode() == null ||
                jsonRole.getTitle() == null) {
            return errorParamMissing();
        }
        String res = rolesMgr.create(jsonRole);
        Response.Status status = Response.Status.OK;
        if (res == null) {
            res = "unable to create the new role";
            status = Response.Status.INTERNAL_SERVER_ERROR;
        }
        return Response.status(status).entity(res).build();
    }

    @GET
    @Path("/roles")
    public Response getRoles() {

        return Response.status(Response.Status.OK).entity(rolesMgr.getAll()).build();
    }

    @GET
    @Path("/roles/{code}")
    public Response getRole(@PathParam(value = "code") String code) {
        return Response.status(Response.Status.OK).entity(rolesMgr.getRoleInfo(code)).build();
    }

    @PUT
    @Consumes("application/json")
    @Path("/roles/{code}")
    public Response updateRole(@PathParam(value = "code") String code, JsonRole jsonRole) {
        if (jsonRole == null ||
                (jsonRole.getCode() == null &&
                jsonRole.getTitle() == null)) {
            return errorParamMissing();
        }
        return Response.status(Response.Status.OK).entity(rolesMgr.updateRoleInfo(code, jsonRole)).build();
    }

    @DELETE
    @Path("/roles/{code}")
    public Response deleteRole(@PathParam(value = "code") String code) {
        return Response.status(Response.Status.OK).entity(rolesMgr.deleteRole(code)).build();
    }

    @GET
    @Path("/roles/{code}/users")
    public Response getUsersByRole(@PathParam(value = "code") String code) {
        return Response.status(Response.Status.OK).entity(rolesMgr.getUsersByCode(code)).build();
    }

    @POST
    @Consumes("application/json")
    @Path("/users/roles/")
    public Response addRole(JsonRoleMgr jsonRoleMgr) {
        if (jsonRoleMgr == null ||
                (jsonRoleMgr.getPersonUuid() == null &&
                jsonRoleMgr.getRoleCode() == null)) {
            return errorParamMissing();
        }
        return Response.status(Response.Status.OK).entity(rolesMgr.addRole(jsonRoleMgr)).build();

    }

    @GET
    @Path("/users/{token}/roles/")
    public Response getRolesByUser(@PathParam(value = "token") String token) {
        return Response.status(Response.Status.OK).entity(rolesMgr.getRolesByUser(token)).build();
    }

    @DELETE
    @Path("/users/{token}/roles/{code}")
    public Response getRolesByUser(@PathParam(value = "token") String token, @PathParam(value = "code") String code) {
        return Response.status(Response.Status.OK).entity(rolesMgr.removeRolesForUser(token, code)).build();
    }
}
