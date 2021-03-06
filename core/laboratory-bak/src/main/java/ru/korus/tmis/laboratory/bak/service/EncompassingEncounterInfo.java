
package ru.korus.tmis.laboratory.bak.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for encompassingEncounterInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="encompassingEncounterInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://cgm.ru}eeIdInfo"/>
 *         &lt;element name="code" type="{http://cgm.ru}eeCodeInfo"/>
 *         &lt;element name="effectiveTime" type="{http://cgm.ru}effectiveTimeInfo"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "encompassingEncounterInfo", propOrder = {
    "id",
    "code",
    "effectiveTime"
})
public class EncompassingEncounterInfo {

    @XmlElement(required = true)
    protected EeIdInfo id;
    @XmlElement(required = true)
    protected EeCodeInfo code;
    @XmlElement(required = true)
    protected EffectiveTimeInfo effectiveTime;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link EeIdInfo }
     *     
     */
    public EeIdInfo getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link EeIdInfo }
     *     
     */
    public void setId(EeIdInfo value) {
        this.id = value;
    }

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link EeCodeInfo }
     *     
     */
    public EeCodeInfo getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link EeCodeInfo }
     *     
     */
    public void setCode(EeCodeInfo value) {
        this.code = value;
    }

    /**
     * Gets the value of the effectiveTime property.
     * 
     * @return
     *     possible object is
     *     {@link EffectiveTimeInfo }
     *     
     */
    public EffectiveTimeInfo getEffectiveTime() {
        return effectiveTime;
    }

    /**
     * Sets the value of the effectiveTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link EffectiveTimeInfo }
     *     
     */
    public void setEffectiveTime(EffectiveTimeInfo value) {
        this.effectiveTime = value;
    }

}
