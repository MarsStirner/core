
package ru.korus.tmis.pix.sda.ws;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.soap.Addressing;


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
@Addressing(enabled=true, required=false)
public interface EMRReceiverServiceSoap {


    /**
     * 
     * @param parameters
     */
    @WebMethod
    public void container(
        @WebParam(name = "container", targetNamespace = "", partName = "parameters")
        Container parameters);

}