
package ru.korus.tmis.pix.sda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LabResultItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LabResultItem">
 *   &lt;complexContent>
 *     &lt;extension base="{}SuperClass">
 *       &lt;sequence>
 *         &lt;element name="TestItemCode" type="{}LabTestItem" minOccurs="0"/>
 *         &lt;element name="ResultValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SubId" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="30"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ResultValueUnits" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="30"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ResultNormalRange" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="220"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ResultInterpretation" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="100"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="TestItemStatus" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="100"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Comments" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ReferenceComment" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="1000"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="PerformedAt" type="{}Organization" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LabResultItem", propOrder = {
    "testItemCode",
    "resultValue",
    "subId",
    "resultValueUnits",
    "resultNormalRange",
    "resultInterpretation",
    "testItemStatus",
    "comments",
    "referenceComment",
    "performedAt"
})
public class LabResultItem
    extends SuperClass
{

    @XmlElement(name = "TestItemCode")
    protected LabTestItem testItemCode;
    @XmlElement(name = "ResultValue")
    protected String resultValue;
    @XmlElement(name = "SubId")
    protected String subId;
    @XmlElement(name = "ResultValueUnits")
    protected String resultValueUnits;
    @XmlElement(name = "ResultNormalRange")
    protected String resultNormalRange;
    @XmlElement(name = "ResultInterpretation")
    protected String resultInterpretation;
    @XmlElement(name = "TestItemStatus")
    protected String testItemStatus;
    @XmlElement(name = "Comments")
    protected String comments;
    @XmlElement(name = "ReferenceComment")
    protected String referenceComment;
    @XmlElement(name = "PerformedAt")
    protected Organization performedAt;

    /**
     * Gets the value of the testItemCode property.
     * 
     * @return
     *     possible object is
     *     {@link LabTestItem }
     *     
     */
    public LabTestItem getTestItemCode() {
        return testItemCode;
    }

    /**
     * Sets the value of the testItemCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link LabTestItem }
     *     
     */
    public void setTestItemCode(LabTestItem value) {
        this.testItemCode = value;
    }

    /**
     * Gets the value of the resultValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultValue() {
        return resultValue;
    }

    /**
     * Sets the value of the resultValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultValue(String value) {
        this.resultValue = value;
    }

    /**
     * Gets the value of the subId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubId() {
        return subId;
    }

    /**
     * Sets the value of the subId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubId(String value) {
        this.subId = value;
    }

    /**
     * Gets the value of the resultValueUnits property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultValueUnits() {
        return resultValueUnits;
    }

    /**
     * Sets the value of the resultValueUnits property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultValueUnits(String value) {
        this.resultValueUnits = value;
    }

    /**
     * Gets the value of the resultNormalRange property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultNormalRange() {
        return resultNormalRange;
    }

    /**
     * Sets the value of the resultNormalRange property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultNormalRange(String value) {
        this.resultNormalRange = value;
    }

    /**
     * Gets the value of the resultInterpretation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultInterpretation() {
        return resultInterpretation;
    }

    /**
     * Sets the value of the resultInterpretation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultInterpretation(String value) {
        this.resultInterpretation = value;
    }

    /**
     * Gets the value of the testItemStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTestItemStatus() {
        return testItemStatus;
    }

    /**
     * Sets the value of the testItemStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTestItemStatus(String value) {
        this.testItemStatus = value;
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
     * Gets the value of the referenceComment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenceComment() {
        return referenceComment;
    }

    /**
     * Sets the value of the referenceComment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenceComment(String value) {
        this.referenceComment = value;
    }

    /**
     * Gets the value of the performedAt property.
     * 
     * @return
     *     possible object is
     *     {@link Organization }
     *     
     */
    public Organization getPerformedAt() {
        return performedAt;
    }

    /**
     * Sets the value of the performedAt property.
     * 
     * @param value
     *     allowed object is
     *     {@link Organization }
     *     
     */
    public void setPerformedAt(Organization value) {
        this.performedAt = value;
    }

}
