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
 * <p>Java class for network-configType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="network-configType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="protocols" type="{}protocolsType"/>
 *         &lt;element name="network-listeners" type="{}network-listenersType"/>
 *         &lt;element name="transports" type="{}transportsType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "network-configType", propOrder = {
    "protocols",
    "networkListeners",
    "transports"
})
public class NetworkConfigType {

    @XmlElement(required = true)
    protected ProtocolsType protocols;
    @XmlElement(name = "network-listeners", required = true)
    protected NetworkListenersType networkListeners;
    @XmlElement(required = true)
    protected TransportsType transports;

    /**
     * Gets the value of the protocols property.
     * 
     * @return
     *     possible object is
     *     {@link ProtocolsType }
     *     
     */
    public ProtocolsType getProtocols() {
        return protocols;
    }

    /**
     * Sets the value of the protocols property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProtocolsType }
     *     
     */
    public void setProtocols(ProtocolsType value) {
        this.protocols = value;
    }

    /**
     * Gets the value of the networkListeners property.
     * 
     * @return
     *     possible object is
     *     {@link NetworkListenersType }
     *     
     */
    public NetworkListenersType getNetworkListeners() {
        return networkListeners;
    }

    /**
     * Sets the value of the networkListeners property.
     * 
     * @param value
     *     allowed object is
     *     {@link NetworkListenersType }
     *     
     */
    public void setNetworkListeners(NetworkListenersType value) {
        this.networkListeners = value;
    }

    /**
     * Gets the value of the transports property.
     * 
     * @return
     *     possible object is
     *     {@link TransportsType }
     *     
     */
    public TransportsType getTransports() {
        return transports;
    }

    /**
     * Sets the value of the transports property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportsType }
     *     
     */
    public void setTransports(TransportsType value) {
        this.transports = value;
    }

}
