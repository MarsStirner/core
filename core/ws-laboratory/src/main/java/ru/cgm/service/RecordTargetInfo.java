
package ru.cgm.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for recordTargetInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="recordTargetInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="patientRole" type="{http://cgm.ru}patientRoleInfo"/>
 *       &lt;/sequence>
 *       &lt;attribute name="typeCode" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "recordTargetInfo", propOrder = {
    "patientRole"
})
public class RecordTargetInfo {

    @XmlElement(required = true)
    protected PatientRoleInfo patientRole;
    @XmlAttribute(name = "typeCode")
    protected String typeCode;

    /**
     * Gets the value of the patientRole property.
     * 
     * @return
     *     possible object is
     *     {@link PatientRoleInfo }
     *     
     */
    public PatientRoleInfo getPatientRole() {
        return patientRole;
    }

    /**
     * Sets the value of the patientRole property.
     * 
     * @param value
     *     allowed object is
     *     {@link PatientRoleInfo }
     *     
     */
    public void setPatientRole(PatientRoleInfo value) {
        this.patientRole = value;
    }

    /**
     * Gets the value of the typeCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeCode() {
        return typeCode;
    }

    /**
     * Sets the value of the typeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeCode(String value) {
        this.typeCode = value;
    }

}
