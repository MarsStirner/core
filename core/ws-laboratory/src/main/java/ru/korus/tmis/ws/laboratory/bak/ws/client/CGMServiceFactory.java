
package ru.korus.tmis.ws.laboratory.bak.ws.client;

import ru.korus.tmis.util.ConfigManager;

import javax.xml.namespace.QName;
import javax.xml.ws.*;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.Map;


/**
 * WSDL File for cgmsoap
 * <p/>
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 */
@WebServiceClient(
        name = "CGM_SOAP",
        targetNamespace = "cgm.ru",
        wsdlLocation = "../../../../../../../../CGMSERVICE_pub.wsdl")
public class CGMServiceFactory extends Service {

    private final static URL CGMSOAP_WSDL_LOCATION;

    private final static WebServiceException CGMSOAP_EXCEPTION;

    private final static QName CGMSOAP_QNAME = new QName("cgm.ru", "CGM_SOAP");

    static {

        final String login = ConfigManager.getBakUser();
        final String password = ConfigManager.getBakPassword();

        if (login != null && password != null) {
            Authenticator.setDefault(new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                            login,
                            password.toCharArray());
                }
            });
        }


        final URL baseUrl = CGMServiceFactory.class.getResource(".");
        try {
            CGMSOAP_WSDL_LOCATION = new URL(baseUrl, "../../../../../../../../CGMSERVICE_pub.wsdl");
            WebServiceException e = null;
            if (CGMSOAP_WSDL_LOCATION == null) {
                e = new WebServiceException("Cannot find '../../../../../../../../CGMSERVICE_pub.wsdl' wsdl. Place the resource correctly in the classpath.");
            }
            CGMSOAP_EXCEPTION = e;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public CGMServiceFactory() {
        super(__getWsdlLocation(), CGMSOAP_QNAME);
    }

    public CGMServiceFactory(WebServiceFeature... features) {
        super(__getWsdlLocation(), CGMSOAP_QNAME, features);
    }

    public CGMServiceFactory(URL wsdlLocation) {
        super(wsdlLocation, CGMSOAP_QNAME);
    }

    public CGMServiceFactory(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, CGMSOAP_QNAME, features);
    }

    public CGMServiceFactory(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public CGMServiceFactory(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    private static URL __getWsdlLocation() {
        if (CGMSOAP_EXCEPTION != null) {
            throw CGMSOAP_EXCEPTION;
        }
        return CGMSOAP_WSDL_LOCATION;
    }

    /**
     * @return returns ICGMService
     */
    @WebEndpoint(name = "cgmsoap_PortType")
    public ICGMService getService() {
        final ICGMService service = super.getPort(new QName("cgm.ru", "cgmsoap_PortType"), ICGMService.class);
        final String serviceUrl = ConfigManager.getBakServiceUrl().toString();
        if (serviceUrl != null) {
            Map<String, Object> requestContext = ((BindingProvider) service).getRequestContext();
            requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, serviceUrl);
        }
        return service;
    }

    /**
     * @param features A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.
     *                 Supported features not in the <code>features</code> parameter will have their default values.
     * @return returns ICGMService
     */
    @WebEndpoint(name = "cgmsoap_PortType")
    public ICGMService getService(WebServiceFeature... features) {
        final ICGMService service = super.getPort(new QName("cgm.ru", "cgmsoap_PortType"), ICGMService.class, features);
        final String serviceUrl = ConfigManager.getBakServiceUrl().toString();
        if (serviceUrl != null) {
            Map<String, Object> requestContext = ((BindingProvider) service).getRequestContext();
            requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, serviceUrl);
        }
        return service;
    }

}
