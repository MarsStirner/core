
package ru.korus.tmis.pix.sda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CareProvider complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CareProvider">
 *   &lt;complexContent>
 *     &lt;extension base="{}CodeTableDetail">
 *       &lt;sequence>
 *         &lt;element name="Name" type="{}Name" minOccurs="0"/>
 *         &lt;element name="Address" type="{}Address" minOccurs="0"/>
 *         &lt;element name="ContactInfo" type="{}ContactInfo" minOccurs="0"/>
 *         &lt;element name="CareProviderType" type="{}CareProviderType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CareProvider", propOrder = {
    "name",
    "address",
    "contactInfo",
    "careProviderType"
})
public class CareProvider
    extends CodeTableDetail
{

    @XmlElement(name = "Name")
    protected Name name;
    @XmlElement(name = "Address")
    protected Address address;
    @XmlElement(name = "ContactInfo")
    protected ContactInfo contactInfo;
    @XmlElement(name = "CareProviderType")
    protected CareProviderType careProviderType;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link Name }
     *     
     */
    public Name getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link Name }
     *     
     */
    public void setName(Name value) {
        this.name = value;
    }

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

    /**
     * Gets the value of the careProviderType property.
     * 
     * @return
     *     possible object is
     *     {@link CareProviderType }
     *     
     */
    public CareProviderType getCareProviderType() {
        return careProviderType;
    }

    /**
     * Sets the value of the careProviderType property.
     * 
     * @param value
     *     allowed object is
     *     {@link CareProviderType }
     *     
     */
    public void setCareProviderType(CareProviderType value) {
        this.careProviderType = value;
    }

}
