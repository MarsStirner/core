package ru.korus.tmis.pix.sda;

import ru.korus.tmis.hs.wss.AuthentificationHeaderHandler;
import ru.korus.tmis.hs.wss.AuthentificationHeaderHandlerResolver;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.PortInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        17.06.13, 9:50 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class SdaHandlerResolver extends AuthentificationHeaderHandlerResolver {

    public List<Handler> getHandlerChain(final PortInfo portInfo) {
        final List<Handler> handlerChain = super.getHandlerChain(portInfo);
        handlerChain.add(new SOAPHandlerSda());
        return handlerChain;
    }
}
