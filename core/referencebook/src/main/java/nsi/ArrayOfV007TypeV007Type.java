
package nsi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ArrayOfV007TypeV007Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfV007TypeV007Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="V007Type" type="{urn:nsi}V007Type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfV007TypeV007Type", propOrder = {
    "v007Type"
})
public class ArrayOfV007TypeV007Type {

    @XmlElement(name = "V007Type", nillable = true)
    protected List<V007Type> v007Type;

    /**
     * Gets the value of the v007Type property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the v007Type property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getV007Type().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link V007Type }
     * 
     * 
     */
    public List<V007Type> getV007Type() {
        if (v007Type == null) {
            v007Type = new ArrayList<V007Type>();
        }
        return this.v007Type;
    }

}
