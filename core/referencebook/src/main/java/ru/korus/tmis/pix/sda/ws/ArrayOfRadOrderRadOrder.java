
package ru.korus.tmis.pix.sda.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfRadOrderRadOrder complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfRadOrderRadOrder">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RadOrder" type="{}RadOrder" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfRadOrderRadOrder", propOrder = {
    "radOrder"
})
public class ArrayOfRadOrderRadOrder {

    @XmlElement(name = "RadOrder", nillable = true)
    protected List<RadOrder> radOrder;

    /**
     * Gets the value of the radOrder property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the radOrder property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRadOrder().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RadOrder }
     * 
     * 
     */
    public List<RadOrder> getRadOrder() {
        if (radOrder == null) {
            radOrder = new ArrayList<RadOrder>();
        }
        return this.radOrder;
    }

}
