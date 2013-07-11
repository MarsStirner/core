
package ru.korus.tmis.pix.sda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CustomObject complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustomObject">
 *   &lt;complexContent>
 *     &lt;extension base="{}SuperClass">
 *       &lt;sequence>
 *         &lt;element name="CustomType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CustomMatchKey" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="220"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustomObject", propOrder = {
    "customType",
    "customMatchKey"
})
public class CustomObject
    extends SuperClass
{

    @XmlElement(name = "CustomType")
    protected String customType;
    @XmlElement(name = "CustomMatchKey")
    protected String customMatchKey;

    /**
     * Gets the value of the customType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomType() {
        return customType;
    }

    /**
     * Sets the value of the customType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomType(String value) {
        this.customType = value;
    }

    /**
     * Gets the value of the customMatchKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomMatchKey() {
        return customMatchKey;
    }

    /**
     * Sets the value of the customMatchKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomMatchKey(String value) {
        this.customMatchKey = value;
    }

}
