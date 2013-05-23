
package nsi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ArrayOfF014TypeF014Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfF014TypeF014Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="F014Type" type="{urn:nsi}F014Type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfF014TypeF014Type", propOrder = {
    "f014Type"
})
public class ArrayOfF014TypeF014Type {

    @XmlElement(name = "F014Type", nillable = true)
    protected List<F014Type> f014Type;

    /**
     * Gets the value of the f014Type property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the f014Type property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getF014Type().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link F014Type }
     * 
     * 
     */
    public List<F014Type> getF014Type() {
        if (f014Type == null) {
            f014Type = new ArrayList<F014Type>();
        }
        return this.f014Type;
    }

}
