package ru.korus.tmis.ws.webmis.rest

import ru.korus.tmis.core.auth.AuthData
import javax.ws.rs.{PathParam, Produces, Path, GET}
import javax.ws.rs.core.MediaType
import com.sun.jersey.api.json.JSONWithPadding
import ru.korus.tmis.core.data.JobTicketContainer

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 5/16/14
 * Time: 6:36 PM
 */
class JobImpl(val wsImpl: WebMisREST, val authData: AuthData, val callback: String) {



  @GET
  @Path("/jobTicket/{id}")
  @Produces(Array("application/x-javascript", MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON))
  def load(@PathParam("id") id: Int) = {
    new JSONWithPadding(new JobTicketContainer(wsImpl.getJobTicketById(id, authData)))
  }

}
