
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for COCT_MT230100UV.TerritorialAuthority complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT230100UV.TerritorialAuthority">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="territory" type="{urn:hl7-org:v3}COCT_MT230100UV.Agency" minOccurs="0"/>
 *         &lt;element name="governingCountry" type="{urn:hl7-org:v3}COCT_MT230100UV.Country" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:hl7-org:v3}InfrastructureRootAttributes"/>
 *       &lt;attribute name="classCode" use="required" type="{urn:hl7-org:v3}RoleClassTerritoryOfAuthority" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "COCT_MT230100UV.TerritorialAuthority", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "territory",
    "governingCountry"
})
public class COCTMT230100UVTerritorialAuthority {

    protected DSETCS realmCode;
    protected II typeId;
    protected LISTII templateId;
    @XmlElementRef(name = "territory", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT230100UVAgency> territory;
    @XmlElementRef(name = "governingCountry", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT230100UVCountry> governingCountry;
    @XmlAttribute(name = "classCode", required = true)
    protected RoleClassTerritoryOfAuthority classCode;

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
     * Gets the value of the territory property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT230100UVAgency }{@code >}
     *     
     */
    public JAXBElement<COCTMT230100UVAgency> getTerritory() {
        return territory;
    }

    /**
     * Sets the value of the territory property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT230100UVAgency }{@code >}
     *     
     */
    public void setTerritory(JAXBElement<COCTMT230100UVAgency> value) {
        this.territory = value;
    }

    /**
     * Gets the value of the governingCountry property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT230100UVCountry }{@code >}
     *     
     */
    public JAXBElement<COCTMT230100UVCountry> getGoverningCountry() {
        return governingCountry;
    }

    /**
     * Sets the value of the governingCountry property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT230100UVCountry }{@code >}
     *     
     */
    public void setGoverningCountry(JAXBElement<COCTMT230100UVCountry> value) {
        this.governingCountry = value;
    }

    /**
     * Gets the value of the classCode property.
     * 
     * @return
     *     possible object is
     *     {@link RoleClassTerritoryOfAuthority }
     *     
     */
    public RoleClassTerritoryOfAuthority getClassCode() {
        return classCode;
    }

    /**
     * Sets the value of the classCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link RoleClassTerritoryOfAuthority }
     *     
     */
    public void setClassCode(RoleClassTerritoryOfAuthority value) {
        this.classCode = value;
    }

}
