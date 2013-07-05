
package nsi;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfKladrStreetTypeKladrStreetType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfKladrStreetTypeKladrStreetType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="KladrStreetType" type="{urn:nsi}KladrStreetType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfKladrStreetTypeKladrStreetType", propOrder = {
    "kladrStreetType"
})
public class ArrayOfKladrStreetTypeKladrStreetType {

    @XmlElement(name = "KladrStreetType", nillable = true)
    protected List<KladrStreetType> kladrStreetType;

    /**
     * Gets the value of the kladrStreetType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the kladrStreetType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKladrStreetType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link KladrStreetType }
     * 
     * 
     */
    public List<KladrStreetType> getKladrStreetType() {
        if (kladrStreetType == null) {
            kladrStreetType = new ArrayList<KladrStreetType>();
        }
        return this.kladrStreetType;
    }

}
