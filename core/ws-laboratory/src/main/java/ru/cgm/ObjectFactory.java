
package ru.cgm;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ru.cgm package. 
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

    private final static QName _PatientInfoExtension_QNAME = new QName("", "extension");
    private final static QName _PatientInfoId_QNAME = new QName("", "id");
    private final static QName _PatientInfoGiven_QNAME = new QName("", "given");
    private final static QName _PatientInfoFamily_QNAME = new QName("", "family");
    private final static QName _PatientInfoName_QNAME = new QName("", "name");
    private final static QName _PatientInfoAdministrativeGenderCode_QNAME = new QName("", "administrativeGenderCode");
    private final static QName _PatientInfoBirthTime_QNAME = new QName("", "birthTime");
    private final static QName _StructuredBodyInfoComponent_QNAME = new QName("", "component");
    private final static QName _AssignedPersonInfoPrefix_QNAME = new QName("", "prefix");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ru.cgm
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link EncompassingEncounterInfo }
     * 
     */
    public EncompassingEncounterInfo createEncompassingEncounterInfo() {
        return new EncompassingEncounterInfo();
    }

    /**
     * Create an instance of {@link PatientInfo }
     * 
     */
    public PatientInfo createPatientInfo() {
        return new PatientInfo();
    }

    /**
     * Create an instance of {@link SectionInfo }
     * 
     */
    public SectionInfo createSectionInfo() {
        return new SectionInfo();
    }

    /**
     * Create an instance of {@link PatientRoleInfo }
     * 
     */
    public PatientRoleInfo createPatientRoleInfo() {
        return new PatientRoleInfo();
    }

    /**
     * Create an instance of {@link RecordTargetInfo }
     * 
     */
    public RecordTargetInfo createRecordTargetInfo() {
        return new RecordTargetInfo();
    }

    /**
     * Create an instance of {@link ComponentInfo }
     * 
     */
    public ComponentInfo createComponentInfo() {
        return new ComponentInfo();
    }

    /**
     * Create an instance of {@link SpecimenInfo }
     * 
     */
    public SpecimenInfo createSpecimenInfo() {
        return new SpecimenInfo();
    }

    /**
     * Create an instance of {@link AssignedPersonInfo }
     * 
     */
    public AssignedPersonInfo createAssignedPersonInfo() {
        return new AssignedPersonInfo();
    }

    /**
     * Create an instance of {@link AuthorInfo }
     * 
     */
    public AuthorInfo createAuthorInfo() {
        return new AuthorInfo();
    }

    /**
     * Create an instance of {@link SpecimenRole }
     * 
     */
    public SpecimenRole createSpecimenRole() {
        return new SpecimenRole();
    }

    /**
     * Create an instance of {@link ComponentInfo2 }
     * 
     */
    public ComponentInfo2 createComponentInfo2() {
        return new ComponentInfo2();
    }

    /**
     * Create an instance of {@link RepresentedCustodianOrganizationInfo }
     * 
     */
    public RepresentedCustodianOrganizationInfo createRepresentedCustodianOrganizationInfo() {
        return new RepresentedCustodianOrganizationInfo();
    }

    /**
     * Create an instance of {@link CustodianInfo }
     * 
     */
    public CustodianInfo createCustodianInfo() {
        return new CustodianInfo();
    }

    /**
     * Create an instance of {@link AssignedCustodianInfo }
     * 
     */
    public AssignedCustodianInfo createAssignedCustodianInfo() {
        return new AssignedCustodianInfo();
    }

    /**
     * Create an instance of {@link AssignedAuthorInfo2 }
     * 
     */
    public AssignedAuthorInfo2 createAssignedAuthorInfo2() {
        return new AssignedAuthorInfo2();
    }

    /**
     * Create an instance of {@link AssignedAuthorInfo }
     * 
     */
    public AssignedAuthorInfo createAssignedAuthorInfo() {
        return new AssignedAuthorInfo();
    }

    /**
     * Create an instance of {@link CodeSpecimenInfo }
     * 
     */
    public CodeSpecimenInfo createCodeSpecimenInfo() {
        return new CodeSpecimenInfo();
    }

    /**
     * Create an instance of {@link SpecimenPlayingEntity }
     * 
     */
    public SpecimenPlayingEntity createSpecimenPlayingEntity() {
        return new SpecimenPlayingEntity();
    }

    /**
     * Create an instance of {@link ComponentOfInfo }
     * 
     */
    public ComponentOfInfo createComponentOfInfo() {
        return new ComponentOfInfo();
    }

    /**
     * Create an instance of {@link StructuredBodyInfo }
     * 
     */
    public StructuredBodyInfo createStructuredBodyInfo() {
        return new StructuredBodyInfo();
    }

    /**
     * Create an instance of {@link HL7Document }
     * 
     */
    public HL7Document createHL7Document() {
        return new HL7Document();
    }

    /**
     * Create an instance of {@link EntryInfo }
     * 
     */
    public EntryInfo createEntryInfo() {
        return new EntryInfo();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "extension", scope = PatientInfo.class)
    public JAXBElement<String> createPatientInfoExtension(String value) {
        return new JAXBElement<String>(_PatientInfoExtension_QNAME, String.class, PatientInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "id", scope = PatientInfo.class)
    public JAXBElement<String> createPatientInfoId(String value) {
        return new JAXBElement<String>(_PatientInfoId_QNAME, String.class, PatientInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "given", scope = PatientInfo.class)
    public JAXBElement<String> createPatientInfoGiven(String value) {
        return new JAXBElement<String>(_PatientInfoGiven_QNAME, String.class, PatientInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "family", scope = PatientInfo.class)
    public JAXBElement<String> createPatientInfoFamily(String value) {
        return new JAXBElement<String>(_PatientInfoFamily_QNAME, String.class, PatientInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "name", scope = PatientInfo.class)
    public JAXBElement<String> createPatientInfoName(String value) {
        return new JAXBElement<String>(_PatientInfoName_QNAME, String.class, PatientInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "administrativeGenderCode", scope = PatientInfo.class)
    public JAXBElement<String> createPatientInfoAdministrativeGenderCode(String value) {
        return new JAXBElement<String>(_PatientInfoAdministrativeGenderCode_QNAME, String.class, PatientInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "birthTime", scope = PatientInfo.class)
    public JAXBElement<XMLGregorianCalendar> createPatientInfoBirthTime(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_PatientInfoBirthTime_QNAME, XMLGregorianCalendar.class, PatientInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ComponentInfo2 }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "component", scope = StructuredBodyInfo.class)
    public JAXBElement<ComponentInfo2> createStructuredBodyInfoComponent(ComponentInfo2 value) {
        return new JAXBElement<ComponentInfo2>(_StructuredBodyInfoComponent_QNAME, ComponentInfo2 .class, StructuredBodyInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "given", scope = AssignedPersonInfo.class)
    public JAXBElement<String> createAssignedPersonInfoGiven(String value) {
        return new JAXBElement<String>(_PatientInfoGiven_QNAME, String.class, AssignedPersonInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "family", scope = AssignedPersonInfo.class)
    public JAXBElement<String> createAssignedPersonInfoFamily(String value) {
        return new JAXBElement<String>(_PatientInfoFamily_QNAME, String.class, AssignedPersonInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "name", scope = AssignedPersonInfo.class)
    public JAXBElement<String> createAssignedPersonInfoName(String value) {
        return new JAXBElement<String>(_PatientInfoName_QNAME, String.class, AssignedPersonInfo.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "prefix", scope = AssignedPersonInfo.class)
    public JAXBElement<String> createAssignedPersonInfoPrefix(String value) {
        return new JAXBElement<String>(_AssignedPersonInfoPrefix_QNAME, String.class, AssignedPersonInfo.class, value);
    }

}
