
package org.hl7.v3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IVL_MO complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IVL_MO">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:hl7-org:v3}SXCM_MO">
 *       &lt;choice>
 *         &lt;element name="low" type="{urn:hl7-org:v3}IVXB_MO" minOccurs="0"/>
 *         &lt;element name="width" type="{urn:hl7-org:v3}MO" minOccurs="0"/>
 *         &lt;element name="high" type="{urn:hl7-org:v3}IVXB_MO" minOccurs="0"/>
 *         &lt;element name="center" type="{urn:hl7-org:v3}MO" minOccurs="0"/>
 *       &lt;/choice>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IVL_MO", propOrder = {
    "low",
    "width",
    "high",
    "center"
})
public class IVLMO
    extends SXCMMO
{

    protected IVXBMO low;
    protected MO width;
    protected IVXBMO high;
    protected MO center;

    /**
     * Gets the value of the low property.
     * 
     * @return
     *     possible object is
     *     {@link IVXBMO }
     *     
     */
    public IVXBMO getLow() {
        return low;
    }

    /**
     * Sets the value of the low property.
     * 
     * @param value
     *     allowed object is
     *     {@link IVXBMO }
     *     
     */
    public void setLow(IVXBMO value) {
        this.low = value;
    }

    /**
     * Gets the value of the width property.
     * 
     * @return
     *     possible object is
     *     {@link MO }
     *     
     */
    public MO getWidth() {
        return width;
    }

    /**
     * Sets the value of the width property.
     * 
     * @param value
     *     allowed object is
     *     {@link MO }
     *     
     */
    public void setWidth(MO value) {
        this.width = value;
    }

    /**
     * Gets the value of the high property.
     * 
     * @return
     *     possible object is
     *     {@link IVXBMO }
     *     
     */
    public IVXBMO getHigh() {
        return high;
    }

    /**
     * Sets the value of the high property.
     * 
     * @param value
     *     allowed object is
     *     {@link IVXBMO }
     *     
     */
    public void setHigh(IVXBMO value) {
        this.high = value;
    }

    /**
     * Gets the value of the center property.
     * 
     * @return
     *     possible object is
     *     {@link MO }
     *     
     */
    public MO getCenter() {
        return center;
    }

    /**
     * Sets the value of the center property.
     * 
     * @param value
     *     allowed object is
     *     {@link MO }
     *     
     */
    public void setCenter(MO value) {
        this.center = value;
    }

}
