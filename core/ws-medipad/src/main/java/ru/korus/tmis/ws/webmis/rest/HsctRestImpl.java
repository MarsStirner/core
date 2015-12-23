package ru.korus.tmis.ws.webmis.rest;

import com.sun.jersey.api.json.JSONWithPadding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.hsct.HsctBean;
import ru.korus.tmis.hsct.HsctRequestActionContainer;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * Author: Upatov Egor <br>
 * Date: 23.12.2015, 16:55 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: REST для ТГСК (отправка заявок во внешнюю систему при запросе с фронтенда <br>
 */
@Stateless
public class HsctRestImpl {
    private static final Logger LOGGER = LoggerFactory.getLogger("HSCT");

    @EJB
    private HsctBean hsctBean;

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({MediaType.APPLICATION_JSON, "application/javascript", "application/x-javascript"})
    public Object sendActionToHsgt(@Context HttpServletRequest servRequest, @QueryParam("callback") String callback, HsctRequestActionContainer data) {
        LOGGER.info("call sendActionToHsgt({})", data);
        return new JSONWithPadding(hsctBean.sendActionToHsct(data.getId()),  callback);
    }


}
