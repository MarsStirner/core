
package nsi;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfF002TypeF002Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfF002TypeF002Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="F002Type" type="{urn:nsi}F002Type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfF002TypeF002Type", propOrder = {
    "f002Type"
})
public class ArrayOfF002TypeF002Type {

    @XmlElement(name = "F002Type", nillable = true)
    protected List<F002Type> f002Type;

    /**
     * Gets the value of the f002Type property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the f002Type property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getF002Type().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link F002Type }
     * 
     * 
     */
    public List<F002Type> getF002Type() {
        if (f002Type == null) {
            f002Type = new ArrayList<F002Type>();
        }
        return this.f002Type;
    }

}
