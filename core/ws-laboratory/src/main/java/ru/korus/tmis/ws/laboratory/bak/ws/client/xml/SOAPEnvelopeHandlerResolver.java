package ru.korus.tmis.ws.laboratory.bak.ws.client.xml;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * @author anosov@outlook.com
 *         date: 6/5/13
 */
public class SOAPEnvelopeHandlerResolver implements HandlerResolver {

    public List<Handler> getHandlerChain(final PortInfo portInfo) {
        final List<Handler> handlerChain = new ArrayList<Handler>();
        handlerChain.add(new SOAPEnvelopeHandler());
        return handlerChain;
    }
}
