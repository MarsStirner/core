
package misexchange;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *         &lt;element name="Storage" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Balance" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Drug" type="{urn:hl7-org:v3}POCD_MT000040.LabeledDrug" minOccurs="0"/>
 *                             &lt;element name="Goods" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="Drug" type="{urn:hl7-org:v3}POCD_MT000040.LabeledDrug"/>
 *                                       &lt;element name="Qty" type="{urn:hl7-org:v3}PQ"/>
 *                                       &lt;element name="BestBefore" type="{urn:hl7-org:v3}ts"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="Ref" use="required" type="{MISExchange}uuid" />
 *                 &lt;attribute name="Description" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
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
    "storage"
})
public class BalanceOfGoods2{

    @XmlElement(name = "Storage")
    protected List<BalanceOfGoods2.Storage> storage;

    /**
     * Gets the value of the storage property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the storage property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStorage().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BalanceOfGoods2.Storage }
     * 
     * 
     */
    public List<BalanceOfGoods2.Storage> getStorage() {
        if (storage == null) {
            storage = new ArrayList<BalanceOfGoods2.Storage>();
        }
        return this.storage;
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
     *         &lt;element name="Balance" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Drug" type="{urn:hl7-org:v3}POCD_MT000040.LabeledDrug" minOccurs="0"/>
     *                   &lt;element name="Goods" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="Drug" type="{urn:hl7-org:v3}POCD_MT000040.LabeledDrug"/>
     *                             &lt;element name="Qty" type="{urn:hl7-org:v3}PQ"/>
     *                             &lt;element name="BestBefore" type="{urn:hl7-org:v3}ts"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="Ref" use="required" type="{MISExchange}uuid" />
     *       &lt;attribute name="Description" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "balance"
    })
    public static class Storage {

        @XmlElement(name = "Balance")
        protected List<BalanceOfGoods2.Storage.Balance> balance;
        @XmlAttribute(name = "Ref", required = true)
        protected String ref;
        @XmlAttribute(name = "Description", required = true)
        protected String description;

        /**
         * Gets the value of the balance property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the balance property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getBalance().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link BalanceOfGoods2.torage.Balance }
         * 
         * 
         */
        public List<BalanceOfGoods2.Storage.Balance> getBalance() {
            if (balance == null) {
                balance = new ArrayList<BalanceOfGoods2.Storage.Balance>();
            }
            return this.balance;
        }

        /**
         * Gets the value of the ref property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRef() {
            return ref;
        }

        /**
         * Sets the value of the ref property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRef(String value) {
            this.ref = value;
        }

        /**
         * Gets the value of the description property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDescription() {
            return description;
        }

        /**
         * Sets the value of the description property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDescription(String value) {
            this.description = value;
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
         *         &lt;element name="Drug" type="{urn:hl7-org:v3}POCD_MT000040.LabeledDrug" minOccurs="0"/>
         *         &lt;element name="Goods" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="Drug" type="{urn:hl7-org:v3}POCD_MT000040.LabeledDrug"/>
         *                   &lt;element name="Qty" type="{urn:hl7-org:v3}PQ"/>
         *                   &lt;element name="BestBefore" type="{urn:hl7-org:v3}ts"/>
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
        @XmlType(name = "", propOrder = {
            "drug",
            "goods"
        })
        public static class Balance {

            @XmlElement(name = "Drug")
            protected POCDMT000040LabeledDrug drug;
            @XmlElement(name = "Goods")
            protected List<BalanceOfGoods2.Storage.Balance.Goods> goods;

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
             * Gets the value of the goods property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the goods property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getGoods().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link BalanceOfGoods2.Storage.Balance.Goods }
             * 
             * 
             */
            public List<BalanceOfGoods2.Storage.Balance.Goods> getGoods() {
                if (goods == null) {
                    goods = new ArrayList<BalanceOfGoods2.Storage.Balance.Goods>();
                }
                return this.goods;
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
             *         &lt;element name="BestBefore" type="{urn:hl7-org:v3}ts"/>
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
                "qty",
                "bestBefore"
            })
            public static class Goods {

                @XmlElement(name = "Drug", required = true)
                protected POCDMT000040LabeledDrug drug;
                @XmlElement(name = "Qty", required = true)
                protected PQ qty;
                @XmlElement(name = "BestBefore", required = true)
                protected String bestBefore;

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

                /**
                 * Gets the value of the bestBefore property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getBestBefore() {
                    return bestBefore;
                }

                /**
                 * Sets the value of the bestBefore property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setBestBefore(String value) {
                    this.bestBefore = value;
                }

            }

        }

    }

}
