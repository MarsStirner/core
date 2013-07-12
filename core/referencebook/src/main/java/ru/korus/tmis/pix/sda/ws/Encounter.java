
package ru.korus.tmis.pix.sda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Encounter complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Encounter">
 *   &lt;complexContent>
 *     &lt;extension base="{}SuperClass">
 *       &lt;sequence>
 *         &lt;element name="AdmissionType" type="{}AdmissionType" minOccurs="0"/>
 *         &lt;element name="EncounterType" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="E"/>
 *               &lt;enumeration value="I"/>
 *               &lt;enumeration value="O"/>
 *               &lt;enumeration value="N"/>
 *               &lt;enumeration value="G"/>
 *               &lt;enumeration value="P"/>
 *               &lt;enumeration value="S"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="AdmittingClinician" type="{}CareProvider" minOccurs="0"/>
 *         &lt;element name="AttendingClinicians" type="{}ArrayOfCareProviderCareProvider" minOccurs="0"/>
 *         &lt;element name="ConsultingClinicians" type="{}ArrayOfCareProviderCareProvider" minOccurs="0"/>
 *         &lt;element name="ReferringClinician" type="{}ReferralDoctor" minOccurs="0"/>
 *         &lt;element name="AccountNumber" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="220"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="PriorVisitNumber" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="220"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="PreAdmissionNumber" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="220"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="AdmissionSource" type="{}AdmissionSource" minOccurs="0"/>
 *         &lt;element name="AssignedWard" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AssignedRoom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AssignedBed" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PriorWard" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PriorRoom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PriorBed" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AdmitReason" type="{}AdmitReason" minOccurs="0"/>
 *         &lt;element name="HealthCareFacility" type="{}HealthCareFacility" minOccurs="0"/>
 *         &lt;element name="DischargeLocation" type="{}DischargeLocation" minOccurs="0"/>
 *         &lt;element name="VisitDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EncounterMRN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HealthFunds" type="{}ArrayOfHealthFundHealthFund" minOccurs="0"/>
 *         &lt;element name="EndTime" type="{}TimeStamp" minOccurs="0"/>
 *         &lt;element name="SeparationMode" type="{}SeparationMode" minOccurs="0"/>
 *         &lt;element name="Specialties" type="{}ArrayOfCareProviderTypeCareProviderType" minOccurs="0"/>
 *         &lt;element name="RecommendationsProvided" type="{}ArrayOfRecommendationRecommendation" minOccurs="0"/>
 *         &lt;element name="ExpectedAdmitTime" type="{}TimeStamp" minOccurs="0"/>
 *         &lt;element name="ExpectedDischargeTime" type="{}TimeStamp" minOccurs="0"/>
 *         &lt;element name="PublicityCode" type="{}PublicityCode" minOccurs="0"/>
 *         &lt;element name="Priority" type="{}EncounterPriority" minOccurs="0"/>
 *         &lt;element name="ExpectedLOAReturnTime" type="{}TimeStamp" minOccurs="0"/>
 *         &lt;element name="DiagnosisRelatedGroup" type="{}DiagnosisRelatedGroup" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Encounter", propOrder = {
    "admissionType",
    "encounterType",
    "admittingClinician",
    "attendingClinicians",
    "consultingClinicians",
    "referringClinician",
    "accountNumber",
    "priorVisitNumber",
    "preAdmissionNumber",
    "admissionSource",
    "assignedWard",
    "assignedRoom",
    "assignedBed",
    "priorWard",
    "priorRoom",
    "priorBed",
    "admitReason",
    "healthCareFacility",
    "dischargeLocation",
    "visitDescription",
    "encounterMRN",
    "healthFunds",
    "endTime",
    "separationMode",
    "specialties",
    "recommendationsProvided",
    "expectedAdmitTime",
    "expectedDischargeTime",
    "publicityCode",
    "priority",
    "expectedLOAReturnTime",
    "diagnosisRelatedGroup"
})
public class Encounter
    extends SuperClass
{

    @XmlElement(name = "AdmissionType")
    protected AdmissionType admissionType;
    @XmlElement(name = "EncounterType")
    protected String encounterType;
    @XmlElement(name = "AdmittingClinician")
    protected CareProvider admittingClinician;
    @XmlElement(name = "AttendingClinicians")
    protected ArrayOfCareProviderCareProvider attendingClinicians;
    @XmlElement(name = "ConsultingClinicians")
    protected ArrayOfCareProviderCareProvider consultingClinicians;
    @XmlElement(name = "ReferringClinician")
    protected ReferralDoctor referringClinician;
    @XmlElement(name = "AccountNumber")
    protected String accountNumber;
    @XmlElement(name = "PriorVisitNumber")
    protected String priorVisitNumber;
    @XmlElement(name = "PreAdmissionNumber")
    protected String preAdmissionNumber;
    @XmlElement(name = "AdmissionSource")
    protected AdmissionSource admissionSource;
    @XmlElement(name = "AssignedWard")
    protected String assignedWard;
    @XmlElement(name = "AssignedRoom")
    protected String assignedRoom;
    @XmlElement(name = "AssignedBed")
    protected String assignedBed;
    @XmlElement(name = "PriorWard")
    protected String priorWard;
    @XmlElement(name = "PriorRoom")
    protected String priorRoom;
    @XmlElement(name = "PriorBed")
    protected String priorBed;
    @XmlElement(name = "AdmitReason")
    protected AdmitReason admitReason;
    @XmlElement(name = "HealthCareFacility")
    protected HealthCareFacility healthCareFacility;
    @XmlElement(name = "DischargeLocation")
    protected DischargeLocation dischargeLocation;
    @XmlElement(name = "VisitDescription")
    protected String visitDescription;
    @XmlElement(name = "EncounterMRN")
    protected String encounterMRN;
    @XmlElement(name = "HealthFunds")
    protected ArrayOfHealthFundHealthFund healthFunds;
    @XmlElement(name = "EndTime")
    protected XMLGregorianCalendar endTime;
    @XmlElement(name = "SeparationMode")
    protected SeparationMode separationMode;
    @XmlElement(name = "Specialties")
    protected ArrayOfCareProviderTypeCareProviderType specialties;
    @XmlElement(name = "RecommendationsProvided")
    protected ArrayOfRecommendationRecommendation recommendationsProvided;
    @XmlElement(name = "ExpectedAdmitTime")
    protected XMLGregorianCalendar expectedAdmitTime;
    @XmlElement(name = "ExpectedDischargeTime")
    protected XMLGregorianCalendar expectedDischargeTime;
    @XmlElement(name = "PublicityCode")
    protected PublicityCode publicityCode;
    @XmlElement(name = "Priority")
    protected EncounterPriority priority;
    @XmlElement(name = "ExpectedLOAReturnTime")
    protected XMLGregorianCalendar expectedLOAReturnTime;
    @XmlElement(name = "DiagnosisRelatedGroup")
    protected DiagnosisRelatedGroup diagnosisRelatedGroup;

    /**
     * Gets the value of the admissionType property.
     * 
     * @return
     *     possible object is
     *     {@link AdmissionType }
     *     
     */
    public AdmissionType getAdmissionType() {
        return admissionType;
    }

    /**
     * Sets the value of the admissionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link AdmissionType }
     *     
     */
    public void setAdmissionType(AdmissionType value) {
        this.admissionType = value;
    }

    /**
     * Gets the value of the encounterType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEncounterType() {
        return encounterType;
    }

    /**
     * Sets the value of the encounterType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEncounterType(String value) {
        this.encounterType = value;
    }

    /**
     * Gets the value of the admittingClinician property.
     * 
     * @return
     *     possible object is
     *     {@link CareProvider }
     *     
     */
    public CareProvider getAdmittingClinician() {
        return admittingClinician;
    }

    /**
     * Sets the value of the admittingClinician property.
     * 
     * @param value
     *     allowed object is
     *     {@link CareProvider }
     *     
     */
    public void setAdmittingClinician(CareProvider value) {
        this.admittingClinician = value;
    }

    /**
     * Gets the value of the attendingClinicians property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCareProviderCareProvider }
     *     
     */
    public ArrayOfCareProviderCareProvider getAttendingClinicians() {
        return attendingClinicians;
    }

    /**
     * Sets the value of the attendingClinicians property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCareProviderCareProvider }
     *     
     */
    public void setAttendingClinicians(ArrayOfCareProviderCareProvider value) {
        this.attendingClinicians = value;
    }

    /**
     * Gets the value of the consultingClinicians property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCareProviderCareProvider }
     *     
     */
    public ArrayOfCareProviderCareProvider getConsultingClinicians() {
        return consultingClinicians;
    }

    /**
     * Sets the value of the consultingClinicians property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCareProviderCareProvider }
     *     
     */
    public void setConsultingClinicians(ArrayOfCareProviderCareProvider value) {
        this.consultingClinicians = value;
    }

    /**
     * Gets the value of the referringClinician property.
     * 
     * @return
     *     possible object is
     *     {@link ReferralDoctor }
     *     
     */
    public ReferralDoctor getReferringClinician() {
        return referringClinician;
    }

    /**
     * Sets the value of the referringClinician property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferralDoctor }
     *     
     */
    public void setReferringClinician(ReferralDoctor value) {
        this.referringClinician = value;
    }

    /**
     * Gets the value of the accountNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Sets the value of the accountNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountNumber(String value) {
        this.accountNumber = value;
    }

    /**
     * Gets the value of the priorVisitNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPriorVisitNumber() {
        return priorVisitNumber;
    }

    /**
     * Sets the value of the priorVisitNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPriorVisitNumber(String value) {
        this.priorVisitNumber = value;
    }

    /**
     * Gets the value of the preAdmissionNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPreAdmissionNumber() {
        return preAdmissionNumber;
    }

    /**
     * Sets the value of the preAdmissionNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPreAdmissionNumber(String value) {
        this.preAdmissionNumber = value;
    }

    /**
     * Gets the value of the admissionSource property.
     * 
     * @return
     *     possible object is
     *     {@link AdmissionSource }
     *     
     */
    public AdmissionSource getAdmissionSource() {
        return admissionSource;
    }

    /**
     * Sets the value of the admissionSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link AdmissionSource }
     *     
     */
    public void setAdmissionSource(AdmissionSource value) {
        this.admissionSource = value;
    }

    /**
     * Gets the value of the assignedWard property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssignedWard() {
        return assignedWard;
    }

    /**
     * Sets the value of the assignedWard property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssignedWard(String value) {
        this.assignedWard = value;
    }

    /**
     * Gets the value of the assignedRoom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssignedRoom() {
        return assignedRoom;
    }

    /**
     * Sets the value of the assignedRoom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssignedRoom(String value) {
        this.assignedRoom = value;
    }

    /**
     * Gets the value of the assignedBed property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssignedBed() {
        return assignedBed;
    }

    /**
     * Sets the value of the assignedBed property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssignedBed(String value) {
        this.assignedBed = value;
    }

    /**
     * Gets the value of the priorWard property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPriorWard() {
        return priorWard;
    }

    /**
     * Sets the value of the priorWard property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPriorWard(String value) {
        this.priorWard = value;
    }

    /**
     * Gets the value of the priorRoom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPriorRoom() {
        return priorRoom;
    }

    /**
     * Sets the value of the priorRoom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPriorRoom(String value) {
        this.priorRoom = value;
    }

    /**
     * Gets the value of the priorBed property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPriorBed() {
        return priorBed;
    }

    /**
     * Sets the value of the priorBed property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPriorBed(String value) {
        this.priorBed = value;
    }

    /**
     * Gets the value of the admitReason property.
     * 
     * @return
     *     possible object is
     *     {@link AdmitReason }
     *     
     */
    public AdmitReason getAdmitReason() {
        return admitReason;
    }

    /**
     * Sets the value of the admitReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link AdmitReason }
     *     
     */
    public void setAdmitReason(AdmitReason value) {
        this.admitReason = value;
    }

    /**
     * Gets the value of the healthCareFacility property.
     * 
     * @return
     *     possible object is
     *     {@link HealthCareFacility }
     *     
     */
    public HealthCareFacility getHealthCareFacility() {
        return healthCareFacility;
    }

    /**
     * Sets the value of the healthCareFacility property.
     * 
     * @param value
     *     allowed object is
     *     {@link HealthCareFacility }
     *     
     */
    public void setHealthCareFacility(HealthCareFacility value) {
        this.healthCareFacility = value;
    }

    /**
     * Gets the value of the dischargeLocation property.
     * 
     * @return
     *     possible object is
     *     {@link DischargeLocation }
     *     
     */
    public DischargeLocation getDischargeLocation() {
        return dischargeLocation;
    }

    /**
     * Sets the value of the dischargeLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link DischargeLocation }
     *     
     */
    public void setDischargeLocation(DischargeLocation value) {
        this.dischargeLocation = value;
    }

    /**
     * Gets the value of the visitDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVisitDescription() {
        return visitDescription;
    }

    /**
     * Sets the value of the visitDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVisitDescription(String value) {
        this.visitDescription = value;
    }

    /**
     * Gets the value of the encounterMRN property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEncounterMRN() {
        return encounterMRN;
    }

    /**
     * Sets the value of the encounterMRN property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEncounterMRN(String value) {
        this.encounterMRN = value;
    }

    /**
     * Gets the value of the healthFunds property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfHealthFundHealthFund }
     *     
     */
    public ArrayOfHealthFundHealthFund getHealthFunds() {
        return healthFunds;
    }

    /**
     * Sets the value of the healthFunds property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfHealthFundHealthFund }
     *     
     */
    public void setHealthFunds(ArrayOfHealthFundHealthFund value) {
        this.healthFunds = value;
    }

    /**
     * Gets the value of the endTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndTime() {
        return endTime;
    }

    /**
     * Sets the value of the endTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndTime(XMLGregorianCalendar value) {
        this.endTime = value;
    }

    /**
     * Gets the value of the separationMode property.
     * 
     * @return
     *     possible object is
     *     {@link SeparationMode }
     *     
     */
    public SeparationMode getSeparationMode() {
        return separationMode;
    }

    /**
     * Sets the value of the separationMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link SeparationMode }
     *     
     */
    public void setSeparationMode(SeparationMode value) {
        this.separationMode = value;
    }

    /**
     * Gets the value of the specialties property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCareProviderTypeCareProviderType }
     *     
     */
    public ArrayOfCareProviderTypeCareProviderType getSpecialties() {
        return specialties;
    }

    /**
     * Sets the value of the specialties property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCareProviderTypeCareProviderType }
     *     
     */
    public void setSpecialties(ArrayOfCareProviderTypeCareProviderType value) {
        this.specialties = value;
    }

    /**
     * Gets the value of the recommendationsProvided property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRecommendationRecommendation }
     *     
     */
    public ArrayOfRecommendationRecommendation getRecommendationsProvided() {
        return recommendationsProvided;
    }

    /**
     * Sets the value of the recommendationsProvided property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRecommendationRecommendation }
     *     
     */
    public void setRecommendationsProvided(ArrayOfRecommendationRecommendation value) {
        this.recommendationsProvided = value;
    }

    /**
     * Gets the value of the expectedAdmitTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExpectedAdmitTime() {
        return expectedAdmitTime;
    }

    /**
     * Sets the value of the expectedAdmitTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExpectedAdmitTime(XMLGregorianCalendar value) {
        this.expectedAdmitTime = value;
    }

    /**
     * Gets the value of the expectedDischargeTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExpectedDischargeTime() {
        return expectedDischargeTime;
    }

    /**
     * Sets the value of the expectedDischargeTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExpectedDischargeTime(XMLGregorianCalendar value) {
        this.expectedDischargeTime = value;
    }

    /**
     * Gets the value of the publicityCode property.
     * 
     * @return
     *     possible object is
     *     {@link PublicityCode }
     *     
     */
    public PublicityCode getPublicityCode() {
        return publicityCode;
    }

    /**
     * Sets the value of the publicityCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link PublicityCode }
     *     
     */
    public void setPublicityCode(PublicityCode value) {
        this.publicityCode = value;
    }

    /**
     * Gets the value of the priority property.
     * 
     * @return
     *     possible object is
     *     {@link EncounterPriority }
     *     
     */
    public EncounterPriority getPriority() {
        return priority;
    }

    /**
     * Sets the value of the priority property.
     * 
     * @param value
     *     allowed object is
     *     {@link EncounterPriority }
     *     
     */
    public void setPriority(EncounterPriority value) {
        this.priority = value;
    }

    /**
     * Gets the value of the expectedLOAReturnTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getExpectedLOAReturnTime() {
        return expectedLOAReturnTime;
    }

    /**
     * Sets the value of the expectedLOAReturnTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setExpectedLOAReturnTime(XMLGregorianCalendar value) {
        this.expectedLOAReturnTime = value;
    }

    /**
     * Gets the value of the diagnosisRelatedGroup property.
     * 
     * @return
     *     possible object is
     *     {@link DiagnosisRelatedGroup }
     *     
     */
    public DiagnosisRelatedGroup getDiagnosisRelatedGroup() {
        return diagnosisRelatedGroup;
    }

    /**
     * Sets the value of the diagnosisRelatedGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link DiagnosisRelatedGroup }
     *     
     */
    public void setDiagnosisRelatedGroup(DiagnosisRelatedGroup value) {
        this.diagnosisRelatedGroup = value;
    }

}
