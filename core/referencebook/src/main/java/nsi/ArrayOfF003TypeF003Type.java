
package nsi;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfF003TypeF003Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfF003TypeF003Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="F003Type" type="{urn:nsi}F003Type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfF003TypeF003Type", propOrder = {
    "f003Type"
})
public class ArrayOfF003TypeF003Type {

    @XmlElement(name = "F003Type", nillable = true)
    protected List<F003Type> f003Type;

    /**
     * Gets the value of the f003Type property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the f003Type property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getF003Type().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link F003Type }
     * 
     * 
     */
    public List<F003Type> getF003Type() {
        if (f003Type == null) {
            f003Type = new ArrayList<F003Type>();
        }
        return this.f003Type;
    }

}
