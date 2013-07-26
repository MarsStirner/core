
package ru.cgm.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for componentOfInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="componentOfInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="encompassingEncounter" type="{http://cgm.ru}encompassingEncounterInfo"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "componentOfInfo", propOrder = {
    "encompassingEncounter"
})
public class ComponentOfInfo {

    @XmlElement(required = true)
    protected EncompassingEncounterInfo encompassingEncounter;

    /**
     * Gets the value of the encompassingEncounter property.
     * 
     * @return
     *     possible object is
     *     {@link EncompassingEncounterInfo }
     *     
     */
    public EncompassingEncounterInfo getEncompassingEncounter() {
        return encompassingEncounter;
    }

    /**
     * Sets the value of the encompassingEncounter property.
     * 
     * @param value
     *     allowed object is
     *     {@link EncompassingEncounterInfo }
     *     
     */
    public void setEncompassingEncounter(EncompassingEncounterInfo value) {
        this.encompassingEncounter = value;
    }

}
