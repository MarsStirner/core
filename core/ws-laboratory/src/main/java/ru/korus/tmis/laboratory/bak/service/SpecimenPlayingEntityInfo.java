
package ru.korus.tmis.laboratory.bak.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for specimenPlayingEntityInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="specimenPlayingEntityInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="code" type="{http://cgm.ru}spCodeInfo"/>
 *         &lt;element name="quantity" type="{http://cgm.ru}spQuantityInfo"/>
 *         &lt;element name="unit" type="{http://cgm.ru}spUnitInfo"/>
 *         &lt;element name="text" type="{http://cgm.ru}spTextInfo"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "specimenPlayingEntityInfo", propOrder = {
    "code",
    "quantity",
    "unit",
    "text"
})
public class SpecimenPlayingEntityInfo {

    @XmlElement(required = true)
    protected SpCodeInfo code;
    @XmlElement(required = true)
    protected SpQuantityInfo quantity;
    @XmlElement(required = true)
    protected SpUnitInfo unit;
    @XmlElement(required = true)
    protected SpTextInfo text;

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link SpCodeInfo }
     *     
     */
    public SpCodeInfo getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link SpCodeInfo }
     *     
     */
    public void setCode(SpCodeInfo value) {
        this.code = value;
    }

    /**
     * Gets the value of the quantity property.
     * 
     * @return
     *     possible object is
     *     {@link SpQuantityInfo }
     *     
     */
    public SpQuantityInfo getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link SpQuantityInfo }
     *     
     */
    public void setQuantity(SpQuantityInfo value) {
        this.quantity = value;
    }

    /**
     * Gets the value of the unit property.
     * 
     * @return
     *     possible object is
     *     {@link SpUnitInfo }
     *     
     */
    public SpUnitInfo getUnit() {
        return unit;
    }

    /**
     * Sets the value of the unit property.
     * 
     * @param value
     *     allowed object is
     *     {@link SpUnitInfo }
     *     
     */
    public void setUnit(SpUnitInfo value) {
        this.unit = value;
    }

    /**
     * Gets the value of the text property.
     * 
     * @return
     *     possible object is
     *     {@link SpTextInfo }
     *     
     */
    public SpTextInfo getText() {
        return text;
    }

    /**
     * Sets the value of the text property.
     * 
     * @param value
     *     allowed object is
     *     {@link SpTextInfo }
     *     
     */
    public void setText(SpTextInfo value) {
        this.text = value;
    }

}
