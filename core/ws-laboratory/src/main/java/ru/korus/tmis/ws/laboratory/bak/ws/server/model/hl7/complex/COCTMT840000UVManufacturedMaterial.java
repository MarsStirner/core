
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
 * <p>Java class for COCT_MT840000UV.ManufacturedMaterial complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT840000UV.ManufacturedMaterial">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="id" type="{urn:hl7-org:v3}II" minOccurs="0"/>
 *         &lt;element name="code" type="{urn:hl7-org:v3}CD" minOccurs="0"/>
 *         &lt;element name="quantity" type="{urn:hl7-org:v3}PQ" minOccurs="0"/>
 *         &lt;element name="name" type="{urn:hl7-org:v3}EN" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="desc" type="{urn:hl7-org:v3}ED" minOccurs="0"/>
 *         &lt;element name="existenceTime" type="{urn:hl7-org:v3}IVL_TS" minOccurs="0"/>
 *         &lt;element name="riskCode" type="{urn:hl7-org:v3}CE" minOccurs="0"/>
 *         &lt;element name="handlingCode" type="{urn:hl7-org:v3}CE" minOccurs="0"/>
 *         &lt;element name="formCode" type="{urn:hl7-org:v3}CE" minOccurs="0"/>
 *         &lt;element name="lotNumberText" type="{urn:hl7-org:v3}ST" minOccurs="0"/>
 *         &lt;element name="expirationTime" type="{urn:hl7-org:v3}IVL_TS" minOccurs="0"/>
 *         &lt;element name="stabilityTime" type="{urn:hl7-org:v3}IVL_TS" minOccurs="0"/>
 *         &lt;element name="asQualifiedEntity" type="{urn:hl7-org:v3}COCT_MT840000UV.QualifiedEntity" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="asIdentifier" type="{urn:hl7-org:v3}COCT_MT840000UV.Identifier" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="asEntityInContainer" type="{urn:hl7-org:v3}COCT_MT840000UV.EntityInContainer" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="asPartOfWhole" type="{urn:hl7-org:v3}COCT_MT840000UV.PartOfWhole" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="asIngredient" type="{urn:hl7-org:v3}COCT_MT840000UV.Ingredient" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="asMemberOf" type="{urn:hl7-org:v3}COCT_MT840000UV.MemberOf" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="asObserved" type="{urn:hl7-org:v3}COCT_MT840000UV.Observed" minOccurs="0"/>
 *         &lt;element name="asOtherIDs" type="{urn:hl7-org:v3}COCT_MT840000UV.OtherIDs" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="asManagingEntity" type="{urn:hl7-org:v3}COCT_MT840000UV.ManagingEntity" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="asProductEntity" type="{urn:hl7-org:v3}COCT_MT840000UV.ProductEntity" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="contactParty" type="{urn:hl7-org:v3}COCT_MT840000UV.ContactParty" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="relatedEntity" type="{urn:hl7-org:v3}COCT_MT840000UV.RelatedEntity" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="containedEntity" type="{urn:hl7-org:v3}COCT_MT840000UV.ContainedEntity" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="part" type="{urn:hl7-org:v3}COCT_MT840000UV.Part" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="hasIngredients" type="{urn:hl7-org:v3}COCT_MT840000UV.HasIngredients" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="locatedEntity" type="{urn:hl7-org:v3}COCT_MT840000UV.LocatedEntity" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="managedEntity" type="{urn:hl7-org:v3}COCT_MT840000UV.ManagedEntity" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:hl7-org:v3}InfrastructureRootAttributes"/>
 *       &lt;attribute name="nullFlavor" type="{urn:hl7-org:v3}NullFlavor" />
 *       &lt;attribute name="classCode" use="required" type="{urn:hl7-org:v3}EntityClassManufacturedMaterial" />
 *       &lt;attribute name="determinerCode" use="required" type="{urn:hl7-org:v3}EntityDeterminer" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "COCT_MT840000UV.ManufacturedMaterial", propOrder = {
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
    "lotNumberText",
    "expirationTime",
    "stabilityTime",
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
    "containedEntity",
    "part",
    "hasIngredients",
    "locatedEntity",
    "managedEntity"
})
public class COCTMT840000UVManufacturedMaterial {

    protected List<CS> realmCode;
    protected II typeId;
    protected List<II> templateId;
    protected II id;
    protected CD code;
    protected PQ quantity;
    protected List<EN> name;
    protected ED desc;
    protected IVLTS existenceTime;
    protected CE riskCode;
    protected CE handlingCode;
    protected CE formCode;
    protected ST lotNumberText;
    protected IVLTS expirationTime;
    protected IVLTS stabilityTime;
    @XmlElement(nillable = true)
    protected List<COCTMT840000UVQualifiedEntity> asQualifiedEntity;
    @XmlElement(nillable = true)
    protected List<COCTMT840000UVIdentifier> asIdentifier;
    @XmlElement(nillable = true)
    protected List<COCTMT840000UVEntityInContainer> asEntityInContainer;
    @XmlElement(nillable = true)
    protected List<COCTMT840000UVPartOfWhole> asPartOfWhole;
    @XmlElement(nillable = true)
    protected List<COCTMT840000UVIngredient> asIngredient;
    @XmlElement(nillable = true)
    protected List<COCTMT840000UVMemberOf> asMemberOf;
    @XmlElementRef(name = "asObserved", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UVObserved> asObserved;
    @XmlElement(nillable = true)
    protected List<COCTMT840000UVOtherIDs> asOtherIDs;
    @XmlElement(nillable = true)
    protected List<COCTMT840000UVManagingEntity> asManagingEntity;
    @XmlElement(nillable = true)
    protected List<COCTMT840000UVProductEntity> asProductEntity;
    @XmlElement(nillable = true)
    protected List<COCTMT840000UVContactParty> contactParty;
    @XmlElement(nillable = true)
    protected List<COCTMT840000UVRelatedEntity> relatedEntity;
    @XmlElement(nillable = true)
    protected List<COCTMT840000UVContainedEntity> containedEntity;
    @XmlElement(nillable = true)
    protected List<COCTMT840000UVPart> part;
    @XmlElement(nillable = true)
    protected List<COCTMT840000UVHasIngredients> hasIngredients;
    @XmlElement(nillable = true)
    protected List<COCTMT840000UVLocatedEntity> locatedEntity;
    @XmlElement(nillable = true)
    protected List<COCTMT840000UVManagedEntity> managedEntity;
    @XmlAttribute(name = "nullFlavor")
    protected List<String> nullFlavor;
    @XmlAttribute(name = "classCode", required = true)
    protected String classCode;
    @XmlAttribute(name = "determinerCode", required = true)
    protected String determinerCode;

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
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the name property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getName().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EN }
     * 
     * 
     */
    public List<EN> getName() {
        if (name == null) {
            name = new ArrayList<EN>();
        }
        return this.name;
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
     *     {@link CE }
     *     
     */
    public CE getRiskCode() {
        return riskCode;
    }

    /**
     * Sets the value of the riskCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CE }
     *     
     */
    public void setRiskCode(CE value) {
        this.riskCode = value;
    }

    /**
     * Gets the value of the handlingCode property.
     * 
     * @return
     *     possible object is
     *     {@link CE }
     *     
     */
    public CE getHandlingCode() {
        return handlingCode;
    }

    /**
     * Sets the value of the handlingCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CE }
     *     
     */
    public void setHandlingCode(CE value) {
        this.handlingCode = value;
    }

    /**
     * Gets the value of the formCode property.
     * 
     * @return
     *     possible object is
     *     {@link CE }
     *     
     */
    public CE getFormCode() {
        return formCode;
    }

    /**
     * Sets the value of the formCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CE }
     *     
     */
    public void setFormCode(CE value) {
        this.formCode = value;
    }

    /**
     * Gets the value of the lotNumberText property.
     * 
     * @return
     *     possible object is
     *     {@link ST }
     *     
     */
    public ST getLotNumberText() {
        return lotNumberText;
    }

    /**
     * Sets the value of the lotNumberText property.
     * 
     * @param value
     *     allowed object is
     *     {@link ST }
     *     
     */
    public void setLotNumberText(ST value) {
        this.lotNumberText = value;
    }

    /**
     * Gets the value of the expirationTime property.
     * 
     * @return
     *     possible object is
     *     {@link IVLTS }
     *     
     */
    public IVLTS getExpirationTime() {
        return expirationTime;
    }

    /**
     * Sets the value of the expirationTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link IVLTS }
     *     
     */
    public void setExpirationTime(IVLTS value) {
        this.expirationTime = value;
    }

    /**
     * Gets the value of the stabilityTime property.
     * 
     * @return
     *     possible object is
     *     {@link IVLTS }
     *     
     */
    public IVLTS getStabilityTime() {
        return stabilityTime;
    }

    /**
     * Sets the value of the stabilityTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link IVLTS }
     *     
     */
    public void setStabilityTime(IVLTS value) {
        this.stabilityTime = value;
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
     * {@link COCTMT840000UVQualifiedEntity }
     * 
     * 
     */
    public List<COCTMT840000UVQualifiedEntity> getAsQualifiedEntity() {
        if (asQualifiedEntity == null) {
            asQualifiedEntity = new ArrayList<COCTMT840000UVQualifiedEntity>();
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
     * {@link COCTMT840000UVIdentifier }
     * 
     * 
     */
    public List<COCTMT840000UVIdentifier> getAsIdentifier() {
        if (asIdentifier == null) {
            asIdentifier = new ArrayList<COCTMT840000UVIdentifier>();
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
     * {@link COCTMT840000UVEntityInContainer }
     * 
     * 
     */
    public List<COCTMT840000UVEntityInContainer> getAsEntityInContainer() {
        if (asEntityInContainer == null) {
            asEntityInContainer = new ArrayList<COCTMT840000UVEntityInContainer>();
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
     * {@link COCTMT840000UVPartOfWhole }
     * 
     * 
     */
    public List<COCTMT840000UVPartOfWhole> getAsPartOfWhole() {
        if (asPartOfWhole == null) {
            asPartOfWhole = new ArrayList<COCTMT840000UVPartOfWhole>();
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
     * {@link COCTMT840000UVIngredient }
     * 
     * 
     */
    public List<COCTMT840000UVIngredient> getAsIngredient() {
        if (asIngredient == null) {
            asIngredient = new ArrayList<COCTMT840000UVIngredient>();
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
     * {@link COCTMT840000UVMemberOf }
     * 
     * 
     */
    public List<COCTMT840000UVMemberOf> getAsMemberOf() {
        if (asMemberOf == null) {
            asMemberOf = new ArrayList<COCTMT840000UVMemberOf>();
        }
        return this.asMemberOf;
    }

    /**
     * Gets the value of the asObserved property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVObserved }{@code >}
     *     
     */
    public JAXBElement<COCTMT840000UVObserved> getAsObserved() {
        return asObserved;
    }

    /**
     * Sets the value of the asObserved property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT840000UVObserved }{@code >}
     *     
     */
    public void setAsObserved(JAXBElement<COCTMT840000UVObserved> value) {
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
     * {@link COCTMT840000UVOtherIDs }
     * 
     * 
     */
    public List<COCTMT840000UVOtherIDs> getAsOtherIDs() {
        if (asOtherIDs == null) {
            asOtherIDs = new ArrayList<COCTMT840000UVOtherIDs>();
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
     * {@link COCTMT840000UVManagingEntity }
     * 
     * 
     */
    public List<COCTMT840000UVManagingEntity> getAsManagingEntity() {
        if (asManagingEntity == null) {
            asManagingEntity = new ArrayList<COCTMT840000UVManagingEntity>();
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
     * {@link COCTMT840000UVProductEntity }
     * 
     * 
     */
    public List<COCTMT840000UVProductEntity> getAsProductEntity() {
        if (asProductEntity == null) {
            asProductEntity = new ArrayList<COCTMT840000UVProductEntity>();
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
     * {@link COCTMT840000UVContactParty }
     * 
     * 
     */
    public List<COCTMT840000UVContactParty> getContactParty() {
        if (contactParty == null) {
            contactParty = new ArrayList<COCTMT840000UVContactParty>();
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
     * {@link COCTMT840000UVRelatedEntity }
     * 
     * 
     */
    public List<COCTMT840000UVRelatedEntity> getRelatedEntity() {
        if (relatedEntity == null) {
            relatedEntity = new ArrayList<COCTMT840000UVRelatedEntity>();
        }
        return this.relatedEntity;
    }

    /**
     * Gets the value of the containedEntity property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the containedEntity property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContainedEntity().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT840000UVContainedEntity }
     * 
     * 
     */
    public List<COCTMT840000UVContainedEntity> getContainedEntity() {
        if (containedEntity == null) {
            containedEntity = new ArrayList<COCTMT840000UVContainedEntity>();
        }
        return this.containedEntity;
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
     * {@link COCTMT840000UVPart }
     * 
     * 
     */
    public List<COCTMT840000UVPart> getPart() {
        if (part == null) {
            part = new ArrayList<COCTMT840000UVPart>();
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
     * {@link COCTMT840000UVHasIngredients }
     * 
     * 
     */
    public List<COCTMT840000UVHasIngredients> getHasIngredients() {
        if (hasIngredients == null) {
            hasIngredients = new ArrayList<COCTMT840000UVHasIngredients>();
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
     * {@link COCTMT840000UVLocatedEntity }
     * 
     * 
     */
    public List<COCTMT840000UVLocatedEntity> getLocatedEntity() {
        if (locatedEntity == null) {
            locatedEntity = new ArrayList<COCTMT840000UVLocatedEntity>();
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
     * {@link COCTMT840000UVManagedEntity }
     * 
     * 
     */
    public List<COCTMT840000UVManagedEntity> getManagedEntity() {
        if (managedEntity == null) {
            managedEntity = new ArrayList<COCTMT840000UVManagedEntity>();
        }
        return this.managedEntity;
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

    /**
     * Gets the value of the determinerCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeterminerCode() {
        return determinerCode;
    }

    /**
     * Sets the value of the determinerCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeterminerCode(String value) {
        this.determinerCode = value;
    }

}
