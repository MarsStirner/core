package ru.korus.tmis.ws.finance;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        11.01.2013, 11:03:59 <br>
 * Company:     Korus Consulting IT<br>
 * Description: RESTful Web Service <br>
 */

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

@Path("/tmis-finance")
public class RestFinanceInfo {
    
    @GET
    @Path("/json")
    public Response getFinanceInfo(@QueryParam(ServiceFinanceInfo.WEB_PARAM_STRUCT) String nameOfStructure) {        
        Gson gson = new Gson();        
        final FinanceInfo financeInfo = new FinanceInfo();
        String res = gson.toJson(financeInfo.getFinanceInfo(nameOfStructure));
        return Response.status(Response.Status.OK).entity(res).build();

    }
}
