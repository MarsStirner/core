
package nsi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ArrayOfF011TypeF011Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfF011TypeF011Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="F011Type" type="{urn:nsi}F011Type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfF011TypeF011Type", propOrder = {
    "f011Type"
})
public class ArrayOfF011TypeF011Type {

    @XmlElement(name = "F011Type", nillable = true)
    protected List<F011Type> f011Type;

    /**
     * Gets the value of the f011Type property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the f011Type property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getF011Type().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link F011Type }
     * 
     * 
     */
    public List<F011Type> getF011Type() {
        if (f011Type == null) {
            f011Type = new ArrayList<F011Type>();
        }
        return this.f011Type;
    }

}
