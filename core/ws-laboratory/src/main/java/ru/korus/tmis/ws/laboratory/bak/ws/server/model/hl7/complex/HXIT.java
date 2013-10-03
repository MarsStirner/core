
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HXIT complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HXIT">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="validTimeLow" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="validTimeHigh" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="controlInformationRoot" type="{urn:hl7-org:v3}Uid" />
 *       &lt;attribute name="controlInformationExtension" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HXIT")
@XmlSeeAlso({
    ANY.class
})
public abstract class HXIT {

    @XmlAttribute(name = "validTimeLow")
    protected String validTimeLow;
    @XmlAttribute(name = "validTimeHigh")
    protected String validTimeHigh;
    @XmlAttribute(name = "controlInformationRoot")
    protected String controlInformationRoot;
    @XmlAttribute(name = "controlInformationExtension")
    protected String controlInformationExtension;

    /**
     * Gets the value of the validTimeLow property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidTimeLow() {
        return validTimeLow;
    }

    /**
     * Sets the value of the validTimeLow property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidTimeLow(String value) {
        this.validTimeLow = value;
    }

    /**
     * Gets the value of the validTimeHigh property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValidTimeHigh() {
        return validTimeHigh;
    }

    /**
     * Sets the value of the validTimeHigh property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValidTimeHigh(String value) {
        this.validTimeHigh = value;
    }

    /**
     * Gets the value of the controlInformationRoot property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getControlInformationRoot() {
        return controlInformationRoot;
    }

    /**
     * Sets the value of the controlInformationRoot property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setControlInformationRoot(String value) {
        this.controlInformationRoot = value;
    }

    /**
     * Gets the value of the controlInformationExtension property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getControlInformationExtension() {
        return controlInformationExtension;
    }

    /**
     * Sets the value of the controlInformationExtension property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setControlInformationExtension(String value) {
        this.controlInformationExtension = value;
    }

}
