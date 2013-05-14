
package nsi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ArrayOfR004TypeR004Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfR004TypeR004Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="R004Type" type="{urn:nsi}R004Type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfR004TypeR004Type", propOrder = {
    "r004Type"
})
public class ArrayOfR004TypeR004Type {

    @XmlElement(name = "R004Type", nillable = true)
    protected List<R004Type> r004Type;

    /**
     * Gets the value of the r004Type property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the r004Type property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getR004Type().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link R004Type }
     * 
     * 
     */
    public List<R004Type> getR004Type() {
        if (r004Type == null) {
            r004Type = new ArrayList<R004Type>();
        }
        return this.r004Type;
    }

}
