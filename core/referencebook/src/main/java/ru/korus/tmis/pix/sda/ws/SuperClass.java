
package ru.korus.tmis.pix.sda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for SuperClass complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SuperClass">
 *   &lt;complexContent>
 *     &lt;extension base="{}DataType">
 *       &lt;sequence>
 *         &lt;element name="ActionCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ActionScope" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EnteredBy" type="{}User" minOccurs="0"/>
 *         &lt;element name="EnteredAt" type="{}Organization" minOccurs="0"/>
 *         &lt;element name="EnteredOn" type="{}TimeStamp" minOccurs="0"/>
 *         &lt;element name="UpdatedOn" type="{}TimeStamp" minOccurs="0"/>
 *         &lt;element name="FromTime" type="{}TimeStamp" minOccurs="0"/>
 *         &lt;element name="ToTime" type="{}TimeStamp" minOccurs="0"/>
 *         &lt;element name="ExternalId" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="220"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="EncounterNumber" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="220"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="CustomPairs" type="{}ArrayOfNVPairNVPair" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SuperClass", propOrder = {
    "actionCode",
    "actionScope",
    "enteredBy",
    "enteredAt",
    "enteredOn",
    "updatedOn",
    "fromTime",
    "toTime",
    "externalId",
    "encounterNumber",
    "customPairs"
})
@XmlSeeAlso({
    Appointment.class,
    Diagnosis.class,
    LabResultItem.class,
    Result.class,
    LabOrder.class,
    RadOrder.class,
    AdvanceDirective.class,
    IllnessHistory.class,
    Observation.class,
    CustomObject.class,
    Alert.class,
    Allergy.class,
    SocialHistory.class,
    Problem.class,
    Guarantor.class,
    Procedure.class,
    Medication.class,
    ProgramMembership.class,
    Administration.class,
    Encounter.class,
    Document.class,
    FamilyHistory.class,
    PhysicalExam.class,
    Referral.class,
    OtherOrder.class,
    Vaccination.class,
    PatientNumber.class,
    ClinicalRelationship.class,
    Patient.class
})
public class SuperClass
    extends DataType
{

    @XmlElement(name = "ActionCode")
    protected String actionCode;
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

}
