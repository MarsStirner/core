
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for QSD_PQ complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QSD_PQ">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:hl7-org:v3}QSET_PQ">
 *       &lt;sequence>
 *         &lt;element name="minuend" type="{urn:hl7-org:v3}QSET_PQ" minOccurs="0"/>
 *         &lt;element name="subtrahend" type="{urn:hl7-org:v3}QSET_PQ" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QSD_PQ", propOrder = {
    "minuend",
    "subtrahend"
})
public class QSDPQ
    extends QSETPQ
{

    protected QSETPQ minuend;
    protected QSETPQ subtrahend;

    /**
     * Gets the value of the minuend property.
     * 
     * @return
     *     possible object is
     *     {@link QSETPQ }
     *     
     */
    public QSETPQ getMinuend() {
        return minuend;
    }

    /**
     * Sets the value of the minuend property.
     * 
     * @param value
     *     allowed object is
     *     {@link QSETPQ }
     *     
     */
    public void setMinuend(QSETPQ value) {
        this.minuend = value;
    }

    /**
     * Gets the value of the subtrahend property.
     * 
     * @return
     *     possible object is
     *     {@link QSETPQ }
     *     
     */
    public QSETPQ getSubtrahend() {
        return subtrahend;
    }

    /**
     * Sets the value of the subtrahend property.
     * 
     * @param value
     *     allowed object is
     *     {@link QSETPQ }
     *     
     */
    public void setSubtrahend(QSETPQ value) {
        this.subtrahend = value;
    }

}
