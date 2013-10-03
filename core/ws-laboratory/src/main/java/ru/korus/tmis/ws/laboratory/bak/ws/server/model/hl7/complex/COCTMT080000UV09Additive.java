
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for COCT_MT080000UV09.Additive complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT080000UV09.Additive">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="id" type="{urn:hl7-org:v3}DSET_II" minOccurs="0"/>
 *         &lt;element name="quantity" type="{urn:hl7-org:v3}RTO" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element name="additiveHolder" type="{urn:hl7-org:v3}COCT_MT080000UV09.Holder" minOccurs="0"/>
 *           &lt;element name="additiveContainer" type="{urn:hl7-org:v3}COCT_MT080000UV09.Container" minOccurs="0"/>
 *           &lt;element name="additiveMaterial" type="{urn:hl7-org:v3}COCT_MT080000UV09.AdditiveMaterial" minOccurs="0"/>
 *         &lt;/choice>
 *         &lt;element name="related" type="{urn:hl7-org:v3}COCT_MT080000UV09.SourceOf" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:hl7-org:v3}InfrastructureRootAttributes"/>
 *       &lt;attribute name="nullFlavor" type="{urn:hl7-org:v3}NullFlavor" />
 *       &lt;attribute name="classCode" use="required" type="{urn:hl7-org:v3}RoleClassAdditive" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "COCT_MT080000UV09.Additive", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "id",
    "quantity",
    "additiveHolder",
    "additiveContainer",
    "additiveMaterial",
    "related"
})
public class COCTMT080000UV09Additive {

    protected DSETCS realmCode;
    protected II typeId;
    protected LISTII templateId;
    protected DSETII id;
    protected RTO quantity;
    @XmlElementRef(name = "additiveHolder", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT080000UV09Holder> additiveHolder;
    @XmlElementRef(name = "additiveContainer", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT080000UV09Container> additiveContainer;
    @XmlElementRef(name = "additiveMaterial", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT080000UV09AdditiveMaterial> additiveMaterial;
    @XmlElement(nillable = true)
    protected List<COCTMT080000UV09SourceOf> related;
    @XmlAttribute(name = "nullFlavor")
    protected NullFlavor nullFlavor;
    @XmlAttribute(name = "classCode", required = true)
    protected RoleClassAdditive classCode;

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
     *     {@link DSETII }
     *     
     */
    public DSETII getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link DSETII }
     *     
     */
    public void setId(DSETII value) {
        this.id = value;
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
     * Gets the value of the additiveHolder property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT080000UV09Holder }{@code >}
     *     
     */
    public JAXBElement<COCTMT080000UV09Holder> getAdditiveHolder() {
        return additiveHolder;
    }

    /**
     * Sets the value of the additiveHolder property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT080000UV09Holder }{@code >}
     *     
     */
    public void setAdditiveHolder(JAXBElement<COCTMT080000UV09Holder> value) {
        this.additiveHolder = value;
    }

    /**
     * Gets the value of the additiveContainer property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT080000UV09Container }{@code >}
     *     
     */
    public JAXBElement<COCTMT080000UV09Container> getAdditiveContainer() {
        return additiveContainer;
    }

    /**
     * Sets the value of the additiveContainer property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT080000UV09Container }{@code >}
     *     
     */
    public void setAdditiveContainer(JAXBElement<COCTMT080000UV09Container> value) {
        this.additiveContainer = value;
    }

    /**
     * Gets the value of the additiveMaterial property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT080000UV09AdditiveMaterial }{@code >}
     *     
     */
    public JAXBElement<COCTMT080000UV09AdditiveMaterial> getAdditiveMaterial() {
        return additiveMaterial;
    }

    /**
     * Sets the value of the additiveMaterial property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT080000UV09AdditiveMaterial }{@code >}
     *     
     */
    public void setAdditiveMaterial(JAXBElement<COCTMT080000UV09AdditiveMaterial> value) {
        this.additiveMaterial = value;
    }

    /**
     * Gets the value of the related property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the related property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRelated().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT080000UV09SourceOf }
     * 
     * 
     */
    public List<COCTMT080000UV09SourceOf> getRelated() {
        if (related == null) {
            related = new ArrayList<COCTMT080000UV09SourceOf>();
        }
        return this.related;
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
     *     {@link RoleClassAdditive }
     *     
     */
    public RoleClassAdditive getClassCode() {
        return classCode;
    }

    /**
     * Sets the value of the classCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link RoleClassAdditive }
     *     
     */
    public void setClassCode(RoleClassAdditive value) {
        this.classCode = value;
    }

}
