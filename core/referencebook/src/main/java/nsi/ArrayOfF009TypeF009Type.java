
package nsi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ArrayOfF009TypeF009Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfF009TypeF009Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="F009Type" type="{urn:nsi}F009Type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfF009TypeF009Type", propOrder = {
    "f009Type"
})
public class ArrayOfF009TypeF009Type {

    @XmlElement(name = "F009Type", nillable = true)
    protected List<F009Type> f009Type;

    /**
     * Gets the value of the f009Type property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the f009Type property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getF009Type().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link F009Type }
     * 
     * 
     */
    public List<F009Type> getF009Type() {
        if (f009Type == null) {
            f009Type = new ArrayList<F009Type>();
        }
        return this.f009Type;
    }

}
