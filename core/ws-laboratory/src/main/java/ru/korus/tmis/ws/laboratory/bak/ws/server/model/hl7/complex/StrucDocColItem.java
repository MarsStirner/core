
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StrucDoc.ColItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StrucDoc.ColItem">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:hl7-org:v3}StrucDoc.TableItem">
 *       &lt;attribute name="span" type="{http://www.w3.org/2001/XMLSchema}int" default="1" />
 *       &lt;attribute name="width" type="{urn:hl7-org:v3}StrucDoc.Length" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StrucDoc.ColItem")
@XmlSeeAlso({
    StrucDocColGroup.class,
    StrucDocCol.class
})
public class StrucDocColItem
    extends StrucDocTableItem
{

    @XmlAttribute(name = "span")
    protected Integer span;
    @XmlAttribute(name = "width")
    protected String width;

    /**
     * Gets the value of the span property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getSpan() {
        if (span == null) {
            return  1;
        } else {
            return span;
        }
    }

    /**
     * Sets the value of the span property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setSpan(Integer value) {
        this.span = value;
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

}
