
package wsdl;

import ru.korus.tmis.scala.util.ConfigManager;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.ws.*;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "NsiService", targetNamespace = "urn:wsdl", wsdlLocation = "../korus.NsiService.cls.wsdl")
public class NsiService
    extends Service
{

    private final static URL NSISERVICE_WSDL_LOCATION;
    private final static WebServiceException NSISERVICE_EXCEPTION;
    private final static QName NSISERVICE_QNAME = new QName("urn:wsdl", "NsiService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            final URL baseUrl = NsiService.class.getResource("");
            url = new URL(baseUrl, "../korus.NsiService.cls.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        NSISERVICE_WSDL_LOCATION = url;
        NSISERVICE_EXCEPTION = e;
    }

    public NsiService() {
        super(__getWsdlLocation(), NSISERVICE_QNAME);
    }

    public NsiService(WebServiceFeature... features) {
        super(__getWsdlLocation(), NSISERVICE_QNAME, features);
    }

    public NsiService(URL wsdlLocation) {
        super(wsdlLocation, NSISERVICE_QNAME);
    }

    public NsiService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, NSISERVICE_QNAME, features);
    }

    public NsiService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public NsiService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns NsiServiceSoap
     */
    @WebEndpoint(name = "NsiServiceSoap")
    public NsiServiceSoap getNsiServiceSoap() {
        NsiServiceSoap port = super.getPort(new QName("urn:wsdl", "NsiServiceSoap"), NsiServiceSoap.class);

        final String serviceUrl = ConfigManager.HealthShare().ServiceUrl().toString();
        Map<String, Object> requestContext = ((BindingProvider) port).getRequestContext();
        requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, serviceUrl);
        return port;
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns NsiServiceSoap
     */
    @WebEndpoint(name = "NsiServiceSoap")
    public NsiServiceSoap getNsiServiceSoap(WebServiceFeature... features) {
        return super.getPort(new QName("urn:wsdl", "NsiServiceSoap"), NsiServiceSoap.class, features);
    }

    private static URL __getWsdlLocation() {
        if (NSISERVICE_EXCEPTION!= null) {
            throw NSISERVICE_EXCEPTION;
        }
        return NSISERVICE_WSDL_LOCATION;
    }

}
