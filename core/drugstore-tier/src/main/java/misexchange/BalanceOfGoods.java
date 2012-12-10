
package misexchange;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.hl7.v3.POCDMT000040LabeledDrug;
import org.hl7.v3.PQ;


/**
 * <p>Java class for BalanceOfGoods complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BalanceOfGoods">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BalanceOfGood" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Drug" type="{urn:hl7-org:v3}POCD_MT000040.LabeledDrug"/>
 *                   &lt;element name="Qty" type="{urn:hl7-org:v3}PQ"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BalanceOfGoods", propOrder = {
    "balanceOfGood"
})
public class BalanceOfGoods {

    @XmlElement(name = "BalanceOfGood")
    protected List<BalanceOfGoods.BalanceOfGood> balanceOfGood;

    /**
     * Gets the value of the balanceOfGood property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the balanceOfGood property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBalanceOfGood().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BalanceOfGoods.BalanceOfGood }
     * 
     * 
     */
    public List<BalanceOfGoods.BalanceOfGood> getBalanceOfGood() {
        if (balanceOfGood == null) {
            balanceOfGood = new ArrayList<BalanceOfGoods.BalanceOfGood>();
        }
        return this.balanceOfGood;
    }


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
     *         &lt;element name="Qty" type="{urn:hl7-org:v3}PQ"/>
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
        "qty"
    })
    public static class BalanceOfGood {

        @XmlElement(name = "Drug", required = true)
        protected POCDMT000040LabeledDrug drug;
        @XmlElement(name = "Qty", required = true)
        protected PQ qty;

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
         * Gets the value of the qty property.
         * 
         * @return
         *     possible object is
         *     {@link PQ }
         *     
         */
        public PQ getQty() {
            return qty;
        }

        /**
         * Sets the value of the qty property.
         * 
         * @param value
         *     allowed object is
         *     {@link PQ }
         *     
         */
        public void setQty(PQ value) {
            this.qty = value;
        }

    }

}
