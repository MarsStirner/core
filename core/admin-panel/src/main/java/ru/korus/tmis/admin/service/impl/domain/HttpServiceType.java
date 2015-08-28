//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.09.15 at 11:16:43 AM MSK 
//


package ru.korus.tmis.admin.service.impl.domain;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for http-serviceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="http-serviceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="access-log" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="virtual-server" type="{}virtual-serverType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "http-serviceType", propOrder = {
    "accessLog",
    "virtualServer"
})
public class HttpServiceType {

    @XmlElement(name = "access-log", required = true)
    protected String accessLog;
    @XmlElement(name = "virtual-server")
    protected List<VirtualServerType> virtualServer;

    /**
     * Gets the value of the accessLog property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccessLog() {
        return accessLog;
    }

    /**
     * Sets the value of the accessLog property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccessLog(String value) {
        this.accessLog = value;
    }

    /**
     * Gets the value of the virtualServer property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the virtualServer property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVirtualServer().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VirtualServerType }
     * 
     * 
     */
    public List<VirtualServerType> getVirtualServer() {
        if (virtualServer == null) {
            virtualServer = new ArrayList<VirtualServerType>();
        }
        return this.virtualServer;
    }

}