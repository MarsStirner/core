package ru.bars.open.tmis.lis.innova.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.io.StringWriter;
import java.util.Set;

/**
 * Author: Upatov Egor <br>
 * Date: 17.05.2016, 1:48 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
public class SHandler implements SOAPHandler<SOAPMessageContext> {

    private static final Logger log = LoggerFactory.getLogger("LIS_INNOVA");

    @Nullable
    @Override
    public Set<QName> getHeaders() {
        log.debug(">>>>>>>>>>> GetHeaders");
        return null;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext soapMessageContext) {
        final boolean isOut = (Boolean) soapMessageContext.get(SOAPMessageContext.MESSAGE_OUTBOUND_PROPERTY);
        final SOAPMessage message = soapMessageContext.getMessage();
        final StringWriter sw = new StringWriter();

        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(new DOMSource(message.getSOAPPart()), new StreamResult(sw));
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
        log.info("{}\n{}", isOut ? ">>>>>>> OUTPUT SOAP >>>>>>>" : "<<<<<<< INPUT  SOAP <<<<<<<", sw.toString());
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext soapMessageContext) {
        final boolean isOut = (Boolean) soapMessageContext.get(SOAPMessageContext.MESSAGE_OUTBOUND_PROPERTY);
        final SOAPMessage message = soapMessageContext.getMessage();
        final StringWriter sw = new StringWriter();

        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(new DOMSource(message.getSOAPPart()), new StreamResult(sw));
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
        log.info("{}\n{}", isOut ? ">>>>>>> OUTPUT FAULT >>>>>>>" : "<<<<<<< INPUT  FAULT <<<<<<<", sw.toString());
        return true;
    }

    @Override
    public void close(MessageContext messageContext) {
        log.debug(">>>>>>>>>>> Close");
    }
}