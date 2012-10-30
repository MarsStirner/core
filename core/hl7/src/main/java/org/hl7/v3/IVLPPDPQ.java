
package org.hl7.v3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IVL_PPD_PQ complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IVL_PPD_PQ">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:hl7-org:v3}SXCM_PPD_PQ">
 *       &lt;choice>
 *         &lt;element name="low" type="{urn:hl7-org:v3}IVXB_PPD_PQ" minOccurs="0"/>
 *         &lt;element name="width" type="{urn:hl7-org:v3}PPD_PQ" minOccurs="0"/>
 *         &lt;element name="high" type="{urn:hl7-org:v3}IVXB_PPD_PQ" minOccurs="0"/>
 *         &lt;element name="center" type="{urn:hl7-org:v3}PPD_PQ" minOccurs="0"/>
 *       &lt;/choice>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IVL_PPD_PQ", propOrder = {
    "low",
    "width",
    "high",
    "center"
})
public class IVLPPDPQ
    extends SXCMPPDPQ
{

    protected IVXBPPDPQ low;
    protected PPDPQ width;
    protected IVXBPPDPQ high;
    protected PPDPQ center;

    /**
     * Gets the value of the low property.
     * 
     * @return
     *     possible object is
     *     {@link IVXBPPDPQ }
     *     
     */
    public IVXBPPDPQ getLow() {
        return low;
    }

    /**
     * Sets the value of the low property.
     * 
     * @param value
     *     allowed object is
     *     {@link IVXBPPDPQ }
     *     
     */
    public void setLow(IVXBPPDPQ value) {
        this.low = value;
    }

    /**
     * Gets the value of the width property.
     * 
     * @return
     *     possible object is
     *     {@link PPDPQ }
     *     
     */
    public PPDPQ getWidth() {
        return width;
    }

    /**
     * Sets the value of the width property.
     * 
     * @param value
     *     allowed object is
     *     {@link PPDPQ }
     *     
     */
    public void setWidth(PPDPQ value) {
        this.width = value;
    }

    /**
     * Gets the value of the high property.
     * 
     * @return
     *     possible object is
     *     {@link IVXBPPDPQ }
     *     
     */
    public IVXBPPDPQ getHigh() {
        return high;
    }

    /**
     * Sets the value of the high property.
     * 
     * @param value
     *     allowed object is
     *     {@link IVXBPPDPQ }
     *     
     */
    public void setHigh(IVXBPPDPQ value) {
        this.high = value;
    }

    /**
     * Gets the value of the center property.
     * 
     * @return
     *     possible object is
     *     {@link PPDPQ }
     *     
     */
    public PPDPQ getCenter() {
        return center;
    }

    /**
     * Sets the value of the center property.
     * 
     * @param value
     *     allowed object is
     *     {@link PPDPQ }
     *     
     */
    public void setCenter(PPDPQ value) {
        this.center = value;
    }

}
