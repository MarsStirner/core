
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for COCT_MT841200UV09.PartOfWhole complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT841200UV09.PartOfWhole">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="id" type="{urn:hl7-org:v3}II" minOccurs="0"/>
 *         &lt;element name="code" type="{urn:hl7-org:v3}CD" minOccurs="0"/>
 *         &lt;element name="statusCode" type="{urn:hl7-org:v3}CS" minOccurs="0"/>
 *         &lt;element name="effectiveTime" type="{urn:hl7-org:v3}IVL_TS" minOccurs="0"/>
 *         &lt;element name="quantity" type="{urn:hl7-org:v3}RTO" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;choice>
 *             &lt;element name="wholePerson1" type="{urn:hl7-org:v3}COCT_MT840000UV09.Person" minOccurs="0"/>
 *             &lt;element name="wholeLivingSubject1" type="{urn:hl7-org:v3}COCT_MT840000UV09.LivingSubject" minOccurs="0"/>
 *             &lt;element name="wholeNonPersonLivingSubject1" type="{urn:hl7-org:v3}COCT_MT840000UV09.NonPersonLivingSubject" minOccurs="0"/>
 *           &lt;/choice>
 *           &lt;choice>
 *             &lt;element name="wholePlace1" type="{urn:hl7-org:v3}COCT_MT840000UV09.Place" minOccurs="0"/>
 *             &lt;choice>
 *               &lt;element name="wholeMaterial" type="{urn:hl7-org:v3}COCT_MT840000UV09.Material" minOccurs="0"/>
 *               &lt;element name="wholeManufacturedMaterial" type="{urn:hl7-org:v3}COCT_MT840000UV09.ManufacturedMaterial" minOccurs="0"/>
 *             &lt;/choice>
 *           &lt;/choice>
 *           &lt;element name="wholeOrganization" type="{urn:hl7-org:v3}COCT_MT840000UV09.Organization" minOccurs="0"/>
 *           &lt;element name="wholeEntityGroup" type="{urn:hl7-org:v3}COCT_MT840000UV09.EntityGroup" minOccurs="0"/>
 *           &lt;element name="wholeEntity" type="{urn:hl7-org:v3}COCT_MT840000UV09.Entity" minOccurs="0"/>
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
@XmlType(name = "COCT_MT841200UV09.PartOfWhole", propOrder = {
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
public class COCTMT841200UV09PartOfWhole {

    protected DSETCS realmCode;
    protected II typeId;
    protected LISTII templateId;
    protected II id;
    protected CD code;
    protected CS statusCode;
    protected IVLTS effectiveTime;
    protected RTO quantity;
    @XmlElementRef(name = "wholePerson1", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UV09Person> wholePerson1;
    @XmlElementRef(name = "wholeLivingSubject1", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UV09LivingSubject> wholeLivingSubject1;
    @XmlElementRef(name = "wholeNonPersonLivingSubject1", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UV09NonPersonLivingSubject> wholeNonPersonLivingSubject1;
    @XmlElementRef(name = "wholePlace1", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UV09Place> wholePlace1;
    @XmlElementRef(name = "wholeMaterial", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UV09Material> wholeMaterial;
    @XmlElementRef(name = "wholeManufacturedMaterial", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UV09ManufacturedMaterial> wholeManufacturedMaterial;
    @XmlElementRef(name = "wholeOrganization", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UV09Organization> wholeOrganization;
    @XmlElementRef(name = "wholeEntityGroup", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UV09EntityGroup> wholeEntityGroup;
    @XmlElementRef(name = "wholeEntity", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UV09Entity> wholeEntity;
    @XmlAttribute(name = "nullFlavor")
    protected NullFlavor nullFlavor;
    @XmlAttribute(name = "classCode", required = true)
    protected RoleClassPart classCode;

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
     * Gets the value of the wholePerson1 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09Person }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UV09Person> getWholePerson1() {
        return wholePerson1;
    }

    /**
     * Sets the value of the wholePerson1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09Person }{@code >}
     *     
     */
    public void setWholePerson1(JAXBElement<COCTMT840000UV09Person> value) {
        this.wholePerson1 = value;
    }

    /**
     * Gets the value of the wholeLivingSubject1 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09LivingSubject }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UV09LivingSubject> getWholeLivingSubject1() {
        return wholeLivingSubject1;
    }

    /**
     * Sets the value of the wholeLivingSubject1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09LivingSubject }{@code >}
     *     
     */
    public void setWholeLivingSubject1(JAXBElement<COCTMT840000UV09LivingSubject> value) {
        this.wholeLivingSubject1 = value;
    }

    /**
     * Gets the value of the wholeNonPersonLivingSubject1 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09NonPersonLivingSubject }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UV09NonPersonLivingSubject> getWholeNonPersonLivingSubject1() {
        return wholeNonPersonLivingSubject1;
    }

    /**
     * Sets the value of the wholeNonPersonLivingSubject1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09NonPersonLivingSubject }{@code >}
     *     
     */
    public void setWholeNonPersonLivingSubject1(JAXBElement<COCTMT840000UV09NonPersonLivingSubject> value) {
        this.wholeNonPersonLivingSubject1 = value;
    }

    /**
     * Gets the value of the wholePlace1 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09Place }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UV09Place> getWholePlace1() {
        return wholePlace1;
    }

    /**
     * Sets the value of the wholePlace1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09Place }{@code >}
     *     
     */
    public void setWholePlace1(JAXBElement<COCTMT840000UV09Place> value) {
        this.wholePlace1 = value;
    }

    /**
     * Gets the value of the wholeMaterial property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09Material }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UV09Material> getWholeMaterial() {
        return wholeMaterial;
    }

    /**
     * Sets the value of the wholeMaterial property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09Material }{@code >}
     *     
     */
    public void setWholeMaterial(JAXBElement<COCTMT840000UV09Material> value) {
        this.wholeMaterial = value;
    }

    /**
     * Gets the value of the wholeManufacturedMaterial property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09ManufacturedMaterial }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UV09ManufacturedMaterial> getWholeManufacturedMaterial() {
        return wholeManufacturedMaterial;
    }

    /**
     * Sets the value of the wholeManufacturedMaterial property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09ManufacturedMaterial }{@code >}
     *     
     */
    public void setWholeManufacturedMaterial(JAXBElement<COCTMT840000UV09ManufacturedMaterial> value) {
        this.wholeManufacturedMaterial = value;
    }

    /**
     * Gets the value of the wholeOrganization property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09Organization }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UV09Organization> getWholeOrganization() {
        return wholeOrganization;
    }

    /**
     * Sets the value of the wholeOrganization property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09Organization }{@code >}
     *     
     */
    public void setWholeOrganization(JAXBElement<COCTMT840000UV09Organization> value) {
        this.wholeOrganization = value;
    }

    /**
     * Gets the value of the wholeEntityGroup property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09EntityGroup }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UV09EntityGroup> getWholeEntityGroup() {
        return wholeEntityGroup;
    }

    /**
     * Sets the value of the wholeEntityGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09EntityGroup }{@code >}
     *     
     */
    public void setWholeEntityGroup(JAXBElement<COCTMT840000UV09EntityGroup> value) {
        this.wholeEntityGroup = value;
    }

    /**
     * Gets the value of the wholeEntity property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09Entity }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UV09Entity> getWholeEntity() {
        return wholeEntity;
    }

    /**
     * Sets the value of the wholeEntity property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09Entity }{@code >}
     *     
     */
    public void setWholeEntity(JAXBElement<COCTMT840000UV09Entity> value) {
        this.wholeEntity = value;
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
     *     {@link RoleClassPart }
     *     
     */
    public RoleClassPart getClassCode() {
        return classCode;
    }

    /**
     * Sets the value of the classCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link RoleClassPart }
     *     
     */
    public void setClassCode(RoleClassPart value) {
        this.classCode = value;
    }

}
