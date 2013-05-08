
package nsi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ArrayOfV001TypeV001Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfV001TypeV001Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="V001Type" type="{urn:nsi}V001Type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfV001TypeV001Type", propOrder = {
    "v001Type"
})
public class ArrayOfV001TypeV001Type {

    @XmlElement(name = "V001Type", nillable = true)
    protected List<V001Type> v001Type;

    /**
     * Gets the value of the v001Type property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the v001Type property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getV001Type().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link V001Type }
     * 
     * 
     */
    public List<V001Type> getV001Type() {
        if (v001Type == null) {
            v001Type = new ArrayList<V001Type>();
        }
        return this.v001Type;
    }

}
