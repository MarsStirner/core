
package ru.korus.tmis.ws.transfusion.efive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ProcedureInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProcedureInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="operationType" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="divisionId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ibNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="planDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="registrationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="attendingPhysicianId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="attendingPhysicianLastName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attendingPhysicianFirstName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attendingPhysicianMiddleName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProcedureInfo", propOrder = {
    "id",
    "operationType",
    "divisionId",
    "ibNumber",
    "planDate",
    "registrationDate",
    "attendingPhysicianId",
    "attendingPhysicianLastName",
    "attendingPhysicianFirstName",
    "attendingPhysicianMiddleName"
})
public class ProcedureInfo {

    protected Integer id;
    protected Integer operationType;
    protected Integer divisionId;
    protected String ibNumber;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar planDate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar registrationDate;
    protected Integer attendingPhysicianId;
    protected String attendingPhysicianLastName;
    protected String attendingPhysicianFirstName;
    protected String attendingPhysicianMiddleName;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setId(Integer value) {
        this.id = value;
    }

    /**
     * Gets the value of the operationType property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOperationType() {
        return operationType;
    }

    /**
     * Sets the value of the operationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOperationType(Integer value) {
        this.operationType = value;
    }

    /**
     * Gets the value of the divisionId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDivisionId() {
        return divisionId;
    }

    /**
     * Sets the value of the divisionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDivisionId(Integer value) {
        this.divisionId = value;
    }

    /**
     * Gets the value of the ibNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIbNumber() {
        return ibNumber;
    }

    /**
     * Sets the value of the ibNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIbNumber(String value) {
        this.ibNumber = value;
    }

    /**
     * Gets the value of the planDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPlanDate() {
        return planDate;
    }

    /**
     * Sets the value of the planDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPlanDate(XMLGregorianCalendar value) {
        this.planDate = value;
    }

    /**
     * Gets the value of the registrationDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRegistrationDate() {
        return registrationDate;
    }

    /**
     * Sets the value of the registrationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRegistrationDate(XMLGregorianCalendar value) {
        this.registrationDate = value;
    }

    /**
     * Gets the value of the attendingPhysicianId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAttendingPhysicianId() {
        return attendingPhysicianId;
    }

    /**
     * Sets the value of the attendingPhysicianId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAttendingPhysicianId(Integer value) {
        this.attendingPhysicianId = value;
    }

    /**
     * Gets the value of the attendingPhysicianLastName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttendingPhysicianLastName() {
        return attendingPhysicianLastName;
    }

    /**
     * Sets the value of the attendingPhysicianLastName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttendingPhysicianLastName(String value) {
        this.attendingPhysicianLastName = value;
    }

    /**
     * Gets the value of the attendingPhysicianFirstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttendingPhysicianFirstName() {
        return attendingPhysicianFirstName;
    }

    /**
     * Sets the value of the attendingPhysicianFirstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttendingPhysicianFirstName(String value) {
        this.attendingPhysicianFirstName = value;
    }

    /**
     * Gets the value of the attendingPhysicianMiddleName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttendingPhysicianMiddleName() {
        return attendingPhysicianMiddleName;
    }

    /**
     * Sets the value of the attendingPhysicianMiddleName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttendingPhysicianMiddleName(String value) {
        this.attendingPhysicianMiddleName = value;
    }

}
