
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for COCT_MT290000UV06.PatientEncounter complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT290000UV06.PatientEncounter">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="id" type="{urn:hl7-org:v3}DSET_II" minOccurs="0"/>
 *         &lt;element name="code" type="{urn:hl7-org:v3}CD" minOccurs="0"/>
 *         &lt;element name="effectiveTime" type="{urn:hl7-org:v3}IVL_TS" minOccurs="0"/>
 *         &lt;element name="activityTime" type="{urn:hl7-org:v3}IVL_TS" minOccurs="0"/>
 *         &lt;element name="priorityCode" type="{urn:hl7-org:v3}CD" minOccurs="0"/>
 *         &lt;element name="admissionReferralSourceCode" type="{urn:hl7-org:v3}CD" minOccurs="0"/>
 *         &lt;element name="dischargeDispositionCode" type="{urn:hl7-org:v3}CD" minOccurs="0"/>
 *         &lt;element name="reason" type="{urn:hl7-org:v3}COCT_MT290000UV06.Reason5" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:hl7-org:v3}InfrastructureRootAttributes"/>
 *       &lt;attribute name="nullFlavor" type="{urn:hl7-org:v3}NullFlavor" />
 *       &lt;attribute name="classCode" use="required" type="{urn:hl7-org:v3}ActClassEncounter" />
 *       &lt;attribute name="moodCode" use="required" type="{urn:hl7-org:v3}ActMoodEventOccurrence" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "COCT_MT290000UV06.PatientEncounter", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "id",
    "code",
    "effectiveTime",
    "activityTime",
    "priorityCode",
    "admissionReferralSourceCode",
    "dischargeDispositionCode",
    "reason"
})
public class COCTMT290000UV06PatientEncounter {

    protected DSETCS realmCode;
    protected II typeId;
    protected LISTII templateId;
    protected DSETII id;
    protected CD code;
    protected IVLTS effectiveTime;
    protected IVLTS activityTime;
    protected CD priorityCode;
    protected CD admissionReferralSourceCode;
    protected CD dischargeDispositionCode;
    @XmlElement(nillable = true)
    protected List<COCTMT290000UV06Reason5> reason;
    @XmlAttribute(name = "nullFlavor")
    protected NullFlavor nullFlavor;
    @XmlAttribute(name = "classCode", required = true)
    protected ActClassEncounter classCode;
    @XmlAttribute(name = "moodCode", required = true)
    protected ActMoodEventOccurrence moodCode;

    /**
     * Gets the value of the realmCode property.
     * 
     * @return
     *     possible object is
     *     {@link DSETCS }
     *     
     */
    public DSETCS getRealmCode() {
        return realmCode;
    }

    /**
     * Sets the value of the realmCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link DSETCS }
     *     
     */
    public void setRealmCode(DSETCS value) {
        this.realmCode = value;
    }

    /**
     * Gets the value of the typeId property.
     * 
     * @return
     *     possible object is
     *     {@link II }
     *     
     */
    public II getTypeId() {
        return typeId;
    }

    /**
     * Sets the value of the typeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link II }
     *     
     */
    public void setTypeId(II value) {
        this.typeId = value;
    }

    /**
     * Gets the value of the templateId property.
     * 
     * @return
     *     possible object is
     *     {@link LISTII }
     *     
     */
    public LISTII getTemplateId() {
        return templateId;
    }

    /**
     * Sets the value of the templateId property.
     * 
     * @param value
     *     allowed object is
     *     {@link LISTII }
     *     
     */
    public void setTemplateId(LISTII value) {
        this.templateId = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link DSETII }
     *     
     */
    public DSETII getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link DSETII }
     *     
     */
    public void setId(DSETII value) {
        this.id = value;
    }

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setCode(CD value) {
        this.code = value;
    }

    /**
     * Gets the value of the effectiveTime property.
     * 
     * @return
     *     possible object is
     *     {@link IVLTS }
     *     
     */
    public IVLTS getEffectiveTime() {
        return effectiveTime;
    }

    /**
     * Sets the value of the effectiveTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link IVLTS }
     *     
     */
    public void setEffectiveTime(IVLTS value) {
        this.effectiveTime = value;
    }

    /**
     * Gets the value of the activityTime property.
     * 
     * @return
     *     possible object is
     *     {@link IVLTS }
     *     
     */
    public IVLTS getActivityTime() {
        return activityTime;
    }

    /**
     * Sets the value of the activityTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link IVLTS }
     *     
     */
    public void setActivityTime(IVLTS value) {
        this.activityTime = value;
    }

    /**
     * Gets the value of the priorityCode property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getPriorityCode() {
        return priorityCode;
    }

    /**
     * Sets the value of the priorityCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setPriorityCode(CD value) {
        this.priorityCode = value;
    }

    /**
     * Gets the value of the admissionReferralSourceCode property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getAdmissionReferralSourceCode() {
        return admissionReferralSourceCode;
    }

    /**
     * Sets the value of the admissionReferralSourceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setAdmissionReferralSourceCode(CD value) {
        this.admissionReferralSourceCode = value;
    }

    /**
     * Gets the value of the dischargeDispositionCode property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getDischargeDispositionCode() {
        return dischargeDispositionCode;
    }

    /**
     * Sets the value of the dischargeDispositionCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setDischargeDispositionCode(CD value) {
        this.dischargeDispositionCode = value;
    }

    /**
     * Gets the value of the reason property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reason property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReason().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT290000UV06Reason5 }
     * 
     * 
     */
    public List<COCTMT290000UV06Reason5> getReason() {
        if (reason == null) {
            reason = new ArrayList<COCTMT290000UV06Reason5>();
        }
        return this.reason;
    }

    /**
     * Gets the value of the nullFlavor property.
     * 
     * @return
     *     possible object is
     *     {@link NullFlavor }
     *     
     */
    public NullFlavor getNullFlavor() {
        return nullFlavor;
    }

    /**
     * Sets the value of the nullFlavor property.
     * 
     * @param value
     *     allowed object is
     *     {@link NullFlavor }
     *     
     */
    public void setNullFlavor(NullFlavor value) {
        this.nullFlavor = value;
    }

    /**
     * Gets the value of the classCode property.
     * 
     * @return
     *     possible object is
     *     {@link ActClassEncounter }
     *     
     */
    public ActClassEncounter getClassCode() {
        return classCode;
    }

    /**
     * Sets the value of the classCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActClassEncounter }
     *     
     */
    public void setClassCode(ActClassEncounter value) {
        this.classCode = value;
    }

    /**
     * Gets the value of the moodCode property.
     * 
     * @return
     *     possible object is
     *     {@link ActMoodEventOccurrence }
     *     
     */
    public ActMoodEventOccurrence getMoodCode() {
        return moodCode;
    }

    /**
     * Sets the value of the moodCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActMoodEventOccurrence }
     *     
     */
    public void setMoodCode(ActMoodEventOccurrence value) {
        this.moodCode = value;
    }

}
