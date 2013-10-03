
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for COCT_MT490000UV04.ContentPackagedProduct complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT490000UV04.ContentPackagedProduct">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="quantity" type="{urn:hl7-org:v3}RTO" minOccurs="0"/>
 *         &lt;element name="containedManufacturedMaterial" type="{urn:hl7-org:v3}COCT_MT490000UV04.ManufacturedMaterial" minOccurs="0"/>
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
@XmlType(name = "COCT_MT490000UV04.ContentPackagedProduct", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "quantity",
    "containedManufacturedMaterial"
})
public class COCTMT490000UV04ContentPackagedProduct {

    protected DSETCS realmCode;
    protected II typeId;
    protected LISTII templateId;
    protected RTO quantity;
    @XmlElementRef(name = "containedManufacturedMaterial", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT490000UV04ManufacturedMaterial> containedManufacturedMaterial;
    @XmlAttribute(name = "nullFlavor")
    protected NullFlavor nullFlavor;
    @XmlAttribute(name = "classCode", required = true)
    protected RoleClassContent classCode;

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
     * Gets the value of the containedManufacturedMaterial property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT490000UV04ManufacturedMaterial }{@code >}
     *     
     */
    public JAXBElement<COCTMT490000UV04ManufacturedMaterial> getContainedManufacturedMaterial() {
        return containedManufacturedMaterial;
    }

    /**
     * Sets the value of the containedManufacturedMaterial property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT490000UV04ManufacturedMaterial }{@code >}
     *     
     */
    public void setContainedManufacturedMaterial(JAXBElement<COCTMT490000UV04ManufacturedMaterial> value) {
        this.containedManufacturedMaterial = value;
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
