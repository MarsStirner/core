
package ru.korus.tmis.pix.sda.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * Льготы пациента
 * 
 * <p>Java class for Privilege complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Privilege">
 *   &lt;complexContent>
 *     &lt;extension base="{}BaseSerial">
 *       &lt;sequence>
 *         &lt;element name="category" type="{}CodeAndName" minOccurs="0"/>
 *         &lt;element name="document" type="{}PrivilegeDocument" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Privilege", propOrder = {
    "category",
    "document"
})
public class Privilege
    extends BaseSerial
{

    protected CodeAndName category;
    protected PrivilegeDocument document;

    /**
     * Gets the value of the category property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getCategory() {
        return category;
    }

    /**
     * Sets the value of the category property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setCategory(CodeAndName value) {
        this.category = value;
    }

    /**
     * Gets the value of the document property.
     * 
     * @return
     *     possible object is
     *     {@link PrivilegeDocument }
     *     
     */
    public PrivilegeDocument getDocument() {
        return document;
    }

    /**
     * Sets the value of the document property.
     * 
     * @param value
     *     allowed object is
     *     {@link PrivilegeDocument }
     *     
     */
    public void setDocument(PrivilegeDocument value) {
        this.document = value;
    }

}
