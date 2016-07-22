package ru.korus.tmis.ws.transfusion.efive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for OrderInformation complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="OrderInformation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="number" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="divisionId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="ibNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="diagnosis" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="componentTypeId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="volume" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="doseCount" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="indication" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="transfusionType" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="planDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="registrationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="attendingPhysicianId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="attendingPhysicianLastName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attendingPhysicianFirstName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attendingPhysicianMiddleName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="bloodGroupId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderInformation", propOrder = {"id", "number", "divisionId", "ibNumber", "diagnosis", "componentTypeId", "volume", "doseCount", "indication", "transfusionType", "planDate", "registrationDate", "attendingPhysicianId", "attendingPhysicianLastName", "attendingPhysicianFirstName", "attendingPhysicianMiddleName", "bloodGroupId", "rhesusFactorId", "lastModifyDateTime"

})
public class OrderInformation {

    protected Integer id;
    protected String number;
    protected Integer divisionId;
    protected String ibNumber;
    protected String diagnosis;
    protected Integer componentTypeId;
    protected Integer volume;
    protected Double doseCount;
    protected String indication;
    protected Integer transfusionType;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar planDate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar registrationDate;
    protected Integer attendingPhysicianId;
    protected String attendingPhysicianLastName;
    protected String attendingPhysicianFirstName;
    protected String attendingPhysicianMiddleName;
    protected Integer bloodGroupId;
    protected Integer rhesusFactorId;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastModifyDateTime;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("OrderInformation");
        sb.append("{id=").append(id);
        sb.append(", number=").append(number);
        sb.append(", divisionId=").append(divisionId);
        sb.append(", ibNumber=").append(ibNumber);
        sb.append(", diagnosis=").append(diagnosis);
        sb.append(", componentTypeId=").append(componentTypeId);
        sb.append(", volume=").append(volume);
        sb.append(", doseCount=").append(doseCount);
        sb.append(", indication=").append(indication);
        sb.append(", transfusionType=").append(transfusionType);
        sb.append(", planDate=").append(planDate);
        sb.append(", registrationDate=").append(registrationDate);
        sb.append(", attendingPhysicianId=").append(attendingPhysicianId);
        sb.append(", attendingPhysicianLastName=").append(attendingPhysicianLastName);
        sb.append(", attendingPhysicianFirstName=").append(attendingPhysicianFirstName);
        sb.append(", attendingPhysicianMiddleName=").append(attendingPhysicianMiddleName);
        sb.append(", lastModifyDateTime=").append(lastModifyDateTime);
        sb.append('}');
        return sb.toString();
    }

    /**
     * Gets the value of the id property.
     *
     * @return possible object is
     * {@link Integer }
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setId(Integer value) {
        this.id = value;
    }

    /**
     * Gets the value of the number property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getNumber() {
        return number;
    }

    /**
     * Sets the value of the number property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setNumber(String value) {
        this.number = value;
    }

    /**
     * Gets the value of the divisionId property.
     *
     * @return possible object is
     * {@link Integer }
     */
    public Integer getDivisionId() {
        return divisionId;
    }

    /**
     * Sets the value of the divisionId property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setDivisionId(Integer value) {
        this.divisionId = value;
    }

    /**
     * Gets the value of the ibNumber property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getIbNumber() {
        return ibNumber;
    }

    /**
     * Sets the value of the ibNumber property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setIbNumber(String value) {
        this.ibNumber = value;
    }

    /**
     * Gets the value of the diagnosis property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getDiagnosis() {
        return diagnosis;
    }

    /**
     * Sets the value of the diagnosis property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDiagnosis(String value) {
        this.diagnosis = value;
    }

    /**
     * Gets the value of the componentTypeId property.
     *
     * @return possible object is
     * {@link Integer }
     */
    public Integer getComponentTypeId() {
        return componentTypeId;
    }

    /**
     * Sets the value of the componentTypeId property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setComponentTypeId(Integer value) {
        this.componentTypeId = value;
    }

    /**
     * Gets the value of the volume property.
     *
     * @return possible object is
     * {@link Integer }
     */
    public Integer getVolume() {
        return volume;
    }

    /**
     * Sets the value of the volume property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setVolume(Integer value) {
        this.volume = value;
    }

    /**
     * Gets the value of the doseCount property.
     *
     * @return possible object is
     * {@link Double }
     */
    public Double getDoseCount() {
        return doseCount;
    }

    /**
     * Sets the value of the doseCount property.
     *
     * @param value allowed object is
     *              {@link Double }
     */
    public void setDoseCount(Double value) {
        this.doseCount = value;
    }

    /**
     * Gets the value of the indication property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getIndication() {
        return indication;
    }

    /**
     * Sets the value of the indication property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setIndication(String value) {
        this.indication = value;
    }

    /**
     * Gets the value of the transfusionType property.
     *
     * @return possible object is
     * {@link Integer }
     */
    public Integer getTransfusionType() {
        return transfusionType;
    }

    /**
     * Sets the value of the transfusionType property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setTransfusionType(Integer value) {
        this.transfusionType = value;
    }

    /**
     * Gets the value of the planDate property.
     *
     * @return possible object is
     * {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getPlanDate() {
        return planDate;
    }

    /**
     * Sets the value of the planDate property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setPlanDate(XMLGregorianCalendar value) {
        this.planDate = value;
    }

    /**
     * Gets the value of the registrationDate property.
     *
     * @return possible object is
     * {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getRegistrationDate() {
        return registrationDate;
    }

    /**
     * Sets the value of the registrationDate property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setRegistrationDate(XMLGregorianCalendar value) {
        this.registrationDate = value;
    }

    /**
     * Gets the value of the attendingPhysicianId property.
     *
     * @return possible object is
     * {@link Integer }
     */
    public Integer getAttendingPhysicianId() {
        return attendingPhysicianId;
    }

    /**
     * Sets the value of the attendingPhysicianId property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setAttendingPhysicianId(Integer value) {
        this.attendingPhysicianId = value;
    }

    /**
     * Gets the value of the attendingPhysicianLastName property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getAttendingPhysicianLastName() {
        return attendingPhysicianLastName;
    }

    /**
     * Sets the value of the attendingPhysicianLastName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setAttendingPhysicianLastName(String value) {
        this.attendingPhysicianLastName = value;
    }

    /**
     * Gets the value of the attendingPhysicianFirstName property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getAttendingPhysicianFirstName() {
        return attendingPhysicianFirstName;
    }

    /**
     * Sets the value of the attendingPhysicianFirstName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setAttendingPhysicianFirstName(String value) {
        this.attendingPhysicianFirstName = value;
    }

    /**
     * Gets the value of the attendingPhysicianMiddleName property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getAttendingPhysicianMiddleName() {
        return attendingPhysicianMiddleName;
    }

    /**
     * Sets the value of the attendingPhysicianMiddleName property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setAttendingPhysicianMiddleName(String value) {
        this.attendingPhysicianMiddleName = value;
    }

    /**
     * Gets the value of the bloodGroupId property.
     *
     * @return possible object is
     * {@link Integer }
     */
    public Integer getBloodGroupId() {
        return bloodGroupId;
    }

    /**
     * Sets the value of the bloodGroupId property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setBloodGroupId(Integer value) {
        this.bloodGroupId = value;
    }

    public Integer getRhesusFactorId() {
        return rhesusFactorId;
    }

    public void setRhesusFactorId(Integer rhesusFactorId) {
        this.rhesusFactorId = rhesusFactorId;
    }

    public XMLGregorianCalendar getLastModifyDateTime() {
        return lastModifyDateTime;
    }

    public void setLastModifyDateTime(final XMLGregorianCalendar lastModifyDateTime) {
        this.lastModifyDateTime = lastModifyDateTime;
    }
}
