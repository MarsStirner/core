
package ru.korus.tmis.pix.sda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Observation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Observation">
 *   &lt;complexContent>
 *     &lt;extension base="{}SuperClass">
 *       &lt;sequence>
 *         &lt;element name="ObservationTime" type="{}TimeStamp" minOccurs="0"/>
 *         &lt;element name="ObservationCode" type="{}ObservationCode" minOccurs="0"/>
 *         &lt;element name="ObservationValue" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="999"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Comments" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="999"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Clinician" type="{}CareProvider" minOccurs="0"/>
 *         &lt;element name="GroupId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Observation", propOrder = {
    "observationTime",
    "observationCode",
    "observationValue",
    "comments",
    "clinician",
    "groupId"
})
public class Observation
    extends SuperClass
{

    @XmlElement(name = "ObservationTime")
    protected XMLGregorianCalendar observationTime;
    @XmlElement(name = "ObservationCode")
    protected ObservationCode observationCode;
    @XmlElement(name = "ObservationValue")
    protected String observationValue;
    @XmlElement(name = "Comments")
    protected String comments;
    @XmlElement(name = "Clinician")
    protected CareProvider clinician;
    @XmlElement(name = "GroupId")
    protected String groupId;

    /**
     * Gets the value of the observationTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getObservationTime() {
        return observationTime;
    }

    /**
     * Sets the value of the observationTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setObservationTime(XMLGregorianCalendar value) {
        this.observationTime = value;
    }

    /**
     * Gets the value of the observationCode property.
     * 
     * @return
     *     possible object is
     *     {@link ObservationCode }
     *     
     */
    public ObservationCode getObservationCode() {
        return observationCode;
    }

    /**
     * Sets the value of the observationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ObservationCode }
     *     
     */
    public void setObservationCode(ObservationCode value) {
        this.observationCode = value;
    }

    /**
     * Gets the value of the observationValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObservationValue() {
        return observationValue;
    }

    /**
     * Sets the value of the observationValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObservationValue(String value) {
        this.observationValue = value;
    }

    /**
     * Gets the value of the comments property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComments() {
        return comments;
    }

    /**
     * Sets the value of the comments property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComments(String value) {
        this.comments = value;
    }

    /**
     * Gets the value of the clinician property.
     * 
     * @return
     *     possible object is
     *     {@link CareProvider }
     *     
     */
    public CareProvider getClinician() {
        return clinician;
    }

    /**
     * Sets the value of the clinician property.
     * 
     * @param value
     *     allowed object is
     *     {@link CareProvider }
     *     
     */
    public void setClinician(CareProvider value) {
        this.clinician = value;
    }

    /**
     * Gets the value of the groupId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * Sets the value of the groupId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroupId(String value) {
        this.groupId = value;
    }

}
