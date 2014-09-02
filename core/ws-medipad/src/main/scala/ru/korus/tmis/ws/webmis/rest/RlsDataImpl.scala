package ru.korus.tmis.ws.webmis.rest

import ru.korus.tmis.core.auth.AuthData
import javax.ws.rs.{QueryParam, PathParam, Path, GET}
import ru.korus.tmis.core.database.DbRlsBeanLocal
import javax.ejb.EJB
import com.sun.jersey.api.json.JSONWithPadding

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 5/13/14
 * Time: 8:11 PM
 */
class RlsDataImpl(val wsImpl: WebMisREST, val authData: AuthData, val callback: String) {

  @EJB
  var dbRlsBean: DbRlsBeanLocal = _

  @GET
  @Path("/{id}")
  def getRlsNomenById(@PathParam("id") id: Int) = {
    new JSONWithPadding(wsImpl.getRlsById(id), callback)
  }

  @GET
  def getRlsNomensByText(@QueryParam("text") text: String) = {
    new JSONWithPadding(wsImpl.getRlsByText(text))
  }

}
