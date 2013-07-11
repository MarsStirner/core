
package ru.korus.tmis.pix.sda.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfVaccinationVaccination complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfVaccinationVaccination">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Vaccination" type="{}Vaccination" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfVaccinationVaccination", propOrder = {
    "vaccination"
})
public class ArrayOfVaccinationVaccination {

    @XmlElement(name = "Vaccination", nillable = true)
    protected List<Vaccination> vaccination;

    /**
     * Gets the value of the vaccination property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vaccination property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVaccination().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Vaccination }
     * 
     * 
     */
    public List<Vaccination> getVaccination() {
        if (vaccination == null) {
            vaccination = new ArrayList<Vaccination>();
        }
        return this.vaccination;
    }

}
