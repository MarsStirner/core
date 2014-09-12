package ru.korus.tmis.ws.webmis.rest

import java.util
import java.util.Arrays
import javax.ejb.{Stateless, EJB}
import javax.servlet.http.HttpServletRequest

import ru.korus.tmis.core.auth.AuthData
import javax.ws.rs._
import com.sun.jersey.api.json.JSONWithPadding
import ru.korus.tmis.core.data.AutoSaveInputDataContainer
import javax.ws.rs.core.{Context, Response, MediaType}

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 4/22/14
 * Time: 2:35 PM
 */
@Stateless
class AutoSaveStorageREST {

  @EJB private var wsImpl: WebMisREST = _

  @POST
  @Consumes(Array("application/json", MediaType.APPLICATION_XML))
  @Produces(Array("application/x-javascript"))
  def save(@Context servRequest: HttpServletRequest,
           @QueryParam("callback") callback: String,
           data: AutoSaveInputDataContainer) = {
    new JSONWithPadding(wsImpl.saveAutoSaveField(data.id, data.text, mkAuth(servRequest)), callback)
  }

  @GET
  @Path("/{id}")
  @Produces(Array("application/x-javascript", MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON))
  def load(@Context servRequest: HttpServletRequest,
           @PathParam("id") id: String) = {
    new JSONWithPadding(wsImpl.loadAutoSaveField(id, mkAuth(servRequest)))
  }

  @DELETE
  @Path("/{id}")
  def delete(@Context servRequest: HttpServletRequest,
             @PathParam("id") id: String) {
      wsImpl.deleteAutoSaveField(id, mkAuth(servRequest))
  }

  private def mkAuth(servRequest: HttpServletRequest) = wsImpl.checkTokenCookies(util.Arrays.asList(servRequest.getCookies:_*))
}
