
package ru.korus.tmis.pix.sda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Appointment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Appointment">
 *   &lt;complexContent>
 *     &lt;extension base="{}SuperClass">
 *       &lt;sequence>
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NoShow" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Type" type="{}AppointmentType" minOccurs="0"/>
 *         &lt;element name="PlacerApptId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FillerApptId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PlacerOrderId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FillerOrderId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderItem" type="{}Order" minOccurs="0"/>
 *         &lt;element name="CareProvider" type="{}CareProvider" minOccurs="0"/>
 *         &lt;element name="Location" type="{}HealthCareFacility" minOccurs="0"/>
 *         &lt;element name="Notes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Appointment", propOrder = {
    "status",
    "noShow",
    "type",
    "placerApptId",
    "fillerApptId",
    "placerOrderId",
    "fillerOrderId",
    "orderItem",
    "careProvider",
    "location",
    "notes"
})
public class Appointment
    extends SuperClass
{

    @XmlElement(name = "Status")
    protected String status;
    @XmlElement(name = "NoShow")
    protected Boolean noShow;
    @XmlElement(name = "Type")
    protected AppointmentType type;
    @XmlElement(name = "PlacerApptId")
    protected String placerApptId;
    @XmlElement(name = "FillerApptId")
    protected String fillerApptId;
    @XmlElement(name = "PlacerOrderId")
    protected String placerOrderId;
    @XmlElement(name = "FillerOrderId")
    protected String fillerOrderId;
    @XmlElement(name = "OrderItem")
    protected Order orderItem;
    @XmlElement(name = "CareProvider")
    protected CareProvider careProvider;
    @XmlElement(name = "Location")
    protected HealthCareFacility location;
    @XmlElement(name = "Notes")
    protected String notes;

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the noShow property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNoShow() {
        return noShow;
    }

    /**
     * Sets the value of the noShow property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNoShow(Boolean value) {
        this.noShow = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link AppointmentType }
     *     
     */
    public AppointmentType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link AppointmentType }
     *     
     */
    public void setType(AppointmentType value) {
        this.type = value;
    }

    /**
     * Gets the value of the placerApptId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlacerApptId() {
        return placerApptId;
    }

    /**
     * Sets the value of the placerApptId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlacerApptId(String value) {
        this.placerApptId = value;
    }

    /**
     * Gets the value of the fillerApptId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFillerApptId() {
        return fillerApptId;
    }

    /**
     * Sets the value of the fillerApptId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFillerApptId(String value) {
        this.fillerApptId = value;
    }

    /**
     * Gets the value of the placerOrderId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlacerOrderId() {
        return placerOrderId;
    }

    /**
     * Sets the value of the placerOrderId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlacerOrderId(String value) {
        this.placerOrderId = value;
    }

    /**
     * Gets the value of the fillerOrderId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFillerOrderId() {
        return fillerOrderId;
    }

    /**
     * Sets the value of the fillerOrderId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFillerOrderId(String value) {
        this.fillerOrderId = value;
    }

    /**
     * Gets the value of the orderItem property.
     * 
     * @return
     *     possible object is
     *     {@link Order }
     *     
     */
    public Order getOrderItem() {
        return orderItem;
    }

    /**
     * Sets the value of the orderItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link Order }
     *     
     */
    public void setOrderItem(Order value) {
        this.orderItem = value;
    }

    /**
     * Gets the value of the careProvider property.
     * 
     * @return
     *     possible object is
     *     {@link CareProvider }
     *     
     */
    public CareProvider getCareProvider() {
        return careProvider;
    }

    /**
     * Sets the value of the careProvider property.
     * 
     * @param value
     *     allowed object is
     *     {@link CareProvider }
     *     
     */
    public void setCareProvider(CareProvider value) {
        this.careProvider = value;
    }

    /**
     * Gets the value of the location property.
     * 
     * @return
     *     possible object is
     *     {@link HealthCareFacility }
     *     
     */
    public HealthCareFacility getLocation() {
        return location;
    }

    /**
     * Sets the value of the location property.
     * 
     * @param value
     *     allowed object is
     *     {@link HealthCareFacility }
     *     
     */
    public void setLocation(HealthCareFacility value) {
        this.location = value;
    }

    /**
     * Gets the value of the notes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the value of the notes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotes(String value) {
        this.notes = value;
    }

}
