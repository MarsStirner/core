
package nsi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ArrayOfO001TypeO001Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfO001TypeO001Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="O001Type" type="{urn:nsi}O001Type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfO001TypeO001Type", propOrder = {
    "o001Type"
})
public class ArrayOfO001TypeO001Type {

    @XmlElement(name = "O001Type", nillable = true)
    protected List<O001Type> o001Type;

    /**
     * Gets the value of the o001Type property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the o001Type property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getO001Type().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link O001Type }
     * 
     * 
     */
    public List<O001Type> getO001Type() {
        if (o001Type == null) {
            o001Type = new ArrayList<O001Type>();
        }
        return this.o001Type;
    }

}
