
package ru.korus.tmis.ws.transfusion.efive;

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
@WebServiceClient(name = "transfusion-medical-service", targetNamespace = "http://www.korusconsulting.ru", wsdlLocation = "http://test3.gfish.fccho-moscow.ru/transfusion-medical-service?wsdl")
public class TransfusionMedicalService_Service
    extends Service
{

    private final static URL TRANSFUSIONMEDICALSERVICE_WSDL_LOCATION;
    private final static WebServiceException TRANSFUSIONMEDICALSERVICE_EXCEPTION;
    private final static QName TRANSFUSIONMEDICALSERVICE_QNAME = new QName("http://www.korusconsulting.ru", "transfusion-medical-service");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://test3.gfish.fccho-moscow.ru/transfusion-medical-service?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        TRANSFUSIONMEDICALSERVICE_WSDL_LOCATION = url;
        TRANSFUSIONMEDICALSERVICE_EXCEPTION = e;
    }

    public TransfusionMedicalService_Service() {
        super(__getWsdlLocation(), TRANSFUSIONMEDICALSERVICE_QNAME);
    }

    public TransfusionMedicalService_Service(WebServiceFeature... features) {
        super(__getWsdlLocation(), TRANSFUSIONMEDICALSERVICE_QNAME, features);
    }

    public TransfusionMedicalService_Service(URL wsdlLocation) {
        super(wsdlLocation, TRANSFUSIONMEDICALSERVICE_QNAME);
    }

    public TransfusionMedicalService_Service(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, TRANSFUSIONMEDICALSERVICE_QNAME, features);
    }

    public TransfusionMedicalService_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public TransfusionMedicalService_Service(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns TransfusionMedicalService
     */
    @WebEndpoint(name = "transfusionMedicalService")
    public TransfusionMedicalService getTransfusionMedicalService() {
        return super.getPort(new QName("http://www.korusconsulting.ru", "transfusionMedicalService"), TransfusionMedicalService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns TransfusionMedicalService
     */
    @WebEndpoint(name = "transfusionMedicalService")
    public TransfusionMedicalService getTransfusionMedicalService(WebServiceFeature... features) {
        return super.getPort(new QName("http://www.korusconsulting.ru", "transfusionMedicalService"), TransfusionMedicalService.class, features);
    }

    private static URL __getWsdlLocation() {
        if (TRANSFUSIONMEDICALSERVICE_EXCEPTION!= null) {
            throw TRANSFUSIONMEDICALSERVICE_EXCEPTION;
        }
        return TRANSFUSIONMEDICALSERVICE_WSDL_LOCATION;
    }

}