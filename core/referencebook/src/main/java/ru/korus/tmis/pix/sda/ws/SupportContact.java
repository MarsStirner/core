
package ru.korus.tmis.pix.sda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for SupportContact complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SupportContact">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ActionScope" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EnteredBy" type="{}User" minOccurs="0"/>
 *         &lt;element name="EnteredAt" type="{}Organization" minOccurs="0"/>
 *         &lt;element name="EnteredOn" type="{}TimeStamp" minOccurs="0"/>
 *         &lt;element name="UpdatedOn" type="{}TimeStamp" minOccurs="0"/>
 *         &lt;element name="FromTime" type="{}TimeStamp" minOccurs="0"/>
 *         &lt;element name="ToTime" type="{}TimeStamp" minOccurs="0"/>
 *         &lt;element name="ExternalId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EncounterNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CustomPairs" type="{}ArrayOfNVPairNVPair" minOccurs="0"/>
 *         &lt;element name="Name" type="{}Name" minOccurs="0"/>
 *         &lt;element name="Gender" type="{}Gender" minOccurs="0"/>
 *         &lt;element name="BirthTime" type="{}TimeStamp" minOccurs="0"/>
 *         &lt;element name="PrimaryLanguage" type="{}Language" minOccurs="0"/>
 *         &lt;element name="Address" type="{}Address" minOccurs="0"/>
 *         &lt;element name="ContactInfo" type="{}ContactInfo" minOccurs="0"/>
 *         &lt;element name="Relationship" type="{}Relationship" minOccurs="0"/>
 *         &lt;element name="ContactType" type="{}ContactType" minOccurs="0"/>
 *         &lt;element name="PrimaryContact" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Identifiers" type="{}ArrayOfPatientNumberPatientNumber" minOccurs="0"/>
 *         &lt;element name="ActionCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SupportContact", propOrder = {
    "actionScope",
    "enteredBy",
    "enteredAt",
    "enteredOn",
    "updatedOn",
    "fromTime",
    "toTime",
    "externalId",
    "encounterNumber",
    "customPairs",
    "name",
    "gender",
    "birthTime",
    "primaryLanguage",
    "address",
    "contactInfo",
    "relationship",
    "contactType",
    "primaryContact",
    "identifiers",
    "actionCode"
})
public class SupportContact {

    @XmlElement(name = "ActionScope")
    protected String actionScope;
    @XmlElement(name = "EnteredBy")
    protected User enteredBy;
    @XmlElement(name = "EnteredAt")
    protected Organization enteredAt;
    @XmlElement(name = "EnteredOn")
    protected XMLGregorianCalendar enteredOn;
    @XmlElement(name = "UpdatedOn")
    protected XMLGregorianCalendar updatedOn;
    @XmlElement(name = "FromTime")
    protected XMLGregorianCalendar fromTime;
    @XmlElement(name = "ToTime")
    protected XMLGregorianCalendar toTime;
    @XmlElement(name = "ExternalId")
    protected String externalId;
    @XmlElement(name = "EncounterNumber")
    protected String encounterNumber;
    @XmlElement(name = "CustomPairs")
    protected ArrayOfNVPairNVPair customPairs;
    @XmlElement(name = "Name")
    protected Name name;
    @XmlElement(name = "Gender")
    protected Gender gender;
    @XmlElement(name = "BirthTime")
    protected XMLGregorianCalendar birthTime;
    @XmlElement(name = "PrimaryLanguage")
    protected Language primaryLanguage;
    @XmlElement(name = "Address")
    protected Address address;
    @XmlElement(name = "ContactInfo")
    protected ContactInfo contactInfo;
    @XmlElement(name = "Relationship")
    protected Relationship relationship;
    @XmlElement(name = "ContactType")
    protected ContactType contactType;
    @XmlElement(name = "PrimaryContact")
    protected Boolean primaryContact;
    @XmlElement(name = "Identifiers")
    protected ArrayOfPatientNumberPatientNumber identifiers;
    @XmlElement(name = "ActionCode")
    protected String actionCode;

    /**
     * Gets the value of the actionScope property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActionScope() {
        return actionScope;
    }

    /**
     * Sets the value of the actionScope property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActionScope(String value) {
        this.actionScope = value;
    }

    /**
     * Gets the value of the enteredBy property.
     * 
     * @return
     *     possible object is
     *     {@link User }
     *     
     */
    public User getEnteredBy() {
        return enteredBy;
    }

    /**
     * Sets the value of the enteredBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link User }
     *     
     */
    public void setEnteredBy(User value) {
        this.enteredBy = value;
    }

    /**
     * Gets the value of the enteredAt property.
     * 
     * @return
     *     possible object is
     *     {@link Organization }
     *     
     */
    public Organization getEnteredAt() {
        return enteredAt;
    }

    /**
     * Sets the value of the enteredAt property.
     * 
     * @param value
     *     allowed object is
     *     {@link Organization }
     *     
     */
    public void setEnteredAt(Organization value) {
        this.enteredAt = value;
    }

    /**
     * Gets the value of the enteredOn property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEnteredOn() {
        return enteredOn;
    }

    /**
     * Sets the value of the enteredOn property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEnteredOn(XMLGregorianCalendar value) {
        this.enteredOn = value;
    }

    /**
     * Gets the value of the updatedOn property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getUpdatedOn() {
        return updatedOn;
    }

    /**
     * Sets the value of the updatedOn property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setUpdatedOn(XMLGregorianCalendar value) {
        this.updatedOn = value;
    }

    /**
     * Gets the value of the fromTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFromTime() {
        return fromTime;
    }

    /**
     * Sets the value of the fromTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFromTime(XMLGregorianCalendar value) {
        this.fromTime = value;
    }

    /**
     * Gets the value of the toTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getToTime() {
        return toTime;
    }

    /**
     * Sets the value of the toTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setToTime(XMLGregorianCalendar value) {
        this.toTime = value;
    }

    /**
     * Gets the value of the externalId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExternalId() {
        return externalId;
    }

    /**
     * Sets the value of the externalId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExternalId(String value) {
        this.externalId = value;
    }

    /**
     * Gets the value of the encounterNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEncounterNumber() {
        return encounterNumber;
    }

    /**
     * Sets the value of the encounterNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEncounterNumber(String value) {
        this.encounterNumber = value;
    }

    /**
     * Gets the value of the customPairs property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfNVPairNVPair }
     *     
     */
    public ArrayOfNVPairNVPair getCustomPairs() {
        return customPairs;
    }

    /**
     * Sets the value of the customPairs property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfNVPairNVPair }
     *     
     */
    public void setCustomPairs(ArrayOfNVPairNVPair value) {
        this.customPairs = value;
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
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link Address }
     *     
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link Address }
     *     
     */
    public void setAddress(Address value) {
        this.address = value;
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
     * Gets the value of the relationship property.
     * 
     * @return
     *     possible object is
     *     {@link Relationship }
     *     
     */
    public Relationship getRelationship() {
        return relationship;
    }

    /**
     * Sets the value of the relationship property.
     * 
     * @param value
     *     allowed object is
     *     {@link Relationship }
     *     
     */
    public void setRelationship(Relationship value) {
        this.relationship = value;
    }

    /**
     * Gets the value of the contactType property.
     * 
     * @return
     *     possible object is
     *     {@link ContactType }
     *     
     */
    public ContactType getContactType() {
        return contactType;
    }

    /**
     * Sets the value of the contactType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactType }
     *     
     */
    public void setContactType(ContactType value) {
        this.contactType = value;
    }

    /**
     * Gets the value of the primaryContact property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPrimaryContact() {
        return primaryContact;
    }

    /**
     * Sets the value of the primaryContact property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPrimaryContact(Boolean value) {
        this.primaryContact = value;
    }

    /**
     * Gets the value of the identifiers property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPatientNumberPatientNumber }
     *     
     */
    public ArrayOfPatientNumberPatientNumber getIdentifiers() {
        return identifiers;
    }

    /**
     * Sets the value of the identifiers property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPatientNumberPatientNumber }
     *     
     */
    public void setIdentifiers(ArrayOfPatientNumberPatientNumber value) {
        this.identifiers = value;
    }

    /**
     * Gets the value of the actionCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActionCode() {
        return actionCode;
    }

    /**
     * Sets the value of the actionCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActionCode(String value) {
        this.actionCode = value;
    }

}
