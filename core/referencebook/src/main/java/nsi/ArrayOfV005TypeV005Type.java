
package nsi;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfV005TypeV005Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfV005TypeV005Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="V005Type" type="{urn:nsi}V005Type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfV005TypeV005Type", propOrder = {
    "v005Type"
})
public class ArrayOfV005TypeV005Type {

    @XmlElement(name = "V005Type", nillable = true)
    protected List<V005Type> v005Type;

    /**
     * Gets the value of the v005Type property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the v005Type property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getV005Type().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link V005Type }
     * 
     * 
     */
    public List<V005Type> getV005Type() {
        if (v005Type == null) {
            v005Type = new ArrayList<V005Type>();
        }
        return this.v005Type;
    }

}
