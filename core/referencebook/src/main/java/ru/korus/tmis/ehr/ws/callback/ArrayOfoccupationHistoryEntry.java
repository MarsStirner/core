
package ru.korus.tmis.ehr.ws.callback;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfoccupationHistoryEntry complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfoccupationHistoryEntry">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="occupation" type="{}HistoryEntry" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfoccupationHistoryEntry", propOrder = {
    "occupation"
})
public class ArrayOfoccupationHistoryEntry {

    @XmlElement(nillable = true)
    protected List<HistoryEntry> occupation;

    /**
     * Gets the value of the occupation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the occupation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOccupation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link HistoryEntry }
     * 
     * 
     */
    public List<HistoryEntry> getOccupation() {
        if (occupation == null) {
            occupation = new ArrayList<HistoryEntry>();
        }
        return this.occupation;
    }

}
