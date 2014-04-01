package ru.korus.tmis.pix.sda;

import java.util.*;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.korus.tmis.scala.util.ConfigManager;

/**
 * Author: Sergey A. Zagrebelny <br>
 * Date: 17.06.13, 9:15 <br>
 * Company: Korus Consulting IT<br>
 * Description: <br>
 */
public class SOAPHandlerSda implements SOAPHandler<SOAPMessageContext> {

    final private String messageUuid;

    public SOAPHandlerSda(final String messageUuid) {
        this.messageUuid = messageUuid;
    }

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
                removeNamespace(envelope);
                addWsAddressing(envelope);
                message.saveChanges();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return outboundProperty;
    }

    /*************************************************************
     Заголовок JAX-WS
     <To xmlns="http://www.w3.org/2005/08/addressing">http://37.139.9.166:57772/csp/healthshare/hsedgesda/isc.emr.EMRReceiverService.cls</To>
     <Action xmlns="http://www.w3.org/2005/08/addressing">urn:wsdl:EMRReceiverServiceSoap:containerRequest</Action>
     <ReplyTo xmlns="http://www.w3.org/2005/08/addressing">
        <Address>http://www.w3.org/2005/08/addressing/anonymous</Address>
     </ReplyTo>
     <FaultTo xmlns="http://www.w3.org/2005/08/addressing">
        <Address>http://www.w3.org/2005/08/addressing/anonymous</Address>
     </FaultTo>
     <MessageID xmlns="http://www.w3.org/2005/08/addressing">uuid:45e85752-559f-4723-9f05-f205e5a6ab24</MessageID>     *************************************************************/

    /*************************************************************
      Пример SOAP-заголовка запроса:
        <s:Envelope xmlns:s="http://www.w3.org/2003/05/soap-envelope" xmlns:wsa="http://www.w3.org/2005/08/addressing">
            <s:Header>
                <wsa:MessageID>urn:uuid:6d296e90-e5dc-43d0-b455-7c1f3e111111</wsa:MessageID>
                <wsa:ReplyTo>
                   <wsa:Address>http://1.1.1.1:57772/csp/iemk/isc.emr.IEMKCallbackService.cls</wsa:Address>
                </wsa:ReplyTo>
            </s:Header>
        <s:Body>...</s:Body>
     </s:Envelope>
     ************************************************************/

    private void addWsAddressing(SOAPEnvelope envelope) throws SOAPException {
        SOAPHeader header = envelope.getHeader();
        final String uriWsa = "http://www.w3.org/2005/08/addressing";//"http://www.w3.org/2006/03/addressing/ws-addr.xsd";
        SOAPElement messageId = header.addChildElement("MessageID", "wsa", uriWsa);  //<wsa:MessageID>
        final String uuid = messageUuid == null ? UUID.randomUUID().toString() : messageUuid;
        messageId.addTextNode("urn:uuid:" + uuid);
        SOAPElement replayTo = header.addChildElement("ReplyTo", "wsa", uriWsa);
        SOAPElement address = replayTo.addChildElement("Address", "wsa");
        address.addTextNode( ConfigManager.HealthShare().ServiceUrlEhrReplayTo().toString());
    }

    private void removeNamespace(SOAPEnvelope envelope) throws SOAPException {
        SOAPBody body = envelope.getBody();
        Element nodeWithNameSpace = (Element) body.getFirstChild(); // элемент в пространстве имен ns2:wsdl
        body.getOwnerDocument().renameNode(nodeWithNameSpace, null, nodeWithNameSpace.getLocalName() );
        nodeWithNameSpace = (Element) body.getFirstChild(); // элемент в пространстве имен ns2:wsdl
        removeEmptyNodes(nodeWithNameSpace);

        /*NodeList nodeList = nodeWithNameSpace.getChildNodes();
        // Создаем новый элемент с пустым пространством имен
        Element cont = body.getOwnerDocument().createElement("container");
        cont.setAttribute("facilityCode", nodeWithNameSpace.getAttribute("facilityCode"));
        cont.setAttribute("patientMRN", nodeWithNameSpace.getAttribute("patientMRN"));
        // копируем в новый элемент узлы из SOAP сообщения
        for (Node el : getChildsSda(nodeWithNameSpace)) {
            cont.appendChild(el);
        }

        // Заменяем элемент из SOAP сообщения на элемент с пустым пространством имен
        body.replaceChild(cont, nodeWithNameSpace);*/
    }

    private List<Node> getChildsSda(Element nodeWithNameSpace) {
        List<Node> res = new Vector<Node>();
        final String[] sdaNodes = {"patient","sourceDocument","encounters","diagnoses","disabilities","sickLeaveDocuments","allergies","documents","services"};
        for(String nodeName : sdaNodes) {
            NodeList el = nodeWithNameSpace.getElementsByTagName(nodeName);
            if (el.getLength() > 0) {
                removeEmptyNodes(el.item(0));
                res.add(el.item(0));
            }
        }
        return res;
    }

    public  void removeEmptyNodes(Node node) {
        NodeList list = node.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            final Node item = list.item(i);
            if(item instanceof Element) {
                final String textContent =  item == null ? null : item.getTextContent();
                if (textContent != null && textContent.trim().isEmpty()) {
                    item.getParentNode().removeChild(item);
                    --i;
                } else {
                    removeEmptyNodes(item);
                }
            }
        }
        boolean emptyElement = node.getNodeType() == Node.ELEMENT_NODE
                && node.getChildNodes().getLength() == 0;
        boolean emptyText = node.getNodeType() == Node.TEXT_NODE
                && node.getNodeValue().trim().isEmpty();
        if (emptyElement || emptyText) {
            node.getParentNode().removeChild(node);
        }
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
