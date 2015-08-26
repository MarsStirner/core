//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.09.15 at 11:16:43 AM MSK 
//


package ru.korus.tmis.admin.service.impl.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for jms-serviceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="jms-serviceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="jms-host" type="{}jms-hostType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="default-jms-host" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="addresslist-behavior" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "jms-serviceType", propOrder = {
    "jmsHost"
})
public class JmsServiceType {

    @XmlElement(name = "jms-host", required = true)
    protected JmsHostType jmsHost;
    @XmlAttribute(name = "default-jms-host")
    protected String defaultJmsHost;
    @XmlAttribute(name = "addresslist-behavior")
    protected String addresslistBehavior;

    /**
     * Gets the value of the jmsHost property.
     * 
     * @return
     *     possible object is
     *     {@link JmsHostType }
     *     
     */
    public JmsHostType getJmsHost() {
        return jmsHost;
    }

    /**
     * Sets the value of the jmsHost property.
     * 
     * @param value
     *     allowed object is
     *     {@link JmsHostType }
     *     
     */
    public void setJmsHost(JmsHostType value) {
        this.jmsHost = value;
    }

    /**
     * Gets the value of the defaultJmsHost property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultJmsHost() {
        return defaultJmsHost;
    }

    /**
     * Sets the value of the defaultJmsHost property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultJmsHost(String value) {
        this.defaultJmsHost = value;
    }

    /**
     * Gets the value of the addresslistBehavior property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddresslistBehavior() {
        return addresslistBehavior;
    }

    /**
     * Sets the value of the addresslistBehavior property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddresslistBehavior(String value) {
        this.addresslistBehavior = value;
    }

}
