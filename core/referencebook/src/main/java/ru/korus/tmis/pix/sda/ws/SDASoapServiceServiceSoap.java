
package ru.korus.tmis.pix.sda.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "SDASoapServiceServiceSoap", targetNamespace = "urn:wsdl")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface SDASoapServiceServiceSoap {


    /**
     * 
     * @param parameters
     */
    @WebMethod
    public void sendSDA(
        @WebParam(name = "Container", targetNamespace = "", partName = "parameters")
        Container parameters);

}
