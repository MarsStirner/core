
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IVL_QTY complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IVL_QTY">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:hl7-org:v3}QSET_QTY">
 *       &lt;sequence>
 *         &lt;element name="low" type="{urn:hl7-org:v3}QTY" minOccurs="0"/>
 *         &lt;element name="high" type="{urn:hl7-org:v3}QTY" minOccurs="0"/>
 *         &lt;element name="width" type="{urn:hl7-org:v3}QTY" minOccurs="0"/>
 *         &lt;element name="any" type="{urn:hl7-org:v3}QTY" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="lowClosed" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="highClosed" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IVL_QTY", propOrder = {
    "low",
    "high",
    "width",
    "any"
})
public class IVLQTY
    extends QSETQTY
{

    protected QTY low;
    protected QTY high;
    protected QTY width;
    protected QTY any;
    @XmlAttribute(name = "lowClosed")
    protected Boolean lowClosed;
    @XmlAttribute(name = "highClosed")
    protected Boolean highClosed;

    /**
     * Gets the value of the low property.
     * 
     * @return
     *     possible object is
     *     {@link QTY }
     *     
     */
    public QTY getLow() {
        return low;
    }

    /**
     * Sets the value of the low property.
     * 
     * @param value
     *     allowed object is
     *     {@link QTY }
     *     
     */
    public void setLow(QTY value) {
        this.low = value;
    }

    /**
     * Gets the value of the high property.
     * 
     * @return
     *     possible object is
     *     {@link QTY }
     *     
     */
    public QTY getHigh() {
        return high;
    }

    /**
     * Sets the value of the high property.
     * 
     * @param value
     *     allowed object is
     *     {@link QTY }
     *     
     */
    public void setHigh(QTY value) {
        this.high = value;
    }

    /**
     * Gets the value of the width property.
     * 
     * @return
     *     possible object is
     *     {@link QTY }
     *     
     */
    public QTY getWidth() {
        return width;
    }

    /**
     * Sets the value of the width property.
     * 
     * @param value
     *     allowed object is
     *     {@link QTY }
     *     
     */
    public void setWidth(QTY value) {
        this.width = value;
    }

    /**
     * Gets the value of the any property.
     * 
     * @return
     *     possible object is
     *     {@link QTY }
     *     
     */
    public QTY getAny() {
        return any;
    }

    /**
     * Sets the value of the any property.
     * 
     * @param value
     *     allowed object is
     *     {@link QTY }
     *     
     */
    public void setAny(QTY value) {
        this.any = value;
    }

    /**
     * Gets the value of the lowClosed property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isLowClosed() {
        return lowClosed;
    }

    /**
     * Sets the value of the lowClosed property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setLowClosed(Boolean value) {
        this.lowClosed = value;
    }

    /**
     * Gets the value of the highClosed property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHighClosed() {
        return highClosed;
    }

    /**
     * Sets the value of the highClosed property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHighClosed(Boolean value) {
        this.highClosed = value;
    }

}
