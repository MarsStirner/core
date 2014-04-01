
package ru.korus.tmis.ehr.ws.callback;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Address complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Address">
 *   &lt;complexContent>
 *     &lt;extension base="{}BaseSerial">
 *       &lt;sequence>
 *         &lt;element name="country" type="{}String" minOccurs="0"/>
 *         &lt;element name="region" type="{}String" minOccurs="0"/>
 *         &lt;element name="regionKladr" type="{}String" minOccurs="0"/>
 *         &lt;element name="district" type="{}String" minOccurs="0"/>
 *         &lt;element name="districtKladr" type="{}String" minOccurs="0"/>
 *         &lt;element name="city" type="{}String" minOccurs="0"/>
 *         &lt;element name="cityKladr" type="{}String" minOccurs="0"/>
 *         &lt;element name="street" type="{}String" minOccurs="0"/>
 *         &lt;element name="streetKladr" type="{}String" minOccurs="0"/>
 *         &lt;element name="house" type="{}String" minOccurs="0"/>
 *         &lt;element name="block" type="{}String" minOccurs="0"/>
 *         &lt;element name="building" type="{}String" minOccurs="0"/>
 *         &lt;element name="apartment" type="{}String" minOccurs="0"/>
 *         &lt;element name="postalCode" type="{}String" minOccurs="0"/>
 *         &lt;element name="okato" type="{}String" minOccurs="0"/>
 *         &lt;element name="effectiveDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Address", propOrder = {
    "country",
    "region",
    "regionKladr",
    "district",
    "districtKladr",
    "city",
    "cityKladr",
    "street",
    "streetKladr",
    "house",
    "block",
    "building",
    "apartment",
    "postalCode",
    "okato",
    "effectiveDate"
})
public class Address
    extends BaseSerial
{

    protected String country;
    protected String region;
    protected String regionKladr;
    protected String district;
    protected String districtKladr;
    protected String city;
    protected String cityKladr;
    protected String street;
    protected String streetKladr;
    protected String house;
    protected String block;
    protected String building;
    protected String apartment;
    protected String postalCode;
    protected String okato;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar effectiveDate;

    /**
     * Gets the value of the country property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the value of the country property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountry(String value) {
        this.country = value;
    }

    /**
     * Gets the value of the region property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegion() {
        return region;
    }

    /**
     * Sets the value of the region property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegion(String value) {
        this.region = value;
    }

    /**
     * Gets the value of the regionKladr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegionKladr() {
        return regionKladr;
    }

    /**
     * Sets the value of the regionKladr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegionKladr(String value) {
        this.regionKladr = value;
    }

    /**
     * Gets the value of the district property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDistrict() {
        return district;
    }

    /**
     * Sets the value of the district property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDistrict(String value) {
        this.district = value;
    }

    /**
     * Gets the value of the districtKladr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDistrictKladr() {
        return districtKladr;
    }

    /**
     * Sets the value of the districtKladr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDistrictKladr(String value) {
        this.districtKladr = value;
    }

    /**
     * Gets the value of the city property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the value of the city property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCity(String value) {
        this.city = value;
    }

    /**
     * Gets the value of the cityKladr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCityKladr() {
        return cityKladr;
    }

    /**
     * Sets the value of the cityKladr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCityKladr(String value) {
        this.cityKladr = value;
    }

    /**
     * Gets the value of the street property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets the value of the street property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreet(String value) {
        this.street = value;
    }

    /**
     * Gets the value of the streetKladr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStreetKladr() {
        return streetKladr;
    }

    /**
     * Sets the value of the streetKladr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStreetKladr(String value) {
        this.streetKladr = value;
    }

    /**
     * Gets the value of the house property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHouse() {
        return house;
    }

    /**
     * Sets the value of the house property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHouse(String value) {
        this.house = value;
    }

    /**
     * Gets the value of the block property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBlock() {
        return block;
    }

    /**
     * Sets the value of the block property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBlock(String value) {
        this.block = value;
    }

    /**
     * Gets the value of the building property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBuilding() {
        return building;
    }

    /**
     * Sets the value of the building property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBuilding(String value) {
        this.building = value;
    }

    /**
     * Gets the value of the apartment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApartment() {
        return apartment;
    }

    /**
     * Sets the value of the apartment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApartment(String value) {
        this.apartment = value;
    }

    /**
     * Gets the value of the postalCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the value of the postalCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostalCode(String value) {
        this.postalCode = value;
    }

    /**
     * Gets the value of the okato property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOkato() {
        return okato;
    }

    /**
     * Sets the value of the okato property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOkato(String value) {
        this.okato = value;
    }

    /**
     * Gets the value of the effectiveDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * Sets the value of the effectiveDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEffectiveDate(XMLGregorianCalendar value) {
        this.effectiveDate = value;
    }

}
