
package nsi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ArrayOfF001TypeF001Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfF001TypeF001Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="F001Type" type="{urn:nsi}F001Type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfF001TypeF001Type", propOrder = {
    "f001Type"
})
public class ArrayOfF001TypeF001Type {

    @XmlElement(name = "F001Type", nillable = true)
    protected List<F001Type> f001Type;

    /**
     * Gets the value of the f001Type property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the f001Type property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getF001Type().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link F001Type }
     * 
     * 
     */
    public List<F001Type> getF001Type() {
        if (f001Type == null) {
            f001Type = new ArrayList<F001Type>();
        }
        return this.f001Type;
    }

}
