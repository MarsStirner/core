
package ru.korus.tmis.pix.sda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Problem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Problem">
 *   &lt;complexContent>
 *     &lt;extension base="{}SuperClass">
 *       &lt;sequence>
 *         &lt;element name="ProblemDetails" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Problem" type="{}BodyPart" minOccurs="0"/>
 *         &lt;element name="Clinician" type="{}CareProvider" minOccurs="0"/>
 *         &lt;element name="CauseofDeath" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="220"/>
 *               &lt;enumeration value="Y"/>
 *               &lt;enumeration value="N"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Status" type="{}DiagnosisStatus" minOccurs="0"/>
 *         &lt;element name="Category" type="{}ProblemCategory" minOccurs="0"/>
 *         &lt;element name="Comments" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Problem", propOrder = {
    "problemDetails",
    "problem",
    "clinician",
    "causeofDeath",
    "status",
    "category",
    "comments"
})
public class Problem
    extends SuperClass
{

    @XmlElement(name = "ProblemDetails")
    protected String problemDetails;
    @XmlElement(name = "Problem")
    protected BodyPart problem;
    @XmlElement(name = "Clinician")
    protected CareProvider clinician;
    @XmlElement(name = "CauseofDeath")
    protected String causeofDeath;
    @XmlElement(name = "Status")
    protected DiagnosisStatus status;
    @XmlElement(name = "Category")
    protected ProblemCategory category;
    @XmlElement(name = "Comments")
    protected String comments;

    /**
     * Gets the value of the problemDetails property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProblemDetails() {
        return problemDetails;
    }

    /**
     * Sets the value of the problemDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProblemDetails(String value) {
        this.problemDetails = value;
    }

    /**
     * Gets the value of the problem property.
     * 
     * @return
     *     possible object is
     *     {@link BodyPart }
     *     
     */
    public BodyPart getProblem() {
        return problem;
    }

    /**
     * Sets the value of the problem property.
     * 
     * @param value
     *     allowed object is
     *     {@link BodyPart }
     *     
     */
    public void setProblem(BodyPart value) {
        this.problem = value;
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
     * Gets the value of the causeofDeath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCauseofDeath() {
        return causeofDeath;
    }

    /**
     * Sets the value of the causeofDeath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCauseofDeath(String value) {
        this.causeofDeath = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link DiagnosisStatus }
     *     
     */
    public DiagnosisStatus getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link DiagnosisStatus }
     *     
     */
    public void setStatus(DiagnosisStatus value) {
        this.status = value;
    }

    /**
     * Gets the value of the category property.
     * 
     * @return
     *     possible object is
     *     {@link ProblemCategory }
     *     
     */
    public ProblemCategory getCategory() {
        return category;
    }

    /**
     * Sets the value of the category property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProblemCategory }
     *     
     */
    public void setCategory(ProblemCategory value) {
        this.category = value;
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

}
