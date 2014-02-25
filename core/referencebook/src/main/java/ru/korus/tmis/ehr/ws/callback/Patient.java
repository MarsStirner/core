
package ru.korus.tmis.ehr.ws.callback;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Пациент
 * 
 * <p>Java class for Patient complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Patient">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="baseClinic" type="{}CodeAndName" minOccurs="0"/>
 *         &lt;element name="snils" type="{}Snils" minOccurs="0"/>
 *         &lt;element name="enp" type="{}Enp" minOccurs="0"/>
 *         &lt;element name="familyName" type="{}String" minOccurs="0"/>
 *         &lt;element name="givenName" type="{}String" minOccurs="0"/>
 *         &lt;element name="middleName" type="{}String" minOccurs="0"/>
 *         &lt;element name="dob" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="birthAddress" type="{}Address" minOccurs="0"/>
 *         &lt;element name="gender" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="M"/>
 *               &lt;enumeration value="F"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="legalAddress" type="{}Address" minOccurs="0"/>
 *         &lt;element name="actualAddress" type="{}Address" minOccurs="0"/>
 *         &lt;element name="postalAddress" type="{}Address" minOccurs="0"/>
 *         &lt;element name="prevAddresses" type="{}ArrayOfaddressHistoryEntry" minOccurs="0"/>
 *         &lt;element name="dwellingType" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="1"/>
 *               &lt;enumeration value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="privilege" type="{}Privilege" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="socialStatus" type="{}CodeAndName" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="isHomeless" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="isServicemanFamily" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="occupation" type="{}Occupation" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="prevOccupations" type="{}ArrayOfoccupationHistoryEntry" minOccurs="0"/>
 *         &lt;element name="citizenship" type="{}CodeAndName" minOccurs="0"/>
 *         &lt;element name="identityDocument" type="{}IdentityDocument" minOccurs="0"/>
 *         &lt;element name="prevIdentityDocuments" type="{}ArrayOfidentityDocumentIdentityDocument" minOccurs="0"/>
 *         &lt;element name="birthCertificate" type="{}IdentityDocument" minOccurs="0"/>
 *         &lt;element name="omsInsurance" type="{}Insurance" minOccurs="0"/>
 *         &lt;element name="dmsInsurance" type="{}Insurance" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="bloodGroup" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="1"/>
 *               &lt;enumeration value="2"/>
 *               &lt;enumeration value="3"/>
 *               &lt;enumeration value="4"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="rhesusFactor" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="contactInfo" type="{}ContactInfo" minOccurs="0"/>
 *         &lt;element name="isTrustee" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="trusteeList" type="{}ArrayOftrusteeTrustee" minOccurs="0"/>
 *         &lt;element name="newBornData" type="{}String" minOccurs="0"/>
 *         &lt;element name="isUnidentified" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="isForeigner" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="inn" type="{}Inn" minOccurs="0"/>
 *         &lt;element name="race" type="{}CodeAndName" minOccurs="0"/>
 *         &lt;element name="maritalStatus" type="{}CodeAndName" minOccurs="0"/>
 *         &lt;element name="children" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="levelOfEducation" type="{}CodeAndName" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Patient", propOrder = {
    "baseClinic",
    "snils",
    "enp",
    "familyName",
    "givenName",
    "middleName",
    "dob",
    "birthAddress",
    "gender",
    "legalAddress",
    "actualAddress",
    "postalAddress",
    "prevAddresses",
    "dwellingType",
    "privilege",
    "socialStatus",
    "isHomeless",
    "isServicemanFamily",
    "occupation",
    "prevOccupations",
    "citizenship",
    "identityDocument",
    "prevIdentityDocuments",
    "birthCertificate",
    "omsInsurance",
    "dmsInsurance",
    "bloodGroup",
    "rhesusFactor",
    "contactInfo",
    "isTrustee",
    "trusteeList",
    "newBornData",
    "isUnidentified",
    "isForeigner",
    "inn",
    "race",
    "maritalStatus",
    "children",
    "levelOfEducation"
})
public class Patient {

    protected CodeAndName baseClinic;
    protected String snils;
    protected String enp;
    protected String familyName;
    protected String givenName;
    protected String middleName;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dob;
    protected Address birthAddress;
    protected String gender;
    protected Address legalAddress;
    protected Address actualAddress;
    protected Address postalAddress;
    protected ArrayOfaddressHistoryEntry prevAddresses;
    protected String dwellingType;
    @XmlElement(nillable = true)
    protected List<Privilege> privilege;
    @XmlElement(nillable = true)
    protected List<CodeAndName> socialStatus;
    protected Boolean isHomeless;
    protected Boolean isServicemanFamily;
    @XmlElement(nillable = true)
    protected List<Occupation> occupation;
    protected ArrayOfoccupationHistoryEntry prevOccupations;
    protected CodeAndName citizenship;
    protected IdentityDocument identityDocument;
    protected ArrayOfidentityDocumentIdentityDocument prevIdentityDocuments;
    protected IdentityDocument birthCertificate;
    protected Insurance omsInsurance;
    @XmlElement(nillable = true)
    protected List<Insurance> dmsInsurance;
    protected String bloodGroup;
    protected Boolean rhesusFactor;
    protected ContactInfo contactInfo;
    protected Boolean isTrustee;
    protected ArrayOftrusteeTrustee trusteeList;
    protected String newBornData;
    protected Boolean isUnidentified;
    protected Boolean isForeigner;
    protected String inn;
    protected CodeAndName race;
    protected CodeAndName maritalStatus;
    protected Long children;
    protected CodeAndName levelOfEducation;

    /**
     * Gets the value of the baseClinic property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getBaseClinic() {
        return baseClinic;
    }

    /**
     * Sets the value of the baseClinic property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setBaseClinic(CodeAndName value) {
        this.baseClinic = value;
    }

    /**
     * Gets the value of the snils property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSnils() {
        return snils;
    }

    /**
     * Sets the value of the snils property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSnils(String value) {
        this.snils = value;
    }

    /**
     * Gets the value of the enp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnp() {
        return enp;
    }

    /**
     * Sets the value of the enp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnp(String value) {
        this.enp = value;
    }

    /**
     * Gets the value of the familyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFamilyName() {
        return familyName;
    }

    /**
     * Sets the value of the familyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFamilyName(String value) {
        this.familyName = value;
    }

    /**
     * Gets the value of the givenName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGivenName() {
        return givenName;
    }

    /**
     * Sets the value of the givenName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGivenName(String value) {
        this.givenName = value;
    }

    /**
     * Gets the value of the middleName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Sets the value of the middleName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMiddleName(String value) {
        this.middleName = value;
    }

    /**
     * Gets the value of the dob property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDob() {
        return dob;
    }

    /**
     * Sets the value of the dob property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDob(XMLGregorianCalendar value) {
        this.dob = value;
    }

    /**
     * Gets the value of the birthAddress property.
     * 
     * @return
     *     possible object is
     *     {@link Address }
     *     
     */
    public Address getBirthAddress() {
        return birthAddress;
    }

    /**
     * Sets the value of the birthAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link Address }
     *     
     */
    public void setBirthAddress(Address value) {
        this.birthAddress = value;
    }

    /**
     * Gets the value of the gender property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the value of the gender property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGender(String value) {
        this.gender = value;
    }

    /**
     * Gets the value of the legalAddress property.
     * 
     * @return
     *     possible object is
     *     {@link Address }
     *     
     */
    public Address getLegalAddress() {
        return legalAddress;
    }

    /**
     * Sets the value of the legalAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link Address }
     *     
     */
    public void setLegalAddress(Address value) {
        this.legalAddress = value;
    }

    /**
     * Gets the value of the actualAddress property.
     * 
     * @return
     *     possible object is
     *     {@link Address }
     *     
     */
    public Address getActualAddress() {
        return actualAddress;
    }

    /**
     * Sets the value of the actualAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link Address }
     *     
     */
    public void setActualAddress(Address value) {
        this.actualAddress = value;
    }

    /**
     * Gets the value of the postalAddress property.
     * 
     * @return
     *     possible object is
     *     {@link Address }
     *     
     */
    public Address getPostalAddress() {
        return postalAddress;
    }

    /**
     * Sets the value of the postalAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link Address }
     *     
     */
    public void setPostalAddress(Address value) {
        this.postalAddress = value;
    }

    /**
     * Gets the value of the prevAddresses property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfaddressHistoryEntry }
     *     
     */
    public ArrayOfaddressHistoryEntry getPrevAddresses() {
        return prevAddresses;
    }

    /**
     * Sets the value of the prevAddresses property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfaddressHistoryEntry }
     *     
     */
    public void setPrevAddresses(ArrayOfaddressHistoryEntry value) {
        this.prevAddresses = value;
    }

    /**
     * Gets the value of the dwellingType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDwellingType() {
        return dwellingType;
    }

    /**
     * Sets the value of the dwellingType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDwellingType(String value) {
        this.dwellingType = value;
    }

    /**
     * Gets the value of the privilege property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the privilege property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPrivilege().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Privilege }
     * 
     * 
     */
    public List<Privilege> getPrivilege() {
        if (privilege == null) {
            privilege = new ArrayList<Privilege>();
        }
        return this.privilege;
    }

    /**
     * Gets the value of the socialStatus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the socialStatus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSocialStatus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CodeAndName }
     * 
     * 
     */
    public List<CodeAndName> getSocialStatus() {
        if (socialStatus == null) {
            socialStatus = new ArrayList<CodeAndName>();
        }
        return this.socialStatus;
    }

    /**
     * Gets the value of the isHomeless property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsHomeless() {
        return isHomeless;
    }

    /**
     * Sets the value of the isHomeless property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsHomeless(Boolean value) {
        this.isHomeless = value;
    }

    /**
     * Gets the value of the isServicemanFamily property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsServicemanFamily() {
        return isServicemanFamily;
    }

    /**
     * Sets the value of the isServicemanFamily property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsServicemanFamily(Boolean value) {
        this.isServicemanFamily = value;
    }

    /**
     * Gets the value of the occupation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the occupation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOccupation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Occupation }
     * 
     * 
     */
    public List<Occupation> getOccupation() {
        if (occupation == null) {
            occupation = new ArrayList<Occupation>();
        }
        return this.occupation;
    }

    /**
     * Gets the value of the prevOccupations property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfoccupationHistoryEntry }
     *     
     */
    public ArrayOfoccupationHistoryEntry getPrevOccupations() {
        return prevOccupations;
    }

    /**
     * Sets the value of the prevOccupations property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfoccupationHistoryEntry }
     *     
     */
    public void setPrevOccupations(ArrayOfoccupationHistoryEntry value) {
        this.prevOccupations = value;
    }

    /**
     * Gets the value of the citizenship property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getCitizenship() {
        return citizenship;
    }

    /**
     * Sets the value of the citizenship property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setCitizenship(CodeAndName value) {
        this.citizenship = value;
    }

    /**
     * Gets the value of the identityDocument property.
     * 
     * @return
     *     possible object is
     *     {@link IdentityDocument }
     *     
     */
    public IdentityDocument getIdentityDocument() {
        return identityDocument;
    }

    /**
     * Sets the value of the identityDocument property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentityDocument }
     *     
     */
    public void setIdentityDocument(IdentityDocument value) {
        this.identityDocument = value;
    }

    /**
     * Gets the value of the prevIdentityDocuments property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfidentityDocumentIdentityDocument }
     *     
     */
    public ArrayOfidentityDocumentIdentityDocument getPrevIdentityDocuments() {
        return prevIdentityDocuments;
    }

    /**
     * Sets the value of the prevIdentityDocuments property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfidentityDocumentIdentityDocument }
     *     
     */
    public void setPrevIdentityDocuments(ArrayOfidentityDocumentIdentityDocument value) {
        this.prevIdentityDocuments = value;
    }

    /**
     * Gets the value of the birthCertificate property.
     * 
     * @return
     *     possible object is
     *     {@link IdentityDocument }
     *     
     */
    public IdentityDocument getBirthCertificate() {
        return birthCertificate;
    }

    /**
     * Sets the value of the birthCertificate property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentityDocument }
     *     
     */
    public void setBirthCertificate(IdentityDocument value) {
        this.birthCertificate = value;
    }

    /**
     * Gets the value of the omsInsurance property.
     * 
     * @return
     *     possible object is
     *     {@link Insurance }
     *     
     */
    public Insurance getOmsInsurance() {
        return omsInsurance;
    }

    /**
     * Sets the value of the omsInsurance property.
     * 
     * @param value
     *     allowed object is
     *     {@link Insurance }
     *     
     */
    public void setOmsInsurance(Insurance value) {
        this.omsInsurance = value;
    }

    /**
     * Gets the value of the dmsInsurance property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dmsInsurance property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDmsInsurance().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Insurance }
     * 
     * 
     */
    public List<Insurance> getDmsInsurance() {
        if (dmsInsurance == null) {
            dmsInsurance = new ArrayList<Insurance>();
        }
        return this.dmsInsurance;
    }

    /**
     * Gets the value of the bloodGroup property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBloodGroup() {
        return bloodGroup;
    }

    /**
     * Sets the value of the bloodGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBloodGroup(String value) {
        this.bloodGroup = value;
    }

    /**
     * Gets the value of the rhesusFactor property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRhesusFactor() {
        return rhesusFactor;
    }

    /**
     * Sets the value of the rhesusFactor property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRhesusFactor(Boolean value) {
        this.rhesusFactor = value;
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
     * Gets the value of the isTrustee property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsTrustee() {
        return isTrustee;
    }

    /**
     * Sets the value of the isTrustee property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsTrustee(Boolean value) {
        this.isTrustee = value;
    }

    /**
     * Gets the value of the trusteeList property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOftrusteeTrustee }
     *     
     */
    public ArrayOftrusteeTrustee getTrusteeList() {
        return trusteeList;
    }

    /**
     * Sets the value of the trusteeList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOftrusteeTrustee }
     *     
     */
    public void setTrusteeList(ArrayOftrusteeTrustee value) {
        this.trusteeList = value;
    }

    /**
     * Gets the value of the newBornData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNewBornData() {
        return newBornData;
    }

    /**
     * Sets the value of the newBornData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNewBornData(String value) {
        this.newBornData = value;
    }

    /**
     * Gets the value of the isUnidentified property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsUnidentified() {
        return isUnidentified;
    }

    /**
     * Sets the value of the isUnidentified property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsUnidentified(Boolean value) {
        this.isUnidentified = value;
    }

    /**
     * Gets the value of the isForeigner property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsForeigner() {
        return isForeigner;
    }

    /**
     * Sets the value of the isForeigner property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsForeigner(Boolean value) {
        this.isForeigner = value;
    }

    /**
     * Gets the value of the inn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInn() {
        return inn;
    }

    /**
     * Sets the value of the inn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInn(String value) {
        this.inn = value;
    }

    /**
     * Gets the value of the race property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getRace() {
        return race;
    }

    /**
     * Sets the value of the race property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setRace(CodeAndName value) {
        this.race = value;
    }

    /**
     * Gets the value of the maritalStatus property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * Sets the value of the maritalStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setMaritalStatus(CodeAndName value) {
        this.maritalStatus = value;
    }

    /**
     * Gets the value of the children property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getChildren() {
        return children;
    }

    /**
     * Sets the value of the children property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setChildren(Long value) {
        this.children = value;
    }

    /**
     * Gets the value of the levelOfEducation property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getLevelOfEducation() {
        return levelOfEducation;
    }

    /**
     * Sets the value of the levelOfEducation property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setLevelOfEducation(CodeAndName value) {
        this.levelOfEducation = value;
    }

}
