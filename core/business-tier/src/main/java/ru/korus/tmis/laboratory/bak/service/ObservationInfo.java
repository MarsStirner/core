
package ru.korus.tmis.laboratory.bak.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for observationInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="observationInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://cgm.ru}obsIdInfo" minOccurs="0"/>
 *         &lt;element name="code" type="{http://cgm.ru}obsCodeInfo" minOccurs="0"/>
 *         &lt;element name="value" type="{http://cgm.ru}obsValueInfo" minOccurs="0"/>
 *         &lt;element name="effectiveTime" type="{http://cgm.ru}obsEffectiveTimeInfo" minOccurs="0"/>
 *         &lt;element name="specimen" type="{http://cgm.ru}specimenInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="classCode" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="moodCode" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="negationInd" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "observationInfo", propOrder = {
    "id",
    "code",
    "value",
    "effectiveTime",
    "specimen"
})
public class ObservationInfo {

    protected ObsIdInfo id;
    protected ObsCodeInfo code;
    protected ObsValueInfo value;
    protected ObsEffectiveTimeInfo effectiveTime;
    protected SpecimenInfo specimen;
    @XmlAttribute(name = "classCode")
    protected String classCode;
    @XmlAttribute(name = "moodCode")
    protected String moodCode;
    @XmlAttribute(name = "negationInd")
    protected String negationInd;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link ObsIdInfo }
     *     
     */
    public ObsIdInfo getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link ObsIdInfo }
     *     
     */
    public void setId(ObsIdInfo value) {
        this.id = value;
    }

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link ObsCodeInfo }
     *     
     */
    public ObsCodeInfo getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link ObsCodeInfo }
     *     
     */
    public void setCode(ObsCodeInfo value) {
        this.code = value;
    }

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link ObsValueInfo }
     *     
     */
    public ObsValueInfo getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link ObsValueInfo }
     *     
     */
    public void setValue(ObsValueInfo value) {
        this.value = value;
    }

    /**
     * Gets the value of the effectiveTime property.
     * 
     * @return
     *     possible object is
     *     {@link ObsEffectiveTimeInfo }
     *     
     */
    public ObsEffectiveTimeInfo getEffectiveTime() {
        return effectiveTime;
    }

    /**
     * Sets the value of the effectiveTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link ObsEffectiveTimeInfo }
     *     
     */
    public void setEffectiveTime(ObsEffectiveTimeInfo value) {
        this.effectiveTime = value;
    }

    /**
     * Gets the value of the specimen property.
     * 
     * @return
     *     possible object is
     *     {@link SpecimenInfo }
     *     
     */
    public SpecimenInfo getSpecimen() {
        return specimen;
    }

    /**
     * Sets the value of the specimen property.
     * 
     * @param value
     *     allowed object is
     *     {@link SpecimenInfo }
     *     
     */
    public void setSpecimen(SpecimenInfo value) {
        this.specimen = value;
    }

    /**
     * Gets the value of the classCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassCode() {
        return classCode;
    }

    /**
     * Sets the value of the classCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassCode(String value) {
        this.classCode = value;
    }

    /**
     * Gets the value of the moodCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMoodCode() {
        return moodCode;
    }

    /**
     * Sets the value of the moodCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMoodCode(String value) {
        this.moodCode = value;
    }

    /**
     * Gets the value of the negationInd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNegationInd() {
        return negationInd;
    }

    /**
     * Sets the value of the negationInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNegationInd(String value) {
        this.negationInd = value;
    }

}
