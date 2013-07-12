
package ru.korus.tmis.pix.sda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SocialHistory complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SocialHistory">
 *   &lt;complexContent>
 *     &lt;extension base="{}SuperClass">
 *       &lt;sequence>
 *         &lt;element name="SocialHabit" type="{}SocialHabit" minOccurs="0"/>
 *         &lt;element name="SocialHabitQty" type="{}SocialHabitQty" minOccurs="0"/>
 *         &lt;element name="SocialHabitComments" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SocialHistory", propOrder = {
    "socialHabit",
    "socialHabitQty",
    "socialHabitComments",
    "status"
})
public class SocialHistory
    extends SuperClass
{

    @XmlElement(name = "SocialHabit")
    protected SocialHabit socialHabit;
    @XmlElement(name = "SocialHabitQty")
    protected SocialHabitQty socialHabitQty;
    @XmlElement(name = "SocialHabitComments")
    protected String socialHabitComments;
    @XmlElement(name = "Status")
    protected String status;

    /**
     * Gets the value of the socialHabit property.
     * 
     * @return
     *     possible object is
     *     {@link SocialHabit }
     *     
     */
    public SocialHabit getSocialHabit() {
        return socialHabit;
    }

    /**
     * Sets the value of the socialHabit property.
     * 
     * @param value
     *     allowed object is
     *     {@link SocialHabit }
     *     
     */
    public void setSocialHabit(SocialHabit value) {
        this.socialHabit = value;
    }

    /**
     * Gets the value of the socialHabitQty property.
     * 
     * @return
     *     possible object is
     *     {@link SocialHabitQty }
     *     
     */
    public SocialHabitQty getSocialHabitQty() {
        return socialHabitQty;
    }

    /**
     * Sets the value of the socialHabitQty property.
     * 
     * @param value
     *     allowed object is
     *     {@link SocialHabitQty }
     *     
     */
    public void setSocialHabitQty(SocialHabitQty value) {
        this.socialHabitQty = value;
    }

    /**
     * Gets the value of the socialHabitComments property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSocialHabitComments() {
        return socialHabitComments;
    }

    /**
     * Sets the value of the socialHabitComments property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSocialHabitComments(String value) {
        this.socialHabitComments = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

}
