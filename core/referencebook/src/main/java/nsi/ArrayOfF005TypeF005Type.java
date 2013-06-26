
package nsi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ArrayOfF005TypeF005Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfF005TypeF005Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="F005Type" type="{urn:nsi}F005Type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfF005TypeF005Type", propOrder = {
    "f005Type"
})
public class ArrayOfF005TypeF005Type {

    @XmlElement(name = "F005Type", nillable = true)
    protected List<F005Type> f005Type;

    /**
     * Gets the value of the f005Type property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the f005Type property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getF005Type().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link F005Type }
     * 
     * 
     */
    public List<F005Type> getF005Type() {
        if (f005Type == null) {
            f005Type = new ArrayList<F005Type>();
        }
        return this.f005Type;
    }

}
