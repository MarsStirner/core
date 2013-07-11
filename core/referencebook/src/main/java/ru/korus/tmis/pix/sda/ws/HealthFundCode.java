
package ru.korus.tmis.pix.sda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HealthFundCode complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HealthFundCode">
 *   &lt;complexContent>
 *     &lt;extension base="{}CodeTableDetail">
 *       &lt;sequence>
 *         &lt;element name="Address" type="{}Address" minOccurs="0"/>
 *         &lt;element name="ContactPerson" type="{}Name" minOccurs="0"/>
 *         &lt;element name="ContactInfo" type="{}ContactInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HealthFundCode", propOrder = {
    "address",
    "contactPerson",
    "contactInfo"
})
public class HealthFundCode
    extends CodeTableDetail
{

    @XmlElement(name = "Address")
    protected Address address;
    @XmlElement(name = "ContactPerson")
    protected Name contactPerson;
    @XmlElement(name = "ContactInfo")
    protected ContactInfo contactInfo;

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link Address }
     *     
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link Address }
     *     
     */
    public void setAddress(Address value) {
        this.address = value;
    }

    /**
     * Gets the value of the contactPerson property.
     * 
     * @return
     *     possible object is
     *     {@link Name }
     *     
     */
    public Name getContactPerson() {
        return contactPerson;
    }

    /**
     * Sets the value of the contactPerson property.
     * 
     * @param value
     *     allowed object is
     *     {@link Name }
     *     
     */
    public void setContactPerson(Name value) {
        this.contactPerson = value;
    }

    /**
     * Gets the value of the contactInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ContactInfo }
     *     
     */
    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    /**
     * Sets the value of the contactInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactInfo }
     *     
     */
    public void setContactInfo(ContactInfo value) {
        this.contactInfo = value;
    }

}
