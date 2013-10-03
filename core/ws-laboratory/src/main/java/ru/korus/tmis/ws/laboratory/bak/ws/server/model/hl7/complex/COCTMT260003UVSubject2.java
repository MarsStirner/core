
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for COCT_MT260003UV.Subject2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT260003UV.Subject2">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;choice>
 *           &lt;element name="otherMedication" type="{urn:hl7-org:v3}COCT_MT260003UV.OtherMedication"/>
 *           &lt;element name="otherSupply" type="{urn:hl7-org:v3}COCT_MT260003UV.OtherSupply"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:hl7-org:v3}InfrastructureRootAttributes"/>
 *       &lt;attribute name="nullFlavor" type="{urn:hl7-org:v3}NullFlavor" />
 *       &lt;attribute name="typeCode" use="required" type="{urn:hl7-org:v3}ActRelationshipHasSubject" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "COCT_MT260003UV.Subject2", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "otherMedication",
    "otherSupply"
})
public class COCTMT260003UVSubject2 {

    protected DSETCS realmCode;
    protected II typeId;
    protected LISTII templateId;
    @XmlElementRef(name = "otherMedication", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT260003UVOtherMedication> otherMedication;
    @XmlElementRef(name = "otherSupply", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT260003UVOtherSupply> otherSupply;
    @XmlAttribute(name = "nullFlavor")
    protected NullFlavor nullFlavor;
    @XmlAttribute(name = "typeCode", required = true)
    protected ActRelationshipHasSubject typeCode;

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
     * Gets the value of the otherMedication property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT260003UVOtherMedication }{@code >}
     *     
     */
    public JAXBElement<COCTMT260003UVOtherMedication> getOtherMedication() {
        return otherMedication;
    }

    /**
     * Sets the value of the otherMedication property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT260003UVOtherMedication }{@code >}
     *     
     */
    public void setOtherMedication(JAXBElement<COCTMT260003UVOtherMedication> value) {
        this.otherMedication = value;
    }

    /**
     * Gets the value of the otherSupply property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT260003UVOtherSupply }{@code >}
     *     
     */
    public JAXBElement<COCTMT260003UVOtherSupply> getOtherSupply() {
        return otherSupply;
    }

    /**
     * Sets the value of the otherSupply property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT260003UVOtherSupply }{@code >}
     *     
     */
    public void setOtherSupply(JAXBElement<COCTMT260003UVOtherSupply> value) {
        this.otherSupply = value;
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
     *     {@link ActRelationshipHasSubject }
     *     
     */
    public ActRelationshipHasSubject getTypeCode() {
        return typeCode;
    }

    /**
     * Sets the value of the typeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActRelationshipHasSubject }
     *     
     */
    public void setTypeCode(ActRelationshipHasSubject value) {
        this.typeCode = value;
    }

}
