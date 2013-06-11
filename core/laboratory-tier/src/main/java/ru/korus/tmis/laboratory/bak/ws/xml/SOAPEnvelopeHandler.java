package ru.korus.tmis.laboratory.bak.ws.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.io.ByteArrayOutputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * Жесточайший хак, для того чтобы согласовать
 * апи с сервисом CGM - выполняется подмена префикса
 * неймспейса на soapenv
 *
 * @author anosov@outlook.com
 *         date: 6/5/13
 */
public class SOAPEnvelopeHandler implements SOAPHandler<SOAPMessageContext> {

    private static final Logger log = LoggerFactory.getLogger(SOAPEnvelopeHandler.class);

    @Override
    public boolean handleMessage(final SOAPMessageContext context) {
        final Boolean isSoapRequest = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        if (isSoapRequest) {
            final SOAPMessage message = context.getMessage();
//            writeMessage(message);
            try {
                final SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
                envelope.addNamespaceDeclaration("soapenv", "http://schemas.xmlsoap.org/soap/envelope/");
                envelope.removeAttributeNS("http://schemas.xmlsoap.org/soap/envelope/", "S");
                envelope.removeAttribute("xmlns:S");
                envelope.setPrefix("soapenv");
                message.getSOAPBody().setPrefix("soapenv");
                message.saveChanges();
                context.setMessage(message);
//                writeMessage(message);
            } catch (SOAPException e) {
                log.error("Error during modify SOAP message [correcting namespace] ", e);
            }
        }
        return true;
    }

    private void writeMessage(final SOAPMessage message) {
        try {
            final ByteArrayOutputStream requestStream = new ByteArrayOutputStream();
            message.writeTo(requestStream);
            System.out.println(requestStream.toString()/*.replaceAll("\n", " ")*/);
            log.info(requestStream.toString()/*.replaceAll("\n", " ")*/);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * The <code>handleFault</code> method is invoked for fault message
     * processing.  Refer to the description of the handler
     * framework in the JAX-WS specification for full details.
     *
     * @param context the message context
     * @return An indication of whether handler fault processing should continue
     *         for the current message
     *         <ul>
     *         <li>Return <code>true</code> to continue
     *         processing.</li>
     *         <li>Return <code>false</code> to block
     *         processing.</li>
     *         </ul>
     * @throws RuntimeException               Causes the JAX-WS runtime to cease
     *                                        handler fault processing and dispatch the fault.
     * @throws javax.xml.ws.ProtocolException Causes the JAX-WS runtime to cease
     *                                        handler fault processing and dispatch the fault.
     */
    @Override
    public boolean handleFault(final SOAPMessageContext context) {
        return true;
    }

    /**
     * Called at the conclusion of a message exchange pattern just prior to
     * the JAX-WS runtime disptaching a message, fault or exception.  Refer to
     * the description of the handler
     * framework in the JAX-WS specification for full details.
     *
     * @param context the message context
     */
    @Override
    public void close(final MessageContext context) {
    }

    /**
     * Gets the header blocks that can be processed by this Handler
     * instance.
     *
     * @return Set of <code>QNames</code> of header blocks processed by this
     *         handler instance. <code>QName</code> is the qualified
     *         name of the outermost element of the Header block.
     */
    @Override
    public Set<QName> getHeaders() {
        return new HashSet<QName>();
    }
}
