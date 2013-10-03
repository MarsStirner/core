
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for QSP_PQ complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QSP_PQ">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:hl7-org:v3}QSET_PQ">
 *       &lt;sequence>
 *         &lt;element name="low" type="{urn:hl7-org:v3}QSET_PQ" minOccurs="0"/>
 *         &lt;element name="high" type="{urn:hl7-org:v3}QSET_PQ" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QSP_PQ", propOrder = {
    "low",
    "high"
})
public class QSPPQ
    extends QSETPQ
{

    protected QSETPQ low;
    protected QSETPQ high;

    /**
     * Gets the value of the low property.
     * 
     * @return
     *     possible object is
     *     {@link QSETPQ }
     *     
     */
    public QSETPQ getLow() {
        return low;
    }

    /**
     * Sets the value of the low property.
     * 
     * @param value
     *     allowed object is
     *     {@link QSETPQ }
     *     
     */
    public void setLow(QSETPQ value) {
        this.low = value;
    }

    /**
     * Gets the value of the high property.
     * 
     * @return
     *     possible object is
     *     {@link QSETPQ }
     *     
     */
    public QSETPQ getHigh() {
        return high;
    }

    /**
     * Sets the value of the high property.
     * 
     * @param value
     *     allowed object is
     *     {@link QSETPQ }
     *     
     */
    public void setHigh(QSETPQ value) {
        this.high = value;
    }

}
