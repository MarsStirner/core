
package ru.korus.tmis.ehr.ws.callback;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CDAFacility complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CDAFacility">
 *   &lt;complexContent>
 *     &lt;extension base="{}CodeAndName">
 *       &lt;sequence>
 *         &lt;element name="facilityAddress" type="{}Address" minOccurs="0"/>
 *         &lt;element name="contactInfo" type="{}ContactInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CDAFacility", propOrder = {
    "facilityAddress",
    "contactInfo"
})
public class CDAFacility
    extends CodeAndName
{

    protected Address facilityAddress;
    protected ContactInfo contactInfo;

    /**
     * Gets the value of the facilityAddress property.
     * 
     * @return
     *     possible object is
     *     {@link Address }
     *     
     */
    public Address getFacilityAddress() {
        return facilityAddress;
    }

    /**
     * Sets the value of the facilityAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link Address }
     *     
     */
    public void setFacilityAddress(Address value) {
        this.facilityAddress = value;
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
