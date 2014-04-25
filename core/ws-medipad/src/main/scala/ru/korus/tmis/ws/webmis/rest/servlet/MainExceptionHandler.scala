package ru.korus.tmis.ws.webmis.rest.servlet

import javax.servlet.http.{HttpServletResponse => Response, HttpServletRequest => Request, HttpServlet}
import javax.servlet.annotation.WebServlet
import javax.ws.rs.core.MediaType
import org.codehaus.jackson.map.ObjectMapper
import ru.korus.tmis.ws.webmis.rest.interceptors.ExceptionJSONMessage

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 3/11/14
 * Time: 8:47 PM
 */
@WebServlet(Array("/error"))
@SerialVersionUID(42l)
class MainExceptionHandler extends HttpServlet {

  override def doGet(p1: Request, p2: Response) = processError(p1, p2)

  override def doPost(p1: Request, p2: Response) = processError(p1, p2)

  override def doPut(p1: Request, p2: Response) = processError(p1, p2)

  override def doDelete(p1: Request, p2: Response) = processError(p1, p2)

  override def doHead(p1: Request, p2: Response) = processError(p1, p2)

  override def doOptions(p1: Request, p2: Response) = processError(p1, p2)

  override def doTrace(p1: Request, p2: Response) = processError(p1, p2)

  private def processError(p1: Request, p2: Response) = {
    val  throwable = p1.getAttribute("javax.servlet.error.exception").asInstanceOf[Throwable]
    val  statusCode = p1.getAttribute("javax.servlet.error.status_code").asInstanceOf[Integer]
    val  servletName = p1.getAttribute("javax.servlet.error.servlet_name").asInstanceOf[String]

    val ob = new ObjectMapper()
    val s = ob.writeValueAsString(new ExceptionJSONMessage(throwable))

    p2.setStatus(500)
    p2 setContentType MediaType.APPLICATION_JSON
    p2 setCharacterEncoding "UTF8"
    p2.getWriter.write(s)

  }
}
