
package ru.korus.tmis.laboratory.across.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ArrayOfTindicator complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfTindicator"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Tindicator" type="{http://schemas.datacontract.org/2004/07/ru.novolabs.MisExchange.ExchangeHelpers.FTMIS}Tindicator" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfTindicator", propOrder = {
    "tindicator"
})
public class ArrayOfTindicator {

    @XmlElement(name = "Tindicator", nillable = true)
    protected List<Tindicator> tindicator;

    /**
     * Gets the value of the tindicator property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tindicator property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTindicator().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Tindicator }
     * 
     * 
     */
    public List<Tindicator> getTindicator() {
        if (tindicator == null) {
            tindicator = new ArrayList<Tindicator>();
        }
        return this.tindicator;
    }

}
