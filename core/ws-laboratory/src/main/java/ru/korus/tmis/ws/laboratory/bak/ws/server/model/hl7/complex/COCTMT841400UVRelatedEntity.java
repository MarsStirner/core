
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
 * <p>Java class for COCT_MT841400UV.RelatedEntity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT841400UV.RelatedEntity">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="id" type="{urn:hl7-org:v3}II" minOccurs="0"/>
 *         &lt;element name="code" type="{urn:hl7-org:v3}CE" minOccurs="0"/>
 *         &lt;element name="statusCode" type="{urn:hl7-org:v3}CS" minOccurs="0"/>
 *         &lt;element name="effectiveTime" type="{urn:hl7-org:v3}IVL_TS" minOccurs="0"/>
 *         &lt;element name="confidentialityCode" type="{urn:hl7-org:v3}CE" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;choice>
 *             &lt;element name="relatedPerson1" type="{urn:hl7-org:v3}COCT_MT840000UV.Person"/>
 *             &lt;element name="relatedLivingSubject1" type="{urn:hl7-org:v3}COCT_MT840000UV.LivingSubject"/>
 *             &lt;element name="relatedNonPersonLivingSubject1" type="{urn:hl7-org:v3}COCT_MT840000UV.NonPersonLivingSubject"/>
 *           &lt;/choice>
 *           &lt;choice>
 *             &lt;element name="relatedPlace1" type="{urn:hl7-org:v3}COCT_MT840000UV.Place"/>
 *             &lt;choice>
 *               &lt;element name="relatedMaterial" type="{urn:hl7-org:v3}COCT_MT840000UV.Material"/>
 *               &lt;element name="relatedManufacturedMaterial" type="{urn:hl7-org:v3}COCT_MT840000UV.ManufacturedMaterial"/>
 *             &lt;/choice>
 *           &lt;/choice>
 *           &lt;element name="relatedOrganization" type="{urn:hl7-org:v3}COCT_MT840000UV.Organization"/>
 *           &lt;element name="relatedEntityGroup" type="{urn:hl7-org:v3}COCT_MT840000UV.EntityGroup"/>
 *           &lt;element name="relatedEntity" type="{urn:hl7-org:v3}COCT_MT840000UV.Entity"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:hl7-org:v3}InfrastructureRootAttributes"/>
 *       &lt;attribute name="nullFlavor" type="{urn:hl7-org:v3}NullFlavor" />
 *       &lt;attribute name="classCode" use="required" type="{urn:hl7-org:v3}RoleClassMutualRelationship" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "COCT_MT841400UV.RelatedEntity", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "id",
    "code",
    "statusCode",
    "effectiveTime",
    "confidentialityCode",
    "relatedPerson1",
    "relatedLivingSubject1",
    "relatedNonPersonLivingSubject1",
    "relatedPlace1",
    "relatedMaterial",
    "relatedManufacturedMaterial",
    "relatedOrganization",
    "relatedEntityGroup",
    "relatedEntity"
})
public class COCTMT841400UVRelatedEntity {

    protected List<CS> realmCode;
    protected II typeId;
    protected List<II> templateId;
    protected II id;
    protected CE code;
    protected CS statusCode;
    protected IVLTS effectiveTime;
    protected List<CE> confidentialityCode;
    @XmlElementRef(name = "relatedPerson1", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UVPerson> relatedPerson1;
    @XmlElementRef(name = "relatedLivingSubject1", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UVLivingSubject> relatedLivingSubject1;
    @XmlElementRef(name = "relatedNonPersonLivingSubject1", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UVNonPersonLivingSubject> relatedNonPersonLivingSubject1;
    @XmlElementRef(name = "relatedPlace1", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UVPlace> relatedPlace1;
    @XmlElementRef(name = "relatedMaterial", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UVMaterial> relatedMaterial;
    @XmlElementRef(name = "relatedManufacturedMaterial", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UVManufacturedMaterial> relatedManufacturedMaterial;
    @XmlElementRef(name = "relatedOrganization", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UVOrganization> relatedOrganization;
    @XmlElementRef(name = "relatedEntityGroup", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UVEntityGroup> relatedEntityGroup;
    @XmlElementRef(name = "relatedEntity", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UVEntity> relatedEntity;
    @XmlAttribute(name = "nullFlavor")
    protected List<String> nullFlavor;
    @XmlAttribute(name = "classCode", required = true)
    protected List<String> classCode;

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
     * Gets the value of the confidentialityCode property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the confidentialityCode property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConfidentialityCode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CE }
     * 
     * 
     */
    public List<CE> getConfidentialityCode() {
        if (confidentialityCode == null) {
            confidentialityCode = new ArrayList<CE>();
        }
        return this.confidentialityCode;
    }

    /**
     * Gets the value of the relatedPerson1 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVPerson }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UVPerson> getRelatedPerson1() {
        return relatedPerson1;
    }

    /**
     * Sets the value of the relatedPerson1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVPerson }{@code >}
     *     
     */
    public void setRelatedPerson1(JAXBElement<COCTMT840000UVPerson> value) {
        this.relatedPerson1 = value;
    }

    /**
     * Gets the value of the relatedLivingSubject1 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVLivingSubject }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UVLivingSubject> getRelatedLivingSubject1() {
        return relatedLivingSubject1;
    }

    /**
     * Sets the value of the relatedLivingSubject1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVLivingSubject }{@code >}
     *     
     */
    public void setRelatedLivingSubject1(JAXBElement<COCTMT840000UVLivingSubject> value) {
        this.relatedLivingSubject1 = value;
    }

    /**
     * Gets the value of the relatedNonPersonLivingSubject1 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVNonPersonLivingSubject }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UVNonPersonLivingSubject> getRelatedNonPersonLivingSubject1() {
        return relatedNonPersonLivingSubject1;
    }

    /**
     * Sets the value of the relatedNonPersonLivingSubject1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVNonPersonLivingSubject }{@code >}
     *     
     */
    public void setRelatedNonPersonLivingSubject1(JAXBElement<COCTMT840000UVNonPersonLivingSubject> value) {
        this.relatedNonPersonLivingSubject1 = value;
    }

    /**
     * Gets the value of the relatedPlace1 property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVPlace }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UVPlace> getRelatedPlace1() {
        return relatedPlace1;
    }

    /**
     * Sets the value of the relatedPlace1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVPlace }{@code >}
     *     
     */
    public void setRelatedPlace1(JAXBElement<COCTMT840000UVPlace> value) {
        this.relatedPlace1 = value;
    }

    /**
     * Gets the value of the relatedMaterial property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVMaterial }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UVMaterial> getRelatedMaterial() {
        return relatedMaterial;
    }

    /**
     * Sets the value of the relatedMaterial property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVMaterial }{@code >}
     *     
     */
    public void setRelatedMaterial(JAXBElement<COCTMT840000UVMaterial> value) {
        this.relatedMaterial = value;
    }

    /**
     * Gets the value of the relatedManufacturedMaterial property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVManufacturedMaterial }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UVManufacturedMaterial> getRelatedManufacturedMaterial() {
        return relatedManufacturedMaterial;
    }

    /**
     * Sets the value of the relatedManufacturedMaterial property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVManufacturedMaterial }{@code >}
     *     
     */
    public void setRelatedManufacturedMaterial(JAXBElement<COCTMT840000UVManufacturedMaterial> value) {
        this.relatedManufacturedMaterial = value;
    }

    /**
     * Gets the value of the relatedOrganization property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVOrganization }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UVOrganization> getRelatedOrganization() {
        return relatedOrganization;
    }

    /**
     * Sets the value of the relatedOrganization property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVOrganization }{@code >}
     *     
     */
    public void setRelatedOrganization(JAXBElement<COCTMT840000UVOrganization> value) {
        this.relatedOrganization = value;
    }

    /**
     * Gets the value of the relatedEntityGroup property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVEntityGroup }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UVEntityGroup> getRelatedEntityGroup() {
        return relatedEntityGroup;
    }

    /**
     * Sets the value of the relatedEntityGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVEntityGroup }{@code >}
     *     
     */
    public void setRelatedEntityGroup(JAXBElement<COCTMT840000UVEntityGroup> value) {
        this.relatedEntityGroup = value;
    }

    /**
     * Gets the value of the relatedEntity property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVEntity }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UVEntity> getRelatedEntity() {
        return relatedEntity;
    }

    /**
     * Sets the value of the relatedEntity property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVEntity }{@code >}
     *     
     */
    public void setRelatedEntity(JAXBElement<COCTMT840000UVEntity> value) {
        this.relatedEntity = value;
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
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the classCode property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getClassCode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getClassCode() {
        if (classCode == null) {
            classCode = new ArrayList<String>();
        }
        return this.classCode;
    }

}
