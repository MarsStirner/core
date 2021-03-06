package ru.korus.tmis.ws.transfusion.devtest.wsimport;

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
@WebServiceClient(name = "TransfusionServiceImpl", targetNamespace = "http://korus.ru/tmis/ws/transfusion",
        wsdlLocation = "http://szagrebelny-nb:8080/tmis-ws-transfusion/TransfusionServiceImpl?wsdl")
public class TransfusionServiceImpl extends Service {

    private final static URL TRANSFUSIONSERVICEIMPL_WSDL_LOCATION;
    private final static WebServiceException TRANSFUSIONSERVICEIMPL_EXCEPTION;
    private final static QName TRANSFUSIONSERVICEIMPL_QNAME = new QName("http://korus.ru/tmis/ws/transfusion", "TransfusionServiceImpl");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://szagrebelny-nb:8080/tmis-ws-transfusion/TransfusionServiceImpl?wsdl");
        } catch (final MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        TRANSFUSIONSERVICEIMPL_WSDL_LOCATION = url;
        TRANSFUSIONSERVICEIMPL_EXCEPTION = e;
    }

    public TransfusionServiceImpl() {
        super(__getWsdlLocation(), TRANSFUSIONSERVICEIMPL_QNAME);
    }

    public TransfusionServiceImpl(final WebServiceFeature... features) {
        super(__getWsdlLocation(), TRANSFUSIONSERVICEIMPL_QNAME, features);
    }

    public TransfusionServiceImpl(final URL wsdlLocation) {
        super(wsdlLocation, TRANSFUSIONSERVICEIMPL_QNAME);
    }

    public TransfusionServiceImpl(final URL wsdlLocation, final WebServiceFeature... features) {
        super(wsdlLocation, TRANSFUSIONSERVICEIMPL_QNAME, features);
    }

    public TransfusionServiceImpl(final URL wsdlLocation, final QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public TransfusionServiceImpl(final URL wsdlLocation, final QName serviceName, final WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return returns TransfusionService
     */
    @WebEndpoint(name = "portTransfusion")
    public TransfusionService getPortTransfusion() {
        return super.getPort(new QName("http://korus.ru/tmis/ws/transfusion", "portTransfusion"), TransfusionService.class);
    }

    /**
     * 
     * @param features
     *            A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy. Supported features not in the <code>features</code> parameter will
     *            have their default values.
     * @return returns TransfusionService
     */
    @WebEndpoint(name = "portTransfusion")
    public TransfusionService getPortTransfusion(final WebServiceFeature... features) {
        return super.getPort(new QName("http://korus.ru/tmis/ws/transfusion", "portTransfusion"), TransfusionService.class, features);
    }

    private static URL __getWsdlLocation() {
        if (TRANSFUSIONSERVICEIMPL_EXCEPTION != null) {
            throw TRANSFUSIONSERVICEIMPL_EXCEPTION;
        }
        return TRANSFUSIONSERVICEIMPL_WSDL_LOCATION;
    }

}
