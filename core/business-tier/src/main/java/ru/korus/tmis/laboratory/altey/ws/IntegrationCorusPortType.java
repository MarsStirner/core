
package ru.korus.tmis.laboratory.altey.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.7-b01-
 * Generated source version: 2.1
 * 
 */
@WebService(name = "IntegrationCorusPortType", targetNamespace = "http://www.korusconsulting.ru")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface IntegrationCorusPortType {


    /**
     * 
     * @param orderInfo
     * @param diagnosticRequestInfo
     * @param patientInfo
     * @param biomaterialInfo
     * @return
     *     returns int
     */
    @WebMethod(action = "http://www.korusconsulting.ru#IntegrationCorus:queryAnalysis")
    @WebResult(targetNamespace = "http://www.korusconsulting.ru")
    @RequestWrapper(localName = "queryAnalysis", targetNamespace = "http://www.korusconsulting.ru", className = "ru.korus.ws.laboratory.QueryAnalysis")
    @ResponseWrapper(localName = "queryAnalysisResponse", targetNamespace = "http://www.korusconsulting.ru", className = "ru.korus.ws.laboratory.QueryAnalysisResponse")
    public int queryAnalysis(
            @WebParam(name = "PatientInfo", targetNamespace = "http://www.korusconsulting.ru")
            PatientInfo patientInfo,
            @WebParam(name = "DiagnosticRequestInfo", targetNamespace = "http://www.korusconsulting.ru")
            DiagnosticRequestInfo diagnosticRequestInfo,
            @WebParam(name = "BiomaterialInfo", targetNamespace = "http://www.korusconsulting.ru")
            BiomaterialInfo biomaterialInfo,
            @WebParam(name = "OrderInfo", targetNamespace = "http://www.korusconsulting.ru")
            OrderInfo orderInfo);

}