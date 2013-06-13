
package ru.korus.tmis.pix.sda.ws;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Medication complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Medication">
 *   &lt;complexContent>
 *     &lt;extension base="{}SuperClass">
 *       &lt;sequence>
 *         &lt;element name="PlacerId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FillerId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderItem" type="{}Order" minOccurs="0"/>
 *         &lt;element name="OrderCategory" type="{}OrderCategory" minOccurs="0"/>
 *         &lt;element name="OrderQuantity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderedBy" type="{}CareProvider" minOccurs="0"/>
 *         &lt;element name="EnteringOrganization" type="{}HealthCareFacility" minOccurs="0"/>
 *         &lt;element name="VerifiedBy" type="{}CareProvider" minOccurs="0"/>
 *         &lt;element name="CallbackNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Specimen" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SpecimenCollectedTime" type="{}TimeStamp" minOccurs="0"/>
 *         &lt;element name="SpecimenReceivedTime" type="{}TimeStamp" minOccurs="0"/>
 *         &lt;element name="ReassessmentTime" type="{}TimeStamp" minOccurs="0"/>
 *         &lt;element name="Frequency" type="{}Frequency" minOccurs="0"/>
 *         &lt;element name="Duration" type="{}Duration" minOccurs="0"/>
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Priority" type="{}Priority" minOccurs="0"/>
 *         &lt;element name="ConfidentialityCode" type="{}Confidentiality" minOccurs="0"/>
 *         &lt;element name="Condition" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TextInstruction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderGroup" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Comments" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ConsultationDepartment" type="{}HealthCareFacility" minOccurs="0"/>
 *         &lt;element name="AuthorizationTime" type="{}TimeStamp" minOccurs="0"/>
 *         &lt;element name="ReceivingLocation" type="{}HealthCareFacility" minOccurs="0"/>
 *         &lt;element name="VerifiedComments" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ResultCopiesTo" type="{}ArrayOfCareProviderCareProvider" minOccurs="0"/>
 *         &lt;element name="GroupId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DrugProduct" type="{}DrugProduct" minOccurs="0"/>
 *         &lt;element name="StrengthVolume" type="{}Numeric" minOccurs="0"/>
 *         &lt;element name="StrengthVolumeUnits" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RateAmount" type="{}Numeric" minOccurs="0"/>
 *         &lt;element name="RateUnits" type="{}RateUnits" minOccurs="0"/>
 *         &lt;element name="RateTimeUnit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DoseQuantity" type="{}Numeric" minOccurs="0"/>
 *         &lt;element name="MaxDoseQuantity" type="{}Numeric" minOccurs="0"/>
 *         &lt;element name="NumberOfRefills" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DoseUoM" type="{}UoM" minOccurs="0"/>
 *         &lt;element name="DosageForm" type="{}DosageForm" minOccurs="0"/>
 *         &lt;element name="Route" type="{}Route" minOccurs="0"/>
 *         &lt;element name="Indication" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PharmacyStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PrescriptionNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RefillNumber" type="{}Numeric" minOccurs="0"/>
 *         &lt;element name="RefillDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DosageSteps" type="{}ArrayOfDosageStepDosageStep" minOccurs="0"/>
 *         &lt;element name="Administrations" type="{}ArrayOfAdministrationAdministration" minOccurs="0"/>
 *         &lt;element name="ComponentMeds" type="{}ArrayOfDrugProductDrugProduct" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Medication", propOrder = {
    "placerId",
    "fillerId",
    "orderItem",
    "orderCategory",
    "orderQuantity",
    "orderedBy",
    "enteringOrganization",
    "verifiedBy",
    "callbackNumber",
    "specimen",
    "specimenCollectedTime",
    "specimenReceivedTime",
    "reassessmentTime",
    "frequency",
    "duration",
    "status",
    "priority",
    "confidentialityCode",
    "condition",
    "textInstruction",
    "orderGroup",
    "comments",
    "consultationDepartment",
    "authorizationTime",
    "receivingLocation",
    "verifiedComments",
    "resultCopiesTo",
    "groupId",
    "drugProduct",
    "strengthVolume",
    "strengthVolumeUnits",
    "rateAmount",
    "rateUnits",
    "rateTimeUnit",
    "doseQuantity",
    "maxDoseQuantity",
    "numberOfRefills",
    "doseUoM",
    "dosageForm",
    "route",
    "indication",
    "pharmacyStatus",
    "prescriptionNumber",
    "refillNumber",
    "refillDescription",
    "dosageSteps",
    "administrations",
    "componentMeds"
})
public class Medication
    extends SuperClass
{

    @XmlElement(name = "PlacerId")
    protected String placerId;
    @XmlElement(name = "FillerId")
    protected String fillerId;
    @XmlElement(name = "OrderItem")
    protected Order orderItem;
    @XmlElement(name = "OrderCategory")
    protected OrderCategory orderCategory;
    @XmlElement(name = "OrderQuantity")
    protected String orderQuantity;
    @XmlElement(name = "OrderedBy")
    protected CareProvider orderedBy;
    @XmlElement(name = "EnteringOrganization")
    protected HealthCareFacility enteringOrganization;
    @XmlElement(name = "VerifiedBy")
    protected CareProvider verifiedBy;
    @XmlElement(name = "CallbackNumber")
    protected String callbackNumber;
    @XmlElement(name = "Specimen")
    protected String specimen;
    @XmlElement(name = "SpecimenCollectedTime")
    protected XMLGregorianCalendar specimenCollectedTime;
    @XmlElement(name = "SpecimenReceivedTime")
    protected XMLGregorianCalendar specimenReceivedTime;
    @XmlElement(name = "ReassessmentTime")
    protected XMLGregorianCalendar reassessmentTime;
    @XmlElement(name = "Frequency")
    protected Frequency frequency;
    @XmlElement(name = "Duration")
    protected Duration duration;
    @XmlElement(name = "Status")
    protected String status;
    @XmlElement(name = "Priority")
    protected Priority priority;
    @XmlElement(name = "ConfidentialityCode")
    protected Confidentiality confidentialityCode;
    @XmlElement(name = "Condition")
    protected String condition;
    @XmlElement(name = "TextInstruction")
    protected String textInstruction;
    @XmlElement(name = "OrderGroup")
    protected String orderGroup;
    @XmlElement(name = "Comments")
    protected String comments;
    @XmlElement(name = "ConsultationDepartment")
    protected HealthCareFacility consultationDepartment;
    @XmlElement(name = "AuthorizationTime")
    protected XMLGregorianCalendar authorizationTime;
    @XmlElement(name = "ReceivingLocation")
    protected HealthCareFacility receivingLocation;
    @XmlElement(name = "VerifiedComments")
    protected String verifiedComments;
    @XmlElement(name = "ResultCopiesTo")
    protected ArrayOfCareProviderCareProvider resultCopiesTo;
    @XmlElement(name = "GroupId")
    protected String groupId;
    @XmlElement(name = "DrugProduct")
    protected DrugProduct drugProduct;
    @XmlElement(name = "StrengthVolume")
    protected BigDecimal strengthVolume;
    @XmlElement(name = "StrengthVolumeUnits")
    protected String strengthVolumeUnits;
    @XmlElement(name = "RateAmount")
    protected BigDecimal rateAmount;
    @XmlElement(name = "RateUnits")
    protected RateUnits rateUnits;
    @XmlElement(name = "RateTimeUnit")
    protected String rateTimeUnit;
    @XmlElement(name = "DoseQuantity")
    protected BigDecimal doseQuantity;
    @XmlElement(name = "MaxDoseQuantity")
    protected BigDecimal maxDoseQuantity;
    @XmlElement(name = "NumberOfRefills")
    protected String numberOfRefills;
    @XmlElement(name = "DoseUoM")
    protected UoM doseUoM;
    @XmlElement(name = "DosageForm")
    protected DosageForm dosageForm;
    @XmlElement(name = "Route")
    protected Route route;
    @XmlElement(name = "Indication")
    protected String indication;
    @XmlElement(name = "PharmacyStatus")
    protected String pharmacyStatus;
    @XmlElement(name = "PrescriptionNumber")
    protected String prescriptionNumber;
    @XmlElement(name = "RefillNumber")
    protected BigDecimal refillNumber;
    @XmlElement(name = "RefillDescription")
    protected String refillDescription;
    @XmlElement(name = "DosageSteps")
    protected ArrayOfDosageStepDosageStep dosageSteps;
    @XmlElement(name = "Administrations")
    protected ArrayOfAdministrationAdministration administrations;
    @XmlElement(name = "ComponentMeds")
    protected ArrayOfDrugProductDrugProduct componentMeds;

    /**
     * Gets the value of the placerId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlacerId() {
        return placerId;
    }

    /**
     * Sets the value of the placerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlacerId(String value) {
        this.placerId = value;
    }

    /**
     * Gets the value of the fillerId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFillerId() {
        return fillerId;
    }

    /**
     * Sets the value of the fillerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFillerId(String value) {
        this.fillerId = value;
    }

    /**
     * Gets the value of the orderItem property.
     * 
     * @return
     *     possible object is
     *     {@link Order }
     *     
     */
    public Order getOrderItem() {
        return orderItem;
    }

    /**
     * Sets the value of the orderItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link Order }
     *     
     */
    public void setOrderItem(Order value) {
        this.orderItem = value;
    }

    /**
     * Gets the value of the orderCategory property.
     * 
     * @return
     *     possible object is
     *     {@link OrderCategory }
     *     
     */
    public OrderCategory getOrderCategory() {
        return orderCategory;
    }

    /**
     * Sets the value of the orderCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderCategory }
     *     
     */
    public void setOrderCategory(OrderCategory value) {
        this.orderCategory = value;
    }

    /**
     * Gets the value of the orderQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderQuantity() {
        return orderQuantity;
    }

    /**
     * Sets the value of the orderQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderQuantity(String value) {
        this.orderQuantity = value;
    }

    /**
     * Gets the value of the orderedBy property.
     * 
     * @return
     *     possible object is
     *     {@link CareProvider }
     *     
     */
    public CareProvider getOrderedBy() {
        return orderedBy;
    }

    /**
     * Sets the value of the orderedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link CareProvider }
     *     
     */
    public void setOrderedBy(CareProvider value) {
        this.orderedBy = value;
    }

    /**
     * Gets the value of the enteringOrganization property.
     * 
     * @return
     *     possible object is
     *     {@link HealthCareFacility }
     *     
     */
    public HealthCareFacility getEnteringOrganization() {
        return enteringOrganization;
    }

    /**
     * Sets the value of the enteringOrganization property.
     * 
     * @param value
     *     allowed object is
     *     {@link HealthCareFacility }
     *     
     */
    public void setEnteringOrganization(HealthCareFacility value) {
        this.enteringOrganization = value;
    }

    /**
     * Gets the value of the verifiedBy property.
     * 
     * @return
     *     possible object is
     *     {@link CareProvider }
     *     
     */
    public CareProvider getVerifiedBy() {
        return verifiedBy;
    }

    /**
     * Sets the value of the verifiedBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link CareProvider }
     *     
     */
    public void setVerifiedBy(CareProvider value) {
        this.verifiedBy = value;
    }

    /**
     * Gets the value of the callbackNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCallbackNumber() {
        return callbackNumber;
    }

    /**
     * Sets the value of the callbackNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCallbackNumber(String value) {
        this.callbackNumber = value;
    }

    /**
     * Gets the value of the specimen property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecimen() {
        return specimen;
    }

    /**
     * Sets the value of the specimen property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecimen(String value) {
        this.specimen = value;
    }

    /**
     * Gets the value of the specimenCollectedTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSpecimenCollectedTime() {
        return specimenCollectedTime;
    }

    /**
     * Sets the value of the specimenCollectedTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSpecimenCollectedTime(XMLGregorianCalendar value) {
        this.specimenCollectedTime = value;
    }

    /**
     * Gets the value of the specimenReceivedTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSpecimenReceivedTime() {
        return specimenReceivedTime;
    }

    /**
     * Sets the value of the specimenReceivedTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSpecimenReceivedTime(XMLGregorianCalendar value) {
        this.specimenReceivedTime = value;
    }

    /**
     * Gets the value of the reassessmentTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getReassessmentTime() {
        return reassessmentTime;
    }

    /**
     * Sets the value of the reassessmentTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setReassessmentTime(XMLGregorianCalendar value) {
        this.reassessmentTime = value;
    }

    /**
     * Gets the value of the frequency property.
     * 
     * @return
     *     possible object is
     *     {@link Frequency }
     *     
     */
    public Frequency getFrequency() {
        return frequency;
    }

    /**
     * Sets the value of the frequency property.
     * 
     * @param value
     *     allowed object is
     *     {@link Frequency }
     *     
     */
    public void setFrequency(Frequency value) {
        this.frequency = value;
    }

    /**
     * Gets the value of the duration property.
     * 
     * @return
     *     possible object is
     *     {@link Duration }
     *     
     */
    public Duration getDuration() {
        return duration;
    }

    /**
     * Sets the value of the duration property.
     * 
     * @param value
     *     allowed object is
     *     {@link Duration }
     *     
     */
    public void setDuration(Duration value) {
        this.duration = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the priority property.
     * 
     * @return
     *     possible object is
     *     {@link Priority }
     *     
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * Sets the value of the priority property.
     * 
     * @param value
     *     allowed object is
     *     {@link Priority }
     *     
     */
    public void setPriority(Priority value) {
        this.priority = value;
    }

    /**
     * Gets the value of the confidentialityCode property.
     * 
     * @return
     *     possible object is
     *     {@link Confidentiality }
     *     
     */
    public Confidentiality getConfidentialityCode() {
        return confidentialityCode;
    }

    /**
     * Sets the value of the confidentialityCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Confidentiality }
     *     
     */
    public void setConfidentialityCode(Confidentiality value) {
        this.confidentialityCode = value;
    }

    /**
     * Gets the value of the condition property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCondition() {
        return condition;
    }

    /**
     * Sets the value of the condition property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCondition(String value) {
        this.condition = value;
    }

    /**
     * Gets the value of the textInstruction property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTextInstruction() {
        return textInstruction;
    }

    /**
     * Sets the value of the textInstruction property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTextInstruction(String value) {
        this.textInstruction = value;
    }

    /**
     * Gets the value of the orderGroup property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderGroup() {
        return orderGroup;
    }

    /**
     * Sets the value of the orderGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderGroup(String value) {
        this.orderGroup = value;
    }

    /**
     * Gets the value of the comments property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComments() {
        return comments;
    }

    /**
     * Sets the value of the comments property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComments(String value) {
        this.comments = value;
    }

    /**
     * Gets the value of the consultationDepartment property.
     * 
     * @return
     *     possible object is
     *     {@link HealthCareFacility }
     *     
     */
    public HealthCareFacility getConsultationDepartment() {
        return consultationDepartment;
    }

    /**
     * Sets the value of the consultationDepartment property.
     * 
     * @param value
     *     allowed object is
     *     {@link HealthCareFacility }
     *     
     */
    public void setConsultationDepartment(HealthCareFacility value) {
        this.consultationDepartment = value;
    }

    /**
     * Gets the value of the authorizationTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getAuthorizationTime() {
        return authorizationTime;
    }

    /**
     * Sets the value of the authorizationTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setAuthorizationTime(XMLGregorianCalendar value) {
        this.authorizationTime = value;
    }

    /**
     * Gets the value of the receivingLocation property.
     * 
     * @return
     *     possible object is
     *     {@link HealthCareFacility }
     *     
     */
    public HealthCareFacility getReceivingLocation() {
        return receivingLocation;
    }

    /**
     * Sets the value of the receivingLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link HealthCareFacility }
     *     
     */
    public void setReceivingLocation(HealthCareFacility value) {
        this.receivingLocation = value;
    }

    /**
     * Gets the value of the verifiedComments property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVerifiedComments() {
        return verifiedComments;
    }

    /**
     * Sets the value of the verifiedComments property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVerifiedComments(String value) {
        this.verifiedComments = value;
    }

    /**
     * Gets the value of the resultCopiesTo property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfCareProviderCareProvider }
     *     
     */
    public ArrayOfCareProviderCareProvider getResultCopiesTo() {
        return resultCopiesTo;
    }

    /**
     * Sets the value of the resultCopiesTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfCareProviderCareProvider }
     *     
     */
    public void setResultCopiesTo(ArrayOfCareProviderCareProvider value) {
        this.resultCopiesTo = value;
    }

    /**
     * Gets the value of the groupId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * Sets the value of the groupId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroupId(String value) {
        this.groupId = value;
    }

    /**
     * Gets the value of the drugProduct property.
     * 
     * @return
     *     possible object is
     *     {@link DrugProduct }
     *     
     */
    public DrugProduct getDrugProduct() {
        return drugProduct;
    }

    /**
     * Sets the value of the drugProduct property.
     * 
     * @param value
     *     allowed object is
     *     {@link DrugProduct }
     *     
     */
    public void setDrugProduct(DrugProduct value) {
        this.drugProduct = value;
    }

    /**
     * Gets the value of the strengthVolume property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getStrengthVolume() {
        return strengthVolume;
    }

    /**
     * Sets the value of the strengthVolume property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setStrengthVolume(BigDecimal value) {
        this.strengthVolume = value;
    }

    /**
     * Gets the value of the strengthVolumeUnits property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrengthVolumeUnits() {
        return strengthVolumeUnits;
    }

    /**
     * Sets the value of the strengthVolumeUnits property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrengthVolumeUnits(String value) {
        this.strengthVolumeUnits = value;
    }

    /**
     * Gets the value of the rateAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRateAmount() {
        return rateAmount;
    }

    /**
     * Sets the value of the rateAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRateAmount(BigDecimal value) {
        this.rateAmount = value;
    }

    /**
     * Gets the value of the rateUnits property.
     * 
     * @return
     *     possible object is
     *     {@link RateUnits }
     *     
     */
    public RateUnits getRateUnits() {
        return rateUnits;
    }

    /**
     * Sets the value of the rateUnits property.
     * 
     * @param value
     *     allowed object is
     *     {@link RateUnits }
     *     
     */
    public void setRateUnits(RateUnits value) {
        this.rateUnits = value;
    }

    /**
     * Gets the value of the rateTimeUnit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRateTimeUnit() {
        return rateTimeUnit;
    }

    /**
     * Sets the value of the rateTimeUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRateTimeUnit(String value) {
        this.rateTimeUnit = value;
    }

    /**
     * Gets the value of the doseQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDoseQuantity() {
        return doseQuantity;
    }

    /**
     * Sets the value of the doseQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDoseQuantity(BigDecimal value) {
        this.doseQuantity = value;
    }

    /**
     * Gets the value of the maxDoseQuantity property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMaxDoseQuantity() {
        return maxDoseQuantity;
    }

    /**
     * Sets the value of the maxDoseQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMaxDoseQuantity(BigDecimal value) {
        this.maxDoseQuantity = value;
    }

    /**
     * Gets the value of the numberOfRefills property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumberOfRefills() {
        return numberOfRefills;
    }

    /**
     * Sets the value of the numberOfRefills property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumberOfRefills(String value) {
        this.numberOfRefills = value;
    }

    /**
     * Gets the value of the doseUoM property.
     * 
     * @return
     *     possible object is
     *     {@link UoM }
     *     
     */
    public UoM getDoseUoM() {
        return doseUoM;
    }

    /**
     * Sets the value of the doseUoM property.
     * 
     * @param value
     *     allowed object is
     *     {@link UoM }
     *     
     */
    public void setDoseUoM(UoM value) {
        this.doseUoM = value;
    }

    /**
     * Gets the value of the dosageForm property.
     * 
     * @return
     *     possible object is
     *     {@link DosageForm }
     *     
     */
    public DosageForm getDosageForm() {
        return dosageForm;
    }

    /**
     * Sets the value of the dosageForm property.
     * 
     * @param value
     *     allowed object is
     *     {@link DosageForm }
     *     
     */
    public void setDosageForm(DosageForm value) {
        this.dosageForm = value;
    }

    /**
     * Gets the value of the route property.
     * 
     * @return
     *     possible object is
     *     {@link Route }
     *     
     */
    public Route getRoute() {
        return route;
    }

    /**
     * Sets the value of the route property.
     * 
     * @param value
     *     allowed object is
     *     {@link Route }
     *     
     */
    public void setRoute(Route value) {
        this.route = value;
    }

    /**
     * Gets the value of the indication property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndication() {
        return indication;
    }

    /**
     * Sets the value of the indication property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndication(String value) {
        this.indication = value;
    }

    /**
     * Gets the value of the pharmacyStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPharmacyStatus() {
        return pharmacyStatus;
    }

    /**
     * Sets the value of the pharmacyStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPharmacyStatus(String value) {
        this.pharmacyStatus = value;
    }

    /**
     * Gets the value of the prescriptionNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrescriptionNumber() {
        return prescriptionNumber;
    }

    /**
     * Sets the value of the prescriptionNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrescriptionNumber(String value) {
        this.prescriptionNumber = value;
    }

    /**
     * Gets the value of the refillNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRefillNumber() {
        return refillNumber;
    }

    /**
     * Sets the value of the refillNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRefillNumber(BigDecimal value) {
        this.refillNumber = value;
    }

    /**
     * Gets the value of the refillDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefillDescription() {
        return refillDescription;
    }

    /**
     * Sets the value of the refillDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRefillDescription(String value) {
        this.refillDescription = value;
    }

    /**
     * Gets the value of the dosageSteps property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDosageStepDosageStep }
     *     
     */
    public ArrayOfDosageStepDosageStep getDosageSteps() {
        return dosageSteps;
    }

    /**
     * Sets the value of the dosageSteps property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDosageStepDosageStep }
     *     
     */
    public void setDosageSteps(ArrayOfDosageStepDosageStep value) {
        this.dosageSteps = value;
    }

    /**
     * Gets the value of the administrations property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfAdministrationAdministration }
     *     
     */
    public ArrayOfAdministrationAdministration getAdministrations() {
        return administrations;
    }

    /**
     * Sets the value of the administrations property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfAdministrationAdministration }
     *     
     */
    public void setAdministrations(ArrayOfAdministrationAdministration value) {
        this.administrations = value;
    }

    /**
     * Gets the value of the componentMeds property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfDrugProductDrugProduct }
     *     
     */
    public ArrayOfDrugProductDrugProduct getComponentMeds() {
        return componentMeds;
    }

    /**
     * Sets the value of the componentMeds property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfDrugProductDrugProduct }
     *     
     */
    public void setComponentMeds(ArrayOfDrugProductDrugProduct value) {
        this.componentMeds = value;
    }

}
