package ru.korus.tmis.ws.transfusion.devtest.wsimport;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for finalVolume complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="finalVolume">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="anticoagulantInCollect" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="anticoagulantInPlasma" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="anticoagulantVolume" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="collectVolume" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="inletVolume" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="plasmaVolume" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="time" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "finalVolume", propOrder = { "anticoagulantInCollect", "anticoagulantInPlasma", "anticoagulantVolume", "collectVolume", "inletVolume",
        "plasmaVolume", "time" })
public class FinalVolume {

    protected Double anticoagulantInCollect;
    protected Double anticoagulantInPlasma;
    protected Double anticoagulantVolume;
    protected Double collectVolume;
    protected Double inletVolume;
    protected Double plasmaVolume;
    protected Double time;

    /**
     * Gets the value of the anticoagulantInCollect property.
     * 
     * @return possible object is {@link Double }
     * 
     */
    public Double getAnticoagulantInCollect() {
        return anticoagulantInCollect;
    }

    /**
     * Sets the value of the anticoagulantInCollect property.
     * 
     * @param value
     *            allowed object is {@link Double }
     * 
     */
    public void setAnticoagulantInCollect(final Double value) {
        this.anticoagulantInCollect = value;
    }

    /**
     * Gets the value of the anticoagulantInPlasma property.
     * 
     * @return possible object is {@link Double }
     * 
     */
    public Double getAnticoagulantInPlasma() {
        return anticoagulantInPlasma;
    }

    /**
     * Sets the value of the anticoagulantInPlasma property.
     * 
     * @param value
     *            allowed object is {@link Double }
     * 
     */
    public void setAnticoagulantInPlasma(final Double value) {
        this.anticoagulantInPlasma = value;
    }

    /**
     * Gets the value of the anticoagulantVolume property.
     * 
     * @return possible object is {@link Double }
     * 
     */
    public Double getAnticoagulantVolume() {
        return anticoagulantVolume;
    }

    /**
     * Sets the value of the anticoagulantVolume property.
     * 
     * @param value
     *            allowed object is {@link Double }
     * 
     */
    public void setAnticoagulantVolume(final Double value) {
        this.anticoagulantVolume = value;
    }

    /**
     * Gets the value of the collectVolume property.
     * 
     * @return possible object is {@link Double }
     * 
     */
    public Double getCollectVolume() {
        return collectVolume;
    }

    /**
     * Sets the value of the collectVolume property.
     * 
     * @param value
     *            allowed object is {@link Double }
     * 
     */
    public void setCollectVolume(final Double value) {
        this.collectVolume = value;
    }

    /**
     * Gets the value of the inletVolume property.
     * 
     * @return possible object is {@link Double }
     * 
     */
    public Double getInletVolume() {
        return inletVolume;
    }

    /**
     * Sets the value of the inletVolume property.
     * 
     * @param value
     *            allowed object is {@link Double }
     * 
     */
    public void setInletVolume(final Double value) {
        this.inletVolume = value;
    }

    /**
     * Gets the value of the plasmaVolume property.
     * 
     * @return possible object is {@link Double }
     * 
     */
    public Double getPlasmaVolume() {
        return plasmaVolume;
    }

    /**
     * Sets the value of the plasmaVolume property.
     * 
     * @param value
     *            allowed object is {@link Double }
     * 
     */
    public void setPlasmaVolume(final Double value) {
        this.plasmaVolume = value;
    }

    /**
     * Gets the value of the time property.
     * 
     * @return possible object is {@link Double }
     * 
     */
    public Double getTime() {
        return time;
    }

    /**
     * Sets the value of the time property.
     * 
     * @param value
     *            allowed object is {@link Double }
     * 
     */
    public void setTime(final Double value) {
        this.time = value;
    }

}
