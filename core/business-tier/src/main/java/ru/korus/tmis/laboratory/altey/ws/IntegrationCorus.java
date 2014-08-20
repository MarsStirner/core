
package ru.korus.tmis.laboratory.altey.ws;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.7-b01-
 * Generated source version: 2.1
 * 
 */
@WebServiceClient(name = "IntegrationCorus", targetNamespace = "http://www.korusconsulting.ru", wsdlLocation = "../../../../labisws.wsdl")
public class IntegrationCorus
    extends Service
{

    private final static URL INTEGRATIONCORUS_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(IntegrationCorus.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = IntegrationCorus.class.getResource(".");
            url = new URL(baseUrl, "../../../../labisws.wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: '../../../../labisws.wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        INTEGRATIONCORUS_WSDL_LOCATION = url;
    }

    public IntegrationCorus(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public IntegrationCorus() {
        super(INTEGRATIONCORUS_WSDL_LOCATION, new QName("http://www.korusconsulting.ru", "IntegrationCorus"));
    }

    /**
     * 
     * @return
     *     returns IntegrationCorusPortType
     */
    @WebEndpoint(name = "IntegrationCorusSoap")
    public IntegrationCorusPortType getIntegrationCorusSoap() {
        return super.getPort(new QName("http://www.korusconsulting.ru", "IntegrationCorusSoap"), IntegrationCorusPortType.class);
    }

    /**
     *
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns IntegrationCorusPortType
     */
    @WebEndpoint(name = "IntegrationCorusSoap")
    public IntegrationCorusPortType getIntegrationCorusSoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://www.korusconsulting.ru", "IntegrationCorusSoap"), IntegrationCorusPortType.class, features);
    }

}