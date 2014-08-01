
package ru.korus.tmis.laboratory.altey.ws;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for DiagnosticRequestInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DiagnosticRequestInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="orderMisId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="orderMisDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="orderPregnatMin" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="orderPregnatMax" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="orderDiagCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="orderDiagText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orderComment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orderDepartmentName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orderDepartmentMisId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="orderDoctorFamily" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orderDoctorName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orderDoctorPatronum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orderDoctorMisId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DiagnosticRequestInfo", propOrder = {
    "orderMisId",
    "orderMisDate",
    "orderPregnatMin",
    "orderPregnatMax",
    "orderDiagCode",
    "orderDiagText",
    "orderComment",
    "orderDepartmentName",
    "orderDepartmentMisId",
    "orderDoctorFamily",
    "orderDoctorName",
    "orderDoctorPatronum",
    "orderDoctorMisId"
})
public class DiagnosticRequestInfo {

    protected int orderMisId;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar orderMisDate;
    protected Integer orderPregnatMin;
    protected Integer orderPregnatMax;
    @XmlElement(required = true)
    protected String orderDiagCode;
    protected String orderDiagText;
    protected String orderComment;
    protected String orderDepartmentName;
    @XmlElement(required = true)
    protected String orderDepartmentMisId;
    protected String orderDoctorFamily;
    protected String orderDoctorName;
    protected String orderDoctorPatronum;
    @XmlElement(required = true)
    protected String orderDoctorMisId;

    /**
     * Gets the value of the orderMisId property.
     * 
     */
    public int getOrderMisId() {
        return orderMisId;
    }

    /**
     * Sets the value of the orderMisId property.
     * 
     */
    public void setOrderMisId(int value) {
        this.orderMisId = value;
    }

    /**
     * Gets the value of the orderMisDate property.
     * 
     * @return
     *     possible object is
     *     {@link javax.xml.datatype.XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getOrderMisDate() {
        return orderMisDate;
    }

    /**
     * Sets the value of the orderMisDate property.
     *
     * @param value
     *     allowed object is
     *     {@link javax.xml.datatype.XMLGregorianCalendar }
     *     
     */
    public void setOrderMisDate(XMLGregorianCalendar value) {
        this.orderMisDate = value;
    }

    /**
     * Gets the value of the orderPregnatMin property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOrderPregnatMin() {
        return orderPregnatMin;
    }

    /**
     * Sets the value of the orderPregnatMin property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOrderPregnatMin(Integer value) {
        this.orderPregnatMin = value;
    }

    /**
     * Gets the value of the orderPregnatMax property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOrderPregnatMax() {
        return orderPregnatMax;
    }

    /**
     * Sets the value of the orderPregnatMax property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOrderPregnatMax(Integer value) {
        this.orderPregnatMax = value;
    }

    /**
     * Gets the value of the orderDiagCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderDiagCode() {
        return orderDiagCode;
    }

    /**
     * Sets the value of the orderDiagCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderDiagCode(String value) {
        this.orderDiagCode = value;
    }

    /**
     * Gets the value of the orderDiagText property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderDiagText() {
        return orderDiagText;
    }

    /**
     * Sets the value of the orderDiagText property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderDiagText(String value) {
        this.orderDiagText = value;
    }

    /**
     * Gets the value of the orderComment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderComment() {
        return orderComment;
    }

    /**
     * Sets the value of the orderComment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderComment(String value) {
        this.orderComment = value;
    }

    /**
     * Gets the value of the orderDepartmentName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderDepartmentName() {
        return orderDepartmentName;
    }

    /**
     * Sets the value of the orderDepartmentName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderDepartmentName(String value) {
        this.orderDepartmentName = value;
    }

    /**
     * Gets the value of the orderDepartmentMisId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderDepartmentMisId() {
        return orderDepartmentMisId;
    }

    /**
     * Sets the value of the orderDepartmentMisId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderDepartmentMisId(String value) {
        this.orderDepartmentMisId = value;
    }

    /**
     * Gets the value of the orderDoctorFamily property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderDoctorFamily() {
        return orderDoctorFamily;
    }

    /**
     * Sets the value of the orderDoctorFamily property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderDoctorFamily(String value) {
        this.orderDoctorFamily = value;
    }

    /**
     * Gets the value of the orderDoctorName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderDoctorName() {
        return orderDoctorName;
    }

    /**
     * Sets the value of the orderDoctorName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderDoctorName(String value) {
        this.orderDoctorName = value;
    }

    /**
     * Gets the value of the orderDoctorPatronum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderDoctorPatronum() {
        return orderDoctorPatronum;
    }

    /**
     * Sets the value of the orderDoctorPatronum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderDoctorPatronum(String value) {
        this.orderDoctorPatronum = value;
    }

    /**
     * Gets the value of the orderDoctorMisId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderDoctorMisId() {
        return orderDoctorMisId;
    }

    /**
     * Sets the value of the orderDoctorMisId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderDoctorMisId(String value) {
        this.orderDoctorMisId = value;
    }

}
