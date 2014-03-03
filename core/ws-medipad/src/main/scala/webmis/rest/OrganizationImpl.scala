package webmis.rest

import ru.korus.tmis.ws.impl.WebMisRESTImpl
import ru.korus.tmis.core.auth.AuthData
import javax.ws.rs.{Produces, PathParam, Path, GET}
import com.sun.jersey.api.json.JSONWithPadding

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 3/3/14
 * Time: 6:55 PM
 */
class OrganizationImpl(val wsImpl: WebMisRESTImpl, val authData: AuthData, val callback: String)  {

  @GET
  @Path("/{id}")
  @Produces(Array[String]("application/x-javascript"))
  def getOrganizationById(@PathParam("id") id: Int): JSONWithPadding = {
    val l = new JSONWithPadding(wsImpl.getOrganizationById(id, authData), callback)
    l
  }

}
