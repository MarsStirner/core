//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.09.15 at 11:16:43 AM MSK 
//


package ru.korus.tmis.admin.service.impl.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for web-containerType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="web-containerType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="session-config" type="{}session-configType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "web-containerType", propOrder = {
    "sessionConfig"
})
public class WebContainerType {

    @XmlElement(name = "session-config", required = true)
    protected SessionConfigType sessionConfig;

    /**
     * Gets the value of the sessionConfig property.
     * 
     * @return
     *     possible object is
     *     {@link SessionConfigType }
     *     
     */
    public SessionConfigType getSessionConfig() {
        return sessionConfig;
    }

    /**
     * Sets the value of the sessionConfig property.
     * 
     * @param value
     *     allowed object is
     *     {@link SessionConfigType }
     *     
     */
    public void setSessionConfig(SessionConfigType value) {
        this.sessionConfig = value;
    }

}