package ru.korus.tmis.rlsupdate;

import ru.korus.tmis.rlsupdate.SyncWith1C;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Author: Sergey A. Zagrebelny <br>
 * Date: 28.05.2013, 14:57:42 <br>
 * Company: Korus Consulting IT<br>
 * Description: <br>
 */

@Path("/tmis-drugstore")
@Stateless
public class RestSyncWith1C {

    @EJB
    SyncWith1C sync = null;

    @GET
    @Path("/update")
    public Response updateDragList() {
        return Response.status(Response.Status.OK).entity(sync.update()).build();
    }
}
