package ru.korus.tmis.hs.wss;


import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        22.04.13, 17:58 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class AuthentificationHeaderHandlerResolver implements HandlerResolver {

    public List<Handler> getHandlerChain(final PortInfo portInfo) {
        final List<Handler> handlerChain = new ArrayList<Handler>();
        handlerChain.add(new AuthentificationHeaderHandler());
        return handlerChain;
    }
}