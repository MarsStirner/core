
package ru.korus.tmis.ehr.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PatientQuery complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PatientQuery">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="facilityCode" type="{}String"/>
 *         &lt;element name="initiatedBy" type="{}Employee" minOccurs="0"/>
 *         &lt;element name="params" type="{}PatientParams" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PatientQuery", propOrder = {
    "facilityCode",
    "initiatedBy",
    "params"
})
public class PatientQuery {

    @XmlElement(required = true)
    protected String facilityCode;
    protected Employee initiatedBy;
    protected PatientParams params;

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
     * Gets the value of the initiatedBy property.
     * 
     * @return
     *     possible object is
     *     {@link Employee }
     *     
     */
    public Employee getInitiatedBy() {
        return initiatedBy;
    }

    /**
     * Sets the value of the initiatedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link Employee }
     *     
     */
    public void setInitiatedBy(Employee value) {
        this.initiatedBy = value;
    }

    /**
     * Gets the value of the params property.
     * 
     * @return
     *     possible object is
     *     {@link PatientParams }
     *     
     */
    public PatientParams getParams() {
        return params;
    }

    /**
     * Sets the value of the params property.
     * 
     * @param value
     *     allowed object is
     *     {@link PatientParams }
     *     
     */
    public void setParams(PatientParams value) {
        this.params = value;
    }

}
