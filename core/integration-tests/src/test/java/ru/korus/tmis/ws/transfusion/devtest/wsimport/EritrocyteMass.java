package ru.korus.tmis.ws.transfusion.devtest.wsimport;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Java class for eritrocyteMass complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="eritrocyteMass">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bloodGroupId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="expirationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="finalHt" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="ht" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="maker" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="number" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="productionDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="rhesusFactorId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="salineVolume" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="volume" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eritrocyteMass", propOrder = { "bloodGroupId", "expirationDate", "finalHt", "ht", "maker", "number", "productionDate", "rhesusFactorId",
        "salineVolume", "volume" })
public class EritrocyteMass {

    protected Integer bloodGroupId;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar expirationDate;
    protected Double finalHt;
    protected Double ht;
    protected String maker;
    protected String number;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar productionDate;
    protected Integer rhesusFactorId;
    protected Double salineVolume;
    protected Double volume;

    /**
     * Gets the value of the bloodGroupId property.
     * 
     * @return possible object is {@link Integer }
     * 
     */
    public Integer getBloodGroupId() {
        return bloodGroupId;
    }

    /**
     * Sets the value of the bloodGroupId property.
     * 
     * @param value
     *            allowed object is {@link Integer }
     * 
     */
    public void setBloodGroupId(final Integer value) {
        this.bloodGroupId = value;
    }

    /**
     * Gets the value of the expirationDate property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getExpirationDate() {
        return expirationDate;
    }

    /**
     * Sets the value of the expirationDate property.
     * 
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setExpirationDate(final XMLGregorianCalendar value) {
        this.expirationDate = value;
    }

    /**
     * Gets the value of the finalHt property.
     * 
     * @return possible object is {@link Double }
     * 
     */
    public Double getFinalHt() {
        return finalHt;
    }

    /**
     * Sets the value of the finalHt property.
     * 
     * @param value
     *            allowed object is {@link Double }
     * 
     */
    public void setFinalHt(final Double value) {
        this.finalHt = value;
    }

    /**
     * Gets the value of the ht property.
     * 
     * @return possible object is {@link Double }
     * 
     */
    public Double getHt() {
        return ht;
    }

    /**
     * Sets the value of the ht property.
     * 
     * @param value
     *            allowed object is {@link Double }
     * 
     */
    public void setHt(final Double value) {
        this.ht = value;
    }

    /**
     * Gets the value of the maker property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getMaker() {
        return maker;
    }

    /**
     * Sets the value of the maker property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setMaker(final String value) {
        this.maker = value;
    }

    /**
     * Gets the value of the number property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getNumber() {
        return number;
    }

    /**
     * Sets the value of the number property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setNumber(final String value) {
        this.number = value;
    }

    /**
     * Gets the value of the productionDate property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getProductionDate() {
        return productionDate;
    }

    /**
     * Sets the value of the productionDate property.
     * 
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setProductionDate(final XMLGregorianCalendar value) {
        this.productionDate = value;
    }

    /**
     * Gets the value of the rhesusFactorId property.
     * 
     * @return possible object is {@link Integer }
     * 
     */
    public Integer getRhesusFactorId() {
        return rhesusFactorId;
    }

    /**
     * Sets the value of the rhesusFactorId property.
     * 
     * @param value
     *            allowed object is {@link Integer }
     * 
     */
    public void setRhesusFactorId(final Integer value) {
        this.rhesusFactorId = value;
    }

    /**
     * Gets the value of the salineVolume property.
     * 
     * @return possible object is {@link Double }
     * 
     */
    public Double getSalineVolume() {
        return salineVolume;
    }

    /**
     * Sets the value of the salineVolume property.
     * 
     * @param value
     *            allowed object is {@link Double }
     * 
     */
    public void setSalineVolume(final Double value) {
        this.salineVolume = value;
    }

    /**
     * Gets the value of the volume property.
     * 
     * @return possible object is {@link Double }
     * 
     */
    public Double getVolume() {
        return volume;
    }

    /**
     * Sets the value of the volume property.
     * 
     * @param value
     *            allowed object is {@link Double }
     * 
     */
    public void setVolume(final Double value) {
        this.volume = value;
    }

}
