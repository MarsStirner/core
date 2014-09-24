package ru.korus.tmis.ws.webmis.rest

import ru.korus.tmis.core.auth.AuthData
import javax.ws.rs._
import com.sun.jersey.api.json.JSONWithPadding
import ru.korus.tmis.core.data.AutoSaveInputDataContainer
import javax.ws.rs.core.{Response, MediaType}

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 4/22/14
 * Time: 2:35 PM
 */
class AutoSaveStorageREST(val wsImpl: WebMisREST, val authData: AuthData, val callback: String) {

  @POST
  @Consumes(Array("application/json", MediaType.APPLICATION_XML))
  @Produces(Array("application/x-javascript"))
  def save(data: AutoSaveInputDataContainer) = {
    new JSONWithPadding(wsImpl.saveAutoSaveField(data.id, data.text, authData), callback)
  }

  @GET
  @Path("/{id}")
  @Produces(Array("application/x-javascript", MediaType.APPLICATION_XML))
  def load(@PathParam("id") id: String) = {
    new JSONWithPadding(wsImpl.loadAutoSaveField(id, authData))
  }

  @DELETE
  @Path("/{id}")
  def delete(@PathParam("id") id: String) {
      wsImpl.deleteAutoSaveField(id, authData)
  }
}
