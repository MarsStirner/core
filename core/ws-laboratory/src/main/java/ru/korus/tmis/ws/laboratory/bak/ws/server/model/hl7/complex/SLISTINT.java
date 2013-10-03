
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SLIST_INT complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SLIST_INT">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:hl7-org:v3}ANY">
 *       &lt;sequence>
 *         &lt;element name="origin" type="{urn:hl7-org:v3}INT" minOccurs="0"/>
 *         &lt;element name="scale" type="{urn:hl7-org:v3}QTY" minOccurs="0"/>
 *         &lt;element name="digit" type="{urn:hl7-org:v3}INT" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SLIST_INT", propOrder = {
    "origin",
    "scale",
    "digit"
})
public class SLISTINT
    extends ANY
{

    protected INT origin;
    protected QTY scale;
    protected List<INT> digit;

    /**
     * Gets the value of the origin property.
     * 
     * @return
     *     possible object is
     *     {@link INT }
     *     
     */
    public INT getOrigin() {
        return origin;
    }

    /**
     * Sets the value of the origin property.
     * 
     * @param value
     *     allowed object is
     *     {@link INT }
     *     
     */
    public void setOrigin(INT value) {
        this.origin = value;
    }

    /**
     * Gets the value of the scale property.
     * 
     * @return
     *     possible object is
     *     {@link QTY }
     *     
     */
    public QTY getScale() {
        return scale;
    }

    /**
     * Sets the value of the scale property.
     * 
     * @param value
     *     allowed object is
     *     {@link QTY }
     *     
     */
    public void setScale(QTY value) {
        this.scale = value;
    }

    /**
     * Gets the value of the digit property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the digit property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDigit().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link INT }
     * 
     * 
     */
    public List<INT> getDigit() {
        if (digit == null) {
            digit = new ArrayList<INT>();
        }
        return this.digit;
    }

}
