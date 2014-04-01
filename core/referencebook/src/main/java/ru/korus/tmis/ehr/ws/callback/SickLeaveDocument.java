
package ru.korus.tmis.ehr.ws.callback;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Документ временной нетрудоспособности
 * 
 * <p>Java class for SickLeaveDocument complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SickLeaveDocument">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="extId" type="{}String" minOccurs="0"/>
 *         &lt;element name="encounterCode" type="{}String" minOccurs="0"/>
 *         &lt;element name="enteredBy" type="{}Employee" minOccurs="0"/>
 *         &lt;element name="enteredOn" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="docSer" type="{}String" minOccurs="0"/>
 *         &lt;element name="docNum" type="{}String" minOccurs="0"/>
 *         &lt;element name="reason" type="{}CodeAndName" minOccurs="0"/>
 *         &lt;element name="diagnosis" type="{}CodeAndName" minOccurs="0"/>
 *         &lt;element name="attendantGender" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="M"/>
 *               &lt;enumeration value="F"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="attendantAge" type="{}String" minOccurs="0"/>
 *         &lt;element name="fromTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
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
@XmlType(name = "SickLeaveDocument", propOrder = {
    "extId",
    "encounterCode",
    "enteredBy",
    "enteredOn",
    "docSer",
    "docNum",
    "reason",
    "diagnosis",
    "attendantGender",
    "attendantAge",
    "fromTime",
    "toTime"
})
public class SickLeaveDocument {

    protected String extId;
    protected String encounterCode;
    protected Employee enteredBy;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar enteredOn;
    protected String docSer;
    protected String docNum;
    protected CodeAndName reason;
    protected CodeAndName diagnosis;
    protected String attendantGender;
    protected String attendantAge;
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
     * Gets the value of the encounterCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEncounterCode() {
        return encounterCode;
    }

    /**
     * Sets the value of the encounterCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEncounterCode(String value) {
        this.encounterCode = value;
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
     * Gets the value of the docSer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocSer() {
        return docSer;
    }

    /**
     * Sets the value of the docSer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocSer(String value) {
        this.docSer = value;
    }

    /**
     * Gets the value of the docNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocNum() {
        return docNum;
    }

    /**
     * Sets the value of the docNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocNum(String value) {
        this.docNum = value;
    }

    /**
     * Gets the value of the reason property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getReason() {
        return reason;
    }

    /**
     * Sets the value of the reason property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setReason(CodeAndName value) {
        this.reason = value;
    }

    /**
     * Gets the value of the diagnosis property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getDiagnosis() {
        return diagnosis;
    }

    /**
     * Sets the value of the diagnosis property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setDiagnosis(CodeAndName value) {
        this.diagnosis = value;
    }

    /**
     * Gets the value of the attendantGender property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttendantGender() {
        return attendantGender;
    }

    /**
     * Sets the value of the attendantGender property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttendantGender(String value) {
        this.attendantGender = value;
    }

    /**
     * Gets the value of the attendantAge property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttendantAge() {
        return attendantAge;
    }

    /**
     * Sets the value of the attendantAge property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttendantAge(String value) {
        this.attendantAge = value;
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
