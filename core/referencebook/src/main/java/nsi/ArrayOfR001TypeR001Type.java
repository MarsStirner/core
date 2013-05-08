
package nsi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ArrayOfR001TypeR001Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfR001TypeR001Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="R001Type" type="{urn:nsi}R001Type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfR001TypeR001Type", propOrder = {
    "r001Type"
})
public class ArrayOfR001TypeR001Type {

    @XmlElement(name = "R001Type", nillable = true)
    protected List<R001Type> r001Type;

    /**
     * Gets the value of the r001Type property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the r001Type property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getR001Type().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link R001Type }
     * 
     * 
     */
    public List<R001Type> getR001Type() {
        if (r001Type == null) {
            r001Type = new ArrayList<R001Type>();
        }
        return this.r001Type;
    }

}
