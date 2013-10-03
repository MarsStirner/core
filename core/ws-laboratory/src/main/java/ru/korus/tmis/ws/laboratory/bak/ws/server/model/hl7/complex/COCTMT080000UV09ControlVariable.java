
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for COCT_MT080000UV09.ControlVariable complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT080000UV09.ControlVariable">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="sequenceNumber" type="{urn:hl7-org:v3}INT" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element name="dillution" type="{urn:hl7-org:v3}COCT_MT210000UV02.Dillution"/>
 *           &lt;element name="endogenousContent" type="{urn:hl7-org:v3}COCT_MT210000UV02.EndogenousContent"/>
 *           &lt;element name="reflexPermission" type="{urn:hl7-org:v3}COCT_MT210000UV02.ReflexPermission"/>
 *           &lt;element name="autoRepeatPermission" type="{urn:hl7-org:v3}COCT_MT210000UV02.AutoRepeatPermission"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:hl7-org:v3}InfrastructureRootAttributes"/>
 *       &lt;attribute name="nullFlavor" type="{urn:hl7-org:v3}NullFlavor" />
 *       &lt;attribute name="typeCode" use="required" type="{urn:hl7-org:v3}ActRelationshipHasControlVariable" />
 *       &lt;attribute name="contextControlCode" type="{urn:hl7-org:v3}ContextControl" default="AN" />
 *       &lt;attribute name="contextConductionInd" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "COCT_MT080000UV09.ControlVariable", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "sequenceNumber",
    "dillution",
    "endogenousContent",
    "reflexPermission",
    "autoRepeatPermission"
})
public class COCTMT080000UV09ControlVariable {

    protected DSETCS realmCode;
    protected II typeId;
    protected LISTII templateId;
    protected INT sequenceNumber;
    @XmlElementRef(name = "dillution", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT210000UV02Dillution> dillution;
    @XmlElementRef(name = "endogenousContent", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT210000UV02EndogenousContent> endogenousContent;
    @XmlElementRef(name = "reflexPermission", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT210000UV02ReflexPermission> reflexPermission;
    @XmlElementRef(name = "autoRepeatPermission", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT210000UV02AutoRepeatPermission> autoRepeatPermission;
    @XmlAttribute(name = "nullFlavor")
    protected NullFlavor nullFlavor;
    @XmlAttribute(name = "typeCode", required = true)
    protected ActRelationshipHasControlVariable typeCode;
    @XmlAttribute(name = "contextControlCode")
    protected ContextControl contextControlCode;
    @XmlAttribute(name = "contextConductionInd", required = true)
    protected boolean contextConductionInd;

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
     * Gets the value of the dillution property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT210000UV02Dillution }{@code >}
     *     
     */
    public JAXBElement<COCTMT210000UV02Dillution> getDillution() {
        return dillution;
    }

    /**
     * Sets the value of the dillution property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT210000UV02Dillution }{@code >}
     *     
     */
    public void setDillution(JAXBElement<COCTMT210000UV02Dillution> value) {
        this.dillution = value;
    }

    /**
     * Gets the value of the endogenousContent property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT210000UV02EndogenousContent }{@code >}
     *     
     */
    public JAXBElement<COCTMT210000UV02EndogenousContent> getEndogenousContent() {
        return endogenousContent;
    }

    /**
     * Sets the value of the endogenousContent property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT210000UV02EndogenousContent }{@code >}
     *     
     */
    public void setEndogenousContent(JAXBElement<COCTMT210000UV02EndogenousContent> value) {
        this.endogenousContent = value;
    }

    /**
     * Gets the value of the reflexPermission property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT210000UV02ReflexPermission }{@code >}
     *     
     */
    public JAXBElement<COCTMT210000UV02ReflexPermission> getReflexPermission() {
        return reflexPermission;
    }

    /**
     * Sets the value of the reflexPermission property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT210000UV02ReflexPermission }{@code >}
     *     
     */
    public void setReflexPermission(JAXBElement<COCTMT210000UV02ReflexPermission> value) {
        this.reflexPermission = value;
    }

    /**
     * Gets the value of the autoRepeatPermission property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT210000UV02AutoRepeatPermission }{@code >}
     *     
     */
    public JAXBElement<COCTMT210000UV02AutoRepeatPermission> getAutoRepeatPermission() {
        return autoRepeatPermission;
    }

    /**
     * Sets the value of the autoRepeatPermission property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT210000UV02AutoRepeatPermission }{@code >}
     *     
     */
    public void setAutoRepeatPermission(JAXBElement<COCTMT210000UV02AutoRepeatPermission> value) {
        this.autoRepeatPermission = value;
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
     *     {@link ActRelationshipHasControlVariable }
     *     
     */
    public ActRelationshipHasControlVariable getTypeCode() {
        return typeCode;
    }

    /**
     * Sets the value of the typeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActRelationshipHasControlVariable }
     *     
     */
    public void setTypeCode(ActRelationshipHasControlVariable value) {
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
        if (contextControlCode == null) {
            return ContextControl.AN;
        } else {
            return contextControlCode;
        }
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
     */
    public boolean isContextConductionInd() {
        return contextConductionInd;
    }

    /**
     * Sets the value of the contextConductionInd property.
     * 
     */
    public void setContextConductionInd(boolean value) {
        this.contextConductionInd = value;
    }

}
