
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlMixed;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StrucDoc.TCell complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StrucDoc.TCell">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:hl7-org:v3}StrucDoc.TableItem">
 *       &lt;sequence>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="footnote" type="{urn:hl7-org:v3}StrucDoc.Footnote"/>
 *           &lt;element name="footnoteRef" type="{urn:hl7-org:v3}StrucDoc.FootnoteRef"/>
 *           &lt;element name="linkHtml" type="{urn:hl7-org:v3}StrucDoc.LinkHtml"/>
 *           &lt;element name="sub" type="{urn:hl7-org:v3}StrucDoc.Sub"/>
 *           &lt;element name="sup" type="{urn:hl7-org:v3}StrucDoc.Sup"/>
 *           &lt;element name="content" type="{urn:hl7-org:v3}StrucDoc.Content"/>
 *           &lt;element name="br" type="{urn:hl7-org:v3}StrucDoc.Br"/>
 *           &lt;element name="renderMultiMedia" type="{urn:hl7-org:v3}StrucDoc.RenderMultiMedia"/>
 *           &lt;element name="paragraph" type="{urn:hl7-org:v3}StrucDoc.Paragraph"/>
 *           &lt;element name="list" type="{urn:hl7-org:v3}StrucDoc.List"/>
 *           &lt;element name="table" type="{urn:hl7-org:v3}StrucDoc.Table"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="abbr" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="axis" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="headers" type="{urn:hl7-org:v3}set_IDREF" />
 *       &lt;attribute name="scope" type="{urn:hl7-org:v3}StrucDoc.CellScope" />
 *       &lt;attribute name="rowspan" type="{http://www.w3.org/2001/XMLSchema}int" default="1" />
 *       &lt;attribute name="colspan" type="{http://www.w3.org/2001/XMLSchema}int" default="1" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StrucDoc.TCell", propOrder = {
    "content"
})
public class StrucDocTCell {

    @XmlElementRefs({
        @XmlElementRef(name = "table", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "paragraph", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "renderMultiMedia", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "content", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "linkHtml", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "footnote", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "sup", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "sub", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "br", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "footnoteRef", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "list", namespace = "urn:hl7-org:v3", type = JAXBElement.class, required = false)
    })
    @XmlMixed
    protected List<Serializable> content;
    @XmlAttribute(name = "abbr")
    protected String abbr;
    @XmlAttribute(name = "axis")
    protected String axis;
    @XmlAttribute(name = "headers")
    @XmlIDREF
    protected List<Object> headers;
    @XmlAttribute(name = "scope")
    protected StrucDocCellScope scope;
    @XmlAttribute(name = "rowspan")
    protected Integer rowspan;
    @XmlAttribute(name = "colspan")
    protected Integer colspan;

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
     * {@link JAXBElement }{@code <}{@link StrucDocTable }{@code >}
     * {@link JAXBElement }{@code <}{@link StrucDocParagraph }{@code >}
     * {@link JAXBElement }{@code <}{@link StrucDocRenderMultiMedia }{@code >}
     * {@link String }
     * {@link JAXBElement }{@code <}{@link StrucDocFootnote }{@code >}
     * {@link JAXBElement }{@code <}{@link StrucDocLinkHtml }{@code >}
     * {@link JAXBElement }{@code <}{@link StrucDocContent }{@code >}
     * {@link JAXBElement }{@code <}{@link StrucDocSub }{@code >}
     * {@link JAXBElement }{@code <}{@link StrucDocSup }{@code >}
     * {@link JAXBElement }{@code <}{@link StrucDocFootnoteRef }{@code >}
     * {@link JAXBElement }{@code <}{@link StrucDocBr }{@code >}
     * {@link JAXBElement }{@code <}{@link StrucDocList }{@code >}
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
     * Gets the value of the abbr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAbbr() {
        return abbr;
    }

    /**
     * Sets the value of the abbr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAbbr(String value) {
        this.abbr = value;
    }

    /**
     * Gets the value of the axis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAxis() {
        return axis;
    }

    /**
     * Sets the value of the axis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAxis(String value) {
        this.axis = value;
    }

    /**
     * Gets the value of the headers property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the headers property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHeaders().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Object }
     * 
     * 
     */
    public List<Object> getHeaders() {
        if (headers == null) {
            headers = new ArrayList<Object>();
        }
        return this.headers;
    }

    /**
     * Gets the value of the scope property.
     * 
     * @return
     *     possible object is
     *     {@link StrucDocCellScope }
     *     
     */
    public StrucDocCellScope getScope() {
        return scope;
    }

    /**
     * Sets the value of the scope property.
     * 
     * @param value
     *     allowed object is
     *     {@link StrucDocCellScope }
     *     
     */
    public void setScope(StrucDocCellScope value) {
        this.scope = value;
    }

    /**
     * Gets the value of the rowspan property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getRowspan() {
        if (rowspan == null) {
            return  1;
        } else {
            return rowspan;
        }
    }

    /**
     * Sets the value of the rowspan property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRowspan(Integer value) {
        this.rowspan = value;
    }

    /**
     * Gets the value of the colspan property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getColspan() {
        if (colspan == null) {
            return  1;
        } else {
            return colspan;
        }
    }

    /**
     * Sets the value of the colspan property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setColspan(Integer value) {
        this.colspan = value;
    }

}
