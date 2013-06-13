
package ru.korus.tmis.pix;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "PIXManager_Service", targetNamespace = "urn:ihe:iti:pixv3:2007", wsdlLocation = "file:/C:/Project/tmis-core/core/referencebook/src/main/resources/PIX/wsdl/PIXManager.wsdl")
public class PIXManagerService
    extends Service
{

    private final static URL PIXMANAGERSERVICE_WSDL_LOCATION;
    private final static WebServiceException PIXMANAGERSERVICE_EXCEPTION;
    private final static QName PIXMANAGERSERVICE_QNAME = new QName("urn:ihe:iti:pixv3:2007", "PIXManager_Service");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/C:/Project/tmis-core/core/referencebook/src/main/resources/PIX/wsdl/PIXManager.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        PIXMANAGERSERVICE_WSDL_LOCATION = url;
        PIXMANAGERSERVICE_EXCEPTION = e;
    }

    public PIXManagerService() {
        super(__getWsdlLocation(), PIXMANAGERSERVICE_QNAME);
    }

    public PIXManagerService(WebServiceFeature... features) {
        super(__getWsdlLocation(), PIXMANAGERSERVICE_QNAME, features);
    }

    public PIXManagerService(URL wsdlLocation) {
        super(wsdlLocation, PIXMANAGERSERVICE_QNAME);
    }

    public PIXManagerService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, PIXMANAGERSERVICE_QNAME, features);
    }

    public PIXManagerService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public PIXManagerService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns PIXManagerPortType
     */
    @WebEndpoint(name = "PIXManager_Port_Soap12")
    public PIXManagerPortType getPIXManagerPortSoap12() {
        return super.getPort(new QName("urn:ihe:iti:pixv3:2007", "PIXManager_Port_Soap12"), PIXManagerPortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns PIXManagerPortType
     */
    @WebEndpoint(name = "PIXManager_Port_Soap12")
    public PIXManagerPortType getPIXManagerPortSoap12(WebServiceFeature... features) {
        return super.getPort(new QName("urn:ihe:iti:pixv3:2007", "PIXManager_Port_Soap12"), PIXManagerPortType.class, features);
    }

    private static URL __getWsdlLocation() {
        if (PIXMANAGERSERVICE_EXCEPTION!= null) {
            throw PIXMANAGERSERVICE_EXCEPTION;
        }
        return PIXMANAGERSERVICE_WSDL_LOCATION;
    }

}
