package ru.korus.tmis.pix.sda;

import java.util.List;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.PortInfo;

import ru.korus.tmis.hs.wss.AuthentificationHeaderHandlerResolver;

/**
 * Author: Sergey A. Zagrebelny <br>
 * Date: 17.06.13, 9:50 <br>
 * Company: Korus Consulting IT<br>
 * Description: <br>
 */
public class SdaHandlerResolver extends AuthentificationHeaderHandlerResolver {

    final private String messageUuid;

    public SdaHandlerResolver() {
        this(null, null);
    }

    public SdaHandlerResolver(String binaryToken, String messageUuid) {
        super(binaryToken);
        this.messageUuid = messageUuid;
    }


    @SuppressWarnings("rawtypes")
    public List<Handler> getHandlerChain(final PortInfo portInfo) {
        final List<Handler> handlerChain = super.getHandlerChain(portInfo);
        handlerChain.add(new SOAPHandlerSda(messageUuid));
        return handlerChain;
    }
}
