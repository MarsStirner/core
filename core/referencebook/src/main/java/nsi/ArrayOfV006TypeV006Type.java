
package nsi;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfV006TypeV006Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfV006TypeV006Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="V006Type" type="{urn:nsi}V006Type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfV006TypeV006Type", propOrder = {
    "v006Type"
})
public class ArrayOfV006TypeV006Type {

    @XmlElement(name = "V006Type", nillable = true)
    protected List<V006Type> v006Type;

    /**
     * Gets the value of the v006Type property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the v006Type property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getV006Type().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link V006Type }
     * 
     * 
     */
    public List<V006Type> getV006Type() {
        if (v006Type == null) {
            v006Type = new ArrayList<V006Type>();
        }
        return this.v006Type;
    }

}
