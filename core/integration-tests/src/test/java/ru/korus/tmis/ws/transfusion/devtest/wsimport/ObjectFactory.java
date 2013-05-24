package ru.korus.tmis.ws.transfusion.devtest.wsimport;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java element interface generated in the
 * ru.korus.tmis.ws.transfusion.devtest.wsimport package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the Java representation for XML content. The Java representation of XML content can
 * consist of schema derived interfaces and classes representing the binding of schema type definitions, element declarations and model groups. Factory methods
 * for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SetOrderIssueResult_QNAME = new QName("http://korus.ru/tmis/ws/transfusion", "setOrderIssueResult");
    private final static QName _SetProcedureResult_QNAME = new QName("http://korus.ru/tmis/ws/transfusion", "setProcedureResult");
    private final static QName _GetDivisions_QNAME = new QName("http://korus.ru/tmis/ws/transfusion", "getDivisions");
    private final static QName _SetOrderIssueResultResponse_QNAME = new QName("http://korus.ru/tmis/ws/transfusion", "setOrderIssueResultResponse");
    private final static QName _GetDivisionsResponse_QNAME = new QName("http://korus.ru/tmis/ws/transfusion", "getDivisionsResponse");
    private final static QName _SetProcedureResultResponse_QNAME = new QName("http://korus.ru/tmis/ws/transfusion", "setProcedureResultResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ru.korus.tmis.ws.transfusion.devtest.wsimport
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PatientCredentials }
     * 
     */
    public PatientCredentials createPatientCredentials() {
        return new PatientCredentials();
    }

    /**
     * Create an instance of {@link SetProcedureResult }
     * 
     */
    public SetProcedureResult createSetProcedureResult() {
        return new SetProcedureResult();
    }

    /**
     * Create an instance of {@link SetOrderIssueResult }
     * 
     */
    public SetOrderIssueResult createSetOrderIssueResult() {
        return new SetOrderIssueResult();
    }

    /**
     * Create an instance of {@link SetProcedureResultResponse }
     * 
     */
    public SetProcedureResultResponse createSetProcedureResultResponse() {
        return new SetProcedureResultResponse();
    }

    /**
     * Create an instance of {@link GetDivisionsResponse }
     * 
     */
    public GetDivisionsResponse createGetDivisionsResponse() {
        return new GetDivisionsResponse();
    }

    /**
     * Create an instance of {@link SetOrderIssueResultResponse }
     * 
     */
    public SetOrderIssueResultResponse createSetOrderIssueResultResponse() {
        return new SetOrderIssueResultResponse();
    }

    /**
     * Create an instance of {@link GetDivisions }
     * 
     */
    public GetDivisions createGetDivisions() {
        return new GetDivisions();
    }

    /**
     * Create an instance of {@link DivisionInfo }
     * 
     */
    public DivisionInfo createDivisionInfo() {
        return new DivisionInfo();
    }

    /**
     * Create an instance of {@link EritrocyteMass }
     * 
     */
    public EritrocyteMass createEritrocyteMass() {
        return new EritrocyteMass();
    }

    /**
     * Create an instance of {@link IssueResult }
     * 
     */
    public IssueResult createIssueResult() {
        return new IssueResult();
    }

    /**
     * Create an instance of {@link FinalVolume }
     * 
     */
    public FinalVolume createFinalVolume() {
        return new FinalVolume();
    }

    /**
     * Create an instance of {@link LaboratoryMeasure }
     * 
     */
    public LaboratoryMeasure createLaboratoryMeasure() {
        return new LaboratoryMeasure();
    }

    /**
     * Create an instance of {@link OrderIssueInfo }
     * 
     */
    public OrderIssueInfo createOrderIssueInfo() {
        return new OrderIssueInfo();
    }

    /**
     * Create an instance of {@link ProcedureInfo }
     * 
     */
    public ProcedureInfo createProcedureInfo() {
        return new ProcedureInfo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetOrderIssueResult }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://korus.ru/tmis/ws/transfusion", name = "setOrderIssueResult")
    public JAXBElement<SetOrderIssueResult> createSetOrderIssueResult(final SetOrderIssueResult value) {
        return new JAXBElement<SetOrderIssueResult>(_SetOrderIssueResult_QNAME, SetOrderIssueResult.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetProcedureResult }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://korus.ru/tmis/ws/transfusion", name = "setProcedureResult")
    public JAXBElement<SetProcedureResult> createSetProcedureResult(final SetProcedureResult value) {
        return new JAXBElement<SetProcedureResult>(_SetProcedureResult_QNAME, SetProcedureResult.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDivisions }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://korus.ru/tmis/ws/transfusion", name = "getDivisions")
    public JAXBElement<GetDivisions> createGetDivisions(final GetDivisions value) {
        return new JAXBElement<GetDivisions>(_GetDivisions_QNAME, GetDivisions.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetOrderIssueResultResponse }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://korus.ru/tmis/ws/transfusion", name = "setOrderIssueResultResponse")
    public JAXBElement<SetOrderIssueResultResponse> createSetOrderIssueResultResponse(final SetOrderIssueResultResponse value) {
        return new JAXBElement<SetOrderIssueResultResponse>(_SetOrderIssueResultResponse_QNAME, SetOrderIssueResultResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDivisionsResponse }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://korus.ru/tmis/ws/transfusion", name = "getDivisionsResponse")
    public JAXBElement<GetDivisionsResponse> createGetDivisionsResponse(final GetDivisionsResponse value) {
        return new JAXBElement<GetDivisionsResponse>(_GetDivisionsResponse_QNAME, GetDivisionsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetProcedureResultResponse }{@code >}
     * 
     */
    @XmlElementDecl(namespace = "http://korus.ru/tmis/ws/transfusion", name = "setProcedureResultResponse")
    public JAXBElement<SetProcedureResultResponse> createSetProcedureResultResponse(final SetProcedureResultResponse value) {
        return new JAXBElement<SetProcedureResultResponse>(_SetProcedureResultResponse_QNAME, SetProcedureResultResponse.class, null, value);
    }

}
