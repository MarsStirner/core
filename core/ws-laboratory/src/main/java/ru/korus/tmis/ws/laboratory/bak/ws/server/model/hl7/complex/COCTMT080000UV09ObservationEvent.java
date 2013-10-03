
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
 * <p>Java class for COCT_MT080000UV09.ObservationEvent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT080000UV09.ObservationEvent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="id" type="{urn:hl7-org:v3}DSET_II" minOccurs="0"/>
 *         &lt;element name="code" type="{urn:hl7-org:v3}CD"/>
 *         &lt;element name="text" type="{urn:hl7-org:v3}ED" minOccurs="0"/>
 *         &lt;element name="statusCode" type="{urn:hl7-org:v3}CS" minOccurs="0"/>
 *         &lt;element name="effectiveTime" type="{urn:hl7-org:v3}TS" minOccurs="0"/>
 *         &lt;element name="value" type="{urn:hl7-org:v3}ANY"/>
 *         &lt;element name="subject" type="{urn:hl7-org:v3}COCT_MT080000UV09.Subject1" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="device" type="{urn:hl7-org:v3}COCT_MT080000UV09.Device" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="consumable" type="{urn:hl7-org:v3}COCT_MT080000UV09.Consumable" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="performer" type="{urn:hl7-org:v3}COCT_MT080000UV09.Performer" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="author" type="{urn:hl7-org:v3}COCT_MT080000UV09.Author" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="transcriber" type="{urn:hl7-org:v3}COCT_MT080000UV09.Transcriber" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="verifier" type="{urn:hl7-org:v3}COCT_MT080000UV09.Verifier" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="definition" type="{urn:hl7-org:v3}COCT_MT080000UV09.Definition" minOccurs="0"/>
 *         &lt;element name="precondition" type="{urn:hl7-org:v3}COCT_MT080000UV09.Precondition" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="pertinentInformation" type="{urn:hl7-org:v3}COCT_MT080000UV09.PertinentInformation" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="controlVariable" type="{urn:hl7-org:v3}COCT_MT080000UV09.ControlVariable" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="subjectOf1" type="{urn:hl7-org:v3}COCT_MT080000UV09.Subject4" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="subjectOf2" type="{urn:hl7-org:v3}COCT_MT080000UV09.Subject5" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:hl7-org:v3}InfrastructureRootAttributes"/>
 *       &lt;attribute name="nullFlavor" type="{urn:hl7-org:v3}NullFlavor" />
 *       &lt;attribute name="classCode" use="required" type="{urn:hl7-org:v3}ActClassObservation" />
 *       &lt;attribute name="moodCode" use="required" type="{urn:hl7-org:v3}ActMoodEventOccurrence" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "COCT_MT080000UV09.ObservationEvent", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "id",
    "code",
    "text",
    "statusCode",
    "effectiveTime",
    "value",
    "subject",
    "device",
    "consumable",
    "performer",
    "author",
    "transcriber",
    "verifier",
    "definition",
    "precondition",
    "pertinentInformation",
    "controlVariable",
    "subjectOf1",
    "subjectOf2"
})
public class COCTMT080000UV09ObservationEvent {

    protected DSETCS realmCode;
    protected II typeId;
    protected LISTII templateId;
    protected DSETII id;
    @XmlElement(required = true)
    protected CD code;
    protected ED text;
    protected CS statusCode;
    protected TS effectiveTime;
    @XmlElement(required = true)
    protected ANY value;
    @XmlElement(nillable = true)
    protected List<COCTMT080000UV09Subject1> subject;
    @XmlElement(nillable = true)
    protected List<COCTMT080000UV09Device> device;
    @XmlElement(nillable = true)
    protected List<COCTMT080000UV09Consumable> consumable;
    @XmlElement(nillable = true)
    protected List<COCTMT080000UV09Performer> performer;
    @XmlElement(nillable = true)
    protected List<COCTMT080000UV09Author> author;
    @XmlElement(nillable = true)
    protected List<COCTMT080000UV09Transcriber> transcriber;
    @XmlElement(nillable = true)
    protected List<COCTMT080000UV09Verifier> verifier;
    @XmlElementRef(name = "definition", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT080000UV09Definition> definition;
    @XmlElement(nillable = true)
    protected List<COCTMT080000UV09Precondition> precondition;
    @XmlElement(nillable = true)
    protected List<COCTMT080000UV09PertinentInformation> pertinentInformation;
    @XmlElement(nillable = true)
    protected List<COCTMT080000UV09ControlVariable> controlVariable;
    @XmlElement(nillable = true)
    protected List<COCTMT080000UV09Subject4> subjectOf1;
    @XmlElementRef(name = "subjectOf2", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT080000UV09Subject5> subjectOf2;
    @XmlAttribute(name = "nullFlavor")
    protected NullFlavor nullFlavor;
    @XmlAttribute(name = "classCode", required = true)
    protected ActClassObservation classCode;
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
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link ANY }
     *     
     */
    public ANY getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link ANY }
     *     
     */
    public void setValue(ANY value) {
        this.value = value;
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
     * {@link COCTMT080000UV09Subject1 }
     * 
     * 
     */
    public List<COCTMT080000UV09Subject1> getSubject() {
        if (subject == null) {
            subject = new ArrayList<COCTMT080000UV09Subject1>();
        }
        return this.subject;
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
     * {@link COCTMT080000UV09Device }
     * 
     * 
     */
    public List<COCTMT080000UV09Device> getDevice() {
        if (device == null) {
            device = new ArrayList<COCTMT080000UV09Device>();
        }
        return this.device;
    }

    /**
     * Gets the value of the consumable property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the consumable property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConsumable().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT080000UV09Consumable }
     * 
     * 
     */
    public List<COCTMT080000UV09Consumable> getConsumable() {
        if (consumable == null) {
            consumable = new ArrayList<COCTMT080000UV09Consumable>();
        }
        return this.consumable;
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
     * {@link COCTMT080000UV09Performer }
     * 
     * 
     */
    public List<COCTMT080000UV09Performer> getPerformer() {
        if (performer == null) {
            performer = new ArrayList<COCTMT080000UV09Performer>();
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
     * {@link COCTMT080000UV09Author }
     * 
     * 
     */
    public List<COCTMT080000UV09Author> getAuthor() {
        if (author == null) {
            author = new ArrayList<COCTMT080000UV09Author>();
        }
        return this.author;
    }

    /**
     * Gets the value of the transcriber property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the transcriber property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTranscriber().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT080000UV09Transcriber }
     * 
     * 
     */
    public List<COCTMT080000UV09Transcriber> getTranscriber() {
        if (transcriber == null) {
            transcriber = new ArrayList<COCTMT080000UV09Transcriber>();
        }
        return this.transcriber;
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
     * {@link COCTMT080000UV09Verifier }
     * 
     * 
     */
    public List<COCTMT080000UV09Verifier> getVerifier() {
        if (verifier == null) {
            verifier = new ArrayList<COCTMT080000UV09Verifier>();
        }
        return this.verifier;
    }

    /**
     * Gets the value of the definition property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT080000UV09Definition }{@code >}
     *     
     */
    public JAXBElement<COCTMT080000UV09Definition> getDefinition() {
        return definition;
    }

    /**
     * Sets the value of the definition property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT080000UV09Definition }{@code >}
     *     
     */
    public void setDefinition(JAXBElement<COCTMT080000UV09Definition> value) {
        this.definition = value;
    }

    /**
     * Gets the value of the precondition property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the precondition property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPrecondition().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT080000UV09Precondition }
     * 
     * 
     */
    public List<COCTMT080000UV09Precondition> getPrecondition() {
        if (precondition == null) {
            precondition = new ArrayList<COCTMT080000UV09Precondition>();
        }
        return this.precondition;
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
     * {@link COCTMT080000UV09PertinentInformation }
     * 
     * 
     */
    public List<COCTMT080000UV09PertinentInformation> getPertinentInformation() {
        if (pertinentInformation == null) {
            pertinentInformation = new ArrayList<COCTMT080000UV09PertinentInformation>();
        }
        return this.pertinentInformation;
    }

    /**
     * Gets the value of the controlVariable property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the controlVariable property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getControlVariable().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT080000UV09ControlVariable }
     * 
     * 
     */
    public List<COCTMT080000UV09ControlVariable> getControlVariable() {
        if (controlVariable == null) {
            controlVariable = new ArrayList<COCTMT080000UV09ControlVariable>();
        }
        return this.controlVariable;
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
     * {@link COCTMT080000UV09Subject4 }
     * 
     * 
     */
    public List<COCTMT080000UV09Subject4> getSubjectOf1() {
        if (subjectOf1 == null) {
            subjectOf1 = new ArrayList<COCTMT080000UV09Subject4>();
        }
        return this.subjectOf1;
    }

    /**
     * Gets the value of the subjectOf2 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT080000UV09Subject5 }{@code >}
     *     
     */
    public JAXBElement<COCTMT080000UV09Subject5> getSubjectOf2() {
        return subjectOf2;
    }

    /**
     * Sets the value of the subjectOf2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT080000UV09Subject5 }{@code >}
     *     
     */
    public void setSubjectOf2(JAXBElement<COCTMT080000UV09Subject5> value) {
        this.subjectOf2 = value;
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
     *     {@link ActClassObservation }
     *     
     */
    public ActClassObservation getClassCode() {
        return classCode;
    }

    /**
     * Sets the value of the classCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActClassObservation }
     *     
     */
    public void setClassCode(ActClassObservation value) {
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
