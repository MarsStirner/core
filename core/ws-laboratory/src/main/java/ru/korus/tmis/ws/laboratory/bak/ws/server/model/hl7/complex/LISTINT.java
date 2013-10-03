
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LIST_INT complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LIST_INT">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:hl7-org:v3}COLL_INT">
 *       &lt;sequence>
 *         &lt;element name="item" type="{urn:hl7-org:v3}INT" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LIST_INT", propOrder = {
    "item"
})
@XmlSeeAlso({
    HISTINT.class
})
public class LISTINT
    extends COLLINT
{

    protected List<INT> item;

    /**
     * Gets the value of the item property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the item property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link INT }
     * 
     * 
     */
    public List<INT> getItem() {
        if (item == null) {
            item = new ArrayList<INT>();
        }
        return this.item;
    }

}
