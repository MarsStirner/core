
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
 * <p>Java class for COCT_MT840000UV.PartOfWhole complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT840000UV.PartOfWhole">
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
 *             &lt;element name="wholePerson1" type="{urn:hl7-org:v3}COCT_MT840000UV.Person" minOccurs="0"/>
 *             &lt;element name="wholeLivingSubject1" type="{urn:hl7-org:v3}COCT_MT840000UV.LivingSubject" minOccurs="0"/>
 *             &lt;element name="wholeNonPersonLivingSubject1" type="{urn:hl7-org:v3}COCT_MT840000UV.NonPersonLivingSubject" minOccurs="0"/>
 *           &lt;/choice>
 *           &lt;choice>
 *             &lt;element name="wholePlace1" type="{urn:hl7-org:v3}COCT_MT840000UV.Place" minOccurs="0"/>
 *             &lt;choice>
 *               &lt;element name="wholeMaterial" type="{urn:hl7-org:v3}COCT_MT840000UV.Material" minOccurs="0"/>
 *               &lt;element name="wholeManufacturedMaterial" type="{urn:hl7-org:v3}COCT_MT840000UV.ManufacturedMaterial" minOccurs="0"/>
 *             &lt;/choice>
 *           &lt;/choice>
 *           &lt;element name="wholeOrganization" type="{urn:hl7-org:v3}COCT_MT840000UV.Organization" minOccurs="0"/>
 *           &lt;element name="wholeEntityGroup" type="{urn:hl7-org:v3}COCT_MT840000UV.EntityGroup" minOccurs="0"/>
 *           &lt;element name="wholeEntity" type="{urn:hl7-org:v3}COCT_MT840000UV.Entity" minOccurs="0"/>
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
@XmlType(name = "COCT_MT840000UV.PartOfWhole", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "id",
    "code",
    "statusCode",
    "effectiveTime",
    "quantity",
    "wholePerson1",
    "wholeLivingSubject1",
    "wholeNonPersonLivingSubject1",
    "wholePlace1",
    "wholeMaterial",
    "wholeManufacturedMaterial",
    "wholeOrganization",
    "wholeEntityGroup",
    "wholeEntity"
})
public class COCTMT840000UVPartOfWhole {

    protected List<CS> realmCode;
    protected II typeId;
    protected List<II> templateId;
    protected II id;
    protected CE code;
    protected CS statusCode;
    protected IVLTS effectiveTime;
    protected RTOQTYQTY quantity;
    @XmlElementRef(name = "wholePerson1", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UVPerson> wholePerson1;
    @XmlElementRef(name = "wholeLivingSubject1", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UVLivingSubject> wholeLivingSubject1;
    @XmlElementRef(name = "wholeNonPersonLivingSubject1", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UVNonPersonLivingSubject> wholeNonPersonLivingSubject1;
    @XmlElementRef(name = "wholePlace1", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UVPlace> wholePlace1;
    @XmlElementRef(name = "wholeMaterial", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UVMaterial> wholeMaterial;
    @XmlElementRef(name = "wholeManufacturedMaterial", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UVManufacturedMaterial> wholeManufacturedMaterial;
    @XmlElementRef(name = "wholeOrganization", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UVOrganization> wholeOrganization;
    @XmlElementRef(name = "wholeEntityGroup", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UVEntityGroup> wholeEntityGroup;
    @XmlElementRef(name = "wholeEntity", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UVEntity> wholeEntity;
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
     * Gets the value of the wholePerson1 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVPerson }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UVPerson> getWholePerson1() {
        return wholePerson1;
    }

    /**
     * Sets the value of the wholePerson1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVPerson }{@code >}
     *     
     */
    public void setWholePerson1(JAXBElement<COCTMT840000UVPerson> value) {
        this.wholePerson1 = value;
    }

    /**
     * Gets the value of the wholeLivingSubject1 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVLivingSubject }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UVLivingSubject> getWholeLivingSubject1() {
        return wholeLivingSubject1;
    }

    /**
     * Sets the value of the wholeLivingSubject1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVLivingSubject }{@code >}
     *     
     */
    public void setWholeLivingSubject1(JAXBElement<COCTMT840000UVLivingSubject> value) {
        this.wholeLivingSubject1 = value;
    }

    /**
     * Gets the value of the wholeNonPersonLivingSubject1 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVNonPersonLivingSubject }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UVNonPersonLivingSubject> getWholeNonPersonLivingSubject1() {
        return wholeNonPersonLivingSubject1;
    }

    /**
     * Sets the value of the wholeNonPersonLivingSubject1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVNonPersonLivingSubject }{@code >}
     *     
     */
    public void setWholeNonPersonLivingSubject1(JAXBElement<COCTMT840000UVNonPersonLivingSubject> value) {
        this.wholeNonPersonLivingSubject1 = value;
    }

    /**
     * Gets the value of the wholePlace1 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVPlace }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UVPlace> getWholePlace1() {
        return wholePlace1;
    }

    /**
     * Sets the value of the wholePlace1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVPlace }{@code >}
     *     
     */
    public void setWholePlace1(JAXBElement<COCTMT840000UVPlace> value) {
        this.wholePlace1 = value;
    }

    /**
     * Gets the value of the wholeMaterial property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVMaterial }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UVMaterial> getWholeMaterial() {
        return wholeMaterial;
    }

    /**
     * Sets the value of the wholeMaterial property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVMaterial }{@code >}
     *     
     */
    public void setWholeMaterial(JAXBElement<COCTMT840000UVMaterial> value) {
        this.wholeMaterial = value;
    }

    /**
     * Gets the value of the wholeManufacturedMaterial property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVManufacturedMaterial }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UVManufacturedMaterial> getWholeManufacturedMaterial() {
        return wholeManufacturedMaterial;
    }

    /**
     * Sets the value of the wholeManufacturedMaterial property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVManufacturedMaterial }{@code >}
     *     
     */
    public void setWholeManufacturedMaterial(JAXBElement<COCTMT840000UVManufacturedMaterial> value) {
        this.wholeManufacturedMaterial = value;
    }

    /**
     * Gets the value of the wholeOrganization property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVOrganization }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UVOrganization> getWholeOrganization() {
        return wholeOrganization;
    }

    /**
     * Sets the value of the wholeOrganization property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVOrganization }{@code >}
     *     
     */
    public void setWholeOrganization(JAXBElement<COCTMT840000UVOrganization> value) {
        this.wholeOrganization = value;
    }

    /**
     * Gets the value of the wholeEntityGroup property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVEntityGroup }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UVEntityGroup> getWholeEntityGroup() {
        return wholeEntityGroup;
    }

    /**
     * Sets the value of the wholeEntityGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVEntityGroup }{@code >}
     *     
     */
    public void setWholeEntityGroup(JAXBElement<COCTMT840000UVEntityGroup> value) {
        this.wholeEntityGroup = value;
    }

    /**
     * Gets the value of the wholeEntity property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVEntity }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UVEntity> getWholeEntity() {
        return wholeEntity;
    }

    /**
     * Sets the value of the wholeEntity property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVEntity }{@code >}
     *     
     */
    public void setWholeEntity(JAXBElement<COCTMT840000UVEntity> value) {
        this.wholeEntity = value;
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
