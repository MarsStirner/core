
package ru.korus.tmis.pix.sda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ClinicalRelationship complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ClinicalRelationship">
 *   &lt;complexContent>
 *     &lt;extension base="{}SuperClass">
 *       &lt;sequence>
 *         &lt;element name="Clinician" type="{}CareProvider" minOccurs="0"/>
 *         &lt;element name="FacilityCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ClinicianGroup" type="{}CareProvider" minOccurs="0"/>
 *         &lt;element name="ExpirationDate" type="{}TimeStamp" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClinicalRelationship", propOrder = {
    "clinician",
    "facilityCode",
    "clinicianGroup",
    "expirationDate"
})
public class ClinicalRelationship
    extends SuperClass
{

    @XmlElement(name = "Clinician")
    protected CareProvider clinician;
    @XmlElement(name = "FacilityCode")
    protected String facilityCode;
    @XmlElement(name = "ClinicianGroup")
    protected CareProvider clinicianGroup;
    @XmlElement(name = "ExpirationDate")
    protected XMLGregorianCalendar expirationDate;

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
     * Gets the value of the facilityCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFacilityCode() {
        return facilityCode;
    }

    /**
     * Sets the value of the facilityCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFacilityCode(String value) {
        this.facilityCode = value;
    }

    /**
     * Gets the value of the clinicianGroup property.
     * 
     * @return
     *     possible object is
     *     {@link CareProvider }
     *     
     */
    public CareProvider getClinicianGroup() {
        return clinicianGroup;
    }

    /**
     * Sets the value of the clinicianGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link CareProvider }
     *     
     */
    public void setClinicianGroup(CareProvider value) {
        this.clinicianGroup = value;
    }

    /**
     * Gets the value of the expirationDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets the value of the expirationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExpirationDate(XMLGregorianCalendar value) {
        this.expirationDate = value;
    }

}
