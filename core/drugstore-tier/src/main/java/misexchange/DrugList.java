
package misexchange;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.hl7.v3.POCDMT000040LabeledDrug;


/**
 * <p>Java class for DrugList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DrugList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Drug" type="{urn:hl7-org:v3}POCD_MT000040.LabeledDrug" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DrugList", propOrder = {
    "drug"
})
public class DrugList {

    @XmlElement(name = "Drug")
    protected List<POCDMT000040LabeledDrug> drug;

    /**
     * Gets the value of the drug property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the drug property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDrug().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link POCDMT000040LabeledDrug }
     * 
     * 
     */
    public List<POCDMT000040LabeledDrug> getDrug() {
        if (drug == null) {
            drug = new ArrayList<POCDMT000040LabeledDrug>();
        }
        return this.drug;
    }

}
