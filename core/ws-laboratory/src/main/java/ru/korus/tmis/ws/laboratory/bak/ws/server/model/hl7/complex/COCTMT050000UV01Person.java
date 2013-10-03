
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
 * <p>Java class for COCT_MT050000UV01.Person complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="COCT_MT050000UV01.Person">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{urn:hl7-org:v3}InfrastructureRootElements"/>
 *         &lt;element name="id" type="{urn:hl7-org:v3}DSET_II" minOccurs="0"/>
 *         &lt;element name="name" type="{urn:hl7-org:v3}COLL_EN" minOccurs="0"/>
 *         &lt;element name="desc" type="{urn:hl7-org:v3}ED" minOccurs="0"/>
 *         &lt;element name="telecom" type="{urn:hl7-org:v3}COLL_TEL" minOccurs="0"/>
 *         &lt;element name="administrativeGenderCode" type="{urn:hl7-org:v3}CD" minOccurs="0"/>
 *         &lt;element name="birthTime" type="{urn:hl7-org:v3}TS" minOccurs="0"/>
 *         &lt;element name="deceasedInd" type="{urn:hl7-org:v3}BL" minOccurs="0"/>
 *         &lt;element name="deceasedTime" type="{urn:hl7-org:v3}TS" minOccurs="0"/>
 *         &lt;element name="multipleBirthInd" type="{urn:hl7-org:v3}BL" minOccurs="0"/>
 *         &lt;element name="multipleBirthOrderNumber" type="{urn:hl7-org:v3}INT" minOccurs="0"/>
 *         &lt;element name="organDonorInd" type="{urn:hl7-org:v3}BL" minOccurs="0"/>
 *         &lt;element name="addr" type="{urn:hl7-org:v3}COLL_AD" minOccurs="0"/>
 *         &lt;element name="maritalStatusCode" type="{urn:hl7-org:v3}CD" minOccurs="0"/>
 *         &lt;element name="educationLevelCode" type="{urn:hl7-org:v3}CD" minOccurs="0"/>
 *         &lt;element name="disabilityCode" type="{urn:hl7-org:v3}DSET_CD" minOccurs="0"/>
 *         &lt;element name="livingArrangementCode" type="{urn:hl7-org:v3}CD" minOccurs="0"/>
 *         &lt;element name="religiousAffiliationCode" type="{urn:hl7-org:v3}CD" minOccurs="0"/>
 *         &lt;element name="raceCode" type="{urn:hl7-org:v3}DSET_CD" minOccurs="0"/>
 *         &lt;element name="ethnicGroupCode" type="{urn:hl7-org:v3}DSET_CD" minOccurs="0"/>
 *         &lt;element name="asPatientOfOtherProvider" type="{urn:hl7-org:v3}COCT_MT050000UV01.PatientOfOtherProvider" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="asEmployee" type="{urn:hl7-org:v3}COCT_MT050000UV01.Employee" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="asCitizen" type="{urn:hl7-org:v3}COCT_MT050000UV01.Citizen" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="asStudent" type="{urn:hl7-org:v3}COCT_MT050000UV01.Student" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="asMember" type="{urn:hl7-org:v3}COCT_MT050000UV01.Member" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="asOtherIDs" type="{urn:hl7-org:v3}COCT_MT050000UV01.OtherIDs" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="contactParty" type="{urn:hl7-org:v3}COCT_MT050000UV01.ContactParty" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="guardian" type="{urn:hl7-org:v3}COCT_MT050000UV01.Guardian" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="personalRelationship" type="{urn:hl7-org:v3}COCT_MT050000UV01.PersonalRelationship" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="careGiver" type="{urn:hl7-org:v3}COCT_MT050000UV01.CareGiver" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="birthPlace" type="{urn:hl7-org:v3}COCT_MT050000UV01.BirthPlace" minOccurs="0"/>
 *         &lt;element name="guarantorRole" type="{urn:hl7-org:v3}COCT_MT670000UV04.GuarantorRole" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="languageCommunication" type="{urn:hl7-org:v3}COCT_MT050000UV01.LanguageCommunication" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{urn:hl7-org:v3}InfrastructureRootAttributes"/>
 *       &lt;attribute name="nullFlavor" type="{urn:hl7-org:v3}NullFlavor" />
 *       &lt;attribute name="classCode" type="{urn:hl7-org:v3}EntityClass" fixed="PSN" />
 *       &lt;attribute name="determinerCode" type="{urn:hl7-org:v3}EntityDeterminer" fixed="INSTANCE" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "COCT_MT050000UV01.Person", propOrder = {
    "realmCode",
    "typeId",
    "templateId",
    "id",
    "name",
    "desc",
    "telecom",
    "administrativeGenderCode",
    "birthTime",
    "deceasedInd",
    "deceasedTime",
    "multipleBirthInd",
    "multipleBirthOrderNumber",
    "organDonorInd",
    "addr",
    "maritalStatusCode",
    "educationLevelCode",
    "disabilityCode",
    "livingArrangementCode",
    "religiousAffiliationCode",
    "raceCode",
    "ethnicGroupCode",
    "asPatientOfOtherProvider",
    "asEmployee",
    "asCitizen",
    "asStudent",
    "asMember",
    "asOtherIDs",
    "contactParty",
    "guardian",
    "personalRelationship",
    "careGiver",
    "birthPlace",
    "guarantorRole",
    "languageCommunication"
})
public class COCTMT050000UV01Person {

    protected DSETCS realmCode;
    protected II typeId;
    protected LISTII templateId;
    protected DSETII id;
    protected COLLEN name;
    protected ED desc;
    protected COLLTEL telecom;
    protected CD administrativeGenderCode;
    protected TS birthTime;
    protected BL deceasedInd;
    protected TS deceasedTime;
    protected BL multipleBirthInd;
    protected INT multipleBirthOrderNumber;
    protected BL organDonorInd;
    protected COLLAD addr;
    protected CD maritalStatusCode;
    protected CD educationLevelCode;
    protected DSETCD disabilityCode;
    protected CD livingArrangementCode;
    protected CD religiousAffiliationCode;
    protected DSETCD raceCode;
    protected DSETCD ethnicGroupCode;
    @XmlElement(nillable = true)
    protected List<COCTMT050000UV01PatientOfOtherProvider> asPatientOfOtherProvider;
    @XmlElement(nillable = true)
    protected List<COCTMT050000UV01Employee> asEmployee;
    @XmlElement(nillable = true)
    protected List<COCTMT050000UV01Citizen> asCitizen;
    @XmlElement(nillable = true)
    protected List<COCTMT050000UV01Student> asStudent;
    @XmlElement(nillable = true)
    protected List<COCTMT050000UV01Member> asMember;
    @XmlElement(nillable = true)
    protected List<COCTMT050000UV01OtherIDs> asOtherIDs;
    @XmlElement(nillable = true)
    protected List<COCTMT050000UV01ContactParty> contactParty;
    @XmlElement(nillable = true)
    protected List<COCTMT050000UV01Guardian> guardian;
    @XmlElement(nillable = true)
    protected List<COCTMT050000UV01PersonalRelationship> personalRelationship;
    @XmlElement(nillable = true)
    protected List<COCTMT050000UV01CareGiver> careGiver;
    @XmlElementRef(name = "birthPlace", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    protected JAXBElement<COCTMT050000UV01BirthPlace> birthPlace;
    @XmlElement(nillable = true)
    protected List<COCTMT670000UV04GuarantorRole> guarantorRole;
    @XmlElement(nillable = true)
    protected List<COCTMT050000UV01LanguageCommunication> languageCommunication;
    @XmlAttribute(name = "nullFlavor")
    protected NullFlavor nullFlavor;
    @XmlAttribute(name = "classCode")
    protected EntityClass classCode;
    @XmlAttribute(name = "determinerCode")
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
     * Gets the value of the telecom property.
     * 
     * @return
     *     possible object is
     *     {@link COLLTEL }
     *     
     */
    public COLLTEL getTelecom() {
        return telecom;
    }

    /**
     * Sets the value of the telecom property.
     * 
     * @param value
     *     allowed object is
     *     {@link COLLTEL }
     *     
     */
    public void setTelecom(COLLTEL value) {
        this.telecom = value;
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
     * Gets the value of the addr property.
     * 
     * @return
     *     possible object is
     *     {@link COLLAD }
     *     
     */
    public COLLAD getAddr() {
        return addr;
    }

    /**
     * Sets the value of the addr property.
     * 
     * @param value
     *     allowed object is
     *     {@link COLLAD }
     *     
     */
    public void setAddr(COLLAD value) {
        this.addr = value;
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
     * Gets the value of the asPatientOfOtherProvider property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the asPatientOfOtherProvider property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAsPatientOfOtherProvider().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT050000UV01PatientOfOtherProvider }
     * 
     * 
     */
    public List<COCTMT050000UV01PatientOfOtherProvider> getAsPatientOfOtherProvider() {
        if (asPatientOfOtherProvider == null) {
            asPatientOfOtherProvider = new ArrayList<COCTMT050000UV01PatientOfOtherProvider>();
        }
        return this.asPatientOfOtherProvider;
    }

    /**
     * Gets the value of the asEmployee property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the asEmployee property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAsEmployee().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT050000UV01Employee }
     * 
     * 
     */
    public List<COCTMT050000UV01Employee> getAsEmployee() {
        if (asEmployee == null) {
            asEmployee = new ArrayList<COCTMT050000UV01Employee>();
        }
        return this.asEmployee;
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
     * {@link COCTMT050000UV01Citizen }
     * 
     * 
     */
    public List<COCTMT050000UV01Citizen> getAsCitizen() {
        if (asCitizen == null) {
            asCitizen = new ArrayList<COCTMT050000UV01Citizen>();
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
     * {@link COCTMT050000UV01Student }
     * 
     * 
     */
    public List<COCTMT050000UV01Student> getAsStudent() {
        if (asStudent == null) {
            asStudent = new ArrayList<COCTMT050000UV01Student>();
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
     * {@link COCTMT050000UV01Member }
     * 
     * 
     */
    public List<COCTMT050000UV01Member> getAsMember() {
        if (asMember == null) {
            asMember = new ArrayList<COCTMT050000UV01Member>();
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
     * {@link COCTMT050000UV01OtherIDs }
     * 
     * 
     */
    public List<COCTMT050000UV01OtherIDs> getAsOtherIDs() {
        if (asOtherIDs == null) {
            asOtherIDs = new ArrayList<COCTMT050000UV01OtherIDs>();
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
     * {@link COCTMT050000UV01ContactParty }
     * 
     * 
     */
    public List<COCTMT050000UV01ContactParty> getContactParty() {
        if (contactParty == null) {
            contactParty = new ArrayList<COCTMT050000UV01ContactParty>();
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
     * {@link COCTMT050000UV01Guardian }
     * 
     * 
     */
    public List<COCTMT050000UV01Guardian> getGuardian() {
        if (guardian == null) {
            guardian = new ArrayList<COCTMT050000UV01Guardian>();
        }
        return this.guardian;
    }

    /**
     * Gets the value of the personalRelationship property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the personalRelationship property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPersonalRelationship().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT050000UV01PersonalRelationship }
     * 
     * 
     */
    public List<COCTMT050000UV01PersonalRelationship> getPersonalRelationship() {
        if (personalRelationship == null) {
            personalRelationship = new ArrayList<COCTMT050000UV01PersonalRelationship>();
        }
        return this.personalRelationship;
    }

    /**
     * Gets the value of the careGiver property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the careGiver property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCareGiver().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT050000UV01CareGiver }
     * 
     * 
     */
    public List<COCTMT050000UV01CareGiver> getCareGiver() {
        if (careGiver == null) {
            careGiver = new ArrayList<COCTMT050000UV01CareGiver>();
        }
        return this.careGiver;
    }

    /**
     * Gets the value of the birthPlace property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link COCTMT050000UV01BirthPlace }{@code >}
     *     
     */
    public JAXBElement<COCTMT050000UV01BirthPlace> getBirthPlace() {
        return birthPlace;
    }

    /**
     * Sets the value of the birthPlace property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link COCTMT050000UV01BirthPlace }{@code >}
     *     
     */
    public void setBirthPlace(JAXBElement<COCTMT050000UV01BirthPlace> value) {
        this.birthPlace = value;
    }

    /**
     * Gets the value of the guarantorRole property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the guarantorRole property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGuarantorRole().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link COCTMT670000UV04GuarantorRole }
     * 
     * 
     */
    public List<COCTMT670000UV04GuarantorRole> getGuarantorRole() {
        if (guarantorRole == null) {
            guarantorRole = new ArrayList<COCTMT670000UV04GuarantorRole>();
        }
        return this.guarantorRole;
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
     * {@link COCTMT050000UV01LanguageCommunication }
     * 
     * 
     */
    public List<COCTMT050000UV01LanguageCommunication> getLanguageCommunication() {
        if (languageCommunication == null) {
            languageCommunication = new ArrayList<COCTMT050000UV01LanguageCommunication>();
        }
        return this.languageCommunication;
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
     *     {@link EntityClass }
     *     
     */
    public EntityClass getClassCode() {
        if (classCode == null) {
            return EntityClass.PSN;
        } else {
            return classCode;
        }
    }

    /**
     * Sets the value of the classCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link EntityClass }
     *     
     */
    public void setClassCode(EntityClass value) {
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
        if (determinerCode == null) {
            return EntityDeterminer.INSTANCE;
        } else {
            return determinerCode;
        }
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
