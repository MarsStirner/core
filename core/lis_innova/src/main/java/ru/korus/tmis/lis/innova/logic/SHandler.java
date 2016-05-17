package ru.korus.tmis.lis.innova.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.Set;

/**
 * Author: Upatov Egor <br>
 * Date: 17.05.2016, 1:48 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
public class SHandler implements SOAPHandler<SOAPMessageContext>
{

    private static final Logger log = LoggerFactory.getLogger("LIS_INNOVA");

    @Nullable
    @Override
    public Set<QName> getHeaders()
    {
        log.debug(">>>>>>>>>>> GetHeaders");
        return null;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext soapMessageContext)
    {
        log.debug(">>>>>>>>>>> HandleMessage");
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext soapMessageContext)
    {
        log.debug(">>>>>>>>>>> HandleFault");
        return true;
    }

    @Override
    public void close(MessageContext messageContext)
    {
        log.debug(">>>>>>>>>>> Close");
    }
}