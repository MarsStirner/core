
package ru.korus.tmis.laboratory.altey.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PatientInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PatientInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="patientMisId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="patientFamily" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="patientName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="patientPatronum" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="patientBirthDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="patientSex" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PatientInfo", propOrder = {
    "patientMisId",
    "patientFamily",
    "patientName",
    "patientPatronum",
    "patientBirthDate",
    "patientSex"
})
public class PatientInfo {

    protected int patientMisId;
    @XmlElement(required = true)
    protected String patientFamily;
    @XmlElement(required = true)
    protected String patientName;
    @XmlElement(required = true)
    protected String patientPatronum;
    @XmlElement(required = true)
    protected String patientBirthDate;
    protected int patientSex;

    /**
     * Gets the value of the patientMisId property.
     * 
     */
    public int getPatientMisId() {
        return patientMisId;
    }

    /**
     * Sets the value of the patientMisId property.
     * 
     */
    public void setPatientMisId(int value) {
        this.patientMisId = value;
    }

    /**
     * Gets the value of the patientFamily property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPatientFamily() {
        return patientFamily;
    }

    /**
     * Sets the value of the patientFamily property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPatientFamily(String value) {
        this.patientFamily = value;
    }

    /**
     * Gets the value of the patientName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPatientName() {
        return patientName;
    }

    /**
     * Sets the value of the patientName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPatientName(String value) {
        this.patientName = value;
    }

    /**
     * Gets the value of the patientPatronum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPatientPatronum() {
        return patientPatronum;
    }

    /**
     * Sets the value of the patientPatronum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPatientPatronum(String value) {
        this.patientPatronum = value;
    }

    /**
     * Gets the value of the patientBirthDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPatientBirthDate() {
        return patientBirthDate;
    }

    /**
     * Sets the value of the patientBirthDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPatientBirthDate(String value) {
        this.patientBirthDate = value;
    }

    /**
     * Gets the value of the patientSex property.
     * 
     */
    public int getPatientSex() {
        return patientSex;
    }

    /**
     * Sets the value of the patientSex property.
     * 
     */
    public void setPatientSex(int value) {
        this.patientSex = value;
    }

}
