
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for COCT_MT290000UV06.ManufacturedProduct complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT290000UV06.ManufacturedProduct">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="id" type="{urn:hl7-org:v3}II" minOccurs="0"/>
 *         &lt;element name="code" type="{urn:hl7-org:v3}CD" minOccurs="0"/>
 *         &lt;element name="manufacturedMaterial" type="{urn:hl7-org:v3}COCT_MT290000UV06.ManufacturedMaterial" minOccurs="0"/>
 *         &lt;element name="manufacturerManufacturedProductOrganization" type="{urn:hl7-org:v3}COCT_MT290000UV06.ManufacturedProductOrganization" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:hl7-org:v3}InfrastructureRootAttributes"/>
 *       &lt;attribute name="nullFlavor" type="{urn:hl7-org:v3}NullFlavor" />
 *       &lt;attribute name="classCode" use="required" type="{urn:hl7-org:v3}RoleClassManufacturedProduct" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "COCT_MT290000UV06.ManufacturedProduct", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "id",
    "code",
    "manufacturedMaterial",
    "manufacturerManufacturedProductOrganization"
})
public class COCTMT290000UV06ManufacturedProduct {

    protected DSETCS realmCode;
    protected II typeId;
    protected LISTII templateId;
    protected II id;
    protected CD code;
    @XmlElementRef(name = "manufacturedMaterial", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT290000UV06ManufacturedMaterial> manufacturedMaterial;
    @XmlElementRef(name = "manufacturerManufacturedProductOrganization", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT290000UV06ManufacturedProductOrganization> manufacturerManufacturedProductOrganization;
    @XmlAttribute(name = "nullFlavor")
    protected NullFlavor nullFlavor;
    @XmlAttribute(name = "classCode", required = true)
    protected RoleClassManufacturedProduct classCode;

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
     *     {@link II }
     *     
     */
    public II getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link II }
     *     
     */
    public void setId(II value) {
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
     * Gets the value of the manufacturedMaterial property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT290000UV06ManufacturedMaterial }{@code >}
     *     
     */
    public JAXBElement<COCTMT290000UV06ManufacturedMaterial> getManufacturedMaterial() {
        return manufacturedMaterial;
    }

    /**
     * Sets the value of the manufacturedMaterial property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT290000UV06ManufacturedMaterial }{@code >}
     *     
     */
    public void setManufacturedMaterial(JAXBElement<COCTMT290000UV06ManufacturedMaterial> value) {
        this.manufacturedMaterial = value;
    }

    /**
     * Gets the value of the manufacturerManufacturedProductOrganization property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT290000UV06ManufacturedProductOrganization }{@code >}
     *     
     */
    public JAXBElement<COCTMT290000UV06ManufacturedProductOrganization> getManufacturerManufacturedProductOrganization() {
        return manufacturerManufacturedProductOrganization;
    }

    /**
     * Sets the value of the manufacturerManufacturedProductOrganization property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT290000UV06ManufacturedProductOrganization }{@code >}
     *     
     */
    public void setManufacturerManufacturedProductOrganization(JAXBElement<COCTMT290000UV06ManufacturedProductOrganization> value) {
        this.manufacturerManufacturedProductOrganization = value;
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
     *     {@link RoleClassManufacturedProduct }
     *     
     */
    public RoleClassManufacturedProduct getClassCode() {
        return classCode;
    }

    /**
     * Sets the value of the classCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link RoleClassManufacturedProduct }
     *     
     */
    public void setClassCode(RoleClassManufacturedProduct value) {
        this.classCode = value;
    }

}
