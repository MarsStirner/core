
package ru.korus.tmis.ehr.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ru.korus.tmis.ehr.ws package. 
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

    private final static QName _PatientQuery_QNAME = new QName("", "patientQuery");
    private final static QName _DocumentQuery_QNAME = new QName("", "documentQuery");
    private final static QName _RetrieveDocumentQuery_QNAME = new QName("", "retrieveDocumentQuery");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ru.korus.tmis.ehr.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DocumentQuery }
     * 
     */
    public DocumentQuery createDocumentQuery() {
        return new DocumentQuery();
    }

    /**
     * Create an instance of {@link PatientQuery }
     * 
     */
    public PatientQuery createPatientQuery() {
        return new PatientQuery();
    }

    /**
     * Create an instance of {@link RetrieveDocumentQuery }
     * 
     */
    public RetrieveDocumentQuery createRetrieveDocumentQuery() {
        return new RetrieveDocumentQuery();
    }

    /**
     * Create an instance of {@link BaseSerial }
     * 
     */
    public BaseSerial createBaseSerial() {
        return new BaseSerial();
    }

    /**
     * Create an instance of {@link InstanceIdentifier }
     * 
     */
    public InstanceIdentifier createInstanceIdentifier() {
        return new InstanceIdentifier();
    }

    /**
     * Create an instance of {@link DocumentParams }
     * 
     */
    public DocumentParams createDocumentParams() {
        return new DocumentParams();
    }

    /**
     * Create an instance of {@link PatientParams }
     * 
     */
    public PatientParams createPatientParams() {
        return new PatientParams();
    }

    /**
     * Create an instance of {@link Employee }
     * 
     */
    public Employee createEmployee() {
        return new Employee();
    }

    /**
     * Create an instance of {@link CodeAndName }
     * 
     */
    public CodeAndName createCodeAndName() {
        return new CodeAndName();
    }

    /**
     * Create an instance of {@link ContactInfo }
     * 
     */
    public ContactInfo createContactInfo() {
        return new ContactInfo();
    }

    /**
     * Create an instance of {@link SeriesNumberAndType }
     * 
     */
    public SeriesNumberAndType createSeriesNumberAndType() {
        return new SeriesNumberAndType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PatientQuery }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "patientQuery")
    public JAXBElement<PatientQuery> createPatientQuery(PatientQuery value) {
        return new JAXBElement<PatientQuery>(_PatientQuery_QNAME, PatientQuery.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DocumentQuery }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "documentQuery")
    public JAXBElement<DocumentQuery> createDocumentQuery(DocumentQuery value) {
        return new JAXBElement<DocumentQuery>(_DocumentQuery_QNAME, DocumentQuery.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetrieveDocumentQuery }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "retrieveDocumentQuery")
    public JAXBElement<RetrieveDocumentQuery> createRetrieveDocumentQuery(RetrieveDocumentQuery value) {
        return new JAXBElement<RetrieveDocumentQuery>(_RetrieveDocumentQuery_QNAME, RetrieveDocumentQuery.class, null, value);
    }

}
