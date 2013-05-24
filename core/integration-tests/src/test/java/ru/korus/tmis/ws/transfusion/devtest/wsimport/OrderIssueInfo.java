package ru.korus.tmis.ws.transfusion.devtest.wsimport;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for orderIssueInfo complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="orderIssueInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="bloodGroupId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="componentId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="componentTypeId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="donorId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="doseCount" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="number" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rhesusFactorId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="volume" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "orderIssueInfo",
        propOrder = { "bloodGroupId", "componentId", "componentTypeId", "donorId", "doseCount", "number", "rhesusFactorId", "volume" })
public class OrderIssueInfo {

    protected Integer bloodGroupId;
    protected Integer componentId;
    protected Integer componentTypeId;
    protected Integer donorId;
    protected Double doseCount;
    protected String number;
    protected Integer rhesusFactorId;
    protected Integer volume;

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
     * Gets the value of the componentId property.
     * 
     * @return possible object is {@link Integer }
     * 
     */
    public Integer getComponentId() {
        return componentId;
    }

    /**
     * Sets the value of the componentId property.
     * 
     * @param value
     *            allowed object is {@link Integer }
     * 
     */
    public void setComponentId(final Integer value) {
        this.componentId = value;
    }

    /**
     * Gets the value of the componentTypeId property.
     * 
     * @return possible object is {@link Integer }
     * 
     */
    public Integer getComponentTypeId() {
        return componentTypeId;
    }

    /**
     * Sets the value of the componentTypeId property.
     * 
     * @param value
     *            allowed object is {@link Integer }
     * 
     */
    public void setComponentTypeId(final Integer value) {
        this.componentTypeId = value;
    }

    /**
     * Gets the value of the donorId property.
     * 
     * @return possible object is {@link Integer }
     * 
     */
    public Integer getDonorId() {
        return donorId;
    }

    /**
     * Sets the value of the donorId property.
     * 
     * @param value
     *            allowed object is {@link Integer }
     * 
     */
    public void setDonorId(final Integer value) {
        this.donorId = value;
    }

    /**
     * Gets the value of the doseCount property.
     * 
     * @return possible object is {@link Double }
     * 
     */
    public Double getDoseCount() {
        return doseCount;
    }

    /**
     * Sets the value of the doseCount property.
     * 
     * @param value
     *            allowed object is {@link Double }
     * 
     */
    public void setDoseCount(final Double value) {
        this.doseCount = value;
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
     * Gets the value of the volume property.
     * 
     * @return possible object is {@link Integer }
     * 
     */
    public Integer getVolume() {
        return volume;
    }

    /**
     * Sets the value of the volume property.
     * 
     * @param value
     *            allowed object is {@link Integer }
     * 
     */
    public void setVolume(final Integer value) {
        this.volume = value;
    }

}
