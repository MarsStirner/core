
package ru.cgm.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *         &lt;element name="id" type="{http://cgm.ru}patientIDInfo"/>
 *         &lt;element name="addr" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="patient" type="{http://cgm.ru}patientInfo"/>
 *         &lt;element name="providerOrganization" type="{http://cgm.ru}providerOrganizationInfo"/>
 *       &lt;/sequence>
 *       &lt;attribute name="classCode" type="{http://www.w3.org/2001/XMLSchema}string" />
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
    "patient",
    "providerOrganization"
})
public class PatientRoleInfo {

    @XmlElement(required = true)
    protected PatientIDInfo id;
    @XmlElement(required = true)
    protected String addr;
    @XmlElement(required = true)
    protected PatientInfo patient;
    @XmlElement(required = true)
    protected ProviderOrganizationInfo providerOrganization;
    @XmlAttribute(name = "classCode")
    protected String classCode;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link PatientIDInfo }
     *     
     */
    public PatientIDInfo getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link PatientIDInfo }
     *     
     */
    public void setId(PatientIDInfo value) {
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
     *     {@link ProviderOrganizationInfo }
     *     
     */
    public ProviderOrganizationInfo getProviderOrganization() {
        return providerOrganization;
    }

    /**
     * Sets the value of the providerOrganization property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProviderOrganizationInfo }
     *     
     */
    public void setProviderOrganization(ProviderOrganizationInfo value) {
        this.providerOrganization = value;
    }

    /**
     * Gets the value of the classCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassCode() {
        return classCode;
    }

    /**
     * Sets the value of the classCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassCode(String value) {
        this.classCode = value;
    }

    @Override
    public String toString() {
        return "PatientRoleInfo{" +
                "id=" + id +
                ", addr='" + addr + '\'' +
                ", patient=" + patient +
                ", providerOrganization=" + providerOrganization +
                ", classCode='" + classCode + '\'' +
                '}';
    }
}
