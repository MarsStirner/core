
package ru.korus.tmis.laboratory.altey.ws;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for BiomaterialInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BiomaterialInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="orderBiomaterialCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="orderBiomaterialname" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="orderBarCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="orderProbeDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="orderBiomaterialComment" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BiomaterialInfo", propOrder = {
    "orderBiomaterialCode",
    "orderBiomaterialname",
    "orderBarCode",
    "orderProbeDate",
    "orderBiomaterialComment"
})
public class BiomaterialInfo {

    @XmlElement(required = true)
    protected String orderBiomaterialCode;
    @XmlElement(required = true)
    protected String orderBiomaterialname;
    @XmlElement(required = true)
    protected String orderBarCode;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar orderProbeDate;
    protected String orderBiomaterialComment;

    /**
     * Gets the value of the orderBiomaterialCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderBiomaterialCode() {
        return orderBiomaterialCode;
    }

    /**
     * Sets the value of the orderBiomaterialCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderBiomaterialCode(String value) {
        this.orderBiomaterialCode = value;
    }

    /**
     * Gets the value of the orderBiomaterialname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderBiomaterialname() {
        return orderBiomaterialname;
    }

    /**
     * Sets the value of the orderBiomaterialname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderBiomaterialname(String value) {
        this.orderBiomaterialname = value;
    }

    /**
     * Gets the value of the orderBarCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderBarCode() {
        return orderBarCode;
    }

    /**
     * Sets the value of the orderBarCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderBarCode(String value) {
        this.orderBarCode = value;
    }

    /**
     * Gets the value of the orderProbeDate property.
     * 
     * @return
     *     possible object is
     *     {@link javax.xml.datatype.XMLGregorianCalendar }
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
     *     {@link javax.xml.datatype.XMLGregorianCalendar }
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
     *     {@link String }
     *     
     */
    public String getOrderBiomaterialComment() {
        return orderBiomaterialComment;
    }

    /**
     * Sets the value of the orderBiomaterialComment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderBiomaterialComment(String value) {
        this.orderBiomaterialComment = value;
    }

}
