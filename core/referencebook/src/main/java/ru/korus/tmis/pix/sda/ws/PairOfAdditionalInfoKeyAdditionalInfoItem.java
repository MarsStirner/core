
package ru.korus.tmis.pix.sda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for PairOfAdditionalInfoKeyAdditionalInfoItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PairOfAdditionalInfoKeyAdditionalInfoItem">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;>AdditionalInfoItem">
 *       &lt;attribute name="AdditionalInfoKey" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PairOfAdditionalInfoKeyAdditionalInfoItem", propOrder = {
    "value"
})
public class PairOfAdditionalInfoKeyAdditionalInfoItem {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "AdditionalInfoKey", required = true)
    protected String additionalInfoKey;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the additionalInfoKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdditionalInfoKey() {
        return additionalInfoKey;
    }

    /**
     * Sets the value of the additionalInfoKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdditionalInfoKey(String value) {
        this.additionalInfoKey = value;
    }

}
