
package nsi;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfV009TypeV009Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfV009TypeV009Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="V009Type" type="{urn:nsi}V009Type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfV009TypeV009Type", propOrder = {
    "v009Type"
})
public class ArrayOfV009TypeV009Type {

    @XmlElement(name = "V009Type", nillable = true)
    protected List<V009Type> v009Type;

    /**
     * Gets the value of the v009Type property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the v009Type property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getV009Type().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link V009Type }
     * 
     * 
     */
    public List<V009Type> getV009Type() {
        if (v009Type == null) {
            v009Type = new ArrayList<V009Type>();
        }
        return this.v009Type;
    }

}
