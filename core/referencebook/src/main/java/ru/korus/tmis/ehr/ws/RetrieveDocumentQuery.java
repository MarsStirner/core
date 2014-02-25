
package ru.korus.tmis.ehr.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RetrieveDocumentQuery complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RetrieveDocumentQuery">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="facilityCode" type="{}String"/>
 *         &lt;element name="initiatedBy" type="{}Employee" minOccurs="0"/>
 *         &lt;element name="documentId" type="{}String"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RetrieveDocumentQuery", propOrder = {
    "facilityCode",
    "initiatedBy",
    "documentId"
})
public class RetrieveDocumentQuery {

    @XmlElement(required = true)
    protected String facilityCode;
    protected Employee initiatedBy;
    @XmlElement(required = true)
    protected String documentId;

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
     * Gets the value of the documentId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentId() {
        return documentId;
    }

    /**
     * Sets the value of the documentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentId(String value) {
        this.documentId = value;
    }

}
