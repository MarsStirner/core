
package ru.korus.tmis.pix.sda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for RadOrder complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RadOrder">
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
 *         &lt;element name="Result" type="{}Result" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RadOrder", propOrder = {
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
    "result"
})
public class RadOrder
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
    @XmlElement(name = "Result")
    protected Result result;

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
     * Gets the value of the result property.
     * 
     * @return
     *     possible object is
     *     {@link Result }
     *     
     */
    public Result getResult() {
        return result;
    }

    /**
     * Sets the value of the result property.
     * 
     * @param value
     *     allowed object is
     *     {@link Result }
     *     
     */
    public void setResult(Result value) {
        this.result = value;
    }

}
