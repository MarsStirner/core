
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for COCT_MT550000UV07.InvestigativeSubject complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT550000UV07.InvestigativeSubject">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="id" type="{urn:hl7-org:v3}II" minOccurs="0"/>
 *         &lt;element name="code" type="{urn:hl7-org:v3}CE" minOccurs="0"/>
 *         &lt;element name="effectiveTime" type="{urn:hl7-org:v3}IVL_TS" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;choice>
 *             &lt;element name="subjectPerson1" type="{urn:hl7-org:v3}COCT_MT840000UV09.Person" minOccurs="0"/>
 *             &lt;element name="subjectLivingSubject1" type="{urn:hl7-org:v3}COCT_MT840000UV09.LivingSubject" minOccurs="0"/>
 *             &lt;element name="subjectNonPersonLivingSubject1" type="{urn:hl7-org:v3}COCT_MT840000UV09.NonPersonLivingSubject" minOccurs="0"/>
 *           &lt;/choice>
 *           &lt;choice>
 *             &lt;element name="subjectPlace1" type="{urn:hl7-org:v3}COCT_MT840000UV09.Place" minOccurs="0"/>
 *             &lt;choice>
 *               &lt;element name="subjectMaterial" type="{urn:hl7-org:v3}COCT_MT840000UV09.Material" minOccurs="0"/>
 *               &lt;element name="subjectManufacturedMaterial" type="{urn:hl7-org:v3}COCT_MT840000UV09.ManufacturedMaterial" minOccurs="0"/>
 *             &lt;/choice>
 *           &lt;/choice>
 *           &lt;element name="subjectOrganization" type="{urn:hl7-org:v3}COCT_MT840000UV09.Organization" minOccurs="0"/>
 *           &lt;element name="subjectEntityGroup" type="{urn:hl7-org:v3}COCT_MT840000UV09.EntityGroup" minOccurs="0"/>
 *           &lt;element name="subjectEntity" type="{urn:hl7-org:v3}COCT_MT840000UV09.Entity" minOccurs="0"/>
 *         &lt;/choice>
 *         &lt;element name="investigationSponsor" type="{urn:hl7-org:v3}COCT_MT150000UV02.Organization" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:hl7-org:v3}InfrastructureRootAttributes"/>
 *       &lt;attribute name="classCode" use="required" type="{urn:hl7-org:v3}RoleClassInvestigationSubject" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "COCT_MT550000UV07.InvestigativeSubject", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "id",
    "code",
    "effectiveTime",
    "subjectPerson1",
    "subjectLivingSubject1",
    "subjectNonPersonLivingSubject1",
    "subjectPlace1",
    "subjectMaterial",
    "subjectManufacturedMaterial",
    "subjectOrganization",
    "subjectEntityGroup",
    "subjectEntity",
    "investigationSponsor"
})
public class COCTMT550000UV07InvestigativeSubject {

    protected List<CS> realmCode;
    protected II typeId;
    protected List<II> templateId;
    protected II id;
    protected CE code;
    protected IVLTS effectiveTime;
    @XmlElementRef(name = "subjectPerson1", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UV09Person> subjectPerson1;
    @XmlElementRef(name = "subjectLivingSubject1", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UV09LivingSubject> subjectLivingSubject1;
    @XmlElementRef(name = "subjectNonPersonLivingSubject1", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UV09NonPersonLivingSubject> subjectNonPersonLivingSubject1;
    @XmlElementRef(name = "subjectPlace1", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UV09Place> subjectPlace1;
    @XmlElementRef(name = "subjectMaterial", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UV09Material> subjectMaterial;
    @XmlElementRef(name = "subjectManufacturedMaterial", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UV09ManufacturedMaterial> subjectManufacturedMaterial;
    @XmlElementRef(name = "subjectOrganization", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UV09Organization> subjectOrganization;
    @XmlElementRef(name = "subjectEntityGroup", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UV09EntityGroup> subjectEntityGroup;
    @XmlElementRef(name = "subjectEntity", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UV09Entity> subjectEntity;
    @XmlElementRef(name = "investigationSponsor", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT150000UV02Organization> investigationSponsor;
    @XmlAttribute(name = "classCode", required = true)
    protected RoleClassInvestigationSubject classCode;

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
     * Gets the value of the subjectPerson1 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09Person }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UV09Person> getSubjectPerson1() {
        return subjectPerson1;
    }

    /**
     * Sets the value of the subjectPerson1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09Person }{@code >}
     *     
     */
    public void setSubjectPerson1(JAXBElement<COCTMT840000UV09Person> value) {
        this.subjectPerson1 = value;
    }

    /**
     * Gets the value of the subjectLivingSubject1 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09LivingSubject }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UV09LivingSubject> getSubjectLivingSubject1() {
        return subjectLivingSubject1;
    }

    /**
     * Sets the value of the subjectLivingSubject1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09LivingSubject }{@code >}
     *     
     */
    public void setSubjectLivingSubject1(JAXBElement<COCTMT840000UV09LivingSubject> value) {
        this.subjectLivingSubject1 = value;
    }

    /**
     * Gets the value of the subjectNonPersonLivingSubject1 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09NonPersonLivingSubject }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UV09NonPersonLivingSubject> getSubjectNonPersonLivingSubject1() {
        return subjectNonPersonLivingSubject1;
    }

    /**
     * Sets the value of the subjectNonPersonLivingSubject1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09NonPersonLivingSubject }{@code >}
     *     
     */
    public void setSubjectNonPersonLivingSubject1(JAXBElement<COCTMT840000UV09NonPersonLivingSubject> value) {
        this.subjectNonPersonLivingSubject1 = value;
    }

    /**
     * Gets the value of the subjectPlace1 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09Place }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UV09Place> getSubjectPlace1() {
        return subjectPlace1;
    }

    /**
     * Sets the value of the subjectPlace1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09Place }{@code >}
     *     
     */
    public void setSubjectPlace1(JAXBElement<COCTMT840000UV09Place> value) {
        this.subjectPlace1 = value;
    }

    /**
     * Gets the value of the subjectMaterial property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09Material }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UV09Material> getSubjectMaterial() {
        return subjectMaterial;
    }

    /**
     * Sets the value of the subjectMaterial property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09Material }{@code >}
     *     
     */
    public void setSubjectMaterial(JAXBElement<COCTMT840000UV09Material> value) {
        this.subjectMaterial = value;
    }

    /**
     * Gets the value of the subjectManufacturedMaterial property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09ManufacturedMaterial }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UV09ManufacturedMaterial> getSubjectManufacturedMaterial() {
        return subjectManufacturedMaterial;
    }

    /**
     * Sets the value of the subjectManufacturedMaterial property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09ManufacturedMaterial }{@code >}
     *     
     */
    public void setSubjectManufacturedMaterial(JAXBElement<COCTMT840000UV09ManufacturedMaterial> value) {
        this.subjectManufacturedMaterial = value;
    }

    /**
     * Gets the value of the subjectOrganization property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09Organization }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UV09Organization> getSubjectOrganization() {
        return subjectOrganization;
    }

    /**
     * Sets the value of the subjectOrganization property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09Organization }{@code >}
     *     
     */
    public void setSubjectOrganization(JAXBElement<COCTMT840000UV09Organization> value) {
        this.subjectOrganization = value;
    }

    /**
     * Gets the value of the subjectEntityGroup property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09EntityGroup }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UV09EntityGroup> getSubjectEntityGroup() {
        return subjectEntityGroup;
    }

    /**
     * Sets the value of the subjectEntityGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09EntityGroup }{@code >}
     *     
     */
    public void setSubjectEntityGroup(JAXBElement<COCTMT840000UV09EntityGroup> value) {
        this.subjectEntityGroup = value;
    }

    /**
     * Gets the value of the subjectEntity property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09Entity }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UV09Entity> getSubjectEntity() {
        return subjectEntity;
    }

    /**
     * Sets the value of the subjectEntity property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UV09Entity }{@code >}
     *     
     */
    public void setSubjectEntity(JAXBElement<COCTMT840000UV09Entity> value) {
        this.subjectEntity = value;
    }

    /**
     * Gets the value of the investigationSponsor property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT150000UV02Organization }{@code >}
     *     
     */
    public JAXBElement<COCTMT150000UV02Organization> getInvestigationSponsor() {
        return investigationSponsor;
    }

    /**
     * Sets the value of the investigationSponsor property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT150000UV02Organization }{@code >}
     *     
     */
    public void setInvestigationSponsor(JAXBElement<COCTMT150000UV02Organization> value) {
        this.investigationSponsor = value;
    }

    /**
     * Gets the value of the classCode property.
     * 
     * @return
     *     possible object is
     *     {@link RoleClassInvestigationSubject }
     *     
     */
    public RoleClassInvestigationSubject getClassCode() {
        return classCode;
    }

    /**
     * Sets the value of the classCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link RoleClassInvestigationSubject }
     *     
     */
    public void setClassCode(RoleClassInvestigationSubject value) {
        this.classCode = value;
    }

}
