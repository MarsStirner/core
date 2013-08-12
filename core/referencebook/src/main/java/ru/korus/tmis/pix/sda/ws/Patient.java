
package ru.korus.tmis.pix.sda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Patient complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Patient">
 *   &lt;complexContent>
 *     &lt;extension base="{}SuperClass">
 *       &lt;sequence>
 *         &lt;element name="MPIID" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="220"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Name" type="{}Name" minOccurs="0"/>
 *         &lt;element name="MothersMaidenName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Aliases" type="{}ArrayOfNameName" minOccurs="0"/>
 *         &lt;element name="PrimaryLanguage" type="{}Language" minOccurs="0"/>
 *         &lt;element name="OtherLanguages" type="{}ArrayOfPatientLanguagePatientLanguage" minOccurs="0"/>
 *         &lt;element name="Religion" type="{}Religion" minOccurs="0"/>
 *         &lt;element name="MaritalStatus" type="{}MaritalStatus" minOccurs="0"/>
 *         &lt;element name="Gender" type="{}Gender" minOccurs="0"/>
 *         &lt;element name="Race" type="{}Race" minOccurs="0"/>
 *         &lt;element name="EthnicGroup" type="{}EthnicGroup" minOccurs="0"/>
 *         &lt;element name="SupportContacts" type="{}ArrayOfSupportContactSupportContact" minOccurs="0"/>
 *         &lt;element name="BirthTime" type="{}TimeStamp" minOccurs="0"/>
 *         &lt;element name="BirthPlace" type="{}Address" minOccurs="0"/>
 *         &lt;element name="DeathTime" type="{}TimeStamp" minOccurs="0"/>
 *         &lt;element name="IsDead" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="DeathLocation" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="255"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="DeathDeclaredBy" type="{}CareProvider" minOccurs="0"/>
 *         &lt;element name="Citizenship" type="{}Citizenship" minOccurs="0"/>
 *         &lt;element name="PatientNumbers" type="{}ArrayOfPatientNumberPatientNumber" minOccurs="0"/>
 *         &lt;element name="PriorPatientNumbers" type="{}ArrayOfPatientNumberPatientNumber" minOccurs="0"/>
 *         &lt;element name="Addresses" type="{}ArrayOfAddressAddress" minOccurs="0"/>
 *         &lt;element name="ContactInfo" type="{}ContactInfo" minOccurs="0"/>
 *         &lt;element name="FamilyDoctor" type="{}FamilyDoctor" minOccurs="0"/>
 *         &lt;element name="InactiveMRNs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Occupation" type="{}Occupation" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Patient", propOrder = {
    "mpiid",
    "name",
    "mothersMaidenName",
    "aliases",
    "primaryLanguage",
    "otherLanguages",
    "religion",
    "maritalStatus",
    "gender",
    "race",
    "ethnicGroup",
    "supportContacts",
    "birthTime",
    "birthPlace",
    "deathTime",
    "isDead",
    "deathLocation",
    "deathDeclaredBy",
    "citizenship",
    "patientNumbers",
    "priorPatientNumbers",
    "addresses",
    "contactInfo",
    "familyDoctor",
    "inactiveMRNs",
    "occupation"
})
public class Patient
    extends SuperClass
{

    @XmlElement(name = "MPIID")
    protected String mpiid;
    @XmlElement(name = "Name")
    protected Name name;
    @XmlElement(name = "MothersMaidenName")
    protected String mothersMaidenName;
    @XmlElement(name = "Aliases")
    protected ArrayOfNameName aliases;
    @XmlElement(name = "PrimaryLanguage")
    protected Language primaryLanguage;
    @XmlElement(name = "OtherLanguages")
    protected ArrayOfPatientLanguagePatientLanguage otherLanguages;
    @XmlElement(name = "Religion")
    protected Religion religion;
    @XmlElement(name = "MaritalStatus")
    protected MaritalStatus maritalStatus;
    @XmlElement(name = "Gender")
    protected Gender gender;
    @XmlElement(name = "Race")
    protected Race race;
    @XmlElement(name = "EthnicGroup")
    protected EthnicGroup ethnicGroup;
    @XmlElement(name = "SupportContacts")
    protected ArrayOfSupportContactSupportContact supportContacts;
    @XmlElement(name = "BirthTime")
    protected XMLGregorianCalendar birthTime;
    @XmlElement(name = "BirthPlace")
    protected Address birthPlace;
    @XmlElement(name = "DeathTime")
    protected XMLGregorianCalendar deathTime;
    @XmlElement(name = "IsDead")
    protected Boolean isDead;
    @XmlElement(name = "DeathLocation")
    protected String deathLocation;
    @XmlElement(name = "DeathDeclaredBy")
    protected CareProvider deathDeclaredBy;
    @XmlElement(name = "Citizenship")
    protected Citizenship citizenship;
    @XmlElement(name = "PatientNumbers")
    protected ArrayOfPatientNumberPatientNumber patientNumbers;
    @XmlElement(name = "PriorPatientNumbers")
    protected ArrayOfPatientNumberPatientNumber priorPatientNumbers;
    @XmlElement(name = "Addresses")
    protected ArrayOfAddressAddress addresses;
    @XmlElement(name = "ContactInfo")
    protected ContactInfo contactInfo;
    @XmlElement(name = "FamilyDoctor")
    protected FamilyDoctor familyDoctor;
    @XmlElement(name = "InactiveMRNs")
    protected String inactiveMRNs;
    @XmlElement(name = "Occupation")
    protected Occupation occupation;

    /**
     * Gets the value of the mpiid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMPIID() {
        return mpiid;
    }

    /**
     * Sets the value of the mpiid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMPIID(String value) {
        this.mpiid = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link Name }
     *     
     */
    public Name getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link Name }
     *     
     */
    public void setName(Name value) {
        this.name = value;
    }

    /**
     * Gets the value of the mothersMaidenName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMothersMaidenName() {
        return mothersMaidenName;
    }

    /**
     * Sets the value of the mothersMaidenName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMothersMaidenName(String value) {
        this.mothersMaidenName = value;
    }

    /**
     * Gets the value of the aliases property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfNameName }
     *     
     */
    public ArrayOfNameName getAliases() {
        return aliases;
    }

    /**
     * Sets the value of the aliases property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfNameName }
     *     
     */
    public void setAliases(ArrayOfNameName value) {
        this.aliases = value;
    }

    /**
     * Gets the value of the primaryLanguage property.
     * 
     * @return
     *     possible object is
     *     {@link Language }
     *     
     */
    public Language getPrimaryLanguage() {
        return primaryLanguage;
    }

    /**
     * Sets the value of the primaryLanguage property.
     * 
     * @param value
     *     allowed object is
     *     {@link Language }
     *     
     */
    public void setPrimaryLanguage(Language value) {
        this.primaryLanguage = value;
    }

    /**
     * Gets the value of the otherLanguages property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPatientLanguagePatientLanguage }
     *     
     */
    public ArrayOfPatientLanguagePatientLanguage getOtherLanguages() {
        return otherLanguages;
    }

    /**
     * Sets the value of the otherLanguages property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPatientLanguagePatientLanguage }
     *     
     */
    public void setOtherLanguages(ArrayOfPatientLanguagePatientLanguage value) {
        this.otherLanguages = value;
    }

    /**
     * Gets the value of the religion property.
     * 
     * @return
     *     possible object is
     *     {@link Religion }
     *     
     */
    public Religion getReligion() {
        return religion;
    }

    /**
     * Sets the value of the religion property.
     * 
     * @param value
     *     allowed object is
     *     {@link Religion }
     *     
     */
    public void setReligion(Religion value) {
        this.religion = value;
    }

    /**
     * Gets the value of the maritalStatus property.
     * 
     * @return
     *     possible object is
     *     {@link MaritalStatus }
     *     
     */
    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * Sets the value of the maritalStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link MaritalStatus }
     *     
     */
    public void setMaritalStatus(MaritalStatus value) {
        this.maritalStatus = value;
    }

    /**
     * Gets the value of the gender property.
     * 
     * @return
     *     possible object is
     *     {@link Gender }
     *     
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Sets the value of the gender property.
     * 
     * @param value
     *     allowed object is
     *     {@link Gender }
     *     
     */
    public void setGender(Gender value) {
        this.gender = value;
    }

    /**
     * Gets the value of the race property.
     * 
     * @return
     *     possible object is
     *     {@link Race }
     *     
     */
    public Race getRace() {
        return race;
    }

    /**
     * Sets the value of the race property.
     * 
     * @param value
     *     allowed object is
     *     {@link Race }
     *     
     */
    public void setRace(Race value) {
        this.race = value;
    }

    /**
     * Gets the value of the ethnicGroup property.
     * 
     * @return
     *     possible object is
     *     {@link EthnicGroup }
     *     
     */
    public EthnicGroup getEthnicGroup() {
        return ethnicGroup;
    }

    /**
     * Sets the value of the ethnicGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link EthnicGroup }
     *     
     */
    public void setEthnicGroup(EthnicGroup value) {
        this.ethnicGroup = value;
    }

    /**
     * Gets the value of the supportContacts property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSupportContactSupportContact }
     *     
     */
    public ArrayOfSupportContactSupportContact getSupportContacts() {
        return supportContacts;
    }

    /**
     * Sets the value of the supportContacts property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSupportContactSupportContact }
     *     
     */
    public void setSupportContacts(ArrayOfSupportContactSupportContact value) {
        this.supportContacts = value;
    }

    /**
     * Gets the value of the birthTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBirthTime() {
        return birthTime;
    }

    /**
     * Sets the value of the birthTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBirthTime(XMLGregorianCalendar value) {
        this.birthTime = value;
    }

    /**
     * Gets the value of the birthPlace property.
     * 
     * @return
     *     possible object is
     *     {@link Address }
     *     
     */
    public Address getBirthPlace() {
        return birthPlace;
    }

    /**
     * Sets the value of the birthPlace property.
     * 
     * @param value
     *     allowed object is
     *     {@link Address }
     *     
     */
    public void setBirthPlace(Address value) {
        this.birthPlace = value;
    }

    /**
     * Gets the value of the deathTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDeathTime() {
        return deathTime;
    }

    /**
     * Sets the value of the deathTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDeathTime(XMLGregorianCalendar value) {
        this.deathTime = value;
    }

    /**
     * Gets the value of the isDead property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsDead() {
        return isDead;
    }

    /**
     * Sets the value of the isDead property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsDead(Boolean value) {
        this.isDead = value;
    }

    /**
     * Gets the value of the deathLocation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeathLocation() {
        return deathLocation;
    }

    /**
     * Sets the value of the deathLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeathLocation(String value) {
        this.deathLocation = value;
    }

    /**
     * Gets the value of the deathDeclaredBy property.
     * 
     * @return
     *     possible object is
     *     {@link CareProvider }
     *     
     */
    public CareProvider getDeathDeclaredBy() {
        return deathDeclaredBy;
    }

    /**
     * Sets the value of the deathDeclaredBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link CareProvider }
     *     
     */
    public void setDeathDeclaredBy(CareProvider value) {
        this.deathDeclaredBy = value;
    }

    /**
     * Gets the value of the citizenship property.
     * 
     * @return
     *     possible object is
     *     {@link Citizenship }
     *     
     */
    public Citizenship getCitizenship() {
        return citizenship;
    }

    /**
     * Sets the value of the citizenship property.
     * 
     * @param value
     *     allowed object is
     *     {@link Citizenship }
     *     
     */
    public void setCitizenship(Citizenship value) {
        this.citizenship = value;
    }

    /**
     * Gets the value of the patientNumbers property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPatientNumberPatientNumber }
     *     
     */
    public ArrayOfPatientNumberPatientNumber getPatientNumbers() {
        return patientNumbers;
    }

    /**
     * Sets the value of the patientNumbers property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPatientNumberPatientNumber }
     *     
     */
    public void setPatientNumbers(ArrayOfPatientNumberPatientNumber value) {
        this.patientNumbers = value;
    }

    /**
     * Gets the value of the priorPatientNumbers property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPatientNumberPatientNumber }
     *     
     */
    public ArrayOfPatientNumberPatientNumber getPriorPatientNumbers() {
        return priorPatientNumbers;
    }

    /**
     * Sets the value of the priorPatientNumbers property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPatientNumberPatientNumber }
     *     
     */
    public void setPriorPatientNumbers(ArrayOfPatientNumberPatientNumber value) {
        this.priorPatientNumbers = value;
    }

    /**
     * Gets the value of the addresses property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAddressAddress }
     *     
     */
    public ArrayOfAddressAddress getAddresses() {
        return addresses;
    }

    /**
     * Sets the value of the addresses property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAddressAddress }
     *     
     */
    public void setAddresses(ArrayOfAddressAddress value) {
        this.addresses = value;
    }

    /**
     * Gets the value of the contactInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ContactInfo }
     *     
     */
    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    /**
     * Sets the value of the contactInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactInfo }
     *     
     */
    public void setContactInfo(ContactInfo value) {
        this.contactInfo = value;
    }

    /**
     * Gets the value of the familyDoctor property.
     * 
     * @return
     *     possible object is
     *     {@link FamilyDoctor }
     *     
     */
    public FamilyDoctor getFamilyDoctor() {
        return familyDoctor;
    }

    /**
     * Sets the value of the familyDoctor property.
     * 
     * @param value
     *     allowed object is
     *     {@link FamilyDoctor }
     *     
     */
    public void setFamilyDoctor(FamilyDoctor value) {
        this.familyDoctor = value;
    }

    /**
     * Gets the value of the inactiveMRNs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInactiveMRNs() {
        return inactiveMRNs;
    }

    /**
     * Sets the value of the inactiveMRNs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInactiveMRNs(String value) {
        this.inactiveMRNs = value;
    }

    /**
     * Gets the value of the occupation property.
     * 
     * @return
     *     possible object is
     *     {@link Occupation }
     *     
     */
    public Occupation getOccupation() {
        return occupation;
    }

    /**
     * Sets the value of the occupation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Occupation }
     *     
     */
    public void setOccupation(Occupation value) {
        this.occupation = value;
    }

}
