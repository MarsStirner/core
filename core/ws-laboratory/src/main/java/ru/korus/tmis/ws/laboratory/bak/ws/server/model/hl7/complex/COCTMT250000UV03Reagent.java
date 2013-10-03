
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for COCT_MT250000UV03.Reagent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT250000UV03.Reagent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="manufacturedTestReagent" type="{urn:hl7-org:v3}COCT_MT250000UV03.TestReagent"/>
 *         &lt;element name="manufacturerOrganization" type="{urn:hl7-org:v3}COCT_MT150000UV02.Organization" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:hl7-org:v3}InfrastructureRootAttributes"/>
 *       &lt;attribute name="classCode" use="required" type="{urn:hl7-org:v3}RoleClassManufacturedProduct" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "COCT_MT250000UV03.Reagent", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "manufacturedTestReagent",
    "manufacturerOrganization"
})
public class COCTMT250000UV03Reagent {

    protected DSETCS realmCode;
    protected II typeId;
    protected LISTII templateId;
    @XmlElement(required = true)
    protected COCTMT250000UV03TestReagent manufacturedTestReagent;
    @XmlElementRef(name = "manufacturerOrganization", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT150000UV02Organization> manufacturerOrganization;
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
     * Gets the value of the manufacturedTestReagent property.
     * 
     * @return
     *     possible object is
     *     {@link COCTMT250000UV03TestReagent }
     *     
     */
    public COCTMT250000UV03TestReagent getManufacturedTestReagent() {
        return manufacturedTestReagent;
    }

    /**
     * Sets the value of the manufacturedTestReagent property.
     * 
     * @param value
     *     allowed object is
     *     {@link COCTMT250000UV03TestReagent }
     *     
     */
    public void setManufacturedTestReagent(COCTMT250000UV03TestReagent value) {
        this.manufacturedTestReagent = value;
    }

    /**
     * Gets the value of the manufacturerOrganization property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT150000UV02Organization }{@code >}
     *     
     */
    public JAXBElement<COCTMT150000UV02Organization> getManufacturerOrganization() {
        return manufacturerOrganization;
    }

    /**
     * Sets the value of the manufacturerOrganization property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT150000UV02Organization }{@code >}
     *     
     */
    public void setManufacturerOrganization(JAXBElement<COCTMT150000UV02Organization> value) {
        this.manufacturerOrganization = value;
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
