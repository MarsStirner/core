
package ru.korus.tmis.pix.sda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LabTestItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LabTestItem">
 *   &lt;complexContent>
 *     &lt;extension base="{}CodeTableTranslated">
 *       &lt;sequence>
 *         &lt;element name="IsNumeric" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LabTestItem", propOrder = {
    "isNumeric"
})
public class LabTestItem
    extends CodeTableTranslated
{

    @XmlElement(name = "IsNumeric")
    protected Boolean isNumeric;

    /**
     * Gets the value of the isNumeric property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsNumeric() {
        return isNumeric;
    }

    /**
     * Sets the value of the isNumeric property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsNumeric(Boolean value) {
        this.isNumeric = value;
    }

}
