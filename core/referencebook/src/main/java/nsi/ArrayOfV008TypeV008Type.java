
package nsi;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfV008TypeV008Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfV008TypeV008Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="V008Type" type="{urn:nsi}V008Type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfV008TypeV008Type", propOrder = {
    "v008Type"
})
public class ArrayOfV008TypeV008Type {

    @XmlElement(name = "V008Type", nillable = true)
    protected List<V008Type> v008Type;

    /**
     * Gets the value of the v008Type property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the v008Type property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getV008Type().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link V008Type }
     * 
     * 
     */
    public List<V008Type> getV008Type() {
        if (v008Type == null) {
            v008Type = new ArrayList<V008Type>();
        }
        return this.v008Type;
    }

}
