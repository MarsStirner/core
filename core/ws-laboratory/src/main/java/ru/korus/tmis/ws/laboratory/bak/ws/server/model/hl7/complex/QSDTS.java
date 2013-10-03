
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for QSD_TS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="QSD_TS">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:hl7-org:v3}QSET_TS">
 *       &lt;sequence>
 *         &lt;element name="minuend" type="{urn:hl7-org:v3}QSET_TS" minOccurs="0"/>
 *         &lt;element name="subtrahend" type="{urn:hl7-org:v3}QSET_TS" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QSD_TS", propOrder = {
    "minuend",
    "subtrahend"
})
public class QSDTS
    extends QSETTS
{

    protected QSETTS minuend;
    protected QSETTS subtrahend;

    /**
     * Gets the value of the minuend property.
     * 
     * @return
     *     possible object is
     *     {@link QSETTS }
     *     
     */
    public QSETTS getMinuend() {
        return minuend;
    }

    /**
     * Sets the value of the minuend property.
     * 
     * @param value
     *     allowed object is
     *     {@link QSETTS }
     *     
     */
    public void setMinuend(QSETTS value) {
        this.minuend = value;
    }

    /**
     * Gets the value of the subtrahend property.
     * 
     * @return
     *     possible object is
     *     {@link QSETTS }
     *     
     */
    public QSETTS getSubtrahend() {
        return subtrahend;
    }

    /**
     * Sets the value of the subtrahend property.
     * 
     * @param value
     *     allowed object is
     *     {@link QSETTS }
     *     
     */
    public void setSubtrahend(QSETTS value) {
        this.subtrahend = value;
    }

}
