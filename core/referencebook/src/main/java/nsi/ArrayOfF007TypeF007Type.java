
package nsi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ArrayOfF007TypeF007Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfF007TypeF007Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="F007Type" type="{urn:nsi}F007Type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfF007TypeF007Type", propOrder = {
    "f007Type"
})
public class ArrayOfF007TypeF007Type {

    @XmlElement(name = "F007Type", nillable = true)
    protected List<F007Type> f007Type;

    /**
     * Gets the value of the f007Type property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the f007Type property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getF007Type().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link F007Type }
     * 
     * 
     */
    public List<F007Type> getF007Type() {
        if (f007Type == null) {
            f007Type = new ArrayList<F007Type>();
        }
        return this.f007Type;
    }

}
