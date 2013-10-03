
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
 * <p>Java class for COCT_MT841500UV09.Material complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT841500UV09.Material">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="id" type="{urn:hl7-org:v3}II" minOccurs="0"/>
 *         &lt;element name="code" type="{urn:hl7-org:v3}CD" minOccurs="0"/>
 *         &lt;element name="quantity" type="{urn:hl7-org:v3}PQ" minOccurs="0"/>
 *         &lt;element name="name" type="{urn:hl7-org:v3}COLL_EN" minOccurs="0"/>
 *         &lt;element name="desc" type="{urn:hl7-org:v3}ED" minOccurs="0"/>
 *         &lt;element name="existenceTime" type="{urn:hl7-org:v3}IVL_TS" minOccurs="0"/>
 *         &lt;element name="riskCode" type="{urn:hl7-org:v3}CD" minOccurs="0"/>
 *         &lt;element name="handlingCode" type="{urn:hl7-org:v3}CD" minOccurs="0"/>
 *         &lt;element name="formCode" type="{urn:hl7-org:v3}CD" minOccurs="0"/>
 *         &lt;element name="asQualifiedEntity" type="{urn:hl7-org:v3}COCT_MT841500UV09.QualifiedEntity" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="asIdentifier" type="{urn:hl7-org:v3}COCT_MT841500UV09.Identifier" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="asEntityInContainer" type="{urn:hl7-org:v3}COCT_MT841500UV09.EntityInContainer" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="asPartOfWhole" type="{urn:hl7-org:v3}COCT_MT841500UV09.PartOfWhole" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="asIngredient" type="{urn:hl7-org:v3}COCT_MT841500UV09.Ingredient" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="asMemberOf" type="{urn:hl7-org:v3}COCT_MT841500UV09.MemberOf" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="asObserved" type="{urn:hl7-org:v3}COCT_MT841500UV09.Observed" minOccurs="0"/>
 *         &lt;element name="asOtherIDs" type="{urn:hl7-org:v3}COCT_MT841500UV09.OtherIDs" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="asManagingEntity" type="{urn:hl7-org:v3}COCT_MT841500UV09.ManagingEntity" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="asProductEntity" type="{urn:hl7-org:v3}COCT_MT841500UV09.ProductEntity" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="contactParty" type="{urn:hl7-org:v3}COCT_MT841500UV09.ContactParty" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="relatedEntity" type="{urn:hl7-org:v3}COCT_MT841500UV09.RelatedEntity" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="part" type="{urn:hl7-org:v3}COCT_MT841500UV09.Part" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="hasIngredients" type="{urn:hl7-org:v3}COCT_MT841500UV09.HasIngredients" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="locatedEntity" type="{urn:hl7-org:v3}COCT_MT841500UV09.LocatedEntity" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="managedEntity" type="{urn:hl7-org:v3}COCT_MT841500UV09.ManagedEntity" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:hl7-org:v3}InfrastructureRootAttributes"/>
 *       &lt;attribute name="classCode" use="required" type="{urn:hl7-org:v3}EntityClassMaterial" />
 *       &lt;attribute name="determinerCode" use="required" type="{urn:hl7-org:v3}EntityDeterminer" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "COCT_MT841500UV09.Material", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "id",
    "code",
    "quantity",
    "name",
    "desc",
    "existenceTime",
    "riskCode",
    "handlingCode",
    "formCode",
    "asQualifiedEntity",
    "asIdentifier",
    "asEntityInContainer",
    "asPartOfWhole",
    "asIngredient",
    "asMemberOf",
    "asObserved",
    "asOtherIDs",
    "asManagingEntity",
    "asProductEntity",
    "contactParty",
    "relatedEntity",
    "part",
    "hasIngredients",
    "locatedEntity",
    "managedEntity"
})
public class COCTMT841500UV09Material {

    protected DSETCS realmCode;
    protected II typeId;
    protected LISTII templateId;
    protected II id;
    protected CD code;
    protected PQ quantity;
    protected COLLEN name;
    protected ED desc;
    protected IVLTS existenceTime;
    protected CD riskCode;
    protected CD handlingCode;
    protected CD formCode;
    @XmlElement(nillable = true)
    protected List<COCTMT841500UV09QualifiedEntity> asQualifiedEntity;
    @XmlElement(nillable = true)
    protected List<COCTMT841500UV09Identifier> asIdentifier;
    @XmlElement(nillable = true)
    protected List<COCTMT841500UV09EntityInContainer> asEntityInContainer;
    @XmlElement(nillable = true)
    protected List<COCTMT841500UV09PartOfWhole> asPartOfWhole;
    @XmlElement(nillable = true)
    protected List<COCTMT841500UV09Ingredient> asIngredient;
    @XmlElement(nillable = true)
    protected List<COCTMT841500UV09MemberOf> asMemberOf;
    @XmlElementRef(name = "asObserved", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT841500UV09Observed> asObserved;
    @XmlElement(nillable = true)
    protected List<COCTMT841500UV09OtherIDs> asOtherIDs;
    @XmlElement(nillable = true)
    protected List<COCTMT841500UV09ManagingEntity> asManagingEntity;
    @XmlElement(nillable = true)
    protected List<COCTMT841500UV09ProductEntity> asProductEntity;
    @XmlElement(nillable = true)
    protected List<COCTMT841500UV09ContactParty> contactParty;
    @XmlElement(nillable = true)
    protected List<COCTMT841500UV09RelatedEntity> relatedEntity;
    @XmlElement(nillable = true)
    protected List<COCTMT841500UV09Part> part;
    @XmlElement(nillable = true)
    protected List<COCTMT841500UV09HasIngredients> hasIngredients;
    @XmlElement(nillable = true)
    protected List<COCTMT841500UV09LocatedEntity> locatedEntity;
    @XmlElement(nillable = true)
    protected List<COCTMT841500UV09ManagedEntity> managedEntity;
    @XmlAttribute(name = "classCode", required = true)
    protected EntityClassMaterial classCode;
    @XmlAttribute(name = "determinerCode", required = true)
    protected EntityDeterminer determinerCode;

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
     * Gets the value of the quantity property.
     * 
     * @return
     *     possible object is
     *     {@link PQ }
     *     
     */
    public PQ getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link PQ }
     *     
     */
    public void setQuantity(PQ value) {
        this.quantity = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link COLLEN }
     *     
     */
    public COLLEN getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link COLLEN }
     *     
     */
    public void setName(COLLEN value) {
        this.name = value;
    }

    /**
     * Gets the value of the desc property.
     * 
     * @return
     *     possible object is
     *     {@link ED }
     *     
     */
    public ED getDesc() {
        return desc;
    }

    /**
     * Sets the value of the desc property.
     * 
     * @param value
     *     allowed object is
     *     {@link ED }
     *     
     */
    public void setDesc(ED value) {
        this.desc = value;
    }

    /**
     * Gets the value of the existenceTime property.
     * 
     * @return
     *     possible object is
     *     {@link IVLTS }
     *     
     */
    public IVLTS getExistenceTime() {
        return existenceTime;
    }

    /**
     * Sets the value of the existenceTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link IVLTS }
     *     
     */
    public void setExistenceTime(IVLTS value) {
        this.existenceTime = value;
    }

    /**
     * Gets the value of the riskCode property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getRiskCode() {
        return riskCode;
    }

    /**
     * Sets the value of the riskCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setRiskCode(CD value) {
        this.riskCode = value;
    }

    /**
     * Gets the value of the handlingCode property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getHandlingCode() {
        return handlingCode;
    }

    /**
     * Sets the value of the handlingCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setHandlingCode(CD value) {
        this.handlingCode = value;
    }

    /**
     * Gets the value of the formCode property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getFormCode() {
        return formCode;
    }

    /**
     * Sets the value of the formCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setFormCode(CD value) {
        this.formCode = value;
    }

    /**
     * Gets the value of the asQualifiedEntity property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the asQualifiedEntity property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAsQualifiedEntity().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT841500UV09QualifiedEntity }
     * 
     * 
     */
    public List<COCTMT841500UV09QualifiedEntity> getAsQualifiedEntity() {
        if (asQualifiedEntity == null) {
            asQualifiedEntity = new ArrayList<COCTMT841500UV09QualifiedEntity>();
        }
        return this.asQualifiedEntity;
    }

    /**
     * Gets the value of the asIdentifier property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the asIdentifier property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAsIdentifier().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT841500UV09Identifier }
     * 
     * 
     */
    public List<COCTMT841500UV09Identifier> getAsIdentifier() {
        if (asIdentifier == null) {
            asIdentifier = new ArrayList<COCTMT841500UV09Identifier>();
        }
        return this.asIdentifier;
    }

    /**
     * Gets the value of the asEntityInContainer property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the asEntityInContainer property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAsEntityInContainer().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT841500UV09EntityInContainer }
     * 
     * 
     */
    public List<COCTMT841500UV09EntityInContainer> getAsEntityInContainer() {
        if (asEntityInContainer == null) {
            asEntityInContainer = new ArrayList<COCTMT841500UV09EntityInContainer>();
        }
        return this.asEntityInContainer;
    }

    /**
     * Gets the value of the asPartOfWhole property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the asPartOfWhole property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAsPartOfWhole().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT841500UV09PartOfWhole }
     * 
     * 
     */
    public List<COCTMT841500UV09PartOfWhole> getAsPartOfWhole() {
        if (asPartOfWhole == null) {
            asPartOfWhole = new ArrayList<COCTMT841500UV09PartOfWhole>();
        }
        return this.asPartOfWhole;
    }

    /**
     * Gets the value of the asIngredient property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the asIngredient property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAsIngredient().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT841500UV09Ingredient }
     * 
     * 
     */
    public List<COCTMT841500UV09Ingredient> getAsIngredient() {
        if (asIngredient == null) {
            asIngredient = new ArrayList<COCTMT841500UV09Ingredient>();
        }
        return this.asIngredient;
    }

    /**
     * Gets the value of the asMemberOf property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the asMemberOf property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAsMemberOf().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT841500UV09MemberOf }
     * 
     * 
     */
    public List<COCTMT841500UV09MemberOf> getAsMemberOf() {
        if (asMemberOf == null) {
            asMemberOf = new ArrayList<COCTMT841500UV09MemberOf>();
        }
        return this.asMemberOf;
    }

    /**
     * Gets the value of the asObserved property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT841500UV09Observed }{@code >}
     *     
     */
    public JAXBElement<COCTMT841500UV09Observed> getAsObserved() {
        return asObserved;
    }

    /**
     * Sets the value of the asObserved property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT841500UV09Observed }{@code >}
     *     
     */
    public void setAsObserved(JAXBElement<COCTMT841500UV09Observed> value) {
        this.asObserved = value;
    }

    /**
     * Gets the value of the asOtherIDs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the asOtherIDs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAsOtherIDs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT841500UV09OtherIDs }
     * 
     * 
     */
    public List<COCTMT841500UV09OtherIDs> getAsOtherIDs() {
        if (asOtherIDs == null) {
            asOtherIDs = new ArrayList<COCTMT841500UV09OtherIDs>();
        }
        return this.asOtherIDs;
    }

    /**
     * Gets the value of the asManagingEntity property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the asManagingEntity property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAsManagingEntity().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT841500UV09ManagingEntity }
     * 
     * 
     */
    public List<COCTMT841500UV09ManagingEntity> getAsManagingEntity() {
        if (asManagingEntity == null) {
            asManagingEntity = new ArrayList<COCTMT841500UV09ManagingEntity>();
        }
        return this.asManagingEntity;
    }

    /**
     * Gets the value of the asProductEntity property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the asProductEntity property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAsProductEntity().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT841500UV09ProductEntity }
     * 
     * 
     */
    public List<COCTMT841500UV09ProductEntity> getAsProductEntity() {
        if (asProductEntity == null) {
            asProductEntity = new ArrayList<COCTMT841500UV09ProductEntity>();
        }
        return this.asProductEntity;
    }

    /**
     * Gets the value of the contactParty property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contactParty property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContactParty().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT841500UV09ContactParty }
     * 
     * 
     */
    public List<COCTMT841500UV09ContactParty> getContactParty() {
        if (contactParty == null) {
            contactParty = new ArrayList<COCTMT841500UV09ContactParty>();
        }
        return this.contactParty;
    }

    /**
     * Gets the value of the relatedEntity property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the relatedEntity property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRelatedEntity().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT841500UV09RelatedEntity }
     * 
     * 
     */
    public List<COCTMT841500UV09RelatedEntity> getRelatedEntity() {
        if (relatedEntity == null) {
            relatedEntity = new ArrayList<COCTMT841500UV09RelatedEntity>();
        }
        return this.relatedEntity;
    }

    /**
     * Gets the value of the part property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the part property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPart().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT841500UV09Part }
     * 
     * 
     */
    public List<COCTMT841500UV09Part> getPart() {
        if (part == null) {
            part = new ArrayList<COCTMT841500UV09Part>();
        }
        return this.part;
    }

    /**
     * Gets the value of the hasIngredients property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hasIngredients property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHasIngredients().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT841500UV09HasIngredients }
     * 
     * 
     */
    public List<COCTMT841500UV09HasIngredients> getHasIngredients() {
        if (hasIngredients == null) {
            hasIngredients = new ArrayList<COCTMT841500UV09HasIngredients>();
        }
        return this.hasIngredients;
    }

    /**
     * Gets the value of the locatedEntity property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the locatedEntity property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLocatedEntity().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT841500UV09LocatedEntity }
     * 
     * 
     */
    public List<COCTMT841500UV09LocatedEntity> getLocatedEntity() {
        if (locatedEntity == null) {
            locatedEntity = new ArrayList<COCTMT841500UV09LocatedEntity>();
        }
        return this.locatedEntity;
    }

    /**
     * Gets the value of the managedEntity property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the managedEntity property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getManagedEntity().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT841500UV09ManagedEntity }
     * 
     * 
     */
    public List<COCTMT841500UV09ManagedEntity> getManagedEntity() {
        if (managedEntity == null) {
            managedEntity = new ArrayList<COCTMT841500UV09ManagedEntity>();
        }
        return this.managedEntity;
    }

    /**
     * Gets the value of the classCode property.
     * 
     * @return
     *     possible object is
     *     {@link EntityClassMaterial }
     *     
     */
    public EntityClassMaterial getClassCode() {
        return classCode;
    }

    /**
     * Sets the value of the classCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link EntityClassMaterial }
     *     
     */
    public void setClassCode(EntityClassMaterial value) {
        this.classCode = value;
    }

    /**
     * Gets the value of the determinerCode property.
     * 
     * @return
     *     possible object is
     *     {@link EntityDeterminer }
     *     
     */
    public EntityDeterminer getDeterminerCode() {
        return determinerCode;
    }

    /**
     * Sets the value of the determinerCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link EntityDeterminer }
     *     
     */
    public void setDeterminerCode(EntityDeterminer value) {
        this.determinerCode = value;
    }

}
