
package ru.cgm;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for specimenInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="specimenInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="section" type="{http://cgm.ru}specimenRole"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "specimenInfo", propOrder = {
    "section"
})
public class SpecimenInfo {

    @XmlElement(required = true)
    protected SpecimenRole section;

    /**
     * Gets the value of the section property.
     * 
     * @return
     *     possible object is
     *     {@link SpecimenRole }
     *     
     */
    public SpecimenRole getSection() {
        return section;
    }

    /**
     * Sets the value of the section property.
     * 
     * @param value
     *     allowed object is
     *     {@link SpecimenRole }
     *     
     */
    public void setSection(SpecimenRole value) {
        this.section = value;
    }

}
