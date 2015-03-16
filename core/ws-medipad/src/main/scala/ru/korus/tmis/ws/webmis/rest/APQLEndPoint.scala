package ru.korus.tmis.ws.webmis.rest

import java.util
import javax.ejb.{EJB, Stateless}
import javax.servlet.http.HttpServletRequest
import javax.ws.rs.{GET, QueryParam, POST}
import javax.ws.rs.core.Context
import scala.collection.JavaConverters._

import com.sun.jersey.api.json.JSONWithPadding
import ru.korus.tmis.core.apql.{IfThenExpr, APQLParser, APQLProcessor}
import ru.korus.tmis.core.exception.CoreException

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 9/16/14
 * Time: 6:05 PM
 */
@Stateless
class APQLEndPoint {

  @EJB private var wsImpl: WebMisREST = _

  @EJB private var apqlProcessor: APQLProcessor = _

  @POST
  def runQuery(@Context servRequest: HttpServletRequest,
               @QueryParam("callback") callback: String,
               query: String) = {
    try {
      val p = new APQLParser
      val parseResult = p.parse(query)
      parseResult match {
        case x: p.NoSuccess => throw new CoreException(x.msg)
        case x: p.Success[IfThenExpr] => apqlProcessor.process(x.get) match {
          case Some(y) => new JSONWithPadding(y.asJava, callback)
          case None => new JSONWithPadding("", callback)
        }
      }
    } catch {
      case t: Throwable => throw new Exception(t)
    }

  }

  @GET
  def legacy(@Context servRequest: HttpServletRequest,
             @QueryParam("callback") callback: String,
             @QueryParam("eventId") eventId: Int,
             @QueryParam("actionTypeId") actionTypeId: Int,
             @QueryParam("actionPropertyTypeId") actionPropertyTypeId: Int) = {
    new JSONWithPadding(wsImpl.calculateActionPropertyValue(eventId, actionTypeId, actionPropertyTypeId), callback)
  }

  private def mkAuth(servRequest: HttpServletRequest) = wsImpl.checkTokenCookies(util.Arrays.asList(servRequest.getCookies:_*))

}
