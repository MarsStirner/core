
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
 * <p>Java class for COCT_MT840000UV.Organization complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT840000UV.Organization">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="code" type="{urn:hl7-org:v3}CD" minOccurs="0"/>
 *         &lt;element name="quantity" type="{urn:hl7-org:v3}PQ" minOccurs="0"/>
 *         &lt;element name="name" type="{urn:hl7-org:v3}ON" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="desc" type="{urn:hl7-org:v3}ED" minOccurs="0"/>
 *         &lt;element name="statusCode" type="{urn:hl7-org:v3}CS" minOccurs="0"/>
 *         &lt;element name="existenceTime" type="{urn:hl7-org:v3}IVL_TS" minOccurs="0"/>
 *         &lt;element name="riskCode" type="{urn:hl7-org:v3}CE" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="handlingCode" type="{urn:hl7-org:v3}CE" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="standardIndustryClassCode" type="{urn:hl7-org:v3}CE" minOccurs="0"/>
 *         &lt;element name="asQualifiedEntity" type="{urn:hl7-org:v3}COCT_MT840000UV.QualifiedEntity" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="asIdentifier" type="{urn:hl7-org:v3}COCT_MT840000UV.Identifier" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="asPartOfWhole" type="{urn:hl7-org:v3}COCT_MT840000UV.PartOfWhole" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="asMemberOf" type="{urn:hl7-org:v3}COCT_MT840000UV.MemberOf" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="asObserved" type="{urn:hl7-org:v3}COCT_MT840000UV.Observed" minOccurs="0"/>
 *         &lt;element name="asOtherIDs" type="{urn:hl7-org:v3}COCT_MT840000UV.OtherIDs" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="asManagingEntity" type="{urn:hl7-org:v3}COCT_MT840000UV.ManagingEntity" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="contactParty" type="{urn:hl7-org:v3}COCT_MT840000UV.ContactParty" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="relatedEntity" type="{urn:hl7-org:v3}COCT_MT840000UV.RelatedEntity" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="jurisdiction" type="{urn:hl7-org:v3}COCT_MT840000UV.Jurisdiction" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="part" type="{urn:hl7-org:v3}COCT_MT840000UV.Part" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="locatedEntity" type="{urn:hl7-org:v3}COCT_MT840000UV.LocatedEntity" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="member" type="{urn:hl7-org:v3}COCT_MT840000UV.Member" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="hasProductEntity" type="{urn:hl7-org:v3}COCT_MT840000UV.HasProductEntity" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="managedEntity" type="{urn:hl7-org:v3}COCT_MT840000UV.ManagedEntity" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:hl7-org:v3}InfrastructureRootAttributes"/>
 *       &lt;attribute name="nullFlavor" type="{urn:hl7-org:v3}NullFlavor" />
 *       &lt;attribute name="classCode" use="required" type="{urn:hl7-org:v3}EntityClassOrganization" />
 *       &lt;attribute name="determinerCode" use="required" type="{urn:hl7-org:v3}EntityDeterminer" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "COCT_MT840000UV.Organization", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "code",
    "quantity",
    "name",
    "desc",
    "statusCode",
    "existenceTime",
    "riskCode",
    "handlingCode",
    "standardIndustryClassCode",
    "asQualifiedEntity",
    "asIdentifier",
    "asPartOfWhole",
    "asMemberOf",
    "asObserved",
    "asOtherIDs",
    "asManagingEntity",
    "contactParty",
    "relatedEntity",
    "jurisdiction",
    "part",
    "locatedEntity",
    "member",
    "hasProductEntity",
    "managedEntity"
})
public class COCTMT840000UVOrganization {

    protected List<CS> realmCode;
    protected II typeId;
    protected List<II> templateId;
    protected CD code;
    protected PQ quantity;
    protected List<ON> name;
    protected ED desc;
    protected CS statusCode;
    protected IVLTS existenceTime;
    protected List<CE> riskCode;
    protected List<CE> handlingCode;
    protected CE standardIndustryClassCode;
    @XmlElement(nillable = true)
    protected List<COCTMT840000UVQualifiedEntity> asQualifiedEntity;
    @XmlElement(nillable = true)
    protected List<COCTMT840000UVIdentifier> asIdentifier;
    @XmlElement(nillable = true)
    protected List<COCTMT840000UVPartOfWhole> asPartOfWhole;
    @XmlElement(nillable = true)
    protected List<COCTMT840000UVMemberOf> asMemberOf;
    @XmlElementRef(name = "asObserved", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT840000UVObserved> asObserved;
    @XmlElement(nillable = true)
    protected List<COCTMT840000UVOtherIDs> asOtherIDs;
    @XmlElement(nillable = true)
    protected List<COCTMT840000UVManagingEntity> asManagingEntity;
    @XmlElement(nillable = true)
    protected List<COCTMT840000UVContactParty> contactParty;
    @XmlElement(nillable = true)
    protected List<COCTMT840000UVRelatedEntity> relatedEntity;
    @XmlElement(nillable = true)
    protected List<COCTMT840000UVJurisdiction> jurisdiction;
    @XmlElement(nillable = true)
    protected List<COCTMT840000UVPart> part;
    @XmlElement(nillable = true)
    protected List<COCTMT840000UVLocatedEntity> locatedEntity;
    @XmlElement(nillable = true)
    protected List<COCTMT840000UVMember> member;
    @XmlElement(nillable = true)
    protected List<COCTMT840000UVHasProductEntity> hasProductEntity;
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
     * {@link ON }
     * 
     * 
     */
    public List<ON> getName() {
        if (name == null) {
            name = new ArrayList<ON>();
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
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the riskCode property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRiskCode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CE }
     * 
     * 
     */
    public List<CE> getRiskCode() {
        if (riskCode == null) {
            riskCode = new ArrayList<CE>();
        }
        return this.riskCode;
    }

    /**
     * Gets the value of the handlingCode property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the handlingCode property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHandlingCode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CE }
     * 
     * 
     */
    public List<CE> getHandlingCode() {
        if (handlingCode == null) {
            handlingCode = new ArrayList<CE>();
        }
        return this.handlingCode;
    }

    /**
     * Gets the value of the standardIndustryClassCode property.
     * 
     * @return
     *     possible object is
     *     {@link CE }
     *     
     */
    public CE getStandardIndustryClassCode() {
        return standardIndustryClassCode;
    }

    /**
     * Sets the value of the standardIndustryClassCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CE }
     *     
     */
    public void setStandardIndustryClassCode(CE value) {
        this.standardIndustryClassCode = value;
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
     * Gets the value of the jurisdiction property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the jurisdiction property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getJurisdiction().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT840000UVJurisdiction }
     * 
     * 
     */
    public List<COCTMT840000UVJurisdiction> getJurisdiction() {
        if (jurisdiction == null) {
            jurisdiction = new ArrayList<COCTMT840000UVJurisdiction>();
        }
        return this.jurisdiction;
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
     * Gets the value of the member property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the member property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMember().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT840000UVMember }
     * 
     * 
     */
    public List<COCTMT840000UVMember> getMember() {
        if (member == null) {
            member = new ArrayList<COCTMT840000UVMember>();
        }
        return this.member;
    }

    /**
     * Gets the value of the hasProductEntity property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hasProductEntity property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHasProductEntity().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT840000UVHasProductEntity }
     * 
     * 
     */
    public List<COCTMT840000UVHasProductEntity> getHasProductEntity() {
        if (hasProductEntity == null) {
            hasProductEntity = new ArrayList<COCTMT840000UVHasProductEntity>();
        }
        return this.hasProductEntity;
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
