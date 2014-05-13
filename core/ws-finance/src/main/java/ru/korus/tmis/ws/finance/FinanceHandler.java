package ru.korus.tmis.ws.finance;

import org.w3c.dom.*;
import ru.korus.tmis.scala.util.ConfigManager;

import javax.xml.namespace.QName;
import javax.xml.soap.*;
import javax.xml.soap.Node;
import javax.xml.ws.handler.HandlerResolver;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        02.05.14, 10:01 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class FinanceHandler implements SOAPHandler<SOAPMessageContext> {
    @Override
    public Set<QName> getHeaders() {
        final HashSet<QName> names = new HashSet<QName>();
//        names.add(new QName("urn:wsdl"));
        return names;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext smc) {
        Boolean outboundProperty = (Boolean) smc.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        if (outboundProperty.booleanValue()) {
            SOAPMessage message = smc.getMessage();
            try {
                SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
                addNamespace(envelope);
                message.saveChanges();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            SOAPMessage message = smc.getMessage();
            try {
                SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
                removeNamespace(envelope);
                message.saveChanges();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return true;
    }

    @Override
    public void close(MessageContext context) {
    }

    private void removeNamespace(SOAPEnvelope envelope) throws SOAPException {
        SOAPBody body = envelope.getBody();
        final org.w3c.dom.Node firstChild = body.getFirstChild();
        final org.w3c.dom.Node parentNode = firstChild.getParentNode();
        Element nodeSetPaymentInfo = getElementByName(parentNode.getChildNodes(), "setPaymentInfo");
        Element nodeWithNameSpace = getElementByName(nodeSetPaymentInfo.getChildNodes(), "inParam");// элемент /inParam
        removeNamespace(nodeWithNameSpace);
    }

    private void addNamespace(SOAPEnvelope envelope) throws SOAPException {
        SOAPBody body = envelope.getBody();
       // final org.w3c.dom.Node firstChild = body.getFirstChild();
       // final org.w3c.dom.Node parentNode = firstChild.getParentNode();
       // Element response = getElementByName(parentNode.getChildNodes(), "getServiceListResponse");
       // Element returnNode = getElementByName(parentNode.getChildNodes(), "return");
        addNamespace(body);
    }

    private void addNamespace(Element nodeWithNameSpace) {
        if(nodeWithNameSpace.getNamespaceURI() == null) {
            nodeWithNameSpace.getOwnerDocument().renameNode(nodeWithNameSpace, "http://korus.ru/tmis/ws/finance", nodeWithNameSpace.getLocalName() );
        }
        NodeList nodeList = nodeWithNameSpace.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); ++i) {
            final org.w3c.dom.Node item = nodeList.item(i);
            if(item instanceof Element)
                addNamespace((Element) item);
        }

    }

    private void removeNamespace(Element nodeWithNameSpace) {
        nodeWithNameSpace.getOwnerDocument().renameNode(nodeWithNameSpace, null, nodeWithNameSpace.getLocalName() );
        NodeList nodeList = nodeWithNameSpace.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); ++i) {
            final org.w3c.dom.Node item = nodeList.item(i);
            if(item instanceof Element)
                removeNamespace((Element) item);
        }

    }

    private Element getElementByName(NodeList childNodes, String name) {
        for(int i = 0; i < childNodes.getLength(); ++i) {
            final org.w3c.dom.Node item = childNodes.item(i);
            if(item instanceof Element && name.equals(item.getLocalName()))
                return (Element)item;
        }
        return null;
    }

    private List<org.w3c.dom.Node> getChildsSda(Element nodeWithNameSpace) {
        List<org.w3c.dom.Node> res = new Vector<org.w3c.dom.Node>();
        final String[] sdaNodes = {"patient","sourceDocument","encounters","diagnoses","disabilities","sickLeaveDocuments","allergies","documents","services"};
        for(String nodeName : sdaNodes) {
            NodeList el = nodeWithNameSpace.getElementsByTagName(nodeName);
            if (el.getLength() > 0) {
                res.add(el.item(0));
            }
        }
        return res;
    }

}
