
package ru.korus.tmis.pix.sda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Guarantor complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Guarantor">
 *   &lt;complexContent>
 *     &lt;extension base="{}SuperClass">
 *       &lt;sequence>
 *         &lt;element name="Name" type="{}Name" minOccurs="0"/>
 *         &lt;element name="Address" type="{}Address" minOccurs="0"/>
 *         &lt;element name="ContactInfo" type="{}ContactInfo" minOccurs="0"/>
 *         &lt;element name="Relationship" type="{}Relationship" minOccurs="0"/>
 *         &lt;element name="GuarantorType" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="32000"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="GuarantorNumber" type="{}PatientNumber" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Guarantor", propOrder = {
    "name",
    "address",
    "contactInfo",
    "relationship",
    "guarantorType",
    "guarantorNumber"
})
public class Guarantor
    extends SuperClass
{

    @XmlElement(name = "Name")
    protected Name name;
    @XmlElement(name = "Address")
    protected Address address;
    @XmlElement(name = "ContactInfo")
    protected ContactInfo contactInfo;
    @XmlElement(name = "Relationship")
    protected Relationship relationship;
    @XmlElement(name = "GuarantorType")
    protected String guarantorType;
    @XmlElement(name = "GuarantorNumber")
    protected PatientNumber guarantorNumber;

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
     * Gets the value of the relationship property.
     * 
     * @return
     *     possible object is
     *     {@link Relationship }
     *     
     */
    public Relationship getRelationship() {
        return relationship;
    }

    /**
     * Sets the value of the relationship property.
     * 
     * @param value
     *     allowed object is
     *     {@link Relationship }
     *     
     */
    public void setRelationship(Relationship value) {
        this.relationship = value;
    }

    /**
     * Gets the value of the guarantorType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGuarantorType() {
        return guarantorType;
    }

    /**
     * Sets the value of the guarantorType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGuarantorType(String value) {
        this.guarantorType = value;
    }

    /**
     * Gets the value of the guarantorNumber property.
     * 
     * @return
     *     possible object is
     *     {@link PatientNumber }
     *     
     */
    public PatientNumber getGuarantorNumber() {
        return guarantorNumber;
    }

    /**
     * Sets the value of the guarantorNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link PatientNumber }
     *     
     */
    public void setGuarantorNumber(PatientNumber value) {
        this.guarantorNumber = value;
    }

}
