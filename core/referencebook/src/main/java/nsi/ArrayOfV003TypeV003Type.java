
package nsi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ArrayOfV003TypeV003Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfV003TypeV003Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="V003Type" type="{urn:nsi}V003Type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfV003TypeV003Type", propOrder = {
    "v003Type"
})
public class ArrayOfV003TypeV003Type {

    @XmlElement(name = "V003Type", nillable = true)
    protected List<V003Type> v003Type;

    /**
     * Gets the value of the v003Type property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the v003Type property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getV003Type().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link V003Type }
     * 
     * 
     */
    public List<V003Type> getV003Type() {
        if (v003Type == null) {
            v003Type = new ArrayList<V003Type>();
        }
        return this.v003Type;
    }

}
