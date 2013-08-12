
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
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for POLB_MT004000UV01.ObservationBattery complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="POLB_MT004000UV01.ObservationBattery">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="id" type="{urn:hl7-org:v3}II" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="code" type="{urn:hl7-org:v3}CD"/>
 *         &lt;element name="text" type="{urn:hl7-org:v3}ED" minOccurs="0"/>
 *         &lt;element name="statusCode" type="{urn:hl7-org:v3}CS"/>
 *         &lt;element name="priorityCode" type="{urn:hl7-org:v3}CE" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="confidentialityCode" type="{urn:hl7-org:v3}CE" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="reasonCode" type="{urn:hl7-org:v3}CE" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="specimen" type="{urn:hl7-org:v3}POLB_MT004000UV01.Specimen" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="device" type="{urn:hl7-org:v3}POLB_MT004000UV01.Device" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="consumable1" type="{urn:hl7-org:v3}POLB_MT004000UV01.Consumable2" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="consumable2" type="{urn:hl7-org:v3}POLB_MT004000UV01.Consumable" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="recordTarget" type="{urn:hl7-org:v3}POLB_MT004000UV01.RecordTarget" minOccurs="0"/>
 *         &lt;element name="performer" type="{urn:hl7-org:v3}POLB_MT004000UV01.Performer" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="author" type="{urn:hl7-org:v3}POLB_MT004000UV01.Author1" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="transcriber" type="{urn:hl7-org:v3}POLB_MT004000UV01.DataEnterer1" minOccurs="0"/>
 *         &lt;element name="informationRecipient" type="{urn:hl7-org:v3}POLB_MT004000UV01.InformationRecipient1" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="verifier" type="{urn:hl7-org:v3}POLB_MT004000UV01.Verifier" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="inFulfillmentOf" type="{urn:hl7-org:v3}POLB_MT004000UV01.InFulfillmentOf2" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="definition" type="{urn:hl7-org:v3}POLB_MT004000UV01.Definition" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="reason" type="{urn:hl7-org:v3}POLB_MT004000UV01.Reason" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="pertinentInformation" type="{urn:hl7-org:v3}POLB_MT004000UV01.PertinentInformation" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="component1" type="{urn:hl7-org:v3}POLB_MT004000UV01.Component2" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="component2" type="{urn:hl7-org:v3}POLB_MT004000UV01.Component3" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="subjectOf1" type="{urn:hl7-org:v3}POLB_MT004000UV01.Subject1" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="subjectOf2" type="{urn:hl7-org:v3}POLB_MT004000UV01.Subject2" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="componentOf" type="{urn:hl7-org:v3}POLB_MT004000UV01.Component1" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:hl7-org:v3}InfrastructureRootAttributes"/>
 *       &lt;attribute name="classCode" use="required" type="{urn:hl7-org:v3}ActClassBattery" />
 *       &lt;attribute name="moodCode" use="required" type="{urn:hl7-org:v3}ActMoodEventOccurrence" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "POLB_MT004000UV01.ObservationBattery", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "id",
    "code",
    "text",
    "statusCode",
    "priorityCode",
    "confidentialityCode",
    "reasonCode",
    "specimen",
    "device",
    "consumable1",
    "consumable2",
    "recordTarget",
    "performer",
    "author",
    "transcriber",
    "informationRecipient",
    "verifier",
    "inFulfillmentOf",
    "definition",
    "reason",
    "pertinentInformation",
    "component1",
    "component2",
    "subjectOf1",
    "subjectOf2",
    "componentOf"
})
public class POLBMT004000UV01ObservationBattery {

    protected List<CS> realmCode;
    protected II typeId;
    protected List<II> templateId;
    protected List<II> id;
    @XmlElement(required = true)
    protected CD code;
    protected ED text;
    @XmlElement(required = true)
    protected CS statusCode;
    protected List<CE> priorityCode;
    protected List<CE> confidentialityCode;
    protected List<CE> reasonCode;
    @XmlElement(nillable = true)
    protected List<POLBMT004000UV01Specimen> specimen;
    @XmlElement(nillable = true)
    protected List<POLBMT004000UV01Device> device;
    @XmlElement(nillable = true)
    protected List<POLBMT004000UV01Consumable2> consumable1;
    @XmlElement(nillable = true)
    protected List<POLBMT004000UV01Consumable> consumable2;
    @XmlElementRef(name = "recordTarget", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<POLBMT004000UV01RecordTarget> recordTarget;
    @XmlElement(nillable = true)
    protected List<POLBMT004000UV01Performer> performer;
    @XmlElement(nillable = true)
    protected List<POLBMT004000UV01Author1> author;
    @XmlElementRef(name = "transcriber", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<POLBMT004000UV01DataEnterer1> transcriber;
    @XmlElement(nillable = true)
    protected List<POLBMT004000UV01InformationRecipient1> informationRecipient;
    @XmlElement(nillable = true)
    protected List<POLBMT004000UV01Verifier> verifier;
    @XmlElement(nillable = true)
    protected List<POLBMT004000UV01InFulfillmentOf2> inFulfillmentOf;
    @XmlElement(nillable = true)
    protected List<POLBMT004000UV01Definition> definition;
    @XmlElement(nillable = true)
    protected List<POLBMT004000UV01Reason> reason;
    @XmlElement(nillable = true)
    protected List<POLBMT004000UV01PertinentInformation> pertinentInformation;
    @XmlElement(nillable = true)
    protected List<POLBMT004000UV01Component2> component1;
    @XmlElement(nillable = true)
    protected List<POLBMT004000UV01Component3> component2;
    @XmlElement(nillable = true)
    protected List<POLBMT004000UV01Subject1> subjectOf1;
    @XmlElement(nillable = true)
    protected List<POLBMT004000UV01Subject2> subjectOf2;
    @XmlElementRef(name = "componentOf", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<POLBMT004000UV01Component1> componentOf;
    @XmlAttribute(name = "classCode", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String classCode;
    @XmlAttribute(name = "moodCode", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String moodCode;

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
     * Gets the value of the priorityCode property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the priorityCode property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPriorityCode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CE }
     * 
     * 
     */
    public List<CE> getPriorityCode() {
        if (priorityCode == null) {
            priorityCode = new ArrayList<CE>();
        }
        return this.priorityCode;
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
     * Gets the value of the specimen property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the specimen property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSpecimen().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link POLBMT004000UV01Specimen }
     * 
     * 
     */
    public List<POLBMT004000UV01Specimen> getSpecimen() {
        if (specimen == null) {
            specimen = new ArrayList<POLBMT004000UV01Specimen>();
        }
        return this.specimen;
    }

    /**
     * Gets the value of the device property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the device property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDevice().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link POLBMT004000UV01Device }
     * 
     * 
     */
    public List<POLBMT004000UV01Device> getDevice() {
        if (device == null) {
            device = new ArrayList<POLBMT004000UV01Device>();
        }
        return this.device;
    }

    /**
     * Gets the value of the consumable1 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the consumable1 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConsumable1().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link POLBMT004000UV01Consumable2 }
     * 
     * 
     */
    public List<POLBMT004000UV01Consumable2> getConsumable1() {
        if (consumable1 == null) {
            consumable1 = new ArrayList<POLBMT004000UV01Consumable2>();
        }
        return this.consumable1;
    }

    /**
     * Gets the value of the consumable2 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the consumable2 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConsumable2().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link POLBMT004000UV01Consumable }
     * 
     * 
     */
    public List<POLBMT004000UV01Consumable> getConsumable2() {
        if (consumable2 == null) {
            consumable2 = new ArrayList<POLBMT004000UV01Consumable>();
        }
        return this.consumable2;
    }

    /**
     * Gets the value of the recordTarget property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link POLBMT004000UV01RecordTarget }{@code >}
     *     
     */
    public JAXBElement<POLBMT004000UV01RecordTarget> getRecordTarget() {
        return recordTarget;
    }

    /**
     * Sets the value of the recordTarget property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link POLBMT004000UV01RecordTarget }{@code >}
     *     
     */
    public void setRecordTarget(JAXBElement<POLBMT004000UV01RecordTarget> value) {
        this.recordTarget = value;
    }

    /**
     * Gets the value of the performer property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the performer property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPerformer().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link POLBMT004000UV01Performer }
     * 
     * 
     */
    public List<POLBMT004000UV01Performer> getPerformer() {
        if (performer == null) {
            performer = new ArrayList<POLBMT004000UV01Performer>();
        }
        return this.performer;
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
     * {@link POLBMT004000UV01Author1 }
     * 
     * 
     */
    public List<POLBMT004000UV01Author1> getAuthor() {
        if (author == null) {
            author = new ArrayList<POLBMT004000UV01Author1>();
        }
        return this.author;
    }

    /**
     * Gets the value of the transcriber property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link POLBMT004000UV01DataEnterer1 }{@code >}
     *     
     */
    public JAXBElement<POLBMT004000UV01DataEnterer1> getTranscriber() {
        return transcriber;
    }

    /**
     * Sets the value of the transcriber property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link POLBMT004000UV01DataEnterer1 }{@code >}
     *     
     */
    public void setTranscriber(JAXBElement<POLBMT004000UV01DataEnterer1> value) {
        this.transcriber = value;
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
     * {@link POLBMT004000UV01InformationRecipient1 }
     * 
     * 
     */
    public List<POLBMT004000UV01InformationRecipient1> getInformationRecipient() {
        if (informationRecipient == null) {
            informationRecipient = new ArrayList<POLBMT004000UV01InformationRecipient1>();
        }
        return this.informationRecipient;
    }

    /**
     * Gets the value of the verifier property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the verifier property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVerifier().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link POLBMT004000UV01Verifier }
     * 
     * 
     */
    public List<POLBMT004000UV01Verifier> getVerifier() {
        if (verifier == null) {
            verifier = new ArrayList<POLBMT004000UV01Verifier>();
        }
        return this.verifier;
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
     * {@link POLBMT004000UV01InFulfillmentOf2 }
     * 
     * 
     */
    public List<POLBMT004000UV01InFulfillmentOf2> getInFulfillmentOf() {
        if (inFulfillmentOf == null) {
            inFulfillmentOf = new ArrayList<POLBMT004000UV01InFulfillmentOf2>();
        }
        return this.inFulfillmentOf;
    }

    /**
     * Gets the value of the definition property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the definition property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDefinition().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link POLBMT004000UV01Definition }
     * 
     * 
     */
    public List<POLBMT004000UV01Definition> getDefinition() {
        if (definition == null) {
            definition = new ArrayList<POLBMT004000UV01Definition>();
        }
        return this.definition;
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
     * {@link POLBMT004000UV01Reason }
     * 
     * 
     */
    public List<POLBMT004000UV01Reason> getReason() {
        if (reason == null) {
            reason = new ArrayList<POLBMT004000UV01Reason>();
        }
        return this.reason;
    }

    /**
     * Gets the value of the pertinentInformation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pertinentInformation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPertinentInformation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link POLBMT004000UV01PertinentInformation }
     * 
     * 
     */
    public List<POLBMT004000UV01PertinentInformation> getPertinentInformation() {
        if (pertinentInformation == null) {
            pertinentInformation = new ArrayList<POLBMT004000UV01PertinentInformation>();
        }
        return this.pertinentInformation;
    }

    /**
     * Gets the value of the component1 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the component1 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getComponent1().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link POLBMT004000UV01Component2 }
     * 
     * 
     */
    public List<POLBMT004000UV01Component2> getComponent1() {
        if (component1 == null) {
            component1 = new ArrayList<POLBMT004000UV01Component2>();
        }
        return this.component1;
    }

    /**
     * Gets the value of the component2 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the component2 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getComponent2().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link POLBMT004000UV01Component3 }
     * 
     * 
     */
    public List<POLBMT004000UV01Component3> getComponent2() {
        if (component2 == null) {
            component2 = new ArrayList<POLBMT004000UV01Component3>();
        }
        return this.component2;
    }

    /**
     * Gets the value of the subjectOf1 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subjectOf1 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubjectOf1().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link POLBMT004000UV01Subject1 }
     * 
     * 
     */
    public List<POLBMT004000UV01Subject1> getSubjectOf1() {
        if (subjectOf1 == null) {
            subjectOf1 = new ArrayList<POLBMT004000UV01Subject1>();
        }
        return this.subjectOf1;
    }

    /**
     * Gets the value of the subjectOf2 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subjectOf2 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubjectOf2().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link POLBMT004000UV01Subject2 }
     * 
     * 
     */
    public List<POLBMT004000UV01Subject2> getSubjectOf2() {
        if (subjectOf2 == null) {
            subjectOf2 = new ArrayList<POLBMT004000UV01Subject2>();
        }
        return this.subjectOf2;
    }

    /**
     * Gets the value of the componentOf property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link POLBMT004000UV01Component1 }{@code >}
     *     
     */
    public JAXBElement<POLBMT004000UV01Component1> getComponentOf() {
        return componentOf;
    }

    /**
     * Sets the value of the componentOf property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link POLBMT004000UV01Component1 }{@code >}
     *     
     */
    public void setComponentOf(JAXBElement<POLBMT004000UV01Component1> value) {
        this.componentOf = value;
    }

    /**
     * Gets the value of the classCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassCode() {
        return classCode;
    }

    /**
     * Sets the value of the classCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassCode(String value) {
        this.classCode = value;
    }

    /**
     * Gets the value of the moodCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMoodCode() {
        return moodCode;
    }

    /**
     * Sets the value of the moodCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMoodCode(String value) {
        this.moodCode = value;
    }

}
