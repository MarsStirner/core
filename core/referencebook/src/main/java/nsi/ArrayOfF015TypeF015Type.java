
package nsi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ArrayOfF015TypeF015Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfF015TypeF015Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="F015Type" type="{urn:nsi}F015Type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfF015TypeF015Type", propOrder = {
    "f015Type"
})
public class ArrayOfF015TypeF015Type {

    @XmlElement(name = "F015Type", nillable = true)
    protected List<F015Type> f015Type;

    /**
     * Gets the value of the f015Type property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the f015Type property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getF015Type().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link F015Type }
     * 
     * 
     */
    public List<F015Type> getF015Type() {
        if (f015Type == null) {
            f015Type = new ArrayList<F015Type>();
        }
        return this.f015Type;
    }

}
