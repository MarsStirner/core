
package ru.korus.tmis.laboratory.bak.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for structuredBodyInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="structuredBodyInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="component" type="{http://cgm.ru}subComponentInfo"/>
 *         &lt;element name="component" type="{http://cgm.ru}subComponentInfo"/>
 *         &lt;element name="component" type="{http://cgm.ru}subComponentInfo"/>
 *         &lt;element name="component" type="{http://cgm.ru}subComponentInfo"/>
 *         &lt;element name="component" type="{http://cgm.ru}subComponentInfo"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "structuredBodyInfo", propOrder = {
    "content"
})
public class StructuredBodyInfo {

    @XmlElementRef(name = "component", type = JAXBElement.class, required = false)
    protected List<JAXBElement<SubComponentInfo>> content;

    /**
     * Gets the rest of the content model. 
     * 
     * <p>
     * You are getting this "catch-all" property because of the following reason: 
     * The field name "Component" is used by two different parts of a schema. See: 
     * line 113 of file:/C:/development/github-work/tmis-core/core/ws-laboratory/src/main/resources/CGMSERVICE_hl7.wsdl
     * line 111 of file:/C:/development/github-work/tmis-core/core/ws-laboratory/src/main/resources/CGMSERVICE_hl7.wsdl
     * <p>
     * To get rid of this property, apply a property customization to one 
     * of both of the following declarations to change their names: 
     * Gets the value of the content property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the content property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link javax.xml.bind.JAXBElement }{@code <}{@link ru.korus.tmis.laboratory.bak.service.SubComponentInfo }{@code >}
     * 
     * 
     */
    public List<JAXBElement<SubComponentInfo>> getContent() {
        if (content == null) {
            content = new ArrayList<JAXBElement<SubComponentInfo>>();
        }
        return this.content;
    }

}
