
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for COCT_MT530000UV.Precondition2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT530000UV.Precondition2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="conjunctionCode" type="{urn:hl7-org:v3}CS" minOccurs="0"/>
 *         &lt;element name="seperatableInd" type="{urn:hl7-org:v3}BL" minOccurs="0"/>
 *         &lt;element name="criterion" type="{urn:hl7-org:v3}COCT_MT530000UV.Criterion"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:hl7-org:v3}InfrastructureRootAttributes"/>
 *       &lt;attribute name="nullFlavor" type="{urn:hl7-org:v3}NullFlavor" />
 *       &lt;attribute name="typeCode" use="required" type="{urn:hl7-org:v3}ActRelationshipHasPre-condition" />
 *       &lt;attribute name="contextControlCode" type="{urn:hl7-org:v3}ContextControl" default="ON" />
 *       &lt;attribute name="contextConductionInd" type="{urn:hl7-org:v3}bl" default="false" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "COCT_MT530000UV.Precondition2", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "conjunctionCode",
    "seperatableInd",
    "criterion"
})
public class COCTMT530000UVPrecondition2 {

    protected List<CS> realmCode;
    protected II typeId;
    protected List<II> templateId;
    protected CS conjunctionCode;
    protected BL seperatableInd;
    @XmlElement(required = true, nillable = true)
    protected COCTMT530000UVCriterion criterion;
    @XmlAttribute(name = "nullFlavor")
    protected NullFlavor nullFlavor;
    @XmlAttribute(name = "typeCode", required = true)
    protected ActRelationshipHasPreCondition typeCode;
    @XmlAttribute(name = "contextControlCode")
    protected ContextControl contextControlCode;
    @XmlAttribute(name = "contextConductionInd")
    protected Boolean contextConductionInd;

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
     * Gets the value of the conjunctionCode property.
     * 
     * @return
     *     possible object is
     *     {@link CS }
     *     
     */
    public CS getConjunctionCode() {
        return conjunctionCode;
    }

    /**
     * Sets the value of the conjunctionCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CS }
     *     
     */
    public void setConjunctionCode(CS value) {
        this.conjunctionCode = value;
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
     * Gets the value of the criterion property.
     * 
     * @return
     *     possible object is
     *     {@link COCTMT530000UVCriterion }
     *     
     */
    public COCTMT530000UVCriterion getCriterion() {
        return criterion;
    }

    /**
     * Sets the value of the criterion property.
     * 
     * @param value
     *     allowed object is
     *     {@link COCTMT530000UVCriterion }
     *     
     */
    public void setCriterion(COCTMT530000UVCriterion value) {
        this.criterion = value;
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
     *     {@link ActRelationshipHasPreCondition }
     *     
     */
    public ActRelationshipHasPreCondition getTypeCode() {
        return typeCode;
    }

    /**
     * Sets the value of the typeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActRelationshipHasPreCondition }
     *     
     */
    public void setTypeCode(ActRelationshipHasPreCondition value) {
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
            return ContextControl.ON;
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
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isContextConductionInd() {
        if (contextConductionInd == null) {
            return false;
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
