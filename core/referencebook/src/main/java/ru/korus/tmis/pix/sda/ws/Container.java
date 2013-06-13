
package ru.korus.tmis.pix.sda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Container complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Container">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Patient" type="{}Patient" minOccurs="0"/>
 *         &lt;element name="Encounters" type="{}ArrayOfEncounterEncounter" minOccurs="0"/>
 *         &lt;element name="Alerts" type="{}ArrayOfAlertAlert" minOccurs="0"/>
 *         &lt;element name="AdvanceDirectives" type="{}ArrayOfAdvanceDirectiveAdvanceDirective" minOccurs="0"/>
 *         &lt;element name="Allergies" type="{}ArrayOfAllergyAllergy" minOccurs="0"/>
 *         &lt;element name="IllnessHistories" type="{}ArrayOfIllnessHistoryIllnessHistory" minOccurs="0"/>
 *         &lt;element name="SocialHistories" type="{}ArrayOfSocialHistorySocialHistory" minOccurs="0"/>
 *         &lt;element name="FamilyHistories" type="{}ArrayOfFamilyHistoryFamilyHistory" minOccurs="0"/>
 *         &lt;element name="Guarantors" type="{}ArrayOfGuarantorGuarantor" minOccurs="0"/>
 *         &lt;element name="Diagnoses" type="{}ArrayOfDiagnosisDiagnosis" minOccurs="0"/>
 *         &lt;element name="Observations" type="{}ArrayOfObservationObservation" minOccurs="0"/>
 *         &lt;element name="Problems" type="{}ArrayOfProblemProblem" minOccurs="0"/>
 *         &lt;element name="PhysicalExams" type="{}ArrayOfPhysicalExamPhysicalExam" minOccurs="0"/>
 *         &lt;element name="Procedures" type="{}ArrayOfProcedureProcedure" minOccurs="0"/>
 *         &lt;element name="Documents" type="{}ArrayOfDocumentDocument" minOccurs="0"/>
 *         &lt;element name="LabOrders" type="{}ArrayOfLabOrderLabOrder" minOccurs="0"/>
 *         &lt;element name="RadOrders" type="{}ArrayOfRadOrderRadOrder" minOccurs="0"/>
 *         &lt;element name="OtherOrders" type="{}ArrayOfOtherOrderOtherOrder" minOccurs="0"/>
 *         &lt;element name="Medications" type="{}ArrayOfMedicationMedication" minOccurs="0"/>
 *         &lt;element name="Vaccinations" type="{}ArrayOfVaccinationVaccination" minOccurs="0"/>
 *         &lt;element name="Appointments" type="{}ArrayOfAppointmentAppointment" minOccurs="0"/>
 *         &lt;element name="Referrals" type="{}ArrayOfReferralReferral" minOccurs="0"/>
 *         &lt;element name="SessionId" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="32000"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ControlId" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="32000"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Action" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="AddOrUpdate"/>
 *               &lt;enumeration value="Merge"/>
 *               &lt;enumeration value="Move"/>
 *               &lt;enumeration value="DeletePatient"/>
 *               &lt;enumeration value="DeleteEncounter"/>
 *               &lt;enumeration value="CancelAdmit"/>
 *               &lt;enumeration value="CancelDischarge"/>
 *               &lt;enumeration value="QueryResponse"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="EventDescription" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="220"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="UpdateECRDemographics" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="SendingFacility" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="220"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ClinicalRelationships" type="{}ArrayOfClinicalRelationshipClinicalRelationship" minOccurs="0"/>
 *         &lt;element name="ProgramMemberships" type="{}ArrayOfProgramMembershipProgramMembership" minOccurs="0"/>
 *         &lt;element name="AdditionalInfo" type="{}ArrayOfAdditionalInfoItemPairOfAdditionalInfoKeyAdditionalInfoItem" minOccurs="0"/>
 *         &lt;element name="CustomObjects" type="{}ArrayOfCustomObjectCustomObject" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Container", propOrder = {
    "patient",
    "encounters",
    "alerts",
    "advanceDirectives",
    "allergies",
    "illnessHistories",
    "socialHistories",
    "familyHistories",
    "guarantors",
    "diagnoses",
    "observations",
    "problems",
    "physicalExams",
    "procedures",
    "documents",
    "labOrders",
    "radOrders",
    "otherOrders",
    "medications",
    "vaccinations",
    "appointments",
    "referrals",
    "sessionId",
    "controlId",
    "action",
    "eventDescription",
    "updateECRDemographics",
    "sendingFacility",
    "clinicalRelationships",
    "programMemberships",
    "additionalInfo",
    "customObjects"
})
public class Container {

    @XmlElement(name = "Patient")
    protected Patient patient;
    @XmlElement(name = "Encounters")
    protected ArrayOfEncounterEncounter encounters;
    @XmlElement(name = "Alerts")
    protected ArrayOfAlertAlert alerts;
    @XmlElement(name = "AdvanceDirectives")
    protected ArrayOfAdvanceDirectiveAdvanceDirective advanceDirectives;
    @XmlElement(name = "Allergies")
    protected ArrayOfAllergyAllergy allergies;
    @XmlElement(name = "IllnessHistories")
    protected ArrayOfIllnessHistoryIllnessHistory illnessHistories;
    @XmlElement(name = "SocialHistories")
    protected ArrayOfSocialHistorySocialHistory socialHistories;
    @XmlElement(name = "FamilyHistories")
    protected ArrayOfFamilyHistoryFamilyHistory familyHistories;
    @XmlElement(name = "Guarantors")
    protected ArrayOfGuarantorGuarantor guarantors;
    @XmlElement(name = "Diagnoses")
    protected ArrayOfDiagnosisDiagnosis diagnoses;
    @XmlElement(name = "Observations")
    protected ArrayOfObservationObservation observations;
    @XmlElement(name = "Problems")
    protected ArrayOfProblemProblem problems;
    @XmlElement(name = "PhysicalExams")
    protected ArrayOfPhysicalExamPhysicalExam physicalExams;
    @XmlElement(name = "Procedures")
    protected ArrayOfProcedureProcedure procedures;
    @XmlElement(name = "Documents")
    protected ArrayOfDocumentDocument documents;
    @XmlElement(name = "LabOrders")
    protected ArrayOfLabOrderLabOrder labOrders;
    @XmlElement(name = "RadOrders")
    protected ArrayOfRadOrderRadOrder radOrders;
    @XmlElement(name = "OtherOrders")
    protected ArrayOfOtherOrderOtherOrder otherOrders;
    @XmlElement(name = "Medications")
    protected ArrayOfMedicationMedication medications;
    @XmlElement(name = "Vaccinations")
    protected ArrayOfVaccinationVaccination vaccinations;
    @XmlElement(name = "Appointments")
    protected ArrayOfAppointmentAppointment appointments;
    @XmlElement(name = "Referrals")
    protected ArrayOfReferralReferral referrals;
    @XmlElement(name = "SessionId")
    protected String sessionId;
    @XmlElement(name = "ControlId")
    protected String controlId;
    @XmlElement(name = "Action")
    protected String action;
    @XmlElement(name = "EventDescription")
    protected String eventDescription;
    @XmlElement(name = "UpdateECRDemographics")
    protected Boolean updateECRDemographics;
    @XmlElement(name = "SendingFacility")
    protected String sendingFacility;
    @XmlElement(name = "ClinicalRelationships")
    protected ArrayOfClinicalRelationshipClinicalRelationship clinicalRelationships;
    @XmlElement(name = "ProgramMemberships")
    protected ArrayOfProgramMembershipProgramMembership programMemberships;
    @XmlElement(name = "AdditionalInfo")
    protected ArrayOfAdditionalInfoItemPairOfAdditionalInfoKeyAdditionalInfoItem additionalInfo;
    @XmlElement(name = "CustomObjects")
    protected ArrayOfCustomObjectCustomObject customObjects;

    /**
     * Gets the value of the patient property.
     * 
     * @return
     *     possible object is
     *     {@link Patient }
     *     
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     * Sets the value of the patient property.
     * 
     * @param value
     *     allowed object is
     *     {@link Patient }
     *     
     */
    public void setPatient(Patient value) {
        this.patient = value;
    }

    /**
     * Gets the value of the encounters property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfEncounterEncounter }
     *     
     */
    public ArrayOfEncounterEncounter getEncounters() {
        return encounters;
    }

    /**
     * Sets the value of the encounters property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfEncounterEncounter }
     *     
     */
    public void setEncounters(ArrayOfEncounterEncounter value) {
        this.encounters = value;
    }

    /**
     * Gets the value of the alerts property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAlertAlert }
     *     
     */
    public ArrayOfAlertAlert getAlerts() {
        return alerts;
    }

    /**
     * Sets the value of the alerts property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAlertAlert }
     *     
     */
    public void setAlerts(ArrayOfAlertAlert value) {
        this.alerts = value;
    }

    /**
     * Gets the value of the advanceDirectives property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAdvanceDirectiveAdvanceDirective }
     *     
     */
    public ArrayOfAdvanceDirectiveAdvanceDirective getAdvanceDirectives() {
        return advanceDirectives;
    }

    /**
     * Sets the value of the advanceDirectives property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAdvanceDirectiveAdvanceDirective }
     *     
     */
    public void setAdvanceDirectives(ArrayOfAdvanceDirectiveAdvanceDirective value) {
        this.advanceDirectives = value;
    }

    /**
     * Gets the value of the allergies property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAllergyAllergy }
     *     
     */
    public ArrayOfAllergyAllergy getAllergies() {
        return allergies;
    }

    /**
     * Sets the value of the allergies property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAllergyAllergy }
     *     
     */
    public void setAllergies(ArrayOfAllergyAllergy value) {
        this.allergies = value;
    }

    /**
     * Gets the value of the illnessHistories property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfIllnessHistoryIllnessHistory }
     *     
     */
    public ArrayOfIllnessHistoryIllnessHistory getIllnessHistories() {
        return illnessHistories;
    }

    /**
     * Sets the value of the illnessHistories property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfIllnessHistoryIllnessHistory }
     *     
     */
    public void setIllnessHistories(ArrayOfIllnessHistoryIllnessHistory value) {
        this.illnessHistories = value;
    }

    /**
     * Gets the value of the socialHistories property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfSocialHistorySocialHistory }
     *     
     */
    public ArrayOfSocialHistorySocialHistory getSocialHistories() {
        return socialHistories;
    }

    /**
     * Sets the value of the socialHistories property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfSocialHistorySocialHistory }
     *     
     */
    public void setSocialHistories(ArrayOfSocialHistorySocialHistory value) {
        this.socialHistories = value;
    }

    /**
     * Gets the value of the familyHistories property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfFamilyHistoryFamilyHistory }
     *     
     */
    public ArrayOfFamilyHistoryFamilyHistory getFamilyHistories() {
        return familyHistories;
    }

    /**
     * Sets the value of the familyHistories property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfFamilyHistoryFamilyHistory }
     *     
     */
    public void setFamilyHistories(ArrayOfFamilyHistoryFamilyHistory value) {
        this.familyHistories = value;
    }

    /**
     * Gets the value of the guarantors property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfGuarantorGuarantor }
     *     
     */
    public ArrayOfGuarantorGuarantor getGuarantors() {
        return guarantors;
    }

    /**
     * Sets the value of the guarantors property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfGuarantorGuarantor }
     *     
     */
    public void setGuarantors(ArrayOfGuarantorGuarantor value) {
        this.guarantors = value;
    }

    /**
     * Gets the value of the diagnoses property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDiagnosisDiagnosis }
     *     
     */
    public ArrayOfDiagnosisDiagnosis getDiagnoses() {
        return diagnoses;
    }

    /**
     * Sets the value of the diagnoses property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDiagnosisDiagnosis }
     *     
     */
    public void setDiagnoses(ArrayOfDiagnosisDiagnosis value) {
        this.diagnoses = value;
    }

    /**
     * Gets the value of the observations property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfObservationObservation }
     *     
     */
    public ArrayOfObservationObservation getObservations() {
        return observations;
    }

    /**
     * Sets the value of the observations property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfObservationObservation }
     *     
     */
    public void setObservations(ArrayOfObservationObservation value) {
        this.observations = value;
    }

    /**
     * Gets the value of the problems property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfProblemProblem }
     *     
     */
    public ArrayOfProblemProblem getProblems() {
        return problems;
    }

    /**
     * Sets the value of the problems property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfProblemProblem }
     *     
     */
    public void setProblems(ArrayOfProblemProblem value) {
        this.problems = value;
    }

    /**
     * Gets the value of the physicalExams property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfPhysicalExamPhysicalExam }
     *     
     */
    public ArrayOfPhysicalExamPhysicalExam getPhysicalExams() {
        return physicalExams;
    }

    /**
     * Sets the value of the physicalExams property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfPhysicalExamPhysicalExam }
     *     
     */
    public void setPhysicalExams(ArrayOfPhysicalExamPhysicalExam value) {
        this.physicalExams = value;
    }

    /**
     * Gets the value of the procedures property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfProcedureProcedure }
     *     
     */
    public ArrayOfProcedureProcedure getProcedures() {
        return procedures;
    }

    /**
     * Sets the value of the procedures property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfProcedureProcedure }
     *     
     */
    public void setProcedures(ArrayOfProcedureProcedure value) {
        this.procedures = value;
    }

    /**
     * Gets the value of the documents property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDocumentDocument }
     *     
     */
    public ArrayOfDocumentDocument getDocuments() {
        return documents;
    }

    /**
     * Sets the value of the documents property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDocumentDocument }
     *     
     */
    public void setDocuments(ArrayOfDocumentDocument value) {
        this.documents = value;
    }

    /**
     * Gets the value of the labOrders property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfLabOrderLabOrder }
     *     
     */
    public ArrayOfLabOrderLabOrder getLabOrders() {
        return labOrders;
    }

    /**
     * Sets the value of the labOrders property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfLabOrderLabOrder }
     *     
     */
    public void setLabOrders(ArrayOfLabOrderLabOrder value) {
        this.labOrders = value;
    }

    /**
     * Gets the value of the radOrders property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfRadOrderRadOrder }
     *     
     */
    public ArrayOfRadOrderRadOrder getRadOrders() {
        return radOrders;
    }

    /**
     * Sets the value of the radOrders property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfRadOrderRadOrder }
     *     
     */
    public void setRadOrders(ArrayOfRadOrderRadOrder value) {
        this.radOrders = value;
    }

    /**
     * Gets the value of the otherOrders property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfOtherOrderOtherOrder }
     *     
     */
    public ArrayOfOtherOrderOtherOrder getOtherOrders() {
        return otherOrders;
    }

    /**
     * Sets the value of the otherOrders property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfOtherOrderOtherOrder }
     *     
     */
    public void setOtherOrders(ArrayOfOtherOrderOtherOrder value) {
        this.otherOrders = value;
    }

    /**
     * Gets the value of the medications property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfMedicationMedication }
     *     
     */
    public ArrayOfMedicationMedication getMedications() {
        return medications;
    }

    /**
     * Sets the value of the medications property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfMedicationMedication }
     *     
     */
    public void setMedications(ArrayOfMedicationMedication value) {
        this.medications = value;
    }

    /**
     * Gets the value of the vaccinations property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfVaccinationVaccination }
     *     
     */
    public ArrayOfVaccinationVaccination getVaccinations() {
        return vaccinations;
    }

    /**
     * Sets the value of the vaccinations property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfVaccinationVaccination }
     *     
     */
    public void setVaccinations(ArrayOfVaccinationVaccination value) {
        this.vaccinations = value;
    }

    /**
     * Gets the value of the appointments property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAppointmentAppointment }
     *     
     */
    public ArrayOfAppointmentAppointment getAppointments() {
        return appointments;
    }

    /**
     * Sets the value of the appointments property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAppointmentAppointment }
     *     
     */
    public void setAppointments(ArrayOfAppointmentAppointment value) {
        this.appointments = value;
    }

    /**
     * Gets the value of the referrals property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfReferralReferral }
     *     
     */
    public ArrayOfReferralReferral getReferrals() {
        return referrals;
    }

    /**
     * Sets the value of the referrals property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfReferralReferral }
     *     
     */
    public void setReferrals(ArrayOfReferralReferral value) {
        this.referrals = value;
    }

    /**
     * Gets the value of the sessionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * Sets the value of the sessionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSessionId(String value) {
        this.sessionId = value;
    }

    /**
     * Gets the value of the controlId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getControlId() {
        return controlId;
    }

    /**
     * Sets the value of the controlId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setControlId(String value) {
        this.controlId = value;
    }

    /**
     * Gets the value of the action property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAction() {
        return action;
    }

    /**
     * Sets the value of the action property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAction(String value) {
        this.action = value;
    }

    /**
     * Gets the value of the eventDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEventDescription() {
        return eventDescription;
    }

    /**
     * Sets the value of the eventDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEventDescription(String value) {
        this.eventDescription = value;
    }

    /**
     * Gets the value of the updateECRDemographics property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isUpdateECRDemographics() {
        return updateECRDemographics;
    }

    /**
     * Sets the value of the updateECRDemographics property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setUpdateECRDemographics(Boolean value) {
        this.updateECRDemographics = value;
    }

    /**
     * Gets the value of the sendingFacility property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSendingFacility() {
        return sendingFacility;
    }

    /**
     * Sets the value of the sendingFacility property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSendingFacility(String value) {
        this.sendingFacility = value;
    }

    /**
     * Gets the value of the clinicalRelationships property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfClinicalRelationshipClinicalRelationship }
     *     
     */
    public ArrayOfClinicalRelationshipClinicalRelationship getClinicalRelationships() {
        return clinicalRelationships;
    }

    /**
     * Sets the value of the clinicalRelationships property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfClinicalRelationshipClinicalRelationship }
     *     
     */
    public void setClinicalRelationships(ArrayOfClinicalRelationshipClinicalRelationship value) {
        this.clinicalRelationships = value;
    }

    /**
     * Gets the value of the programMemberships property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfProgramMembershipProgramMembership }
     *     
     */
    public ArrayOfProgramMembershipProgramMembership getProgramMemberships() {
        return programMemberships;
    }

    /**
     * Sets the value of the programMemberships property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfProgramMembershipProgramMembership }
     *     
     */
    public void setProgramMemberships(ArrayOfProgramMembershipProgramMembership value) {
        this.programMemberships = value;
    }

    /**
     * Gets the value of the additionalInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAdditionalInfoItemPairOfAdditionalInfoKeyAdditionalInfoItem }
     *     
     */
    public ArrayOfAdditionalInfoItemPairOfAdditionalInfoKeyAdditionalInfoItem getAdditionalInfo() {
        return additionalInfo;
    }

    /**
     * Sets the value of the additionalInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAdditionalInfoItemPairOfAdditionalInfoKeyAdditionalInfoItem }
     *     
     */
    public void setAdditionalInfo(ArrayOfAdditionalInfoItemPairOfAdditionalInfoKeyAdditionalInfoItem value) {
        this.additionalInfo = value;
    }

    /**
     * Gets the value of the customObjects property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCustomObjectCustomObject }
     *     
     */
    public ArrayOfCustomObjectCustomObject getCustomObjects() {
        return customObjects;
    }

    /**
     * Sets the value of the customObjects property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCustomObjectCustomObject }
     *     
     */
    public void setCustomObjects(ArrayOfCustomObjectCustomObject value) {
        this.customObjects = value;
    }

}
