
package org.hl7.v3;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for StrucDoc.Content complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StrucDoc.Content">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="content" type="{urn:hl7-org:v3}StrucDoc.Content" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="linkHtml" type="{urn:hl7-org:v3}StrucDoc.LinkHtml" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="sub" type="{urn:hl7-org:v3}StrucDoc.Sub" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="sup" type="{urn:hl7-org:v3}StrucDoc.Sup" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="br" type="{urn:hl7-org:v3}StrucDoc.Br" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="footnote" type="{urn:hl7-org:v3}StrucDoc.Footnote" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="footnoteRef" type="{urn:hl7-org:v3}StrucDoc.FootnoteRef" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="renderMultiMedia" type="{urn:hl7-org:v3}StrucDoc.RenderMultiMedia" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/choice>
 *       &lt;attribute name="ID1" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="language" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN" />
 *       &lt;attribute name="styleCode" type="{http://www.w3.org/2001/XMLSchema}NMTOKENS" />
 *       &lt;attribute name="revised">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="insert"/>
 *             &lt;enumeration value="delete"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StrucDoc.Content", propOrder = {
    "content"
})
public class StrucDocContent {

    @XmlElementRefs({
        @XmlElementRef(name = "sub", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "content", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "sup", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "footnote", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "footnoteRef", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "linkHtml", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "renderMultiMedia", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "br", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    })
    @XmlMixed
    protected List<Serializable> content;
    @XmlAttribute(name = "ID1")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id1;
    @XmlAttribute(name = "language")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "NMTOKEN")
    protected String language;
    @XmlAttribute(name = "styleCode")
    @XmlSchemaType(name = "NMTOKENS")
    protected List<String> styleCode;
    @XmlAttribute(name = "revised")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String revised;

    /**
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
     * {@link JAXBElement }{@code <}{@link StrucDocSub }{@code >}
     * {@link JAXBElement }{@code <}{@link StrucDocContent }{@code >}
     * {@link JAXBElement }{@code <}{@link StrucDocSup }{@code >}
     * {@link JAXBElement }{@code <}{@link StrucDocFootnote }{@code >}
     * {@link JAXBElement }{@code <}{@link StrucDocFootnoteRef }{@code >}
     * {@link JAXBElement }{@code <}{@link StrucDocLinkHtml }{@code >}
     * {@link String }
     * {@link JAXBElement }{@code <}{@link StrucDocRenderMultiMedia }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * 
     */
    public List<Serializable> getContent() {
        if (content == null) {
            content = new ArrayList<Serializable>();
        }
        return this.content;
    }

    /**
     * Gets the value of the id1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getID1() {
        return id1;
    }

    /**
     * Sets the value of the id1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setID1(String value) {
        this.id1 = value;
    }

    /**
     * Gets the value of the language property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets the value of the language property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguage(String value) {
        this.language = value;
    }

    /**
     * Gets the value of the styleCode property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the styleCode property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStyleCode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getStyleCode() {
        if (styleCode == null) {
            styleCode = new ArrayList<String>();
        }
        return this.styleCode;
    }

    /**
     * Gets the value of the revised property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRevised() {
        return revised;
    }

    /**
     * Sets the value of the revised property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRevised(String value) {
        this.revised = value;
    }

}
