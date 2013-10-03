
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for COCT_MT841400UV09.Member complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT841400UV09.Member">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="id" type="{urn:hl7-org:v3}DSET_II" minOccurs="0"/>
 *         &lt;element name="code" type="{urn:hl7-org:v3}CD" minOccurs="0"/>
 *         &lt;element name="statusCode" type="{urn:hl7-org:v3}CS" minOccurs="0"/>
 *         &lt;element name="effectiveTime" type="{urn:hl7-org:v3}IVL_TS" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;choice>
 *             &lt;element name="memberPerson1" type="{urn:hl7-org:v3}COCT_MT840000UV09.Person" minOccurs="0"/>
 *             &lt;element name="memberLivingSubject1" type="{urn:hl7-org:v3}COCT_MT840000UV09.LivingSubject" minOccurs="0"/>
 *             &lt;element name="memberNonPersonLivingSubject1" type="{urn:hl7-org:v3}COCT_MT840000UV09.NonPersonLivingSubject" minOccurs="0"/>
 *           &lt;/choice>
 *           &lt;choice>
 *             &lt;element name="memberPlace1" type="{urn:hl7-org:v3}COCT_MT840000UV09.Place" minOccurs="0"/>
 *             &lt;choice>
 *               &lt;element name="memberMaterial" type="{urn:hl7-org:v3}COCT_MT840000UV09.Material" minOccurs="0"/>
 *               &lt;element name="memberManufacturedMaterial" type="{urn:hl7-org:v3}COCT_MT840000UV09.ManufacturedMaterial" minOccurs="0"/>
 *             &lt;/choice>
 *           &lt;/choice>
 *           &lt;element name="memberOrganization" type="{urn:hl7-org:v3}COCT_MT840000UV09.Organization" minOccurs="0"/>
 *           &lt;element name="memberEntityGroup" type="{urn:hl7-org:v3}COCT_MT840000UV09.EntityGroup" minOccurs="0"/>
 *           &lt;element name="memberEntity" type="{urn:hl7-org:v3}COCT_MT840000UV09.Entity" minOccurs="0"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:hl7-org:v3}InfrastructureRootAttributes"/>
 *       &lt;attribute name="nullFlavor" type="{urn:hl7-org:v3}NullFlavor" />
 *       &lt;attribute name="classCode" use="required" type="{urn:hl7-org:v3}RoleClassMember" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "COCT_MT841400UV09.Member", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "id",
    "code",
    "statusCode",
    "effectiveTime",
    "memberPerson1",
    "memberLivingSubject1",
    "memberNonPersonLivingSubject1",
    "memberPlace1",
    "memberMaterial",
    "memberManufacturedMaterial",
    "memberOrganization",
    "memberEntityGroup",
    "memberEntity"
})
public class COCTMT841400UV09Member {

    protected DSETCS realmCode;
    protected II typeId;
    protected LISTII templateId;
    protected DSETII id;
    protected CD code;
    protected CS statusCode;
    protected IVLTS effectiveTime;
    @XmlElementRef(name = "memberPerson1", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UV09Person> memberPerson1;
    @XmlElementRef(name = "memberLivingSubject1", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UV09LivingSubject> memberLivingSubject1;
    @XmlElementRef(name = "memberNonPersonLivingSubject1", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UV09NonPersonLivingSubject> memberNonPersonLivingSubject1;
    @XmlElementRef(name = "memberPlace1", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UV09Place> memberPlace1;
    @XmlElementRef(name = "memberMaterial", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UV09Material> memberMaterial;
    @XmlElementRef(name = "memberManufacturedMaterial", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UV09ManufacturedMaterial> memberManufacturedMaterial;
    @XmlElementRef(name = "memberOrganization", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UV09Organization> memberOrganization;
    @XmlElementRef(name = "memberEntityGroup", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UV09EntityGroup> memberEntityGroup;
    @XmlElementRef(name = "memberEntity", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UV09Entity> memberEntity;
    @XmlAttribute(name = "nullFlavor")
    protected NullFlavor nullFlavor;
    @XmlAttribute(name = "classCode", required = true)
    protected RoleClassMember classCode;

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
     * Gets the value of the memberPerson1 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09Person }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UV09Person> getMemberPerson1() {
        return memberPerson1;
    }

    /**
     * Sets the value of the memberPerson1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09Person }{@code >}
     *     
     */
    public void setMemberPerson1(JAXBElement<COCTMT840000UV09Person> value) {
        this.memberPerson1 = value;
    }

    /**
     * Gets the value of the memberLivingSubject1 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09LivingSubject }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UV09LivingSubject> getMemberLivingSubject1() {
        return memberLivingSubject1;
    }

    /**
     * Sets the value of the memberLivingSubject1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09LivingSubject }{@code >}
     *     
     */
    public void setMemberLivingSubject1(JAXBElement<COCTMT840000UV09LivingSubject> value) {
        this.memberLivingSubject1 = value;
    }

    /**
     * Gets the value of the memberNonPersonLivingSubject1 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09NonPersonLivingSubject }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UV09NonPersonLivingSubject> getMemberNonPersonLivingSubject1() {
        return memberNonPersonLivingSubject1;
    }

    /**
     * Sets the value of the memberNonPersonLivingSubject1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09NonPersonLivingSubject }{@code >}
     *     
     */
    public void setMemberNonPersonLivingSubject1(JAXBElement<COCTMT840000UV09NonPersonLivingSubject> value) {
        this.memberNonPersonLivingSubject1 = value;
    }

    /**
     * Gets the value of the memberPlace1 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09Place }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UV09Place> getMemberPlace1() {
        return memberPlace1;
    }

    /**
     * Sets the value of the memberPlace1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09Place }{@code >}
     *     
     */
    public void setMemberPlace1(JAXBElement<COCTMT840000UV09Place> value) {
        this.memberPlace1 = value;
    }

    /**
     * Gets the value of the memberMaterial property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09Material }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UV09Material> getMemberMaterial() {
        return memberMaterial;
    }

    /**
     * Sets the value of the memberMaterial property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09Material }{@code >}
     *     
     */
    public void setMemberMaterial(JAXBElement<COCTMT840000UV09Material> value) {
        this.memberMaterial = value;
    }

    /**
     * Gets the value of the memberManufacturedMaterial property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09ManufacturedMaterial }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UV09ManufacturedMaterial> getMemberManufacturedMaterial() {
        return memberManufacturedMaterial;
    }

    /**
     * Sets the value of the memberManufacturedMaterial property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09ManufacturedMaterial }{@code >}
     *     
     */
    public void setMemberManufacturedMaterial(JAXBElement<COCTMT840000UV09ManufacturedMaterial> value) {
        this.memberManufacturedMaterial = value;
    }

    /**
     * Gets the value of the memberOrganization property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09Organization }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UV09Organization> getMemberOrganization() {
        return memberOrganization;
    }

    /**
     * Sets the value of the memberOrganization property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09Organization }{@code >}
     *     
     */
    public void setMemberOrganization(JAXBElement<COCTMT840000UV09Organization> value) {
        this.memberOrganization = value;
    }

    /**
     * Gets the value of the memberEntityGroup property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09EntityGroup }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UV09EntityGroup> getMemberEntityGroup() {
        return memberEntityGroup;
    }

    /**
     * Sets the value of the memberEntityGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09EntityGroup }{@code >}
     *     
     */
    public void setMemberEntityGroup(JAXBElement<COCTMT840000UV09EntityGroup> value) {
        this.memberEntityGroup = value;
    }

    /**
     * Gets the value of the memberEntity property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09Entity }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UV09Entity> getMemberEntity() {
        return memberEntity;
    }

    /**
     * Sets the value of the memberEntity property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09Entity }{@code >}
     *     
     */
    public void setMemberEntity(JAXBElement<COCTMT840000UV09Entity> value) {
        this.memberEntity = value;
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
     *     {@link RoleClassMember }
     *     
     */
    public RoleClassMember getClassCode() {
        return classCode;
    }

    /**
     * Sets the value of the classCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link RoleClassMember }
     *     
     */
    public void setClassCode(RoleClassMember value) {
        this.classCode = value;
    }

}
