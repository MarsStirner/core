
package ru.cgm.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for authorInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="authorInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="time" type="{http://cgm.ru}dateTimeInfo"/>
 *         &lt;element name="assignedAuthor" type="{http://cgm.ru}assignedAuthorInfo"/>
 *       &lt;/sequence>
 *       &lt;attribute name="typeCode" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "authorInfo", propOrder = {
    "time",
    "assignedAuthor"
})
public class AuthorInfo {

    @XmlElement(required = true)
    protected DateTimeInfo time;
    @XmlElement(required = true)
    protected AssignedAuthorInfo assignedAuthor;
    @XmlAttribute(name = "typeCode")
    protected String typeCode;

    /**
     * Gets the value of the time property.
     * 
     * @return
     *     possible object is
     *     {@link DateTimeInfo }
     *     
     */
    public DateTimeInfo getTime() {
        return time;
    }

    /**
     * Sets the value of the time property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateTimeInfo }
     *     
     */
    public void setTime(DateTimeInfo value) {
        this.time = value;
    }

    /**
     * Gets the value of the assignedAuthor property.
     * 
     * @return
     *     possible object is
     *     {@link AssignedAuthorInfo }
     *     
     */
    public AssignedAuthorInfo getAssignedAuthor() {
        return assignedAuthor;
    }

    /**
     * Sets the value of the assignedAuthor property.
     * 
     * @param value
     *     allowed object is
     *     {@link AssignedAuthorInfo }
     *     
     */
    public void setAssignedAuthor(AssignedAuthorInfo value) {
        this.assignedAuthor = value;
    }

    /**
     * Gets the value of the typeCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeCode() {
        return typeCode;
    }

    /**
     * Sets the value of the typeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeCode(String value) {
        this.typeCode = value;
    }

}
