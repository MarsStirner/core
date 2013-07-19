package ru.korus.tmis.pix.sda;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Author: Sergey A. Zagrebelny <br>
 * Date: 17.06.13, 9:15 <br>
 * Company: Korus Consulting IT<br>
 * Description: <br>
 */
public class SOAPHandlerSda implements SOAPHandler<SOAPMessageContext> {
    NodeList tmp;
    Element tmp1;
    Element tmp2;
    Element tmp3;
    /**
     * Обработчик входных/выходных сообщений SOAP. Переоперделен с целью удаления пространства имен ns2:wsdl
     * 
     * @see SOAPHandler#handleMessage(javax.xml.ws.handler.MessageContext)
     */
    @Override
    public boolean handleMessage(final SOAPMessageContext smc) {
        Boolean outboundProperty = (Boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if (outboundProperty.booleanValue()) { // если сообщение на отправку
            SOAPMessage message = smc.getMessage();
            try {

                SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
                SOAPBody body = envelope.getBody(); // Теле сообщения

                Element nodeWithNameSpace = (Element) body.getFirstChild(); // элемент в пространстве имен ns2:wsdl
                tmp2 = nodeWithNameSpace;
                tmp3 = (Element)nodeWithNameSpace.cloneNode(true);
                NodeList nodeList = nodeWithNameSpace.getChildNodes();
                tmp = nodeList;
                // Создаем новый элемент с пустым пространством имен
                Element cont = body.getOwnerDocument().createElement("Container");

                tmp1= cont;
                // копируем в новый элемент узлы из SOAP сообщения
                for (Node el : getChildsSda(nodeWithNameSpace)) {
                    cont.appendChild(el);
                }

                // Заменяем элемент из SOAP сообщения на элемент с пустым пространством имен
                body.replaceChild(cont, nodeWithNameSpace);

                message.saveChanges();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return outboundProperty;
    }

    private List<Node> getChildsSda(Element nodeWithNameSpace) {
        List<Node> res = new Vector<Node>();
        final String[] sdaNodes = {"Patient", "Encounters", "Allergies", "Diagnoses", "Documents", "SendingFacility"};
        for(String nodeName : sdaNodes) {
            NodeList el = nodeWithNameSpace.getElementsByTagName(nodeName);
            if (el.getLength() > 0) {
                res.add(el.item(0));
            }
        }
        return res;
    }

    /**
     * Called at the conclusion of a message exchange pattern just prior to the JAX-WS runtime disptaching a message, fault or exception. Refer to the
     * description of the handler framework in the JAX-WS specification for full details.
     * 
     * @param context
     *            the message context
     */
    @Override
    public void close(final MessageContext context) {
    }

    /**
     * The <code>handleFault</code> method is invoked for fault message processing. Refer to the description of the handler framework in the JAX-WS
     * specification for full details.
     * 
     * @param context
     *            the message context
     * @return An indication of whether handler fault processing should continue for the current message
     *         <ul>
     *         <li>Return <code>true</code> to continue processing.</li>
     *         <li>Return <code>false</code> to block processing.</li>
     *         </ul>
     * @throws RuntimeException
     *             Causes the JAX-WS runtime to cease handler fault processing and dispatch the fault.
     * @throws javax.xml.ws.ProtocolException
     *             Causes the JAX-WS runtime to cease handler fault processing and dispatch the fault.
     */
    @Override
    public boolean handleFault(final SOAPMessageContext context) {
        return true;
    }

    /**
     * Gets the header blocks that can be processed by this Handler instance.
     * 
     * @return Set of <code>QNames</code> of header blocks processed by this handler instance. <code>QName</code> is the qualified name of the outermost element
     *         of the Header block.
     */
    @Override
    public Set<QName> getHeaders() {
        final HashSet<QName> names = new HashSet<QName>();
        return names;
    }
}