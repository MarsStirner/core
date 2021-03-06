
package ru.korus.tmis.ws.finance.odvd;

import ru.korus.tmis.scala.util.ConfigManager;

import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
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
@WebServiceClient(name = "wsPoliclinic", targetNamespace = "http://localhost/Policlinic", wsdlLocation = "file:/C:/Project/tmis-core/core/ws-finance/src/main/resourses/wsdl/wsPoliclinic.wsdl")
public class WsPoliclinic
    extends Service
{

    private final static URL WSPOLICLINIC_WSDL_LOCATION;
    private final static WebServiceException WSPOLICLINIC_EXCEPTION;
    private final static QName WSPOLICLINIC_QNAME = new QName("http://localhost/Policlinic", "wsPoliclinic");

    static {

        final String login = ConfigManager.Finance().User();
        final String password = ConfigManager.Finance().Password();

        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        login,
                        password.toCharArray());
            }
        });

        URL url = null;
        WebServiceException e = null;
        try {
            final URL baseUrl = WsPoliclinic.class.getResource("");
            url = new URL(baseUrl, "../../../../../../../wsdl/wsPoliclinic.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        WSPOLICLINIC_WSDL_LOCATION = url;
        WSPOLICLINIC_EXCEPTION = e;
    }

    public WsPoliclinic() {
        super(__getWsdlLocation(), WSPOLICLINIC_QNAME);
    }

    public WsPoliclinic(WebServiceFeature... features) {
        super(__getWsdlLocation(), WSPOLICLINIC_QNAME, features);
    }

    public WsPoliclinic(URL wsdlLocation) {
        super(wsdlLocation, WSPOLICLINIC_QNAME);
    }

    public WsPoliclinic(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, WSPOLICLINIC_QNAME, features);
    }

    public WsPoliclinic(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public WsPoliclinic(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns WsPoliclinicPortType
     */
    @WebEndpoint(name = "wsPoliclinicSoap")
    public WsPoliclinicPortType getWsPoliclinicSoap() {
        final WsPoliclinicPortType port = super.getPort(new QName("http://localhost/Policlinic", "wsPoliclinicSoap"), WsPoliclinicPortType.class);
        final String serviceUrl = ConfigManager.Finance().ServiceUrl().toString();
        Map<String, Object> requestContext = ((BindingProvider) port).getRequestContext();
        requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, serviceUrl);
        return port;

    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns WsPoliclinicPortType
     */
    @WebEndpoint(name = "wsPoliclinicSoap")
    public WsPoliclinicPortType getWsPoliclinicSoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://localhost/Policlinic", "wsPoliclinicSoap"), WsPoliclinicPortType.class, features);
    }

    /**
     * 
     * @return
     *     returns WsPoliclinicPortType
     */
    @WebEndpoint(name = "wsPoliclinicSoap12")
    public WsPoliclinicPortType getWsPoliclinicSoap12() {
        return super.getPort(new QName("http://localhost/Policlinic", "wsPoliclinicSoap12"), WsPoliclinicPortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns WsPoliclinicPortType
     */
    @WebEndpoint(name = "wsPoliclinicSoap12")
    public WsPoliclinicPortType getWsPoliclinicSoap12(WebServiceFeature... features) {
        return super.getPort(new QName("http://localhost/Policlinic", "wsPoliclinicSoap12"), WsPoliclinicPortType.class, features);
    }

    private static URL __getWsdlLocation() {
        if (WSPOLICLINIC_EXCEPTION!= null) {
            throw WSPOLICLINIC_EXCEPTION;
        }
        return WSPOLICLINIC_WSDL_LOCATION;
    }

}
