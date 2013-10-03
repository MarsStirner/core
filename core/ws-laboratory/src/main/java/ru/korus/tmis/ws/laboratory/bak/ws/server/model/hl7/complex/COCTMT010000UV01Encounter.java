
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

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
 * <p>Java class for COCT_MT010000UV01.Encounter complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT010000UV01.Encounter">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="id" type="{urn:hl7-org:v3}DSET_II"/>
 *         &lt;element name="code" type="{urn:hl7-org:v3}CD" minOccurs="0"/>
 *         &lt;element name="statusCode" type="{urn:hl7-org:v3}CS"/>
 *         &lt;element name="effectiveTime" type="{urn:hl7-org:v3}IVL_TS" minOccurs="0"/>
 *         &lt;element name="activityTime" type="{urn:hl7-org:v3}IVL_TS" minOccurs="0"/>
 *         &lt;element name="priorityCode" type="{urn:hl7-org:v3}DSET_CD" minOccurs="0"/>
 *         &lt;element name="confidentialityCode" type="{urn:hl7-org:v3}DSET_CD" minOccurs="0"/>
 *         &lt;element name="reasonCode" type="{urn:hl7-org:v3}DSET_CD" minOccurs="0"/>
 *         &lt;element name="admissionReferralSourceCode" type="{urn:hl7-org:v3}CD" minOccurs="0"/>
 *         &lt;element name="lengthOfStayQuantity" type="{urn:hl7-org:v3}PQ" minOccurs="0"/>
 *         &lt;element name="dischargeDispositionCode" type="{urn:hl7-org:v3}CD" minOccurs="0"/>
 *         &lt;element name="preAdmitTestInd" type="{urn:hl7-org:v3}BL" minOccurs="0"/>
 *         &lt;element name="specialCourtesiesCode" type="{urn:hl7-org:v3}DSET_CD" minOccurs="0"/>
 *         &lt;element name="specialArrangementCode" type="{urn:hl7-org:v3}DSET_CD" minOccurs="0"/>
 *         &lt;element name="subject" type="{urn:hl7-org:v3}COCT_MT010000UV01.Subject1"/>
 *         &lt;element name="responsibleParty" type="{urn:hl7-org:v3}COCT_MT010000UV01.ResponsibleParty1" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="admitter" type="{urn:hl7-org:v3}COCT_MT010000UV01.Admitter" minOccurs="0"/>
 *         &lt;element name="attender" type="{urn:hl7-org:v3}COCT_MT010000UV01.Attender" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="discharger" type="{urn:hl7-org:v3}COCT_MT010000UV01.Discharger" minOccurs="0"/>
 *         &lt;element name="referrer" type="{urn:hl7-org:v3}COCT_MT010000UV01.Referrer" minOccurs="0"/>
 *         &lt;element name="consultant" type="{urn:hl7-org:v3}COCT_MT010000UV01.Consultant" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="notificationContact" type="{urn:hl7-org:v3}COCT_MT010000UV01.NotificationContact" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="location" type="{urn:hl7-org:v3}COCT_MT010000UV01.Location1" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="inFulfillmentOf" type="{urn:hl7-org:v3}COCT_MT010000UV01.InFulfillmentOf" minOccurs="0"/>
 *         &lt;element name="sequelTo" type="{urn:hl7-org:v3}COCT_MT010000UV01.SequelTo" minOccurs="0"/>
 *         &lt;element name="causeOf" type="{urn:hl7-org:v3}COCT_MT010000UV01.CauseOf" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="authorization" type="{urn:hl7-org:v3}COCT_MT010000UV01.Authorization" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="reference" type="{urn:hl7-org:v3}COCT_MT010000UV01.Reference" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="pertinentInformation1" type="{urn:hl7-org:v3}COCT_MT010000UV01.PertinentInformation2" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="pertinentInformation2" type="{urn:hl7-org:v3}COCT_MT010000UV01.PertinentInformation3" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="transportedBy" type="{urn:hl7-org:v3}COCT_MT010000UV01.TransportedBy" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="componentOf" type="{urn:hl7-org:v3}COCT_MT010000UV01.Component" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "COCT_MT010000UV01.Encounter", propOrder = {
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
    "discharger",
    "referrer",
    "consultant",
    "notificationContact",
    "location",
    "inFulfillmentOf",
    "sequelTo",
    "causeOf",
    "authorization",
    "reference",
    "pertinentInformation1",
    "pertinentInformation2",
    "transportedBy",
    "componentOf"
})
public class COCTMT010000UV01Encounter {

    protected DSETCS realmCode;
    protected II typeId;
    protected LISTII templateId;
    @XmlElement(required = true)
    protected DSETII id;
    protected CD code;
    @XmlElement(required = true)
    protected CS statusCode;
    protected IVLTS effectiveTime;
    protected IVLTS activityTime;
    protected DSETCD priorityCode;
    protected DSETCD confidentialityCode;
    protected DSETCD reasonCode;
    protected CD admissionReferralSourceCode;
    protected PQ lengthOfStayQuantity;
    protected CD dischargeDispositionCode;
    protected BL preAdmitTestInd;
    protected DSETCD specialCourtesiesCode;
    protected DSETCD specialArrangementCode;
    @XmlElement(required = true)
    protected COCTMT010000UV01Subject1 subject;
    @XmlElement(nillable = true)
    protected List<COCTMT010000UV01ResponsibleParty1> responsibleParty;
    @XmlElementRef(name = "admitter", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT010000UV01Admitter> admitter;
    @XmlElement(nillable = true)
    protected List<COCTMT010000UV01Attender> attender;
    @XmlElementRef(name = "discharger", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT010000UV01Discharger> discharger;
    @XmlElementRef(name = "referrer", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT010000UV01Referrer> referrer;
    @XmlElement(nillable = true)
    protected List<COCTMT010000UV01Consultant> consultant;
    @XmlElement(nillable = true)
    protected List<COCTMT010000UV01NotificationContact> notificationContact;
    @XmlElement(nillable = true)
    protected List<COCTMT010000UV01Location1> location;
    @XmlElementRef(name = "inFulfillmentOf", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT010000UV01InFulfillmentOf> inFulfillmentOf;
    @XmlElementRef(name = "sequelTo", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT010000UV01SequelTo> sequelTo;
    @XmlElement(nillable = true)
    protected List<COCTMT010000UV01CauseOf> causeOf;
    @XmlElement(nillable = true)
    protected List<COCTMT010000UV01Authorization> authorization;
    @XmlElement(nillable = true)
    protected List<COCTMT010000UV01Reference> reference;
    @XmlElement(nillable = true)
    protected List<COCTMT010000UV01PertinentInformation2> pertinentInformation1;
    @XmlElement(nillable = true)
    protected List<COCTMT010000UV01PertinentInformation3> pertinentInformation2;
    @XmlElement(nillable = true)
    protected List<COCTMT010000UV01TransportedBy> transportedBy;
    @XmlElement(nillable = true)
    protected List<COCTMT010000UV01Component> componentOf;
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
     *     {@link DSETCD }
     *     
     */
    public DSETCD getPriorityCode() {
        return priorityCode;
    }

    /**
     * Sets the value of the priorityCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link DSETCD }
     *     
     */
    public void setPriorityCode(DSETCD value) {
        this.priorityCode = value;
    }

    /**
     * Gets the value of the confidentialityCode property.
     * 
     * @return
     *     possible object is
     *     {@link DSETCD }
     *     
     */
    public DSETCD getConfidentialityCode() {
        return confidentialityCode;
    }

    /**
     * Sets the value of the confidentialityCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link DSETCD }
     *     
     */
    public void setConfidentialityCode(DSETCD value) {
        this.confidentialityCode = value;
    }

    /**
     * Gets the value of the reasonCode property.
     * 
     * @return
     *     possible object is
     *     {@link DSETCD }
     *     
     */
    public DSETCD getReasonCode() {
        return reasonCode;
    }

    /**
     * Sets the value of the reasonCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link DSETCD }
     *     
     */
    public void setReasonCode(DSETCD value) {
        this.reasonCode = value;
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
     * @return
     *     possible object is
     *     {@link DSETCD }
     *     
     */
    public DSETCD getSpecialCourtesiesCode() {
        return specialCourtesiesCode;
    }

    /**
     * Sets the value of the specialCourtesiesCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link DSETCD }
     *     
     */
    public void setSpecialCourtesiesCode(DSETCD value) {
        this.specialCourtesiesCode = value;
    }

    /**
     * Gets the value of the specialArrangementCode property.
     * 
     * @return
     *     possible object is
     *     {@link DSETCD }
     *     
     */
    public DSETCD getSpecialArrangementCode() {
        return specialArrangementCode;
    }

    /**
     * Sets the value of the specialArrangementCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link DSETCD }
     *     
     */
    public void setSpecialArrangementCode(DSETCD value) {
        this.specialArrangementCode = value;
    }

    /**
     * Gets the value of the subject property.
     * 
     * @return
     *     possible object is
     *     {@link COCTMT010000UV01Subject1 }
     *     
     */
    public COCTMT010000UV01Subject1 getSubject() {
        return subject;
    }

    /**
     * Sets the value of the subject property.
     * 
     * @param value
     *     allowed object is
     *     {@link COCTMT010000UV01Subject1 }
     *     
     */
    public void setSubject(COCTMT010000UV01Subject1 value) {
        this.subject = value;
    }

    /**
     * Gets the value of the responsibleParty property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the responsibleParty property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResponsibleParty().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT010000UV01ResponsibleParty1 }
     * 
     * 
     */
    public List<COCTMT010000UV01ResponsibleParty1> getResponsibleParty() {
        if (responsibleParty == null) {
            responsibleParty = new ArrayList<COCTMT010000UV01ResponsibleParty1>();
        }
        return this.responsibleParty;
    }

    /**
     * Gets the value of the admitter property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT010000UV01Admitter }{@code >}
     *     
     */
    public JAXBElement<COCTMT010000UV01Admitter> getAdmitter() {
        return admitter;
    }

    /**
     * Sets the value of the admitter property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT010000UV01Admitter }{@code >}
     *     
     */
    public void setAdmitter(JAXBElement<COCTMT010000UV01Admitter> value) {
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
     * {@link COCTMT010000UV01Attender }
     * 
     * 
     */
    public List<COCTMT010000UV01Attender> getAttender() {
        if (attender == null) {
            attender = new ArrayList<COCTMT010000UV01Attender>();
        }
        return this.attender;
    }

    /**
     * Gets the value of the discharger property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT010000UV01Discharger }{@code >}
     *     
     */
    public JAXBElement<COCTMT010000UV01Discharger> getDischarger() {
        return discharger;
    }

    /**
     * Sets the value of the discharger property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT010000UV01Discharger }{@code >}
     *     
     */
    public void setDischarger(JAXBElement<COCTMT010000UV01Discharger> value) {
        this.discharger = value;
    }

    /**
     * Gets the value of the referrer property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT010000UV01Referrer }{@code >}
     *     
     */
    public JAXBElement<COCTMT010000UV01Referrer> getReferrer() {
        return referrer;
    }

    /**
     * Sets the value of the referrer property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT010000UV01Referrer }{@code >}
     *     
     */
    public void setReferrer(JAXBElement<COCTMT010000UV01Referrer> value) {
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
     * {@link COCTMT010000UV01Consultant }
     * 
     * 
     */
    public List<COCTMT010000UV01Consultant> getConsultant() {
        if (consultant == null) {
            consultant = new ArrayList<COCTMT010000UV01Consultant>();
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
     * {@link COCTMT010000UV01NotificationContact }
     * 
     * 
     */
    public List<COCTMT010000UV01NotificationContact> getNotificationContact() {
        if (notificationContact == null) {
            notificationContact = new ArrayList<COCTMT010000UV01NotificationContact>();
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
     * {@link COCTMT010000UV01Location1 }
     * 
     * 
     */
    public List<COCTMT010000UV01Location1> getLocation() {
        if (location == null) {
            location = new ArrayList<COCTMT010000UV01Location1>();
        }
        return this.location;
    }

    /**
     * Gets the value of the inFulfillmentOf property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT010000UV01InFulfillmentOf }{@code >}
     *     
     */
    public JAXBElement<COCTMT010000UV01InFulfillmentOf> getInFulfillmentOf() {
        return inFulfillmentOf;
    }

    /**
     * Sets the value of the inFulfillmentOf property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT010000UV01InFulfillmentOf }{@code >}
     *     
     */
    public void setInFulfillmentOf(JAXBElement<COCTMT010000UV01InFulfillmentOf> value) {
        this.inFulfillmentOf = value;
    }

    /**
     * Gets the value of the sequelTo property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT010000UV01SequelTo }{@code >}
     *     
     */
    public JAXBElement<COCTMT010000UV01SequelTo> getSequelTo() {
        return sequelTo;
    }

    /**
     * Sets the value of the sequelTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT010000UV01SequelTo }{@code >}
     *     
     */
    public void setSequelTo(JAXBElement<COCTMT010000UV01SequelTo> value) {
        this.sequelTo = value;
    }

    /**
     * Gets the value of the causeOf property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the causeOf property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCauseOf().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT010000UV01CauseOf }
     * 
     * 
     */
    public List<COCTMT010000UV01CauseOf> getCauseOf() {
        if (causeOf == null) {
            causeOf = new ArrayList<COCTMT010000UV01CauseOf>();
        }
        return this.causeOf;
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
     * {@link COCTMT010000UV01Authorization }
     * 
     * 
     */
    public List<COCTMT010000UV01Authorization> getAuthorization() {
        if (authorization == null) {
            authorization = new ArrayList<COCTMT010000UV01Authorization>();
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
     * {@link COCTMT010000UV01Reference }
     * 
     * 
     */
    public List<COCTMT010000UV01Reference> getReference() {
        if (reference == null) {
            reference = new ArrayList<COCTMT010000UV01Reference>();
        }
        return this.reference;
    }

    /**
     * Gets the value of the pertinentInformation1 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pertinentInformation1 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPertinentInformation1().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT010000UV01PertinentInformation2 }
     * 
     * 
     */
    public List<COCTMT010000UV01PertinentInformation2> getPertinentInformation1() {
        if (pertinentInformation1 == null) {
            pertinentInformation1 = new ArrayList<COCTMT010000UV01PertinentInformation2>();
        }
        return this.pertinentInformation1;
    }

    /**
     * Gets the value of the pertinentInformation2 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pertinentInformation2 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPertinentInformation2().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT010000UV01PertinentInformation3 }
     * 
     * 
     */
    public List<COCTMT010000UV01PertinentInformation3> getPertinentInformation2() {
        if (pertinentInformation2 == null) {
            pertinentInformation2 = new ArrayList<COCTMT010000UV01PertinentInformation3>();
        }
        return this.pertinentInformation2;
    }

    /**
     * Gets the value of the transportedBy property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the transportedBy property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTransportedBy().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT010000UV01TransportedBy }
     * 
     * 
     */
    public List<COCTMT010000UV01TransportedBy> getTransportedBy() {
        if (transportedBy == null) {
            transportedBy = new ArrayList<COCTMT010000UV01TransportedBy>();
        }
        return this.transportedBy;
    }

    /**
     * Gets the value of the componentOf property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the componentOf property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getComponentOf().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT010000UV01Component }
     * 
     * 
     */
    public List<COCTMT010000UV01Component> getComponentOf() {
        if (componentOf == null) {
            componentOf = new ArrayList<COCTMT010000UV01Component>();
        }
        return this.componentOf;
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
