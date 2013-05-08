
package nsi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ArrayOfF010TypeF010Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfF010TypeF010Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="F010Type" type="{urn:nsi}F010Type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfF010TypeF010Type", propOrder = {
    "f010Type"
})
public class ArrayOfF010TypeF010Type {

    @XmlElement(name = "F010Type", nillable = true)
    protected List<F010Type> f010Type;

    /**
     * Gets the value of the f010Type property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the f010Type property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getF010Type().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link F010Type }
     * 
     * 
     */
    public List<F010Type> getF010Type() {
        if (f010Type == null) {
            f010Type = new ArrayList<F010Type>();
        }
        return this.f010Type;
    }

}
