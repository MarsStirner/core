
package ru.korus.tmis.pix.sda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Trustee complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Trustee">
 *   &lt;complexContent>
 *     &lt;extension base="{}BaseSerial">
 *       &lt;sequence>
 *         &lt;element name="trusteeType" type="{}CodeAndName" minOccurs="0"/>
 *         &lt;element name="familyName" type="{}String" minOccurs="0"/>
 *         &lt;element name="givenName" type="{}String" minOccurs="0"/>
 *         &lt;element name="middleName" type="{}String" minOccurs="0"/>
 *         &lt;element name="kinship" type="{}CodeAndName" minOccurs="0"/>
 *         &lt;element name="trusteeDocType" type="{}CodeAndName" minOccurs="0"/>
 *         &lt;element name="identityDocument" type="{}IdentityDocument" minOccurs="0"/>
 *         &lt;element name="legalAddress" type="{}Address" minOccurs="0"/>
 *         &lt;element name="actualAddress" type="{}Address" minOccurs="0"/>
 *         &lt;element name="contactInfo" type="{}ContactInfo" minOccurs="0"/>
 *         &lt;element name="isGuardian" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Trustee", propOrder = {
    "trusteeType",
    "familyName",
    "givenName",
    "middleName",
    "kinship",
    "trusteeDocType",
    "identityDocument",
    "legalAddress",
    "actualAddress",
    "contactInfo",
    "isGuardian"
})
public class Trustee
    extends BaseSerial
{

    protected CodeAndName trusteeType;
    protected String familyName;
    protected String givenName;
    protected String middleName;
    protected CodeAndName kinship;
    protected CodeAndName trusteeDocType;
    protected IdentityDocument identityDocument;
    protected Address legalAddress;
    protected Address actualAddress;
    protected ContactInfo contactInfo;
    protected Boolean isGuardian;

    /**
     * Gets the value of the trusteeType property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getTrusteeType() {
        return trusteeType;
    }

    /**
     * Sets the value of the trusteeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setTrusteeType(CodeAndName value) {
        this.trusteeType = value;
    }

    /**
     * Gets the value of the familyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFamilyName() {
        return familyName;
    }

    /**
     * Sets the value of the familyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFamilyName(String value) {
        this.familyName = value;
    }

    /**
     * Gets the value of the givenName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGivenName() {
        return givenName;
    }

    /**
     * Sets the value of the givenName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGivenName(String value) {
        this.givenName = value;
    }

    /**
     * Gets the value of the middleName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Sets the value of the middleName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMiddleName(String value) {
        this.middleName = value;
    }

    /**
     * Gets the value of the kinship property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getKinship() {
        return kinship;
    }

    /**
     * Sets the value of the kinship property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setKinship(CodeAndName value) {
        this.kinship = value;
    }

    /**
     * Gets the value of the trusteeDocType property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getTrusteeDocType() {
        return trusteeDocType;
    }

    /**
     * Sets the value of the trusteeDocType property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setTrusteeDocType(CodeAndName value) {
        this.trusteeDocType = value;
    }

    /**
     * Gets the value of the identityDocument property.
     * 
     * @return
     *     possible object is
     *     {@link IdentityDocument }
     *     
     */
    public IdentityDocument getIdentityDocument() {
        return identityDocument;
    }

    /**
     * Sets the value of the identityDocument property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentityDocument }
     *     
     */
    public void setIdentityDocument(IdentityDocument value) {
        this.identityDocument = value;
    }

    /**
     * Gets the value of the legalAddress property.
     * 
     * @return
     *     possible object is
     *     {@link Address }
     *     
     */
    public Address getLegalAddress() {
        return legalAddress;
    }

    /**
     * Sets the value of the legalAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link Address }
     *     
     */
    public void setLegalAddress(Address value) {
        this.legalAddress = value;
    }

    /**
     * Gets the value of the actualAddress property.
     * 
     * @return
     *     possible object is
     *     {@link Address }
     *     
     */
    public Address getActualAddress() {
        return actualAddress;
    }

    /**
     * Sets the value of the actualAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link Address }
     *     
     */
    public void setActualAddress(Address value) {
        this.actualAddress = value;
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
     * Gets the value of the isGuardian property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsGuardian() {
        return isGuardian;
    }

    /**
     * Sets the value of the isGuardian property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsGuardian(Boolean value) {
        this.isGuardian = value;
    }

}
