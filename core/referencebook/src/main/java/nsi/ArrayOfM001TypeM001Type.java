
package nsi;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfM001TypeM001Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfM001TypeM001Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="M001Type" type="{urn:nsi}M001Type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfM001TypeM001Type", propOrder = {
    "m001Type"
})
public class ArrayOfM001TypeM001Type {

    @XmlElement(name = "M001Type", nillable = true)
    protected List<M001Type> m001Type;

    /**
     * Gets the value of the m001Type property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the m001Type property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getM001Type().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link M001Type }
     * 
     * 
     */
    public List<M001Type> getM001Type() {
        if (m001Type == null) {
            m001Type = new ArrayList<M001Type>();
        }
        return this.m001Type;
    }

}
