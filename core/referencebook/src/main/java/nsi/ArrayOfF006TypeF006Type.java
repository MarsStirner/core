
package nsi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ArrayOfF006TypeF006Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfF006TypeF006Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="F006Type" type="{urn:nsi}F006Type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfF006TypeF006Type", propOrder = {
    "f006Type"
})
public class ArrayOfF006TypeF006Type {

    @XmlElement(name = "F006Type", nillable = true)
    protected List<F006Type> f006Type;

    /**
     * Gets the value of the f006Type property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the f006Type property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getF006Type().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link F006Type }
     * 
     * 
     */
    public List<F006Type> getF006Type() {
        if (f006Type == null) {
            f006Type = new ArrayList<F006Type>();
        }
        return this.f006Type;
    }

}
