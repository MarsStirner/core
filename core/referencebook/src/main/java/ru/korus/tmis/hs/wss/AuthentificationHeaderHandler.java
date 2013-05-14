package ru.korus.tmis.hs.wss;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.io.ByteArrayOutputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        22.04.13, 18:04 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class AuthentificationHeaderHandler implements SOAPHandler<SOAPMessageContext> {

    @Override
    public boolean handleMessage(SOAPMessageContext smc) {

        Boolean outboundProperty = (Boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        // LOGIN AND PASSWORD ADD TO HEADER
        if (outboundProperty.booleanValue()) {
            SOAPMessage message = smc.getMessage();
//            writeMessage(message);
            try {
                SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
                SOAPHeader header = envelope.addHeader();
                SOAPElement security =
                        header.addChildElement("Security", "wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
                SOAPElement usernameToken =
                        security.addChildElement("UsernameToken", "wsse");
                usernameToken.addAttribute(new QName("xmlns:wsu"), "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd");
                SOAPElement username1 =
                        usernameToken.addChildElement("Username", "wsse");
                username1.addTextNode("demo");
                SOAPElement password1 =
                        usernameToken.addChildElement("Password", "wsse");
                password1.setAttribute("Type", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText");
                password1.addTextNode("demo");

                message.saveChanges();
                // writeMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return outboundProperty;
    }

    private void writeMessage(SOAPMessage message) {
        try {
            ByteArrayOutputStream requestStream = new ByteArrayOutputStream();
            message.writeTo(requestStream);
            System.out.println(requestStream.toString()/*.replaceAll("\n", " ")*/);
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
    public boolean handleFault(SOAPMessageContext context) {
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
    public void close(MessageContext context) {
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
        final HashSet<QName> names = new HashSet<QName>();
//        names.add(new QName("urn:wsdl"));
        return names;
    }
}
