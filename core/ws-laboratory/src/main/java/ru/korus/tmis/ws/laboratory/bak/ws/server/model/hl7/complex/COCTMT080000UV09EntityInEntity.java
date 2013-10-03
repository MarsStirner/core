
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for COCT_MT080000UV09.EntityInEntity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT080000UV09.EntityInEntity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="effectiveTime" type="{urn:hl7-org:v3}IVL_TS" minOccurs="0"/>
 *         &lt;element name="positionNumber" type="{urn:hl7-org:v3}LIST_INT" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element name="containerHolder" type="{urn:hl7-org:v3}COCT_MT080000UV09.Holder" minOccurs="0"/>
 *           &lt;element name="container" type="{urn:hl7-org:v3}COCT_MT080000UV09.Container" minOccurs="0"/>
 *           &lt;element name="containerAdditiveMaterial" type="{urn:hl7-org:v3}COCT_MT080000UV09.AdditiveMaterial" minOccurs="0"/>
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
@XmlType(name = "COCT_MT080000UV09.EntityInEntity", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "effectiveTime",
    "positionNumber",
    "containerHolder",
    "container",
    "containerAdditiveMaterial"
})
public class COCTMT080000UV09EntityInEntity {

    protected DSETCS realmCode;
    protected II typeId;
    protected LISTII templateId;
    protected IVLTS effectiveTime;
    protected LISTINT positionNumber;
    @XmlElementRef(name = "containerHolder", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT080000UV09Holder> containerHolder;
    @XmlElementRef(name = "container", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT080000UV09Container> container;
    @XmlElementRef(name = "containerAdditiveMaterial", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT080000UV09AdditiveMaterial> containerAdditiveMaterial;
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
     * Gets the value of the effectiveTime property.
     * 
     * @return
     *     possible object is
     *     {@link IVLTS }
     *     
     */
    public IVLTS getEffectiveTime() {
        return effectiveTime;
    }

    /**
     * Sets the value of the effectiveTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link IVLTS }
     *     
     */
    public void setEffectiveTime(IVLTS value) {
        this.effectiveTime = value;
    }

    /**
     * Gets the value of the positionNumber property.
     * 
     * @return
     *     possible object is
     *     {@link LISTINT }
     *     
     */
    public LISTINT getPositionNumber() {
        return positionNumber;
    }

    /**
     * Sets the value of the positionNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link LISTINT }
     *     
     */
    public void setPositionNumber(LISTINT value) {
        this.positionNumber = value;
    }

    /**
     * Gets the value of the containerHolder property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT080000UV09Holder }{@code >}
     *     
     */
    public JAXBElement<COCTMT080000UV09Holder> getContainerHolder() {
        return containerHolder;
    }

    /**
     * Sets the value of the containerHolder property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT080000UV09Holder }{@code >}
     *     
     */
    public void setContainerHolder(JAXBElement<COCTMT080000UV09Holder> value) {
        this.containerHolder = value;
    }

    /**
     * Gets the value of the container property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT080000UV09Container }{@code >}
     *     
     */
    public JAXBElement<COCTMT080000UV09Container> getContainer() {
        return container;
    }

    /**
     * Sets the value of the container property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT080000UV09Container }{@code >}
     *     
     */
    public void setContainer(JAXBElement<COCTMT080000UV09Container> value) {
        this.container = value;
    }

    /**
     * Gets the value of the containerAdditiveMaterial property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT080000UV09AdditiveMaterial }{@code >}
     *     
     */
    public JAXBElement<COCTMT080000UV09AdditiveMaterial> getContainerAdditiveMaterial() {
        return containerAdditiveMaterial;
    }

    /**
     * Sets the value of the containerAdditiveMaterial property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT080000UV09AdditiveMaterial }{@code >}
     *     
     */
    public void setContainerAdditiveMaterial(JAXBElement<COCTMT080000UV09AdditiveMaterial> value) {
        this.containerAdditiveMaterial = value;
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
