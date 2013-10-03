
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for II complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="II">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:hl7-org:v3}ANY">
 *       &lt;attribute name="root" type="{urn:hl7-org:v3}Uid" />
 *       &lt;attribute name="extension" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="identifierName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="displayable" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="scope" type="{urn:hl7-org:v3}IdentifierScope" />
 *       &lt;attribute name="reliability" type="{urn:hl7-org:v3}IdentifierReliability" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "II")
public class II
    extends ANY
{

    @XmlAttribute(name = "root")
    protected String root;
    @XmlAttribute(name = "extension")
    protected String extension;
    @XmlAttribute(name = "identifierName")
    protected String identifierName;
    @XmlAttribute(name = "displayable")
    protected Boolean displayable;
    @XmlAttribute(name = "scope")
    protected IdentifierScope scope;
    @XmlAttribute(name = "reliability")
    protected IdentifierReliability reliability;

    /**
     * Gets the value of the root property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoot() {
        return root;
    }

    /**
     * Sets the value of the root property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoot(String value) {
        this.root = value;
    }

    /**
     * Gets the value of the extension property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExtension() {
        return extension;
    }

    /**
     * Sets the value of the extension property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExtension(String value) {
        this.extension = value;
    }

    /**
     * Gets the value of the identifierName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdentifierName() {
        return identifierName;
    }

    /**
     * Sets the value of the identifierName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentifierName(String value) {
        this.identifierName = value;
    }

    /**
     * Gets the value of the displayable property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDisplayable() {
        return displayable;
    }

    /**
     * Sets the value of the displayable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDisplayable(Boolean value) {
        this.displayable = value;
    }

    /**
     * Gets the value of the scope property.
     * 
     * @return
     *     possible object is
     *     {@link IdentifierScope }
     *     
     */
    public IdentifierScope getScope() {
        return scope;
    }

    /**
     * Sets the value of the scope property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentifierScope }
     *     
     */
    public void setScope(IdentifierScope value) {
        this.scope = value;
    }

    /**
     * Gets the value of the reliability property.
     * 
     * @return
     *     possible object is
     *     {@link IdentifierReliability }
     *     
     */
    public IdentifierReliability getReliability() {
        return reliability;
    }

    /**
     * Sets the value of the reliability property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentifierReliability }
     *     
     */
    public void setReliability(IdentifierReliability value) {
        this.reliability = value;
    }

}
