
package ru.korus.tmis.ehr.ws.callback;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ContainerDocument complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContainerDocument">
 *   &lt;complexContent>
 *     &lt;extension base="{}BaseSerial">
 *       &lt;sequence>
 *         &lt;element name="docType" type="{}CodeAndName" minOccurs="0"/>
 *         &lt;element name="docNum" type="{}String" minOccurs="0"/>
 *         &lt;element name="docDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="docAuthor" type="{}Employee" minOccurs="0"/>
 *         &lt;element name="docTarget" type="{}CodeAndName" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContainerDocument", propOrder = {
    "docType",
    "docNum",
    "docDate",
    "docAuthor",
    "docTarget"
})
public class ContainerDocument
    extends BaseSerial
{

    protected CodeAndName docType;
    protected String docNum;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar docDate;
    protected Employee docAuthor;
    protected CodeAndName docTarget;

    /**
     * Gets the value of the docType property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getDocType() {
        return docType;
    }

    /**
     * Sets the value of the docType property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setDocType(CodeAndName value) {
        this.docType = value;
    }

    /**
     * Gets the value of the docNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocNum() {
        return docNum;
    }

    /**
     * Sets the value of the docNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocNum(String value) {
        this.docNum = value;
    }

    /**
     * Gets the value of the docDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDocDate() {
        return docDate;
    }

    /**
     * Sets the value of the docDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDocDate(XMLGregorianCalendar value) {
        this.docDate = value;
    }

    /**
     * Gets the value of the docAuthor property.
     * 
     * @return
     *     possible object is
     *     {@link Employee }
     *     
     */
    public Employee getDocAuthor() {
        return docAuthor;
    }

    /**
     * Sets the value of the docAuthor property.
     * 
     * @param value
     *     allowed object is
     *     {@link Employee }
     *     
     */
    public void setDocAuthor(Employee value) {
        this.docAuthor = value;
    }

    /**
     * Gets the value of the docTarget property.
     * 
     * @return
     *     possible object is
     *     {@link CodeAndName }
     *     
     */
    public CodeAndName getDocTarget() {
        return docTarget;
    }

    /**
     * Sets the value of the docTarget property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeAndName }
     *     
     */
    public void setDocTarget(CodeAndName value) {
        this.docTarget = value;
    }

}
