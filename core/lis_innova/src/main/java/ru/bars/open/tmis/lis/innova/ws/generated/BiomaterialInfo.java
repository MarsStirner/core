

package ru.bars.open.tmis.lis.innova.ws.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for BiomaterialInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BiomaterialInfo"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="orderBiomaterialCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="orderBiomaterialName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="orderPrefBarCode" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="orderBarCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="orderProbeDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/&gt;
 *         &lt;element name="orderBiomaterialComment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BiomaterialInfo", propOrder = {
    "orderBiomaterialCode",
    "orderBiomaterialName",
    "orderPrefBarCode",
    "orderBarCode",
    "orderProbeDate",
    "orderBiomaterialComment"
})
public class BiomaterialInfo {

    @XmlElementRef(name = "orderBiomaterialCode", namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", type = JAXBElement.class, required = false)
    protected JAXBElement<String> orderBiomaterialCode;
    @XmlElementRef(name = "orderBiomaterialName", namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", type = JAXBElement.class, required = false)
    protected JAXBElement<String> orderBiomaterialName;
    @XmlElement(name = "orderPrefBarCode", namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS")
    protected Integer orderPrefBarCode;
    @XmlElementRef(name = "orderBarCode", namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", type = JAXBElement.class, required = false)
    protected JAXBElement<String> orderBarCode;
    @XmlElement(name = "orderProbeDate", namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar orderProbeDate;
    @XmlElementRef(name = "orderBiomaterialComment", namespace = "http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS", type = JAXBElement.class, required = false)
    protected JAXBElement<String> orderBiomaterialComment;

    /**
     * Gets the value of the orderBiomaterialCode property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOrderBiomaterialCode() {
        return orderBiomaterialCode;
    }

    /**
     * Sets the value of the orderBiomaterialCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOrderBiomaterialCode(JAXBElement<String> value) {
        this.orderBiomaterialCode = value;
    }

    /**
     * Gets the value of the orderBiomaterialName property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOrderBiomaterialName() {
        return orderBiomaterialName;
    }

    /**
     * Sets the value of the orderBiomaterialName property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOrderBiomaterialName(JAXBElement<String> value) {
        this.orderBiomaterialName = value;
    }

    /**
     * Gets the value of the orderPrefBarCode property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOrderPrefBarCode() {
        return orderPrefBarCode;
    }

    /**
     * Sets the value of the orderPrefBarCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOrderPrefBarCode(Integer value) {
        this.orderPrefBarCode = value;
    }

    /**
     * Gets the value of the orderBarCode property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOrderBarCode() {
        return orderBarCode;
    }

    /**
     * Sets the value of the orderBarCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOrderBarCode(JAXBElement<String> value) {
        this.orderBarCode = value;
    }

    /**
     * Gets the value of the orderProbeDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOrderProbeDate() {
        return orderProbeDate;
    }

    /**
     * Sets the value of the orderProbeDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOrderProbeDate(XMLGregorianCalendar value) {
        this.orderProbeDate = value;
    }

    /**
     * Gets the value of the orderBiomaterialComment property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOrderBiomaterialComment() {
        return orderBiomaterialComment;
    }

    /**
     * Sets the value of the orderBiomaterialComment property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOrderBiomaterialComment(JAXBElement<String> value) {
        this.orderBiomaterialComment = value;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BiomaterialInfo{");
        sb.append("orderBiomaterialCode=").append(orderBiomaterialCode);
        sb.append(", orderBiomaterialName=").append(orderBiomaterialName);
        sb.append(", orderPrefBarCode=").append(orderPrefBarCode);
        sb.append(", orderBarCode=").append(orderBarCode);
        sb.append(", orderProbeDate=").append(orderProbeDate);
        sb.append(", orderBiomaterialComment=").append(orderBiomaterialComment);
        sb.append('}');
        return sb.toString();
    }
}
