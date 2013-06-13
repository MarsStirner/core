package ru.korus.tmis.pix.sda.ws;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;

/**
 * This class was generated by the JAX-WS RI. JAX-WS RI 2.2.4-b01 Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "SDASoapServiceService", targetNamespace = "urn:wsdl",
        wsdlLocation = "file:/C:/Project/tmis-core/core/referencebook/src/main/resources/PIX/wsdl/sda.wsdl")
public class SDASoapServiceService
        extends Service
{

    private final static URL SDASOAPSERVICESERVICE_WSDL_LOCATION;
    private final static WebServiceException SDASOAPSERVICESERVICE_EXCEPTION;
    private final static QName SDASOAPSERVICESERVICE_QNAME = new QName("urn:wsdl", "SDASoapServiceService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            final URL baseUrl = SDASoapServiceService.class.getResource(".");
            url = new URL(baseUrl, "../../../../../../PIX/wsdl/sda.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        SDASOAPSERVICESERVICE_WSDL_LOCATION = url;
        SDASOAPSERVICESERVICE_EXCEPTION = e;
    }

    public SDASoapServiceService() {
        super(__getWsdlLocation(), SDASOAPSERVICESERVICE_QNAME);
    }

    public SDASoapServiceService(WebServiceFeature... features) {
        super(__getWsdlLocation(), SDASOAPSERVICESERVICE_QNAME, features);
    }

    public SDASoapServiceService(URL wsdlLocation) {
        super(wsdlLocation, SDASOAPSERVICESERVICE_QNAME);
    }

    public SDASoapServiceService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, SDASOAPSERVICESERVICE_QNAME, features);
    }

    public SDASoapServiceService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SDASoapServiceService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return returns SDASoapServiceServiceSoap
     */
    @WebEndpoint(name = "SDASoapServiceServiceSoap")
    public SDASoapServiceServiceSoap getSDASoapServiceServiceSoap() {
        return super.getPort(new QName("urn:wsdl", "SDASoapServiceServiceSoap"), SDASoapServiceServiceSoap.class);
    }

    /**
     * 
     * @param features
     *            A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy. Supported features not in the <code>features</code> parameter will
     *            have their default values.
     * @return returns SDASoapServiceServiceSoap
     */
    @WebEndpoint(name = "SDASoapServiceServiceSoap")
    public SDASoapServiceServiceSoap getSDASoapServiceServiceSoap(WebServiceFeature... features) {
        return super.getPort(new QName("urn:wsdl", "SDASoapServiceServiceSoap"), SDASoapServiceServiceSoap.class, features);
    }

    private static URL __getWsdlLocation() {
        if (SDASOAPSERVICESERVICE_EXCEPTION != null) {
            throw SDASOAPSERVICESERVICE_EXCEPTION;
        }
        return SDASOAPSERVICESERVICE_WSDL_LOCATION;
    }

}
