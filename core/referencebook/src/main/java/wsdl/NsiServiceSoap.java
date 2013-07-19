
package wsdl;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Holder;
import nsi.F001;
import nsi.F002;
import nsi.F003;
import nsi.F005;
import nsi.F006;
import nsi.F007;
import nsi.F008;
import nsi.F009;
import nsi.F010;
import nsi.F011;
import nsi.F012;
import nsi.F013;
import nsi.F014;
import nsi.F015;
import nsi.Kladr;
import nsi.KladrStreet;
import nsi.M001;
import nsi.O001;
import nsi.O002;
import nsi.O003;
import nsi.O004;
import nsi.O005;
import nsi.ObjectFactory;
import nsi.R001;
import nsi.R002;
import nsi.R003;
import nsi.R004;
import nsi.V001;
import nsi.V002;
import nsi.V003;
import nsi.V004;
import nsi.V005;
import nsi.V006;
import nsi.V007;
import nsi.V008;
import nsi.V009;
import nsi.V010;
import nsi.V012;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "NsiServiceSoap", targetNamespace = "urn:wsdl")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface NsiServiceSoap {


    /**
     * 
     * @param parameters
     */
    @WebMethod(operationName = "F001")
    public void f001(
        @WebParam(name = "F001", targetNamespace = "urn:nsi", mode = WebParam.Mode.INOUT, partName = "parameters")
        Holder<F001> parameters);

    /**
     * 
     * @param parameters
     */
    @WebMethod(operationName = "F002")
    public void f002(
        @WebParam(name = "F002", targetNamespace = "urn:nsi", mode = WebParam.Mode.INOUT, partName = "parameters")
        Holder<F002> parameters);

    /**
     * 
     * @param parameters
     */
    @WebMethod(operationName = "F003")
    public void f003(
        @WebParam(name = "F003", targetNamespace = "urn:nsi", mode = WebParam.Mode.INOUT, partName = "parameters")
        Holder<F003> parameters);

    /**
     * 
     * @param parameters
     */
    @WebMethod(operationName = "F005")
    public void f005(
        @WebParam(name = "F005", targetNamespace = "urn:nsi", mode = WebParam.Mode.INOUT, partName = "parameters")
        Holder<F005> parameters);

    /**
     * 
     * @param parameters
     */
    @WebMethod(operationName = "F006")
    public void f006(
        @WebParam(name = "F006", targetNamespace = "urn:nsi", mode = WebParam.Mode.INOUT, partName = "parameters")
        Holder<F006> parameters);

    /**
     * 
     * @param parameters
     */
    @WebMethod(operationName = "F007")
    public void f007(
        @WebParam(name = "F007", targetNamespace = "urn:nsi", mode = WebParam.Mode.INOUT, partName = "parameters")
        Holder<F007> parameters);

    /**
     * 
     * @param parameters
     */
    @WebMethod(operationName = "F008")
    public void f008(
        @WebParam(name = "F008", targetNamespace = "urn:nsi", mode = WebParam.Mode.INOUT, partName = "parameters")
        Holder<F008> parameters);

    /**
     * 
     * @param parameters
     */
    @WebMethod(operationName = "F009")
    public void f009(
        @WebParam(name = "F009", targetNamespace = "urn:nsi", mode = WebParam.Mode.INOUT, partName = "parameters")
        Holder<F009> parameters);

    /**
     * 
     * @param parameters
     */
    @WebMethod(operationName = "F010")
    public void f010(
        @WebParam(name = "F010", targetNamespace = "urn:nsi", mode = WebParam.Mode.INOUT, partName = "parameters")
        Holder<F010> parameters);

    /**
     * 
     * @param parameters
     */
    @WebMethod(operationName = "F011")
    public void f011(
        @WebParam(name = "F011", targetNamespace = "urn:nsi", mode = WebParam.Mode.INOUT, partName = "parameters")
        Holder<F011> parameters);

    /**
     * 
     * @param parameters
     */
    @WebMethod(operationName = "F012")
    public void f012(
        @WebParam(name = "F012", targetNamespace = "urn:nsi", mode = WebParam.Mode.INOUT, partName = "parameters")
        Holder<F012> parameters);

    /**
     * 
     * @param parameters
     */
    @WebMethod(operationName = "F013")
    public void f013(
        @WebParam(name = "F013", targetNamespace = "urn:nsi", mode = WebParam.Mode.INOUT, partName = "parameters")
        Holder<F013> parameters);

    /**
     * 
     * @param parameters
     */
    @WebMethod(operationName = "F014")
    public void f014(
        @WebParam(name = "F014", targetNamespace = "urn:nsi", mode = WebParam.Mode.INOUT, partName = "parameters")
        Holder<F014> parameters);

    /**
     * 
     * @param parameters
     */
    @WebMethod(operationName = "F015")
    public void f015(
        @WebParam(name = "F015", targetNamespace = "urn:nsi", mode = WebParam.Mode.INOUT, partName = "parameters")
        Holder<F015> parameters);

    /**
     * 
     * @param parameters
     */
    @WebMethod(operationName = "Kladr")
    public void kladr(
        @WebParam(name = "Kladr", targetNamespace = "urn:nsi", mode = WebParam.Mode.INOUT, partName = "parameters")
        Holder<Kladr> parameters);

    /**
     * 
     * @param parameters
     */
    @WebMethod(operationName = "KladrStreet")
    public void kladrStreet(
        @WebParam(name = "KladrStreet", targetNamespace = "urn:nsi", mode = WebParam.Mode.INOUT, partName = "parameters")
        Holder<KladrStreet> parameters);

    /**
     * 
     * @param parameters
     */
    @WebMethod(operationName = "M001")
    public void m001(
        @WebParam(name = "M001", targetNamespace = "urn:nsi", mode = WebParam.Mode.INOUT, partName = "parameters")
        Holder<M001> parameters);

    /**
     * 
     * @param parameters
     */
    @WebMethod(operationName = "O001")
    public void o001(
        @WebParam(name = "O001", targetNamespace = "urn:nsi", mode = WebParam.Mode.INOUT, partName = "parameters")
        Holder<O001> parameters);

    /**
     * 
     * @param parameters
     */
    @WebMethod(operationName = "O002")
    public void o002(
        @WebParam(name = "O002", targetNamespace = "urn:nsi", mode = WebParam.Mode.INOUT, partName = "parameters")
        Holder<O002> parameters);

    /**
     * 
     * @param parameters
     */
    @WebMethod(operationName = "O003")
    public void o003(
        @WebParam(name = "O003", targetNamespace = "urn:nsi", mode = WebParam.Mode.INOUT, partName = "parameters")
        Holder<O003> parameters);

    /**
     * 
     * @param parameters
     */
    @WebMethod(operationName = "O004")
    public void o004(
        @WebParam(name = "O004", targetNamespace = "urn:nsi", mode = WebParam.Mode.INOUT, partName = "parameters")
        Holder<O004> parameters);

    /**
     * 
     * @param parameters
     */
    @WebMethod(operationName = "O005")
    public void o005(
        @WebParam(name = "O005", targetNamespace = "urn:nsi", mode = WebParam.Mode.INOUT, partName = "parameters")
        Holder<O005> parameters);

    /**
     * 
     * @param parameters
     */
    @WebMethod(operationName = "R001")
    public void r001(
        @WebParam(name = "R001", targetNamespace = "urn:nsi", mode = WebParam.Mode.INOUT, partName = "parameters")
        Holder<R001> parameters);

    /**
     * 
     * @param parameters
     */
    @WebMethod(operationName = "R002")
    public void r002(
        @WebParam(name = "R002", targetNamespace = "urn:nsi", mode = WebParam.Mode.INOUT, partName = "parameters")
        Holder<R002> parameters);

    /**
     * 
     * @param parameters
     */
    @WebMethod(operationName = "R003")
    public void r003(
        @WebParam(name = "R003", targetNamespace = "urn:nsi", mode = WebParam.Mode.INOUT, partName = "parameters")
        Holder<R003> parameters);

    /**
     * 
     * @param parameters
     */
    @WebMethod(operationName = "R004")
    public void r004(
        @WebParam(name = "R004", targetNamespace = "urn:nsi", mode = WebParam.Mode.INOUT, partName = "parameters")
        Holder<R004> parameters);

    /**
     * 
     * @param parameters
     */
    @WebMethod(operationName = "V001")
    public void v001(
        @WebParam(name = "V001", targetNamespace = "urn:nsi", mode = WebParam.Mode.INOUT, partName = "parameters")
        Holder<V001> parameters);

    /**
     * 
     * @param parameters
     */
    @WebMethod(operationName = "V002")
    public void v002(
        @WebParam(name = "V002", targetNamespace = "urn:nsi", mode = WebParam.Mode.INOUT, partName = "parameters")
        Holder<V002> parameters);

    /**
     * 
     * @param parameters
     */
    @WebMethod(operationName = "V003")
    public void v003(
        @WebParam(name = "V003", targetNamespace = "urn:nsi", mode = WebParam.Mode.INOUT, partName = "parameters")
        Holder<V003> parameters);

    /**
     * 
     * @param parameters
     */
    @WebMethod(operationName = "V004")
    public void v004(
        @WebParam(name = "V004", targetNamespace = "urn:nsi", mode = WebParam.Mode.INOUT, partName = "parameters")
        Holder<V004> parameters);

    /**
     * 
     * @param parameters
     */
    @WebMethod(operationName = "V005")
    public void v005(
        @WebParam(name = "V005", targetNamespace = "urn:nsi", mode = WebParam.Mode.INOUT, partName = "parameters")
        Holder<V005> parameters);

    /**
     * 
     * @param parameters
     */
    @WebMethod(operationName = "V006")
    public void v006(
        @WebParam(name = "V006", targetNamespace = "urn:nsi", mode = WebParam.Mode.INOUT, partName = "parameters")
        Holder<V006> parameters);

    /**
     * 
     * @param parameters
     */
    @WebMethod(operationName = "V007")
    public void v007(
        @WebParam(name = "V007", targetNamespace = "urn:nsi", mode = WebParam.Mode.INOUT, partName = "parameters")
        Holder<V007> parameters);

    /**
     * 
     * @param parameters
     */
    @WebMethod(operationName = "V008")
    public void v008(
        @WebParam(name = "V008", targetNamespace = "urn:nsi", mode = WebParam.Mode.INOUT, partName = "parameters")
        Holder<V008> parameters);

    /**
     * 
     * @param parameters
     */
    @WebMethod(operationName = "V009")
    public void v009(
        @WebParam(name = "V009", targetNamespace = "urn:nsi", mode = WebParam.Mode.INOUT, partName = "parameters")
        Holder<V009> parameters);

    /**
     * 
     * @param parameters
     */
    @WebMethod(operationName = "V010")
    public void v010(
        @WebParam(name = "V010", targetNamespace = "urn:nsi", mode = WebParam.Mode.INOUT, partName = "parameters")
        Holder<V010> parameters);

    /**
     * 
     * @param parameters
     */
    @WebMethod(operationName = "V012")
    public void v012(
        @WebParam(name = "V012", targetNamespace = "urn:nsi", mode = WebParam.Mode.INOUT, partName = "parameters")
        Holder<V012> parameters);

}