
package ru.korus.tmis.laboratory.bak.service;

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
 *         &lt;element name="specimenRole" type="{http://cgm.ru}specimenRoleInfo"/>
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
    "specimenRole"
})
public class SpecimenInfo {

    @XmlElement(required = true)
    protected SpecimenRoleInfo specimenRole;

    /**
     * Gets the value of the specimenRole property.
     * 
     * @return
     *     possible object is
     *     {@link ru.korus.tmis.laboratory.bak.service.SpecimenRoleInfo }
     *
     */
    public SpecimenRoleInfo getSpecimenRole() {
        return specimenRole;
    }

    /**
     * Sets the value of the specimenRole property.
     *
     * @param value
     *     allowed object is
     *     {@link ru.korus.tmis.laboratory.bak.service.SpecimenRoleInfo }
     *     
     */
    public void setSpecimenRole(SpecimenRoleInfo value) {
        this.specimenRole = value;
    }

}
