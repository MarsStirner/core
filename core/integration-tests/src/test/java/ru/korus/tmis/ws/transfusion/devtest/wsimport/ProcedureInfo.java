package ru.korus.tmis.ws.transfusion.devtest.wsimport;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Java class for procedureInfo complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="procedureInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="acdLoad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="afterHemodynamicsArterialPressure" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="afterHemodynamicsPulse" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="afterHemodynamicsTemperature" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="balance" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="beforeHemodynamicsArterialPressure" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="beforeHemodynamicsPulse" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="beforeHemodynamicsTemperature" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="caLoad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="changeInletAcRatio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="changeProductVolume" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="changeSpeed" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="changeTbv" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="changeTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="changeVolume" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="complications" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="contraindication" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="factDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="initialInletAcRatio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="initialProductVolume" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="initialSpeed" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="initialTbv" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="initialTime" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="initialVolume" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="naClLoad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="otherLoad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="otherRemove" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="packRemove" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="totalLoad" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="totalRemove" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "procedureInfo", propOrder = { "acdLoad", "afterHemodynamicsArterialPressure", "afterHemodynamicsPulse", "afterHemodynamicsTemperature",
        "balance", "beforeHemodynamicsArterialPressure", "beforeHemodynamicsPulse", "beforeHemodynamicsTemperature", "caLoad", "changeInletAcRatio",
        "changeProductVolume", "changeSpeed", "changeTbv", "changeTime", "changeVolume", "complications", "contraindication", "factDate", "id",
        "initialInletAcRatio", "initialProductVolume", "initialSpeed", "initialTbv", "initialTime", "initialVolume", "naClLoad", "otherLoad", "otherRemove",
        "packRemove", "totalLoad", "totalRemove" })
public class ProcedureInfo {

    protected String acdLoad;
    protected String afterHemodynamicsArterialPressure;
    protected String afterHemodynamicsPulse;
    protected String afterHemodynamicsTemperature;
    protected String balance;
    protected String beforeHemodynamicsArterialPressure;
    protected String beforeHemodynamicsPulse;
    protected String beforeHemodynamicsTemperature;
    protected String caLoad;
    protected String changeInletAcRatio;
    protected Double changeProductVolume;
    protected String changeSpeed;
    protected String changeTbv;
    protected String changeTime;
    protected Double changeVolume;
    protected String complications;
    protected String contraindication;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar factDate;
    protected Integer id;
    protected String initialInletAcRatio;
    protected Double initialProductVolume;
    protected String initialSpeed;
    protected String initialTbv;
    protected String initialTime;
    protected Double initialVolume;
    protected String naClLoad;
    protected String otherLoad;
    protected String otherRemove;
    protected String packRemove;
    protected String totalLoad;
    protected String totalRemove;

    /**
     * Gets the value of the acdLoad property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getAcdLoad() {
        return acdLoad;
    }

    /**
     * Sets the value of the acdLoad property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setAcdLoad(final String value) {
        this.acdLoad = value;
    }

    /**
     * Gets the value of the afterHemodynamicsArterialPressure property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getAfterHemodynamicsArterialPressure() {
        return afterHemodynamicsArterialPressure;
    }

    /**
     * Sets the value of the afterHemodynamicsArterialPressure property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setAfterHemodynamicsArterialPressure(final String value) {
        this.afterHemodynamicsArterialPressure = value;
    }

    /**
     * Gets the value of the afterHemodynamicsPulse property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getAfterHemodynamicsPulse() {
        return afterHemodynamicsPulse;
    }

    /**
     * Sets the value of the afterHemodynamicsPulse property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setAfterHemodynamicsPulse(final String value) {
        this.afterHemodynamicsPulse = value;
    }

    /**
     * Gets the value of the afterHemodynamicsTemperature property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getAfterHemodynamicsTemperature() {
        return afterHemodynamicsTemperature;
    }

    /**
     * Sets the value of the afterHemodynamicsTemperature property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setAfterHemodynamicsTemperature(final String value) {
        this.afterHemodynamicsTemperature = value;
    }

    /**
     * Gets the value of the balance property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getBalance() {
        return balance;
    }

    /**
     * Sets the value of the balance property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setBalance(final String value) {
        this.balance = value;
    }

    /**
     * Gets the value of the beforeHemodynamicsArterialPressure property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getBeforeHemodynamicsArterialPressure() {
        return beforeHemodynamicsArterialPressure;
    }

    /**
     * Sets the value of the beforeHemodynamicsArterialPressure property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setBeforeHemodynamicsArterialPressure(final String value) {
        this.beforeHemodynamicsArterialPressure = value;
    }

    /**
     * Gets the value of the beforeHemodynamicsPulse property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getBeforeHemodynamicsPulse() {
        return beforeHemodynamicsPulse;
    }

    /**
     * Sets the value of the beforeHemodynamicsPulse property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setBeforeHemodynamicsPulse(final String value) {
        this.beforeHemodynamicsPulse = value;
    }

    /**
     * Gets the value of the beforeHemodynamicsTemperature property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getBeforeHemodynamicsTemperature() {
        return beforeHemodynamicsTemperature;
    }

    /**
     * Sets the value of the beforeHemodynamicsTemperature property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setBeforeHemodynamicsTemperature(final String value) {
        this.beforeHemodynamicsTemperature = value;
    }

    /**
     * Gets the value of the caLoad property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getCaLoad() {
        return caLoad;
    }

    /**
     * Sets the value of the caLoad property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setCaLoad(final String value) {
        this.caLoad = value;
    }

    /**
     * Gets the value of the changeInletAcRatio property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getChangeInletAcRatio() {
        return changeInletAcRatio;
    }

    /**
     * Sets the value of the changeInletAcRatio property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setChangeInletAcRatio(final String value) {
        this.changeInletAcRatio = value;
    }

    /**
     * Gets the value of the changeProductVolume property.
     * 
     * @return possible object is {@link Double }
     * 
     */
    public Double getChangeProductVolume() {
        return changeProductVolume;
    }

    /**
     * Sets the value of the changeProductVolume property.
     * 
     * @param value
     *            allowed object is {@link Double }
     * 
     */
    public void setChangeProductVolume(final Double value) {
        this.changeProductVolume = value;
    }

    /**
     * Gets the value of the changeSpeed property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getChangeSpeed() {
        return changeSpeed;
    }

    /**
     * Sets the value of the changeSpeed property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setChangeSpeed(final String value) {
        this.changeSpeed = value;
    }

    /**
     * Gets the value of the changeTbv property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getChangeTbv() {
        return changeTbv;
    }

    /**
     * Sets the value of the changeTbv property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setChangeTbv(final String value) {
        this.changeTbv = value;
    }

    /**
     * Gets the value of the changeTime property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getChangeTime() {
        return changeTime;
    }

    /**
     * Sets the value of the changeTime property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setChangeTime(final String value) {
        this.changeTime = value;
    }

    /**
     * Gets the value of the changeVolume property.
     * 
     * @return possible object is {@link Double }
     * 
     */
    public Double getChangeVolume() {
        return changeVolume;
    }

    /**
     * Sets the value of the changeVolume property.
     * 
     * @param value
     *            allowed object is {@link Double }
     * 
     */
    public void setChangeVolume(final Double value) {
        this.changeVolume = value;
    }

    /**
     * Gets the value of the complications property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getComplications() {
        return complications;
    }

    /**
     * Sets the value of the complications property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setComplications(final String value) {
        this.complications = value;
    }

    /**
     * Gets the value of the contraindication property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getContraindication() {
        return contraindication;
    }

    /**
     * Sets the value of the contraindication property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setContraindication(final String value) {
        this.contraindication = value;
    }

    /**
     * Gets the value of the factDate property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getFactDate() {
        return factDate;
    }

    /**
     * Sets the value of the factDate property.
     * 
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setFactDate(final XMLGregorianCalendar value) {
        this.factDate = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return possible object is {@link Integer }
     * 
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *            allowed object is {@link Integer }
     * 
     */
    public void setId(final Integer value) {
        this.id = value;
    }

    /**
     * Gets the value of the initialInletAcRatio property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getInitialInletAcRatio() {
        return initialInletAcRatio;
    }

    /**
     * Sets the value of the initialInletAcRatio property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setInitialInletAcRatio(final String value) {
        this.initialInletAcRatio = value;
    }

    /**
     * Gets the value of the initialProductVolume property.
     * 
     * @return possible object is {@link Double }
     * 
     */
    public Double getInitialProductVolume() {
        return initialProductVolume;
    }

    /**
     * Sets the value of the initialProductVolume property.
     * 
     * @param value
     *            allowed object is {@link Double }
     * 
     */
    public void setInitialProductVolume(final Double value) {
        this.initialProductVolume = value;
    }

    /**
     * Gets the value of the initialSpeed property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getInitialSpeed() {
        return initialSpeed;
    }

    /**
     * Sets the value of the initialSpeed property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setInitialSpeed(final String value) {
        this.initialSpeed = value;
    }

    /**
     * Gets the value of the initialTbv property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getInitialTbv() {
        return initialTbv;
    }

    /**
     * Sets the value of the initialTbv property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setInitialTbv(final String value) {
        this.initialTbv = value;
    }

    /**
     * Gets the value of the initialTime property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getInitialTime() {
        return initialTime;
    }

    /**
     * Sets the value of the initialTime property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setInitialTime(final String value) {
        this.initialTime = value;
    }

    /**
     * Gets the value of the initialVolume property.
     * 
     * @return possible object is {@link Double }
     * 
     */
    public Double getInitialVolume() {
        return initialVolume;
    }

    /**
     * Sets the value of the initialVolume property.
     * 
     * @param value
     *            allowed object is {@link Double }
     * 
     */
    public void setInitialVolume(final Double value) {
        this.initialVolume = value;
    }

    /**
     * Gets the value of the naClLoad property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getNaClLoad() {
        return naClLoad;
    }

    /**
     * Sets the value of the naClLoad property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setNaClLoad(final String value) {
        this.naClLoad = value;
    }

    /**
     * Gets the value of the otherLoad property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getOtherLoad() {
        return otherLoad;
    }

    /**
     * Sets the value of the otherLoad property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setOtherLoad(final String value) {
        this.otherLoad = value;
    }

    /**
     * Gets the value of the otherRemove property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getOtherRemove() {
        return otherRemove;
    }

    /**
     * Sets the value of the otherRemove property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setOtherRemove(final String value) {
        this.otherRemove = value;
    }

    /**
     * Gets the value of the packRemove property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPackRemove() {
        return packRemove;
    }

    /**
     * Sets the value of the packRemove property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setPackRemove(final String value) {
        this.packRemove = value;
    }

    /**
     * Gets the value of the totalLoad property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTotalLoad() {
        return totalLoad;
    }

    /**
     * Sets the value of the totalLoad property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setTotalLoad(final String value) {
        this.totalLoad = value;
    }

    /**
     * Gets the value of the totalRemove property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTotalRemove() {
        return totalRemove;
    }

    /**
     * Sets the value of the totalRemove property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setTotalRemove(final String value) {
        this.totalRemove = value;
    }

}
