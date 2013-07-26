
package ru.cgm.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for patientInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="patientInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="name" type="{http://cgm.ru}nameInfo"/>
 *         &lt;element name="administrativeGenderCode" type="{http://cgm.ru}administrativeGenderCodeInfo"/>
 *         &lt;element name="birthTime" type="{http://cgm.ru}birthTimeInfo"/>
 *       &lt;/sequence>
 *       &lt;attribute name="classCode" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="determinerCode" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "patientInfo", propOrder = {
    "name",
    "administrativeGenderCode",
    "birthTime"
})
public class PatientInfo {

    @XmlElement(required = true)
    protected NameInfo name;
    @XmlElement(required = true)
    protected AdministrativeGenderCodeInfo administrativeGenderCode;
    @XmlElement(required = true)
    protected BirthTimeInfo birthTime;
    @XmlAttribute(name = "classCode")
    protected String classCode;
    @XmlAttribute(name = "determinerCode")
    protected String determinerCode;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link NameInfo }
     *     
     */
    public NameInfo getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameInfo }
     *     
     */
    public void setName(NameInfo value) {
        this.name = value;
    }

    /**
     * Gets the value of the administrativeGenderCode property.
     * 
     * @return
     *     possible object is
     *     {@link AdministrativeGenderCodeInfo }
     *     
     */
    public AdministrativeGenderCodeInfo getAdministrativeGenderCode() {
        return administrativeGenderCode;
    }

    /**
     * Sets the value of the administrativeGenderCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link AdministrativeGenderCodeInfo }
     *     
     */
    public void setAdministrativeGenderCode(AdministrativeGenderCodeInfo value) {
        this.administrativeGenderCode = value;
    }

    /**
     * Gets the value of the birthTime property.
     * 
     * @return
     *     possible object is
     *     {@link BirthTimeInfo }
     *     
     */
    public BirthTimeInfo getBirthTime() {
        return birthTime;
    }

    /**
     * Sets the value of the birthTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link BirthTimeInfo }
     *     
     */
    public void setBirthTime(BirthTimeInfo value) {
        this.birthTime = value;
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
     * Gets the value of the determinerCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeterminerCode() {
        return determinerCode;
    }

    /**
     * Sets the value of the determinerCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeterminerCode(String value) {
        this.determinerCode = value;
    }

}
