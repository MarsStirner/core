package ru.korus.tmis.pharmacy.rlsupdate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.prescription.BalanceOfGoodsInfo;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import ru.korus.tmis.pharmacy.PharmacyBeanLocal;

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
    SyncWith1C sync;

    @EJB
    PharmacyBeanLocal pharmacyBean;

    @EJB
    BalanceOfGoodsInfo balanceOfGoodsInfoBean;

    private static final Logger logger = LoggerFactory.getLogger(RestSyncWith1C.class);


    @GET
    @Path("/update")
    public Response updateDragList() {
        logger.info("REST: /tmis-drugstore-tier/tmis-drugstore/update... start");
        String res = sync.update();
        return Response.status(Response.Status.OK).entity(res).build();
    }

    @GET
    @Path("/update-balance")
    public Response updateBalance() {
        logger.info("REST: /tmis-drugstore-tier/tmis-drugstore/update-balance... start");
        String res = sync.updateBalance();
        return Response.status(Response.Status.OK).entity(res).build();
    }


    @Schedule(hour = "22", minute = "50", second = "0", persistent=false)
    public void updateDragListSchedule() {
        logger.info("Schedule 22h 50min: update RLS and balance... start");
        sync.update();
        sync.updateBalance();
    }


    @GET
    @Path("/update-tst")
    public Response updateDragListTst() {
        logger.info("REST: /tmis-drugstore-tier/tmis-drugstore/update-tst... start");
        String res = "";
        sync.UpdateStorageUuid();
        final Integer drugs[] = { 162019 };
        res += balanceOfGoodsInfoBean.update(Arrays.asList(drugs)) ? "OK" : "ERROR";
        return Response.status(Response.Status.OK).entity(res).build();
    }
    
    @GET
    @Path("/send-pre")
    public Response sendPrescription() {
        logger.info("REST: /tmis-drugstore-tier/tmis-drugstore/send-pre... start");
        String res = "OK";
        try {
            pharmacyBean.sendPrescriptionTo1C();
        } catch (CoreException e) {
            res = e.getMessage();
        }
        return Response.status(Response.Status.OK).entity(res).build();
    }

}
