
package ru.korus.tmis.ws.transfusion.efive;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ru.korus.tmis.ws.transfusion.efive package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _OrderInformation_QNAME = new QName("http://www.korusconsulting.ru", "OrderInformation");
    private final static QName _ComponentType_QNAME = new QName("http://www.korusconsulting.ru", "ComponentType");
    private final static QName _PatientCredentials_QNAME = new QName("http://www.korusconsulting.ru", "PatientCredentials");
    private final static QName _OrderBloodComponents_QNAME = new QName("http://www.korusconsulting.ru", "orderBloodComponents");
    private final static QName _GetProcedureTypes_QNAME = new QName("http://www.korusconsulting.ru", "getProcedureTypes");
    private final static QName _ProcedureType_QNAME = new QName("http://www.korusconsulting.ru", "ProcedureType");
    private final static QName _OrderResult_QNAME = new QName("http://www.korusconsulting.ru", "OrderResult");
    private final static QName _GetProcedureTypesResponse_QNAME = new QName("http://www.korusconsulting.ru", "getProcedureTypesResponse");
    private final static QName _OrderMedicalProcedureResponse_QNAME = new QName("http://www.korusconsulting.ru", "orderMedicalProcedureResponse");
    private final static QName _DonorInfo_QNAME = new QName("http://www.korusconsulting.ru", "DonorInfo");
    private final static QName _OrderMedicalProcedure_QNAME = new QName("http://www.korusconsulting.ru", "orderMedicalProcedure");
    private final static QName _ProcedureInfo_QNAME = new QName("http://www.korusconsulting.ru", "ProcedureInfo");
    private final static QName _GetComponentTypes_QNAME = new QName("http://www.korusconsulting.ru", "getComponentTypes");
    private final static QName _GetLaboratoryMeasureTypes_QNAME = new QName("http://www.korusconsulting.ru", "getLaboratoryMeasureTypes");
    private final static QName _OrderBloodComponentsResponse_QNAME = new QName("http://www.korusconsulting.ru", "orderBloodComponentsResponse");
    private final static QName _LaboratoryMeasureType_QNAME = new QName("http://www.korusconsulting.ru", "LaboratoryMeasureType");
    private final static QName _GetComponentTypesResponse_QNAME = new QName("http://www.korusconsulting.ru", "getComponentTypesResponse");
    private final static QName _GetLaboratoryMeasureTypesResponse_QNAME = new QName("http://www.korusconsulting.ru", "getLaboratoryMeasureTypesResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ru.korus.tmis.ws.transfusion.efive
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetProcedureTypesResponse }
     * 
     */
    public GetProcedureTypesResponse createGetProcedureTypesResponse() {
        return new GetProcedureTypesResponse();
    }

    /**
     * Create an instance of {@link OrderResult }
     * 
     */
    public OrderResult createOrderResult() {
        return new OrderResult();
    }

    /**
     * Create an instance of {@link ProcedureType }
     * 
     */
    public ProcedureType createProcedureType() {
        return new ProcedureType();
    }

    /**
     * Create an instance of {@link OrderBloodComponents }
     * 
     */
    public OrderBloodComponents createOrderBloodComponents() {
        return new OrderBloodComponents();
    }

    /**
     * Create an instance of {@link GetProcedureTypes }
     * 
     */
    public GetProcedureTypes createGetProcedureTypes() {
        return new GetProcedureTypes();
    }

    /**
     * Create an instance of {@link ComponentType }
     * 
     */
    public ComponentType createComponentType() {
        return new ComponentType();
    }

    /**
     * Create an instance of {@link PatientCredentials }
     * 
     */
    public PatientCredentials createPatientCredentials() {
        return new PatientCredentials();
    }

    /**
     * Create an instance of {@link OrderInformation }
     * 
     */
    public OrderInformation createOrderInformation() {
        return new OrderInformation();
    }

    /**
     * Create an instance of {@link LaboratoryMeasureType }
     * 
     */
    public LaboratoryMeasureType createLaboratoryMeasureType() {
        return new LaboratoryMeasureType();
    }

    /**
     * Create an instance of {@link GetComponentTypesResponse }
     * 
     */
    public GetComponentTypesResponse createGetComponentTypesResponse() {
        return new GetComponentTypesResponse();
    }

    /**
     * Create an instance of {@link GetLaboratoryMeasureTypesResponse }
     * 
     */
    public GetLaboratoryMeasureTypesResponse createGetLaboratoryMeasureTypesResponse() {
        return new GetLaboratoryMeasureTypesResponse();
    }

    /**
     * Create an instance of {@link GetComponentTypes }
     * 
     */
    public GetComponentTypes createGetComponentTypes() {
        return new GetComponentTypes();
    }

    /**
     * Create an instance of {@link OrderBloodComponentsResponse }
     * 
     */
    public OrderBloodComponentsResponse createOrderBloodComponentsResponse() {
        return new OrderBloodComponentsResponse();
    }

    /**
     * Create an instance of {@link GetLaboratoryMeasureTypes }
     * 
     */
    public GetLaboratoryMeasureTypes createGetLaboratoryMeasureTypes() {
        return new GetLaboratoryMeasureTypes();
    }

    /**
     * Create an instance of {@link OrderMedicalProcedure }
     * 
     */
    public OrderMedicalProcedure createOrderMedicalProcedure() {
        return new OrderMedicalProcedure();
    }

    /**
     * Create an instance of {@link DonorInfo }
     * 
     */
    public DonorInfo createDonorInfo() {
        return new DonorInfo();
    }

    /**
     * Create an instance of {@link ProcedureInfo }
     * 
     */
    public ProcedureInfo createProcedureInfo() {
        return new ProcedureInfo();
    }

    /**
     * Create an instance of {@link OrderMedicalProcedureResponse }
     * 
     */
    public OrderMedicalProcedureResponse createOrderMedicalProcedureResponse() {
        return new OrderMedicalProcedureResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OrderInformation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.korusconsulting.ru", name = "OrderInformation")
    public JAXBElement<OrderInformation> createOrderInformation(OrderInformation value) {
        return new JAXBElement<OrderInformation>(_OrderInformation_QNAME, OrderInformation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ComponentType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.korusconsulting.ru", name = "ComponentType")
    public JAXBElement<ComponentType> createComponentType(ComponentType value) {
        return new JAXBElement<ComponentType>(_ComponentType_QNAME, ComponentType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PatientCredentials }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.korusconsulting.ru", name = "PatientCredentials")
    public JAXBElement<PatientCredentials> createPatientCredentials(PatientCredentials value) {
        return new JAXBElement<PatientCredentials>(_PatientCredentials_QNAME, PatientCredentials.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OrderBloodComponents }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.korusconsulting.ru", name = "orderBloodComponents")
    public JAXBElement<OrderBloodComponents> createOrderBloodComponents(OrderBloodComponents value) {
        return new JAXBElement<OrderBloodComponents>(_OrderBloodComponents_QNAME, OrderBloodComponents.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProcedureTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.korusconsulting.ru", name = "getProcedureTypes")
    public JAXBElement<GetProcedureTypes> createGetProcedureTypes(GetProcedureTypes value) {
        return new JAXBElement<GetProcedureTypes>(_GetProcedureTypes_QNAME, GetProcedureTypes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProcedureType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.korusconsulting.ru", name = "ProcedureType")
    public JAXBElement<ProcedureType> createProcedureType(ProcedureType value) {
        return new JAXBElement<ProcedureType>(_ProcedureType_QNAME, ProcedureType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OrderResult }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.korusconsulting.ru", name = "OrderResult")
    public JAXBElement<OrderResult> createOrderResult(OrderResult value) {
        return new JAXBElement<OrderResult>(_OrderResult_QNAME, OrderResult.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetProcedureTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.korusconsulting.ru", name = "getProcedureTypesResponse")
    public JAXBElement<GetProcedureTypesResponse> createGetProcedureTypesResponse(GetProcedureTypesResponse value) {
        return new JAXBElement<GetProcedureTypesResponse>(_GetProcedureTypesResponse_QNAME, GetProcedureTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OrderMedicalProcedureResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.korusconsulting.ru", name = "orderMedicalProcedureResponse")
    public JAXBElement<OrderMedicalProcedureResponse> createOrderMedicalProcedureResponse(OrderMedicalProcedureResponse value) {
        return new JAXBElement<OrderMedicalProcedureResponse>(_OrderMedicalProcedureResponse_QNAME, OrderMedicalProcedureResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DonorInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.korusconsulting.ru", name = "DonorInfo")
    public JAXBElement<DonorInfo> createDonorInfo(DonorInfo value) {
        return new JAXBElement<DonorInfo>(_DonorInfo_QNAME, DonorInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OrderMedicalProcedure }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.korusconsulting.ru", name = "orderMedicalProcedure")
    public JAXBElement<OrderMedicalProcedure> createOrderMedicalProcedure(OrderMedicalProcedure value) {
        return new JAXBElement<OrderMedicalProcedure>(_OrderMedicalProcedure_QNAME, OrderMedicalProcedure.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ProcedureInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.korusconsulting.ru", name = "ProcedureInfo")
    public JAXBElement<ProcedureInfo> createProcedureInfo(ProcedureInfo value) {
        return new JAXBElement<ProcedureInfo>(_ProcedureInfo_QNAME, ProcedureInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetComponentTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.korusconsulting.ru", name = "getComponentTypes")
    public JAXBElement<GetComponentTypes> createGetComponentTypes(GetComponentTypes value) {
        return new JAXBElement<GetComponentTypes>(_GetComponentTypes_QNAME, GetComponentTypes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLaboratoryMeasureTypes }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.korusconsulting.ru", name = "getLaboratoryMeasureTypes")
    public JAXBElement<GetLaboratoryMeasureTypes> createGetLaboratoryMeasureTypes(GetLaboratoryMeasureTypes value) {
        return new JAXBElement<GetLaboratoryMeasureTypes>(_GetLaboratoryMeasureTypes_QNAME, GetLaboratoryMeasureTypes.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OrderBloodComponentsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.korusconsulting.ru", name = "orderBloodComponentsResponse")
    public JAXBElement<OrderBloodComponentsResponse> createOrderBloodComponentsResponse(OrderBloodComponentsResponse value) {
        return new JAXBElement<OrderBloodComponentsResponse>(_OrderBloodComponentsResponse_QNAME, OrderBloodComponentsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LaboratoryMeasureType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.korusconsulting.ru", name = "LaboratoryMeasureType")
    public JAXBElement<LaboratoryMeasureType> createLaboratoryMeasureType(LaboratoryMeasureType value) {
        return new JAXBElement<LaboratoryMeasureType>(_LaboratoryMeasureType_QNAME, LaboratoryMeasureType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetComponentTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.korusconsulting.ru", name = "getComponentTypesResponse")
    public JAXBElement<GetComponentTypesResponse> createGetComponentTypesResponse(GetComponentTypesResponse value) {
        return new JAXBElement<GetComponentTypesResponse>(_GetComponentTypesResponse_QNAME, GetComponentTypesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetLaboratoryMeasureTypesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.korusconsulting.ru", name = "getLaboratoryMeasureTypesResponse")
    public JAXBElement<GetLaboratoryMeasureTypesResponse> createGetLaboratoryMeasureTypesResponse(GetLaboratoryMeasureTypesResponse value) {
        return new JAXBElement<GetLaboratoryMeasureTypesResponse>(_GetLaboratoryMeasureTypesResponse_QNAME, GetLaboratoryMeasureTypesResponse.class, null, value);
    }

}
