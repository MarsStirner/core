package ru.korus.tmis.laboratory.bak;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 7/11/14
 * Time: 6:32 PM
 */
@Path("/")
public class BakWebService extends javax.ws.rs.core.Application {


    @GET
    @Path("/receive")
    @Produces(MediaType.TEXT_PLAIN)
    public String getHello() {
        return "Lab3";
    }
}
