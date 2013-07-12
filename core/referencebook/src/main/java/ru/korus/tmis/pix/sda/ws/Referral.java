
package ru.korus.tmis.pix.sda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Referral complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Referral">
 *   &lt;complexContent>
 *     &lt;extension base="{}SuperClass">
 *       &lt;sequence>
 *         &lt;element name="PlacerId" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="220"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="FillerId" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="220"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ReferralReason" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="32767"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ReferringProvider" type="{}CareProvider" minOccurs="0"/>
 *         &lt;element name="ReferringOrganization" type="{}HealthCareFacility" minOccurs="0"/>
 *         &lt;element name="ReferredToProvider" type="{}CareProvider" minOccurs="0"/>
 *         &lt;element name="ReferredToOrganization" type="{}HealthCareFacility" minOccurs="0"/>
 *         &lt;element name="ValidityDuration" type="{}Duration" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Referral", propOrder = {
    "placerId",
    "fillerId",
    "referralReason",
    "referringProvider",
    "referringOrganization",
    "referredToProvider",
    "referredToOrganization",
    "validityDuration"
})
public class Referral
    extends SuperClass
{

    @XmlElement(name = "PlacerId")
    protected String placerId;
    @XmlElement(name = "FillerId")
    protected String fillerId;
    @XmlElement(name = "ReferralReason")
    protected String referralReason;
    @XmlElement(name = "ReferringProvider")
    protected CareProvider referringProvider;
    @XmlElement(name = "ReferringOrganization")
    protected HealthCareFacility referringOrganization;
    @XmlElement(name = "ReferredToProvider")
    protected CareProvider referredToProvider;
    @XmlElement(name = "ReferredToOrganization")
    protected HealthCareFacility referredToOrganization;
    @XmlElement(name = "ValidityDuration")
    protected Duration validityDuration;

    /**
     * Gets the value of the placerId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlacerId() {
        return placerId;
    }

    /**
     * Sets the value of the placerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlacerId(String value) {
        this.placerId = value;
    }

    /**
     * Gets the value of the fillerId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFillerId() {
        return fillerId;
    }

    /**
     * Sets the value of the fillerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFillerId(String value) {
        this.fillerId = value;
    }

    /**
     * Gets the value of the referralReason property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferralReason() {
        return referralReason;
    }

    /**
     * Sets the value of the referralReason property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferralReason(String value) {
        this.referralReason = value;
    }

    /**
     * Gets the value of the referringProvider property.
     * 
     * @return
     *     possible object is
     *     {@link CareProvider }
     *     
     */
    public CareProvider getReferringProvider() {
        return referringProvider;
    }

    /**
     * Sets the value of the referringProvider property.
     * 
     * @param value
     *     allowed object is
     *     {@link CareProvider }
     *     
     */
    public void setReferringProvider(CareProvider value) {
        this.referringProvider = value;
    }

    /**
     * Gets the value of the referringOrganization property.
     * 
     * @return
     *     possible object is
     *     {@link HealthCareFacility }
     *     
     */
    public HealthCareFacility getReferringOrganization() {
        return referringOrganization;
    }

    /**
     * Sets the value of the referringOrganization property.
     * 
     * @param value
     *     allowed object is
     *     {@link HealthCareFacility }
     *     
     */
    public void setReferringOrganization(HealthCareFacility value) {
        this.referringOrganization = value;
    }

    /**
     * Gets the value of the referredToProvider property.
     * 
     * @return
     *     possible object is
     *     {@link CareProvider }
     *     
     */
    public CareProvider getReferredToProvider() {
        return referredToProvider;
    }

    /**
     * Sets the value of the referredToProvider property.
     * 
     * @param value
     *     allowed object is
     *     {@link CareProvider }
     *     
     */
    public void setReferredToProvider(CareProvider value) {
        this.referredToProvider = value;
    }

    /**
     * Gets the value of the referredToOrganization property.
     * 
     * @return
     *     possible object is
     *     {@link HealthCareFacility }
     *     
     */
    public HealthCareFacility getReferredToOrganization() {
        return referredToOrganization;
    }

    /**
     * Sets the value of the referredToOrganization property.
     * 
     * @param value
     *     allowed object is
     *     {@link HealthCareFacility }
     *     
     */
    public void setReferredToOrganization(HealthCareFacility value) {
        this.referredToOrganization = value;
    }

    /**
     * Gets the value of the validityDuration property.
     * 
     * @return
     *     possible object is
     *     {@link Duration }
     *     
     */
    public Duration getValidityDuration() {
        return validityDuration;
    }

    /**
     * Sets the value of the validityDuration property.
     * 
     * @param value
     *     allowed object is
     *     {@link Duration }
     *     
     */
    public void setValidityDuration(Duration value) {
        this.validityDuration = value;
    }

}
