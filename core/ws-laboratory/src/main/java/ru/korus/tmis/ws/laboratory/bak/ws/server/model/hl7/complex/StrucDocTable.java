
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StrucDoc.Table complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StrucDoc.Table">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:hl7-org:v3}StrucDoc.Captioned">
 *       &lt;sequence>
 *         &lt;element name="col" type="{urn:hl7-org:v3}StrucDoc.Col" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="colgroup" type="{urn:hl7-org:v3}StrucDoc.ColGroup" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="thead" type="{urn:hl7-org:v3}StrucDoc.TRowGroup" minOccurs="0"/>
 *         &lt;element name="tfoot" type="{urn:hl7-org:v3}StrucDoc.TRowGroup" minOccurs="0"/>
 *         &lt;element name="tbody" type="{urn:hl7-org:v3}StrucDoc.TRowGroup" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="summary" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="width" type="{urn:hl7-org:v3}StrucDoc.Length" />
 *       &lt;attribute name="border" type="{urn:hl7-org:v3}StrucDoc.Length" />
 *       &lt;attribute name="frame" type="{urn:hl7-org:v3}StrucDoc.Frame" />
 *       &lt;attribute name="rules" type="{urn:hl7-org:v3}StrucDoc.Rules" />
 *       &lt;attribute name="cellspacing" type="{urn:hl7-org:v3}StrucDoc.Length" />
 *       &lt;attribute name="cellpadding" type="{urn:hl7-org:v3}StrucDoc.Length" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StrucDoc.Table")
public class StrucDocTable
    extends StrucDocCaptioned
{

    @XmlAttribute(name = "summary")
    protected String summary;
    @XmlAttribute(name = "width")
    protected String width;
    @XmlAttribute(name = "border")
    protected String border;
    @XmlAttribute(name = "frame")
    protected StrucDocFrame frame;
    @XmlAttribute(name = "rules")
    protected StrucDocRules rules;
    @XmlAttribute(name = "cellspacing")
    protected String cellspacing;
    @XmlAttribute(name = "cellpadding")
    protected String cellpadding;

    /**
     * Gets the value of the summary property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSummary() {
        return summary;
    }

    /**
     * Sets the value of the summary property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSummary(String value) {
        this.summary = value;
    }

    /**
     * Gets the value of the width property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWidth() {
        return width;
    }

    /**
     * Sets the value of the width property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWidth(String value) {
        this.width = value;
    }

    /**
     * Gets the value of the border property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBorder() {
        return border;
    }

    /**
     * Sets the value of the border property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBorder(String value) {
        this.border = value;
    }

    /**
     * Gets the value of the frame property.
     * 
     * @return
     *     possible object is
     *     {@link StrucDocFrame }
     *     
     */
    public StrucDocFrame getFrame() {
        return frame;
    }

    /**
     * Sets the value of the frame property.
     * 
     * @param value
     *     allowed object is
     *     {@link StrucDocFrame }
     *     
     */
    public void setFrame(StrucDocFrame value) {
        this.frame = value;
    }

    /**
     * Gets the value of the rules property.
     * 
     * @return
     *     possible object is
     *     {@link StrucDocRules }
     *     
     */
    public StrucDocRules getRules() {
        return rules;
    }

    /**
     * Sets the value of the rules property.
     * 
     * @param value
     *     allowed object is
     *     {@link StrucDocRules }
     *     
     */
    public void setRules(StrucDocRules value) {
        this.rules = value;
    }

    /**
     * Gets the value of the cellspacing property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCellspacing() {
        return cellspacing;
    }

    /**
     * Sets the value of the cellspacing property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCellspacing(String value) {
        this.cellspacing = value;
    }

    /**
     * Gets the value of the cellpadding property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCellpadding() {
        return cellpadding;
    }

    /**
     * Sets the value of the cellpadding property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCellpadding(String value) {
        this.cellpadding = value;
    }

}
