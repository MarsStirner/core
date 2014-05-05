package ru.korus.tmis.ws.finance;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        02.05.14, 11:04 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class FinanceHandlerResolver implements HandlerResolver {
    @Override
    public List<Handler> getHandlerChain(PortInfo portInfo) {
        final List<Handler> handlerChain = new ArrayList<Handler>();
        handlerChain.add(new FinanceHandler());
        return handlerChain;
    }
}
