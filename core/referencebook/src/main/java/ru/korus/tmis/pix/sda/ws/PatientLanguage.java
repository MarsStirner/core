
package ru.korus.tmis.pix.sda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for PatientLanguage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PatientLanguage">
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
 *         &lt;element name="PreferredLanguage" type="{}Language" minOccurs="0"/>
 *         &lt;element name="Use" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="220"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
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
@XmlType(name = "PatientLanguage", propOrder = {
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
    "preferredLanguage",
    "use",
    "actionCode"
})
public class PatientLanguage {

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
    @XmlElement(name = "PreferredLanguage")
    protected Language preferredLanguage;
    @XmlElement(name = "Use")
    protected String use;
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
     * Gets the value of the preferredLanguage property.
     * 
     * @return
     *     possible object is
     *     {@link Language }
     *     
     */
    public Language getPreferredLanguage() {
        return preferredLanguage;
    }

    /**
     * Sets the value of the preferredLanguage property.
     * 
     * @param value
     *     allowed object is
     *     {@link Language }
     *     
     */
    public void setPreferredLanguage(Language value) {
        this.preferredLanguage = value;
    }

    /**
     * Gets the value of the use property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUse() {
        return use;
    }

    /**
     * Sets the value of the use property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUse(String value) {
        this.use = value;
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
