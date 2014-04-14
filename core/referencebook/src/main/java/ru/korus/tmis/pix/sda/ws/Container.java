
package ru.korus.tmis.pix.sda.ws;

import javax.xml.bind.annotation.*;


/**
 * Пакет данных, поступивший от МИС
 * 
 * <p>Java class for Container complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Container">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="patient" type="{http://schemas.intersystems.ru/hs/ehr/v1}Patient" minOccurs="0"/>
 *         &lt;element name="sourceDocument" type="{http://schemas.intersystems.ru/hs/ehr/v1}ContainerDocument" minOccurs="0"/>
 *         &lt;element name="encounters" type="{http://schemas.intersystems.ru/hs/ehr/v1}ArrayOfencounterEncounter" minOccurs="0"/>
 *         &lt;element name="diagnoses" type="{http://schemas.intersystems.ru/hs/ehr/v1}ArrayOfdiagnosisDiagnosis" minOccurs="0"/>
 *         &lt;element name="disabilities" type="{http://schemas.intersystems.ru/hs/ehr/v1}ArrayOfdisabilityDisability" minOccurs="0"/>
 *         &lt;element name="sickLeaveDocuments" type="{http://schemas.intersystems.ru/hs/ehr/v1}ArrayOfsickLeaveDocumentSickLeaveDocument" minOccurs="0"/>
 *         &lt;element name="allergies" type="{http://schemas.intersystems.ru/hs/ehr/v1}ArrayOfallergyAllergy" minOccurs="0"/>
 *         &lt;element name="documents" type="{http://schemas.intersystems.ru/hs/ehr/v1}ArrayOfdocumentDocument" minOccurs="0"/>
 *         &lt;element name="services" type="{http://schemas.intersystems.ru/hs/ehr/v1}ArrayOfserviceMedService" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="facilityCode" use="required" type="{http://schemas.intersystems.ru/hs/ehr/v1}String" />
 *       &lt;attribute name="patientMRN" use="required" type="{http://schemas.intersystems.ru/hs/ehr/v1}String" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Container", propOrder = {
    "patient",
    "sourceDocument",
    "encounters",
    "diagnoses",
    "disabilities",
    "sickLeaveDocuments",
    "allergies",
    "documents",
    "services"
})
public class Container {

    protected Patient patient;
    protected ContainerDocument sourceDocument;
    protected ArrayOfencounterEncounter encounters;
    protected ArrayOfdiagnosisDiagnosis diagnoses;
    protected ArrayOfdisabilityDisability disabilities;
    protected ArrayOfsickLeaveDocumentSickLeaveDocument sickLeaveDocuments;
    protected ArrayOfallergyAllergy allergies;
    protected ArrayOfdocumentDocument documents;
    protected ArrayOfserviceMedService services;
    @XmlAttribute(name = "facilityCode", required = true)
    protected String facilityCode;
    @XmlAttribute(name = "patientMRN", required = true)
    protected String patientMRN;

    /**
     * Gets the value of the patient property.
     * 
     * @return
     *     possible object is
     *     {@link Patient }
     *     
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * Sets the value of the patient property.
     * 
     * @param value
     *     allowed object is
     *     {@link Patient }
     *     
     */
    public void setPatient(Patient value) {
        this.patient = value;
    }

    /**
     * Gets the value of the sourceDocument property.
     * 
     * @return
     *     possible object is
     *     {@link ContainerDocument }
     *     
     */
    public ContainerDocument getSourceDocument() {
        return sourceDocument;
    }

    /**
     * Sets the value of the sourceDocument property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContainerDocument }
     *     
     */
    public void setSourceDocument(ContainerDocument value) {
        this.sourceDocument = value;
    }

    /**
     * Gets the value of the encounters property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfencounterEncounter }
     *     
     */
    public ArrayOfencounterEncounter getEncounters() {
        return encounters;
    }

    /**
     * Sets the value of the encounters property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfencounterEncounter }
     *     
     */
    public void setEncounters(ArrayOfencounterEncounter value) {
        this.encounters = value;
    }

    /**
     * Gets the value of the diagnoses property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfdiagnosisDiagnosis }
     *     
     */
    public ArrayOfdiagnosisDiagnosis getDiagnoses() {
        return diagnoses;
    }

    /**
     * Sets the value of the diagnoses property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfdiagnosisDiagnosis }
     *     
     */
    public void setDiagnoses(ArrayOfdiagnosisDiagnosis value) {
        this.diagnoses = value;
    }

    /**
     * Gets the value of the disabilities property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfdisabilityDisability }
     *     
     */
    public ArrayOfdisabilityDisability getDisabilities() {
        return disabilities;
    }

    /**
     * Sets the value of the disabilities property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfdisabilityDisability }
     *     
     */
    public void setDisabilities(ArrayOfdisabilityDisability value) {
        this.disabilities = value;
    }

    /**
     * Gets the value of the sickLeaveDocuments property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfsickLeaveDocumentSickLeaveDocument }
     *     
     */
    public ArrayOfsickLeaveDocumentSickLeaveDocument getSickLeaveDocuments() {
        return sickLeaveDocuments;
    }

    /**
     * Sets the value of the sickLeaveDocuments property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfsickLeaveDocumentSickLeaveDocument }
     *     
     */
    public void setSickLeaveDocuments(ArrayOfsickLeaveDocumentSickLeaveDocument value) {
        this.sickLeaveDocuments = value;
    }

    /**
     * Gets the value of the allergies property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfallergyAllergy }
     *     
     */
    public ArrayOfallergyAllergy getAllergies() {
        return allergies;
    }

    /**
     * Sets the value of the allergies property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfallergyAllergy }
     *     
     */
    public void setAllergies(ArrayOfallergyAllergy value) {
        this.allergies = value;
    }

    /**
     * Gets the value of the documents property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfdocumentDocument }
     *     
     */
    public ArrayOfdocumentDocument getDocuments() {
        return documents;
    }

    /**
     * Sets the value of the documents property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfdocumentDocument }
     *     
     */
    public void setDocuments(ArrayOfdocumentDocument value) {
        this.documents = value;
    }

    /**
     * Gets the value of the services property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfserviceMedService }
     *     
     */
    public ArrayOfserviceMedService getServices() {
        return services;
    }

    /**
     * Sets the value of the services property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfserviceMedService }
     *     
     */
    public void setServices(ArrayOfserviceMedService value) {
        this.services = value;
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
