
package nsi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ArrayOfR002TypeR002Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfR002TypeR002Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="R002Type" type="{urn:nsi}R002Type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfR002TypeR002Type", propOrder = {
    "r002Type"
})
public class ArrayOfR002TypeR002Type {

    @XmlElement(name = "R002Type", nillable = true)
    protected List<R002Type> r002Type;

    /**
     * Gets the value of the r002Type property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the r002Type property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getR002Type().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link R002Type }
     * 
     * 
     */
    public List<R002Type> getR002Type() {
        if (r002Type == null) {
            r002Type = new ArrayList<R002Type>();
        }
        return this.r002Type;
    }

}
