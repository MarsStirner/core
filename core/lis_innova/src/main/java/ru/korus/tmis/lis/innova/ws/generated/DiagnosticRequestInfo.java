
package ru.korus.tmis.lis.innova.ws.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for DiagnosticRequestInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DiagnosticRequestInfo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="orderMisId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="orderCaseId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="orderFinanceId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="orderMisDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="orderPregnat" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="orderDiagCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="orderDiagText" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="orderComment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="orderDepartmentName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="orderDepartmentMisId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="orderDoctorFamily" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="orderDoctorName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="orderDoctorPatronum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="orderDoctorMisId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DiagnosticRequestInfo", propOrder = {
    "orderMisId",
    "orderCaseId",
    "orderFinanceId",
    "orderMisDate",
    "orderPregnat",
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

    @XmlElement(name = "orderMisId", namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS")
    protected Integer orderMisId;
    @XmlElementRef(name = "orderCaseId", namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", type = JAXBElement.class, required = false)
    protected JAXBElement<String> orderCaseId;
    @XmlElement(name = "orderFinanceId", namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS")
    protected Integer orderFinanceId;
    @XmlElement(name = "orderMisDate", namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar orderMisDate;
    @XmlElement(name = "orderPregnat", namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS")
    protected Integer orderPregnat;
    @XmlElementRef(name = "orderDiagCode", namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", type = JAXBElement.class, required = false)
    protected JAXBElement<String> orderDiagCode;
    @XmlElementRef(name = "orderDiagText", namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", type = JAXBElement.class, required = false)
    protected JAXBElement<String> orderDiagText;
    @XmlElementRef(name = "orderComment", namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", type = JAXBElement.class, required = false)
    protected JAXBElement<String> orderComment;
    @XmlElementRef(name = "orderDepartmentName", namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", type = JAXBElement.class, required = false)
    protected JAXBElement<String> orderDepartmentName;
    @XmlElementRef(name = "orderDepartmentMisId", namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", type = JAXBElement.class, required = false)
    protected JAXBElement<String> orderDepartmentMisId;
    @XmlElementRef(name = "orderDoctorFamily", namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", type = JAXBElement.class, required = false)
    protected JAXBElement<String> orderDoctorFamily;
    @XmlElementRef(name = "orderDoctorName", namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", type = JAXBElement.class, required = false)
    protected JAXBElement<String> orderDoctorName;
    @XmlElementRef(name = "orderDoctorPatronum", namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", type = JAXBElement.class, required = false)
    protected JAXBElement<String> orderDoctorPatronum;
    @XmlElementRef(name = "orderDoctorMisId", namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", type = JAXBElement.class, required = false)
    protected JAXBElement<String> orderDoctorMisId;

    /**
     * Gets the value of the orderMisId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOrderMisId() {
        return orderMisId;
    }

    /**
     * Sets the value of the orderMisId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOrderMisId(Integer value) {
        this.orderMisId = value;
    }

    /**
     * Gets the value of the orderCaseId property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOrderCaseId() {
        return orderCaseId;
    }

    /**
     * Sets the value of the orderCaseId property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOrderCaseId(JAXBElement<String> value) {
        this.orderCaseId = value;
    }

    /**
     * Gets the value of the orderFinanceId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOrderFinanceId() {
        return orderFinanceId;
    }

    /**
     * Sets the value of the orderFinanceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOrderFinanceId(Integer value) {
        this.orderFinanceId = value;
    }

    /**
     * Gets the value of the orderMisDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
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
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOrderMisDate(XMLGregorianCalendar value) {
        this.orderMisDate = value;
    }

    /**
     * Gets the value of the orderPregnat property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOrderPregnat() {
        return orderPregnat;
    }

    /**
     * Sets the value of the orderPregnat property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOrderPregnat(Integer value) {
        this.orderPregnat = value;
    }

    /**
     * Gets the value of the orderDiagCode property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOrderDiagCode() {
        return orderDiagCode;
    }

    /**
     * Sets the value of the orderDiagCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOrderDiagCode(JAXBElement<String> value) {
        this.orderDiagCode = value;
    }

    /**
     * Gets the value of the orderDiagText property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOrderDiagText() {
        return orderDiagText;
    }

    /**
     * Sets the value of the orderDiagText property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOrderDiagText(JAXBElement<String> value) {
        this.orderDiagText = value;
    }

    /**
     * Gets the value of the orderComment property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOrderComment() {
        return orderComment;
    }

    /**
     * Sets the value of the orderComment property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOrderComment(JAXBElement<String> value) {
        this.orderComment = value;
    }

    /**
     * Gets the value of the orderDepartmentName property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOrderDepartmentName() {
        return orderDepartmentName;
    }

    /**
     * Sets the value of the orderDepartmentName property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOrderDepartmentName(JAXBElement<String> value) {
        this.orderDepartmentName = value;
    }

    /**
     * Gets the value of the orderDepartmentMisId property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOrderDepartmentMisId() {
        return orderDepartmentMisId;
    }

    /**
     * Sets the value of the orderDepartmentMisId property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOrderDepartmentMisId(JAXBElement<String> value) {
        this.orderDepartmentMisId = value;
    }

    /**
     * Gets the value of the orderDoctorFamily property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOrderDoctorFamily() {
        return orderDoctorFamily;
    }

    /**
     * Sets the value of the orderDoctorFamily property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOrderDoctorFamily(JAXBElement<String> value) {
        this.orderDoctorFamily = value;
    }

    /**
     * Gets the value of the orderDoctorName property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOrderDoctorName() {
        return orderDoctorName;
    }

    /**
     * Sets the value of the orderDoctorName property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOrderDoctorName(JAXBElement<String> value) {
        this.orderDoctorName = value;
    }

    /**
     * Gets the value of the orderDoctorPatronum property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOrderDoctorPatronum() {
        return orderDoctorPatronum;
    }

    /**
     * Sets the value of the orderDoctorPatronum property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOrderDoctorPatronum(JAXBElement<String> value) {
        this.orderDoctorPatronum = value;
    }

    /**
     * Gets the value of the orderDoctorMisId property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOrderDoctorMisId() {
        return orderDoctorMisId;
    }

    /**
     * Sets the value of the orderDoctorMisId property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOrderDoctorMisId(JAXBElement<String> value) {
        this.orderDoctorMisId = value;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DiagnosticRequestInfo{");
        sb.append("orderMisId=").append(orderMisId);
        sb.append(", orderCaseId=").append(orderCaseId);
        sb.append(", orderFinanceId=").append(orderFinanceId);
        sb.append(", orderMisDate=").append(orderMisDate);
        sb.append(", orderPregnat=").append(orderPregnat);
        sb.append(", orderDiagCode=").append(orderDiagCode);
        sb.append(", orderDiagText=").append(orderDiagText);
        sb.append(", orderComment=").append(orderComment);
        sb.append(", orderDepartmentName=").append(orderDepartmentName);
        sb.append(", orderDepartmentMisId=").append(orderDepartmentMisId);
        sb.append(", orderDoctorFamily=").append(orderDoctorFamily);
        sb.append(", orderDoctorName=").append(orderDoctorName);
        sb.append(", orderDoctorPatronum=").append(orderDoctorPatronum);
        sb.append(", orderDoctorMisId=").append(orderDoctorMisId);
        sb.append('}');
        return sb.toString();
    }
}
