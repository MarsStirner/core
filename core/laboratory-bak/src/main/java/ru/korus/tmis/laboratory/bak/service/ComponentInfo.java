
package ru.korus.tmis.laboratory.bak.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for componentInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="componentInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="structuredBody" type="{http://cgm.ru}structuredBodyInfo"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "componentInfo", propOrder = {
    "structuredBody"
})
public class ComponentInfo {

    @XmlElement(required = true)
    protected StructuredBodyInfo structuredBody;

    /**
     * Gets the value of the structuredBody property.
     * 
     * @return
     *     possible object is
     *     {@link ru.korus.tmis.laboratory.bak.service.StructuredBodyInfo }
     *
     */
    public StructuredBodyInfo getStructuredBody() {
        return structuredBody;
    }

    /**
     * Sets the value of the structuredBody property.
     *
     * @param value
     *     allowed object is
     *     {@link ru.korus.tmis.laboratory.bak.service.StructuredBodyInfo }
     *     
     */
    public void setStructuredBody(StructuredBodyInfo value) {
        this.structuredBody = value;
    }

}
