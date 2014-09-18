package ru.korus.tmis.ws.webmis.rest

import java.util
import javax.ejb.{EJB, Stateless}
import javax.servlet.http.HttpServletRequest
import javax.ws.rs.core.Context

import ru.korus.tmis.core.auth.AuthData
import javax.ws.rs._
import com.sun.jersey.api.json.JSONWithPadding

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 3/3/14
 * Time: 6:55 PM
 */
@Stateless
class OrganizationImpl {

  @EJB private var wsImpl: WebMisREST = _

  @GET
  @Path("/{id}")
  @Produces(Array[String]("application/x-javascript"))
  def getOrganizationById(@Context servRequest:HttpServletRequest,
                          @QueryParam("callback") callback: String,
                          @PathParam("id") id: Int): JSONWithPadding = {
    new JSONWithPadding(wsImpl.getOrganizationById(id, wsImpl.checkTokenCookies(util.Arrays.asList(servRequest.getCookies:_*))), callback)
  }

}
