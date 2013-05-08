
package nsi;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="rec" type="{urn:nsi}F013Type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "rec"
})
@XmlRootElement(name = "F013")
public class F013 {

    @XmlElement(nillable = true)
    protected List<F013Type> rec;

    /**
     * Gets the value of the rec property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rec property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRec().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link F013Type }
     * 
     * 
     */
    public List<F013Type> getRec() {
        if (rec == null) {
            rec = new ArrayList<F013Type>();
        }
        return this.rec;
    }

}
