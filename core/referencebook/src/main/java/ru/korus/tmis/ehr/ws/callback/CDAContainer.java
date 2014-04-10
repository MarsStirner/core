
package ru.korus.tmis.ehr.ws.callback;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CDAContainer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CDAContainer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="patient" type="{}CDAPatient" minOccurs="0"/>
 *         &lt;element name="encounters" type="{}ArrayOfencounterCDAEncounter" minOccurs="0"/>
 *         &lt;element name="documents" type="{}ArrayOfdocumentCDADocument" minOccurs="0"/>
 *         &lt;element name="facility" type="{}CDAFacility" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="facilityCode" use="required" type="{}String" />
 *       &lt;attribute name="patientMRN" use="required" type="{}String" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CDAContainer", propOrder = {
    "patient",
    "encounters",
    "documents",
    "facility"
})
public class CDAContainer {

    protected CDAPatient patient;
    protected ArrayOfencounterCDAEncounter encounters;
    protected ArrayOfdocumentCDADocument documents;
    protected CDAFacility facility;
    @XmlAttribute(name = "facilityCode", required = true)
    protected String facilityCode;
    @XmlAttribute(name = "patientMRN", required = true)
    protected String patientMRN;

    /**
     * Gets the value of the patient property.
     * 
     * @return
     *     possible object is
     *     {@link CDAPatient }
     *     
     */
    public CDAPatient getPatient() {
        return patient;
    }

    /**
     * Sets the value of the patient property.
     * 
     * @param value
     *     allowed object is
     *     {@link CDAPatient }
     *     
     */
    public void setPatient(CDAPatient value) {
        this.patient = value;
    }

    /**
     * Gets the value of the encounters property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfencounterCDAEncounter }
     *     
     */
    public ArrayOfencounterCDAEncounter getEncounters() {
        return encounters;
    }

    /**
     * Sets the value of the encounters property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfencounterCDAEncounter }
     *     
     */
    public void setEncounters(ArrayOfencounterCDAEncounter value) {
        this.encounters = value;
    }

    /**
     * Gets the value of the documents property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfdocumentCDADocument }
     *     
     */
    public ArrayOfdocumentCDADocument getDocuments() {
        return documents;
    }

    /**
     * Sets the value of the documents property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfdocumentCDADocument }
     *     
     */
    public void setDocuments(ArrayOfdocumentCDADocument value) {
        this.documents = value;
    }

    /**
     * Gets the value of the facility property.
     * 
     * @return
     *     possible object is
     *     {@link CDAFacility }
     *     
     */
    public CDAFacility getFacility() {
        return facility;
    }

    /**
     * Sets the value of the facility property.
     * 
     * @param value
     *     allowed object is
     *     {@link CDAFacility }
     *     
     */
    public void setFacility(CDAFacility value) {
        this.facility = value;
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
     * Gets the value of the patientMRN property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPatientMRN() {
        return patientMRN;
    }

    /**
     * Sets the value of the patientMRN property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPatientMRN(String value) {
        this.patientMRN = value;
    }

}
