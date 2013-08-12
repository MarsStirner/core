
package ru.korus.tmis.pix.sda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Diagnosis complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Diagnosis">
 *   &lt;complexContent>
 *     &lt;extension base="{}SuperClass">
 *       &lt;sequence>
 *         &lt;element name="DiagnosingClinician" type="{}CareProvider" minOccurs="0"/>
 *         &lt;element name="Diagnosis" type="{}DiagnosisCode" minOccurs="0"/>
 *         &lt;element name="DiagnosisType" type="{}DiagnosisType" minOccurs="0"/>
 *         &lt;element name="LinkedDiagnosisCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LinkedExternalId" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="220"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="IdentificationTime" type="{}TimeStamp" minOccurs="0"/>
 *         &lt;element name="Status" type="{}DiagnosisStatus" minOccurs="0"/>
 *         &lt;element name="OnsetTime" type="{}TimeStamp" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Diagnosis", propOrder = {
    "diagnosingClinician",
    "diagnosis",
    "diagnosisType",
    "linkedDiagnosisCode",
    "linkedExternalId",
    "identificationTime",
    "status",
    "onsetTime"
})
public class Diagnosis
    extends SuperClass
{

    @XmlElement(name = "DiagnosingClinician")
    protected CareProvider diagnosingClinician;
    @XmlElement(name = "Diagnosis")
    protected DiagnosisCode diagnosis;
    @XmlElement(name = "DiagnosisType")
    protected DiagnosisType diagnosisType;
    @XmlElement(name = "LinkedDiagnosisCode")
    protected String linkedDiagnosisCode;
    @XmlElement(name = "LinkedExternalId")
    protected String linkedExternalId;
    @XmlElement(name = "IdentificationTime")
    protected XMLGregorianCalendar identificationTime;
    @XmlElement(name = "Status")
    protected DiagnosisStatus status;
    @XmlElement(name = "OnsetTime")
    protected XMLGregorianCalendar onsetTime;

    /**
     * Gets the value of the diagnosingClinician property.
     * 
     * @return
     *     possible object is
     *     {@link CareProvider }
     *     
     */
    public CareProvider getDiagnosingClinician() {
        return diagnosingClinician;
    }

    /**
     * Sets the value of the diagnosingClinician property.
     * 
     * @param value
     *     allowed object is
     *     {@link CareProvider }
     *     
     */
    public void setDiagnosingClinician(CareProvider value) {
        this.diagnosingClinician = value;
    }

    /**
     * Gets the value of the diagnosis property.
     * 
     * @return
     *     possible object is
     *     {@link DiagnosisCode }
     *     
     */
    public DiagnosisCode getDiagnosis() {
        return diagnosis;
    }

    /**
     * Sets the value of the diagnosis property.
     * 
     * @param value
     *     allowed object is
     *     {@link DiagnosisCode }
     *     
     */
    public void setDiagnosis(DiagnosisCode value) {
        this.diagnosis = value;
    }

    /**
     * Gets the value of the diagnosisType property.
     * 
     * @return
     *     possible object is
     *     {@link DiagnosisType }
     *     
     */
    public DiagnosisType getDiagnosisType() {
        return diagnosisType;
    }

    /**
     * Sets the value of the diagnosisType property.
     * 
     * @param value
     *     allowed object is
     *     {@link DiagnosisType }
     *     
     */
    public void setDiagnosisType(DiagnosisType value) {
        this.diagnosisType = value;
    }

    /**
     * Gets the value of the linkedDiagnosisCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLinkedDiagnosisCode() {
        return linkedDiagnosisCode;
    }

    /**
     * Sets the value of the linkedDiagnosisCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLinkedDiagnosisCode(String value) {
        this.linkedDiagnosisCode = value;
    }

    /**
     * Gets the value of the linkedExternalId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLinkedExternalId() {
        return linkedExternalId;
    }

    /**
     * Sets the value of the linkedExternalId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLinkedExternalId(String value) {
        this.linkedExternalId = value;
    }

    /**
     * Gets the value of the identificationTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getIdentificationTime() {
        return identificationTime;
    }

    /**
     * Sets the value of the identificationTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setIdentificationTime(XMLGregorianCalendar value) {
        this.identificationTime = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link DiagnosisStatus }
     *     
     */
    public DiagnosisStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link DiagnosisStatus }
     *     
     */
    public void setStatus(DiagnosisStatus value) {
        this.status = value;
    }

    /**
     * Gets the value of the onsetTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOnsetTime() {
        return onsetTime;
    }

    /**
     * Sets the value of the onsetTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOnsetTime(XMLGregorianCalendar value) {
        this.onsetTime = value;
    }

}
