
package ru.korus.tmis.laboratory.altey.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IndicatorMetodic complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IndicatorMetodic">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="indicatorName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="indicatorCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IndicatorMetodic", propOrder = {
    "indicatorName",
    "indicatorCode"
})
public class IndicatorMetodic {

    @XmlElement(required = true)
    protected String indicatorName;
    protected String indicatorCode;

    /**
     * Gets the value of the indicatorName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndicatorName() {
        return indicatorName;
    }

    /**
     * Sets the value of the indicatorName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndicatorName(String value) {
        this.indicatorName = value;
    }

    /**
     * Gets the value of the indicatorCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndicatorCode() {
        return indicatorCode;
    }

    /**
     * Sets the value of the indicatorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndicatorCode(String value) {
        this.indicatorCode = value;
    }

}
