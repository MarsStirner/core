
package nsi;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfO005TypeO005Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfO005TypeO005Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="O005Type" type="{urn:nsi}O005Type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfO005TypeO005Type", propOrder = {
    "o005Type"
})
public class ArrayOfO005TypeO005Type {

    @XmlElement(name = "O005Type", nillable = true)
    protected List<O005Type> o005Type;

    /**
     * Gets the value of the o005Type property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the o005Type property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getO005Type().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link O005Type }
     * 
     * 
     */
    public List<O005Type> getO005Type() {
        if (o005Type == null) {
            o005Type = new ArrayList<O005Type>();
        }
        return this.o005Type;
    }

}
