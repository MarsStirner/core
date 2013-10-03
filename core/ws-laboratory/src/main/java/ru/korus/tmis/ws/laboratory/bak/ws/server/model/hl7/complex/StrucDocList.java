
package ru.korus.tmis.ws.laboratory.bak.ws.server.model.hl7.complex;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StrucDoc.List complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StrucDoc.List">
 *   &lt;complexContent>
 *     &lt;extension base="{urn:hl7-org:v3}StrucDoc.Captioned">
 *       &lt;sequence>
 *         &lt;element name="item" type="{urn:hl7-org:v3}StrucDoc.Item" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="listType" type="{urn:hl7-org:v3}StrucDoc.ListType" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StrucDoc.List")
public class StrucDocList
    extends StrucDocCaptioned
{

    @XmlAttribute(name = "listType")
    protected StrucDocListType listType;

    /**
     * Gets the value of the listType property.
     * 
     * @return
     *     possible object is
     *     {@link StrucDocListType }
     *     
     */
    public StrucDocListType getListType() {
        return listType;
    }

    /**
     * Sets the value of the listType property.
     * 
     * @param value
     *     allowed object is
     *     {@link StrucDocListType }
     *     
     */
    public void setListType(StrucDocListType value) {
        this.listType = value;
    }

}
