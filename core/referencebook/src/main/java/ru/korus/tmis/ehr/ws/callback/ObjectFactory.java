
package ru.korus.tmis.ehr.ws.callback;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ru.korus.tmis.ehr.ws.callback package. 
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

    private final static QName _PatientQueryResponse_QNAME = new QName("", "patientQueryResponse");
    private final static QName _RetrieveDocumentQueryResponse_QNAME = new QName("", "retrieveDocumentQueryResponse");
    private final static QName _ContainerResponse_QNAME = new QName("", "containerResponse");
    private final static QName _DocumentQueryResponse_QNAME = new QName("", "documentQueryResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ru.korus.tmis.ehr.ws.callback
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PatientResponse }
     * 
     */
    public PatientResponse createPatientResponse() {
        return new PatientResponse();
    }

    /**
     * Create an instance of {@link RetrieveDocumentResponse }
     * 
     */
    public RetrieveDocumentResponse createRetrieveDocumentResponse() {
        return new RetrieveDocumentResponse();
    }

    /**
     * Create an instance of {@link BaseResponse }
     * 
     */
    public BaseResponse createBaseResponse() {
        return new BaseResponse();
    }

    /**
     * Create an instance of {@link DocumentResponse }
     * 
     */
    public DocumentResponse createDocumentResponse() {
        return new DocumentResponse();
    }

    /**
     * Create an instance of {@link ArrayOfdocumentIEMKDocument }
     * 
     */
    public ArrayOfdocumentIEMKDocument createArrayOfdocumentIEMKDocument() {
        return new ArrayOfdocumentIEMKDocument();
    }

    /**
     * Create an instance of {@link ArrayOfallergyAllergy }
     * 
     */
    public ArrayOfallergyAllergy createArrayOfallergyAllergy() {
        return new ArrayOfallergyAllergy();
    }

    /**
     * Create an instance of {@link ArrayOfencounterEncounter }
     * 
     */
    public ArrayOfencounterEncounter createArrayOfencounterEncounter() {
        return new ArrayOfencounterEncounter();
    }

    /**
     * Create an instance of {@link ArrayOfsickLeaveDocumentSickLeaveDocument }
     * 
     */
    public ArrayOfsickLeaveDocumentSickLeaveDocument createArrayOfsickLeaveDocumentSickLeaveDocument() {
        return new ArrayOfsickLeaveDocumentSickLeaveDocument();
    }

    /**
     * Create an instance of {@link ArrayOfErrorError }
     * 
     */
    public ArrayOfErrorError createArrayOfErrorError() {
        return new ArrayOfErrorError();
    }

    /**
     * Create an instance of {@link CodeAndName }
     * 
     */
    public CodeAndName createCodeAndName() {
        return new CodeAndName();
    }

    /**
     * Create an instance of {@link Diagnosis }
     * 
     */
    public Diagnosis createDiagnosis() {
        return new Diagnosis();
    }

    /**
     * Create an instance of {@link IdentityDocument }
     * 
     */
    public IdentityDocument createIdentityDocument() {
        return new IdentityDocument();
    }

    /**
     * Create an instance of {@link ArrayOfPrivilegePrivilege }
     * 
     */
    public ArrayOfPrivilegePrivilege createArrayOfPrivilegePrivilege() {
        return new ArrayOfPrivilegePrivilege();
    }

    /**
     * Create an instance of {@link ArrayOfidentityDocumentIdentityDocument }
     * 
     */
    public ArrayOfidentityDocumentIdentityDocument createArrayOfidentityDocumentIdentityDocument() {
        return new ArrayOfidentityDocumentIdentityDocument();
    }

    /**
     * Create an instance of {@link Trustee }
     * 
     */
    public Trustee createTrustee() {
        return new Trustee();
    }

    /**
     * Create an instance of {@link MedService }
     * 
     */
    public MedService createMedService() {
        return new MedService();
    }

    /**
     * Create an instance of {@link IEMKPatient }
     * 
     */
    public IEMKPatient createIEMKPatient() {
        return new IEMKPatient();
    }

    /**
     * Create an instance of {@link ArrayOfdocumentDocument }
     * 
     */
    public ArrayOfdocumentDocument createArrayOfdocumentDocument() {
        return new ArrayOfdocumentDocument();
    }

    /**
     * Create an instance of {@link BaseSerial }
     * 
     */
    public BaseSerial createBaseSerial() {
        return new BaseSerial();
    }

    /**
     * Create an instance of {@link Allergy }
     * 
     */
    public Allergy createAllergy() {
        return new Allergy();
    }

    /**
     * Create an instance of {@link ArrayOfInsuranceInsurance }
     * 
     */
    public ArrayOfInsuranceInsurance createArrayOfInsuranceInsurance() {
        return new ArrayOfInsuranceInsurance();
    }

    /**
     * Create an instance of {@link ContainerDocument }
     * 
     */
    public ContainerDocument createContainerDocument() {
        return new ContainerDocument();
    }

    /**
     * Create an instance of {@link ArrayOfOccupationOccupation }
     * 
     */
    public ArrayOfOccupationOccupation createArrayOfOccupationOccupation() {
        return new ArrayOfOccupationOccupation();
    }

    /**
     * Create an instance of {@link PrivilegeDocument }
     * 
     */
    public PrivilegeDocument createPrivilegeDocument() {
        return new PrivilegeDocument();
    }

    /**
     * Create an instance of {@link ArrayOfoccupationHistoryEntry }
     * 
     */
    public ArrayOfoccupationHistoryEntry createArrayOfoccupationHistoryEntry() {
        return new ArrayOfoccupationHistoryEntry();
    }

    /**
     * Create an instance of {@link ContactInfo }
     * 
     */
    public ContactInfo createContactInfo() {
        return new ContactInfo();
    }

    /**
     * Create an instance of {@link ArrayOfdisabilityDisability }
     * 
     */
    public ArrayOfdisabilityDisability createArrayOfdisabilityDisability() {
        return new ArrayOfdisabilityDisability();
    }

    /**
     * Create an instance of {@link IEMKDocument }
     * 
     */
    public IEMKDocument createIEMKDocument() {
        return new IEMKDocument();
    }

    /**
     * Create an instance of {@link ArrayOftrusteeTrustee }
     * 
     */
    public ArrayOftrusteeTrustee createArrayOftrusteeTrustee() {
        return new ArrayOftrusteeTrustee();
    }

    /**
     * Create an instance of {@link Privilege }
     * 
     */
    public Privilege createPrivilege() {
        return new Privilege();
    }

    /**
     * Create an instance of {@link Disability }
     * 
     */
    public Disability createDisability() {
        return new Disability();
    }

    /**
     * Create an instance of {@link SickLeaveDocument }
     * 
     */
    public SickLeaveDocument createSickLeaveDocument() {
        return new SickLeaveDocument();
    }

    /**
     * Create an instance of {@link ArrayOfmesCodeString }
     * 
     */
    public ArrayOfmesCodeString createArrayOfmesCodeString() {
        return new ArrayOfmesCodeString();
    }

    /**
     * Create an instance of {@link Occupation }
     * 
     */
    public Occupation createOccupation() {
        return new Occupation();
    }

    /**
     * Create an instance of {@link Encounter }
     * 
     */
    public Encounter createEncounter() {
        return new Encounter();
    }

    /**
     * Create an instance of {@link ArrayOfserviceMedService }
     * 
     */
    public ArrayOfserviceMedService createArrayOfserviceMedService() {
        return new ArrayOfserviceMedService();
    }

    /**
     * Create an instance of {@link HistoryEntry }
     * 
     */
    public HistoryEntry createHistoryEntry() {
        return new HistoryEntry();
    }

    /**
     * Create an instance of {@link Container }
     * 
     */
    public Container createContainer() {
        return new Container();
    }

    /**
     * Create an instance of {@link Document }
     * 
     */
    public Document createDocument() {
        return new Document();
    }

    /**
     * Create an instance of {@link DispensarySupervision }
     * 
     */
    public DispensarySupervision createDispensarySupervision() {
        return new DispensarySupervision();
    }

    /**
     * Create an instance of {@link ArrayOfCodeAndNameCodeAndName }
     * 
     */
    public ArrayOfCodeAndNameCodeAndName createArrayOfCodeAndNameCodeAndName() {
        return new ArrayOfCodeAndNameCodeAndName();
    }

    /**
     * Create an instance of {@link Admission }
     * 
     */
    public Admission createAdmission() {
        return new Admission();
    }

    /**
     * Create an instance of {@link Employee }
     * 
     */
    public Employee createEmployee() {
        return new Employee();
    }

    /**
     * Create an instance of {@link Insurance }
     * 
     */
    public Insurance createInsurance() {
        return new Insurance();
    }

    /**
     * Create an instance of {@link Error }
     * 
     */
    public Error createError() {
        return new Error();
    }

    /**
     * Create an instance of {@link ArrayOfdiagnosisDiagnosis }
     * 
     */
    public ArrayOfdiagnosisDiagnosis createArrayOfdiagnosisDiagnosis() {
        return new ArrayOfdiagnosisDiagnosis();
    }

    /**
     * Create an instance of {@link ArrayOfaddressHistoryEntry }
     * 
     */
    public ArrayOfaddressHistoryEntry createArrayOfaddressHistoryEntry() {
        return new ArrayOfaddressHistoryEntry();
    }

    /**
     * Create an instance of {@link Address }
     * 
     */
    public Address createAddress() {
        return new Address();
    }

    /**
     * Create an instance of {@link ArrayOfpatientIEMKPatient }
     * 
     */
    public ArrayOfpatientIEMKPatient createArrayOfpatientIEMKPatient() {
        return new ArrayOfpatientIEMKPatient();
    }

    /**
     * Create an instance of {@link Patient }
     * 
     */
    public Patient createPatient() {
        return new Patient();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PatientResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "patientQueryResponse")
    public JAXBElement<PatientResponse> createPatientQueryResponse(PatientResponse value) {
        return new JAXBElement<PatientResponse>(_PatientQueryResponse_QNAME, PatientResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetrieveDocumentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "retrieveDocumentQueryResponse")
    public JAXBElement<RetrieveDocumentResponse> createRetrieveDocumentQueryResponse(RetrieveDocumentResponse value) {
        return new JAXBElement<RetrieveDocumentResponse>(_RetrieveDocumentQueryResponse_QNAME, RetrieveDocumentResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BaseResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "containerResponse")
    public JAXBElement<BaseResponse> createContainerResponse(BaseResponse value) {
        return new JAXBElement<BaseResponse>(_ContainerResponse_QNAME, BaseResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DocumentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "documentQueryResponse")
    public JAXBElement<DocumentResponse> createDocumentQueryResponse(DocumentResponse value) {
        return new JAXBElement<DocumentResponse>(_DocumentQueryResponse_QNAME, DocumentResponse.class, null, value);
    }

}
