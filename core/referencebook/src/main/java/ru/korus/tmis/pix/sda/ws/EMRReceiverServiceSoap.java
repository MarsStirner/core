
package ru.korus.tmis.pix.sda.ws;

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
@WebService(name = "EMRReceiverServiceSoap", targetNamespace = "urn:wsdl")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface EMRReceiverServiceSoap {


    /**
     * 
     * @param parameters
     */
    @WebMethod
    public void container(
        @WebParam(name = "container", targetNamespace = "http://schemas.intersystems.ru/hs/ehr/v1", partName = "parameters")
        Container parameters);

}
