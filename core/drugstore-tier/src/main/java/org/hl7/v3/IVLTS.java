
package org.hl7.v3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IVL_TS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IVL_TS">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:hl7-org:v3}SXCM_TS">
 *       &lt;choice>
 *         &lt;element name="low" type="{urn:hl7-org:v3}IVXB_TS" minOccurs="0"/>
 *         &lt;element name="width" type="{urn:hl7-org:v3}PQ" minOccurs="0"/>
 *         &lt;element name="high" type="{urn:hl7-org:v3}IVXB_TS" minOccurs="0"/>
 *         &lt;element name="center" type="{urn:hl7-org:v3}TS" minOccurs="0"/>
 *       &lt;/choice>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IVL_TS", propOrder = {
    "low",
    "width",
    "high",
    "center"
})
public class IVLTS
    extends SXCMTS
{

    protected IVXBTS low;
    protected PQ width;
    protected IVXBTS high;
    protected TS center;

    /**
     * Gets the value of the low property.
     * 
     * @return
     *     possible object is
     *     {@link IVXBTS }
     *     
     */
    public IVXBTS getLow() {
        return low;
    }

    /**
     * Sets the value of the low property.
     * 
     * @param value
     *     allowed object is
     *     {@link IVXBTS }
     *     
     */
    public void setLow(IVXBTS value) {
        this.low = value;
    }

    /**
     * Gets the value of the width property.
     * 
     * @return
     *     possible object is
     *     {@link PQ }
     *     
     */
    public PQ getWidth() {
        return width;
    }

    /**
     * Sets the value of the width property.
     * 
     * @param value
     *     allowed object is
     *     {@link PQ }
     *     
     */
    public void setWidth(PQ value) {
        this.width = value;
    }

    /**
     * Gets the value of the high property.
     * 
     * @return
     *     possible object is
     *     {@link IVXBTS }
     *     
     */
    public IVXBTS getHigh() {
        return high;
    }

    /**
     * Sets the value of the high property.
     * 
     * @param value
     *     allowed object is
     *     {@link IVXBTS }
     *     
     */
    public void setHigh(IVXBTS value) {
        this.high = value;
    }

    /**
     * Gets the value of the center property.
     * 
     * @return
     *     possible object is
     *     {@link TS }
     *     
     */
    public TS getCenter() {
        return center;
    }

    /**
     * Sets the value of the center property.
     * 
     * @param value
     *     allowed object is
     *     {@link TS }
     *     
     */
    public void setCenter(TS value) {
        this.center = value;
    }

}
