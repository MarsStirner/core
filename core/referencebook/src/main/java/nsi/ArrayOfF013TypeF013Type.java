
package nsi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ArrayOfF013TypeF013Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfF013TypeF013Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="F013Type" type="{urn:nsi}F013Type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfF013TypeF013Type", propOrder = {
    "f013Type"
})
public class ArrayOfF013TypeF013Type {

    @XmlElement(name = "F013Type", nillable = true)
    protected List<F013Type> f013Type;

    /**
     * Gets the value of the f013Type property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the f013Type property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getF013Type().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link F013Type }
     * 
     * 
     */
    public List<F013Type> getF013Type() {
        if (f013Type == null) {
            f013Type = new ArrayList<F013Type>();
        }
        return this.f013Type;
    }

}
