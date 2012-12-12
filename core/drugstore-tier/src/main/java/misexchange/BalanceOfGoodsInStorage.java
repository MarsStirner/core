
package misexchange;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.hl7.v3.POCDMT000040LabeledDrug;


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
 *         &lt;element name="Drug" type="{urn:hl7-org:v3}POCD_MT000040.LabeledDrug"/>
 *         &lt;element name="OrganizationRef" type="{MISExchange}uuid"/>
 *         &lt;element name="StorageRef" type="{MISExchange}uuid"/>
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
    "drug",
    "organizationRef",
    "storageRef"
})
@XmlRootElement(name = "BalanceOfGoodsInStorage")
public class BalanceOfGoodsInStorage {

    @XmlElement(name = "Drug", required = true)
    protected POCDMT000040LabeledDrug drug;
    @XmlElement(name = "OrganizationRef", required = true, nillable = true)
    protected String organizationRef;
    @XmlElement(name = "StorageRef", required = true, nillable = true)
    protected String storageRef;

    /**
     * Gets the value of the drug property.
     * 
     * @return
     *     possible object is
     *     {@link POCDMT000040LabeledDrug }
     *     
     */
    public POCDMT000040LabeledDrug getDrug() {
        return drug;
    }

    /**
     * Sets the value of the drug property.
     * 
     * @param value
     *     allowed object is
     *     {@link POCDMT000040LabeledDrug }
     *     
     */
    public void setDrug(POCDMT000040LabeledDrug value) {
        this.drug = value;
    }

    /**
     * Gets the value of the organizationRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrganizationRef() {
        return organizationRef;
    }

    /**
     * Sets the value of the organizationRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrganizationRef(String value) {
        this.organizationRef = value;
    }

    /**
     * Gets the value of the storageRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStorageRef() {
        return storageRef;
    }

    /**
     * Sets the value of the storageRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStorageRef(String value) {
        this.storageRef = value;
    }

}
