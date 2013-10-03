
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
 * <p>Java class for COCT_MT030007UV.Person complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT030007UV.Person">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="id" type="{urn:hl7-org:v3}DSET_II" minOccurs="0"/>
 *         &lt;element name="name" type="{urn:hl7-org:v3}COLL_EN" minOccurs="0"/>
 *         &lt;element name="desc" type="{urn:hl7-org:v3}ED" minOccurs="0"/>
 *         &lt;element name="statusCode" type="{urn:hl7-org:v3}CS" minOccurs="0"/>
 *         &lt;element name="administrativeGenderCode" type="{urn:hl7-org:v3}CD" minOccurs="0"/>
 *         &lt;element name="birthTime" type="{urn:hl7-org:v3}TS" minOccurs="0"/>
 *         &lt;element name="deceasedInd" type="{urn:hl7-org:v3}BL" minOccurs="0"/>
 *         &lt;element name="deceasedTime" type="{urn:hl7-org:v3}TS" minOccurs="0"/>
 *         &lt;element name="multipleBirthInd" type="{urn:hl7-org:v3}BL" minOccurs="0"/>
 *         &lt;element name="multipleBirthOrderNumber" type="{urn:hl7-org:v3}INT" minOccurs="0"/>
 *         &lt;element name="organDonorInd" type="{urn:hl7-org:v3}BL" minOccurs="0"/>
 *         &lt;element name="maritalStatusCode" type="{urn:hl7-org:v3}CD" minOccurs="0"/>
 *         &lt;element name="educationLevelCode" type="{urn:hl7-org:v3}CD" minOccurs="0"/>
 *         &lt;element name="disabilityCode" type="{urn:hl7-org:v3}DSET_CD" minOccurs="0"/>
 *         &lt;element name="livingArrangementCode" type="{urn:hl7-org:v3}CD" minOccurs="0"/>
 *         &lt;element name="religiousAffiliationCode" type="{urn:hl7-org:v3}CD" minOccurs="0"/>
 *         &lt;element name="raceCode" type="{urn:hl7-org:v3}DSET_CD" minOccurs="0"/>
 *         &lt;element name="ethnicGroupCode" type="{urn:hl7-org:v3}DSET_CD" minOccurs="0"/>
 *         &lt;element name="asEmployment" type="{urn:hl7-org:v3}COCT_MT030007UV.Employment" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="asCitizen" type="{urn:hl7-org:v3}COCT_MT030007UV.Citizen" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="asStudent" type="{urn:hl7-org:v3}COCT_MT030007UV.Student" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="asMember" type="{urn:hl7-org:v3}COCT_MT030007UV.Member" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="asOtherIDs" type="{urn:hl7-org:v3}COCT_MT030007UV.OtherIDs" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="contactParty" type="{urn:hl7-org:v3}COCT_MT030007UV.ContactParty" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="guardian" type="{urn:hl7-org:v3}COCT_MT030007UV.Guardian" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="guarantor" type="{urn:hl7-org:v3}COCT_MT030007UV.Guarantor" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="birthPlace" type="{urn:hl7-org:v3}COCT_MT030007UV.BirthPlace" minOccurs="0"/>
 *         &lt;element name="languageCommunication" type="{urn:hl7-org:v3}COCT_MT030007UV.LanguageCommunication" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:hl7-org:v3}InfrastructureRootAttributes"/>
 *       &lt;attribute name="classCode" use="required" type="{urn:hl7-org:v3}EntityClassPerson" />
 *       &lt;attribute name="determinerCode" use="required" type="{urn:hl7-org:v3}x_DeterminerInstanceKind" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "COCT_MT030007UV.Person", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "id",
    "name",
    "desc",
    "statusCode",
    "administrativeGenderCode",
    "birthTime",
    "deceasedInd",
    "deceasedTime",
    "multipleBirthInd",
    "multipleBirthOrderNumber",
    "organDonorInd",
    "maritalStatusCode",
    "educationLevelCode",
    "disabilityCode",
    "livingArrangementCode",
    "religiousAffiliationCode",
    "raceCode",
    "ethnicGroupCode",
    "asEmployment",
    "asCitizen",
    "asStudent",
    "asMember",
    "asOtherIDs",
    "contactParty",
    "guardian",
    "guarantor",
    "birthPlace",
    "languageCommunication"
})
public class COCTMT030007UVPerson {

    protected DSETCS realmCode;
    protected II typeId;
    protected LISTII templateId;
    protected DSETII id;
    protected COLLEN name;
    protected ED desc;
    protected CS statusCode;
    protected CD administrativeGenderCode;
    protected TS birthTime;
    protected BL deceasedInd;
    protected TS deceasedTime;
    protected BL multipleBirthInd;
    protected INT multipleBirthOrderNumber;
    protected BL organDonorInd;
    protected CD maritalStatusCode;
    protected CD educationLevelCode;
    protected DSETCD disabilityCode;
    protected CD livingArrangementCode;
    protected CD religiousAffiliationCode;
    protected DSETCD raceCode;
    protected DSETCD ethnicGroupCode;
    @XmlElement(nillable = true)
    protected List<COCTMT030007UVEmployment> asEmployment;
    @XmlElement(nillable = true)
    protected List<COCTMT030007UVCitizen> asCitizen;
    @XmlElement(nillable = true)
    protected List<COCTMT030007UVStudent> asStudent;
    @XmlElement(nillable = true)
    protected List<COCTMT030007UVMember> asMember;
    @XmlElement(nillable = true)
    protected List<COCTMT030007UVOtherIDs> asOtherIDs;
    @XmlElement(nillable = true)
    protected List<COCTMT030007UVContactParty> contactParty;
    @XmlElement(nillable = true)
    protected List<COCTMT030007UVGuardian> guardian;
    @XmlElement(nillable = true)
    protected List<COCTMT030007UVGuarantor> guarantor;
    @XmlElementRef(name = "birthPlace", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT030007UVBirthPlace> birthPlace;
    @XmlElement(nillable = true)
    protected List<COCTMT030007UVLanguageCommunication> languageCommunication;
    @XmlAttribute(name = "classCode", required = true)
    protected EntityClassPerson classCode;
    @XmlAttribute(name = "determinerCode", required = true)
    protected XDeterminerInstanceKind determinerCode;

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
     * Gets the value of the administrativeGenderCode property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getAdministrativeGenderCode() {
        return administrativeGenderCode;
    }

    /**
     * Sets the value of the administrativeGenderCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setAdministrativeGenderCode(CD value) {
        this.administrativeGenderCode = value;
    }

    /**
     * Gets the value of the birthTime property.
     * 
     * @return
     *     possible object is
     *     {@link TS }
     *     
     */
    public TS getBirthTime() {
        return birthTime;
    }

    /**
     * Sets the value of the birthTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link TS }
     *     
     */
    public void setBirthTime(TS value) {
        this.birthTime = value;
    }

    /**
     * Gets the value of the deceasedInd property.
     * 
     * @return
     *     possible object is
     *     {@link BL }
     *     
     */
    public BL getDeceasedInd() {
        return deceasedInd;
    }

    /**
     * Sets the value of the deceasedInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link BL }
     *     
     */
    public void setDeceasedInd(BL value) {
        this.deceasedInd = value;
    }

    /**
     * Gets the value of the deceasedTime property.
     * 
     * @return
     *     possible object is
     *     {@link TS }
     *     
     */
    public TS getDeceasedTime() {
        return deceasedTime;
    }

    /**
     * Sets the value of the deceasedTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link TS }
     *     
     */
    public void setDeceasedTime(TS value) {
        this.deceasedTime = value;
    }

    /**
     * Gets the value of the multipleBirthInd property.
     * 
     * @return
     *     possible object is
     *     {@link BL }
     *     
     */
    public BL getMultipleBirthInd() {
        return multipleBirthInd;
    }

    /**
     * Sets the value of the multipleBirthInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link BL }
     *     
     */
    public void setMultipleBirthInd(BL value) {
        this.multipleBirthInd = value;
    }

    /**
     * Gets the value of the multipleBirthOrderNumber property.
     * 
     * @return
     *     possible object is
     *     {@link INT }
     *     
     */
    public INT getMultipleBirthOrderNumber() {
        return multipleBirthOrderNumber;
    }

    /**
     * Sets the value of the multipleBirthOrderNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link INT }
     *     
     */
    public void setMultipleBirthOrderNumber(INT value) {
        this.multipleBirthOrderNumber = value;
    }

    /**
     * Gets the value of the organDonorInd property.
     * 
     * @return
     *     possible object is
     *     {@link BL }
     *     
     */
    public BL getOrganDonorInd() {
        return organDonorInd;
    }

    /**
     * Sets the value of the organDonorInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link BL }
     *     
     */
    public void setOrganDonorInd(BL value) {
        this.organDonorInd = value;
    }

    /**
     * Gets the value of the maritalStatusCode property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getMaritalStatusCode() {
        return maritalStatusCode;
    }

    /**
     * Sets the value of the maritalStatusCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setMaritalStatusCode(CD value) {
        this.maritalStatusCode = value;
    }

    /**
     * Gets the value of the educationLevelCode property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getEducationLevelCode() {
        return educationLevelCode;
    }

    /**
     * Sets the value of the educationLevelCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setEducationLevelCode(CD value) {
        this.educationLevelCode = value;
    }

    /**
     * Gets the value of the disabilityCode property.
     * 
     * @return
     *     possible object is
     *     {@link DSETCD }
     *     
     */
    public DSETCD getDisabilityCode() {
        return disabilityCode;
    }

    /**
     * Sets the value of the disabilityCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link DSETCD }
     *     
     */
    public void setDisabilityCode(DSETCD value) {
        this.disabilityCode = value;
    }

    /**
     * Gets the value of the livingArrangementCode property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getLivingArrangementCode() {
        return livingArrangementCode;
    }

    /**
     * Sets the value of the livingArrangementCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setLivingArrangementCode(CD value) {
        this.livingArrangementCode = value;
    }

    /**
     * Gets the value of the religiousAffiliationCode property.
     * 
     * @return
     *     possible object is
     *     {@link CD }
     *     
     */
    public CD getReligiousAffiliationCode() {
        return religiousAffiliationCode;
    }

    /**
     * Sets the value of the religiousAffiliationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CD }
     *     
     */
    public void setReligiousAffiliationCode(CD value) {
        this.religiousAffiliationCode = value;
    }

    /**
     * Gets the value of the raceCode property.
     * 
     * @return
     *     possible object is
     *     {@link DSETCD }
     *     
     */
    public DSETCD getRaceCode() {
        return raceCode;
    }

    /**
     * Sets the value of the raceCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link DSETCD }
     *     
     */
    public void setRaceCode(DSETCD value) {
        this.raceCode = value;
    }

    /**
     * Gets the value of the ethnicGroupCode property.
     * 
     * @return
     *     possible object is
     *     {@link DSETCD }
     *     
     */
    public DSETCD getEthnicGroupCode() {
        return ethnicGroupCode;
    }

    /**
     * Sets the value of the ethnicGroupCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link DSETCD }
     *     
     */
    public void setEthnicGroupCode(DSETCD value) {
        this.ethnicGroupCode = value;
    }

    /**
     * Gets the value of the asEmployment property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the asEmployment property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAsEmployment().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT030007UVEmployment }
     * 
     * 
     */
    public List<COCTMT030007UVEmployment> getAsEmployment() {
        if (asEmployment == null) {
            asEmployment = new ArrayList<COCTMT030007UVEmployment>();
        }
        return this.asEmployment;
    }

    /**
     * Gets the value of the asCitizen property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the asCitizen property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAsCitizen().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT030007UVCitizen }
     * 
     * 
     */
    public List<COCTMT030007UVCitizen> getAsCitizen() {
        if (asCitizen == null) {
            asCitizen = new ArrayList<COCTMT030007UVCitizen>();
        }
        return this.asCitizen;
    }

    /**
     * Gets the value of the asStudent property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the asStudent property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAsStudent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT030007UVStudent }
     * 
     * 
     */
    public List<COCTMT030007UVStudent> getAsStudent() {
        if (asStudent == null) {
            asStudent = new ArrayList<COCTMT030007UVStudent>();
        }
        return this.asStudent;
    }

    /**
     * Gets the value of the asMember property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the asMember property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAsMember().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT030007UVMember }
     * 
     * 
     */
    public List<COCTMT030007UVMember> getAsMember() {
        if (asMember == null) {
            asMember = new ArrayList<COCTMT030007UVMember>();
        }
        return this.asMember;
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
     * {@link COCTMT030007UVOtherIDs }
     * 
     * 
     */
    public List<COCTMT030007UVOtherIDs> getAsOtherIDs() {
        if (asOtherIDs == null) {
            asOtherIDs = new ArrayList<COCTMT030007UVOtherIDs>();
        }
        return this.asOtherIDs;
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
     * {@link COCTMT030007UVContactParty }
     * 
     * 
     */
    public List<COCTMT030007UVContactParty> getContactParty() {
        if (contactParty == null) {
            contactParty = new ArrayList<COCTMT030007UVContactParty>();
        }
        return this.contactParty;
    }

    /**
     * Gets the value of the guardian property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the guardian property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGuardian().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT030007UVGuardian }
     * 
     * 
     */
    public List<COCTMT030007UVGuardian> getGuardian() {
        if (guardian == null) {
            guardian = new ArrayList<COCTMT030007UVGuardian>();
        }
        return this.guardian;
    }

    /**
     * Gets the value of the guarantor property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the guarantor property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGuarantor().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT030007UVGuarantor }
     * 
     * 
     */
    public List<COCTMT030007UVGuarantor> getGuarantor() {
        if (guarantor == null) {
            guarantor = new ArrayList<COCTMT030007UVGuarantor>();
        }
        return this.guarantor;
    }

    /**
     * Gets the value of the birthPlace property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT030007UVBirthPlace }{@code >}
     *     
     */
    public JAXBElement<COCTMT030007UVBirthPlace> getBirthPlace() {
        return birthPlace;
    }

    /**
     * Sets the value of the birthPlace property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT030007UVBirthPlace }{@code >}
     *     
     */
    public void setBirthPlace(JAXBElement<COCTMT030007UVBirthPlace> value) {
        this.birthPlace = value;
    }

    /**
     * Gets the value of the languageCommunication property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the languageCommunication property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLanguageCommunication().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT030007UVLanguageCommunication }
     * 
     * 
     */
    public List<COCTMT030007UVLanguageCommunication> getLanguageCommunication() {
        if (languageCommunication == null) {
            languageCommunication = new ArrayList<COCTMT030007UVLanguageCommunication>();
        }
        return this.languageCommunication;
    }

    /**
     * Gets the value of the classCode property.
     * 
     * @return
     *     possible object is
     *     {@link EntityClassPerson }
     *     
     */
    public EntityClassPerson getClassCode() {
        return classCode;
    }

    /**
     * Sets the value of the classCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link EntityClassPerson }
     *     
     */
    public void setClassCode(EntityClassPerson value) {
        this.classCode = value;
    }

    /**
     * Gets the value of the determinerCode property.
     * 
     * @return
     *     possible object is
     *     {@link XDeterminerInstanceKind }
     *     
     */
    public XDeterminerInstanceKind getDeterminerCode() {
        return determinerCode;
    }

    /**
     * Sets the value of the determinerCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link XDeterminerInstanceKind }
     *     
     */
    public void setDeterminerCode(XDeterminerInstanceKind value) {
        this.determinerCode = value;
    }

}
