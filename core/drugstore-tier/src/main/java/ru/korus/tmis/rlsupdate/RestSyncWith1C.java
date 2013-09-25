package ru.korus.tmis.rlsupdate;

import ru.korus.tmis.rlsupdate.SyncWith1C;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.Arrays;

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

    @EJB
    BalanceOfGoodsInfoBean balanceOfGoodsInfoBean = null;


    @GET
    @Path("/update")
    @Schedule(hour = "1", minute = "33")
    public Response updateDragList() {
        String res = sync.update();
        res += sync.updateBalance();
        return Response.status(Response.Status.OK).entity(res).build();
    }

    @GET
    @Path("/update-tst")
    public Response updateDragListTst() {
        String res = sync.update();
        final Integer drugs[] = { 1, 2};
        res += balanceOfGoodsInfoBean.update(Arrays.asList(drugs)) ? "OK" : "ERROR";
        return Response.status(Response.Status.OK).entity(res).build();
    }
}
