//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.05.16 at 03:07:22 PM MSD 
//


package org.hl7.v3;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for POCD_MT000040.Organizer complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="POCD_MT000040.Organizer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="realmCode" type="{urn:hl7-org:v3}CS" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="typeId" type="{urn:hl7-org:v3}POCD_MT000040.InfrastructureRoot.typeId" minOccurs="0"/>
 *         &lt;element name="templateId" type="{urn:hl7-org:v3}II" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="id" type="{urn:hl7-org:v3}II" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="code" type="{urn:hl7-org:v3}CD" minOccurs="0"/>
 *         &lt;element name="statusCode" type="{urn:hl7-org:v3}CS"/>
 *         &lt;element name="effectiveTime" type="{urn:hl7-org:v3}IVL_TS" minOccurs="0"/>
 *         &lt;element name="subject" type="{urn:hl7-org:v3}POCD_MT000040.Subject" minOccurs="0"/>
 *         &lt;element name="specimen" type="{urn:hl7-org:v3}POCD_MT000040.Specimen" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="performer" type="{urn:hl7-org:v3}POCD_MT000040.Performer2" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="author" type="{urn:hl7-org:v3}POCD_MT000040.Author" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="informant" type="{urn:hl7-org:v3}POCD_MT000040.Informant12" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="participant" type="{urn:hl7-org:v3}POCD_MT000040.Participant2" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="reference" type="{urn:hl7-org:v3}POCD_MT000040.Reference" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="precondition" type="{urn:hl7-org:v3}POCD_MT000040.Precondition" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="component" type="{urn:hl7-org:v3}POCD_MT000040.Component4" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="nullFlavor" type="{urn:hl7-org:v3}NullFlavor" />
 *       &lt;attribute name="classCode" use="required" type="{urn:hl7-org:v3}x_ActClassDocumentEntryOrganizer" />
 *       &lt;attribute name="moodCode" use="required" type="{urn:hl7-org:v3}ActMood" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "POCD_MT000040.Organizer", propOrder = {
        "realmCode",
        "typeId",
        "templateId",
        "id",
        "code",
        "statusCode",
        "effectiveTime",
        "subject",
        "specimen",
        "performer",
        "author",
        "informant",
        "participant",
        "reference",
        "precondition",
        "component"
})
public class PocdOrganizer {

    protected List<CS> realmCode;
    protected PocdInfrastructureRootTypeId typeId;
    protected List<II> templateId;
    protected List<II> id;
    protected CD code;
    @XmlElement(required = true)
    protected CS statusCode;
    protected IVLTS effectiveTime;
    protected PocdSubject subject;
    protected List<PocdSpecimen> specimen;
    protected List<PocdPerformer2> performer;
    protected List<PocdAuthor> author;
    protected List<PocdInformant12> informant;
    protected List<PocdParticipant2> participant;
    protected List<PocdReference> reference;
    protected List<PocdPrecondition> precondition;
    protected List<PocdComponent4> component;
    @XmlAttribute
    protected List<String> nullFlavor;
    @XmlAttribute(required = true)
    protected XActClassDocumentEntryOrganizer classCode;
    @XmlAttribute(required = true)
    protected List<String> moodCode;

    /**
     * Gets the value of the realmCode property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the realmCode property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRealmCode().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link CS }
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
     * @return possible object is
     *         {@link PocdInfrastructureRootTypeId }
     */
    public PocdInfrastructureRootTypeId getTypeId() {
        return typeId;
    }

    /**
     * Sets the value of the typeId property.
     *
     * @param value allowed object is
     *              {@link PocdInfrastructureRootTypeId }
     */
    public void setTypeId(PocdInfrastructureRootTypeId value) {
        this.typeId = value;
    }

    /**
     * Gets the value of the templateId property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the templateId property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTemplateId().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link II }
     */
    public List<II> getTemplateId() {
        if (templateId == null) {
            templateId = new ArrayList<II>();
        }
        return this.templateId;
    }

    /**
     * Gets the value of the id property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the id property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getId().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link II }
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
     * @return possible object is
     *         {@link CD }
     */
    public CD getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     *
     * @param value allowed object is
     *              {@link CD }
     */
    public void setCode(CD value) {
        this.code = value;
    }

    /**
     * Gets the value of the statusCode property.
     *
     * @return possible object is
     *         {@link CS }
     */
    public CS getStatusCode() {
        return statusCode;
    }

    /**
     * Sets the value of the statusCode property.
     *
     * @param value allowed object is
     *              {@link CS }
     */
    public void setStatusCode(CS value) {
        this.statusCode = value;
    }

    /**
     * Gets the value of the effectiveTime property.
     *
     * @return possible object is
     *         {@link IVLTS }
     */
    public IVLTS getEffectiveTime() {
        return effectiveTime;
    }

    /**
     * Sets the value of the effectiveTime property.
     *
     * @param value allowed object is
     *              {@link IVLTS }
     */
    public void setEffectiveTime(IVLTS value) {
        this.effectiveTime = value;
    }

    /**
     * Gets the value of the subject property.
     *
     * @return possible object is
     *         {@link PocdSubject }
     */
    public PocdSubject getSubject() {
        return subject;
    }

    /**
     * Sets the value of the subject property.
     *
     * @param value allowed object is
     *              {@link PocdSubject }
     */
    public void setSubject(PocdSubject value) {
        this.subject = value;
    }

    /**
     * Gets the value of the specimen property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the specimen property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSpecimen().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link PocdSpecimen }
     */
    public List<PocdSpecimen> getSpecimen() {
        if (specimen == null) {
            specimen = new ArrayList<PocdSpecimen>();
        }
        return this.specimen;
    }

    /**
     * Gets the value of the performer property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the performer property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPerformer().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link PocdPerformer2 }
     */
    public List<PocdPerformer2> getPerformer() {
        if (performer == null) {
            performer = new ArrayList<PocdPerformer2>();
        }
        return this.performer;
    }

    /**
     * Gets the value of the author property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the author property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAuthor().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link PocdAuthor }
     */
    public List<PocdAuthor> getAuthor() {
        if (author == null) {
            author = new ArrayList<PocdAuthor>();
        }
        return this.author;
    }

    /**
     * Gets the value of the informant property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the informant property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInformant().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link PocdInformant12 }
     */
    public List<PocdInformant12> getInformant() {
        if (informant == null) {
            informant = new ArrayList<PocdInformant12>();
        }
        return this.informant;
    }

    /**
     * Gets the value of the participant property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the participant property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParticipant().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link PocdParticipant2 }
     */
    public List<PocdParticipant2> getParticipant() {
        if (participant == null) {
            participant = new ArrayList<PocdParticipant2>();
        }
        return this.participant;
    }

    /**
     * Gets the value of the reference property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the reference property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReference().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link PocdReference }
     */
    public List<PocdReference> getReference() {
        if (reference == null) {
            reference = new ArrayList<PocdReference>();
        }
        return this.reference;
    }

    /**
     * Gets the value of the precondition property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the precondition property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPrecondition().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link PocdPrecondition }
     */
    public List<PocdPrecondition> getPrecondition() {
        if (precondition == null) {
            precondition = new ArrayList<PocdPrecondition>();
        }
        return this.precondition;
    }

    /**
     * Gets the value of the component property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the component property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getComponent().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link PocdComponent4 }
     */
    public List<PocdComponent4> getComponent() {
        if (component == null) {
            component = new ArrayList<PocdComponent4>();
        }
        return this.component;
    }

    /**
     * Gets the value of the nullFlavor property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nullFlavor property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNullFlavor().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     */
    public List<String> getNullFlavor() {
        if (nullFlavor == null) {
            nullFlavor = new ArrayList<String>();
        }
        return this.nullFlavor;
    }

    /**
     * Gets the value of the classCode property.
     *
     * @return possible object is
     *         {@link XActClassDocumentEntryOrganizer }
     */
    public XActClassDocumentEntryOrganizer getClassCode() {
        return classCode;
    }

    /**
     * Sets the value of the classCode property.
     *
     * @param value allowed object is
     *              {@link XActClassDocumentEntryOrganizer }
     */
    public void setClassCode(XActClassDocumentEntryOrganizer value) {
        this.classCode = value;
    }

    /**
     * Gets the value of the moodCode property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the moodCode property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMoodCode().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     */
    public List<String> getMoodCode() {
        if (moodCode == null) {
            moodCode = new ArrayList<String>();
        }
        return this.moodCode;
    }

}
