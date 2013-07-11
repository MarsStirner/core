
package ru.korus.tmis.pix.sda.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfHealthFundHealthFund complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfHealthFundHealthFund">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="HealthFund" type="{}HealthFund" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfHealthFundHealthFund", propOrder = {
    "healthFund"
})
public class ArrayOfHealthFundHealthFund {

    @XmlElement(name = "HealthFund", nillable = true)
    protected List<HealthFund> healthFund;

    /**
     * Gets the value of the healthFund property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the healthFund property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHealthFund().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HealthFund }
     * 
     * 
     */
    public List<HealthFund> getHealthFund() {
        if (healthFund == null) {
            healthFund = new ArrayList<HealthFund>();
        }
        return this.healthFund;
    }

}
