package ru.korus.tmis.ws.webmis.rest

import javax.ws.rs._
import javax.ws.rs.core.{Context, MediaType}
import com.sun.jersey.api.json.JSONWithPadding
import ru.korus.tmis.core.data.JobTicketContainer
import javax.ejb.{EJB, Stateless}
import javax.servlet.http.HttpServletRequest

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 5/16/14
 * Time: 6:36 PM
 */
@Stateless
class JobImpl {

  @EJB
  var wsImpl: WebMisREST = _

  @GET
  @Path("/jobTicket/{id}")
  @Produces(Array("application/x-javascript", MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON))
  def load(@Context servRequest: HttpServletRequest,
           @QueryParam("callback") callback: String,
           @PathParam("id") id: Int) = {
    new JSONWithPadding(new JobTicketContainer(
      wsImpl.getJobTicketById(id, this.wsImpl.checkTokenCookies(java.util.Arrays.asList(servRequest.getCookies:_*)))), callback)
  }
}
