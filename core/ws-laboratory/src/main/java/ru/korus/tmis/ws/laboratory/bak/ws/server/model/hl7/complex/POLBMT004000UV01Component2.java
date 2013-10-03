
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for POLB_MT004000UV01.Component2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="POLB_MT004000UV01.Component2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="sequenceNumber" type="{urn:hl7-org:v3}INT" minOccurs="0"/>
 *         &lt;element name="priorityNumber" type="{urn:hl7-org:v3}INT" minOccurs="0"/>
 *         &lt;element name="seperatableInd" type="{urn:hl7-org:v3}BL" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element name="observationReport" type="{urn:hl7-org:v3}POLB_MT004000UV01.ObservationReport"/>
 *           &lt;element name="observationBattery" type="{urn:hl7-org:v3}POLB_MT004000UV01.ObservationBattery"/>
 *           &lt;element name="specimenObservationCluster" type="{urn:hl7-org:v3}POLB_MT004000UV01.SpecimenObservationCluster"/>
 *           &lt;element name="observationEvent" type="{urn:hl7-org:v3}POLB_MT004000UV01.ObservationEvent"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:hl7-org:v3}InfrastructureRootAttributes"/>
 *       &lt;attribute name="nullFlavor" type="{urn:hl7-org:v3}NullFlavor" />
 *       &lt;attribute name="typeCode" use="required" type="{urn:hl7-org:v3}ActRelationshipHasComponent" />
 *       &lt;attribute name="contextControlCode" use="required" type="{urn:hl7-org:v3}ContextControl" />
 *       &lt;attribute name="contextConductionInd" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "POLB_MT004000UV01.Component2", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "sequenceNumber",
    "priorityNumber",
    "seperatableInd",
    "observationReport",
    "observationBattery",
    "specimenObservationCluster",
    "observationEvent"
})
public class POLBMT004000UV01Component2 {

    protected DSETCS realmCode;
    protected II typeId;
    protected LISTII templateId;
    protected INT sequenceNumber;
    protected INT priorityNumber;
    protected BL seperatableInd;
    @XmlElementRef(name = "observationReport", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<POLBMT004000UV01ObservationReport> observationReport;
    @XmlElementRef(name = "observationBattery", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<POLBMT004000UV01ObservationBattery> observationBattery;
    @XmlElementRef(name = "specimenObservationCluster", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<POLBMT004000UV01SpecimenObservationCluster> specimenObservationCluster;
    @XmlElementRef(name = "observationEvent", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<POLBMT004000UV01ObservationEvent> observationEvent;
    @XmlAttribute(name = "nullFlavor")
    protected NullFlavor nullFlavor;
    @XmlAttribute(name = "typeCode", required = true)
    protected ActRelationshipHasComponent typeCode;
    @XmlAttribute(name = "contextControlCode", required = true)
    protected ContextControl contextControlCode;
    @XmlAttribute(name = "contextConductionInd")
    protected Boolean contextConductionInd;

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
     * Gets the value of the sequenceNumber property.
     * 
     * @return
     *     possible object is
     *     {@link INT }
     *     
     */
    public INT getSequenceNumber() {
        return sequenceNumber;
    }

    /**
     * Sets the value of the sequenceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link INT }
     *     
     */
    public void setSequenceNumber(INT value) {
        this.sequenceNumber = value;
    }

    /**
     * Gets the value of the priorityNumber property.
     * 
     * @return
     *     possible object is
     *     {@link INT }
     *     
     */
    public INT getPriorityNumber() {
        return priorityNumber;
    }

    /**
     * Sets the value of the priorityNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link INT }
     *     
     */
    public void setPriorityNumber(INT value) {
        this.priorityNumber = value;
    }

    /**
     * Gets the value of the seperatableInd property.
     * 
     * @return
     *     possible object is
     *     {@link BL }
     *     
     */
    public BL getSeperatableInd() {
        return seperatableInd;
    }

    /**
     * Sets the value of the seperatableInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link BL }
     *     
     */
    public void setSeperatableInd(BL value) {
        this.seperatableInd = value;
    }

    /**
     * Gets the value of the observationReport property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link POLBMT004000UV01ObservationReport }{@code >}
     *     
     */
    public JAXBElement<POLBMT004000UV01ObservationReport> getObservationReport() {
        return observationReport;
    }

    /**
     * Sets the value of the observationReport property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link POLBMT004000UV01ObservationReport }{@code >}
     *     
     */
    public void setObservationReport(JAXBElement<POLBMT004000UV01ObservationReport> value) {
        this.observationReport = value;
    }

    /**
     * Gets the value of the observationBattery property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link POLBMT004000UV01ObservationBattery }{@code >}
     *     
     */
    public JAXBElement<POLBMT004000UV01ObservationBattery> getObservationBattery() {
        return observationBattery;
    }

    /**
     * Sets the value of the observationBattery property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link POLBMT004000UV01ObservationBattery }{@code >}
     *     
     */
    public void setObservationBattery(JAXBElement<POLBMT004000UV01ObservationBattery> value) {
        this.observationBattery = value;
    }

    /**
     * Gets the value of the specimenObservationCluster property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link POLBMT004000UV01SpecimenObservationCluster }{@code >}
     *     
     */
    public JAXBElement<POLBMT004000UV01SpecimenObservationCluster> getSpecimenObservationCluster() {
        return specimenObservationCluster;
    }

    /**
     * Sets the value of the specimenObservationCluster property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link POLBMT004000UV01SpecimenObservationCluster }{@code >}
     *     
     */
    public void setSpecimenObservationCluster(JAXBElement<POLBMT004000UV01SpecimenObservationCluster> value) {
        this.specimenObservationCluster = value;
    }

    /**
     * Gets the value of the observationEvent property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link POLBMT004000UV01ObservationEvent }{@code >}
     *     
     */
    public JAXBElement<POLBMT004000UV01ObservationEvent> getObservationEvent() {
        return observationEvent;
    }

    /**
     * Sets the value of the observationEvent property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link POLBMT004000UV01ObservationEvent }{@code >}
     *     
     */
    public void setObservationEvent(JAXBElement<POLBMT004000UV01ObservationEvent> value) {
        this.observationEvent = value;
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
     * Gets the value of the typeCode property.
     * 
     * @return
     *     possible object is
     *     {@link ActRelationshipHasComponent }
     *     
     */
    public ActRelationshipHasComponent getTypeCode() {
        return typeCode;
    }

    /**
     * Sets the value of the typeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActRelationshipHasComponent }
     *     
     */
    public void setTypeCode(ActRelationshipHasComponent value) {
        this.typeCode = value;
    }

    /**
     * Gets the value of the contextControlCode property.
     * 
     * @return
     *     possible object is
     *     {@link ContextControl }
     *     
     */
    public ContextControl getContextControlCode() {
        return contextControlCode;
    }

    /**
     * Sets the value of the contextControlCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContextControl }
     *     
     */
    public void setContextControlCode(ContextControl value) {
        this.contextControlCode = value;
    }

    /**
     * Gets the value of the contextConductionInd property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isContextConductionInd() {
        if (contextConductionInd == null) {
            return true;
        } else {
            return contextConductionInd;
        }
    }

    /**
     * Sets the value of the contextConductionInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setContextConductionInd(Boolean value) {
        this.contextConductionInd = value;
    }

}
