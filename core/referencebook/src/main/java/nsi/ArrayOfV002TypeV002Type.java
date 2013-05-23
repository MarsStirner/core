
package nsi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ArrayOfV002TypeV002Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfV002TypeV002Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="V002Type" type="{urn:nsi}V002Type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfV002TypeV002Type", propOrder = {
    "v002Type"
})
public class ArrayOfV002TypeV002Type {

    @XmlElement(name = "V002Type", nillable = true)
    protected List<V002Type> v002Type;

    /**
     * Gets the value of the v002Type property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the v002Type property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getV002Type().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link V002Type }
     * 
     * 
     */
    public List<V002Type> getV002Type() {
        if (v002Type == null) {
            v002Type = new ArrayList<V002Type>();
        }
        return this.v002Type;
    }

}
