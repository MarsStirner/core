
package nsi;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfO004TypeO004Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfO004TypeO004Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="O004Type" type="{urn:nsi}O004Type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfO004TypeO004Type", propOrder = {
    "o004Type"
})
public class ArrayOfO004TypeO004Type {

    @XmlElement(name = "O004Type", nillable = true)
    protected List<O004Type> o004Type;

    /**
     * Gets the value of the o004Type property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the o004Type property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getO004Type().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link O004Type }
     * 
     * 
     */
    public List<O004Type> getO004Type() {
        if (o004Type == null) {
            o004Type = new ArrayList<O004Type>();
        }
        return this.o004Type;
    }

}
