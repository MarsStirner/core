
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for COCT_MT841500UV09.HasIngredients complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT841500UV09.HasIngredients">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="confidentialityCode" type="{urn:hl7-org:v3}DSET_CD" minOccurs="0"/>
 *         &lt;element name="quantity" type="{urn:hl7-org:v3}RTO" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element name="ingredientMaterial" type="{urn:hl7-org:v3}COCT_MT841500UV09.Material" minOccurs="0"/>
 *           &lt;element name="ingredientManufacturedMaterial" type="{urn:hl7-org:v3}COCT_MT841500UV09.ManufacturedMaterial" minOccurs="0"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:hl7-org:v3}InfrastructureRootAttributes"/>
 *       &lt;attribute name="nullFlavor" type="{urn:hl7-org:v3}NullFlavor" />
 *       &lt;attribute name="classCode" use="required" type="{urn:hl7-org:v3}RoleClassIngredientEntity" />
 *       &lt;attribute name="negationInd" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "COCT_MT841500UV09.HasIngredients", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "confidentialityCode",
    "quantity",
    "ingredientMaterial",
    "ingredientManufacturedMaterial"
})
public class COCTMT841500UV09HasIngredients {

    protected DSETCS realmCode;
    protected II typeId;
    protected LISTII templateId;
    protected DSETCD confidentialityCode;
    protected RTO quantity;
    @XmlElementRef(name = "ingredientMaterial", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT841500UV09Material> ingredientMaterial;
    @XmlElementRef(name = "ingredientManufacturedMaterial", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT841500UV09ManufacturedMaterial> ingredientManufacturedMaterial;
    @XmlAttribute(name = "nullFlavor")
    protected NullFlavor nullFlavor;
    @XmlAttribute(name = "classCode", required = true)
    protected RoleClassIngredientEntity classCode;
    @XmlAttribute(name = "negationInd")
    protected Boolean negationInd;

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
     * Gets the value of the confidentialityCode property.
     * 
     * @return
     *     possible object is
     *     {@link DSETCD }
     *     
     */
    public DSETCD getConfidentialityCode() {
        return confidentialityCode;
    }

    /**
     * Sets the value of the confidentialityCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link DSETCD }
     *     
     */
    public void setConfidentialityCode(DSETCD value) {
        this.confidentialityCode = value;
    }

    /**
     * Gets the value of the quantity property.
     * 
     * @return
     *     possible object is
     *     {@link RTO }
     *     
     */
    public RTO getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link RTO }
     *     
     */
    public void setQuantity(RTO value) {
        this.quantity = value;
    }

    /**
     * Gets the value of the ingredientMaterial property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT841500UV09Material }{@code >}
     *     
     */
    public JAXBElement<COCTMT841500UV09Material> getIngredientMaterial() {
        return ingredientMaterial;
    }

    /**
     * Sets the value of the ingredientMaterial property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT841500UV09Material }{@code >}
     *     
     */
    public void setIngredientMaterial(JAXBElement<COCTMT841500UV09Material> value) {
        this.ingredientMaterial = value;
    }

    /**
     * Gets the value of the ingredientManufacturedMaterial property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT841500UV09ManufacturedMaterial }{@code >}
     *     
     */
    public JAXBElement<COCTMT841500UV09ManufacturedMaterial> getIngredientManufacturedMaterial() {
        return ingredientManufacturedMaterial;
    }

    /**
     * Sets the value of the ingredientManufacturedMaterial property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT841500UV09ManufacturedMaterial }{@code >}
     *     
     */
    public void setIngredientManufacturedMaterial(JAXBElement<COCTMT841500UV09ManufacturedMaterial> value) {
        this.ingredientManufacturedMaterial = value;
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
     *     {@link RoleClassIngredientEntity }
     *     
     */
    public RoleClassIngredientEntity getClassCode() {
        return classCode;
    }

    /**
     * Sets the value of the classCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link RoleClassIngredientEntity }
     *     
     */
    public void setClassCode(RoleClassIngredientEntity value) {
        this.classCode = value;
    }

    /**
     * Gets the value of the negationInd property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNegationInd() {
        return negationInd;
    }

    /**
     * Sets the value of the negationInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNegationInd(Boolean value) {
        this.negationInd = value;
    }

}
