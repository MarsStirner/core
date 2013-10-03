
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UVP_II complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UVP_II">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:hl7-org:v3}ANY">
 *       &lt;sequence>
 *         &lt;element name="value" type="{urn:hl7-org:v3}II" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="probability" type="{urn:hl7-org:v3}Decimal" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UVP_II", propOrder = {
    "value"
})
public class UVPII
    extends ANY
{

    protected II value;
    @XmlAttribute(name = "probability")
    protected Double probability;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link II }
     *     
     */
    public II getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link II }
     *     
     */
    public void setValue(II value) {
        this.value = value;
    }

    /**
     * Gets the value of the probability property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getProbability() {
        return probability;
    }

    /**
     * Sets the value of the probability property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setProbability(Double value) {
        this.probability = value;
    }

}
