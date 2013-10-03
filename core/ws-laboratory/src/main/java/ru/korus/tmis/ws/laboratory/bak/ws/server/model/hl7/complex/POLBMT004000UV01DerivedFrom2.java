
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for POLB_MT004000UV01.DerivedFrom2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="POLB_MT004000UV01.DerivedFrom2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="sequenceNumber" type="{urn:hl7-org:v3}INT" minOccurs="0"/>
 *         &lt;element name="localVariableName" type="{urn:hl7-org:v3}ST" minOccurs="0"/>
 *         &lt;element name="observationEvent" type="{urn:hl7-org:v3}POLB_MT004000UV01.ObservationEvent"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:hl7-org:v3}InfrastructureRootAttributes"/>
 *       &lt;attribute name="nullFlavor" type="{urn:hl7-org:v3}NullFlavor" />
 *       &lt;attribute name="typeCode" use="required" type="{urn:hl7-org:v3}ActRelationshipIsDerivedFrom" />
 *       &lt;attribute name="inversionInd" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
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
@XmlType(name = "POLB_MT004000UV01.DerivedFrom2", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "sequenceNumber",
    "localVariableName",
    "observationEvent"
})
public class POLBMT004000UV01DerivedFrom2 {

    protected DSETCS realmCode;
    protected II typeId;
    protected LISTII templateId;
    protected INT sequenceNumber;
    protected ST localVariableName;
    @XmlElement(required = true, nillable = true)
    protected POLBMT004000UV01ObservationEvent observationEvent;
    @XmlAttribute(name = "nullFlavor")
    protected NullFlavor nullFlavor;
    @XmlAttribute(name = "typeCode", required = true)
    protected ActRelationshipIsDerivedFrom typeCode;
    @XmlAttribute(name = "inversionInd")
    protected Boolean inversionInd;
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
     * Gets the value of the localVariableName property.
     * 
     * @return
     *     possible object is
     *     {@link ST }
     *     
     */
    public ST getLocalVariableName() {
        return localVariableName;
    }

    /**
     * Sets the value of the localVariableName property.
     * 
     * @param value
     *     allowed object is
     *     {@link ST }
     *     
     */
    public void setLocalVariableName(ST value) {
        this.localVariableName = value;
    }

    /**
     * Gets the value of the observationEvent property.
     * 
     * @return
     *     possible object is
     *     {@link POLBMT004000UV01ObservationEvent }
     *     
     */
    public POLBMT004000UV01ObservationEvent getObservationEvent() {
        return observationEvent;
    }

    /**
     * Sets the value of the observationEvent property.
     * 
     * @param value
     *     allowed object is
     *     {@link POLBMT004000UV01ObservationEvent }
     *     
     */
    public void setObservationEvent(POLBMT004000UV01ObservationEvent value) {
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
     *     {@link ActRelationshipIsDerivedFrom }
     *     
     */
    public ActRelationshipIsDerivedFrom getTypeCode() {
        return typeCode;
    }

    /**
     * Sets the value of the typeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActRelationshipIsDerivedFrom }
     *     
     */
    public void setTypeCode(ActRelationshipIsDerivedFrom value) {
        this.typeCode = value;
    }

    /**
     * Gets the value of the inversionInd property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isInversionInd() {
        if (inversionInd == null) {
            return false;
        } else {
            return inversionInd;
        }
    }

    /**
     * Sets the value of the inversionInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setInversionInd(Boolean value) {
        this.inversionInd = value;
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
