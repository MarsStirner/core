
package misexchange;

import ru.korus.tmis.core.database.DbSettingsBeanLocal;
import ru.korus.tmis.util.ConfigManager;
import ru.korus.tmis.util.reflect.Configuration;

import javax.ejb.EJB;
import javax.xml.namespace.QName;
import javax.xml.ws.*;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.List;
import java.util.Map;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 */
@WebServiceClient(name = "MISExchange", targetNamespace = "MISExchange", wsdlLocation = "../MISExchange.wsdl")
public class MISExchange
        extends Service {

    private final static URL MISEXCHANGE_WSDL_LOCATION;
    private final static WebServiceException MISEXCHANGE_EXCEPTION;
    private final static QName MISEXCHANGE_QNAME = new QName("MISExchange", "MISExchange");

    static {
        final String login = ConfigManager.Drugstore().User();
        final String password = ConfigManager.Drugstore().Password();

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
            url = new URL(MISExchange.class.getResource("."), "../MISExchange.wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        MISEXCHANGE_WSDL_LOCATION = url;
        MISEXCHANGE_EXCEPTION = e;
    }

    public MISExchange() {
        super(__getWsdlLocation(), MISEXCHANGE_QNAME);
    }

    public MISExchange(WebServiceFeature... features) {
        super(__getWsdlLocation(), MISEXCHANGE_QNAME, features);
    }

    public MISExchange(URL wsdlLocation) {
        super(wsdlLocation, MISEXCHANGE_QNAME);
    }

    public MISExchange(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, MISEXCHANGE_QNAME, features);
    }

    public MISExchange(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public MISExchange(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * @return returns MISExchangePortType
     */
    @WebEndpoint(name = "MISExchangeSoap")
    public MISExchangePortType getMISExchangeSoap() {
        final MISExchangePortType port = super.getPort(new QName("MISExchange", "MISExchangeSoap"), MISExchangePortType.class);

        final String serviceUrl = ConfigManager.getDrugUrl().toString();
        Map<String, Object> requestContext = ((BindingProvider) port).getRequestContext();
        requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, serviceUrl);
//        requestContext.put(BindingProvider.USERNAME_PROPERTY, login);
//        requestContext.put(BindingProvider.PASSWORD_PROPERTY, password);
        return port;
    }

    /**
     * @param features A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return returns MISExchangePortType
     */
    @WebEndpoint(name = "MISExchangeSoap")
    public MISExchangePortType getMISExchangeSoap(WebServiceFeature... features) {
        return super.getPort(new QName("MISExchange", "MISExchangeSoap"), MISExchangePortType.class, features);
    }

    private static URL __getWsdlLocation() {
        if (MISEXCHANGE_EXCEPTION != null) {
            throw MISEXCHANGE_EXCEPTION;
        }
        return MISEXCHANGE_WSDL_LOCATION;
    }

}
