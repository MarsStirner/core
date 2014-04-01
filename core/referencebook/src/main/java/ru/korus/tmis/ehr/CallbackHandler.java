package ru.korus.tmis.ehr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        05.03.14, 10:06 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class CallbackHandler implements SOAPHandler<SOAPMessageContext> {

    private static final Logger logger = LoggerFactory.getLogger(CallbackHandler.class);
    public static final String RELATES_TO = "ru.korus.tmis.ehr.CallbackHandler.MessageId";

    @Override
    public Set<QName> getHeaders() {
        final HashSet<QName> names = new HashSet<QName>();
//        names.add(new QName("urn:wsdl"));
        return names;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        Boolean outboundProperty = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        if (!outboundProperty) {
            SOAPMessage message = context.getMessage();
            try {
                SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
                final QName messageID = new QName("http://www.w3.org/2005/08/addressing", "RelatesTo");
                final Iterator iterator = envelope.getHeader().getChildElements(messageID);
                if (iterator.hasNext()) {
                    SOAPHeaderElement element = (SOAPHeaderElement) iterator.next();
                    final String messageUuid = element.getTextContent().trim().toLowerCase();
                    logger.info("latest RelatesTo ID: " + messageUuid);
                    context.put(CallbackHandler.RELATES_TO, messageUuid);
                    context.setScope(CallbackHandler.RELATES_TO, MessageContext.Scope.APPLICATION);
                }
            } catch (SOAPException e) {
                logger.warn("Exception in CallbackHandler.handleMessage: ", e);
            }
        }
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void close(MessageContext context) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
