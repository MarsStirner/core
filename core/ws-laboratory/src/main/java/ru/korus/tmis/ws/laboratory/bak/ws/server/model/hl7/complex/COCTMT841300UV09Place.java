
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
 * <p>Java class for COCT_MT841300UV09.Place complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT841300UV09.Place">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="code" type="{urn:hl7-org:v3}CD" minOccurs="0"/>
 *         &lt;element name="quantity" type="{urn:hl7-org:v3}PQ" minOccurs="0"/>
 *         &lt;element name="name" type="{urn:hl7-org:v3}EN" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="desc" type="{urn:hl7-org:v3}ED" minOccurs="0"/>
 *         &lt;element name="existenceTime" type="{urn:hl7-org:v3}IVL_TS" minOccurs="0"/>
 *         &lt;element name="riskCode" type="{urn:hl7-org:v3}CE" minOccurs="0"/>
 *         &lt;element name="handlingCode" type="{urn:hl7-org:v3}CE" minOccurs="0"/>
 *         &lt;element name="mobileInd" type="{urn:hl7-org:v3}BL" minOccurs="0"/>
 *         &lt;element name="directionsText" type="{urn:hl7-org:v3}ED" minOccurs="0"/>
 *         &lt;element name="asIdentifier" type="{urn:hl7-org:v3}COCT_MT841300UV09.Identifier" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="asTerritorialAuthority" type="{urn:hl7-org:v3}COCT_MT841300UV09.TerritorialAuthority" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="asEntityInContainer" type="{urn:hl7-org:v3}COCT_MT841300UV09.EntityInContainer" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="asPartOfWhole" type="{urn:hl7-org:v3}COCT_MT841300UV09.PartOfWhole" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="asLocatedPlacePartOf" type="{urn:hl7-org:v3}COCT_MT841300UV09.LocatedPlacePartOf" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="asMemberOf" type="{urn:hl7-org:v3}COCT_MT841300UV09.MemberOf" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="asObserved" type="{urn:hl7-org:v3}COCT_MT841300UV09.Observed" minOccurs="0"/>
 *         &lt;element name="asOtherIDs" type="{urn:hl7-org:v3}COCT_MT841300UV09.OtherIDs" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="asManagingEntity" type="{urn:hl7-org:v3}COCT_MT841300UV09.ManagingEntity" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="asProductEntity" type="{urn:hl7-org:v3}COCT_MT841300UV09.ProductEntity" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="contactParty" type="{urn:hl7-org:v3}COCT_MT841300UV09.ContactParty" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="part" type="{urn:hl7-org:v3}COCT_MT841300UV09.Part" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="locatedEntity" type="{urn:hl7-org:v3}COCT_MT841300UV09.LocatedEntity" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="locatedPlaceHasParts" type="{urn:hl7-org:v3}COCT_MT841300UV09.LocatedPlaceHasParts" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:hl7-org:v3}InfrastructureRootAttributes"/>
 *       &lt;attribute name="nullFlavor" type="{urn:hl7-org:v3}NullFlavor" />
 *       &lt;attribute name="classCode" use="required" type="{urn:hl7-org:v3}EntityClassPlace" />
 *       &lt;attribute name="determinerCode" use="required" type="{urn:hl7-org:v3}EntityDeterminer" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "COCT_MT841300UV09.Place", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "code",
    "quantity",
    "name",
    "desc",
    "existenceTime",
    "riskCode",
    "handlingCode",
    "mobileInd",
    "directionsText",
    "asIdentifier",
    "asTerritorialAuthority",
    "asEntityInContainer",
    "asPartOfWhole",
    "asLocatedPlacePartOf",
    "asMemberOf",
    "asObserved",
    "asOtherIDs",
    "asManagingEntity",
    "asProductEntity",
    "contactParty",
    "part",
    "locatedEntity",
    "locatedPlaceHasParts"
})
public class COCTMT841300UV09Place {

    protected List<CS> realmCode;
    protected II typeId;
    protected List<II> templateId;
    protected CD code;
    protected PQ quantity;
    protected List<EN> name;
    protected ED desc;
    protected IVLTS existenceTime;
    protected CE riskCode;
    protected CE handlingCode;
    protected BL mobileInd;
    protected ED directionsText;
    @XmlElement(nillable = true)
    protected List<COCTMT841300UV09Identifier> asIdentifier;
    @XmlElement(nillable = true)
    protected List<COCTMT841300UV09TerritorialAuthority> asTerritorialAuthority;
    @XmlElement(nillable = true)
    protected List<COCTMT841300UV09EntityInContainer> asEntityInContainer;
    @XmlElement(nillable = true)
    protected List<COCTMT841300UV09PartOfWhole> asPartOfWhole;
    @XmlElement(nillable = true)
    protected List<COCTMT841300UV09LocatedPlacePartOf> asLocatedPlacePartOf;
    @XmlElement(nillable = true)
    protected List<COCTMT841300UV09MemberOf> asMemberOf;
    @XmlElementRef(name = "asObserved", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT841300UV09Observed> asObserved;
    @XmlElement(nillable = true)
    protected List<COCTMT841300UV09OtherIDs> asOtherIDs;
    @XmlElement(nillable = true)
    protected List<COCTMT841300UV09ManagingEntity> asManagingEntity;
    @XmlElement(nillable = true)
    protected List<COCTMT841300UV09ProductEntity> asProductEntity;
    @XmlElement(nillable = true)
    protected List<COCTMT841300UV09ContactParty> contactParty;
    @XmlElement(nillable = true)
    protected List<COCTMT841300UV09Part> part;
    @XmlElement(nillable = true)
    protected List<COCTMT841300UV09LocatedEntity> locatedEntity;
    @XmlElement(nillable = true)
    protected List<COCTMT841300UV09LocatedPlaceHasParts> locatedPlaceHasParts;
    @XmlAttribute(name = "nullFlavor")
    protected NullFlavor nullFlavor;
    @XmlAttribute(name = "classCode", required = true)
    protected EntityClassPlace classCode;
    @XmlAttribute(name = "determinerCode", required = true)
    protected EntityDeterminer determinerCode;

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
     * Gets the value of the mobileInd property.
     * 
     * @return
     *     possible object is
     *     {@link BL }
     *     
     */
    public BL getMobileInd() {
        return mobileInd;
    }

    /**
     * Sets the value of the mobileInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link BL }
     *     
     */
    public void setMobileInd(BL value) {
        this.mobileInd = value;
    }

    /**
     * Gets the value of the directionsText property.
     * 
     * @return
     *     possible object is
     *     {@link ED }
     *     
     */
    public ED getDirectionsText() {
        return directionsText;
    }

    /**
     * Sets the value of the directionsText property.
     * 
     * @param value
     *     allowed object is
     *     {@link ED }
     *     
     */
    public void setDirectionsText(ED value) {
        this.directionsText = value;
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
     * {@link COCTMT841300UV09Identifier }
     * 
     * 
     */
    public List<COCTMT841300UV09Identifier> getAsIdentifier() {
        if (asIdentifier == null) {
            asIdentifier = new ArrayList<COCTMT841300UV09Identifier>();
        }
        return this.asIdentifier;
    }

    /**
     * Gets the value of the asTerritorialAuthority property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the asTerritorialAuthority property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAsTerritorialAuthority().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT841300UV09TerritorialAuthority }
     * 
     * 
     */
    public List<COCTMT841300UV09TerritorialAuthority> getAsTerritorialAuthority() {
        if (asTerritorialAuthority == null) {
            asTerritorialAuthority = new ArrayList<COCTMT841300UV09TerritorialAuthority>();
        }
        return this.asTerritorialAuthority;
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
     * {@link COCTMT841300UV09EntityInContainer }
     * 
     * 
     */
    public List<COCTMT841300UV09EntityInContainer> getAsEntityInContainer() {
        if (asEntityInContainer == null) {
            asEntityInContainer = new ArrayList<COCTMT841300UV09EntityInContainer>();
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
     * {@link COCTMT841300UV09PartOfWhole }
     * 
     * 
     */
    public List<COCTMT841300UV09PartOfWhole> getAsPartOfWhole() {
        if (asPartOfWhole == null) {
            asPartOfWhole = new ArrayList<COCTMT841300UV09PartOfWhole>();
        }
        return this.asPartOfWhole;
    }

    /**
     * Gets the value of the asLocatedPlacePartOf property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the asLocatedPlacePartOf property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAsLocatedPlacePartOf().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT841300UV09LocatedPlacePartOf }
     * 
     * 
     */
    public List<COCTMT841300UV09LocatedPlacePartOf> getAsLocatedPlacePartOf() {
        if (asLocatedPlacePartOf == null) {
            asLocatedPlacePartOf = new ArrayList<COCTMT841300UV09LocatedPlacePartOf>();
        }
        return this.asLocatedPlacePartOf;
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
     * {@link COCTMT841300UV09MemberOf }
     * 
     * 
     */
    public List<COCTMT841300UV09MemberOf> getAsMemberOf() {
        if (asMemberOf == null) {
            asMemberOf = new ArrayList<COCTMT841300UV09MemberOf>();
        }
        return this.asMemberOf;
    }

    /**
     * Gets the value of the asObserved property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT841300UV09Observed }{@code >}
     *     
     */
    public JAXBElement<COCTMT841300UV09Observed> getAsObserved() {
        return asObserved;
    }

    /**
     * Sets the value of the asObserved property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT841300UV09Observed }{@code >}
     *     
     */
    public void setAsObserved(JAXBElement<COCTMT841300UV09Observed> value) {
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
     * {@link COCTMT841300UV09OtherIDs }
     * 
     * 
     */
    public List<COCTMT841300UV09OtherIDs> getAsOtherIDs() {
        if (asOtherIDs == null) {
            asOtherIDs = new ArrayList<COCTMT841300UV09OtherIDs>();
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
     * {@link COCTMT841300UV09ManagingEntity }
     * 
     * 
     */
    public List<COCTMT841300UV09ManagingEntity> getAsManagingEntity() {
        if (asManagingEntity == null) {
            asManagingEntity = new ArrayList<COCTMT841300UV09ManagingEntity>();
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
     * {@link COCTMT841300UV09ProductEntity }
     * 
     * 
     */
    public List<COCTMT841300UV09ProductEntity> getAsProductEntity() {
        if (asProductEntity == null) {
            asProductEntity = new ArrayList<COCTMT841300UV09ProductEntity>();
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
     * {@link COCTMT841300UV09ContactParty }
     * 
     * 
     */
    public List<COCTMT841300UV09ContactParty> getContactParty() {
        if (contactParty == null) {
            contactParty = new ArrayList<COCTMT841300UV09ContactParty>();
        }
        return this.contactParty;
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
     * {@link COCTMT841300UV09Part }
     * 
     * 
     */
    public List<COCTMT841300UV09Part> getPart() {
        if (part == null) {
            part = new ArrayList<COCTMT841300UV09Part>();
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
     * {@link COCTMT841300UV09LocatedEntity }
     * 
     * 
     */
    public List<COCTMT841300UV09LocatedEntity> getLocatedEntity() {
        if (locatedEntity == null) {
            locatedEntity = new ArrayList<COCTMT841300UV09LocatedEntity>();
        }
        return this.locatedEntity;
    }

    /**
     * Gets the value of the locatedPlaceHasParts property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the locatedPlaceHasParts property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLocatedPlaceHasParts().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT841300UV09LocatedPlaceHasParts }
     * 
     * 
     */
    public List<COCTMT841300UV09LocatedPlaceHasParts> getLocatedPlaceHasParts() {
        if (locatedPlaceHasParts == null) {
            locatedPlaceHasParts = new ArrayList<COCTMT841300UV09LocatedPlaceHasParts>();
        }
        return this.locatedPlaceHasParts;
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
     *     {@link EntityClassPlace }
     *     
     */
    public EntityClassPlace getClassCode() {
        return classCode;
    }

    /**
     * Sets the value of the classCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link EntityClassPlace }
     *     
     */
    public void setClassCode(EntityClassPlace value) {
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
