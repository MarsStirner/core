
package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PRPA_MT402001UV02.InpatientEncounterEvent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PRPA_MT402001UV02.InpatientEncounterEvent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="realmCode" type="{urn:hl7-org:v3}CS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="typeId" type="{urn:hl7-org:v3}II" minOccurs="0"/>
 *         &lt;element name="templateId" type="{urn:hl7-org:v3}II" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="id" type="{urn:hl7-org:v3}II" maxOccurs="unbounded"/>
 *         &lt;element name="code" type="{urn:hl7-org:v3}CD"/>
 *         &lt;element name="statusCode" type="{urn:hl7-org:v3}CS"/>
 *         &lt;element name="effectiveTime" type="{urn:hl7-org:v3}IVL_TS"/>
 *         &lt;element name="activityTime" type="{urn:hl7-org:v3}IVL_TS" minOccurs="0"/>
 *         &lt;element name="priorityCode" type="{urn:hl7-org:v3}CE" minOccurs="0"/>
 *         &lt;element name="confidentialityCode" type="{urn:hl7-org:v3}CE" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="reasonCode" type="{urn:hl7-org:v3}CE" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="admissionReferralSourceCode" type="{urn:hl7-org:v3}CE" minOccurs="0"/>
 *         &lt;element name="lengthOfStayQuantity" type="{urn:hl7-org:v3}PQ" minOccurs="0"/>
 *         &lt;element name="dischargeDispositionCode" type="{urn:hl7-org:v3}CE" minOccurs="0"/>
 *         &lt;element name="preAdmitTestInd" type="{urn:hl7-org:v3}BL" minOccurs="0"/>
 *         &lt;element name="specialCourtesiesCode" type="{urn:hl7-org:v3}CE" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="specialArrangementCode" type="{urn:hl7-org:v3}CE" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="subject" type="{urn:hl7-org:v3}PRPA_MT402001UV02.Subject"/>
 *         &lt;element name="responsibleParty" type="{urn:hl7-org:v3}PRPA_MT402001UV02.ResponsibleParty" minOccurs="0"/>
 *         &lt;element name="admitter" type="{urn:hl7-org:v3}PRPA_MT402001UV02.Admitter"/>
 *         &lt;element name="attender" type="{urn:hl7-org:v3}PRPA_MT402001UV02.Attender" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="referrer" type="{urn:hl7-org:v3}PRPA_MT402001UV02.Referrer" minOccurs="0"/>
 *         &lt;element name="consultant" type="{urn:hl7-org:v3}PRPA_MT402001UV02.Consultant" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="notificationContact" type="{urn:hl7-org:v3}PRPA_MT402001UV02.NotificationContact" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="location" type="{urn:hl7-org:v3}PRPA_MT402001UV02.Location1" maxOccurs="unbounded"/>
 *         &lt;element name="inFulfillmentOf" type="{urn:hl7-org:v3}PRPA_MT402001UV02.InFulfillmentOf" minOccurs="0"/>
 *         &lt;element name="sequelTo" type="{urn:hl7-org:v3}PRPA_MT402001UV02.SequelTo" minOccurs="0"/>
 *         &lt;element name="reason" type="{urn:hl7-org:v3}PRPA_MT402001UV02.Reason1" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="authorization" type="{urn:hl7-org:v3}PRPA_MT402001UV02.Authorization" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="reference" type="{urn:hl7-org:v3}PRPA_MT402001UV02.Reference" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="itemStorage" type="{urn:hl7-org:v3}PRPA_MT402001UV02.ItemStorage" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="arrivedBy" type="{urn:hl7-org:v3}PRPA_MT402001UV02.ArrivedBy" minOccurs="0"/>
 *         &lt;element name="departedBy" type="{urn:hl7-org:v3}PRPA_MT402001UV02.DepartedBy" minOccurs="0"/>
 *         &lt;element name="reasonOf" type="{urn:hl7-org:v3}PRPA_MT402001UV02.Reason2" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="componentOf" type="{urn:hl7-org:v3}PRPA_MT402001UV02.Component" minOccurs="0"/>
 *       &lt;/sequence>
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
@XmlType(name = "PRPA_MT402001UV02.InpatientEncounterEvent", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "id",
    "code",
    "statusCode",
    "effectiveTime",
    "activityTime",
    "priorityCode",
    "confidentialityCode",
    "reasonCode",
    "admissionReferralSourceCode",
    "lengthOfStayQuantity",
    "dischargeDispositionCode",
    "preAdmitTestInd",
    "specialCourtesiesCode",
    "specialArrangementCode",
    "subject",
    "responsibleParty",
    "admitter",
    "attender",
    "referrer",
    "consultant",
    "notificationContact",
    "location",
    "inFulfillmentOf",
    "sequelTo",
    "reason",
    "authorization",
    "reference",
    "itemStorage",
    "arrivedBy",
    "departedBy",
    "reasonOf",
    "componentOf"
})
public class PRPAMT402001UV02InpatientEncounterEvent {

    protected List<CS> realmCode;
    protected II typeId;
    protected List<II> templateId;
    @XmlElement(required = true)
    protected List<II> id;
    @XmlElement(required = true)
    protected CD code;
    @XmlElement(required = true)
    protected CS statusCode;
    @XmlElement(required = true)
    protected IVLTS effectiveTime;
    protected IVLTS activityTime;
    protected CE priorityCode;
    protected List<CE> confidentialityCode;
    protected List<CE> reasonCode;
    protected CE admissionReferralSourceCode;
    protected PQ lengthOfStayQuantity;
    protected CE dischargeDispositionCode;
    protected BL preAdmitTestInd;
    protected List<CE> specialCourtesiesCode;
    protected List<CE> specialArrangementCode;
    @XmlElement(required = true)
    protected PRPAMT402001UV02Subject subject;
    @XmlElementRef(name = "responsibleParty", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<PRPAMT402001UV02ResponsibleParty> responsibleParty;
    @XmlElement(required = true, nillable = true)
    protected PRPAMT402001UV02Admitter admitter;
    @XmlElement(nillable = true)
    protected List<PRPAMT402001UV02Attender> attender;
    @XmlElementRef(name = "referrer", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<PRPAMT402001UV02Referrer> referrer;
    @XmlElement(nillable = true)
    protected List<PRPAMT402001UV02Consultant> consultant;
    @XmlElement(nillable = true)
    protected List<PRPAMT402001UV02NotificationContact> notificationContact;
    @XmlElement(required = true, nillable = true)
    protected List<PRPAMT402001UV02Location1> location;
    @XmlElementRef(name = "inFulfillmentOf", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<PRPAMT402001UV02InFulfillmentOf> inFulfillmentOf;
    @XmlElementRef(name = "sequelTo", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<PRPAMT402001UV02SequelTo> sequelTo;
    @XmlElement(nillable = true)
    protected List<PRPAMT402001UV02Reason1> reason;
    @XmlElement(nillable = true)
    protected List<PRPAMT402001UV02Authorization> authorization;
    @XmlElement(nillable = true)
    protected List<PRPAMT402001UV02Reference> reference;
    @XmlElement(nillable = true)
    protected List<PRPAMT402001UV02ItemStorage> itemStorage;
    @XmlElementRef(name = "arrivedBy", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<PRPAMT402001UV02ArrivedBy> arrivedBy;
    @XmlElementRef(name = "departedBy", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<PRPAMT402001UV02DepartedBy> departedBy;
    @XmlElement(nillable = true)
    protected List<PRPAMT402001UV02Reason2> reasonOf;
    @XmlElementRef(name = "componentOf", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<PRPAMT402001UV02Component> componentOf;
    @XmlAttribute(name = "classCode", required = true)
    protected ActClassEncounter classCode;
    @XmlAttribute(name = "moodCode", required = true)
    protected ActMoodEventOccurrence moodCode;

    /**
     * Gets the value of the realmCode property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the realmCode property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRealmCode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CS }
     * 
     * 
     */
    public List<CS> getRealmCode() {
        if (realmCode == null) {
            realmCode = new ArrayList<CS>();
        }
        return this.realmCode;
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
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the templateId property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTemplateId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link II }
     * 
     * 
     */
    public List<II> getTemplateId() {
        if (templateId == null) {
            templateId = new ArrayList<II>();
        }
        return this.templateId;
    }

    /**
     * Gets the value of the id property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the id property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link II }
     * 
     * 
     */
    public List<II> getId() {
        if (id == null) {
            id = new ArrayList<II>();
        }
        return this.id;
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
     * Gets the value of the statusCode property.
     * 
     * @return
     *     possible object is
     *     {@link CS }
     *     
     */
    public CS getStatusCode() {
        return statusCode;
    }

    /**
     * Sets the value of the statusCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CS }
     *     
     */
    public void setStatusCode(CS value) {
        this.statusCode = value;
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
     *     {@link CE }
     *     
     */
    public CE getPriorityCode() {
        return priorityCode;
    }

    /**
     * Sets the value of the priorityCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CE }
     *     
     */
    public void setPriorityCode(CE value) {
        this.priorityCode = value;
    }

    /**
     * Gets the value of the confidentialityCode property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the confidentialityCode property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConfidentialityCode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CE }
     * 
     * 
     */
    public List<CE> getConfidentialityCode() {
        if (confidentialityCode == null) {
            confidentialityCode = new ArrayList<CE>();
        }
        return this.confidentialityCode;
    }

    /**
     * Gets the value of the reasonCode property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reasonCode property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReasonCode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CE }
     * 
     * 
     */
    public List<CE> getReasonCode() {
        if (reasonCode == null) {
            reasonCode = new ArrayList<CE>();
        }
        return this.reasonCode;
    }

    /**
     * Gets the value of the admissionReferralSourceCode property.
     * 
     * @return
     *     possible object is
     *     {@link CE }
     *     
     */
    public CE getAdmissionReferralSourceCode() {
        return admissionReferralSourceCode;
    }

    /**
     * Sets the value of the admissionReferralSourceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CE }
     *     
     */
    public void setAdmissionReferralSourceCode(CE value) {
        this.admissionReferralSourceCode = value;
    }

    /**
     * Gets the value of the lengthOfStayQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link PQ }
     *     
     */
    public PQ getLengthOfStayQuantity() {
        return lengthOfStayQuantity;
    }

    /**
     * Sets the value of the lengthOfStayQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link PQ }
     *     
     */
    public void setLengthOfStayQuantity(PQ value) {
        this.lengthOfStayQuantity = value;
    }

    /**
     * Gets the value of the dischargeDispositionCode property.
     * 
     * @return
     *     possible object is
     *     {@link CE }
     *     
     */
    public CE getDischargeDispositionCode() {
        return dischargeDispositionCode;
    }

    /**
     * Sets the value of the dischargeDispositionCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CE }
     *     
     */
    public void setDischargeDispositionCode(CE value) {
        this.dischargeDispositionCode = value;
    }

    /**
     * Gets the value of the preAdmitTestInd property.
     * 
     * @return
     *     possible object is
     *     {@link BL }
     *     
     */
    public BL getPreAdmitTestInd() {
        return preAdmitTestInd;
    }

    /**
     * Sets the value of the preAdmitTestInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link BL }
     *     
     */
    public void setPreAdmitTestInd(BL value) {
        this.preAdmitTestInd = value;
    }

    /**
     * Gets the value of the specialCourtesiesCode property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the specialCourtesiesCode property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSpecialCourtesiesCode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CE }
     * 
     * 
     */
    public List<CE> getSpecialCourtesiesCode() {
        if (specialCourtesiesCode == null) {
            specialCourtesiesCode = new ArrayList<CE>();
        }
        return this.specialCourtesiesCode;
    }

    /**
     * Gets the value of the specialArrangementCode property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the specialArrangementCode property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSpecialArrangementCode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CE }
     * 
     * 
     */
    public List<CE> getSpecialArrangementCode() {
        if (specialArrangementCode == null) {
            specialArrangementCode = new ArrayList<CE>();
        }
        return this.specialArrangementCode;
    }

    /**
     * Gets the value of the subject property.
     * 
     * @return
     *     possible object is
     *     {@link PRPAMT402001UV02Subject }
     *     
     */
    public PRPAMT402001UV02Subject getSubject() {
        return subject;
    }

    /**
     * Sets the value of the subject property.
     * 
     * @param value
     *     allowed object is
     *     {@link PRPAMT402001UV02Subject }
     *     
     */
    public void setSubject(PRPAMT402001UV02Subject value) {
        this.subject = value;
    }

    /**
     * Gets the value of the responsibleParty property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link PRPAMT402001UV02ResponsibleParty }{@code >}
     *     
     */
    public JAXBElement<PRPAMT402001UV02ResponsibleParty> getResponsibleParty() {
        return responsibleParty;
    }

    /**
     * Sets the value of the responsibleParty property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link PRPAMT402001UV02ResponsibleParty }{@code >}
     *     
     */
    public void setResponsibleParty(JAXBElement<PRPAMT402001UV02ResponsibleParty> value) {
        this.responsibleParty = value;
    }

    /**
     * Gets the value of the admitter property.
     * 
     * @return
     *     possible object is
     *     {@link PRPAMT402001UV02Admitter }
     *     
     */
    public PRPAMT402001UV02Admitter getAdmitter() {
        return admitter;
    }

    /**
     * Sets the value of the admitter property.
     * 
     * @param value
     *     allowed object is
     *     {@link PRPAMT402001UV02Admitter }
     *     
     */
    public void setAdmitter(PRPAMT402001UV02Admitter value) {
        this.admitter = value;
    }

    /**
     * Gets the value of the attender property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attender property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttender().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PRPAMT402001UV02Attender }
     * 
     * 
     */
    public List<PRPAMT402001UV02Attender> getAttender() {
        if (attender == null) {
            attender = new ArrayList<PRPAMT402001UV02Attender>();
        }
        return this.attender;
    }

    /**
     * Gets the value of the referrer property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link PRPAMT402001UV02Referrer }{@code >}
     *     
     */
    public JAXBElement<PRPAMT402001UV02Referrer> getReferrer() {
        return referrer;
    }

    /**
     * Sets the value of the referrer property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link PRPAMT402001UV02Referrer }{@code >}
     *     
     */
    public void setReferrer(JAXBElement<PRPAMT402001UV02Referrer> value) {
        this.referrer = value;
    }

    /**
     * Gets the value of the consultant property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the consultant property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConsultant().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PRPAMT402001UV02Consultant }
     * 
     * 
     */
    public List<PRPAMT402001UV02Consultant> getConsultant() {
        if (consultant == null) {
            consultant = new ArrayList<PRPAMT402001UV02Consultant>();
        }
        return this.consultant;
    }

    /**
     * Gets the value of the notificationContact property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the notificationContact property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNotificationContact().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PRPAMT402001UV02NotificationContact }
     * 
     * 
     */
    public List<PRPAMT402001UV02NotificationContact> getNotificationContact() {
        if (notificationContact == null) {
            notificationContact = new ArrayList<PRPAMT402001UV02NotificationContact>();
        }
        return this.notificationContact;
    }

    /**
     * Gets the value of the location property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the location property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLocation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PRPAMT402001UV02Location1 }
     * 
     * 
     */
    public List<PRPAMT402001UV02Location1> getLocation() {
        if (location == null) {
            location = new ArrayList<PRPAMT402001UV02Location1>();
        }
        return this.location;
    }

    /**
     * Gets the value of the inFulfillmentOf property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link PRPAMT402001UV02InFulfillmentOf }{@code >}
     *     
     */
    public JAXBElement<PRPAMT402001UV02InFulfillmentOf> getInFulfillmentOf() {
        return inFulfillmentOf;
    }

    /**
     * Sets the value of the inFulfillmentOf property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link PRPAMT402001UV02InFulfillmentOf }{@code >}
     *     
     */
    public void setInFulfillmentOf(JAXBElement<PRPAMT402001UV02InFulfillmentOf> value) {
        this.inFulfillmentOf = value;
    }

    /**
     * Gets the value of the sequelTo property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link PRPAMT402001UV02SequelTo }{@code >}
     *     
     */
    public JAXBElement<PRPAMT402001UV02SequelTo> getSequelTo() {
        return sequelTo;
    }

    /**
     * Sets the value of the sequelTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link PRPAMT402001UV02SequelTo }{@code >}
     *     
     */
    public void setSequelTo(JAXBElement<PRPAMT402001UV02SequelTo> value) {
        this.sequelTo = value;
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
     * {@link PRPAMT402001UV02Reason1 }
     * 
     * 
     */
    public List<PRPAMT402001UV02Reason1> getReason() {
        if (reason == null) {
            reason = new ArrayList<PRPAMT402001UV02Reason1>();
        }
        return this.reason;
    }

    /**
     * Gets the value of the authorization property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the authorization property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAuthorization().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PRPAMT402001UV02Authorization }
     * 
     * 
     */
    public List<PRPAMT402001UV02Authorization> getAuthorization() {
        if (authorization == null) {
            authorization = new ArrayList<PRPAMT402001UV02Authorization>();
        }
        return this.authorization;
    }

    /**
     * Gets the value of the reference property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reference property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReference().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PRPAMT402001UV02Reference }
     * 
     * 
     */
    public List<PRPAMT402001UV02Reference> getReference() {
        if (reference == null) {
            reference = new ArrayList<PRPAMT402001UV02Reference>();
        }
        return this.reference;
    }

    /**
     * Gets the value of the itemStorage property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the itemStorage property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItemStorage().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PRPAMT402001UV02ItemStorage }
     * 
     * 
     */
    public List<PRPAMT402001UV02ItemStorage> getItemStorage() {
        if (itemStorage == null) {
            itemStorage = new ArrayList<PRPAMT402001UV02ItemStorage>();
        }
        return this.itemStorage;
    }

    /**
     * Gets the value of the arrivedBy property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link PRPAMT402001UV02ArrivedBy }{@code >}
     *     
     */
    public JAXBElement<PRPAMT402001UV02ArrivedBy> getArrivedBy() {
        return arrivedBy;
    }

    /**
     * Sets the value of the arrivedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link PRPAMT402001UV02ArrivedBy }{@code >}
     *     
     */
    public void setArrivedBy(JAXBElement<PRPAMT402001UV02ArrivedBy> value) {
        this.arrivedBy = value;
    }

    /**
     * Gets the value of the departedBy property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link PRPAMT402001UV02DepartedBy }{@code >}
     *     
     */
    public JAXBElement<PRPAMT402001UV02DepartedBy> getDepartedBy() {
        return departedBy;
    }

    /**
     * Sets the value of the departedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link PRPAMT402001UV02DepartedBy }{@code >}
     *     
     */
    public void setDepartedBy(JAXBElement<PRPAMT402001UV02DepartedBy> value) {
        this.departedBy = value;
    }

    /**
     * Gets the value of the reasonOf property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reasonOf property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReasonOf().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PRPAMT402001UV02Reason2 }
     * 
     * 
     */
    public List<PRPAMT402001UV02Reason2> getReasonOf() {
        if (reasonOf == null) {
            reasonOf = new ArrayList<PRPAMT402001UV02Reason2>();
        }
        return this.reasonOf;
    }

    /**
     * Gets the value of the componentOf property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link PRPAMT402001UV02Component }{@code >}
     *     
     */
    public JAXBElement<PRPAMT402001UV02Component> getComponentOf() {
        return componentOf;
    }

    /**
     * Sets the value of the componentOf property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link PRPAMT402001UV02Component }{@code >}
     *     
     */
    public void setComponentOf(JAXBElement<PRPAMT402001UV02Component> value) {
        this.componentOf = value;
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
