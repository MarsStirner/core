
package ru.korus.tmis.pix.sda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ObservationCode complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ObservationCode">
 *   &lt;complexContent>
 *     &lt;extension base="{}CodeTableTranslated">
 *       &lt;sequence>
 *         &lt;element name="ObservationValueUnits" type="{}UoM" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ObservationCode", propOrder = {
    "observationValueUnits"
})
public class ObservationCode
    extends CodeTableTranslated
{

    @XmlElement(name = "ObservationValueUnits")
    protected UoM observationValueUnits;

    /**
     * Gets the value of the observationValueUnits property.
     * 
     * @return
     *     possible object is
     *     {@link UoM }
     *     
     */
    public UoM getObservationValueUnits() {
        return observationValueUnits;
    }

    /**
     * Sets the value of the observationValueUnits property.
     * 
     * @param value
     *     allowed object is
     *     {@link UoM }
     *     
     */
    public void setObservationValueUnits(UoM value) {
        this.observationValueUnits = value;
    }

}
