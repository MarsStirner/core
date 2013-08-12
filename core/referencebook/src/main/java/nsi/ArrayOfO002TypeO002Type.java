
package nsi;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfO002TypeO002Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfO002TypeO002Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="O002Type" type="{urn:nsi}O002Type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfO002TypeO002Type", propOrder = {
    "o002Type"
})
public class ArrayOfO002TypeO002Type {

    @XmlElement(name = "O002Type", nillable = true)
    protected List<O002Type> o002Type;

    /**
     * Gets the value of the o002Type property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the o002Type property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getO002Type().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link O002Type }
     * 
     * 
     */
    public List<O002Type> getO002Type() {
        if (o002Type == null) {
            o002Type = new ArrayList<O002Type>();
        }
        return this.o002Type;
    }

}
