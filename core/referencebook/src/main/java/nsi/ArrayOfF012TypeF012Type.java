
package nsi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ArrayOfF012TypeF012Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfF012TypeF012Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="F012Type" type="{urn:nsi}F012Type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfF012TypeF012Type", propOrder = {
    "f012Type"
})
public class ArrayOfF012TypeF012Type {

    @XmlElement(name = "F012Type", nillable = true)
    protected List<F012Type> f012Type;

    /**
     * Gets the value of the f012Type property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the f012Type property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getF012Type().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link F012Type }
     * 
     * 
     */
    public List<F012Type> getF012Type() {
        if (f012Type == null) {
            f012Type = new ArrayList<F012Type>();
        }
        return this.f012Type;
    }

}
