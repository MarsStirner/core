
package ru.korus.tmis.pix.sda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Эпизод (амбулаторное обращение или госпитализация)
 * 
 * <p>Java class for Encounter complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Encounter">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="extId" type="{}String"/>
 *         &lt;element name="enteredBy" type="{}Employee" minOccurs="0"/>
 *         &lt;element name="enteredOn" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="encType">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="I"/>
 *               &lt;enumeration value="O"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="paymentType" type="{}CodeAndName" minOccurs="0"/>
 *         &lt;element name="isAtHome" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="encounterReason" type="{}CodeAndName" minOccurs="0"/>
 *         &lt;element name="facilityDept" type="{}CodeAndName" minOccurs="0"/>
 *         &lt;element name="referralFacility" type="{}CodeAndName" minOccurs="0"/>
 *         &lt;element name="admissionInfo" type="{}Admission" minOccurs="0"/>
 *         &lt;element name="encounterResult" type="{}CodeAndName" minOccurs="0"/>
 *         &lt;element name="encounterOutcome" type="{}CodeAndName" minOccurs="0"/>
 *         &lt;element name="finalAbilityToWork" type="{}CodeAndName" minOccurs="0"/>
 *         &lt;element name="careType" type="{}CodeAndName" minOccurs="0"/>
 *         &lt;element name="mes" type="{}ArrayOfmesCodeString" minOccurs="0"/>
 *         &lt;element name="recordNumber" type="{}String" minOccurs="0"/>
 *         &lt;element name="fromTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="toTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Encounter", propOrder = {
    "extId",
    "enteredBy",
    "enteredOn",
    "encType",
    "paymentType",
    "isAtHome",
    "encounterReason",
    "facilityDept",
    "referralFacility",
    "admissionInfo",
    "encounterResult",
    "encounterOutcome",
    "finalAbilityToWork",
    "careType",
    "mes",
    "recordNumber",
    "fromTime",
    "toTime"
})
public class Encounter {

    @XmlElement(required = true)
    protected String extId;
    protected Employee enteredBy;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar enteredOn;
    @XmlElement(required = true)
    protected String encType;
    protected CodeAndName paymentType;
    protected Boolean isAtHome;
    protected CodeAndName encounterReason;
    protected CodeAndName facilityDept;
    protected CodeAndName referralFacility;
    protected Admission admissionInfo;
    protected CodeAndName encounterResult;
    protected CodeAndName encounterOutcome;
    protected CodeAndName finalAbilityToWork;
    protected CodeAndName careType;
    protected ArrayOfmesCodeString mes;
    protected String recordNumber;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fromTime;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar toTime;

    /**
     * Gets the value of the extId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtId() {
        return extId;
    }

    /**
     * Sets the value of the extId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtId(String value) {
        this.extId = value;
    }

    /**
     * Gets the value of the enteredBy property.
     * 
     * @return
     *     possible object is
     *     {@link Employee }
     *     
     */
    public Employee getEnteredBy() {
        return enteredBy;
    }

    /**
     * Sets the value of the enteredBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link Employee }
     *     
     */
    public void setEnteredBy(Employee value) {
        this.enteredBy = value;
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
     * Gets the value of the encType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEncType() {
        return encType;
    }

    /**
     * Sets the value of the encType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEncType(String value) {
        this.encType = value;
    }

    /**
     * Gets the value of the paymentType property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getPaymentType() {
        return paymentType;
    }

    /**
     * Sets the value of the paymentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setPaymentType(CodeAndName value) {
        this.paymentType = value;
    }

    /**
     * Gets the value of the isAtHome property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsAtHome() {
        return isAtHome;
    }

    /**
     * Sets the value of the isAtHome property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsAtHome(Boolean value) {
        this.isAtHome = value;
    }

    /**
     * Gets the value of the encounterReason property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getEncounterReason() {
        return encounterReason;
    }

    /**
     * Sets the value of the encounterReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setEncounterReason(CodeAndName value) {
        this.encounterReason = value;
    }

    /**
     * Gets the value of the facilityDept property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getFacilityDept() {
        return facilityDept;
    }

    /**
     * Sets the value of the facilityDept property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setFacilityDept(CodeAndName value) {
        this.facilityDept = value;
    }

    /**
     * Gets the value of the referralFacility property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getReferralFacility() {
        return referralFacility;
    }

    /**
     * Sets the value of the referralFacility property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setReferralFacility(CodeAndName value) {
        this.referralFacility = value;
    }

    /**
     * Gets the value of the admissionInfo property.
     * 
     * @return
     *     possible object is
     *     {@link Admission }
     *     
     */
    public Admission getAdmissionInfo() {
        return admissionInfo;
    }

    /**
     * Sets the value of the admissionInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Admission }
     *     
     */
    public void setAdmissionInfo(Admission value) {
        this.admissionInfo = value;
    }

    /**
     * Gets the value of the encounterResult property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getEncounterResult() {
        return encounterResult;
    }

    /**
     * Sets the value of the encounterResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setEncounterResult(CodeAndName value) {
        this.encounterResult = value;
    }

    /**
     * Gets the value of the encounterOutcome property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getEncounterOutcome() {
        return encounterOutcome;
    }

    /**
     * Sets the value of the encounterOutcome property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setEncounterOutcome(CodeAndName value) {
        this.encounterOutcome = value;
    }

    /**
     * Gets the value of the finalAbilityToWork property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getFinalAbilityToWork() {
        return finalAbilityToWork;
    }

    /**
     * Sets the value of the finalAbilityToWork property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setFinalAbilityToWork(CodeAndName value) {
        this.finalAbilityToWork = value;
    }

    /**
     * Gets the value of the careType property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getCareType() {
        return careType;
    }

    /**
     * Sets the value of the careType property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setCareType(CodeAndName value) {
        this.careType = value;
    }

    /**
     * Gets the value of the mes property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfmesCodeString }
     *     
     */
    public ArrayOfmesCodeString getMes() {
        return mes;
    }

    /**
     * Sets the value of the mes property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfmesCodeString }
     *     
     */
    public void setMes(ArrayOfmesCodeString value) {
        this.mes = value;
    }

    /**
     * Gets the value of the recordNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecordNumber() {
        return recordNumber;
    }

    /**
     * Sets the value of the recordNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecordNumber(String value) {
        this.recordNumber = value;
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

}
