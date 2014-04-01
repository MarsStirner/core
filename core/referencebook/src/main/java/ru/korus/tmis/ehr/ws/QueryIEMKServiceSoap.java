
package ru.korus.tmis.ehr.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.6-2b01 
 * Generated source version: 2.2
 * 
 */
@WebService(name = "QueryIEMKServiceSoap", targetNamespace = "urn:wsdl")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface QueryIEMKServiceSoap {


    /**
     * 
     * @param parameters
     */
    @WebMethod
    public void patientQuery(
        @WebParam(name = "patientQuery", targetNamespace = "", partName = "parameters")
        PatientQuery parameters);

    /**
     * 
     * @param parameters
     */
    @WebMethod
    public void documentQuery(
        @WebParam(name = "documentQuery", targetNamespace = "", partName = "parameters")
        DocumentQuery parameters);

    /**
     * 
     * @param parameters
     */
    @WebMethod
    public void retrieveDocumentQuery(
        @WebParam(name = "retrieveDocumentQuery", targetNamespace = "", partName = "parameters")
        RetrieveDocumentQuery parameters);

}
