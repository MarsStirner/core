
package ru.korus.tmis.ehr.ws.callback;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for CDAEncounter complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CDAEncounter">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="extId" type="{}String" minOccurs="0"/>
 *         &lt;element name="enteredBy" type="{}Employee" minOccurs="0"/>
 *         &lt;element name="enteredOn" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="enteredAt" type="{}CodeAndName" minOccurs="0"/>
 *         &lt;element name="encType" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="I"/>
 *               &lt;enumeration value="O"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="admissionInfo" type="{}Admission" minOccurs="0"/>
 *         &lt;element name="encounterResult" type="{}CodeAndName" minOccurs="0"/>
 *         &lt;element name="encounterOutcome" type="{}CodeAndName" minOccurs="0"/>
 *         &lt;element name="recordNumber" type="{}String" minOccurs="0"/>
 *         &lt;element name="fromTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="toTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="statusCode" type="{}String" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CDAEncounter", propOrder = {
    "extId",
    "enteredBy",
    "enteredOn",
    "enteredAt",
    "encType",
    "admissionInfo",
    "encounterResult",
    "encounterOutcome",
    "recordNumber",
    "fromTime",
    "toTime",
    "statusCode"
})
public class CDAEncounter {

    protected String extId;
    protected Employee enteredBy;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar enteredOn;
    protected CodeAndName enteredAt;
    protected String encType;
    protected Admission admissionInfo;
    protected CodeAndName encounterResult;
    protected CodeAndName encounterOutcome;
    protected String recordNumber;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fromTime;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar toTime;
    protected String statusCode;

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
     * Gets the value of the enteredAt property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getEnteredAt() {
        return enteredAt;
    }

    /**
     * Sets the value of the enteredAt property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setEnteredAt(CodeAndName value) {
        this.enteredAt = value;
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

    /**
     * Gets the value of the statusCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * Sets the value of the statusCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusCode(String value) {
        this.statusCode = value;
    }

}
