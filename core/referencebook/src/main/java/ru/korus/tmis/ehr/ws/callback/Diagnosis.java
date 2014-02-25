
package ru.korus.tmis.ehr.ws.callback;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Диагноз
 * 
 * <p>Java class for Diagnosis complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Diagnosis">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="extId" type="{}String" minOccurs="0"/>
 *         &lt;element name="encounterCode" type="{}String" minOccurs="0"/>
 *         &lt;element name="enteredBy" type="{}Employee" minOccurs="0"/>
 *         &lt;element name="enteredOn" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="diagnosis" type="{}CodeAndName" minOccurs="0"/>
 *         &lt;element name="diagnosisType" type="{}CodeAndName" minOccurs="0"/>
 *         &lt;element name="diagnosisKind" type="{}CodeAndName" minOccurs="0"/>
 *         &lt;element name="replacedExtId" type="{}String" minOccurs="0"/>
 *         &lt;element name="acuteOrChronic" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{}String">
 *               &lt;enumeration value="ACUTE"/>
 *               &lt;enumeration value="CHRONIC"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="onsetTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="isFirstTime" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="dispensarySupervision" type="{}DispensarySupervision" minOccurs="0"/>
 *         &lt;element name="traumaType" type="{}CodeAndName" minOccurs="0"/>
 *         &lt;element name="admissionsThisYear" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Diagnosis", propOrder = {
    "extId",
    "encounterCode",
    "enteredBy",
    "enteredOn",
    "diagnosis",
    "diagnosisType",
    "diagnosisKind",
    "replacedExtId",
    "acuteOrChronic",
    "onsetTime",
    "isFirstTime",
    "dispensarySupervision",
    "traumaType",
    "admissionsThisYear"
})
public class Diagnosis {

    protected String extId;
    protected String encounterCode;
    protected Employee enteredBy;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar enteredOn;
    protected CodeAndName diagnosis;
    protected CodeAndName diagnosisType;
    protected CodeAndName diagnosisKind;
    protected String replacedExtId;
    protected String acuteOrChronic;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar onsetTime;
    protected Boolean isFirstTime;
    protected DispensarySupervision dispensarySupervision;
    protected CodeAndName traumaType;
    protected Long admissionsThisYear;

    /**
     * Gets the value of the extId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtId() {
        return extId;
    }

    /**
     * Sets the value of the extId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtId(String value) {
        this.extId = value;
    }

    /**
     * Gets the value of the encounterCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEncounterCode() {
        return encounterCode;
    }

    /**
     * Sets the value of the encounterCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEncounterCode(String value) {
        this.encounterCode = value;
    }

    /**
     * Gets the value of the enteredBy property.
     * 
     * @return
     *     possible object is
     *     {@link Employee }
     *     
     */
    public Employee getEnteredBy() {
        return enteredBy;
    }

    /**
     * Sets the value of the enteredBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link Employee }
     *     
     */
    public void setEnteredBy(Employee value) {
        this.enteredBy = value;
    }

    /**
     * Gets the value of the enteredOn property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEnteredOn() {
        return enteredOn;
    }

    /**
     * Sets the value of the enteredOn property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEnteredOn(XMLGregorianCalendar value) {
        this.enteredOn = value;
    }

    /**
     * Gets the value of the diagnosis property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getDiagnosis() {
        return diagnosis;
    }

    /**
     * Sets the value of the diagnosis property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setDiagnosis(CodeAndName value) {
        this.diagnosis = value;
    }

    /**
     * Gets the value of the diagnosisType property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getDiagnosisType() {
        return diagnosisType;
    }

    /**
     * Sets the value of the diagnosisType property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setDiagnosisType(CodeAndName value) {
        this.diagnosisType = value;
    }

    /**
     * Gets the value of the diagnosisKind property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getDiagnosisKind() {
        return diagnosisKind;
    }

    /**
     * Sets the value of the diagnosisKind property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setDiagnosisKind(CodeAndName value) {
        this.diagnosisKind = value;
    }

    /**
     * Gets the value of the replacedExtId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReplacedExtId() {
        return replacedExtId;
    }

    /**
     * Sets the value of the replacedExtId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReplacedExtId(String value) {
        this.replacedExtId = value;
    }

    /**
     * Gets the value of the acuteOrChronic property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcuteOrChronic() {
        return acuteOrChronic;
    }

    /**
     * Sets the value of the acuteOrChronic property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAcuteOrChronic(String value) {
        this.acuteOrChronic = value;
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

    /**
     * Gets the value of the isFirstTime property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsFirstTime() {
        return isFirstTime;
    }

    /**
     * Sets the value of the isFirstTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsFirstTime(Boolean value) {
        this.isFirstTime = value;
    }

    /**
     * Gets the value of the dispensarySupervision property.
     * 
     * @return
     *     possible object is
     *     {@link DispensarySupervision }
     *     
     */
    public DispensarySupervision getDispensarySupervision() {
        return dispensarySupervision;
    }

    /**
     * Sets the value of the dispensarySupervision property.
     * 
     * @param value
     *     allowed object is
     *     {@link DispensarySupervision }
     *     
     */
    public void setDispensarySupervision(DispensarySupervision value) {
        this.dispensarySupervision = value;
    }

    /**
     * Gets the value of the traumaType property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getTraumaType() {
        return traumaType;
    }

    /**
     * Sets the value of the traumaType property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setTraumaType(CodeAndName value) {
        this.traumaType = value;
    }

    /**
     * Gets the value of the admissionsThisYear property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAdmissionsThisYear() {
        return admissionsThisYear;
    }

    /**
     * Sets the value of the admissionsThisYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAdmissionsThisYear(Long value) {
        this.admissionsThisYear = value;
    }

}
