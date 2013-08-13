
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
 * <p>Java class for COCT_MT841400UV.Member complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT841400UV.Member">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="id" type="{urn:hl7-org:v3}II" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="code" type="{urn:hl7-org:v3}CE" minOccurs="0"/>
 *         &lt;element name="statusCode" type="{urn:hl7-org:v3}CS" minOccurs="0"/>
 *         &lt;element name="effectiveTime" type="{urn:hl7-org:v3}IVL_TS" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;choice>
 *             &lt;element name="memberPerson1" type="{urn:hl7-org:v3}COCT_MT840000UV.Person" minOccurs="0"/>
 *             &lt;element name="memberLivingSubject1" type="{urn:hl7-org:v3}COCT_MT840000UV.LivingSubject" minOccurs="0"/>
 *             &lt;element name="memberNonPersonLivingSubject1" type="{urn:hl7-org:v3}COCT_MT840000UV.NonPersonLivingSubject" minOccurs="0"/>
 *           &lt;/choice>
 *           &lt;choice>
 *             &lt;element name="memberPlace1" type="{urn:hl7-org:v3}COCT_MT840000UV.Place" minOccurs="0"/>
 *             &lt;choice>
 *               &lt;element name="memberMaterial" type="{urn:hl7-org:v3}COCT_MT840000UV.Material" minOccurs="0"/>
 *               &lt;element name="memberManufacturedMaterial" type="{urn:hl7-org:v3}COCT_MT840000UV.ManufacturedMaterial" minOccurs="0"/>
 *             &lt;/choice>
 *           &lt;/choice>
 *           &lt;element name="memberOrganization" type="{urn:hl7-org:v3}COCT_MT840000UV.Organization" minOccurs="0"/>
 *           &lt;element name="memberEntityGroup" type="{urn:hl7-org:v3}COCT_MT840000UV.EntityGroup" minOccurs="0"/>
 *           &lt;element name="memberEntity" type="{urn:hl7-org:v3}COCT_MT840000UV.Entity" minOccurs="0"/>
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
@XmlType(name = "COCT_MT841400UV.Member", propOrder = {
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
public class COCTMT841400UVMember {

    protected List<CS> realmCode;
    protected II typeId;
    protected List<II> templateId;
    protected List<II> id;
    protected CE code;
    protected CS statusCode;
    protected IVLTS effectiveTime;
    @XmlElementRef(name = "memberPerson1", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UVPerson> memberPerson1;
    @XmlElementRef(name = "memberLivingSubject1", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UVLivingSubject> memberLivingSubject1;
    @XmlElementRef(name = "memberNonPersonLivingSubject1", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UVNonPersonLivingSubject> memberNonPersonLivingSubject1;
    @XmlElementRef(name = "memberPlace1", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UVPlace> memberPlace1;
    @XmlElementRef(name = "memberMaterial", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UVMaterial> memberMaterial;
    @XmlElementRef(name = "memberManufacturedMaterial", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UVManufacturedMaterial> memberManufacturedMaterial;
    @XmlElementRef(name = "memberOrganization", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UVOrganization> memberOrganization;
    @XmlElementRef(name = "memberEntityGroup", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UVEntityGroup> memberEntityGroup;
    @XmlElementRef(name = "memberEntity", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UVEntity> memberEntity;
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
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the id property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link II }
     * 
     * 
     */
    public List<II> getId() {
        if (id == null) {
            id = new ArrayList<II>();
        }
        return this.id;
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
     * Gets the value of the memberPerson1 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVPerson }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UVPerson> getMemberPerson1() {
        return memberPerson1;
    }

    /**
     * Sets the value of the memberPerson1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVPerson }{@code >}
     *     
     */
    public void setMemberPerson1(JAXBElement<COCTMT840000UVPerson> value) {
        this.memberPerson1 = value;
    }

    /**
     * Gets the value of the memberLivingSubject1 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVLivingSubject }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UVLivingSubject> getMemberLivingSubject1() {
        return memberLivingSubject1;
    }

    /**
     * Sets the value of the memberLivingSubject1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVLivingSubject }{@code >}
     *     
     */
    public void setMemberLivingSubject1(JAXBElement<COCTMT840000UVLivingSubject> value) {
        this.memberLivingSubject1 = value;
    }

    /**
     * Gets the value of the memberNonPersonLivingSubject1 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVNonPersonLivingSubject }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UVNonPersonLivingSubject> getMemberNonPersonLivingSubject1() {
        return memberNonPersonLivingSubject1;
    }

    /**
     * Sets the value of the memberNonPersonLivingSubject1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVNonPersonLivingSubject }{@code >}
     *     
     */
    public void setMemberNonPersonLivingSubject1(JAXBElement<COCTMT840000UVNonPersonLivingSubject> value) {
        this.memberNonPersonLivingSubject1 = value;
    }

    /**
     * Gets the value of the memberPlace1 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVPlace }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UVPlace> getMemberPlace1() {
        return memberPlace1;
    }

    /**
     * Sets the value of the memberPlace1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVPlace }{@code >}
     *     
     */
    public void setMemberPlace1(JAXBElement<COCTMT840000UVPlace> value) {
        this.memberPlace1 = value;
    }

    /**
     * Gets the value of the memberMaterial property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVMaterial }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UVMaterial> getMemberMaterial() {
        return memberMaterial;
    }

    /**
     * Sets the value of the memberMaterial property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVMaterial }{@code >}
     *     
     */
    public void setMemberMaterial(JAXBElement<COCTMT840000UVMaterial> value) {
        this.memberMaterial = value;
    }

    /**
     * Gets the value of the memberManufacturedMaterial property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVManufacturedMaterial }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UVManufacturedMaterial> getMemberManufacturedMaterial() {
        return memberManufacturedMaterial;
    }

    /**
     * Sets the value of the memberManufacturedMaterial property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVManufacturedMaterial }{@code >}
     *     
     */
    public void setMemberManufacturedMaterial(JAXBElement<COCTMT840000UVManufacturedMaterial> value) {
        this.memberManufacturedMaterial = value;
    }

    /**
     * Gets the value of the memberOrganization property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVOrganization }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UVOrganization> getMemberOrganization() {
        return memberOrganization;
    }

    /**
     * Sets the value of the memberOrganization property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVOrganization }{@code >}
     *     
     */
    public void setMemberOrganization(JAXBElement<COCTMT840000UVOrganization> value) {
        this.memberOrganization = value;
    }

    /**
     * Gets the value of the memberEntityGroup property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVEntityGroup }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UVEntityGroup> getMemberEntityGroup() {
        return memberEntityGroup;
    }

    /**
     * Sets the value of the memberEntityGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVEntityGroup }{@code >}
     *     
     */
    public void setMemberEntityGroup(JAXBElement<COCTMT840000UVEntityGroup> value) {
        this.memberEntityGroup = value;
    }

    /**
     * Gets the value of the memberEntity property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVEntity }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UVEntity> getMemberEntity() {
        return memberEntity;
    }

    /**
     * Sets the value of the memberEntity property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVEntity }{@code >}
     *     
     */
    public void setMemberEntity(JAXBElement<COCTMT840000UVEntity> value) {
        this.memberEntity = value;
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
