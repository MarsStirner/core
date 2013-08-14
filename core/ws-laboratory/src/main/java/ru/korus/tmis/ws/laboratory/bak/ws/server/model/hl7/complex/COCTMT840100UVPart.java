
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for COCT_MT840100UV.Part complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT840100UV.Part">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="id" type="{urn:hl7-org:v3}II" minOccurs="0"/>
 *         &lt;element name="code" type="{urn:hl7-org:v3}CE" minOccurs="0"/>
 *         &lt;element name="statusCode" type="{urn:hl7-org:v3}CS" minOccurs="0"/>
 *         &lt;element name="effectiveTime" type="{urn:hl7-org:v3}IVL_TS" minOccurs="0"/>
 *         &lt;element name="quantity" type="{urn:hl7-org:v3}RTO_QTY_QTY" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;choice>
 *             &lt;element name="partPerson1" type="{urn:hl7-org:v3}COCT_MT840000UV.Person" minOccurs="0"/>
 *             &lt;element name="partLivingSubject1" type="{urn:hl7-org:v3}COCT_MT840000UV.LivingSubject" minOccurs="0"/>
 *             &lt;element name="partNonPersonLivingSubject1" type="{urn:hl7-org:v3}COCT_MT840000UV.NonPersonLivingSubject" minOccurs="0"/>
 *           &lt;/choice>
 *           &lt;choice>
 *             &lt;element name="partPlace1" type="{urn:hl7-org:v3}COCT_MT840000UV.Place" minOccurs="0"/>
 *             &lt;choice>
 *               &lt;element name="partMaterial" type="{urn:hl7-org:v3}COCT_MT840000UV.Material" minOccurs="0"/>
 *               &lt;element name="partManufacturedMaterial" type="{urn:hl7-org:v3}COCT_MT840000UV.ManufacturedMaterial" minOccurs="0"/>
 *             &lt;/choice>
 *           &lt;/choice>
 *           &lt;element name="partOrganization" type="{urn:hl7-org:v3}COCT_MT840000UV.Organization" minOccurs="0"/>
 *           &lt;element name="partEntityGroup" type="{urn:hl7-org:v3}COCT_MT840000UV.EntityGroup" minOccurs="0"/>
 *           &lt;element name="partEntity" type="{urn:hl7-org:v3}COCT_MT840000UV.Entity" minOccurs="0"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:hl7-org:v3}InfrastructureRootAttributes"/>
 *       &lt;attribute name="nullFlavor" type="{urn:hl7-org:v3}NullFlavor" />
 *       &lt;attribute name="classCode" use="required" type="{urn:hl7-org:v3}RoleClassPart" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "COCT_MT840100UV.Part", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "id",
    "code",
    "statusCode",
    "effectiveTime",
    "quantity",
    "partPerson1",
    "partLivingSubject1",
    "partNonPersonLivingSubject1",
    "partPlace1",
    "partMaterial",
    "partManufacturedMaterial",
    "partOrganization",
    "partEntityGroup",
    "partEntity"
})
public class COCTMT840100UVPart {

    protected List<CS> realmCode;
    protected II typeId;
    protected List<II> templateId;
    protected II id;
    protected CE code;
    protected CS statusCode;
    protected IVLTS effectiveTime;
    protected RTOQTYQTY quantity;
    @XmlElementRef(name = "partPerson1", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UVPerson> partPerson1;
    @XmlElementRef(name = "partLivingSubject1", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UVLivingSubject> partLivingSubject1;
    @XmlElementRef(name = "partNonPersonLivingSubject1", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UVNonPersonLivingSubject> partNonPersonLivingSubject1;
    @XmlElementRef(name = "partPlace1", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UVPlace> partPlace1;
    @XmlElementRef(name = "partMaterial", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UVMaterial> partMaterial;
    @XmlElementRef(name = "partManufacturedMaterial", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UVManufacturedMaterial> partManufacturedMaterial;
    @XmlElementRef(name = "partOrganization", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UVOrganization> partOrganization;
    @XmlElementRef(name = "partEntityGroup", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UVEntityGroup> partEntityGroup;
    @XmlElementRef(name = "partEntity", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UVEntity> partEntity;
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
     *     {@link CE }
     *     
     */
    public CE getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link CE }
     *     
     */
    public void setCode(CE value) {
        this.code = value;
    }

    /**
     * Gets the value of the statusCode property.
     * 
     * @return
     *     possible object is
     *     {@link CS }
     *     
     */
    public CS getStatusCode() {
        return statusCode;
    }

    /**
     * Sets the value of the statusCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CS }
     *     
     */
    public void setStatusCode(CS value) {
        this.statusCode = value;
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
     * Gets the value of the quantity property.
     * 
     * @return
     *     possible object is
     *     {@link RTOQTYQTY }
     *     
     */
    public RTOQTYQTY getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link RTOQTYQTY }
     *     
     */
    public void setQuantity(RTOQTYQTY value) {
        this.quantity = value;
    }

    /**
     * Gets the value of the partPerson1 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVPerson }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UVPerson> getPartPerson1() {
        return partPerson1;
    }

    /**
     * Sets the value of the partPerson1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVPerson }{@code >}
     *     
     */
    public void setPartPerson1(JAXBElement<COCTMT840000UVPerson> value) {
        this.partPerson1 = value;
    }

    /**
     * Gets the value of the partLivingSubject1 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVLivingSubject }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UVLivingSubject> getPartLivingSubject1() {
        return partLivingSubject1;
    }

    /**
     * Sets the value of the partLivingSubject1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVLivingSubject }{@code >}
     *     
     */
    public void setPartLivingSubject1(JAXBElement<COCTMT840000UVLivingSubject> value) {
        this.partLivingSubject1 = value;
    }

    /**
     * Gets the value of the partNonPersonLivingSubject1 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVNonPersonLivingSubject }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UVNonPersonLivingSubject> getPartNonPersonLivingSubject1() {
        return partNonPersonLivingSubject1;
    }

    /**
     * Sets the value of the partNonPersonLivingSubject1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVNonPersonLivingSubject }{@code >}
     *     
     */
    public void setPartNonPersonLivingSubject1(JAXBElement<COCTMT840000UVNonPersonLivingSubject> value) {
        this.partNonPersonLivingSubject1 = value;
    }

    /**
     * Gets the value of the partPlace1 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVPlace }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UVPlace> getPartPlace1() {
        return partPlace1;
    }

    /**
     * Sets the value of the partPlace1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVPlace }{@code >}
     *     
     */
    public void setPartPlace1(JAXBElement<COCTMT840000UVPlace> value) {
        this.partPlace1 = value;
    }

    /**
     * Gets the value of the partMaterial property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVMaterial }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UVMaterial> getPartMaterial() {
        return partMaterial;
    }

    /**
     * Sets the value of the partMaterial property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVMaterial }{@code >}
     *     
     */
    public void setPartMaterial(JAXBElement<COCTMT840000UVMaterial> value) {
        this.partMaterial = value;
    }

    /**
     * Gets the value of the partManufacturedMaterial property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVManufacturedMaterial }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UVManufacturedMaterial> getPartManufacturedMaterial() {
        return partManufacturedMaterial;
    }

    /**
     * Sets the value of the partManufacturedMaterial property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVManufacturedMaterial }{@code >}
     *     
     */
    public void setPartManufacturedMaterial(JAXBElement<COCTMT840000UVManufacturedMaterial> value) {
        this.partManufacturedMaterial = value;
    }

    /**
     * Gets the value of the partOrganization property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVOrganization }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UVOrganization> getPartOrganization() {
        return partOrganization;
    }

    /**
     * Sets the value of the partOrganization property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVOrganization }{@code >}
     *     
     */
    public void setPartOrganization(JAXBElement<COCTMT840000UVOrganization> value) {
        this.partOrganization = value;
    }

    /**
     * Gets the value of the partEntityGroup property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVEntityGroup }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UVEntityGroup> getPartEntityGroup() {
        return partEntityGroup;
    }

    /**
     * Sets the value of the partEntityGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVEntityGroup }{@code >}
     *     
     */
    public void setPartEntityGroup(JAXBElement<COCTMT840000UVEntityGroup> value) {
        this.partEntityGroup = value;
    }

    /**
     * Gets the value of the partEntity property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVEntity }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UVEntity> getPartEntity() {
        return partEntity;
    }

    /**
     * Sets the value of the partEntity property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVEntity }{@code >}
     *     
     */
    public void setPartEntity(JAXBElement<COCTMT840000UVEntity> value) {
        this.partEntity = value;
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