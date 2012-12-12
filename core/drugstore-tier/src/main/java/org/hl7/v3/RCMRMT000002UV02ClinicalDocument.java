
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
 * <p>Java class for RCMR_MT000002UV02.ClinicalDocument complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RCMR_MT000002UV02.ClinicalDocument">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="realmCode" type="{urn:hl7-org:v3}CS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="typeId" type="{urn:hl7-org:v3}II" minOccurs="0"/>
 *         &lt;element name="templateId" type="{urn:hl7-org:v3}II" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="id" type="{urn:hl7-org:v3}II"/>
 *         &lt;element name="code" type="{urn:hl7-org:v3}CE"/>
 *         &lt;element name="title" type="{urn:hl7-org:v3}ST" minOccurs="0"/>
 *         &lt;element name="text" type="{urn:hl7-org:v3}ED" minOccurs="0"/>
 *         &lt;element name="statusCode" type="{urn:hl7-org:v3}CS"/>
 *         &lt;element name="effectiveTime" type="{urn:hl7-org:v3}TS"/>
 *         &lt;element name="availabilityTime" type="{urn:hl7-org:v3}TS" minOccurs="0"/>
 *         &lt;element name="confidentialityCode" type="{urn:hl7-org:v3}CE"/>
 *         &lt;element name="reasonCode" type="{urn:hl7-org:v3}CE" minOccurs="0"/>
 *         &lt;element name="languageCode" type="{urn:hl7-org:v3}CE" minOccurs="0"/>
 *         &lt;element name="setId" type="{urn:hl7-org:v3}II" minOccurs="0"/>
 *         &lt;element name="versionNumber" type="{urn:hl7-org:v3}INT" minOccurs="0"/>
 *         &lt;element name="completionCode" type="{urn:hl7-org:v3}CE" minOccurs="0"/>
 *         &lt;element name="storageCode" type="{urn:hl7-org:v3}CE" minOccurs="0"/>
 *         &lt;element name="copyTime" type="{urn:hl7-org:v3}TS" minOccurs="0"/>
 *         &lt;element name="subject" type="{urn:hl7-org:v3}RCMR_MT000002UV02.Subject" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="recordTarget" type="{urn:hl7-org:v3}RCMR_MT000002UV02.RecordTarget" maxOccurs="unbounded"/>
 *         &lt;element name="author" type="{urn:hl7-org:v3}RCMR_MT000002UV02.Author" maxOccurs="unbounded"/>
 *         &lt;element name="dataEnterer" type="{urn:hl7-org:v3}RCMR_MT000002UV02.DataEnterer" minOccurs="0"/>
 *         &lt;element name="custodian" type="{urn:hl7-org:v3}RCMR_MT000002UV02.Custodian"/>
 *         &lt;element name="informationRecipient" type="{urn:hl7-org:v3}RCMR_MT000002UV02.InformationRecipient" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="legalAuthenticator" type="{urn:hl7-org:v3}RCMR_MT000002UV02.LegalAuthenticator" minOccurs="0"/>
 *         &lt;element name="authenticator" type="{urn:hl7-org:v3}RCMR_MT000002UV02.Authenticator" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="participant" type="{urn:hl7-org:v3}RCMR_MT000002UV02.Participant1" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="inFulfillmentOf" type="{urn:hl7-org:v3}RCMR_MT000002UV02.InFulfillmentOf" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="documentationOf" type="{urn:hl7-org:v3}RCMR_MT000002UV02.DocumentationOf" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="relatedDocument" type="{urn:hl7-org:v3}RCMR_MT000002UV02.RelatedDocument" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="authorization" type="{urn:hl7-org:v3}RCMR_MT000002UV02.Authorization" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="componentOf" type="{urn:hl7-org:v3}RCMR_MT000002UV02.Component1" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="classCode" use="required" type="{urn:hl7-org:v3}ActClassClinicalDocument" />
 *       &lt;attribute name="moodCode" use="required" type="{urn:hl7-org:v3}ActMoodEventOccurrence" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RCMR_MT000002UV02.ClinicalDocument", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "id",
    "code",
    "title",
    "text",
    "statusCode",
    "effectiveTime",
    "availabilityTime",
    "confidentialityCode",
    "reasonCode",
    "languageCode",
    "setId",
    "versionNumber",
    "completionCode",
    "storageCode",
    "copyTime",
    "subject",
    "recordTarget",
    "author",
    "dataEnterer",
    "custodian",
    "informationRecipient",
    "legalAuthenticator",
    "authenticator",
    "participant",
    "inFulfillmentOf",
    "documentationOf",
    "relatedDocument",
    "authorization",
    "componentOf"
})
public class RCMRMT000002UV02ClinicalDocument {

    protected List<CS> realmCode;
    protected II typeId;
    protected List<II> templateId;
    @XmlElement(required = true)
    protected II id;
    @XmlElement(required = true)
    protected CE code;
    protected ST title;
    protected ED text;
    @XmlElement(required = true)
    protected CS statusCode;
    @XmlElement(required = true)
    protected TS effectiveTime;
    protected TS availabilityTime;
    @XmlElement(required = true)
    protected CE confidentialityCode;
    protected CE reasonCode;
    protected CE languageCode;
    protected II setId;
    protected INT versionNumber;
    protected CE completionCode;
    protected CE storageCode;
    protected TS copyTime;
    @XmlElement(nillable = true)
    protected List<RCMRMT000002UV02Subject> subject;
    @XmlElement(required = true, nillable = true)
    protected List<RCMRMT000002UV02RecordTarget> recordTarget;
    @XmlElement(required = true, nillable = true)
    protected List<RCMRMT000002UV02Author> author;
    @XmlElementRef(name = "dataEnterer", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<RCMRMT000002UV02DataEnterer> dataEnterer;
    @XmlElement(required = true)
    protected RCMRMT000002UV02Custodian custodian;
    @XmlElement(nillable = true)
    protected List<RCMRMT000002UV02InformationRecipient> informationRecipient;
    @XmlElementRef(name = "legalAuthenticator", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<RCMRMT000002UV02LegalAuthenticator> legalAuthenticator;
    @XmlElement(nillable = true)
    protected List<RCMRMT000002UV02Authenticator> authenticator;
    @XmlElement(nillable = true)
    protected List<RCMRMT000002UV02Participant1> participant;
    @XmlElement(nillable = true)
    protected List<RCMRMT000002UV02InFulfillmentOf> inFulfillmentOf;
    @XmlElement(nillable = true)
    protected List<RCMRMT000002UV02DocumentationOf> documentationOf;
    @XmlElement(nillable = true)
    protected List<RCMRMT000002UV02RelatedDocument> relatedDocument;
    @XmlElement(nillable = true)
    protected List<RCMRMT000002UV02Authorization> authorization;
    @XmlElementRef(name = "componentOf", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<RCMRMT000002UV02Component1> componentOf;
    @XmlAttribute(name = "classCode", required = true)
    protected ActClassClinicalDocument classCode;
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
     * @return
     *     possible object is
     *     {@link II }
     *     
     */
    public II getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link II }
     *     
     */
    public void setId(II value) {
        this.id = value;
    }

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link CE }
     *     
     */
    public CE getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link CE }
     *     
     */
    public void setCode(CE value) {
        this.code = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link ST }
     *     
     */
    public ST getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link ST }
     *     
     */
    public void setTitle(ST value) {
        this.title = value;
    }

    /**
     * Gets the value of the text property.
     * 
     * @return
     *     possible object is
     *     {@link ED }
     *     
     */
    public ED getText() {
        return text;
    }

    /**
     * Sets the value of the text property.
     * 
     * @param value
     *     allowed object is
     *     {@link ED }
     *     
     */
    public void setText(ED value) {
        this.text = value;
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
     *     {@link TS }
     *     
     */
    public TS getEffectiveTime() {
        return effectiveTime;
    }

    /**
     * Sets the value of the effectiveTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link TS }
     *     
     */
    public void setEffectiveTime(TS value) {
        this.effectiveTime = value;
    }

    /**
     * Gets the value of the availabilityTime property.
     * 
     * @return
     *     possible object is
     *     {@link TS }
     *     
     */
    public TS getAvailabilityTime() {
        return availabilityTime;
    }

    /**
     * Sets the value of the availabilityTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link TS }
     *     
     */
    public void setAvailabilityTime(TS value) {
        this.availabilityTime = value;
    }

    /**
     * Gets the value of the confidentialityCode property.
     * 
     * @return
     *     possible object is
     *     {@link CE }
     *     
     */
    public CE getConfidentialityCode() {
        return confidentialityCode;
    }

    /**
     * Sets the value of the confidentialityCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CE }
     *     
     */
    public void setConfidentialityCode(CE value) {
        this.confidentialityCode = value;
    }

    /**
     * Gets the value of the reasonCode property.
     * 
     * @return
     *     possible object is
     *     {@link CE }
     *     
     */
    public CE getReasonCode() {
        return reasonCode;
    }

    /**
     * Sets the value of the reasonCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CE }
     *     
     */
    public void setReasonCode(CE value) {
        this.reasonCode = value;
    }

    /**
     * Gets the value of the languageCode property.
     * 
     * @return
     *     possible object is
     *     {@link CE }
     *     
     */
    public CE getLanguageCode() {
        return languageCode;
    }

    /**
     * Sets the value of the languageCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CE }
     *     
     */
    public void setLanguageCode(CE value) {
        this.languageCode = value;
    }

    /**
     * Gets the value of the setId property.
     * 
     * @return
     *     possible object is
     *     {@link II }
     *     
     */
    public II getSetId() {
        return setId;
    }

    /**
     * Sets the value of the setId property.
     * 
     * @param value
     *     allowed object is
     *     {@link II }
     *     
     */
    public void setSetId(II value) {
        this.setId = value;
    }

    /**
     * Gets the value of the versionNumber property.
     * 
     * @return
     *     possible object is
     *     {@link INT }
     *     
     */
    public INT getVersionNumber() {
        return versionNumber;
    }

    /**
     * Sets the value of the versionNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link INT }
     *     
     */
    public void setVersionNumber(INT value) {
        this.versionNumber = value;
    }

    /**
     * Gets the value of the completionCode property.
     * 
     * @return
     *     possible object is
     *     {@link CE }
     *     
     */
    public CE getCompletionCode() {
        return completionCode;
    }

    /**
     * Sets the value of the completionCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CE }
     *     
     */
    public void setCompletionCode(CE value) {
        this.completionCode = value;
    }

    /**
     * Gets the value of the storageCode property.
     * 
     * @return
     *     possible object is
     *     {@link CE }
     *     
     */
    public CE getStorageCode() {
        return storageCode;
    }

    /**
     * Sets the value of the storageCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CE }
     *     
     */
    public void setStorageCode(CE value) {
        this.storageCode = value;
    }

    /**
     * Gets the value of the copyTime property.
     * 
     * @return
     *     possible object is
     *     {@link TS }
     *     
     */
    public TS getCopyTime() {
        return copyTime;
    }

    /**
     * Sets the value of the copyTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link TS }
     *     
     */
    public void setCopyTime(TS value) {
        this.copyTime = value;
    }

    /**
     * Gets the value of the subject property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subject property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubject().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RCMRMT000002UV02Subject }
     * 
     * 
     */
    public List<RCMRMT000002UV02Subject> getSubject() {
        if (subject == null) {
            subject = new ArrayList<RCMRMT000002UV02Subject>();
        }
        return this.subject;
    }

    /**
     * Gets the value of the recordTarget property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the recordTarget property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRecordTarget().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RCMRMT000002UV02RecordTarget }
     * 
     * 
     */
    public List<RCMRMT000002UV02RecordTarget> getRecordTarget() {
        if (recordTarget == null) {
            recordTarget = new ArrayList<RCMRMT000002UV02RecordTarget>();
        }
        return this.recordTarget;
    }

    /**
     * Gets the value of the author property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the author property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAuthor().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RCMRMT000002UV02Author }
     * 
     * 
     */
    public List<RCMRMT000002UV02Author> getAuthor() {
        if (author == null) {
            author = new ArrayList<RCMRMT000002UV02Author>();
        }
        return this.author;
    }

    /**
     * Gets the value of the dataEnterer property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link RCMRMT000002UV02DataEnterer }{@code >}
     *     
     */
    public JAXBElement<RCMRMT000002UV02DataEnterer> getDataEnterer() {
        return dataEnterer;
    }

    /**
     * Sets the value of the dataEnterer property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link RCMRMT000002UV02DataEnterer }{@code >}
     *     
     */
    public void setDataEnterer(JAXBElement<RCMRMT000002UV02DataEnterer> value) {
        this.dataEnterer = value;
    }

    /**
     * Gets the value of the custodian property.
     * 
     * @return
     *     possible object is
     *     {@link RCMRMT000002UV02Custodian }
     *     
     */
    public RCMRMT000002UV02Custodian getCustodian() {
        return custodian;
    }

    /**
     * Sets the value of the custodian property.
     * 
     * @param value
     *     allowed object is
     *     {@link RCMRMT000002UV02Custodian }
     *     
     */
    public void setCustodian(RCMRMT000002UV02Custodian value) {
        this.custodian = value;
    }

    /**
     * Gets the value of the informationRecipient property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the informationRecipient property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInformationRecipient().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RCMRMT000002UV02InformationRecipient }
     * 
     * 
     */
    public List<RCMRMT000002UV02InformationRecipient> getInformationRecipient() {
        if (informationRecipient == null) {
            informationRecipient = new ArrayList<RCMRMT000002UV02InformationRecipient>();
        }
        return this.informationRecipient;
    }

    /**
     * Gets the value of the legalAuthenticator property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link RCMRMT000002UV02LegalAuthenticator }{@code >}
     *     
     */
    public JAXBElement<RCMRMT000002UV02LegalAuthenticator> getLegalAuthenticator() {
        return legalAuthenticator;
    }

    /**
     * Sets the value of the legalAuthenticator property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link RCMRMT000002UV02LegalAuthenticator }{@code >}
     *     
     */
    public void setLegalAuthenticator(JAXBElement<RCMRMT000002UV02LegalAuthenticator> value) {
        this.legalAuthenticator = value;
    }

    /**
     * Gets the value of the authenticator property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the authenticator property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAuthenticator().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RCMRMT000002UV02Authenticator }
     * 
     * 
     */
    public List<RCMRMT000002UV02Authenticator> getAuthenticator() {
        if (authenticator == null) {
            authenticator = new ArrayList<RCMRMT000002UV02Authenticator>();
        }
        return this.authenticator;
    }

    /**
     * Gets the value of the participant property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the participant property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParticipant().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RCMRMT000002UV02Participant1 }
     * 
     * 
     */
    public List<RCMRMT000002UV02Participant1> getParticipant() {
        if (participant == null) {
            participant = new ArrayList<RCMRMT000002UV02Participant1>();
        }
        return this.participant;
    }

    /**
     * Gets the value of the inFulfillmentOf property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the inFulfillmentOf property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInFulfillmentOf().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RCMRMT000002UV02InFulfillmentOf }
     * 
     * 
     */
    public List<RCMRMT000002UV02InFulfillmentOf> getInFulfillmentOf() {
        if (inFulfillmentOf == null) {
            inFulfillmentOf = new ArrayList<RCMRMT000002UV02InFulfillmentOf>();
        }
        return this.inFulfillmentOf;
    }

    /**
     * Gets the value of the documentationOf property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the documentationOf property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDocumentationOf().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RCMRMT000002UV02DocumentationOf }
     * 
     * 
     */
    public List<RCMRMT000002UV02DocumentationOf> getDocumentationOf() {
        if (documentationOf == null) {
            documentationOf = new ArrayList<RCMRMT000002UV02DocumentationOf>();
        }
        return this.documentationOf;
    }

    /**
     * Gets the value of the relatedDocument property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the relatedDocument property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRelatedDocument().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RCMRMT000002UV02RelatedDocument }
     * 
     * 
     */
    public List<RCMRMT000002UV02RelatedDocument> getRelatedDocument() {
        if (relatedDocument == null) {
            relatedDocument = new ArrayList<RCMRMT000002UV02RelatedDocument>();
        }
        return this.relatedDocument;
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
     * {@link RCMRMT000002UV02Authorization }
     * 
     * 
     */
    public List<RCMRMT000002UV02Authorization> getAuthorization() {
        if (authorization == null) {
            authorization = new ArrayList<RCMRMT000002UV02Authorization>();
        }
        return this.authorization;
    }

    /**
     * Gets the value of the componentOf property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link RCMRMT000002UV02Component1 }{@code >}
     *     
     */
    public JAXBElement<RCMRMT000002UV02Component1> getComponentOf() {
        return componentOf;
    }

    /**
     * Sets the value of the componentOf property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link RCMRMT000002UV02Component1 }{@code >}
     *     
     */
    public void setComponentOf(JAXBElement<RCMRMT000002UV02Component1> value) {
        this.componentOf = value;
    }

    /**
     * Gets the value of the classCode property.
     * 
     * @return
     *     possible object is
     *     {@link ActClassClinicalDocument }
     *     
     */
    public ActClassClinicalDocument getClassCode() {
        return classCode;
    }

    /**
     * Sets the value of the classCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActClassClinicalDocument }
     *     
     */
    public void setClassCode(ActClassClinicalDocument value) {
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
