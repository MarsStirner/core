
package ru.korus.tmis.ehr.ws.callback;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Оказанные медицинские услуги
 * 
 * <p>Java class for MedService complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MedService">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="extId" type="{}String" minOccurs="0"/>
 *         &lt;element name="encounterCode" type="{}String" minOccurs="0"/>
 *         &lt;element name="enteredBy" type="{}Employee" minOccurs="0"/>
 *         &lt;element name="enteredOn" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="service" type="{}CodeAndName" minOccurs="0"/>
 *         &lt;element name="standard" type="{}CodeAndName" minOccurs="0"/>
 *         &lt;element name="diagnosis" type="{}CodeAndName" minOccurs="0"/>
 *         &lt;element name="quantity" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="unitOfMeasure" type="{}CodeAndName" minOccurs="0"/>
 *         &lt;element name="renderedOn" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="renderedBy" type="{}Employee" minOccurs="0"/>
 *         &lt;element name="description" type="{}String" minOccurs="0"/>
 *         &lt;element name="anesthesia" type="{}CodeAndName" minOccurs="0"/>
 *         &lt;element name="complications" type="{}String" minOccurs="0"/>
 *         &lt;element name="servCareType" type="{}CodeAndName" minOccurs="0"/>
 *         &lt;element name="typeOper" type="{}CodeAndName" minOccurs="0"/>
 *         &lt;element name="interventionType" type="{}CodeAndName" minOccurs="0"/>
 *         &lt;element name="facilityDept" type="{}CodeAndName" minOccurs="0"/>
 *         &lt;element name="careProfile" type="{}CodeAndName" minOccurs="0"/>
 *         &lt;element name="isChildProfile" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="bedProfile" type="{}CodeAndName" minOccurs="0"/>
 *         &lt;element name="servPaymentType" type="{}CodeAndName" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MedService", propOrder = {
    "extId",
    "encounterCode",
    "enteredBy",
    "enteredOn",
    "service",
    "standard",
    "diagnosis",
    "quantity",
    "unitOfMeasure",
    "renderedOn",
    "renderedBy",
    "description",
    "anesthesia",
    "complications",
    "servCareType",
    "typeOper",
    "interventionType",
    "facilityDept",
    "careProfile",
    "isChildProfile",
    "bedProfile",
    "servPaymentType"
})
public class MedService {

    protected String extId;
    protected String encounterCode;
    protected Employee enteredBy;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar enteredOn;
    protected CodeAndName service;
    protected CodeAndName standard;
    protected CodeAndName diagnosis;
    protected BigDecimal quantity;
    protected CodeAndName unitOfMeasure;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar renderedOn;
    protected Employee renderedBy;
    protected String description;
    protected CodeAndName anesthesia;
    protected String complications;
    protected CodeAndName servCareType;
    protected CodeAndName typeOper;
    protected CodeAndName interventionType;
    protected CodeAndName facilityDept;
    protected CodeAndName careProfile;
    protected Boolean isChildProfile;
    protected CodeAndName bedProfile;
    protected CodeAndName servPaymentType;

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
     * Gets the value of the service property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getService() {
        return service;
    }

    /**
     * Sets the value of the service property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setService(CodeAndName value) {
        this.service = value;
    }

    /**
     * Gets the value of the standard property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getStandard() {
        return standard;
    }

    /**
     * Sets the value of the standard property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setStandard(CodeAndName value) {
        this.standard = value;
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
     * Gets the value of the quantity property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setQuantity(BigDecimal value) {
        this.quantity = value;
    }

    /**
     * Gets the value of the unitOfMeasure property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getUnitOfMeasure() {
        return unitOfMeasure;
    }

    /**
     * Sets the value of the unitOfMeasure property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setUnitOfMeasure(CodeAndName value) {
        this.unitOfMeasure = value;
    }

    /**
     * Gets the value of the renderedOn property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRenderedOn() {
        return renderedOn;
    }

    /**
     * Sets the value of the renderedOn property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRenderedOn(XMLGregorianCalendar value) {
        this.renderedOn = value;
    }

    /**
     * Gets the value of the renderedBy property.
     * 
     * @return
     *     possible object is
     *     {@link Employee }
     *     
     */
    public Employee getRenderedBy() {
        return renderedBy;
    }

    /**
     * Sets the value of the renderedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link Employee }
     *     
     */
    public void setRenderedBy(Employee value) {
        this.renderedBy = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the anesthesia property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getAnesthesia() {
        return anesthesia;
    }

    /**
     * Sets the value of the anesthesia property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setAnesthesia(CodeAndName value) {
        this.anesthesia = value;
    }

    /**
     * Gets the value of the complications property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComplications() {
        return complications;
    }

    /**
     * Sets the value of the complications property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComplications(String value) {
        this.complications = value;
    }

    /**
     * Gets the value of the servCareType property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getServCareType() {
        return servCareType;
    }

    /**
     * Sets the value of the servCareType property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setServCareType(CodeAndName value) {
        this.servCareType = value;
    }

    /**
     * Gets the value of the typeOper property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getTypeOper() {
        return typeOper;
    }

    /**
     * Sets the value of the typeOper property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setTypeOper(CodeAndName value) {
        this.typeOper = value;
    }

    /**
     * Gets the value of the interventionType property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getInterventionType() {
        return interventionType;
    }

    /**
     * Sets the value of the interventionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setInterventionType(CodeAndName value) {
        this.interventionType = value;
    }

    /**
     * Gets the value of the facilityDept property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getFacilityDept() {
        return facilityDept;
    }

    /**
     * Sets the value of the facilityDept property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setFacilityDept(CodeAndName value) {
        this.facilityDept = value;
    }

    /**
     * Gets the value of the careProfile property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getCareProfile() {
        return careProfile;
    }

    /**
     * Sets the value of the careProfile property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setCareProfile(CodeAndName value) {
        this.careProfile = value;
    }

    /**
     * Gets the value of the isChildProfile property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsChildProfile() {
        return isChildProfile;
    }

    /**
     * Sets the value of the isChildProfile property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsChildProfile(Boolean value) {
        this.isChildProfile = value;
    }

    /**
     * Gets the value of the bedProfile property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getBedProfile() {
        return bedProfile;
    }

    /**
     * Sets the value of the bedProfile property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setBedProfile(CodeAndName value) {
        this.bedProfile = value;
    }

    /**
     * Gets the value of the servPaymentType property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getServPaymentType() {
        return servPaymentType;
    }

    /**
     * Sets the value of the servPaymentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setServPaymentType(CodeAndName value) {
        this.servPaymentType = value;
    }

}
