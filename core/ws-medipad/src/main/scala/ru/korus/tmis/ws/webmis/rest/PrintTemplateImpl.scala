package ru.korus.tmis.ws.webmis.rest

import javax.ejb.{Stateless, EJB}
import javax.servlet.http.HttpServletRequest
import javax.ws.rs._
import javax.ws.rs.core.Context
import com.sun.jersey.api.json.JSONWithPadding
import java.{util => ju}

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 2/24/14
 * Time: 10:36 AM
 */
@Stateless
class PrintTemplateImpl {

  @EJB private var wsImpl: WebMisREST = _

  @GET
  @Path("/byIds")
  @Produces(Array[String]("application/javascript", "application/x-javascript"))
  def getPrintTemplateByIds(@Context servRequest:HttpServletRequest,
                            @QueryParam("callback") callback: String,
                            @QueryParam("id") ids: ju.List[Integer]): Object = {
    new JSONWithPadding(wsImpl.getRbPrintTemplatesByIds(ids, mkAuth(servRequest)), callback)
  }

  @GET
  @Path("/byContexts")
  @Produces(Array[String]("application/javascript", "application/x-javascript"))
  def getPrintTemplateByContexts(@Context servRequest:HttpServletRequest,
                                 @QueryParam("callback") callback: String,
                                 @QueryParam("context") contexts: ju.List[String],
                                 @QueryParam("fields") fields: String,
                                 @QueryParam("filter[render]") render: Integer): Object = {
    val f =
      if(fields != null && !fields.isEmpty)
        fields.split(',')
      else
        Array[String]()
    new JSONWithPadding(wsImpl.getRbPrintTemplatesByContexts(contexts, mkAuth(servRequest), f, render), callback)
  }

  private def mkAuth(servRequest: HttpServletRequest) = wsImpl.checkTokenCookies(ju.Arrays.asList(servRequest.getCookies:_*))

}
