
package nsi;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfV012TypeV012Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfV012TypeV012Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="V012Type" type="{urn:nsi}V012Type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfV012TypeV012Type", propOrder = {
    "v012Type"
})
public class ArrayOfV012TypeV012Type {

    @XmlElement(name = "V012Type", nillable = true)
    protected List<V012Type> v012Type;

    /**
     * Gets the value of the v012Type property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the v012Type property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getV012Type().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link V012Type }
     * 
     * 
     */
    public List<V012Type> getV012Type() {
        if (v012Type == null) {
            v012Type = new ArrayList<V012Type>();
        }
        return this.v012Type;
    }

}
