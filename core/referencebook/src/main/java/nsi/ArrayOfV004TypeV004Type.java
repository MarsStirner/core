
package nsi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ArrayOfV004TypeV004Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfV004TypeV004Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="V004Type" type="{urn:nsi}V004Type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfV004TypeV004Type", propOrder = {
    "v004Type"
})
public class ArrayOfV004TypeV004Type {

    @XmlElement(name = "V004Type", nillable = true)
    protected List<V004Type> v004Type;

    /**
     * Gets the value of the v004Type property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the v004Type property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getV004Type().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link V004Type }
     * 
     * 
     */
    public List<V004Type> getV004Type() {
        if (v004Type == null) {
            v004Type = new ArrayList<V004Type>();
        }
        return this.v004Type;
    }

}
