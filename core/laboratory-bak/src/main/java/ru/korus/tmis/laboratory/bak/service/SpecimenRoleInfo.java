
package ru.korus.tmis.laboratory.bak.service;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for specimenRoleInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="specimenRoleInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://cgm.ru}srIdInfo"/>
 *         &lt;element name="specimenPlayingEntity" type="{http://cgm.ru}specimenPlayingEntityInfo"/>
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
@XmlType(name = "specimenRoleInfo", propOrder = {
    "id",
    "specimenPlayingEntity"
})
public class SpecimenRoleInfo {

    @XmlElement(required = true)
    protected SrIdInfo id;
    @XmlElement(required = true)
    protected SpecimenPlayingEntityInfo specimenPlayingEntity;
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
     *     {@link ru.korus.tmis.laboratory.bak.service.SrIdInfo }
     *
     */
    public SrIdInfo getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     * @param value
     *     allowed object is
     *     {@link ru.korus.tmis.laboratory.bak.service.SrIdInfo }
     *
     */
    public void setId(SrIdInfo value) {
        this.id = value;
    }

    /**
     * Gets the value of the specimenPlayingEntity property.
     *
     * @return
     *     possible object is
     *     {@link ru.korus.tmis.laboratory.bak.service.SpecimenPlayingEntityInfo }
     *
     */
    public SpecimenPlayingEntityInfo getSpecimenPlayingEntity() {
        return specimenPlayingEntity;
    }

    /**
     * Sets the value of the specimenPlayingEntity property.
     *
     * @param value
     *     allowed object is
     *     {@link ru.korus.tmis.laboratory.bak.service.SpecimenPlayingEntityInfo }
     *     
     */
    public void setSpecimenPlayingEntity(SpecimenPlayingEntityInfo value) {
        this.specimenPlayingEntity = value;
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
