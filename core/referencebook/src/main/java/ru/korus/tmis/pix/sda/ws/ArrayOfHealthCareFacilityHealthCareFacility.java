
package ru.korus.tmis.pix.sda.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfHealthCareFacilityHealthCareFacility complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfHealthCareFacilityHealthCareFacility">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="HealthCareFacility" type="{}HealthCareFacility" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfHealthCareFacilityHealthCareFacility", propOrder = {
    "healthCareFacility"
})
public class ArrayOfHealthCareFacilityHealthCareFacility {

    @XmlElement(name = "HealthCareFacility", nillable = true)
    protected List<HealthCareFacility> healthCareFacility;

    /**
     * Gets the value of the healthCareFacility property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the healthCareFacility property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHealthCareFacility().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HealthCareFacility }
     * 
     * 
     */
    public List<HealthCareFacility> getHealthCareFacility() {
        if (healthCareFacility == null) {
            healthCareFacility = new ArrayList<HealthCareFacility>();
        }
        return this.healthCareFacility;
    }

}
