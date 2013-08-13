
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for COCT_MT841500UV.ContainedEntity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT841500UV.ContainedEntity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;choice>
 *           &lt;element name="contained1" type="{urn:hl7-org:v3}COCT_MT841500UV.Person"/>
 *           &lt;element name="contained2" type="{urn:hl7-org:v3}COCT_MT841500UV.NonPersonLivingSubject"/>
 *           &lt;element name="contained3" type="{urn:hl7-org:v3}COCT_MT841500UV.LivingSubject"/>
 *           &lt;element name="contained4" type="{urn:hl7-org:v3}COCT_MT841500UV.Material"/>
 *           &lt;element name="containedManufacturedMaterial" type="{urn:hl7-org:v3}COCT_MT841500UV.ManufacturedMaterial"/>
 *           &lt;element name="contained5" type="{urn:hl7-org:v3}COCT_MT841500UV.Entity"/>
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
@XmlType(name = "COCT_MT841500UV.ContainedEntity", propOrder = {
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
public class COCTMT841500UVContainedEntity {

    protected List<CS> realmCode;
    protected II typeId;
    protected List<II> templateId;
    protected COCTMT841500UVPerson contained1;
    protected COCTMT841500UVNonPersonLivingSubject contained2;
    protected COCTMT841500UVLivingSubject contained3;
    protected COCTMT841500UVMaterial contained4;
    protected COCTMT841500UVManufacturedMaterial containedManufacturedMaterial;
    protected COCTMT841500UVEntity contained5;
    @XmlAttribute(name = "nullFlavor")
    protected List<String> nullFlavor;
    @XmlAttribute(name = "classCode", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String classCode;

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
     *     {@link COCTMT841500UVPerson }
     *     
     */
    public COCTMT841500UVPerson getContained1() {
        return contained1;
    }

    /**
     * Sets the value of the contained1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link COCTMT841500UVPerson }
     *     
     */
    public void setContained1(COCTMT841500UVPerson value) {
        this.contained1 = value;
    }

    /**
     * Gets the value of the contained2 property.
     * 
     * @return
     *     possible object is
     *     {@link COCTMT841500UVNonPersonLivingSubject }
     *     
     */
    public COCTMT841500UVNonPersonLivingSubject getContained2() {
        return contained2;
    }

    /**
     * Sets the value of the contained2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link COCTMT841500UVNonPersonLivingSubject }
     *     
     */
    public void setContained2(COCTMT841500UVNonPersonLivingSubject value) {
        this.contained2 = value;
    }

    /**
     * Gets the value of the contained3 property.
     * 
     * @return
     *     possible object is
     *     {@link COCTMT841500UVLivingSubject }
     *     
     */
    public COCTMT841500UVLivingSubject getContained3() {
        return contained3;
    }

    /**
     * Sets the value of the contained3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link COCTMT841500UVLivingSubject }
     *     
     */
    public void setContained3(COCTMT841500UVLivingSubject value) {
        this.contained3 = value;
    }

    /**
     * Gets the value of the contained4 property.
     * 
     * @return
     *     possible object is
     *     {@link COCTMT841500UVMaterial }
     *     
     */
    public COCTMT841500UVMaterial getContained4() {
        return contained4;
    }

    /**
     * Sets the value of the contained4 property.
     * 
     * @param value
     *     allowed object is
     *     {@link COCTMT841500UVMaterial }
     *     
     */
    public void setContained4(COCTMT841500UVMaterial value) {
        this.contained4 = value;
    }

    /**
     * Gets the value of the containedManufacturedMaterial property.
     * 
     * @return
     *     possible object is
     *     {@link COCTMT841500UVManufacturedMaterial }
     *     
     */
    public COCTMT841500UVManufacturedMaterial getContainedManufacturedMaterial() {
        return containedManufacturedMaterial;
    }

    /**
     * Sets the value of the containedManufacturedMaterial property.
     * 
     * @param value
     *     allowed object is
     *     {@link COCTMT841500UVManufacturedMaterial }
     *     
     */
    public void setContainedManufacturedMaterial(COCTMT841500UVManufacturedMaterial value) {
        this.containedManufacturedMaterial = value;
    }

    /**
     * Gets the value of the contained5 property.
     * 
     * @return
     *     possible object is
     *     {@link COCTMT841500UVEntity }
     *     
     */
    public COCTMT841500UVEntity getContained5() {
        return contained5;
    }

    /**
     * Sets the value of the contained5 property.
     * 
     * @param value
     *     allowed object is
     *     {@link COCTMT841500UVEntity }
     *     
     */
    public void setContained5(COCTMT841500UVEntity value) {
        this.contained5 = value;
    }

    /**
     * Gets the value of the nullFlavor property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nullFlavor property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNullFlavor().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
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

}
