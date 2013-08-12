
package ru.korus.tmis.pix.sda.ws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfIllnessHistoryIllnessHistory complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfIllnessHistoryIllnessHistory">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IllnessHistory" type="{}IllnessHistory" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfIllnessHistoryIllnessHistory", propOrder = {
    "illnessHistory"
})
public class ArrayOfIllnessHistoryIllnessHistory {

    @XmlElement(name = "IllnessHistory", nillable = true)
    protected List<IllnessHistory> illnessHistory;

    /**
     * Gets the value of the illnessHistory property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the illnessHistory property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIllnessHistory().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IllnessHistory }
     * 
     * 
     */
    public List<IllnessHistory> getIllnessHistory() {
        if (illnessHistory == null) {
            illnessHistory = new ArrayList<IllnessHistory>();
        }
        return this.illnessHistory;
    }

}
