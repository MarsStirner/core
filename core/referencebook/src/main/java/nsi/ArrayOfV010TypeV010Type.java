
package nsi;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfV010TypeV010Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfV010TypeV010Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="V010Type" type="{urn:nsi}V010Type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfV010TypeV010Type", propOrder = {
    "v010Type"
})
public class ArrayOfV010TypeV010Type {

    @XmlElement(name = "V010Type", nillable = true)
    protected List<V010Type> v010Type;

    /**
     * Gets the value of the v010Type property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the v010Type property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getV010Type().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link V010Type }
     * 
     * 
     */
    public List<V010Type> getV010Type() {
        if (v010Type == null) {
            v010Type = new ArrayList<V010Type>();
        }
        return this.v010Type;
    }

}
