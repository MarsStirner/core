
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for COCT_MT841500UV09.ContainedEntity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT841500UV09.ContainedEntity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;choice>
 *           &lt;element name="contained1" type="{urn:hl7-org:v3}COCT_MT841500UV09.Person"/>
 *           &lt;element name="contained2" type="{urn:hl7-org:v3}COCT_MT841500UV09.NonPersonLivingSubject"/>
 *           &lt;element name="contained3" type="{urn:hl7-org:v3}COCT_MT841500UV09.LivingSubject"/>
 *           &lt;element name="contained4" type="{urn:hl7-org:v3}COCT_MT841500UV09.Material"/>
 *           &lt;element name="containedManufacturedMaterial" type="{urn:hl7-org:v3}COCT_MT841500UV09.ManufacturedMaterial"/>
 *           &lt;element name="contained5" type="{urn:hl7-org:v3}COCT_MT841500UV09.Entity"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:hl7-org:v3}InfrastructureRootAttributes"/>
 *       &lt;attribute name="nullFlavor" type="{urn:hl7-org:v3}NullFlavor" />
 *       &lt;attribute name="classCode" use="required" type="{urn:hl7-org:v3}RoleClassContent" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "COCT_MT841500UV09.ContainedEntity", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "contained1",
    "contained2",
    "contained3",
    "contained4",
    "containedManufacturedMaterial",
    "contained5"
})
public class COCTMT841500UV09ContainedEntity {

    protected List<CS> realmCode;
    protected II typeId;
    protected List<II> templateId;
    protected COCTMT841500UV09Person contained1;
    protected COCTMT841500UV09NonPersonLivingSubject contained2;
    protected COCTMT841500UV09LivingSubject contained3;
    protected COCTMT841500UV09Material contained4;
    protected COCTMT841500UV09ManufacturedMaterial containedManufacturedMaterial;
    protected COCTMT841500UV09Entity contained5;
    @XmlAttribute(name = "nullFlavor")
    protected NullFlavor nullFlavor;
    @XmlAttribute(name = "classCode", required = true)
    protected RoleClassContent classCode;

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
     * Gets the value of the contained1 property.
     * 
     * @return
     *     possible object is
     *     {@link COCTMT841500UV09Person }
     *     
     */
    public COCTMT841500UV09Person getContained1() {
        return contained1;
    }

    /**
     * Sets the value of the contained1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link COCTMT841500UV09Person }
     *     
     */
    public void setContained1(COCTMT841500UV09Person value) {
        this.contained1 = value;
    }

    /**
     * Gets the value of the contained2 property.
     * 
     * @return
     *     possible object is
     *     {@link COCTMT841500UV09NonPersonLivingSubject }
     *     
     */
    public COCTMT841500UV09NonPersonLivingSubject getContained2() {
        return contained2;
    }

    /**
     * Sets the value of the contained2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link COCTMT841500UV09NonPersonLivingSubject }
     *     
     */
    public void setContained2(COCTMT841500UV09NonPersonLivingSubject value) {
        this.contained2 = value;
    }

    /**
     * Gets the value of the contained3 property.
     * 
     * @return
     *     possible object is
     *     {@link COCTMT841500UV09LivingSubject }
     *     
     */
    public COCTMT841500UV09LivingSubject getContained3() {
        return contained3;
    }

    /**
     * Sets the value of the contained3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link COCTMT841500UV09LivingSubject }
     *     
     */
    public void setContained3(COCTMT841500UV09LivingSubject value) {
        this.contained3 = value;
    }

    /**
     * Gets the value of the contained4 property.
     * 
     * @return
     *     possible object is
     *     {@link COCTMT841500UV09Material }
     *     
     */
    public COCTMT841500UV09Material getContained4() {
        return contained4;
    }

    /**
     * Sets the value of the contained4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link COCTMT841500UV09Material }
     *     
     */
    public void setContained4(COCTMT841500UV09Material value) {
        this.contained4 = value;
    }

    /**
     * Gets the value of the containedManufacturedMaterial property.
     * 
     * @return
     *     possible object is
     *     {@link COCTMT841500UV09ManufacturedMaterial }
     *     
     */
    public COCTMT841500UV09ManufacturedMaterial getContainedManufacturedMaterial() {
        return containedManufacturedMaterial;
    }

    /**
     * Sets the value of the containedManufacturedMaterial property.
     * 
     * @param value
     *     allowed object is
     *     {@link COCTMT841500UV09ManufacturedMaterial }
     *     
     */
    public void setContainedManufacturedMaterial(COCTMT841500UV09ManufacturedMaterial value) {
        this.containedManufacturedMaterial = value;
    }

    /**
     * Gets the value of the contained5 property.
     * 
     * @return
     *     possible object is
     *     {@link COCTMT841500UV09Entity }
     *     
     */
    public COCTMT841500UV09Entity getContained5() {
        return contained5;
    }

    /**
     * Sets the value of the contained5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link COCTMT841500UV09Entity }
     *     
     */
    public void setContained5(COCTMT841500UV09Entity value) {
        this.contained5 = value;
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
     *     {@link RoleClassContent }
     *     
     */
    public RoleClassContent getClassCode() {
        return classCode;
    }

    /**
     * Sets the value of the classCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link RoleClassContent }
     *     
     */
    public void setClassCode(RoleClassContent value) {
        this.classCode = value;
    }

}
