package ru.bars.open.tmis.lis.innova.logic;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.PortInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 17.05.2016, 16:27 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
public class JaxWsHandlerResolver implements HandlerResolver {

    @SuppressWarnings("rawtypes")
    @Override
    public List<Handler> getHandlerChain(PortInfo arg0) {
        List<Handler> hchain = new ArrayList<>();
        hchain.add(new SHandler());
        return hchain;
    }

}