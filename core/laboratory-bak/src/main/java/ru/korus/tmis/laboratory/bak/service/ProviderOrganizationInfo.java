
package ru.korus.tmis.laboratory.bak.service;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for providerOrganizationInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="providerOrganizationInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{http://cgm.ru}lpuIDInfo"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
@XmlType(name = "providerOrganizationInfo", propOrder = {
    "id",
    "name"
})
public class ProviderOrganizationInfo {

    @XmlElement(required = true)
    protected LpuIDInfo id;
    @XmlElement(required = true)
    protected String name;
    @XmlAttribute(name = "classCode")
    protected String classCode;
    @XmlAttribute(name = "determinerCode")
    protected String determinerCode;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link ru.korus.tmis.laboratory.bak.service.LpuIDInfo }
     *
     */
    public LpuIDInfo getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     * @param value
     *     allowed object is
     *     {@link ru.korus.tmis.laboratory.bak.service.LpuIDInfo }
     *     
     */
    public void setId(LpuIDInfo value) {
        this.id = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
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
