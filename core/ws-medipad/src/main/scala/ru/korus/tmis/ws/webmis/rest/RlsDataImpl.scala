package ru.korus.tmis.ws.webmis.rest

import javax.ws.rs.{QueryParam, PathParam, Path, GET}
import javax.ejb.{Stateless, EJB}
import com.sun.jersey.api.json.JSONWithPadding

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 5/13/14
 * Time: 8:11 PM
 */
@Stateless
class RlsDataImpl {

  @EJB
  var wsImpl: WebMisREST = _

  @GET
  @Path("/{id}")
  def getRlsNomenById(@QueryParam("callback") callback: String,
                      @PathParam("id") id: Int): JSONWithPadding = {
    new JSONWithPadding(wsImpl.getRlsById(id), callback)
  }

  @GET
  def getRlsNomensByText(@QueryParam("text") text: String): JSONWithPadding = {
    new JSONWithPadding(wsImpl.getRlsByText(text))
  }

}
