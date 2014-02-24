package webmis.rest

import javax.ws.rs._
import com.sun.jersey.api.json.JSONWithPadding
import ru.korus.tmis.ws.impl.WebMisRESTImpl
import ru.korus.tmis.core.auth.AuthData

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 2/24/14
 * Time: 10:36 AM
 */
class PrintTemplateImpl(val wsImpl: WebMisRESTImpl, val authData: AuthData, val callback: String) {

  @GET
  @Path("/byIds")
  @Produces(Array("application/x-javascript"))
  def getPrintTemplateByIds(@QueryParam("id") ids: Array[Int]): Object = {
    new JSONWithPadding(wsImpl.getRbPrintTemplatesByIds(ids, authData), callback)
  }

  @GET
  @Path("/byContexts")
  @Produces(Array("application/x-javascript"))
  def getPrintTemplateByContexts(contexts: Array[String]): Object = {
    new JSONWithPadding()
  }

}
