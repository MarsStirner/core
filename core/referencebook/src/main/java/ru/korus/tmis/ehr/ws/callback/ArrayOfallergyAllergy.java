
package ru.korus.tmis.ehr.ws.callback;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfallergyAllergy complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfallergyAllergy">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="allergy" type="{}Allergy" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfallergyAllergy", propOrder = {
    "allergy"
})
public class ArrayOfallergyAllergy {

    @XmlElement(nillable = true)
    protected List<Allergy> allergy;

    /**
     * Gets the value of the allergy property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the allergy property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAllergy().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Allergy }
     * 
     * 
     */
    public List<Allergy> getAllergy() {
        if (allergy == null) {
            allergy = new ArrayList<Allergy>();
        }
        return this.allergy;
    }

}
