
package nsi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ArrayOfF008TypeF008Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfF008TypeF008Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="F008Type" type="{urn:nsi}F008Type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfF008TypeF008Type", propOrder = {
    "f008Type"
})
public class ArrayOfF008TypeF008Type {

    @XmlElement(name = "F008Type", nillable = true)
    protected List<F008Type> f008Type;

    /**
     * Gets the value of the f008Type property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the f008Type property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getF008Type().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link F008Type }
     * 
     * 
     */
    public List<F008Type> getF008Type() {
        if (f008Type == null) {
            f008Type = new ArrayList<F008Type>();
        }
        return this.f008Type;
    }

}
