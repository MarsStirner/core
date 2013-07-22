
package ru.cgm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for patientRoleInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="patientRoleInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="addr" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="telecom" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="patient" type="{http://cgm.ru}patientInfo"/>
 *         &lt;element name="providerOrganization" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "patientRoleInfo", propOrder = {
    "id",
    "addr",
    "telecom",
    "patient",
    "providerOrganization"
})
public class PatientRoleInfo {

    protected int id;
    @XmlElement(required = true)
    protected String addr;
    @XmlElement(required = true)
    protected String telecom;
    @XmlElement(required = true)
    protected PatientInfo patient;
    @XmlElement(required = true)
    protected String providerOrganization;

    /**
     * Gets the value of the id property.
     * 
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     */
    public void setId(int value) {
        this.id = value;
    }

    /**
     * Gets the value of the addr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddr() {
        return addr;
    }

    /**
     * Sets the value of the addr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddr(String value) {
        this.addr = value;
    }

    /**
     * Gets the value of the telecom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelecom() {
        return telecom;
    }

    /**
     * Sets the value of the telecom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelecom(String value) {
        this.telecom = value;
    }

    /**
     * Gets the value of the patient property.
     * 
     * @return
     *     possible object is
     *     {@link PatientInfo }
     *     
     */
    public PatientInfo getPatient() {
        return patient;
    }

    /**
     * Sets the value of the patient property.
     * 
     * @param value
     *     allowed object is
     *     {@link PatientInfo }
     *     
     */
    public void setPatient(PatientInfo value) {
        this.patient = value;
    }

    /**
     * Gets the value of the providerOrganization property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProviderOrganization() {
        return providerOrganization;
    }

    /**
     * Sets the value of the providerOrganization property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProviderOrganization(String value) {
        this.providerOrganization = value;
    }

}
