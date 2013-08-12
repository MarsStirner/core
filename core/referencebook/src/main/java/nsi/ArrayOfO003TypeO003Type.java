
package nsi;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfO003TypeO003Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfO003TypeO003Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="O003Type" type="{urn:nsi}O003Type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfO003TypeO003Type", propOrder = {
    "o003Type"
})
public class ArrayOfO003TypeO003Type {

    @XmlElement(name = "O003Type", nillable = true)
    protected List<O003Type> o003Type;

    /**
     * Gets the value of the o003Type property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the o003Type property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getO003Type().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link O003Type }
     * 
     * 
     */
    public List<O003Type> getO003Type() {
        if (o003Type == null) {
            o003Type = new ArrayList<O003Type>();
        }
        return this.o003Type;
    }

}
