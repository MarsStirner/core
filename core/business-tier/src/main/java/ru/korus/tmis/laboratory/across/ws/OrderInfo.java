
package ru.korus.tmis.laboratory.across.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;


/**
 * <p>Java class for OrderInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrderInfo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="diagnosticCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="diagnosticName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="orderPriority" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="indicators" type="{http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS}ArrayOfTindicator" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderInfo", propOrder = {
    "diagnosticCode",
    "diagnosticName",
    "orderPriority",
    "indicators"
})
public class OrderInfo {

    @XmlElementRef(name = "diagnosticCode", namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", type = JAXBElement.class, required = false)
    protected JAXBElement<String> diagnosticCode;
    @XmlElementRef(name = "diagnosticName", namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", type = JAXBElement.class, required = false)
    protected JAXBElement<String> diagnosticName;
    @XmlElement(name = "orderPriority", namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS" )
    protected Integer orderPriority;
    @XmlElementRef(name = "indicators", namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", type = JAXBElement.class, required = false)
    protected JAXBElement<ArrayOfTindicator> indicators;

    /**
     * Gets the value of the diagnosticCode property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDiagnosticCode() {
        return diagnosticCode;
    }

    /**
     * Sets the value of the diagnosticCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDiagnosticCode(JAXBElement<String> value) {
        this.diagnosticCode = value;
    }

    /**
     * Gets the value of the diagnosticName property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getDiagnosticName() {
        return diagnosticName;
    }

    /**
     * Sets the value of the diagnosticName property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setDiagnosticName(JAXBElement<String> value) {
        this.diagnosticName = value;
    }

    /**
     * Gets the value of the orderPriority property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOrderPriority() {
        return orderPriority;
    }

    /**
     * Sets the value of the orderPriority property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOrderPriority(Integer value) {
        this.orderPriority = value;
    }

    /**
     * Gets the value of the indicators property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfTindicator }{@code >}
     *     
     */
    public JAXBElement<ArrayOfTindicator> getIndicators() {
        return indicators;
    }

    /**
     * Sets the value of the indicators property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ArrayOfTindicator }{@code >}
     *     
     */
    public void setIndicators(JAXBElement<ArrayOfTindicator> value) {
        this.indicators = value;
    }

}
