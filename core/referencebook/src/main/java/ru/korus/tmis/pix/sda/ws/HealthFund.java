
package ru.korus.tmis.pix.sda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for HealthFund complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HealthFund">
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
 *         &lt;element name="HealthFund" type="{}HealthFundCode" minOccurs="0"/>
 *         &lt;element name="HealthFundPlan" type="{}HealthFundPlan" minOccurs="0"/>
 *         &lt;element name="GroupName" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="32000"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="GroupNumber" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="32000"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="MembershipNumber" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="220"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="PlanType" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="32000"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="InsuredName" type="{}Name" minOccurs="0"/>
 *         &lt;element name="InsuredAddress" type="{}Address" minOccurs="0"/>
 *         &lt;element name="InsuredContact" type="{}ContactInfo" minOccurs="0"/>
 *         &lt;element name="InsuredRelationship" type="{}Relationship" minOccurs="0"/>
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
@XmlType(name = "HealthFund", propOrder = {
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
    "healthFund",
    "healthFundPlan",
    "groupName",
    "groupNumber",
    "membershipNumber",
    "planType",
    "insuredName",
    "insuredAddress",
    "insuredContact",
    "insuredRelationship",
    "actionCode"
})
public class HealthFund {

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
    @XmlElement(name = "HealthFund")
    protected HealthFundCode healthFund;
    @XmlElement(name = "HealthFundPlan")
    protected HealthFundPlan healthFundPlan;
    @XmlElement(name = "GroupName")
    protected String groupName;
    @XmlElement(name = "GroupNumber")
    protected String groupNumber;
    @XmlElement(name = "MembershipNumber")
    protected String membershipNumber;
    @XmlElement(name = "PlanType")
    protected String planType;
    @XmlElement(name = "InsuredName")
    protected Name insuredName;
    @XmlElement(name = "InsuredAddress")
    protected Address insuredAddress;
    @XmlElement(name = "InsuredContact")
    protected ContactInfo insuredContact;
    @XmlElement(name = "InsuredRelationship")
    protected Relationship insuredRelationship;
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
     * Gets the value of the healthFund property.
     * 
     * @return
     *     possible object is
     *     {@link HealthFundCode }
     *     
     */
    public HealthFundCode getHealthFund() {
        return healthFund;
    }

    /**
     * Sets the value of the healthFund property.
     * 
     * @param value
     *     allowed object is
     *     {@link HealthFundCode }
     *     
     */
    public void setHealthFund(HealthFundCode value) {
        this.healthFund = value;
    }

    /**
     * Gets the value of the healthFundPlan property.
     * 
     * @return
     *     possible object is
     *     {@link HealthFundPlan }
     *     
     */
    public HealthFundPlan getHealthFundPlan() {
        return healthFundPlan;
    }

    /**
     * Sets the value of the healthFundPlan property.
     * 
     * @param value
     *     allowed object is
     *     {@link HealthFundPlan }
     *     
     */
    public void setHealthFundPlan(HealthFundPlan value) {
        this.healthFundPlan = value;
    }

    /**
     * Gets the value of the groupName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Sets the value of the groupName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroupName(String value) {
        this.groupName = value;
    }

    /**
     * Gets the value of the groupNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroupNumber() {
        return groupNumber;
    }

    /**
     * Sets the value of the groupNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroupNumber(String value) {
        this.groupNumber = value;
    }

    /**
     * Gets the value of the membershipNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMembershipNumber() {
        return membershipNumber;
    }

    /**
     * Sets the value of the membershipNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMembershipNumber(String value) {
        this.membershipNumber = value;
    }

    /**
     * Gets the value of the planType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlanType() {
        return planType;
    }

    /**
     * Sets the value of the planType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlanType(String value) {
        this.planType = value;
    }

    /**
     * Gets the value of the insuredName property.
     * 
     * @return
     *     possible object is
     *     {@link Name }
     *     
     */
    public Name getInsuredName() {
        return insuredName;
    }

    /**
     * Sets the value of the insuredName property.
     * 
     * @param value
     *     allowed object is
     *     {@link Name }
     *     
     */
    public void setInsuredName(Name value) {
        this.insuredName = value;
    }

    /**
     * Gets the value of the insuredAddress property.
     * 
     * @return
     *     possible object is
     *     {@link Address }
     *     
     */
    public Address getInsuredAddress() {
        return insuredAddress;
    }

    /**
     * Sets the value of the insuredAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link Address }
     *     
     */
    public void setInsuredAddress(Address value) {
        this.insuredAddress = value;
    }

    /**
     * Gets the value of the insuredContact property.
     * 
     * @return
     *     possible object is
     *     {@link ContactInfo }
     *     
     */
    public ContactInfo getInsuredContact() {
        return insuredContact;
    }

    /**
     * Sets the value of the insuredContact property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactInfo }
     *     
     */
    public void setInsuredContact(ContactInfo value) {
        this.insuredContact = value;
    }

    /**
     * Gets the value of the insuredRelationship property.
     * 
     * @return
     *     possible object is
     *     {@link Relationship }
     *     
     */
    public Relationship getInsuredRelationship() {
        return insuredRelationship;
    }

    /**
     * Sets the value of the insuredRelationship property.
     * 
     * @param value
     *     allowed object is
     *     {@link Relationship }
     *     
     */
    public void setInsuredRelationship(Relationship value) {
        this.insuredRelationship = value;
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
