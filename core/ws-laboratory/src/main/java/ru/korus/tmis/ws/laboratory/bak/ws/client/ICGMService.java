
package ru.korus.tmis.ws.laboratory.bak.ws.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "cgmsoap_PortType", targetNamespace = "cgm.ru")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface ICGMService {

    /**
     * 
     * @param hl7Qry
     * @return
     *     returns java.lang.String
     */
    @WebMethod(action = "qry_CGM")
    @WebResult(name = "returnValue", partName = "returnValue")
    public String queryAnalysis(
            @WebParam(name = "hl7_qry", partName = "hl7_qry")
            String hl7Qry);

}