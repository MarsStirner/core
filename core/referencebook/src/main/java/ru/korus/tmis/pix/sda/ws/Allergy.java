
package ru.korus.tmis.pix.sda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Allergy complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Allergy">
 *   &lt;complexContent>
 *     &lt;extension base="{}SuperClass">
 *       &lt;sequence>
 *         &lt;element name="Allergy" type="{}AllergyCode" minOccurs="0"/>
 *         &lt;element name="AllergyCategory" type="{}AllergyCategory" minOccurs="0"/>
 *         &lt;element name="Clinician" type="{}CareProvider" minOccurs="0"/>
 *         &lt;element name="Reaction" type="{}Reaction" minOccurs="0"/>
 *         &lt;element name="Severity" type="{}Severity" minOccurs="0"/>
 *         &lt;element name="Certainty" type="{}Certainty" minOccurs="0"/>
 *         &lt;element name="DiscoveryTime" type="{}TimeStamp" minOccurs="0"/>
 *         &lt;element name="ConfirmedTime" type="{}TimeStamp" minOccurs="0"/>
 *         &lt;element name="Comments" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InactiveTime" type="{}TimeStamp" minOccurs="0"/>
 *         &lt;element name="InactiveComments" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VerifiedTime" type="{}TimeStamp" minOccurs="0"/>
 *         &lt;element name="ATCCode" type="{}ATCCode" minOccurs="0"/>
 *         &lt;element name="ReasonNotCoded" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FreeTextAllergy" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="5000"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="QualifyingDetails" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="32000"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Allergy", propOrder = {
    "allergy",
    "allergyCategory",
    "clinician",
    "reaction",
    "severity",
    "certainty",
    "discoveryTime",
    "confirmedTime",
    "comments",
    "inactiveTime",
    "inactiveComments",
    "verifiedTime",
    "atcCode",
    "reasonNotCoded",
    "freeTextAllergy",
    "qualifyingDetails",
    "status"
})
public class Allergy
    extends SuperClass
{

    @XmlElement(name = "Allergy")
    protected AllergyCode allergy;
    @XmlElement(name = "AllergyCategory")
    protected AllergyCategory allergyCategory;
    @XmlElement(name = "Clinician")
    protected CareProvider clinician;
    @XmlElement(name = "Reaction")
    protected Reaction reaction;
    @XmlElement(name = "Severity")
    protected Severity severity;
    @XmlElement(name = "Certainty")
    protected Certainty certainty;
    @XmlElement(name = "DiscoveryTime")
    protected XMLGregorianCalendar discoveryTime;
    @XmlElement(name = "ConfirmedTime")
    protected XMLGregorianCalendar confirmedTime;
    @XmlElement(name = "Comments")
    protected String comments;
    @XmlElement(name = "InactiveTime")
    protected XMLGregorianCalendar inactiveTime;
    @XmlElement(name = "InactiveComments")
    protected String inactiveComments;
    @XmlElement(name = "VerifiedTime")
    protected XMLGregorianCalendar verifiedTime;
    @XmlElement(name = "ATCCode")
    protected ATCCode atcCode;
    @XmlElement(name = "ReasonNotCoded")
    protected String reasonNotCoded;
    @XmlElement(name = "FreeTextAllergy")
    protected String freeTextAllergy;
    @XmlElement(name = "QualifyingDetails")
    protected String qualifyingDetails;
    @XmlElement(name = "Status")
    protected String status;

    /**
     * Gets the value of the allergy property.
     * 
     * @return
     *     possible object is
     *     {@link AllergyCode }
     *     
     */
    public AllergyCode getAllergy() {
        return allergy;
    }

    /**
     * Sets the value of the allergy property.
     * 
     * @param value
     *     allowed object is
     *     {@link AllergyCode }
     *     
     */
    public void setAllergy(AllergyCode value) {
        this.allergy = value;
    }

    /**
     * Gets the value of the allergyCategory property.
     * 
     * @return
     *     possible object is
     *     {@link AllergyCategory }
     *     
     */
    public AllergyCategory getAllergyCategory() {
        return allergyCategory;
    }

    /**
     * Sets the value of the allergyCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link AllergyCategory }
     *     
     */
    public void setAllergyCategory(AllergyCategory value) {
        this.allergyCategory = value;
    }

    /**
     * Gets the value of the clinician property.
     * 
     * @return
     *     possible object is
     *     {@link CareProvider }
     *     
     */
    public CareProvider getClinician() {
        return clinician;
    }

    /**
     * Sets the value of the clinician property.
     * 
     * @param value
     *     allowed object is
     *     {@link CareProvider }
     *     
     */
    public void setClinician(CareProvider value) {
        this.clinician = value;
    }

    /**
     * Gets the value of the reaction property.
     * 
     * @return
     *     possible object is
     *     {@link Reaction }
     *     
     */
    public Reaction getReaction() {
        return reaction;
    }

    /**
     * Sets the value of the reaction property.
     * 
     * @param value
     *     allowed object is
     *     {@link Reaction }
     *     
     */
    public void setReaction(Reaction value) {
        this.reaction = value;
    }

    /**
     * Gets the value of the severity property.
     * 
     * @return
     *     possible object is
     *     {@link Severity }
     *     
     */
    public Severity getSeverity() {
        return severity;
    }

    /**
     * Sets the value of the severity property.
     * 
     * @param value
     *     allowed object is
     *     {@link Severity }
     *     
     */
    public void setSeverity(Severity value) {
        this.severity = value;
    }

    /**
     * Gets the value of the certainty property.
     * 
     * @return
     *     possible object is
     *     {@link Certainty }
     *     
     */
    public Certainty getCertainty() {
        return certainty;
    }

    /**
     * Sets the value of the certainty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Certainty }
     *     
     */
    public void setCertainty(Certainty value) {
        this.certainty = value;
    }

    /**
     * Gets the value of the discoveryTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDiscoveryTime() {
        return discoveryTime;
    }

    /**
     * Sets the value of the discoveryTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDiscoveryTime(XMLGregorianCalendar value) {
        this.discoveryTime = value;
    }

    /**
     * Gets the value of the confirmedTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getConfirmedTime() {
        return confirmedTime;
    }

    /**
     * Sets the value of the confirmedTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setConfirmedTime(XMLGregorianCalendar value) {
        this.confirmedTime = value;
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
     * Gets the value of the inactiveTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getInactiveTime() {
        return inactiveTime;
    }

    /**
     * Sets the value of the inactiveTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setInactiveTime(XMLGregorianCalendar value) {
        this.inactiveTime = value;
    }

    /**
     * Gets the value of the inactiveComments property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInactiveComments() {
        return inactiveComments;
    }

    /**
     * Sets the value of the inactiveComments property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInactiveComments(String value) {
        this.inactiveComments = value;
    }

    /**
     * Gets the value of the verifiedTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getVerifiedTime() {
        return verifiedTime;
    }

    /**
     * Sets the value of the verifiedTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setVerifiedTime(XMLGregorianCalendar value) {
        this.verifiedTime = value;
    }

    /**
     * Gets the value of the atcCode property.
     * 
     * @return
     *     possible object is
     *     {@link ATCCode }
     *     
     */
    public ATCCode getATCCode() {
        return atcCode;
    }

    /**
     * Sets the value of the atcCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link ATCCode }
     *     
     */
    public void setATCCode(ATCCode value) {
        this.atcCode = value;
    }

    /**
     * Gets the value of the reasonNotCoded property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReasonNotCoded() {
        return reasonNotCoded;
    }

    /**
     * Sets the value of the reasonNotCoded property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReasonNotCoded(String value) {
        this.reasonNotCoded = value;
    }

    /**
     * Gets the value of the freeTextAllergy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFreeTextAllergy() {
        return freeTextAllergy;
    }

    /**
     * Sets the value of the freeTextAllergy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFreeTextAllergy(String value) {
        this.freeTextAllergy = value;
    }

    /**
     * Gets the value of the qualifyingDetails property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQualifyingDetails() {
        return qualifyingDetails;
    }

    /**
     * Sets the value of the qualifyingDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQualifyingDetails(String value) {
        this.qualifyingDetails = value;
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

}
