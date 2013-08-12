
package nsi;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="rec" type="{urn:nsi}V001Type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="fromRow">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}long">
 *             &lt;minInclusive value="1"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="toRow">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}long">
 *             &lt;minInclusive value="1"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "rec"
})
@XmlRootElement(name = "V001")
public class V001 {

    @XmlElement(nillable = true)
    protected List<V001Type> rec;
    @XmlAttribute(name = "fromRow")
    protected Long fromRow;
    @XmlAttribute(name = "toRow")
    protected Long toRow;

    /**
     * Gets the value of the rec property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rec property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRec().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link V001Type }
     * 
     * 
     */
    public List<V001Type> getRec() {
        if (rec == null) {
            rec = new ArrayList<V001Type>();
        }
        return this.rec;
    }

    /**
     * Gets the value of the fromRow property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getFromRow() {
        return fromRow;
    }

    /**
     * Sets the value of the fromRow property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setFromRow(Long value) {
        this.fromRow = value;
    }

    /**
     * Gets the value of the toRow property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getToRow() {
        return toRow;
    }

    /**
     * Sets the value of the toRow property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setToRow(Long value) {
        this.toRow = value;
    }

}
