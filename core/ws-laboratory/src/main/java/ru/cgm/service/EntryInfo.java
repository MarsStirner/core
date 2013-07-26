
package ru.cgm.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for entryInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="entryInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="observation" type="{http://cgm.ru}observationInfo"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "entryInfo", propOrder = {
    "observation"
})
public class EntryInfo {

    @XmlElement(required = true)
    protected ObservationInfo observation;

    /**
     * Gets the value of the observation property.
     * 
     * @return
     *     possible object is
     *     {@link ObservationInfo }
     *     
     */
    public ObservationInfo getObservation() {
        return observation;
    }

    /**
     * Sets the value of the observation property.
     * 
     * @param value
     *     allowed object is
     *     {@link ObservationInfo }
     *     
     */
    public void setObservation(ObservationInfo value) {
        this.observation = value;
    }

}
